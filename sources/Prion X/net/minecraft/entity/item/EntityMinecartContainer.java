package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer
{
  private ItemStack[] minecartContainerItems = new ItemStack[36];
  




  private boolean dropContentsWhenDead = true;
  private static final String __OBFID = "CL_00001674";
  
  public EntityMinecartContainer(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityMinecartContainer(World worldIn, double p_i1717_2_, double p_i1717_4_, double p_i1717_6_)
  {
    super(worldIn, p_i1717_2_, p_i1717_4_, p_i1717_6_);
  }
  
  public void killMinecart(DamageSource p_94095_1_)
  {
    super.killMinecart(p_94095_1_);
    InventoryHelper.func_180176_a(worldObj, this, this);
  }
  



  public ItemStack getStackInSlot(int slotIn)
  {
    return minecartContainerItems[slotIn];
  }
  




  public ItemStack decrStackSize(int index, int count)
  {
    if (minecartContainerItems[index] != null)
    {


      if (minecartContainerItems[index].stackSize <= count)
      {
        ItemStack var3 = minecartContainerItems[index];
        minecartContainerItems[index] = null;
        return var3;
      }
      

      ItemStack var3 = minecartContainerItems[index].splitStack(count);
      
      if (minecartContainerItems[index].stackSize == 0)
      {
        minecartContainerItems[index] = null;
      }
      
      return var3;
    }
    


    return null;
  }
  





  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (minecartContainerItems[index] != null)
    {
      ItemStack var2 = minecartContainerItems[index];
      minecartContainerItems[index] = null;
      return var2;
    }
    

    return null;
  }
  




  public void setInventorySlotContents(int index, ItemStack stack)
  {
    minecartContainerItems[index] = stack;
    
    if ((stack != null) && (stackSize > getInventoryStackLimit()))
    {
      stackSize = getInventoryStackLimit();
    }
  }
  




  public void markDirty() {}
  



  public boolean isUseableByPlayer(EntityPlayer playerIn)
  {
    return !isDead;
  }
  

  public void openInventory(EntityPlayer playerIn) {}
  

  public void closeInventory(EntityPlayer playerIn) {}
  

  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return true;
  }
  



  public String getName()
  {
    return hasCustomName() ? getCustomNameTag() : "container.minecart";
  }
  




  public int getInventoryStackLimit()
  {
    return 64;
  }
  



  public void travelToDimension(int dimensionId)
  {
    dropContentsWhenDead = false;
    super.travelToDimension(dimensionId);
  }
  



  public void setDead()
  {
    if (dropContentsWhenDead)
    {
      InventoryHelper.func_180176_a(worldObj, this, this);
    }
    
    super.setDead();
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    NBTTagList var2 = new NBTTagList();
    
    for (int var3 = 0; var3 < minecartContainerItems.length; var3++)
    {
      if (minecartContainerItems[var3] != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var3);
        minecartContainerItems[var3].writeToNBT(var4);
        var2.appendTag(var4);
      }
    }
    
    tagCompound.setTag("Items", var2);
  }
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    NBTTagList var2 = tagCompund.getTagList("Items", 10);
    minecartContainerItems = new ItemStack[getSizeInventory()];
    
    for (int var3 = 0; var3 < var2.tagCount(); var3++)
    {
      NBTTagCompound var4 = var2.getCompoundTagAt(var3);
      int var5 = var4.getByte("Slot") & 0xFF;
      
      if ((var5 >= 0) && (var5 < minecartContainerItems.length))
      {
        minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
      }
    }
  }
  



  public boolean interactFirst(EntityPlayer playerIn)
  {
    if (!worldObj.isRemote)
    {
      playerIn.displayGUIChest(this);
    }
    
    return true;
  }
  
  protected void applyDrag()
  {
    int var1 = 15 - Container.calcRedstoneFromInventory(this);
    float var2 = 0.98F + var1 * 0.001F;
    motionX *= var2;
    motionY *= 0.0D;
    motionZ *= var2;
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
    return false;
  }
  
  public void setLockCode(LockCode code) {}
  
  public LockCode getLockCode()
  {
    return LockCode.EMPTY_CODE;
  }
  
  public void clearInventory()
  {
    for (int var1 = 0; var1 < minecartContainerItems.length; var1++)
    {
      minecartContainerItems[var1] = null;
    }
  }
}
