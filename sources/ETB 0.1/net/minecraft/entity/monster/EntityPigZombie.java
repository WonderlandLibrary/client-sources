package net.minecraft.entity.monster;

import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityPigZombie extends EntityZombie
{
  private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
  private static final AttributeModifier field_110190_br = new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.05D, 0).setSaved(false);
  
  private int angerLevel;
  
  private int randomSoundDelay;
  
  private UUID field_175459_bn;
  
  private static final String __OBFID = "CL_00001693";
  
  public EntityPigZombie(World worldIn)
  {
    super(worldIn);
    isImmuneToFire = true;
  }
  
  public void setRevengeTarget(EntityLivingBase p_70604_1_)
  {
    super.setRevengeTarget(p_70604_1_);
    
    if (p_70604_1_ != null)
    {
      field_175459_bn = p_70604_1_.getUniqueID();
    }
  }
  
  protected void func_175456_n()
  {
    targetTasks.addTask(1, new AIHurtByAggressor());
    targetTasks.addTask(2, new AITargetAggressor());
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(field_110186_bp).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
  }
  



  public void onUpdate()
  {
    super.onUpdate();
  }
  
  protected void updateAITasks()
  {
    IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
    
    if (func_175457_ck())
    {
      if ((!isChild()) && (!var1.func_180374_a(field_110190_br)))
      {
        var1.applyModifier(field_110190_br);
      }
      
      angerLevel -= 1;
    }
    else if (var1.func_180374_a(field_110190_br))
    {
      var1.removeModifier(field_110190_br);
    }
    
    if ((randomSoundDelay > 0) && (--randomSoundDelay == 0))
    {
      playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
    }
    
    if ((angerLevel > 0) && (field_175459_bn != null) && (getAITarget() == null))
    {
      EntityPlayer var2 = worldObj.getPlayerEntityByUUID(field_175459_bn);
      setRevengeTarget(var2);
      attackingPlayer = var2;
      recentlyHit = getRevengeTimer();
    }
    
    super.updateAITasks();
  }
  



  public boolean getCanSpawnHere()
  {
    return worldObj.getDifficulty() != net.minecraft.world.EnumDifficulty.PEACEFUL;
  }
  



  public boolean handleLavaMovement()
  {
    return (worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)) && (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) && (!worldObj.isAnyLiquid(getEntityBoundingBox()));
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setShort("Anger", (short)angerLevel);
    
    if (field_175459_bn != null)
    {
      tagCompound.setString("HurtBy", field_175459_bn.toString());
    }
    else
    {
      tagCompound.setString("HurtBy", "");
    }
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    angerLevel = tagCompund.getShort("Anger");
    String var2 = tagCompund.getString("HurtBy");
    
    if (var2.length() > 0)
    {
      field_175459_bn = UUID.fromString(var2);
      EntityPlayer var3 = worldObj.getPlayerEntityByUUID(field_175459_bn);
      setRevengeTarget(var3);
      
      if (var3 != null)
      {
        attackingPlayer = var3;
        recentlyHit = getRevengeTimer();
      }
    }
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    Entity var3 = source.getEntity();
    
    if ((var3 instanceof EntityPlayer))
    {
      becomeAngryAt(var3);
    }
    
    return super.attackEntityFrom(source, amount);
  }
  




  private void becomeAngryAt(Entity p_70835_1_)
  {
    angerLevel = (400 + rand.nextInt(400));
    randomSoundDelay = rand.nextInt(40);
    
    if ((p_70835_1_ instanceof EntityLivingBase))
    {
      setRevengeTarget((EntityLivingBase)p_70835_1_);
    }
  }
  
  public boolean func_175457_ck()
  {
    return angerLevel > 0;
  }
  



  protected String getLivingSound()
  {
    return "mob.zombiepig.zpig";
  }
  



  protected String getHurtSound()
  {
    return "mob.zombiepig.zpighurt";
  }
  



  protected String getDeathSound()
  {
    return "mob.zombiepig.zpigdeath";
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(2 + p_70628_2_);
    

    for (int var4 = 0; var4 < var3; var4++)
    {
      dropItem(Items.rotten_flesh, 1);
    }
    
    var3 = rand.nextInt(2 + p_70628_2_);
    
    for (var4 = 0; var4 < var3; var4++)
    {
      dropItem(Items.gold_nugget, 1);
    }
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    return false;
  }
  



  protected void addRandomArmor()
  {
    dropItem(Items.gold_ingot, 1);
  }
  
  protected void func_180481_a(DifficultyInstance p_180481_1_)
  {
    setCurrentItemOrArmor(0, new net.minecraft.item.ItemStack(Items.golden_sword));
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    super.func_180482_a(p_180482_1_, p_180482_2_);
    setVillager(false);
    return p_180482_2_;
  }
  
  class AIHurtByAggressor extends EntityAIHurtByTarget
  {
    private static final String __OBFID = "CL_00002206";
    
    public AIHurtByAggressor()
    {
      super(true, new Class[0]);
    }
    
    protected void func_179446_a(EntityCreature p_179446_1_, EntityLivingBase p_179446_2_)
    {
      super.func_179446_a(p_179446_1_, p_179446_2_);
      
      if ((p_179446_1_ instanceof EntityPigZombie))
      {
        ((EntityPigZombie)p_179446_1_).becomeAngryAt(p_179446_2_);
      }
    }
  }
  
  class AITargetAggressor extends EntityAINearestAttackableTarget
  {
    private static final String __OBFID = "CL_00002207";
    
    public AITargetAggressor()
    {
      super(EntityPlayer.class, true);
    }
    
    public boolean shouldExecute()
    {
      return (((EntityPigZombie)taskOwner).func_175457_ck()) && (super.shouldExecute());
    }
  }
}
