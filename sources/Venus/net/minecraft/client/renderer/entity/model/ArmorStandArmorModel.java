/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

public class ArmorStandArmorModel
extends BipedModel<ArmorStandEntity> {
    public ArmorStandArmorModel(float f) {
        this(f, 64, 32);
    }

    protected ArmorStandArmorModel(float f, int n, int n2) {
        super(f, 0.0f, n, n2);
    }

    @Override
    public void setRotationAngles(ArmorStandEntity armorStandEntity, float f, float f2, float f3, float f4, float f5) {
        this.bipedHead.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getX();
        this.bipedHead.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getY();
        this.bipedHead.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getHeadRotation().getZ();
        this.bipedHead.setRotationPoint(0.0f, 1.0f, 0.0f);
        this.bipedBody.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getX();
        this.bipedBody.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getY();
        this.bipedBody.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getBodyRotation().getZ();
        this.bipedLeftArm.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getX();
        this.bipedLeftArm.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getY();
        this.bipedLeftArm.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getLeftArmRotation().getZ();
        this.bipedRightArm.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getX();
        this.bipedRightArm.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getY();
        this.bipedRightArm.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getRightArmRotation().getZ();
        this.bipedLeftLeg.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getX();
        this.bipedLeftLeg.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getY();
        this.bipedLeftLeg.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getLeftLegRotation().getZ();
        this.bipedLeftLeg.setRotationPoint(1.9f, 11.0f, 0.0f);
        this.bipedRightLeg.rotateAngleX = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getX();
        this.bipedRightLeg.rotateAngleY = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getY();
        this.bipedRightLeg.rotateAngleZ = (float)Math.PI / 180 * armorStandEntity.getRightLegRotation().getZ();
        this.bipedRightLeg.setRotationPoint(-1.9f, 11.0f, 0.0f);
        this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((ArmorStandEntity)livingEntity, f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((ArmorStandEntity)entity2, f, f2, f3, f4, f5);
    }
}

