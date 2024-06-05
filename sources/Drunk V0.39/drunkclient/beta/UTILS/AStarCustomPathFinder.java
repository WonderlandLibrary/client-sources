/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS;

import drunkclient.beta.UTILS.Vec3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class AStarCustomPathFinder {
    private Vec3 startVec3;
    private Vec3 endVec3;
    private ArrayList<Vec3> path = new ArrayList();
    private ArrayList<Hub> hubs = new ArrayList();
    private ArrayList<Hub> hubsToWork = new ArrayList();
    private double minDistanceSquared = 9.0;
    private boolean nearest = true;
    private static final Vec3[] flatCardinalDirections = new Vec3[]{new Vec3(1.0, 0.0, 0.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 0.0, -1.0)};

    public AStarCustomPathFinder(Vec3 startVec3, Vec3 endVec3) {
        this.startVec3 = startVec3.addVector(0.0, 0.0, 0.0).floor();
        this.endVec3 = endVec3.addVector(0.0, 0.0, 0.0).floor();
    }

    public ArrayList<Vec3> getPath() {
        return this.path;
    }

    public void compute() {
        this.compute(1000, 4);
    }

    public void compute(int loops, int depth) {
        this.path.clear();
        this.hubsToWork.clear();
        ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(this.startVec3);
        this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.squareDistanceTo(this.endVec3), 0.0, 0.0));
        block0: for (int i = 0; i < loops; ++i) {
            Vec3 loc2;
            Hub hub;
            Vec3 loc1;
            this.hubsToWork.sort(new CompareHub());
            int j = 0;
            if (this.hubsToWork.size() == 0) break;
            Iterator<Hub> iterator = new ArrayList<Hub>(this.hubsToWork).iterator();
            do {
                if (!iterator.hasNext()) continue block0;
                hub = iterator.next();
                if (++j > depth) continue block0;
                this.hubsToWork.remove(hub);
                this.hubs.add(hub);
                for (Vec3 direction : flatCardinalDirections) {
                    Vec3 loc = hub.getLoc().add(direction);
                    if (AStarCustomPathFinder.checkPositionValidity(loc, false) && this.addHub(hub, loc, 0.0)) break block0;
                }
            } while ((!AStarCustomPathFinder.checkPositionValidity(loc1 = hub.getLoc().addVector(0.0, 1.0, 0.0), false) || !this.addHub(hub, loc1, 0.0)) && (!AStarCustomPathFinder.checkPositionValidity(loc2 = hub.getLoc().addVector(0.0, -1.0, 0.0), false) || !this.addHub(hub, loc2, 0.0)));
        }
        if (!this.nearest) return;
        this.hubs.sort(new CompareHub());
        this.path = this.hubs.get(0).getPath();
    }

    public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
        return AStarCustomPathFinder.checkPositionValidity((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), checkGround);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        if (AStarCustomPathFinder.isBlockSolid(block1)) return false;
        if (AStarCustomPathFinder.isBlockSolid(block2)) return false;
        if (!AStarCustomPathFinder.isBlockSolid(block3)) {
            if (checkGround) return false;
        }
        if (!AStarCustomPathFinder.isSafeToWalkOn(block3)) return false;
        return true;
    }

    private static boolean isBlockSolid(BlockPos blockPos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
        if (block == null) {
            return false;
        }
        if (block.isFullBlock()) return true;
        if (block instanceof BlockSlab) return true;
        if (block instanceof BlockStairs) return true;
        if (block instanceof BlockCactus) return true;
        if (block instanceof BlockChest) return true;
        if (block instanceof BlockEnderChest) return true;
        if (block instanceof BlockSkull) return true;
        if (block instanceof BlockPane) return true;
        if (block instanceof BlockFence) return true;
        if (block instanceof BlockWall) return true;
        if (block instanceof BlockGlass) return true;
        if (block instanceof BlockPistonBase) return true;
        if (block instanceof BlockPistonExtension) return true;
        if (block instanceof BlockPistonMoving) return true;
        if (block instanceof BlockStainedGlass) return true;
        if (block instanceof BlockTrapDoor) return true;
        return false;
    }

    private static boolean isSafeToWalkOn(BlockPos blockPos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
        if (block == null) {
            return false;
        }
        if (block instanceof BlockFence) return false;
        if (block instanceof BlockWall) return false;
        return true;
    }

    public Hub isHubExisting(Vec3 loc) {
        Hub hub2;
        for (Hub hub2 : this.hubs) {
            if (hub2.getLoc().getX() != loc.getX() || hub2.getLoc().getY() != loc.getY() || hub2.getLoc().getZ() != loc.getZ()) continue;
            return hub2;
        }
        Iterator<Hub> iterator = this.hubsToWork.iterator();
        do {
            if (!iterator.hasNext()) return null;
        } while ((hub2 = iterator.next()).getLoc().getX() != loc.getX() || hub2.getLoc().getY() != loc.getY() || hub2.getLoc().getZ() != loc.getZ());
        return hub2;
    }

    public boolean addHub(Hub parent, Vec3 loc, double cost) {
        Hub existingHub = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub != null) {
            if (!(existingHub.getCost() > cost)) return false;
            ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(this.endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
            return false;
        }
        if (loc.getX() == this.endVec3.getX() && loc.getY() == this.endVec3.getY() && loc.getZ() == this.endVec3.getZ() || this.minDistanceSquared != 0.0 && loc.squareDistanceTo(this.endVec3) <= this.minDistanceSquared) {
            this.path.clear();
            this.path = parent.getPath();
            this.path.add(loc);
            return true;
        }
        ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
        path.add(loc);
        this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(this.endVec3), cost, totalCost));
        return false;
    }

    public class CompareHub
    implements Comparator<Hub> {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }

    private class Hub {
        private Vec3 loc = null;
        private Hub parent = null;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;

        public Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Vec3 getLoc() {
            return this.loc;
        }

        public Hub getParent() {
            return this.parent;
        }

        public ArrayList<Vec3> getPath() {
            return this.path;
        }

        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }

        public double getCost() {
            return this.cost;
        }

        public void setLoc(Vec3 loc) {
            this.loc = loc;
        }

        public void setParent(Hub parent) {
            this.parent = parent;
        }

        public void setPath(ArrayList<Vec3> path) {
            this.path = path;
        }

        public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getTotalCost() {
            return this.totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }
    }
}

