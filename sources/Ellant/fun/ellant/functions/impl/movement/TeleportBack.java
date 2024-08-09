package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;

@FunctionRegister(name = "TeleportBack", type = Category.MOVEMENT, desc = "Пон?")
public class TeleportBack extends Function {
    public void onToggled(boolean actived) {
        if (!actived) {
            mc.player.motion.y = 0.08;
        }

        super.toggle();
    }
    @Subscribe
    public void onUpdate(EventUpdate e) {
        mc.player.setOnGround(false);
        mc.player.isAirBorne = true;
        if (mc.player.collidedVertically && mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motion.y = 0.42;
        }

        if (mc.player.isSneaking() && mc.player.ticksExisted % 2 == 0) {
            mc.player.motion.y = 0.15;
        }

    }
}