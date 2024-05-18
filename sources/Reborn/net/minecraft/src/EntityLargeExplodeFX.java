package net.minecraft.src;

import org.lwjgl.opengl.*;

public class EntityLargeExplodeFX extends EntityFX
{
    private int field_70581_a;
    private int field_70584_aq;
    private RenderEngine theRenderEngine;
    private float field_70582_as;
    
    public EntityLargeExplodeFX(final RenderEngine par1RenderEngine, final World par2World, final double par3, final double par5, final double par7, final double par9, final double par11, final double par13) {
        super(par2World, par3, par5, par7, 0.0, 0.0, 0.0);
        this.field_70581_a = 0;
        this.field_70584_aq = 0;
        this.theRenderEngine = par1RenderEngine;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        final float particleRed = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.field_70582_as = 1.0f - (float)par9 * 0.5f;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        final int var8 = (int)((this.field_70581_a + par2) * 15.0f / this.field_70584_aq);
        if (var8 <= 15) {
            this.theRenderEngine.bindTexture("/misc/explosion.png");
            final float var9 = var8 % 4 / 4.0f;
            final float var10 = var9 + 0.24975f;
            final float var11 = var8 / 4 / 4.0f;
            final float var12 = var11 + 0.24975f;
            final float var13 = 2.0f * this.field_70582_as;
            final float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - EntityLargeExplodeFX.interpPosX);
            final float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - EntityLargeExplodeFX.interpPosY);
            final float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EntityLargeExplodeFX.interpPosZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
            RenderHelper.disableStandardItemLighting();
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
            par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
            par1Tessellator.setBrightness(240);
            par1Tessellator.addVertexWithUV(var14 - par3 * var13 - par6 * var13, var15 - par4 * var13, var16 - par5 * var13 - par7 * var13, var10, var12);
            par1Tessellator.addVertexWithUV(var14 - par3 * var13 + par6 * var13, var15 + par4 * var13, var16 - par5 * var13 + par7 * var13, var10, var11);
            par1Tessellator.addVertexWithUV(var14 + par3 * var13 + par6 * var13, var15 + par4 * var13, var16 + par5 * var13 + par7 * var13, var9, var11);
            par1Tessellator.addVertexWithUV(var14 + par3 * var13 - par6 * var13, var15 - par4 * var13, var16 + par5 * var13 - par7 * var13, var9, var12);
            par1Tessellator.draw();
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glEnable(2896);
        }
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
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
}
