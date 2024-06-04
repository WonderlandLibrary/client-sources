package com.polarware.module.impl.movement.flight;

import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.util.chat.ChatUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class GrimFlight extends Mode<FlightModule> {

    public GrimFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("USe a tnt to fleeeeee uwuasdu");
    }

    @Override
    public void onDisable() {

    }


    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (event.getPacket() instanceof C03PacketPlayer) {
            ((C03PacketPlayer) event.getPacket()).setX(mc.thePlayer.posX + 1000);
            ((C03PacketPlayer) event.getPacket()).setZ(mc.thePlayer.posZ + 1000);
        }
    };
}
