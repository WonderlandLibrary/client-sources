package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite extends EntityMob
{
  private int lifetime = 0;
  private boolean playerSpawned = false;
  private static final String __OBFID = "CL_00002219";
  
  public EntityEndermite(World worldIn)
  {
    super(worldIn);
    experienceValue = 3;
    setSize(0.4F, 0.3F);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
    tasks.addTask(3, new EntityAIWander(this, 1.0D));
    tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(8, new EntityAILookIdle(this));
    targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
  }
  
  public float getEyeHeight()
  {
    return 0.1F;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  



  protected String getLivingSound()
  {
    return "mob.silverfish.say";
  }
  



  protected String getHurtSound()
  {
    return "mob.silverfish.hit";
  }
  



  protected String getDeathSound()
  {
    return "mob.silverfish.kill";
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.silverfish.step", 0.15F, 1.0F);
  }
  
  protected net.minecraft.item.Item getDropItem()
  {
    return null;
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    lifetime = tagCompund.getInteger("Lifetime");
    playerSpawned = tagCompund.getBoolean("PlayerSpawned");
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Lifetime", lifetime);
    tagCompound.setBoolean("PlayerSpawned", playerSpawned);
  }
  



  public void onUpdate()
  {
    renderYawOffset = rotationYaw;
    super.onUpdate();
  }
  
  public boolean isSpawnedByPlayer()
  {
    return playerSpawned;
  }
  
  public void setSpawnedByPlayer(boolean p_175496_1_)
  {
    playerSpawned = p_175496_1_;
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if (worldObj.isRemote)
    {
      for (int var1 = 0; var1 < 2; var1++)
      {
        worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
      }
    }
    else
    {
      if (!isNoDespawnRequired())
      {
        lifetime += 1;
      }
      
      if (lifetime >= 2400)
      {
        setDead();
      }
    }
  }
  



  protected boolean isValidLightLevel()
  {
    return true;
  }
  



  public boolean getCanSpawnHere()
  {
    if (super.getCanSpawnHere())
    {
      EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 5.0D);
      return var1 == null;
    }
    

    return false;
  }
  




  public EnumCreatureAttribute getCreatureAttribute()
  {
    return EnumCreatureAttribute.ARTHROPOD;
  }
}
