/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;

public class ZombieVillagerModel<T extends ZombieEntity>
extends BipedModel<T>
implements IHeadToggle {
    private ModelRenderer field_217150_a;

    public ZombieVillagerModel(float f, boolean bl) {
        super(f, 0.0f, 64, bl ? 32 : 64);
        if (bl) {
            this.bipedHead = new ModelRenderer(this, 0, 0);
            this.bipedHead.addBox(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, f);
            this.bipedBody = new ModelRenderer(this, 16, 16);
            this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f + 0.1f);
            this.bipedRightLeg = new ModelRenderer(this, 0, 16);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.1f);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f + 0.1f);
        } else {
            this.bipedHead = new ModelRenderer(this, 0, 0);
            this.bipedHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f);
            this.bipedHead.setTextureOffset(24, 0).addBox(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f, f);
            this.bipedHeadwear = new ModelRenderer(this, 32, 0);
            this.bipedHeadwear.addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, f + 0.5f);
            this.field_217150_a = new ModelRenderer(this);
            this.field_217150_a.setTextureOffset(30, 47).addBox(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f, f);
            this.field_217150_a.rotateAngleX = -1.5707964f;
            this.bipedHeadwear.addChild(this.field_217150_a);
            this.bipedBody = new ModelRenderer(this, 16, 20);
            this.bipedBody.addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, f);
            this.bipedBody.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, f + 0.05f);
            this.bipedRightArm = new ModelRenderer(this, 44, 22);
            this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            this.bipedLeftArm = new ModelRenderer(this, 44, 22);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            this.bipedRightLeg = new ModelRenderer(this, 0, 22);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
            this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        }
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        ModelHelper.func_239105_a_(this.bipedLeftArm, this.bipedRightArm, ((MobEntity)t).isAggressive(), this.swingProgress, f3);
    }

    @Override
    public void func_217146_a(boolean bl) {
        this.bipedHead.showModel = bl;
        this.bipedHeadwear.showModel = bl;
        this.field_217150_a.showModel = bl;
    }

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ZombieEntity)livingEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((ZombieEntity)entity2), f, f2, f3, f4, f5);
    }
}

