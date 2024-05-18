package rina.turok.bope.bopemod.guiscreen.render.pinnables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopePinnable {
   private String title;
   private String tag;
   private boolean state;
   private boolean move;
   public BopeDraw font;
   private int x;
   private int y;
   private int width;
   private int height;
   public int screen_width;
   public int screen_height;
   private int move_x;
   private int move_y;
   private int tolerance;
   private boolean dock = true;
   private boolean dock_y = false;
   public boolean smoth = Bope.get_setting_manager().get_setting_with_tag("HUDStringsSmooth").get_value(true);
   public boolean event_is_resize = false;
   public final Minecraft mc = Minecraft.getMinecraft();
   public boolean pass;

   public BopePinnable(String title, String tag, float font_, int x, int y) {
      this.title = title;
      this.tag = tag;
      this.font = new BopeDraw(font_);
      this.x = x;
      this.y = y;
      this.width = 1;
      this.height = 10;
      this.move = false;
      this.screen_width = BopeDraw.get_width();
      this.screen_height = BopeDraw.get_height();
      this.tolerance = this.x;
      this.pass = false;
   }

   public void background() {
      this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 60);
   }

   public void set_move(boolean value) {
      this.move = value;
   }

   public void set_active(boolean value) {
      this.state = value;
   }

   public void set_x(int x) {
      this.x = x;
   }

   public void set_y(int y) {
      this.y = y;
   }

   public void set_x(int x, String mult) {
      if (mult.equals("+")) {
         this.x += x;
      } else if (mult.equals("-")) {
         this.x -= x;
      }

   }

   public void set_y(int y, String mult) {
      if (mult.equals("+")) {
         this.y += y;
      } else if (mult.equals("-")) {
         this.y -= y;
      }

   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public void set_move_x(int x) {
      this.move_x = x;
   }

   public void set_move_y(int y) {
      this.move_y = y;
   }

   public void set_dock(boolean value) {
      this.dock = value;
   }

   public void set_dock_y(boolean value) {
      this.dock_y = value;
   }

   public boolean is_moving() {
      return this.move;
   }

   public String get_title() {
      return this.title;
   }

   public String get_tag() {
      return this.tag;
   }

   public int get_title_height() {
      return this.font.get_string_height(this.title, this.smoth);
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

   public boolean get_dock() {
      return this.dock;
   }

   public boolean get_dock_y() {
      return this.dock_y;
   }

   public boolean is_active() {
      return this.state;
   }

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_y() && mx <= this.get_x() + this.get_width() && my <= this.get_y() + this.get_height();
   }

   public void update() {
      this.screen_width = BopeDraw.get_width();
      this.screen_height = BopeDraw.get_height();
   }

   public void fix_screen() {
      if (this.get_x() <= 0) {
         this.set_dock(true);
         this.set_x(1);
      }

      if (this.get_x() + this.get_width() >= this.screen_width) {
         this.set_dock(false);
         this.set_x(this.screen_width - this.get_width() - 1);
      }

      if (this.get_y() <= 0) {
         this.set_dock_y(true);
         this.set_y(1);
      }

      if (this.get_y() + this.get_height() >= this.screen_height) {
         this.set_dock_y(false);
         this.set_y(this.screen_height - this.get_height() - 1);
      }

      if (this.get_x() % 2 != 0) {
         this.set_x(this.get_x() % 2, "+");
      }

      if (this.get_y() % 2 != 0) {
         this.set_y(this.get_y() % 2, "+");
      }

   }

   public void crush(int mx, int my) {
      this.set_x(mx - this.move_x);
      this.set_y(my - this.move_y);
      this.tolerance = this.get_x() + this.get_width() - this.screen_width;
      this.fix_screen();
   }

   public void render() {
   }

   public void click(int mx, int my, int mouse) {
      if (mouse == 0 && this.is_active() && this.motion(mx, my)) {
         this.set_move(true);
         this.set_move_x(mx - this.get_x());
         this.set_move_y(my - this.get_y());
      }

   }

   public void release(int mx, int my, int mouse) {
      this.set_move(false);
   }

   public void render(int mx, int my, int tick) {
      if (this.is_moving()) {
         this.crush(mx, my);
         this.event_is_resize = false;
      }

      if (this.is_active()) {
         this.render();
         GL11.glPushMatrix();
         GL11.glEnable(3553);
         GL11.glEnable(3042);
         GlStateManager.enableBlend();
         GL11.glPopMatrix();
         TurokRenderHelp.release_gl();
         if (this.motion(mx, my) && !this.is_moving()) {
            this.create_rect(0, 0, this.width, this.height, 255, 255, 255, 50);
         } else if (this.is_moving()) {
            this.create_rect(0, 0, this.width, this.height, 255, 255, 255, 75);
         }
      }

   }

   protected void chat_event() {
      boolean in_gui = this.mc.ingameGUI.getChatGUI().getChatOpen();
      if (in_gui && this.get_y() + this.get_height() >= BopeDraw.get_height() - this.get_height() - 1) {
         int comparator = BopeDraw.get_height() - this.get_height() - 17;
         this.set_y(comparator);
         this.state = true;
      }

      if (!in_gui && this.state) {
         this.set_y(BopeDraw.get_height() - this.get_height() - 1);
         this.state = false;
      }

   }

   protected void create_line(String string, int pos_x, int pos_y, String no_dock) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("HUDStringsSmooth").get_value(true);
      int nl_r = Bope.client_r;
      int nl_g = Bope.client_g;
      int nl_b = Bope.client_b;
      boolean shadow = Bope.get_setting_manager().get_setting_with_tag("HUDStringsShadow").get_value(true);
      BopeDraw.draw_string(string, this.x + pos_x, this.y + pos_y, nl_r, nl_g, nl_b, shadow, this.smoth);
   }

   protected void create_line(String string, int pos_x, int pos_y) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("HUDStringsSmooth").get_value(true);
      int nl_r = Bope.client_r;
      int nl_g = Bope.client_g;
      int nl_b = Bope.client_b;
      boolean shadow = Bope.get_setting_manager().get_setting_with_tag("HUDStringsShadow").get_value(true);
      BopeDraw.draw_string(string, this.x + this.docking(pos_x, string), this.y + pos_y, nl_r, nl_g, nl_b, shadow, this.smoth);
   }

   protected void create_line(String string, int pos_x, int pos_y, int r, int g, int b) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("HUDStringsSmooth").get_value(true);
      boolean shadow = Bope.get_setting_manager().get_setting_with_tag("HUDStringsShadow").get_value(true);
      BopeDraw.draw_string(string, this.x + this.docking(pos_x, string), this.y + pos_y, r, g, b, shadow, this.smoth);
   }

   protected void create_rect(int pos_x, int pos_y, int width, int height, int r, int g, int b, int a) {
      BopeDraw.draw_rect(this.x + pos_x, this.y + pos_y, this.x + width, this.y + height, r, g, b, a);
   }

   protected int get(String string, String type) {
      int value_to_request = 0;
      if (type.equals("width")) {
         value_to_request = this.font.get_string_width(string, this.smoth);
      }

      if (type.equals("height")) {
         value_to_request = this.font.get_string_height(string, this.smoth);
      }

      return value_to_request;
   }

   public int docking(int position_x, String string) {
      return this.get_dock() ? position_x : this.width - this.get(string, "width") - position_x;
   }

   public int docking(int position_x, int width) {
      return this.get_dock() ? position_x : this.width - width - position_x;
   }

   protected boolean is_on_gui() {
      return Bope.click_hud.on_gui;
   }
}
