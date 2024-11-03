package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.BloomUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class BooleanButton extends SettingsButton {
   private BooleanSettings setting;
   private PositionUtils checkBox;
   private double animation;
   private BloomUtils bloom = new BloomUtils();

   public BooleanButton(BooleanSettings setting, PositionUtils checkBox) {
      this.setting = setting;
      this.settingMain = setting;
      this.position = checkBox;
      this.checkBox = new PositionUtils(0.0, 0.0, 10.0, 10.0, 1.0F);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      if (this.setting.isEnabled()) {
         this.animation = (this.animation * 15.0 + 5.0) / 16.0;
      } else {
         this.animation = this.animation * 15.0 / 16.0;
      }

      Fonts.getFont("rb-r").drawString(this.setting.getName(), this.position.getX() + 8.0, this.position.getY() + 3.0, -1);
      this.checkBox.setX(this.position.getX2() - 55.0);
      this.checkBox.setY(this.position.getY() + 2.5);
      RenderUtils.drawRoundedRect(this.checkBox.getX(), this.checkBox.getY(), this.checkBox.getX2(), this.checkBox.getY2(), -15526889, 2.0F);
      RenderUtils.drawRoundedRect(
         this.checkBox.getX() + this.checkBox.getWidth() / 2.0 - this.animation,
         this.checkBox.getY() + this.checkBox.getHeight() / 2.0 - this.animation,
         this.checkBox.getX() + this.checkBox.getWidth() / 2.0 + this.animation,
         this.checkBox.getY() + this.checkBox.getHeight() / 2.0 + this.animation,
         -12424715,
         2.0F
      );
      if (this.setting.isEnabled()) {
         RenderUtils.drawImage(
            this.checkBox.getX() + this.checkBox.getWidth() / 2.0 - 3.0,
            this.checkBox.getY() + this.checkBox.getHeight() / 2.0 - 3.0 + 0.5,
            6.0,
            6.0,
            new ResourceLocation("client/images/check.png"),
            -1
         );
      }
   }

   @Override
   public void click(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY) && this.checkBox.isInside(mouseX, mouseY) && button == 0) {
         this.setting.setEnabled(!this.setting.isEnabled());
      }
   }

   public BooleanSettings getSetting() {
      return this.setting;
   }

   public void setSetting(BooleanSettings setting) {
      this.setting = setting;
   }

   public PositionUtils getCheckBox() {
      return this.checkBox;
   }

   public void setCheckBox(PositionUtils checkBox) {
      this.checkBox = checkBox;
   }
}
