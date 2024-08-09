/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.entity.Entity;

public abstract class AbstractTropicalFishModel<E extends Entity>
extends SegmentedModel<E> {
    private float field_228254_a_ = 1.0f;
    private float field_228255_b_ = 1.0f;
    private float field_228256_f_ = 1.0f;

    public void func_228257_a_(float f, float f2, float f3) {
        this.field_228254_a_ = f;
        this.field_228255_b_ = f2;
        this.field_228256_f_ = f3;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        super.render(matrixStack, iVertexBuilder, n, n2, this.field_228254_a_ * f, this.field_228255_b_ * f2, this.field_228256_f_ * f3, f4);
    }
}

