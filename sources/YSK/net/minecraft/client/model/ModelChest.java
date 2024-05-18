package net.minecraft.client.model;

public class ModelChest extends ModelBase
{
    public ModelRenderer chestKnob;
    public ModelRenderer chestLid;
    public ModelRenderer chestBelow;
    
    public ModelChest() {
        (this.chestLid = new ModelRenderer(this, "".length(), "".length()).setTextureSize(0xDD ^ 0x9D, 0xF8 ^ 0xB8)).addBox(0.0f, -5.0f, -14.0f, 0x92 ^ 0x9C, 0x38 ^ 0x3D, 0x7A ^ 0x74, 0.0f);
        this.chestLid.rotationPointX = 1.0f;
        this.chestLid.rotationPointY = 7.0f;
        this.chestLid.rotationPointZ = 15.0f;
        (this.chestKnob = new ModelRenderer(this, "".length(), "".length()).setTextureSize(0xF2 ^ 0xB2, 0x29 ^ 0x69)).addBox(-1.0f, -2.0f, -15.0f, "  ".length(), 0xB0 ^ 0xB4, " ".length(), 0.0f);
        this.chestKnob.rotationPointX = 8.0f;
        this.chestKnob.rotationPointY = 7.0f;
        this.chestKnob.rotationPointZ = 15.0f;
        (this.chestBelow = new ModelRenderer(this, "".length(), 0x31 ^ 0x22).setTextureSize(0xE ^ 0x4E, 0x4B ^ 0xB)).addBox(0.0f, 0.0f, 0.0f, 0x9 ^ 0x7, 0xB ^ 0x1, 0x7A ^ 0x74, 0.0f);
        this.chestBelow.rotationPointX = 1.0f;
        this.chestBelow.rotationPointY = 6.0f;
        this.chestBelow.rotationPointZ = 1.0f;
    }
    
    public void renderAll() {
        this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
        this.chestLid.render(0.0625f);
        this.chestKnob.render(0.0625f);
        this.chestBelow.render(0.0625f);
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
