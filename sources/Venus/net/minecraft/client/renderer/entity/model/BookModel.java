/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BookModel
extends Model {
    private final ModelRenderer coverRight = new ModelRenderer(64, 32, 0, 0).addBox(-6.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f);
    private final ModelRenderer coverLeft = new ModelRenderer(64, 32, 16, 0).addBox(0.0f, -5.0f, -0.005f, 6.0f, 10.0f, 0.005f);
    private final ModelRenderer pagesRight;
    private final ModelRenderer pagesLeft;
    private final ModelRenderer flippingPageRight;
    private final ModelRenderer flippingPageLeft;
    private final ModelRenderer bookSpine = new ModelRenderer(64, 32, 12, 0).addBox(-1.0f, -5.0f, 0.0f, 2.0f, 10.0f, 0.005f);
    private final List<ModelRenderer> bookParts;

    public BookModel() {
        super(RenderType::getEntitySolid);
        this.pagesRight = new ModelRenderer(64, 32, 0, 10).addBox(0.0f, -4.0f, -0.99f, 5.0f, 8.0f, 1.0f);
        this.pagesLeft = new ModelRenderer(64, 32, 12, 10).addBox(0.0f, -4.0f, -0.01f, 5.0f, 8.0f, 1.0f);
        this.flippingPageRight = new ModelRenderer(64, 32, 24, 10).addBox(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f);
        this.flippingPageLeft = new ModelRenderer(64, 32, 24, 10).addBox(0.0f, -4.0f, 0.0f, 5.0f, 8.0f, 0.005f);
        this.bookParts = ImmutableList.of(this.coverRight, this.coverLeft, this.bookSpine, this.pagesRight, this.pagesLeft, this.flippingPageRight, this.flippingPageLeft);
        this.coverRight.setRotationPoint(0.0f, 0.0f, -1.0f);
        this.coverLeft.setRotationPoint(0.0f, 0.0f, 1.0f);
        this.bookSpine.rotateAngleY = 1.5707964f;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.renderAll(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }

    public void renderAll(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.bookParts.forEach(arg_0 -> BookModel.lambda$renderAll$0(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
    }

    public void setBookState(float f, float f2, float f3, float f4) {
        float f5 = (MathHelper.sin(f * 0.02f) * 0.1f + 1.25f) * f4;
        this.coverRight.rotateAngleY = (float)Math.PI + f5;
        this.coverLeft.rotateAngleY = -f5;
        this.pagesRight.rotateAngleY = f5;
        this.pagesLeft.rotateAngleY = -f5;
        this.flippingPageRight.rotateAngleY = f5 - f5 * 2.0f * f2;
        this.flippingPageLeft.rotateAngleY = f5 - f5 * 2.0f * f3;
        this.pagesRight.rotationPointX = MathHelper.sin(f5);
        this.pagesLeft.rotationPointX = MathHelper.sin(f5);
        this.flippingPageRight.rotationPointX = MathHelper.sin(f5);
        this.flippingPageLeft.rotationPointX = MathHelper.sin(f5);
    }

    private static void lambda$renderAll$0(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

