package net.minecraft.entity.ai;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

class EntityMinecartMobSpawner$1 extends MobSpawnerBaseLogic {
    @Override
    public void func_98267_a(final int p_98267_1_) {
        EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
    }
    
    @Override
    public World getSpawnerWorld() {
        return EntityMinecartMobSpawner.this.worldObj;
    }
    
    @Override
    public BlockPos func_177221_b() {
        return new BlockPos(EntityMinecartMobSpawner.this);
    }
}