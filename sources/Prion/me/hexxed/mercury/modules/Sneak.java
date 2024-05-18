package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;


public class Sneak
  extends Module
{
  public Sneak()
  {
    super("Sneak", 51, true, ModuleCategory.PLAYER);
  }
  
















































  public void onPreMotionUpdate()
  {
    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
  }
  

  public void onPostMotionUpdate()
  {
    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    if ((ModuleManager.getModByName("Sprint").isEnabled()) && ((mc.thePlayer.motionX != 0.0D) || (mc.thePlayer.motionZ != 0.0D) || (mc.thePlayer.moveStrafing != 0.0F)) && (!ModuleManager.getModByName("Freecam").isEnabled())) {
      mc.thePlayer.setSprinting(true);
    }
  }
  
  public void onDisable()
  {
    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
  }
}
