package cc.slack.features.modules.impl.movement;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.impl.world.Scaffold;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.potion.Potion;

@ModuleInfo(
   name = "Sprint",
   category = Category.MOVEMENT
)
public class Sprint extends Module {
   private final BooleanValue omniSprint = new BooleanValue("OmniSprint", false);

   public Sprint() {
      this.addSettings(new Value[]{this.omniSprint});
   }

   private boolean hasPotion() {
      return !mc.getPlayer().isPotionActive(Potion.confusion) && !mc.getPlayer().isPotionActive(Potion.blindness);
   }

   private boolean isMoving() {
      return !mc.getPlayer().isCollidedHorizontally && (mc.getPlayer().getFoodStats().getFoodLevel() > 6 || mc.getPlayer().capabilities.allowFlying) && !mc.getPlayer().isSneaking() && !mc.getPlayer().isUsingItem();
   }

   @Listen
   public void onUpdate(UpdateEvent e) {
      if (!((Scaffold)Slack.getInstance().getModuleManager().getInstance(Scaffold.class)).isToggle()) {
         mc.getPlayer().setSprinting(((Boolean)this.omniSprint.getValue() || mc.getPlayer().moveForward > 0.0F) && this.isMoving() && this.hasPotion());
      }
   }
}
