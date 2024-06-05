package ClassSub;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.block.*;

public class Class171
{
    private Class148 startVec3;
    private Class148 endVec3;
    private ArrayList<Class148> path;
    private ArrayList<Class93> hubs;
    private ArrayList<Class93> hubsToWork;
    private double minDistanceSquared;
    private boolean nearest;
    private static Class148[] flatCardinalDirections;
    
    
    public Class171(final Class148 class148, final Class148 class149) {
        this.path = new ArrayList<Class148>();
        this.hubs = new ArrayList<Class93>();
        this.hubsToWork = new ArrayList<Class93>();
        this.minDistanceSquared = 9.0;
        this.nearest = true;
        this.startVec3 = class148.addVector(0.0, 0.0, 0.0).floor();
        this.endVec3 = class149.addVector(0.0, 0.0, 0.0).floor();
    }
    
    public ArrayList<Class148> getPath() {
        return this.path;
    }
    
    public void compute() {
        this.compute(1000, 4);
    }
    
    public void compute(final int n, final int n2) {
        this.path.clear();
        this.hubsToWork.clear();
        final ArrayList<Class148> list = new ArrayList<Class148>();
        list.add(this.startVec3);
        this.hubsToWork.add(new Class93(this.startVec3, null, list, this.startVec3.squareDistanceTo(this.endVec3), 0.0, 0.0));
    Label_0405:
        for (int i = 0; i < n; ++i) {
            Collections.sort(this.hubsToWork, new Class219());
            int n3 = 0;
            if (this.hubsToWork.size() == 0) {
                break;
            }
            for (final Class93 class93 : new ArrayList<Class93>(this.hubsToWork)) {
                if (++n3 > n2) {
                    break;
                }
                this.hubsToWork.remove(class93);
                this.hubs.add(class93);
                final Class148[] flatCardinalDirections = Class171.flatCardinalDirections;
                for (int length = flatCardinalDirections.length, j = 0; j < length; ++j) {
                    final Class148 floor = class93.getLoc().add(flatCardinalDirections[j]).floor();
                    if (checkPositionValidity(floor, false) && this.addHub(class93, floor, 0.0)) {
                        break Label_0405;
                    }
                }
                final Class148 floor2 = class93.getLoc().addVector(0.0, 1.0, 0.0).floor();
                if (checkPositionValidity(floor2, false) && this.addHub(class93, floor2, 0.0)) {
                    break Label_0405;
                }
                final Class148 floor3 = class93.getLoc().addVector(0.0, -1.0, 0.0).floor();
                if (checkPositionValidity(floor3, false) && this.addHub(class93, floor3, 0.0)) {
                    break Label_0405;
                }
            }
        }
        if (this.nearest) {
            Collections.sort(this.hubs, new Class219());
            this.path = this.hubs.get(0).getPath();
        }
    }
    
    public static boolean checkPositionValidity(final Class148 class148, final boolean b) {
        return checkPositionValidity((int)class148.getX(), (int)class148.getY(), (int)class148.getZ(), b);
    }
    
