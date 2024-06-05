package client.metaware.impl.module.combat;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;

@ModuleInfo(renderName = "Wtap", name = "Wtap", category = Category.COMBAT)
public class Wtap extends Module {

    @EventHandler
    private Listener<UpdatePlayerEvent> eventMoveListener = event -> {
        if (event.isPre() && mc.thePlayer.isSprinting() && mc.thePlayer.swingProgress > 0.6) {
            mc.thePlayer.setSprinting(false);
        }
    };
}
