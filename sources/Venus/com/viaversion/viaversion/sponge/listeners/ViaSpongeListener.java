/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.Sponge
 */
package com.viaversion.viaversion.sponge.listeners;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.ViaListener;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.lang.reflect.Field;
import org.spongepowered.api.Sponge;

public class ViaSpongeListener
extends ViaListener {
    private static Field entityIdField;
    private final SpongePlugin plugin;

    public ViaSpongeListener(SpongePlugin spongePlugin, Class<? extends Protocol> clazz) {
        super(clazz);
        this.plugin = spongePlugin;
    }

    @Override
    public void register() {
        if (this.isRegistered()) {
            return;
        }
        Sponge.eventManager().registerListeners(this.plugin.container(), (Object)this);
        this.setRegistered(false);
    }
}

