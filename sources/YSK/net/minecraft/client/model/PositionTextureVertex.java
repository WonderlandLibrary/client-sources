package net.minecraft.client.model;

import net.minecraft.util.*;

public class PositionTextureVertex
{
    public Vec3 vector3D;
    public float texturePositionY;
    public float texturePositionX;
    
    public PositionTextureVertex(final PositionTextureVertex positionTextureVertex, final float texturePositionX, final float texturePositionY) {
        this.vector3D = positionTextureVertex.vector3D;
        this.texturePositionX = texturePositionX;
        this.texturePositionY = texturePositionY;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PositionTextureVertex setTexturePosition(final float n, final float n2) {
        return new PositionTextureVertex(this, n, n2);
    }
    
    public PositionTextureVertex(final Vec3 vector3D, final float texturePositionX, final float texturePositionY) {
        this.vector3D = vector3D;
        this.texturePositionX = texturePositionX;
        this.texturePositionY = texturePositionY;
    }
    
    public PositionTextureVertex(final float n, final float n2, final float n3, final float n4, final float n5) {
        this(new Vec3(n, n2, n3), n4, n5);
    }
}
