package net.minecraft.client.model;

public class ModelSign extends ModelBase
{
    public ModelRenderer signBoard;
    public ModelRenderer signStick;
    
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelSign() {
        (this.signBoard = new ModelRenderer(this, "".length(), "".length())).addBox(-12.0f, -14.0f, -1.0f, 0x8F ^ 0x97, 0x11 ^ 0x1D, "  ".length(), 0.0f);
        (this.signStick = new ModelRenderer(this, "".length(), 0x39 ^ 0x37)).addBox(-1.0f, -2.0f, -1.0f, "  ".length(), 0x84 ^ 0x8A, "  ".length(), 0.0f);
    }
    
    public void renderSign() {
        this.signBoard.render(0.0625f);
        this.signStick.render(0.0625f);
    }
}
