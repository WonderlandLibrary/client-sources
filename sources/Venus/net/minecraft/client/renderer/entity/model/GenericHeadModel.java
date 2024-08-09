/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GenericHeadModel
extends Model {
    protected final ModelRenderer field_217105_a;

    public GenericHeadModel() {
        this(0, 35, 64, 64);
    }

    public GenericHeadModel(int n, int n2, int n3, int n4) {
        super(RenderType::getEntityTranslucent);
        this.textureWidth = n3;
        this.textureHeight = n4;
        this.field_217105_a = new ModelRenderer(this, n, n2);
        this.field_217105_a.addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, 0.0f);
        this.field_217105_a.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    public void func_225603_a_(float f, float f2, float f3) {
        this.field_217105_a.rotateAngleY = f2 * ((float)Math.PI / 180);
        this.field_217105_a.rotateAngleX = f3 * ((float)Math.PI / 180);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.field_217105_a.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

