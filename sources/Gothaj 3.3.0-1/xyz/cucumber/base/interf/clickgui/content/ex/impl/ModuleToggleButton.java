package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ModuleToggleButton extends SettingsButton {
   private Mod module;
   private PositionUtils toggleButton;
   private double toggleAnimation;

   public ModuleToggleButton(Mod mod, PositionUtils position) {
      this.module = mod;
      this.position = position;
      this.toggleButton = new PositionUtils(0.0, 0.0, 20.0, 10.0, 1.0F);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      int color = -1;
      if (this.module.isEnabled()) {
         this.toggleAnimation = (this.toggleAnimation * 9.0 + this.toggleButton.getWidth() - 10.0) / 10.0;
      } else {
         this.toggleAnimation = this.toggleAnimation * 9.0 / 10.0;
      }

      color = ColorUtils.mix(-1, -12424715, this.toggleAnimation, this.toggleButton.getWidth() - 10.0);
      RenderUtils.drawRoundedRect(this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -15066598, 1.0F);
      Fonts.getFont("rb-r").drawString(this.module.getName(), this.position.getX() + 5.0, this.position.getY() + 5.0, color);
      Fonts.getFont("rb-r")
         .drawString(
            this.module.getDescription(),
            this.position.getX() + this.position.getWidth() / 2.0 - Fonts.getFont("rb-r").getWidth(this.module.getDescription()) / 2.0,
            this.position.getY() + 4.0,
            -5592406
         );
      this.toggleButton.setX(this.position.getX2() - this.toggleButton.getWidth() - 2.5);
      this.toggleButton.setY(this.position.getY() + 2.5);
      RenderUtils.drawRoundedRect(
         this.toggleButton.getX(),
         this.toggleButton.getY(),
         this.toggleButton.getX2(),
         this.toggleButton.getY2(),
         ColorUtils.mix(-13421773, -12424715, this.toggleAnimation, this.toggleButton.getWidth() - 10.0),
         4.0F
      );
      RenderUtils.drawCircle(
         this.toggleButton.getX() + 4.0 + 1.0 + this.toggleAnimation, this.toggleButton.getY() + this.toggleButton.getHeight() / 2.0, 4.0, -1, 1.0
      );
   }

   @Override
   public void click(int mouseX, int mouseY, int button) {
      if (this.toggleButton.isInside(mouseX, mouseY) && button == 0) {
         this.module.toggle();
      }
   }
}
