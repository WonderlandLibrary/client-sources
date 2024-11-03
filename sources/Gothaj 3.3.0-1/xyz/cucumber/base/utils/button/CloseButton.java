package xyz.cucumber.base.utils.button;

import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

public class CloseButton extends Button {
   private int animation;

   public CloseButton(int id, double x, double y, double width, double height) {
      this.position = new PositionUtils(x, y, width, height, 1.0F);
      this.id = id;
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      if (this.position.isInside(mouseX, mouseY)) {
         this.animation = (this.animation * 9 + 80) / 10;
      } else {
         this.animation = (this.animation * 9 + 30) / 10;
      }

      RenderUtils.drawRoundedRect(
         this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), ColorUtils.getAlphaColor(890771480, this.animation), 3.0F
      );
      RenderUtils.drawOutlinedRoundedRect(
         this.position.getX(),
         this.position.getY(),
         this.position.getX2(),
         this.position.getY2(),
         ColorUtils.getAlphaColor(890771480, this.animation),
         3.0,
         1.0
      );
      RenderUtils.cross(
         this.position.getX() + this.position.getWidth() / 2.0,
         this.position.getY() + this.position.getHeight() / 2.0,
         this.position.getHeight() / 2.0 - 5.0,
         2.0,
         -5592406
      );
   }

   @Override
   public void onClick(int mouseX, int mouseY, int b) {
   }
}
