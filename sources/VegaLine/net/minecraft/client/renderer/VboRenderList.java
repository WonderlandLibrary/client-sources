/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.BlockRenderLayer;
import optifine.Config;
import shadersmod.client.ShadersRender;

public class VboRenderList
extends ChunkRenderContainer {
    @Override
    public void renderChunkLayer(BlockRenderLayer layer) {
        if (this.initialized) {
            for (RenderChunk renderchunk : this.renderChunks) {
                VertexBuffer vertexbuffer = renderchunk.getVertexBufferByLayer(layer.ordinal());
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderchunk);
                renderchunk.multModelviewMatrix();
                vertexbuffer.bindBuffer();
                this.setupArrayPointers();
                vertexbuffer.drawArrays(7);
                GlStateManager.popMatrix();
            }
            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }

    private void setupArrayPointers() {
        if (Config.isShaders()) {
            ShadersRender.setupArrayPointersVbo();
        } else {
            GlStateManager.glVertexPointer(3, 5126, 28, 0);
            GlStateManager.glColorPointer(4, 5121, 28, 12);
            GlStateManager.glTexCoordPointer(2, 5126, 28, 16);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.glTexCoordPointer(2, 5122, 28, 24);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }
}

