package me.jDev.xenza.files.parts;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import net.augustus.modules.Module;

public class ConfigPart {
   @Expose
   private String name;
   @Expose
   private String date;
   @Expose
   private String time;
   @Expose
   private ArrayList<Module> modules;
   @Expose
   private ArrayList<SettingPart> settings;

   public ConfigPart(String name, String date, String time, ArrayList<Module> moduleParts, ArrayList<SettingPart> settings) {
      this.name = name;
      this.date = date;
      this.time = time;
      this.modules = moduleParts;
      this.settings = settings;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDate() {
      return this.date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getTime() {
      return this.time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public ArrayList<Module> getModules() {
      return this.modules;
   }

   public void setModules(ArrayList<Module> modules) {
      this.modules = modules;
   }

   public ArrayList<SettingPart> getSettingParts() {
      return this.settings;
   }

   public void setSettingParts(ArrayList<SettingPart> settingParts) {
      this.settings = settingParts;
   }
}
