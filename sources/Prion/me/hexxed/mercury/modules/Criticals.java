package me.hexxed.mercury.modules;

import java.util.Collection;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.modulebase.Values.SwiftMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class Criticals extends Module
{
  private double fallDist;
  public static boolean active;
  
  public Criticals()
  {
    super("Criticals", 0, true, ModuleCategory.COMBAT);
  }
  


  public boolean isSafe()
  {
    if ((!mc.thePlayer.isInWater()) && 
      (!mc.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.lava)) && 
      (!mc.thePlayer.isOnLadder()))
    {
      if ((!mc.thePlayer.getActivePotionEffects().contains(net.minecraft.potion.Potion.blindness)) && (mc.thePlayer.ridingEntity == null))
        return false; } return true;
  }
  





  public void onEnable()
  {
    if (!mc.thePlayer.onGround) return;
    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.01D, mc.thePlayer.posZ, false));
    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
  }
  

  private boolean wasOnGround = true;
  


  public void onPreMotionUpdate()
  {
    if (mc.thePlayer.onGround) wasOnGround = true;
    if (shouldUseOldCrits()) {
      setModuleDisplayName(!active ? "§8Criticals" : "Criticals");
    } else {
      setModuleDisplayName("Criticals");
    }
  }
  



  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if (ModuleManager.getModByName("ACC").isEnabled()) {
        y += 0.6D;
        field_149474_g = false;
        active = true;
        return;
      }
      if (!shouldUseOldCrits()) {
        if ((wasOnGround) && (!mc.thePlayer.onGround)) {
          field_149474_g = true;
          wasOnGround = false;
        } else {
          field_149474_g = false;
        }
        


        if (isSafe()) {
          field_149474_g = true;
        }
      } else {
        if ((ModuleManager.getModByName("Fly").isEnabled()) && (!mc.thePlayer.onGround)) {
          active = false;
          return;
        }
        boolean onGr = mc.thePlayer.onGround;
        if (!isSafe()) {
          fallDist += mc.thePlayer.fallDistance;
        }
        if ((fallDist >= 4.0D) || (isSafe())) {
          onGr = true;
          active = false;
          fallDist = 0.0D;
          mc.thePlayer.fallDistance = 0.0F;
        } else if (fallDist > 0.0D) {
          onGr = false;
          active = true;
        } else {
          active = false;
        }
        field_149474_g = onGr;
      }
    }
  }
  
  private boolean shouldUseOldCrits() {
    return (ModuleManager.getModByName("Swift").isEnabled()) && (getValuesswiftmode == Values.SwiftMode.SANIC);
  }
}
