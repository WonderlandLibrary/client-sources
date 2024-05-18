package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart.EnumMinecartType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart
{
  private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic()
  {
    private static final String __OBFID = "CL_00001679";
    
    public void func_98267_a(int p_98267_1_) {
      worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
    }
    
    public World getSpawnerWorld() {
      return worldObj;
    }
    
    public BlockPos func_177221_b() {
      return new BlockPos(EntityMinecartMobSpawner.this);
    }
  };
  private static final String __OBFID = "CL_00001678";
  
  public EntityMinecartMobSpawner(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityMinecartMobSpawner(World worldIn, double p_i1726_2_, double p_i1726_4_, double p_i1726_6_)
  {
    super(worldIn, p_i1726_2_, p_i1726_4_, p_i1726_6_);
  }
  
  public EntityMinecart.EnumMinecartType func_180456_s()
  {
    return EntityMinecart.EnumMinecartType.SPAWNER;
  }
  
  public IBlockState func_180457_u()
  {
    return net.minecraft.init.Blocks.mob_spawner.getDefaultState();
  }
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    mobSpawnerLogic.readFromNBT(tagCompund);
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    mobSpawnerLogic.writeToNBT(tagCompound);
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    mobSpawnerLogic.setDelayToMin(p_70103_1_);
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    mobSpawnerLogic.updateSpawner();
  }
  
  public MobSpawnerBaseLogic func_98039_d()
  {
    return mobSpawnerLogic;
  }
}
