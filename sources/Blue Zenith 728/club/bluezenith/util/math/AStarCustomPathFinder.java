package club.bluezenith.util.math;

import net.minecraft.block.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Comparator;

import static net.minecraft.client.Minecraft.getMinecraft;

public class AStarCustomPathFinder {
    private final Vec3 startVec3;
    private final Vec3 endVec3;
    private ArrayList<Vec3> path = new ArrayList<>();
    private final ArrayList<Hub> hubs = new ArrayList<>();
    private final ArrayList<Hub> hubsToWork = new ArrayList<>();
    private final double minDistanceSquared = 16;
    private final boolean nearest = true;

    private static final Vec3[] flatCardinalDirections = {
            new Vec3(1, 0, 0),
            new Vec3(-1, 0, 0),
            new Vec3(0, 0, 1),
            new Vec3(0, 0, -1)
    };

    private static final Vec3[] twoCoordDirections = {
            new Vec3(1, 0, 1),
            new Vec3(1, 0, -1),
            new Vec3(-1, 0, 1),
            new Vec3(-1, 0, -1),

            new Vec3(1, 1, 0),
            new Vec3(-1, 1, 0),
            new Vec3(0, 1, 1),
            new Vec3(0, 1, -1),

            new Vec3(1, -1, 0),
            new Vec3(-1, -1, 0),
            new Vec3(0, -1, 1),
            new Vec3(0, -1, -1)
    };

    public AStarCustomPathFinder(Vec3 startVec3, Vec3 endVec3) {
        this.startVec3 = startVec3.addVector(0, 0, 0).floor();
        this.endVec3 = endVec3.addVector(0, 0, 0).floor();
    }

    public ArrayList<Vec3> getPath() {
        Vec3 prevLoc = path.get(0);
        Vec3 currentDir = new Vec3(0, 0, 0);
        Vec3 prevDir = new Vec3(-11, -11, -11);
        Vec3 lastLocWithoutTheSameDir = path.get(0);
        ArrayList<Vec3> optimizedPath = new ArrayList<>();
        ArrayList<Vec3> newpath = path;
        //Vec3 sus = newpath.get(newpath.size() - 1);
        //newpath.remove(newpath.size() - 1);

        for (int i = 0, newpathSize = newpath.size(); i < newpathSize; i++) {
            Vec3 loc = newpath.get(i);
            currentDir = loc.subtract(prevLoc);



            boolean canContinue = true;
            double smallX = Math.min(lastLocWithoutTheSameDir.xCoord, loc.xCoord);
            double smallY = Math.min(lastLocWithoutTheSameDir.yCoord, loc.yCoord);
            double smallZ = Math.min(lastLocWithoutTheSameDir.zCoord, loc.zCoord);
            double bigX = Math.max(lastLocWithoutTheSameDir.xCoord, loc.xCoord);
            double bigY = Math.max(lastLocWithoutTheSameDir.yCoord, loc.yCoord);
            double bigZ = Math.max(lastLocWithoutTheSameDir.zCoord, loc.zCoord);
            cordsLoop:
            for (int x = (int) smallX; x <= bigX; x++) {
                for (int y = (int) smallY; y <= bigY; y++) {
                    for (int z = (int) smallZ; z <= bigZ; z++) {
                        if (!checkPositionValidity(x, y, z, false)) {
                            canContinue = false;
                            break cordsLoop;
                        }
                    }
                }
            }
            if (!canContinue) {
                optimizedPath.add(prevLoc);
                lastLocWithoutTheSameDir = prevLoc;
                prevDir = currentDir;
            } else if (loc.squareDistanceTo(lastLocWithoutTheSameDir) > 24) {
                optimizedPath.add(prevLoc);
                lastLocWithoutTheSameDir = prevLoc;
                prevDir = currentDir;
            }

            if (i == 0 || i == newpathSize - 1) {
                optimizedPath.add(loc);
            }
            prevLoc = loc;
        }
        //optimizedPath.add(sus);
        return optimizedPath;
    }

    public void compute() {
        compute(1000, 4);
    }

