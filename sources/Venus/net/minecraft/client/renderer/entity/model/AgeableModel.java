/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class AgeableModel<E extends Entity>
extends EntityModel<E> {
    private final boolean isChildHeadScaled;
    private final float childHeadOffsetY;
    private final float childHeadOffsetZ;
    private final float childHeadScale;
    private final float childBodyScale;
    private final float childBodyOffsetY;

    protected AgeableModel(boolean bl, float f, float f2) {
        this(bl, f, f2, 2.0f, 2.0f, 24.0f);
    }

    protected AgeableModel(boolean bl, float f, float f2, float f3, float f4, float f5) {
        this(RenderType::getEntityCutoutNoCull, bl, f, f2, f3, f4, f5);
    }

    protected AgeableModel(Function<ResourceLocation, RenderType> function, boolean bl, float f, float f2, float f3, float f4, float f5) {
        super(function);
        this.isChildHeadScaled = bl;
        this.childHeadOffsetY = f;
        this.childHeadOffsetZ = f2;
        this.childHeadScale = f3;
        this.childBodyScale = f4;
        this.childBodyOffsetY = f5;
    }

    protected AgeableModel() {
        this(false, 5.0f, 2.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        if (this.isChild) {
            float f5;
            matrixStack.push();
            if (this.isChildHeadScaled) {
                f5 = 1.5f / this.childHeadScale;
                matrixStack.scale(f5, f5, f5);
            }
            matrixStack.translate(0.0, this.childHeadOffsetY / 16.0f, this.childHeadOffsetZ / 16.0f);
            this.getHeadParts().forEach(arg_0 -> AgeableModel.lambda$render$0(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
            matrixStack.pop();
            matrixStack.push();
            f5 = 1.0f / this.childBodyScale;
            matrixStack.scale(f5, f5, f5);
            matrixStack.translate(0.0, this.childBodyOffsetY / 16.0f, 0.0);
            this.getBodyParts().forEach(arg_0 -> AgeableModel.lambda$render$1(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
            matrixStack.pop();
        } else {
            this.getHeadParts().forEach(arg_0 -> AgeableModel.lambda$render$2(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
            this.getBodyParts().forEach(arg_0 -> AgeableModel.lambda$render$3(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
        }
    }

    protected abstract Iterable<ModelRenderer> getHeadParts();

    protected abstract Iterable<ModelRenderer> getBodyParts();

    private static void lambda$render$3(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }

    private static void lambda$render$2(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }

    private static void lambda$render$1(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }

    private static void lambda$render$0(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

