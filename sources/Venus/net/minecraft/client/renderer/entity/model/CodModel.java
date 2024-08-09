/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class CodModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer finTop;
    private final ModelRenderer head;
    private final ModelRenderer headFront;
    private final ModelRenderer finRight;
    private final ModelRenderer finLeft;
    private final ModelRenderer tail;

    public CodModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 22;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-1.0f, -2.0f, 0.0f, 2.0f, 4.0f, 7.0f);
        this.body.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.head = new ModelRenderer(this, 11, 0);
        this.head.addBox(-1.0f, -2.0f, -3.0f, 2.0f, 4.0f, 3.0f);
        this.head.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.headFront = new ModelRenderer(this, 0, 0);
        this.headFront.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 3.0f, 1.0f);
        this.headFront.setRotationPoint(0.0f, 22.0f, -3.0f);
        this.finRight = new ModelRenderer(this, 22, 1);
        this.finRight.addBox(-2.0f, 0.0f, -1.0f, 2.0f, 0.0f, 2.0f);
        this.finRight.setRotationPoint(-1.0f, 23.0f, 0.0f);
        this.finRight.rotateAngleZ = -0.7853982f;
        this.finLeft = new ModelRenderer(this, 22, 4);
        this.finLeft.addBox(0.0f, 0.0f, -1.0f, 2.0f, 0.0f, 2.0f);
        this.finLeft.setRotationPoint(1.0f, 23.0f, 0.0f);
        this.finLeft.rotateAngleZ = 0.7853982f;
        this.tail = new ModelRenderer(this, 22, 3);
        this.tail.addBox(0.0f, -2.0f, 0.0f, 0.0f, 4.0f, 4.0f);
        this.tail.setRotationPoint(0.0f, 22.0f, 7.0f);
        this.finTop = new ModelRenderer(this, 20, -6);
        this.finTop.addBox(0.0f, -1.0f, -1.0f, 0.0f, 1.0f, 6.0f);
        this.finTop.setRotationPoint(0.0f, 20.0f, 0.0f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.head, this.headFront, this.finRight, this.finLeft, this.tail, this.finTop);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        float f6 = 1.0f;
        if (!((Entity)t).isInWater()) {
            f6 = 1.5f;
        }
        this.tail.rotateAngleY = -f6 * 0.45f * MathHelper.sin(0.6f * f3);
    }
}

