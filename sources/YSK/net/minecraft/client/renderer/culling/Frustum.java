package net.minecraft.client.renderer.culling;

import net.minecraft.util.*;

public class Frustum implements ICamera
{
    private double yPosition;
    private double zPosition;
    private double xPosition;
    private ClippingHelper clippingHelper;
    
    public boolean isBoxInFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        return this.clippingHelper.isBoxInFrustum(n - this.xPosition, n2 - this.yPosition, n3 - this.zPosition, n4 - this.xPosition, n5 - this.yPosition, n6 - this.zPosition);
    }
    
    @Override
    public void setPosition(final double xPosition, final double yPosition, final double zPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
    }
    
    public Frustum() {
        this(ClippingHelperImpl.getInstance());
    }
    
    public Frustum(final ClippingHelper clippingHelper) {
        this.clippingHelper = clippingHelper;
    }
    
    @Override
    public boolean isBoundingBoxInFrustum(final AxisAlignedBB axisAlignedBB) {
        return this.isBoxInFrustum(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
