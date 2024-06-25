package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
   name = "Stap",
   category = Category.GHOST
)
public class Stap extends Module {
   private int ticks;
   private final TimeUtil wtapTimer = new TimeUtil();

   @Listen
   public void onAttack(AttackEvent event) {
      if (this.wtapTimer.hasReached(500L)) {
         this.wtapTimer.reset();
         this.ticks = 2;
      }

   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      switch(this.ticks) {
      case 1:
         mc.getGameSettings().keyBindForward.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindForward);
         mc.getGameSettings().keyBindBack.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindBack);
         --this.ticks;
         break;
      case 2:
         mc.getGameSettings().keyBindForward.pressed = false;
         mc.getGameSettings().keyBindBack.pressed = true;
         --this.ticks;
      }

   }
}
