package net.minecraft.entity.passive;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable
{
  private EntityAIAvoidEntity field_175545_bm;
  private EntityAITempt aiTempt;
  private static final String __OBFID = "CL_00001646";
  
  public EntityOcelot(World worldIn)
  {
    super(worldIn);
    setSize(0.6F, 0.7F);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, aiSit);
    tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6D, Items.fish, true));
    tasks.addTask(5, new net.minecraft.entity.ai.EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
    tasks.addTask(6, new net.minecraft.entity.ai.EntityAIOcelotSit(this, 0.8D));
    tasks.addTask(7, new net.minecraft.entity.ai.EntityAILeapAtTarget(this, 0.3F));
    tasks.addTask(8, new EntityAIOcelotAttack(this));
    tasks.addTask(9, new EntityAIMate(this, 0.8D));
    tasks.addTask(10, new EntityAIWander(this, 0.8D));
    tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
    targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(18, Byte.valueOf((byte)0));
  }
  
  public void updateAITasks()
  {
    if (getMoveHelper().isUpdating())
    {
      double var1 = getMoveHelper().getSpeed();
      
      if (var1 == 0.6D)
      {
        setSneaking(true);
        setSprinting(false);
      }
      else if (var1 == 1.33D)
      {
        setSneaking(false);
        setSprinting(true);
      }
      else
      {
        setSneaking(false);
        setSprinting(false);
      }
    }
    else
    {
      setSneaking(false);
      setSprinting(false);
    }
  }
  



  protected boolean canDespawn()
  {
    return (!isTamed()) && (ticksExisted > 2400);
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
  }
  


  public void fall(float distance, float damageMultiplier) {}
  

  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("CatType", getTameSkin());
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setTameSkin(tagCompund.getInteger("CatType"));
  }
  



  protected String getLivingSound()
  {
    return isTamed() ? "mob.cat.meow" : rand.nextInt(4) == 0 ? "mob.cat.purreow" : isInLove() ? "mob.cat.purr" : "";
  }
  



  protected String getHurtSound()
  {
    return "mob.cat.hitt";
  }
  



  protected String getDeathSound()
  {
    return "mob.cat.hitt";
  }
  



  protected float getSoundVolume()
  {
    return 0.4F;
  }
  
  protected Item getDropItem()
  {
    return Items.leather;
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    aiSit.setSitting(false);
    return super.attackEntityFrom(source, amount);
  }
  




  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {}
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    
    if (isTamed())
    {
      if ((func_152114_e(p_70085_1_)) && (!worldObj.isRemote) && (!isBreedingItem(var2)))
      {
        aiSit.setSitting(!isSitting());
      }
    }
    else if ((aiTempt.isRunning()) && (var2 != null) && (var2.getItem() == Items.fish) && (p_70085_1_.getDistanceSqToEntity(this) < 9.0D))
    {
      if (!capabilities.isCreativeMode)
      {
        stackSize -= 1;
      }
      
      if (stackSize <= 0)
      {
        inventory.setInventorySlotContents(inventory.currentItem, null);
      }
      
      if (!worldObj.isRemote)
      {
        if (rand.nextInt(3) == 0)
        {
          setTamed(true);
          setTameSkin(1 + worldObj.rand.nextInt(3));
          func_152115_b(p_70085_1_.getUniqueID().toString());
          playTameEffect(true);
          aiSit.setSitting(true);
          worldObj.setEntityState(this, (byte)7);
        }
        else
        {
          playTameEffect(false);
          worldObj.setEntityState(this, (byte)6);
        }
      }
      
      return true;
    }
    
    return super.interact(p_70085_1_);
  }
  
  public EntityOcelot func_180493_b(EntityAgeable p_180493_1_)
  {
    EntityOcelot var2 = new EntityOcelot(worldObj);
    
    if (isTamed())
    {
      var2.func_152115_b(func_152113_b());
      var2.setTamed(true);
      var2.setTameSkin(getTameSkin());
    }
    
    return var2;
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return (p_70877_1_ != null) && (p_70877_1_.getItem() == Items.fish);
  }
  



  public boolean canMateWith(EntityAnimal p_70878_1_)
  {
    if (p_70878_1_ == this)
    {
      return false;
    }
    if (!isTamed())
    {
      return false;
    }
    if (!(p_70878_1_ instanceof EntityOcelot))
    {
      return false;
    }
    

    EntityOcelot var2 = (EntityOcelot)p_70878_1_;
    return var2.isTamed();
  }
  

  public int getTameSkin()
  {
    return dataWatcher.getWatchableObjectByte(18);
  }
  
  public void setTameSkin(int p_70912_1_)
  {
    dataWatcher.updateObject(18, Byte.valueOf((byte)p_70912_1_));
  }
  



  public boolean getCanSpawnHere()
  {
    return worldObj.rand.nextInt(3) != 0;
  }
  



  public boolean handleLavaMovement()
  {
    if ((worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)) && (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) && (!worldObj.isAnyLiquid(getEntityBoundingBox())))
    {
      BlockPos var1 = new BlockPos(posX, getEntityBoundingBoxminY, posZ);
      
      if (var1.getY() < 63)
      {
        return false;
      }
      
      Block var2 = worldObj.getBlockState(var1.offsetDown()).getBlock();
      
      if ((var2 == net.minecraft.init.Blocks.grass) || (var2.getMaterial() == Material.leaves))
      {
        return true;
      }
    }
    
    return false;
  }
  



  public String getName()
  {
    return isTamed() ? net.minecraft.util.StatCollector.translateToLocal("entity.Cat.name") : hasCustomName() ? getCustomNameTag() : super.getName();
  }
  
  public void setTamed(boolean p_70903_1_)
  {
    super.setTamed(p_70903_1_);
  }
  
  protected void func_175544_ck()
  {
    if (field_175545_bm == null)
    {
      field_175545_bm = new EntityAIAvoidEntity(this, new com.google.common.base.Predicate()
      {
        private static final String __OBFID = "CL_00002243";
        
        public boolean func_179874_a(Entity p_179874_1_) {
          return p_179874_1_ instanceof EntityPlayer;
        }
        
        public boolean apply(Object p_apply_1_) {
          return func_179874_a((Entity)p_apply_1_);
        }
      }, 16.0F, 0.8D, 1.33D);
    }
    
    tasks.removeTask(field_175545_bm);
    
    if (!isTamed())
    {
      tasks.addTask(4, field_175545_bm);
    }
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
    
    if (worldObj.rand.nextInt(7) == 0)
    {
      for (int var3 = 0; var3 < 2; var3++)
      {
        EntityOcelot var4 = new EntityOcelot(worldObj);
        var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
        var4.setGrowingAge(41536);
        worldObj.spawnEntityInWorld(var4);
      }
    }
    
    return p_180482_2_;
  }
  
  public EntityAgeable createChild(EntityAgeable p_90011_1_)
  {
    return func_180493_b(p_90011_1_);
  }
}
