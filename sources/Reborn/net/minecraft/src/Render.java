package net.minecraft.src;

import org.lwjgl.opengl.*;

public abstract class Render
{
    protected RenderManager renderManager;
    private ModelBase modelBase;
    protected RenderBlocks renderBlocks;
    protected float shadowSize;
    protected float shadowOpaque;
    
    public Render() {
        this.modelBase = new ModelBiped();
        this.renderBlocks = new RenderBlocks();
        this.shadowSize = 0.0f;
        this.shadowOpaque = 1.0f;
    }
    
    public abstract void doRender(final Entity p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    protected void loadTexture(final String par1Str) {
        this.renderManager.renderEngine.bindTexture(par1Str);
    }
    
    protected boolean loadDownloadableImageTexture(final String par1Str, final String par2Str) {
        final RenderEngine var3 = this.renderManager.renderEngine;
        final int var4 = var3.getTextureForDownloadableImage(par1Str, par2Str);
        if (var4 >= 0) {
            GL11.glBindTexture(3553, var4);
            var3.resetBoundTexture();
            return true;
        }
        return false;
    }
    
    private void renderEntityOnFire(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8) {
        GL11.glDisable(2896);
        final Icon var9 = Block.fire.func_94438_c(0);
        final Icon var10 = Block.fire.func_94438_c(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        final float var11 = par1Entity.width * 1.4f;
        GL11.glScalef(var11, var11, var11);
        this.loadTexture("/terrain.png");
        final Tessellator var12 = Tessellator.instance;
        float var13 = 0.5f;
        final float var14 = 0.0f;
        float var15 = par1Entity.height / var11;
        float var16 = (float)(par1Entity.posY - par1Entity.boundingBox.minY);
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, -0.3f + (int)var15 * 0.02f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float var17 = 0.0f;
        int var18 = 0;
        var12.startDrawingQuads();
        while (var15 > 0.0f) {
            Icon var19;
            if (var18 % 2 == 0) {
                var19 = var9;
            }
            else {
                var19 = var10;
            }
            float var20 = var19.getMinU();
            final float var21 = var19.getMinV();
            float var22 = var19.getMaxU();
            final float var23 = var19.getMaxV();
            if (var18 / 2 % 2 == 0) {
                final float var24 = var22;
                var22 = var20;
                var20 = var24;
            }
            var12.addVertexWithUV(var13 - var14, 0.0f - var16, var17, var22, var23);
            var12.addVertexWithUV(-var13 - var14, 0.0f - var16, var17, var20, var23);
            var12.addVertexWithUV(-var13 - var14, 1.4f - var16, var17, var20, var21);
            var12.addVertexWithUV(var13 - var14, 1.4f - var16, var17, var22, var21);
            var15 -= 0.45f;
            var16 -= 0.45f;
            var13 *= 0.9f;
            var17 += 0.03f;
            ++var18;
        }
        var12.draw();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }
    
    private void renderShadow(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.renderManager.renderEngine.bindTexture("%clamp%/misc/shadow.png");
        final World var10 = this.getWorldFromRenderManager();
        GL11.glDepthMask(false);
        float var11 = this.shadowSize;
        if (par1Entity instanceof EntityLiving) {
            final EntityLiving var12 = (EntityLiving)par1Entity;
            var11 *= var12.getRenderSizeModifier();
            if (var12.isChild()) {
                var11 *= 0.5f;
            }
        }
        final double var13 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par9;
        final double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par9 + par1Entity.getShadowSize();
        final double var15 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par9;
        final int var16 = MathHelper.floor_double(var13 - var11);
        final int var17 = MathHelper.floor_double(var13 + var11);
        final int var18 = MathHelper.floor_double(var14 - var11);
        final int var19 = MathHelper.floor_double(var14);
        final int var20 = MathHelper.floor_double(var15 - var11);
        final int var21 = MathHelper.floor_double(var15 + var11);
        final double var22 = par2 - var13;
        final double var23 = par4 - var14;
        final double var24 = par6 - var15;
        final Tessellator var25 = Tessellator.instance;
        var25.startDrawingQuads();
        for (int var26 = var16; var26 <= var17; ++var26) {
            for (int var27 = var18; var27 <= var19; ++var27) {
                for (int var28 = var20; var28 <= var21; ++var28) {
                    final int var29 = var10.getBlockId(var26, var27 - 1, var28);
                    if (var29 > 0 && var10.getBlockLightValue(var26, var27, var28) > 3) {
                        this.renderShadowOnBlock(Block.blocksList[var29], par2, par4 + par1Entity.getShadowSize(), par6, var26, var27, var28, par8, var11, var22, var23 + par1Entity.getShadowSize(), var24);
                    }
                }
            }
        }
        var25.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
    }
    
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }
    
    private void renderShadowOnBlock(final Block par1Block, final double par2, final double par4, final double par6, final int par8, final int par9, final int par10, final float par11, final float par12, final double par13, final double par15, final double par17) {
        final Tessellator var19 = Tessellator.instance;
        if (par1Block.renderAsNormalBlock()) {
            double var20 = (par11 - (par4 - (par9 + par15)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(par8, par9, par10);
            if (var20 >= 0.0) {
                if (var20 > 1.0) {
                    var20 = 1.0;
                }
                var19.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)var20);
                final double var21 = par8 + par1Block.getBlockBoundsMinX() + par13;
                final double var22 = par8 + par1Block.getBlockBoundsMaxX() + par13;
                final double var23 = par9 + par1Block.getBlockBoundsMinY() + par15 + 0.015625;
                final double var24 = par10 + par1Block.getBlockBoundsMinZ() + par17;
                final double var25 = par10 + par1Block.getBlockBoundsMaxZ() + par17;
                final float var26 = (float)((par2 - var21) / 2.0 / par12 + 0.5);
                final float var27 = (float)((par2 - var22) / 2.0 / par12 + 0.5);
                final float var28 = (float)((par6 - var24) / 2.0 / par12 + 0.5);
                final float var29 = (float)((par6 - var25) / 2.0 / par12 + 0.5);
                var19.addVertexWithUV(var21, var23, var24, var26, var28);
                var19.addVertexWithUV(var21, var23, var25, var26, var29);
                var19.addVertexWithUV(var22, var23, var25, var27, var29);
                var19.addVertexWithUV(var22, var23, var24, var27, var28);
            }
        }
    }
    
    public static void renderOffsetAABB(final AxisAlignedBB par0AxisAlignedBB, final double par1, final double par3, final double par5) {
        GL11.glDisable(3553);
        final Tessellator var7 = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var7.startDrawingQuads();
        var7.setTranslation(par1, par3, par5);
        var7.setNormal(0.0f, 0.0f, -1.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(0.0f, 0.0f, 1.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0f, -1.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0f, 1.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.setNormal(-1.0f, 0.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(1.0f, 0.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setTranslation(0.0, 0.0, 0.0);
        var7.draw();
        GL11.glEnable(3553);
    }
    
    public static void renderAABB(final AxisAlignedBB par0AxisAlignedBB) {
        final Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.draw();
    }
    
    public void setRenderManager(final RenderManager par1RenderManager) {
        this.renderManager = par1RenderManager;
    }
    
    public void doRenderShadowAndFire(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && !par1Entity.isInvisible()) {
            final double var10 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
            final float var11 = (float)((1.0 - var10 / 256.0) * this.shadowOpaque);
            if (var11 > 0.0f) {
                this.renderShadow(par1Entity, par2, par4, par6, var11, par9);
            }
        }
        if (par1Entity.canRenderOnFire()) {
            this.renderEntityOnFire(par1Entity, par2, par4, par6, par9);
        }
    }
    
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
    
    public void updateIcons(final IconRegister par1IconRegister) {
    }
}
