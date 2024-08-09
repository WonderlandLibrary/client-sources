/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class VexModel
extends BipedModel<VexEntity> {
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;

    public VexModel() {
        super(0.0f, 0.0f, 64, 64);
        this.bipedLeftLeg.showModel = false;
        this.bipedHeadwear.showModel = false;
        this.bipedRightLeg = new ModelRenderer(this, 32, 0);
        this.bipedRightLeg.addBox(-1.0f, -1.0f, -2.0f, 6.0f, 10.0f, 4.0f, 0.0f);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.rightWing = new ModelRenderer(this, 0, 32);
        this.rightWing.addBox(-20.0f, 0.0f, 0.0f, 20.0f, 12.0f, 1.0f);
        this.leftWing = new ModelRenderer(this, 0, 32);
        this.leftWing.mirror = true;
        this.leftWing.addBox(0.0f, 0.0f, 0.0f, 20.0f, 12.0f, 1.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.rightWing, this.leftWing));
    }

    @Override
    public void setRotationAngles(VexEntity vexEntity, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(vexEntity, f, f2, f3, f4, f5);
        if (vexEntity.isCharging()) {
            if (vexEntity.getHeldItemMainhand().isEmpty()) {
                this.bipedRightArm.rotateAngleX = 4.712389f;
                this.bipedLeftArm.rotateAngleX = 4.712389f;
            } else if (vexEntity.getPrimaryHand() == HandSide.RIGHT) {
                this.bipedRightArm.rotateAngleX = 3.7699115f;
            } else {
                this.bipedLeftArm.rotateAngleX = 3.7699115f;
            }
        }
        this.bipedRightLeg.rotateAngleX += 0.62831855f;
        this.rightWing.rotationPointZ = 2.0f;
        this.leftWing.rotationPointZ = 2.0f;
        this.rightWing.rotationPointY = 1.0f;
        this.leftWing.rotationPointY = 1.0f;
        this.rightWing.rotateAngleY = 0.47123894f + MathHelper.cos(f3 * 0.8f) * (float)Math.PI * 0.05f;
        this.leftWing.rotateAngleY = -this.rightWing.rotateAngleY;
        this.leftWing.rotateAngleZ = -0.47123894f;
        this.leftWing.rotateAngleX = 0.47123894f;
        this.rightWing.rotateAngleX = 0.47123894f;
        this.rightWing.rotateAngleZ = 0.47123894f;
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((VexEntity)livingEntity, f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((VexEntity)entity2, f, f2, f3, f4, f5);
    }
}

