package club.strifeclient.module.implementations.movement.flight;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.networking.PacketOutboundEvent;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.module.implementations.movement.Flight;
import club.strifeclient.setting.Mode;
import club.strifeclient.util.player.MovementUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class ZoneCraftFlight extends Mode<Flight.FlightMode> {
    private Flight parent;

    @Override
    public Flight.FlightMode getRepresentation() {
        return Flight.FlightMode.ZONE_CRAFT;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(parent == null)
            parent = Client.INSTANCE.getModuleManager().getModule(Flight.class);
    }

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = e -> {
        e.y = mc.thePlayer.motionY = 0;
        MovementUtil.setSpeed(e, MovementUtil.isMoving() ? parent.flightSpeedSetting.getDouble() / 2 : 0);
    };

    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundEventListener = e -> {
        if (e.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer p = e.getPacket();
            p.y -= p.y % (1/64f);
            p.onGround = true;
        }
    };
}
