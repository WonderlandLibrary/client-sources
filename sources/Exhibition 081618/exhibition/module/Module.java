package exhibition.module;

import com.google.gson.annotations.Expose;
import exhibition.event.EventListener;
import exhibition.event.EventSystem;
import exhibition.management.Saveable;
import exhibition.management.animate.Translate;
import exhibition.management.keybinding.Bindable;
import exhibition.management.keybinding.KeyHandler;
import exhibition.management.keybinding.Keybind;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.util.MinecraftUtil;
import java.util.Iterator;

public abstract class Module extends Saveable implements EventListener, Bindable, Toggleable, MinecraftUtil {
   @Expose
   protected final ModuleData data;
   @Expose
   protected final SettingsMap settings = new SettingsMap();
   private String suffix;
   private boolean hidden;
   private Keybind keybind;
   private boolean enabled;
   public Translate translate = new Translate(0.0F, 0.0F);

   public Module(ModuleData data) {
      this.data = data;
      this.setKeybind(new Keybind(this, data.key, data.mask));
   }

   public static Setting getSetting(SettingsMap map, String settingText) {
      settingText = settingText.toUpperCase();
      if (map.containsKey(settingText)) {
         return (Setting)map.get(settingText);
      } else {
         Iterator var2 = map.keySet().iterator();

         String key;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            key = (String)var2.next();
         } while(!key.startsWith(settingText));

         return (Setting)map.get(key);
      }
   }

   public void toggle() {
      this.enabled = !this.enabled;
      ModuleManager.saveStatus();
      ModuleManager.saveSettings();
      if (this.enabled) {
         EventSystem.register(this);
         this.onEnable();
      } else {
         EventSystem.unregister(this);
         this.onDisable();
      }

   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onBindPress() {
      this.toggle();
   }

   public void onBindRelease() {
   }

   public void setKeybind(Keybind newBind) {
      if (newBind != null) {
         if (this.keybind == null) {
            this.keybind = newBind;
            KeyHandler.register(this.keybind);
         } else {
            boolean sameKey = newBind.getKeyInt() == this.keybind.getKeyInt();
            boolean sameMask = newBind.getMask() == this.keybind.getMask();
            if (sameKey && !sameMask) {
               KeyHandler.update(this, this.keybind, newBind);
            } else if (!sameKey) {
               if (KeyHandler.keyHasBinds(this.keybind.getKeyInt())) {
                  KeyHandler.unregister(this, this.keybind);
               }

               KeyHandler.register(newBind);
            }

            this.keybind.update(newBind);
            this.data.key = this.keybind.getKeyInt();
            this.data.mask = this.keybind.getMask();
         }
      }
   }

   public Keybind getKeybind() {
      return this.keybind;
   }

   public boolean addSetting(String key, Setting setting) {
      if (this.settings.containsKey(key)) {
         return false;
      } else {
         this.settings.put(key, setting);
         return true;
      }
   }

   public Setting getSetting(String key) {
      return (Setting)this.settings.get(key);
   }

   public String getSuffix() {
      return this.suffix;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   public SettingsMap getSettings() {
      return this.settings;
   }

   public String getName() {
      return this.data.name;
   }

   public String getDescription() {
      return this.data.description;
   }

   public ModuleData.Type getType() {
      return this.data.type;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean hidden) {
      this.hidden = hidden;
   }
}
