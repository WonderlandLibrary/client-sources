package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderScoreboard;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.renderer.GlStateManager;

@ModuleInfo(
   name = "Scoreboard",
   category = Category.RENDER
)
public class ScoreboardModule extends Module {
   public final BooleanValue noscoreboard = new BooleanValue("No Scoreboard", false);
   private final NumberValue<Double> x = new NumberValue("PosX", 0.0D, -1000.0D, 1000.0D, 0.1D);
   private final NumberValue<Double> y = new NumberValue("PosY", 30.0D, -1000.0D, 1000.0D, 0.1D);

   public ScoreboardModule() {
      this.addSettings(new Value[]{this.noscoreboard, this.x, this.y});
   }

   @Listen
   public void onRenderScoreboard(RenderScoreboard event) {
      if (event.isPre()) {
         GlStateManager.translate(-(Double)this.x.getValue(), (Double)this.y.getValue(), 1.0D);
      } else {
         GlStateManager.translate((Double)this.x.getValue(), -(Double)this.y.getValue(), 1.0D);
      }

   }
}
