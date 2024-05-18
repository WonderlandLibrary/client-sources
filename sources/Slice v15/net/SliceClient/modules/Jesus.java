package net.SliceClient.modules;

import net.SliceClient.Utils.TimerUtil;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class Jesus extends Module
{
  public Jesus()
  {
    super("Jesus", Category.MOVEMENT, 16376546);
  }
  
  public void onEnable()
  {
    Minecraft.getMinecraft();
  }
  

  public void onDisable()
  {
    Minecraft.getMinecraft();
  }
  

  public void onUpdate()
  {
    if (getState())
    {
      if (isInLiquid()) {
        Minecraft mc = mc;
        if (Minecraft.thePlayer.isInsideOfMaterial(net.minecraft.block.material.Material.air)) {
          Minecraft mc2 = mc;
          if (!Minecraft.thePlayer.isSneaking()) {
            try {
              TimerUtil.updateMS();
              if (TimerUtil.hasTimePassedS(0L)) {
                Minecraft mc3 = mc;
                thePlayermotionY = 1.1111112E7D;
                TimerUtil.updateLastMS();
              }
            }
            catch (Exception e) {
              e.printStackTrace();
            }
            thePlayeronGround = true;
            Minecraft mc4 = mc;
            thePlayermotionY = 0.03799999877810478D;
            Minecraft mc5 = mc;
            thePlayerfallDistance = 0.42F;
            Timer timer = mctimer;
            Timer.timerSpeed = 10.0F;
            Timer timer2 = mctimer;
            Timer.timerSpeed = 1.0F;
          }
        }
      }
    }
  }
  
  static boolean isInLiquid() {
    boolean inLiquid = false;
    int y = (int)thePlayerboundingBox.minY;
    for (int x = MathHelper.floor_double(thePlayerboundingBox.minX); x < MathHelper.floor_double(thePlayerboundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(thePlayerboundingBox.minZ); z < MathHelper.floor_double(thePlayerboundingBox.maxZ) + 1; z++) {
        net.minecraft.block.Block block = Minecraft.theWorld.getBlockState(new net.minecraft.util.BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof net.minecraft.block.BlockAir))) {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          inLiquid = true;
          thePlayeronGround = true;
          thePlayerisInWeb = false;
          thePlayerisInWater = false;
        }
      }
    }
    return inLiquid;
  }
}
