package xyz.cucumber.base.module;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.feat.other.NotificationsModule;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.position.PositionUtils;

public class Mod {
   private String name = this.getClass().getAnnotation(ModuleInfo.class).name();
   private String description = this.getClass().getAnnotation(ModuleInfo.class).description();
   private int key = this.getClass().getAnnotation(ModuleInfo.class).key();
   private Category category = this.getClass().getAnnotation(ModuleInfo.class).category();
   private ArrayPriority priority = this.getClass().getAnnotation(ModuleInfo.class).priority();
   public boolean enabled;
   public Minecraft mc = Minecraft.getMinecraft();
   private ArrayList<ModuleSettings> settings = new ArrayList<>();
   public String info = "";

   public Mod() {
   }

   public Mod(String name, String description, Category category) {
      this.category = category;
      this.description = description;
      this.name = name;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onSettingChange() {
   }

   public void toggle() {
      NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
      this.enabled = !this.enabled;
      if (this.enabled) {
         if (mod.isEnabled()) {
            mod.notifications
               .add(
                  new NotificationsModule.Notification(
                     this.name, "Module was enabled!", NotificationsModule.Type.ENABLED, new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                  )
               );
         }

         Client.INSTANCE.getEventBus().register(this);

         try {
            this.onEnable();
         } catch (Exception var4) {
         }
      } else {
         if (mod.isEnabled()) {
            mod.notifications
               .add(
                  new NotificationsModule.Notification(
                     this.name, "Module was disabled!", NotificationsModule.Type.DISABLED, new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                  )
               );
         }

         Client.INSTANCE.getEventBus().unregister(this);

         try {
            this.onDisable();
         } catch (Exception var3) {
         }
      }
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public ArrayList<ModuleSettings> getSettings() {
      return this.settings;
   }

   public void addSettings(ModuleSettings... moduleSettings) {
      this.settings.addAll(Arrays.asList(moduleSettings));
   }

   public String getName() {
      return this.name;
   }

   public String getName(boolean split) {
      return split ? this.name : this.name.replace(" ", "");
   }

   public PositionUtils getPosition() {
      return null;
   }

   public void setXYPosition(double x, double y) {
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
   }

   public Category getCategory() {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public ArrayPriority getPriority() {
      return this.priority;
   }

   public void setPriority(ArrayPriority priority) {
      this.priority = priority;
   }

   public boolean isEnabled() {
      return this.enabled;
   }
}
