/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package us.myles.ViaVersion.bungee.providers;

import java.lang.reflect.Method;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.bungee.storage.BungeeStorage;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.EntityIdProvider;

public class BungeeEntityIdProvider
extends EntityIdProvider {
    private static Method getClientEntityId;

    @Override
    public int getEntityId(UserConnection user) throws Exception {
        BungeeStorage storage = user.get(BungeeStorage.class);
        ProxiedPlayer player = storage.getPlayer();
        return (Integer)getClientEntityId.invoke((Object)player, new Object[0]);
    }

    static {
        try {
            getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", new Class[0]);
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

