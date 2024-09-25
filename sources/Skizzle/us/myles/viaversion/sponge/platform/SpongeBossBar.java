/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.entity.living.player.Player
 */
package us.myles.ViaVersion.sponge.platform;

import org.spongepowered.api.entity.living.player.Player;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.boss.CommonBoss;

public class SpongeBossBar
extends CommonBoss<Player> {
    public SpongeBossBar(String title, float health, BossColor color, BossStyle style) {
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

