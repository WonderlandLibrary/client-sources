package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;




public class NoFall
  extends Module
{
  public NoFall()
  {
    super("NoFall", 49, false, ModuleCategory.MOVEMENT);
  }
  
  public void onPreUpdate()
  {
    if ((mc.thePlayer.fallDistance > 2.0F) && 
      (ModuleManager.isAntiCheatOn())) {
      Block block = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock();
      if ((!(block instanceof BlockAir)) && (!(block instanceof BlockLiquid))) {
        mc.thePlayer.setVelocity(0.0D, 0.0D, 0.0D);
        mc.thePlayer.fallDistance = 0.0F;
      }
    }
  }
  

  public void onPacketSend(Packet packet)
  {
    if ((packet instanceof C03PacketPlayer)) {
      C03PacketPlayer p = (C03PacketPlayer)packet;
      if (!field_149474_g) {
        field_149474_g = true;
      }
    }
  }
}
