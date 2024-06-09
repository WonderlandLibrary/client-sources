package net.minecraft.item;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.world.World;

public class ItemSnowball extends Item
{
  private static final String __OBFID = "CL_00000069";
  
  public ItemSnowball()
  {
    maxStackSize = 16;
    setCreativeTab(CreativeTabs.tabMisc);
  }
  



  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
    if (!capabilities.isCreativeMode)
    {
      stackSize -= 1;
    }
    
    worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    
    if (!isRemote)
    {
      worldIn.spawnEntityInWorld(new EntitySnowball(worldIn, playerIn));
    }
    
    playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    return itemStackIn;
  }
}
