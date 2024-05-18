package net.minecraft.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item
{
  private Block isFull;
  private static final String __OBFID = "CL_00000000";
  
  public ItemBucket(Block p_i45331_1_)
  {
    maxStackSize = 1;
    isFull = p_i45331_1_;
    setCreativeTab(CreativeTabs.tabMisc);
  }
  



  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
    boolean var4 = isFull == Blocks.air;
    MovingObjectPosition var5 = getMovingObjectPositionFromPlayer(worldIn, playerIn, var4);
    
    if (var5 == null)
    {
      return itemStackIn;
    }
    

    if (typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK)
    {
      BlockPos var6 = var5.func_178782_a();
      
      if (!worldIn.isBlockModifiable(playerIn, var6))
      {
        return itemStackIn;
      }
      
      if (var4)
      {
        if (!playerIn.func_175151_a(var6.offset(field_178784_b), field_178784_b, itemStackIn))
        {
          return itemStackIn;
        }
        
        IBlockState var7 = worldIn.getBlockState(var6);
        Material var8 = var7.getBlock().getMaterial();
        
        if ((var8 == Material.water) && (((Integer)var7.getValue(BlockLiquid.LEVEL)).intValue() == 0))
        {
          worldIn.setBlockToAir(var6);
          playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
          return func_150910_a(itemStackIn, playerIn, Items.water_bucket);
        }
        
        if ((var8 == Material.lava) && (((Integer)var7.getValue(BlockLiquid.LEVEL)).intValue() == 0))
        {
          worldIn.setBlockToAir(var6);
          playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
          return func_150910_a(itemStackIn, playerIn, Items.lava_bucket);
        }
      }
      else
      {
        if (isFull == Blocks.air)
        {
          return new ItemStack(Items.bucket);
        }
        
        BlockPos var9 = var6.offset(field_178784_b);
        
        if (!playerIn.func_175151_a(var9, field_178784_b, itemStackIn))
        {
          return itemStackIn;
        }
        
        if ((func_180616_a(worldIn, var9)) && (!capabilities.isCreativeMode))
        {
          playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
          return new ItemStack(Items.bucket);
        }
      }
    }
    
    return itemStackIn;
  }
  

  private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_)
  {
    if (capabilities.isCreativeMode)
    {
      return p_150910_1_;
    }
    if (--stackSize <= 0)
    {
      return new ItemStack(p_150910_3_);
    }
    

    if (!inventory.addItemStackToInventory(new ItemStack(p_150910_3_)))
    {
      p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
    }
    
    return p_150910_1_;
  }
  

  public boolean func_180616_a(World worldIn, BlockPos p_180616_2_)
  {
    if (isFull == Blocks.air)
    {
      return false;
    }
    

    Material var3 = worldIn.getBlockState(p_180616_2_).getBlock().getMaterial();
    boolean var4 = !var3.isSolid();
    
    if ((!worldIn.isAirBlock(p_180616_2_)) && (!var4))
    {
      return false;
    }
    

    if ((provider.func_177500_n()) && (isFull == Blocks.flowing_water))
    {
      int var5 = p_180616_2_.getX();
      int var6 = p_180616_2_.getY();
      int var7 = p_180616_2_.getZ();
      worldIn.playSoundEffect(var5 + 0.5F, var6 + 0.5F, var7 + 0.5F, "random.fizz", 0.5F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
      
      for (int var8 = 0; var8 < 8; var8++)
      {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var5 + Math.random(), var6 + Math.random(), var7 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    else
    {
      if ((!isRemote) && (var4) && (!var3.isLiquid()))
      {
        worldIn.destroyBlock(p_180616_2_, true);
      }
      
      worldIn.setBlockState(p_180616_2_, isFull.getDefaultState(), 3);
    }
    
    return true;
  }
}
