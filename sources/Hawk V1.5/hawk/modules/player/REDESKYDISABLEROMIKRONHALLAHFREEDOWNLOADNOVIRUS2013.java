package hawk.modules.player;

import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventSendPacket;
import hawk.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class REDESKYDISABLEROMIKRONHALLAHFREEDOWNLOADNOVIRUS2013 extends Module {
   boolean started = false;

   public REDESKYDISABLEROMIKRONHALLAHFREEDOWNLOADNOVIRUS2013() {
      super("Redesky disabler 100% no fake", 0, Module.Category.PLAYER);
   }

   public void onMotion(EventMotion var1) {
      if (var1.isPre()) {
         var1.setPitch(90.0F);
         this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.thePlayer);
      }

   }

   public void onDisable() {
      System.err.println(String.valueOf((new StringBuilder("Player velocity")).append(this.mc.thePlayer.velocityChanged)));
   }

   public void onSendPacket(EventSendPacket var1) {
      Packet var2 = var1.getPacket();
      if (var2 instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
         var1.setCancelled(true);
      }

   }

   public void onEnable() {
      this.started = false;
      Thread var1 = new Thread(this) {
         final REDESKYDISABLEROMIKRONHALLAHFREEDOWNLOADNOVIRUS2013 this$0;

         {
            this.this$0 = var1;
         }

         public void run() {
            try {
               Thread.sleep(10000L);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }

            this.this$0.started = true;
         }
      };
      var1.start();
   }
}
