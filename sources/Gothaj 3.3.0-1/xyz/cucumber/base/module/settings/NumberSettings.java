package xyz.cucumber.base.module.settings;

import java.util.function.Supplier;

public class NumberSettings extends ModuleSettings {
   public double value;
   public double min;
   public double max;
   public double increment = 0.01;

   public double getMin() {
      return this.min;
   }

   public void setMin(double min) {
      this.min = min;
   }

   public double getMax() {
      return this.max;
   }

   public void setMax(double max) {
      this.max = max;
   }

   public double getIncrement() {
      return this.increment;
   }

   public void setIncrement(double increment) {
      this.increment = increment;
   }

   public double getValue() {
      return this.value;
   }

   public NumberSettings(String name, Supplier<Boolean> visibility, double value, double min, double max, double increment) {
      this.setName(name);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
      this.category = null;
      this.visibility = visibility;
   }

   public NumberSettings(String name, double value, double min, double max, double increment) {
      this.setName(name);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
      this.category = null;
   }

   public NumberSettings(String category, String name, double value, double min, double max, double increment) {
      this.setName(name);
      this.value = value;
      this.min = min;
      this.max = max;
      this.increment = increment;
      this.category = category;
   }

   public void setValue(double value) {
      double precision = 1.0 / this.increment;
      this.value = (double)Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
   }
}
