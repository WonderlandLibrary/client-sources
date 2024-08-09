/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.render.VertexPosition;

public abstract class VertexBuilderWrapper
implements IVertexBuilder {
    private IVertexBuilder vertexBuilder;

    public VertexBuilderWrapper(IVertexBuilder iVertexBuilder) {
        this.vertexBuilder = iVertexBuilder;
    }

    public IVertexBuilder getVertexBuilder() {
        return this.vertexBuilder;
    }

    @Override
    public void putSprite(TextureAtlasSprite textureAtlasSprite) {
        this.vertexBuilder.putSprite(textureAtlasSprite);
    }

    @Override
    public void setSprite(TextureAtlasSprite textureAtlasSprite) {
        this.vertexBuilder.setSprite(textureAtlasSprite);
    }

    @Override
    public boolean isMultiTexture() {
        return this.vertexBuilder.isMultiTexture();
    }

    @Override
    public void setRenderType(RenderType renderType) {
        this.vertexBuilder.setRenderType(renderType);
    }

    @Override
    public RenderType getRenderType() {
        return this.vertexBuilder.getRenderType();
    }

    @Override
    public void setRenderBlocks(boolean bl) {
        this.vertexBuilder.setRenderBlocks(bl);
    }

    @Override
    public Vector3f getTempVec3f(Vector3f vector3f) {
        return this.vertexBuilder.getTempVec3f(vector3f);
    }

    @Override
    public Vector3f getTempVec3f(float f, float f2, float f3) {
        return this.vertexBuilder.getTempVec3f(f, f2, f3);
    }

    @Override
    public float[] getTempFloat4(float f, float f2, float f3, float f4) {
        return this.vertexBuilder.getTempFloat4(f, f2, f3, f4);
    }

    @Override
    public int[] getTempInt4(int n, int n2, int n3, int n4) {
        return this.vertexBuilder.getTempInt4(n, n2, n3, n4);
    }

    @Override
    public IRenderTypeBuffer.Impl getRenderTypeBuffer() {
        return this.vertexBuilder.getRenderTypeBuffer();
    }

    @Override
    public void setQuadVertexPositions(VertexPosition[] vertexPositionArray) {
        this.vertexBuilder.setQuadVertexPositions(vertexPositionArray);
    }

    @Override
    public void setMidBlock(float f, float f2, float f3) {
        this.vertexBuilder.setMidBlock(f, f2, f3);
    }
}

