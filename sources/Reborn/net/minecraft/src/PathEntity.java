package net.minecraft.src;

public class PathEntity
{
    private final PathPoint[] points;
    private int currentPathIndex;
    private int pathLength;
    
    public PathEntity(final PathPoint[] par1ArrayOfPathPoint) {
        this.points = par1ArrayOfPathPoint;
        this.pathLength = par1ArrayOfPathPoint.length;
    }
    
    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }
    
    public boolean isFinished() {
        return this.currentPathIndex >= this.pathLength;
    }
    
    public PathPoint getFinalPathPoint() {
        return (this.pathLength > 0) ? this.points[this.pathLength - 1] : null;
    }
    
    public PathPoint getPathPointFromIndex(final int par1) {
        return this.points[par1];
    }
    
    public int getCurrentPathLength() {
        return this.pathLength;
    }
    
    public void setCurrentPathLength(final int par1) {
        this.pathLength = par1;
    }
    
    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }
    
    public void setCurrentPathIndex(final int par1) {
        this.currentPathIndex = par1;
    }
    
    public Vec3 getVectorFromIndex(final Entity par1Entity, final int par2) {
        final double var3 = this.points[par2].xCoord + (int)(par1Entity.width + 1.0f) * 0.5;
        final double var4 = this.points[par2].yCoord;
        final double var5 = this.points[par2].zCoord + (int)(par1Entity.width + 1.0f) * 0.5;
        return par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(var3, var4, var5);
    }
    
    public Vec3 getPosition(final Entity par1Entity) {
        return this.getVectorFromIndex(par1Entity, this.currentPathIndex);
    }
    
    public boolean isSamePath(final PathEntity par1PathEntity) {
        if (par1PathEntity == null) {
            return false;
        }
        if (par1PathEntity.points.length != this.points.length) {
            return false;
        }
        for (int var2 = 0; var2 < this.points.length; ++var2) {
            if (this.points[var2].xCoord != par1PathEntity.points[var2].xCoord || this.points[var2].yCoord != par1PathEntity.points[var2].yCoord || this.points[var2].zCoord != par1PathEntity.points[var2].zCoord) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isDestinationSame(final Vec3 par1Vec3) {
        final PathPoint var2 = this.getFinalPathPoint();
        return var2 != null && (var2.xCoord == (int)par1Vec3.xCoord && var2.zCoord == (int)par1Vec3.zCoord);
    }
}
