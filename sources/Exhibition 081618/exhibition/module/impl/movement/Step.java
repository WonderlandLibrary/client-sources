package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventStep;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.combat.Killaura;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {
   public Step(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventStep.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventStep) {
         EventStep es = (EventStep)event;
         if (Killaura.canJump) {
            es.setCancelled(true);
            return;
         }

         if (es.isPre() && !mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedVertically) {
            es.setStepHeight(1.0D);
            es.setActive(true);
         } else if (!es.isPre() && es.isActive() && es.getRealHeight() > 0.5D && es.getStepHeight() > 0.0D && !mc.thePlayer.movementInput.jump && mc.thePlayer.isCollidedVertically) {
            Speed.stage = -4;
            if (es.getRealHeight() >= 0.87D) {
               double realHeight = es.getRealHeight();
               double height1 = realHeight * 0.42D;
               double height2 = realHeight * 0.75D;
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + height1, mc.thePlayer.posZ, mc.thePlayer.onGround));
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + height2, mc.thePlayer.posZ, mc.thePlayer.onGround));
            }

            mc.timer.timerSpeed = 0.55F;
            (new Thread(() -> {
               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var1) {
                  ;
               }

               mc.timer.timerSpeed = 1.0F;
            })).start();
         }
      }

   }
}
