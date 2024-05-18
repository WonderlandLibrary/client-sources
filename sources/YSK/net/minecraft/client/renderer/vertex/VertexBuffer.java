package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class VertexBuffer
{
    private int glBufferId;
    private int count;
    private final VertexFormat vertexFormat;
    
    public void unbindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, "".length());
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void bindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
    }
    
    public void func_181722_a(final ByteBuffer byteBuffer) {
        this.bindBuffer();
        OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, byteBuffer, 31486 + 5016 - 4392 + 2934);
        this.unbindBuffer();
        this.count = byteBuffer.limit() / this.vertexFormat.getNextOffset();
    }
    
    public VertexBuffer(final VertexFormat vertexFormat) {
        this.vertexFormat = vertexFormat;
        this.glBufferId = OpenGlHelper.glGenBuffers();
    }
    
    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            OpenGlHelper.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -" ".length();
        }
    }
    
    public void drawArrays(final int n) {
        GL11.glDrawArrays(n, "".length(), this.count);
    }
}
