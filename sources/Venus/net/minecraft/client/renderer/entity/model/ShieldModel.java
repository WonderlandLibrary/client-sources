/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ShieldModel
extends Model {
    private final ModelRenderer plate;
    private final ModelRenderer handle;

    public ShieldModel() {
        super(RenderType::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.plate = new ModelRenderer(this, 0, 0);
        this.plate.addBox(-6.0f, -11.0f, -2.0f, 12.0f, 22.0f, 1.0f, 0.0f);
        this.handle = new ModelRenderer(this, 26, 0);
        this.handle.addBox(-1.0f, -3.0f, -1.0f, 2.0f, 6.0f, 6.0f, 0.0f);
    }

    public ModelRenderer func_228293_a_() {
        return this.plate;
    }

    public ModelRenderer func_228294_b_() {
        return this.handle;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.plate.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
        this.handle.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

