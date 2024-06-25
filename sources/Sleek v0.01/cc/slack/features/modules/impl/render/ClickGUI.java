package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.ui.NewCGUI.TransparentClickGUI;
import cc.slack.ui.clickGUI.ClickGui;
import cc.slack.utils.client.mc;
import net.minecraft.client.gui.GuiScreen;

@ModuleInfo(
   name = "ClickGUI",
   category = Category.RENDER,
   key = 54
)
public class ClickGUI<ClickGUIType extends GuiScreen> extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Old", "New"});
   private ClickGUIType clickgui;

   public ClickGUI() {
      this.addSettings(new Value[]{this.mode});
   }

   public void onEnable() {
      if (this.clickgui == null) {
         String var1 = (String)this.mode.getValue();
         byte var2 = -1;
         switch(var1.hashCode()) {
         case 78208:
            if (var1.equals("New")) {
               var2 = 0;
            }
            break;
         case 79367:
            if (var1.equals("Old")) {
               var2 = 1;
            }
         }

         switch(var2) {
         case 0:
            this.clickgui = new TransparentClickGUI();
            break;
         case 1:
            this.clickgui = new ClickGui();
            break;
         default:
            throw new RuntimeException("Unknown Type: ClickGUI");
         }
      }

      mc.getMinecraft().displayGuiScreen(this.clickgui);
      this.toggle();
   }
}
