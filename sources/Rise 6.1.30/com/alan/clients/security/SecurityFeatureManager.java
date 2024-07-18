package com.alan.clients.security;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.other.AntiCrash;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

import java.util.ArrayList;

public final class SecurityFeatureManager extends ArrayList<SecurityFeature> {

    private AntiCrash features;

    public SecurityFeatureManager() {
        super();
    }

    public void init() {
        Client.INSTANCE.getEventBus().register(this);

        this.features = Client.INSTANCE.getModuleManager().get(AntiCrash.class);

        if (this.features == null) return;

//        final Reflections reflections = new Reflections("com.alan.clients.security.impl");
//
//        reflections.getSubTypesOf(SecurityFeature.class).forEach(clazz -> {
//            try {
//                this.add(clazz.getConstructor().newInstance());
//            } catch (final Exception ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    public boolean isInsecure(final Packet<?> packet) {
        // Notification
        return this.features != null && this.features.isEnabled()
                && !Minecraft.getMinecraft().isSingleplayer()
                && this.stream().anyMatch(feature -> feature.handle(packet));
    }

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        event.setCancelled(isInsecure(event.getPacket()));
    };
}
