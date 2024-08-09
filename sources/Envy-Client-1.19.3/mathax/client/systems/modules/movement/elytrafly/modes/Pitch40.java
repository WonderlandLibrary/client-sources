package mathax.client.systems.modules.movement.elytrafly.modes;

import mathax.client.MatHax;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.systems.modules.movement.elytrafly.ElytraFlightMode;
import mathax.client.systems.modules.movement.elytrafly.ElytraFlightModes;

public class Pitch40 extends ElytraFlightMode {
    private boolean pitchingDown = true;
    private int pitch;

    public Pitch40() {
        super(ElytraFlightModes.Pitch40);
    }

    @Override
    public void onActivate() {
        if (MatHax.mc.player.getY() < elytraFly.pitch40upperBounds.get()) {
            elytraFly.error("You must be above upper bounds!");
            elytraFly.toggle();
        }

        pitch = 40;
    }

    @Override
    public void onDeactivate() {}

    @Override
    public void onTick() {
        if (pitchingDown && MatHax.mc.player.getY() <= elytraFly.pitch40lowerBounds.get()) pitchingDown = false;
        else if (!pitchingDown && MatHax.mc.player.getY() >= elytraFly.pitch40upperBounds.get()) pitchingDown = true;

        if (!pitchingDown && MatHax.mc.player.getPitch() > -40) {
            pitch -= elytraFly.pitch40rotationSpeed.get();

            if (pitch < -40) pitch = -40;
        } else if (pitchingDown && MatHax.mc.player.getPitch() < 40) {
            pitch += elytraFly.pitch40rotationSpeed.get();

            if (pitch > 40) pitch = 40;
        }

        MatHax.mc.player.setPitch(pitch);
    }

    @Override
    public void autoTakeoff() {}

    @Override
    public void handleHorizontalSpeed(PlayerMoveEvent event) {
        velX = event.movement.x;
        velZ = event.movement.z;
    }

    @Override
    public void handleVerticalSpeed(PlayerMoveEvent event) {}

    @Override
    public void handleFallMultiplier() {}

    @Override
    public void handleAutopilot() {}
}
