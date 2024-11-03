package xyz.cucumber.base.interf.clickgui.content.ex;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.BooleanButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.ColorButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.ModeButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.ModuleToggleButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.NumberButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.SettingsButton;
import xyz.cucumber.base.interf.clickgui.content.ex.impl.StringButton;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.module.settings.StringSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.position.PositionUtils;

public class ModuleButton {
   private Mod module;
   private PositionUtils position;
   private List<SettingsButton> settings = new ArrayList<>();
   private boolean open = false;
   private double rollAnimation = this.getSettingsHeight();

   public ModuleButton(Mod module, PositionUtils position) {
      this.module = module;
      this.position = position;
      this.settings.add(new ModuleToggleButton(module, new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F)));

      for (ModuleSettings setting : module.getSettings()) {
         if (setting instanceof ModeSettings) {
            this.settings.add(new ModeButton((ModeSettings)setting, new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F)));
         }

         if (setting instanceof BooleanSettings) {
            this.settings.add(new BooleanButton((BooleanSettings)setting, new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F)));
         }

         if (setting instanceof StringSettings) {
            this.settings.add(new StringButton((StringSettings)setting, new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F)));
         }

         if (setting instanceof ColorSettings) {
            this.settings
               .add(
                  new ColorButton(
                     (ColorSettings)setting,
                     new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F),
                     new PositionUtils(0.0, 0.0, 65.0, 40.0, 1.0F),
                     new PositionUtils(0.0, 0.0, 65.0, 40.0, 1.0F),
                     new PositionUtils(0.0, 0.0, 135.0, 4.0, 1.0F),
                     new PositionUtils(0.0, 0.0, 80.0, 15.0, 1.0F)
                  )
               );
         }

         if (setting instanceof NumberSettings) {
            this.settings.add(new NumberButton((NumberSettings)setting, new PositionUtils(0.0, 0.0, position.getWidth(), 15.0, 1.0F)));
         }
      }
   }

   public void draw(int mouseX, int mouseY) {
      this.rollAnimation = (this.rollAnimation * 12.0 + this.getSettingsHeight()) / 13.0;
      this.position.setHeight(this.rollAnimation);
      RenderUtils.enableScisor();
      RenderUtils.scissor(
         new ScaledResolution(Minecraft.getMinecraft()), this.position.getX(), this.position.getY(), this.position.getWidth(), this.position.getHeight()
      );
      RenderUtils.drawRoundedRectWithCorners(
         this.position.getX(), this.position.getY(), this.position.getX2(), this.position.getY2(), -1876942816, 4.0, true, true, true, true
      );
      double h = 0.0;

      for (SettingsButton s : this.getVisibleSettings()) {
         if (s.getSettingMain() == null || s.getSettingMain().getVisibility().get()) {
            s.getPosition().setX(this.position.getX());
            s.getPosition().setY(this.position.getY() + h);
            s.draw(mouseX, mouseY);
            h += s.getPosition().getHeight();
         }
      }

      RenderUtils.disableScisor();
   }

   public double getSettingsHeight() {
      double h = 0.0;

      for (SettingsButton s : this.settings) {
         if ((this.open || s instanceof ModuleToggleButton) && (s.getSettingMain() == null || s.getSettingMain().getVisibility().get())) {
            h += s.getPosition().getHeight();
         }
      }

      return h;
   }

   public List<SettingsButton> getVisibleSettings() {
      List<SettingsButton> set = new ArrayList<>();

      for (SettingsButton s : this.settings) {
         if ((this.open || s instanceof ModuleToggleButton)
            && (s.getSettingMain() == null || s.getSettingMain().getVisibility().get())
            && (this.position.getY() <= s.getPosition().getY2() || this.position.getY2() >= s.getPosition().getY())) {
            set.add(s);
         }
      }

      return set;
   }

   public void clicked(int mouseX, int mouseY, int button) {
      if (this.position.isInside(mouseX, mouseY)) {
         for (SettingsButton s : this.getVisibleSettings()) {
            if (this.open || s instanceof ModuleToggleButton) {
               if (s instanceof ModuleToggleButton && s.getPosition().isInside(mouseX, mouseY) && button == 1) {
                  this.open = !this.open;
               }

               if (s.getSettingMain() == null || s.getSettingMain().getVisibility().get()) {
                  s.click(mouseX, mouseY, button);
               }
            }
         }
      }
   }

   public void released(int mouseX, int mouseY, int button) {
      for (SettingsButton s : this.getVisibleSettings()) {
         if ((this.open || s instanceof ModuleToggleButton) && (s.getSettingMain() == null || s.getSettingMain().getVisibility().get())) {
            s.release(mouseX, mouseY, button);
         }
      }
   }

   public void key(char typedChar, int keyCode) {
      for (SettingsButton s : this.getVisibleSettings()) {
         if ((this.open || s instanceof ModuleToggleButton) && (s.getSettingMain() == null || s.getSettingMain().getVisibility().get())) {
            s.key(typedChar, keyCode);
         }
      }
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }
}
