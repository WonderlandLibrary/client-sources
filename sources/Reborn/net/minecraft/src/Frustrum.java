package net.minecraft.src;

public class Frustrum implements ICamera
{
    private ClippingHelper clippingHelper;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    
    public Frustrum() {
        this.clippingHelper = ClippingHelperImpl.getInstance();
    }
    
    @Override
    public void setPosition(final double par1, final double par3, final double par5) {
        this.xPosition = par1;
        this.yPosition = par3;
        this.zPosition = par5;
    }
    
    public boolean isBoxInFrustum(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        return this.clippingHelper.isBoxInFrustum(par1 - this.xPosition, par3 - this.yPosition, par5 - this.zPosition, par7 - this.xPosition, par9 - this.yPosition, par11 - this.zPosition);
    }
    
    @Override
    public boolean isBoundingBoxInFrustum(final AxisAlignedBB par1AxisAlignedBB) {
        return this.isBoxInFrustum(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    }
    
    public boolean isBoxInFrustumFully(final double var1, final double var3, final double var5, final double var7, final double var9, final double var11) {
        return this.clippingHelper.isBoxInFrustumFully(var1 - this.xPosition, var3 - this.yPosition, var5 - this.zPosition, var7 - this.xPosition, var9 - this.yPosition, var11 - this.zPosition);
    }
    
    @Override
    public boolean isBoundingBoxInFrustumFully(final AxisAlignedBB var1) {
        return this.isBoxInFrustumFully(var1.minX, var1.minY, var1.minZ, var1.maxX, var1.maxY, var1.maxZ);
    }
}
