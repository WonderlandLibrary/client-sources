package net.minecraft.client.model;

public class ModelPig extends ModelQuadruped
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelPig() {
        this(0.0f);
    }
    
    public ModelPig(final float n) {
        super(0x20 ^ 0x26, n);
        this.head.setTextureOffset(0x11 ^ 0x1, 0x8E ^ 0x9E).addBox(-2.0f, 0.0f, -9.0f, 0x29 ^ 0x2D, "   ".length(), " ".length(), n);
        this.childYOffset = 4.0f;
    }
}
