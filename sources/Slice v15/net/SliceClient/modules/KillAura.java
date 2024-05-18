package net.SliceClient.modules;

import java.util.Collection;
import java.util.Queue;
import java.util.Random;
import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.Values.Values;
import net.SliceClient.event.UpdateEvent;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
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

public class KillAura extends Module
{
  public static Queue<Entity> entityList = new java.util.concurrent.ConcurrentLinkedQueue();
  public boolean players = true;
  public boolean mobs;
  public boolean animals;
  public boolean block;
  public double distance;
  private TimeHelper time;
  private TimeHelper blocktime;
  private TimeHelper targetSwitchTime;
  private TimeHelper angleSwitchTime;
  private boolean shouldblock;
  public int aps;
  public static Entity target = null;
  public static Entity targetedplayer = null;
  private boolean wassprint;
  private boolean waslistfull;
  private boolean wasblock;
  public static EntityLivingBase nexttarget;
  public static float[] rot = { 0.0F, 0.0F };
  private float lastYaw;
  private float lastPitch;
  public static boolean npc = true;
  
  Random rand;
  
  private TimeHelper lasthit;
  boolean editpacket;
  private EntityLivingBase lastAttackedTarget;
  
  public boolean checkEntityRadius(Entity e)
  {
    if (Minecraft.thePlayer.getCommandSenderName() == e.getCommandSenderName()) {
      return true;
    }
    if ((Minecraft.thePlayer.getDistanceToEntity(e) > 5.0F) && (e.getCommandSenderName().length() >= 16)) {
      return true;
    }
    return false;
  }
  
