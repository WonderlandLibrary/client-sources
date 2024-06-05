package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class RenderSnowball extends Render
{
    protected final Item field_177084_a;
    private final RenderItem field_177083_e;
    
    public RenderSnowball(final RenderManager p_i46137_1_, final Item p_i46137_2_, final RenderItem p_i46137_3_) {
        super(p_i46137_1_);
        this.field_177084_a = p_i46137_2_;
        this.field_177083_e = p_i46137_3_;
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        this.bindTexture(TextureMap.locationBlocksTexture);
        this.field_177083_e.func_175043_b(this.func_177082_d(p_76986_1_));
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ItemStack func_177082_d(final Entity p_177082_1_) {
        return new ItemStack(this.field_177084_a, 1, 0);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return TextureMap.locationBlocksTexture;
    }
}
