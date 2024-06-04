package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends EntityAnimal
{
  public float field_70886_e;
  public float destPos;
  public float field_70884_g;
  public float field_70888_h;
  public float field_70889_i = 1.0F;
  
  public int timeUntilNextEgg;
  
  public boolean field_152118_bv;
  private static final String __OBFID = "CL_00001639";
  
  public EntityChicken(World worldIn)
  {
    super(worldIn);
    setSize(0.4F, 0.7F);
    timeUntilNextEgg = (rand.nextInt(6000) + 6000);
    tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAIPanic(this, 1.4D));
    tasks.addTask(2, new EntityAIMate(this, 1.0D));
    tasks.addTask(3, new net.minecraft.entity.ai.EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
    tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
    tasks.addTask(5, new EntityAIWander(this, 1.0D));
    tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    tasks.addTask(7, new net.minecraft.entity.ai.EntityAILookIdle(this));
  }
  
  public float getEyeHeight()
  {
    return height;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    field_70888_h = field_70886_e;
    field_70884_g = destPos;
    destPos = ((float)(destPos + (onGround ? -1 : 4) * 0.3D));
    destPos = MathHelper.clamp_float(destPos, 0.0F, 1.0F);
    
    if ((!onGround) && (field_70889_i < 1.0F))
    {
      field_70889_i = 1.0F;
    }
    
    field_70889_i = ((float)(field_70889_i * 0.9D));
    
    if ((!onGround) && (motionY < 0.0D))
    {
      motionY *= 0.6D;
    }
    
    field_70886_e += field_70889_i * 2.0F;
    
    if ((!worldObj.isRemote) && (!isChild()) && (!func_152116_bZ()) && (--timeUntilNextEgg <= 0))
    {
      playSound("mob.chicken.plop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
      dropItem(Items.egg, 1);
      timeUntilNextEgg = (rand.nextInt(6000) + 6000);
    }
  }
  


  public void fall(float distance, float damageMultiplier) {}
  

  protected String getLivingSound()
  {
    return "mob.chicken.say";
  }
  



  protected String getHurtSound()
  {
    return "mob.chicken.hurt";
  }
  



  protected String getDeathSound()
  {
    return "mob.chicken.hurt";
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.chicken.step", 0.15F, 1.0F);
  }
  
  protected Item getDropItem()
  {
    return Items.feather;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(3) + rand.nextInt(1 + p_70628_2_);
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      dropItem(Items.feather, 1);
    }
    
    if (isBurning())
    {
      dropItem(Items.cooked_chicken, 1);
    }
    else
    {
      dropItem(Items.chicken, 1);
    }
  }
  
  public EntityChicken createChild1(EntityAgeable p_90011_1_)
  {
    return new EntityChicken(worldObj);
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return (p_70877_1_ != null) && (p_70877_1_.getItem() == Items.wheat_seeds);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    field_152118_bv = tagCompund.getBoolean("IsChickenJockey");
    
    if (tagCompund.hasKey("EggLayTime"))
    {
      timeUntilNextEgg = tagCompund.getInteger("EggLayTime");
    }
  }
  



  protected int getExperiencePoints(EntityPlayer p_70693_1_)
  {
    return func_152116_bZ() ? 10 : super.getExperiencePoints(p_70693_1_);
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("IsChickenJockey", field_152118_bv);
    tagCompound.setInteger("EggLayTime", timeUntilNextEgg);
  }
  



  protected boolean canDespawn()
  {
    return (func_152116_bZ()) && (riddenByEntity == null);
  }
  
  public void updateRiderPosition()
  {
    super.updateRiderPosition();
    float var1 = MathHelper.sin(renderYawOffset * 3.1415927F / 180.0F);
    float var2 = MathHelper.cos(renderYawOffset * 3.1415927F / 180.0F);
    float var3 = 0.1F;
    float var4 = 0.0F;
    riddenByEntity.setPosition(posX + var3 * var1, posY + height * 0.5F + riddenByEntity.getYOffset() + var4, posZ - var3 * var2);
    
    if ((riddenByEntity instanceof EntityLivingBase))
    {
      riddenByEntity).renderYawOffset = renderYawOffset;
    }
  }
  
  public boolean func_152116_bZ()
  {
    return field_152118_bv;
  }
  
  public void func_152117_i(boolean p_152117_1_)
  {
    field_152118_bv = p_152117_1_;
  }
  
  public EntityAgeable createChild(EntityAgeable p_90011_1_)
  {
    return createChild1(p_90011_1_);
  }
}
