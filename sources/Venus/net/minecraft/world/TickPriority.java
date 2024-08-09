/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

public enum TickPriority {
    EXTREMELY_HIGH(-3),
    VERY_HIGH(-2),
    HIGH(-1),
    NORMAL(0),
    LOW(1),
    VERY_LOW(2),
    EXTREMELY_LOW(3);

    private final int priority;

    private TickPriority(int n2) {
        this.priority = n2;
    }

    public static TickPriority getPriority(int n) {
        for (TickPriority tickPriority : TickPriority.values()) {
            if (tickPriority.priority != n) continue;
            return tickPriority;
        }
        return n < TickPriority.EXTREMELY_HIGH.priority ? EXTREMELY_HIGH : EXTREMELY_LOW;
    }

    public int getPriority() {
        return this.priority;
    }
}

