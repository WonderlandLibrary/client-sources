/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.legacy;

import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.legacy.bossbar.CommonBoss;

public final class LegacyAPI<T>
implements LegacyViaAPI<T> {
    @Override
    public BossBar createLegacyBossBar(String string, float f, BossColor bossColor, BossStyle bossStyle) {
        return new CommonBoss(string, f, bossColor, bossStyle);
    }
}

