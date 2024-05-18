package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityFurnace extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory
{
  private static final int[] slotsTop = new int[1];
  private static final int[] slotsBottom = { 2, 1 };
  private static final int[] slotsSides = { 1 };
  



  private ItemStack[] furnaceItemStacks = new ItemStack[3];
  
  private int furnaceBurnTime;
  
  private int currentItemBurnTime;
  
  private int field_174906_k;
  
  private int field_174905_l;
  
  private String furnaceCustomName;
  
  private static final String __OBFID = "CL_00000357";
  

  public TileEntityFurnace() {}
  
  public int getSizeInventory()
  {
    return furnaceItemStacks.length;
  }
  



  public ItemStack getStackInSlot(int slotIn)
  {
    return furnaceItemStacks[slotIn];
  }
  




  public ItemStack decrStackSize(int index, int count)
  {
    if (furnaceItemStacks[index] != null)
    {


      if (furnaceItemStacks[index].stackSize <= count)
      {
        ItemStack var3 = furnaceItemStacks[index];
        furnaceItemStacks[index] = null;
        return var3;
      }
      

      ItemStack var3 = furnaceItemStacks[index].splitStack(count);
      
      if (furnaceItemStacks[index].stackSize == 0)
      {
        furnaceItemStacks[index] = null;
      }
      
      return var3;
    }
    


    return null;
  }
  





  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (furnaceItemStacks[index] != null)
    {
      ItemStack var2 = furnaceItemStacks[index];
      furnaceItemStacks[index] = null;
      return var2;
    }
    

    return null;
  }
  




  public void setInventorySlotContents(int index, ItemStack stack)
  {
    boolean var3 = (stack != null) && (stack.isItemEqual(furnaceItemStacks[index])) && (ItemStack.areItemStackTagsEqual(stack, furnaceItemStacks[index]));
    furnaceItemStacks[index] = stack;
    
    if ((stack != null) && (stackSize > getInventoryStackLimit()))
    {
      stackSize = getInventoryStackLimit();
    }
    
    if ((index == 0) && (!var3))
    {
      field_174905_l = func_174904_a(stack);
      field_174906_k = 0;
      markDirty();
    }
  }
  



  public String getName()
  {
    return hasCustomName() ? furnaceCustomName : "container.furnace";
  }
  



  public boolean hasCustomName()
  {
    return (furnaceCustomName != null) && (furnaceCustomName.length() > 0);
  }
  
  public void setCustomInventoryName(String p_145951_1_)
  {
    furnaceCustomName = p_145951_1_;
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    NBTTagList var2 = compound.getTagList("Items", 10);
    furnaceItemStacks = new ItemStack[getSizeInventory()];
    
    for (int var3 = 0; var3 < var2.tagCount(); var3++)
    {
      NBTTagCompound var4 = var2.getCompoundTagAt(var3);
      byte var5 = var4.getByte("Slot");
      
      if ((var5 >= 0) && (var5 < furnaceItemStacks.length))
      {
        furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
      }
    }
    
    furnaceBurnTime = compound.getShort("BurnTime");
    field_174906_k = compound.getShort("CookTime");
    field_174905_l = compound.getShort("CookTimeTotal");
    currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
    
    if (compound.hasKey("CustomName", 8))
    {
      furnaceCustomName = compound.getString("CustomName");
    }
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    compound.setShort("BurnTime", (short)furnaceBurnTime);
    compound.setShort("CookTime", (short)field_174906_k);
    compound.setShort("CookTimeTotal", (short)field_174905_l);
    NBTTagList var2 = new NBTTagList();
    
    for (int var3 = 0; var3 < furnaceItemStacks.length; var3++)
    {
      if (furnaceItemStacks[var3] != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var3);
        furnaceItemStacks[var3].writeToNBT(var4);
        var2.appendTag(var4);
      }
    }
    
    compound.setTag("Items", var2);
    
    if (hasCustomName())
    {
      compound.setString("CustomName", furnaceCustomName);
    }
  }
  




  public int getInventoryStackLimit()
  {
    return 64;
  }
  



  public boolean isBurning()
  {
    return furnaceBurnTime > 0;
  }
  
  public static boolean func_174903_a(IInventory p_174903_0_)
  {
    return p_174903_0_.getField(0) > 0;
  }
  



  public void update()
  {
    boolean var1 = isBurning();
    boolean var2 = false;
    
    if (isBurning())
    {
      furnaceBurnTime -= 1;
    }
    
    if (!worldObj.isRemote)
    {
      if ((!isBurning()) && ((furnaceItemStacks[1] == null) || (furnaceItemStacks[0] == null)))
      {
        if ((!isBurning()) && (field_174906_k > 0))
        {
          field_174906_k = MathHelper.clamp_int(field_174906_k - 2, 0, field_174905_l);
        }
      }
      else
      {
        if ((!isBurning()) && (canSmelt()))
        {
          currentItemBurnTime = (this.furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]));
          
          if (isBurning())
          {
            var2 = true;
            
            if (furnaceItemStacks[1] != null)
            {
              furnaceItemStacks[1].stackSize -= 1;
              
              if (furnaceItemStacks[1].stackSize == 0)
              {
                Item var3 = furnaceItemStacks[1].getItem().getContainerItem();
                furnaceItemStacks[1] = (var3 != null ? new ItemStack(var3) : null);
              }
            }
          }
        }
        
        if ((isBurning()) && (canSmelt()))
        {
          field_174906_k += 1;
          
          if (field_174906_k == field_174905_l)
          {
            field_174906_k = 0;
            field_174905_l = func_174904_a(furnaceItemStacks[0]);
            smeltItem();
            var2 = true;
          }
        }
        else
        {
          field_174906_k = 0;
        }
      }
      
      if (var1 != isBurning())
      {
        var2 = true;
        BlockFurnace.func_176446_a(isBurning(), worldObj, pos);
      }
    }
    
    if (var2)
    {
      markDirty();
    }
  }
  
  public int func_174904_a(ItemStack p_174904_1_)
  {
    return 200;
  }
  



  private boolean canSmelt()
  {
    if (furnaceItemStacks[0] == null)
    {
      return false;
    }
    

    ItemStack var1 = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]);
    return var1 != null;
  }
  




  public void smeltItem()
  {
    if (canSmelt())
    {
      ItemStack var1 = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]);
      
      if (furnaceItemStacks[2] == null)
      {
        furnaceItemStacks[2] = var1.copy();
      }
      else if (furnaceItemStacks[2].getItem() == var1.getItem())
      {
        furnaceItemStacks[2].stackSize += 1;
      }
      
      if ((furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge)) && (furnaceItemStacks[0].getMetadata() == 1) && (furnaceItemStacks[1] != null) && (furnaceItemStacks[1].getItem() == Items.bucket))
      {
        furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
      }
      
      furnaceItemStacks[0].stackSize -= 1;
      
      if (furnaceItemStacks[0].stackSize <= 0)
      {
        furnaceItemStacks[0] = null;
      }
    }
  }
  




  public static int getItemBurnTime(ItemStack p_145952_0_)
  {
    if (p_145952_0_ == null)
    {
      return 0;
    }
    

    Item var1 = p_145952_0_.getItem();
    
    if (((var1 instanceof ItemBlock)) && (Block.getBlockFromItem(var1) != Blocks.air))
    {
      Block var2 = Block.getBlockFromItem(var1);
      
      if (var2 == Blocks.wooden_slab)
      {
        return 150;
      }
      
      if (var2.getMaterial() == Material.wood)
      {
        return 300;
      }
      
      if (var2 == Blocks.coal_block)
      {
        return 16000;
      }
    }
    
    return var1 == Items.blaze_rod ? 2400 : var1 == Item.getItemFromBlock(Blocks.sapling) ? 100 : var1 == Items.lava_bucket ? 20000 : var1 == Items.coal ? 1600 : var1 == Items.stick ? 100 : ((var1 instanceof ItemHoe)) && (((ItemHoe)var1).getMaterialName().equals("WOOD")) ? 200 : ((var1 instanceof ItemSword)) && (((ItemSword)var1).getToolMaterialName().equals("WOOD")) ? 200 : ((var1 instanceof ItemTool)) && (((ItemTool)var1).getToolMaterialName().equals("WOOD")) ? 200 : 0;
  }
  

  public static boolean isItemFuel(ItemStack p_145954_0_)
  {
    return getItemBurnTime(p_145954_0_) > 0;
  }
  



  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return worldObj.getTileEntity(pos) == this;
  }
  

  public void openInventory(EntityPlayer playerIn) {}
  

  public void closeInventory(EntityPlayer playerIn) {}
  

  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return index != 2;
  }
  
  public int[] getSlotsForFace(EnumFacing side)
  {
    return side == EnumFacing.UP ? slotsTop : side == EnumFacing.DOWN ? slotsBottom : slotsSides;
  }
  




  public boolean canInsertItem(int slotIn, ItemStack itemStackIn, EnumFacing direction)
  {
    return isItemValidForSlot(slotIn, itemStackIn);
  }
  




  public boolean canExtractItem(int slotId, ItemStack stack, EnumFacing direction)
  {
    if ((direction == EnumFacing.DOWN) && (slotId == 1))
    {
      Item var4 = stack.getItem();
      
      if ((var4 != Items.water_bucket) && (var4 != Items.bucket))
      {
        return false;
      }
    }
    
    return true;
  }
  
  public String getGuiID()
  {
    return "minecraft:furnace";
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
  {
    return new ContainerFurnace(playerInventory, this);
  }
  
  public int getField(int id)
  {
    switch (id)
    {
    case 0: 
      return furnaceBurnTime;
    
    case 1: 
      return currentItemBurnTime;
    
    case 2: 
      return field_174906_k;
    
    case 3: 
      return field_174905_l;
    }
    
    return 0;
  }
  

  public void setField(int id, int value)
  {
    switch (id)
    {
    case 0: 
      furnaceBurnTime = value;
      break;
    
    case 1: 
      currentItemBurnTime = value;
      break;
    
    case 2: 
      field_174906_k = value;
      break;
    
    case 3: 
      field_174905_l = value;
    }
  }
  
  public int getFieldCount()
  {
    return 4;
  }
  
  public void clearInventory()
  {
    for (int var1 = 0; var1 < furnaceItemStacks.length; var1++)
    {
      furnaceItemStacks[var1] = null;
    }
  }
}
