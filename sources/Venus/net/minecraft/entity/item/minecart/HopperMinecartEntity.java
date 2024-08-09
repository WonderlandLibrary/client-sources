/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HopperContainer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.IHopper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class HopperMinecartEntity
extends ContainerMinecartEntity
implements IHopper {
    private boolean isBlocked = true;
    private int transferTicker = -1;
    private final BlockPos lastPosition = BlockPos.ZERO;

    public HopperMinecartEntity(EntityType<? extends HopperMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public HopperMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.HOPPER_MINECART, d, d2, d3, world);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.HOPPER;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return Blocks.HOPPER.getDefaultState();
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 0;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public void onActivatorRailPass(int n, int n2, int n3, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = !bl;
        if (bl2 != this.getBlocked()) {
            this.setBlocked(bl2);
        }
    }

    public boolean getBlocked() {
        return this.isBlocked;
    }

    public void setBlocked(boolean bl) {
        this.isBlocked = bl;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public double getXPos() {
        return this.getPosX();
    }

    @Override
    public double getYPos() {
        return this.getPosY() + 0.5;
    }

    @Override
    public double getZPos() {
        return this.getPosZ();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.isAlive() && this.getBlocked()) {
            BlockPos blockPos = this.getPosition();
            if (blockPos.equals(this.lastPosition)) {
                --this.transferTicker;
            } else {
                this.setTransferTicker(0);
            }
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.captureDroppedItems()) {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }

    public boolean captureDroppedItems() {
        if (HopperTileEntity.pullItems(this)) {
            return false;
        }
        List<Entity> list = this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(0.25, 0.0, 0.25), EntityPredicates.IS_ALIVE);
        if (!list.isEmpty()) {
            HopperTileEntity.captureItem(this, (ItemEntity)list.get(0));
        }
        return true;
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.entityDropItem(Blocks.HOPPER);
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("TransferCooldown", this.transferTicker);
        compoundNBT.putBoolean("Enabled", this.isBlocked);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.transferTicker = compoundNBT.getInt("TransferCooldown");
        this.isBlocked = compoundNBT.contains("Enabled") ? compoundNBT.getBoolean("Enabled") : true;
    }

    public void setTransferTicker(int n) {
        this.transferTicker = n;
    }

    public boolean canTransfer() {
        return this.transferTicker > 0;
    }

    @Override
    public Container createContainer(int n, PlayerInventory playerInventory) {
        return new HopperContainer(n, playerInventory, this);
    }
}

