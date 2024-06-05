package me.mees.remix.pathfinder;

import net.minecraft.util.*;

public class Node
{
    public Node parent;
    private boolean walkable;
    private BlockPos blockPos;
    
    public Node(final boolean walkable, final BlockPos blockPos) {
        this.walkable = walkable;
        this.blockPos = blockPos;
    }
    
    public double getG_Cost(final Node startNode) {
        return this.distance(this.blockPos, startNode.getBlockpos());
    }
    
    public double getH_Cost(final Node endNode) {
        return this.distance(this.blockPos, endNode.getBlockpos());
    }
    
    public double getF_Cost(final Node startNode, final Node endNode) {
        return this.getG_Cost(startNode) + this.getH_Cost(endNode);
    }
    
    public BlockPos getBlockpos() {
        return this.blockPos;
    }
    
    public boolean isWalkable() {
        return this.walkable;
    }
    
    public double distance(final BlockPos b1, final BlockPos b2) {
        final float f = (float)(b1.getX() - b2.getX());
        final float f2 = (float)(b1.getY() - b2.getY());
        final float f3 = (float)(b1.getZ() - b2.getZ());
        return MathHelper.sqrt_float(f * f + f2 * f2 + f3 * f3);
    }
}
