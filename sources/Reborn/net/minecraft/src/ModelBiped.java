package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelBiped extends ModelBase
{
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedEars;
    public ModelRenderer bipedCloak;
    public int heldItemLeft;
    public int heldItemRight;
    public boolean isSneak;
    public boolean aimedBow;
    
    public ModelBiped() {
        this(0.0f);
    }
    
    public ModelBiped(final float par1) {
        this(par1, 0.0f, 64, 32);
    }
    
    public ModelBiped(final float par1, final float par2, final int par3, final int par4) {
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.aimedBow = false;
        this.textureWidth = par3;
        this.textureHeight = par4;
        (this.bipedCloak = new ModelRenderer(this, 0, 0)).addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, par1);
        (this.bipedEars = new ModelRenderer(this, 24, 0)).addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, par1);
        (this.bipedHead = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1);
        this.bipedHead.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        (this.bipedHeadwear = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1 + 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        (this.bipedBody = new ModelRenderer(this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, par1);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 40, 16)).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, par1);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f + par2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, par1);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + par2, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f + par2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f + par2, 0.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            final float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / var8, 1.5f / var8, 1.5f / var8);
            GL11.glTranslatef(0.0f, 16.0f * par7, 0.0f);
            this.bipedHead.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GL11.glTranslatef(0.0f, 24.0f * par7, 0.0f);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
            GL11.glPopMatrix();
        }
        else {
            this.bipedHead.render(par7);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.bipedHead.rotateAngleY = par4 / 57.295776f;
        this.bipedHead.rotateAngleX = par5 / 57.295776f;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 2.0f * par2 * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 2.0f * par2 * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        if (this.isRiding) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= 0.62831855f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= 0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemLeft;
        }
        if (this.heldItemRight != 0) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedLeftArm.rotateAngleY = 0.0f;
        if (this.onGround > -9990.0f) {
            float var8 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var8) * 3.1415927f * 2.0f) * 0.2f;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            bipedRightArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
            bipedLeftArm2.rotateAngleY += this.bipedBody.rotateAngleY;
            final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
            bipedLeftArm3.rotateAngleX += this.bipedBody.rotateAngleY;
            var8 = 1.0f - this.onGround;
            var8 *= var8;
            var8 *= var8;
            var8 = 1.0f - var8;
            final float var9 = MathHelper.sin(var8 * 3.1415927f);
            final float var10 = MathHelper.sin(this.onGround * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            this.bipedRightArm.rotateAngleX -= (float)(var9 * 1.2 + var10);
            final ModelRenderer bipedRightArm3 = this.bipedRightArm;
            bipedRightArm3.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * 3.1415927f) * -0.4f;
        }
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            final ModelRenderer bipedRightArm4 = this.bipedRightArm;
            bipedRightArm4.rotateAngleX += 0.4f;
            final ModelRenderer bipedLeftArm4 = this.bipedLeftArm;
            bipedLeftArm4.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = 4.0f;
            this.bipedLeftLeg.rotationPointZ = 4.0f;
            this.bipedRightLeg.rotationPointY = 9.0f;
            this.bipedLeftLeg.rotationPointY = 9.0f;
            this.bipedHead.rotationPointY = 1.0f;
            this.bipedHeadwear.rotationPointY = 1.0f;
        }
        else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.rotationPointZ = 0.1f;
            this.bipedLeftLeg.rotationPointZ = 0.1f;
            this.bipedRightLeg.rotationPointY = 12.0f;
            this.bipedLeftLeg.rotationPointY = 12.0f;
            this.bipedHead.rotationPointY = 0.0f;
            this.bipedHeadwear.rotationPointY = 0.0f;
        }
        final ModelRenderer bipedRightArm5 = this.bipedRightArm;
        bipedRightArm5.rotateAngleZ += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm5 = this.bipedLeftArm;
        bipedLeftArm5.rotateAngleZ -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm6 = this.bipedRightArm;
        bipedRightArm6.rotateAngleX += MathHelper.sin(par3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm6 = this.bipedLeftArm;
        bipedLeftArm6.rotateAngleX -= MathHelper.sin(par3 * 0.067f) * 0.05f;
        if (this.aimedBow) {
            final float var8 = 0.0f;
            final float var9 = 0.0f;
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - var8 * 0.6f) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1f - var8 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            final ModelRenderer bipedRightArm7 = this.bipedRightArm;
            bipedRightArm7.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
            final ModelRenderer bipedLeftArm7 = this.bipedLeftArm;
            bipedLeftArm7.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
            final ModelRenderer bipedRightArm8 = this.bipedRightArm;
            bipedRightArm8.rotateAngleZ += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedLeftArm8 = this.bipedLeftArm;
            bipedLeftArm8.rotateAngleZ -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedRightArm9 = this.bipedRightArm;
            bipedRightArm9.rotateAngleX += MathHelper.sin(par3 * 0.067f) * 0.05f;
            final ModelRenderer bipedLeftArm9 = this.bipedLeftArm;
            bipedLeftArm9.rotateAngleX -= MathHelper.sin(par3 * 0.067f) * 0.05f;
        }
    }
    
    public void renderEars(final float par1) {
        this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedEars.rotationPointX = 0.0f;
        this.bipedEars.rotationPointY = 0.0f;
        this.bipedEars.render(par1);
    }
    
    public void renderCloak(final float par1) {
        this.bipedCloak.render(par1);
    }
}
