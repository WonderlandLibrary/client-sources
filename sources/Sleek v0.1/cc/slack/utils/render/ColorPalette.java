package cc.slack.utils.render;

import java.awt.Color;

public enum ColorPalette {
   Background(new Color(20, 20, 20)),
   Primary(new Color(35, 35, 35)),
   Secondary(new Color(16, 16, 16));

   final Color color;

   private ColorPalette(Color color) {
      this.color = color;
   }

   public int getRGB() {
      return this.color.getRGB();
   }

   public Color getColor() {
      return this.color;
   }
}
