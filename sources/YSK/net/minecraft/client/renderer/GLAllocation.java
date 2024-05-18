package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import java.nio.*;

public class GLAllocation
{
    private static final String[] I;
    
    public static synchronized int generateDisplayLists(final int n) {
        final int glGenLists = GL11.glGenLists(n);
        if (glGenLists == 0) {
            final int glGetError = GL11.glGetError();
            String gluErrorString = GLAllocation.I["".length()];
            if (glGetError != 0) {
                gluErrorString = GLU.gluErrorString(glGetError);
            }
            throw new IllegalStateException(GLAllocation.I[" ".length()] + n + GLAllocation.I["  ".length()] + glGetError + GLAllocation.I["   ".length()] + gluErrorString);
        }
        return glGenLists;
    }
    
    public static IntBuffer createDirectIntBuffer(final int n) {
        return createDirectByteBuffer(n << "  ".length()).asIntBuffer();
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static synchronized ByteBuffer createDirectByteBuffer(final int n) {
        return ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
    }
    
    private static void I() {
        (I = new String[0xF ^ 0xB])["".length()] = I("-.z1;\u0011.(t*\f%?t;\u000615&=\u0006%", "cAZTI");
        GLAllocation.I[" ".length()] = I(" \u0004\u0011\n\u000b\u000b\u0001%\u001b\u0016g\u001a3\u001b\u00105\u00063\u000bE&\u0006v&!g\u00070OUg\u000e9\u001dE&H5\u0000\u0010)\u001cv\u0000\u0003g", "GhVoe");
        GLAllocation.I["  ".length()] = I("Fi\u0013\u0005G\u000f;&&\u0015Ja", "jITIg");
        GLAllocation.I["   ".length()] = I("}vo", "TLOFk");
    }
    
    public static synchronized void deleteDisplayLists(final int n) {
        GL11.glDeleteLists(n, " ".length());
    }
    
    static {
        I();
    }
    
    public static FloatBuffer createDirectFloatBuffer(final int n) {
        return createDirectByteBuffer(n << "  ".length()).asFloatBuffer();
    }
    
    public static synchronized void deleteDisplayLists(final int n, final int n2) {
        GL11.glDeleteLists(n, n2);
    }
}
