/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.api;

public interface ViaRewindConfig {
    public CooldownIndicator getCooldownIndicator();

    public boolean isReplaceAdventureMode();

    public boolean isReplaceParticles();

    public static enum CooldownIndicator {
        TITLE,
        ACTION_BAR,
        BOSS_BAR,
        DISABLED;

    }
}

