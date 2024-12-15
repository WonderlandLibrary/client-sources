package com.alan.clients.security;

import com.alan.clients.util.Accessor;
import net.minecraft.network.Packet;

public abstract class SecurityFeature implements Accessor {

    private final String check, description;

    public SecurityFeature(final String check, final String description) {
        this.check = check;
        this.description = description;
    }

    public abstract boolean handle(final Packet<?> packet);
}
