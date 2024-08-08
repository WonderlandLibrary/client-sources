package com.example.editme.settings;

import com.example.editme.util.converters.BooleanConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BooleanSetting extends Setting {
   private static final BooleanConverter converter = new BooleanConverter();

   public BooleanConverter converter() {
      return converter;
   }

   public Converter converter() {
      return this.converter();
   }

   public BooleanSetting(Boolean var1, Predicate var2, BiConsumer var3, String var4, Predicate var5) {
      super(var1, var2, var3, var4, var5);
   }
}
