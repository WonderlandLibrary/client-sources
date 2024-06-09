package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap extends ItemMapBase
{
  private static final String __OBFID = "CL_00000024";
  
  protected ItemEmptyMap()
  {
    setCreativeTab(CreativeTabs.tabMisc);
  }
  



  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
    ItemStack var4 = new ItemStack(Items.filled_map, 1, worldIn.getUniqueDataId("map"));
    String var5 = "map_" + var4.getMetadata();
    MapData var6 = new MapData(var5);
    worldIn.setItemData(var5, var6);
    scale = 0;
    var6.func_176054_a(posX, posZ, scale);
    dimension = ((byte)provider.getDimensionId());
    var6.markDirty();
    stackSize -= 1;
    
    if (stackSize <= 0)
    {
      return var4;
    }
    

    if (!inventory.addItemStackToInventory(var4.copy()))
    {
      playerIn.dropPlayerItemWithRandomChoice(var4, false);
    }
    
    playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    return itemStackIn;
  }
}
