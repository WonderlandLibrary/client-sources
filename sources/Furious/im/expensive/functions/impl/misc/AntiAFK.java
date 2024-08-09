package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;

import java.util.concurrent.ThreadLocalRandom;

@FunctionRegister(name = "AntiAFK", type = Category.Miscellaneous)
public class AntiAFK extends Function {

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.player.ticksExisted % 200 != 0) return;

        if (mc.player.isOnGround()) mc.player.jump();
        mc.player.rotationYaw += ThreadLocalRandom.current().nextFloat(-10, 10);
    }
}
