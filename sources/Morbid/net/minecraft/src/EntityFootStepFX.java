package net.minecraft.src;

import org.lwjgl.opengl.*;

public class EntityFootStepFX extends EntityFX
{
    private int field_70576_a;
    private int field_70578_aq;
    private RenderEngine currentFootSteps;
    
    public EntityFootStepFX(final RenderEngine par1RenderEngine, final World par2World, final double par3, final double par5, final double par7) {
        super(par2World, par3, par5, par7, 0.0, 0.0, 0.0);
        this.field_70576_a = 0;
        this.field_70578_aq = 0;
        this.currentFootSteps = par1RenderEngine;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.field_70578_aq = 200;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        float var8 = (this.field_70576_a + par2) / this.field_70578_aq;
        var8 *= var8;
        float var9 = 2.0f - var8 * 2.0f;
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        var9 *= 0.2f;
        GL11.glDisable(2896);
        final float var10 = 0.125f;
        final float var11 = (float)(this.posX - EntityFootStepFX.interpPosX);
        final float var12 = (float)(this.posY - EntityFootStepFX.interpPosY);
        final float var13 = (float)(this.posZ - EntityFootStepFX.interpPosZ);
        final float var14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        this.currentFootSteps.bindTexture("/misc/footprint.png");
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorRGBA_F(var14, var14, var14, var9);
        par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 + var10, 0.0, 1.0);
        par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 + var10, 1.0, 1.0);
        par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 - var10, 1.0, 0.0);
        par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 - var10, 0.0, 0.0);
        par1Tessellator.draw();
        GL11.glDisable(3042);
        GL11.glEnable(2896);
    }
    
    @Override
    public void onUpdate() {
        ++this.field_70576_a;
        if (this.field_70576_a == this.field_70578_aq) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
}
