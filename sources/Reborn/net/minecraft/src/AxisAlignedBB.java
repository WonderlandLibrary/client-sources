package net.minecraft.src;

public class AxisAlignedBB
{
    private static final ThreadLocal theAABBLocalPool;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    
    static {
        theAABBLocalPool = new AABBLocalPool();
    }
    
    public static AxisAlignedBB getBoundingBox(final double par0, final double par2, final double par4, final double par6, final double par8, final double par10) {
        return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
    }
    
    public static AABBPool getAABBPool() {
        return AxisAlignedBB.theAABBLocalPool.get();
    }
    
    public AxisAlignedBB(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        this.minX = par1;
        this.minY = par3;
        this.minZ = par5;
        this.maxX = par7;
        this.maxY = par9;
        this.maxZ = par11;
    }
    
    public AxisAlignedBB setBounds(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11) {
        this.minX = par1;
        this.minY = par3;
        this.minZ = par5;
        this.maxX = par7;
        this.maxY = par9;
        this.maxZ = par11;
        return this;
    }
    
    public AxisAlignedBB addCoord(final double par1, final double par3, final double par5) {
        double var7 = this.minX;
        double var8 = this.minY;
        double var9 = this.minZ;
        double var10 = this.maxX;
        double var11 = this.maxY;
        double var12 = this.maxZ;
        if (par1 < 0.0) {
            var7 += par1;
        }
        if (par1 > 0.0) {
            var10 += par1;
        }
        if (par3 < 0.0) {
            var8 += par3;
        }
        if (par3 > 0.0) {
            var11 += par3;
        }
        if (par5 < 0.0) {
            var9 += par5;
        }
        if (par5 > 0.0) {
            var12 += par5;
        }
        return getAABBPool().getAABB(var7, var8, var9, var10, var11, var12);
    }
    
    public AxisAlignedBB expand(final double par1, final double par3, final double par5) {
        final double var7 = this.minX - par1;
        final double var8 = this.minY - par3;
        final double var9 = this.minZ - par5;
        final double var10 = this.maxX + par1;
        final double var11 = this.maxY + par3;
        final double var12 = this.maxZ + par5;
        return getAABBPool().getAABB(var7, var8, var9, var10, var11, var12);
    }
    
    public AxisAlignedBB getOffsetBoundingBox(final double par1, final double par3, final double par5) {
        return getAABBPool().getAABB(this.minX + par1, this.minY + par3, this.minZ + par5, this.maxX + par1, this.maxY + par3, this.maxZ + par5);
    }
    
