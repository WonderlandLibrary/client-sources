package net.augustus.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.SettingPart;
import net.augustus.utils.interfaces.SM;

public abstract class Setting implements SM {
   @Expose
   @SerializedName("Name")
   private String name;
   @Expose
   @SerializedName("ParentName")
   private String parentName;
   private Module parent;
   private boolean visible;
   @Expose
   private int id;

   public Setting(int id, String name, Module parent) {
      this.name = name;
      this.parent = parent;
      this.parentName = parent.getName();
      this.id = id;
      this.visible = true;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Module getParent() {
      return this.parent;
   }

   public void setParent(Module parent) {
      this.parent = parent;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getParentName() {
      return this.parentName;
   }

   public void setParentName(String parentName) {
      this.parentName = parentName;
   }

   public void readSetting(SettingPart setting) {
      this.setId(setting.getId());
      this.setName(setting.getName());
   }

   public void readConfigSetting(SettingPart setting) {
   }
}
