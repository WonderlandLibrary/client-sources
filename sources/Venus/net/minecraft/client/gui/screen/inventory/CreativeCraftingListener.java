/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CreativeCraftingListener
implements IContainerListener {
    private final Minecraft mc;

    public CreativeCraftingListener(Minecraft minecraft) {
        this.mc = minecraft;
    }

    @Override
    public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList) {
    }

    @Override
    public void sendSlotContents(Container container, int n, ItemStack itemStack) {
        this.mc.playerController.sendSlotPacket(itemStack, n);
    }

    @Override
    public void sendWindowProperty(Container container, int n, int n2) {
    }
}

