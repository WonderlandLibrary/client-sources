/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.entity.Entity;

public abstract class TintedAgeableModel<E extends Entity>
extends AgeableModel<E> {
    private float redTint = 1.0f;
    private float greenTint = 1.0f;
    private float blueTint = 1.0f;

    public void setTint(float f, float f2, float f3) {
        this.redTint = f;
        this.greenTint = f2;
        this.blueTint = f3;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        super.render(matrixStack, iVertexBuilder, n, n2, this.redTint * f, this.greenTint * f2, this.blueTint * f3, f4);
    }
}

