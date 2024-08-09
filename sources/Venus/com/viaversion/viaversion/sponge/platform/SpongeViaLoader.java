/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.Sponge
 *  org.spongepowered.api.event.EventManager
 */
package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.sponge.listeners.UpdateListener;
import java.util.HashSet;
import java.util.Set;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;

public class SpongeViaLoader
implements ViaPlatformLoader {
    private final SpongePlugin plugin;
    private final Set<Object> listeners = new HashSet<Object>();
    private final Set<PlatformTask> tasks = new HashSet<PlatformTask>();

    public SpongeViaLoader(SpongePlugin spongePlugin) {
        this.plugin = spongePlugin;
    }

    private void registerListener(Object object) {
        Sponge.eventManager().registerListeners(this.plugin.container(), this.storeListener(object));
    }

    private <T> T storeListener(T t) {
        this.listeners.add(t);
        return t;
    }

    @Override
    public void load() {
        this.registerListener(new UpdateListener());
    }

    @Override
    public void unload() {
        this.listeners.forEach(arg_0 -> ((EventManager)Sponge.eventManager()).unregisterListeners(arg_0));
        this.listeners.clear();
        this.tasks.forEach(PlatformTask::cancel);
        this.tasks.clear();
    }
}

