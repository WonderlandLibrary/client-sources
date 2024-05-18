package net.minecraft.entity.effect;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;






public class EntityLightningBolt
  extends EntityWeatherEffect
{
  private int lightningState;
  public long boltVertex;
  private int boltLivingTime;
  private static final String __OBFID = "CL_00001666";
  
  public EntityLightningBolt(World worldIn, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_)
  {
    super(worldIn);
    setLocationAndAngles(p_i1703_2_, p_i1703_4_, p_i1703_6_, 0.0F, 0.0F);
    lightningState = 2;
    boltVertex = rand.nextLong();
    boltLivingTime = (rand.nextInt(3) + 1);
    
    if ((!isRemote) && (worldIn.getGameRules().getGameRuleBooleanValue("doFireTick")) && ((worldIn.getDifficulty() == EnumDifficulty.NORMAL) || (worldIn.getDifficulty() == EnumDifficulty.HARD)) && (worldIn.isAreaLoaded(new BlockPos(this), 10)))
    {
      BlockPos var8 = new BlockPos(this);
      
      if ((worldIn.getBlockState(var8).getBlock().getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(worldIn, var8)))
      {
        worldIn.setBlockState(var8, Blocks.fire.getDefaultState());
      }
      
      for (int var9 = 0; var9 < 4; var9++)
      {
        BlockPos var10 = var8.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
        
        if ((worldIn.getBlockState(var10).getBlock().getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(worldIn, var10)))
        {
          worldIn.setBlockState(var10, Blocks.fire.getDefaultState());
        }
      }
    }
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (lightningState == 2)
    {
      worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
      worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 2.0F, 0.5F + rand.nextFloat() * 0.2F);
    }
    
    lightningState -= 1;
    
    if (lightningState < 0)
    {
      if (boltLivingTime == 0)
      {
        setDead();
      }
      else if (lightningState < -rand.nextInt(10))
      {
        boltLivingTime -= 1;
        lightningState = 1;
        boltVertex = rand.nextLong();
        BlockPos var1 = new BlockPos(this);
        
        if ((!worldObj.isRemote) && (worldObj.getGameRules().getGameRuleBooleanValue("doFireTick")) && (worldObj.isAreaLoaded(var1, 10)) && (worldObj.getBlockState(var1).getBlock().getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(worldObj, var1)))
        {
          worldObj.setBlockState(var1, Blocks.fire.getDefaultState());
        }
      }
    }
    
    if (lightningState >= 0)
    {
      if (worldObj.isRemote)
      {
        worldObj.setLastLightningBolt(2);
      }
      else
      {
        double var6 = 3.0D;
        List var3 = worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(posX - var6, posY - var6, posZ - var6, posX + var6, posY + 6.0D + var6, posZ + var6));
        
        for (int var4 = 0; var4 < var3.size(); var4++)
        {
          Entity var5 = (Entity)var3.get(var4);
          var5.onStruckByLightning(this);
        }
      }
    }
  }
  
  protected void entityInit() {}
  
  protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
  
  protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
}
