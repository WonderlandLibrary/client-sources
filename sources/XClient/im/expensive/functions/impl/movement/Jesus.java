package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.player.MoveUtils;

@FunctionRegister(name = "Jesus", type = Category.MOVEMENT)
public class Jesus extends Function {

    @Subscribe
    private void onUpdate(EventUpdate update) {
        if (mc.player.isInWater()) {
            float moveSpeed = 10.0f;
            moveSpeed /= 100.0f;

            double moveX = mc.player.getForward().x * moveSpeed;
            double moveZ = mc.player.getForward().z * moveSpeed;
            mc.player.motion.y = 0f;
            if (MoveUtils.isMoving()) {
                if (MoveUtils.getMotion() < 0.9f) {
                    mc.player.motion.x *= 0.5f;
                    mc.player.motion.z *= 0f;
                }
            }
        }
    }
}
