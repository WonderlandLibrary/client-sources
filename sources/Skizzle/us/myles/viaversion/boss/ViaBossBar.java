/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package us.myles.ViaVersion.boss;

import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.boss.CommonBoss;

public class ViaBossBar
extends CommonBoss<Player> {
    public ViaBossBar(String title, float health, BossColor color, BossStyle style) {
        super(title, health, color, style);
    }

    @Override
    public BossBar addPlayer(Player player) {
        this.addPlayer(player.getUniqueId());
        return this;
    }

    public BossBar addPlayers(Player ... players) {
        for (Player p : players) {
            this.addPlayer(p);
        }
        return this;
    }

    @Override
    public BossBar removePlayer(Player player) {
        this.removePlayer(player.getUniqueId());
        return this;
    }
}

