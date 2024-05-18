package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;




public class EntityAIControlledByPlayer
  extends EntityAIBase
{
  private final EntityLiving thisEntity;
  private final float maxSpeed;
  private float currentSpeed;
  private boolean speedBoosted;
  private int speedBoostTime;
  private int maxSpeedBoostTime;
  private static final String __OBFID = "CL_00001580";
  
  public EntityAIControlledByPlayer(EntityLiving p_i1620_1_, float p_i1620_2_)
  {
    thisEntity = p_i1620_1_;
    maxSpeed = p_i1620_2_;
    setMutexBits(7);
  }
  



  public void startExecuting()
  {
    currentSpeed = 0.0F;
  }
  



  public void resetTask()
  {
    speedBoosted = false;
    currentSpeed = 0.0F;
  }
  



  public boolean shouldExecute()
  {
    return (thisEntity.isEntityAlive()) && (thisEntity.riddenByEntity != null) && ((thisEntity.riddenByEntity instanceof EntityPlayer)) && ((speedBoosted) || (thisEntity.canBeSteered()));
  }
  



  public void updateTask()
  {
    EntityPlayer var1 = (EntityPlayer)thisEntity.riddenByEntity;
    EntityCreature var2 = (EntityCreature)thisEntity;
    float var3 = MathHelper.wrapAngleTo180_float(rotationYaw - thisEntity.rotationYaw) * 0.5F;
    
    if (var3 > 5.0F)
    {
      var3 = 5.0F;
    }
    
    if (var3 < -5.0F)
    {
      var3 = -5.0F;
    }
    
    thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(thisEntity.rotationYaw + var3);
    
    if (currentSpeed < maxSpeed)
    {
      currentSpeed += (maxSpeed - currentSpeed) * 0.01F;
    }
    
    if (currentSpeed > maxSpeed)
    {
      currentSpeed = maxSpeed;
    }
    
    int var4 = MathHelper.floor_double(thisEntity.posX);
    int var5 = MathHelper.floor_double(thisEntity.posY);
    int var6 = MathHelper.floor_double(thisEntity.posZ);
    float var7 = currentSpeed;
    
    if (speedBoosted)
    {
      if (speedBoostTime++ > maxSpeedBoostTime)
      {
        speedBoosted = false;
      }
      
      var7 += var7 * 1.15F * MathHelper.sin(speedBoostTime / maxSpeedBoostTime * 3.1415927F);
    }
    
    float var8 = 0.91F;
    
    if (thisEntity.onGround)
    {
      var8 = thisEntity.worldObj.getBlockState(new BlockPos(MathHelper.floor_float(var4), MathHelper.floor_float(var5) - 1, MathHelper.floor_float(var6))).getBlock().slipperiness * 0.91F;
    }
    
    float var9 = 0.16277136F / (var8 * var8 * var8);
    float var10 = MathHelper.sin(rotationYaw * 3.1415927F / 180.0F);
    float var11 = MathHelper.cos(rotationYaw * 3.1415927F / 180.0F);
    float var12 = var2.getAIMoveSpeed() * var9;
    float var13 = Math.max(var7, 1.0F);
    var13 = var12 / var13;
    float var14 = var7 * var13;
    float var15 = -(var14 * var10);
    float var16 = var14 * var11;
    
    if (MathHelper.abs(var15) > MathHelper.abs(var16))
    {
      if (var15 < 0.0F)
      {
        var15 -= thisEntity.width / 2.0F;
      }
      
      if (var15 > 0.0F)
      {
        var15 += thisEntity.width / 2.0F;
      }
      
      var16 = 0.0F;
    }
    else
    {
      var15 = 0.0F;
      
      if (var16 < 0.0F)
      {
        var16 -= thisEntity.width / 2.0F;
      }
      
      if (var16 > 0.0F)
      {
        var16 += thisEntity.width / 2.0F;
      }
    }
    
    int var17 = MathHelper.floor_double(thisEntity.posX + var15);
    int var18 = MathHelper.floor_double(thisEntity.posZ + var16);
    int var19 = MathHelper.floor_float(thisEntity.width + 1.0F);
    int var20 = MathHelper.floor_float(thisEntity.height + height + 1.0F);
    int var21 = MathHelper.floor_float(thisEntity.width + 1.0F);
    
    if ((var4 != var17) || (var6 != var18))
    {
      Block var22 = thisEntity.worldObj.getBlockState(new BlockPos(var4, var5, var6)).getBlock();
      boolean var23 = (!isStairOrSlab(var22)) && ((var22.getMaterial() != Material.air) || (!isStairOrSlab(thisEntity.worldObj.getBlockState(new BlockPos(var4, var5 - 1, var6)).getBlock())));
      
      if ((var23) && (WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var17, var5, var18, var19, var20, var21, false, false, true) == 0) && (1 == WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var4, var5 + 1, var6, var19, var20, var21, false, false, true)) && (1 == WalkNodeProcessor.func_176170_a(thisEntity.worldObj, thisEntity, var17, var5 + 1, var18, var19, var20, var21, false, false, true)))
      {
        var2.getJumpHelper().setJumping();
      }
    }
    
    if ((!capabilities.isCreativeMode) && (currentSpeed >= maxSpeed * 0.5F) && (thisEntity.getRNG().nextFloat() < 0.006F) && (!speedBoosted))
    {
      ItemStack var24 = var1.getHeldItem();
      
      if ((var24 != null) && (var24.getItem() == Items.carrot_on_a_stick))
      {
        var24.damageItem(1, var1);
        
        if (stackSize == 0)
        {
          ItemStack var25 = new ItemStack(Items.fishing_rod);
          var25.setTagCompound(var24.getTagCompound());
          inventory.mainInventory[inventory.currentItem] = var25;
        }
      }
    }
    
    thisEntity.moveEntityWithHeading(0.0F, var7);
  }
  



  private boolean isStairOrSlab(Block p_151498_1_)
  {
    return ((p_151498_1_ instanceof BlockStairs)) || ((p_151498_1_ instanceof BlockSlab));
  }
  



  public boolean isSpeedBoosted()
  {
    return speedBoosted;
  }
  



  public void boostSpeed()
  {
    speedBoosted = true;
    speedBoostTime = 0;
    maxSpeedBoostTime = (thisEntity.getRNG().nextInt(841) + 140);
  }
  



  public boolean isControlledByPlayer()
  {
    return (!isSpeedBoosted()) && (currentSpeed > maxSpeed * 0.3F);
  }
}
