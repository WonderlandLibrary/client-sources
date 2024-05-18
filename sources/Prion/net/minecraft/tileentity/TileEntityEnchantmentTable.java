package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class TileEntityEnchantmentTable extends TileEntity implements IUpdatePlayerListBox, IInteractionObject
{
  public int tickCount;
  public float pageFlip;
  public float pageFlipPrev;
  public float field_145932_k;
  public float field_145929_l;
  public float bookSpread;
  public float bookSpreadPrev;
  public float bookRotation;
  public float bookRotationPrev;
  public float field_145924_q;
  private static Random field_145923_r = new Random();
  private String field_145922_s;
  private static final String __OBFID = "CL_00000354";
  
  public TileEntityEnchantmentTable() {}
  
  public void writeToNBT(NBTTagCompound compound) { super.writeToNBT(compound);
    
    if (hasCustomName())
    {
      compound.setString("CustomName", field_145922_s);
    }
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    
    if (compound.hasKey("CustomName", 8))
    {
      field_145922_s = compound.getString("CustomName");
    }
  }
  



  public void update()
  {
    bookSpreadPrev = bookSpread;
    bookRotationPrev = bookRotation;
    EntityPlayer var1 = worldObj.getClosestPlayer(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 3.0D);
    
    if (var1 != null)
    {
      double var2 = posX - (pos.getX() + 0.5F);
      double var4 = posZ - (pos.getZ() + 0.5F);
      field_145924_q = ((float)Math.atan2(var4, var2));
      bookSpread += 0.1F;
      
      if ((bookSpread < 0.5F) || (field_145923_r.nextInt(40) == 0))
      {
        float var6 = field_145932_k;
        
        do
        {
          field_145932_k += field_145923_r.nextInt(4) - field_145923_r.nextInt(4);
        }
        while (var6 == field_145932_k);
      }
    }
    else
    {
      field_145924_q += 0.02F;
      bookSpread -= 0.1F;
    }
    
    while (bookRotation >= 3.1415927F)
    {
      bookRotation -= 6.2831855F;
    }
    
    while (bookRotation < -3.1415927F)
    {
      bookRotation += 6.2831855F;
    }
    
    while (field_145924_q >= 3.1415927F)
    {
      field_145924_q -= 6.2831855F;
    }
    
    while (field_145924_q < -3.1415927F)
    {
      field_145924_q += 6.2831855F;
    }
    


    for (float var7 = field_145924_q - bookRotation; var7 >= 3.1415927F; var7 -= 6.2831855F) {}
    



    while (var7 < -3.1415927F)
    {
      var7 += 6.2831855F;
    }
    
    bookRotation += var7 * 0.4F;
    bookSpread = MathHelper.clamp_float(bookSpread, 0.0F, 1.0F);
    tickCount += 1;
    pageFlipPrev = pageFlip;
    float var3 = (field_145932_k - pageFlip) * 0.4F;
    float var8 = 0.2F;
    var3 = MathHelper.clamp_float(var3, -var8, var8);
    field_145929_l += (var3 - field_145929_l) * 0.9F;
    pageFlip += field_145929_l;
  }
  



  public String getName()
  {
    return hasCustomName() ? field_145922_s : "container.enchant";
  }
  



  public boolean hasCustomName()
  {
    return (field_145922_s != null) && (field_145922_s.length() > 0);
  }
  
  public void func_145920_a(String p_145920_1_)
  {
    field_145922_s = p_145920_1_;
  }
  
  public IChatComponent getDisplayName()
  {
    return hasCustomName() ? new ChatComponentText(getName()) : new net.minecraft.util.ChatComponentTranslation(getName(), new Object[0]);
  }
  
  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
  {
    return new net.minecraft.inventory.ContainerEnchantment(playerInventory, worldObj, pos);
  }
  
  public String getGuiID()
  {
    return "minecraft:enchanting_table";
  }
}
