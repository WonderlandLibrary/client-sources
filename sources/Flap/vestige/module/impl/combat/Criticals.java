package vestige.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketSendEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.movement.Speed;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;

public class Criticals extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Packet", new String[]{"Packet", "Cris", "Precise"});
   private Killaura killauraModule;
   private Speed speedModule;
   static double[] y1 = new double[]{0.104080378093037D, 0.105454222033912D, 0.102888018147468D, 0.099634532004642D};

   public Criticals() {
      super("Criticals", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.mode});
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   public void onClientStarted() {
      this.speedModule = (Speed)Flap.instance.getModuleManager().getModule(Speed.class);
      this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
   }

   private boolean hurtTimeCheck(Entity entity) {
      return entity != null && entity.hurtResistantTime <= 15;
   }

   @Listener
   public void onSend(PacketSendEvent event) {
      if (event.getPacket() instanceof C02PacketUseEntity) {
         C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
         if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && this.hurtTimeCheck(packet.getEntityFromWorld(mc.theWorld))) {
            packet.getEntity();
            String var4 = this.mode.getMode();
            byte var5 = -1;
            switch(var4.hashCode()) {
            case -1911998296:
               if (var4.equals("Packet")) {
                  var5 = 0;
               }
               break;
            case 2108921:
               if (var4.equals("Cris")) {
                  var5 = 1;
               }
               break;
            case 1345903163:
               if (var4.equals("Precise")) {
                  var5 = 2;
               }
            }

            switch(var5) {
            case 0:
               if (mc.thePlayer.onGround) {
                  doCrits();
               }
            case 1:
               doCritsNoGround();
               break;
            case 2:
               doCritsNoGround();
            }
         }
      }

   }

   static void doCritsNoGround() {
      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.001D, mc.thePlayer.posZ);
   }

   static void doCrits() {
      double[] var0 = new double[]{0.06D, 0.0D, 0.03D, 0.0D};
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         double offset = var0[var2];
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
      }

   }
}
