/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

public class SpawnerMinecartEntity
extends AbstractMinecartEntity {
    private final AbstractSpawner mobSpawnerLogic = new AbstractSpawner(this){
        final SpawnerMinecartEntity this$0;
        {
            this.this$0 = spawnerMinecartEntity;
        }

        @Override
        public void broadcastEvent(int n) {
            this.this$0.world.setEntityState(this.this$0, (byte)n);
        }

        @Override
        public World getWorld() {
            return this.this$0.world;
        }

        @Override
        public BlockPos getSpawnerPosition() {
            return this.this$0.getPosition();
        }
    };

    public SpawnerMinecartEntity(EntityType<? extends SpawnerMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpawnerMinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.SPAWNER_MINECART, world, d, d2, d3);
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.SPAWNER;
    }

    @Override
    public BlockState getDefaultDisplayTile() {
        return Blocks.SPAWNER.getDefaultState();
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.mobSpawnerLogic.read(compoundNBT);
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        this.mobSpawnerLogic.write(compoundNBT);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        this.mobSpawnerLogic.setDelayToMin(by);
    }

    @Override
    public void tick() {
        super.tick();
        this.mobSpawnerLogic.tick();
    }

    @Override
    public boolean ignoreItemEntityData() {
        return false;
    }
}

