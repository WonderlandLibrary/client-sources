package rina.turok.bope.external;

import java.util.Arrays;
import java.util.EventListener;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventPacket;

public class BopeEventHandler implements EventListener {
   public static BopeEventHandler INSTANCE;
   static final float[] tick_rates = new float[20];
   private long last_update_tick;
   private int next_index = 0;
   @EventHandler
   private Listener<BopeEventPacket.ReceivePacket> receive_event_packet = new Listener<>(event -> {
      if (event.get_packet() instanceof SPacketTimeUpdate) {
         INSTANCE.update_time();
      }

   });

   public BopeEventHandler() {
      Bope.ZERO_ALPINE_EVENT_BUS.subscribe((Object)this);
      this.reset_tick();
   }

   public float get_tick_rate() {
      float num_ticks = 0.0F;
      float sum_ticks = 0.0F;
      float[] var3 = tick_rates;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         float ticks = var3[var5];
         if (ticks > 0.0F) {
            sum_ticks += ticks;
            ++num_ticks;
         }
      }

      return MathHelper.clamp(sum_ticks / num_ticks, 0.0F, 20.0F);
   }

   public void reset_tick() {
      this.next_index = 0;
      this.last_update_tick = -1L;
      Arrays.fill(tick_rates, 0.0F);
   }

   public void update_time() {
      if (this.last_update_tick != -1L) {
         float time = (float)(System.currentTimeMillis() - this.last_update_tick) / 1000.0F;
         tick_rates[this.next_index % tick_rates.length] = MathHelper.clamp(20.0F / time, 0.0F, 20.0F);
         ++this.next_index;
      }

      this.last_update_tick = System.currentTimeMillis();
   }
}
