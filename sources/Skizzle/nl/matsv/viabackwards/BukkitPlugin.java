/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package nl.matsv.viabackwards;

import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
import nl.matsv.viabackwards.listener.FireExtinguishListener;
import nl.matsv.viabackwards.listener.LecternInteractListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.bukkit.platform.BukkitViaLoader;

public class BukkitPlugin
extends JavaPlugin
implements ViaBackwardsPlatform {
    public void onEnable() {
        this.init(this.getDataFolder());
        Via.getPlatform().runSync(this::onServerLoaded);
    }

    private void onServerLoaded() {
        BukkitViaLoader loader = (BukkitViaLoader)Via.getManager().getLoader();
        if (ProtocolRegistry.SERVER_PROTOCOL >= ProtocolVersion.v1_16.getVersion()) {
            loader.storeListener(new FireExtinguishListener(this)).register();
        }
        if (ProtocolRegistry.SERVER_PROTOCOL >= ProtocolVersion.v1_14.getVersion()) {
            loader.storeListener(new LecternInteractListener(this)).register();
        }
    }

    @Override
    public void disable() {
        this.getPluginLoader().disablePlugin((Plugin)this);
    }
}

