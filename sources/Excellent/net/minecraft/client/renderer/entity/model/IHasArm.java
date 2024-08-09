package net.minecraft.client.renderer.entity.model;

import net.minecraft.util.HandSide;
import net.mojang.blaze3d.matrix.MatrixStack;

public interface IHasArm
{
    void translateHand(HandSide sideIn, MatrixStack matrixStackIn);
}
