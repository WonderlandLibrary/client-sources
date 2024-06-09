package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory
{
  private final IMerchant theMerchant;
  private ItemStack[] theInventory = new ItemStack[3];
  private final EntityPlayer thePlayer;
  private MerchantRecipe currentRecipe;
  private int currentRecipeIndex;
  private static final String __OBFID = "CL_00001756";
  
  public InventoryMerchant(EntityPlayer p_i1820_1_, IMerchant p_i1820_2_)
  {
    thePlayer = p_i1820_1_;
    theMerchant = p_i1820_2_;
  }
  



  public int getSizeInventory()
  {
    return theInventory.length;
  }
  



  public ItemStack getStackInSlot(int slotIn)
  {
    return theInventory[slotIn];
  }
  




  public ItemStack decrStackSize(int index, int count)
  {
    if (theInventory[index] != null)
    {


      if (index == 2)
      {
        ItemStack var3 = theInventory[index];
        theInventory[index] = null;
        return var3;
      }
      if (theInventory[index].stackSize <= count)
      {
        ItemStack var3 = theInventory[index];
        theInventory[index] = null;
        
        if (inventoryResetNeededOnSlotChange(index))
        {
          resetRecipeAndSlots();
        }
        
        return var3;
      }
      

      ItemStack var3 = theInventory[index].splitStack(count);
      
      if (theInventory[index].stackSize == 0)
      {
        theInventory[index] = null;
      }
      
      if (inventoryResetNeededOnSlotChange(index))
      {
        resetRecipeAndSlots();
      }
      
      return var3;
    }
    


    return null;
  }
  




  private boolean inventoryResetNeededOnSlotChange(int p_70469_1_)
  {
    return (p_70469_1_ == 0) || (p_70469_1_ == 1);
  }
  




  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (theInventory[index] != null)
    {
      ItemStack var2 = theInventory[index];
      theInventory[index] = null;
      return var2;
    }
    

    return null;
  }
  




  public void setInventorySlotContents(int index, ItemStack stack)
  {
    theInventory[index] = stack;
    
    if ((stack != null) && (stackSize > getInventoryStackLimit()))
    {
      stackSize = getInventoryStackLimit();
    }
    
    if (inventoryResetNeededOnSlotChange(index))
    {
      resetRecipeAndSlots();
    }
  }
  



  public String getName()
  {
    return "mob.villager";
  }
  



  public boolean hasCustomName()
  {
    return false;
  }
  
  public IChatComponent getDisplayName()
  {
    return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
  }
  




  public int getInventoryStackLimit()
  {
    return 64;
  }
  



  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return theMerchant.getCustomer() == playerIn;
  }
  

  public void openInventory(EntityPlayer playerIn) {}
  

  public void closeInventory(EntityPlayer playerIn) {}
  

  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return true;
  }
  




  public void markDirty()
  {
    resetRecipeAndSlots();
  }
  
  public void resetRecipeAndSlots()
  {
    currentRecipe = null;
    ItemStack var1 = theInventory[0];
    ItemStack var2 = theInventory[1];
    
    if (var1 == null)
    {
      var1 = var2;
      var2 = null;
    }
    
    if (var1 == null)
    {
      setInventorySlotContents(2, null);
    }
    else
    {
      MerchantRecipeList var3 = theMerchant.getRecipes(thePlayer);
      
      if (var3 != null)
      {
        MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, currentRecipeIndex);
        
        if ((var4 != null) && (!var4.isRecipeDisabled()))
        {
          currentRecipe = var4;
          setInventorySlotContents(2, var4.getItemToSell().copy());
        }
        else if (var2 != null)
        {
          var4 = var3.canRecipeBeUsed(var2, var1, currentRecipeIndex);
          
          if ((var4 != null) && (!var4.isRecipeDisabled()))
          {
            currentRecipe = var4;
            setInventorySlotContents(2, var4.getItemToSell().copy());
          }
          else
          {
            setInventorySlotContents(2, null);
          }
        }
        else
        {
          setInventorySlotContents(2, null);
        }
      }
    }
    
    theMerchant.verifySellingItem(getStackInSlot(2));
  }
  
  public MerchantRecipe getCurrentRecipe()
  {
    return currentRecipe;
  }
  
  public void setCurrentRecipeIndex(int p_70471_1_)
  {
    currentRecipeIndex = p_70471_1_;
    resetRecipeAndSlots();
  }
  
  public int getField(int id)
  {
    return 0;
  }
  
  public void setField(int id, int value) {}
  
  public int getFieldCount()
  {
    return 0;
  }
  
  public void clearInventory()
  {
    for (int var1 = 0; var1 < theInventory.length; var1++)
    {
      theInventory[var1] = null;
    }
  }
}
