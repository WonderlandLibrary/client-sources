package dev.stephen.nexus.utils.mc;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Rise
 */
public final class PathFinder {

    private final ArrayList<Hub> hubsToWork = new ArrayList<>();
    @Getter
    private ArrayList<Vec3d> path = new ArrayList<>();
    private final ArrayList<Hub> hubs = new ArrayList<>();
    private final double minDistanceSquared = 9.5;
    private final boolean nearest = true;
    private final Vec3d startVec3d;
    private final Vec3d endVec3d;

    private static final Vec3d[] flatCardinalDirections = {
            new Vec3d(1, 0, 0),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(0, 0, -1)
    };

    public PathFinder(final Vec3d startVec3d, final Vec3d endVec3d) {
        this.startVec3d = floor(startVec3d.add(0, 0, 0));
        this.endVec3d = floor(endVec3d.add(0, 0, 0));
    }

    public Vec3d floor(Vec3d vec3d) {
        return new Vec3d(Math.floor(vec3d.x), Math.floor(vec3d.y), Math.floor(vec3d.z));
    }

    public void compute() {
        compute(1000, 4);
    }

    public void compute(final int loops, final int depth) {
        path.clear();
        hubsToWork.clear();
        final ArrayList<Vec3d> initPath = new ArrayList<>();
        initPath.add(startVec3d);
        hubsToWork.add(new Hub(startVec3d, null, initPath, startVec3d.squaredDistanceTo(endVec3d), 0, 0));
        search:
        for (int i = 0; i < loops; i++) {
            hubsToWork.sort(new CompareHub());
            int j = 0;
            if (hubsToWork.size() == 0) {
                break;
            }
            for (final Hub hub : new ArrayList<>(hubsToWork)) {
                j++;
                if (j > depth) {
                    break;
                } else {
                    hubsToWork.remove(hub);
                    hubs.add(hub);

                    for (final Vec3d direction : flatCardinalDirections) {
                        final Vec3d loc = floor(hub.getLoc().add(direction));
                        if (checkPositionValidity(loc, false)) {
                            if (addHub(hub, loc, 0)) {
                                break search;
                            }
                        }
                    }

                    final Vec3d loc1 = floor(hub.getLoc().add(0, 1, 0));
                    if (checkPositionValidity(loc1, false)) {
                        if (addHub(hub, loc1, 0)) {
                            break search;
                        }
                    }

                    final Vec3d loc2 = floor(hub.getLoc().add(0, -1, 0));
                    if (checkPositionValidity(loc2, false)) {
                        if (addHub(hub, loc2, 0)) {
                            break search;
                        }
                    }
                }
            }
        }
        if (nearest) {
            hubs.sort(new CompareHub());

            path = hubs.get(0).getPath();
        }
    }

    public static boolean checkPositionValidity(final Vec3d loc, final boolean checkGround) {
        return checkPositionValidity((int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), checkGround);
    }

    public static boolean checkPositionValidity(final int x, final int y, final int z, final boolean checkGround) {
        final BlockPos block3 = new BlockPos(x, y - 1, z);

        return !isBlockSolid(new BlockPos(x, y, z)) && !isBlockSolid(new BlockPos(x, y + 1, z)) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }

    private static boolean isBlockSolid(final BlockPos blockPos) {
        final Block b = MinecraftClient.getInstance().world.getBlockState(blockPos).getBlock();


        return !MinecraftClient.getInstance().world.getBlockState(blockPos).isAir() ||
                b instanceof SlabBlock
                || b instanceof StairsBlock
                || b instanceof CactusBlock
                || b instanceof ChestBlock
                || b instanceof EnderChestBlock
                || b instanceof SkullBlock
                || b instanceof PaneBlock
                || b instanceof FenceBlock
                || b instanceof WallBlock
                || b instanceof StainedGlassBlock
                || b instanceof PistonExtensionBlock
                || b instanceof PistonHeadBlock
                || b instanceof TrapdoorBlock
                || b instanceof EndPortalFrameBlock
                || b instanceof EndPortalBlock
                || b instanceof BedBlock
                || b instanceof CobwebBlock
                || b instanceof BarrierBlock
                || b instanceof LadderBlock
                || b instanceof LeavesBlock
                || b instanceof SnowBlock
                || b instanceof SnowBlock
                || b instanceof CarpetBlock
                || b instanceof DoorBlock
                || b instanceof VineBlock
                || b instanceof LilyPadBlock;
    }

    private static boolean isSafeToWalkOn(final BlockPos block) {
        final Block blockClazz = MinecraftClient.getInstance().world.getBlockState(block).getBlock();

        return !(blockClazz instanceof FenceBlock) && !(blockClazz instanceof WallBlock);
    }

    public Hub isHubExisting(final Vec3d loc) {
        for (final Hub hub : hubs) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }

        for (final Hub hub : hubsToWork) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }
        return null;
    }

    public boolean addHub(final Hub parent, final Vec3d loc, final double cost) {
        final Hub existingHub = isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if ((loc.getX() == endVec3d.getX() && loc.getY() == endVec3d.getY() && loc.getZ() == endVec3d.getZ()) || (minDistanceSquared != 0 && loc.squaredDistanceTo(endVec3d) <= minDistanceSquared)) {
                if (parent != null) {
                    path.clear();
                    path = parent.getPath();
                    path.add(loc);

                    return true;
                } else {
                    return false;
                }
            } else {
                final ArrayList<Vec3d> path = new ArrayList<>(parent.getPath());
                path.add(loc);
                hubsToWork.add(new Hub(loc, parent, path, loc.squaredDistanceTo(endVec3d), cost, totalCost));
            }
        } else if (existingHub.getCost() > cost) {
            final ArrayList<Vec3d> path = new ArrayList<>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquaredDistanceToFromTarget(loc.squaredDistanceTo(endVec3d));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }

    @Getter
    @Setter
    private static class Hub {
        private Vec3d loc;
        private Hub parent;
        private ArrayList<Vec3d> path;
        private double squaredDistanceToFromTarget;
        private double cost;
        private double totalCost;

        public Hub(final Vec3d loc, final Hub parent, final ArrayList<Vec3d> path, final double squaredDistanceToFromTarget, final double cost, final double totalCost) {
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squaredDistanceToFromTarget = squaredDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }
    }

    public static class CompareHub implements Comparator<Hub> {
        @Override
        public int compare(final Hub o1, final Hub o2) {
            return (int) (
                    (o1.getSquaredDistanceToFromTarget() + o1.getTotalCost()) - (o2.getSquaredDistanceToFromTarget() + o2.getTotalCost())
            );
        }
    }
}