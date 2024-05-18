package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Vclip extends Module
{
  public Vclip()
  {
    super("Vertical Phase", 38, true, ModuleCategory.EXPLOITS);
  }
  
  public void onPostMotionUpdate()
  {
    if ((!isStandingStill()) && (mc.thePlayer.isCollidedVertically)) {
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, true));
    }
  }
  
  public void onBoundingBox(Block block, BlockPos pos)
  {
    if (getMinecraftthePlayer == null)
      return;
    if (((!getMinecraftthePlayer.isCollidedHorizontally) || (me.hexxed.mercury.util.Util.isInsideBlock())) && 
      (pos.getY() > getMinecraftthePlayer.boundingBox.minY - 0.4D)) {
      getValuesboundingBox = null;
    }
  }
  
  private boolean isInsideBlock()
  {
    for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < 
          MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
      for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < 
            MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; y++)
        for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < 
              MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
          Block block = getMinecrafttheWorld
            .getBlockState(new BlockPos(x, y, z)).getBlock();
          if ((block != null) && (!(block instanceof net.minecraft.block.BlockAir)))
          {


            AxisAlignedBB boundingBox = block
              .getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
            if ((boundingBox != null) && 
              (mc.thePlayer.boundingBox.intersectsWith(boundingBox)))
              return true;
          }
        }
    }
    return false;
  }
  
  private boolean isStandingStill() {
    return (Math.abs(mc.thePlayer.motionX) <= 0.01D) && (Math.abs(mc.thePlayer.motionZ) <= 0.01D);
  }
}
