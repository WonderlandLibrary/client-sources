package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.util.Random;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

@ModuleInfo(
   name = "JumpReset",
   category = Category.GHOST
)
public class JumpReset extends Module {
   public final NumberValue<Double> chance = new NumberValue("Chance", 1.0D, 0.0D, 1.0D, 0.01D);
   boolean enable;

   public JumpReset() {
      this.addSettings(new Value[]{this.chance});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getCurrentScreen() == null) {
         if (mc.getPlayer().hurtTime == 10) {
            this.enable = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D) <= (Double)this.chance.getValue();
         }

         if (!this.enable) {
            if (mc.getPlayer().hurtTime >= 8) {
               mc.getGameSettings().keyBindJump.pressed = true;
            }

            if (mc.getPlayer().hurtTime >= 7) {
               mc.getGameSettings().keyBindForward.pressed = true;
            } else if (mc.getPlayer().hurtTime >= 4) {
               mc.getGameSettings().keyBindJump.pressed = false;
               mc.getGameSettings().keyBindForward.pressed = false;
            } else if (mc.getPlayer().hurtTime > 1) {
               mc.getGameSettings().keyBindForward.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindForward);
               mc.getGameSettings().keyBindJump.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindJump);
            }

         }
      }
   }
}
