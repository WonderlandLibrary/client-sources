package com.example.editme.settings;

import com.example.editme.util.converters.AbstractBoxedNumberConverter;
import com.example.editme.util.converters.BoxedDoubleConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class DoubleSetting extends NumberSetting {
   private static final BoxedDoubleConverter converter = new BoxedDoubleConverter();

   public AbstractBoxedNumberConverter converter() {
      return converter;
   }

   public DoubleSetting(Double var1, Predicate var2, BiConsumer var3, String var4, Predicate var5, Double var6, Double var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public Converter converter() {
      return this.converter();
   }
}
