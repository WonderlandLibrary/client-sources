/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

public class MobSpawnerTileEntity
extends TileEntity
implements ITickableTileEntity {
    private final AbstractSpawner spawnerLogic = new AbstractSpawner(this){
        final MobSpawnerTileEntity this$0;
        {
            this.this$0 = mobSpawnerTileEntity;
        }

        @Override
        public void broadcastEvent(int n) {
            this.this$0.world.addBlockEvent(this.this$0.pos, Blocks.SPAWNER, n, 0);
        }

        @Override
        public World getWorld() {
            return this.this$0.world;
        }

        @Override
        public BlockPos getSpawnerPosition() {
            return this.this$0.pos;
        }

        @Override
        public void setNextSpawnData(WeightedSpawnerEntity weightedSpawnerEntity) {
            super.setNextSpawnData(weightedSpawnerEntity);
            if (this.getWorld() != null) {
                BlockState blockState = this.getWorld().getBlockState(this.getSpawnerPosition());
                this.getWorld().notifyBlockUpdate(this.this$0.pos, blockState, blockState, 4);
            }
        }
    };

    public MobSpawnerTileEntity() {
        super(TileEntityType.MOB_SPAWNER);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.spawnerLogic.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        this.spawnerLogic.write(compoundNBT);
        return compoundNBT;
    }

    @Override
    public void tick() {
        this.spawnerLogic.tick();
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = this.write(new CompoundNBT());
        compoundNBT.remove("SpawnPotentials");
        return compoundNBT;
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        return this.spawnerLogic.setDelayToMin(n) ? true : super.receiveClientEvent(n, n2);
    }

    @Override
    public boolean onlyOpsCanSetNbt() {
        return false;
    }

    public AbstractSpawner getSpawnerBaseLogic() {
        return this.spawnerLogic;
    }
}

