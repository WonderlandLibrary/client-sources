package net.minecraft.src;

public class PathNavigate
{
    private EntityLiving theEntity;
    private World worldObj;
    private PathEntity currentPath;
    private float speed;
    private float pathSearchRange;
    private boolean noSunPathfind;
    private int totalTicks;
    private int ticksAtLastPos;
    private Vec3 lastPosCheck;
    private boolean canPassOpenWoodenDoors;
    private boolean canPassClosedWoodenDoors;
    private boolean avoidsWater;
    private boolean canSwim;
    
    public PathNavigate(final EntityLiving par1EntityLiving, final World par2World, final float par3) {
        this.noSunPathfind = false;
        this.lastPosCheck = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        this.canPassOpenWoodenDoors = true;
        this.canPassClosedWoodenDoors = false;
        this.avoidsWater = false;
        this.canSwim = false;
        this.theEntity = par1EntityLiving;
        this.worldObj = par2World;
        this.pathSearchRange = par3;
    }
    
    public void setAvoidsWater(final boolean par1) {
        this.avoidsWater = par1;
    }
    
    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }
    
    public void setBreakDoors(final boolean par1) {
        this.canPassClosedWoodenDoors = par1;
    }
    
    public void setEnterDoors(final boolean par1) {
        this.canPassOpenWoodenDoors = par1;
    }
    
    public boolean getCanBreakDoors() {
        return this.canPassClosedWoodenDoors;
    }
    
    public void setAvoidSun(final boolean par1) {
        this.noSunPathfind = par1;
    }
    
    public void setSpeed(final float par1) {
        this.speed = par1;
    }
    
    public void setCanSwim(final boolean par1) {
        this.canSwim = par1;
    }
    
    public PathEntity getPathToXYZ(final double par1, final double par3, final double par5) {
        return this.canNavigate() ? this.worldObj.getEntityPathToXYZ(this.theEntity, MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5), this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim) : null;
    }
    
    public boolean tryMoveToXYZ(final double par1, final double par3, final double par5, final float par7) {
        final PathEntity var8 = this.getPathToXYZ(MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5));
        return this.setPath(var8, par7);
    }
    
    public PathEntity getPathToEntityLiving(final EntityLiving par1EntityLiving) {
        return this.canNavigate() ? this.worldObj.getPathEntityToEntity(this.theEntity, par1EntityLiving, this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim) : null;
    }
    
    public boolean tryMoveToEntityLiving(final EntityLiving par1EntityLiving, final float par2) {
        final PathEntity var3 = this.getPathToEntityLiving(par1EntityLiving);
        return var3 != null && this.setPath(var3, par2);
    }
    
    public boolean setPath(final PathEntity par1PathEntity, final float par2) {
        if (par1PathEntity == null) {
            this.currentPath = null;
            return false;
        }
        if (!par1PathEntity.isSamePath(this.currentPath)) {
            this.currentPath = par1PathEntity;
        }
        if (this.noSunPathfind) {
            this.removeSunnyPath();
        }
        if (this.currentPath.getCurrentPathLength() == 0) {
            return false;
        }
        this.speed = par2;
        final Vec3 var3 = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck.xCoord = var3.xCoord;
        this.lastPosCheck.yCoord = var3.yCoord;
        this.lastPosCheck.zCoord = var3.zCoord;
        return true;
    }
    
    public PathEntity getPath() {
        return this.currentPath;
    }
    
    public void onUpdateNavigation() {
        ++this.totalTicks;
        if (!this.noPath()) {
            if (this.canNavigate()) {
                this.pathFollow();
            }
            if (!this.noPath()) {
                final Vec3 var1 = this.currentPath.getPosition(this.theEntity);
                if (var1 != null) {
                    this.theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, this.speed);
                }
            }
        }
    }
    
    private void pathFollow() {
        final Vec3 var1 = this.getEntityPosition();
        int var2 = this.currentPath.getCurrentPathLength();
        for (int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); ++var3) {
            if (this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord) {
                var2 = var3;
                break;
            }
        }
        final float var4 = this.theEntity.width * this.theEntity.width;
        for (int var5 = this.currentPath.getCurrentPathIndex(); var5 < var2; ++var5) {
            if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var5)) < var4) {
                this.currentPath.setCurrentPathIndex(var5 + 1);
            }
        }
        int var5 = MathHelper.ceiling_float_int(this.theEntity.width);
        final int var6 = (int)this.theEntity.height + 1;
        final int var7 = var5;
        for (int var8 = var2 - 1; var8 >= this.currentPath.getCurrentPathIndex(); --var8) {
            if (this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var8), var5, var6, var7)) {
                this.currentPath.setCurrentPathIndex(var8);
                break;
            }
        }
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (var1.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck.xCoord = var1.xCoord;
            this.lastPosCheck.yCoord = var1.yCoord;
            this.lastPosCheck.zCoord = var1.zCoord;
        }
    }
    
    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }
    
    public void clearPathEntity() {
        this.currentPath = null;
    }
    
    private Vec3 getEntityPosition() {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(this.theEntity.posX, this.getPathableYPos(), this.theEntity.posZ);
    }
    
    private int getPathableYPos() {
        if (this.theEntity.isInWater() && this.canSwim) {
            int var1 = (int)this.theEntity.boundingBox.minY;
            int var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
            int var3 = 0;
            while (var2 == Block.waterMoving.blockID || var2 == Block.waterStill.blockID) {
                ++var1;
                var2 = this.worldObj.getBlockId(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
                if (++var3 > 16) {
                    return (int)this.theEntity.boundingBox.minY;
                }
            }
            return var1;
        }
        return (int)(this.theEntity.boundingBox.minY + 0.5);
    }
    
    private boolean canNavigate() {
        return this.theEntity.onGround || (this.canSwim && this.isInFluid());
    }
    
    private boolean isInFluid() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }
    
    private void removeSunnyPath() {
        if (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5), MathHelper.floor_double(this.theEntity.posZ))) {
            for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1) {
                final PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
                if (this.worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord)) {
                    this.currentPath.setCurrentPathLength(var1 - 1);
                    return;
                }
            }
        }
    }
    
    private boolean isDirectPathBetweenPoints(final Vec3 par1Vec3, final Vec3 par2Vec3, int par3, final int par4, int par5) {
        int var6 = MathHelper.floor_double(par1Vec3.xCoord);
        int var7 = MathHelper.floor_double(par1Vec3.zCoord);
        double var8 = par2Vec3.xCoord - par1Vec3.xCoord;
        double var9 = par2Vec3.zCoord - par1Vec3.zCoord;
        final double var10 = var8 * var8 + var9 * var9;
        if (var10 < 1.0E-8) {
            return false;
        }
        final double var11 = 1.0 / Math.sqrt(var10);
        var8 *= var11;
        var9 *= var11;
        par3 += 2;
        par5 += 2;
        if (!this.isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3, par4, par5, par1Vec3, var8, var9)) {
            return false;
        }
        par3 -= 2;
        par5 -= 2;
        final double var12 = 1.0 / Math.abs(var8);
        final double var13 = 1.0 / Math.abs(var9);
        double var14 = var6 * 1 - par1Vec3.xCoord;
        double var15 = var7 * 1 - par1Vec3.zCoord;
        if (var8 >= 0.0) {
            ++var14;
        }
        if (var9 >= 0.0) {
            ++var15;
        }
        var14 /= var8;
        var15 /= var9;
        final int var16 = (var8 < 0.0) ? -1 : 1;
        final int var17 = (var9 < 0.0) ? -1 : 1;
        final int var18 = MathHelper.floor_double(par2Vec3.xCoord);
        final int var19 = MathHelper.floor_double(par2Vec3.zCoord);
        int var20 = var18 - var6;
        int var21 = var19 - var7;
        while (var20 * var16 > 0 || var21 * var17 > 0) {
            if (var14 < var15) {
                var14 += var12;
                var6 += var16;
                var20 = var18 - var6;
            }
            else {
                var15 += var13;
                var7 += var17;
                var21 = var19 - var7;
            }
            if (!this.isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3, par4, par5, par1Vec3, var8, var9)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSafeToStandAt(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6, final Vec3 par7Vec3, final double par8, final double par10) {
        final int var12 = par1 - par4 / 2;
        final int var13 = par3 - par6 / 2;
        if (!this.isPositionClear(var12, par2, var13, par4, par5, par6, par7Vec3, par8, par10)) {
            return false;
        }
        for (int var14 = var12; var14 < var12 + par4; ++var14) {
            for (int var15 = var13; var15 < var13 + par6; ++var15) {
                final double var16 = var14 + 0.5 - par7Vec3.xCoord;
                final double var17 = var15 + 0.5 - par7Vec3.zCoord;
                if (var16 * par8 + var17 * par10 >= 0.0) {
                    final int var18 = this.worldObj.getBlockId(var14, par2 - 1, var15);
                    if (var18 <= 0) {
                        return false;
                    }
                    final Material var19 = Block.blocksList[var18].blockMaterial;
                    if (var19 == Material.water && !this.theEntity.isInWater()) {
                        return false;
                    }
                    if (var19 == Material.lava) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isPositionClear(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6, final Vec3 par7Vec3, final double par8, final double par10) {
        for (int var12 = par1; var12 < par1 + par4; ++var12) {
            for (int var13 = par2; var13 < par2 + par5; ++var13) {
                for (int var14 = par3; var14 < par3 + par6; ++var14) {
                    final double var15 = var12 + 0.5 - par7Vec3.xCoord;
                    final double var16 = var14 + 0.5 - par7Vec3.zCoord;
                    if (var15 * par8 + var16 * par10 >= 0.0) {
                        final int var17 = this.worldObj.getBlockId(var12, var13, var14);
                        if (var17 > 0 && !Block.blocksList[var17].getBlocksMovement(this.worldObj, var12, var13, var14)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
