package cc.slack.features.modules.api;

import cc.slack.Slack;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.utils.EventUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Module {
   private final List<Value> setting = new ArrayList();
   final ModuleInfo moduleInfo = (ModuleInfo)this.getClass().getAnnotation(ModuleInfo.class);
   private final String name;
   private final String displayName;
   private final Category category;
   private int key;
   private boolean toggle;

   public Module() {
      this.name = this.moduleInfo.name();
      this.displayName = this.moduleInfo.displayName().isEmpty() ? this.moduleInfo.name() : this.moduleInfo.displayName();
      this.category = this.moduleInfo.category();
      this.key = this.moduleInfo.key();
   }

   public void onToggled() {
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void toggle() {
      this.setToggle(!this.toggle);
   }

   public void setToggle(boolean toggle) {
      if (this.toggle != toggle) {
         this.toggle = toggle;
         if (toggle) {
            EventUtil.register(this);
            this.onEnable();
            Slack.getInstance().addNotification("Enabled module: " + this.name + ".", " ", 2000L);
         } else {
            EventUtil.unRegister(this);
            this.onDisable();
            Slack.getInstance().addNotification("Disabled module: " + this.name + ".", " ", 2000L);
         }

         this.onToggled();
      }
   }

   public Value getValueByName(String n) {
      Iterator var2 = this.setting.iterator();

      Value m;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         m = (Value)var2.next();
      } while(!m.getName().equals(n));

      return m;
   }

   public void addSettings(Value... settings) {
      this.setting.addAll(Arrays.asList(settings));
   }

   public List<Value> getSetting() {
      return this.setting;
   }

   public ModuleInfo getModuleInfo() {
      return this.moduleInfo;
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public Category getCategory() {
      return this.category;
   }

   public int getKey() {
      return this.key;
   }

   public boolean isToggle() {
      return this.toggle;
   }

   public void setKey(int key) {
      this.key = key;
   }
}
