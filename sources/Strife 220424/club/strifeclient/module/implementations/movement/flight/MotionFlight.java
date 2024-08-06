package club.strifeclient.module.implementations.movement.flight;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.module.implementations.movement.Flight;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.util.player.MovementUtil;

public final class MotionFlight extends Mode<Flight.FlightMode> {

    private final DoubleSetting horizontalSpeedSetting = new DoubleSetting("Horizontal", 1, 1, 10, 0.1);
    private final DoubleSetting verticalSpeedSetting = new DoubleSetting("Vertical", 1, 1, 10, 0.1);

    @Override
    public Flight.FlightMode getRepresentation() {
        return Flight.FlightMode.MOTION;
    }

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = e -> {
        e.y = mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? verticalSpeedSetting.getFloat() : mc.gameSettings.keyBindSneak.isKeyDown() ? -verticalSpeedSetting.getFloat() : 0;
            MovementUtil.setSpeed(e, MovementUtil.isMoving() ? horizontalSpeedSetting.getFloat() : 0);
    };
}
