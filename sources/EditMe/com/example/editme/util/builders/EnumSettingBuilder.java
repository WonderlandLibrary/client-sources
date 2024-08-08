package com.example.editme.util.builders;

import com.example.editme.settings.EnumSetting;
import com.example.editme.settings.Setting;

public class EnumSettingBuilder extends SettingBuilder {
   Class clazz;

   public Setting build() {
      return new EnumSetting((Enum)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), this.clazz);
   }

   public EnumSettingBuilder(Class var1) {
      this.clazz = var1;
   }
}
