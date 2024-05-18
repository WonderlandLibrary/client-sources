package net.minecraft.client.renderer;

import java.util.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public abstract class ChunkRenderContainer
{
    protected boolean initialized;
    private double viewEntityX;
    protected List<RenderChunk> renderChunks;
    private double viewEntityZ;
    private double viewEntityY;
    
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void addRenderChunk(final RenderChunk renderChunk, final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.renderChunks.add(renderChunk);
    }
    
    public void preRenderChunk(final RenderChunk renderChunk) {
        final BlockPos position = renderChunk.getPosition();
        GlStateManager.translate((float)(position.getX() - this.viewEntityX), (float)(position.getY() - this.viewEntityY), (float)(position.getZ() - this.viewEntityZ));
    }
    
    public abstract void renderChunkLayer(final EnumWorldBlockLayer p0);
    
    public void initialize(final double viewEntityX, final double viewEntityY, final double viewEntityZ) {
        this.initialized = (" ".length() != 0);
        this.renderChunks.clear();
        this.viewEntityX = viewEntityX;
        this.viewEntityY = viewEntityY;
        this.viewEntityZ = viewEntityZ;
    }
    
    public ChunkRenderContainer() {
        this.renderChunks = (List<RenderChunk>)Lists.newArrayListWithCapacity(15584 + 2181 - 6984 + 6643);
    }
}
