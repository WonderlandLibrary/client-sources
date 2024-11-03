package xyz.cucumber.base.module.feat.other;

import net.minecraft.client.gui.GuiScreen;
import xyz.cucumber.base.interf.DropdownClickGui.DropdownClickGui;
import xyz.cucumber.base.interf.newclickgui.NewClickGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;

@ModuleInfo(
   category = Category.OTHER,
   description = "Displays client gui",
   name = "Click Gui",
   key = 54,
   priority = ArrayPriority.LOW
)
public class ClickGuiModule extends Mod {
   GuiScreen clickGui;
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Windowed", "Dropdown"});
   public static ModeSettings imageMode = new ModeSettings(
      "Image Mode",
      new String[]{
         "Astolfo",
         "Astolfo 2",
         "Akane",
         "Akane 2",
         "Akane 3",
         "Akane 4",
         "Megumin",
         "Megumin 2",
         "Megumin 3",
         "Yuki",
         "Yuki 2",
         "Ai",
         "Miko",
         "Child",
         "Astolfo Fucked",
         "Astolfo Hot",
         "Astolfo Hot 2",
         "Astolfo Hot 3",
         "Astolfo Cum",
         "Astolfo BJ",
         "Astolfo Leashed"
      }
   );

   public ClickGuiModule() {
      this.addSettings(new ModuleSettings[]{this.mode, imageMode});
   }

   @Override
   public void onEnable() {
      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1115378545:
            if (var1.equals("windowed") && !(this.clickGui instanceof NewClickGui)) {
               this.clickGui = new NewClickGui();
            }
            break;
         case -432061423:
            if (var1.equals("dropdown") && !(this.clickGui instanceof DropdownClickGui)) {
               this.clickGui = new DropdownClickGui();
            }
      }

      this.mc.displayGuiScreen(this.clickGui);
      this.toggle();
   }
}
