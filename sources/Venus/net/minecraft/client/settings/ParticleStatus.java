/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum ParticleStatus {
    ALL(0, "options.particles.all"),
    DECREASED(1, "options.particles.decreased"),
    MINIMAL(2, "options.particles.minimal");

    private static final ParticleStatus[] BY_ID;
    private final int id;
    private final String resourceKey;

    private ParticleStatus(int n2, String string2) {
        this.id = n2;
        this.resourceKey = string2;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public int getId() {
        return this.id;
    }

    public static ParticleStatus byId(int n) {
        return BY_ID[MathHelper.normalizeAngle(n, BY_ID.length)];
    }

    private static ParticleStatus[] lambda$static$0(int n) {
        return new ParticleStatus[n];
    }

    static {
        BY_ID = (ParticleStatus[])Arrays.stream(ParticleStatus.values()).sorted(Comparator.comparingInt(ParticleStatus::getId)).toArray(ParticleStatus::lambda$static$0);
    }
}

