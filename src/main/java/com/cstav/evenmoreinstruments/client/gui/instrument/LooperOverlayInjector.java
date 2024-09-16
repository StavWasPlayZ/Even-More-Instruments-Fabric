package com.cstav.evenmoreinstruments.client.gui.instrument;

import com.cstav.evenmoreinstruments.client.KeyMappings;
import com.cstav.evenmoreinstruments.networking.EMIPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.DoesLooperExistPacket;
import com.cstav.evenmoreinstruments.networking.packet.LooperRecordStatePacket;
import com.cstav.evenmoreinstruments.util.LooperUtil;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.InstrumentScreen;
import com.cstav.genshinstrument.util.InstrumentEntityData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

@Environment(EnvType.CLIENT)
public class LooperOverlayInjector {
    private static final int REC_BTN_WIDTH = 120;

    private static InstrumentScreen screen = null;
    private static boolean isRecording = false;
    private static Button recordBtn;

    @SuppressWarnings("resource")
    public static void afterScreenInit(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (!(screen instanceof InstrumentScreen instrumentScreen))
            return;

        final Player player = Minecraft.getInstance().player;

        if (InstrumentEntityData.isItem(player)) {
            final InteractionHand hand = InstrumentEntityData.getHand(player);
            final ItemStack instrumentItem = player.getItemInHand(hand);

            if (!LooperUtil.hasLooperTag(instrumentItem))
                return;

            EMIPacketHandler.sendToServer(new DoesLooperExistPacket(hand));
        } else {
            final BlockPos instrumentBlockPos = InstrumentEntityData.getBlockPos(player);
            final BlockEntity instrumentBE = player.level().getBlockEntity(instrumentBlockPos);

            if (!LooperUtil.hasLooperTag(instrumentBE))
                return;

            EMIPacketHandler.sendToServer(new DoesLooperExistPacket());
        }

        LooperOverlayInjector.screen = instrumentScreen;

        Screens.getButtons(instrumentScreen).add(
            recordBtn = Button.builder(
                    appendRecordKeyHint(Component.translatable("button.evenmoreinstruments.record")),
                    LooperOverlayInjector::onRecordPress
                )
                .width(REC_BTN_WIDTH)
                .pos((instrumentScreen.width - REC_BTN_WIDTH) / 2, 5)
                .build()
        );

        ScreenEvents.remove(instrumentScreen).register(LooperOverlayInjector::onScreenClose);
    }
    public static void beforeScreenInit(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (!(screen instanceof InstrumentScreen))
            return;

        ScreenKeyboardEvents.beforeKeyPress(screen).register(LooperOverlayInjector::onKeyboardPress);
    }

    public static void handleLooperRemoved() {
        removeRecordButton();
        screen = null;
    }

    private static MutableComponent appendRecordKeyHint(final MutableComponent component) {
        return component
            .append(" (")
            .append(KeyMappings.RECORD.getTranslatedKeyMessage())
            .append(")");
    }

    private static void onKeyboardPress(Screen screen, int key, int scancode, int modifiers) {
        if (KeyMappings.RECORD.matches(key, scancode)) {

            if (recordBtn != null) {
                recordBtn.playDownSound(Minecraft.getInstance().getSoundManager());
                recordBtn.onPress();
            }

        }
    }

    private static void onScreenClose(final Screen screen) {
        if (LooperOverlayInjector.screen != screen)
            return;

        isRecording = false;
        LooperOverlayInjector.screen = null;
    }

    @SuppressWarnings("resource")
    private static void onRecordPress(final Button btn) {
        final LocalPlayer player = Minecraft.getInstance().player;

        final boolean isItem = InstrumentEntityData.isItem(player);
        final InteractionHand hand = isItem ?
            InstrumentEntityData.getHand(Minecraft.getInstance().player)
            : null;


        if (isRecording) {
            removeRecordButton();
            screen = null;
        } else
            btn.setMessage(appendRecordKeyHint(Component.translatable("button.evenmoreinstruments.stop")));

        isRecording = !isRecording;
        EMIPacketHandler.sendToServer(new LooperRecordStatePacket(isRecording, hand));
    }

//    private static BlockEntity getIBE(final Player player) {
//        final BlockPos instrumentPos = InstrumentEntityData.getBlockPos(player);
//
//        return (instrumentPos == null) ? null
//            : player.level().getBlockEntity(instrumentPos);
//    }


    public static void removeRecordButton() {
        if (screen == null)
            return;

        Screens.getButtons(screen).remove(recordBtn);
        recordBtn = null;
    }
}
