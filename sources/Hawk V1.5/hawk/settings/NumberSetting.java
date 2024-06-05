package hawk.settings;

import hawk.modules.Module;

public class NumberSetting extends Setting implements com.lukflug.panelstudio.settings.NumberSetting {
   public double maximum;
   public Module parent;
   public double value;
   public double increment;
   public double minimum;

   public double getMinimumValue() {
      return this.minimum;
   }

   public double getMaximum() {
      return this.maximum;
   }

   public NumberSetting(String var1, double var2, double var4, double var6, double var8, Module var10) {
      this.name = var1;
      this.value = var2;
      this.minimum = var4;
      this.maximum = var6;
      this.increment = var8;
      this.parent = var10;
   }

   public double getIncrement() {
      return this.increment;
   }

   public void increment(boolean var1) {
      this.setValue(this.getValue() + (double)(var1 ? 1 : -1) * this.increment);
   }

   public void setIncrement(double var1) {
      this.increment = var1;
   }

   public void setValue(double var1) {
      double var3 = 1.0D / this.increment;
      this.value = (double)Math.round(Math.max(this.minimum, Math.min(this.maximum, var1)) * var3) / var3;
   }

   public int getPrecision() {
      return (int)this.increment;
   }

   public void setNumber(double var1) {
      this.value = var1;
   }

   public double getValue() {
      return this.value;
   }

   public void setMinimum(double var1) {
      this.minimum = var1;
   }

   public double getNumber() {
      return this.value;
   }

   public double getMinimum() {
      return this.minimum;
   }

   public void setMaximum(double var1) {
      this.maximum = var1;
   }

   public double getMaximumValue() {
      return this.maximum;
   }
}
