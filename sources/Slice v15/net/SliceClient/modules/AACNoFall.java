package net.SliceClient.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class AACNoFall extends net.SliceClient.module.Module
{
  public AACNoFall()
  {
    super("GommeNoFall", net.SliceClient.module.Category.MOVEMENT, 16376546);
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    if (thePlayerfallDistance > 2.0F)
    {

      Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.41D, thePlayerposZ, true));
      Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.31D, thePlayerposZ, true));
      Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.19D, thePlayerposZ, true));
      Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.15D, thePlayerposZ, true));
      Minecraft.thePlayer.jump();
      thePlayermotionY = -1.0D;
      thePlayerposY += 0.3993000090122223D;
      
      thePlayermotionY -= 0.1D;
    }
  }
}
