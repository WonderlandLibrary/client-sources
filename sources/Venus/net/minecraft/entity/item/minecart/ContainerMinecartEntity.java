/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ContainerMinecartEntity
extends AbstractMinecartEntity
implements IInventory,
INamedContainerProvider {
    private NonNullList<ItemStack> minecartContainerItems = NonNullList.withSize(36, ItemStack.EMPTY);
    private boolean dropContentsWhenDead = true;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    protected ContainerMinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    protected ContainerMinecartEntity(EntityType<?> entityType, double d, double d2, double d3, World world) {
        super(entityType, world, d, d2, d3);
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            Entity entity2;
            InventoryHelper.dropInventoryItems(this.world, this, (IInventory)this);
            if (!this.world.isRemote && (entity2 = damageSource.getImmediateSource()) != null && entity2.getType() == EntityType.PLAYER) {
                PiglinTasks.func_234478_a_((PlayerEntity)entity2, true);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.minecartContainerItems) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        this.addLoot(null);
        return this.minecartContainerItems.get(n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        this.addLoot(null);
        return ItemStackHelper.getAndSplit(this.minecartContainerItems, n, n2);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        this.addLoot(null);
        ItemStack itemStack = this.minecartContainerItems.get(n);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        this.minecartContainerItems.set(n, ItemStack.EMPTY);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.addLoot(null);
        this.minecartContainerItems.set(n, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (n >= 0 && n < this.getSizeInventory()) {
            this.setInventorySlotContents(n, itemStack);
            return false;
        }
        return true;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        if (this.removed) {
            return true;
        }
        return !(playerEntity.getDistanceSq(this) > 64.0);
    }

    @Override
    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        this.dropContentsWhenDead = false;
        return super.changeDimension(serverWorld);
    }

    @Override
    public void remove() {
        if (!this.world.isRemote && this.dropContentsWhenDead) {
            InventoryHelper.dropInventoryItems(this.world, this, (IInventory)this);
        }
        super.remove();
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.lootTable != null) {
            compoundNBT.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                compoundNBT.putLong("LootTableSeed", this.lootTableSeed);
            }
        } else {
            ItemStackHelper.saveAllItems(compoundNBT, this.minecartContainerItems);
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.minecartContainerItems = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (compoundNBT.contains("LootTable", 1)) {
            this.lootTable = new ResourceLocation(compoundNBT.getString("LootTable"));
            this.lootTableSeed = compoundNBT.getLong("LootTableSeed");
        } else {
            ItemStackHelper.loadAllItems(compoundNBT, this.minecartContainerItems);
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        playerEntity.openContainer(this);
        if (!playerEntity.world.isRemote) {
            PiglinTasks.func_234478_a_(playerEntity, true);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void applyDrag() {
        float f = 0.98f;
        if (this.lootTable == null) {
            int n = 15 - Container.calcRedstoneFromInventory(this);
            f += (float)n * 0.001f;
        }
        this.setMotion(this.getMotion().mul(f, 0.0, f));
    }

    public void addLoot(@Nullable PlayerEntity playerEntity) {
        if (this.lootTable != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootTableManager().getLootTableFromLocation(this.lootTable);
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLAYER_GENERATES_CONTAINER_LOOT.test((ServerPlayerEntity)playerEntity, this.lootTable);
            }
            this.lootTable = null;
            LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).withParameter(LootParameters.field_237457_g_, this.getPositionVec()).withSeed(this.lootTableSeed);
            if (playerEntity != null) {
                builder.withLuck(playerEntity.getLuck()).withParameter(LootParameters.THIS_ENTITY, playerEntity);
            }
            lootTable.fillInventory(this, builder.build(LootParameterSets.CHEST));
        }
    }

    @Override
    public void clear() {
        this.addLoot(null);
        this.minecartContainerItems.clear();
    }

    public void setLootTable(ResourceLocation resourceLocation, long l) {
        this.lootTable = resourceLocation;
        this.lootTableSeed = l;
    }

    @Override
    @Nullable
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.lootTable != null && playerEntity.isSpectator()) {
            return null;
        }
        this.addLoot(playerInventory.player);
        return this.createContainer(n, playerInventory);
    }

    protected abstract Container createContainer(int var1, PlayerInventory var2);
}

