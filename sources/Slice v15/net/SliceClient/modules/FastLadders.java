package net.SliceClient.modules;

import net.SliceClient.Utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class FastLadders extends net.SliceClient.module.Module
{
  public TimeHelper time;
  
  public FastLadders()
  {
    super("FastLadders", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  


  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    if ((Minecraft.thePlayer.isOnLadder()) && (thePlayerisCollidedHorizontally))
    {
      net.SliceClient.Utils.NetHandlerPlayClientHook.netManager.sendPacket(new net.minecraft.network.play.client.C03PacketPlayer(Minecraft.thePlayer.isSneaking()));
      thePlayermotionY += 0.019D;
      Minecraft.thePlayer.setPosition(thePlayerposX + thePlayermotionX * 0.1D, thePlayerposY + thePlayermotionY * 1.0D, thePlayerposZ);
      if (!time.hasReached(1525L))
      {
        net.SliceClient.Utils.NetHandlerPlayClientHook.netManager.sendPacket(new net.minecraft.network.play.client.C03PacketPlayer(Minecraft.thePlayer.isSneaking()));
        net.minecraft.util.Timer.timerSpeed = 8.1F;
        
        Minecraft.thePlayer.setPosition(thePlayerposX + thePlayermotionX * 0.1D, thePlayerposY + thePlayermotionY * 1.0D, thePlayerposZ);
        
        time.reset();
      }
      else
      {
        net.minecraft.util.Timer.timerSpeed = 1.0F;
      }
    }
  }
}
