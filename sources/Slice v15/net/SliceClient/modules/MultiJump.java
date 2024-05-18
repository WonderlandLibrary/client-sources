package net.SliceClient.modules;

import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class MultiJump extends Module
{
  public MultiJump()
  {
    super("MultiJump", net.SliceClient.module.Category.MOVEMENT, 16376546);
    
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  


  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    

    Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + 0.41D, thePlayerposZ, true));
    thePlayeronGround = true;
  }
}
