/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.velocity.providers;

import java.util.UUID;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import us.myles.ViaVersion.velocity.storage.VelocityStorage;

public class VelocityBossBarProvider
extends BossBarProvider {
    @Override
    public void handleAdd(UserConnection user, UUID barUUID) {
        VelocityStorage storage;
        if (user.has(VelocityStorage.class) && (storage = user.get(VelocityStorage.class)).getBossbar() != null) {
            storage.getBossbar().add(barUUID);
        }
    }

    @Override
    public void handleRemove(UserConnection user, UUID barUUID) {
        VelocityStorage storage;
        if (user.has(VelocityStorage.class) && (storage = user.get(VelocityStorage.class)).getBossbar() != null) {
            storage.getBossbar().remove(barUUID);
        }
    }
}

