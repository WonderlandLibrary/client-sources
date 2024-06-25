package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.util.Vec3;

@ModuleInfo(
   name = "SafeWalk",
   category = Category.MOVEMENT
)
public class SafeWalk extends Module {
   private final BooleanValue offGround = new BooleanValue("In Air", false);
   private final BooleanValue overEdge = new BooleanValue("Only Over Edge", true);
   private final BooleanValue avoidJump = new BooleanValue("Avoid During Jump", true);

   public SafeWalk() {
      this.addSettings(new Value[]{this.offGround, this.overEdge, this.avoidJump});
   }

   @Listen
   public void onMove(MoveEvent event) {
      if (this.isOverEdge() || !(Boolean)this.overEdge.getValue()) {
         if (!(mc.getPlayer().motionY > 0.0D) || mc.getPlayer().offGroundTicks >= 6 || !(Boolean)this.avoidJump.getValue()) {
            event.safewalk = mc.getPlayer().onGround || (Boolean)this.offGround.getValue();
         }
      }
   }

   private boolean isOverEdge() {
      return mc.getWorld().rayTraceBlocks(new Vec3(mc.getPlayer().posX, mc.getPlayer().posY, mc.getPlayer().posZ), new Vec3(mc.getPlayer().posX, mc.getPlayer().posY - 2.0D, mc.getPlayer().posZ), true, true, false) == null;
   }
}
