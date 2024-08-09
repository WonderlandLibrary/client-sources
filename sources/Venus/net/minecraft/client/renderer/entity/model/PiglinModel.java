/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.util.math.MathHelper;

public class PiglinModel<T extends MobEntity>
extends PlayerModel<T> {
    public final ModelRenderer field_239115_a_;
    public final ModelRenderer field_239116_b_;
    private final ModelRenderer field_241660_y_;
    private final ModelRenderer field_241661_z_;
    private final ModelRenderer field_241658_A_;
    private final ModelRenderer field_241659_B_;

    public PiglinModel(float f, int n, int n2) {
        super(f, false);
        this.textureWidth = n;
        this.textureHeight = n2;
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f);
        this.bipedHead = new ModelRenderer(this);
        this.bipedHead.setTextureOffset(0, 0).addBox(-5.0f, -8.0f, -4.0f, 10.0f, 8.0f, 8.0f, f);
        this.bipedHead.setTextureOffset(31, 1).addBox(-2.0f, -4.0f, -5.0f, 4.0f, 4.0f, 1.0f, f);
        this.bipedHead.setTextureOffset(2, 4).addBox(2.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, f);
        this.bipedHead.setTextureOffset(2, 0).addBox(-3.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, f);
        this.field_239115_a_ = new ModelRenderer(this);
        this.field_239115_a_.setRotationPoint(4.5f, -6.0f, 0.0f);
        this.field_239115_a_.setTextureOffset(51, 6).addBox(0.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, f);
        this.bipedHead.addChild(this.field_239115_a_);
        this.field_239116_b_ = new ModelRenderer(this);
        this.field_239116_b_.setRotationPoint(-4.5f, -6.0f, 0.0f);
        this.field_239116_b_.setTextureOffset(39, 6).addBox(-1.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, f);
        this.bipedHead.addChild(this.field_239116_b_);
        this.bipedHeadwear = new ModelRenderer(this);
        this.field_241660_y_ = this.bipedBody.getModelAngleCopy();
        this.field_241661_z_ = this.bipedHead.getModelAngleCopy();
        this.field_241658_A_ = this.bipedLeftArm.getModelAngleCopy();
        this.field_241659_B_ = this.bipedLeftArm.getModelAngleCopy();
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.bipedBody.copyModelAngles(this.field_241660_y_);
        this.bipedHead.copyModelAngles(this.field_241661_z_);
        this.bipedLeftArm.copyModelAngles(this.field_241658_A_);
        this.bipedRightArm.copyModelAngles(this.field_241659_B_);
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        float f6 = 0.5235988f;
        float f7 = f3 * 0.1f + f * 0.5f;
        float f8 = 0.08f + f2 * 0.4f;
        this.field_239115_a_.rotateAngleZ = -0.5235988f - MathHelper.cos(f7 * 1.2f) * f8;
        this.field_239116_b_.rotateAngleZ = 0.5235988f + MathHelper.cos(f7) * f8;
        if (t instanceof AbstractPiglinEntity) {
            AbstractPiglinEntity abstractPiglinEntity = (AbstractPiglinEntity)t;
            PiglinAction piglinAction = abstractPiglinEntity.func_234424_eM_();
            if (piglinAction == PiglinAction.DANCING) {
                float f9 = f3 / 60.0f;
                this.field_239116_b_.rotateAngleZ = 0.5235988f + (float)Math.PI / 180 * MathHelper.sin(f9 * 30.0f) * 10.0f;
                this.field_239115_a_.rotateAngleZ = -0.5235988f - (float)Math.PI / 180 * MathHelper.cos(f9 * 30.0f) * 10.0f;
                this.bipedHead.rotationPointX = MathHelper.sin(f9 * 10.0f);
                this.bipedHead.rotationPointY = MathHelper.sin(f9 * 40.0f) + 0.4f;
                this.bipedRightArm.rotateAngleZ = (float)Math.PI / 180 * (70.0f + MathHelper.cos(f9 * 40.0f) * 10.0f);
                this.bipedLeftArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ * -1.0f;
                this.bipedRightArm.rotationPointY = MathHelper.sin(f9 * 40.0f) * 0.5f + 1.5f;
                this.bipedLeftArm.rotationPointY = MathHelper.sin(f9 * 40.0f) * 0.5f + 1.5f;
                this.bipedBody.rotationPointY = MathHelper.sin(f9 * 40.0f) * 0.35f;
            } else if (piglinAction == PiglinAction.ATTACKING_WITH_MELEE_WEAPON && this.swingProgress == 0.0f) {
                this.func_239117_a_(t);
            } else if (piglinAction == PiglinAction.CROSSBOW_HOLD) {
                ModelHelper.func_239104_a_(this.bipedRightArm, this.bipedLeftArm, this.bipedHead, !((MobEntity)t).isLeftHanded());
            } else if (piglinAction == PiglinAction.CROSSBOW_CHARGE) {
                ModelHelper.func_239102_a_(this.bipedRightArm, this.bipedLeftArm, t, !((MobEntity)t).isLeftHanded());
            } else if (piglinAction == PiglinAction.ADMIRING_ITEM) {
                this.bipedHead.rotateAngleX = 0.5f;
                this.bipedHead.rotateAngleY = 0.0f;
                if (((MobEntity)t).isLeftHanded()) {
                    this.bipedRightArm.rotateAngleY = -0.5f;
                    this.bipedRightArm.rotateAngleX = -0.9f;
                } else {
                    this.bipedLeftArm.rotateAngleY = 0.5f;
                    this.bipedLeftArm.rotateAngleX = -0.9f;
                }
            }
        } else if (((Entity)t).getType() == EntityType.ZOMBIFIED_PIGLIN) {
            ModelHelper.func_239105_a_(this.bipedLeftArm, this.bipedRightArm, ((MobEntity)t).isAggressive(), this.swingProgress, f3);
        }
        this.bipedLeftLegwear.copyModelAngles(this.bipedLeftLeg);
        this.bipedRightLegwear.copyModelAngles(this.bipedRightLeg);
        this.bipedLeftArmwear.copyModelAngles(this.bipedLeftArm);
        this.bipedRightArmwear.copyModelAngles(this.bipedRightArm);
        this.bipedBodyWear.copyModelAngles(this.bipedBody);
        this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

    @Override
    protected void func_230486_a_(T t, float f) {
        if (this.swingProgress > 0.0f && t instanceof PiglinEntity && ((PiglinEntity)t).func_234424_eM_() == PiglinAction.ATTACKING_WITH_MELEE_WEAPON) {
            ModelHelper.func_239103_a_(this.bipedRightArm, this.bipedLeftArm, t, this.swingProgress, f);
        } else {
            super.func_230486_a_(t, f);
        }
    }

    private void func_239117_a_(T t) {
        if (((MobEntity)t).isLeftHanded()) {
            this.bipedLeftArm.rotateAngleX = -1.8f;
        } else {
            this.bipedRightArm.rotateAngleX = -1.8f;
        }
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MobEntity)livingEntity), f, f2, f3, f4, f5);
    }

    @Override
    protected void func_230486_a_(LivingEntity livingEntity, float f) {
        this.func_230486_a_((T)((MobEntity)livingEntity), f);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MobEntity)entity2), f, f2, f3, f4, f5);
    }
}

