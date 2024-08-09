/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBook;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class ClientRecipeBook
extends RecipeBook {
    private static final Logger field_241555_k_ = LogManager.getLogger();
    private Map<RecipeBookCategories, List<RecipeList>> recipesByCategory = ImmutableMap.of();
    private List<RecipeList> allRecipes = ImmutableList.of();

    public void func_243196_a(Iterable<IRecipe<?>> iterable) {
        Map<RecipeBookCategories, List<List<IRecipe<?>>>> map = ClientRecipeBook.func_243201_b(iterable);
        HashMap hashMap = Maps.newHashMap();
        ImmutableList.Builder builder = ImmutableList.builder();
        map.forEach((arg_0, arg_1) -> ClientRecipeBook.lambda$func_243196_a$0(hashMap, builder, arg_0, arg_1));
        RecipeBookCategories.field_243235_w.forEach((arg_0, arg_1) -> ClientRecipeBook.lambda$func_243196_a$2(hashMap, arg_0, arg_1));
        this.recipesByCategory = ImmutableMap.copyOf(hashMap);
        this.allRecipes = builder.build();
    }

    private static Map<RecipeBookCategories, List<List<IRecipe<?>>>> func_243201_b(Iterable<IRecipe<?>> iterable) {
        HashMap<RecipeBookCategories, List<List<IRecipe<?>>>> hashMap = Maps.newHashMap();
        HashBasedTable hashBasedTable = HashBasedTable.create();
        for (IRecipe<?> iRecipe : iterable) {
            if (iRecipe.isDynamic()) continue;
            RecipeBookCategories recipeBookCategories = ClientRecipeBook.getCategory(iRecipe);
            String string = iRecipe.getGroup();
            if (string.isEmpty()) {
                hashMap.computeIfAbsent(recipeBookCategories, ClientRecipeBook::lambda$func_243201_b$3).add(ImmutableList.of(iRecipe));
                continue;
            }
            ArrayList<IRecipe<?>> arrayList = (ArrayList<IRecipe<?>>)hashBasedTable.get((Object)recipeBookCategories, string);
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
                hashBasedTable.put(recipeBookCategories, string, arrayList);
                hashMap.computeIfAbsent(recipeBookCategories, ClientRecipeBook::lambda$func_243201_b$4).add(arrayList);
            }
            arrayList.add(iRecipe);
        }
        return hashMap;
    }

    private static RecipeBookCategories getCategory(IRecipe<?> iRecipe) {
        IRecipeType<?> iRecipeType = iRecipe.getType();
        if (iRecipeType == IRecipeType.CRAFTING) {
            ItemStack itemStack = iRecipe.getRecipeOutput();
            ItemGroup itemGroup = itemStack.getItem().getGroup();
            if (itemGroup == ItemGroup.BUILDING_BLOCKS) {
                return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
            }
            if (itemGroup != ItemGroup.TOOLS && itemGroup != ItemGroup.COMBAT) {
                return itemGroup == ItemGroup.REDSTONE ? RecipeBookCategories.CRAFTING_REDSTONE : RecipeBookCategories.CRAFTING_MISC;
            }
            return RecipeBookCategories.CRAFTING_EQUIPMENT;
        }
        if (iRecipeType == IRecipeType.SMELTING) {
            if (iRecipe.getRecipeOutput().getItem().isFood()) {
                return RecipeBookCategories.FURNACE_FOOD;
            }
            return iRecipe.getRecipeOutput().getItem() instanceof BlockItem ? RecipeBookCategories.FURNACE_BLOCKS : RecipeBookCategories.FURNACE_MISC;
        }
        if (iRecipeType == IRecipeType.BLASTING) {
            return iRecipe.getRecipeOutput().getItem() instanceof BlockItem ? RecipeBookCategories.BLAST_FURNACE_BLOCKS : RecipeBookCategories.BLAST_FURNACE_MISC;
        }
        if (iRecipeType == IRecipeType.SMOKING) {
            return RecipeBookCategories.SMOKER_FOOD;
        }
        if (iRecipeType == IRecipeType.STONECUTTING) {
            return RecipeBookCategories.STONECUTTER;
        }
        if (iRecipeType == IRecipeType.CAMPFIRE_COOKING) {
            return RecipeBookCategories.CAMPFIRE;
        }
        if (iRecipeType == IRecipeType.SMITHING) {
            return RecipeBookCategories.SMITHING;
        }
        Supplier[] supplierArray = new Supplier[2];
        supplierArray[0] = () -> ClientRecipeBook.lambda$getCategory$5(iRecipe);
        supplierArray[1] = iRecipe::getId;
        field_241555_k_.warn("Unknown recipe category: {}/{}", supplierArray);
        return RecipeBookCategories.UNKNOWN;
    }

    public List<RecipeList> getRecipes() {
        return this.allRecipes;
    }

    public List<RecipeList> getRecipes(RecipeBookCategories recipeBookCategories) {
        return this.recipesByCategory.getOrDefault((Object)recipeBookCategories, Collections.emptyList());
    }

    private static Object lambda$getCategory$5(IRecipe iRecipe) {
        return Registry.RECIPE_TYPE.getKey(iRecipe.getType());
    }

    private static List lambda$func_243201_b$4(RecipeBookCategories recipeBookCategories) {
        return Lists.newArrayList();
    }

    private static List lambda$func_243201_b$3(RecipeBookCategories recipeBookCategories) {
        return Lists.newArrayList();
    }

    private static void lambda$func_243196_a$2(Map map, RecipeBookCategories recipeBookCategories, List list) {
        List list2 = map.put(recipeBookCategories, (List)list.stream().flatMap(arg_0 -> ClientRecipeBook.lambda$func_243196_a$1(map, arg_0)).collect(ImmutableList.toImmutableList()));
    }

    private static Stream lambda$func_243196_a$1(Map map, RecipeBookCategories recipeBookCategories) {
        return ((List)map.getOrDefault((Object)recipeBookCategories, ImmutableList.of())).stream();
    }

    private static void lambda$func_243196_a$0(Map map, ImmutableList.Builder builder, RecipeBookCategories recipeBookCategories, List list) {
        List list2 = map.put(recipeBookCategories, (List)list.stream().map(RecipeList::new).peek(builder::add).collect(ImmutableList.toImmutableList()));
    }
}

