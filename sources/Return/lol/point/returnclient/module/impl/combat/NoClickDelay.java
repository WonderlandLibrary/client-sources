package lol.point.returnclient.module.impl.combat;

import lol.point.returnclient.events.impl.packet.EventPacket;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "NoClickDelay",
        description = "removes click delay",
        category = Category.COMBAT
)
public class NoClickDelay extends Module {

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        mc.leftClickCounter = 0;
    });

}
