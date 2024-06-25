package cc.slack.features.modules.impl.combat;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
   name = "Hitbox",
   category = Category.COMBAT
)
public class Hitbox extends Module {
   public final NumberValue<Float> hitboxSize = new NumberValue("Expand", 0.1F, 0.0F, 1.0F, 0.01F);

   public Hitbox() {
      this.addSettings(new Value[]{this.hitboxSize});
   }
}
