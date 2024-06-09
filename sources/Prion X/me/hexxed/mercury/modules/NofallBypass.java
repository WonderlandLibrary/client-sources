package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.TimeHelper;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class NofallBypass extends Module
{
  private float height;
  private boolean setLoc;
  private boolean airdamaged;
  private TimeHelper starter = new TimeHelper();
  
  public NofallBypass() {
    super("Nofall Bypass", 0, true, ModuleCategory.MOVEMENT);
  }
  
  public void onEnable()
  {
    setModuleDisplayName(getModuleName());
    airdamaged = false;
    tick = 0;
    starter.setLastMS();
    if (mc.thePlayer.onGround) Util.damagePlayer(1);
  }
  
  public void onPreMotionUpdate()
  {
    if ((starter.isDelayComplete(1000L)) && (!airdamaged) && (mc.thePlayer.hurtTime > 0)) {
      airdamaged = true;
      setModuleDisplayName("Movement Bypass");
    }
    if (!onGround())
    {
      tick = 0;
      Block highBlock = Util.getBlock((int)Math.round(mc.thePlayer.posX), (int)Math.round(mc.thePlayer.boundingBox.minY - 0.5D), (int)Math.round(mc.thePlayer.posZ));
      if (!(highBlock instanceof BlockAir))
      {
        setLoc = true;
      }
      else
      {
        setLoc = false;
        height = 0.6F;
      }
    } else if ((setLoc) && (onGround()) && (height >= 0.11D)) {
      height = ((float)(height - 0.005D));
    } }
  
  int tick = 0;
  
  public void onPacketSend(Packet packet)
  {
    if (((packet instanceof C03PacketPlayer)) && (starter.isDelayComplete(1000L))) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      y = (mc.thePlayer.posY + height - tick * 1.0E-13D);
      field_149474_g = false;
      tick += 1;
    }
  }
  
  private boolean onGround()
  {
    Block block = Util.getBlock((int)mc.thePlayer.posX, (int)(mc.thePlayer.boundingBox.minY - 0.01D), (int)mc.thePlayer.posZ);
    return (!(block instanceof BlockAir)) && (block.isCollidable());
  }
}
