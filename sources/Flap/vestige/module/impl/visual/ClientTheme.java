package vestige.module.impl.visual;

import java.awt.Color;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.EventListenType;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.render.ColorUtil;

public class ClientTheme extends Module {
   public final ModeSetting color = new ModeSetting("Color", "Breeze", new String[]{"Static", "Gradient", "Rainbow", "Candy", "Breeze", "Police", "Dog", "Bloody", "Roses", "White", "Blue"});
   private final IntegerSetting red = new IntegerSetting("Red", () -> {
      return this.color.getMode().equals("Static") || this.color.is("Gradient");
   }, 100, 0, 255, 5);
   private final IntegerSetting green = new IntegerSetting("Green", () -> {
      return this.color.getMode().equals("Static") || this.color.is("Gradient");
   }, 0, 0, 255, 5);
   private final IntegerSetting blue = new IntegerSetting("Blue", () -> {
      return this.color.getMode().equals("Static") || this.color.is("Gradient");
   }, 255, 0, 255, 5);
   private final IntegerSetting red2 = new IntegerSetting("Red 2", () -> {
      return this.color.is("Gradient") || this.color.is("Custom 3 colors");
   }, 175, 0, 255, 5);
   private final IntegerSetting green2 = new IntegerSetting("Green 2", () -> {
      return this.color.is("Gradient") || this.color.is("Custom 3 colors");
   }, 85, 0, 255, 5);
   private final IntegerSetting blue2 = new IntegerSetting("Blue 2", () -> {
      return this.color.is("Gradient") || this.color.is("Custom 3 colors");
   }, 255, 0, 255, 5);
   private final DoubleSetting saturation = new DoubleSetting("Saturation", () -> {
      return this.color.is("Rainbow");
   }, 0.9D, 0.05D, 1.0D, 0.05D);
   private final DoubleSetting brightness = new DoubleSetting("Brightness", () -> {
      return this.color.is("Rainbow");
   }, 0.9D, 0.05D, 1.0D, 0.05D);
   private Color color1;
   private Color color2;
   private Color color3;
   private boolean colorsSet;

