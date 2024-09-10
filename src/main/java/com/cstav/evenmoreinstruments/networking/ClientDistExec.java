package com.cstav.evenmoreinstruments.networking;

import com.cstav.evenmoreinstruments.client.gui.instrument.LooperOverlayInjector;
import com.cstav.evenmoreinstruments.client.gui.instrument.noteblockinstrument.NoteBlockInstrumentScreen;
import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import com.cstav.evenmoreinstruments.networking.packet.s2c.LooperPlayStatePacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.LooperUnplayablePacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.OpenNoteBlockInstrumentPacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.SyncModTagPacket;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.cstav.genshinstrument.util.ServerUtil.switchEntry;

/**
 * <p>This class holds network client methods.</p>
 * tuwo06 aka ign leorio told me to name this as such for the memes,
 * this is meant to be called "ClientMethods".
 */
public class ClientDistExec {
    public static final Map<String, BiConsumer<? extends IModPacket, Context>> PACKET_SWITCH = Map.ofEntries(
        switchEntry(ClientDistExec::handle, LooperPlayStatePacket.class),
        switchEntry(ClientDistExec::handle, LooperUnplayablePacket.class),
        switchEntry(ClientDistExec::handle, OpenNoteBlockInstrumentPacket.class),
        switchEntry(ClientDistExec::handle, SyncModTagPacket.class)
    );


    private static void handle(final LooperPlayStatePacket packet, final Context context) {
        final Level level = Minecraft.getInstance().player.level();

        final List<LivingEntity> entities = level.getEntitiesOfClass(
            LivingEntity.class,
            (new AABB(packet.blockPos)).inflate(3)
        );

        // Parrots go brrrr
        for (final LivingEntity livingentity : entities)
            livingentity.setRecordPlayingNearby(packet.blockPos, packet.isPlaying);
    }

    private static void handle(final LooperUnplayablePacket packet, final Context context) {
        LooperOverlayInjector.handleLooperRemoved();
    }

    private static void handle(final OpenNoteBlockInstrumentPacket packet, final Context context) {
        Minecraft.getInstance().setScreen(new NoteBlockInstrumentScreen(packet.instrument));
    }

    private static void handle(final SyncModTagPacket packet, final Context context) {
        final BlockEntity be = Minecraft.getInstance().player.level().getBlockEntity(packet.pos);

        if (be != null)
            ((InjectedBlockEntity) be).evenmoreinstruments$setModTag(packet.modTag);
    }
}
