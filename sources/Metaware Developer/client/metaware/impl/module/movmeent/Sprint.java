package client.metaware.impl.module.movmeent;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.impl.event.impl.player.MovePlayerEvent;

@ModuleInfo(renderName = "Sprint", name = "Sprint", category = Category.MOVEMENT)
public class Sprint extends Module {

    public Property<Boolean> omni = new Property<>("Omnisprint", true);

    @Override
    public void init() {
        super.init();
        toggle();
    }

    @EventHandler
    private Listener<MovePlayerEvent> eventMoveListener = event -> {
        mc.thePlayer.setSprinting(shouldSprint());
    };

    private boolean shouldSprint() {
        return omni.getValue() ? mc.thePlayer.isMoving() : mc.thePlayer.moveForward > 0;
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}