   public ClientTheme() {
      super("Theme", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.color, this.red, this.green, this.blue, this.red2, this.green2, this.blue2, this.saturation, this.brightness});
      this.listenType = EventListenType.MANUAL;
      this.startListening();
   }

   public void onEnable() {
      this.setEnabled(false);
   }

   @Listener(0)
   public void onRender(RenderEvent event) {
      this.setColors();
      this.colorsSet = true;
   }

   public int getColor(int offset) {
      if (!this.colorsSet) {
         this.setColors();
         this.colorsSet = true;
      }

      String var2 = this.color.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1898802882:
         if (var2.equals("Police")) {
            var3 = 7;
         }
         break;
      case -1808614770:
         if (var2.equals("Static")) {
            var3 = 2;
         }
         break;
      case -1656737386:
         if (var2.equals("Rainbow")) {
            var3 = 4;
         }
         break;
      case 68892:
         if (var2.equals("Dog")) {
            var3 = 8;
         }
         break;
      case 2073722:
         if (var2.equals("Blue")) {
            var3 = 1;
         }
         break;
      case 64874565:
         if (var2.equals("Candy")) {
            var3 = 5;
         }
         break;
      case 79149284:
         if (var2.equals("Roses")) {
            var3 = 10;
         }
         break;
      case 83549193:
         if (var2.equals("White")) {
            var3 = 0;
         }
         break;
      case 154295120:
         if (var2.equals("Gradient")) {
            var3 = 3;
         }
         break;
      case 1992680927:
         if (var2.equals("Bloody")) {
            var3 = 9;
         }
         break;
      case 1997915195:
         if (var2.equals("Breeze")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
         return -1;
      case 1:
         return ColorUtil.getColor(new Color(5, 138, 255), new Color(0, 35, 206), 2500L, offset);
      case 2:
         return this.color1.getRGB();
      case 3:
         return ColorUtil.getColor(this.color1, this.color2, 2500L, offset);
      case 4:
         return ColorUtil.getRainbow(4500L, (int)((double)offset * 0.65D), (float)this.saturation.getValue(), (float)this.brightness.getValue());
      case 5:
         return ColorUtil.getColor(new Color(100, 0, 255), new Color(255, 255, 255), 2000L, offset);
      case 6:
         return ColorUtil.getColor(new Color(60, 60, 255), new Color(165, 100, 255), 2000L, offset);
      case 7:
         return ColorUtil.getColor(new Color(255, 0, 0), new Color(0, 0, 255), 2000L, offset);
      case 8:
         return ColorUtil.getColor(new Color(255, 90, 0), new Color(90, 155, 255), 2000L, offset);
      case 9:
         return ColorUtil.getColor(new Color(255, 0, 0), new Color(100, 100, 100), 2000L, offset);
      case 10:
         return ColorUtil.getColor(new Color(255, 0, 65), new Color(175, 140, 255), 2000L, offset);
      default:
         return -1;
      }
   }

   public Color getColortocolor(int offset) {
      if (!this.colorsSet) {
         this.setColors();
         this.colorsSet = true;
      }

      String var2 = this.color.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1898802882:
         if (var2.equals("Police")) {
            var3 = 7;
         }
         break;
      case -1808614770:
         if (var2.equals("Static")) {
            var3 = 2;
         }
         break;
      case -1656737386:
         if (var2.equals("Rainbow")) {
            var3 = 4;
         }
         break;
      case 68892:
         if (var2.equals("Dog")) {
            var3 = 8;
         }
         break;
      case 2073722:
         if (var2.equals("Blue")) {
            var3 = 1;
         }
         break;
      case 64874565:
         if (var2.equals("Candy")) {
            var3 = 5;
         }
         break;
      case 79149284:
         if (var2.equals("Roses")) {
            var3 = 10;
         }
         break;
      case 83549193:
         if (var2.equals("White")) {
            var3 = 0;
         }
         break;
      case 154295120:
         if (var2.equals("Gradient")) {
            var3 = 3;
         }
         break;
      case 1992680927:
         if (var2.equals("Bloody")) {
            var3 = 9;
         }
         break;
      case 1997915195:
         if (var2.equals("Breeze")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
         return new Color(255, 255, 255);
      case 1:
         return ColorUtil.getColorinColor(new Color(5, 138, 255), new Color(0, 35, 206), 2500L, offset);
      case 2:
         return this.color1;
      case 3:
         return ColorUtil.getColorinColor(this.color1, this.color2, 2500L, offset);
      case 4:
         return ColorUtil.getRainboww(4500L, (int)((double)offset * 0.65D), (float)this.saturation.getValue(), (float)this.brightness.getValue());
      case 5:
         return ColorUtil.getColorinColor(new Color(140, 0, 170), new Color(255, 255, 255), 2000L, offset);
      case 6:
         return ColorUtil.getColorinColor(new Color(140, 0, 170), new Color(0, 165, 255), 2000L, offset);
      case 7:
         return ColorUtil.getColorinColor(new Color(255, 0, 0), new Color(0, 0, 255), 2000L, offset);
      case 8:
         return ColorUtil.getColorinColor(new Color(255, 90, 0), new Color(90, 155, 255), 2000L, offset);
      case 9:
         return ColorUtil.getColorinColor(new Color(255, 0, 0), new Color(100, 100, 100), 2000L, offset);
      case 10:
         return ColorUtil.getColorinColor(new Color(255, 0, 65), new Color(175, 140, 255), 2000L, offset);
      default:
         return new Color(255, 255, 255);
      }
   }

   public int getColorlowopacity(int offset) {
      if (!this.colorsSet) {
         this.setColors();
         this.colorsSet = true;
      }

      String var2 = this.color.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1898802882:
         if (var2.equals("Police")) {
            var3 = 7;
         }
         break;
      case -1808614770:
         if (var2.equals("Static")) {
            var3 = 2;
         }
         break;
      case -1656737386:
         if (var2.equals("Rainbow")) {
            var3 = 4;
         }
         break;
      case 68892:
         if (var2.equals("Dog")) {
            var3 = 8;
         }
         break;
      case 2073722:
         if (var2.equals("Blue")) {
            var3 = 1;
         }
         break;
      case 64874565:
         if (var2.equals("Candy")) {
            var3 = 5;
         }
         break;
      case 79149284:
         if (var2.equals("Roses")) {
            var3 = 10;
         }
         break;
      case 83549193:
         if (var2.equals("White")) {
            var3 = 0;
         }
         break;
      case 154295120:
         if (var2.equals("Gradient")) {
            var3 = 3;
         }
         break;
      case 1992680927:
         if (var2.equals("Bloody")) {
            var3 = 9;
         }
         break;
      case 1997915195:
         if (var2.equals("Breeze")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
         return -1;
      case 1:
         return ColorUtil.getColor(new Color(5, 138, 255, 175), new Color(0, 35, 206, 175), 2500L, offset);
      case 2:
         return this.color1.getRGB();
      case 3:
         return ColorUtil.getColor(this.color1, this.color2, 2500L, offset);
      case 4:
         return ColorUtil.getRainbow(4500L, (int)((double)offset * 0.65D), (float)this.saturation.getValue(), (float)this.brightness.getValue());
      case 5:
         return ColorUtil.getColor(new Color(100, 0, 255, 175), new Color(255, 255, 255, 175), 2000L, offset);
      case 6:
         return ColorUtil.getColor(new Color(60, 60, 255, 175), new Color(165, 100, 255, 175), 2000L, offset);
      case 7:
         return ColorUtil.getColor(new Color(255, 0, 0, 175), new Color(0, 0, 255, 175), 2000L, offset);
      case 8:
         return ColorUtil.getColor(new Color(255, 90, 0, 175), new Color(90, 155, 255, 175), 2000L, offset);
      case 9:
         return ColorUtil.getColor(new Color(255, 0, 0, 175), new Color(100, 100, 100, 175), 2000L, offset);
      case 10:
         return ColorUtil.getColor(new Color(255, 0, 65), new Color(175, 140, 255), 2000L, offset);
      default:
         return -1;
      }
   }

   private void setColors() {
      this.color1 = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue());
      this.color2 = new Color(this.red2.getValue(), this.green2.getValue(), this.blue2.getValue());
   }
}
