package net.minecraft.src;

public class PathFinder
{
    private IBlockAccess worldMap;
    private Path path;
    private IntHashMap pointMap;
    private PathPoint[] pathOptions;
    private boolean isWoddenDoorAllowed;
    private boolean isMovementBlockAllowed;
    private boolean isPathingInWater;
    private boolean canEntityDrown;
    
    public PathFinder(final IBlockAccess par1IBlockAccess, final boolean par2, final boolean par3, final boolean par4, final boolean par5) {
        this.path = new Path();
        this.pointMap = new IntHashMap();
        this.pathOptions = new PathPoint[32];
        this.worldMap = par1IBlockAccess;
        this.isWoddenDoorAllowed = par2;
        this.isMovementBlockAllowed = par3;
        this.isPathingInWater = par4;
        this.canEntityDrown = par5;
    }
    
    public PathEntity createEntityPathTo(final Entity par1Entity, final Entity par2Entity, final float par3) {
        return this.createEntityPathTo(par1Entity, par2Entity.posX, par2Entity.boundingBox.minY, par2Entity.posZ, par3);
    }
    
    public PathEntity createEntityPathTo(final Entity par1Entity, final int par2, final int par3, final int par4, final float par5) {
        return this.createEntityPathTo(par1Entity, par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, par5);
    }
    
