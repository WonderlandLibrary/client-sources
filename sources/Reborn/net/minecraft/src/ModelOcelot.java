package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelOcelot extends ModelBase
{
    ModelRenderer ocelotBackLeftLeg;
    ModelRenderer ocelotBackRightLeg;
    ModelRenderer ocelotFrontLeftLeg;
    ModelRenderer ocelotFrontRightLeg;
    ModelRenderer ocelotTail;
    ModelRenderer ocelotTail2;
    ModelRenderer ocelotHead;
    ModelRenderer ocelotBody;
    int field_78163_i;
    
    public ModelOcelot() {
        this.field_78163_i = 1;
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        (this.ocelotHead = new ModelRenderer(this, "head")).addBox("main", -2.5f, -2.0f, -3.0f, 5, 4, 5);
        this.ocelotHead.addBox("nose", -1.5f, 0.0f, -4.0f, 3, 2, 2);
        this.ocelotHead.addBox("ear1", -2.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.addBox("ear2", 1.0f, -3.0f, 0.0f, 1, 1, 2);
        this.ocelotHead.setRotationPoint(0.0f, 15.0f, -9.0f);
        (this.ocelotBody = new ModelRenderer(this, 20, 0)).addBox(-2.0f, 3.0f, -8.0f, 4, 16, 6, 0.0f);
        this.ocelotBody.setRotationPoint(0.0f, 12.0f, -10.0f);
        (this.ocelotTail = new ModelRenderer(this, 0, 15)).addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail.rotateAngleX = 0.9f;
        this.ocelotTail.setRotationPoint(0.0f, 15.0f, 8.0f);
        (this.ocelotTail2 = new ModelRenderer(this, 4, 15)).addBox(-0.5f, 0.0f, 0.0f, 1, 8, 1);
        this.ocelotTail2.setRotationPoint(0.0f, 20.0f, 14.0f);
        (this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13)).addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackLeftLeg.setRotationPoint(1.1f, 18.0f, 5.0f);
        (this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13)).addBox(-1.0f, 0.0f, 1.0f, 2, 6, 2);
        this.ocelotBackRightLeg.setRotationPoint(-1.1f, 18.0f, 5.0f);
        (this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0)).addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontLeftLeg.setRotationPoint(1.2f, 13.8f, -5.0f);
        (this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0)).addBox(-1.0f, 0.0f, 0.0f, 2, 10, 2);
        this.ocelotFrontRightLeg.setRotationPoint(-1.2f, 13.8f, -5.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            final float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / var8, 1.5f / var8, 1.5f / var8);
            GL11.glTranslatef(0.0f, 10.0f * par7, 4.0f * par7);
            this.ocelotHead.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GL11.glTranslatef(0.0f, 24.0f * par7, 0.0f);
            this.ocelotBody.render(par7);
            this.ocelotBackLeftLeg.render(par7);
            this.ocelotBackRightLeg.render(par7);
            this.ocelotFrontLeftLeg.render(par7);
            this.ocelotFrontRightLeg.render(par7);
            this.ocelotTail.render(par7);
            this.ocelotTail2.render(par7);
            GL11.glPopMatrix();
        }
        else {
            this.ocelotHead.render(par7);
            this.ocelotBody.render(par7);
            this.ocelotTail.render(par7);
            this.ocelotTail2.render(par7);
            this.ocelotBackLeftLeg.render(par7);
            this.ocelotBackRightLeg.render(par7);
            this.ocelotFrontLeftLeg.render(par7);
            this.ocelotFrontRightLeg.render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.ocelotHead.rotateAngleX = par5 / 57.295776f;
        this.ocelotHead.rotateAngleY = par4 / 57.295776f;
        if (this.field_78163_i != 3) {
            this.ocelotBody.rotateAngleX = 1.5707964f;
            if (this.field_78163_i == 2) {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 0.3f) * 1.0f * par2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f + 0.3f) * 1.0f * par2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotTail2.rotateAngleX = 1.7278761f + 0.31415927f * MathHelper.cos(par1) * par2;
            }
            else {
                this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.0f * par2;
                this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.0f * par2;
                if (this.field_78163_i == 1) {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.7853982f * MathHelper.cos(par1) * par2;
                }
                else {
                    this.ocelotTail2.rotateAngleX = 1.7278761f + 0.47123894f * MathHelper.cos(par1) * par2;
                }
            }
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        final EntityOcelot var5 = (EntityOcelot)par1EntityLiving;
        this.ocelotBody.rotationPointY = 12.0f;
        this.ocelotBody.rotationPointZ = -10.0f;
        this.ocelotHead.rotationPointY = 15.0f;
        this.ocelotHead.rotationPointZ = -9.0f;
        this.ocelotTail.rotationPointY = 15.0f;
        this.ocelotTail.rotationPointZ = 8.0f;
        this.ocelotTail2.rotationPointY = 20.0f;
        this.ocelotTail2.rotationPointZ = 14.0f;
        final ModelRenderer ocelotFrontLeftLeg = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg = this.ocelotFrontRightLeg;
        final float n = 13.8f;
        ocelotFrontRightLeg.rotationPointY = n;
        ocelotFrontLeftLeg.rotationPointY = n;
        final ModelRenderer ocelotFrontLeftLeg2 = this.ocelotFrontLeftLeg;
        final ModelRenderer ocelotFrontRightLeg2 = this.ocelotFrontRightLeg;
        final float n2 = -5.0f;
        ocelotFrontRightLeg2.rotationPointZ = n2;
        ocelotFrontLeftLeg2.rotationPointZ = n2;
        final ModelRenderer ocelotBackLeftLeg = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg = this.ocelotBackRightLeg;
        final float n3 = 18.0f;
        ocelotBackRightLeg.rotationPointY = n3;
        ocelotBackLeftLeg.rotationPointY = n3;
        final ModelRenderer ocelotBackLeftLeg2 = this.ocelotBackLeftLeg;
        final ModelRenderer ocelotBackRightLeg2 = this.ocelotBackRightLeg;
        final float n4 = 5.0f;
        ocelotBackRightLeg2.rotationPointZ = n4;
        ocelotBackLeftLeg2.rotationPointZ = n4;
        this.ocelotTail.rotateAngleX = 0.9f;
        if (var5.isSneaking()) {
            final ModelRenderer ocelotBody = this.ocelotBody;
            ++ocelotBody.rotationPointY;
            final ModelRenderer ocelotHead = this.ocelotHead;
            ocelotHead.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail = this.ocelotTail;
            ++ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail2 = this.ocelotTail2;
            ocelotTail2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotTail3 = this.ocelotTail2;
            ocelotTail3.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 0;
        }
        else if (var5.isSprinting()) {
            this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
            final ModelRenderer ocelotTail4 = this.ocelotTail2;
            ocelotTail4.rotationPointZ += 2.0f;
            this.ocelotTail.rotateAngleX = 1.5707964f;
            this.ocelotTail2.rotateAngleX = 1.5707964f;
            this.field_78163_i = 2;
        }
        else if (var5.isSitting()) {
            this.ocelotBody.rotateAngleX = 0.7853982f;
            final ModelRenderer ocelotBody2 = this.ocelotBody;
            ocelotBody2.rotationPointY -= 4.0f;
            final ModelRenderer ocelotBody3 = this.ocelotBody;
            ocelotBody3.rotationPointZ += 5.0f;
            final ModelRenderer ocelotHead2 = this.ocelotHead;
            ocelotHead2.rotationPointY -= 3.3f;
            final ModelRenderer ocelotHead3 = this.ocelotHead;
            ++ocelotHead3.rotationPointZ;
            final ModelRenderer ocelotTail5 = this.ocelotTail;
            ocelotTail5.rotationPointY += 8.0f;
            final ModelRenderer ocelotTail6 = this.ocelotTail;
            ocelotTail6.rotationPointZ -= 2.0f;
            final ModelRenderer ocelotTail7 = this.ocelotTail2;
            ocelotTail7.rotationPointY += 2.0f;
            final ModelRenderer ocelotTail8 = this.ocelotTail2;
            ocelotTail8.rotationPointZ -= 0.8f;
            this.ocelotTail.rotateAngleX = 1.7278761f;
            this.ocelotTail2.rotateAngleX = 2.670354f;
            final ModelRenderer ocelotFrontLeftLeg3 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg3 = this.ocelotFrontRightLeg;
            final float n5 = -0.15707964f;
            ocelotFrontRightLeg3.rotateAngleX = n5;
            ocelotFrontLeftLeg3.rotateAngleX = n5;
            final ModelRenderer ocelotFrontLeftLeg4 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg4 = this.ocelotFrontRightLeg;
            final float n6 = 15.8f;
            ocelotFrontRightLeg4.rotationPointY = n6;
            ocelotFrontLeftLeg4.rotationPointY = n6;
            final ModelRenderer ocelotFrontLeftLeg5 = this.ocelotFrontLeftLeg;
            final ModelRenderer ocelotFrontRightLeg5 = this.ocelotFrontRightLeg;
            final float n7 = -7.0f;
            ocelotFrontRightLeg5.rotationPointZ = n7;
            ocelotFrontLeftLeg5.rotationPointZ = n7;
            final ModelRenderer ocelotBackLeftLeg3 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg3 = this.ocelotBackRightLeg;
            final float n8 = -1.5707964f;
            ocelotBackRightLeg3.rotateAngleX = n8;
            ocelotBackLeftLeg3.rotateAngleX = n8;
            final ModelRenderer ocelotBackLeftLeg4 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg4 = this.ocelotBackRightLeg;
            final float n9 = 21.0f;
            ocelotBackRightLeg4.rotationPointY = n9;
            ocelotBackLeftLeg4.rotationPointY = n9;
            final ModelRenderer ocelotBackLeftLeg5 = this.ocelotBackLeftLeg;
            final ModelRenderer ocelotBackRightLeg5 = this.ocelotBackRightLeg;
            final float n10 = 1.0f;
            ocelotBackRightLeg5.rotationPointZ = n10;
            ocelotBackLeftLeg5.rotationPointZ = n10;
            this.field_78163_i = 3;
        }
        else {
            this.field_78163_i = 1;
        }
    }
}
