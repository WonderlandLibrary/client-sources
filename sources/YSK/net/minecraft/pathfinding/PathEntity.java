package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathEntity
{
    private int currentPathIndex;
    private final PathPoint[] points;
    private int pathLength;
    
    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }
    
    public PathEntity(final PathPoint[] points) {
        this.points = points;
        this.pathLength = points.length;
    }
    
    public boolean isSamePath(final PathEntity pathEntity) {
        if (pathEntity == null) {
            return "".length() != 0;
        }
        if (pathEntity.points.length != this.points.length) {
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.points.length) {
            if (this.points[i].xCoord != pathEntity.points[i].xCoord || this.points[i].yCoord != pathEntity.points[i].yCoord || this.points[i].zCoord != pathEntity.points[i].zCoord) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public int getCurrentPathLength() {
        return this.pathLength;
    }
    
    public void setCurrentPathLength(final int pathLength) {
        this.pathLength = pathLength;
    }
    
    public PathPoint getFinalPathPoint() {
        PathPoint pathPoint;
        if (this.pathLength > 0) {
            pathPoint = this.points[this.pathLength - " ".length()];
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            pathPoint = null;
        }
        return pathPoint;
    }
    
    public boolean isFinished() {
        if (this.currentPathIndex >= this.pathLength) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setCurrentPathIndex(final int currentPathIndex) {
        this.currentPathIndex = currentPathIndex;
    }
    
    public Vec3 getPosition(final Entity entity) {
        return this.getVectorFromIndex(entity, this.currentPathIndex);
    }
    
    public Vec3 getVectorFromIndex(final Entity entity, final int n) {
        return new Vec3(this.points[n].xCoord + (int)(entity.width + 1.0f) * 0.5, this.points[n].yCoord, this.points[n].zCoord + (int)(entity.width + 1.0f) * 0.5);
    }
    
    public PathPoint getPathPointFromIndex(final int n) {
        return this.points[n];
    }
    
    public boolean isDestinationSame(final Vec3 vec3) {
        final PathPoint finalPathPoint = this.getFinalPathPoint();
        int n;
        if (finalPathPoint == null) {
            n = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (finalPathPoint.xCoord == (int)vec3.xCoord && finalPathPoint.zCoord == (int)vec3.zCoord) {
            n = " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void incrementPathIndex() {
        this.currentPathIndex += " ".length();
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
