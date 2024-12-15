package com.alan.clients.util.constants;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketAuthenticationFinish;

public class ConstantsManager {

    public static double a = Math.PI * 2;
    public static float b = 180.0F;

    public ConstantsManager() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink
    public final Listener<BackendPacketEvent> onBackend = event -> {
        if (event.getPacket() instanceof S2CPacketAuthenticationFinish) {
            S2CPacketAuthenticationFinish packet = (S2CPacketAuthenticationFinish) event.getPacket();
            a = packet.getB();
            b = packet.getC();
        }
    };
}
