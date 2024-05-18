package rina.turok.bope.bopemod.hacks;

import net.minecraft.client.gui.GuiScreen;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeClickGUI extends BopeModule {
   BopeSetting shadow = this.create("Shadow", "GUIStringsShadow", false);
   BopeSetting smoth_font = this.create("Smooth Font", "GUISmothFont", true);
   BopeSetting label_frame = this.create("info", "ClickGUIInfoFrame", "Frames");
   BopeSetting name_frame_r = this.create("Name R", "ClickGUINameFrameR", 255, 0, 255);
   BopeSetting name_frame_g = this.create("Name G", "ClickGUINameFrameG", 255, 0, 255);
   BopeSetting name_frame_b = this.create("Name B", "ClickGUINameFrameB", 255, 0, 255);
   BopeSetting background_frame_r = this.create("Background R", "ClickGUIBackgroundFrameR", 0, 0, 255);
   BopeSetting background_frame_g = this.create("Background G", "ClickGUIBackgroundFrameG", 0, 0, 255);
   BopeSetting background_frame_b = this.create("Background B", "ClickGUIBackgroundFrameB", 0, 0, 255);
   BopeSetting background_frame_a = this.create("Background A", "ClickGUIBackgroundFrameA", 50, 0, 255);
   BopeSetting border_frame_r = this.create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
   BopeSetting border_frame_g = this.create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
   BopeSetting border_frame_b = this.create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);
   BopeSetting label_widget = this.create("info", "ClickGUIInfoWidget", "Widgets");
   BopeSetting name_widget_r = this.create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
   BopeSetting name_widget_g = this.create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
   BopeSetting name_widget_b = this.create("Name B", "ClickGUINameWidgetB", 255, 0, 255);
   BopeSetting background_widget_r = this.create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
   BopeSetting background_widget_g = this.create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
   BopeSetting background_widget_b = this.create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
   BopeSetting background_widget_a = this.create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);
   BopeSetting border_widget_r = this.create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
   BopeSetting border_widget_g = this.create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
   BopeSetting border_widget_b = this.create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);

   public BopeClickGUI() {
      super(BopeCategory.BOPE_GUI);
      this.name = "GUI";
      this.tag = "GUI";
      this.description = "B.O.P.E GUI for enbable or disable modules.";
      this.release("B.O.P.E");
      this.set_bind(54);
   }

   public void update() {
      Bope.click_gui.theme_frame_name_r = this.name_frame_r.get_value(1);
      Bope.click_gui.theme_frame_name_g = this.name_frame_g.get_value(1);
      Bope.click_gui.theme_frame_name_b = this.name_frame_b.get_value(1);
      Bope.click_gui.theme_frame_background_r = this.background_frame_r.get_value(1);
      Bope.click_gui.theme_frame_background_g = this.background_frame_g.get_value(1);
      Bope.click_gui.theme_frame_background_b = this.background_frame_b.get_value(1);
      Bope.click_gui.theme_frame_background_a = this.background_frame_a.get_value(1);
      Bope.click_gui.theme_frame_border_r = this.border_frame_r.get_value(1);
      Bope.click_gui.theme_frame_border_g = this.border_frame_g.get_value(1);
      Bope.click_gui.theme_frame_border_b = this.border_frame_b.get_value(1);
      Bope.click_gui.theme_widget_name_r = this.name_widget_r.get_value(1);
      Bope.click_gui.theme_widget_name_g = this.name_widget_g.get_value(1);
      Bope.click_gui.theme_widget_name_b = this.name_widget_b.get_value(1);
      Bope.click_gui.theme_widget_background_r = this.background_widget_r.get_value(1);
      Bope.click_gui.theme_widget_background_g = this.background_widget_g.get_value(1);
      Bope.click_gui.theme_widget_background_b = this.background_widget_b.get_value(1);
      Bope.click_gui.theme_widget_background_a = this.background_widget_a.get_value(1);
      Bope.click_gui.theme_widget_border_r = this.border_widget_r.get_value(1);
      Bope.click_gui.theme_widget_border_g = this.border_widget_g.get_value(1);
      Bope.click_gui.theme_widget_border_b = this.border_widget_b.get_value(1);
   }

   public void enable() {
      if (this.mc.world != null && this.mc.player != null) {
         this.mc.displayGuiScreen(Bope.click_gui);
      }

   }

   public void disable() {
      if (this.mc.world != null && this.mc.player != null) {
         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }
}
