package net.minecraftforge.client.extensions;

import net.minecraft.client.renderer.model.BakedQuad;
import net.mojang.blaze3d.matrix.MatrixStack;

public interface IForgeVertexBuilder {
    default void addVertexData(MatrixStack.Entry matrixStack, BakedQuad bakedQuad, float red, float green, float blue, int lightmapCoord, int overlayColor, boolean readExistingColor) {
    }
}
