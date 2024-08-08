package com.example.editme.util.converters;

import com.example.editme.settings.EnumSetting;
import com.example.editme.settings.Setting;
import com.example.editme.util.builders.SettingBuilder;

public class KamiEnumToEditmeEnum extends SettingBuilder {
   Class clazz;

   public KamiEnumToEditmeEnum(Class var1) {
      this.clazz = var1;
   }

   public Setting build() {
      return new EnumSetting((Enum)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate(), this.clazz);
   }
}
