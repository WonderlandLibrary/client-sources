/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package us.myles.ViaVersion.bukkit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import us.myles.ViaVersion.api.ViaListener;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;

public class ViaBukkitListener
extends ViaListener
implements Listener {
    private final Plugin plugin;

    public ViaBukkitListener(Plugin plugin, Class<? extends Protocol> requiredPipeline) {
        super(requiredPipeline);
        this.plugin = plugin;
    }

    protected UserConnection getUserConnection(Player player) {
        return this.getUserConnection(player.getUniqueId());
    }

    protected boolean isOnPipe(Player player) {
        return this.isOnPipe(player.getUniqueId());
    }

    @Override
    public void register() {
        if (this.isRegistered()) {
            return;
        }
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, this.plugin);
        this.setRegistered(true);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}

