package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
   name = "LegitScaffold",
   category = Category.GHOST
)
public class LegitScaffold extends Module {
   private final NumberValue<Integer> sneakTime = new NumberValue("Sneak Time", 60, 0, 300, 20);
   private final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
   private final BooleanValue holdSneak = new BooleanValue("Hold Sneak", false);
   private boolean shouldSneak = false;
   private final TimeUtil sneakTimer = new TimeUtil();

   public LegitScaffold() {
      this.addSettings(new Value[]{this.sneakTime, this.onlyGround, this.holdSneak});
   }

   public void onDisable() {
      mc.getGameSettings().keyBindSneak.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindSneak);
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getCurrentScreen() == null) {
         this.shouldSneak = !this.sneakTimer.hasReached((long)(Integer)this.sneakTime.getValue());
         if (PlayerUtil.isOverAir() && (!(Boolean)this.onlyGround.getValue() || mc.getPlayer().onGround) && mc.getPlayer().motionY < 0.1D) {
            this.shouldSneak = true;
         }

         if ((Boolean)this.holdSneak.getValue()) {
            mc.getGameSettings().keyBindSneak.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindSneak) && this.shouldSneak;
         } else {
            mc.getGameSettings().keyBindSneak.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindSneak) || this.shouldSneak;
         }

      }
   }
}
