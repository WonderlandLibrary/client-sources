// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import java.util.Iterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import net.minecraft.util.Vec3;

public class PathFinder
{
    public static Vec3 startPos;
    public static Vec3 endPos;
    private ArrayList<Vec3> path;
    private ArrayList<Node> nodes;
    private ArrayList<Node> activeNodes;
    private boolean nearest;
    private static Vec3[] coords;
    
    public PathFinder(final Vec3 startPos, final Vec3 endPos) {
        this.path = new ArrayList<Vec3>();
        this.nodes = new ArrayList<Node>();
        this.activeNodes = new ArrayList<Node>();
        this.nearest = true;
        PathFinder.startPos = startPos;
        PathFinder.endPos = endPos;
    }
    
    public ArrayList<Vec3> getPath() {
        return this.path;
    }
    
    public void calculatePath(final int iterations) {
        this.path.clear();
        this.activeNodes.clear();
        final ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(PathFinder.startPos);
        this.activeNodes.add(new Node(PathFinder.startPos, null, initPath, PathFinder.startPos.squareDistanceTo(PathFinder.endPos), 0.0, 0.0));
    Label_0220:
        for (int i = 0; i < iterations; ++i) {
            Collections.sort(this.activeNodes, new CompareNode());
            final Iterator<Node> iterator = new ArrayList<Node>(this.activeNodes).iterator();
            if (iterator.hasNext()) {
                final Node node = iterator.next();
                this.activeNodes.remove(node);
                this.nodes.add(node);
                for (final Vec3 direction : PathFinder.coords) {
                    final Vec3 loc = node.getLoc().add(direction);
                    if (checkPositionValidity(loc, false) && this.addHub(node, loc, 0.0)) {
                        break Label_0220;
                    }
                }
            }
        }
        Collections.sort(this.nodes, new CompareNode());
        this.path = this.nodes.get(0).getPath();
    }
    
    public static boolean checkPositionValidity(final Vec3 loc, final boolean checkGround) {
        return checkPositionValidity((int)loc.xCoord, (int)loc.yCoord, (int)loc.zCoord, checkGround);
    }
    
    public static boolean checkPositionValidity(final int x, final int y, final int z, final boolean checkGround) {
        final BlockPos block1 = new BlockPos(x, y, z);
        final BlockPos block2 = new BlockPos(x, y + 1, z);
        final BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3) && block1.getBlock() == Blocks.air;
    }
    
    private static boolean isBlockSolid(final BlockPos block) {
        return block.getBlock().isFullBlock() || block.getBlock() instanceof BlockSlab || block.getBlock() instanceof BlockStairs || block.getBlock() instanceof BlockCactus || block.getBlock() instanceof BlockChest || block.getBlock() instanceof BlockEnderChest || block.getBlock() instanceof BlockSkull || block.getBlock() instanceof BlockPane || block.getBlock() instanceof BlockFence || block.getBlock() instanceof BlockWall || block.getBlock() instanceof BlockGlass || block.getBlock() instanceof BlockPistonBase || block.getBlock() instanceof BlockPistonExtension || block.getBlock() instanceof BlockPistonMoving || block.getBlock() instanceof BlockStainedGlass || block.getBlock() instanceof BlockTrapDoor || !(block.getBlock() instanceof BlockAir);
    }
    
    private static boolean isSafeToWalkOn(final BlockPos block) {
        return !(Minecraft.getMinecraft().theWorld.getBlockState(block) instanceof BlockFence) && !(Minecraft.getMinecraft().theWorld.getBlockState(block) instanceof BlockWall);
    }
    
    public Node isHubExisting(final Vec3 loc) {
        for (final Node hub : this.nodes) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        for (final Node hub : this.activeNodes) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        return null;
    }
    
    public boolean addHub(final Node parent, final Vec3 loc, final double cost) {
        final Node existingNode = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getFCost();
        }
        if (existingNode == null) {
            if ((loc.xCoord == PathFinder.endPos.xCoord && loc.yCoord == PathFinder.endPos.yCoord && loc.zCoord == PathFinder.endPos.zCoord) || loc.squareDistanceTo(PathFinder.endPos) <= 3.0) {
                this.path.clear();
                (this.path = parent.getPath()).add(loc);
                return true;
            }
            final ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            this.activeNodes.add(new Node(loc, parent, path, loc.squareDistanceTo(PathFinder.endPos), cost, totalCost));
        }
        return false;
    }
    
    static {
        PathFinder.coords = new Vec3[] { new Vec3(1.0, 0.0, 0.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 0.0, -1.0), new Vec3(0.0, 1.0, 0.0), new Vec3(0.0, -1.0, 0.0) };
    }
    
    class Node
    {
        Vec3 loc;
        Node parent;
        ArrayList<Vec3> path;
        double squareDistanceToFromTarget;
        double hCost;
        double fCost;
        
        public Node(final Vec3 loc, final Node parent, final ArrayList<Vec3> path, final double squareDistanceToFromTarget, final double cost, final double totalCost) {
            this.loc = null;
            this.parent = null;
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.hCost = cost;
            this.fCost = totalCost;
        }
        
        public Vec3 getLoc() {
            return this.loc;
        }
        
        public Node getParent() {
            return this.parent;
        }
        
        public ArrayList<Vec3> getPath() {
            return this.path;
        }
        
        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }
        
        public double getHCost() {
            return this.hCost;
        }
        
        public void setLoc(final Vec3 loc) {
            this.loc = loc;
        }
        
        public void setParent(final Node parent) {
            this.parent = parent;
        }
        
        public void setPath(final ArrayList<Vec3> path) {
            this.path = path;
        }
        
        public void setSquareDistanceToFromTarget(final double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }
        
        public void setHCost(final double hCost) {
            this.hCost = hCost;
        }
        
        public double getFCost() {
            return this.fCost;
        }
        
        public void setFCost(final double fCost) {
            this.fCost = fCost;
        }
    }
    
    public class CompareNode implements Comparator<Node>
    {
        @Override
        public int compare(final Node o1, final Node o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getFCost() - (o2.getSquareDistanceToFromTarget() + o2.getFCost()));
        }
    }
}
