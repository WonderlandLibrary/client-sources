package rina.turok.bope.bopemod;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeModule {
   public BopeCategory category;
   public String name = "";
   public String tag = "";
   public String description = "";
   public String detail_option;
   public int bind = -1;
   public boolean state_module;
   public boolean alert_message = true;
   public boolean widget_usage = false;
   public boolean show_arraylist;
   public boolean stage_primitive;
   public final Minecraft mc = Minecraft.getMinecraft();

   public BopeModule(BopeCategory category) {
      this.show_arraylist = true;
      this.stage_primitive = false;
      this.category = category;
   }

   public BopeModule(BopeCategory category, boolean to_show) {
      this.show_arraylist = to_show;
      this.stage_primitive = false;
      this.category = category;
   }

   public void release(String tag) {
   }

   public void set_bind(int key) {
      this.bind = key;
   }

   public void set_bind(String key) {
      this.bind = Keyboard.getKeyIndex(key.toLowerCase());
   }

   public void alert(boolean value) {
      this.alert_message = value;
   }

   public void set_usage_widget(boolean value) {
      this.widget_usage = value;
   }

   public boolean to_show() {
      return this.show_arraylist;
   }

   public boolean is_active() {
      return this.state_module;
   }

   public boolean using_widget() {
      return this.widget_usage;
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public String get_description() {
      return this.description;
   }

   public int get_bind(int type) {
      return this.bind;
   }

   public String get_bind(String type) {
      String converted_bind = "null";
      if (this.get_bind(0) < 0) {
         converted_bind = "NONE";
      }

      if (!converted_bind.equals("NONE")) {
         String key = Keyboard.getKeyName(this.get_bind(0));
         converted_bind = Character.toUpperCase(key.charAt(0)) + (key.length() != 1 ? key.substring(1).toLowerCase() : "");
      } else {
         converted_bind = "None";
      }

      return converted_bind;
   }

   public BopeCategory get_category() {
      return this.category;
   }

   public boolean alert() {
      return this.alert_message;
   }

   public void set_disable() {
      this.state_module = false;
      this.stage_primitive = true;
      this.disable();
      Bope.ZERO_ALPINE_EVENT_BUS.unsubscribe((Object)this);
      this.stage_primitive = false;
   }

   public void set_enable() {
      this.state_module = true;
      this.stage_primitive = true;
      this.enable();
      Bope.ZERO_ALPINE_EVENT_BUS.subscribe((Object)this);
      this.stage_primitive = false;
   }

   public void set_active(boolean value) {
      if (this.state_module != value) {
         if (value) {
            this.set_enable();
         } else {
            this.set_disable();
         }
      }

      if (!this.tag.equals("GUI") && !this.tag.equals("HUD") && this.alert_message) {
         BopeMessage.alert_message(this);
      }

   }

   public void toggle() {
      this.set_active(!this.is_active());
   }

   protected BopeSetting create(String name, String tag, boolean value) {
      Bope.get_setting_manager().register(tag, new BopeSetting(this, name, tag, value));
      return Bope.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected BopeSetting create(String name, String tag, String value, List values) {
      Bope.get_setting_manager().register(tag, new BopeSetting(this, name, tag, values, value));
      return Bope.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected BopeSetting create(String name, String tag, String value) {
      Bope.get_setting_manager().register(tag, new BopeSetting(this, name, tag, value));
      return Bope.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected BopeSetting create(String name, String tag, double value, double min, double max) {
      Bope.get_setting_manager().register(tag, new BopeSetting(this, name, tag, value, min, max));
      return Bope.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected BopeSetting create(String name, String tag, int value, int min, int max) {
      Bope.get_setting_manager().register(tag, new BopeSetting(this, name, tag, value, min, max));
      return Bope.get_setting_manager().get_setting_with_tag(this, tag);
   }

   protected List combobox(String... item) {
      ArrayList item_requested = new ArrayList();
      String[] var3 = item;
      int var4 = item.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String items = var3[var5];
         item_requested.add(items);
      }

      return item_requested;
   }

   public void render(BopeEventRender event) {
   }

   public void render() {
   }

   public void update() {
   }

   public void event_widget() {
   }

   protected void disable() {
   }

   protected void enable() {
   }

   public String detail_option() {
      return null;
   }

   public String value_string_0() {
      return null;
   }

   public String value_string_1() {
      return null;
   }

   public boolean value_boolean_0() {
      return false;
   }

   public boolean value_boolean_1() {
      return false;
   }

   public double value_double() {
      return 0.0D;
   }

   public double value_double_0() {
      return 0.0D;
   }

   public double value_double_1() {
      return 0.0D;
   }

   public int value_int_0() {
      return 0;
   }

   public int value_int_1() {
      return 0;
   }
}
