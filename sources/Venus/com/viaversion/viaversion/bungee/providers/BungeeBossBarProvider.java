/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import java.util.UUID;

public class BungeeBossBarProvider
extends BossBarProvider {
    @Override
    public void handleAdd(UserConnection userConnection, UUID uUID) {
        BungeeStorage bungeeStorage;
        if (userConnection.has(BungeeStorage.class) && (bungeeStorage = userConnection.get(BungeeStorage.class)).getBossbar() != null) {
            bungeeStorage.getBossbar().add(uUID);
        }
    }

    @Override
    public void handleRemove(UserConnection userConnection, UUID uUID) {
        BungeeStorage bungeeStorage;
        if (userConnection.has(BungeeStorage.class) && (bungeeStorage = userConnection.get(BungeeStorage.class)).getBossbar() != null) {
            bungeeStorage.getBossbar().remove(uUID);
        }
    }
}

