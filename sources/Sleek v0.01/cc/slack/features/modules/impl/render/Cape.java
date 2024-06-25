package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(
   name = "Cape",
   category = Category.RENDER
)
public class Cape extends Module {
   private final ModeValue<String> capes = new ModeValue("Cape", new String[]{"Slack", "Rise6"});

   public Cape() {
      this.addSettings(new Value[]{this.capes});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      String var2 = (String)this.capes.getValue();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 78970477:
         if (var2.equals("Rise6")) {
            var3 = 1;
         }
         break;
      case 79966064:
         if (var2.equals("Slack")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         mc.getPlayer().setLocationOfCape(new ResourceLocation("slack/capes/slack.png"));
         break;
      case 1:
         mc.getPlayer().setLocationOfCape(new ResourceLocation("slack/capes/rise6.png"));
      }

   }

   public void onDisable() {
      mc.getPlayer().setLocationOfCape((ResourceLocation)null);
   }
}
