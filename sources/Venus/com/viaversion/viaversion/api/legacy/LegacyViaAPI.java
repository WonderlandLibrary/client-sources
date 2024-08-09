/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.legacy;

import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;

public interface LegacyViaAPI<T> {
    public BossBar createLegacyBossBar(String var1, float var2, BossColor var3, BossStyle var4);

    default public BossBar createLegacyBossBar(String string, BossColor bossColor, BossStyle bossStyle) {
        return this.createLegacyBossBar(string, 1.0f, bossColor, bossStyle);
    }
}

