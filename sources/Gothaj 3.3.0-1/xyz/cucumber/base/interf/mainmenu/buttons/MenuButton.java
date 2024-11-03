package xyz.cucumber.base.interf.mainmenu.buttons;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.RoundedUtils;

public class MenuButton {
   private String name;
   private PositionUtils position;
   private double imageAnimation;
   private double textAnimation = 255.0;
   private float startTime;
   private int id;
   private Minecraft mc = Minecraft.getMinecraft();
   public int color1;
   public int color2;

   public MenuButton(String name, PositionUtils position, int color1, int color2, int id) {
      this.position = position;
      this.id = id;
      this.name = name;
      this.color1 = color1;
      this.color2 = color2;
   }

   public void draw(int mouseX, int mouseY) {
      if (this.position.isInside(mouseX, mouseY)) {
         this.textAnimation = this.textAnimation * 9.0 / 10.0;
      } else {
         this.textAnimation = (this.textAnimation * 9.0 + 255.0) / 10.0;
      }

      RoundedUtils.drawGradientRectSideways(
         this.position.getX(), this.position.getY(), this.position.getWidth(), this.position.getHeight(), this.color2, this.color1, 4.0F
      );
      RenderUtils.drawRoundedRect(
         this.position.getX() + 1.5,
         this.position.getY() + 1.5,
         this.position.getX2() - 1.5,
         this.position.getY2() - 1.5,
         new Color(28, 28, 28, (int)this.textAnimation).getRGB(),
         3.0F
      );
      Fonts.getFont("rb-b")
         .drawString(
            this.name, this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-b").getWidth(this.name) / 2.0, this.position.getY() + 9.5, -1
         );
   }

   public void clicked(int mouseX, int mouseY, int button) {
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   public int getId() {
      return this.id;
   }
}
