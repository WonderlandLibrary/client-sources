package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerFurnace extends Container
{
  private final IInventory tileFurnace;
  private int field_178152_f;
  private int field_178153_g;
  private int field_178154_h;
  private int field_178155_i;
  private static final String __OBFID = "CL_00001748";
  
  public ContainerFurnace(InventoryPlayer p_i45794_1_, IInventory p_i45794_2_)
  {
    tileFurnace = p_i45794_2_;
    addSlotToContainer(new Slot(p_i45794_2_, 0, 56, 17));
    addSlotToContainer(new SlotFurnaceFuel(p_i45794_2_, 1, 56, 53));
    addSlotToContainer(new SlotFurnaceOutput(player, p_i45794_2_, 2, 116, 35));
    

    for (int var3 = 0; var3 < 3; var3++)
    {
      for (int var4 = 0; var4 < 9; var4++)
      {
        addSlotToContainer(new Slot(p_i45794_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      addSlotToContainer(new Slot(p_i45794_1_, var3, 8 + var3 * 18, 142));
    }
  }
  
  public void onCraftGuiOpened(ICrafting p_75132_1_)
  {
    super.onCraftGuiOpened(p_75132_1_);
    p_75132_1_.func_175173_a(this, tileFurnace);
  }
  



  public void detectAndSendChanges()
  {
    super.detectAndSendChanges();
    
    for (int var1 = 0; var1 < crafters.size(); var1++)
    {
      ICrafting var2 = (ICrafting)crafters.get(var1);
      
      if (field_178152_f != tileFurnace.getField(2))
      {
        var2.sendProgressBarUpdate(this, 2, tileFurnace.getField(2));
      }
      
      if (field_178154_h != tileFurnace.getField(0))
      {
        var2.sendProgressBarUpdate(this, 0, tileFurnace.getField(0));
      }
      
      if (field_178155_i != tileFurnace.getField(1))
      {
        var2.sendProgressBarUpdate(this, 1, tileFurnace.getField(1));
      }
      
      if (field_178153_g != tileFurnace.getField(3))
      {
        var2.sendProgressBarUpdate(this, 3, tileFurnace.getField(3));
      }
    }
    
    field_178152_f = tileFurnace.getField(2);
    field_178154_h = tileFurnace.getField(0);
    field_178155_i = tileFurnace.getField(1);
    field_178153_g = tileFurnace.getField(3);
  }
  
  public void updateProgressBar(int p_75137_1_, int p_75137_2_)
  {
    tileFurnace.setField(p_75137_1_, p_75137_2_);
  }
  
  public boolean canInteractWith(EntityPlayer playerIn)
  {
    return tileFurnace.isUseableByPlayer(playerIn);
  }
  



  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
  {
    ItemStack var3 = null;
    Slot var4 = (Slot)inventorySlots.get(index);
    
    if ((var4 != null) && (var4.getHasStack()))
    {
      ItemStack var5 = var4.getStack();
      var3 = var5.copy();
      
      if (index == 2)
      {
        if (!mergeItemStack(var5, 3, 39, true))
        {
          return null;
        }
        
        var4.onSlotChange(var5, var3);
      }
      else if ((index != 1) && (index != 0))
      {
        if (FurnaceRecipes.instance().getSmeltingResult(var5) != null)
        {
          if (!mergeItemStack(var5, 0, 1, false))
          {
            return null;
          }
        }
        else if (net.minecraft.tileentity.TileEntityFurnace.isItemFuel(var5))
        {
          if (!mergeItemStack(var5, 1, 2, false))
          {
            return null;
          }
        }
        else if ((index >= 3) && (index < 30))
        {
          if (!mergeItemStack(var5, 30, 39, false))
          {
            return null;
          }
        }
        else if ((index >= 30) && (index < 39) && (!mergeItemStack(var5, 3, 30, false)))
        {
          return null;
        }
      }
      else if (!mergeItemStack(var5, 3, 39, false))
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
}
