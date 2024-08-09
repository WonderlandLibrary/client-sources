/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PhantomModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer leftWingBody;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWingBody;
    private final ModelRenderer rightWing;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;

    public PhantomModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.body = new ModelRenderer(this, 0, 8);
        this.body.addBox(-3.0f, -2.0f, -8.0f, 5.0f, 3.0f, 9.0f);
        this.tail1 = new ModelRenderer(this, 3, 20);
        this.tail1.addBox(-2.0f, 0.0f, 0.0f, 3.0f, 2.0f, 6.0f);
        this.tail1.setRotationPoint(0.0f, -2.0f, 1.0f);
        this.body.addChild(this.tail1);
        this.tail2 = new ModelRenderer(this, 4, 29);
        this.tail2.addBox(-1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 6.0f);
        this.tail2.setRotationPoint(0.0f, 0.5f, 6.0f);
        this.tail1.addChild(this.tail2);
        this.leftWingBody = new ModelRenderer(this, 23, 12);
        this.leftWingBody.addBox(0.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f);
        this.leftWingBody.setRotationPoint(2.0f, -2.0f, -8.0f);
        this.leftWing = new ModelRenderer(this, 16, 24);
        this.leftWing.addBox(0.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f);
        this.leftWing.setRotationPoint(6.0f, 0.0f, 0.0f);
        this.leftWingBody.addChild(this.leftWing);
        this.rightWingBody = new ModelRenderer(this, 23, 12);
        this.rightWingBody.mirror = true;
        this.rightWingBody.addBox(-6.0f, 0.0f, 0.0f, 6.0f, 2.0f, 9.0f);
        this.rightWingBody.setRotationPoint(-3.0f, -2.0f, -8.0f);
        this.rightWing = new ModelRenderer(this, 16, 24);
        this.rightWing.mirror = true;
        this.rightWing.addBox(-13.0f, 0.0f, 0.0f, 13.0f, 1.0f, 9.0f);
        this.rightWing.setRotationPoint(-6.0f, 0.0f, 0.0f);
        this.rightWingBody.addChild(this.rightWing);
        this.leftWingBody.rotateAngleZ = 0.1f;
        this.leftWing.rotateAngleZ = 0.1f;
        this.rightWingBody.rotateAngleZ = -0.1f;
        this.rightWing.rotateAngleZ = -0.1f;
        this.body.rotateAngleX = -0.1f;
        ModelRenderer modelRenderer = new ModelRenderer(this, 0, 0);
        modelRenderer.addBox(-4.0f, -2.0f, -5.0f, 7.0f, 3.0f, 5.0f);
        modelRenderer.setRotationPoint(0.0f, 1.0f, -7.0f);
        modelRenderer.rotateAngleX = 0.2f;
        this.body.addChild(modelRenderer);
        this.body.addChild(this.leftWingBody);
        this.body.addChild(this.rightWingBody);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = ((float)(((Entity)t).getEntityId() * 3) + f3) * 0.13f;
        float f7 = 16.0f;
        this.leftWingBody.rotateAngleZ = MathHelper.cos(f6) * 16.0f * ((float)Math.PI / 180);
        this.leftWing.rotateAngleZ = MathHelper.cos(f6) * 16.0f * ((float)Math.PI / 180);
        this.rightWingBody.rotateAngleZ = -this.leftWingBody.rotateAngleZ;
        this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
        this.tail1.rotateAngleX = -(5.0f + MathHelper.cos(f6 * 2.0f) * 5.0f) * ((float)Math.PI / 180);
        this.tail2.rotateAngleX = -(5.0f + MathHelper.cos(f6 * 2.0f) * 5.0f) * ((float)Math.PI / 180);
    }
}

