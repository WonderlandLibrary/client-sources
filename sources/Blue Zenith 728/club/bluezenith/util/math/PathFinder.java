package club.bluezenith.util.math;

import net.minecraft.block.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

import static club.bluezenith.util.MinecraftInstance.mc;
import static java.lang.Math.sqrt;

public class PathFinder {
    private final Vec3 startVec;
    private final Vec3 endVec;
    private ArrayList<Vec3> path = new ArrayList<>();
    public int minDistance = 5;
    private double targetRange = 2;
    private int maxLoops = 1000;
    private static final Vec3[] possibleDirections = {
            new Vec3(1, 0, 0),
            new Vec3(-1, 0, 0),
            new Vec3(0, 0, 1),
            new Vec3(0, 0, -1),
            new Vec3(0, 1, 0),
            new Vec3(0, -1, 0),

            new Vec3(1, 0, 1),
            new Vec3(-1, 0, 1),
            new Vec3(1, 0, -1),
            new Vec3(-1, 0, -1),

            new Vec3(1, 1, 1),
            new Vec3(-1, 1, 1),
            new Vec3(1, 1, -1),
            new Vec3(-1, 1, -1),

            new Vec3(1, -1, 1),
            new Vec3(-1, -1, 1),
            new Vec3(1, -1, -1),
            new Vec3(-1, -1, -1)
    };

    public PathFinder(Vec3 startPos, Vec3 endPos) {
        this.startVec = startPos.addVector(0, 0, 0).floor();
        this.endVec = endPos.addVector(0, 0, 0).floor();
    }

    public ArrayList<Vec3> getPath() {
        return this.path;
    }

    public boolean compute() {
        boolean pathFinished = true;
        path.clear();
        if (startVec.squareDistanceTo(endVec) < targetRange * targetRange) {
            return pathFinished;
        }

        Vec3 c04loc = startVec;
        Vec3 lastClosestLoc = startVec;
        Vec3 closestLoc = startVec;
        int loops = 0;

        while (closestLoc.distanceTo(endVec) > 1) {
            if (loops > 100) {
                pathFinished = false;
                break;
            }
            final ArrayList<Vec3> validLocs = new ArrayList<>();
            final ArrayList<Vec3> validDirs = new ArrayList<>();
            for (Vec3 dir : possibleDirections) {

                Vec3 loc = lastClosestLoc.add(dir).floor();
                final Block block1 = mc.theWorld.getBlockState(new BlockPos(loc.xCoord, loc.yCoord, loc.zCoord)).getBlock();
                final boolean block1Check = (block1 instanceof BlockAir) || (block1 instanceof BlockTallGrass) || (block1 instanceof BlockFlower) || (block1 instanceof BlockDoublePlant);
                final Block block2 = mc.theWorld.getBlockState(new BlockPos(loc.xCoord, loc.yCoord + 1, loc.zCoord)).getBlock();
                final boolean block2Check = (block2 instanceof BlockAir) || (block2 instanceof BlockTallGrass) || (block2 instanceof BlockFlower) || (block2 instanceof BlockDoublePlant);

                if (block1Check && block2Check) {
                    validLocs.add(loc);
                    validDirs.add(dir);
                }
            }

            for (int i = 0, validLocsSize = validLocs.size(); i < validLocsSize; i++) {
                Vec3 loc = validLocs.get(i);
                Vec3 dir = validDirs.get(i);

                Vec3 loc1 = loc.add(dir).floor();
                final Block block1 = mc.theWorld.getBlockState(new BlockPos(loc1.xCoord, loc1.yCoord, loc1.zCoord)).getBlock();
                final boolean block1Check = (block1 instanceof BlockAir) || (block1 instanceof BlockTallGrass) || (block1 instanceof BlockFlower) || (block1 instanceof BlockDoublePlant);
                final Block block2 = mc.theWorld.getBlockState(new BlockPos(loc1.xCoord, loc1.yCoord + 1, loc1.zCoord)).getBlock();
                final boolean block2Check = (block2 instanceof BlockAir) || (block2 instanceof BlockTallGrass) || (block2 instanceof BlockFlower) || (block2 instanceof BlockDoublePlant);

                if (block1Check && block2Check) {
                    validLocs.add(loc1);
                    //validDirs.add(dir);
                }
            }
            for (Vec3 loc : validLocs) {
                if (loc.squareDistanceTo(endVec) < closestLoc.squareDistanceTo(endVec)) {
                    closestLoc = loc;
                }
            }

            if (closestLoc == null) continue;
            if (lastClosestLoc == null) lastClosestLoc = closestLoc;

//            if (c04loc.squareDistanceTo(closestLoc) > 64) {
//                path.add(lastClosestLoc);
//                c04loc = lastClosestLoc;
//            }
            path.add(new Vec3(closestLoc.xCoord, closestLoc.yCoord, closestLoc.zCoord));

            /*if (c04loc.squareDistanceTo(endVec) < targetRange * targetRange) {
                return;
            }*/
            lastClosestLoc = closestLoc;
            loops++;
        }

        Vec3 prevLoc = path.get(0);
        Vec3 currentDir = new Vec3(0, 0, 0);
        Vec3 prevDir = new Vec3(0, 0, 0);
        Vec3 lastLocWithoutTheSameDir = path.get(0);
        ArrayList<Vec3> optimizedPath = new ArrayList<>();
        Vec3 sus = path.get(path.size() - 1);
        boolean elif = false;
        for (Vec3 loc : path) {
            currentDir = loc.subtract(prevLoc);

            elif = true;
            if (!currentDir.equals(prevDir)) {
                optimizedPath.add(prevLoc);
                lastLocWithoutTheSameDir = loc;
                prevDir = currentDir;
                elif = false;
            }
            final double x = lastLocWithoutTheSameDir.xCoord - loc.xCoord;
            double y = lastLocWithoutTheSameDir.yCoord - loc.yCoord;
            y *= 2;
            final double z = lastLocWithoutTheSameDir.zCoord - loc.zCoord;
            final double diff = sqrt(x*x + y*y + z*z);
            if (elif && diff > minDistance){
                optimizedPath.add(prevLoc);
                lastLocWithoutTheSameDir = loc;
                prevDir = currentDir;
            }
            prevLoc = loc;
        }
        path = optimizedPath;
        path.add(sus);
        return pathFinished;
    }
}
