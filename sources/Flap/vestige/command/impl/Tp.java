package vestige.command.impl;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import vestige.command.Command;
import vestige.util.misc.LogUtil;

public class Tp extends Command {
   public Tp() {
      super("Tp", "Teleport to specific target");
   }

   public void onCommand(String[] args) {
      if (args.length >= 2) {
         String nick = args[1];
         BlockPos targetPos = getPlayerPositionByNick(nick);
         if (targetPos != null) {
            this.sendTeleportPackets((double)targetPos.getX(), (double)targetPos.getY(), (double)targetPos.getZ());
            LogUtil.addChatMessage("&9&l[FLAP]&r &aSuccessfully teleported to " + nick);
         } else {
            LogUtil.addChatMessage("&9&l[FLAP]&r &cPlayer " + nick + " not found!");
         }
      } else {
         LogUtil.addChatMessage("&9&l[FLAP]&r &cUsage: &a.tp [NICK]");
      }

   }

   private void sendTeleportPackets(double x, double y, double z) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
      mc.thePlayer.setPosition(x, y, z);
   }

   public static BlockPos getPlayerPositionByNick(String playerName) {
      Iterator var1 = Minecraft.getMinecraft().theWorld.playerEntities.iterator();

      EntityPlayer player;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         player = (EntityPlayer)var1.next();
      } while(!player.getGameProfile().getName().equalsIgnoreCase(playerName));

      return player.getPosition();
   }
}
