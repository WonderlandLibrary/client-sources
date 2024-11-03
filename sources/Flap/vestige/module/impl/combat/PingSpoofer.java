package vestige.module.impl.combat;

import java.util.concurrent.TimeUnit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketSendEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.network.PacketUtil;

public class PingSpoofer extends Module {
   private final IntegerSetting latency = new IntegerSetting("Latency", 100, 0, 2000, 1);
   public final BooleanSetting cancelPacket = new BooleanSetting("Cancel Packet", false);
   public final BooleanSetting sendAllOnDisabled = new BooleanSetting("Send all Disabled", false);

   public PingSpoofer() {
      super("PingSpoofer", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.latency, this.cancelPacket, this.sendAllOnDisabled});
   }

   @Listener
   public void onSendPacket(@NotNull PacketSendEvent event) {
      if (event == null) {
         $$$reportNull$$$0(0);
      }

      if (!event.isCancelled()) {
         Packet<?> packet = event.getPacket();
         if (packet instanceof C0FPacketConfirmTransaction || packet instanceof C00PacketKeepAlive || packet instanceof C13PacketPlayerAbilities || packet instanceof C17PacketCustomPayload) {
            event.setCancelled(true);
            if (!this.cancelPacket.isEnabled()) {
               Flap.getExecutor().schedule(() -> {
                  if (!this.cancelPacket.isEnabled() && this.sendAllOnDisabled.isEnabled()) {
                     PacketUtil.sendPacketNoEvent(packet);
                  }

               }, (long)this.latency.getValue(), TimeUnit.MILLISECONDS);
            }
         }

      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "vestige/module/impl/combat/PingSpoofer", "onSendPacket"));
   }
}
