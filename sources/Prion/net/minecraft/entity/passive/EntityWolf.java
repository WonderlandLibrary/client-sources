package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable
{
  private float headRotationCourse;
  private float headRotationCourseOld;
  private boolean isWet;
  private boolean isShaking;
  private float timeWolfIsShaking;
  private float prevTimeWolfIsShaking;
  private static final String __OBFID = "CL_00001654";
  
  public EntityWolf(World worldIn)
  {
    super(worldIn);
    setSize(0.6F, 0.8F);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, aiSit);
    tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
    tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
    tasks.addTask(6, new EntityAIMate(this, 1.0D));
    tasks.addTask(7, new EntityAIWander(this, 1.0D));
    tasks.addTask(8, new EntityAIBeg(this, 8.0F));
    tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(9, new EntityAILookIdle(this));
    targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
    targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
    targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate()
    {
      private static final String __OBFID = "CL_00002229";
      
      public boolean func_180094_a(Entity p_180094_1_) {
        return ((p_180094_1_ instanceof EntitySheep)) || ((p_180094_1_ instanceof EntityRabbit));
      }
      
      public boolean apply(Object p_apply_1_) {
        return func_180094_a((Entity)p_apply_1_);
      }
    }));
    targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, net.minecraft.entity.monster.EntitySkeleton.class, false));
    setTamed(false);
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
    
    if (isTamed())
    {
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
    else
    {
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }
    
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
  }
  



  public void setAttackTarget(EntityLivingBase p_70624_1_)
  {
    super.setAttackTarget(p_70624_1_);
    
    if (p_70624_1_ == null)
    {
      setAngry(false);
    }
    else if (!isTamed())
    {
      setAngry(true);
    }
  }
  
  protected void updateAITasks()
  {
    dataWatcher.updateObject(18, Float.valueOf(getHealth()));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(18, new Float(getHealth()));
    dataWatcher.addObject(19, new Byte((byte)0));
    dataWatcher.addObject(20, new Byte((byte)EnumDyeColor.RED.func_176765_a()));
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.wolf.step", 0.15F, 1.0F);
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("Angry", isAngry());
    tagCompound.setByte("CollarColor", (byte)func_175546_cu().getDyeColorDamage());
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setAngry(tagCompund.getBoolean("Angry"));
    
    if (tagCompund.hasKey("CollarColor", 99))
    {
      func_175547_a(EnumDyeColor.func_176766_a(tagCompund.getByte("CollarColor")));
    }
  }
  



  protected String getLivingSound()
  {
    return rand.nextInt(3) == 0 ? "mob.wolf.panting" : (isTamed()) && (dataWatcher.getWatchableObjectFloat(18) < 10.0F) ? "mob.wolf.whine" : isAngry() ? "mob.wolf.growl" : "mob.wolf.bark";
  }
  



  protected String getHurtSound()
  {
    return "mob.wolf.hurt";
  }
  



  protected String getDeathSound()
  {
    return "mob.wolf.death";
  }
  



  protected float getSoundVolume()
  {
    return 0.4F;
  }
  
  protected Item getDropItem()
  {
    return Item.getItemById(-1);
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if ((!worldObj.isRemote) && (isWet) && (!isShaking) && (!hasPath()) && (onGround))
    {
      isShaking = true;
      timeWolfIsShaking = 0.0F;
      prevTimeWolfIsShaking = 0.0F;
      worldObj.setEntityState(this, (byte)8);
    }
    
    if ((!worldObj.isRemote) && (getAttackTarget() == null) && (isAngry()))
    {
      setAngry(false);
    }
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    headRotationCourseOld = headRotationCourse;
    
    if (func_70922_bv())
    {
      headRotationCourse += (1.0F - headRotationCourse) * 0.4F;
    }
    else
    {
      headRotationCourse += (0.0F - headRotationCourse) * 0.4F;
    }
    
    if (isWet())
    {
      isWet = true;
      isShaking = false;
      timeWolfIsShaking = 0.0F;
      prevTimeWolfIsShaking = 0.0F;
    }
    else if (((isWet) || (isShaking)) && (isShaking))
    {
      if (timeWolfIsShaking == 0.0F)
      {
        playSound("mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
      }
      
      prevTimeWolfIsShaking = timeWolfIsShaking;
      timeWolfIsShaking += 0.05F;
      
      if (prevTimeWolfIsShaking >= 2.0F)
      {
        isWet = false;
        isShaking = false;
        prevTimeWolfIsShaking = 0.0F;
        timeWolfIsShaking = 0.0F;
      }
      
      if (timeWolfIsShaking > 0.4F)
      {
        float var1 = (float)getEntityBoundingBoxminY;
        int var2 = (int)(MathHelper.sin((timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
        
        for (int var3 = 0; var3 < var2; var3++)
        {
          float var4 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
          float var5 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
          worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + var4, var1 + 0.8F, posZ + var5, motionX, motionY, motionZ, new int[0]);
        }
      }
    }
  }
  



  public boolean isWolfWet()
  {
    return isWet;
  }
  



  public float getShadingWhileWet(float p_70915_1_)
  {
    return 0.75F + (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
  }
  
  public float getShakeAngle(float p_70923_1_, float p_70923_2_)
  {
    float var3 = (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
    
    if (var3 < 0.0F)
    {
      var3 = 0.0F;
    }
    else if (var3 > 1.0F)
    {
      var3 = 1.0F;
    }
    
    return MathHelper.sin(var3 * 3.1415927F) * MathHelper.sin(var3 * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
  }
  
  public float getInterestedAngle(float p_70917_1_)
  {
    return (headRotationCourseOld + (headRotationCourse - headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
  }
  
  public float getEyeHeight()
  {
    return height * 0.8F;
  }
  




  public int getVerticalFaceSpeed()
  {
    return isSitting() ? 20 : super.getVerticalFaceSpeed();
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    Entity var3 = source.getEntity();
    aiSit.setSitting(false);
    
    if ((var3 != null) && (!(var3 instanceof EntityPlayer)) && (!(var3 instanceof EntityArrow)))
    {
      amount = (amount + 1.0F) / 2.0F;
    }
    
    return super.attackEntityFrom(source, amount);
  }
  

  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    boolean var2 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
    
    if (var2)
    {
      func_174815_a(this, p_70652_1_);
    }
    
    return var2;
  }
  
  public void setTamed(boolean p_70903_1_)
  {
    super.setTamed(p_70903_1_);
    
    if (p_70903_1_)
    {
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }
    else
    {
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }
    
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    
    if (isTamed())
    {
      if (var2 != null)
      {
        if ((var2.getItem() instanceof ItemFood))
        {
          ItemFood var3 = (ItemFood)var2.getItem();
          
          if ((var3.isWolfsFavoriteMeat()) && (dataWatcher.getWatchableObjectFloat(18) < 20.0F))
          {
            if (!capabilities.isCreativeMode)
            {
              stackSize -= 1;
            }
            
            heal(var3.getHealAmount(var2));
            
            if (stackSize <= 0)
            {
              inventory.setInventorySlotContents(inventory.currentItem, null);
            }
            
            return true;
          }
        }
        else if (var2.getItem() == Items.dye)
        {
          EnumDyeColor var4 = EnumDyeColor.func_176766_a(var2.getMetadata());
          
          if (var4 != func_175546_cu())
          {
            func_175547_a(var4);
            
            if (!capabilities.isCreativeMode) { if (--stackSize <= 0)
              {
                inventory.setInventorySlotContents(inventory.currentItem, null);
              }
            }
            return true;
          }
        }
      }
      
      if ((func_152114_e(p_70085_1_)) && (!worldObj.isRemote) && (!isBreedingItem(var2)))
      {
        aiSit.setSitting(!isSitting());
        isJumping = false;
        navigator.clearPathEntity();
        setAttackTarget(null);
      }
    }
    else if ((var2 != null) && (var2.getItem() == Items.bone) && (!isAngry()))
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
          navigator.clearPathEntity();
          setAttackTarget(null);
          aiSit.setSitting(true);
          setHealth(20.0F);
          func_152115_b(p_70085_1_.getUniqueID().toString());
          playTameEffect(true);
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
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 8)
    {
      isShaking = true;
      timeWolfIsShaking = 0.0F;
      prevTimeWolfIsShaking = 0.0F;
    }
    else
    {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  
  public float getTailRotation()
  {
    return isTamed() ? (0.55F - (20.0F - dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * 3.1415927F : isAngry() ? 1.5393804F : 0.62831855F;
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return !(p_70877_1_.getItem() instanceof ItemFood) ? false : p_70877_1_ == null ? false : ((ItemFood)p_70877_1_.getItem()).isWolfsFavoriteMeat();
  }
  



  public int getMaxSpawnedInChunk()
  {
    return 8;
  }
  



  public boolean isAngry()
  {
    return (dataWatcher.getWatchableObjectByte(16) & 0x2) != 0;
  }
  



  public void setAngry(boolean p_70916_1_)
  {
    byte var2 = dataWatcher.getWatchableObjectByte(16);
    
    if (p_70916_1_)
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x2)));
    }
    else
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFD)));
    }
  }
  
  public EnumDyeColor func_175546_cu()
  {
    return EnumDyeColor.func_176766_a(dataWatcher.getWatchableObjectByte(20) & 0xF);
  }
  
  public void func_175547_a(EnumDyeColor p_175547_1_)
  {
    dataWatcher.updateObject(20, Byte.valueOf((byte)(p_175547_1_.getDyeColorDamage() & 0xF)));
  }
  
  public EntityWolf createChild(EntityAgeable p_90011_1_)
  {
    EntityWolf var2 = new EntityWolf(worldObj);
    String var3 = func_152113_b();
    
    if ((var3 != null) && (var3.trim().length() > 0))
    {
      var2.func_152115_b(var3);
      var2.setTamed(true);
    }
    
    return var2;
  }
  
  public void func_70918_i(boolean p_70918_1_)
  {
    if (p_70918_1_)
    {
      dataWatcher.updateObject(19, Byte.valueOf((byte)1));
    }
    else
    {
      dataWatcher.updateObject(19, Byte.valueOf((byte)0));
    }
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
    if (!(p_70878_1_ instanceof EntityWolf))
    {
      return false;
    }
    

    EntityWolf var2 = (EntityWolf)p_70878_1_;
    return var2.isTamed();
  }
  

  public boolean func_70922_bv()
  {
    return dataWatcher.getWatchableObjectByte(19) == 1;
  }
  



  protected boolean canDespawn()
  {
    return (!isTamed()) && (ticksExisted > 2400);
  }
  
  public boolean func_142018_a(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_)
  {
    if ((!(p_142018_1_ instanceof EntityCreeper)) && (!(p_142018_1_ instanceof EntityGhast)))
    {
      if ((p_142018_1_ instanceof EntityWolf))
      {
        EntityWolf var3 = (EntityWolf)p_142018_1_;
        
        if ((var3.isTamed()) && (var3.func_180492_cm() == p_142018_2_))
        {
          return false;
        }
      }
      
      return (!(p_142018_1_ instanceof EntityPlayer)) || (!(p_142018_2_ instanceof EntityPlayer)) || (((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_));
    }
    

    return false;
  }
  

  public boolean allowLeashing()
  {
    return (!isAngry()) && (super.allowLeashing());
  }
}
