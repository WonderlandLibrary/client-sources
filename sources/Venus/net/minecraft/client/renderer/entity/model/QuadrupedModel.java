/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class QuadrupedModel<T extends Entity>
extends AgeableModel<T> {
    protected ModelRenderer headModel = new ModelRenderer(this, 0, 0);
    protected ModelRenderer body;
    protected ModelRenderer legBackRight;
    protected ModelRenderer legBackLeft;
    protected ModelRenderer legFrontRight;
    protected ModelRenderer legFrontLeft;

    public QuadrupedModel(int n, float f, boolean bl, float f2, float f3, float f4, float f5, int n2) {
        super(bl, f2, f3, f4, f5, n2);
        this.headModel.addBox(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, f);
        this.headModel.setRotationPoint(0.0f, 18 - n, -6.0f);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-5.0f, -10.0f, -7.0f, 10.0f, 16.0f, 8.0f, f);
        this.body.setRotationPoint(0.0f, 17 - n, 2.0f);
        this.legBackRight = new ModelRenderer(this, 0, 16);
        this.legBackRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, (float)n, 4.0f, f);
        this.legBackRight.setRotationPoint(-3.0f, 24 - n, 7.0f);
        this.legBackLeft = new ModelRenderer(this, 0, 16);
        this.legBackLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, (float)n, 4.0f, f);
        this.legBackLeft.setRotationPoint(3.0f, 24 - n, 7.0f);
        this.legFrontRight = new ModelRenderer(this, 0, 16);
        this.legFrontRight.addBox(-2.0f, 0.0f, -2.0f, 4.0f, (float)n, 4.0f, f);
        this.legFrontRight.setRotationPoint(-3.0f, 24 - n, -5.0f);
        this.legFrontLeft = new ModelRenderer(this, 0, 16);
        this.legFrontLeft.addBox(-2.0f, 0.0f, -2.0f, 4.0f, (float)n, 4.0f, f);
        this.legFrontLeft.setRotationPoint(3.0f, 24 - n, -5.0f);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.headModel);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.headModel.rotateAngleX = f5 * ((float)Math.PI / 180);
        this.headModel.rotateAngleY = f4 * ((float)Math.PI / 180);
        this.body.rotateAngleX = 1.5707964f;
        this.legBackRight.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        this.legBackLeft.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.legFrontRight.rotateAngleX = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.legFrontLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
    }
}

