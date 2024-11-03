package xyz.cucumber.base.interf.clientsettings.ext.impl;

import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class BooleanClientSetting extends ClientSetting {
   private BooleanSettings setting;
   private PositionUtils button = new PositionUtils(0.0, 0.0, 20.0, 10.0, 1.0F);
   private double animation;

   public BooleanClientSetting(ModuleSettings setting) {
      this.setting = (BooleanSettings)setting;
      this.position.setHeight(15.0);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.button.setX(this.position.getX2() - this.button.getWidth() - 7.0);
      this.button.setY(this.position.getY() + this.position.getHeight() / 2.0 - this.button.getHeight() / 2.0);
      Fonts.getFont("rb-m")
         .drawString(
            this.setting.getName(),
            this.position.getX() + 8.0,
            this.position.getY() + this.position.getHeight() / 2.0 - (double)(Fonts.getFont("rb-m").getHeight("H") / 2.0F),
            -1
         );
      RenderUtils.drawRoundedRect(this.button.getX(), this.button.getY(), this.button.getX2(), this.button.getY2(), -1867863382, 4.0F);
      if (this.setting.isEnabled()) {
         this.animation = (this.animation * 9.0 + 10.0) / 10.0;
      } else {
         this.animation = this.animation * 9.0 / 10.0;
      }

      RenderUtils.drawCircle(this.button.getX() + 5.0 + this.animation, this.button.getY() + 5.0, 4.5, 637534207, 2.0);
      RenderUtils.drawCircle(this.button.getX() + 5.0 + this.animation, this.button.getY() + 5.0, 5.5, 637534207, 2.0);
      RenderUtils.drawCircle(this.button.getX() + 5.0 + this.animation, this.button.getY() + 5.0, 6.5, 637534207, 2.0);
      RenderUtils.drawCircle(this.button.getX() + 5.0 + this.animation, this.button.getY() + 5.0, 4.0, -1, 2.0);
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if (this.button.isInside(mouseX, mouseY) && button == 0) {
         this.setting.setEnabled(!this.setting.isEnabled());
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
   }
}
