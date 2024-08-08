package com.example.editme.util.builders;

import com.example.editme.settings.DoubleSetting;
import com.example.editme.settings.NumberSetting;
import com.example.editme.settings.Setting;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class DoubleSettingBuilder extends NumericalSettingBuilder {
   public DoubleSettingBuilder withName(String var1) {
      return (DoubleSettingBuilder)super.withName(var1);
   }

   public DoubleSettingBuilder withValue(Double var1) {
      return (DoubleSettingBuilder)super.withValue((Number)var1);
   }

   public SettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public NumericalSettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public DoubleSettingBuilder withRestriction(Predicate var1) {
      return (DoubleSettingBuilder)super.withRestriction(var1);
   }

   public SettingBuilder withValue(Object var1) {
      return this.withValue((Double)var1);
   }

   public SettingBuilder withVisibility(Predicate var1) {
      return this.withVisibility(var1);
   }

   public NumericalSettingBuilder withValue(Number var1) {
      return this.withValue((Double)var1);
   }

   public DoubleSettingBuilder withRange(Double var1, Double var2) {
      return (DoubleSettingBuilder)super.withRange(var1, var2);
   }

   public SettingBuilder withConsumer(BiConsumer var1) {
      return this.withConsumer(var1);
   }

   public NumericalSettingBuilder withMaximum(Number var1) {
      return this.withMaximum((Double)var1);
   }

   public NumericalSettingBuilder withListener(BiConsumer var1) {
      return this.withListener(var1);
   }

   public NumericalSettingBuilder withRange(Number var1, Number var2) {
      return this.withRange((Double)var1, (Double)var2);
   }

   public DoubleSettingBuilder withListener(BiConsumer var1) {
      return (DoubleSettingBuilder)super.withListener(var1);
   }

   public DoubleSettingBuilder withVisibility(Predicate var1) {
      return (DoubleSettingBuilder)super.withVisibility(var1);
   }

   public DoubleSettingBuilder withConsumer(BiConsumer var1) {
      return (DoubleSettingBuilder)super.withConsumer(var1);
   }

   public NumberSetting build() {
      return new DoubleSetting((Double)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), (Double)this.min, (Double)this.max);
   }

   public DoubleSettingBuilder withMaximum(Double var1) {
      return (DoubleSettingBuilder)super.withMaximum(var1);
   }

   public DoubleSettingBuilder withMinimum(Double var1) {
      return (DoubleSettingBuilder)super.withMinimum(var1);
   }

   public NumericalSettingBuilder withMinimum(Number var1) {
      return this.withMinimum((Double)var1);
   }

   public Setting build() {
      return this.build();
   }

   public SettingBuilder withRestriction(Predicate var1) {
      return this.withRestriction(var1);
   }
}
