package net.augustus.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;

public class BooleanValue extends Setting {
   @Expose
   @SerializedName("Boolean")
   private boolean aBoolean;

   public BooleanValue(int id, String name, Module parent, boolean aBoolean) {
      super(id, name, parent);
      this.aBoolean = aBoolean;
      sm.newSetting(this);
   }

   public boolean getBoolean() {
      return this.aBoolean;
   }

   public void setBoolean(boolean aBoolean) {
      this.aBoolean = aBoolean;
   }

   @Override
   public void readSetting(SettingPart setting) {
      super.readSetting(setting);
      this.setBoolean(setting.isaBoolean());
   }

   @Override
   public void readConfigSetting(SettingPart setting) {
      super.readConfigSetting(setting);
      this.setBoolean(setting.isaBoolean());
   }
}
