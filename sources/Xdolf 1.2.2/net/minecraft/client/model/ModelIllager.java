package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelIllager extends ModelBase
{
    public ModelRenderer field_191217_a;
    public ModelRenderer field_191218_b;
    public ModelRenderer field_191219_c;
    public ModelRenderer field_191220_d;
    public ModelRenderer field_191221_e;
    public ModelRenderer field_191222_f;
    public ModelRenderer field_191223_g;
    public ModelRenderer field_191224_h;

    public ModelIllager(float p_i47227_1_, float p_i47227_2_, int p_i47227_3_, int p_i47227_4_)
    {
        this.field_191217_a = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191217_a.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.field_191217_a.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i47227_1_);
        this.field_191222_f = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191222_f.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
        this.field_191222_f.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i47227_1_);
        this.field_191217_a.addChild(this.field_191222_f);
        this.field_191218_b = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191218_b.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.field_191218_b.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i47227_1_);
        this.field_191218_b.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i47227_1_ + 0.5F);
        this.field_191219_c = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191219_c.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
        this.field_191219_c.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
        this.field_191219_c.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
        this.field_191219_c.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i47227_1_);
        this.field_191220_d = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191220_d.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.field_191220_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
        this.field_191221_e = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191221_e.mirror = true;
        this.field_191221_e.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.field_191221_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
        this.field_191223_g = (new ModelRenderer(this, 40, 46)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191223_g.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
        this.field_191223_g.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
        this.field_191224_h = (new ModelRenderer(this, 40, 46)).setTextureSize(p_i47227_3_, p_i47227_4_);
        this.field_191224_h.mirror = true;
        this.field_191224_h.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
        this.field_191224_h.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.field_191217_a.render(scale);
        this.field_191218_b.render(scale);
        this.field_191220_d.render(scale);
        this.field_191221_e.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.field_191217_a.rotateAngleY = netHeadYaw * 0.017453292F;
        this.field_191217_a.rotateAngleX = headPitch * 0.017453292F;
        this.field_191219_c.rotationPointY = 3.0F;
        this.field_191219_c.rotationPointZ = -1.0F;
        this.field_191219_c.rotateAngleX = -0.75F;
        this.field_191220_d.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.field_191221_e.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.field_191220_d.rotateAngleY = 0.0F;
        this.field_191221_e.rotateAngleY = 0.0F;
    }

    public ModelRenderer func_191216_a(EnumHandSide p_191216_1_)
    {
        return p_191216_1_ == EnumHandSide.LEFT ? this.field_191224_h : this.field_191223_g;
    }
}
