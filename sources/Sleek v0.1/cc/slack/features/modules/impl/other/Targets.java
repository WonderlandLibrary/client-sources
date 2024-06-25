package cc.slack.features.modules.impl.other;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;

@ModuleInfo(
   name = "Targets",
   category = Category.OTHER
)
public class Targets extends Module {
   public final BooleanValue teams = new BooleanValue("Teams", false);
   public final BooleanValue playerTarget = new BooleanValue("Players", false);
   public final BooleanValue animalTarget = new BooleanValue("Animals", false);
   public final BooleanValue mobsTarget = new BooleanValue("Mobs", false);

   public Targets() {
      this.addSettings(new Value[]{this.teams, this.playerTarget, this.animalTarget, this.mobsTarget});
   }
}
