package rina.turok.bope.bopemod.guiscreen;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeGUI extends GuiScreen {
   private ArrayList frame = new ArrayList();
   private int frame_x = 10;
   private BopeFrame current;
   private boolean event_start = true;
   private boolean event_finish = false;
   public int theme_frame_name_r = 0;
   public int theme_frame_name_g = 0;
   public int theme_frame_name_b = 0;
   public int theme_frame_background_r = 0;
   public int theme_frame_background_g = 0;
   public int theme_frame_background_b = 0;
   public int theme_frame_background_a = 0;
   public int theme_frame_border_r = 0;
   public int theme_frame_border_g = 0;
   public int theme_frame_border_b = 0;
   public int theme_widget_name_r = 0;
   public int theme_widget_name_g = 0;
   public int theme_widget_name_b = 0;
   public int theme_widget_background_r = 0;
   public int theme_widget_background_g = 0;
   public int theme_widget_background_b = 0;
   public int theme_widget_background_a = 0;
   public int theme_widget_border_r = 0;
   public int theme_widget_border_g = 0;
   public int theme_widget_border_b = 0;
   private final Minecraft mc = Minecraft.getMinecraft();

   public BopeGUI() {
      int count = 0;
      int size = BopeCategory.values().length;
      BopeCategory[] var3 = BopeCategory.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BopeCategory categorys = var3[var5];
         if (!categorys.equals(BopeCategory.BOPE_SYS)) {
            BopeFrame frames = new BopeFrame(categorys);
            frames.set_x(this.frame_x);
            this.frame.add(frames);
            ++count;
            this.frame_x += frames.get_width() / 2;
            this.current = frames;
         }
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      Bope.get_config_manager().save_binds();
      Bope.get_config_manager().save_client();
      Bope.get_config_manager().save_settings();
      Bope.get_module_manager().get_module_with_tag("GUI").set_active(false);
   }

   protected void keyTyped(char char_, int key) {
      Iterator var3 = this.frame.iterator();

      while(var3.hasNext()) {
         BopeFrame frames = (BopeFrame)var3.next();
         frames.bind(char_, key);
         if (key == 1 && !frames.is_binding()) {
            this.mc.displayGuiScreen((GuiScreen)null);
         }
      }

   }

   protected void mouseClicked(int mx, int my, int mouse) {
      Iterator var4 = this.frame.iterator();

      while(var4.hasNext()) {
         BopeFrame frames = (BopeFrame)var4.next();
         frames.mouse(mx, my, mouse);
         if (mouse == 0 && frames.motion(mx, my) && frames.can()) {
            frames.does_button_for_do_widgets_can(false);
            this.current = frames;
            this.current.set_move(true);
            this.current.set_move_x(mx - this.current.get_x());
            this.current.set_move_y(my - this.current.get_y());
         }
      }

   }

   protected void mouseReleased(int mx, int my, int state) {
      Iterator var4 = this.frame.iterator();

      while(var4.hasNext()) {
         BopeFrame frames = (BopeFrame)var4.next();
         frames.does_button_for_do_widgets_can(true);
         frames.mouse_release(mx, my, state);
         frames.set_move(false);
      }

      this.set_current(this.current);
   }

   public void drawScreen(int mx, int my, float tick) {
      this.drawDefaultBackground();
      Iterator var4 = this.frame.iterator();

      while(var4.hasNext()) {
         BopeFrame frames = (BopeFrame)var4.next();
         frames.render(mx, my, 2);
      }

   }

   public void set_current(BopeFrame current) {
      this.frame.remove(current);
      this.frame.add(current);
   }

   public BopeFrame get_current() {
      return this.current;
   }

   public ArrayList get_array_frames() {
      return this.frame;
   }

   public BopeFrame get_frame_with_tag(String tag) {
      BopeFrame frame_requested = null;
      Iterator var3 = this.get_array_frames().iterator();

      while(var3.hasNext()) {
         BopeFrame frames = (BopeFrame)var3.next();
         if (frames.get_tag().equals(tag)) {
            frame_requested = frames;
         }
      }

      return frame_requested;
   }
}
