package net.minecraft.item;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.World;

public class ItemEgg extends Item
{
  private static final String __OBFID = "CL_00000023";
  
  public ItemEgg()
  {
    maxStackSize = 16;
    setCreativeTab(CreativeTabs.tabMaterials);
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
      worldIn.spawnEntityInWorld(new net.minecraft.entity.projectile.EntityEgg(worldIn, playerIn));
    }
    
    playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    return itemStackIn;
  }
}
