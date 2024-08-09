/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PufferFishBigModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer rightFin;
    private final ModelRenderer leftFin;
    private final ModelRenderer frontTopSpines;
    private final ModelRenderer topMidSpines;
    private final ModelRenderer backTopSpines;
    private final ModelRenderer frontRightSpines;
    private final ModelRenderer frontLeftSpines;
    private final ModelRenderer frontBottomSpines;
    private final ModelRenderer bottomBackSpines;
    private final ModelRenderer bottomMidSpines;
    private final ModelRenderer backRightSpines;
    private final ModelRenderer backLeftSpines;

    public PufferFishBigModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 22;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.body.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.rightFin = new ModelRenderer(this, 24, 0);
        this.rightFin.addBox(-2.0f, 0.0f, -1.0f, 2.0f, 1.0f, 2.0f);
        this.rightFin.setRotationPoint(-4.0f, 15.0f, -2.0f);
        this.leftFin = new ModelRenderer(this, 24, 3);
        this.leftFin.addBox(0.0f, 0.0f, -1.0f, 2.0f, 1.0f, 2.0f);
        this.leftFin.setRotationPoint(4.0f, 15.0f, -2.0f);
        this.frontTopSpines = new ModelRenderer(this, 15, 17);
        this.frontTopSpines.addBox(-4.0f, -1.0f, 0.0f, 8.0f, 1.0f, 0.0f);
        this.frontTopSpines.setRotationPoint(0.0f, 14.0f, -4.0f);
        this.frontTopSpines.rotateAngleX = 0.7853982f;
        this.topMidSpines = new ModelRenderer(this, 14, 16);
        this.topMidSpines.addBox(-4.0f, -1.0f, 0.0f, 8.0f, 1.0f, 1.0f);
        this.topMidSpines.setRotationPoint(0.0f, 14.0f, 0.0f);
        this.backTopSpines = new ModelRenderer(this, 23, 18);
        this.backTopSpines.addBox(-4.0f, -1.0f, 0.0f, 8.0f, 1.0f, 0.0f);
        this.backTopSpines.setRotationPoint(0.0f, 14.0f, 4.0f);
        this.backTopSpines.rotateAngleX = -0.7853982f;
        this.frontRightSpines = new ModelRenderer(this, 5, 17);
        this.frontRightSpines.addBox(-1.0f, -8.0f, 0.0f, 1.0f, 8.0f, 0.0f);
        this.frontRightSpines.setRotationPoint(-4.0f, 22.0f, -4.0f);
        this.frontRightSpines.rotateAngleY = -0.7853982f;
        this.frontLeftSpines = new ModelRenderer(this, 1, 17);
        this.frontLeftSpines.addBox(0.0f, -8.0f, 0.0f, 1.0f, 8.0f, 0.0f);
        this.frontLeftSpines.setRotationPoint(4.0f, 22.0f, -4.0f);
        this.frontLeftSpines.rotateAngleY = 0.7853982f;
        this.frontBottomSpines = new ModelRenderer(this, 15, 20);
        this.frontBottomSpines.addBox(-4.0f, 0.0f, 0.0f, 8.0f, 1.0f, 0.0f);
        this.frontBottomSpines.setRotationPoint(0.0f, 22.0f, -4.0f);
        this.frontBottomSpines.rotateAngleX = -0.7853982f;
        this.bottomMidSpines = new ModelRenderer(this, 15, 20);
        this.bottomMidSpines.addBox(-4.0f, 0.0f, 0.0f, 8.0f, 1.0f, 0.0f);
        this.bottomMidSpines.setRotationPoint(0.0f, 22.0f, 0.0f);
        this.bottomBackSpines = new ModelRenderer(this, 15, 20);
        this.bottomBackSpines.addBox(-4.0f, 0.0f, 0.0f, 8.0f, 1.0f, 0.0f);
        this.bottomBackSpines.setRotationPoint(0.0f, 22.0f, 4.0f);
        this.bottomBackSpines.rotateAngleX = 0.7853982f;
        this.backRightSpines = new ModelRenderer(this, 9, 17);
        this.backRightSpines.addBox(-1.0f, -8.0f, 0.0f, 1.0f, 8.0f, 0.0f);
        this.backRightSpines.setRotationPoint(-4.0f, 22.0f, 4.0f);
        this.backRightSpines.rotateAngleY = 0.7853982f;
        this.backLeftSpines = new ModelRenderer(this, 9, 17);
        this.backLeftSpines.addBox(0.0f, -8.0f, 0.0f, 1.0f, 8.0f, 0.0f);
        this.backLeftSpines.setRotationPoint(4.0f, 22.0f, 4.0f);
        this.backLeftSpines.rotateAngleY = -0.7853982f;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.body, this.rightFin, this.leftFin, this.frontTopSpines, this.topMidSpines, this.backTopSpines, this.frontRightSpines, this.frontLeftSpines, this.frontBottomSpines, this.bottomMidSpines, this.bottomBackSpines, this.backRightSpines, this.backLeftSpines);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.rightFin.rotateAngleZ = -0.2f + 0.4f * MathHelper.sin(f3 * 0.2f);
        this.leftFin.rotateAngleZ = 0.2f - 0.4f * MathHelper.sin(f3 * 0.2f);
    }
}