    public static boolean checkPositionValidity(final int n, final int n2, final int n3, final boolean b) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final BlockPos blockPos2 = new BlockPos(n, n2 + 1, n3);
        final BlockPos blockPos3 = new BlockPos(n, n2 - 1, n3);
        return !isBlockSolid(blockPos) && !isBlockSolid(blockPos2) && (isBlockSolid(blockPos3) || !b) && isSafeToWalkOn(blockPos3);
    }
    
    private static boolean isBlockSolid(final BlockPos blockPos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock().isFullBlock() || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockSlab || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockStairs || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockCactus || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockChest || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockEnderChest || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockSkull || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockPane || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockFence || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockWall || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockGlass || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockPistonBase || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockPistonExtension || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockPistonMoving || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockStainedGlass || Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockTrapDoor;
    }
    
    private static boolean isSafeToWalkOn(final BlockPos blockPos) {
        return !(Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockFence) && !(Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock() instanceof BlockWall);
    }
    
    public Class93 isHubExisting(final Class148 class148) {
        for (final Class93 class149 : this.hubs) {
            if (class149.getLoc().getX() == class148.getX() && class149.getLoc().getY() == class148.getY() && class149.getLoc().getZ() == class148.getZ()) {
                return class149;
            }
        }
        for (final Class93 class150 : this.hubsToWork) {
            if (class150.getLoc().getX() == class148.getX() && class150.getLoc().getY() == class148.getY() && class150.getLoc().getZ() == class148.getZ()) {
                return class150;
            }
        }
        return null;
    }
    
    public boolean addHub(final Class93 parent, final Class148 loc, final double cost) {
        final Class93 hubExisting = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (hubExisting == null) {
            if ((loc.getX() == this.endVec3.getX() && loc.getY() == this.endVec3.getY() && loc.getZ() == this.endVec3.getZ()) || (this.minDistanceSquared != 0.0 && loc.squareDistanceTo(this.endVec3) <= this.minDistanceSquared)) {
                this.path.clear();
                (this.path = parent.getPath()).add(loc);
                return true;
            }
            final ArrayList<Class148> list = new ArrayList<Class148>(parent.getPath());
            list.add(loc);
            this.hubsToWork.add(new Class93(loc, parent, list, loc.squareDistanceTo(this.endVec3), cost, totalCost));
        }
        else if (hubExisting.getCost() > cost) {
            final ArrayList<Class148> path = new ArrayList<Class148>(parent.getPath());
            path.add(loc);
            hubExisting.setLoc(loc);
            hubExisting.setParent(parent);
            hubExisting.setPath(path);
            hubExisting.setSquareDistanceToFromTarget(loc.squareDistanceTo(this.endVec3));
            hubExisting.setCost(cost);
            hubExisting.setTotalCost(totalCost);
        }
        return false;
    }
    
    static {
        Class171.flatCardinalDirections = new Class148[] { new Class148(1.0, 0.0, 0.0), new Class148(-1.0, 0.0, 0.0), new Class148(0.0, 0.0, 1.0), new Class148(0.0, 0.0, -1.0) };
    }
    
    public class Class219 implements Comparator<Class93>
    {
        final Class171 this$0;
        
        
        public Class219(final Class171 this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int compare(final Class93 class93, final Class93 class94) {
            return (int)(class93.getSquareDistanceToFromTarget() + class93.getTotalCost() - (class94.getSquareDistanceToFromTarget() + class94.getTotalCost()));
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Class93)o, (Class93)o2);
        }
    }
    
    private class Class93
    {
        private Class148 loc;
        private Class93 parent;
        private ArrayList<Class148> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;
        final Class171 this$0;
        
        
        public Class93(final Class171 this$0, final Class148 loc, final Class93 parent, final ArrayList<Class148> path, final double squareDistanceToFromTarget, final double cost, final double totalCost) {
            this.this$0 = this$0;
            this.loc = null;
            this.parent = null;
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }
        
        public Class148 getLoc() {
            return this.loc;
        }
        
        public Class93 getParent() {
            return this.parent;
        }
        
        public ArrayList<Class148> getPath() {
            return this.path;
        }
        
        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }
        
        public double getCost() {
            return this.cost;
        }
        
        public void setLoc(final Class148 loc) {
            this.loc = loc;
        }
        
        public void setParent(final Class93 parent) {
            this.parent = parent;
        }
        
        public void setPath(final ArrayList<Class148> path) {
            this.path = path;
        }
        
        public void setSquareDistanceToFromTarget(final double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }
        
        public void setCost(final double cost) {
            this.cost = cost;
        }
        
        public double getTotalCost() {
            return this.totalCost;
        }
        
        public void setTotalCost(final double totalCost) {
            this.totalCost = totalCost;
        }
    }
}
