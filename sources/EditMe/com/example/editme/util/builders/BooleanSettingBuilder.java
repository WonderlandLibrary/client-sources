package com.example.editme.util.builders;

import com.example.editme.settings.BooleanSetting;
import com.example.editme.settings.Setting;

public class BooleanSettingBuilder extends SettingBuilder {
   public BooleanSetting build() {
      return new BooleanSetting((Boolean)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate());
   }

   public SettingBuilder withName(String var1) {
      return this.withName(var1);
   }

   public Setting build() {
      return this.build();
   }

   public BooleanSettingBuilder withName(String var1) {
      return (BooleanSettingBuilder)super.withName(var1);
   }
}
