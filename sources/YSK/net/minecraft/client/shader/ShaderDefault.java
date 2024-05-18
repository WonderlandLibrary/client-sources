package net.minecraft.client.shader;

import org.lwjgl.util.vector.*;

public class ShaderDefault extends ShaderUniform
{
    private static final String[] I;
    
    @Override
    public void set(final float n, final float n2, final float n3) {
    }
    
    @Override
    public void set(final float[] array) {
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*\".\u0018\u0011", "NWCuh");
    }
    
    @Override
    public void set(final int n, final int n2, final int n3, final int n4) {
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void set(final float n) {
    }
    
    @Override
    public void func_148092_b(final float n, final float n2, final float n3, final float n4) {
    }
    
    @Override
    public void set(final float n, final float n2) {
    }
    
    @Override
    public void set(final float n, final float n2, final float n3, final float n4) {
    }
    
    @Override
    public void set(final Matrix4f matrix4f) {
    }
    
    @Override
    public void set(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14, final float n15, final float n16) {
    }
    
    public ShaderDefault() {
        super(ShaderDefault.I["".length()], 0x83 ^ 0x87, " ".length(), null);
    }
    
    static {
        I();
    }
}
