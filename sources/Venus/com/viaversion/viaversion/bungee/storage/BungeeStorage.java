/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package com.viaversion.viaversion.bungee.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeStorage
implements StorableObject {
    private static Field bossField;
    private final ProxiedPlayer player;
    private String currentServer;
    private Set<UUID> bossbar;

    public BungeeStorage(ProxiedPlayer proxiedPlayer) {
        this.player = proxiedPlayer;
        this.currentServer = "";
        if (bossField != null) {
            try {
                this.bossbar = (Set)bossField.get(proxiedPlayer);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public String getCurrentServer() {
        return this.currentServer;
    }

    public void setCurrentServer(String string) {
        this.currentServer = string;
    }

    public Set<UUID> getBossbar() {
        return this.bossbar;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        BungeeStorage bungeeStorage = (BungeeStorage)object;
        if (!Objects.equals(this.player, bungeeStorage.player)) {
            return true;
        }
        if (!Objects.equals(this.currentServer, bungeeStorage.currentServer)) {
            return true;
        }
        return Objects.equals(this.bossbar, bungeeStorage.bossbar);
    }

    public int hashCode() {
        int n = this.player != null ? this.player.hashCode() : 0;
        n = 31 * n + (this.currentServer != null ? this.currentServer.hashCode() : 0);
        n = 31 * n + (this.bossbar != null ? this.bossbar.hashCode() : 0);
        return n;
    }

    static {
        try {
            Class<?> clazz = Class.forName("net.md_5.bungee.UserConnection");
            bossField = clazz.getDeclaredField("sentBossBars");
            bossField.setAccessible(false);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (NoSuchFieldException noSuchFieldException) {
            // empty catch block
        }
    }
}

