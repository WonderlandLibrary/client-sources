package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

public class ModelSheep1 extends ModelQuadruped
{
    private float headRotationAngleX;
    
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
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleX = this.headRotationAngleX;
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        super.setLivingAnimations(entityLivingBase, n, n2, n3);
        this.head.rotationPointY = 6.0f + ((EntitySheep)entityLivingBase).getHeadRotationPointY(n3) * 9.0f;
        this.headRotationAngleX = ((EntitySheep)entityLivingBase).getHeadRotationAngleX(n3);
    }
    
    public ModelSheep1() {
        super(0x56 ^ 0x5A, 0.0f);
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -4.0f, -4.0f, 0xA7 ^ 0xA1, 0x56 ^ 0x50, 0x93 ^ 0x95, 0.6f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(this, 0x2A ^ 0x36, 0x28 ^ 0x20)).addBox(-4.0f, -10.0f, -7.0f, 0x8D ^ 0x85, 0x8C ^ 0x9C, 0x77 ^ 0x71, 1.75f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        final float n = 0.5f;
        (this.leg1 = new ModelRenderer(this, "".length(), 0x79 ^ 0x69)).addBox(-2.0f, 0.0f, -2.0f, 0xAC ^ 0xA8, 0xB0 ^ 0xB6, 0x90 ^ 0x94, n);
        this.leg1.setRotationPoint(-3.0f, 12.0f, 7.0f);
        (this.leg2 = new ModelRenderer(this, "".length(), 0x58 ^ 0x48)).addBox(-2.0f, 0.0f, -2.0f, 0x97 ^ 0x93, 0xF ^ 0x9, 0x7A ^ 0x7E, n);
        this.leg2.setRotationPoint(3.0f, 12.0f, 7.0f);
        (this.leg3 = new ModelRenderer(this, "".length(), 0x33 ^ 0x23)).addBox(-2.0f, 0.0f, -2.0f, 0x84 ^ 0x80, 0xA0 ^ 0xA6, 0x53 ^ 0x57, n);
        this.leg3.setRotationPoint(-3.0f, 12.0f, -5.0f);
        (this.leg4 = new ModelRenderer(this, "".length(), 0x6C ^ 0x7C)).addBox(-2.0f, 0.0f, -2.0f, 0x37 ^ 0x33, 0x62 ^ 0x64, 0x24 ^ 0x20, n);
        this.leg4.setRotationPoint(3.0f, 12.0f, -5.0f);
    }
}
