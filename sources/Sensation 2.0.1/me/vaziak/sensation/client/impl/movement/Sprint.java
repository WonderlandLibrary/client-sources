 package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;

/**
 * @author Spec
 */
public class Sprint extends Module {

    private BooleanProperty legit = new BooleanProperty("Legit", "Sprint like a LEGIT player, not omni, and following game mechanics", null, false);

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        registerValue(legit);
    }

    @Collect
    public void onTickEvent(PlayerUpdateEvent event) {
        setMode(legit.getValue() ? "Legit" : "Omni");
        if (mc.thePlayer != null && !mc.thePlayer.isSneaking() && mc.thePlayer.getFoodStats().getFoodLevel() > 5) {
            if (legit.getValue()) {
                if (mc.thePlayer.movementInput.moveForward > 0 && Math.abs(event.getYaw() - mc.thePlayer.rotationYaw) < 15) {
                    mc.thePlayer.setSprinting(true);
                } else {
                    mc.thePlayer.setSprinting(false);
                }
            } else {
                if (mc.thePlayer.isMoving() && !mc.thePlayer.isOnLadder()) {
                    mc.thePlayer.setSprinting(true);
                }
            }
        }
    }
}
