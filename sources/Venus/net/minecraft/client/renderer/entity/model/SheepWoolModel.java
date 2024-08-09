/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;

public class SheepWoolModel<T extends SheepEntity>
extends QuadrupedModel<T> {
    private float headRotationAngleX;

    public SheepWoolModel() {
        super(12, 0.0f, false, 8.0f, 4.0f, 2.0f, 2.0f, 24);
        this.headModel = new ModelRenderer(this, 0, 0);
        this.headModel.addBox(-3.0f, -4.0f, -4.0f, 6.0f, 6.0f, 6.0f, 0.6f);
        this.headModel.setRotationPoint(0.0f, 6.0f, -8.0f);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f, 1.75f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        float f = 0.5f;
        this.legBackRight = new ModelRenderer(this, 0, 16);
        this.legBackRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, 0.5f);
        this.legBackRight.setRotationPoint(-3.0f, 12.0f, 7.0f);
        this.legBackLeft = new ModelRenderer(this, 0, 16);
        this.legBackLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, 0.5f);
        this.legBackLeft.setRotationPoint(3.0f, 12.0f, 7.0f);
        this.legFrontRight = new ModelRenderer(this, 0, 16);
        this.legFrontRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, 0.5f);
        this.legFrontRight.setRotationPoint(-3.0f, 12.0f, -5.0f);
        this.legFrontLeft = new ModelRenderer(this, 0, 16);
        this.legFrontLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, 0.5f);
        this.legFrontLeft.setRotationPoint(3.0f, 12.0f, -5.0f);
    }

    @Override
    public void setLivingAnimations(T t, float f, float f2, float f3) {
        super.setLivingAnimations(t, f, f2, f3);
        this.headModel.rotationPointY = 6.0f + ((SheepEntity)t).getHeadRotationPointY(f3) * 9.0f;
        this.headRotationAngleX = ((SheepEntity)t).getHeadRotationAngleX(f3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        this.headModel.rotateAngleX = this.headRotationAngleX;
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((SheepEntity)entity2), f, f2, f3, f4, f5);
    }

    @Override
    public void setLivingAnimations(Entity entity2, float f, float f2, float f3) {
        this.setLivingAnimations((T)((SheepEntity)entity2), f, f2, f3);
    }
}

