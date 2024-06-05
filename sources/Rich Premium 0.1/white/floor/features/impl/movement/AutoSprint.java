package white.floor.features.impl.movement;

import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

public class AutoSprint extends Feature {
    public AutoSprint() {
        super("AutoSprint", "auto run.", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void eventUpdate(EventUpdate eventUpdate) {
        if(mc.gameSettings.keyBindForward.isKeyDown())
            mc.player.setSprinting(true);
    }
}
