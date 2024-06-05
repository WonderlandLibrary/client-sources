package net.minecraft.src;

public class Path
{
    private PathPoint[] pathPoints;
    private int count;
    
    public Path() {
        this.pathPoints = new PathPoint[1024];
        this.count = 0;
    }
    
    public PathPoint addPoint(final PathPoint par1PathPoint) {
        if (par1PathPoint.index >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.count == this.pathPoints.length) {
            final PathPoint[] var2 = new PathPoint[this.count << 1];
            System.arraycopy(this.pathPoints, 0, var2, 0, this.count);
            this.pathPoints = var2;
        }
        this.pathPoints[this.count] = par1PathPoint;
        par1PathPoint.index = this.count;
        this.sortBack(this.count++);
        return par1PathPoint;
    }
    
    public void clearPath() {
        this.count = 0;
    }
    
    public PathPoint dequeue() {
        final PathPoint var1 = this.pathPoints[0];
        final PathPoint[] pathPoints = this.pathPoints;
        final int n = 0;
        final PathPoint[] pathPoints2 = this.pathPoints;
        final int count = this.count - 1;
        this.count = count;
        pathPoints[n] = pathPoints2[count];
        this.pathPoints[this.count] = null;
        if (this.count > 0) {
            this.sortForward(0);
        }
        var1.index = -1;
        return var1;
    }
    
    public void changeDistance(final PathPoint par1PathPoint, final float par2) {
        final float var3 = par1PathPoint.distanceToTarget;
        par1PathPoint.distanceToTarget = par2;
        if (par2 < var3) {
            this.sortBack(par1PathPoint.index);
        }
        else {
            this.sortForward(par1PathPoint.index);
        }
    }
    
    private void sortBack(int par1) {
        final PathPoint var2 = this.pathPoints[par1];
        final float var3 = var2.distanceToTarget;
        while (par1 > 0) {
            final int var4 = par1 - 1 >> 1;
            final PathPoint var5 = this.pathPoints[var4];
            if (var3 >= var5.distanceToTarget) {
                break;
            }
            this.pathPoints[par1] = var5;
            var5.index = par1;
            par1 = var4;
        }
        this.pathPoints[par1] = var2;
        var2.index = par1;
    }
    
    private void sortForward(int par1) {
        final PathPoint var2 = this.pathPoints[par1];
        final float var3 = var2.distanceToTarget;
        while (true) {
            final int var4 = 1 + (par1 << 1);
            final int var5 = var4 + 1;
            if (var4 >= this.count) {
                break;
            }
            final PathPoint var6 = this.pathPoints[var4];
            final float var7 = var6.distanceToTarget;
            PathPoint var8;
            float var9;
            if (var5 >= this.count) {
                var8 = null;
                var9 = Float.POSITIVE_INFINITY;
            }
            else {
                var8 = this.pathPoints[var5];
                var9 = var8.distanceToTarget;
            }
            if (var7 < var9) {
                if (var7 >= var3) {
                    break;
                }
                this.pathPoints[par1] = var6;
                var6.index = par1;
                par1 = var4;
            }
            else {
                if (var9 >= var3) {
                    break;
                }
                this.pathPoints[par1] = var8;
                var8.index = par1;
                par1 = var5;
            }
        }
        this.pathPoints[par1] = var2;
        var2.index = par1;
    }
    
    public boolean isPathEmpty() {
        return this.count == 0;
    }
}
