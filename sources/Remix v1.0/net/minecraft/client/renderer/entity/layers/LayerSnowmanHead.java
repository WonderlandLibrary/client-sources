package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;

public class LayerSnowmanHead implements LayerRenderer
{
    private final RenderSnowMan field_177152_a;
    
    public LayerSnowmanHead(final RenderSnowMan p_i46110_1_) {
        this.field_177152_a = p_i46110_1_;
    }
    
    public void func_177151_a(final EntitySnowman p_177151_1_, final float p_177151_2_, final float p_177151_3_, final float p_177151_4_, final float p_177151_5_, final float p_177151_6_, final float p_177151_7_, final float p_177151_8_) {
        if (!p_177151_1_.isInvisible()) {
            GlStateManager.pushMatrix();
            this.field_177152_a.func_177123_g().head.postRender(0.0625f);
            final float var9 = 0.625f;
            GlStateManager.translate(0.0f, -0.34375f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(var9, -var9, -var9);
            Minecraft.getMinecraft().getItemRenderer().renderItem(p_177151_1_, new ItemStack(Blocks.pumpkin, 1), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.func_177151_a((EntitySnowman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
