package net.minecraft.client.renderer.culling;

public class ClippingHelper
{
    public float[] clippingMatrix;
    private static final String[] I;
    public float[][] frustum;
    private static final String __OBFID;
    public float[] projectionMatrix;
    public float[] modelviewMatrix;
    
    public ClippingHelper() {
        this.frustum = new float[0xB9 ^ 0xBF][0x77 ^ 0x73];
        this.projectionMatrix = new float[0x17 ^ 0x7];
        this.modelviewMatrix = new float[0x64 ^ 0x74];
        this.clippingMatrix = new float[0x9C ^ 0x8C];
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\b&\u000f\\s{Z`Ut|", "KjPlC");
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private float dot(final float[] array, final float n, final float n2, final float n3) {
        return array["".length()] * n + array[" ".length()] * n2 + array["  ".length()] * n3 + array["   ".length()];
    }
    
    public boolean isBoxInFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final float n7 = (float)n;
        final float n8 = (float)n2;
        final float n9 = (float)n3;
        final float n10 = (float)n4;
        final float n11 = (float)n5;
        final float n12 = (float)n6;
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < (0x90 ^ 0x96)) {
            final float[] array = this.frustum[i];
            if (this.dot(array, n7, n8, n9) <= 0.0f && this.dot(array, n10, n8, n9) <= 0.0f && this.dot(array, n7, n11, n9) <= 0.0f && this.dot(array, n10, n11, n9) <= 0.0f && this.dot(array, n7, n8, n12) <= 0.0f && this.dot(array, n10, n8, n12) <= 0.0f && this.dot(array, n7, n11, n12) <= 0.0f && this.dot(array, n10, n11, n12) <= 0.0f) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        __OBFID = ClippingHelper.I["".length()];
    }
}
