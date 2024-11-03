package xyz.cucumber.base.interf.DropdownClickGui.ext.Settings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class DropdownClickGuiNumber extends DropdownClickGuiSettings {
   private NumberSettings setting;
   private boolean dragging;
   private double animation;
   private PositionUtils slider = new PositionUtils(0.0, 0.0, 90.0, 3.0, 1.0F);
   private PositionUtils collideCircle = new PositionUtils(0.0, 0.0, 6.0, 6.0, 1.0F);

   public DropdownClickGuiNumber(ModuleSettings setting, PositionUtils modesPosition) {
      this.setting = (NumberSettings)setting;
      this.position = modesPosition;
      this.mainSetting = setting;
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      double diff = Math.min(this.slider.getWidth(), Math.max(0.0, (double)mouseX - this.slider.getX()));
      if (this.dragging) {
         double newValue = this.roundToPlace(
            diff / this.slider.getWidth() * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), (int)this.slider.getWidth()
         );
         this.setting.setValue((double)((float)newValue));
      }

      Fonts.getFont("rb-m-13").drawString(this.setting.getName(), this.position.getX() + 3.0, this.position.getY() + 1.0, -1);
      Fonts.getFont("rb-m-13")
         .drawString(
            String.valueOf(this.setting.getValue()),
            this.position.getX2() - 5.0 - Fonts.getFont("rb-m-13").getWidth(String.valueOf(this.setting.getValue())),
            this.position.getY() + 1.0,
            -5592406
         );
      this.slider.setX(this.position.getX() + 5.0);
      this.slider.setY(this.position.getY() + 2.0 + 8.0);
      double x = this.slider.getWidth() * (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      this.animation = (this.animation * 9.0 + x) / 10.0;
      RenderUtils.drawRoundedRect(this.slider.getX(), this.slider.getY(), this.slider.getX2(), this.slider.getY2(), -16777216, 1.0F);
      RenderUtils.drawRoundedRect(this.slider.getX(), this.slider.getY(), this.slider.getX() + this.animation, this.slider.getY2(), -7706881, 1.0F);
      this.collideCircle.setX(this.slider.getX() + this.animation - this.collideCircle.getWidth() / 2.0);
      this.collideCircle.setY(this.slider.getY() + this.slider.getHeight() / 2.0 - this.collideCircle.getHeight() / 2.0);
      RenderUtils.drawCircle(
         this.slider.getX() + this.animation, this.slider.getY() + this.slider.getHeight() / 2.0, this.collideCircle.getHeight() / 2.0, -7706881, 1.0
      );
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if ((this.slider.isInside(mouseX, mouseY) || this.collideCircle.isInside(mouseX, mouseY)) && button == 0) {
         this.dragging = true;
      }
   }

   @Override
   public void onRelease(int mouseX, int mouseY, int button) {
      this.dragging = false;
   }

   private double roundToPlace(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal(value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }
}
