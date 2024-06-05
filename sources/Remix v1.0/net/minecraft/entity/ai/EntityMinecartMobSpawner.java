package net.minecraft.entity.ai;

import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    
    public EntityMinecartMobSpawner(final World worldIn) {
        super(worldIn);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
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
        };
    }
    
    public EntityMinecartMobSpawner(final World worldIn, final double p_i1726_2_, final double p_i1726_4_, final double p_i1726_6_) {
        super(worldIn, p_i1726_2_, p_i1726_4_, p_i1726_6_);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
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
        };
    }
    
    @Override
    public EnumMinecartType func_180456_s() {
        return EnumMinecartType.SPAWNER;
    }
    
    @Override
    public IBlockState func_180457_u() {
        return Blocks.mob_spawner.getDefaultState();
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.mobSpawnerLogic.readFromNBT(tagCompund);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        this.mobSpawnerLogic.writeToNBT(tagCompound);
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        this.mobSpawnerLogic.setDelayToMin(p_70103_1_);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
    
    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }
}
