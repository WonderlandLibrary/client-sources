package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Glide extends Module
{
  private double oldposx;
  private double oldposy;
  private double oldposz;
  
  public Glide()
  {
    super("Glide", 34, false, ModuleCategory.MOVEMENT);
  }
  
  public void onEnable() {
    if (me.hexxed.mercury.modulebase.ModuleManager.isAntiCheatOn()) {
      me.hexxed.mercury.util.Util.damagePlayer(1);
    }
  }
  
  public void onPreUpdate()
  {
    if ((mc.thePlayer.motionY <= -getValuesglidespeed) && (!mc.thePlayer.isInWater()) && (!mc.thePlayer.onGround) && (!mc.thePlayer.isOnLadder()))
    {
      mc.thePlayer.motionY = (-getValuesglidespeed);
    }
  }
  
  public void onDisable() {
    getValuesbypasspackets = Integer.valueOf(0);
  }
  






  boolean lagging = false;
  
  boolean shouldsend = false;
  









  public void onPostUpdate() {}
  








  public void onPacketSend(Packet packet)
  {
    if (((packet instanceof C03PacketPlayer)) && (getValuesshouldbypass) && (!shouldsend)) {
      setOutboundPacketCancelled(true);
    }
  }
}
