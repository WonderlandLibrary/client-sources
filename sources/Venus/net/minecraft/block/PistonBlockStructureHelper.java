/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonBlockStructureHelper {
    private final World world;
    private final BlockPos pistonPos;
    private final boolean extending;
    private final BlockPos blockToMove;
    private final Direction moveDirection;
    private final List<BlockPos> toMove = Lists.newArrayList();
    private final List<BlockPos> toDestroy = Lists.newArrayList();
    private final Direction facing;

    public PistonBlockStructureHelper(World world, BlockPos blockPos, Direction direction, boolean bl) {
        this.world = world;
        this.pistonPos = blockPos;
        this.facing = direction;
        this.extending = bl;
        if (bl) {
            this.moveDirection = direction;
            this.blockToMove = blockPos.offset(direction);
        } else {
            this.moveDirection = direction.getOpposite();
            this.blockToMove = blockPos.offset(direction, 2);
        }
    }

    public boolean canMove() {
        this.toMove.clear();
        this.toDestroy.clear();
        BlockState blockState = this.world.getBlockState(this.blockToMove);
        if (!PistonBlock.canPush(blockState, this.world, this.blockToMove, this.moveDirection, false, this.facing)) {
            if (this.extending && blockState.getPushReaction() == PushReaction.DESTROY) {
                this.toDestroy.add(this.blockToMove);
                return false;
            }
            return true;
        }
        if (!this.addBlockLine(this.blockToMove, this.moveDirection)) {
            return true;
        }
        for (int i = 0; i < this.toMove.size(); ++i) {
            BlockPos blockPos = this.toMove.get(i);
            if (!PistonBlockStructureHelper.func_227029_a_(this.world.getBlockState(blockPos).getBlock()) || this.addBranchingBlocks(blockPos)) continue;
            return true;
        }
        return false;
    }

    private static boolean func_227029_a_(Block block) {
        return block == Blocks.SLIME_BLOCK || block == Blocks.HONEY_BLOCK;
    }

    private static boolean func_227030_a_(Block block, Block block2) {
        if (block == Blocks.HONEY_BLOCK && block2 == Blocks.SLIME_BLOCK) {
            return true;
        }
        if (block == Blocks.SLIME_BLOCK && block2 == Blocks.HONEY_BLOCK) {
            return true;
        }
        return PistonBlockStructureHelper.func_227029_a_(block) || PistonBlockStructureHelper.func_227029_a_(block2);
    }

    private boolean addBlockLine(BlockPos blockPos, Direction direction) {
        int n;
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (blockState.isAir()) {
            return false;
        }
        if (!PistonBlock.canPush(blockState, this.world, blockPos, this.moveDirection, false, direction)) {
            return false;
        }
        if (blockPos.equals(this.pistonPos)) {
            return false;
        }
        if (this.toMove.contains(blockPos)) {
            return false;
        }
        int n2 = 1;
        if (n2 + this.toMove.size() > 12) {
            return true;
        }
        while (PistonBlockStructureHelper.func_227029_a_(block)) {
            BlockPos blockPos2 = blockPos.offset(this.moveDirection.getOpposite(), n2);
            Block block2 = block;
            blockState = this.world.getBlockState(blockPos2);
            block = blockState.getBlock();
            if (blockState.isAir() || !PistonBlockStructureHelper.func_227030_a_(block2, block) || !PistonBlock.canPush(blockState, this.world, blockPos2, this.moveDirection, false, this.moveDirection.getOpposite()) || blockPos2.equals(this.pistonPos)) break;
            if (++n2 + this.toMove.size() <= 12) continue;
            return true;
        }
        int n3 = 0;
        for (n = n2 - 1; n >= 0; --n) {
            this.toMove.add(blockPos.offset(this.moveDirection.getOpposite(), n));
            ++n3;
        }
        n = 1;
        while (true) {
            BlockPos blockPos3;
            int n4;
            if ((n4 = this.toMove.indexOf(blockPos3 = blockPos.offset(this.moveDirection, n))) > -1) {
                this.reorderListAtCollision(n3, n4);
                for (int i = 0; i <= n4 + n3; ++i) {
                    BlockPos blockPos4 = this.toMove.get(i);
                    if (!PistonBlockStructureHelper.func_227029_a_(this.world.getBlockState(blockPos4).getBlock()) || this.addBranchingBlocks(blockPos4)) continue;
                    return true;
                }
                return false;
            }
            blockState = this.world.getBlockState(blockPos3);
            if (blockState.isAir()) {
                return false;
            }
            if (!PistonBlock.canPush(blockState, this.world, blockPos3, this.moveDirection, true, this.moveDirection) || blockPos3.equals(this.pistonPos)) {
                return true;
            }
            if (blockState.getPushReaction() == PushReaction.DESTROY) {
                this.toDestroy.add(blockPos3);
                return false;
            }
            if (this.toMove.size() >= 12) {
                return true;
            }
            this.toMove.add(blockPos3);
            ++n3;
            ++n;
        }
    }

    private void reorderListAtCollision(int n, int n2) {
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        ArrayList<BlockPos> arrayList2 = Lists.newArrayList();
        ArrayList<BlockPos> arrayList3 = Lists.newArrayList();
        arrayList.addAll(this.toMove.subList(0, n2));
        arrayList2.addAll(this.toMove.subList(this.toMove.size() - n, this.toMove.size()));
        arrayList3.addAll(this.toMove.subList(n2, this.toMove.size() - n));
        this.toMove.clear();
        this.toMove.addAll(arrayList);
        this.toMove.addAll(arrayList2);
        this.toMove.addAll(arrayList3);
    }

    private boolean addBranchingBlocks(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        for (Direction direction : Direction.values()) {
            BlockPos blockPos2;
            BlockState blockState2;
            if (direction.getAxis() == this.moveDirection.getAxis() || !PistonBlockStructureHelper.func_227030_a_((blockState2 = this.world.getBlockState(blockPos2 = blockPos.offset(direction))).getBlock(), blockState.getBlock()) || this.addBlockLine(blockPos2, direction)) continue;
            return true;
        }
        return false;
    }

    public List<BlockPos> getBlocksToMove() {
        return this.toMove;
    }

    public List<BlockPos> getBlocksToDestroy() {
        return this.toDestroy;
    }
}