    private PathEntity createEntityPathTo(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8) {
        this.path.clearPath();
        this.pointMap.clearMap();
        boolean var9 = this.isPathingInWater;
        int var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5);
        if (this.canEntityDrown && par1Entity.isInWater()) {
            var10 = (int)par1Entity.boundingBox.minY;
            for (int var11 = this.worldMap.getBlockId(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ)); var11 == Block.waterMoving.blockID || var11 == Block.waterStill.blockID; var11 = this.worldMap.getBlockId(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ))) {
                ++var10;
            }
            var9 = this.isPathingInWater;
            this.isPathingInWater = false;
        }
        else {
            var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5);
        }
        final PathPoint var12 = this.openPoint(MathHelper.floor_double(par1Entity.boundingBox.minX), var10, MathHelper.floor_double(par1Entity.boundingBox.minZ));
        final PathPoint var13 = this.openPoint(MathHelper.floor_double(par2 - par1Entity.width / 2.0f), MathHelper.floor_double(par4), MathHelper.floor_double(par6 - par1Entity.width / 2.0f));
        final PathPoint var14 = new PathPoint(MathHelper.floor_float(par1Entity.width + 1.0f), MathHelper.floor_float(par1Entity.height + 1.0f), MathHelper.floor_float(par1Entity.width + 1.0f));
        final PathEntity var15 = this.addToPath(par1Entity, var12, var13, var14, par8);
        this.isPathingInWater = var9;
        return var15;
    }
    
    private PathEntity addToPath(final Entity par1Entity, final PathPoint par2PathPoint, final PathPoint par3PathPoint, final PathPoint par4PathPoint, final float par5) {
        par2PathPoint.totalPathDistance = 0.0f;
        par2PathPoint.distanceToNext = par2PathPoint.func_75832_b(par3PathPoint);
        par2PathPoint.distanceToTarget = par2PathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(par2PathPoint);
        PathPoint var6 = par2PathPoint;
        while (!this.path.isPathEmpty()) {
            final PathPoint var7 = this.path.dequeue();
            if (var7.equals(par3PathPoint)) {
                return this.createEntityPath(par2PathPoint, par3PathPoint);
            }
            if (var7.func_75832_b(par3PathPoint) < var6.func_75832_b(par3PathPoint)) {
                var6 = var7;
            }
            var7.isFirst = true;
            for (int var8 = this.findPathOptions(par1Entity, var7, par4PathPoint, par3PathPoint, par5), var9 = 0; var9 < var8; ++var9) {
                final PathPoint var10 = this.pathOptions[var9];
                final float var11 = var7.totalPathDistance + var7.func_75832_b(var10);
                if (!var10.isAssigned() || var11 < var10.totalPathDistance) {
                    var10.previous = var7;
                    var10.totalPathDistance = var11;
                    var10.distanceToNext = var10.func_75832_b(par3PathPoint);
                    if (var10.isAssigned()) {
                        this.path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
                    }
                    else {
                        var10.distanceToTarget = var10.totalPathDistance + var10.distanceToNext;
                        this.path.addPoint(var10);
                    }
                }
            }
        }
        if (var6 == par2PathPoint) {
            return null;
        }
        return this.createEntityPath(par2PathPoint, var6);
    }
    
    private int findPathOptions(final Entity par1Entity, final PathPoint par2PathPoint, final PathPoint par3PathPoint, final PathPoint par4PathPoint, final float par5) {
        int var6 = 0;
        byte var7 = 0;
        if (this.getVerticalOffset(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord + 1, par2PathPoint.zCoord, par3PathPoint) == 1) {
            var7 = 1;
        }
        final PathPoint var8 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord + 1, par3PathPoint, var7);
        final PathPoint var9 = this.getSafePoint(par1Entity, par2PathPoint.xCoord - 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
        final PathPoint var10 = this.getSafePoint(par1Entity, par2PathPoint.xCoord + 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
        final PathPoint var11 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord - 1, par3PathPoint, var7);
        if (var8 != null && !var8.isFirst && var8.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var8;
        }
        if (var9 != null && !var9.isFirst && var9.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var9;
        }
        if (var10 != null && !var10.isFirst && var10.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var10;
        }
        if (var11 != null && !var11.isFirst && var11.distanceTo(par4PathPoint) < par5) {
            this.pathOptions[var6++] = var11;
        }
        return var6;
    }
    
    private PathPoint getSafePoint(final Entity par1Entity, final int par2, int par3, final int par4, final PathPoint par5PathPoint, final int par6) {
        PathPoint var7 = null;
        final int var8 = this.getVerticalOffset(par1Entity, par2, par3, par4, par5PathPoint);
        if (var8 == 2) {
            return this.openPoint(par2, par3, par4);
        }
        if (var8 == 1) {
            var7 = this.openPoint(par2, par3, par4);
        }
        if (var7 == null && par6 > 0 && var8 != -3 && var8 != -4 && this.getVerticalOffset(par1Entity, par2, par3 + par6, par4, par5PathPoint) == 1) {
            var7 = this.openPoint(par2, par3 + par6, par4);
            par3 += par6;
        }
        if (var7 != null) {
            int var9 = 0;
            int var10 = 0;
            while (par3 > 0) {
                var10 = this.getVerticalOffset(par1Entity, par2, par3 - 1, par4, par5PathPoint);
                if (this.isPathingInWater && var10 == -1) {
                    return null;
                }
                if (var10 != 1) {
                    break;
                }
                if (var9++ >= par1Entity.func_82143_as()) {
                    return null;
                }
                if (--par3 <= 0) {
                    continue;
                }
                var7 = this.openPoint(par2, par3, par4);
            }
            if (var10 == -2) {
                return null;
            }
        }
        return var7;
    }
    
    private final PathPoint openPoint(final int par1, final int par2, final int par3) {
        final int var4 = PathPoint.makeHash(par1, par2, par3);
        PathPoint var5 = (PathPoint)this.pointMap.lookup(var4);
        if (var5 == null) {
            var5 = new PathPoint(par1, par2, par3);
            this.pointMap.addKey(var4, var5);
        }
        return var5;
    }
    
    public int getVerticalOffset(final Entity par1Entity, final int par2, final int par3, final int par4, final PathPoint par5PathPoint) {
        return func_82565_a(par1Entity, par2, par3, par4, par5PathPoint, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
    }
    
    public static int func_82565_a(final Entity par0Entity, final int par1, final int par2, final int par3, final PathPoint par4PathPoint, final boolean par5, final boolean par6, final boolean par7) {
        boolean var8 = false;
        for (int var9 = par1; var9 < par1 + par4PathPoint.xCoord; ++var9) {
            for (int var10 = par2; var10 < par2 + par4PathPoint.yCoord; ++var10) {
                for (int var11 = par3; var11 < par3 + par4PathPoint.zCoord; ++var11) {
                    final int var12 = par0Entity.worldObj.getBlockId(var9, var10, var11);
                    if (var12 > 0) {
                        if (var12 == Block.trapdoor.blockID) {
                            var8 = true;
                        }
                        else if (var12 != Block.waterMoving.blockID && var12 != Block.waterStill.blockID) {
                            if (!par7 && var12 == Block.doorWood.blockID) {
                                return 0;
                            }
                        }
                        else {
                            if (par5) {
                                return -1;
                            }
                            var8 = true;
                        }
                        final Block var13 = Block.blocksList[var12];
                        final int var14 = var13.getRenderType();
                        if (par0Entity.worldObj.blockGetRenderType(var9, var10, var11) == 9) {
                            final int var15 = MathHelper.floor_double(par0Entity.posX);
                            final int var16 = MathHelper.floor_double(par0Entity.posY);
                            final int var17 = MathHelper.floor_double(par0Entity.posZ);
                            if (par0Entity.worldObj.blockGetRenderType(var15, var16, var17) != 9 && par0Entity.worldObj.blockGetRenderType(var15, var16 - 1, var17) != 9) {
                                return -3;
                            }
                        }
                        else if (!var13.getBlocksMovement(par0Entity.worldObj, var9, var10, var11) && (!par6 || var12 != Block.doorWood.blockID)) {
                            if (var14 == 11 || var12 == Block.fenceGate.blockID || var14 == 32) {
                                return -3;
                            }
                            if (var12 == Block.trapdoor.blockID) {
                                return -4;
                            }
                            final Material var18 = var13.blockMaterial;
                            if (var18 != Material.lava) {
                                return 0;
                            }
                            if (!par0Entity.handleLavaMovement()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return var8 ? 2 : 1;
    }
    
    private PathEntity createEntityPath(final PathPoint par1PathPoint, final PathPoint par2PathPoint) {
        int var3 = 1;
        for (PathPoint var4 = par2PathPoint; var4.previous != null; var4 = var4.previous) {
            ++var3;
        }
        final PathPoint[] var5 = new PathPoint[var3];
        PathPoint var4 = par2PathPoint;
        --var3;
        var5[var3] = par2PathPoint;
        while (var4.previous != null) {
            var4 = var4.previous;
            --var3;
            var5[var3] = var4;
        }
        return new PathEntity(var5);
    }
}
