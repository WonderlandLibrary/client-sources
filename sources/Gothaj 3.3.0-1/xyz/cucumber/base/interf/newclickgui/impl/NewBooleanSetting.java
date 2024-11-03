package xyz.cucumber.base.interf.newclickgui.impl;

import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NewBooleanSetting extends NewSetting {
   private PositionUtils button = new PositionUtils(0.0, 0.0, 20.0, 10.0, 1.0F);
   private double animation;

   public NewBooleanSetting(ModuleSettings setting) {
      this.setting = setting;
      this.position.setWidth(294.0);
      this.position.setHeight(14.0);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.animation = (this.animation * 9.0 + (double)(((BooleanSettings)this.setting).isEnabled() ? 1 : 0)) / 10.0;
      Fonts.getFont("rb-m")
         .drawString(this.setting.getName(), this.position.getX() + 12.0, this.position.getY() + 5.0, ColorUtils.mix(-5592406, -1, this.animation, 1.0));
      this.button.setX(this.position.getX2() - 32.0);
      this.button.setY(this.position.getY() + 1.0);
      RenderUtils.drawRoundedRect(
         this.button.getX(), this.button.getY(), this.button.getX2(), this.button.getY2(), ColorUtils.mix(-16185079, -881934, this.animation, 1.0), 4.0F
      );
      RenderUtils.drawCircle(
         this.button.getX() + this.button.getWidth() / 4.0 + this.animation * this.button.getWidth() / 2.1,
         this.button.getY() + this.button.getWidth() / 4.0,
         this.button.getWidth() / 4.5,
         -1,
         2.0
      );
   }

   @Override
   public void onClick(int mouseX, int mouseY, int b) {
      if (this.button.isInside(mouseX, mouseY) && b == 0) {
         ((BooleanSettings)this.setting).setEnabled(!((BooleanSettings)this.setting).isEnabled());
      }
   }
}
