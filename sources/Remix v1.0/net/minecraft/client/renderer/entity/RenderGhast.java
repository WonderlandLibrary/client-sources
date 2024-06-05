package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderGhast extends RenderLiving
{
    private static final ResourceLocation ghastTextures;
    private static final ResourceLocation ghastShootingTextures;
    
    public RenderGhast(final RenderManager p_i46174_1_) {
        super(p_i46174_1_, new ModelGhast(), 0.5f);
    }
    
    protected ResourceLocation func_180576_a(final EntityGhast p_180576_1_) {
        return p_180576_1_.func_110182_bF() ? RenderGhast.ghastShootingTextures : RenderGhast.ghastTextures;
    }
    
    protected void preRenderCallback(final EntityGhast p_77041_1_, final float p_77041_2_) {
        final float var3 = 1.0f;
        final float var4 = (8.0f + var3) / 2.0f;
        final float var5 = (8.0f + 1.0f / var3) / 2.0f;
        GlStateManager.scale(var5, var4, var5);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityGhast)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180576_a((EntityGhast)p_110775_1_);
    }
    
    static {
        ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
        ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
    }
}
