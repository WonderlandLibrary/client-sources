package rina.turok.bope.bopemod.guiscreen.render.pinnables;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;

public class BopeFrame {
   private ArrayList pinnable_button = new ArrayList();
   private String name;
   private String tag;
   private int x;
   private int y;
   private int move_x;
   private int move_y;
   private int width;
   private int height;
   private boolean move;
   private boolean can;
   private boolean smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
   private int border_size = 1;
   private BopeDraw font = new BopeDraw(1.0F);
   public static int nc_r = 0;
   public static int nc_g = 0;
   public static int nc_b = 0;
   public static int bg_r = 0;
   public static int bg_g = 0;
   public static int bg_b = 0;
   public static int bg_a = 0;
   public static int bd_r = 0;
   public static int bd_g = 0;
   public static int bd_b = 0;
   public static int bd_a = 0;
   public static int bdw_r = 0;
   public static int bdw_g = 0;
   public static int bdw_b = 0;
   public static int bdw_a = 255;
   public final Minecraft mc = Minecraft.getMinecraft();

   public BopeFrame(String name, String tag, int initial_x, int initial_y) {
      this.name = name;
      this.tag = tag;
      this.x = initial_x;
      this.y = initial_y;
      this.move_x = 0;
      this.move_y = 0;
      this.width = 100;
      this.height = 25;
      this.can = true;
      int size = Bope.get_hud_manager().get_array_huds().size();
      int count = 0;
      Iterator var7 = Bope.get_hud_manager().get_array_huds().iterator();

      while(var7.hasNext()) {
         BopePinnable pinnables = (BopePinnable)var7.next();
         BopePinnableButton pinnables_buttons = new BopePinnableButton(this, pinnables.get_title(), pinnables.get_tag());
         pinnables_buttons.set_y(this.height);
         this.pinnable_button.add(pinnables_buttons);
         ++count;
         if (count >= size) {
            this.height += 5;
         } else {
            this.height += 10;
         }
      }

   }

   public void set_move(boolean value) {
      this.move = value;
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

   public void set_move_x(int x) {
      this.move_x = x;
   }

   public void set_move_y(int y) {
      this.move_y = y;
   }

   public void set_width(int width) {
      this.width = width;
   }

   public void set_height(int height) {
      this.height = height;
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public boolean is_moving() {
      return this.move;
   }

   public boolean can() {
      return this.can;
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

   public boolean motion(int mx, int my) {
      return mx >= this.get_x() && my >= this.get_y() && mx <= this.get_x() + this.get_width() && my <= this.get_y() + this.get_height();
   }

   public void crush(int mx, int my) {
      int screen_x = this.mc.displayWidth / 2;
      int screen_y = this.mc.displayHeight / 2;
      this.set_x(mx - this.move_x);
      this.set_y(my - this.move_y);
      if (this.x + this.width >= screen_x) {
         this.x = screen_x - this.width - 1;
      }

      if (this.x <= 0) {
         this.x = 1;
      }

      if (this.y + this.height >= screen_y) {
         this.y = screen_y - this.height - 1;
      }

      if (this.y <= 0) {
         this.y = 1;
      }

      if (this.x % 2 != 0) {
         this.x += this.x % 2;
      }

      if (this.y % 2 != 0) {
         this.y += this.y % 2;
      }

   }

   public void mouse(int mx, int my, int mouse) {
      Iterator var4 = this.pinnable_button.iterator();

      while(var4.hasNext()) {
         BopePinnableButton pinnables_buttons = (BopePinnableButton)var4.next();
         pinnables_buttons.click(mx, my, mouse);
      }

   }

   public void release(int mx, int my, int mouse) {
      Iterator var4 = this.pinnable_button.iterator();

      while(var4.hasNext()) {
         BopePinnableButton pinnables_buttons = (BopePinnableButton)var4.next();
         pinnables_buttons.release(mx, my, mouse);
      }

      this.set_move(false);
   }

   public void render(int mx, int my, int separate) {
      this.smoth = Bope.get_setting_manager().get_setting_with_tag("GUISmothFont").get_value(true);
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_b = Color.HSBtoRGB(tick_color[0], 1.0F, 1.0F);
      int color;
      if (color_b <= 50) {
         color = 50;
      } else if (color_b >= 120) {
         color = 120;
      } else {
         color = color_b;
      }

      bd_a = color;
      bdw_a = 255;
      BopeDraw.draw_rect(this.x, this.y, this.x + this.width, this.y + this.height, bg_r, bg_g, bg_b, bg_a);
      BopeDraw.draw_rect(this.x - 1, this.y, this.width + 2, this.height, bd_r, bd_g, bd_b, bd_a, this.border_size, "left-right");
      BopeDraw.draw_string(this.name, this.x + 4, this.y + 4, nc_r, nc_g, nc_b, Bope.get_setting_manager().get_setting_with_tag("GUIStringsShadow").get_value(true), this.smoth);
      if (this.is_moving()) {
         this.crush(mx, my);
      }

      Iterator var7 = this.pinnable_button.iterator();

      while(var7.hasNext()) {
         BopePinnableButton pinnables_buttons = (BopePinnableButton)var7.next();
         pinnables_buttons.set_x(this.x + separate);
         pinnables_buttons.render(mx, my, separate);
         if (pinnables_buttons.motion(mx, my)) {
            pinnables_buttons.pass(true);
            BopeDraw.draw_rect(this.get_x() - 1, pinnables_buttons.get_save_y(), this.get_width() + 2, pinnables_buttons.get_height(), bdw_r, bdw_g, bdw_b, bdw_a, this.border_size, "right-left");
         } else {
            pinnables_buttons.pass(false);
         }
      }

   }
}
