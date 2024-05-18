package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelBoat extends ModelBase
{
    public ModelRenderer[] boatSides;
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < (0x43 ^ 0x46)) {
            this.boatSides[i].render(n6);
            ++i;
        }
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelBoat() {
        (this.boatSides = new ModelRenderer[0x12 ^ 0x17])["".length()] = new ModelRenderer(this, "".length(), 0x41 ^ 0x49);
        this.boatSides[" ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.boatSides["  ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.boatSides["   ".length()] = new ModelRenderer(this, "".length(), "".length());
        this.boatSides[0x73 ^ 0x77] = new ModelRenderer(this, "".length(), "".length());
        final int n = 0xB2 ^ 0xAA;
        final int n2 = 0xA7 ^ 0xA1;
        final int n3 = 0x73 ^ 0x67;
        final int n4 = 0x4B ^ 0x4F;
        this.boatSides["".length()].addBox(-n / "  ".length(), -n3 / "  ".length() + "  ".length(), -3.0f, n, n3 - (0x53 ^ 0x57), 0x6C ^ 0x68, 0.0f);
        this.boatSides["".length()].setRotationPoint(0.0f, n4, 0.0f);
        this.boatSides[" ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x65 ^ 0x61), n2, "  ".length(), 0.0f);
        this.boatSides[" ".length()].setRotationPoint(-n / "  ".length() + " ".length(), n4, 0.0f);
        this.boatSides["  ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x56 ^ 0x52), n2, "  ".length(), 0.0f);
        this.boatSides["  ".length()].setRotationPoint(n / "  ".length() - " ".length(), n4, 0.0f);
        this.boatSides["   ".length()].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x22 ^ 0x26), n2, "  ".length(), 0.0f);
        this.boatSides["   ".length()].setRotationPoint(0.0f, n4, -n3 / "  ".length() + " ".length());
        this.boatSides[0x86 ^ 0x82].addBox(-n / "  ".length() + "  ".length(), -n2 - " ".length(), -1.0f, n - (0x69 ^ 0x6D), n2, "  ".length(), 0.0f);
        this.boatSides[0xD ^ 0x9].setRotationPoint(0.0f, n4, n3 / "  ".length() - " ".length());
        this.boatSides["".length()].rotateAngleX = 1.5707964f;
        this.boatSides[" ".length()].rotateAngleY = 4.712389f;
        this.boatSides["  ".length()].rotateAngleY = 1.5707964f;
        this.boatSides["   ".length()].rotateAngleY = 3.1415927f;
    }
}
