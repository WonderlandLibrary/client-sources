package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelVindicator extends ModelIllager
{
    public ModelVindicator(float p_i47222_1_)
    {
        this(p_i47222_1_, 0.0F, 64, 64);
    }

    public ModelVindicator(float p_i47223_1_, float p_i47223_2_, int p_i47223_3_, int p_i47223_4_)
    {
        super(p_i47223_1_, p_i47223_2_, p_i47223_3_, p_i47223_4_);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        EntityVindicator entityvindicator = (EntityVindicator)entityIn;

        if (entityvindicator.func_190639_o())
        {
            this.field_191223_g.render(scale);
            this.field_191224_h.render(scale);
        }
        else
        {
            this.field_191219_c.render(scale);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        this.field_191223_g.rotateAngleZ = 0.0F;
        this.field_191224_h.rotateAngleZ = 0.0F;
        this.field_191223_g.rotateAngleY = 0.15707964F;
        this.field_191224_h.rotateAngleY = -0.15707964F;

        if (((EntityLivingBase)entityIn).getPrimaryHand() == EnumHandSide.RIGHT)
        {
            this.field_191223_g.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
            this.field_191224_h.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
            this.field_191223_g.rotateAngleX += f * 2.2F - f1 * 0.4F;
            this.field_191224_h.rotateAngleX += f * 1.2F - f1 * 0.4F;
        }
        else
        {
            this.field_191223_g.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
            this.field_191224_h.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
            this.field_191223_g.rotateAngleX += f * 1.2F - f1 * 0.4F;
            this.field_191224_h.rotateAngleX += f * 2.2F - f1 * 0.4F;
        }

        this.field_191223_g.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.field_191224_h.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.field_191223_g.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.field_191224_h.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
