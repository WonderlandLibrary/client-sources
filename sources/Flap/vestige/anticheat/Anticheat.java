package vestige.anticheat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import vestige.Flap;
import vestige.anticheat.impl.BotCheck;
import vestige.anticheat.impl.FlyCheck;
import vestige.anticheat.impl.SpeedCheck;
import vestige.event.Listener;
import vestige.event.impl.EntityMoveEvent;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.util.IMinecraft;
import vestige.util.network.ServerUtil;

public class Anticheat implements IMinecraft {
   public final ArrayList<ACPlayer> players = new ArrayList();
   private boolean shouldClear;
   private boolean isInLobby;

   public Anticheat() {
      Flap.instance.getEventManager().register(this);
   }

   @Listener
   public void onEntityMove(EntityMoveEvent event) {
      Entity entity = event.getEntity();
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         boolean found = false;
         Iterator var5 = this.players.iterator();

         while(var5.hasNext()) {
            ACPlayer acPlayer = (ACPlayer)var5.next();
            if (acPlayer.getEntity().equals(player)) {
               found = true;
               acPlayer.getData().updateInfos();
               if (!this.isInLobby) {
                  acPlayer.getChecks().forEach((c) -> {
                     c.check();
                  });
               }
            }
         }

         if (!found && !player.getGameProfile().getName().contains("-")) {
            ACPlayer acPlayer = new ACPlayer(player);
            acPlayer.getChecks().addAll(Arrays.asList(this.getChecks(acPlayer)));
            this.players.add(acPlayer);
         }
      }

   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S3DPacketDisplayScoreboard) {
         S3DPacketDisplayScoreboard packet = (S3DPacketDisplayScoreboard)event.getPacket();
         String scoreboardName = packet.func_149370_d();
         if (ServerUtil.isOnHypixel() && !scoreboardName.contains("PreScoreboard") && !scoreboardName.contains("SForeboard") && !scoreboardName.contains("health") && !scoreboardName.contains("health_tab")) {
            this.isInLobby = true;
         }
      }

   }

   @Listener
   public void onSend(PacketSendEvent event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted >= 5) {
         this.shouldClear = true;
      } else if (this.shouldClear) {
         this.players.clear();
         this.shouldClear = false;
         this.isInLobby = false;
      }

   }

   public ACPlayer getACPlayer(EntityPlayer entity) {
      Iterator var2 = this.players.iterator();

      ACPlayer player;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         player = (ACPlayer)var2.next();
      } while(player.getEntity() != entity);

      return player;
   }

   private AnticheatCheck[] getChecks(ACPlayer player) {
      AnticheatCheck[] checks = new AnticheatCheck[]{new FlyCheck(player), new SpeedCheck(player), new BotCheck(player)};
      return checks;
   }
}
