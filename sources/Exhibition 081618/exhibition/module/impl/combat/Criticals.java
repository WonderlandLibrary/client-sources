package exhibition.module.impl.combat;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.movement.Fly;
import exhibition.module.impl.movement.LongJump;
import exhibition.module.impl.movement.Speed;
import exhibition.util.PlayerUtil;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S2APacketParticles;

public class Criticals extends Module {
   private static String PACKET = "MODE";
   private static String TICKDELAY = "TICKDELAY";
   private int waitTicks;

   public Criticals(ModuleData data) {
      super(data);
      this.settings.put(PACKET, new Setting(PACKET, new Options("Mode", "Packet", new String[]{"Packet", "Jump", "Pussy"}), "Critical attack method."));
      this.settings.put(TICKDELAY, new Setting(TICKDELAY, Integer.valueOf(9), "Amount of ticks to wait before crit attacking again.", 1.0D, 0.0D, 20.0D));
   }

   @RegisterEvent(
      events = {EventPacket.class, EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         ++this.waitTicks;
         this.setSuffix(((Options)((Setting)this.settings.get(PACKET)).getValue()).getSelected());
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event.cast();
         if (ep.getPacket() instanceof S2APacketParticles || ep.getPacket().toString().contains("S2APacketParticles")) {
            return;
         }

         try {
            if (ep.isOutgoing() && ep.getPacket() instanceof C02PacketUseEntity && !(ep.getPacket() instanceof S2APacketParticles) && !(ep.getPacket() instanceof C0APacketAnimation)) {
               C02PacketUseEntity packet = (C02PacketUseEntity)ep.getPacket();
               int ticks = ((Number)((Setting)this.settings.get(TICKDELAY)).getValue()).intValue();
               if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && mc.thePlayer.isCollidedVertically && Killaura.allowCrits && this.waitTicks >= ticks) {
                  if (Client.getModuleManager().isEnabled(LongJump.class) || Client.getModuleManager().isEnabled(Speed.class)) {
                     return;
                  }

                  String var5 = ((Options)((Setting)this.settings.get(PACKET)).getValue()).getSelected();
                  byte var6 = -1;
                  switch(var5.hashCode()) {
                  case -1911998296:
                     if (var5.equals("Packet")) {
                        var6 = 0;
                     }
                     break;
                  case 2320462:
                     if (var5.equals("Jump")) {
                        var6 = 1;
                     }
                     break;
                  case 1573037090:
                     if (var5.equals("PussyJump")) {
                        var6 = 2;
                     }
                  }

                  switch(var6) {
                  case 0:
                     doCrits();
                     break;
                  case 1:
                     if (!mc.thePlayer.isJumping) {
                        mc.thePlayer.jump();
                     }
                     break;
                  case 2:
                     if (mc.thePlayer.onGround) {
                        mc.thePlayer.moveEntity(0.0D, 0.05D, 0.0D);
                     }
                  }

                  this.waitTicks = 0;
               }
            }

            if (ep.getPacket() instanceof C03PacketPlayer && ((Options)((Setting)this.settings.get(PACKET)).getValue()).getSelected().equalsIgnoreCase("Pussy")) {
               ((C03PacketPlayer)ep.getPacket()).setMoving(false);
               ((C03PacketPlayer)ep.getPacket()).onGround = false;
               if (PlayerUtil.isOnLiquid()) {
                  return;
               }

               if (!Client.getModuleManager().isEnabled(Fly.class)) {
                  if (ep.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
                     C03PacketPlayer.C04PacketPlayerPosition playerPos = (C03PacketPlayer.C04PacketPlayerPosition)ep.getPacket();
                     playerPos.onGround = false;
                  } else if (ep.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
                     C03PacketPlayer.C05PacketPlayerLook look = (C03PacketPlayer.C05PacketPlayerLook)ep.getPacket();
                     look.onGround = false;
                  } else if (ep.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                     C03PacketPlayer.C06PacketPlayerPosLook posLook = (C03PacketPlayer.C06PacketPlayerPosLook)ep.getPacket();
                     posLook.onGround = false;
                  }
               }
            }
         } catch (Exception var7) {
            ;
         }
      }

   }

   private static int randomNumber(int max, int min) {
      return (int)(Math.random() * (double)(max - min)) + min;
   }

   static void doCrits() {
      Setting setting = ((Module)Client.getModuleManager().get(Killaura.class)).getSetting("MODE");
      if (!((Options)setting.getValue()).getSelected().equalsIgnoreCase("Switch")) {
         double offset1 = (double)(randomNumber(-9999, 9999) / 10000000);
         double offset2 = (double)(randomNumber(-9999, 9999) / 1000000000);
         double[] var5 = new double[]{0.0624218713251234D + offset1, 0.0D, 1.0834773E-5D + offset2, 0.0D};
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            double offset = var5[var7];
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
         }

      }
   }
}
