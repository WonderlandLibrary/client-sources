package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelWitch extends ModelVillager
{
    public boolean field_82900_g;
    private ModelRenderer field_82901_h;
    private ModelRenderer witchHat;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final ModelRenderer villagerNose = this.villagerNose;
        final ModelRenderer villagerNose2 = this.villagerNose;
        final ModelRenderer villagerNose3 = this.villagerNose;
        final float offsetX = 0.0f;
        villagerNose3.offsetZ = offsetX;
        villagerNose2.offsetY = offsetX;
        villagerNose.offsetX = offsetX;
        final float n7 = 0.01f * (entity.getEntityId() % (0xBB ^ 0xB1));
        this.villagerNose.rotateAngleX = MathHelper.sin(entity.ticksExisted * n7) * 4.5f * 3.1415927f / 180.0f;
        this.villagerNose.rotateAngleY = 0.0f;
        this.villagerNose.rotateAngleZ = MathHelper.cos(entity.ticksExisted * n7) * 2.5f * 3.1415927f / 180.0f;
        if (this.field_82900_g) {
            this.villagerNose.rotateAngleX = -0.9f;
            this.villagerNose.offsetZ = -0.09375f;
            this.villagerNose.offsetY = 0.1875f;
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelWitch(final float n) {
        super(n, 0.0f, 0x75 ^ 0x35, 123 + 107 - 226 + 124);
        (this.field_82901_h = new ModelRenderer(this).setTextureSize(0x35 ^ 0x75, 21 + 59 + 24 + 24)).setRotationPoint(0.0f, -2.0f, 0.0f);
        this.field_82901_h.setTextureOffset("".length(), "".length()).addBox(0.0f, 3.0f, -6.75f, " ".length(), " ".length(), " ".length(), -0.25f);
        this.villagerNose.addChild(this.field_82901_h);
        (this.witchHat = new ModelRenderer(this).setTextureSize(0xE4 ^ 0xA4, 7 + 122 - 96 + 95)).setRotationPoint(-5.0f, -10.03125f, -5.0f);
        this.witchHat.setTextureOffset("".length(), 0x4D ^ 0xD).addBox(0.0f, 0.0f, 0.0f, 0x6B ^ 0x61, "  ".length(), 0x29 ^ 0x23);
        this.villagerHead.addChild(this.witchHat);
        final ModelRenderer setTextureSize = new ModelRenderer(this).setTextureSize(0xDE ^ 0x9E, 44 + 55 - 4 + 33);
        setTextureSize.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize.setTextureOffset("".length(), 0x77 ^ 0x3B).addBox(0.0f, 0.0f, 0.0f, 0xA1 ^ 0xA6, 0xB6 ^ 0xB2, 0x29 ^ 0x2E);
        setTextureSize.rotateAngleX = -0.05235988f;
        setTextureSize.rotateAngleZ = 0.02617994f;
        this.witchHat.addChild(setTextureSize);
        final ModelRenderer setTextureSize2 = new ModelRenderer(this).setTextureSize(0xC8 ^ 0x88, 13 + 17 + 10 + 88);
        setTextureSize2.setRotationPoint(1.75f, -4.0f, 2.0f);
        setTextureSize2.setTextureOffset("".length(), 0x7B ^ 0x2C).addBox(0.0f, 0.0f, 0.0f, 0x43 ^ 0x47, 0x67 ^ 0x63, 0x4D ^ 0x49);
        setTextureSize2.rotateAngleX = -0.10471976f;
        setTextureSize2.rotateAngleZ = 0.05235988f;
        setTextureSize.addChild(setTextureSize2);
        final ModelRenderer setTextureSize3 = new ModelRenderer(this).setTextureSize(0x41 ^ 0x1, 106 + 50 - 77 + 49);
        setTextureSize3.setRotationPoint(1.75f, -2.0f, 2.0f);
        setTextureSize3.setTextureOffset("".length(), 0x73 ^ 0x2C).addBox(0.0f, 0.0f, 0.0f, " ".length(), "  ".length(), " ".length(), 0.25f);
        setTextureSize3.rotateAngleX = -0.20943952f;
        setTextureSize3.rotateAngleZ = 0.10471976f;
        setTextureSize2.addChild(setTextureSize3);
    }
}
