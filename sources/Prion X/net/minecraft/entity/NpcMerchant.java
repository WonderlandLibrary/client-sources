package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;





public class NpcMerchant
  implements IMerchant
{
  private InventoryMerchant theMerchantInventory;
  private EntityPlayer customer;
  private MerchantRecipeList recipeList;
  private IChatComponent field_175548_d;
  private static final String __OBFID = "CL_00001705";
  
  public NpcMerchant(EntityPlayer p_i45817_1_, IChatComponent p_i45817_2_)
  {
    customer = p_i45817_1_;
    field_175548_d = p_i45817_2_;
    theMerchantInventory = new InventoryMerchant(p_i45817_1_, this);
  }
  
  public EntityPlayer getCustomer()
  {
    return customer;
  }
  
  public void setCustomer(EntityPlayer p_70932_1_) {}
  
  public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_)
  {
    return recipeList;
  }
  
  public void setRecipes(MerchantRecipeList p_70930_1_)
  {
    recipeList = p_70930_1_;
  }
  
  public void useRecipe(MerchantRecipe p_70933_1_)
  {
    p_70933_1_.incrementToolUses();
  }
  


  public void verifySellingItem(ItemStack p_110297_1_) {}
  


  public IChatComponent getDisplayName()
  {
    return field_175548_d != null ? field_175548_d : new ChatComponentTranslation("entity.Villager.name", new Object[0]);
  }
}
