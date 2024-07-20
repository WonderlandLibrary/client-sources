/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.api;

public interface ViaRewindConfig {
    public CooldownIndicator getCooldownIndicator();

    public boolean isReplaceAdventureMode();

    public boolean isReplaceParticles();

    public int getMaxBookPages();

    public int getMaxBookPageSize();

    public static enum CooldownIndicator {
        TITLE,
        ACTION_BAR,
        BOSS_BAR,
        DISABLED;

    }
}

