package com.example.editme.settings;

import com.example.editme.util.converters.AbstractBoxedNumberConverter;
import com.example.editme.util.converters.BoxedIntegerConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class IntegerSetting extends NumberSetting {
   private static final BoxedIntegerConverter converter = new BoxedIntegerConverter();

   public Converter converter() {
      return this.converter();
   }

   public AbstractBoxedNumberConverter converter() {
      return converter;
   }

   public IntegerSetting(Integer var1, Predicate var2, BiConsumer var3, String var4, Predicate var5, Integer var6, Integer var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }
}
