/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.bungee.providers;

import java.util.UUID;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.bungee.storage.BungeeStorage;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BossBarProvider;

public class BungeeBossBarProvider
extends BossBarProvider {
    @Override
    public void handleAdd(UserConnection user, UUID barUUID) {
        BungeeStorage storage;
        if (user.has(BungeeStorage.class) && (storage = user.get(BungeeStorage.class)).getBossbar() != null) {
            storage.getBossbar().add(barUUID);
        }
    }

    @Override
    public void handleRemove(UserConnection user, UUID barUUID) {
        BungeeStorage storage;
        if (user.has(BungeeStorage.class) && (storage = user.get(BungeeStorage.class)).getBossbar() != null) {
            storage.getBossbar().remove(barUUID);
        }
    }
}

