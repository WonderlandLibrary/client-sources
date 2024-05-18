package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Instant extends Module
{
  public Instant()
  {
    super("Instant", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.WORLD);
  }
  
  public void onClickBlock(BlockPos pos, EnumFacing face)
  {
    if ((net.minecraft.block.Block.getIdFromBlock(mc.theWorld.getBlockState(pos).getBlock()) == 0) || (net.minecraft.block.Block.getIdFromBlock(mc.theWorld.getBlockState(pos).getBlock()) == 7)) {
      return;
    }
    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, face));
    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
  }
}
