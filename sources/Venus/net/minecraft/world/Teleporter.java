/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class Teleporter {
    private final ServerWorld world;

    public Teleporter(ServerWorld serverWorld) {
        this.world = serverWorld;
    }

    public Optional<TeleportationRepositioner.Result> getExistingPortal(BlockPos blockPos, boolean bl) {
        PointOfInterestManager pointOfInterestManager = this.world.getPointOfInterestManager();
        int n = bl ? 16 : 128;
        pointOfInterestManager.ensureLoadedAndValid(this.world, blockPos, n);
        Optional<PointOfInterest> optional = pointOfInterestManager.getInSquare(Teleporter::lambda$getExistingPortal$0, blockPos, n, PointOfInterestManager.Status.ANY).sorted(Comparator.comparingDouble(arg_0 -> Teleporter.lambda$getExistingPortal$1(blockPos, arg_0)).thenComparingInt(Teleporter::lambda$getExistingPortal$2)).filter(this::lambda$getExistingPortal$3).findFirst();
        return optional.map(this::lambda$getExistingPortal$5);
    }

    public Optional<TeleportationRepositioner.Result> makePortal(BlockPos blockPos, Direction.Axis axis) {
        int n;
        int n2;
        Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis);
        double d = -1.0;
        BlockPos blockPos2 = null;
        double d2 = -1.0;
        BlockPos blockPos3 = null;
        WorldBorder worldBorder = this.world.getWorldBorder();
        int n3 = this.world.func_234938_ad_() - 1;
        BlockPos.Mutable mutable = blockPos.toMutable();
        for (BlockPos.Mutable mutable2 : BlockPos.func_243514_a(blockPos, 16, Direction.EAST, Direction.SOUTH)) {
            n2 = Math.min(n3, this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, mutable2.getX(), mutable2.getZ()));
            n = 1;
            if (!worldBorder.contains(mutable2) || !worldBorder.contains(mutable2.move(direction, 1))) continue;
            mutable2.move(direction.getOpposite(), 1);
            for (int i = n2; i >= 0; --i) {
                int n4;
                mutable2.setY(i);
                if (!this.world.isAirBlock(mutable2)) continue;
                int n5 = i;
                while (i > 0 && this.world.isAirBlock(mutable2.move(Direction.DOWN))) {
                    --i;
                }
                if (i + 4 > n3 || (n4 = n5 - i) > 0 && n4 < 3) continue;
                mutable2.setY(i);
                if (!this.checkRegionForPlacement(mutable2, mutable, direction, 1)) continue;
                double d3 = blockPos.distanceSq(mutable2);
                if (this.checkRegionForPlacement(mutable2, mutable, direction, 1) && this.checkRegionForPlacement(mutable2, mutable, direction, 0) && (d == -1.0 || d > d3)) {
                    d = d3;
                    blockPos2 = mutable2.toImmutable();
                }
                if (d != -1.0 || d2 != -1.0 && !(d2 > d3)) continue;
                d2 = d3;
                blockPos3 = mutable2.toImmutable();
            }
        }
        if (d == -1.0 && d2 != -1.0) {
            blockPos2 = blockPos3;
            d = d2;
        }
        if (d == -1.0) {
            blockPos2 = new BlockPos(blockPos.getX(), MathHelper.clamp(blockPos.getY(), 70, this.world.func_234938_ad_() - 10), blockPos.getZ()).toImmutable();
            Direction direction2 = direction.rotateY();
            if (!worldBorder.contains(blockPos2)) {
                return Optional.empty();
            }
            for (int i = -1; i < 2; ++i) {
                for (n2 = 0; n2 < 2; ++n2) {
                    for (n = -1; n < 3; ++n) {
                        BlockState blockState = n < 0 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                        mutable.setAndOffset(blockPos2, n2 * direction.getXOffset() + i * direction2.getXOffset(), n, n2 * direction.getZOffset() + i * direction2.getZOffset());
                        this.world.setBlockState(mutable, blockState);
                    }
                }
            }
        }
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                if (i != -1 && i != 2 && j != -1 && j != 3) continue;
                mutable.setAndOffset(blockPos2, i * direction.getXOffset(), j, i * direction.getZOffset());
                this.world.setBlockState(mutable, Blocks.OBSIDIAN.getDefaultState(), 0);
            }
        }
        BlockState blockState = (BlockState)Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis);
        for (int i = 0; i < 2; ++i) {
            for (n2 = 0; n2 < 3; ++n2) {
                mutable.setAndOffset(blockPos2, i * direction.getXOffset(), n2, i * direction.getZOffset());
                this.world.setBlockState(mutable, blockState, 1);
            }
        }
        return Optional.of(new TeleportationRepositioner.Result(blockPos2.toImmutable(), 2, 3));
    }

    private boolean checkRegionForPlacement(BlockPos blockPos, BlockPos.Mutable mutable, Direction direction, int n) {
        Direction direction2 = direction.rotateY();
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                mutable.setAndOffset(blockPos, direction.getXOffset() * i + direction2.getXOffset() * n, j, direction.getZOffset() * i + direction2.getZOffset() * n);
                if (j < 0 && !this.world.getBlockState(mutable).getMaterial().isSolid()) {
                    return true;
                }
                if (j < 0 || this.world.isAirBlock(mutable)) continue;
                return true;
            }
        }
        return false;
    }

    private TeleportationRepositioner.Result lambda$getExistingPortal$5(PointOfInterest pointOfInterest) {
        BlockPos blockPos = pointOfInterest.getPos();
        this.world.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
        BlockState blockState = this.world.getBlockState(blockPos);
        return TeleportationRepositioner.findLargestRectangle(blockPos, blockState.get(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, arg_0 -> this.lambda$getExistingPortal$4(blockState, arg_0));
    }

    private boolean lambda$getExistingPortal$4(BlockState blockState, BlockPos blockPos) {
        return this.world.getBlockState(blockPos) == blockState;
    }

    private boolean lambda$getExistingPortal$3(PointOfInterest pointOfInterest) {
        return this.world.getBlockState(pointOfInterest.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS);
    }

    private static int lambda$getExistingPortal$2(PointOfInterest pointOfInterest) {
        return pointOfInterest.getPos().getY();
    }

    private static double lambda$getExistingPortal$1(BlockPos blockPos, PointOfInterest pointOfInterest) {
        return pointOfInterest.getPos().distanceSq(blockPos);
    }

    private static boolean lambda$getExistingPortal$0(PointOfInterestType pointOfInterestType) {
        return pointOfInterestType == PointOfInterestType.NETHER_PORTAL;
    }
}

