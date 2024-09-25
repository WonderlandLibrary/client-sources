/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.proxy.Player
 */
package us.myles.ViaVersion.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.boss.CommonBoss;

public class VelocityBossBar
extends CommonBoss<Player> {
    public VelocityBossBar(String title, float health, BossColor color, BossStyle style) {
        super(title, health, color, style);
    }
}

