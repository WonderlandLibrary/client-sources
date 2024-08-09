/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.MathHelper;

public class IronGolemModel<T extends IronGolemEntity>
extends SegmentedModel<T> {
    private final ModelRenderer ironGolemHead;
    private final ModelRenderer ironGolemBody;
    private final ModelRenderer ironGolemRightArm;
    private final ModelRenderer ironGolemLeftArm;
    private final ModelRenderer ironGolemLeftLeg;
    private final ModelRenderer ironGolemRightLeg;

    public IronGolemModel() {
        int n = 128;
        int n2 = 128;
        this.ironGolemHead = new ModelRenderer(this).setTextureSize(128, 128);
        this.ironGolemHead.setRotationPoint(0.0f, -7.0f, -2.0f);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f, 0.0f);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2.0f, 4.0f, 2.0f, 0.0f);
        this.ironGolemBody = new ModelRenderer(this).setTextureSize(128, 128);
        this.ironGolemBody.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18.0f, 12.0f, 11.0f, 0.0f);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9.0f, 5.0f, 6.0f, 0.5f);
        this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(128, 128);
        this.ironGolemRightArm.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, 0.0f);
        this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(128, 128);
        this.ironGolemLeftArm.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, 0.0f);
        this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128);
        this.ironGolemLeftLeg.setRotationPoint(-4.0f, 11.0f, 0.0f);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, 0.0f);
        this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0f, 11.0f, 0.0f);
        this.ironGolemRightLeg.addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, 0.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.ironGolemHead, this.ironGolemBody, this.ironGolemLeftLeg, this.ironGolemRightLeg, this.ironGolemRightArm, this.ironGolemLeftArm);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.ironGolemHead.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.ironGolemHead.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.ironGolemLeftLeg.rotateAngleX = -1.5f * MathHelper.func_233021_e_(f, 13.0f) * f2;
        this.ironGolemRightLeg.rotateAngleX = 1.5f * MathHelper.func_233021_e_(f, 13.0f) * f2;
        this.ironGolemLeftLeg.rotateAngleY = 0.0f;
        this.ironGolemRightLeg.rotateAngleY = 0.0f;
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        int n = ((IronGolemEntity)t).getAttackTimer();
        if (n > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0f + 1.5f * MathHelper.func_233021_e_((float)n - f3, 10.0f);
            this.ironGolemLeftArm.rotateAngleX = -2.0f + 1.5f * MathHelper.func_233021_e_((float)n - f3, 10.0f);
        } else {
            int n2 = ((IronGolemEntity)t).getHoldRoseTick();
            if (n2 > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8f + 0.025f * MathHelper.func_233021_e_(n2, 70.0f);
                this.ironGolemLeftArm.rotateAngleX = 0.0f;
            } else {
                this.ironGolemRightArm.rotateAngleX = (-0.2f + 1.5f * MathHelper.func_233021_e_(f, 13.0f)) * f2;
                this.ironGolemLeftArm.rotateAngleX = (-0.2f - 1.5f * MathHelper.func_233021_e_(f, 13.0f)) * f2;
            }
        }
    }

    public ModelRenderer getArmHoldingRose() {
        return this.ironGolemRightArm;
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((IronGolemEntity)entity2), f, f2, f3);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((IronGolemEntity)entity2), f, f2, f3, f4, f5);
    }
}

