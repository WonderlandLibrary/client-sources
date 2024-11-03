package net.augustus.settings;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;

public class BooleansSetting extends Setting {
   @SerializedName("Settings")
   private Setting[] settings;

   public BooleansSetting(int id, String name, Module parent, Setting[] settings) {
      super(id, name, parent);
      this.settings = settings;
      Arrays.stream(settings).forEach(setting -> setting.setVisible(false));
      sm.newSetting(this);
   }

   public Setting[] getSettingList() {
      return this.settings;
   }

   public void setSettingList(Setting[] settings) {
      this.settings = settings;
   }

   @Override
   public void readSetting(SettingPart setting) {
      super.readSetting(setting);
   }
}
