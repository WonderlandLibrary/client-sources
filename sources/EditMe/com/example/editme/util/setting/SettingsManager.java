package com.example.editme.util.setting;

import com.example.editme.settings.Setting;
import com.example.editme.util.builders.BooleanSettingBuilder;
import com.example.editme.util.builders.DoubleSettingBuilder;
import com.example.editme.util.builders.EnumSettingBuilder;
import com.example.editme.util.builders.FloatSettingBuilder;
import com.example.editme.util.builders.IntegerSettingBuilder;
import com.example.editme.util.builders.NumericalSettingBuilder;
import com.example.editme.util.builders.SettingBuilder;
import com.example.editme.util.builders.StringSettingBuilder;
import com.google.common.base.Converter;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SettingsManager {
   public static SettingBuilder custom(String var0, Object var1, Converter var2, Predicate var3, BiConsumer var4, boolean var5) {
      return custom(var0, var1, var2, var3, var4, SettingsManager::lambda$custom$0);
   }

   private static void lambda$custom$5(Object var0, Object var1) {
   }

   public static SettingBuilder custom(String var0, Object var1, Converter var2, Predicate var3, boolean var4) {
      return custom(var0, var1, var2, var3, SettingsManager::lambda$custom$1, var4);
   }

   public static SettingBuilder custom(String var0, Object var1, Converter var2, boolean var3) {
      return custom(var0, var1, var2, SettingsManager::lambda$custom$2, SettingsManager::lambda$custom$3, var3);
   }

   public static DoubleSettingBuilder doubleBuilder() {
      return new DoubleSettingBuilder();
   }

   public static Setting e(String var0, Enum var1) {
      return enumBuilder(var1.getClass()).withName(var0).withValue(var1).build();
   }

   public static Setting i(String var0, int var1) {
      return integerBuilder(var0).withValue((Number)var1).build();
   }

   public static Setting s(String var0, String var1) {
      return stringBuilder(var0).withValue(var1).build();
   }

   public static NumericalSettingBuilder integerBuilder(String var0) {
      return (new IntegerSettingBuilder()).withName(var0);
   }

   public static SettingBuilder custom(String var0, Object var1, Converter var2) {
      return custom(var0, var1, var2, SettingsManager::lambda$custom$4, SettingsManager::lambda$custom$5, false);
   }

   private static boolean lambda$custom$0(boolean var0, Object var1) {
      return !var0;
   }

   private static boolean lambda$custom$4(Object var0) {
      return true;
   }

   public static StringSettingBuilder stringBuilder(String var0) {
      return (StringSettingBuilder)(new StringSettingBuilder()).withName(var0);
   }

   public static BooleanSettingBuilder booleanBuilder(String var0) {
      return (new BooleanSettingBuilder()).withName(var0);
   }

   public static SettingBuilder custom(String var0, Object var1, Converter var2, Predicate var3, BiConsumer var4, Predicate var5) {
      return (new SettingBuilder(var2) {
         final Converter val$converter;

         public Setting build() {
            return new Setting(this, this.initialValue, this.predicate(), this.consumer, this.name, this.visibilityPredicate()) {
               final <undefinedtype> this$0;

               {
                  this.this$0 = var1;
               }

               public Converter converter() {
                  return this.this$0.val$converter;
               }
            };
         }

         {
            this.val$converter = var1;
         }
      }).withName(var0).withValue(var1).withConsumer(var4).withVisibility(var5).withRestriction(var3);
   }

   public static NumericalSettingBuilder doubleBuilder(String var0) {
      return (new DoubleSettingBuilder()).withName(var0);
   }

   private static void lambda$custom$1(Object var0, Object var1) {
   }

   public static NumericalSettingBuilder floatBuilder(String var0) {
      return (new FloatSettingBuilder()).withName(var0);
   }

   public static StringSettingBuilder stringBuilder() {
      return new StringSettingBuilder();
   }

   public static Setting d(String var0, double var1) {
      return doubleBuilder(var0).withValue((Number)var1).build();
   }

   public static FloatSettingBuilder floatBuilder() {
      return new FloatSettingBuilder();
   }

   private static boolean lambda$custom$2(Object var0) {
      return true;
   }

   public static Setting f(String var0, float var1) {
      return floatBuilder(var0).withValue((Number)var1).build();
   }

   public static EnumSettingBuilder enumBuilder(Class var0) {
      return new EnumSettingBuilder(var0);
   }

   public static IntegerSettingBuilder integerBuilder() {
      return new IntegerSettingBuilder();
   }

   public static Setting b(String var0, boolean var1) {
      return booleanBuilder(var0).withValue(var1).build();
   }

   private static void lambda$custom$3(Object var0, Object var1) {
   }

   public static BooleanSettingBuilder booleanBuilder() {
      return new BooleanSettingBuilder();
   }

   public static Setting b(String var0) {
      return booleanBuilder(var0).withValue(true).build();
   }
}
