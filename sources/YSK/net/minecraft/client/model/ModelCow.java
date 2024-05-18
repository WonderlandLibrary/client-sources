package net.minecraft.client.model;

public class ModelCow extends ModelQuadruped
{
    public ModelCow() {
        super(0x22 ^ 0x2E, 0.0f);
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -4.0f, -6.0f, 0x3E ^ 0x36, 0x5F ^ 0x57, 0x37 ^ 0x31, 0.0f);
        this.head.setRotationPoint(0.0f, 4.0f, -8.0f);
        this.head.setTextureOffset(0x87 ^ 0x91, "".length()).addBox(-5.0f, -5.0f, -4.0f, " ".length(), "   ".length(), " ".length(), 0.0f);
        this.head.setTextureOffset(0x33 ^ 0x25, "".length()).addBox(4.0f, -5.0f, -4.0f, " ".length(), "   ".length(), " ".length(), 0.0f);
        (this.body = new ModelRenderer(this, 0x42 ^ 0x50, 0x60 ^ 0x64)).addBox(-6.0f, -10.0f, -7.0f, 0x33 ^ 0x3F, 0x47 ^ 0x55, 0x4D ^ 0x47, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        this.body.setTextureOffset(0x80 ^ 0xB4, "".length()).addBox(-2.0f, 2.0f, -8.0f, 0xBF ^ 0xBB, 0x30 ^ 0x36, " ".length());
        final ModelRenderer leg1 = this.leg1;
        --leg1.rotationPointX;
        final ModelRenderer leg2 = this.leg2;
        ++leg2.rotationPointX;
        final ModelRenderer leg3 = this.leg1;
        leg3.rotationPointZ += 0.0f;
        final ModelRenderer leg4 = this.leg2;
        leg4.rotationPointZ += 0.0f;
        final ModelRenderer leg5 = this.leg3;
        --leg5.rotationPointX;
        final ModelRenderer leg6 = this.leg4;
        ++leg6.rotationPointX;
        final ModelRenderer leg7 = this.leg3;
        --leg7.rotationPointZ;
        final ModelRenderer leg8 = this.leg4;
        --leg8.rotationPointZ;
        this.childZOffset += 2.0f;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
