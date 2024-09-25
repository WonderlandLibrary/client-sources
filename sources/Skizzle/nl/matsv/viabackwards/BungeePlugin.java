/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.plugin.Plugin
 */
package nl.matsv.viabackwards;

import net.md_5.bungee.api.plugin.Plugin;
import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
import us.myles.ViaVersion.api.Via;

public class BungeePlugin
extends Plugin
implements ViaBackwardsPlatform {
    public void onLoad() {
        Via.getManager().addEnableListener(() -> this.init(this.getDataFolder()));
    }

    @Override
    public void disable() {
    }
}

