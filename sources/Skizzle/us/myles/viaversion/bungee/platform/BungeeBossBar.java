/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package us.myles.ViaVersion.bungee.platform;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.boss.CommonBoss;

public class BungeeBossBar
extends CommonBoss<ProxiedPlayer> {
    public BungeeBossBar(String title, float health, BossColor color, BossStyle style) {
        super(title, health, color, style);
    }

    @Override
    public BossBar addPlayer(ProxiedPlayer player) {
        this.addPlayer(player.getUniqueId());
        return this;
    }

    public BossBar addPlayers(ProxiedPlayer ... players) {
        for (ProxiedPlayer p : players) {
            this.addPlayer(p);
        }
        return this;
    }

    @Override
    public BossBar removePlayer(ProxiedPlayer player) {
        this.removePlayer(player.getUniqueId());
        return this;
    }
}

