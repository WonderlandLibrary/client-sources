package net.minecraft.entity.passive;

import java.util.Calendar;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBat
  extends EntityAmbientCreature
{
  private BlockPos spawnPosition;
  private static final String __OBFID = "CL_00001637";
  
  public EntityBat(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.9F);
    setIsBatHanging(true);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, new Byte((byte)0));
  }
  
  protected float getSoundVolume()
  {
    return 0.1F;
  }
  
  protected float getSoundPitch()
  {
    return super.getSoundPitch() * 0.95F;
  }
  
  protected String getLivingSound()
  {
    return (getIsBatHanging()) && (this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
  }
  
  protected String getHurtSound()
  {
    return "mob.bat.hurt";
  }
  
  protected String getDeathSound()
  {
    return "mob.bat.death";
  }
  
  public boolean canBePushed()
  {
    return false;
  }
  
  protected void collideWithEntity(Entity p_82167_1_) {}
  
  protected void collideWithNearbyEntities() {}
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
  }
  
  public boolean getIsBatHanging()
  {
    return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  
  public void setIsBatHanging(boolean p_82236_1_)
  {
    byte var2 = this.dataWatcher.getWatchableObjectByte(16);
    if (p_82236_1_) {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
    } else {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
    }
  }
  
  public void onUpdate()
  {
    super.onUpdate();
    if (getIsBatHanging())
    {
      this.motionX = (this.motionY = this.motionZ = 0.0D);
      this.posY = (MathHelper.floor_double(this.posY) + 1.0D - this.height);
    }
    else
    {
      this.motionY *= 0.6000000238418579D;
    }
  }
  
  protected void updateAITasks()
  {
    super.updateAITasks();
    BlockPos var1 = new BlockPos(this);
    BlockPos var2 = var1.offsetUp();
    if (getIsBatHanging())
    {
      if (!this.worldObj.getBlockState(var2).getBlock().isNormalCube())
      {
        setIsBatHanging(false);
        this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, var1, 0);
      }
      else
      {
        if (this.rand.nextInt(200) == 0) {
          this.rotationYawHead = this.rand.nextInt(360);
        }
        if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
        {
          setIsBatHanging(false);
          this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, var1, 0);
        }
      }
    }
    else
    {
      if ((this.spawnPosition != null) && ((!this.worldObj.isAirBlock(this.spawnPosition)) || (this.spawnPosition.getY() < 1))) {
        this.spawnPosition = null;
      }
      if ((this.spawnPosition == null) || (this.rand.nextInt(30) == 0) || (this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D)) {
        this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
      }
      double var3 = this.spawnPosition.getX() + 0.5D - this.posX;
      double var5 = this.spawnPosition.getY() + 0.1D - this.posY;
      double var7 = this.spawnPosition.getZ() + 0.5D - this.posZ;
      this.motionX += (Math.signum(var3) * 0.5D - this.motionX) * 0.10000000149011612D;
      this.motionY += (Math.signum(var5) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
      this.motionZ += (Math.signum(var7) * 0.5D - this.motionZ) * 0.10000000149011612D;
      float var9 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
      float var10 = MathHelper.wrapAngleTo180_float(var9 - this.rotationYaw);
      this.moveForward = 0.5F;
      this.rotationYaw += var10;
      if ((this.rand.nextInt(100) == 0) && (this.worldObj.getBlockState(var2).getBlock().isNormalCube())) {
        setIsBatHanging(true);
      }
    }
  }
  
  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_) {}
  
  public boolean doesEntityNotTriggerPressurePlate()
  {
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source)) {
      return false;
    }
    if ((!this.worldObj.isRemote) && (getIsBatHanging())) {
      setIsBatHanging(false);
    }
    return super.attackEntityFrom(source, amount);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    this.dataWatcher.updateObject(16, Byte.valueOf(tagCompund.getByte("BatFlags")));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
  }
  
  public boolean getCanSpawnHere()
  {
    BlockPos var1 = new BlockPos(this.posX, getEntityBoundingBox().minY, this.posZ);
    if (var1.getY() >= 63) {
      return false;
    }
    int var2 = this.worldObj.getLightFromNeighbors(var1);
    byte var3 = 4;
    if (func_175569_a(this.worldObj.getCurrentDate())) {
      var3 = 7;
    } else if (this.rand.nextBoolean()) {
      return false;
    }
    return var2 > this.rand.nextInt(var3) ? false : super.getCanSpawnHere();
  }
  
  private boolean func_175569_a(Calendar p_175569_1_)
  {
    return ((p_175569_1_.get(2) + 1 == 10) && (p_175569_1_.get(5) >= 20)) || ((p_175569_1_.get(2) + 1 == 11) && (p_175569_1_.get(5) <= 3));
  }
  
  public float getEyeHeight()
  {
    return this.height / 2.0F;
  }
}
