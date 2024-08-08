package com.example.editme.settings;

import com.example.editme.util.converters.StringConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class StringSetting extends Setting {
   private static final StringConverter converter = new StringConverter();

   public StringConverter converter() {
      return converter;
   }

   public Converter converter() {
      return this.converter();
   }

   public StringSetting(String var1, Predicate var2, BiConsumer var3, String var4, Predicate var5) {
      super(var1, var2, var3, var4, var5);
   }
}
