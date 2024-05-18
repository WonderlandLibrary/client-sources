package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;


public class EntityMoveHelper
{
  protected EntityLiving entity;
  protected double posX;
  protected double posY;
  protected double posZ;
  protected double speed;
  protected boolean update;
  private static final String __OBFID = "CL_00001573";
  
  public EntityMoveHelper(EntityLiving p_i1614_1_)
  {
    entity = p_i1614_1_;
    posX = posX;
    posY = posY;
    posZ = posZ;
  }
  
  public boolean isUpdating()
  {
    return update;
  }
  
  public double getSpeed()
  {
    return speed;
  }
  



  public void setMoveTo(double p_75642_1_, double p_75642_3_, double p_75642_5_, double p_75642_7_)
  {
    posX = p_75642_1_;
    posY = p_75642_3_;
    posZ = p_75642_5_;
    speed = p_75642_7_;
    update = true;
  }
  
  public void onUpdateMoveHelper()
  {
    entity.setMoveForward(0.0F);
    
    if (update)
    {
      update = false;
      int var1 = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5D);
      double var2 = posX - entity.posX;
      double var4 = posZ - entity.posZ;
      double var6 = posY - var1;
      double var8 = var2 * var2 + var6 * var6 + var4 * var4;
      
      if (var8 >= 2.500000277905201E-7D)
      {
        float var10 = (float)(Math.atan2(var4, var2) * 180.0D / 3.141592653589793D) - 90.0F;
        entity.rotationYaw = limitAngle(entity.rotationYaw, var10, 30.0F);
        entity.setAIMoveSpeed((float)(speed * entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
        
        if ((var6 > 0.0D) && (var2 * var2 + var4 * var4 < 1.0D))
        {
          entity.getJumpHelper().setJumping();
        }
      }
    }
  }
  



  protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_)
  {
    float var4 = MathHelper.wrapAngleTo180_float(p_75639_2_ - p_75639_1_);
    
    if (var4 > p_75639_3_)
    {
      var4 = p_75639_3_;
    }
    
    if (var4 < -p_75639_3_)
    {
      var4 = -p_75639_3_;
    }
    
    float var5 = p_75639_1_ + var4;
    
    if (var5 < 0.0F)
    {
      var5 += 360.0F;
    }
    else if (var5 > 360.0F)
    {
      var5 -= 360.0F;
    }
    
    return var5;
  }
  
  public double func_179917_d()
  {
    return posX;
  }
  
  public double func_179919_e()
  {
    return posY;
  }
  
  public double func_179918_f()
  {
    return posZ;
  }
}
