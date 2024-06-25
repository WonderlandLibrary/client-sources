package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.game.TickEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.client.mc;
import cc.slack.utils.render.FreeLookUtil;
import io.github.nevalackin.radbus.Listen;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
   name = "FreeLook",
   category = Category.PLAYER,
   key = 42
)
public class FreeLook extends Module {
   private boolean freeLookingactivated;

   public void onDisable() {
      this.freeLookingactivated = false;
   }

   @Listen
   public void onTick(TickEvent event) {
      if (mc.getPlayer().ticksExisted < 10) {
         this.stop();
      }

      if (Keyboard.isKeyDown(this.getKey())) {
         if (!this.freeLookingactivated) {
            this.freeLookingactivated = true;
            FreeLookUtil.enable();
            mc.getGameSettings().thirdPersonView = 1;
         }
      } else if (this.freeLookingactivated) {
         this.stop();
      }

   }

   private void stop() {
      this.toggle();
      FreeLookUtil.setFreelooking(false);
      this.freeLookingactivated = false;
      mc.getGameSettings().thirdPersonView = 0;
   }
}
