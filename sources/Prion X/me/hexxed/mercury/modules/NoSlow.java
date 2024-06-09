package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class NoSlow extends Module
{
  public NoSlow()
  {
    super("NoSlow", 0, true, ModuleCategory.COMBAT);
  }
  
  public void onPreMotionUpdate()
  {
    if (mc.thePlayer.isBlocking()) {
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), net.minecraft.util.EnumFacing.DOWN));
    }
  }
  
  public void onPostMotionUpdate()
  {
    if (mc.thePlayer.isBlocking()) {
      mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
    }
  }
  
  public void onPostUpdate()
  {
    if ((ModuleManager.getModByName("Sprint").isEnabled()) && ((mc.thePlayer.motionX != 0.0D) || (mc.thePlayer.motionZ != 0.0D) || (mc.thePlayer.moveStrafing != 0.0F)) && (!ModuleManager.getModByName("Freecam").isEnabled())) {
      mc.thePlayer.setSprinting(true);
    }
  }
}
