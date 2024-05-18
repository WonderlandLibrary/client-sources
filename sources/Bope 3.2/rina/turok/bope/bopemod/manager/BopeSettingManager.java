package rina.turok.bope.bopemod.manager;

import java.util.ArrayList;
import java.util.Iterator;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeSettingManager {
   private String tag;
   public ArrayList array_setting;

   public BopeSettingManager(String tag) {
      this.tag = tag;
      this.array_setting = new ArrayList();
   }

   public void register(String tag, BopeSetting setting) {
      this.array_setting.add(setting);
   }

   public ArrayList get_array_settings() {
      return this.array_setting;
   }

   public BopeSetting get_setting_with_tag(BopeModule module, String tag) {
      BopeSetting setting_requested = null;
      Iterator var4 = this.get_array_settings().iterator();

      while(var4.hasNext()) {
         BopeSetting settings = (BopeSetting)var4.next();
         if (settings.get_master().equals(module) && settings.get_tag().equalsIgnoreCase(tag)) {
            setting_requested = settings;
         }
      }

      return setting_requested;
   }

   public BopeSetting get_setting_with_tag(String tag) {
      BopeSetting setting_requested = null;
      Iterator var3 = this.get_array_settings().iterator();

      while(var3.hasNext()) {
         BopeSetting settings = (BopeSetting)var3.next();
         if (settings.get_tag().equalsIgnoreCase(tag)) {
            setting_requested = settings;
            break;
         }
      }

      return setting_requested;
   }

   public BopeSetting get_setting_with_tag(String tag, String tag_) {
      BopeSetting setting_requested = null;
      Iterator var4 = this.get_array_settings().iterator();

      while(var4.hasNext()) {
         BopeSetting settings = (BopeSetting)var4.next();
         if (settings.get_master().get_tag().equalsIgnoreCase(tag) && settings.get_tag().equalsIgnoreCase(tag_)) {
            setting_requested = settings;
            break;
         }
      }

      return setting_requested;
   }

   public ArrayList get_settings_with_module(BopeModule module) {
      ArrayList setting_requesteds = new ArrayList();
      Iterator var3 = this.get_array_settings().iterator();

      while(var3.hasNext()) {
         BopeSetting settings = (BopeSetting)var3.next();
         if (settings.get_master().equals(module)) {
            setting_requesteds.add(settings);
         }
      }

      return setting_requesteds;
   }
}
