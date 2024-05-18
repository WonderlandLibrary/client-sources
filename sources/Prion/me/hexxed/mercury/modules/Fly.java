package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.modulebase.Values.FlyMode;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import org.lwjgl.input.Keyboard;

public class Fly extends me.hexxed.mercury.modulebase.Module
{
  private double savedposx;
  private double savedposy;
  private double savedposz;
  private boolean wasflying;
  
  public Fly()
  {
    super("Fly", 19, false, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  





  Integer in = Integer.valueOf(0);
  
  private TimeHelper time = new TimeHelper();
  
  private TimeHelper starter = new TimeHelper();
  
  private TimeHelper dmgstarter = new TimeHelper();
  
  private boolean bypass = false;
  private float speed = 1.0F;
  
  public int delay;
  
  public int ticks = 0;
  
  private double lasty;
  
  public static int uptime = 0;
  
  private double maxy;
  
  private boolean airdamaged = false;
  

  TimeHelper asd = new TimeHelper();
  
  public void onEnable()
  {
    airdamaged = false;
    maxy = (mc.thePlayer.posY - 0.1D);
    starter.setLastMS();
    dmgstarter.setLastMS();
    if ((getValuesflymode == Values.FlyMode.NCP) && (mc.thePlayer.onGround) && (ModuleManager.isAntiCheatOn())) {
      Util.damagePlayer(1);
      mc.thePlayer.motionX *= 0.1D;
      mc.thePlayer.motionZ *= 0.1D;
    }
    if (getValuesflymode == Values.FlyMode.GLIDE) {
      lasty = 1337.0D;
      uptime = 0;
      if ((mc.thePlayer.onGround) && (ModuleManager.isAntiCheatOn())) {
        Util.damagePlayer(1);
      }
    }
    in = Integer.valueOf(0);
    if (getValuesflymode != Values.FlyMode.NORMAL) {
      return;
    }
    if (!mc.thePlayer.capabilities.allowFlying) {
      mc.thePlayer.capabilities.allowFlying = true;
      wasflying = false;
    } else {
      wasflying = true;
    }
  }
  
  public void onDisable()
  {
    airdamaged = false;
    lasty = 1337.0D;
    uptime = 0;
    maxy = 1337.0D;
    if (getValuesflymode == Values.FlyMode.NCP) {
      mc.thePlayer.motionY = -0.005D;
    }
    if (getValuesflymode != Values.FlyMode.NORMAL) {
      return;
    }
    mc.thePlayer.capabilities.isFlying = false;
    if (!wasflying) {
      mc.thePlayer.capabilities.allowFlying = false;
    }
    getValuesbypasspackets = Integer.valueOf(0); }
  
  Integer i = Integer.valueOf(0);
  private double height;
  private boolean setLoc;
  
  public void onPreUpdate()
  {
    if ((mc.thePlayer.hurtTime > 0) && (!airdamaged) && (dmgstarter.isDelayComplete(1000L))) {
      Util.sendInfo("Damage detected! You can now touch the ground while §7flying.");
      airdamaged = true;
    }
    if (getValuesflymode == Values.FlyMode.GLIDE) {
      if ((mc.thePlayer.motionY <= -0.03126D) && (!onGround()))
      {
        mc.thePlayer.motionY = -0.03126D;
        Block highBlock = Util.getBlock((int)Math.round(mc.thePlayer.posX), (int)Math.round(mc.thePlayer.boundingBox.minY - 0.5D), (int)Math.round(mc.thePlayer.posZ));
        if (!(highBlock instanceof net.minecraft.block.BlockAir))
        {
          setLoc = true;
        }
        else
        {
          setLoc = false;
          height = 0.6000000238418579D;
        }
      } else if ((setLoc) && (onGround()) && (height >= 0.11D)) {
        height = ((float)(height - 0.005D));
      }
      if (time.isDelayComplete(10L)) {
        time.setLastMS();
        if (mc.currentScreen != null) return;
        if (Keyboard.isKeyDown(57)) if ((maxy - 1.0D > mc.thePlayer.posY + (mc.gameSettings.keyBindSneak.pressed ? 1.4D : 0.28D * getValuesFlySpeed)) && (ModuleManager.isAntiCheatOn()))
          {
            if (mc.gameSettings.keyBindSneak.pressed) {
              getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 1.4D, mc.thePlayer.posZ);
              break label631; }
            getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
            break label631; }
        if (Keyboard.isKeyDown(57)) {
          if (!ModuleManager.isAntiCheatOn()) {
            if (mc.gameSettings.keyBindSneak.pressed) {
              getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 1.4D, mc.thePlayer.posZ);
            } else {
              getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
            }
          }
          else if (maxy <= mc.thePlayer.posY + (mc.gameSettings.keyBindSneak.pressed ? 1.4D : 0.28D * getValuesFlySpeed)) {
            return;
          }
        }
        label631:
        if (Keyboard.isKeyDown(29))
        {
          getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
        }
      }
      return;
    }
    if (getValuesflymode == Values.FlyMode.NORMAL) {
      mc.thePlayer.onGround = false;
      mc.thePlayer.capabilities.isFlying = true;
      mc.thePlayer.capabilities.setFlySpeed((float)getValuesFlySpeed * 0.05F);
      
      return;
    }
    
    if (getValuesflymode == Values.FlyMode.TIGHT) {
      float speed = (float)(getValuesFlySpeed / 3.0D);
      
      mc.thePlayer.motionX = 0.0D;
      mc.thePlayer.motionY = 0.0D;
      mc.thePlayer.motionZ = 0.0D;
      
      mc.thePlayer.landMovementFactor = 1.5F;
      mc.thePlayer.jumpMovementFactor = 1.5F;
      
      if (getMinecraftcurrentScreen == null)
      {
        if (GameSettings.isKeyDown(getMinecraftgameSettings.keyBindJump)) {
          getMinecraftthePlayer.motionY = (speed * 5.0F / 6.0F);
        }
        if (GameSettings.isKeyDown(getMinecraftgameSettings.keyBindSneak)) {
          getMinecraftthePlayer.motionY = (-speed * 5.0F / 6.0F);
        }
      }
      getMinecraftthePlayer.jumpMovementFactor *= speed;
      return;
    }
  }
  