    public double calculateXOffset(final AxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxY <= this.minY || par1AxisAlignedBB.minY >= this.maxY) {
            return par2;
        }
        if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
            if (par2 > 0.0 && par1AxisAlignedBB.maxX <= this.minX) {
                final double var4 = this.minX - par1AxisAlignedBB.maxX;
                if (var4 < par2) {
                    par2 = var4;
                }
            }
            if (par2 < 0.0 && par1AxisAlignedBB.minX >= this.maxX) {
                final double var4 = this.maxX - par1AxisAlignedBB.minX;
                if (var4 > par2) {
                    par2 = var4;
                }
            }
            return par2;
        }
        return par2;
    }
    
    public double calculateYOffset(final AxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxX <= this.minX || par1AxisAlignedBB.minX >= this.maxX) {
            return par2;
        }
        if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
            if (par2 > 0.0 && par1AxisAlignedBB.maxY <= this.minY) {
                final double var4 = this.minY - par1AxisAlignedBB.maxY;
                if (var4 < par2) {
                    par2 = var4;
                }
            }
            if (par2 < 0.0 && par1AxisAlignedBB.minY >= this.maxY) {
                final double var4 = this.maxY - par1AxisAlignedBB.minY;
                if (var4 > par2) {
                    par2 = var4;
                }
            }
            return par2;
        }
        return par2;
    }
    
    public double calculateZOffset(final AxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxX <= this.minX || par1AxisAlignedBB.minX >= this.maxX) {
            return par2;
        }
        if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) {
            if (par2 > 0.0 && par1AxisAlignedBB.maxZ <= this.minZ) {
                final double var4 = this.minZ - par1AxisAlignedBB.maxZ;
                if (var4 < par2) {
                    par2 = var4;
                }
            }
            if (par2 < 0.0 && par1AxisAlignedBB.minZ >= this.maxZ) {
                final double var4 = this.maxZ - par1AxisAlignedBB.minZ;
                if (var4 > par2) {
                    par2 = var4;
                }
            }
            return par2;
        }
        return par2;
    }
    
    public boolean intersectsWith(final AxisAlignedBB par1AxisAlignedBB) {
        return par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX && (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) && (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ);
    }
    
    public AxisAlignedBB offset(final double par1, final double par3, final double par5) {
        this.minX += par1;
        this.minY += par3;
        this.minZ += par5;
        this.maxX += par1;
        this.maxY += par3;
        this.maxZ += par5;
        return this;
    }
    
    public boolean isVecInside(final Vec3 par1Vec3) {
        return par1Vec3.xCoord > this.minX && par1Vec3.xCoord < this.maxX && (par1Vec3.yCoord > this.minY && par1Vec3.yCoord < this.maxY) && (par1Vec3.zCoord > this.minZ && par1Vec3.zCoord < this.maxZ);
    }
    
    public double getAverageEdgeLength() {
        final double var1 = this.maxX - this.minX;
        final double var2 = this.maxY - this.minY;
        final double var3 = this.maxZ - this.minZ;
        return (var1 + var2 + var3) / 3.0;
    }
    
    public AxisAlignedBB contract(final double par1, final double par3, final double par5) {
        final double var7 = this.minX + par1;
        final double var8 = this.minY + par3;
        final double var9 = this.minZ + par5;
        final double var10 = this.maxX - par1;
        final double var11 = this.maxY - par3;
        final double var12 = this.maxZ - par5;
        return getAABBPool().getAABB(var7, var8, var9, var10, var11, var12);
    }
    
    public AxisAlignedBB copy() {
        return getAABBPool().getAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }
    
    public MovingObjectPosition calculateIntercept(final Vec3 par1Vec3, final Vec3 par2Vec3) {
        Vec3 var3 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.minX);
        Vec3 var4 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.maxX);
        Vec3 var5 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.minY);
        Vec3 var6 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.maxY);
        Vec3 var7 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.minZ);
        Vec3 var8 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.maxZ);
        if (!this.isVecInYZ(var3)) {
            var3 = null;
        }
        if (!this.isVecInYZ(var4)) {
            var4 = null;
        }
        if (!this.isVecInXZ(var5)) {
            var5 = null;
        }
        if (!this.isVecInXZ(var6)) {
            var6 = null;
        }
        if (!this.isVecInXY(var7)) {
            var7 = null;
        }
        if (!this.isVecInXY(var8)) {
            var8 = null;
        }
        Vec3 var9 = null;
        if (var3 != null && (var9 == null || par1Vec3.squareDistanceTo(var3) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var3;
        }
        if (var4 != null && (var9 == null || par1Vec3.squareDistanceTo(var4) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var4;
        }
        if (var5 != null && (var9 == null || par1Vec3.squareDistanceTo(var5) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var5;
        }
        if (var6 != null && (var9 == null || par1Vec3.squareDistanceTo(var6) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var6;
        }
        if (var7 != null && (var9 == null || par1Vec3.squareDistanceTo(var7) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var7;
        }
        if (var8 != null && (var9 == null || par1Vec3.squareDistanceTo(var8) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var8;
        }
        if (var9 == null) {
            return null;
        }
        byte var10 = -1;
        if (var9 == var3) {
            var10 = 4;
        }
        if (var9 == var4) {
            var10 = 5;
        }
        if (var9 == var5) {
            var10 = 0;
        }
        if (var9 == var6) {
            var10 = 1;
        }
        if (var9 == var7) {
            var10 = 2;
        }
        if (var9 == var8) {
            var10 = 3;
        }
        return new MovingObjectPosition(0, 0, 0, var10, var9);
    }
    
    private boolean isVecInYZ(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ);
    }
    
    private boolean isVecInXZ(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.zCoord >= this.minZ && par1Vec3.zCoord <= this.maxZ);
    }
    
    private boolean isVecInXY(final Vec3 par1Vec3) {
        return par1Vec3 != null && (par1Vec3.xCoord >= this.minX && par1Vec3.xCoord <= this.maxX && par1Vec3.yCoord >= this.minY && par1Vec3.yCoord <= this.maxY);
    }
    
    public void setBB(final AxisAlignedBB par1AxisAlignedBB) {
        this.minX = par1AxisAlignedBB.minX;
        this.minY = par1AxisAlignedBB.minY;
        this.minZ = par1AxisAlignedBB.minZ;
        this.maxX = par1AxisAlignedBB.maxX;
        this.maxY = par1AxisAlignedBB.maxY;
        this.maxZ = par1AxisAlignedBB.maxZ;
    }
    
    @Override
    public String toString() {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
}
