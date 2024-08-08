package com.example.editme.util.builders;

import com.example.editme.settings.Setting;
import com.example.editme.settings.StringSetting;

public class StringSettingBuilder extends SettingBuilder {
   public Setting build() {
      return this.build();
   }

   public StringSetting build() {
      return new StringSetting((String)this.initialValue, this.predicate(), this.consumer(), this.name, this.visibilityPredicate());
   }
}
