package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Slider;
import com.lukflug.panelstudio.theme.Renderer;

public class NumberComponent extends Slider {
   protected NumberSetting setting;
   protected String text;

   public NumberComponent(String var1, String var2, Renderer var3, NumberSetting var4, double var5, double var7) {
      super("", var2, var3);
      this.setting = var4;
      this.text = var1;
   }

   protected double getValue() {
      return (this.setting.getNumber() - this.setting.getMinimumValue()) / (this.setting.getMaximumValue() - this.setting.getMinimumValue());
   }

   protected void setValue(double var1) {
      this.setting.setNumber(var1 * (this.setting.getMaximumValue() - this.setting.getMinimumValue()) + this.setting.getMinimumValue());
   }

   public void render(Context var1) {
      if (this.setting.getPrecision() == 0) {
         this.title = String.format("%s: §7%d", this.text, (int)this.setting.getNumber());
      } else {
         this.title = String.format(String.valueOf((new StringBuilder("%s: §7%.")).append(this.setting.getPrecision()).append("f")), this.text, this.setting.getNumber());
      }

      super.render(var1);
   }
}
