package com.example.editme.settings;

import com.example.editme.util.converters.EnumConverter;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class EnumSetting extends Setting {
   private EnumConverter converter;
   public final Class clazz;

   public Converter converter() {
      return this.converter;
   }

   public EnumSetting(Enum var1, Predicate var2, BiConsumer var3, String var4, Predicate var5, Class var6) {
      super(var1, var2, var3, var4, var5);
      this.converter = new EnumConverter(var6);
      this.clazz = var6;
   }
}
