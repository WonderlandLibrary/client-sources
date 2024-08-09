/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.plugin.Plugin
 */
package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin
extends Plugin
implements ViaBackwardsPlatform {
    public void onLoad() {
        Via.getManager().addEnableListener(this::lambda$onLoad$0);
    }

    @Override
    public void disable() {
    }

    private void lambda$onLoad$0() {
        this.init(this.getDataFolder());
    }
}

