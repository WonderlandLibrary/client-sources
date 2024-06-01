package com.polarware.security;

import com.polarware.Client;
import com.polarware.module.impl.other.SecurityFeaturesModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import org.reflections.Reflections;

import java.util.ArrayList;

public final class SecurityFeatureManager extends ArrayList<SecurityFeature> {

    private SecurityFeaturesModule features;

    public SecurityFeatureManager() {
        super();
    }

    public void init() {
        Client.INSTANCE.getEventBus().register(this);

        this.features = Client.INSTANCE.getModuleManager().get(SecurityFeaturesModule.class);

        if (this.features == null) return;

        final Reflections reflections = new Reflections("com.polarware.security.impl");

        reflections.getSubTypesOf(SecurityFeature.class).forEach(clazz -> {
            try {
                this.add(clazz.getConstructor().newInstance());
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean isInsecure(final Packet<?> packet) {
        // Notification
        return this.features != null && this.features.isEnabled()
                && !Minecraft.getMinecraft().isSingleplayer()
                && this.stream().anyMatch(feature -> feature.handle(packet));
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        event.setCancelled(isInsecure(event.getPacket()));
    };
}
