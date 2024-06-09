package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart
{
  private int fuel;
  public double pushX;
  public double pushZ;
  private static final String __OBFID = "CL_00001675";
  
  public EntityMinecartFurnace(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityMinecartFurnace(World worldIn, double p_i1719_2_, double p_i1719_4_, double p_i1719_6_)
  {
    super(worldIn, p_i1719_2_, p_i1719_4_, p_i1719_6_);
  }
  
  public EntityMinecart.EnumMinecartType func_180456_s()
  {
    return EntityMinecart.EnumMinecartType.FURNACE;
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, new Byte((byte)0));
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (fuel > 0)
    {
      fuel -= 1;
    }
    
    if (fuel <= 0)
    {
      pushX = (this.pushZ = 0.0D);
    }
    
    setMinecartPowered(fuel > 0);
    
    if ((isMinecartPowered()) && (rand.nextInt(4) == 0))
    {
      worldObj.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, posX, posY + 0.8D, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
    }
  }
  
  protected double func_174898_m()
  {
    return 0.2D;
  }
  
  public void killMinecart(DamageSource p_94095_1_)
  {
    super.killMinecart(p_94095_1_);
    
    if (!p_94095_1_.isExplosion())
    {
      entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
    }
  }
  
  protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_)
  {
    super.func_180460_a(p_180460_1_, p_180460_2_);
    double var3 = pushX * pushX + pushZ * pushZ;
    
    if ((var3 > 1.0E-4D) && (motionX * motionX + motionZ * motionZ > 0.001D))
    {
      var3 = MathHelper.sqrt_double(var3);
      pushX /= var3;
      pushZ /= var3;
      
      if (pushX * motionX + pushZ * motionZ < 0.0D)
      {
        pushX = 0.0D;
        pushZ = 0.0D;
      }
      else
      {
        double var5 = var3 / func_174898_m();
        pushX *= var5;
        pushZ *= var5;
      }
    }
  }
  
  protected void applyDrag()
  {
    double var1 = pushX * pushX + pushZ * pushZ;
    
    if (var1 > 1.0E-4D)
    {
      var1 = MathHelper.sqrt_double(var1);
      pushX /= var1;
      pushZ /= var1;
      double var3 = 1.0D;
      motionX *= 0.800000011920929D;
      motionY *= 0.0D;
      motionZ *= 0.800000011920929D;
      motionX += pushX * var3;
      motionZ += pushZ * var3;
    }
    else
    {
      motionX *= 0.9800000190734863D;
      motionY *= 0.0D;
      motionZ *= 0.9800000190734863D;
    }
    
    super.applyDrag();
  }
  



  public boolean interactFirst(EntityPlayer playerIn)
  {
    ItemStack var2 = inventory.getCurrentItem();
    
    if ((var2 != null) && (var2.getItem() == net.minecraft.init.Items.coal))
    {
      if (!capabilities.isCreativeMode) { if (--stackSize == 0)
        {
          inventory.setInventorySlotContents(inventory.currentItem, null);
        }
      }
      fuel += 3600;
    }
    
    pushX = (posX - posX);
    pushZ = (posZ - posZ);
    return true;
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setDouble("PushX", pushX);
    tagCompound.setDouble("PushZ", pushZ);
    tagCompound.setShort("Fuel", (short)fuel);
  }
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    pushX = tagCompund.getDouble("PushX");
    pushZ = tagCompund.getDouble("PushZ");
    fuel = tagCompund.getShort("Fuel");
  }
  
  protected boolean isMinecartPowered()
  {
    return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  
  protected void setMinecartPowered(boolean p_94107_1_)
  {
    if (p_94107_1_)
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(dataWatcher.getWatchableObjectByte(16) | 0x1)));
    }
    else
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE)));
    }
  }
  
  public IBlockState func_180457_u()
  {
    return (isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(net.minecraft.block.BlockFurnace.FACING, net.minecraft.util.EnumFacing.NORTH);
  }
}
