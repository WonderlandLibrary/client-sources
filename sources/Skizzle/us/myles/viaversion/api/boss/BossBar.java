/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.boss;

import java.util.Set;
import java.util.UUID;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossFlag;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.api.data.UserConnection;

public abstract class BossBar<T> {
    public abstract String getTitle();

    public abstract BossBar setTitle(String var1);

    public abstract float getHealth();

    public abstract BossBar setHealth(float var1);

    public abstract BossColor getColor();

    public abstract BossBar setColor(BossColor var1);

    public abstract BossStyle getStyle();

    public abstract BossBar setStyle(BossStyle var1);

    @Deprecated
    public BossBar addPlayer(T player) {
        throw new UnsupportedOperationException("This method is not implemented for the platform " + Via.getPlatform().getPlatformName());
    }

    public abstract BossBar addPlayer(UUID var1);

    public abstract BossBar addConnection(UserConnection var1);

    @Deprecated
    public BossBar addPlayers(T ... players) {
        throw new UnsupportedOperationException("This method is not implemented for the platform " + Via.getPlatform().getPlatformName());
    }

    @Deprecated
    public BossBar removePlayer(T player) {
        throw new UnsupportedOperationException("This method is not implemented for the platform " + Via.getPlatform().getPlatformName());
    }

    public abstract BossBar removePlayer(UUID var1);

    public abstract BossBar removeConnection(UserConnection var1);

    public abstract BossBar addFlag(BossFlag var1);

    public abstract BossBar removeFlag(BossFlag var1);

    public abstract boolean hasFlag(BossFlag var1);

    public abstract Set<UUID> getPlayers();

    public abstract Set<UserConnection> getConnections();

    public abstract BossBar show();

    public abstract BossBar hide();

    public abstract boolean isVisible();

    public abstract UUID getId();
}

