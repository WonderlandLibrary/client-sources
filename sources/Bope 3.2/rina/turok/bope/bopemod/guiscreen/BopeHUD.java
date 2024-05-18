package rina.turok.bope.bopemod.guiscreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnableButton;

public class BopeHUD extends GuiScreen {
   private BopeFrame frame = new BopeFrame("B.O.P.E HUD", "BopeHUD", 40, 40);
   private int frame_height;
   private final Minecraft mc = Minecraft.getMinecraft();
   public boolean on_gui = false;
   public boolean back = false;

   public BopeFrame get_frame_hud() {
      return this.frame;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void initGui() {
      this.on_gui = true;
      BopeFrame.nc_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameR").get_value(1);
      BopeFrame.nc_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameG").get_value(1);
      BopeFrame.nc_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameFrameB").get_value(1);
      BopeFrame.bg_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameR").get_value(1);
      BopeFrame.bg_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameG").get_value(1);
      BopeFrame.bg_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameB").get_value(1);
      BopeFrame.bg_a = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundFrameA").get_value(1);
      BopeFrame.bd_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameR").get_value(1);
      BopeFrame.bd_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameG").get_value(1);
      BopeFrame.bd_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderFrameB").get_value(1);
      BopeFrame.bd_a = 0;
      BopeFrame.bdw_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").get_value(1);
      BopeFrame.bdw_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").get_value(1);
      BopeFrame.bdw_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").get_value(1);
      BopeFrame.bdw_a = 255;
      BopePinnableButton.nc_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetR").get_value(1);
      BopePinnableButton.nc_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetG").get_value(1);
      BopePinnableButton.nc_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUINameWidgetB").get_value(1);
      BopePinnableButton.bg_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetR").get_value(1);
      BopePinnableButton.bg_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetG").get_value(1);
      BopePinnableButton.bg_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetB").get_value(1);
      BopePinnableButton.bg_a = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBackgroundWidgetA").get_value(1);
      BopePinnableButton.bd_r = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetR").get_value(1);
      BopePinnableButton.bd_g = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetG").get_value(1);
      BopePinnableButton.bd_b = Bope.get_setting_manager().get_setting_with_tag("GUI", "ClickGUIBorderWidgetB").get_value(1);
   }

   public void onGuiClosed() {
      if (this.back) {
         Bope.get_module_manager().get_module_with_tag("GUI").set_active(true);
         Bope.get_module_manager().get_module_with_tag("HUD").set_active(false);
      } else {
         Bope.get_module_manager().get_module_with_tag("HUD").set_active(false);
         Bope.get_module_manager().get_module_with_tag("GUI").set_active(false);
      }

      this.on_gui = false;
      Bope.get_config_manager().save_client();
   }

   protected void mouseClicked(int mx, int my, int mouse) {
      this.frame.mouse(mx, my, mouse);
      if (mouse == 0 && this.frame.motion(mx, my) && this.frame.can()) {
         this.frame.set_move(true);
         this.frame.set_move_x(mx - this.frame.get_x());
         this.frame.set_move_y(my - this.frame.get_y());
      }

   }

   protected void mouseReleased(int mx, int my, int state) {
      this.frame.release(mx, my, state);
      this.frame.set_move(false);
   }

   public void drawScreen(int mx, int my, float tick) {
      this.drawDefaultBackground();
      this.frame.render(mx, my, 2);
   }
}
