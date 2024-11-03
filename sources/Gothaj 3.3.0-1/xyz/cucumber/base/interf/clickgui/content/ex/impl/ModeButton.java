package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import java.util.ArrayList;
import java.util.List;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ModeButton extends SettingsButton {
   private ModeSettings setting;
   private PositionUtils modesPosition;
   private double defaultHeight;
   private List<ModeButton.mode> modes;
   private boolean open;
   private double rotateAnimation;
   private double rollAnimation;

   public ModeButton(ModeSettings setting, PositionUtils modesPosition) {
      this.settingMain = setting;
      this.setting = setting;
      this.position = modesPosition;
      this.defaultHeight = this.position.getHeight();
      this.modes = new ArrayList<>();

      for (String s : setting.getModes()) {
         this.modes.add(new ModeButton.mode(s, new PositionUtils(0.0, 0.0, 80.0, 12.0, 1.0F)));
      }

      this.modesPosition = new PositionUtils(0.0, 0.0, 80.0, 12.0, 1.0F);
   }

   @Override
   public void draw(int mouseX, int mouseY) {
      this.rotateAnimation = this.rotateAnimation * 12.0 / 13.0;
      Fonts.getFont("rb-r").drawString(this.setting.getName(), this.position.getX() + 8.0, this.position.getY() + 4.0, -1);
      this.modesPosition.setX(this.position.getX2() - this.modesPosition.getWidth() - 10.0);
      this.modesPosition.setY(this.position.getY() + 1.0);
      RenderUtils.drawRect(this.modesPosition.getX(), this.modesPosition.getY(), this.modesPosition.getX2(), this.modesPosition.getY2(), 805306368);
      RenderUtils.drawArrowForClickGui(
         this.modesPosition.getX2() - 4.0, this.modesPosition.getY() + this.modesPosition.getHeight() / 2.0, 3.0, -12424715, this.rotateAnimation
      );
      Fonts.getFont("rb-r")
         .drawString(
            this.setting.getMode(),
            this.modesPosition.getX() + this.modesPosition.getWidth() / 2.0 - Fonts.getFont("rb-r").getWidth(this.setting.getMode()) / 2.0,
            this.modesPosition.getY() + this.modesPosition.getHeight() / 2.0 - (double)(Fonts.getFont("rb-r").getHeight(this.setting.getMode()) / 2.0F),
            -12424715
         );
      if (this.open) {
         this.rollAnimation = this.getModesHeight();
         double p = 0.0;

         for (ModeButton.mode m : this.modes) {
            m.pos.setX(this.modesPosition.getX());
            m.pos.setY(this.modesPosition.getY2() + p);
            m.draw(mouseX, mouseY);
            p += m.pos.getHeight();
         }

         this.position.setHeight(this.defaultHeight + this.rollAnimation);
      } else {
         this.rollAnimation = (this.rollAnimation * 11.0 + this.defaultHeight) / 12.0;
         this.rotateAnimation = (this.rotateAnimation * 12.0 + 180.0) / 13.0;
         this.position.setHeight(this.rollAnimation);
      }

      RenderUtils.drawOutlinedRect(
         this.modesPosition.getX(), this.modesPosition.getY(), this.modesPosition.getX2(), this.modesPosition.getY2(), -12424715, 1.0F
      );
   }

   public double getModesHeight() {
      double p = 0.0;

      for (ModeButton.mode m : this.modes) {
         if (!m.mode.toLowerCase().equals(this.setting.getMode().toLowerCase())) {
            p += m.pos.getHeight();
         }
      }

      return p;
   }

   @Override
   public void click(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         if (button == 0) {
            if (this.open) {
               for (ModeButton.mode m : this.modes) {
                  m.click(mouseX, mouseY, this.setting, button);
               }
            }
         } else if (button == 1 && this.modesPosition.isInside(mouseX, mouseY)) {
            this.open = !this.open;
         }
      }
   }

   public class mode {
      public String mode;
      public PositionUtils pos;

      public mode(String mode, PositionUtils pos) {
         this.mode = mode;
         this.pos = pos;
      }

      public void draw(int mouseX, int mouseY) {
         RenderUtils.drawRect(this.pos.getX(), this.pos.getY(), this.pos.getX2(), this.pos.getY2(), 805306368);
         Fonts.getFont("rb-r")
            .drawString(
               this.mode,
               this.pos.getX() + this.pos.getWidth() / 2.0 - Fonts.getFont("rb-r").getWidth(this.mode) / 2.0,
               this.pos.getY() + this.pos.getHeight() / 2.0 - (double)(Fonts.getFont("rb-r").getHeight(this.mode) / 2.0F),
               -1
            );
      }

      public void click(int mouseX, int mouseY, ModeSettings setting, int button) {
         if (this.pos.isInside(mouseX, mouseY) && button == 0) {
            setting.setMode(this.mode);
         }
      }
   }
}
