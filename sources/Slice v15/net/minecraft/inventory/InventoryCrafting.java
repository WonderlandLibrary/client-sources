package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;







public class InventoryCrafting
  implements IInventory
{
  private final ItemStack[] stackList;
  private final int inventoryWidth;
  private final int field_174924_c;
  private final Container eventHandler;
  private static final String __OBFID = "CL_00001743";
  
  public InventoryCrafting(Container p_i1807_1_, int p_i1807_2_, int p_i1807_3_)
  {
    int var4 = p_i1807_2_ * p_i1807_3_;
    stackList = new ItemStack[var4];
    eventHandler = p_i1807_1_;
    inventoryWidth = p_i1807_2_;
    field_174924_c = p_i1807_3_;
  }
  



  public int getSizeInventory()
  {
    return stackList.length;
  }
  



  public ItemStack getStackInSlot(int slotIn)
  {
    return slotIn >= getSizeInventory() ? null : stackList[slotIn];
  }
  



  public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_)
  {
    return (p_70463_1_ >= 0) && (p_70463_1_ < inventoryWidth) && (p_70463_2_ >= 0) && (p_70463_2_ <= field_174924_c) ? getStackInSlot(p_70463_1_ + p_70463_2_ * inventoryWidth) : null;
  }
  



  public String getName()
  {
    return "container.crafting";
  }
  



  public boolean hasCustomName()
  {
    return false;
  }
  
  public IChatComponent getDisplayName()
  {
    return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
  }
  




  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (stackList[index] != null)
    {
      ItemStack var2 = stackList[index];
      stackList[index] = null;
      return var2;
    }
    

    return null;
  }
  





  public ItemStack decrStackSize(int index, int count)
  {
    if (stackList[index] != null)
    {


      if (stackList[index].stackSize <= count)
      {
        ItemStack var3 = stackList[index];
        stackList[index] = null;
        eventHandler.onCraftMatrixChanged(this);
        return var3;
      }
      

      ItemStack var3 = stackList[index].splitStack(count);
      
      if (stackList[index].stackSize == 0)
      {
        stackList[index] = null;
      }
      
      eventHandler.onCraftMatrixChanged(this);
      return var3;
    }
    


    return null;
  }
  




  public void setInventorySlotContents(int index, ItemStack stack)
  {
    stackList[index] = stack;
    eventHandler.onCraftMatrixChanged(this);
  }
  




  public int getInventoryStackLimit()
  {
    return 64;
  }
  




  public void markDirty() {}
  



  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return true;
  }
  

  public void openInventory(EntityPlayer playerIn) {}
  

  public void closeInventory(EntityPlayer playerIn) {}
  

  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return true;
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
    for (int var1 = 0; var1 < stackList.length; var1++)
    {
      stackList[var1] = null;
    }
  }
  
  public int func_174923_h()
  {
    return field_174924_c;
  }
  
  public int func_174922_i()
  {
    return inventoryWidth;
  }
}