    public void compute(int loops, int depth) {
        path.clear();
        hubsToWork.clear();
        ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(startVec3);
        hubsToWork.add(new Hub(startVec3, null, initPath, startVec3.squareDistanceTo(endVec3), 0, 0));
        search:
        for (int i = 0; i < loops; i++) {
            hubsToWork.sort(new CompareHub());
            int j = 0;
            if (hubsToWork.size() == 0) {
                break;
            }
            for (Hub hub : new ArrayList<>(hubsToWork)) {
                j++;
                if (j > depth) {
                    break;
                } else {
                    hubsToWork.remove(hub);
                    hubs.add(hub);

                    boolean x1 = false;
                    boolean xminus1 = false;
                    boolean z1 = false;
                    boolean zminus1 = false;
                    boolean up = false;
                    boolean down = false;

                    for (Vec3 direction : flatCardinalDirections) {
                        Vec3 loc = hub.getLoc().add(direction).floor();
                        if (checkPositionValidity(loc, false)) {
                            if (addHub(hub, loc, 0)) {
                                break search;
                            }
                            /*if (direction.xCoord == 1) {
                                x1 = true;
                            }
                            else if (direction.xCoord == -1) {
                                xminus1 = true;
                            }
                            else if (direction.zCoord == 1) {
                                z1 = true;
                            }
                            else if (direction.zCoord == -1) {
                                zminus1 = true;
                            }*/
                        }
                    }

                    Vec3 loc1 = hub.getLoc().addVector(0, 1, 0).floor();
                    if (checkPositionValidity(loc1, false)) {
                        if (addHub(hub, loc1, 0)) {
                            break search;
                        }
                        up = true;
                    }

                    Vec3 loc2 = hub.getLoc().addVector(0, -1, 0).floor();
                    if (checkPositionValidity(loc2, false)) {
                        if (addHub(hub, loc2, 0)) {
                            break search;
                        }
                        down = true;
                    }

                    /*for (Vec3 direction : twoCoordDirections) {
                        Vec3 loc = hub.getLoc().add(direction).floor();
                        if ((direction.xCoord == 1 && !x1)
                                || (direction.xCoord == -1 && !xminus1)
                                || (direction.zCoord == 1 && !z1)
                                || (direction.zCoord == -1 && !zminus1)
                                || (direction.yCoord == 1 && !up)
                                || (direction.yCoord == -1 && !down)
                        ) {
                            continue;
                        }
                        if (checkPositionValidity(loc, false)) {
                            if (addHub(hub, loc, 0)) {
                                break search;
                            }
                        }
                    }*/
                }
            }
        }
        if (nearest) {
            hubs.sort(new CompareHub());
            path = hubs.get(0).getPath();
        }
    }

    public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
        return checkPositionValidity((int) loc.xCoord, (int) loc.yCoord, (int) loc.zCoord, checkGround);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }

    private static boolean isBlockSolid(BlockPos block) {
        return getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()).isBlockNormalCube() ||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSlab) ||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStairs)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockCactus)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockChest)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockEnderChest)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSkull)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPane)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockGlass)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonBase)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonExtension)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonMoving)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStainedGlass)||
                (getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockTrapDoor);
    }

    private static boolean isSafeToWalkOn(BlockPos block) {
        return !(getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence) &&
                !(getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall);
    }

    public Hub isHubExisting(Vec3 loc) {
        for (Hub hub : hubs) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        for (Hub hub : hubsToWork) {
            if (hub.getLoc().xCoord == loc.xCoord && hub.getLoc().yCoord == loc.yCoord && hub.getLoc().zCoord == loc.zCoord) {
                return hub;
            }
        }
        return null;
    }

    public boolean addHub(Hub parent, Vec3 loc, double cost) {
        Hub existingHub = isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if (loc.xCoord == endVec3.xCoord && loc.yCoord == endVec3.yCoord && loc.zCoord == endVec3.zCoord || loc.squareDistanceTo(endVec3) <= 2.25) {
                path.clear();
                path = parent.getPath();
                path.add(loc);
                return true;
            } else {
                ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
                path.add(loc);
                hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(endVec3), cost, totalCost));
            }
        } else if (existingHub.getCost() > cost) {
            ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }

    private static class Hub {
        private Vec3 loc;
        private Hub parent;
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
            return loc;
        }

        public Hub getParent() {
            return parent;
        }

        public ArrayList<Vec3> getPath() {
            return path;
        }

        public double getSquareDistanceToFromTarget() {
            return squareDistanceToFromTarget;
        }

        public double getCost() {
            return cost;
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
            return totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }
    }

     static class CompareHub implements Comparator<Hub> {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int) (
                    (o1.getSquareDistanceToFromTarget() + o1.getTotalCost()) - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost())
            );
        }
    }
}
