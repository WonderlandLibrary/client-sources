package net.minecraft.src;

public class Vec3
{
    public static final Vec3Pool fakePool;
    public final Vec3Pool myVec3LocalPool;
    public double xCoord;
    public double yCoord;
    public double zCoord;
    
    static {
        fakePool = new Vec3Pool(-1, -1);
    }
    
    public static Vec3 createVectorHelper(final double par0, final double par2, final double par4) {
        return new Vec3(Vec3.fakePool, par0, par2, par4);
    }
    
    protected Vec3(final Vec3Pool par1Vec3Pool, double par2, double par4, double par6) {
        if (par2 == -0.0) {
            par2 = 0.0;
        }
        if (par4 == -0.0) {
            par4 = 0.0;
        }
        if (par6 == -0.0) {
            par6 = 0.0;
        }
        this.xCoord = par2;
        this.yCoord = par4;
        this.zCoord = par6;
        this.myVec3LocalPool = par1Vec3Pool;
    }
    
    protected Vec3 setComponents(final double par1, final double par3, final double par5) {
        this.xCoord = par1;
        this.yCoord = par3;
        this.zCoord = par5;
        return this;
    }
    
    public Vec3 subtract(final Vec3 par1Vec3) {
        return this.myVec3LocalPool.getVecFromPool(par1Vec3.xCoord - this.xCoord, par1Vec3.yCoord - this.yCoord, par1Vec3.zCoord - this.zCoord);
    }
    
    public Vec3 normalize() {
        final double var1 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (var1 < 1.0E-4) ? this.myVec3LocalPool.getVecFromPool(0.0, 0.0, 0.0) : this.myVec3LocalPool.getVecFromPool(this.xCoord / var1, this.yCoord / var1, this.zCoord / var1);
    }
    
    public double dotProduct(final Vec3 par1Vec3) {
        return this.xCoord * par1Vec3.xCoord + this.yCoord * par1Vec3.yCoord + this.zCoord * par1Vec3.zCoord;
    }
    
    public Vec3 crossProduct(final Vec3 par1Vec3) {
        return this.myVec3LocalPool.getVecFromPool(this.yCoord * par1Vec3.zCoord - this.zCoord * par1Vec3.yCoord, this.zCoord * par1Vec3.xCoord - this.xCoord * par1Vec3.zCoord, this.xCoord * par1Vec3.yCoord - this.yCoord * par1Vec3.xCoord);
    }
    
    public Vec3 addVector(final double par1, final double par3, final double par5) {
        return this.myVec3LocalPool.getVecFromPool(this.xCoord + par1, this.yCoord + par3, this.zCoord + par5);
    }
    
    public double distanceTo(final Vec3 par1Vec3) {
        final double var2 = par1Vec3.xCoord - this.xCoord;
        final double var3 = par1Vec3.yCoord - this.yCoord;
        final double var4 = par1Vec3.zCoord - this.zCoord;
        return MathHelper.sqrt_double(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double squareDistanceTo(final Vec3 par1Vec3) {
        final double var2 = par1Vec3.xCoord - this.xCoord;
        final double var3 = par1Vec3.yCoord - this.yCoord;
        final double var4 = par1Vec3.zCoord - this.zCoord;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public double squareDistanceTo(final double par1, final double par3, final double par5) {
        final double var7 = par1 - this.xCoord;
        final double var8 = par3 - this.yCoord;
        final double var9 = par5 - this.zCoord;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double lengthVector() {
        return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }
    
    public Vec3 getIntermediateWithXValue(final Vec3 par1Vec3, final double par2) {
        final double var4 = par1Vec3.xCoord - this.xCoord;
        final double var5 = par1Vec3.yCoord - this.yCoord;
        final double var6 = par1Vec3.zCoord - this.zCoord;
        if (var4 * var4 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (par2 - this.xCoord) / var4;
        return (var7 >= 0.0 && var7 <= 1.0) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    public Vec3 getIntermediateWithYValue(final Vec3 par1Vec3, final double par2) {
        final double var4 = par1Vec3.xCoord - this.xCoord;
        final double var5 = par1Vec3.yCoord - this.yCoord;
        final double var6 = par1Vec3.zCoord - this.zCoord;
        if (var5 * var5 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (par2 - this.yCoord) / var5;
        return (var7 >= 0.0 && var7 <= 1.0) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    public Vec3 getIntermediateWithZValue(final Vec3 par1Vec3, final double par2) {
        final double var4 = par1Vec3.xCoord - this.xCoord;
        final double var5 = par1Vec3.yCoord - this.yCoord;
        final double var6 = par1Vec3.zCoord - this.zCoord;
        if (var6 * var6 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (par2 - this.zCoord) / var6;
        return (var7 >= 0.0 && var7 <= 1.0) ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    @Override
    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }
    
    public void rotateAroundX(final float par1) {
        final float var2 = MathHelper.cos(par1);
        final float var3 = MathHelper.sin(par1);
        final double var4 = this.xCoord;
        final double var5 = this.yCoord * var2 + this.zCoord * var3;
        final double var6 = this.zCoord * var2 - this.yCoord * var3;
        this.xCoord = var4;
        this.yCoord = var5;
        this.zCoord = var6;
    }
    
    public void rotateAroundY(final float par1) {
        final float var2 = MathHelper.cos(par1);
        final float var3 = MathHelper.sin(par1);
        final double var4 = this.xCoord * var2 + this.zCoord * var3;
        final double var5 = this.yCoord;
        final double var6 = this.zCoord * var2 - this.xCoord * var3;
        this.xCoord = var4;
        this.yCoord = var5;
        this.zCoord = var6;
    }
    
    public void rotateAroundZ(final float par1) {
        final float var2 = MathHelper.cos(par1);
        final float var3 = MathHelper.sin(par1);
        final double var4 = this.xCoord * var2 + this.yCoord * var3;
        final double var5 = this.yCoord * var2 - this.xCoord * var3;
        final double var6 = this.zCoord;
        this.xCoord = var4;
        this.yCoord = var5;
        this.zCoord = var6;
    }
}
