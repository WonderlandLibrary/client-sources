package cc.slack.features.modules.api;

import java.awt.Color;

public enum Category {
   COMBAT(new Color(213, 35, 106)),
   MOVEMENT(new Color(213, 106, 35)),
   PLAYER(new Color(35, 213, 106)),
   WORLD(new Color(106, 213, 35)),
   EXPLOIT(new Color(213, 35, 35)),
   RENDER(new Color(106, 35, 213)),
   GHOST(new Color(0, 0, 0)),
   UTILITIES(new Color(0, 128, 128)),
   OTHER(new Color(102, 30, 200));

   final String name;
   final String icon;
   final Color color;

   private Category(Color color) {
      this.color = color;
      this.name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
      this.icon = this.name().substring(0, 1);
   }

   public int getColorRGB() {
      return this.getColor().getRGB();
   }

   public String getName() {
      return this.name;
   }

   public String getIcon() {
      return this.icon;
   }

   public Color getColor() {
      return this.color;
   }
}
