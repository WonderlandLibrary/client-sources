/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ChestMinecartEntity
extends ContainerMinecartEntity {
    public ChestMinecartEntity(EntityType<? extends ChestMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public ChestMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.CHEST_MINECART, d, d2, d3, world);
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.entityDropItem(Blocks.CHEST);
        }
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.CHEST;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH);
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }

    @Override
    public Container createContainer(int n, PlayerInventory playerInventory) {
        return ChestContainer.createGeneric9X3(n, playerInventory, this);
    }
}

