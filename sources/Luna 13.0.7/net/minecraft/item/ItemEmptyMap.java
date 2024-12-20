package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap
  extends ItemMapBase
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
    var6.scale = 0;
    var6.func_176054_a(playerIn.posX, playerIn.posZ, var6.scale);
    var6.dimension = ((byte)worldIn.provider.getDimensionId());
    var6.markDirty();
    itemStackIn.stackSize -= 1;
    if (itemStackIn.stackSize <= 0) {
      return var4;
    }
    if (!playerIn.inventory.addItemStackToInventory(var4.copy())) {
      playerIn.dropPlayerItemWithRandomChoice(var4, false);
    }
    playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    return itemStackIn;
  }
}
