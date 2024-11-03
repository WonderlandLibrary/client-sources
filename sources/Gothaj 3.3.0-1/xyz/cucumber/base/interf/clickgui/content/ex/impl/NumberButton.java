package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NumberButton extends SettingsButton {
   private NumberSettings setting;
   private boolean dragging;
   private PositionUtils slider;

   public NumberButton(NumberSettings setting, PositionUtils modesPosition) {
      this.setting = setting;
      this.position = modesPosition;
      this.settingMain = setting;
      this.slider = new PositionUtils(0.0, 0.0, 120.0, 10.0, 1.0F);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      Fonts.getFont("rb-r")
         .drawString(this.setting.getName(), (double)((float)(this.position.getX() + 8.0)), (double)((float)(this.position.getY() + 3.0)), -1);
      this.slider.setX(this.position.getX2() - 10.0 - this.slider.getWidth());
      this.slider.setY(this.position.getY() + 2.5);
      double diff = Math.min(this.slider.getWidth(), Math.max(0.0, (double)mouseX - this.slider.getX()));
      if (this.dragging) {
         double newValue = this.roundToPlace(
            diff / this.slider.getWidth() * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), (int)this.slider.getWidth()
         );
         this.setting.setValue((double)((float)newValue));
      }

      RenderUtils.drawRect(this.slider.getX(), this.slider.getY(), this.slider.getX2(), this.slider.getY2(), 805306368);
      RenderUtils.drawOutlinedRect(this.slider.getX(), this.slider.getY(), this.slider.getX2(), this.slider.getY2(), -1874695691, 1.0F);
      double x = this.slider.getX()
         + 2.0
         + (this.slider.getWidth() - 4.0) * (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      RenderUtils.drawRect(x - 2.0, this.slider.getY(), x + 2.0, this.slider.getY2(), 1078094325);
      Fonts.getFont("rb-r")
         .drawString(
            String.valueOf(this.setting.getValue()),
            (double)((float)(this.slider.getX() + this.slider.getWidth() / 2.0 - Fonts.getFont("rb-r").getWidth(String.valueOf(this.setting.getValue())) / 2.0)),
            (double)((float)this.slider.getY() + 2.5F),
            -12424715
         );
   }

   @Override
   public void click(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY) && this.slider.isInside(mouseX, mouseY) && button == 0) {
         this.dragging = true;
      }
   }

   @Override
   public void release(int mouseX, int mouseY, int button) {
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
