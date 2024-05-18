package rina.turok.bope.bopemod.guiscreen.render.components.widgets;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeAbstractWidget;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeModuleButton;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.turok.values.TurokDouble;

public class BopeSlider extends BopeAbstractWidget {
   private BopeFrame frame;
   private BopeModuleButton master;
   private BopeSetting setting;
   private String slider_name;
   private double double_;
   private int intenger;
   private int x;
   private int y;
   private int width;
   private int height;
   private int save_y;
   private boolean can;
   private boolean compare;
   private boolean click;
   private boolean smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
   private BopeDraw font = new BopeDraw(1.0F);
   private int border_size = 0;

   public BopeSlider(BopeFrame frame, BopeModuleButton master, String tag, int update_postion) {
      this.frame = frame;
      this.master = master;
      this.setting = Bope.get_setting_manager().get_setting_with_tag(master.get_module(), tag);
      this.x = master.get_x();
      this.y = update_postion;
      this.save_y = this.y;
      this.width = master.get_width();
      this.height = this.font.get_string_height(this.setting.get_name(), this.smoth) + 2;
      this.slider_name = this.setting.get_name();
      this.can = true;
      this.double_ = 8192.0D;
      this.intenger = 8192;
      if (this.setting.get_type().equals("doubleslider")) {
         this.double_ = this.setting.get_value(1.0D);
      } else if (this.setting.get_type().equals("integerslider")) {
         this.intenger = this.setting.get_value(1);
      }

   }

   public BopeSetting get_setting() {
      return this.setting;
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

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_save_y() && mx <= this.get_x() + this.get_width() && my <= this.get_save_y() + this.get_height();
   }

   public boolean can() {
      return this.can;
   }

   public void mouse(int mx, int my, int mouse) {
      if (mouse == 0 && this.motion(mx, my) && this.master.is_open() && this.can()) {
         this.frame.does_can(false);
         this.click = true;
      }

   }

   public void release(int mx, int my, int mouse) {
      this.click = false;
   }

   public void render(int master_y, int separate, int absolute_x, int absolute_y) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
      this.set_width(this.master.get_width() - separate);
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
      if (this.double_ != 8192.0D && this.intenger == 8192) {
         this.compare = false;
      }

      if (this.double_ == 8192.0D && this.intenger != 8192) {
         this.compare = true;
      }

      double mouse = (double)Math.min(this.width, Math.max(0, absolute_x - this.get_x()));
      if (this.click) {
         if (mouse != 0.0D) {
            this.setting.set_value(TurokDouble.round(mouse / (double)this.width * (this.setting.get_max(1.0D) - this.setting.get_min(1.0D)) + this.setting.get_min(1.0D)));
         } else {
            this.setting.set_value(this.setting.get_min(1.0D));
         }
      }

      String slider_value = !this.compare ? Double.toString(this.setting.get_value(this.double_)) : Integer.toString(this.setting.get_value(this.intenger));
      BopeDraw.draw_rect(this.x, this.save_y, this.x + this.width * (this.setting.get_value(1) - this.setting.get_min(1)) / (this.setting.get_max(1) - this.setting.get_min(1)), this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
      BopeDraw.draw_string(this.slider_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      BopeDraw.draw_string(slider_value, this.x + this.width - separate - this.font.get_string_width(slider_value, this.smoth) - 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
   }
}
