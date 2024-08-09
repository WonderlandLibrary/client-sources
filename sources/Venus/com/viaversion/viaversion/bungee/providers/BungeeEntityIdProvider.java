/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import java.lang.reflect.Method;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeEntityIdProvider
extends EntityIdProvider {
    private static Method getClientEntityId;

    @Override
    public int getEntityId(UserConnection userConnection) throws Exception {
        BungeeStorage bungeeStorage = userConnection.get(BungeeStorage.class);
        ProxiedPlayer proxiedPlayer = bungeeStorage.getPlayer();
        return (Integer)getClientEntityId.invoke(proxiedPlayer, new Object[0]);
    }

    static {
        try {
            getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }
}

