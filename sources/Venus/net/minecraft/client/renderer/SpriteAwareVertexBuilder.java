/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.SmartAnimations;
import net.optifine.render.VertexBuilderWrapper;

public class SpriteAwareVertexBuilder
extends VertexBuilderWrapper
implements IVertexBuilder {
    private final IVertexBuilder vertexBuilder;
    private final TextureAtlasSprite atlasSprite;

    public SpriteAwareVertexBuilder(IVertexBuilder iVertexBuilder, TextureAtlasSprite textureAtlasSprite) {
        super(iVertexBuilder);
        if (SmartAnimations.isActive()) {
            SmartAnimations.spriteRendered(textureAtlasSprite);
        }
        this.vertexBuilder = iVertexBuilder;
        this.atlasSprite = textureAtlasSprite;
    }

    @Override
    public IVertexBuilder pos(double d, double d2, double d3) {
        return this.vertexBuilder.pos(d, d2, d3);
    }

    @Override
    public IVertexBuilder color(int n, int n2, int n3, int n4) {
        return this.vertexBuilder.color(n, n2, n3, n4);
    }

    @Override
    public IVertexBuilder tex(float f, float f2) {
        return this.vertexBuilder.tex(this.atlasSprite.getInterpolatedU(f * 16.0f), this.atlasSprite.getInterpolatedV(f2 * 16.0f));
    }

    @Override
    public IVertexBuilder overlay(int n, int n2) {
        return this.vertexBuilder.overlay(n, n2);
    }

    @Override
    public IVertexBuilder lightmap(int n, int n2) {
        return this.vertexBuilder.lightmap(n, n2);
    }

    @Override
    public IVertexBuilder normal(float f, float f2, float f3) {
        return this.vertexBuilder.normal(f, f2, f3);
    }

    @Override
    public void endVertex() {
        this.vertexBuilder.endVertex();
    }

    @Override
    public void addVertex(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
        this.vertexBuilder.addVertex(f, f2, f3, f4, f5, f6, f7, this.atlasSprite.getInterpolatedU(f8 * 16.0f), this.atlasSprite.getInterpolatedV(f9 * 16.0f), n, n2, f10, f11, f12);
    }
}

