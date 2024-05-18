package net.minecraft.client.renderer;

import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import org.lwjgl.opengl.*;
import optfine.*;
import java.util.*;

public class RenderList extends ChunkRenderContainer
{
    private static final String[] I;
    private static final String __OBFID;
    
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderChunkLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.initialized) {
            if (this.renderChunks.size() == 0) {
                return;
            }
            final Iterator<RenderChunk> iterator = this.renderChunks.iterator();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final RenderChunk renderChunk = iterator.next();
                final ListedRenderChunk listedRenderChunk = (ListedRenderChunk)renderChunk;
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                GL11.glCallList(listedRenderChunk.getDisplayList(enumWorldBlockLayer, listedRenderChunk.getCompiledChunk()));
                GlStateManager.popMatrix();
            }
            if (Config.isMultiTexture()) {
                GlStateManager.bindCurrentTexture();
            }
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }
    
    static {
        I();
        __OBFID = RenderList.I["".length()];
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("2'=UZA[R\\_F", "qkbej");
    }
}
