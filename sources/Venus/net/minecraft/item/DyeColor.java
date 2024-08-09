/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.optifine.reflect.Reflector;

public enum DyeColor implements IStringSerializable
{
    WHITE(0, "white", 0xF9FFFE, MaterialColor.SNOW, 0xF0F0F0, 0xFFFFFF),
    ORANGE(1, "orange", 16351261, MaterialColor.ADOBE, 15435844, 16738335),
    MAGENTA(2, "magenta", 13061821, MaterialColor.MAGENTA, 12801229, 0xFF00FF),
    LIGHT_BLUE(3, "light_blue", 3847130, MaterialColor.LIGHT_BLUE, 6719955, 10141901),
    YELLOW(4, "yellow", 16701501, MaterialColor.YELLOW, 14602026, 0xFFFF00),
    LIME(5, "lime", 8439583, MaterialColor.LIME, 4312372, 0xBFFF00),
    PINK(6, "pink", 15961002, MaterialColor.PINK, 14188952, 16738740),
    GRAY(7, "gray", 4673362, MaterialColor.GRAY, 0x434343, 0x808080),
    LIGHT_GRAY(8, "light_gray", 0x9D9D97, MaterialColor.LIGHT_GRAY, 0xABABAB, 0xD3D3D3),
    CYAN(9, "cyan", 1481884, MaterialColor.CYAN, 2651799, 65535),
    PURPLE(10, "purple", 8991416, MaterialColor.PURPLE, 8073150, 10494192),
    BLUE(11, "blue", 3949738, MaterialColor.BLUE, 2437522, 255),
    BROWN(12, "brown", 8606770, MaterialColor.BROWN, 5320730, 9127187),
    GREEN(13, "green", 6192150, MaterialColor.GREEN, 3887386, 65280),
    RED(14, "red", 11546150, MaterialColor.RED, 11743532, 0xFF0000),
    BLACK(15, "black", 0x1D1D21, MaterialColor.BLACK, 0x1E1B1B, 0);

    private static final DyeColor[] VALUES;
    private static final Int2ObjectOpenHashMap<DyeColor> BY_FIREWORK_COLOR;
    private final int id;
    private final String translationKey;
    private final MaterialColor mapColor;
    private final int colorValue;
    private final int swappedColorValue;
    private float[] colorComponentValues;
    private final int fireworkColor;
    private final Tags.IOptionalNamedTag<Item> tag;
    private final int textColor;

    private DyeColor(int n2, String string2, int n3, MaterialColor materialColor, int n4, int n5) {
        this.id = n2;
        this.translationKey = string2;
        this.colorValue = n3;
        this.mapColor = materialColor;
        this.textColor = n5;
        int n6 = (n3 & 0xFF0000) >> 16;
        int n7 = (n3 & 0xFF00) >> 8;
        int n8 = (n3 & 0xFF) >> 0;
        this.swappedColorValue = n8 << 16 | n7 << 8 | n6 << 0;
        this.tag = (Tags.IOptionalNamedTag)Reflector.ForgeItemTags_createOptional.call((Object)new ResourceLocation("forge", "dyes/" + string2));
        this.colorComponentValues = new float[]{(float)n6 / 255.0f, (float)n7 / 255.0f, (float)n8 / 255.0f};
        this.fireworkColor = n4;
    }

    public int getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public float[] getColorComponentValues() {
        return this.colorComponentValues;
    }

    public MaterialColor getMapColor() {
        return this.mapColor;
    }

    public int getFireworkColor() {
        return this.fireworkColor;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public static DyeColor byId(int n) {
        if (n < 0 || n >= VALUES.length) {
            n = 0;
        }
        return VALUES[n];
    }

    public static DyeColor byTranslationKey(String string, DyeColor dyeColor) {
        for (DyeColor dyeColor2 : DyeColor.values()) {
            if (!dyeColor2.translationKey.equals(string)) continue;
            return dyeColor2;
        }
        return dyeColor;
    }

    @Nullable
    public static DyeColor byFireworkColor(int n) {
        return BY_FIREWORK_COLOR.get(n);
    }

    public String toString() {
        return this.translationKey;
    }

    @Override
    public String getString() {
        return this.translationKey;
    }

    public void setColorComponentValues(float[] fArray) {
        this.colorComponentValues = fArray;
    }

    public int getColorValue() {
        return this.colorValue;
    }

    public Tags.IOptionalNamedTag<Item> getTag() {
        return this.tag;
    }

    @Nullable
    public static DyeColor getColor(ItemStack itemStack) {
        if (itemStack.getItem() instanceof DyeItem) {
            return ((DyeItem)itemStack.getItem()).getDyeColor();
        }
        for (DyeColor dyeColor : VALUES) {
            if (!itemStack.getItem().isIn(dyeColor.getTag())) continue;
            return dyeColor;
        }
        return null;
    }

    private static DyeColor lambda$static$2(DyeColor dyeColor) {
        return dyeColor;
    }

    private static Integer lambda$static$1(DyeColor dyeColor) {
        return dyeColor.fireworkColor;
    }

    private static DyeColor[] lambda$static$0(int n) {
        return new DyeColor[n];
    }

    static {
        VALUES = (DyeColor[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).toArray(DyeColor::lambda$static$0);
        BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap<DyeColor>(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(DyeColor::lambda$static$1, DyeColor::lambda$static$2)));
    }
}

