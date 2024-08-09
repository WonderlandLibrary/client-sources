/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum AttackIndicatorStatus {
    OFF(0, "options.off"),
    CROSSHAIR(1, "options.attack.crosshair"),
    HOTBAR(2, "options.attack.hotbar");

    private static final AttackIndicatorStatus[] BY_ID;
    private final int id;
    private final String resourceKey;

    private AttackIndicatorStatus(int n2, String string2) {
        this.id = n2;
        this.resourceKey = string2;
    }

    public int getId() {
        return this.id;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public static AttackIndicatorStatus byId(int n) {
        return BY_ID[MathHelper.normalizeAngle(n, BY_ID.length)];
    }

    private static AttackIndicatorStatus[] lambda$static$0(int n) {
        return new AttackIndicatorStatus[n];
    }

    static {
        BY_ID = (AttackIndicatorStatus[])Arrays.stream(AttackIndicatorStatus.values()).sorted(Comparator.comparingInt(AttackIndicatorStatus::getId)).toArray(AttackIndicatorStatus::lambda$static$0);
    }
}

