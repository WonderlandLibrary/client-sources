/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;

public abstract class ConnectionHandler {
    public abstract int connect(UserConnection var1, Position var2, int var3);

    public int getBlockData(UserConnection user, Position position) {
        return Via.getManager().getProviders().get(BlockConnectionProvider.class).getBlockData(user, position.getX(), position.getY(), position.getZ());
    }
}

