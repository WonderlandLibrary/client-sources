/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.EntitySize;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public class PortalSize {
    private static final AbstractBlock.IPositionPredicate POSITION_PREDICATE = PortalSize::lambda$static$0;
    private final IWorld world;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private int portalBlockCount;
    @Nullable
    private BlockPos bottomLeft;
    private int height;
    private int width;

    public static Optional<PortalSize> func_242964_a(IWorld iWorld, BlockPos blockPos, Direction.Axis axis) {
        return PortalSize.func_242965_a(iWorld, blockPos, PortalSize::lambda$func_242964_a$1, axis);
    }

    public static Optional<PortalSize> func_242965_a(IWorld iWorld, BlockPos blockPos, Predicate<PortalSize> predicate, Direction.Axis axis) {
        Optional<PortalSize> optional = Optional.of(new PortalSize(iWorld, blockPos, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        }
        Direction.Axis axis2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        return Optional.of(new PortalSize(iWorld, blockPos, axis2)).filter(predicate);
    }

    public PortalSize(IWorld iWorld, BlockPos blockPos, Direction.Axis axis) {
        this.world = iWorld;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.bottomLeft = this.func_242971_a(blockPos);
        if (this.bottomLeft == null) {
            this.bottomLeft = blockPos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.func_242974_d();
            if (this.width > 0) {
                this.height = this.func_242975_e();
            }
        }
    }

    @Nullable
    private BlockPos func_242971_a(BlockPos blockPos) {
        int n = Math.max(0, blockPos.getY() - 21);
        while (blockPos.getY() > n && PortalSize.canConnect(this.world.getBlockState(blockPos.down()))) {
            blockPos = blockPos.down();
        }
        Direction direction = this.rightDir.getOpposite();
        int n2 = this.func_242972_a(blockPos, direction) - 1;
        return n2 < 0 ? null : blockPos.offset(direction, n2);
    }

    private int func_242974_d() {
        int n = this.func_242972_a(this.bottomLeft, this.rightDir);
        return n >= 2 && n <= 21 ? n : 0;
    }

    private int func_242972_a(BlockPos blockPos, Direction direction) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i <= 21; ++i) {
            mutable.setPos(blockPos).move(direction, i);
            BlockState blockState = this.world.getBlockState(mutable);
            if (!PortalSize.canConnect(blockState)) {
                if (!POSITION_PREDICATE.test(blockState, this.world, mutable)) break;
                return i;
            }
            BlockState blockState2 = this.world.getBlockState(mutable.move(Direction.DOWN));
            if (!POSITION_PREDICATE.test(blockState2, this.world, mutable)) break;
        }
        return 1;
    }

    private int func_242975_e() {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n = this.func_242969_a(mutable);
        return n >= 3 && n <= 21 && this.func_242970_a(mutable, n) ? n : 0;
    }

    private boolean func_242970_a(BlockPos.Mutable mutable, int n) {
        for (int i = 0; i < this.width; ++i) {
            BlockPos.Mutable mutable2 = mutable.setPos(this.bottomLeft).move(Direction.UP, n).move(this.rightDir, i);
            if (POSITION_PREDICATE.test(this.world.getBlockState(mutable2), this.world, mutable2)) continue;
            return true;
        }
        return false;
    }

    private int func_242969_a(BlockPos.Mutable mutable) {
        for (int i = 0; i < 21; ++i) {
            mutable.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
            if (!POSITION_PREDICATE.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }
            mutable.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
            if (!POSITION_PREDICATE.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }
            for (int j = 0; j < this.width; ++j) {
                mutable.setPos(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                BlockState blockState = this.world.getBlockState(mutable);
                if (!PortalSize.canConnect(blockState)) {
                    return i;
                }
                if (!blockState.isIn(Blocks.NETHER_PORTAL)) continue;
                ++this.portalBlockCount;
            }
        }
        return 0;
    }

    private static boolean canConnect(BlockState blockState) {
        return blockState.isAir() || blockState.isIn(BlockTags.FIRE) || blockState.isIn(Blocks.NETHER_PORTAL);
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void placePortalBlocks() {
        BlockState blockState = (BlockState)Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
        BlockPos.getAllInBoxMutable(this.bottomLeft, this.bottomLeft.offset(Direction.UP, this.height - 1).offset(this.rightDir, this.width - 1)).forEach(arg_0 -> this.lambda$placePortalBlocks$2(blockState, arg_0));
    }

    public boolean validatePortal() {
        return this.isValid() && this.portalBlockCount == this.width * this.height;
    }

    public static Vector3d func_242973_a(TeleportationRepositioner.Result result, Direction.Axis axis, Vector3d vector3d, EntitySize entitySize) {
        double d;
        Direction.Axis axis2;
        double d2;
        double d3 = (double)result.width - (double)entitySize.width;
        double d4 = (double)result.height - (double)entitySize.height;
        BlockPos blockPos = result.startPos;
        if (d3 > 0.0) {
            float f = (float)blockPos.func_243648_a(axis) + entitySize.width / 2.0f;
            d2 = MathHelper.clamp(MathHelper.func_233020_c_(vector3d.getCoordinate(axis) - (double)f, 0.0, d3), 0.0, 1.0);
        } else {
            d2 = 0.5;
        }
        if (d4 > 0.0) {
            axis2 = Direction.Axis.Y;
            d = MathHelper.clamp(MathHelper.func_233020_c_(vector3d.getCoordinate(axis2) - (double)blockPos.func_243648_a(axis2), 0.0, d4), 0.0, 1.0);
        } else {
            d = 0.0;
        }
        axis2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double d5 = vector3d.getCoordinate(axis2) - ((double)blockPos.func_243648_a(axis2) + 0.5);
        return new Vector3d(d2, d, d5);
    }

    public static PortalInfo func_242963_a(ServerWorld serverWorld, TeleportationRepositioner.Result result, Direction.Axis axis, Vector3d vector3d, EntitySize entitySize, Vector3d vector3d2, float f, float f2) {
        BlockPos blockPos = result.startPos;
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Direction.Axis axis2 = blockState.get(BlockStateProperties.HORIZONTAL_AXIS);
        double d = result.width;
        double d2 = result.height;
        int n = axis == axis2 ? 0 : 90;
        Vector3d vector3d3 = axis == axis2 ? vector3d2 : new Vector3d(vector3d2.z, vector3d2.y, -vector3d2.x);
        double d3 = (double)entitySize.width / 2.0 + (d - (double)entitySize.width) * vector3d.getX();
        double d4 = (d2 - (double)entitySize.height) * vector3d.getY();
        double d5 = 0.5 + vector3d.getZ();
        boolean bl = axis2 == Direction.Axis.X;
        Vector3d vector3d4 = new Vector3d((double)blockPos.getX() + (bl ? d3 : d5), (double)blockPos.getY() + d4, (double)blockPos.getZ() + (bl ? d5 : d3));
        return new PortalInfo(vector3d4, vector3d3, f + (float)n, f2);
    }

    private void lambda$placePortalBlocks$2(BlockState blockState, BlockPos blockPos) {
        this.world.setBlockState(blockPos, blockState, 18);
    }

    private static boolean lambda$func_242964_a$1(PortalSize portalSize) {
        return portalSize.isValid() && portalSize.portalBlockCount == 0;
    }

    private static boolean lambda$static$0(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(Blocks.OBSIDIAN);
    }
}

