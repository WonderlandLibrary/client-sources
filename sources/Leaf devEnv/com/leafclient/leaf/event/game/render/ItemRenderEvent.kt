package com.leafclient.leaf.event.game.render

import fr.shyrogan.publisher4k.Cancellable
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

abstract class ItemRenderEvent {

    /**
     * Event called before the render of an item
     */
    class Render(val itemStack: ItemStack, val hand: EnumHand): Cancellable()

    /**
     * Event called when the transformations are done
     */
    class Transform(val itemStack: ItemStack, val hand: EnumHand, val swingProgress: Float)

}