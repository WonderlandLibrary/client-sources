package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.settings.ColorSetting;
import com.lukflug.panelstudio.settings.NumberSetting;
import java.awt.Color;

public class SettingsColorScheme implements ColorScheme {
   protected final ColorSetting outlineColor;
   protected final NumberSetting opacity;
   protected final ColorSetting fontColor;
   protected final ColorSetting inactiveColor;
   protected final ColorSetting activeColor;
   protected final ColorSetting backgroundColor;

   public Color getInactiveColor() {
      return this.inactiveColor.getValue();
   }

   public Color getBackgroundColor() {
      return this.backgroundColor.getValue();
   }

   public Color getOutlineColor() {
      return this.outlineColor.getValue();
   }

   public int getOpacity() {
      return (int)this.opacity.getNumber();
   }

   public Color getFontColor() {
      return this.fontColor.getValue();
   }

   public SettingsColorScheme(ColorSetting var1, ColorSetting var2, ColorSetting var3, ColorSetting var4, ColorSetting var5, NumberSetting var6) {
      this.activeColor = var1;
      this.inactiveColor = var2;
      this.backgroundColor = var3;
      this.outlineColor = var4;
      this.fontColor = var5;
      this.opacity = var6;
   }

   public Color getActiveColor() {
      return this.activeColor.getValue();
   }
}
