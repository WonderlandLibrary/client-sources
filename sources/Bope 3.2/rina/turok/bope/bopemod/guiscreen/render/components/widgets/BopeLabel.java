package rina.turok.bope.bopemod.guiscreen.render.components.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeAbstractWidget;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeModuleButton;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeLabel extends BopeAbstractWidget {
   private BopeFrame frame;
   private BopeModuleButton master;
   private BopeSetting setting;
   private String label_name;
   private GuiTextField entry;
   private int x;
   private int y;
   private int width;
   private int height;
   private int save_y;
   private boolean can;
   private boolean info;
   private boolean click;
   private boolean smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
   private BopeDraw font = new BopeDraw(1.0F);
   private int border_size = 0;
   public final Minecraft mc = Minecraft.getMinecraft();

   public BopeLabel(BopeFrame frame, BopeModuleButton master, String tag, int update_postion) {
      this.frame = frame;
      this.master = master;
      this.setting = Bope.get_setting_manager().get_setting_with_tag(master.get_module(), tag);
      this.x = master.get_x();
      this.y = update_postion;
      this.save_y = this.y;
      BopeDraw var10004 = this.font;
      this.entry = new GuiTextField(0, BopeDraw.custom_font, this.x, this.save_y, this.width, this.height);
      this.entry.writeText(this.setting.get_value("true"));
      this.entry.setCursorPosition(0);
      this.entry.setMaxStringLength(24);
      this.width = master.get_width();
      this.height = this.font.get_string_height(this.setting.get_name(), this.smoth) + 2;
      this.label_name = this.setting.get_name();
      if (this.setting.get_name().equalsIgnoreCase("info")) {
         this.info = true;
      }

      this.can = true;
      this.click = false;
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

   public int get_actual_side() {
      return this.master.master.get_x() + this.master.master.get_width() + this.width > BopeDraw.get_width() ? this.master.master.get_x() - this.master.master.get_width() : this.master.master.get_x() + this.master.master.get_width() + 4;
   }

   public boolean can() {
      return this.can;
   }

   public void input(char char_, int key) {
      this.entry.textboxKeyTyped(char_, key);
      if (key == 28 && this.entry.isFocused()) {
         this.entry.setFocused(false);
      }

   }

   public void mouse(int mx, int my, int mouse) {
      this.entry.mouseClicked(mx, my, mouse);
      if (mouse == 0 && this.motion(mx, my) && this.master.is_open() && this.can()) {
         this.frame.does_can(false);
         this.click = true;
         this.entry.setFocused(this.click);
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
      if (this.info) {
         BopeDraw.draw_string(this.setting.get_value(zbob), this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      } else {
         this.entry.width = this.width;
         this.entry.height = this.height;
         this.entry.x = this.get_actual_side();
         this.entry.y = this.save_y;
         this.setting.set_value(this.entry.getText());
         if (this.motion(mx, my)) {
            BopeDraw.draw_string(this.label_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
            this.entry.drawTextBox();
         } else if (this.click) {
            BopeDraw.draw_string(this.label_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
            this.entry.drawTextBox();
            this.click = this.entry.isFocused();
         } else {
            BopeDraw.draw_string(this.label_name, this.x + 2, this.save_y, ns_r, ns_g, ns_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
         }
      }

   }
}
