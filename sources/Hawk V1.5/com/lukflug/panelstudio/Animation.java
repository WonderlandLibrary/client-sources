package com.lukflug.panelstudio;

public abstract class Animation {
   protected long lastTime = System.currentTimeMillis();
   protected double lastValue;
   protected double value;

   protected abstract int getSpeed();

   public void initValue(double var1) {
      this.value = var1;
      this.lastValue = var1;
   }

   public void setValue(double var1) {
      this.lastValue = this.getValue();
      this.value = var1;
      this.lastTime = System.currentTimeMillis();
   }

   public double getTarget() {
      return this.value;
   }

   public double getValue() {
      if (this.getSpeed() == 0) {
         return this.value;
      } else {
         double var1 = (double)(System.currentTimeMillis() - this.lastTime) / (double)this.getSpeed();
         if (var1 >= 1.0D) {
            return this.value;
         } else {
            return var1 <= 0.0D ? this.lastValue : this.value * var1 + this.lastValue * (1.0D - var1);
         }
      }
   }
}
