package net.augustus.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;

public class StringValue extends Setting {
   @Expose
   @SerializedName("SelectedOption")
   private String string;
   private String[] options;

   public StringValue(int id, String name, Module parent, String value, String[] options) {
      super(id, name, parent);
      this.string = value;
      this.options = options;
      sm.newSetting(this);
   }

   public String[] getStringList() {
      return this.options;
   }

   public void setStringList(String[] options) {
      this.options = options;
   }

   public String getSelected() {
      return this.string;
   }

   public void setString(String string) {
      this.string = string;
   }

   @Override
   public void readSetting(SettingPart setting) {
      super.readSetting(setting);
      this.setString(setting.getString());
   }

   @Override
   public void readConfigSetting(SettingPart setting) {
      this.setString(setting.getString());
   }
}
