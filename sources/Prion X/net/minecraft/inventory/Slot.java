package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;










public class Slot
{
  private final int slotIndex;
  public final IInventory inventory;
  public int slotNumber;
  public int xDisplayPosition;
  public int yDisplayPosition;
  private static final String __OBFID = "CL_00001762";
  
  public Slot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_)
  {
    inventory = p_i1824_1_;
    slotIndex = p_i1824_2_;
    xDisplayPosition = p_i1824_3_;
    yDisplayPosition = p_i1824_4_;
  }
  



  public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_)
  {
    if ((p_75220_1_ != null) && (p_75220_2_ != null))
    {
      if (p_75220_1_.getItem() == p_75220_2_.getItem())
      {
        int var3 = stackSize - stackSize;
        
        if (var3 > 0)
        {
          onCrafting(p_75220_1_, var3);
        }
      }
    }
  }
  



  protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {}
  


  protected void onCrafting(ItemStack p_75208_1_) {}
  


  public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
  {
    onSlotChanged();
  }
  



  public boolean isItemValid(ItemStack stack)
  {
    return true;
  }
  



  public ItemStack getStack()
  {
    return inventory.getStackInSlot(slotIndex);
  }
  



  public boolean getHasStack()
  {
    return getStack() != null;
  }
  



  public void putStack(ItemStack p_75215_1_)
  {
    inventory.setInventorySlotContents(slotIndex, p_75215_1_);
    onSlotChanged();
  }
  



  public void onSlotChanged()
  {
    inventory.markDirty();
  }
  




  public int getSlotStackLimit()
  {
    return inventory.getInventoryStackLimit();
  }
  
  public int func_178170_b(ItemStack p_178170_1_)
  {
    return getSlotStackLimit();
  }
  
  public String func_178171_c()
  {
    return null;
  }
  




  public ItemStack decrStackSize(int p_75209_1_)
  {
    return inventory.decrStackSize(slotIndex, p_75209_1_);
  }
  



  public boolean isHere(IInventory p_75217_1_, int p_75217_2_)
  {
    return (p_75217_1_ == inventory) && (p_75217_2_ == slotIndex);
  }
  



  public boolean canTakeStack(EntityPlayer p_82869_1_)
  {
    return true;
  }
  




  public boolean canBeHovered()
  {
    return true;
  }
}
