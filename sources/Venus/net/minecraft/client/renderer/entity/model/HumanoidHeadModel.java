/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HumanoidHeadModel
extends GenericHeadModel {
    private final ModelRenderer head = new ModelRenderer(this, 32, 0);

    public HumanoidHeadModel() {
        super(0, 0, 64, 64);
        this.head.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, 0.25f);
        this.head.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void func_225603_a_(float f, float f2, float f3) {
        super.func_225603_a_(f, f2, f3);
        this.head.rotateAngleY = this.field_217105_a.rotateAngleY;
        this.head.rotateAngleX = this.field_217105_a.rotateAngleX;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        super.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
        this.head.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

