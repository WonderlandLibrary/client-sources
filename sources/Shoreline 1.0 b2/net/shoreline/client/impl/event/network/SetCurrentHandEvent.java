package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Event;
import net.shoreline.client.util.Globals;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.shoreline.client.mixin.network.MixinClientPlayerEntity;

/**
 *
 * @author linus
 * @since 1.0
 *
 * @see MixinClientPlayerEntity
 */
public class SetCurrentHandEvent extends Event implements Globals
{
    //
    private final Hand hand;

    public SetCurrentHandEvent(Hand hand)
    {
        this.hand = hand;
    }

    public Hand getHand()
    {
        return hand;
    }

    public ItemStack getStackInHand()
    {
        return mc.player.getStackInHand(hand);
    }
}
