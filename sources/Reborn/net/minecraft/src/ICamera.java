package net.minecraft.src;

public interface ICamera
{
    boolean isBoundingBoxInFrustum(final AxisAlignedBB p0);
    
    boolean isBoundingBoxInFrustumFully(final AxisAlignedBB p0);
    
    void setPosition(final double p0, final double p1, final double p2);
}
