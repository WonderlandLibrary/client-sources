package me.hexxed.mercury.modules;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;




public class Freecam
  extends Module
{
  private EntityOtherPlayerMP entity = null;
  private double freecamX;
  private double freecamY;
  private double freecamZ;
  private float freecamPitch;
  private float freecamYaw;
  
  public Freecam() {
    super("Freecam", 46, true, ModuleCategory.PLAYER);
  }
  
  public void onEnable()
  {
    try
    {
      freecamX = mc.thePlayer.posX;
      freecamY = mc.thePlayer.posY;
      freecamZ = mc.thePlayer.posZ;
      freecamPitch = mc.thePlayer.rotationPitch;
      freecamYaw = mc.thePlayer.rotationYaw;
      entity = new EntityOtherPlayerMP(getMinecraftthePlayer.getEntityWorld(), new GameProfile(UUID.randomUUID(), mc.thePlayer.getName()));
      entity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
      entity.inventory = mc.thePlayer.inventory;
      entity.entityUniqueID = mc.thePlayer.getUniqueID();
      
      mc.theWorld.addEntityToWorld(64199, entity);
    }
    catch (Exception localException) {}
  }
  
  public void onDisable()
  {
    try
    {
      mc.theWorld.removeEntityFromWorld(64199);
      mc.thePlayer.setPositionAndRotation(freecamX, freecamY, freecamZ, freecamYaw, freecamPitch);
      mc.thePlayer.noClip = false;
    }
    catch (Exception localException) {}
  }
  
  public void onPreUpdate()
  {
    mc.thePlayer.noClip = true;
  }
  
  public void onPreMotionUpdate()
  {
    if (entity != null) {
      float speed = (float)(getValuesFlySpeed / 2.0D);
      
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
      mc.thePlayer.onGround = false;
      mc.thePlayer.setSprinting(false);
    }
  }
  

  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      






      setOutboundPacketCancelled(true);
    }
  }
}
