package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelWolf extends ModelBase
{
    public ModelRenderer wolfHeadMain;
    public ModelRenderer wolfBody;
    public ModelRenderer wolfLeg1;
    public ModelRenderer wolfLeg2;
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    ModelRenderer wolfTail;
    ModelRenderer wolfMane;
    
    public ModelWolf() {
        final float var1 = 0.0f;
        final float var2 = 13.5f;
        (this.wolfHeadMain = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -3.0f, -2.0f, 6, 6, 4, var1);
        this.wolfHeadMain.setRotationPoint(-1.0f, var2, -7.0f);
        (this.wolfBody = new ModelRenderer(this, 18, 14)).addBox(-4.0f, -2.0f, -3.0f, 6, 9, 6, var1);
        this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
        (this.wolfMane = new ModelRenderer(this, 21, 0)).addBox(-4.0f, -3.0f, -3.0f, 8, 6, 7, var1);
        this.wolfMane.setRotationPoint(-1.0f, 14.0f, 2.0f);
        (this.wolfLeg1 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
        (this.wolfLeg2 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
        (this.wolfLeg3 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
        (this.wolfLeg4 = new ModelRenderer(this, 0, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
        (this.wolfTail = new ModelRenderer(this, 9, 18)).addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, var1);
        this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0f, -5.0f, 0.0f, 2, 2, 1, var1);
        this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5f, 0.0f, -5.0f, 3, 3, 4, var1);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            final float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 5.0f * par7, 2.0f * par7);
            this.wolfHeadMain.renderWithRotation(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GL11.glTranslatef(0.0f, 24.0f * par7, 0.0f);
            this.wolfBody.render(par7);
            this.wolfLeg1.render(par7);
            this.wolfLeg2.render(par7);
            this.wolfLeg3.render(par7);
            this.wolfLeg4.render(par7);
            this.wolfTail.renderWithRotation(par7);
            this.wolfMane.render(par7);
            GL11.glPopMatrix();
        }
        else {
            this.wolfHeadMain.renderWithRotation(par7);
            this.wolfBody.render(par7);
            this.wolfLeg1.render(par7);
            this.wolfLeg2.render(par7);
            this.wolfLeg3.render(par7);
            this.wolfLeg4.render(par7);
            this.wolfTail.renderWithRotation(par7);
            this.wolfMane.render(par7);
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        final EntityWolf var5 = (EntityWolf)par1EntityLiving;
        if (var5.isAngry()) {
            this.wolfTail.rotateAngleY = 0.0f;
        }
        else {
            this.wolfTail.rotateAngleY = MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
        }
        if (var5.isSitting()) {
            this.wolfMane.setRotationPoint(-1.0f, 16.0f, -3.0f);
            this.wolfMane.rotateAngleX = 1.2566371f;
            this.wolfMane.rotateAngleY = 0.0f;
            this.wolfBody.setRotationPoint(0.0f, 18.0f, 0.0f);
            this.wolfBody.rotateAngleX = 0.7853982f;
            this.wolfTail.setRotationPoint(-1.0f, 21.0f, 6.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 22.0f, 2.0f);
            this.wolfLeg1.rotateAngleX = 4.712389f;
            this.wolfLeg2.setRotationPoint(0.5f, 22.0f, 2.0f);
            this.wolfLeg2.rotateAngleX = 4.712389f;
            this.wolfLeg3.rotateAngleX = 5.811947f;
            this.wolfLeg3.setRotationPoint(-2.49f, 17.0f, -4.0f);
            this.wolfLeg4.rotateAngleX = 5.811947f;
            this.wolfLeg4.setRotationPoint(0.51f, 17.0f, -4.0f);
        }
        else {
            this.wolfBody.setRotationPoint(0.0f, 14.0f, 2.0f);
            this.wolfBody.rotateAngleX = 1.5707964f;
            this.wolfMane.setRotationPoint(-1.0f, 14.0f, -3.0f);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setRotationPoint(-1.0f, 12.0f, 8.0f);
            this.wolfLeg1.setRotationPoint(-2.5f, 16.0f, 7.0f);
            this.wolfLeg2.setRotationPoint(0.5f, 16.0f, 7.0f);
            this.wolfLeg3.setRotationPoint(-2.5f, 16.0f, -4.0f);
            this.wolfLeg4.setRotationPoint(0.5f, 16.0f, -4.0f);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662f + 3.1415927f) * 1.4f * par3;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662f + 3.1415927f) * 1.4f * par3;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662f) * 1.4f * par3;
        }
        this.wolfHeadMain.rotateAngleZ = var5.getInterestedAngle(par4) + var5.getShakeAngle(par4, 0.0f);
        this.wolfMane.rotateAngleZ = var5.getShakeAngle(par4, -0.08f);
        this.wolfBody.rotateAngleZ = var5.getShakeAngle(par4, -0.16f);
        this.wolfTail.rotateAngleZ = var5.getShakeAngle(par4, -0.2f);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.wolfHeadMain.rotateAngleX = par5 / 57.295776f;
        this.wolfHeadMain.rotateAngleY = par4 / 57.295776f;
        this.wolfTail.rotateAngleX = par3;
    }
}
