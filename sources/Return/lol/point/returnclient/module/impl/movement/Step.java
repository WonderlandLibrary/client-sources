package lol.point.returnclient.module.impl.movement;

import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.util.minecraft.MoveUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "Step",
        description = "rides blocks",
        category = Category.MOVEMENT
)
public class Step extends Module {

    private boolean isStepping = false;

    public void onDisable() {
        isStepping = false;
    }

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(eventMotion -> {
        if (eventMotion.isPre()) {
            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally && MoveUtil.isMoving()) {
                isStepping = true;
            }

            if (isStepping) {
                if (!MoveUtil.isMoving() || !mc.thePlayer.isCollidedHorizontally) {
                    isStepping = false;
                    return;
                }

                switch (mc.thePlayer.offGroundTicks) {
                    case 0:
                        MoveUtil.stop();
                        MoveUtil.strafe();
                        mc.thePlayer.jump();
                        break;
                    case 9:
                        MoveUtil.stop();
                        break;
                    case 15:
                        mc.thePlayer.motionY = 0;
                        MoveUtil.stop();
                        break;
                    case 16:
                        mc.thePlayer.jump();
                        isStepping = false;
                        break;
                }
            }
        }
    });
}
