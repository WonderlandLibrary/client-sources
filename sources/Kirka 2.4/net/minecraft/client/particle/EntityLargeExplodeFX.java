/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX
extends EntityFX {
    private static final ResourceLocation field_110127_a = new ResourceLocation("textures/entity/explosion.png");
    private int field_70581_a;
    private int field_70584_aq;
    private TextureManager theRenderEngine;
    private float field_70582_as;
    private static final String __OBFID = "CL_00000910";

    protected EntityLargeExplodeFX(TextureManager p_i1213_1_, World worldIn, double p_i1213_3_, double p_i1213_5_, double p_i1213_7_, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_) {
        super(worldIn, p_i1213_3_, p_i1213_5_, p_i1213_7_, 0.0, 0.0, 0.0);
        this.theRenderEngine = p_i1213_1_;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleRed = this.particleBlue;
        this.field_70582_as = 1.0f - (float)p_i1213_9_ * 0.5f;
    }

    @Override
    public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
        int var9 = (int)(((float)this.field_70581_a + p_180434_3_) * 15.0f / (float)this.field_70584_aq);
        if (var9 <= 15) {
            this.theRenderEngine.bindTexture(field_110127_a);
            float var10 = (float)(var9 % 4) / 4.0f;
            float var11 = var10 + 0.24975f;
            float var12 = (float)(var9 / 4) / 4.0f;
            float var13 = var12 + 0.24975f;
            float var14 = 2.0f * this.field_70582_as;
            float var15 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_180434_3_ - interpPosX);
            float var16 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_180434_3_ - interpPosY);
            float var17 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_180434_3_ - interpPosZ);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            p_180434_1_.startDrawingQuads();
            p_180434_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
            p_180434_1_.func_178980_d(0.0f, 1.0f, 0.0f);
            p_180434_1_.setBrightness(240);
            p_180434_1_.addVertexWithUV(var15 - p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14, var17 - p_180434_6_ * var14 - p_180434_8_ * var14, var11, var13);
            p_180434_1_.addVertexWithUV(var15 - p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14, var17 - p_180434_6_ * var14 + p_180434_8_ * var14, var11, var12);
            p_180434_1_.addVertexWithUV(var15 + p_180434_4_ * var14 + p_180434_7_ * var14, var16 + p_180434_5_ * var14, var17 + p_180434_6_ * var14 + p_180434_8_ * var14, var10, var12);
            p_180434_1_.addVertexWithUV(var15 + p_180434_4_ * var14 - p_180434_7_ * var14, var16 - p_180434_5_ * var14, var17 + p_180434_6_ * var14 - p_180434_8_ * var14, var10, var13);
            Tessellator.getInstance().draw();
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.enableLighting();
        }
    }

    @Override
    public int getBrightnessForRender(float p_70070_1_) {
        return 61680;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_70581_a;
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    public static class Factory
    implements IParticleFactory {
        private static final String __OBFID = "CL_00002598";

        @Override
        public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int ... p_178902_15_) {
            return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }

}

