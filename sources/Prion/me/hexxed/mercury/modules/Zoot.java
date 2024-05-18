package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Zoot extends Module
{
  public Zoot()
  {
    super("Zoot", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.PLAYER);
  }
  
  private final Potion[] potions = { Potion.poison, 
    Potion.blindness, Potion.moveSlowdown, Potion.hunger };
  
  public void onPreMotionUpdate()
  {
    for (Potion potion : potions) {
      if (mc.thePlayer.getActivePotionEffect(potion) != null)
      {
        PotionEffect effect = mc.thePlayer
          .getActivePotionEffect(potion);
        for (int index = 0; index < effect.getDuration() / 20; index++) {
          mc.getNetHandler().addToSendQueue(
            new C03PacketPlayer(mc.thePlayer.onGround));
        }
      }
    }
    if ((mc.thePlayer.isBurning()) && 
      (!mc.thePlayer.isPotionActive(Potion.fireResistance)) && 
      (!mc.thePlayer.isImmuneToFire()) && (mc.thePlayer.onGround) && 
      (!mc.thePlayer.isInWater()) && 
      (!mc.thePlayer.isInsideOfMaterial(Material.lava)) && 
      (!mc.thePlayer.isInsideOfMaterial(Material.fire))) {
      for (int index = 0; index < 20; index++) {
        mc.getNetHandler().addToSendQueue(
          new C03PacketPlayer(mc.thePlayer.onGround));
      }
    }
  }
  
  public void onPacketSend(Packet p)
  {
    if (((p instanceof C03PacketPlayer)) && (isStandingStill()) && (!mc.thePlayer.isUsingItem())) {
      if (getValuesztpotion) {
        setOutboundPacketCancelled(true);
        return;
      }
      if ((getValuesztbreath) && (mc.thePlayer.isCollidedVertically) && (((mc.thePlayer.isInsideOfMaterial(Material.lava)) && (!mc.thePlayer.isBurning())) || (mc.thePlayer.isInsideOfMaterial(Material.water)))) {
        setOutboundPacketCancelled(true);
      }
      if ((getValuesztfirionfreeze) && (mc.thePlayer.isBurning()) && (!Jesus.isInLiquid())) {
        setOutboundPacketCancelled(true);
      }
    }
  }
  
  private boolean isStandingStill() {
    return (Math.abs(mc.thePlayer.motionX) <= 0.01D) && (Math.abs(mc.thePlayer.motionZ) <= 0.01D) && (Math.abs(mc.thePlayer.motionY) <= 0.1D) && (Math.abs(mc.thePlayer.motionY) >= -0.1D);
  }
}
