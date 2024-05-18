package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob
{
  private static final String __OBFID = "CL_00001699";
  
  public EntitySpider(World worldIn)
  {
    super(worldIn);
    setSize(1.4F, 0.9F);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, field_175455_a);
    tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    tasks.addTask(4, new AISpiderAttack(EntityPlayer.class));
    tasks.addTask(4, new AISpiderAttack(EntityIronGolem.class));
    tasks.addTask(5, new EntityAIWander(this, 0.8D));
    tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(6, new EntityAILookIdle(this));
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
    targetTasks.addTask(2, new AISpiderTarget(EntityPlayer.class));
    targetTasks.addTask(3, new AISpiderTarget(EntityIronGolem.class));
  }
  
  protected PathNavigate func_175447_b(World worldIn)
  {
    return new net.minecraft.pathfinding.PathNavigateClimber(this, worldIn);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, new Byte((byte)0));
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (!worldObj.isRemote)
    {
      setBesideClimbableBlock(isCollidedHorizontally);
    }
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
  }
  



  protected String getLivingSound()
  {
    return "mob.spider.say";
  }
  



  protected String getHurtSound()
  {
    return "mob.spider.say";
  }
  



  protected String getDeathSound()
  {
    return "mob.spider.death";
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.spider.step", 0.15F, 1.0F);
  }
  
  protected Item getDropItem()
  {
    return Items.string;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    super.dropFewItems(p_70628_1_, p_70628_2_);
    
    if ((p_70628_1_) && ((rand.nextInt(3) == 0) || (rand.nextInt(1 + p_70628_2_) > 0)))
    {
      dropItem(Items.spider_eye, 1);
    }
  }
  



  public boolean isOnLadder()
  {
    return isBesideClimbableBlock();
  }
  



  public void setInWeb() {}
  



  public EnumCreatureAttribute getCreatureAttribute()
  {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public boolean isPotionApplicable(PotionEffect p_70687_1_)
  {
    return p_70687_1_.getPotionID() == poisonid ? false : super.isPotionApplicable(p_70687_1_);
  }
  




  public boolean isBesideClimbableBlock()
  {
    return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  




  public void setBesideClimbableBlock(boolean p_70839_1_)
  {
    byte var2 = dataWatcher.getWatchableObjectByte(16);
    
    if (p_70839_1_)
    {
      var2 = (byte)(var2 | 0x1);
    }
    else
    {
      var2 = (byte)(var2 & 0xFFFFFFFE);
    }
    
    dataWatcher.updateObject(16, Byte.valueOf(var2));
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);
    
    if (worldObj.rand.nextInt(100) == 0)
    {
      EntitySkeleton var3 = new EntitySkeleton(worldObj);
      var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
      var3.func_180482_a(p_180482_1_, null);
      worldObj.spawnEntityInWorld(var3);
      var3.mountEntity(this);
    }
    
    if (p_180482_2_1 == null)
    {
      p_180482_2_1 = new GroupData();
      
      if ((worldObj.getDifficulty() == net.minecraft.world.EnumDifficulty.HARD) && (worldObj.rand.nextFloat() < 0.1F * p_180482_1_.func_180170_c()))
      {
        ((GroupData)p_180482_2_1).func_111104_a(worldObj.rand);
      }
    }
    
    if ((p_180482_2_1 instanceof GroupData))
    {
      int var5 = field_111105_a;
      
      if ((var5 > 0) && (Potion.potionTypes[var5] != null))
      {
        addPotionEffect(new PotionEffect(var5, Integer.MAX_VALUE));
      }
    }
    
    return (IEntityLivingData)p_180482_2_1;
  }
  
  public float getEyeHeight()
  {
    return 0.65F;
  }
  
  class AISpiderAttack extends EntityAIAttackOnCollide
  {
    private static final String __OBFID = "CL_00002197";
    
    public AISpiderAttack(Class p_i45819_2_)
    {
      super(p_i45819_2_, 1.0D, true);
    }
    
    public boolean continueExecuting()
    {
      float var1 = attacker.getBrightness(1.0F);
      
      if ((var1 >= 0.5F) && (attacker.getRNG().nextInt(100) == 0))
      {
        attacker.setAttackTarget(null);
        return false;
      }
      

      return super.continueExecuting();
    }
    

    protected double func_179512_a(EntityLivingBase p_179512_1_)
    {
      return 4.0F + width;
    }
  }
  
  class AISpiderTarget extends EntityAINearestAttackableTarget
  {
    private static final String __OBFID = "CL_00002196";
    
    public AISpiderTarget(Class p_i45818_2_)
    {
      super(p_i45818_2_, true);
    }
    
    public boolean shouldExecute()
    {
      float var1 = taskOwner.getBrightness(1.0F);
      return var1 >= 0.5F ? false : super.shouldExecute();
    }
  }
  
  public static class GroupData implements IEntityLivingData {
    public int field_111105_a;
    private static final String __OBFID = "CL_00001700";
    
    public GroupData() {}
    
    public void func_111104_a(Random p_111104_1_) {
      int var2 = p_111104_1_.nextInt(5);
      
      if (var2 <= 1)
      {
        field_111105_a = moveSpeedid;
      }
      else if (var2 <= 2)
      {
        field_111105_a = damageBoostid;
      }
      else if (var2 <= 3)
      {
        field_111105_a = regenerationid;
      }
      else if (var2 <= 4)
      {
        field_111105_a = invisibilityid;
      }
    }
  }
}
