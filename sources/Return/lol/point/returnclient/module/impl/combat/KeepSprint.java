package lol.point.returnclient.module.impl.combat;

import lol.point.returnclient.events.impl.player.EventKeepSprint;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "KeepSprint",
        description = "maintains momentum during attacks",
        category = Category.COMBAT
)
public class KeepSprint extends Module {

    @Subscribe
    private final Listener<EventKeepSprint> onSprint = new Listener<>(eventKeepSprint -> {
        eventKeepSprint.setCancelled(true);
    });

}
