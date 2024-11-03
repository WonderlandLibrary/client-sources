package net.augustus.utils;

import java.awt.Color;

public class RainbowUtil {
   private float rainBowColor = 0.0F;
   private Color color = new Color(0, 0, 0);
   private long lastTime;

   public Color updateRainbow(float speed, int alpha) {
      long deltaTime = System.currentTimeMillis() - this.lastTime;
      if (speed > 0.0F) {
         speed *= (float)deltaTime / 2.0F;
      }

      this.rainBowColor += speed;
      this.rainBowColor = this.rainBowColor > 1.0F ? (this.rainBowColor %= 1.0F) : this.rainBowColor;
      int red = Color.getHSBColor(this.rainBowColor, 1.0F, 1.0F).getRed();
      int green = Color.getHSBColor(this.rainBowColor, 1.0F, 1.0F).getGreen();
      int blue = Color.getHSBColor(this.rainBowColor, 1.0F, 1.0F).getBlue();
      this.color = new Color(red, green, blue, alpha);
      this.lastTime = System.currentTimeMillis();
      return this.color;
   }

   public Color getColor() {
      return this.color;
   }
}
