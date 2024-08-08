package com.example.editme.settings;

import com.example.editme.util.converters.AbstractBoxedNumberConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class NumberSetting extends Setting {
   private final Number max;
   private final Number min;

   public NumberSetting(Number var1, Predicate var2, BiConsumer var3, String var4, Predicate var5, Number var6, Number var7) {
      super(var1, var2, var3, var4, var5);
      this.min = var6;
      this.max = var7;
   }

   public Number getMin() {
      return this.min;
   }

   public boolean isBound() {
      return this.min != null && this.max != null;
   }

   public Number getValue() {
      return (Number)super.getValue();
   }

   public Converter converter() {
      return this.converter();
   }

   public abstract AbstractBoxedNumberConverter converter();

   public Object getValue() {
      return this.getValue();
   }

   public Number getMax() {
      return this.max;
   }
}
