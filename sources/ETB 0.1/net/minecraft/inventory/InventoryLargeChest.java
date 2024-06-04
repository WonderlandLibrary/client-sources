package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;





public class InventoryLargeChest
  implements ILockableContainer
{
  private String name;
  private ILockableContainer upperChest;
  private ILockableContainer lowerChest;
  private static final String __OBFID = "CL_00001507";
  
  public InventoryLargeChest(String p_i45905_1_, ILockableContainer p_i45905_2_, ILockableContainer p_i45905_3_)
  {
    name = p_i45905_1_;
    
    if (p_i45905_2_ == null)
    {
      p_i45905_2_ = p_i45905_3_;
    }
    
    if (p_i45905_3_ == null)
    {
      p_i45905_3_ = p_i45905_2_;
    }
    
    upperChest = p_i45905_2_;
    lowerChest = p_i45905_3_;
    
    if (p_i45905_2_.isLocked())
    {
      p_i45905_3_.setLockCode(p_i45905_2_.getLockCode());
    }
    else if (p_i45905_3_.isLocked())
    {
      p_i45905_2_.setLockCode(p_i45905_3_.getLockCode());
    }
  }
  



  public int getSizeInventory()
  {
    return upperChest.getSizeInventory() + lowerChest.getSizeInventory();
  }
  



  public boolean isPartOfLargeChest(IInventory p_90010_1_)
  {
    return (upperChest == p_90010_1_) || (lowerChest == p_90010_1_);
  }
  



  public String getName()
  {
    return lowerChest.hasCustomName() ? lowerChest.getName() : upperChest.hasCustomName() ? upperChest.getName() : name;
  }
  



  public boolean hasCustomName()
  {
    return (upperChest.hasCustomName()) || (lowerChest.hasCustomName());
  }
  
  public IChatComponent getDisplayName()
  {
    return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
  }
  



  public ItemStack getStackInSlot(int slotIn)
  {
    return slotIn >= upperChest.getSizeInventory() ? lowerChest.getStackInSlot(slotIn - upperChest.getSizeInventory()) : upperChest.getStackInSlot(slotIn);
  }
  




  public ItemStack decrStackSize(int index, int count)
  {
    return index >= upperChest.getSizeInventory() ? lowerChest.decrStackSize(index - upperChest.getSizeInventory(), count) : upperChest.decrStackSize(index, count);
  }
  




  public ItemStack getStackInSlotOnClosing(int index)
  {
    return index >= upperChest.getSizeInventory() ? lowerChest.getStackInSlotOnClosing(index - upperChest.getSizeInventory()) : upperChest.getStackInSlotOnClosing(index);
  }
  



  public void setInventorySlotContents(int index, ItemStack stack)
  {
    if (index >= upperChest.getSizeInventory())
    {
      lowerChest.setInventorySlotContents(index - upperChest.getSizeInventory(), stack);
    }
    else
    {
      upperChest.setInventorySlotContents(index, stack);
    }
  }
  




  public int getInventoryStackLimit()
  {
    return upperChest.getInventoryStackLimit();
  }
  




  public void markDirty()
  {
    upperChest.markDirty();
    lowerChest.markDirty();
  }
  



  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return (upperChest.isUseableByPlayer(playerIn)) && (lowerChest.isUseableByPlayer(playerIn));
  }
  
  public void openInventory(EntityPlayer playerIn)
  {
    upperChest.openInventory(playerIn);
    lowerChest.openInventory(playerIn);
  }
  
  public void closeInventory(EntityPlayer playerIn)
  {
    upperChest.closeInventory(playerIn);
    lowerChest.closeInventory(playerIn);
  }
  



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
  
  public boolean isLocked()
  {
    return (upperChest.isLocked()) || (lowerChest.isLocked());
  }
  
  public void setLockCode(LockCode code)
  {
    upperChest.setLockCode(code);
    lowerChest.setLockCode(code);
  }
  
  public LockCode getLockCode()
  {
    return upperChest.getLockCode();
  }
  
  public String getGuiID()
  {
    return upperChest.getGuiID();
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
  {
    return new ContainerChest(playerInventory, this, playerIn);
  }
  
  public void clearInventory()
  {
    upperChest.clearInventory();
    lowerChest.clearInventory();
  }
}
