package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelSpider extends ModelBase
{
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderHead;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg8;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg2;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.spiderHead.rotateAngleY = n4 / 57.295776f;
        this.spiderHead.rotateAngleX = n5 / 57.295776f;
        final float n7 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -n7;
        this.spiderLeg2.rotateAngleZ = n7;
        this.spiderLeg3.rotateAngleZ = -n7 * 0.74f;
        this.spiderLeg4.rotateAngleZ = n7 * 0.74f;
        this.spiderLeg5.rotateAngleZ = -n7 * 0.74f;
        this.spiderLeg6.rotateAngleZ = n7 * 0.74f;
        this.spiderLeg7.rotateAngleZ = -n7;
        this.spiderLeg8.rotateAngleZ = n7;
        final float n8 = -0.0f;
        final float n9 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = n9 * 2.0f + n8;
        this.spiderLeg2.rotateAngleY = -n9 * 2.0f - n8;
        this.spiderLeg3.rotateAngleY = n9 * 1.0f + n8;
        this.spiderLeg4.rotateAngleY = -n9 * 1.0f - n8;
        this.spiderLeg5.rotateAngleY = -n9 * 1.0f + n8;
        this.spiderLeg6.rotateAngleY = n9 * 1.0f - n8;
        this.spiderLeg7.rotateAngleY = -n9 * 2.0f + n8;
        this.spiderLeg8.rotateAngleY = n9 * 2.0f - n8;
        final float n10 = -(MathHelper.cos(n * 0.6662f * 2.0f + 0.0f) * 0.4f) * n2;
        final float n11 = -(MathHelper.cos(n * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * n2;
        final float n12 = -(MathHelper.cos(n * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * n2;
        final float n13 = -(MathHelper.cos(n * 0.6662f * 2.0f + 4.712389f) * 0.4f) * n2;
        final float n14 = Math.abs(MathHelper.sin(n * 0.6662f + 0.0f) * 0.4f) * n2;
        final float n15 = Math.abs(MathHelper.sin(n * 0.6662f + 3.1415927f) * 0.4f) * n2;
        final float n16 = Math.abs(MathHelper.sin(n * 0.6662f + 1.5707964f) * 0.4f) * n2;
        final float n17 = Math.abs(MathHelper.sin(n * 0.6662f + 4.712389f) * 0.4f) * n2;
        final ModelRenderer spiderLeg1 = this.spiderLeg1;
        spiderLeg1.rotateAngleY += n10;
        final ModelRenderer spiderLeg2 = this.spiderLeg2;
        spiderLeg2.rotateAngleY += -n10;
        final ModelRenderer spiderLeg3 = this.spiderLeg3;
        spiderLeg3.rotateAngleY += n11;
        final ModelRenderer spiderLeg4 = this.spiderLeg4;
        spiderLeg4.rotateAngleY += -n11;
        final ModelRenderer spiderLeg5 = this.spiderLeg5;
        spiderLeg5.rotateAngleY += n12;
        final ModelRenderer spiderLeg6 = this.spiderLeg6;
        spiderLeg6.rotateAngleY += -n12;
        final ModelRenderer spiderLeg7 = this.spiderLeg7;
        spiderLeg7.rotateAngleY += n13;
        final ModelRenderer spiderLeg8 = this.spiderLeg8;
        spiderLeg8.rotateAngleY += -n13;
        final ModelRenderer spiderLeg9 = this.spiderLeg1;
        spiderLeg9.rotateAngleZ += n14;
        final ModelRenderer spiderLeg10 = this.spiderLeg2;
        spiderLeg10.rotateAngleZ += -n14;
        final ModelRenderer spiderLeg11 = this.spiderLeg3;
        spiderLeg11.rotateAngleZ += n15;
        final ModelRenderer spiderLeg12 = this.spiderLeg4;
        spiderLeg12.rotateAngleZ += -n15;
        final ModelRenderer spiderLeg13 = this.spiderLeg5;
        spiderLeg13.rotateAngleZ += n16;
        final ModelRenderer spiderLeg14 = this.spiderLeg6;
        spiderLeg14.rotateAngleZ += -n16;
        final ModelRenderer spiderLeg15 = this.spiderLeg7;
        spiderLeg15.rotateAngleZ += n17;
        final ModelRenderer spiderLeg16 = this.spiderLeg8;
        spiderLeg16.rotateAngleZ += -n17;
    }
    
    public ModelSpider() {
        final float n = 0.0f;
        final int n2 = 0x45 ^ 0x4A;
        (this.spiderHead = new ModelRenderer(this, 0x34 ^ 0x14, 0xB8 ^ 0xBC)).addBox(-4.0f, -4.0f, -8.0f, 0x83 ^ 0x8B, 0x27 ^ 0x2F, 0xA4 ^ 0xAC, n);
        this.spiderHead.setRotationPoint(0.0f, n2, -3.0f);
        (this.spiderNeck = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -3.0f, -3.0f, 0xF ^ 0x9, 0x5B ^ 0x5D, 0x98 ^ 0x9E, n);
        this.spiderNeck.setRotationPoint(0.0f, n2, 0.0f);
        (this.spiderBody = new ModelRenderer(this, "".length(), 0x15 ^ 0x19)).addBox(-5.0f, -4.0f, -6.0f, 0x3 ^ 0x9, 0x8D ^ 0x85, 0x5A ^ 0x56, n);
        this.spiderBody.setRotationPoint(0.0f, n2, 9.0f);
        (this.spiderLeg1 = new ModelRenderer(this, 0x4 ^ 0x16, "".length())).addBox(-15.0f, -1.0f, -1.0f, 0x8F ^ 0x9F, "  ".length(), "  ".length(), n);
        this.spiderLeg1.setRotationPoint(-4.0f, n2, 2.0f);
        (this.spiderLeg2 = new ModelRenderer(this, 0x2B ^ 0x39, "".length())).addBox(-1.0f, -1.0f, -1.0f, 0x62 ^ 0x72, "  ".length(), "  ".length(), n);
        this.spiderLeg2.setRotationPoint(4.0f, n2, 2.0f);
        (this.spiderLeg3 = new ModelRenderer(this, 0x95 ^ 0x87, "".length())).addBox(-15.0f, -1.0f, -1.0f, 0x52 ^ 0x42, "  ".length(), "  ".length(), n);
        this.spiderLeg3.setRotationPoint(-4.0f, n2, 1.0f);
        (this.spiderLeg4 = new ModelRenderer(this, 0x4F ^ 0x5D, "".length())).addBox(-1.0f, -1.0f, -1.0f, 0xBA ^ 0xAA, "  ".length(), "  ".length(), n);
        this.spiderLeg4.setRotationPoint(4.0f, n2, 1.0f);
        (this.spiderLeg5 = new ModelRenderer(this, 0x5C ^ 0x4E, "".length())).addBox(-15.0f, -1.0f, -1.0f, 0x41 ^ 0x51, "  ".length(), "  ".length(), n);
        this.spiderLeg5.setRotationPoint(-4.0f, n2, 0.0f);
        (this.spiderLeg6 = new ModelRenderer(this, 0x4B ^ 0x59, "".length())).addBox(-1.0f, -1.0f, -1.0f, 0x50 ^ 0x40, "  ".length(), "  ".length(), n);
        this.spiderLeg6.setRotationPoint(4.0f, n2, 0.0f);
        (this.spiderLeg7 = new ModelRenderer(this, 0x35 ^ 0x27, "".length())).addBox(-15.0f, -1.0f, -1.0f, 0x85 ^ 0x95, "  ".length(), "  ".length(), n);
        this.spiderLeg7.setRotationPoint(-4.0f, n2, -1.0f);
        (this.spiderLeg8 = new ModelRenderer(this, 0x56 ^ 0x44, "".length())).addBox(-1.0f, -1.0f, -1.0f, 0x96 ^ 0x86, "  ".length(), "  ".length(), n);
        this.spiderLeg8.setRotationPoint(4.0f, n2, -1.0f);
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.spiderHead.render(n6);
        this.spiderNeck.render(n6);
        this.spiderBody.render(n6);
        this.spiderLeg1.render(n6);
        this.spiderLeg2.render(n6);
        this.spiderLeg3.render(n6);
        this.spiderLeg4.render(n6);
        this.spiderLeg5.render(n6);
        this.spiderLeg6.render(n6);
        this.spiderLeg7.render(n6);
        this.spiderLeg8.render(n6);
    }
}
