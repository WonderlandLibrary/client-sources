/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.proxy.Player
 */
package com.viaversion.viaversion.velocity.storage;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VelocityStorage
implements StorableObject {
    private final Player player;
    private String currentServer;
    private List<UUID> cachedBossbar;
    private static Method getServerBossBars;
    private static Class<?> clientPlaySessionHandler;
    private static Method getMinecraftConnection;

    public VelocityStorage(Player player) {
        this.player = player;
        this.currentServer = "";
    }

    public List<UUID> getBossbar() {
        if (this.cachedBossbar == null) {
            if (clientPlaySessionHandler == null) {
                return null;
            }
            if (getServerBossBars == null) {
                return null;
            }
            if (getMinecraftConnection == null) {
                return null;
            }
            try {
                Object object = getMinecraftConnection.invoke(this.player, new Object[0]);
                Object object2 = ReflectionUtil.invoke(object, "getSessionHandler");
                if (clientPlaySessionHandler.isInstance(object2)) {
                    this.cachedBossbar = (List)getServerBossBars.invoke(object2, new Object[0]);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                reflectiveOperationException.printStackTrace();
            }
        }
        return this.cachedBossbar;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getCurrentServer() {
        return this.currentServer;
    }

    public void setCurrentServer(String string) {
        this.currentServer = string;
    }

    public List<UUID> getCachedBossbar() {
        return this.cachedBossbar;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        VelocityStorage velocityStorage = (VelocityStorage)object;
        if (!Objects.equals(this.player, velocityStorage.player)) {
            return true;
        }
        if (!Objects.equals(this.currentServer, velocityStorage.currentServer)) {
            return true;
        }
        return Objects.equals(this.cachedBossbar, velocityStorage.cachedBossbar);
    }

    public int hashCode() {
        int n = this.player != null ? this.player.hashCode() : 0;
        n = 31 * n + (this.currentServer != null ? this.currentServer.hashCode() : 0);
        n = 31 * n + (this.cachedBossbar != null ? this.cachedBossbar.hashCode() : 0);
        return n;
    }

    static {
        try {
            clientPlaySessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
            getServerBossBars = clientPlaySessionHandler.getDeclaredMethod("getServerBossBars", new Class[0]);
            getMinecraftConnection = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer").getDeclaredMethod("getMinecraftConnection", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }
}

