package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPig extends EntityAnimal
{
  private final EntityAIControlledByPlayer aiControlledByPlayer;
  private static final String __OBFID = "CL_00001647";
  
  public EntityPig(World worldIn)
  {
    super(worldIn);
    setSize(0.9F, 0.9F);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAIPanic(this, 1.25D));
    tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
    tasks.addTask(3, new EntityAIMate(this, 1.0D));
    tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
    tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
    tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
    tasks.addTask(6, new EntityAIWander(this, 1.0D));
    tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    tasks.addTask(8, new EntityAILookIdle(this));
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
  }
  




  public boolean canBeSteered()
  {
    ItemStack var1 = ((EntityPlayer)riddenByEntity).getHeldItem();
    return (var1 != null) && (var1.getItem() == Items.carrot_on_a_stick);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("Saddle", getSaddled());
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setSaddled(tagCompund.getBoolean("Saddle"));
  }
  



  protected String getLivingSound()
  {
    return "mob.pig.say";
  }
  



  protected String getHurtSound()
  {
    return "mob.pig.say";
  }
  



  protected String getDeathSound()
  {
    return "mob.pig.death";
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.pig.step", 0.15F, 1.0F);
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    if (super.interact(p_70085_1_))
    {
      return true;
    }
    if ((getSaddled()) && (!worldObj.isRemote) && ((riddenByEntity == null) || (riddenByEntity == p_70085_1_)))
    {
      p_70085_1_.mountEntity(this);
      return true;
    }
    

    return false;
  }
  

  protected Item getDropItem()
  {
    return isBurning() ? Items.cooked_porkchop : Items.porkchop;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + p_70628_2_);
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      if (isBurning())
      {
        dropItem(Items.cooked_porkchop, 1);
      }
      else
      {
        dropItem(Items.porkchop, 1);
      }
    }
    
    if (getSaddled())
    {
      dropItem(Items.saddle, 1);
    }
  }
  



  public boolean getSaddled()
  {
    return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  



  public void setSaddled(boolean p_70900_1_)
  {
    if (p_70900_1_)
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)1));
    }
    else
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)0));
    }
  }
  



  public void onStruckByLightning(EntityLightningBolt lightningBolt)
  {
    if (!worldObj.isRemote)
    {
      EntityPigZombie var2 = new EntityPigZombie(worldObj);
      var2.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
      var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
      worldObj.spawnEntityInWorld(var2);
      setDead();
    }
  }
  
  public void fall(float distance, float damageMultiplier)
  {
    super.fall(distance, damageMultiplier);
    
    if ((distance > 5.0F) && ((riddenByEntity instanceof EntityPlayer)))
    {
      ((EntityPlayer)riddenByEntity).triggerAchievement(net.minecraft.stats.AchievementList.flyPig);
    }
  }
  
  public EntityPig createChild(EntityAgeable p_90011_1_)
  {
    return new EntityPig(worldObj);
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return (p_70877_1_ != null) && (p_70877_1_.getItem() == Items.carrot);
  }
  



  public EntityAIControlledByPlayer getAIControlledByPlayer()
  {
    return aiControlledByPlayer;
  }
}
