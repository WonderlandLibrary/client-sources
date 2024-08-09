package net.optifine.player;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.vertex.IVertexBuilder;

import java.util.function.Function;

public class ModelPlayerItem extends Model
{
    public ModelPlayerItem(Function<ResourceLocation, RenderType> renderTypeIn)
    {
        super(renderTypeIn);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
    }
}
