package net.minecraft.entity.monster;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
  private static final UUID field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
  private static final AttributeModifier field_110185_bq = new AttributeModifier(field_110184_bp, "Drinking speed penalty", -0.25D, 0).setSaved(false);
  

  private static final Item[] witchDrops = { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
  

  private int witchAttackTimer;
  

  private static final String __OBFID = "CL_00001701";
  

  public EntityWitch(World worldIn)
  {
    super(worldIn);
    setSize(0.6F, 1.95F);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
    tasks.addTask(2, new EntityAIWander(this, 1.0D));
    tasks.addTask(2, field_175455_a);
    tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(3, new net.minecraft.entity.ai.EntityAILookIdle(this));
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
    targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    getDataWatcher().addObject(21, Byte.valueOf((byte)0));
  }
  



  protected String getLivingSound()
  {
    return null;
  }
  



  protected String getHurtSound()
  {
    return null;
  }
  



  protected String getDeathSound()
  {
    return null;
  }
  



  public void setAggressive(boolean p_82197_1_)
  {
    getDataWatcher().updateObject(21, Byte.valueOf((byte)(p_82197_1_ ? 1 : 0)));
  }
  



  public boolean getAggressive()
  {
    return getDataWatcher().getWatchableObjectByte(21) == 1;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
  }
  




  public void onLivingUpdate()
  {
    if (!worldObj.isRemote)
    {
      if (getAggressive())
      {
        if (witchAttackTimer-- <= 0)
        {
          setAggressive(false);
          ItemStack var1 = getHeldItem();
          setCurrentItemOrArmor(0, null);
          
          if ((var1 != null) && (var1.getItem() == Items.potionitem))
          {
            List var2 = Items.potionitem.getEffects(var1);
            
            if (var2 != null)
            {
              Iterator var3 = var2.iterator();
              
              while (var3.hasNext())
              {
                PotionEffect var4 = (PotionEffect)var3.next();
                addPotionEffect(new PotionEffect(var4));
              }
            }
          }
          
          getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(field_110185_bq);
        }
      }
      else
      {
        short var5 = -1;
        
        if ((rand.nextFloat() < 0.15F) && (isInsideOfMaterial(net.minecraft.block.material.Material.water)) && (!isPotionActive(Potion.waterBreathing)))
        {
          var5 = 8237;
        }
        else if ((rand.nextFloat() < 0.15F) && (isBurning()) && (!isPotionActive(Potion.fireResistance)))
        {
          var5 = 16307;
        }
        else if ((rand.nextFloat() < 0.05F) && (getHealth() < getMaxHealth()))
        {
          var5 = 16341;
        }
        else if ((rand.nextFloat() < 0.25F) && (getAttackTarget() != null) && (!isPotionActive(Potion.moveSpeed)) && (getAttackTarget().getDistanceSqToEntity(this) > 121.0D))
        {
          var5 = 16274;
        }
        else if ((rand.nextFloat() < 0.25F) && (getAttackTarget() != null) && (!isPotionActive(Potion.moveSpeed)) && (getAttackTarget().getDistanceSqToEntity(this) > 121.0D))
        {
          var5 = 16274;
        }
        
        if (var5 > -1)
        {
          setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var5));
          witchAttackTimer = getHeldItem().getMaxItemUseDuration();
          setAggressive(true);
          IAttributeInstance var6 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
          var6.removeModifier(field_110185_bq);
          var6.applyModifier(field_110185_bq);
        }
      }
      
      if (rand.nextFloat() < 7.5E-4F)
      {
        worldObj.setEntityState(this, (byte)15);
      }
    }
    
    super.onLivingUpdate();
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 15)
    {
      for (int var2 = 0; var2 < rand.nextInt(35) + 10; var2++)
      {
        worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, posX + rand.nextGaussian() * 0.12999999523162842D, getEntityBoundingBoxmaxY + 0.5D + rand.nextGaussian() * 0.12999999523162842D, posZ + rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      
    }
    else {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  



  protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
  {
    p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
    
    if (p_70672_1_.getEntity() == this)
    {
      p_70672_2_ = 0.0F;
    }
    
    if (p_70672_1_.isMagicDamage())
    {
      p_70672_2_ = (float)(p_70672_2_ * 0.15D);
    }
    
    return p_70672_2_;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(3) + 1;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      int var5 = rand.nextInt(3);
      Item var6 = witchDrops[rand.nextInt(witchDrops.length)];
      
      if (p_70628_2_ > 0)
      {
        var5 += rand.nextInt(p_70628_2_ + 1);
      }
      
      for (int var7 = 0; var7 < var5; var7++)
      {
        dropItem(var6, 1);
      }
    }
  }
  



  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
    if (!getAggressive())
    {
      EntityPotion var3 = new EntityPotion(worldObj, this, 32732);
      double var4 = posY + p_82196_1_.getEyeHeight() - 1.100000023841858D;
      rotationPitch -= -20.0F;
      double var6 = posX + motionX - posX;
      double var8 = var4 - posY;
      double var10 = posZ + motionZ - posZ;
      float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
      
      if ((var12 >= 8.0F) && (!p_82196_1_.isPotionActive(Potion.moveSlowdown)))
      {
        var3.setPotionDamage(32698);
      }
      else if ((p_82196_1_.getHealth() >= 8.0F) && (!p_82196_1_.isPotionActive(Potion.poison)))
      {
        var3.setPotionDamage(32660);
      }
      else if ((var12 <= 3.0F) && (!p_82196_1_.isPotionActive(Potion.weakness)) && (rand.nextFloat() < 0.25F))
      {
        var3.setPotionDamage(32696);
      }
      
      var3.setThrowableHeading(var6, var8 + var12 * 0.2F, var10, 0.75F, 8.0F);
      worldObj.spawnEntityInWorld(var3);
    }
  }
  
  public float getEyeHeight()
  {
    return 1.62F;
  }
}
