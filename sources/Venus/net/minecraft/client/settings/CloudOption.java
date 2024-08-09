/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum CloudOption {
    OFF(0, "options.off"),
    FAST(1, "options.clouds.fast"),
    FANCY(2, "options.clouds.fancy");

    private static final CloudOption[] BY_ID;
    private final int id;
    private final String key;

    private CloudOption(int n2, String string2) {
        this.id = n2;
        this.key = string2;
    }

    public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public static CloudOption byId(int n) {
        return BY_ID[MathHelper.normalizeAngle(n, BY_ID.length)];
    }

    private static CloudOption[] lambda$static$0(int n) {
        return new CloudOption[n];
    }

    static {
        BY_ID = (CloudOption[])Arrays.stream(CloudOption.values()).sorted(Comparator.comparingInt(CloudOption::getId)).toArray(CloudOption::lambda$static$0);
    }
}

