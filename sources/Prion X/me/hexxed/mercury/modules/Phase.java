package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;

public class Phase extends Module
{
  public Phase()
  {
    super("Phase", 44, true, me.hexxed.mercury.modulebase.ModuleCategory.EXPLOITS);
  }
  
  private boolean phasing = false;
  


  private double state;
  



  public void onEnable()
  {
    if (mc.thePlayer.isSneaking())
    {
      double x = 0.0D;
      double z = 0.0D;
      switch (mc.thePlayer.func_174811_aO()) {
      case DOWN: 
        break;
      case WEST: 
        x = 0.1D;
        break;
      case NORTH: 
        z = -0.1D;
        break;
      case SOUTH: 
        z = 0.1D;
        break;
      case EAST: 
        break;
      case UP: 
        x = -0.1D;
      }
      
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, true));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x * 4.0D, mc.thePlayer.posY, mc.thePlayer.posZ + z * 4.0D, true));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, true));
      mc.thePlayer.setPosition(mc.thePlayer.posX + x * 4.0D, mc.thePlayer.posY, mc.thePlayer.posZ + z * 4.0D);
    }
  }
  















  public void onPostMotionUpdate()
  {
    if ((mc.thePlayer.isCollidedHorizontally) && (!me.hexxed.mercury.util.Util.isInsideBlock()) && (!mc.thePlayer.isSneaking()))
    {
      double x = 0.0D;
      double z = 0.0D;
      switch (mc.thePlayer.func_174811_aO()) {
      case DOWN: 
        break;
      case WEST: 
        x = 0.1D;
        break;
      case NORTH: 
        z = -0.1D;
        break;
      case SOUTH: 
        z = 0.1D;
        break;
      case EAST: 
        break;
      case UP: 
        x = -0.1D;
      }
      
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, true));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x * 4.0D, mc.thePlayer.posY, mc.thePlayer.posZ + z * 4.0D, true));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05D, mc.thePlayer.posZ, true));
      mc.thePlayer.setPosition(mc.thePlayer.posX + x * 4.0D, mc.thePlayer.posY, mc.thePlayer.posZ + z * 4.0D);
    }
  }
  

  public void onDisable()
  {
    if (mc.theWorld != null) {
      mc.thePlayer.noClip = false;
    }
  }
  
  public void onBoundingBox(Block block, BlockPos pos)
  {
    if (getMinecraftthePlayer == null)
      return;
    if (pos.getY() > getMinecraftthePlayer.posY + 1.0D) {
      getValuesboundingBox = null;
    }
    if ((getMinecraftthePlayer.isCollidedHorizontally) && 
      (pos.getY() > getMinecraftthePlayer.boundingBox.minY - 0.4D)) {
      getValuesboundingBox = null;
    }
  }
  




  public void onPreEntityMotionUpdate() {}
  



  private boolean isMoving()
  {
    return (mc.thePlayer.motionX != 0.0D) || (mc.thePlayer.motionY != 0.0D) || (mc.thePlayer.motionZ != 0.0D);
  }
}
