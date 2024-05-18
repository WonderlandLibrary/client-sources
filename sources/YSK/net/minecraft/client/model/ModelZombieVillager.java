package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelZombieVillager extends ModelBiped
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelZombieVillager(final float n, final float n2, final boolean b) {
        final float n3 = 0.0f;
        final int n4 = 0x57 ^ 0x17;
        int n5;
        if (b) {
            n5 = (0x3A ^ 0x1A);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n5 = (0x43 ^ 0x3);
        }
        super(n, n3, n4, n5);
        if (b) {
            (this.bipedHead = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -10.0f, -4.0f, 0x26 ^ 0x2E, 0x59 ^ 0x51, 0x6A ^ 0x62, n);
            this.bipedHead.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            (this.bipedHead = new ModelRenderer(this)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
            this.bipedHead.setTextureOffset("".length(), 0xA5 ^ 0x85).addBox(-4.0f, -10.0f, -4.0f, 0x9E ^ 0x96, 0x7C ^ 0x76, 0xCE ^ 0xC6, n);
            this.bipedHead.setTextureOffset(0x73 ^ 0x6B, 0x62 ^ 0x42).addBox(-1.0f, -3.0f, -6.0f, "  ".length(), 0x9F ^ 0x9B, "  ".length(), n);
        }
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final float sin = MathHelper.sin(this.swingProgress * 3.1415927f);
        final float sin2 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - sin * 0.6f);
        this.bipedLeftArm.rotateAngleY = 0.1f - sin * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= sin * 1.2f - sin2 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(n3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(n3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(n3 * 0.067f) * 0.05f;
    }
    
    public ModelZombieVillager() {
        this(0.0f, 0.0f, "".length() != 0);
    }
}
