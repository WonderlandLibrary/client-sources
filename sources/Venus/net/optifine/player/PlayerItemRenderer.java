/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.player.PlayerItemModel;

public class PlayerItemRenderer {
    private int attachTo = 0;
    private ModelRenderer modelRenderer = null;

    public PlayerItemRenderer(int n, ModelRenderer modelRenderer) {
        this.attachTo = n;
        this.modelRenderer = modelRenderer;
    }

    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }

    public void render(BipedModel bipedModel, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2) {
        ModelRenderer modelRenderer = PlayerItemModel.getAttachModel(bipedModel, this.attachTo);
        if (modelRenderer != null) {
            modelRenderer.translateRotate(matrixStack);
        }
        this.modelRenderer.render(matrixStack, iVertexBuilder, n, n2);
    }
}

