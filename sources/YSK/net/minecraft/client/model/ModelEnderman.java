package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelEnderman extends ModelBiped
{
    public boolean isAttacking;
    public boolean isCarrying;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.bipedHead.showModel = (" ".length() != 0);
        final float rotationPointY = -14.0f;
        this.bipedBody.rotateAngleX = 0.0f;
        this.bipedBody.rotationPointY = rotationPointY;
        this.bipedBody.rotationPointZ = -0.0f;
        final ModelRenderer bipedRightLeg = this.bipedRightLeg;
        bipedRightLeg.rotateAngleX -= 0.0f;
        final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
        bipedLeftLeg.rotateAngleX -= 0.0f;
        this.bipedRightArm.rotateAngleX *= 0.5;
        this.bipedLeftArm.rotateAngleX *= 0.5;
        this.bipedRightLeg.rotateAngleX *= 0.5;
        this.bipedLeftLeg.rotateAngleX *= 0.5;
        final float n7 = 0.4f;
        if (this.bipedRightArm.rotateAngleX > n7) {
            this.bipedRightArm.rotateAngleX = n7;
        }
        if (this.bipedLeftArm.rotateAngleX > n7) {
            this.bipedLeftArm.rotateAngleX = n7;
        }
        if (this.bipedRightArm.rotateAngleX < -n7) {
            this.bipedRightArm.rotateAngleX = -n7;
        }
        if (this.bipedLeftArm.rotateAngleX < -n7) {
            this.bipedLeftArm.rotateAngleX = -n7;
        }
        if (this.bipedRightLeg.rotateAngleX > n7) {
            this.bipedRightLeg.rotateAngleX = n7;
        }
        if (this.bipedLeftLeg.rotateAngleX > n7) {
            this.bipedLeftLeg.rotateAngleX = n7;
        }
        if (this.bipedRightLeg.rotateAngleX < -n7) {
            this.bipedRightLeg.rotateAngleX = -n7;
        }
        if (this.bipedLeftLeg.rotateAngleX < -n7) {
            this.bipedLeftLeg.rotateAngleX = -n7;
        }
        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5f;
            this.bipedLeftArm.rotateAngleX = -0.5f;
            this.bipedRightArm.rotateAngleZ = 0.05f;
            this.bipedLeftArm.rotateAngleZ = -0.05f;
        }
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointZ = 0.0f;
        this.bipedLeftLeg.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointY = 9.0f + rotationPointY;
        this.bipedLeftLeg.rotationPointY = 9.0f + rotationPointY;
        this.bipedHead.rotationPointZ = -0.0f;
        this.bipedHead.rotationPointY = rotationPointY + 1.0f;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            final float n8 = 1.0f;
            final ModelRenderer bipedHead = this.bipedHead;
            bipedHead.rotationPointY -= n8 * 5.0f;
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelEnderman(final float n) {
        super(0.0f, -14.0f, 0x1B ^ 0x5B, 0x69 ^ 0x49);
        final float n2 = -14.0f;
        (this.bipedHeadwear = new ModelRenderer(this, "".length(), 0x19 ^ 0x9)).addBox(-4.0f, -8.0f, -4.0f, 0x44 ^ 0x4C, 0x3 ^ 0xB, 0x2D ^ 0x25, n - 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        (this.bipedBody = new ModelRenderer(this, 0x63 ^ 0x43, 0x5 ^ 0x15)).addBox(-4.0f, 0.0f, -2.0f, 0x80 ^ 0x88, 0x9D ^ 0x91, 0x62 ^ 0x66, n);
        this.bipedBody.setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 0xAD ^ 0x95, "".length())).addBox(-1.0f, -2.0f, -1.0f, "  ".length(), 0x96 ^ 0x88, "  ".length(), n);
        this.bipedRightArm.setRotationPoint(-3.0f, 2.0f + n2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 0x7E ^ 0x46, "".length());
        this.bipedLeftArm.mirror = (" ".length() != 0);
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, "  ".length(), 0x7A ^ 0x64, "  ".length(), n);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f + n2, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 0x50 ^ 0x68, "".length())).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x1C ^ 0x2, "  ".length(), n);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f + n2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0xA3 ^ 0x9B, "".length());
        this.bipedLeftLeg.mirror = (" ".length() != 0);
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0xD ^ 0x13, "  ".length(), n);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f + n2, 0.0f);
    }
}
