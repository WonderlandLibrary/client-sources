/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

public class RenderEntity
extends Render {
    private static final String __OBFID = "CL_00000986";

    public RenderEntity(RenderManager p_i46185_1_) {
        super(p_i46185_1_);
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GlStateManager.pushMatrix();
        RenderEntity.renderOffsetAABB(p_76986_1_.getEntityBoundingBox(), p_76986_2_ - p_76986_1_.lastTickPosX, p_76986_4_ - p_76986_1_.lastTickPosY, p_76986_6_ - p_76986_1_.lastTickPosZ);
        GlStateManager.popMatrix();
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}

