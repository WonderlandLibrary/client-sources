// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.ResourceLocation;

public class RenderPainting extends Render
{
    private static final ResourceLocation field_110807_a;
    private static final String __OBFID = "CL_00001018";
    
    static {
        field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
    }
    
    public RenderPainting(final RenderManager p_i46150_1_) {
        super(p_i46150_1_);
    }
    
    public void doRender(final EntityPainting p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(p_76986_2_, p_76986_4_, p_76986_6_);
        GlStateManager.rotate(180.0f - p_76986_8_, 0.0f, 1.0f, 0.0f);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(p_76986_1_);
        final EntityPainting.EnumArt var10 = p_76986_1_.art;
        final float var11 = 0.0625f;
        GlStateManager.scale(var11, var11, var11);
        this.func_77010_a(p_76986_1_, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    protected ResourceLocation func_180562_a(final EntityPainting p_180562_1_) {
        return RenderPainting.field_110807_a;
    }
    
    private void func_77010_a(final EntityPainting p_77010_1_, final int p_77010_2_, final int p_77010_3_, final int p_77010_4_, final int p_77010_5_) {
        final float var6 = -p_77010_2_ / 2.0f;
        final float var7 = -p_77010_3_ / 2.0f;
        final float var8 = 0.5f;
        final float var9 = 0.75f;
        final float var10 = 0.8125f;
        final float var11 = 0.0f;
        final float var12 = 0.0625f;
        final float var13 = 0.75f;
        final float var14 = 0.8125f;
        final float var15 = 0.001953125f;
        final float var16 = 0.001953125f;
        final float var17 = 0.7519531f;
        final float var18 = 0.7519531f;
        final float var19 = 0.0f;
        final float var20 = 0.0625f;
        for (int var21 = 0; var21 < p_77010_2_ / 16; ++var21) {
            for (int var22 = 0; var22 < p_77010_3_ / 16; ++var22) {
                final float var23 = var6 + (var21 + 1) * 16;
                final float var24 = var6 + var21 * 16;
                final float var25 = var7 + (var22 + 1) * 16;
                final float var26 = var7 + var22 * 16;
                this.func_77008_a(p_77010_1_, (var23 + var24) / 2.0f, (var25 + var26) / 2.0f);
                final float var27 = (p_77010_4_ + p_77010_2_ - var21 * 16) / 256.0f;
                final float var28 = (p_77010_4_ + p_77010_2_ - (var21 + 1) * 16) / 256.0f;
                final float var29 = (p_77010_5_ + p_77010_3_ - var22 * 16) / 256.0f;
                final float var30 = (p_77010_5_ + p_77010_3_ - (var22 + 1) * 16) / 256.0f;
                final Tessellator var31 = Tessellator.getInstance();
                final WorldRenderer var32 = var31.getWorldRenderer();
                var32.startDrawingQuads();
                var32.func_178980_d(0.0f, 0.0f, -1.0f);
                var32.addVertexWithUV(var23, var26, -var8, var28, var29);
                var32.addVertexWithUV(var24, var26, -var8, var27, var29);
                var32.addVertexWithUV(var24, var25, -var8, var27, var30);
                var32.addVertexWithUV(var23, var25, -var8, var28, var30);
                var32.func_178980_d(0.0f, 0.0f, 1.0f);
                var32.addVertexWithUV(var23, var25, var8, var9, var11);
                var32.addVertexWithUV(var24, var25, var8, var10, var11);
                var32.addVertexWithUV(var24, var26, var8, var10, var12);
                var32.addVertexWithUV(var23, var26, var8, var9, var12);
                var32.func_178980_d(0.0f, 1.0f, 0.0f);
                var32.addVertexWithUV(var23, var25, -var8, var13, var15);
                var32.addVertexWithUV(var24, var25, -var8, var14, var15);
                var32.addVertexWithUV(var24, var25, var8, var14, var16);
                var32.addVertexWithUV(var23, var25, var8, var13, var16);
                var32.func_178980_d(0.0f, -1.0f, 0.0f);
                var32.addVertexWithUV(var23, var26, var8, var13, var15);
                var32.addVertexWithUV(var24, var26, var8, var14, var15);
                var32.addVertexWithUV(var24, var26, -var8, var14, var16);
                var32.addVertexWithUV(var23, var26, -var8, var13, var16);
                var32.func_178980_d(-1.0f, 0.0f, 0.0f);
                var32.addVertexWithUV(var23, var25, var8, var18, var19);
                var32.addVertexWithUV(var23, var26, var8, var18, var20);
                var32.addVertexWithUV(var23, var26, -var8, var17, var20);
                var32.addVertexWithUV(var23, var25, -var8, var17, var19);
                var32.func_178980_d(1.0f, 0.0f, 0.0f);
                var32.addVertexWithUV(var24, var25, -var8, var18, var19);
                var32.addVertexWithUV(var24, var26, -var8, var18, var20);
                var32.addVertexWithUV(var24, var26, var8, var17, var20);
                var32.addVertexWithUV(var24, var25, var8, var17, var19);
                var31.draw();
            }
        }
    }
    
    private void func_77008_a(final EntityPainting p_77008_1_, final float p_77008_2_, final float p_77008_3_) {
        int var4 = MathHelper.floor_double(p_77008_1_.posX);
        final int var5 = MathHelper.floor_double(p_77008_1_.posY + p_77008_3_ / 16.0f);
        int var6 = MathHelper.floor_double(p_77008_1_.posZ);
        final EnumFacing var7 = p_77008_1_.field_174860_b;
        if (var7 == EnumFacing.NORTH) {
            var4 = MathHelper.floor_double(p_77008_1_.posX + p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.WEST) {
            var6 = MathHelper.floor_double(p_77008_1_.posZ - p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.SOUTH) {
            var4 = MathHelper.floor_double(p_77008_1_.posX - p_77008_2_ / 16.0f);
        }
        if (var7 == EnumFacing.EAST) {
            var6 = MathHelper.floor_double(p_77008_1_.posZ + p_77008_2_ / 16.0f);
        }
        final int var8 = this.renderManager.worldObj.getCombinedLight(new BlockPos(var4, var5, var6), 0);
        final int var9 = var8 % 65536;
        final int var10 = var8 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var9, (float)var10);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180562_a((EntityPainting)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityPainting)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
