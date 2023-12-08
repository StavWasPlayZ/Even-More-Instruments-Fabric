package com.cstav.evenmoreinstruments.client.gui.instrument;

import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.DoesLooperExistPacket;
import com.cstav.evenmoreinstruments.networking.packet.LooperRecordStatePacket;
import com.cstav.evenmoreinstruments.util.LooperUtil;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.InstrumentScreen;
import com.cstav.genshinstrument.util.InstrumentEntityData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.impl.client.screen.ScreenExtensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
    public static void onScreenInit(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (!(screen instanceof InstrumentScreen instrumentScreen))
            return;

        final Player player = Minecraft.getInstance().player;

        if (InstrumentEntityData.isItem(player)) {
            final InteractionHand hand = InstrumentEntityData.getHand(player);
            final ItemStack instrumentItem = player.getItemInHand(hand);

            if (!LooperUtil.hasLooperTag(instrumentItem))
                return;

            ModPacketHandler.sendToServer(new DoesLooperExistPacket(hand));
        } else {
            final BlockPos instrumentBlockPos = InstrumentEntityData.getBlockPos(player);
            final BlockEntity instrumentBE = player.getLevel().getBlockEntity(instrumentBlockPos);

            if (!LooperUtil.hasLooperTag(instrumentBE))
                return;

            ModPacketHandler.sendToServer(new DoesLooperExistPacket());
        }

        LooperOverlayInjector.screen = instrumentScreen;

        ScreenExtensions.getExtensions(instrumentScreen).fabric_getButtons().add(
            recordBtn = new Button((screen.width - REC_BTN_WIDTH) / 2, 5, REC_BTN_WIDTH, 20,
                Component.translatable("button.evenmoreinstruments.record"),
                LooperOverlayInjector::onRecordPress
            )
        );

        ScreenEvents.remove(instrumentScreen).register(LooperOverlayInjector::onScreenClose);
    }
    public static void handleLooperRemoved() {
        removeRecordButton();
        screen = null;
    }

    public static void onScreenClose(final Screen screen) {
        if (!isRecording || (LooperOverlayInjector.screen != screen))
            return;

        final Player player = Minecraft.getInstance().player;

        ModPacketHandler.sendToServer(
            new LooperRecordStatePacket(false,
                InstrumentEntityData.isItem(player)
                    ? InstrumentEntityData.getHand(player)
                    : null
            )
        );

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

        isRecording = isItem
            ? LooperUtil.isRecording(LooperUtil.looperTag(player.getItemInHand(hand)))
            : LooperUtil.isRecording(LooperUtil.looperTag(getIBE(player)));


        if (isRecording) {
            removeRecordButton();
            screen = null;
        } else
            btn.setMessage(Component.translatable("button.evenmoreinstruments.stop"));

        isRecording = !isRecording;
        ModPacketHandler.sendToServer(new LooperRecordStatePacket(isRecording, hand));
    }

    private static BlockEntity getIBE(final Player player) {
        final BlockPos instrumentPos = InstrumentEntityData.getBlockPos(player);

        return (instrumentPos == null) ? null
            : player.getLevel().getBlockEntity(instrumentPos);
    }


    public static void removeRecordButton() {
        if (screen == null)
            return;

        ScreenExtensions.getExtensions(screen)
            .fabric_getButtons().remove(recordBtn);
    }
}
