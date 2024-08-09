package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;

@FunctionRegister(name = "Phase", type = Category.Movement)
public class Phase extends Function {

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (!collisionPredict()) {
            if (mc.gameSettings.keyBindJump.pressed) {
                mc.player.setOnGround(true);
            }
        }
    }

    public boolean collisionPredict() {
        boolean prevCollision = mc.world
                .getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D)).toList().isEmpty();

        return mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625D))
                .toList().isEmpty() && prevCollision;
    }
}
