package net.SliceClient.Utils;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class CombatUtils
{
  public Minecraft mc = Minecraft.getMinecraft();
  public ArrayList<Entity> entities = new ArrayList();
  public float delay;
  public int lookDelay;
  
  public CombatUtils() {}
  
  public Entity getBestEntity(double range, float fov, boolean rayTrace, int ticksExisted, int invisibles, int players, int mobs, int animals) { Entity tempEntity = null;
    double dist = range;
    for (Object i : theWorldloadedEntityList)
    {
      Entity entity = (Entity)i;
      if (shouldHitEntity(entity, range, fov, rayTrace, ticksExisted, invisibles, players, mobs, animals))
      {
        double curDist = Minecraft.thePlayer.getDistanceToEntity(entity);
        if (curDist <= dist)
        {
          dist = curDist;
          tempEntity = entity;
        }
      }
    }
    return tempEntity;
  }
  
  public void clearEntities()
  {
    entities.clear();
  }
  
  public boolean shouldHitEntity(Entity e, double range, float fov, boolean rayTrace, int ticksExisted, int invisibles, int players, int mobs, int animals)
  {
    boolean isAlive = e instanceof net.minecraft.entity.EntityLivingBase;
    boolean isNotMe = e != Minecraft.thePlayer;
    boolean isNotNull = e != null;
    boolean isInRange = Minecraft.thePlayer.getDistanceToEntity(e) <= range;
    boolean isInFov = isVisibleFOV(e, Minecraft.thePlayer, fov);
    boolean isNotDead = !isDead;
    boolean isNotAACGarb = e.getEntityId() < 1000000000;
    boolean canSeeEntity = Minecraft.thePlayer.canEntityBeSeen(e);
    boolean ticks = ticksExisted >= ticksExisted;
    boolean isNotFakeDummie = e.getName() != Minecraft.thePlayer.getName();
    boolean isValidEntity = ((mobs == 1) && ((e instanceof EntityMob)) && (!e.isInvisible()) && (!(e instanceof EntityAnimal)) && (!(e instanceof EntityPlayer))) || ((animals == 1) && ((e instanceof EntityAnimal)) && (!e.isInvisible()) && (!(e instanceof EntityMob)) && (!(e instanceof EntityPlayer))) || ((players == 1) && ((e instanceof EntityPlayer)) && (!e.isInvisible()) && (!(e instanceof EntityAnimal)) && (!(e instanceof EntityMob))) || ((invisibles == 1) && (e.isInvisible()));
    if (rayTrace) {
      return (isAlive) && (isNotFakeDummie) && (canSeeEntity) && (isValidEntity) && (ticks) && (isNotDead) && (isInFov) && (isNotMe) && (isNotNull) && (isInRange);
    }
    return (isAlive) && (isNotFakeDummie) && (isNotAACGarb) && (ticks) && (isValidEntity) && (isNotDead) && (isInFov) && (isNotMe) && (isNotNull) && (isInRange);
  }
  
  public void attackTarget(Entity entity)
  {
    Minecraft.thePlayer.swingItem();
    
    thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
  }
  
  public float getPitchChange(Entity entity)
  {
    Minecraft.getMinecraft();double deltaX = posX - thePlayerposX;
    Minecraft.getMinecraft();double deltaZ = posZ - thePlayerposZ;
    Minecraft.getMinecraft();double deltaY = posY - 2.2D + entity.getEyeHeight() - thePlayerposY;
    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    Minecraft.getMinecraft();return -MathHelper.wrapAngleTo180_float(thePlayerrotationPitch - (float)pitchToEntity) - 2.5F;
  }
  
  public float getYawChange(Entity entity)
  {
    Minecraft.getMinecraft();double deltaX = posX - thePlayerposX;
    Minecraft.getMinecraft();double deltaZ = posZ - thePlayerposZ;
    double yawToEntity = 0.0D;
    if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
      yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
      yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else {
      yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
    }
    Minecraft.getMinecraft();return MathHelper.wrapAngleTo180_float(-(thePlayerrotationYaw - (float)yawToEntity));
  }
  
  public boolean canCrit()
  {
    return (!Minecraft.thePlayer.isInWater()) && (thePlayeronGround);
  }
  
  public boolean needsHealth()
  {
    boolean isLow = Minecraft.thePlayer.getHealth() <= 20.0F;
    return isLow;
  }
  
  public void faceEntity(Entity entity)
  {
    double diffX = posX - thePlayerposX;
    double diffZ = posZ - thePlayerposZ;
    double diffY = posY + entity.getEyeHeight() - thePlayerposY + Minecraft.thePlayer.getEyeHeight() * -2.0F + 1.2999999523162842D;
    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
    thePlayerrotationPitch += MathHelper.wrapAngleTo180_float(pitch - thePlayerrotationPitch);
    thePlayerrotationYaw += MathHelper.wrapAngleTo180_float(yaw - thePlayerrotationYaw);
  }
  
  public boolean isMouseOverEntity(Entity entity)
  {
    if (mc.objectMouseOver.entityHit == entity) {
      return true;
    }
    return false;
  }
  
  private Float[] getRotations(Entity entity)
  {
    double posX = posX - thePlayerposX;
    double posZ = posZ - thePlayerposZ;
    double posY = posY + entity.getEyeHeight() - thePlayerposY + Minecraft.thePlayer.getEyeHeight();
    double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
    float newPitch = (float)-Math.toDegrees(Math.atan(posY / helper));
    if ((posZ < 0.0D) && (posX < 0.0D)) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    } else if ((posZ < 0.0D) && (posX > 0.0D)) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    }
    return new Float[] { Float.valueOf(newYaw), Float.valueOf(newPitch) };
  }
  
  private Float[] getRotationsTileEntity(TileEntity entity)
  {
    double posX = entity.getPos().getX() - thePlayerposX;
    double posZ = entity.getPos().getY() - thePlayerposZ;
    double posY = entity.getPos().getZ() + 1 - thePlayerposY + Minecraft.thePlayer.getEyeHeight();
    double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
    float newPitch = (float)-Math.toDegrees(Math.atan(posY / helper));
    if ((posZ < 0.0D) && (posX < 0.0D)) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    } else if ((posZ < 0.0D) && (posX > 0.0D)) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    }
    return new Float[] { Float.valueOf(newYaw), Float.valueOf(newPitch) };
  }
  
  public boolean faceEntitySmooth(Entity e, float speed)
  {
    double x = posX - thePlayerposX;
    double y = posY - thePlayerposY;
    double z = posZ - thePlayerposZ;
    Minecraft.getMinecraft();double d1 = thePlayerposY + Minecraft.thePlayer.getEyeHeight() - (posY + e.getEyeHeight());
    double d3 = MathHelper.sqrt_double(x * x + z * z);
    float f = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
    float f1 = (float)(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
    
    f = MathHelper.wrapAngleTo180_float(f);
    f1 = MathHelper.wrapAngleTo180_float(f1);
    
    f -= MathHelper.wrapAngleTo180_float(thePlayerrotationYaw);
    f1 -= MathHelper.wrapAngleTo180_float(thePlayerrotationPitch);
    
    boolean facing = (f < speed) && (f1 < speed) && (f > -speed) && (f1 > -speed);
    if (f > 0.0F) {
      f = MathHelper.clamp_float(f, 0.0F, speed);
    } else if (f < 0.0F) {
      f = MathHelper.clamp_float(f, -speed, 0.0F);
    }
    if (f1 > 0.0F) {
      f1 = MathHelper.clamp_float(f1, 0.0F, speed);
    } else if (f1 < 0.0F) {
      f1 = MathHelper.clamp_float(f1, -speed, 0.0F);
    }
    thePlayerrotationYaw += f;
    thePlayerrotationPitch += f1;
    
    return facing;
  }
  
  public boolean isVisibleFOV(Entity e, Entity e2, float fov)
  {
    return (Math.abs(getRotations(e)[0].floatValue() - rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(getRotations(e)[0].floatValue() - rotationYaw) % 360.0F : Math.abs(getRotations(e)[0].floatValue() - rotationYaw) % 360.0F) <= fov;
  }
  
  public boolean isVisibleFOVTileEntity(TileEntity e, Entity e2, float fov)
  {
    return (Math.abs(getRotationsTileEntity(e)[0].floatValue() - rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(getRotationsTileEntity(e)[0].floatValue() - rotationYaw) % 360.0F : Math.abs(getRotationsTileEntity(e)[0].floatValue() - rotationYaw) % 360.0F) <= fov;
  }
  
  public boolean canAttackEntityNotLegit(Entity e)
  {
    if (e == null) {
      return false;
    }
    boolean isNotMe = e != Minecraft.thePlayer;
    boolean isInRange = Minecraft.thePlayer.getDistanceToEntity(e) <= 6.0F;
    boolean isAlive = ((e instanceof net.minecraft.entity.EntityLivingBase)) && (!isDead);
    return (isNotMe) && (isInRange) && (isAlive);
  }
  
  public void attackEntityNotLegit(Entity e)
  {
    thePlayersendQueue.addToSendQueue(new net.minecraft.network.play.client.C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
  }
}
