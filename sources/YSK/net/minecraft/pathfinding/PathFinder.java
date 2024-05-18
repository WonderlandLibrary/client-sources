package net.minecraft.pathfinding;

import net.minecraft.world.pathfinder.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathFinder
{
    private NodeProcessor nodeProcessor;
    private PathPoint[] pathOptions;
    private Path path;
    
    private PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final double n, final double n2, final double n3, final float n4) {
        this.path.clearPath();
        this.nodeProcessor.initProcessor(blockAccess, entity);
        final PathEntity addToPath = this.addToPath(entity, this.nodeProcessor.getPathPointTo(entity), this.nodeProcessor.getPathPointToCoords(entity, n, n2, n3), n4);
        this.nodeProcessor.postProcess();
        return addToPath;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final Entity entity2, final float n) {
        return this.createEntityPathTo(blockAccess, entity, entity2.posX, entity2.getEntityBoundingBox().minY, entity2.posZ, n);
    }
    
    public PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final BlockPos blockPos, final float n) {
        return this.createEntityPathTo(blockAccess, entity, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, n);
    }
    
    public PathFinder(final NodeProcessor nodeProcessor) {
        this.path = new Path();
        this.pathOptions = new PathPoint[0xB3 ^ 0x93];
        this.nodeProcessor = nodeProcessor;
    }
    
    private PathEntity createEntityPath(final PathPoint pathPoint, final PathPoint pathPoint2) {
        int length = " ".length();
        PathPoint previous = pathPoint2;
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (previous.previous != null) {
            ++length;
            previous = previous.previous;
        }
        final PathPoint[] array = new PathPoint[length];
        PathPoint previous2 = pathPoint2;
        --length;
        array[length] = pathPoint2;
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (previous2.previous != null) {
            previous2 = previous2.previous;
            --length;
            array[length] = previous2;
        }
        return new PathEntity(array);
    }
    
    private PathEntity addToPath(final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        pathPoint.totalPathDistance = 0.0f;
        pathPoint.distanceToNext = pathPoint.distanceToSquared(pathPoint2);
        pathPoint.distanceToTarget = pathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(pathPoint);
        PathPoint pathPoint3 = pathPoint;
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (!this.path.isPathEmpty()) {
            final PathPoint dequeue = this.path.dequeue();
            if (dequeue.equals(pathPoint2)) {
                return this.createEntityPath(pathPoint, pathPoint2);
            }
            if (dequeue.distanceToSquared(pathPoint2) < pathPoint3.distanceToSquared(pathPoint2)) {
                pathPoint3 = dequeue;
            }
            dequeue.visited = (" ".length() != 0);
            final int pathOptions = this.nodeProcessor.findPathOptions(this.pathOptions, entity, dequeue, pathPoint2, n);
            int i = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (i < pathOptions) {
                final PathPoint pathPoint4 = this.pathOptions[i];
                final float totalPathDistance = dequeue.totalPathDistance + dequeue.distanceToSquared(pathPoint4);
                if (totalPathDistance < n * 2.0f && (!pathPoint4.isAssigned() || totalPathDistance < pathPoint4.totalPathDistance)) {
                    pathPoint4.previous = dequeue;
                    pathPoint4.totalPathDistance = totalPathDistance;
                    pathPoint4.distanceToNext = pathPoint4.distanceToSquared(pathPoint2);
                    if (pathPoint4.isAssigned()) {
                        this.path.changeDistance(pathPoint4, pathPoint4.totalPathDistance + pathPoint4.distanceToNext);
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        pathPoint4.distanceToTarget = pathPoint4.totalPathDistance + pathPoint4.distanceToNext;
                        this.path.addPoint(pathPoint4);
                    }
                }
                ++i;
            }
        }
        if (pathPoint3 == pathPoint) {
            return null;
        }
        return this.createEntityPath(pathPoint, pathPoint3);
    }
}
