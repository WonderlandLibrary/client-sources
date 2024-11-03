package xyz.cucumber.base.interf.clickgui.content.ex.impl;

import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.position.PositionUtils;

public class SettingsButton {
   PositionUtils position;
   ModuleSettings settingMain;
   boolean isVisible = true;

   public void draw(int mouseX, int mouseY) {
   }

   public void click(int mouseX, int mouseY, int button) {
   }

   public void release(int mouseX, int mouseY, int button) {
   }

   public void key(char character, int key) {
   }

   public PositionUtils getPosition() {
      return this.position;
   }

   public void setPosition(PositionUtils position) {
      this.position = position;
   }

   public boolean isVisible() {
      return this.isVisible;
   }

   public void setVisible(boolean isVisible) {
      this.isVisible = isVisible;
   }

   public ModuleSettings getSettingMain() {
      return this.settingMain;
   }

   public void setSettingMain(ModuleSettings settingMain) {
      this.settingMain = settingMain;
   }
}
