package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace
  extends EntityMinecart
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
    this.dataWatcher.addObject(16, new Byte((byte)0));
  }
  
  public void onUpdate()
  {
    super.onUpdate();
    if (this.fuel > 0) {
      this.fuel -= 1;
    }
    if (this.fuel <= 0) {
      this.pushX = (this.pushZ = 0.0D);
    }
    setMinecartPowered(this.fuel > 0);
    if ((isMinecartPowered()) && (this.rand.nextInt(4) == 0)) {
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
    }
  }
  
  protected double func_174898_m()
  {
    return 0.2D;
  }
  
  public void killMinecart(DamageSource p_94095_1_)
  {
    super.killMinecart(p_94095_1_);
    if (!p_94095_1_.isExplosion()) {
      entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
    }
  }
  
  protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_)
  {
    super.func_180460_a(p_180460_1_, p_180460_2_);
    double var3 = this.pushX * this.pushX + this.pushZ * this.pushZ;
    if ((var3 > 1.0E-4D) && (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D))
    {
      var3 = MathHelper.sqrt_double(var3);
      this.pushX /= var3;
      this.pushZ /= var3;
      if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D)
      {
        this.pushX = 0.0D;
        this.pushZ = 0.0D;
      }
      else
      {
        double var5 = var3 / func_174898_m();
        this.pushX *= var5;
        this.pushZ *= var5;
      }
    }
  }
  
  protected void applyDrag()
  {
    double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
    if (var1 > 1.0E-4D)
    {
      var1 = MathHelper.sqrt_double(var1);
      this.pushX /= var1;
      this.pushZ /= var1;
      double var3 = 1.0D;
      this.motionX *= 0.800000011920929D;
      this.motionY *= 0.0D;
      this.motionZ *= 0.800000011920929D;
      this.motionX += this.pushX * var3;
      this.motionZ += this.pushZ * var3;
    }
    else
    {
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.0D;
      this.motionZ *= 0.9800000190734863D;
    }
    super.applyDrag();
  }
  
  public boolean interactFirst(EntityPlayer playerIn)
  {
    ItemStack var2 = playerIn.inventory.getCurrentItem();
    if ((var2 != null) && (var2.getItem() == Items.coal))
    {
      if (!playerIn.capabilities.isCreativeMode) {
        if (--var2.stackSize == 0) {
          playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack)null);
        }
      }
      this.fuel += 3600;
    }
    this.pushX = (this.posX - playerIn.posX);
    this.pushZ = (this.posZ - playerIn.posZ);
    return true;
  }
  
  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setDouble("PushX", this.pushX);
    tagCompound.setDouble("PushZ", this.pushZ);
    tagCompound.setShort("Fuel", (short)this.fuel);
  }
  
  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    this.pushX = tagCompund.getDouble("PushX");
    this.pushZ = tagCompund.getDouble("PushZ");
    this.fuel = tagCompund.getShort("Fuel");
  }
  
  protected boolean isMinecartPowered()
  {
    return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  
  protected void setMinecartPowered(boolean p_94107_1_)
  {
    if (p_94107_1_) {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1)));
    } else {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE)));
    }
  }
  
  public IBlockState func_180457_u()
  {
    return (isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
  }
}
