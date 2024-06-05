package client.metaware.impl.module.render;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Nobob", renderName = "Nobob", category = Category.VISUALS, keybind = Keyboard.KEY_NONE)
public class Nobob extends Module {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    private Listener<MovePlayerEvent> eventListener = event -> {
        mc.thePlayer.distanceWalkedModified = 0;
    };

}
