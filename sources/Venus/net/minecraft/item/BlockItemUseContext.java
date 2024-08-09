/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BlockItemUseContext
extends ItemUseContext {
    private final BlockPos offsetPos;
    protected boolean replaceClicked = true;

    public BlockItemUseContext(PlayerEntity playerEntity, Hand hand, ItemStack itemStack, BlockRayTraceResult blockRayTraceResult) {
        this(playerEntity.world, playerEntity, hand, itemStack, blockRayTraceResult);
    }

    public BlockItemUseContext(ItemUseContext itemUseContext) {
        this(itemUseContext.getWorld(), itemUseContext.getPlayer(), itemUseContext.getHand(), itemUseContext.getItem(), itemUseContext.func_242401_i());
    }

    protected BlockItemUseContext(World world, @Nullable PlayerEntity playerEntity, Hand hand, ItemStack itemStack, BlockRayTraceResult blockRayTraceResult) {
        super(world, playerEntity, hand, itemStack, blockRayTraceResult);
        this.offsetPos = blockRayTraceResult.getPos().offset(blockRayTraceResult.getFace());
        this.replaceClicked = world.getBlockState(blockRayTraceResult.getPos()).isReplaceable(this);
    }

    public static BlockItemUseContext func_221536_a(BlockItemUseContext blockItemUseContext, BlockPos blockPos, Direction direction) {
        return new BlockItemUseContext(blockItemUseContext.getWorld(), blockItemUseContext.getPlayer(), blockItemUseContext.getHand(), blockItemUseContext.getItem(), new BlockRayTraceResult(new Vector3d((double)blockPos.getX() + 0.5 + (double)direction.getXOffset() * 0.5, (double)blockPos.getY() + 0.5 + (double)direction.getYOffset() * 0.5, (double)blockPos.getZ() + 0.5 + (double)direction.getZOffset() * 0.5), direction, blockPos, false));
    }

    @Override
    public BlockPos getPos() {
        return this.replaceClicked ? super.getPos() : this.offsetPos;
    }

    public boolean canPlace() {
        return this.replaceClicked || this.getWorld().getBlockState(this.getPos()).isReplaceable(this);
    }

    public boolean replacingClickedOnBlock() {
        return this.replaceClicked;
    }

    public Direction getNearestLookingDirection() {
        return Direction.getFacingDirections(this.getPlayer())[0];
    }

    public Direction[] getNearestLookingDirections() {
        int n;
        Direction[] directionArray = Direction.getFacingDirections(this.getPlayer());
        if (this.replaceClicked) {
            return directionArray;
        }
        Direction direction = this.getFace();
        for (n = 0; n < directionArray.length && directionArray[n] != direction.getOpposite(); ++n) {
        }
        if (n > 0) {
            System.arraycopy(directionArray, 0, directionArray, 1, n);
            directionArray[0] = direction.getOpposite();
        }
        return directionArray;
    }
}

