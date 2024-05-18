package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelLeashKnot extends ModelBase
{
    public ModelRenderer field_110723_a;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.field_110723_a.rotateAngleY = n4 / 57.295776f;
        this.field_110723_a.rotateAngleX = n5 / 57.295776f;
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelLeashKnot() {
        this("".length(), "".length(), 0x2F ^ 0xF, 0x6D ^ 0x4D);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.field_110723_a.render(n6);
    }
    
    public ModelLeashKnot(final int n, final int n2, final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        (this.field_110723_a = new ModelRenderer(this, n, n2)).addBox(-3.0f, -6.0f, -3.0f, 0xA5 ^ 0xA3, 0xA5 ^ 0xAD, 0x91 ^ 0x97, 0.0f);
        this.field_110723_a.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
}
