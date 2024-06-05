package net.minecraft.src;

public class PathPoint
{
    public final int xCoord;
    public final int yCoord;
    public final int zCoord;
    private final int hash;
    int index;
    float totalPathDistance;
    float distanceToNext;
    float distanceToTarget;
    PathPoint previous;
    public boolean isFirst;
    
    public PathPoint(final int par1, final int par2, final int par3) {
        this.index = -1;
        this.isFirst = false;
        this.xCoord = par1;
        this.yCoord = par2;
        this.zCoord = par3;
        this.hash = makeHash(par1, par2, par3);
    }
    
    public static int makeHash(final int par0, final int par1, final int par2) {
        return (par1 & 0xFF) | (par0 & 0x7FFF) << 8 | (par2 & 0x7FFF) << 24 | ((par0 < 0) ? Integer.MIN_VALUE : 0) | ((par2 < 0) ? 32768 : 0);
    }
    
    public float distanceTo(final PathPoint par1PathPoint) {
        final float var2 = par1PathPoint.xCoord - this.xCoord;
        final float var3 = par1PathPoint.yCoord - this.yCoord;
        final float var4 = par1PathPoint.zCoord - this.zCoord;
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public float func_75832_b(final PathPoint par1PathPoint) {
        final float var2 = par1PathPoint.xCoord - this.xCoord;
        final float var3 = par1PathPoint.yCoord - this.yCoord;
        final float var4 = par1PathPoint.zCoord - this.zCoord;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof PathPoint)) {
            return false;
        }
        final PathPoint var2 = (PathPoint)par1Obj;
        return this.hash == var2.hash && this.xCoord == var2.xCoord && this.yCoord == var2.yCoord && this.zCoord == var2.zCoord;
    }
    
    @Override
    public int hashCode() {
        return this.hash;
    }
    
    public boolean isAssigned() {
        return this.index >= 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.xCoord) + ", " + this.yCoord + ", " + this.zCoord;
    }
}
