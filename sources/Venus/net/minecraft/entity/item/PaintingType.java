/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import net.minecraft.util.registry.Registry;

public class PaintingType {
    public static final PaintingType KEBAB = PaintingType.register("kebab", 16, 16);
    public static final PaintingType AZTEC = PaintingType.register("aztec", 16, 16);
    public static final PaintingType ALBAN = PaintingType.register("alban", 16, 16);
    public static final PaintingType AZTEC2 = PaintingType.register("aztec2", 16, 16);
    public static final PaintingType BOMB = PaintingType.register("bomb", 16, 16);
    public static final PaintingType PLANT = PaintingType.register("plant", 16, 16);
    public static final PaintingType WASTELAND = PaintingType.register("wasteland", 16, 16);
    public static final PaintingType POOL = PaintingType.register("pool", 32, 16);
    public static final PaintingType COURBET = PaintingType.register("courbet", 32, 16);
    public static final PaintingType SEA = PaintingType.register("sea", 32, 16);
    public static final PaintingType SUNSET = PaintingType.register("sunset", 32, 16);
    public static final PaintingType CREEBET = PaintingType.register("creebet", 32, 16);
    public static final PaintingType WANDERER = PaintingType.register("wanderer", 16, 32);
    public static final PaintingType GRAHAM = PaintingType.register("graham", 16, 32);
    public static final PaintingType MATCH = PaintingType.register("match", 32, 32);
    public static final PaintingType BUST = PaintingType.register("bust", 32, 32);
    public static final PaintingType STAGE = PaintingType.register("stage", 32, 32);
    public static final PaintingType VOID = PaintingType.register("void", 32, 32);
    public static final PaintingType SKULL_AND_ROSES = PaintingType.register("skull_and_roses", 32, 32);
    public static final PaintingType WITHER = PaintingType.register("wither", 32, 32);
    public static final PaintingType FIGHTERS = PaintingType.register("fighters", 64, 32);
    public static final PaintingType POINTER = PaintingType.register("pointer", 64, 64);
    public static final PaintingType PIGSCENE = PaintingType.register("pigscene", 64, 64);
    public static final PaintingType BURNING_SKULL = PaintingType.register("burning_skull", 64, 64);
    public static final PaintingType SKELETON = PaintingType.register("skeleton", 64, 48);
    public static final PaintingType DONKEY_KONG = PaintingType.register("donkey_kong", 64, 48);
    private final int width;
    private final int height;

    private static PaintingType register(String string, int n, int n2) {
        return Registry.register(Registry.MOTIVE, string, new PaintingType(n, n2));
    }

    public PaintingType(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

