package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.util.math.MathHelper;

public class ModelEvoker extends ModelIllager
{
    public ModelEvoker(float p_i47228_1_)
    {
        super(p_i47228_1_, 0.0F, 64, 64);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        EntityEvoker entityevoker = (EntityEvoker)entityIn;

        if (entityevoker.func_190749_o())
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
        this.field_191223_g.rotationPointZ = 0.0F;
        this.field_191223_g.rotationPointX = -5.0F;
        this.field_191224_h.rotationPointZ = 0.0F;
        this.field_191224_h.rotationPointX = 5.0F;
        this.field_191223_g.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
        this.field_191224_h.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
        this.field_191223_g.rotateAngleZ = 2.3561945F;
        this.field_191224_h.rotateAngleZ = -2.3561945F;
        this.field_191223_g.rotateAngleY = 0.0F;
        this.field_191224_h.rotateAngleY = 0.0F;
    }
}
