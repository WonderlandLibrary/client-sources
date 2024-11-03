package vestige.setting.impl;

import java.util.function.Supplier;
import vestige.setting.AbstractSetting;

public class IntegerSetting extends AbstractSetting {
   private int value;
   private int min;
   private int max;
   private int increment;

   public IntegerSetting(String name, int value, int min, int max, int increment) {
      super(name);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
   }

   public IntegerSetting(String name, Supplier<Boolean> visibility, int value, int min, int max, int increment) {
      super(name, visibility);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
   }

   public void setValue(int value) {
      double doubleMin = (double)this.min;
      double doubleMax = (double)this.max;
      double doubleIncrement = (double)this.increment;
      double doubleValue = (double)value;
      double precision = 1.0D / doubleIncrement;
      this.value = (int)Math.max(doubleMin, Math.min(doubleMax, (double)Math.round(doubleValue * precision) / precision));
   }

   public int getValue() {
      return this.value;
   }

   public int getMin() {
      return this.min;
   }

   public int getMax() {
      return this.max;
   }

   public int getIncrement() {
      return this.increment;
   }
}
