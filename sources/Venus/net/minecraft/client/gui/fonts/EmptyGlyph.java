/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class EmptyGlyph
extends TexturedGlyph {
    public EmptyGlyph() {
        super(RenderType.getText(new ResourceLocation("")), RenderType.getTextSeeThrough(new ResourceLocation("")), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(boolean bl, float f, float f2, Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f3, float f4, float f5, float f6, int n) {
    }
}

