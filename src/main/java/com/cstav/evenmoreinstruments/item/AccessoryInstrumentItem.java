package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableInstrumentItem;
import com.cstav.evenmoreinstruments.util.CommonUtil;
import com.cstav.genshinstrument.event.InstrumentPlayedEvent;
import com.cstav.genshinstrument.event.InstrumentPlayedEvent.ByPlayer.ByPlayerArgs;
import com.cstav.genshinstrument.networking.OpenInstrumentPacketSender;
import com.cstav.genshinstrument.util.ServerUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * An instrument that requires the usage of a {@link InstrumentAccessoryItem}
 */
public class AccessoryInstrumentItem extends CreditableInstrumentItem {
    private final InstrumentAccessoryItem accessorySupplier;

    public AccessoryInstrumentItem(OpenInstrumentPacketSender onOpenRequest, InstrumentAccessoryItem accessorySupplier, String credit) {
        super(onOpenRequest, credit);
        this.accessorySupplier = accessorySupplier;
    }
    public AccessoryInstrumentItem(OpenInstrumentPacketSender onOpenRequest, Properties properties, InstrumentAccessoryItem accessorySupplier,
                                   String credit) {
        super(onOpenRequest, properties, credit);
        this.accessorySupplier = accessorySupplier;
    }

    public InstrumentAccessoryItem getAccessoryItem() {
        return accessorySupplier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        final ItemStack handItem = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.getItemInHand(CommonUtil.getOffhand(pUsedHand)).is(getAccessoryItem())) {
            if (!pLevel.isClientSide) {
                pPlayer.displayClientMessage(
                    Component.translatable(
                        "item.evenmoreinstruments.instrument.accessory.not_present",
                        getAccessoryItem().getName(new ItemStack(getAccessoryItem()))
                    ),
                    true
                );
            }

            return InteractionResultHolder.fail(handItem);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void onAccessoryUsed(final ByPlayerArgs args, final ItemStack accessory) {
        if (!accessory.isDamageableItem())
            return;

        accessory.hurtAndBreak(
            hurtInstrumentBy(args, accessory),
            args.player,
            (player) -> {
                player.broadcastBreakEvent(player.getUsedItemHand());
                ServerUtil.setInstrumentClosed(player);
            }
        );
    }

    public int hurtInstrumentBy(final ByPlayerArgs args, final ItemStack accessory) {
        return 1;
    }


    // Call AccessoryInstrumentItem#onAccessoryUsed
    static {
        InstrumentPlayedEvent.ByPlayer.EVENT.register(AccessoryInstrumentItem::onInstrumentPlayedEvent);
    }

    public static void onInstrumentPlayedEvent(final ByPlayerArgs args) {
        if (args.level.isClientSide)
            return;

        if (!args.isItemInstrument())
            return;

        final Item instruemntItem = args.player.getItemInHand(args.hand.get()).getItem();
        if (!(instruemntItem instanceof AccessoryInstrumentItem aiItem))
            return;

        final ItemStack offhandStack = args.player.getItemInHand(CommonUtil.getOffhand(args.hand.get()));
        if (!(offhandStack.getItem() instanceof InstrumentAccessoryItem))
            return;

        if (offhandStack.is(aiItem.getAccessoryItem())) {
            aiItem.onAccessoryUsed(args, offhandStack);
        }
    }
}