/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public abstract class ConnectionHandler {
    public abstract int connect(UserConnection var1, Position var2, int var3);

    public int getBlockData(UserConnection userConnection, Position position) {
        return ConnectionData.blockConnectionProvider.getBlockData(userConnection, position.x(), position.y(), position.z());
    }
}

