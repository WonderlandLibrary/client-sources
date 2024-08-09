/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;

public final class RecipeBookStatus {
    private static final Map<RecipeBookCategory, Pair<String, String>> field_242147_a = ImmutableMap.of(RecipeBookCategory.CRAFTING, Pair.of("isGuiOpen", "isFilteringCraftable"), RecipeBookCategory.FURNACE, Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"), RecipeBookCategory.BLAST_FURNACE, Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"), RecipeBookCategory.SMOKER, Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable"));
    private final Map<RecipeBookCategory, CategoryStatus> field_242148_b;

    private RecipeBookStatus(Map<RecipeBookCategory, CategoryStatus> map) {
        this.field_242148_b = map;
    }

    public RecipeBookStatus() {
        this(Util.make(Maps.newEnumMap(RecipeBookCategory.class), RecipeBookStatus::lambda$new$0));
    }

    public boolean func_242151_a(RecipeBookCategory recipeBookCategory) {
        return this.field_242148_b.get((Object)((Object)recipeBookCategory)).field_242162_a;
    }

    public void func_242152_a(RecipeBookCategory recipeBookCategory, boolean bl) {
        this.field_242148_b.get((Object)((Object)recipeBookCategory)).field_242162_a = bl;
    }

    public boolean func_242158_b(RecipeBookCategory recipeBookCategory) {
        return this.field_242148_b.get((Object)((Object)recipeBookCategory)).field_242163_b;
    }

    public void func_242159_b(RecipeBookCategory recipeBookCategory, boolean bl) {
        this.field_242148_b.get((Object)((Object)recipeBookCategory)).field_242163_b = bl;
    }

    public static RecipeBookStatus func_242157_a(PacketBuffer packetBuffer) {
        EnumMap<RecipeBookCategory, CategoryStatus> enumMap = Maps.newEnumMap(RecipeBookCategory.class);
        for (RecipeBookCategory recipeBookCategory : RecipeBookCategory.values()) {
            boolean bl = packetBuffer.readBoolean();
            boolean bl2 = packetBuffer.readBoolean();
            enumMap.put(recipeBookCategory, new CategoryStatus(bl, bl2));
        }
        return new RecipeBookStatus(enumMap);
    }

    public void func_242161_b(PacketBuffer packetBuffer) {
        for (RecipeBookCategory recipeBookCategory : RecipeBookCategory.values()) {
            CategoryStatus categoryStatus = this.field_242148_b.get((Object)recipeBookCategory);
            if (categoryStatus == null) {
                packetBuffer.writeBoolean(true);
                packetBuffer.writeBoolean(true);
                continue;
            }
            packetBuffer.writeBoolean(categoryStatus.field_242162_a);
            packetBuffer.writeBoolean(categoryStatus.field_242163_b);
        }
    }

    public static RecipeBookStatus func_242154_a(CompoundNBT compoundNBT) {
        EnumMap<RecipeBookCategory, CategoryStatus> enumMap = Maps.newEnumMap(RecipeBookCategory.class);
        field_242147_a.forEach((arg_0, arg_1) -> RecipeBookStatus.lambda$func_242154_a$1(compoundNBT, enumMap, arg_0, arg_1));
        return new RecipeBookStatus(enumMap);
    }

    public void func_242160_b(CompoundNBT compoundNBT) {
        field_242147_a.forEach((arg_0, arg_1) -> this.lambda$func_242160_b$2(compoundNBT, arg_0, arg_1));
    }

    public RecipeBookStatus func_242149_a() {
        EnumMap<RecipeBookCategory, CategoryStatus> enumMap = Maps.newEnumMap(RecipeBookCategory.class);
        for (RecipeBookCategory recipeBookCategory : RecipeBookCategory.values()) {
            CategoryStatus categoryStatus = this.field_242148_b.get((Object)recipeBookCategory);
            enumMap.put(recipeBookCategory, categoryStatus.func_242164_a());
        }
        return new RecipeBookStatus(enumMap);
    }

    public void func_242150_a(RecipeBookStatus recipeBookStatus) {
        this.field_242148_b.clear();
        for (RecipeBookCategory recipeBookCategory : RecipeBookCategory.values()) {
            CategoryStatus categoryStatus = recipeBookStatus.field_242148_b.get((Object)recipeBookCategory);
            this.field_242148_b.put(recipeBookCategory, categoryStatus.func_242164_a());
        }
    }

    public boolean equals(Object object) {
        return this == object || object instanceof RecipeBookStatus && this.field_242148_b.equals(((RecipeBookStatus)object).field_242148_b);
    }

    public int hashCode() {
        return this.field_242148_b.hashCode();
    }

    private void lambda$func_242160_b$2(CompoundNBT compoundNBT, RecipeBookCategory recipeBookCategory, Pair pair) {
        CategoryStatus categoryStatus = this.field_242148_b.get((Object)recipeBookCategory);
        compoundNBT.putBoolean((String)pair.getFirst(), categoryStatus.field_242162_a);
        compoundNBT.putBoolean((String)pair.getSecond(), categoryStatus.field_242163_b);
    }

    private static void lambda$func_242154_a$1(CompoundNBT compoundNBT, Map map, RecipeBookCategory recipeBookCategory, Pair pair) {
        boolean bl = compoundNBT.getBoolean((String)pair.getFirst());
        boolean bl2 = compoundNBT.getBoolean((String)pair.getSecond());
        map.put(recipeBookCategory, new CategoryStatus(bl, bl2));
    }

    private static void lambda$new$0(EnumMap enumMap) {
        for (RecipeBookCategory recipeBookCategory : RecipeBookCategory.values()) {
            enumMap.put(recipeBookCategory, new CategoryStatus(false, false));
        }
    }

    static final class CategoryStatus {
        private boolean field_242162_a;
        private boolean field_242163_b;

        public CategoryStatus(boolean bl, boolean bl2) {
            this.field_242162_a = bl;
            this.field_242163_b = bl2;
        }

        public CategoryStatus func_242164_a() {
            return new CategoryStatus(this.field_242162_a, this.field_242163_b);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof CategoryStatus)) {
                return true;
            }
            CategoryStatus categoryStatus = (CategoryStatus)object;
            return this.field_242162_a == categoryStatus.field_242162_a && this.field_242163_b == categoryStatus.field_242163_b;
        }

        public int hashCode() {
            int n = this.field_242162_a ? 1 : 0;
            return 31 * n + (this.field_242163_b ? 1 : 0);
        }

        public String toString() {
            return "[open=" + this.field_242162_a + ", filtering=" + this.field_242163_b + "]";
        }
    }
}

