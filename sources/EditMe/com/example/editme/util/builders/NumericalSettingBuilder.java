package com.example.editme.util.builders;

import com.example.editme.settings.NumberSetting;
import com.example.editme.settings.Setting;
import java.util.function.BiConsumer;

public abstract class NumericalSettingBuilder extends SettingBuilder {
   protected Number min;
   protected Number max;

   public NumericalSettingBuilder withListener(BiConsumer var1) {
      this.consumer = var1;
      return this;
   }

   public Setting build() {
      return this.build();
   }

   public NumericalSettingBuilder withMinimum(Number var1) {
      this.predicateList.add(NumericalSettingBuilder::lambda$withMinimum$0);
      if (this.min == null || var1.doubleValue() > this.min.doubleValue()) {
         this.min = var1;
      }

      return this;
   }

   private static boolean lambda$withRange$2(Number var0, Number var1, Number var2) {
      double var3 = var2.doubleValue();
      return var3 >= var0.doubleValue() && var3 <= var1.doubleValue();
   }

   public abstract NumberSetting build();

   public NumericalSettingBuilder withMaximum(Number var1) {
      this.predicateList.add(NumericalSettingBuilder::lambda$withMaximum$1);
      if (this.max == null || var1.doubleValue() < this.max.doubleValue()) {
         this.max = var1;
      }

      return this;
   }

   private static boolean lambda$withMaximum$1(Number var0, Number var1) {
      return var1.doubleValue() <= var0.doubleValue();
   }

   public NumericalSettingBuilder withName(String var1) {
      return (NumericalSettingBuilder)super.withName(var1);
   }

   public NumericalSettingBuilder withValue(Number var1) {
      return (NumericalSettingBuilder)super.withValue(var1);
   }

   private static boolean lambda$withMinimum$0(Number var0, Number var1) {
      return var1.doubleValue() >= var0.doubleValue();
   }

   public SettingBuilder withValue(Object var1) {
      return this.withValue((Number)var1);
   }

   public SettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public NumericalSettingBuilder withRange(Number var1, Number var2) {
      this.predicateList.add(NumericalSettingBuilder::lambda$withRange$2);
      if (this.min == null || var1.doubleValue() > this.min.doubleValue()) {
         this.min = var1;
      }

      if (this.max == null || var2.doubleValue() < this.max.doubleValue()) {
         this.max = var2;
      }

      return this;
   }
}
