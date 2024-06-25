package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;

@ModuleInfo(
   name = "Animations",
   category = Category.RENDER
)
public class Animations extends Module {
   public final ModeValue<String> blockStyle = new ModeValue("Block Animation", new String[]{"1.7", "1.8", "Slide"});

   public Animations() {
      this.addSettings(new Value[]{this.blockStyle});
   }
}
