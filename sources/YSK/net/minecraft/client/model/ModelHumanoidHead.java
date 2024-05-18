package net.minecraft.client.model;

import net.minecraft.entity.*;

public class ModelHumanoidHead extends ModelSkeletonHead
{
    private final ModelRenderer head;
    
    public ModelHumanoidHead() {
        super("".length(), "".length(), 0xE8 ^ 0xA8, 0x42 ^ 0x2);
        (this.head = new ModelRenderer(this, 0xE4 ^ 0xC4, "".length())).addBox(-4.0f, -8.0f, -4.0f, 0xB5 ^ 0xBD, 0xAE ^ 0xA6, 0x56 ^ 0x5E, 0.25f);
        this.head.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        this.head.render(n6);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.head.rotateAngleY = this.skeletonHead.rotateAngleY;
        this.head.rotateAngleX = this.skeletonHead.rotateAngleX;
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
