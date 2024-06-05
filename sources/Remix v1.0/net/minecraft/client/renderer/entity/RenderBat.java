package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderBat extends RenderLiving
{
    private static final ResourceLocation batTextures;
    
    public RenderBat(final RenderManager p_i46192_1_) {
        super(p_i46192_1_, new ModelBat(), 0.25f);
    }
    
    protected ResourceLocation func_180566_a(final EntityBat p_180566_1_) {
        return RenderBat.batTextures;
    }
    
    protected void func_180567_a(final EntityBat p_180567_1_, final float p_180567_2_) {
        GlStateManager.scale(0.35f, 0.35f, 0.35f);
    }
    
    protected void rotateCorpse(final EntityBat p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        if (!p_77043_1_.getIsBatHanging()) {
            GlStateManager.translate(0.0f, MathHelper.cos(p_77043_2_ * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GlStateManager.translate(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.func_180567_a((EntityBat)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((EntityBat)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180566_a((EntityBat)p_110775_1_);
    }
    
    static {
        batTextures = new ResourceLocation("textures/entity/bat.png");
    }
}
