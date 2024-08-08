package com.example.editme.util.builders;

import com.example.editme.settings.IntegerSetting;
import com.example.editme.settings.NumberSetting;
import com.example.editme.settings.Setting;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class IntegerSettingBuilder extends NumericalSettingBuilder {
   public IntegerSettingBuilder withRestriction(Predicate var1) {
      return (IntegerSettingBuilder)super.withRestriction(var1);
   }

   public IntegerSettingBuilder withConsumer(BiConsumer var1) {
      return (IntegerSettingBuilder)super.withConsumer(var1);
   }

   public Setting build() {
      return this.build();
   }

   public SettingBuilder withValue(Object var1) {
      return this.withValue((Integer)var1);
   }

   public SettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public SettingBuilder withVisibility(Predicate var1) {
      return this.withVisibility(var1);
   }

   public SettingBuilder withRestriction(Predicate var1) {
      return this.withRestriction(var1);
   }

   public NumericalSettingBuilder withMinimum(Number var1) {
      return this.withMinimum((Integer)var1);
   }

   public IntegerSettingBuilder withMinimum(Integer var1) {
      return (IntegerSettingBuilder)super.withMinimum(var1);
   }

   public IntegerSettingBuilder withMaximum(Integer var1) {
      return (IntegerSettingBuilder)super.withMaximum(var1);
   }

   public IntegerSettingBuilder withListener(BiConsumer var1) {
      return (IntegerSettingBuilder)super.withListener(var1);
   }

   public IntegerSettingBuilder withRange(Integer var1, Integer var2) {
      return (IntegerSettingBuilder)super.withRange(var1, var2);
   }

   public IntegerSettingBuilder withValue(Integer var1) {
      return (IntegerSettingBuilder)super.withValue((Number)var1);
   }

   public NumericalSettingBuilder withName(String var1) {
      return (IntegerSettingBuilder)super.withName(var1);
   }

   public NumberSetting build() {
      return new IntegerSetting((Integer)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), (Integer)this.min, (Integer)this.max);
   }

   public IntegerSettingBuilder withVisibility(Predicate var1) {
      return (IntegerSettingBuilder)super.withVisibility(var1);
   }

   public NumericalSettingBuilder withRange(Number var1, Number var2) {
      return this.withRange((Integer)var1, (Integer)var2);
   }

   public SettingBuilder withConsumer(BiConsumer var1) {
      return this.withConsumer(var1);
   }

   public NumericalSettingBuilder withListener(BiConsumer var1) {
      return this.withListener(var1);
   }

   public NumericalSettingBuilder withMaximum(Number var1) {
      return this.withMaximum((Integer)var1);
   }

   public NumericalSettingBuilder withValue(Number var1) {
      return this.withValue((Integer)var1);
   }
}
