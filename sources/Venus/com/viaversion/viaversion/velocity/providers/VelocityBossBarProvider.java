/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.velocity.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.velocity.storage.VelocityStorage;
import java.util.UUID;

public class VelocityBossBarProvider
extends BossBarProvider {
    @Override
    public void handleAdd(UserConnection userConnection, UUID uUID) {
        VelocityStorage velocityStorage;
        if (userConnection.has(VelocityStorage.class) && (velocityStorage = userConnection.get(VelocityStorage.class)).getBossbar() != null) {
            velocityStorage.getBossbar().add(uUID);
        }
    }

    @Override
    public void handleRemove(UserConnection userConnection, UUID uUID) {
        VelocityStorage velocityStorage;
        if (userConnection.has(VelocityStorage.class) && (velocityStorage = userConnection.get(VelocityStorage.class)).getBossbar() != null) {
            velocityStorage.getBossbar().remove(uUID);
        }
    }
}

