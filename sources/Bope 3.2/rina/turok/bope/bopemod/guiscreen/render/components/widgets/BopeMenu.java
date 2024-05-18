package rina.turok.bope.bopemod.guiscreen.render.components.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;

public class BopeMenu {
   private BopeCombobox master;
   private ArrayList sub_menu;
   private int x;
   private int y;
   private int save_y;
   private int width;
   private int height;
   private boolean opened;
   public final Minecraft mc = Minecraft.getMinecraft();

   public BopeMenu(BopeCombobox master, List items) {
      this.master = master;
      this.sub_menu = new ArrayList();
      this.x = 0;
      this.y = 0;
      this.save_y = this.y;
      this.width = 100;
      this.height = 5;
      this.opened = false;
      int count = 0;
      int size = items.size();
      Iterator var5 = items.iterator();

      while(var5.hasNext()) {
         String values = (String)var5.next();
         BopeSubMenu sub_menus = new BopeSubMenu(this, values, this.height);
         this.sub_menu.add(sub_menus);
         ++count;
         if (count >= size) {
            this.height += 15;
         } else {
            this.height += 10;
         }
      }

   }

   public void set_open(boolean value) {
      this.opened = value;
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

   public BopeCombobox get_master() {
      return this.master;
   }

   public boolean is_open() {
      return this.opened;
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

   public int get_actual_side() {
      return this.master.frame.get_x() + this.master.frame.get_width() + this.width > BopeDraw.get_width() ? this.master.frame.get_x() - this.master.frame.get_width() : this.master.frame.get_x() + this.master.frame.get_width();
   }

   public boolean motion(int mx, int my) {
      return mx >= this.x && my >= this.save_y && mx <= this.x + this.width && my <= this.save_y + this.height;
   }

   public void click(int mx, int my, int mouse) {
      Iterator var4 = this.sub_menu.iterator();

      while(var4.hasNext()) {
         BopeSubMenu sub_menus = (BopeSubMenu)var4.next();
         sub_menus.click(mx, my, mouse);
      }

   }

   public void render(boolean event_pass, int master_y, int mx, int my) {
      this.save_y = this.y + master_y;
      this.x = this.get_actual_side();
      int bg_r = Bope.click_gui.theme_frame_background_r;
      int bg_g = Bope.click_gui.theme_frame_background_g;
      int bg_b = Bope.click_gui.theme_frame_background_b;
      int bg_a = Bope.click_gui.theme_frame_background_a;
      if (this.is_open() || event_pass) {
         BopeDraw.draw_rect(this.x, this.save_y, this.x + this.width, this.save_y + this.height, bg_r, bg_g, bg_b, bg_a);
      }

      Iterator var9 = this.sub_menu.iterator();

      while(true) {
         BopeSubMenu sub_menus;
         do {
            if (!var9.hasNext()) {
               return;
            }

            sub_menus = (BopeSubMenu)var9.next();
         } while(!this.is_open() && !event_pass);

         sub_menus.render(master_y, mx, my, 2);
      }
   }
}
