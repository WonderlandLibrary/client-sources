package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;



public class ContainerPlayer
  extends Container
{
  public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
  public IInventory craftResult = new InventoryCraftResult();
  
  public boolean isLocalWorld;
  
  private final EntityPlayer thePlayer;
  private static final String __OBFID = "CL_00001754";
  
  public ContainerPlayer(InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer p_i1819_3_)
  {
    isLocalWorld = p_i1819_2_;
    thePlayer = p_i1819_3_;
    addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 144, 36));
    


    for (int var4 = 0; var4 < 2; var4++)
    {
      for (int var5 = 0; var5 < 2; var5++)
      {
        addSlotToContainer(new Slot(craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
      }
    }
    
    for (var4 = 0; var4 < 4; var4++)
    {
      final int var44 = var4;
      addSlotToContainer(new Slot(p_i1819_1_, p_i1819_1_.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18)
      {
        private static final String __OBFID = "CL_00001755";
        
        public int getSlotStackLimit() {
          return 1;
        }
        
        public boolean isItemValid(ItemStack stack) {
          return stack != null;
        }
        
        public String func_178171_c() {
          return ItemArmor.EMPTY_SLOT_NAMES[var44];
        }
      });
    }
    
    for (var4 = 0; var4 < 3; var4++)
    {
      for (int var5 = 0; var5 < 9; var5++)
      {
        addSlotToContainer(new Slot(p_i1819_1_, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
      }
    }
    
    for (var4 = 0; var4 < 9; var4++)
    {
      addSlotToContainer(new Slot(p_i1819_1_, var4, 8 + var4 * 18, 142));
    }
    
    onCraftMatrixChanged(craftMatrix);
  }
  



  public void onCraftMatrixChanged(IInventory p_75130_1_)
  {
    craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, thePlayer.worldObj));
  }
  



  public void onContainerClosed(EntityPlayer p_75134_1_)
  {
    super.onContainerClosed(p_75134_1_);
    
    for (int var2 = 0; var2 < 4; var2++)
    {
      ItemStack var3 = craftMatrix.getStackInSlotOnClosing(var2);
      
      if (var3 != null)
      {
        p_75134_1_.dropPlayerItemWithRandomChoice(var3, false);
      }
    }
    
    craftResult.setInventorySlotContents(0, null);
  }
  
  public boolean canInteractWith(EntityPlayer playerIn)
  {
    return true;
  }
  



  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    ItemStack var3 = null;
    Slot var4 = (Slot)inventorySlots.get(index);
    
    if ((var4 != null) && (var4.getHasStack()))
    {
      ItemStack var5 = var4.getStack();
      var3 = var5.copy();
      
      if (index == 0)
      {
        if (!mergeItemStack(var5, 9, 45, true))
        {
          return null;
        }
        
        var4.onSlotChange(var5, var3);
      }
      else if ((index >= 1) && (index < 5))
      {
        if (!mergeItemStack(var5, 9, 45, false))
        {
          return null;
        }
      }
      else if ((index >= 5) && (index < 9))
      {
        if (!mergeItemStack(var5, 9, 45, false))
        {
          return null;
        }
      }
      else if (((var3.getItem() instanceof ItemArmor)) && (!((Slot)inventorySlots.get(5 + getItemarmorType)).getHasStack()))
      {
        int var6 = 5 + getItemarmorType;
        
        if (!mergeItemStack(var5, var6, var6 + 1, false))
        {
          return null;
        }
      }
      else if ((index >= 9) && (index < 36))
      {
        if (!mergeItemStack(var5, 36, 45, false))
        {
          return null;
        }
      }
      else if ((index >= 36) && (index < 45))
      {
        if (!mergeItemStack(var5, 9, 36, false))
        {
          return null;
        }
      }
      else if (!mergeItemStack(var5, 9, 45, false))
      {
        return null;
      }
      
      if (stackSize == 0)
      {
        var4.putStack(null);
      }
      else
      {
        var4.onSlotChanged();
      }
      
      if (stackSize == stackSize)
      {
        return null;
      }
      
      var4.onPickupFromSlot(playerIn, var5);
    }
    
    return var3;
  }
  
  public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
  {
    return (inventory != craftResult) && (super.func_94530_a(p_94530_1_, p_94530_2_));
  }
}
