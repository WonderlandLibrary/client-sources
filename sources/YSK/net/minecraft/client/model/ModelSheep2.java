package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;

public class ModelSheep2 extends ModelQuadruped
{
    private float headRotationAngleX;
    
    public ModelSheep2() {
        super(0x9A ^ 0x96, 0.0f);
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -4.0f, -6.0f, 0x60 ^ 0x66, 0x80 ^ 0x86, 0x5 ^ 0xD, 0.0f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(this, 0x73 ^ 0x6F, 0xB ^ 0x3)).addBox(-4.0f, -10.0f, -7.0f, 0x22 ^ 0x2A, 0x54 ^ 0x44, 0x5E ^ 0x58, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
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
            if (0 >= 1) {
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
}
