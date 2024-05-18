package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelSkeletonHead extends ModelBase
{
    public ModelRenderer skeletonHead;
    
    public ModelSkeletonHead() {
        this("".length(), 0x2B ^ 0x8, 0x45 ^ 0x5, 0xF7 ^ 0xB7);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.skeletonHead.rotateAngleY = n4 / 57.295776f;
        this.skeletonHead.rotateAngleX = n5 / 57.295776f;
    }
    
    public ModelSkeletonHead(final int n, final int n2, final int textureWidth, final int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        (this.skeletonHead = new ModelRenderer(this, n, n2)).addBox(-4.0f, -8.0f, -4.0f, 0x1E ^ 0x16, 0x63 ^ 0x6B, 0xF ^ 0x7, 0.0f);
        this.skeletonHead.setRotationPoint(0.0f, 0.0f, 0.0f);
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.skeletonHead.render(n6);
    }
}
