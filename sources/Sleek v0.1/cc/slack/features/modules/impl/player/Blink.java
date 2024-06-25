package cc.slack.features.modules.impl.player;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.player.BlinkUtil;

@ModuleInfo(
   name = "Blink",
   category = Category.PLAYER
)
public class Blink extends Module {
   private final BooleanValue outbound = new BooleanValue("Outbound", true);
   private final BooleanValue inbound = new BooleanValue("Inbound", false);

   public Blink() {
      this.addSettings(new Value[]{this.outbound, this.inbound});
   }

   public void onEnable() {
      BlinkUtil.enable((Boolean)this.inbound.getValue(), (Boolean)this.outbound.getValue());
   }

   public void onDisable() {
      BlinkUtil.disable();
   }
}
