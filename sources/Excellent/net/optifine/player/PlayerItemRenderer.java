package net.optifine.player;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.vertex.IVertexBuilder;

public class PlayerItemRenderer
{
    private int attachTo = 0;
    private ModelRenderer modelRenderer = null;

    public PlayerItemRenderer(int attachTo, ModelRenderer modelRenderer)
    {
        this.attachTo = attachTo;
        this.modelRenderer = modelRenderer;
    }

    public ModelRenderer getModelRenderer()
    {
        return this.modelRenderer;
    }

    public void render(BipedModel modelBiped, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn)
    {
        ModelRenderer modelrenderer = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);

        if (modelrenderer != null)
        {
            modelrenderer.translateRotate(matrixStackIn);
        }

        this.modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
