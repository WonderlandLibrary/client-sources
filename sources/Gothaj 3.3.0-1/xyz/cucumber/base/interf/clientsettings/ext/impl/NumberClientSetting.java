package xyz.cucumber.base.interf.clientsettings.ext.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class NumberClientSetting extends ClientSetting {
   private NumberSettings setting;
   private boolean dragging;
   private double animation;
   private double anim;
   private PositionUtils slider = new PositionUtils(0.0, 0.0, 136.0, 3.0, 1.0F);
   private PositionUtils point = new PositionUtils(0.0, 0.0, 6.0, 6.0, 1.0F);

   public NumberClientSetting(ModuleSettings setting) {
      this.setting = (NumberSettings)setting;
      this.position.setHeight(20.0);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      double diff = Math.min(this.slider.getWidth(), Math.max(0.0, (double)mouseX - this.slider.getX()));
      this.slider.setX(this.position.getX() + 7.0);
      this.slider.setY(this.position.getY2() - 8.0);
      if (this.dragging) {
         double newValue = this.roundToPlace(
            diff / this.slider.getWidth() * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), (int)this.slider.getWidth()
         );
         this.setting.setValue((double)((float)newValue));
      }

      Fonts.getFont("rb-m").drawString(this.setting.getName(), this.position.getX() + 8.0, this.position.getY() + 4.0, -1);
      Fonts.getFont("rb-r")
         .drawString(
            String.valueOf(this.setting.getValue()),
            this.position.getX2() - 8.0 - Fonts.getFont("rb-r").getWidth(String.valueOf(this.setting.getValue())),
            this.position.getY() + 4.0,
            -1
         );
      double x = this.slider.getWidth() * (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      this.anim = (this.anim * 9.0 + x) / 10.0;
      RenderUtils.drawRoundedRect(this.slider.getX(), this.slider.getY(), this.slider.getX2(), this.slider.getY2(), -1, 1.0F);
      RenderUtils.drawRoundedRect(this.slider.getX(), this.slider.getY(), this.slider.getX() + this.anim, this.slider.getY2(), -7303024, 1.0F);
      if (this.dragging) {
         this.animation = (this.animation * 9.0 + 3.5) / 10.0;
      } else {
         this.animation = (this.animation * 9.0 + 0.2) / 10.0;
      }

      this.point.setX(this.position.getX() + 7.0 + (this.anim - 1.5));
      this.point.setY(this.position.getY2() - 8.0);
      RenderUtils.drawCircle(this.point.getX() + 1.5, this.point.getY() + 1.5, this.animation, 637534207, 2.0);
      RenderUtils.drawCircle(this.point.getX() + 1.5, this.point.getY() + 1.5, this.animation + 1.0, 637534207, 2.0);
      RenderUtils.drawCircle(this.point.getX() + 1.5, this.point.getY() + 1.5, this.animation + 2.0, 637534207, 2.0);
      RenderUtils.drawCircle(this.point.getX() + 1.5, this.point.getY() + 1.5, 3.5, -5592406, 2.0);
      RenderUtils.drawCircle(this.point.getX() + 1.5, this.point.getY() + 1.5, 3.0, -1, 2.0);
   }

   @Override
   public void onClick(int mouseX, int mouseY, int button) {
      if ((this.slider.isInside(mouseX, mouseY) || this.point.isInside(mouseX, mouseY)) && button == 0) {
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
