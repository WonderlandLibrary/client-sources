package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer[] sideModels;
    
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.sideModels[0xB9 ^ 0xBC].rotationPointY = 4.0f - n3;
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < (0x28 ^ 0x2E)) {
            this.sideModels[i].render(n6);
            ++i;
        }
    }
    
    public ModelMinecart() {
        (this.sideModels = new ModelRenderer[0x97 ^ 0x90])["".length()] = new ModelRenderer(this, "".length(), 0x9 ^ 0x3);
        this.sideModels[" ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.sideModels["  ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.sideModels["   ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.sideModels[0x54 ^ 0x50] = new ModelRenderer(this, "".length(), "".length());
        this.sideModels[0x51 ^ 0x54] = new ModelRenderer(this, 0x27 ^ 0xB, 0x3F ^ 0x35);
        final int n = 0x7 ^ 0x13;
        final int n2 = 0x74 ^ 0x7C;
        final int n3 = 0xBA ^ 0xAA;
        final int n4 = 0x56 ^ 0x52;
        this.sideModels["".length()].addBox(-n / "  ".length(), -n3 / "  ".length(), -1.0f, n, n3, "  ".length(), 0.0f);
        this.sideModels["".length()].setRotationPoint(0.0f, n4, 0.0f);
        this.sideModels[0xB1 ^ 0xB4].addBox(-n / "  ".length() + " ".length(), -n3 / "  ".length() + " ".length(), -1.0f, n - "  ".length(), n3 - "  ".length(), " ".length(), 0.0f);
        this.sideModels[0xC1 ^ 0xC4].setRotationPoint(0.0f, n4, 0.0f);
        this.sideModels[" ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0xC2 ^ 0xC6), n2, "  ".length(), 0.0f);
        this.sideModels[" ".length()].setRotationPoint(-n / "  ".length() + " ".length(), n4, 0.0f);
        this.sideModels["  ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x9A ^ 0x9E), n2, "  ".length(), 0.0f);
        this.sideModels["  ".length()].setRotationPoint(n / "  ".length() - " ".length(), n4, 0.0f);
        this.sideModels["   ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x9B ^ 0x9F), n2, "  ".length(), 0.0f);
        this.sideModels["   ".length()].setRotationPoint(0.0f, n4, -n3 / "  ".length() + " ".length());
        this.sideModels[0x7 ^ 0x3].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x85 ^ 0x81), n2, "  ".length(), 0.0f);
        this.sideModels[0xF ^ 0xB].setRotationPoint(0.0f, n4, n3 / "  ".length() - " ".length());
        this.sideModels["".length()].rotateAngleX = 1.5707964f;
        this.sideModels[" ".length()].rotateAngleY = 4.712389f;
        this.sideModels["  ".length()].rotateAngleY = 1.5707964f;
        this.sideModels["   ".length()].rotateAngleY = 3.1415927f;
        this.sideModels[0xAB ^ 0xAE].rotateAngleX = -1.5707964f;
    }
}
