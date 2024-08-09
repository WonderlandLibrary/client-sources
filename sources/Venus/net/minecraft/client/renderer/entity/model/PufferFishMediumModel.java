/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PufferFishMediumModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer rightFin;
    private final ModelRenderer leftFin;
    private final ModelRenderer frontTopSpines;
    private final ModelRenderer backTopSpines;
    private final ModelRenderer frontRightSpines;
    private final ModelRenderer backRightSpines;
    private final ModelRenderer backLeftSpines;
    private final ModelRenderer frontLeftSpines;
    private final ModelRenderer backBottomSpine;
    private final ModelRenderer frontBottomSpines;

    public PufferFishMediumModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 22;
        this.body = new ModelRenderer(this, 12, 22);
        this.body.addBox(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f);
        this.body.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.rightFin = new ModelRenderer(this, 24, 0);
        this.rightFin.addBox(-2.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f);
        this.rightFin.setRotationPoint(-2.5f, 17.0f, -1.5f);
        this.leftFin = new ModelRenderer(this, 24, 3);
        this.leftFin.addBox(0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f);
        this.leftFin.setRotationPoint(2.5f, 17.0f, -1.5f);
        this.frontTopSpines = new ModelRenderer(this, 15, 16);
        this.frontTopSpines.addBox(-2.5f, -1.0f, 0.0f, 5.0f, 1.0f, 1.0f);
        this.frontTopSpines.setRotationPoint(0.0f, 17.0f, -2.5f);
        this.frontTopSpines.rotateAngleX = 0.7853982f;
        this.backTopSpines = new ModelRenderer(this, 10, 16);
        this.backTopSpines.addBox(-2.5f, -1.0f, -1.0f, 5.0f, 1.0f, 1.0f);
        this.backTopSpines.setRotationPoint(0.0f, 17.0f, 2.5f);
        this.backTopSpines.rotateAngleX = -0.7853982f;
        this.frontRightSpines = new ModelRenderer(this, 8, 16);
        this.frontRightSpines.addBox(-1.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f);
        this.frontRightSpines.setRotationPoint(-2.5f, 22.0f, -2.5f);
        this.frontRightSpines.rotateAngleY = -0.7853982f;
        this.backRightSpines = new ModelRenderer(this, 8, 16);
        this.backRightSpines.addBox(-1.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f);
        this.backRightSpines.setRotationPoint(-2.5f, 22.0f, 2.5f);
        this.backRightSpines.rotateAngleY = 0.7853982f;
        this.backLeftSpines = new ModelRenderer(this, 4, 16);
        this.backLeftSpines.addBox(0.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f);
        this.backLeftSpines.setRotationPoint(2.5f, 22.0f, 2.5f);
        this.backLeftSpines.rotateAngleY = -0.7853982f;
        this.frontLeftSpines = new ModelRenderer(this, 0, 16);
        this.frontLeftSpines.addBox(0.0f, -5.0f, 0.0f, 1.0f, 5.0f, 1.0f);
        this.frontLeftSpines.setRotationPoint(2.5f, 22.0f, -2.5f);
        this.frontLeftSpines.rotateAngleY = 0.7853982f;
        this.backBottomSpine = new ModelRenderer(this, 8, 22);
        this.backBottomSpine.addBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.backBottomSpine.setRotationPoint(0.5f, 22.0f, 2.5f);
        this.backBottomSpine.rotateAngleX = 0.7853982f;
        this.frontBottomSpines = new ModelRenderer(this, 17, 21);
        this.frontBottomSpines.addBox(-2.5f, 0.0f, 0.0f, 5.0f, 1.0f, 1.0f);
        this.frontBottomSpines.setRotationPoint(0.0f, 22.0f, -2.5f);
        this.frontBottomSpines.rotateAngleX = -0.7853982f;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.rightFin, this.leftFin, this.frontTopSpines, this.backTopSpines, this.frontRightSpines, this.backRightSpines, this.backLeftSpines, this.frontLeftSpines, this.backBottomSpine, this.frontBottomSpines);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.rightFin.rotateAngleZ = -0.2f + 0.4f * MathHelper.sin(f3 * 0.2f);
        this.leftFin.rotateAngleZ = 0.2f - 0.4f * MathHelper.sin(f3 * 0.2f);
    }
}

