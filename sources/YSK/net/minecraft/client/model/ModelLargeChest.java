package net.minecraft.client.model;

public class ModelLargeChest extends ModelChest
{
    public ModelLargeChest() {
        (this.chestLid = new ModelRenderer(this, "".length(), "".length()).setTextureSize(116 + 102 - 190 + 100, 0x3F ^ 0x7F)).addBox(0.0f, -5.0f, -14.0f, 0x3D ^ 0x23, 0x95 ^ 0x90, 0x75 ^ 0x7B, 0.0f);
        this.chestLid.rotationPointX = 1.0f;
        this.chestLid.rotationPointY = 7.0f;
        this.chestLid.rotationPointZ = 15.0f;
        (this.chestKnob = new ModelRenderer(this, "".length(), "".length()).setTextureSize(116 + 75 - 165 + 102, 0xEC ^ 0xAC)).addBox(-1.0f, -2.0f, -15.0f, "  ".length(), 0xB1 ^ 0xB5, " ".length(), 0.0f);
        this.chestKnob.rotationPointX = 16.0f;
        this.chestKnob.rotationPointY = 7.0f;
        this.chestKnob.rotationPointZ = 15.0f;
        (this.chestBelow = new ModelRenderer(this, "".length(), 0x67 ^ 0x74).setTextureSize(60 + 30 - 57 + 95, 0x86 ^ 0xC6)).addBox(0.0f, 0.0f, 0.0f, 0xB5 ^ 0xAB, 0x21 ^ 0x2B, 0xB1 ^ 0xBF, 0.0f);
        this.chestBelow.rotationPointX = 1.0f;
        this.chestBelow.rotationPointY = 6.0f;
        this.chestBelow.rotationPointZ = 1.0f;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