  public void onPreEntityMotionUpdate()
  {
    if (getValuesflymode != Values.FlyMode.NCP) {
      return;
    }
    getValuesmotionY *= 1.0E-14D;
    if (mc.currentScreen == null)
    {
      if (time.isDelayComplete(10L)) {
        time.setLastMS();
        if (Keyboard.isKeyDown(29))
        {
          if ((airdamaged) && (!Util.getBlock(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ).getMaterial().blocksMovement())) {
            getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
          }
          else if (!Util.getBlock(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ).getMaterial().blocksMovement()) {
            getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
          }
        }
        
        if ((Keyboard.isKeyDown(57)) && (maxy > mc.thePlayer.posY + 0.14D * getValuesFlySpeed + 0.05D))
        {
          getMinecraftthePlayer.onGround = false;
          getMinecraftthePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 0.28D * getValuesFlySpeed, mc.thePlayer.posZ);
        } else {
          getMinecraftthePlayer.onGround = true;
        }
      }
    }
    
    mc.thePlayer.fallDistance = 0.0F;
    


    mc.thePlayer.isAirBorne = false;
  }
  



  private double oldposx;
  

  private double oldposy;
  

  private double oldposz;
  

  boolean lagging = false;
  
  boolean shouldsend = false;
  
  public void onPostMotionUpdate() {}
  
  public void onPostUpdate() { if (!lagging) {
      oldposx = mc.thePlayer.posX;
      oldposy = mc.thePlayer.posY;
      oldposz = mc.thePlayer.posZ;
      lagging = true;
    }
    else if ((Util.distance(oldposx, oldposy, oldposz, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ).doubleValue() >= (mc.getCurrentServerData().serverIP.contains("mineplex") ? 1.2D : 7.9999D)) && (getValuesshouldbypass)) {
      shouldsend = true; Values 
        tmp154_151 = Values.getValues();154151bypasspackets = Integer.valueOf(154151bypasspackets.intValue() + 1);
      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
      if (mc.getCurrentServerData().serverIP.contains("mineplex")) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.1D, mc.thePlayer.posZ, true));
      }
      shouldsend = false;
      lagging = false;
    }
  }
  

  public void onPacketSend(net.minecraft.network.Packet packet)
  {
    if (((packet instanceof C03PacketPlayer)) && (getValuesshouldbypass) && (!shouldsend)) {
      if (mc.getCurrentServerData().serverIP.contains("mineplex")) {
        if (!(packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook)) {
          setOutboundPacketCancelled(true);
        }
      } else {
        setOutboundPacketCancelled(true);
      }
    }
    if ((getValuesflymode == Values.FlyMode.GLIDE) && 
      ((packet instanceof C03PacketPlayer))) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if ((p.getPositionY() > lasty) && (!mc.thePlayer.onGround)) {
        uptime += 1;
      }
      if (mc.thePlayer.onGround) {
        uptime = 0;
      }
      lasty = p.getPositionY();
      if (starter.isDelayComplete(1000L)) {
        x = mc.thePlayer.posX;
        y = (mc.thePlayer.posY + height);
        field_149474_g = false;
      }
    }
    

    if ((getValuesflymode == Values.FlyMode.NCP) && 
      ((packet instanceof C03PacketPlayer))) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if ((Util.getBlock(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ).getMaterial().blocksMovement()) && (starter.isDelayComplete(1000L)) && (airdamaged)) {
        y = (mc.thePlayer.posY + 1.0D);
        field_149474_g = false;
        mc.thePlayer.onGround = true;
      }
    }
  }
  




  public void onDamage() {}
  




  private boolean onGround()
  {
    Block block = Util.getBlock((int)mc.thePlayer.posX, (int)(mc.thePlayer.boundingBox.minY - 0.01D), (int)mc.thePlayer.posZ);
    return (!(block instanceof net.minecraft.block.BlockAir)) && (block.isCollidable());
  }
}
