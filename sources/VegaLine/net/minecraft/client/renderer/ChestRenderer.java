/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class ChestRenderer {
    public void renderChestBrightness(Block blockIn, float color) {
        GlStateManager.color(color, color, color, 1.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(blockIn));
    }
}

