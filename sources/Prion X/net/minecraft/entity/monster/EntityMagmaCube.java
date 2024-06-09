package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime
{
  private static final String __OBFID = "CL_00001691";
  
  public EntityMagmaCube(World worldIn)
  {
    super(worldIn);
    isImmuneToFire = true;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
  }
  



  public boolean getCanSpawnHere()
  {
    return worldObj.getDifficulty() != net.minecraft.world.EnumDifficulty.PEACEFUL;
  }
  



  public boolean handleLavaMovement()
  {
    return (worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)) && (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) && (!worldObj.isAnyLiquid(getEntityBoundingBox()));
  }
  



  public int getTotalArmorValue()
  {
    return getSlimeSize() * 3;
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    return 15728880;
  }
  



  public float getBrightness(float p_70013_1_)
  {
    return 1.0F;
  }
  
  protected EnumParticleTypes func_180487_n()
  {
    return EnumParticleTypes.FLAME;
  }
  
  protected EntitySlime createInstance()
  {
    return new EntityMagmaCube(worldObj);
  }
  
  protected Item getDropItem()
  {
    return Items.magma_cream;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    Item var3 = getDropItem();
    
    if ((var3 != null) && (getSlimeSize() > 1))
    {
      int var4 = rand.nextInt(4) - 2;
      
      if (p_70628_2_ > 0)
      {
        var4 += rand.nextInt(p_70628_2_ + 1);
      }
      
      for (int var5 = 0; var5 < var4; var5++)
      {
        dropItem(var3, 1);
      }
    }
  }
  



  public boolean isBurning()
  {
    return false;
  }
  



  protected int getJumpDelay()
  {
    return super.getJumpDelay() * 4;
  }
  
  protected void alterSquishAmount()
  {
    squishAmount *= 0.9F;
  }
  



  protected void jump()
  {
    motionY = (0.42F + getSlimeSize() * 0.1F);
    isAirBorne = true;
  }
  
  protected void func_180466_bG()
  {
    motionY = (0.22F + getSlimeSize() * 0.05F);
    isAirBorne = true;
  }
  


  public void fall(float distance, float damageMultiplier) {}
  

  protected boolean canDamagePlayer()
  {
    return true;
  }
  



  protected int getAttackStrength()
  {
    return super.getAttackStrength() + 2;
  }
  



  protected String getJumpSound()
  {
    return getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
  }
  



  protected boolean makesSoundOnLand()
  {
    return true;
  }
}
