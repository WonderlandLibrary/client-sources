package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelVillager extends ModelBase
{
    public ModelRenderer villagerBody;
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer villagerHead;
    public ModelRenderer villagerArms;
    public ModelRenderer rightVillagerLeg;
    public ModelRenderer villagerNose;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.villagerHead.rotateAngleY = n4 / 57.295776f;
        this.villagerHead.rotateAngleX = n5 / 57.295776f;
        this.villagerArms.rotationPointY = 3.0f;
        this.villagerArms.rotationPointZ = -1.0f;
        this.villagerArms.rotateAngleX = -0.75f;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2 * 0.5f;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2 * 0.5f;
        this.rightVillagerLeg.rotateAngleY = 0.0f;
        this.leftVillagerLeg.rotateAngleY = 0.0f;
    }
    
    public ModelVillager(final float n, final float n2, final int n3, final int n4) {
        (this.villagerHead = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.villagerHead.setTextureOffset("".length(), "".length()).addBox(-4.0f, -10.0f, -4.0f, 0x6B ^ 0x63, 0x8 ^ 0x2, 0x40 ^ 0x48, n);
        (this.villagerNose = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, n2 - 2.0f, 0.0f);
        this.villagerNose.setTextureOffset(0x96 ^ 0x8E, "".length()).addBox(-1.0f, -1.0f, -6.0f, "  ".length(), 0x0 ^ 0x4, "  ".length(), n);
        this.villagerHead.addChild(this.villagerNose);
        (this.villagerBody = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.villagerBody.setTextureOffset(0xAF ^ 0xBF, 0x66 ^ 0x72).addBox(-4.0f, 0.0f, -3.0f, 0x43 ^ 0x4B, 0x58 ^ 0x54, 0xC3 ^ 0xC5, n);
        this.villagerBody.setTextureOffset("".length(), 0xA4 ^ 0x82).addBox(-4.0f, 0.0f, -3.0f, 0x5E ^ 0x56, 0xA4 ^ 0xB6, 0x45 ^ 0x43, n + 0.5f);
        (this.villagerArms = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2 + 2.0f, 0.0f);
        this.villagerArms.setTextureOffset(0x9 ^ 0x25, 0x12 ^ 0x4).addBox(-8.0f, -2.0f, -2.0f, 0x9D ^ 0x99, 0x6F ^ 0x67, 0xB9 ^ 0xBD, n);
        this.villagerArms.setTextureOffset(0xAB ^ 0x87, 0x54 ^ 0x42).addBox(4.0f, -2.0f, -2.0f, 0xA6 ^ 0xA2, 0x92 ^ 0x9A, 0x3E ^ 0x3A, n);
        this.villagerArms.setTextureOffset(0x74 ^ 0x5C, 0x4F ^ 0x69).addBox(-4.0f, 2.0f, -2.0f, 0x4B ^ 0x43, 0x42 ^ 0x46, 0xAF ^ 0xAB, n);
        (this.rightVillagerLeg = new ModelRenderer(this, "".length(), 0x7F ^ 0x69).setTextureSize(n3, n4)).setRotationPoint(-2.0f, 12.0f + n2, 0.0f);
        this.rightVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 0x4E ^ 0x4A, 0x91 ^ 0x9D, 0x35 ^ 0x31, n);
        this.leftVillagerLeg = new ModelRenderer(this, "".length(), 0x7B ^ 0x6D).setTextureSize(n3, n4);
        this.leftVillagerLeg.mirror = (" ".length() != 0);
        this.leftVillagerLeg.setRotationPoint(2.0f, 12.0f + n2, 0.0f);
        this.leftVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 0x5A ^ 0x5E, 0x46 ^ 0x4A, 0x6C ^ 0x68, n);
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.villagerHead.render(n6);
        this.villagerBody.render(n6);
        this.rightVillagerLeg.render(n6);
        this.leftVillagerLeg.render(n6);
        this.villagerArms.render(n6);
    }
    
    public ModelVillager(final float n) {
        this(n, 0.0f, 0x4A ^ 0xA, 0xFE ^ 0xBE);
    }
}
