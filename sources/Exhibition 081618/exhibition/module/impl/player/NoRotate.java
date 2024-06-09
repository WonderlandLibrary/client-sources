package exhibition.module.impl.player;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventTick;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.movement.LongJump;
import exhibition.module.impl.movement.Speed;
import exhibition.util.Timer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {
   private int packetCounter;
   private Timer spamTimer = new Timer();
   private Timer deactivationDelay = new Timer();

   public NoRotate(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class, EventTick.class}
   )
   public void onEvent(Event event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted > 0) {
         if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket)event;
            if (ep.isIncoming() && ep.getPacket() instanceof S08PacketPlayerPosLook && this.deactivationDelay.delay(2000.0F)) {
               ++this.packetCounter;
               S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)ep.getPacket();
               pac.yaw = mc.thePlayer.rotationYaw;
               pac.pitch = mc.thePlayer.rotationPitch;
               Speed.stage = -5;
               if (((Module)Client.getModuleManager().get(LongJump.class)).isEnabled()) {
                  ((Module)Client.getModuleManager().get(LongJump.class)).toggle();
                  Notifications.getManager().post("LagBack check!", "Disabled longjump.", 750L, Notifications.Type.WARNING);
               }
            }
         } else if (this.spamTimer.delay(750.0F) && this.packetCounter > 3 && this.deactivationDelay.delay(2000.0F)) {
            Module[] toggleModules = new Module[]{(Module)Client.getModuleManager().get(LongJump.class), (Module)Client.getModuleManager().get(Speed.class)};
            boolean wasDisabled = false;
            Module[] var4 = toggleModules;
            int var5 = toggleModules.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Module module = var4[var6];
               if (module.isEnabled()) {
                  module.toggle();
                  wasDisabled = true;
               }
            }

            if (wasDisabled) {
               Notifications.getManager().post("LagBack check!", "Disabled movement modules.", 500L, Notifications.Type.WARNING);
            }

            this.deactivationDelay.reset();
            this.spamTimer.reset();
            this.packetCounter = 0;
         }
      }

   }
}
