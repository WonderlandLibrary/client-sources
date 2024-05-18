package net.minecraft.entity.item;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityItem extends Entity
{
  private static final org.apache.logging.log4j.Logger logger = ;
  
  private int age;
  
  private int delayBeforeCanPickup;
  
  private int health;
  
  private String thrower;
  
  private String owner;
  
  public float hoverStart;
  
  private static final String __OBFID = "CL_00001669";
  

  public EntityItem(World worldIn, double x, double y, double z)
  {
    super(worldIn);
    health = 5;
    hoverStart = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    setSize(0.25F, 0.25F);
    setPosition(x, y, z);
    rotationYaw = ((float)(Math.random() * 360.0D));
    motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    motionY = 0.20000000298023224D;
    motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
  }
  
  public EntityItem(World worldIn, double x, double y, double z, ItemStack stack)
  {
    this(worldIn, x, y, z);
    setEntityItemStack(stack);
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  public EntityItem(World worldIn)
  {
    super(worldIn);
    health = 5;
    hoverStart = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    setSize(0.25F, 0.25F);
    setEntityItemStack(new ItemStack(Blocks.air, 0));
  }
  
  protected void entityInit()
  {
    getDataWatcher().addObjectByDataType(10, 5);
  }
  



  public void onUpdate()
  {
    if (getEntityItem() == null)
    {
      setDead();
    }
    else
    {
      super.onUpdate();
      
      if ((delayBeforeCanPickup > 0) && (delayBeforeCanPickup != 32767))
      {
        delayBeforeCanPickup -= 1;
      }
      
      prevPosX = posX;
      prevPosY = posY;
      prevPosZ = posZ;
      motionY -= 0.03999999910593033D;
      noClip = pushOutOfBlocks(posX, (getEntityBoundingBoxminY + getEntityBoundingBoxmaxY) / 2.0D, posZ);
      moveEntity(motionX, motionY, motionZ);
      boolean var1 = ((int)prevPosX != (int)posX) || ((int)prevPosY != (int)posY) || ((int)prevPosZ != (int)posZ);
      
      if ((var1) || (ticksExisted % 25 == 0))
      {
        if (worldObj.getBlockState(new net.minecraft.util.BlockPos(this)).getBlock().getMaterial() == net.minecraft.block.material.Material.lava)
        {
          motionY = 0.20000000298023224D;
          motionX = ((rand.nextFloat() - rand.nextFloat()) * 0.2F);
          motionZ = ((rand.nextFloat() - rand.nextFloat()) * 0.2F);
          playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
        }
        
        if (!worldObj.isRemote)
        {
          searchForOtherItemsNearby();
        }
      }
      
      float var2 = 0.98F;
      
      if (onGround)
      {
        var2 = worldObj.getBlockState(new net.minecraft.util.BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(getEntityBoundingBoxminY) - 1, MathHelper.floor_double(posZ))).getBlock().slipperiness * 0.98F;
      }
      
      motionX *= var2;
      motionY *= 0.9800000190734863D;
      motionZ *= var2;
      
      if (onGround)
      {
        motionY *= -0.5D;
      }
      
      if (age != 32768)
      {
        age += 1;
      }
      
      handleWaterMovement();
      
      if ((!worldObj.isRemote) && (age >= 6000))
      {
        setDead();
      }
    }
  }
  



  private void searchForOtherItemsNearby()
  {
    Iterator var1 = worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D)).iterator();
    
    while (var1.hasNext())
    {
      EntityItem var2 = (EntityItem)var1.next();
      combineItems(var2);
    }
  }
  




  private boolean combineItems(EntityItem other)
  {
    if (other == this)
    {
      return false;
    }
    if ((other.isEntityAlive()) && (isEntityAlive()))
    {
      ItemStack var2 = getEntityItem();
      ItemStack var3 = other.getEntityItem();
      
      if ((delayBeforeCanPickup != 32767) && (delayBeforeCanPickup != 32767))
      {
        if ((age != 32768) && (age != 32768))
        {
          if (var3.getItem() != var2.getItem())
          {
            return false;
          }
          if ((var3.hasTagCompound() ^ var2.hasTagCompound()))
          {
            return false;
          }
          if ((var3.hasTagCompound()) && (!var3.getTagCompound().equals(var2.getTagCompound())))
          {
            return false;
          }
          if (var3.getItem() == null)
          {
            return false;
          }
          if ((var3.getItem().getHasSubtypes()) && (var3.getMetadata() != var2.getMetadata()))
          {
            return false;
          }
          if (stackSize < stackSize)
          {
            return other.combineItems(this);
          }
          if (stackSize + stackSize > var3.getMaxStackSize())
          {
            return false;
          }
          

          stackSize += stackSize;
          delayBeforeCanPickup = Math.max(delayBeforeCanPickup, delayBeforeCanPickup);
          age = Math.min(age, age);
          other.setEntityItemStack(var3);
          setDead();
          return true;
        }
        


        return false;
      }
      


      return false;
    }
    


    return false;
  }
  





  public void setAgeToCreativeDespawnTime()
  {
    age = 4800;
  }
  



  public boolean handleWaterMovement()
  {
    if (worldObj.handleMaterialAcceleration(getEntityBoundingBox(), net.minecraft.block.material.Material.water, this))
    {
      if ((!inWater) && (!firstUpdate))
      {
        resetHeight();
      }
      
      inWater = true;
    }
    else
    {
      inWater = false;
    }
    
    return inWater;
  }
  




  protected void dealFireDamage(int amount)
  {
    attackEntityFrom(DamageSource.inFire, amount);
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    if ((getEntityItem() != null) && (getEntityItem().getItem() == Items.nether_star) && (source.isExplosion()))
    {
      return false;
    }
    

    setBeenAttacked();
    health = ((int)(health - amount));
    
    if (health <= 0)
    {
      setDead();
    }
    
    return false;
  }
  




  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("Health", (short)(byte)health);
    tagCompound.setShort("Age", (short)age);
    tagCompound.setShort("PickupDelay", (short)delayBeforeCanPickup);
    
    if (getThrower() != null)
    {
      tagCompound.setString("Thrower", thrower);
    }
    
    if (getOwner() != null)
    {
      tagCompound.setString("Owner", owner);
    }
    
    if (getEntityItem() != null)
    {
      tagCompound.setTag("Item", getEntityItem().writeToNBT(new NBTTagCompound()));
    }
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    health = (tagCompund.getShort("Health") & 0xFF);
    age = tagCompund.getShort("Age");
    
    if (tagCompund.hasKey("PickupDelay"))
    {
      delayBeforeCanPickup = tagCompund.getShort("PickupDelay");
    }
    
    if (tagCompund.hasKey("Owner"))
    {
      owner = tagCompund.getString("Owner");
    }
    
    if (tagCompund.hasKey("Thrower"))
    {
      thrower = tagCompund.getString("Thrower");
    }
    
    NBTTagCompound var2 = tagCompund.getCompoundTag("Item");
    setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
    
    if (getEntityItem() == null)
    {
      setDead();
    }
  }
  



  public void onCollideWithPlayer(EntityPlayer entityIn)
  {
    if (!worldObj.isRemote)
    {
      ItemStack var2 = getEntityItem();
      int var3 = stackSize;
      
      if ((delayBeforeCanPickup == 0) && ((owner == null) || (6000 - age <= 200) || (owner.equals(entityIn.getName()))) && (inventory.addItemStackToInventory(var2)))
      {
        if (var2.getItem() == Item.getItemFromBlock(Blocks.log))
        {
          entityIn.triggerAchievement(AchievementList.mineWood);
        }
        
        if (var2.getItem() == Item.getItemFromBlock(Blocks.log2))
        {
          entityIn.triggerAchievement(AchievementList.mineWood);
        }
        
        if (var2.getItem() == Items.leather)
        {
          entityIn.triggerAchievement(AchievementList.killCow);
        }
        
        if (var2.getItem() == Items.diamond)
        {
          entityIn.triggerAchievement(AchievementList.diamonds);
        }
        
        if (var2.getItem() == Items.blaze_rod)
        {
          entityIn.triggerAchievement(AchievementList.blazeRod);
        }
        
        if ((var2.getItem() == Items.diamond) && (getThrower() != null))
        {
          EntityPlayer var4 = worldObj.getPlayerEntityByName(getThrower());
          
          if ((var4 != null) && (var4 != entityIn))
          {
            var4.triggerAchievement(AchievementList.diamondsToYou);
          }
        }
        
        if (!isSlient())
        {
          worldObj.playSoundAtEntity(entityIn, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        
        entityIn.onItemPickup(this, var3);
        
        if (stackSize <= 0)
        {
          setDead();
        }
      }
    }
  }
  



  public String getName()
  {
    return hasCustomName() ? getCustomNameTag() : net.minecraft.util.StatCollector.translateToLocal("item." + getEntityItem().getUnlocalizedName());
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
  



  public void travelToDimension(int dimensionId)
  {
    super.travelToDimension(dimensionId);
    
    if (!worldObj.isRemote)
    {
      searchForOtherItemsNearby();
    }
  }
  




  public ItemStack getEntityItem()
  {
    ItemStack var1 = getDataWatcher().getWatchableObjectItemStack(10);
    
    if (var1 == null)
    {
      if (worldObj != null)
      {
        logger.error("Item entity " + getEntityId() + " has no item?!");
      }
      
      return new ItemStack(Blocks.stone);
    }
    

    return var1;
  }
  




  public void setEntityItemStack(ItemStack stack)
  {
    getDataWatcher().updateObject(10, stack);
    getDataWatcher().setObjectWatched(10);
  }
  
  public String getOwner()
  {
    return owner;
  }
  
  public void setOwner(String owner)
  {
    this.owner = owner;
  }
  
  public String getThrower()
  {
    return thrower;
  }
  
  public void setThrower(String thrower)
  {
    this.thrower = thrower;
  }
  
  public int func_174872_o()
  {
    return age;
  }
  
  public void setDefaultPickupDelay()
  {
    delayBeforeCanPickup = 10;
  }
  
  public void setNoPickupDelay()
  {
    delayBeforeCanPickup = 0;
  }
  
  public void setInfinitePickupDelay()
  {
    delayBeforeCanPickup = 32767;
  }
  
  public void setPickupDelay(int ticks)
  {
    delayBeforeCanPickup = ticks;
  }
  
  public boolean func_174874_s()
  {
    return delayBeforeCanPickup > 0;
  }
  
  public void func_174873_u()
  {
    age = 59536;
  }
  
  public void func_174870_v()
  {
    setInfinitePickupDelay();
    age = 5999;
  }
}
