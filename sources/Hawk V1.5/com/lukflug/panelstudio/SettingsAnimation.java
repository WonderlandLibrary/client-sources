package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.NumberSetting;

public class SettingsAnimation extends Animation {
   protected final NumberSetting speed;

   protected int getSpeed() {
      return (int)this.speed.getNumber();
   }

   public SettingsAnimation(NumberSetting var1) {
      this.speed = var1;
   }
}
