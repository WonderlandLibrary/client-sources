package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;





public class SlotMerchantResult
  extends Slot
{
  private final InventoryMerchant theMerchantInventory;
  private EntityPlayer thePlayer;
  private int field_75231_g;
  private final IMerchant theMerchant;
  private static final String __OBFID = "CL_00001758";
  
  public SlotMerchantResult(EntityPlayer p_i1822_1_, IMerchant p_i1822_2_, InventoryMerchant p_i1822_3_, int p_i1822_4_, int p_i1822_5_, int p_i1822_6_)
  {
    super(p_i1822_3_, p_i1822_4_, p_i1822_5_, p_i1822_6_);
    thePlayer = p_i1822_1_;
    theMerchant = p_i1822_2_;
    theMerchantInventory = p_i1822_3_;
  }
  



  public boolean isItemValid(ItemStack stack)
  {
    return false;
  }
  




  public ItemStack decrStackSize(int p_75209_1_)
  {
    if (getHasStack())
    {
      field_75231_g += Math.min(p_75209_1_, getStackstackSize);
    }
    
    return super.decrStackSize(p_75209_1_);
  }
  




  protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
  {
    field_75231_g += p_75210_2_;
    onCrafting(p_75210_1_);
  }
  



  protected void onCrafting(ItemStack p_75208_1_)
  {
    p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, field_75231_g);
    field_75231_g = 0;
  }
  
  public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
  {
    onCrafting(stack);
    MerchantRecipe var3 = theMerchantInventory.getCurrentRecipe();
    
    if (var3 != null)
    {
      ItemStack var4 = theMerchantInventory.getStackInSlot(0);
      ItemStack var5 = theMerchantInventory.getStackInSlot(1);
      
      if ((doTrade(var3, var4, var5)) || (doTrade(var3, var5, var4)))
      {
        theMerchant.useRecipe(var3);
        playerIn.triggerAchievement(StatList.timesTradedWithVillagerStat);
        
        if ((var4 != null) && (stackSize <= 0))
        {
          var4 = null;
        }
        
        if ((var5 != null) && (stackSize <= 0))
        {
          var5 = null;
        }
        
        theMerchantInventory.setInventorySlotContents(0, var4);
        theMerchantInventory.setInventorySlotContents(1, var5);
      }
    }
  }
  
  private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem)
  {
    ItemStack var4 = trade.getItemToBuy();
    ItemStack var5 = trade.getSecondItemToBuy();
    
    if ((firstItem != null) && (firstItem.getItem() == var4.getItem()))
    {
      if ((var5 != null) && (secondItem != null) && (var5.getItem() == secondItem.getItem()))
      {
        stackSize -= stackSize;
        stackSize -= stackSize;
        return true;
      }
      
      if ((var5 == null) && (secondItem == null))
      {
        stackSize -= stackSize;
        return true;
      }
    }
    
    return false;
  }
}
