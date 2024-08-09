/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum AmbientOcclusionStatus {
    OFF(0, "options.ao.off"),
    MIN(1, "options.ao.min"),
    MAX(2, "options.ao.max");

    private static final AmbientOcclusionStatus[] VALUES;
    private final int id;
    private final String resourceKey;

    private AmbientOcclusionStatus(int n2, String string2) {
        this.id = n2;
        this.resourceKey = string2;
    }

    public int getId() {
        return this.id;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public static AmbientOcclusionStatus getValue(int n) {
        return VALUES[MathHelper.normalizeAngle(n, VALUES.length)];
    }

    private static AmbientOcclusionStatus[] lambda$static$0(int n) {
        return new AmbientOcclusionStatus[n];
    }

    static {
        VALUES = (AmbientOcclusionStatus[])Arrays.stream(AmbientOcclusionStatus.values()).sorted(Comparator.comparingInt(AmbientOcclusionStatus::getId)).toArray(AmbientOcclusionStatus::lambda$static$0);
    }
}

