package com.example.editme.util.client;

import com.example.editme.EditmeMod;
import com.example.editme.events.PacketEvent;
import java.util.Arrays;
import java.util.EventListener;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class LagCompensator implements EventListener {
   @EventHandler
   Listener packetEventListener = new Listener(LagCompensator::lambda$new$0, new Predicate[0]);
   private long timeLastTimeUpdate;
   private int nextIndex = 0;
   private final float[] tickRates = new float[20];
   public static LagCompensator INSTANCE;

   public void onTimeUpdate() {
      if (this.timeLastTimeUpdate != -1L) {
         float var1 = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0F;
         this.tickRates[this.nextIndex % this.tickRates.length] = MathHelper.func_76131_a(20.0F / var1, 0.0F, 20.0F);
         ++this.nextIndex;
      }

      this.timeLastTimeUpdate = System.currentTimeMillis();
   }

   public float getTickRate() {
      float var1 = 0.0F;
      float var2 = 0.0F;
      float[] var3 = this.tickRates;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         float var6 = var3[var5];
         if (var6 > 0.0F) {
            var2 += var6;
            ++var1;
         }
      }

      return MathHelper.func_76131_a(var2 / var1, 0.0F, 20.0F);
   }

   private static void lambda$new$0(PacketEvent.Receive var0) {
      if (var0.getPacket() instanceof SPacketTimeUpdate) {
         INSTANCE.onTimeUpdate();
      }

   }

   public LagCompensator() {
      EditmeMod.EVENT_BUS.subscribe((Object)this);
      this.reset();
   }

   public void reset() {
      this.nextIndex = 0;
      this.timeLastTimeUpdate = -1L;
      Arrays.fill(this.tickRates, 0.0F);
   }
}