  @com.darkmagician6.eventapi.EventTarget
  private void preMotionUpdate(UpdateEvent event)
  {
    if (!getState()) {
      return;
    }
    if (Minecraft.theWorld == null) {
      return;
    }
    aps = 1;
    Values.getValues();distance = Values.distance;
    populateEntityList();
    nexttarget = setupForNextEntity();
    try
    {
      if ((entityList.isEmpty()) && (lasthit.isDelayComplete(10L)) && 
        (isSword(thePlayerinventory.getCurrentItem().getItem()))) {
        wasblock = Minecraft.thePlayer.isBlocking();
      }
      if (!entityList.isEmpty()) { Values.getValues(); if ((Values.autoblock) && 
          (isSword(thePlayerinventory.getCurrentItem().getItem())))
        {
          gameSettingskeyBindUseItem.pressed = true;
          waslistfull = true;
          break label178;
        }
      }
      waslistfull = false;
      label178:
      if (lasthit.isDelayComplete(250L)) { Values.getValues(); if ((Values.autoblock) && 
          (!lasthit.isDelayComplete(500L)) && 
          (isSword(thePlayerinventory.getCurrentItem().getItem())))
        {
          gameSettingskeyBindUseItem.pressed = false;
          for (int i = 0; i <= 3; i++) {
            thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(
              C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 1), EnumFacing.DOWN));
          }
        }
      }
    } catch (NullPointerException localNullPointerException) {}
    lastYaw = thePlayerrotationYaw;
    lastPitch = thePlayerrotationPitch;
    if (!lasthit.isDelayComplete(2000L))
    {
      thePlayerrotationYaw = rot[0];
      thePlayerrotationPitch = rot[1];
    }
    wassprint = Minecraft.thePlayer.isSprinting();
    Minecraft.thePlayer.setSprinting(false);
    attackEntity(nexttarget);
    targetedplayer = nexttarget;
    Minecraft.thePlayer.setSprinting(wassprint);
    thePlayerrotationYaw = lastYaw;
    thePlayerrotationPitch = lastPitch;
  }
  
  public void onDisable()
  {
    try
    {
      Values.getValues(); if ((Values.autoblock) && (isSword(thePlayerinventory.getCurrentItem().getItem())))
      {
        gameSettingskeyBindUseItem.pressed = false;
        for (int i = 0; i <= 2; i++) {
          thePlayersendQueue.addToSendQueue(new C07PacketPlayerDigging(
            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
      }
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  private void populateEntityList()
  {
    for (Object o : theWorldloadedEntityList)
    {
      Entity e = (Entity)o;
      if (isAttackable(e)) {
        entityList.add(e);
      }
    }
  }
  
  public KillAura()
  {
    super("KillAura", net.SliceClient.module.Category.COMBAT, 16376546);Values.getValues();distance = Values.distance;time = new TimeHelper();blocktime = new TimeHelper();targetSwitchTime = new TimeHelper();angleSwitchTime = new TimeHelper();shouldblock = false;aps = 1;wassprint = false;waslistfull = false;wasblock = false;rand = new Random();
    
































































































    lasthit = new TimeHelper();
    editpacket = false;
  }
  
  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer localC03PacketPlayer = (C03PacketPlayer)packet;
    }
  }
  
  public int randomNumberFromRange(int min, int max)
  {
    return rand.nextInt(max - min + 1) + max;
  }
  
  private EntityLivingBase setupForNextEntity()
  {
    EntityLivingBase toattack = null;
    try
    {
      for (Entity e : entityList) {
        if (time.isDelayComplete(randomNumberFromRange(aps * 1, aps * 10))) {
          if ((isViableToAttack((EntityLivingBase)e)) && (isAttackable(e)))
          {
            rot = getAngles(e);
            target = e;
            toattack = (EntityLivingBase)e;
            if (Minecraft.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                thePlayersendQueue.addToSendQueue(
                  new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
                  new BlockPos(0, 0, 0), EnumFacing.DOWN));
              }
            }
            time.setLastMS();
            entityList.remove(e);
          }
          else
          {
            entityList.remove(e);
          }
        }
      }
    }
    catch (NullPointerException localNullPointerException) {}
    return toattack;
  }
  
  private void attackEntity(EntityLivingBase e)
  {
    if ((e instanceof EntityPlayer)) {
      EntityLivingBase localEntityLivingBase = e;
    }
    try
    {
      if (deathTime != 0) {
        return;
      }
      thePlayersendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
      Minecraft.thePlayer.swingItem();
      Minecraft.thePlayer.swingItem();
      Minecraft.thePlayer.swingItem();
      lasthit.setLastMS();
      if (Minecraft.thePlayer.isBlocking()) {
        for (int i = 0; i <= 2; i++) {
          thePlayersendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 
            255, thePlayerinventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
        }
      }
      targetSwitchTime.setLastMS();
      lastAttackedTarget = ((EntityLivingBase)target);
      target = null;
      Minecraft.thePlayer.setSprinting(wassprint);
      nexttarget = null;
    }
    catch (NullPointerException localNullPointerException1) {}
  }
  
  private void attackEntityList()
  {
    Random rand = new Random();
    try
    {
      for (Entity e : entityList) {
        if (time.isDelayComplete(rand.nextInt(aps - 53 + 1) + (aps - 20))) {
          if (isViableToAttack((EntityLivingBase)e))
          {
            rot = getAngles(e);
            Minecraft.thePlayer.setSprinting(false);
            target = e;
            if (Minecraft.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                thePlayersendQueue.addToSendQueue(
                  new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, 
                  new BlockPos(0, 0, 0), EnumFacing.DOWN));
              }
            }
            thePlayersendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            
            Minecraft.thePlayer.swingItem();
            Minecraft.thePlayer.swingItem();
            Minecraft.thePlayer.swingItem();
            lasthit.setLastMS();
            
            entityList.remove(e);
            time.setLastMS();
            if (Minecraft.thePlayer.isBlocking()) {
              for (int i = 0; i <= 2; i++) {
                thePlayersendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, 
                  thePlayerinventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
              }
            }
            targetSwitchTime.setLastMS();
            lastAttackedTarget = ((EntityLivingBase)target);
            target = null;
            Minecraft.thePlayer.setSprinting(wassprint);
          }
          else
          {
            entityList.remove(e);
          }
        }
      }
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  private boolean isAttackable(Entity e)
  {
    if (e == null) {
      return false;
    }
    if (((e instanceof EntityPlayer)) && (players))
    {
      EntityPlayer p = (EntityPlayer)e;
      if (!isDead) { Values.getValues(); if (ticksExisted > Values.ticks) if (p.isInvisible()) { Values.getValues(); if (!Values.invisibles) {}
          } else if ((Minecraft.thePlayer.getDistanceToEntity(p) <= (Minecraft.thePlayer.canEntityBeSeen(p) ? distance : 2.8D)) && (p != Minecraft.thePlayer)) {
            return true;
          }
      }
    }
    else if ((e instanceof EntityMob))
    {
      EntityMob m = (EntityMob)e;
      if (!isDead) { Values.getValues(); if (ticksExisted > Values.ticks) {
          if (m.isInvisible()) { Values.getValues(); if (!Values.invisibles) {}
          } else if (Minecraft.thePlayer.getDistanceToEntity(m) <= (Minecraft.thePlayer.canEntityBeSeen(m) ? distance : 2.8D)) {
            return true;
          }
        }
      }
    } else if ((e instanceof EntityAnimal))
    {
      EntityAnimal a = (EntityAnimal)e;
      if (!isDead) { Values.getValues(); if (ticksExisted > Values.ticks)
          if (a.isInvisible()) { Values.getValues(); if (!Values.invisibles) {}
          } else if (Minecraft.thePlayer.getDistanceToEntity(a) <= (Minecraft.thePlayer.canEntityBeSeen(a) ? distance : 2.8D)) {
            return true;
          }
      }
    }
    return false;
  }
  
  public float[] getAngles(Entity entity)
  {
    float xDiff = (float)(posX - thePlayerposX);
    float yDiff = (float)(boundingBox.minY + entity.getEyeHeight() - thePlayerboundingBox.maxY);
    float zDiff = (float)(posZ - thePlayerposZ);
    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
    float pitch = (float)-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
    return new float[] { yaw, pitch };
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
  
  public boolean isViableToAttack(EntityLivingBase p)
  {
    double range = Minecraft.thePlayer.isSprinting() ? distance - 0.20000000298023224D : distance;
    
    float playerYaw = lastYaw;
    float desiredYaw = getAngles(p)[0];
    float distYaw = (playerYaw - desiredYaw + 360.0F) % 360.0F;
    if (distYaw > 180.0F) {
      distYaw = 360.0F - distYaw;
    }
    float playerPitch = lastPitch;
    float desiredPitch = getAngles(p)[1];
    float distPitch = (playerPitch - desiredPitch + 360.0F) % 360.0F;
    if (distPitch > 180.0F) {
      distPitch = 360.0F - distPitch;
    }
    float distCombined = distYaw + distPitch;
    double maxAngle = 60.0D;
    if ((lastYaw != thePlayerrotationYaw) || (lastPitch != thePlayerrotationPitch)) {
      maxAngle = 45.0D;
    }
    boolean angleCheck = distCombined < maxAngle;
    
    angleCheck = angleSwitchTime.isDelayComplete(entityList.size() <= 3 ? 10 : 60);
    if (angleCheck) {
      angleSwitchTime.setLastMS();
    }
    return (isAttackable(p)) && (Minecraft.thePlayer.getDistanceSqToEntity(p) < range * range) && (
      (p != lastAttackedTarget) || (entityList.size() <= 1) || (
      (targetSwitchTime.isDelayComplete(200L)) && 
      ((hurtTime <= 2) || (entityList.size() <= 1)) && (
      (angleCheck) || (entityList.size() <= 1))));
  }
  
  boolean isNameValid(EntityPlayer ep)
  {
    Collection<NetworkPlayerInfo> playerinfos = thePlayersendQueue.getPlayerInfoMap();
    for (NetworkPlayerInfo info : playerinfos) {
      if (info.getGameProfile().getName().matches(ep.getCommandSenderName())) {
        return true;
      }
    }
    return false;
  }
}
