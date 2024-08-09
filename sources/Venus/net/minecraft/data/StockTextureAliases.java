/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import javax.annotation.Nullable;

public final class StockTextureAliases {
    public static final StockTextureAliases ALL = StockTextureAliases.createTextureAlias("all");
    public static final StockTextureAliases TEXTURE = StockTextureAliases.createTextureAlias("texture", ALL);
    public static final StockTextureAliases PARTICLE = StockTextureAliases.createTextureAlias("particle", TEXTURE);
    public static final StockTextureAliases END = StockTextureAliases.createTextureAlias("end", ALL);
    public static final StockTextureAliases BOTTOM = StockTextureAliases.createTextureAlias("bottom", END);
    public static final StockTextureAliases TOP = StockTextureAliases.createTextureAlias("top", END);
    public static final StockTextureAliases FRONT = StockTextureAliases.createTextureAlias("front", ALL);
    public static final StockTextureAliases BACK = StockTextureAliases.createTextureAlias("back", ALL);
    public static final StockTextureAliases SIDE = StockTextureAliases.createTextureAlias("side", ALL);
    public static final StockTextureAliases NORTH = StockTextureAliases.createTextureAlias("north", SIDE);
    public static final StockTextureAliases SOUTH = StockTextureAliases.createTextureAlias("south", SIDE);
    public static final StockTextureAliases EAST = StockTextureAliases.createTextureAlias("east", SIDE);
    public static final StockTextureAliases WEST = StockTextureAliases.createTextureAlias("west", SIDE);
    public static final StockTextureAliases UP = StockTextureAliases.createTextureAlias("up");
    public static final StockTextureAliases DOWN = StockTextureAliases.createTextureAlias("down");
    public static final StockTextureAliases CROSS = StockTextureAliases.createTextureAlias("cross");
    public static final StockTextureAliases PLANT = StockTextureAliases.createTextureAlias("plant");
    public static final StockTextureAliases WALL = StockTextureAliases.createTextureAlias("wall", ALL);
    public static final StockTextureAliases RAIL = StockTextureAliases.createTextureAlias("rail");
    public static final StockTextureAliases WOOL = StockTextureAliases.createTextureAlias("wool");
    public static final StockTextureAliases PATTERN = StockTextureAliases.createTextureAlias("pattern");
    public static final StockTextureAliases PANE = StockTextureAliases.createTextureAlias("pane");
    public static final StockTextureAliases EDGE = StockTextureAliases.createTextureAlias("edge");
    public static final StockTextureAliases FAN = StockTextureAliases.createTextureAlias("fan");
    public static final StockTextureAliases STEM = StockTextureAliases.createTextureAlias("stem");
    public static final StockTextureAliases UPPERSTEM = StockTextureAliases.createTextureAlias("upperstem");
    public static final StockTextureAliases CROP = StockTextureAliases.createTextureAlias("crop");
    public static final StockTextureAliases DIRT = StockTextureAliases.createTextureAlias("dirt");
    public static final StockTextureAliases FIRE = StockTextureAliases.createTextureAlias("fire");
    public static final StockTextureAliases LANTERN = StockTextureAliases.createTextureAlias("lantern");
    public static final StockTextureAliases PLATFORM = StockTextureAliases.createTextureAlias("platform");
    public static final StockTextureAliases UNSTICKY = StockTextureAliases.createTextureAlias("unsticky");
    public static final StockTextureAliases TORCH = StockTextureAliases.createTextureAlias("torch");
    public static final StockTextureAliases LAYER_ZERO = StockTextureAliases.createTextureAlias("layer0");
    public static final StockTextureAliases LIT_LOG = StockTextureAliases.createTextureAlias("lit_log");
    private final String name;
    @Nullable
    private final StockTextureAliases textureAlias;

    private static StockTextureAliases createTextureAlias(String string) {
        return new StockTextureAliases(string, null);
    }

    private static StockTextureAliases createTextureAlias(String string, StockTextureAliases stockTextureAliases) {
        return new StockTextureAliases(string, stockTextureAliases);
    }

    private StockTextureAliases(String string, @Nullable StockTextureAliases stockTextureAliases) {
        this.name = string;
        this.textureAlias = stockTextureAliases;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public StockTextureAliases getAlias() {
        return this.textureAlias;
    }

    public String toString() {
        return "#" + this.name;
    }
}

