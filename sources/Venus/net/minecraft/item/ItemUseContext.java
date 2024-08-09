/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemUseContext {
    @Nullable
    private final PlayerEntity player;
    private final Hand hand;
    private final BlockRayTraceResult rayTraceResult;
    private final World world;
    private final ItemStack item;

    public ItemUseContext(PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        this(playerEntity.world, playerEntity, hand, playerEntity.getHeldItem(hand), blockRayTraceResult);
    }

    protected ItemUseContext(World world, @Nullable PlayerEntity playerEntity, Hand hand, ItemStack itemStack, BlockRayTraceResult blockRayTraceResult) {
        this.player = playerEntity;
        this.hand = hand;
        this.rayTraceResult = blockRayTraceResult;
        this.item = itemStack;
        this.world = world;
    }

    protected final BlockRayTraceResult func_242401_i() {
        return this.rayTraceResult;
    }

    public BlockPos getPos() {
        return this.rayTraceResult.getPos();
    }

    public Direction getFace() {
        return this.rayTraceResult.getFace();
    }

    public Vector3d getHitVec() {
        return this.rayTraceResult.getHitVec();
    }

    public boolean isInside() {
        return this.rayTraceResult.isInside();
    }

    public ItemStack getItem() {
        return this.item;
    }

    @Nullable
    public PlayerEntity getPlayer() {
        return this.player;
    }

    public Hand getHand() {
        return this.hand;
    }

    public World getWorld() {
        return this.world;
    }

    public Direction getPlacementHorizontalFacing() {
        return this.player == null ? Direction.NORTH : this.player.getHorizontalFacing();
    }

    public boolean hasSecondaryUseForPlayer() {
        return this.player != null && this.player.isSecondaryUseActive();
    }

    public float getPlacementYaw() {
        return this.player == null ? 0.0f : this.player.rotationYaw;
    }
}

