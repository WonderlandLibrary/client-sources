package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import java.util.Random;
import net.minecraft.util.MathHelper;

@ModuleInfo(
   name = "Reach",
   category = Category.GHOST
)
public class Reach extends Module {
   public final NumberValue<Double> reach = new NumberValue("Reach", 3.1D, 3.0D, 6.0D, 0.01D);
   public final NumberValue<Double> chance = new NumberValue("Chance", 1.0D, 0.0D, 1.0D, 0.01D);
   public double combatReach = 3.0D;

   public Reach() {
      this.addSettings(new Value[]{this.reach, this.chance});
   }

   @Listen
   public void onAttack(AttackEvent event) {
      double rnd = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
      if (rnd <= (Double)this.chance.getValue()) {
         this.combatReach = (Double)this.reach.getValue();
      } else {
         this.combatReach = 3.0D;
      }

   }
}
