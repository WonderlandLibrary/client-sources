package exhibition.management.command.impl;

import exhibition.management.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Damage extends Command {
   static Minecraft mc = Minecraft.getMinecraft();

   public Damage(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      damagePlayer();
   }

   public static void damagePlayer() {
      for(int index = 0; index < 70; ++index) {
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06D, mc.thePlayer.posZ, false));
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
      }

      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
   }

   public String getUsage() {
      return null;
   }
}
