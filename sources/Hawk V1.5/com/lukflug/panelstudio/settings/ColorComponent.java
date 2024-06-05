package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.Slider;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;
import java.awt.Color;

public class ColorComponent extends CollapsibleContainer {
   protected final boolean alpha;
   protected final boolean rainbow;
   protected ColorSetting setting;
   protected ColorScheme overrideScheme;
   protected Toggleable colorModel;
   protected ColorScheme scheme;

   public void render(Context var1) {
      this.renderer.overrideColorScheme(this.scheme);
      super.render(var1);
      this.renderer.restoreColorScheme();
   }

   public ColorComponent(String var1, String var2, Renderer var3, Animation var4, Renderer var5, ColorSetting var6, boolean var7, boolean var8, Toggleable var9) {
      super(var1, var2, var3, new SimpleToggleable(false), var4, (Toggleable)null);
      this.setting = var6;
      this.alpha = var7;
      this.rainbow = var8;
      this.scheme = new ColorComponent.ColorSettingScheme(this, var3);
      this.overrideScheme = new ColorComponent.ColorSettingScheme(this, var5);
      this.colorModel = var9;
      if (var8) {
         this.addComponent(new ColorComponent.ColorButton(this, var5));
      }

      this.addComponent(new ColorComponent.ColorSlider(this, var5, 0));
      this.addComponent(new ColorComponent.ColorSlider(this, var5, 1));
      this.addComponent(new ColorComponent.ColorSlider(this, var5, 2));
      if (var7) {
         this.addComponent(new ColorComponent.ColorSlider(this, var5, 3));
      }

   }

   protected class ColorButton extends FocusableComponent {
      final ColorComponent this$0;

      public void handleButton(Context var1, int var2) {
         super.handleButton(var1, var2);
         if (var2 == 0 && var1.isClicked()) {
            this.this$0.setting.setRainbow(!this.this$0.setting.getRainbow());
         }

      }

      public void render(Context var1) {
         super.render(var1);
         this.renderer.overrideColorScheme(this.this$0.overrideScheme);
         this.renderer.renderTitle(var1, this.title, this.hasFocus(var1), this.this$0.setting.getRainbow());
         this.renderer.restoreColorScheme();
      }

      public ColorButton(ColorComponent var1, Renderer var2) {
         super("Rainbow", (String)null, var2);
         this.this$0 = var1;
      }
   }

   protected class ColorSlider extends Slider {
      final ColorComponent this$0;
      private final int value;

      protected String getTitle(int var1) {
         switch(var1) {
         case 0:
            return String.valueOf((new StringBuilder(String.valueOf(this.this$0.colorModel.isOn() ? "Hue:" : "Red:"))).append(" §7"));
         case 1:
            return String.valueOf((new StringBuilder(String.valueOf(this.this$0.colorModel.isOn() ? "Saturation:" : "Green:"))).append(" §7"));
         case 2:
            return String.valueOf((new StringBuilder(String.valueOf(this.this$0.colorModel.isOn() ? "Brightness:" : "Blue:"))).append(" §7"));
         case 3:
            return "Alpha: §7";
         default:
            return "";
         }
      }

      public void render(Context var1) {
         this.title = String.valueOf((new StringBuilder(String.valueOf(this.getTitle(this.value)))).append((int)((double)this.getMax() * this.getValue())));
         this.renderer.overrideColorScheme(this.this$0.overrideScheme);
         super.render(var1);
         this.renderer.restoreColorScheme();
      }

      protected int getMax() {
         if (!this.this$0.colorModel.isOn()) {
            return 255;
         } else if (this.value == 0) {
            return 360;
         } else {
            return this.value < 3 ? 100 : 255;
         }
      }

      public ColorSlider(ColorComponent var1, Renderer var2, int var3) {
         super("", (String)null, var2);
         this.this$0 = var1;
         this.value = var3;
      }

      protected double getValue() {
         Color var1 = this.this$0.setting.getColor();
         if (this.value < 3) {
            if (this.this$0.colorModel.isOn()) {
               return (double)Color.RGBtoHSB(var1.getRed(), var1.getGreen(), var1.getBlue(), (float[])null)[this.value];
            }

            switch(this.value) {
            case 0:
               return (double)var1.getRed() / 255.0D;
            case 1:
               return (double)var1.getGreen() / 255.0D;
            case 2:
               return (double)var1.getBlue() / 255.0D;
            }
         }

         return (double)var1.getAlpha() / 255.0D;
      }

      protected void setValue(double var1) {
         Color var3 = this.this$0.setting.getColor();
         float[] var4 = Color.RGBtoHSB(var3.getRed(), var3.getGreen(), var3.getBlue(), (float[])null);
         switch(this.value) {
         case 0:
            if (this.this$0.colorModel.isOn()) {
               var3 = Color.getHSBColor((float)var1, var4[1], var4[2]);
            } else {
               var3 = new Color((int)(255.0D * var1), var3.getGreen(), var3.getBlue());
            }

            if (this.this$0.alpha) {
               this.this$0.setting.setValue(new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), this.this$0.setting.getColor().getAlpha()));
            } else {
               this.this$0.setting.setValue(var3);
            }
            break;
         case 1:
            if (this.this$0.colorModel.isOn()) {
               var3 = Color.getHSBColor(var4[0], (float)var1, var4[2]);
            } else {
               var3 = new Color(var3.getRed(), (int)(255.0D * var1), var3.getBlue());
            }

            if (this.this$0.alpha) {
               this.this$0.setting.setValue(new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), this.this$0.setting.getColor().getAlpha()));
            } else {
               this.this$0.setting.setValue(var3);
            }
            break;
         case 2:
            if (this.this$0.colorModel.isOn()) {
               var3 = Color.getHSBColor(var4[0], var4[1], (float)var1);
            } else {
               var3 = new Color(var3.getRed(), var3.getGreen(), (int)(255.0D * var1));
            }

            if (this.this$0.alpha) {
               this.this$0.setting.setValue(new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), this.this$0.setting.getColor().getAlpha()));
            } else {
               this.this$0.setting.setValue(var3);
            }
            break;
         case 3:
            this.this$0.setting.setValue(new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(255.0D * var1)));
         }

      }
   }

   protected class ColorSettingScheme implements ColorScheme {
      ColorScheme scheme;
      final ColorComponent this$0;

      public ColorSettingScheme(ColorComponent var1, Renderer var2) {
         this.this$0 = var1;
         this.scheme = var2.getDefaultColorScheme();
      }

      public Color getBackgroundColor() {
         return this.scheme.getBackgroundColor();
      }

      public Color getOutlineColor() {
         return this.scheme.getOutlineColor();
      }

      public Color getFontColor() {
         return this.scheme.getFontColor();
      }

      public Color getInactiveColor() {
         return this.scheme.getInactiveColor();
      }

      public int getOpacity() {
         return this.scheme.getOpacity();
      }

      public Color getActiveColor() {
         return this.this$0.setting.getValue();
      }
   }
}
