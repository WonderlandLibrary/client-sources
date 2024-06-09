package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Civbreak extends Module
{
  private BlockPos pos;
  private EnumFacing face;
  
  public Civbreak()
  {
    super("Civbreak", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.EXPLOITS);
  }
  




  public void onClickBlock(BlockPos pos, EnumFacing face)
  {
    this.pos = pos;
    this.face = face;
  }
  
  public void onPreMotionUpdate()
  {
    if ((pos == null) || (face == null)) return;
    mc.getNetHandler().addToSendQueue(
      new net.minecraft.network.play.client.C0APacketAnimation());
    mc.getNetHandler().addToSendQueue(
      new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, face));
    mc.getNetHandler().addToSendQueue(
      new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
  }
}
