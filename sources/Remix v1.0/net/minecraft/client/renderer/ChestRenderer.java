package net.minecraft.client.renderer;

import net.minecraft.block.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;

public class ChestRenderer
{
    public void func_178175_a(final Block p_178175_1_, final float p_178175_2_) {
        GlStateManager.color(p_178175_2_, p_178175_2_, p_178175_2_, 1.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        TileEntityRendererChestHelper.instance.renderByItem(new ItemStack(p_178175_1_));
    }
}
