package me.hexxed.mercury.modules;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.UUID;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;


public class Blink
  extends Module
{
  public Blink()
  {
    super("Blink", 47, true, ModuleCategory.MOVEMENT);
  }
  
  public static ArrayList<Packet> packetCache = new ArrayList();
  
  private EntityOtherPlayerMP entity;
  public static TimeHelper disabled = new TimeHelper();
  
  public void onEnable()
  {
    try {
      entity = new EntityOtherPlayerMP(getMinecraftthePlayer.getEntityWorld(), new GameProfile(UUID.randomUUID(), mc.thePlayer.getName()));
      entity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
      entity.inventory = mc.thePlayer.inventory;
      entity.entityUniqueID = mc.thePlayer.getUniqueID();
      mc.theWorld.addEntityToWorld(69, entity);
    }
    catch (Exception localException) {}
  }
  
  private double oldposX = 0.0D;
  private double oldposY = 0.0D;
  private double oldposZ = 0.0D;
  
  public void onPacketSend(Packet packet)
  {
    if ((mc.thePlayer.motionY == 0.0D) && (mc.thePlayer.motionZ == 0.0D) && (mc.thePlayer.motionX == 0.0D)) {
      setOutboundPacketCancelled(true);
      return;
    }
    if (((packet instanceof C03PacketPlayer)) || ((packet instanceof C08PacketPlayerBlockPlacement)) || ((packet instanceof C07PacketPlayerDigging)) || ((packet instanceof C0APacketAnimation)) || ((packet instanceof C0BPacketEntityAction)) || ((packet instanceof C02PacketUseEntity))) {
      packetCache.add(packet);
      setOutboundPacketCancelled(true);
    }
  }
  
  public void onDisable()
  {
    for (Packet p : packetCache) {
      mc.thePlayer.sendQueue.addToSendQueue(p);
    }
    disabled.setLastMS();
    packetCache.clear();
    try
    {
      mc.theWorld.removeEntityFromWorld(69);
    }
    catch (Exception localException) {}
    
    oldposX = 0.0D;
    oldposY = 0.0D;
    oldposZ = 0.0D;
  }
}
