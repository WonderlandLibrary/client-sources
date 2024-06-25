package cc.slack.features.modules.api.settings.impl;

import cc.slack.features.modules.api.settings.Value;

public class NumberValue<T extends Number> extends Value<T> {
   private final T minimum;
   private final T maximum;
   private final T increment;

   public NumberValue(String name, T defaultValue, T minimum, T maximum, T increment) {
      super(name, defaultValue, (Value.VisibilityCheck)null);
      this.minimum = minimum;
      this.maximum = maximum;
      this.increment = increment;
   }

   public T getMinimum() {
      return this.minimum;
   }

   public T getMaximum() {
      return this.maximum;
   }

   public T getIncrement() {
      return this.increment;
   }
}
