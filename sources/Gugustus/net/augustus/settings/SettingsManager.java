package net.augustus.settings;

import java.util.ArrayList;
import net.augustus.Augustus;
import net.augustus.modules.Module;

public class SettingsManager {
   private final ArrayList<Setting> settings = new ArrayList<>();

   public void newSetting(Setting setting) {
      this.settings.add(setting);
   }

   public ArrayList<Setting> getSettingsByMod(Module mod) {
      ArrayList<Setting> sets = new ArrayList<>();
      for(Setting setting : settings) {
         if(setting.getParent().equals(mod)) {
            sets.add(setting);
         }
      }
      return sets;
   }
   public ArrayList<Setting> getSettingByName(String name) {
      for(Setting setting : settings) {
         if(setting.getName().equalsIgnoreCase(name)) {
            return settings;
         }
      }
      return null;
   }

   public ArrayList<Setting> getStgs() {
      return this.settings;
   }

   public Setting getFromID(int id) {
      for(Setting setting : this.getStgs()) {
         if (setting.getId() == id) {
            return setting;
         }
      }

      System.err.println("[" + Augustus.getInstance().getName() + "] ERROR Setting not found: '" + id + "'!");
      return null;
   }
}
