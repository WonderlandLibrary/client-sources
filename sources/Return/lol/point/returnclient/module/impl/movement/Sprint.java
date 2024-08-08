package lol.point.returnclient.module.impl.movement;

import lol.point.Return;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.module.impl.player.Scaffold;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "Sprint",
        description = "automatically sprints",
        category = Category.MOVEMENT
)
public class Sprint extends Module {

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        if (!Return.INSTANCE.moduleManager.getByClass(Scaffold.class).enabled) {
            mc.thePlayer.setSprinting(true);
            if (mc.thePlayer.isCollidedHorizontally || mc.thePlayer.moveForward <= 0 || mc.thePlayer.isUsingItem() || mc.thePlayer.isSneaking()) {
                mc.thePlayer.setSprinting(false);
            }
        }
    });
}
