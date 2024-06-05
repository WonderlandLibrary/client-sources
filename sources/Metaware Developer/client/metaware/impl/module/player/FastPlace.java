package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;

@ModuleInfo(renderName = "AutoRespawn", name = "AutoRespawn", category = Category.MOVEMENT)
public class FastPlace extends Module {

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        mc.rightClickDelayTimer = 0;
    };
}
