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

public abstract class SegmentedModel<E extends Entity>
extends EntityModel<E> {
    public SegmentedModel() {
        this(RenderType::getEntityCutoutNoCull);
    }

    public SegmentedModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.getParts().forEach(arg_0 -> SegmentedModel.lambda$render$0(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4, arg_0));
    }

    public abstract Iterable<ModelRenderer> getParts();

    private static void lambda$render$0(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4, ModelRenderer modelRenderer) {
        modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

