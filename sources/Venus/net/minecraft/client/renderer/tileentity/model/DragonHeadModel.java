/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DragonHeadModel
extends GenericHeadModel {
    private final ModelRenderer head;
    private final ModelRenderer jaw;

    public DragonHeadModel(float f) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        float f2 = -16.0f;
        this.head = new ModelRenderer(this);
        this.head.addBox("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, f, 176, 44);
        this.head.addBox("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, f, 112, 30);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, f, 0, 0);
        this.head.addBox("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, f, 112, 0);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, f, 0, 0);
        this.head.addBox("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, f, 112, 0);
        this.jaw = new ModelRenderer(this);
        this.jaw.setRotationPoint(0.0f, 4.0f, -8.0f);
        this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16, f, 176, 65);
        this.head.addChild(this.jaw);
    }

    @Override
    public void func_225603_a_(float f, float f2, float f3) {
        this.jaw.rotateAngleX = (float)(Math.sin(f * (float)Math.PI * 0.2f) + 1.0) * 0.2f;
        this.head.rotateAngleY = f2 * ((float)Math.PI / 180);
        this.head.rotateAngleX = f3 * ((float)Math.PI / 180);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        matrixStack.push();
        matrixStack.translate(0.0, -0.374375f, 0.0);
        matrixStack.scale(0.75f, 0.75f, 0.75f);
        this.head.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
        matrixStack.pop();
    }
}

