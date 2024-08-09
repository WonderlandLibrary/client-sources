/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client.extensions;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.BakedQuad;

public interface IForgeVertexBuilder {
    default public void addVertexData(MatrixStack.Entry entry, BakedQuad bakedQuad, float f, float f2, float f3, int n, int n2, boolean bl) {
    }
}

