package me.hexxed.mercury.modules;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class Revive extends Module
{
  public Revive()
  {
    super("Revive", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.COMBAT);
  }
  
  public void onPreMotionUpdate()
  {
    if (mc.thePlayer.getHealth() < 10.0F) {
      EntityPlayer player = getMinecraftthePlayer;
      double posX = getMinecraftthePlayer.posX;
      double posY = getMinecraftthePlayer.posY;
      double posZ = getMinecraftthePlayer.posZ;
      
      int damage = 20;
      Float height = Float.valueOf((Float.valueOf(20.0F).floatValue() + 2.0F) * 2.0F);
      
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 1.0D, posZ, false));
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY - 2.0D - damage, posZ, true));
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + 0.4D, posZ, false));
    }
  }
}
