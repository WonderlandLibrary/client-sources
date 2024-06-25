package cc.slack.features.modules.impl.other;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "AntiBot",
   category = Category.OTHER
)
public class AntiBot extends Module {
   public final BooleanValue colored = new BooleanValue("Colored name", true);

   public AntiBot() {
      this.addSettings(new Value[]{this.colored});
   }

   public boolean isBot(EntityLivingBase e) {
      return (Boolean)this.colored.getValue() && e.getCustomNameTag().contains("§");
   }
}
