/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public abstract class LockableLootTileEntity
extends LockableTileEntity {
    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed;

    protected LockableLootTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public static void setLootTable(IBlockReader iBlockReader, Random random2, BlockPos blockPos, ResourceLocation resourceLocation) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        if (tileEntity instanceof LockableLootTileEntity) {
            ((LockableLootTileEntity)tileEntity).setLootTable(resourceLocation, random2.nextLong());
        }
    }

    protected boolean checkLootAndRead(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("LootTable", 1)) {
            this.lootTable = new ResourceLocation(compoundNBT.getString("LootTable"));
            this.lootTableSeed = compoundNBT.getLong("LootTableSeed");
            return false;
        }
        return true;
    }

    protected boolean checkLootAndWrite(CompoundNBT compoundNBT) {
        if (this.lootTable == null) {
            return true;
        }
        compoundNBT.putString("LootTable", this.lootTable.toString());
        if (this.lootTableSeed != 0L) {
            compoundNBT.putLong("LootTableSeed", this.lootTableSeed);
        }
        return false;
    }

    public void fillWithLoot(@Nullable PlayerEntity playerEntity) {
        if (this.lootTable != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootTableManager().getLootTableFromLocation(this.lootTable);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLAYER_GENERATES_CONTAINER_LOOT.test((ServerPlayerEntity)playerEntity, this.lootTable);
            }
            this.lootTable = null;
            LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(this.pos)).withSeed(this.lootTableSeed);
            if (playerEntity != null) {
                builder.withLuck(playerEntity.getLuck()).withParameter(LootParameters.THIS_ENTITY, playerEntity);
            }
            lootTable.fillInventory(this, builder.build(LootParameterSets.CHEST));
        }
    }

    public void setLootTable(ResourceLocation resourceLocation, long l) {
        this.lootTable = resourceLocation;
        this.lootTableSeed = l;
    }

    @Override
    public boolean isEmpty() {
        this.fillWithLoot(null);
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        this.fillWithLoot(null);
        return this.getItems().get(n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        this.fillWithLoot(null);
        ItemStack itemStack = ItemStackHelper.getAndSplit(this.getItems(), n, n2);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        this.fillWithLoot(null);
        return ItemStackHelper.getAndRemove(this.getItems(), n);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.fillWithLoot(null);
        this.getItems().set(n, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        if (this.world.getTileEntity(this.pos) != this) {
            return true;
        }
        return !(playerEntity.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public void clear() {
        this.getItems().clear();
    }

    protected abstract NonNullList<ItemStack> getItems();

    protected abstract void setItems(NonNullList<ItemStack> var1);

    @Override
    public boolean canOpen(PlayerEntity playerEntity) {
        return super.canOpen(playerEntity) && (this.lootTable == null || !playerEntity.isSpectator());
    }

    @Override
    @Nullable
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.canOpen(playerEntity)) {
            this.fillWithLoot(playerInventory.player);
            return this.createMenu(n, playerInventory);
        }
        return null;
    }
}

