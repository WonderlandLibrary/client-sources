package lunadevs.luna.utils;


import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class EntityUtilsTwo
{
  public static boolean lookChanged;
  public static float yaw;
  public static float pitch;
  public static Minecraft mc = Minecraft.getMinecraft();
  
  public static synchronized void faceEntityClient(EntityLivingBase entity)
  {
    float[] rotations = getRotationsNeeded(entity);
    if (rotations != null)
    {
      Minecraft.getMinecraft().thePlayer.rotationYaw = limitAngleChange(Minecraft.getMinecraft().thePlayer.prevRotationYaw, rotations[0], 55.0F);
      Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1];
    }
  }
  
  public static void attackEntity(EntityLivingBase entity)
  {
    Minecraft.getMinecraft().thePlayer.swingItem();
    
    Minecraft.getMinecraft().playerController.attackEntity(mc.thePlayer, entity);
  }
  
  public static void doCritical()
  {
    if ((!Minecraft.getMinecraft().thePlayer.isInWater()) && (!Minecraft.getMinecraft().thePlayer.isInsideOfMaterial(Material.lava)) && (Minecraft.getMinecraft().thePlayer.onGround))
    {
      Minecraft.getMinecraft().thePlayer.motionY = 0.10000000149011612D;
      Minecraft.getMinecraft().thePlayer.fallDistance = 0.1F;
      Minecraft.getMinecraft().thePlayer.onGround = false;
    }
  }
  
  public static synchronized float getDistanceToEntity(EntityLivingBase entity)
  {
    return mc.thePlayer.getDistanceToEntity(entity);
  }
  
  public static synchronized void faceEntityPacket(EntityLivingBase entity)
  {
    float[] rotations = getRotationsNeeded(entity);
    if (rotations != null)
    {
      yaw = limitAngleChange(Minecraft.getMinecraft().thePlayer.prevRotationYaw, rotations[0], 55.0F);
      pitch = rotations[1];
      lookChanged = true;
    }
  }
  
  public static float[] getRotationsNeeded(Entity entity)
  {
    if (entity == null) {
      return null;
    }
    double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
    double diffY;

    if ((entity instanceof EntityLivingBase))
    {
      EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
      diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9D - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
    }
    else
    {
      diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
    }
    double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)(-Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
    return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
  }
  
  private static final float limitAngleChange(float current, float intended, float maxChange)
  {
    float change = intended - current;
    if (change > maxChange) {
      change = maxChange;
    } else if (change < -maxChange) {
      change = -maxChange;
    }
    return current + change;
  }
  
  public static int getDistanceFromMouse(Entity entity)
  {
    float[] neededRotations = getRotationsNeeded(entity);
    if (neededRotations != null)
    {
      float neededYaw = Minecraft.getMinecraft().thePlayer.rotationYaw - neededRotations[0];
      float neededPitch = Minecraft.getMinecraft().thePlayer.rotationPitch - neededRotations[1];
      float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
      return (int)distanceFromMouse;
    }
    return -1;
  }
  
  private static boolean checkName(String name)
  {
    String[] colors = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    boolean unknownColor = true;
    int i2 = 0;
    while (i2 < 16)
    {
      if (name.contains("?" + colors[i2])) {
        return true;
      }
      unknownColor = false;
      i2++;
    }
    if ((!unknownColor) && (name.contains("?"))) {
      return false;
    }
    return true;
  }
  
  public static EntityLivingBase getClosestEntity(boolean ignoreFriends, boolean useFOV)
  {
    EntityLivingBase closestEntity = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
      if (((o instanceof EntityLivingBase)) && 
        (getDistanceFromMouse((Entity)o) <= 180))
      {
        EntityLivingBase en = (EntityLivingBase)o;
        if ((!(o instanceof EntityPlayerSP)) && 
          (!en.isDead) && 
          (en.getHealth() > 0.0F) && 
          (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en)) && 
          (!en.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) && (
          (closestEntity == null) || 
          
          (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity)))) {
          closestEntity = en;
        }
      }
    }
    return closestEntity;
  }
  
  public static ArrayList<EntityLivingBase> getCloseEntities(float range)
  {
    ArrayList<EntityLivingBase> closeEntities = new ArrayList();
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (!en2.isDead) && (en2.getHealth() > 0.0F) && (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en2)) && (!en2.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) && (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en2) <= range)) {
        closeEntities.add(en2);
      }
    }
    return closeEntities;
  }
  
  public static EntityLivingBase getClosestEntityRaw()
  {
    EntityLivingBase closestEntity = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (!en2.isDead) && (en2.getHealth() > 0.0F) && ((closestEntity == null) || (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en2) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity)))) {
        closestEntity = en2;
      }
    }
    return closestEntity;
  }
  
  public static EntityLivingBase getClosestEnemy(EntityLivingBase friend)
  {
    EntityLivingBase closestEnemy = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (o != friend) && (!en2.isDead) && (en2.getHealth() > 0.0F) && (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en2)) && ((closestEnemy == null) || (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en2) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEnemy)))) {
        closestEnemy = en2;
      }
    }
    return closestEnemy;
  }
  
  public static EntityLivingBase searchEntityByIdRaw(UUID ID)
  {
    EntityLivingBase newEntity = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (!en2.isDead) && (newEntity == null) && (en2.getUniqueID().equals(ID))) {
        newEntity = en2;
      }
    }
    return newEntity;
  }
  
  public static EntityLivingBase searchEntityByName(String name)
  {
    EntityLivingBase newEntity = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (!en2.isDead) && (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en2)) && (newEntity == null) && (en2.getName().equals(name))) {
        newEntity = en2;
      }
    }
    return newEntity;
  }
  
  public static EntityLivingBase searchEntityByNameRaw(String name)
  {
    EntityLivingBase newEntity = null;
    for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
    {
      EntityLivingBase en2 = (EntityLivingBase)o;
      if ((!(o instanceof EntityPlayerSP)) && (!en2.isDead) && (newEntity == null) && (en2.getName().equals(name))) {
        newEntity = en2;
      }
    }
    return newEntity;
  }
}
