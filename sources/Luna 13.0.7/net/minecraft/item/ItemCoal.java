package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCoal
  extends Item
{
  private static final String __OBFID = "CL_00000002";
  
  public ItemCoal()
  {
    setHasSubtypes(true);
    setMaxDamage(0);
    setCreativeTab(CreativeTabs.tabMaterials);
  }
  
  public String getUnlocalizedName(ItemStack stack)
  {
    return stack.getMetadata() == 1 ? "item.charcoal" : "item.coal";
  }
  
  public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
  {
    subItems.add(new ItemStack(itemIn, 1, 0));
    subItems.add(new ItemStack(itemIn, 1, 1));
  }
}
