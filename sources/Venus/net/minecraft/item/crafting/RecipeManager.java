/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeManager
extends JsonReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ImmutableMap.of();
    private boolean someRecipesErrored;

    public RecipeManager() {
        super(GSON, "recipes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.someRecipesErrored = false;
        HashMap<IRecipeType, ImmutableMap.Builder> hashMap = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            try {
                IRecipe<?> iRecipe = RecipeManager.deserializeRecipe(resourceLocation, JSONUtils.getJsonObject(entry.getValue(), "top element"));
                hashMap.computeIfAbsent(iRecipe.getType(), RecipeManager::lambda$apply$0).put(resourceLocation, iRecipe);
            } catch (JsonParseException | IllegalArgumentException runtimeException) {
                LOGGER.error("Parsing error loading recipe {}", (Object)resourceLocation, (Object)runtimeException);
            }
        }
        this.recipes = hashMap.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, RecipeManager::lambda$apply$1));
        LOGGER.info("Loaded {} recipes", (Object)hashMap.size());
    }

    public <C extends IInventory, T extends IRecipe<C>> Optional<T> getRecipe(IRecipeType<T> iRecipeType, C c, World world) {
        return this.getRecipes(iRecipeType).values().stream().flatMap(arg_0 -> RecipeManager.lambda$getRecipe$2(iRecipeType, world, c, arg_0)).findFirst();
    }

    public <C extends IInventory, T extends IRecipe<C>> List<T> getRecipesForType(IRecipeType<T> iRecipeType) {
        return this.getRecipes(iRecipeType).values().stream().map(RecipeManager::lambda$getRecipesForType$3).collect(Collectors.toList());
    }

    public <C extends IInventory, T extends IRecipe<C>> List<T> getRecipes(IRecipeType<T> iRecipeType, C c, World world) {
        return this.getRecipes(iRecipeType).values().stream().flatMap(arg_0 -> RecipeManager.lambda$getRecipes$4(iRecipeType, world, c, arg_0)).sorted(Comparator.comparing(RecipeManager::lambda$getRecipes$5)).collect(Collectors.toList());
    }

    private <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, IRecipe<C>> getRecipes(IRecipeType<T> iRecipeType) {
        return this.recipes.getOrDefault(iRecipeType, Collections.emptyMap());
    }

    public <C extends IInventory, T extends IRecipe<C>> NonNullList<ItemStack> getRecipeNonNull(IRecipeType<T> iRecipeType, C c, World world) {
        Optional<T> optional = this.getRecipe(iRecipeType, c, world);
        if (optional.isPresent()) {
            return ((IRecipe)optional.get()).getRemainingItems(c);
        }
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(c.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonNullList.size(); ++i) {
            nonNullList.set(i, c.getStackInSlot(i));
        }
        return nonNullList;
    }

    public Optional<? extends IRecipe<?>> getRecipe(ResourceLocation resourceLocation) {
        return this.recipes.values().stream().map(arg_0 -> RecipeManager.lambda$getRecipe$6(resourceLocation, arg_0)).filter(Objects::nonNull).findFirst();
    }

    public Collection<IRecipe<?>> getRecipes() {
        return this.recipes.values().stream().flatMap(RecipeManager::lambda$getRecipes$7).collect(Collectors.toSet());
    }

    public Stream<ResourceLocation> getKeys() {
        return this.recipes.values().stream().flatMap(RecipeManager::lambda$getKeys$8);
    }

    public static IRecipe<?> deserializeRecipe(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String string = JSONUtils.getString(jsonObject, "type");
        return Registry.RECIPE_SERIALIZER.getOptional(new ResourceLocation(string)).orElseThrow(() -> RecipeManager.lambda$deserializeRecipe$9(string)).read(resourceLocation, jsonObject);
    }

    public void deserializeRecipes(Iterable<IRecipe<?>> iterable) {
        this.someRecipesErrored = false;
        HashMap hashMap = Maps.newHashMap();
        iterable.forEach(arg_0 -> RecipeManager.lambda$deserializeRecipes$11(hashMap, arg_0));
        this.recipes = ImmutableMap.copyOf(hashMap);
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((Map)object, iResourceManager, iProfiler);
    }

    private static void lambda$deserializeRecipes$11(Map map, IRecipe iRecipe) {
        Map map2 = map.computeIfAbsent(iRecipe.getType(), RecipeManager::lambda$deserializeRecipes$10);
        IRecipe iRecipe2 = map2.put(iRecipe.getId(), iRecipe);
        if (iRecipe2 != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + iRecipe.getId());
        }
    }

    private static Map lambda$deserializeRecipes$10(IRecipeType iRecipeType) {
        return Maps.newHashMap();
    }

    private static JsonSyntaxException lambda$deserializeRecipe$9(String string) {
        return new JsonSyntaxException("Invalid or unsupported recipe type '" + string + "'");
    }

    private static Stream lambda$getKeys$8(Map map) {
        return map.keySet().stream();
    }

    private static Stream lambda$getRecipes$7(Map map) {
        return map.values().stream();
    }

    private static IRecipe lambda$getRecipe$6(ResourceLocation resourceLocation, Map map) {
        return (IRecipe)map.get(resourceLocation);
    }

    private static String lambda$getRecipes$5(IRecipe iRecipe) {
        return iRecipe.getRecipeOutput().getTranslationKey();
    }

    private static Stream lambda$getRecipes$4(IRecipeType iRecipeType, World world, IInventory iInventory, IRecipe iRecipe) {
        return Util.streamOptional(iRecipeType.matches(iRecipe, world, iInventory));
    }

    private static IRecipe lambda$getRecipesForType$3(IRecipe iRecipe) {
        return iRecipe;
    }

    private static Stream lambda$getRecipe$2(IRecipeType iRecipeType, World world, IInventory iInventory, IRecipe iRecipe) {
        return Util.streamOptional(iRecipeType.matches(iRecipe, world, iInventory));
    }

    private static Map lambda$apply$1(Map.Entry entry) {
        return ((ImmutableMap.Builder)entry.getValue()).build();
    }

    private static ImmutableMap.Builder lambda$apply$0(IRecipeType iRecipeType) {
        return ImmutableMap.builder();
    }
}

