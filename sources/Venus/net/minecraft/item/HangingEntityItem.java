/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HangingEntityItem
extends Item {
    private final EntityType<? extends HangingEntity> hangingEntity;

    public HangingEntityItem(EntityType<? extends HangingEntity> entityType, Item.Properties properties) {
        super(properties);
        this.hangingEntity = entityType;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        HangingEntity hangingEntity;
        BlockPos blockPos = itemUseContext.getPos();
        Direction direction = itemUseContext.getFace();
        BlockPos blockPos2 = blockPos.offset(direction);
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        ItemStack itemStack = itemUseContext.getItem();
        if (playerEntity != null && !this.canPlace(playerEntity, direction, itemStack, blockPos2)) {
            return ActionResultType.FAIL;
        }
        World world = itemUseContext.getWorld();
        if (this.hangingEntity == EntityType.PAINTING) {
            hangingEntity = new PaintingEntity(world, blockPos2, direction);
        } else {
            if (this.hangingEntity != EntityType.ITEM_FRAME) {
                return ActionResultType.func_233537_a_(world.isRemote);
            }
            hangingEntity = new ItemFrameEntity(world, blockPos2, direction);
        }
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            EntityType.applyItemNBT(world, playerEntity, hangingEntity, compoundNBT);
        }
        if (hangingEntity.onValidSurface()) {
            if (!world.isRemote) {
                hangingEntity.playPlaceSound();
                world.addEntity(hangingEntity);
            }
            itemStack.shrink(1);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.CONSUME;
    }

    protected boolean canPlace(PlayerEntity playerEntity, Direction direction, ItemStack itemStack, BlockPos blockPos) {
        return !direction.getAxis().isVertical() && playerEntity.canPlayerEdit(blockPos, direction, itemStack);
    }
}

