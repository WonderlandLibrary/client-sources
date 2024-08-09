package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

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
