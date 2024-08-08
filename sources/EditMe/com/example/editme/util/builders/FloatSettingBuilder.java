package com.example.editme.util.builders;

import com.example.editme.settings.FloatSetting;
import com.example.editme.settings.NumberSetting;
import com.example.editme.settings.Setting;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class FloatSettingBuilder extends NumericalSettingBuilder {
   public NumericalSettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public NumberSetting build() {
      return new FloatSetting((Float)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), (Float)this.min, (Float)this.max);
   }

   public SettingBuilder withRestriction(Predicate var1) {
      return this.withRestriction(var1);
   }

   public SettingBuilder withValue(Object var1) {
      return this.withValue((Float)var1);
   }

   public NumericalSettingBuilder withMaximum(Number var1) {
      return this.withMaximum((Float)var1);
   }

   public FloatSettingBuilder withMinimum(Float var1) {
      return (FloatSettingBuilder)super.withMinimum(var1);
   }

   public NumericalSettingBuilder withValue(Number var1) {
      return this.withValue((Float)var1);
   }

   public NumericalSettingBuilder withListener(BiConsumer var1) {
      return this.withListener(var1);
   }

   public SettingBuilder withVisibility(Predicate var1) {
      return this.withVisibility(var1);
   }

   public FloatSettingBuilder withRange(Float var1, Float var2) {
      return (FloatSettingBuilder)super.withRange(var1, var2);
   }

   public SettingBuilder withConsumer(BiConsumer var1) {
      return this.withConsumer(var1);
   }

   public FloatSettingBuilder withListener(BiConsumer var1) {
      return (FloatSettingBuilder)super.withListener(var1);
   }

   public SettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public NumericalSettingBuilder withRange(Number var1, Number var2) {
      return this.withRange((Float)var1, (Float)var2);
   }

   public Setting build() {
      return this.build();
   }

   public FloatSettingBuilder withName(String var1) {
      return (FloatSettingBuilder)super.withName(var1);
   }

   public NumericalSettingBuilder withMinimum(Number var1) {
      return this.withMinimum((Float)var1);
   }

   public FloatSettingBuilder withMaximum(Float var1) {
      return (FloatSettingBuilder)super.withMaximum(var1);
   }

   public FloatSettingBuilder withConsumer(BiConsumer var1) {
      return (FloatSettingBuilder)super.withConsumer(var1);
   }

   public FloatSettingBuilder withValue(Float var1) {
      return (FloatSettingBuilder)super.withValue((Number)var1);
   }

   public FloatSettingBuilder withRestriction(Predicate var1) {
      return (FloatSettingBuilder)super.withRestriction(var1);
   }

   public FloatSettingBuilder withVisibility(Predicate var1) {
      return (FloatSettingBuilder)super.withVisibility(var1);
   }
}
