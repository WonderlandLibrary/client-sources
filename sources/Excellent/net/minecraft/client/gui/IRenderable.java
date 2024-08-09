package net.minecraft.client.gui;

import net.mojang.blaze3d.matrix.MatrixStack;

public interface IRenderable
{
    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
}
