package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class ModelSkeleton extends ModelZombie
{
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
    }
    
    public ModelSkeleton() {
        this(0.0f, "".length() != 0);
    }
    
    public ModelSkeleton(final float n, final boolean b) {
        super(n, 0.0f, 0x84 ^ 0xC4, 0x3B ^ 0x1B);
        if (!b) {
            (this.bipedRightArm = new ModelRenderer(this, 0x24 ^ 0xC, 0x36 ^ 0x26)).addBox(-1.0f, -2.0f, -1.0f, "  ".length(), 0x1B ^ 0x17, "  ".length(), n);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            this.bipedLeftArm = new ModelRenderer(this, 0xB8 ^ 0x90, 0x3A ^ 0x2A);
            this.bipedLeftArm.mirror = (" ".length() != 0);
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, "  ".length(), 0x45 ^ 0x49, "  ".length(), n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedRightLeg = new ModelRenderer(this, "".length(), 0x51 ^ 0x41)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x64 ^ 0x68, "  ".length(), n);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg = new ModelRenderer(this, "".length(), 0x24 ^ 0x34);
            this.bipedLeftLeg.mirror = (" ".length() != 0);
            this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x64 ^ 0x68, "  ".length(), n);
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        int aimedBow;
        if (((EntitySkeleton)entityLivingBase).getSkeletonType() == " ".length()) {
            aimedBow = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            aimedBow = "".length();
        }
        this.aimedBow = (aimedBow != 0);
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
