/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.lwjgl.opengl.GL11;

public class VboRenderList
extends ChunkRenderContainer {
    @Override
    public void renderChunkLayer(EnumWorldBlockLayer layer) {
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
        GL11.glVertexPointer(3, 5126, 28, 0L);
        GL11.glColorPointer(4, 5121, 28, 12L);
        GL11.glTexCoordPointer(2, 5126, 28, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer(2, 5122, 28, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}

