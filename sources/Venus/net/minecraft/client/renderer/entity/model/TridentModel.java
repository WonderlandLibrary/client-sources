/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class TridentModel
extends Model {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/trident.png");
    private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 6);

    public TridentModel() {
        super(RenderType::getEntitySolid);
        this.modelRenderer.addBox(-0.5f, 2.0f, -0.5f, 1.0f, 25.0f, 1.0f, 0.0f);
        ModelRenderer modelRenderer = new ModelRenderer(32, 32, 4, 0);
        modelRenderer.addBox(-1.5f, 0.0f, -0.5f, 3.0f, 2.0f, 1.0f);
        this.modelRenderer.addChild(modelRenderer);
        ModelRenderer modelRenderer2 = new ModelRenderer(32, 32, 4, 3);
        modelRenderer2.addBox(-2.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f);
        this.modelRenderer.addChild(modelRenderer2);
        ModelRenderer modelRenderer3 = new ModelRenderer(32, 32, 0, 0);
        modelRenderer3.addBox(-0.5f, -4.0f, -0.5f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.modelRenderer.addChild(modelRenderer3);
        ModelRenderer modelRenderer4 = new ModelRenderer(32, 32, 4, 3);
        modelRenderer4.mirror = true;
        modelRenderer4.addBox(1.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f);
        this.modelRenderer.addChild(modelRenderer4);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        this.modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
    }
}

