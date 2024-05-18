package com.darkcart.xdolf.util;

import java.util.Iterator;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class EntityHelper {
	public static boolean isAttackable(EntityEnderCrystal entity)
	  {
	    return (entity != null) && (entity.isEntityAlive());
	  }
	  
	  public static boolean isReachable(EntityEnderCrystal entity)
	  {
	    return Wrapper.getMinecraft().player.getDistanceToEntity(
	      entity) <= 5;
	  }
	  
	  public static boolean isFar(EntityEnderCrystal entity)
	  {
	    return Wrapper.getMinecraft().player.getDistanceToEntity(
	      entity) > 5;
	  }
	  
	  public static boolean isCloser(Entity entityCompared, Entity referenceEntity)
	  {
	    return Wrapper.getMinecraft().player
	      .getDistanceToEntity(entityCompared) < Wrapper.getMinecraft().player
	      .getDistanceToEntity(referenceEntity);
	  }
	public static EntityEnderCrystal getClosestEntity(boolean killaura)
	  {
	    return getClosestEntityWithoutReachFactor();
	  }
	public static EntityEnderCrystal getClosestEntityWithoutReachFactor()
	  {
	    EntityEnderCrystal closestEntity = null;
	    float distance = 9999.0F;
	    for (Object object : Wrapper.getMinecraft().world.loadedEntityList) {
	      if ((object instanceof EntityLivingBase))
	      {
	        EntityEnderCrystal entity = (EntityEnderCrystal)object;
	        if (isAttackable(entity))
	        {
	          float newDistance = 
	            Wrapper.getMinecraft().player.getDistanceToEntity(entity);
	          if ((entity instanceof EntityEnderCrystal))
	              if (closestEntity != null)
	              {
	                if (distance > newDistance)
	                {
	                  closestEntity = entity;
	                  distance = newDistance;
	                }
	              }
	              else
	              {
	                closestEntity = entity;
	                distance = newDistance;
	              }
	            }
	          }
	        }
	    return closestEntity;
	  }
	  public static EntityLivingBase getClosestEntity()
	  {
	    EntityLivingBase closestEntity = null;
	    Iterator localIterator;
	      localIterator = Wrapper.getMinecraft().world.loadedEntityList.iterator();
	      while (localIterator.hasNext())
	      {
	        Object object = localIterator.next();
	        if (object != Wrapper.getMinecraft().player) {
	          if ((object instanceof EntityLivingBase))
	          {
	            EntityLivingBase mob = (EntityLivingBase)object;
	            if (mob.rotationPitch != 0.0F) {
	              if (closestEntity != null)
	              {
	              if ((isCloser(mob, closestEntity))){
	                  closestEntity = mob;
	                }
	              }
	              else {
	                closestEntity = mob;
	              }
	            }
	          }
	        }
	      }
	      return closestEntity;
	    }
	public static void faceEntity(Entity entity)
	  {
	    faceEntity(entity, 20.0F);
	  }
	  
	  public static void faceEntity(Entity entity, float limit)
	  {
	    if (entity == null) {
	      return;
	    }
	    double diffX = entity.posX - Wrapper.getMinecraft().player.posX;
	    double diffZ = entity.posZ - Wrapper.getMinecraft().player.posZ;
	    double diffY;
	    if ((entity instanceof EntityLivingBase))
	    {
	      EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
	      diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (
	    		  Wrapper.getMinecraft().player.posY + 
	    		  Wrapper.getMinecraft().player.getEyeHeight());
	    }
	    else
	    {
	      diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (
	    		  Wrapper.getMinecraft().player.posY + 
	        Wrapper.getMinecraft().player.getEyeHeight());
	    }
	    double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
	    
	    float yaw = 
	      (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = 
	      (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
	    
	    float diffYaw = 
	      MathHelper.wrapDegrees(yaw - Wrapper.getMinecraft().player.rotationYaw);
	    float diffPitch = 
	      MathHelper.wrapDegrees(pitch - Wrapper.getMinecraft().player.rotationPitch);
	    if (diffYaw > limit) {
	      diffYaw = limit;
	    } else if (diffYaw < -limit) {
	      diffYaw = -limit;
	    }
	    Wrapper.getMinecraft().player.rotationYaw += diffYaw;
	    Wrapper.getMinecraft().player.rotationPitch += diffPitch;
	  }
}
