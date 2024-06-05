package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.MoveEntityEvent;
import io.github.nevalackin.radium.event.impl.player.SprintEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;

@ModuleInfo(label = "Sprint", category = ModuleCategory.MOVEMENT)
public final class Sprint extends Module {

    private final Property<Boolean> omniProperty = new Property<>("Omni", true);

    public Sprint() {
        toggle();
    }

    @Listener
    public void onMoveEntityEvent(MoveEntityEvent event) {
        if (MovementUtils.isOnGround() && MovementUtils.isMoving() &&
                !Speed.isSpeeding() && !ModuleManager.getInstance(Flight.class).isEnabled() && omniProperty.getValue())
            MovementUtils.setSpeed(event, MovementUtils.getBaseMoveSpeed());
    }

    @Listener
    public void onSprintEvent(SprintEvent event) {
        if (!event.isSprinting()) {
            boolean canSprint = MovementUtils.canSprint(omniProperty.getValue());
            Wrapper.getPlayer().setSprinting(canSprint);
            event.setSprinting(canSprint);
        }
    }
}
