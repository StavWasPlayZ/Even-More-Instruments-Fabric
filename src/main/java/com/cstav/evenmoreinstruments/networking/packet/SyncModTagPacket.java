package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;


public class SyncModTagPacket implements IModPacket {
    private final CompoundTag modTag;
    private final BlockPos pos;

    public SyncModTagPacket(final CompoundTag modTag, final BlockPos pos) {
        this.modTag = modTag;
        this.pos = pos;
    }

    public SyncModTagPacket(final FriendlyByteBuf buf) {
        // Assuming we only send the INITIAL data of a looper,
        // we don't need to read over 0x20000 or however many zeroes.
        modTag = buf.readNbt();
        pos = buf.readBlockPos();
    }

    @Override
    public void write(final FriendlyByteBuf buf) {
        buf.writeNbt(modTag);
        buf.writeBlockPos(pos);
    }

    @SuppressWarnings("resource")
    @Override
    public void handle(Player player, PacketSender responseSender) {
        final BlockEntity be = Minecraft.getInstance().player.level().getBlockEntity(pos);

        if (be != null)
            ((InjectedBlockEntity) be).setModTag(modTag);
    }
}
