package me.hexxed.mercury.modules;

import java.util.Queue;
import java.util.Random;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class Killaura extends Module
{
  public Killaura()
  {
    super("Killaura", 45, true, me.hexxed.mercury.modulebase.ModuleCategory.COMBAT);
  }
  
  public static Queue<Entity> entityList = new java.util.concurrent.ConcurrentLinkedQueue();
  public boolean players = true;
  public boolean mobs; public boolean animals; public boolean block; public double distance = getValuesdistance;
  private TimeHelper time = new TimeHelper();
  private TimeHelper blocktime = new TimeHelper();
  private TimeHelper targetSwitchTime = new TimeHelper();
  private TimeHelper angleSwitchTime = new TimeHelper();
  private boolean shouldblock = false;
  public int aps = time.convertToMS(getValuescps);
  public static Entity target = null;
  private boolean wassprint = false;
  private boolean waslistfull = false;
  private boolean wasblock = false;
  private EntityLivingBase nexttarget;
  public static float[] rot = { 0.0F, 0.0F };
  private float lastYaw;
  private float lastPitch;
  
  public void onPreMotionUpdate()
  {
    if (mc.theWorld == null) return;
    aps = time.convertToMS(getValuescps);
    distance = getValuesdistance;
    populateEntityList();
    nexttarget = setupForNextEntity();
    try {
      if ((entityList.isEmpty()) && (lasthit.isDelayComplete(1000L)) && (isSword(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
        wasblock = mc.thePlayer.isBlocking();
      }
      if ((!entityList.isEmpty()) && (getValuesautoblock) && (isSword(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
        mc.gameSettings.keyBindUseItem.pressed = true;
        waslistfull = true;
      } else {
        waslistfull = false;
      }
      if ((lasthit.isDelayComplete(250L)) && (getValuesautoblock) && (!lasthit.isDelayComplete(500L)) && (isSword(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
        mc.gameSettings.keyBindUseItem.pressed = false;
        for (int i = 0; i <= 2; i++) {
          mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
      }
    }
    catch (NullPointerException localNullPointerException) {}
    
    lastYaw = mc.thePlayer.rotationYaw;
    lastPitch = mc.thePlayer.rotationPitch;
    if (!lasthit.isDelayComplete(2000L)) {
      mc.thePlayer.rotationYaw = rot[0];
      mc.thePlayer.rotationPitch = rot[1];
    }
  }
  
  public void onPostMotionUpdate()
  {
    if (mc.theWorld == null) return;
    wassprint = mc.thePlayer.isSprinting();
    mc.thePlayer.setSprinting(false);
    attackEntity(nexttarget);
    mc.thePlayer.setSprinting(wassprint);
    mc.thePlayer.rotationYaw = lastYaw;
    mc.thePlayer.rotationPitch = lastPitch;
  }
  
  public void onDisable()
  {
    try {
      if ((getValuesautoblock) && (isSword(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
        mc.gameSettings.keyBindUseItem.pressed = false;
        for (int i = 0; i <= 2; i++) {
          mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
      }
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  private void populateEntityList()
  {
    for (Object o : mc.theWorld.loadedEntityList) {
      Entity e = (Entity)o;
      if (isAttackable(e))
        entityList.add(e);
    }
  }
  
  private TimeHelper lasthit = new TimeHelper();
  
  boolean editpacket = false;
  








  private EntityLivingBase lastAttackedTarget;
  









  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer localC03PacketPlayer = (C03PacketPlayer)packet;
    }
  }
  






  private EntityLivingBase setupForNextEntity()
  {
    Random rand = new Random();
    EntityLivingBase toattack = null;
    try {
      for (Entity e : entityList) {
        if (time.isDelayComplete(rand.nextInt(aps - 53 + 1) + (aps - 20))) {
          if ((isViableToAttack((EntityLivingBase)e)) && (isAttackable(e))) {
            rot = getAngles(e);
            target = e;
            toattack = (EntityLivingBase)e;
            if (mc.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
              }
            }
            time.setLastMS();
            entityList.remove(e);
          } else {
            entityList.remove(e);
          }
        }
      }
    } catch (NullPointerException localNullPointerException) {}
    return toattack;
  }
  
  private void attackEntity(EntityLivingBase e) {
    try {
      mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
      mc.thePlayer.swingItem();
      lasthit.setLastMS();
      if (mc.thePlayer.isBlocking()) {
        for (int i = 0; i <= 2; i++) {
          mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
        }
      }
      targetSwitchTime.setLastMS();
      lastAttackedTarget = ((EntityLivingBase)target);
      target = null;
      mc.thePlayer.setSprinting(wassprint);
      nexttarget = null;
    } catch (NullPointerException localNullPointerException) {}
  }
  
  private void attackEntityList() {
    Random rand = new Random();
    try {
      for (Entity e : entityList)
      {
        if (time.isDelayComplete(rand.nextInt(aps - 53 + 1) + (aps - 20))) {
          if (isViableToAttack((EntityLivingBase)e)) {
            rot = getAngles(e);
            mc.thePlayer.setSprinting(false);
            target = e;
            if (mc.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
              }
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            


            mc.thePlayer.swingItem();
            lasthit.setLastMS();
            
            entityList.remove(e);
            time.setLastMS();
            if (mc.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
              }
            }
            


            targetSwitchTime.setLastMS();
            lastAttackedTarget = ((EntityLivingBase)target);
            target = null;
            mc.thePlayer.setSprinting(wassprint);
          } else {
            entityList.remove(e);
          }
        }
      }
    } catch (NullPointerException localNullPointerException) {}
  }
  
  private boolean isAttackable(Entity e) {
    if (e == null)
      return false;
    if (((e instanceof EntityPlayer)) && (players)) {
      EntityPlayer p = (EntityPlayer)e;
      if (me.hexxed.mercury.util.Friends.isFriend(e.getName())) return false;
      if ((!isDead) && (ticksExisted > getValuesticks) && ((!p.isInvisible()) || (getValuesinvisibles))) if ((mc.thePlayer.getDistanceToEntity(p) <= (mc.thePlayer.canEntityBeSeen(p) ? distance : 2.8D)) && (p != mc.thePlayer)) {
          return true;
        }
    } else if ((e instanceof EntityMob)) {
      EntityMob m = (EntityMob)e;
      if ((!isDead) && (ticksExisted > getValuesticks) && ((!m.isInvisible()) || (getValuesinvisibles))) if (mc.thePlayer.getDistanceToEntity(m) <= (mc.thePlayer.canEntityBeSeen(m) ? distance : 2.8D)) {
          return true;
        }
    } else if ((e instanceof EntityAnimal)) {
      EntityAnimal a = (EntityAnimal)e;
      if ((!isDead) && (ticksExisted > getValuesticks) && ((!a.isInvisible()) || (getValuesinvisibles))) { if (mc.thePlayer.getDistanceToEntity(a) <= (mc.thePlayer.canEntityBeSeen(a) ? distance : 2.8D)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public float[] getAngles(Entity entity) {
    float xDiff = (float)(posX - mc.thePlayer.posX);
    float yDiff = (float)(boundingBox.minY + entity.getEyeHeight() - mc.thePlayer.boundingBox.maxY);
    float zDiff = (float)(posZ - mc.thePlayer.posZ);
    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
    float pitch = (float)-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
    return new float[] {
      yaw, pitch };
  }
  

  private float updateRotation(float par1, float par2, float par3)
  {
    float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
    if (var4 > par3) {
      var4 = par3;
    }
    if (var4 < -par3) {
      var4 = -par3;
    }
    return par1 + var4;
  }
  
  private boolean isSword(Item item)
  {
    return item instanceof net.minecraft.item.ItemSword;
  }
  
  public boolean isViableToAttack(EntityLivingBase p) {
    double range = mc.thePlayer.isSprinting() ? distance - 0.20000000298023224D : distance;
    

    float playerYaw = lastYaw;
    float desiredYaw = getAngles(p)[0];
    float distYaw = (playerYaw - desiredYaw + 360.0F) % 360.0F;
    if (distYaw > 180.0F) { distYaw = 360.0F - distYaw;
    }
    
    float playerPitch = lastPitch;
    float desiredPitch = getAngles(p)[1];
    float distPitch = (playerPitch - desiredPitch + 360.0F) % 360.0F;
    if (distPitch > 180.0F) { distPitch = 360.0F - distPitch;
    }
    float distCombined = distYaw + distPitch;
    double maxAngle = 60.0D;
    if ((lastYaw != mc.thePlayer.rotationYaw) || (lastPitch != mc.thePlayer.rotationPitch)) {
      maxAngle = 45.0D;
    }
    boolean angleCheck = distCombined < maxAngle;
    if (!angleCheck) {} angleCheck = angleSwitchTime.isDelayComplete(entityList.size() <= 3 ? 10 : 60);
    if (angleCheck) {
      angleSwitchTime.setLastMS();
    }
    return (isAttackable(p)) && (mc.thePlayer.getDistanceSqToEntity(p) < range * range) && (
      (p != lastAttackedTarget) || (entityList.size() <= 1) || ((targetSwitchTime.isDelayComplete(200L)) && 
      ((hurtTime <= 2) || (entityList.size() <= 1)) && (
      (angleCheck) || (entityList.size() <= 1))));
  }
}
