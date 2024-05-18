package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelZombie extends ModelBiped
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
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
    
    protected ModelZombie(final float n, final float n2, final int n3, final int n4) {
        super(n, n2, n3, n4);
    }
    
    public ModelZombie() {
        this(0.0f, "".length() != 0);
    }
    
    public ModelZombie(final float n, final boolean b) {
        final float n2 = 0.0f;
        final int n3 = 0x6A ^ 0x2A;
        int n4;
        if (b) {
            n4 = (0x4F ^ 0x6F);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n4 = (0x43 ^ 0x3);
        }
        super(n, n2, n3, n4);
    }
}
