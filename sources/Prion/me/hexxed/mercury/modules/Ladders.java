package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Ladders extends Module
{
  public Ladders()
  {
    super("Ladders", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.MOVEMENT);
  }
  
  public void onPreMotionUpdate()
  {
    boolean movementInput = (mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindLeft.pressed);
    int posX = MathHelper.floor_double(mc.thePlayer.posX);
    int minY = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);
    int maxY = MathHelper.floor_double(mc.thePlayer.boundingBox.minY + 1.0D);
    int posZ = MathHelper.floor_double(mc.thePlayer.posZ);
    if ((movementInput) && (mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isInWater())) {
      if (!mc.thePlayer.isOnLadder())
      {
        Block block = Util.getBlock(posX, minY, posZ);
        if ((!(block instanceof BlockLadder)) && (!(block instanceof BlockVine)))
        {
          block = Util.getBlock(posX, maxY, posZ);
          if (((block instanceof BlockLadder)) || ((block instanceof BlockVine))) {
            mc.thePlayer.motionY = 0.5D;
          }
        }
      }
      else if (mc.thePlayer.isOnLadder())
      {

        mc.thePlayer.motionY *= 2.44D;
        if ((mc.thePlayer.onGround) && (getAboveLadders() > 0) && (!mc.thePlayer.isSneaking())) {
          mc.thePlayer.setLocationAndAngles(mc.thePlayer.posX, (getAboveLadders() >= 10 ? 9.79D : getAboveLadders() + 0.99D) + mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }
      }
    }
  }
  
  public int getAboveLadders() {
    int ladders = 0;
    for (int dist = 1; dist < 256; dist++)
    {
      BlockPos bpos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + dist, mc.thePlayer.posZ);
      Block block = mc.theWorld.getBlockState(bpos).getBlock();
      if ((!(block instanceof BlockLadder)) && (!(block instanceof BlockVine)))
        break;
      ladders++;
    }
    


    return ladders;
  }
}
