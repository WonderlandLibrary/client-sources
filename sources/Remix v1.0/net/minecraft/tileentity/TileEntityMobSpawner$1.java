package net.minecraft.tileentity;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

class TileEntityMobSpawner$1 extends MobSpawnerBaseLogic {
    @Override
    public void func_98267_a(final int p_98267_1_) {
        TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner, p_98267_1_, 0);
    }
    
    @Override
    public World getSpawnerWorld() {
        return TileEntityMobSpawner.this.worldObj;
    }
    
    @Override
    public BlockPos func_177221_b() {
        return TileEntityMobSpawner.this.pos;
    }
    
    @Override
    public void setRandomEntity(final WeightedRandomMinecart p_98277_1_) {
        super.setRandomEntity(p_98277_1_);
        if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
        }
    }
}