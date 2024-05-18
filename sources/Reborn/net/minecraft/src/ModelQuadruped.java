package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelQuadruped extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    protected float field_78145_g;
    protected float field_78151_h;
    
    public ModelQuadruped(final int par1, final float par2) {
        this.head = new ModelRenderer(this, 0, 0);
        this.field_78145_g = 8.0f;
        this.field_78151_h = 4.0f;
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, par2);
        this.head.setRotationPoint(0.0f, 18 - par1, -6.0f);
        (this.body = new ModelRenderer(this, 28, 8)).addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, par2);
        this.body.setRotationPoint(0.0f, 17 - par1, 2.0f);
        (this.leg1 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg1.setRotationPoint(-3.0f, 24 - par1, 7.0f);
        (this.leg2 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg2.setRotationPoint(3.0f, 24 - par1, 7.0f);
        (this.leg3 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg3.setRotationPoint(-3.0f, 24 - par1, -5.0f);
        (this.leg4 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, par1, 4, par2);
        this.leg4.setRotationPoint(3.0f, 24 - par1, -5.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        if (this.isChild) {
            final float var8 = 2.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, this.field_78145_g * par7, this.field_78151_h * par7);
            this.head.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / var8, 1.0f / var8, 1.0f / var8);
            GL11.glTranslatef(0.0f, 24.0f * par7, 0.0f);
            this.body.render(par7);
            this.leg1.render(par7);
            this.leg2.render(par7);
            this.leg3.render(par7);
            this.leg4.render(par7);
            GL11.glPopMatrix();
        }
        else {
            this.head.render(par7);
            this.body.render(par7);
            this.leg1.render(par7);
            this.leg2.render(par7);
            this.leg3.render(par7);
            this.leg4.render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.head.rotateAngleX = par5 / 57.295776f;
        this.head.rotateAngleY = par4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
    }
}
