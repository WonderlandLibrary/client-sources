package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
   name = "Jesus",
   category = Category.MOVEMENT,
   key = 36
)
public class Jesus extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Vanilla", "Verus"});

   public Jesus() {
      this.addSettings(new Value[]{this.mode});
   }

   @Listen
   public void onMove(MoveEvent event) {
      if (mc.getPlayer().isInWater()) {
         String var2 = ((String)this.mode.getValue()).toLowerCase();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case 112097665:
            if (var2.equals("verus")) {
               var3 = 1;
            }
            break;
         case 233102203:
            if (var2.equals("vanilla")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            MovementUtil.setSpeed(event, 0.4000000059604645D);
            event.setY(0.01D);
            break;
         case 1:
            MovementUtil.setSpeed(event, 0.4000000059604645D);
            event.setY(0.4050000011920929D);
         }

      }
   }
}
