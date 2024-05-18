package rina.turok.bope.bopemod.guiscreen.render.components.widgets;

import net.minecraft.client.Minecraft;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeAbstractWidget;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeModuleButton;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeCombobox extends BopeAbstractWidget {
   public BopeFrame frame;
   private BopeModuleButton master;
   private BopeSetting setting;
   private BopeMenu menu;
   private String combobox_name;
   private int x;
   private int y;
   private int menu_x;
   private int width;
   private int height;
   private int save_y;
   private int border_size = 0;
   private int side = 0;
   private boolean can;
   private boolean smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
   private boolean opened = false;
   private BopeDraw font = new BopeDraw(1.0F);
   public final Minecraft mc = Minecraft.getMinecraft();

   public BopeCombobox(BopeFrame frame, BopeModuleButton master, String tag, int update_postion) {
      this.frame = frame;
      this.master = master;
      this.setting = Bope.get_setting_manager().get_setting_with_tag(master.get_module(), tag);
      this.menu = new BopeMenu(this, this.setting.get_values());
      this.x = master.get_x();
      this.y = update_postion;
      this.menu_x = 0;
      this.save_y = this.y;
      this.width = master.get_width();
      this.height = this.font.get_string_height(this.setting.get_name(), this.smoth) + 2;
      this.combobox_name = this.setting.get_name();
      this.can = true;
   }

   public BopeSetting get_setting() {
      return this.setting;
   }

   public void set_open(boolean value) {
      this.opened = value;
   }

   public void does_can(boolean value) {
      this.can = value;
   }

   public void set_x(int x) {
      this.x = x;
   }

   public void set_y(int y) {
      this.y = y;
   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public int get_x() {
      return this.x;
   }

   public int get_y() {
      return this.y;
   }

   public int get_width() {
      return this.width;
   }

   public int get_height() {
      return this.height;
   }

   public int get_save_y() {
      return this.save_y;
   }

   public boolean motion_pass(int mx, int my) {
      return this.motion(mx, my);
   }

   public boolean is_open() {
      return this.opened;
   }

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
   }

   public boolean can() {
      return this.can;
   }

   public void mouse(int mx, int my, int mouse) {
      this.menu.click(mx, my, mouse);
      if (mouse == 0) {
         if (this.motion(mx, my) && this.master.is_open() && this.can()) {
            this.frame.does_can(false);
            this.set_open(true);
            this.menu.set_open(true);
         } else if (this.motion(mx, my) && this.master.is_open() && this.is_open()) {
            this.frame.does_can(false);
            this.set_open(false);
            this.menu.set_open(false);
         } else if (!this.motion(mx, my) && !this.menu.motion(mx, my) && this.master.is_open() && this.is_open()) {
            this.frame.does_can(false);
            this.set_open(false);
            this.menu.set_open(false);
         }
      } else if (mouse == 1 && this.motion(mx, my) && this.master.is_open() && this.is_open()) {
         this.frame.does_can(false);
         this.set_open(false);
         this.menu.set_open(false);
      }

   }

   public void render(int master_y, int separate, int mx, int my) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
      this.set_width(this.master.get_width() - separate);
      String zbob = "rina";
      this.save_y = this.y + master_y;
      int ns_r = Bope.click_gui.theme_widget_name_r;
      int ns_g = Bope.click_gui.theme_widget_name_g;
      int ns_b = Bope.click_gui.theme_widget_name_b;
      int bg_r = Bope.click_gui.theme_widget_background_r;
      int bg_g = Bope.click_gui.theme_widget_background_g;
      int bg_b = Bope.click_gui.theme_widget_background_b;
      int bg_a = Bope.click_gui.theme_widget_background_a;
      int bd_r = Bope.click_gui.theme_widget_border_r;
      int bd_g = Bope.click_gui.theme_widget_border_g;
      int bd_b = Bope.click_gui.theme_widget_border_b;
      if (this.is_open()) {
         BopeDraw.draw_rect(this.x, this.save_y, this.x + this.width, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
         BopeDraw.draw_string(this.combobox_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      } else {
         BopeDraw.draw_string(this.combobox_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      }

      this.menu.render(this.motion(mx, my), this.save_y, mx, my);
   }
}
