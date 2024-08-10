// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
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
        addSettings(offGround, overEdge, avoidJump);
    }

    @Listen
    public void onMove(MoveEvent event) {
        if (!isOverEdge() && overEdge.getValue()) return;
        if (mc.thePlayer.motionY > 0 && mc.thePlayer.offGroundTicks < 6 && avoidJump.getValue()) return;
        event.safewalk = mc.thePlayer.onGround || offGround.getValue();
    }

    private boolean isOverEdge() {
        return mc.theWorld.rayTraceBlocks(
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ),
                true, true, false) == null;
    }


}
