package hawk.modules;

import com.lukflug.panelstudio.settings.Toggleable;
import hawk.events.Event;
import hawk.settings.KeyBindSetting;
import hawk.settings.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public class Module implements Toggleable {
   public String displayname;
   public Minecraft mc = Minecraft.getMinecraft();
   public boolean expanded;
   public boolean toggled;
   public String name;
   public int index;
   public Module.Category category;
   public KeyBindSetting keyCode = new KeyBindSetting(0, this);
   public List<Setting> settings = new ArrayList();

   public void onDisable() {
   }

   public void addSettings(Setting... var1) {
      this.settings.addAll(Arrays.asList(var1));
   }

   public int getKey() {
      return this.keyCode.code;
   }

   public void onEvent(Event var1) {
   }

   public String getDisplayname() {
      return this.displayname;
   }

   public void setEnabled(boolean var1) {
      this.toggled = var1;
   }

   public void toggle() {
      this.toggled = !this.toggled;
      if (this.toggled) {
         this.onEnable();
      } else {
         this.onDisable();
      }

   }

   public Module(String var1, int var2, Module.Category var3) {
      this.name = var1;
      this.keyCode.code = var2;
      this.category = var3;
      this.addSettings(this.keyCode);
      this.setDisplayname(var1);
   }

   public boolean isEnabled() {
      return this.toggled;
   }

   public void setKey(int var1) {
      this.keyCode.code = var1;
   }

   public boolean isOn() {
      return this.toggled;
   }

   public void onEnable() {
   }

   public void setDisplayname(String var1) {
      this.displayname = var1;
   }

   public static enum Category {
      public int moduleIndex;
      COMBAT("Combat", 130),
      PLAYER("Player", 390),
      RENDER("Render", 520);

      public int pos;
      private static final Module.Category[] ENUM$VALUES = new Module.Category[]{COMBAT, MOVEMENT, PLAYER, RENDER};
      public String name;
      MOVEMENT("Movement", 260);

      private Category(String var3, int var4) {
         this.name = var3;
         this.pos = var4;
      }
   }
}
