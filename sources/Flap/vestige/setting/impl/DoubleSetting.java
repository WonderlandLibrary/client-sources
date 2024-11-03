package vestige.setting.impl;

import java.util.function.Supplier;
import vestige.setting.AbstractSetting;

public class DoubleSetting extends AbstractSetting {
   private double value;
   private double min;
   private double max;
   private double increment;

   public DoubleSetting(String name, double value, double min, double max, double increment) {
      super(name);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
   }

   public DoubleSetting(String name, Supplier<Boolean> visibility, double value, double min, double max, double increment) {
      super(name, visibility);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
   }

   public void setValue(double value) {
      double precision = 1.0D / this.increment;
      this.value = Math.max(this.min, Math.min(this.max, (double)Math.round(value * precision) / precision));
   }

   public String getStringValue() {
      return this.value % 1.0D == 0.0D ? "" + (int)this.value : "" + this.value;
   }

   public double getValue() {
      return this.value;
   }

   public double getMin() {
      return this.min;
   }

   public double getMax() {
      return this.max;
   }

   public double getIncrement() {
      return this.increment;
   }
}
