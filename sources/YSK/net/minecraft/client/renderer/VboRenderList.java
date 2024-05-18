package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;

public class VboRenderList extends ChunkRenderContainer
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void setupArrayPointers() {
        GL11.glVertexPointer("   ".length(), 3425 + 4544 - 6726 + 3883, 0xD9 ^ 0xC5, 0L);
        GL11.glColorPointer(0x1F ^ 0x1B, 2746 + 4311 - 4574 + 2638, 0x6E ^ 0x72, 12L);
        GL11.glTexCoordPointer("  ".length(), 2100 + 2149 - 4140 + 5017, 0x71 ^ 0x6D, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer("  ".length(), 4136 + 250 - 4144 + 4880, 0x8E ^ 0x92, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    public void renderChunkLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.initialized) {
            final Iterator<RenderChunk> iterator = this.renderChunks.iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final RenderChunk renderChunk = iterator.next();
                final VertexBuffer vertexBufferByLayer = renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal());
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                renderChunk.multModelviewMatrix();
                vertexBufferByLayer.bindBuffer();
                this.setupArrayPointers();
                vertexBufferByLayer.drawArrays(0x1D ^ 0x1A);
                GlStateManager.popMatrix();
            }
            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, "".length());
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }
}
