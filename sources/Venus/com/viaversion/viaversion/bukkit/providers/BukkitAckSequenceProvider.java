/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.tasks.protocol1_19to1_18_2.AckSequenceTask;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;
import org.bukkit.plugin.Plugin;

public final class BukkitAckSequenceProvider
extends AckSequenceProvider {
    private final ViaVersionPlugin plugin;

    public BukkitAckSequenceProvider(ViaVersionPlugin viaVersionPlugin) {
        this.plugin = viaVersionPlugin;
    }

    @Override
    public void handleSequence(UserConnection userConnection, int n) {
        SequenceStorage sequenceStorage = userConnection.get(SequenceStorage.class);
        int n2 = sequenceStorage.setSequenceId(n);
        if (n2 == -1) {
            long l;
            int n3 = userConnection.getProtocolInfo().getServerProtocolVersion();
            long l2 = l = n3 > ProtocolVersion.v1_8.getVersion() && n3 < ProtocolVersion.v1_14.getVersion() ? 2L : 1L;
            if (this.plugin.isEnabled()) {
                this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new AckSequenceTask(userConnection, sequenceStorage), l);
            }
        }
    }
}

