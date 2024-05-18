package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

public class Warp extends Module
{
  public Warp()
  {
    super("Warp", 0, true, ModuleCategory.MISC);
  }
  
  private boolean warping = false;
  
  private TimeHelper time = new TimeHelper();
  private TimeHelper cooldown = new TimeHelper();
  private int timerDelay;
  private int packetDelay;
  private int groundTimer;
  
  public void onPreMotionUpdate() {
    mc.timer.timerSpeed = 1.0F;
    timerDelay += 1;
    if (mc.thePlayer.onGround)
      groundTimer += 1;
    mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.01D, 0.0D);
  }
  
  public void onPostMotionUpdate() {
    boolean jesusCheck = !(getBlock(2) instanceof BlockLiquid);
    if ((Keyboard.isKeyDown(29)) && (!warping) && (cooldown.isDelayComplete(1000L))) {
      cooldown.setLastMS();
      if ((ModuleManager.getModByName("Phase").isEnabled()) || (ModuleManager.getModByName("Fly").isEnabled())) {
        Util.sendInfo("Some enabled modules don't work with Warp. Aborting!");
        return;
      }
      

      warping = true;
      time.setLastMS();
    }
    if (!warping) {
      return;
    }
    if (time.isDelayComplete(100L)) {
      time.setLastMS();
      warping = false;
      return;
    }
    if ((timerDelay >= 3) && (!isStandingStill()) && (jesusCheck)) {
      mc.timer.timerSpeed = 30.0F;
      timerDelay = 0;
    } else {
      mc.timer.timerSpeed = 1.0F;
    }
    mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.01D, 0.0D);
  }
  
  public void onPacketSend(Packet packet)
  {
    if (!warping) {
      return;
    }
    boolean jesusCheck = !(getBlock(2) instanceof BlockLiquid);
    packetDelay += 1;
    if (((packet instanceof net.minecraft.network.play.client.C03PacketPlayer)) && (packetDelay >= 3)) {
      if ((groundTimer >= 2) && (jesusCheck))
        setOutboundPacketCancelled(true);
      packetDelay = 0;
    }
  }
  

  public void onDisable()
  {
    mc.timer.timerSpeed = 1.0F;
  }
  
  private Block getBlock(int offset) {
    int x = (int)Math.round(mc.thePlayer.posX - 0.5D);
    int y = (int)Math.round(mc.thePlayer.posY - 0.5D);
    int z = (int)Math.round(mc.thePlayer.posZ - 0.5D);
    
    Block block = Util.getBlock(x, y - offset, z);
    return block;
  }
  
  private boolean isStandingStill() {
    return (Math.abs(mc.thePlayer.motionX) <= 0.01D) && (Math.abs(mc.thePlayer.motionZ) <= 0.01D);
  }
}
