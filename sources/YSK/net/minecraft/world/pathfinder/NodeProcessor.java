package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class NodeProcessor
{
    protected int entitySizeX;
    protected int entitySizeZ;
    protected int entitySizeY;
    protected IBlockAccess blockaccess;
    protected IntHashMap<PathPoint> pointMap;
    
    public void postProcess() {
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NodeProcessor() {
        this.pointMap = new IntHashMap<PathPoint>();
    }
    
    public abstract int findPathOptions(final PathPoint[] p0, final Entity p1, final PathPoint p2, final PathPoint p3, final float p4);
    
    protected PathPoint openPoint(final int n, final int n2, final int n3) {
        final int hash = PathPoint.makeHash(n, n2, n3);
        PathPoint pathPoint = this.pointMap.lookup(hash);
        if (pathPoint == null) {
            pathPoint = new PathPoint(n, n2, n3);
            this.pointMap.addKey(hash, pathPoint);
        }
        return pathPoint;
    }
    
    public abstract PathPoint getPathPointTo(final Entity p0);
    
    public abstract PathPoint getPathPointToCoords(final Entity p0, final double p1, final double p2, final double p3);
    
    public void initProcessor(final IBlockAccess blockaccess, final Entity entity) {
        this.blockaccess = blockaccess;
        this.pointMap.clearMap();
        this.entitySizeX = MathHelper.floor_float(entity.width + 1.0f);
        this.entitySizeY = MathHelper.floor_float(entity.height + 1.0f);
        this.entitySizeZ = MathHelper.floor_float(entity.width + 1.0f);
    }
}
