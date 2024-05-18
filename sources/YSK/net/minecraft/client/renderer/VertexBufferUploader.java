package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;

public class VertexBufferUploader extends WorldVertexBufferUploader
{
    private VertexBuffer vertexBuffer;
    
    @Override
    public void func_181679_a(final WorldRenderer worldRenderer) {
        worldRenderer.reset();
        this.vertexBuffer.func_181722_a(worldRenderer.getByteBuffer());
    }
    
    public VertexBufferUploader() {
        this.vertexBuffer = null;
    }
    
    public void setVertexBuffer(final VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }
    
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
