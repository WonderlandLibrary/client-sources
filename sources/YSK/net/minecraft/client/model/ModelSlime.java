package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSlime extends ModelBase
{
    ModelRenderer slimeBodies;
    ModelRenderer slimeRightEye;
    ModelRenderer slimeMouth;
    ModelRenderer slimeLeftEye;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.slimeBodies.render(n6);
        if (this.slimeRightEye != null) {
            this.slimeRightEye.render(n6);
            this.slimeLeftEye.render(n6);
            this.slimeMouth.render(n6);
        }
    }
    
    public ModelSlime(final int n) {
        (this.slimeBodies = new ModelRenderer(this, "".length(), n)).addBox(-4.0f, 16.0f, -4.0f, 0x8A ^ 0x82, 0x2 ^ 0xA, 0x7A ^ 0x72);
        if (n > 0) {
            (this.slimeBodies = new ModelRenderer(this, "".length(), n)).addBox(-3.0f, 17.0f, -3.0f, 0xC0 ^ 0xC6, 0x3F ^ 0x39, 0xB6 ^ 0xB0);
            (this.slimeRightEye = new ModelRenderer(this, 0x22 ^ 0x2, "".length())).addBox(-3.25f, 18.0f, -3.5f, "  ".length(), "  ".length(), "  ".length());
            (this.slimeLeftEye = new ModelRenderer(this, 0x83 ^ 0xA3, 0x3E ^ 0x3A)).addBox(1.25f, 18.0f, -3.5f, "  ".length(), "  ".length(), "  ".length());
            (this.slimeMouth = new ModelRenderer(this, 0xBC ^ 0x9C, 0x37 ^ 0x3F)).addBox(0.0f, 21.0f, -3.5f, " ".length(), " ".length(), " ".length());
        }
    }
}
