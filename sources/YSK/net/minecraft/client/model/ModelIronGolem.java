package net.minecraft.client.model;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ModelIronGolem extends ModelBase
{
    public ModelRenderer ironGolemBody;
    public ModelRenderer ironGolemLeftLeg;
    public ModelRenderer ironGolemHead;
    public ModelRenderer ironGolemRightArm;
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer ironGolemRightLeg;
    
    public ModelIronGolem() {
        this(0.0f);
    }
    
    private float func_78172_a(final float n, final float n2) {
        return (Math.abs(n % n2 - n2 * 0.5f) - n2 * 0.25f) / (n2 * 0.25f);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityIronGolem entityIronGolem = (EntityIronGolem)entityLivingBase;
        final int attackTimer = entityIronGolem.getAttackTimer();
        if (attackTimer > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(attackTimer - n3, 10.0f);
            this.ironGolemLeftArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(attackTimer - n3, 10.0f);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            final int holdRoseTick = entityIronGolem.getHoldRoseTick();
            if (holdRoseTick > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8f + 0.025f * this.func_78172_a(holdRoseTick, 70.0f);
                this.ironGolemLeftArm.rotateAngleX = 0.0f;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                this.ironGolemRightArm.rotateAngleX = (-0.2f + 1.5f * this.func_78172_a(n, 13.0f)) * n2;
                this.ironGolemLeftArm.rotateAngleX = (-0.2f - 1.5f * this.func_78172_a(n, 13.0f)) * n2;
            }
        }
    }
    
    public ModelIronGolem(final float n) {
        this(n, -7.0f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        this.ironGolemHead.rotateAngleY = n4 / 57.295776f;
        this.ironGolemHead.rotateAngleX = n5 / 57.295776f;
        this.ironGolemLeftLeg.rotateAngleX = -1.5f * this.func_78172_a(n, 13.0f) * n2;
        this.ironGolemRightLeg.rotateAngleX = 1.5f * this.func_78172_a(n, 13.0f) * n2;
        this.ironGolemLeftLeg.rotateAngleY = 0.0f;
        this.ironGolemRightLeg.rotateAngleY = 0.0f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.ironGolemHead.render(n6);
        this.ironGolemBody.render(n6);
        this.ironGolemLeftLeg.render(n6);
        this.ironGolemRightLeg.render(n6);
        this.ironGolemRightArm.render(n6);
        this.ironGolemLeftArm.render(n6);
    }
    
    public ModelIronGolem(final float n, final float n2) {
        final int n3 = 89 + 111 - 98 + 26;
        final int n4 = 33 + 95 - 68 + 68;
        (this.ironGolemHead = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, -2.0f);
        this.ironGolemHead.setTextureOffset("".length(), "".length()).addBox(-4.0f, -12.0f, -5.5f, 0x85 ^ 0x8D, 0x84 ^ 0x8E, 0xCF ^ 0xC7, n);
        this.ironGolemHead.setTextureOffset(0x61 ^ 0x79, "".length()).addBox(-1.0f, -5.0f, -7.5f, "  ".length(), 0xC6 ^ 0xC2, "  ".length(), n);
        (this.ironGolemBody = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, 0.0f + n2, 0.0f);
        this.ironGolemBody.setTextureOffset("".length(), 0x87 ^ 0xAF).addBox(-9.0f, -2.0f, -6.0f, 0x85 ^ 0x97, 0xAD ^ 0xA1, 0x4D ^ 0x46, n);
        this.ironGolemBody.setTextureOffset("".length(), 0x23 ^ 0x65).addBox(-4.5f, 10.0f, -3.0f, 0x32 ^ 0x3B, 0x5E ^ 0x5B, 0x60 ^ 0x66, n + 0.5f);
        (this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemRightArm.setTextureOffset(0x80 ^ 0xBC, 0xBE ^ 0xAB).addBox(-13.0f, -2.5f, -3.0f, 0x81 ^ 0x85, 0x2D ^ 0x33, 0x43 ^ 0x45, n);
        (this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(n3, n4)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemLeftArm.setTextureOffset(0xB1 ^ 0x8D, 0x8A ^ 0xB0).addBox(9.0f, -2.5f, -3.0f, 0x3C ^ 0x38, 0x8D ^ 0x93, 0x4D ^ 0x4B, n);
        (this.ironGolemLeftLeg = new ModelRenderer(this, "".length(), 0x12 ^ 0x4).setTextureSize(n3, n4)).setRotationPoint(-4.0f, 18.0f + n2, 0.0f);
        this.ironGolemLeftLeg.setTextureOffset(0x2E ^ 0xB, "".length()).addBox(-3.5f, -3.0f, -3.0f, 0x7F ^ 0x79, 0xB9 ^ 0xA9, 0xB ^ 0xE, n);
        this.ironGolemRightLeg = new ModelRenderer(this, "".length(), 0xA3 ^ 0xB5).setTextureSize(n3, n4);
        this.ironGolemRightLeg.mirror = (" ".length() != 0);
        this.ironGolemRightLeg.setTextureOffset(0x3E ^ 0x2, "".length()).setRotationPoint(5.0f, 18.0f + n2, 0.0f);
        this.ironGolemRightLeg.addBox(-3.5f, -3.0f, -3.0f, 0x94 ^ 0x92, 0xAF ^ 0xBF, 0x15 ^ 0x10, n);
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
