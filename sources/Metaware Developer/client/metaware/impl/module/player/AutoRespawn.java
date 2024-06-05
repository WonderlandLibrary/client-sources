package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;

@ModuleInfo(renderName = "AutoRespawn", name = "AutoRespawn", category = Category.PLAYER)
public class AutoRespawn extends Module {

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(mc.thePlayer.getHealth() == 0){
            mc.thePlayer.respawnPlayer();
        }
    };
}
