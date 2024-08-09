package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.vertex.IVertexBuilder;

public abstract class TintedAgeableModel<E extends Entity> extends AgeableModel<E>
{
    private float redTint = 1.0F;
    private float greenTint = 1.0F;
    private float blueTint = 1.0F;

    public void setTint(float p_228253_1_, float p_228253_2_, float p_228253_3_)
    {
        this.redTint = p_228253_1_;
        this.greenTint = p_228253_2_;
        this.blueTint = p_228253_3_;
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, this.redTint * red, this.greenTint * green, this.blueTint * blue, alpha);
    }
}
