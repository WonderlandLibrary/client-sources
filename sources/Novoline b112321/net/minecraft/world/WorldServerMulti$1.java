package net.minecraft.world;

import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;

class WorldServerMulti$1 implements IBorderListener {
   final WorldServerMulti this$0;

   WorldServerMulti$1(WorldServerMulti var1) {
      this.this$0 = var1;
   }

   public void onSizeChanged(WorldBorder var1, double var2) {
      this.this$0.getWorldBorder().setTransition(var2);
   }

   public void onTransitionStarted(WorldBorder var1, double var2, double var4, long var6) {
      this.this$0.getWorldBorder().setTransition(var2, var4, var6);
   }

   public void onCenterChanged(WorldBorder var1, double var2, double var4) {
      this.this$0.getWorldBorder().setCenter(var2, var4);
   }

   public void onWarningTimeChanged(WorldBorder var1, int var2) {
      this.this$0.getWorldBorder().setWarningTime(var2);
   }

   public void onWarningDistanceChanged(WorldBorder var1, int var2) {
      this.this$0.getWorldBorder().setWarningDistance(var2);
   }

   public void onDamageAmountChanged(WorldBorder var1, double var2) {
      this.this$0.getWorldBorder().setDamageAmount(var2);
   }

   public void onDamageBufferChanged(WorldBorder var1, double var2) {
      this.this$0.getWorldBorder().setDamageBuffer(var2);
   }
}
