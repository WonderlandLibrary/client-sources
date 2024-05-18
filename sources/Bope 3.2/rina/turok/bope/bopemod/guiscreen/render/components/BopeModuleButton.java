package rina.turok.bope.bopemod.guiscreen.render.components;

import java.util.ArrayList;
import java.util.Iterator;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.render.components.widgets.BopeButton;
import rina.turok.bope.bopemod.guiscreen.render.components.widgets.BopeButtonBind;
import rina.turok.bope.bopemod.guiscreen.render.components.widgets.BopeCombobox;
import rina.turok.bope.bopemod.guiscreen.render.components.widgets.BopeLabel;
import rina.turok.bope.bopemod.guiscreen.render.components.widgets.BopeSlider;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeModuleButton {
   private BopeModule module;
   public BopeFrame master;
   private ArrayList widget;
   private String module_name;
   private boolean opened;
   private boolean smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
   public boolean can = true;
   private int x;
   private int y;
   private int width;
   private int height;
   private int opened_height;
   private int save_y;
   private BopeDraw font = new BopeDraw(1.0F);
   private int border_a = 200;
   private int border_size = 1;
   private int master_height_cache;
   public int settings_height;
   private int count;

   public BopeModuleButton(BopeModule module, BopeFrame master) {
      this.module = module;
      this.master = master;
      this.widget = new ArrayList();
      this.module_name = module.get_name();
      this.x = 0;
      this.y = 0;
      this.width = this.master.get_width();
      this.height = this.font.get_string_height(module.get_name(), this.smoth) + 2;
      this.opened_height = this.height;
      this.save_y = 0;
      this.opened = false;
      this.master_height_cache = master.get_height();
      this.settings_height = this.y + 10;
      this.count = 0;
      Iterator var3 = Bope.get_setting_manager().get_settings_with_module(module).iterator();

      while(true) {
         BopeSetting settings;
         do {
            if (!var3.hasNext()) {
               int size = Bope.get_setting_manager().get_settings_with_module(module).size();
               if (this.count >= size) {
                  this.widget.add(new BopeButtonBind(master, this, "bind", this.settings_height));
                  this.settings_height += 10;
               }

               return;
            }

            settings = (BopeSetting)var3.next();
            if (settings.get_type().equals("button")) {
               this.widget.add(new BopeButton(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }

            if (settings.get_type().equals("combobox")) {
               this.widget.add(new BopeCombobox(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }

            if (settings.get_type().equals("label")) {
               this.widget.add(new BopeLabel(master, this, settings.get_tag(), this.settings_height));
               this.settings_height += 10;
               ++this.count;
            }
         } while(!settings.get_type().equals("doubleslider") && !settings.get_type().equals("integerslider"));

         this.widget.add(new BopeSlider(master, this, settings.get_tag(), this.settings_height));
         this.settings_height += 10;
         ++this.count;
      }
   }

   public BopeModule get_module() {
      return this.module;
   }

   public BopeFrame get_master() {
      return this.master;
   }

   public void event_can(boolean event) {
      this.can = event;
      Iterator var2 = this.widget.iterator();

      while(var2.hasNext()) {
         BopeAbstractWidget wdigets = (BopeAbstractWidget)var2.next();
         wdigets.does_can(event);
      }

   }

   public void set_pressed(boolean value) {
      this.module.set_active(value);
   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public void set_x(int x) {
      this.x = x;
   }

   public void set_y(int y) {
      this.y = y;
   }

   public void set_open(boolean value) {
      this.opened = value;
   }

   public boolean get_state() {
      return this.module.is_active();
   }

   public int get_settings_height() {
      return this.settings_height;
   }

   public int get_cache_height() {
      return this.master_height_cache;
   }

   public int get_width() {
      return this.width;
   }

   public int get_height() {
      return this.height;
   }

   public int get_x() {
      return this.x;
   }

   public int get_y() {
      return this.y;
   }

   public int get_save_y() {
      return this.save_y;
   }

   public boolean is_open() {
      return this.opened;
   }

   public boolean is_binding() {
      boolean value_requested = false;
      Iterator var2 = this.widget.iterator();

      while(var2.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var2.next();
         if (widgets.is_binding()) {
            value_requested = true;
         }
      }

      return value_requested;
   }

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
   }

   public boolean motion(int mx, int my, String s) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.opened_height;
   }

   public void does_widgets_can(boolean can) {
      Iterator var2 = this.widget.iterator();

      while(var2.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var2.next();
         widgets.does_can(can);
      }

   }

   public void bind(char char_, int key) {
      Iterator var3 = this.widget.iterator();

      while(var3.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var3.next();
         widgets.bind(char_, key);
         widgets.input(char_, key);
      }

   }

   public void mouse(int mx, int my, int mouse) {
      Iterator var4 = this.widget.iterator();

      while(var4.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var4.next();
         widgets.mouse(mx, my, mouse);
      }

      if (mouse == 0 && this.motion(mx, my) && this.can) {
         this.master.does_can(false);
         this.set_pressed(!this.get_state());
      }

      if (mouse == 1 && this.motion(mx, my) && this.can) {
         this.master.does_can(false);
         this.set_open(!this.is_open());
         this.master.refresh_frame(this, 0);
      }

   }

   public void button_release(int mx, int my, int mouse) {
      Iterator var4 = this.widget.iterator();

      while(var4.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var4.next();
         widgets.release(mx, my, mouse);
      }

      this.master.does_can(true);
   }

   public void render(int mx, int my, int separate) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
      this.set_width(this.master.get_width() - separate);
      this.save_y = this.y + this.master.get_y() - 10;
      int nm_r = Bope.click_gui.theme_widget_name_r;
      int nm_g = Bope.click_gui.theme_widget_name_g;
      int nm_b = Bope.click_gui.theme_widget_name_b;
      int bg_r = Bope.click_gui.theme_widget_background_r;
      int bg_g = Bope.click_gui.theme_widget_background_g;
      int bg_b = Bope.click_gui.theme_widget_background_b;
      int bg_a = Bope.click_gui.theme_widget_background_a;
      int bd_r = Bope.click_gui.theme_widget_border_r;
      int bd_g = Bope.click_gui.theme_widget_border_g;
      int bd_b = Bope.click_gui.theme_widget_border_b;
      BopeDraw var10000;
      if (this.module.is_active()) {
         BopeDraw.draw_rect(this.x, this.save_y, this.x + this.width - separate, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
         var10000 = this.font;
         BopeDraw.draw_string(this.module_name, this.x + separate, this.save_y, nm_r, nm_g, nm_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      } else {
         var10000 = this.font;
         BopeDraw.draw_string(this.module_name, this.x + separate, this.save_y, nm_r, nm_g, nm_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      }

      Iterator var14 = this.widget.iterator();

      while(var14.hasNext()) {
         BopeAbstractWidget widgets = (BopeAbstractWidget)var14.next();
         widgets.set_x(this.get_x());
         boolean is_passing_in_widget = this.opened ? widgets.motion_pass(mx, my) : false;
         if (this.motion(mx, my, "opened_height") || is_passing_in_widget && this.can) {
            BopeDraw.draw_rect(this.master.get_x() - 1, this.save_y, this.master.get_width() + separate, this.opened_height, bd_r, bd_g, bd_b, this.border_a, this.border_size, "right-left");
            var10000 = this.font;
            BopeDraw.draw_string(this.module.get_description(), 2, 1, nm_r, nm_g, nm_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
         }

         if (this.opened) {
            this.opened_height = this.height + this.settings_height - 10;
            widgets.render(this.get_save_y(), separate, mx, my);
         } else {
            this.opened_height = this.height;
         }
      }

   }
}
