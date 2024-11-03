package vestige.ui.click.dropdown.impl;

import java.util.ArrayList;
import java.util.Iterator;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.util.misc.TimerUtil;

public class ModuleHolder {
   private Module module;
   private final TimerUtil timer = new TimerUtil();
   private final TimerUtil settingsShownTimer = new TimerUtil();
   private boolean lastEnabled;
   private final ArrayList<SettingHolder> settings = new ArrayList();
   private boolean settingsShown;

   public ModuleHolder(Module m) {
      this.module = m;
      Iterator var2 = m.getSettings().iterator();

      while(var2.hasNext()) {
         AbstractSetting s = (AbstractSetting)var2.next();
         this.settings.add(new SettingHolder(s));
      }

   }

   public void updateState() {
      boolean enabled = this.module.isEnabled();
      if (enabled != this.lastEnabled) {
         this.timer.reset();
      }

      this.lastEnabled = this.module.isEnabled();
   }

   public void setSettingsShown(boolean shown) {
      if (this.settingsShown != shown) {
         this.settingsShown = shown;
         this.settingsShownTimer.reset();
      }

   }

   public Module getModule() {
      return this.module;
   }

   public TimerUtil getTimer() {
      return this.timer;
   }

   public TimerUtil getSettingsShownTimer() {
      return this.settingsShownTimer;
   }

   public boolean isLastEnabled() {
      return this.lastEnabled;
   }

   public ArrayList<SettingHolder> getSettings() {
      return this.settings;
   }

   public boolean isSettingsShown() {
      return this.settingsShown;
   }
}
