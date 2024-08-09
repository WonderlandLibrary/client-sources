/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeProvider
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public RecipeProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        Path path = this.generator.getOutputFolder();
        HashSet hashSet = Sets.newHashSet();
        RecipeProvider.registerRecipes(arg_0 -> RecipeProvider.lambda$act$0(hashSet, directoryCache, path, arg_0));
        RecipeProvider.saveRecipeAdvancement(directoryCache, Advancement.Builder.builder().withCriterion("impossible", new ImpossibleTrigger.Instance()).serialize(), path.resolve("data/minecraft/advancements/recipes/root.json"));
    }

    private static void saveRecipe(DirectoryCache directoryCache, JsonObject jsonObject, Path path) {
        try {
            String string = GSON.toJson(jsonObject);
            String string2 = HASH_FUNCTION.hashUnencodedChars(string).toString();
            if (!Objects.equals(directoryCache.getPreviousHash(path), string2) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                    bufferedWriter.write(string);
                }
            }
            directoryCache.recordHash(path, string2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save recipe {}", (Object)path, (Object)iOException);
        }
    }

    private static void saveRecipeAdvancement(DirectoryCache directoryCache, JsonObject jsonObject, Path path) {
        try {
            String string = GSON.toJson(jsonObject);
            String string2 = HASH_FUNCTION.hashUnencodedChars(string).toString();
            if (!Objects.equals(directoryCache.getPreviousHash(path), string2) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                    bufferedWriter.write(string);
                }
            }
            directoryCache.recordHash(path, string2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save recipe advancement {}", (Object)path, (Object)iOException);
        }
    }

    private static void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        RecipeProvider.shapelessPlanksNew(consumer, Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS);
        RecipeProvider.shapelessPlanksNew(consumer, Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.OAK_PLANKS, ItemTags.OAK_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS);
        RecipeProvider.shapelessPlanks(consumer, Blocks.WARPED_PLANKS, ItemTags.WARPED_STEMS);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.ACACIA_WOOD, Blocks.ACACIA_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.BIRCH_WOOD, Blocks.BIRCH_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.JUNGLE_WOOD, Blocks.JUNGLE_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.OAK_WOOD, Blocks.OAK_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.SPRUCE_WOOD, Blocks.SPRUCE_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.CRIMSON_HYPHAE, Blocks.CRIMSON_STEM);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.WARPED_HYPHAE, Blocks.WARPED_STEM);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_ACACIA_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_OAK_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_STEM);
        RecipeProvider.shapelessStrippedToPlanks(consumer, Blocks.STRIPPED_WARPED_HYPHAE, Blocks.STRIPPED_WARPED_STEM);
        RecipeProvider.shapedBoat(consumer, Items.ACACIA_BOAT, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedBoat(consumer, Items.BIRCH_BOAT, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedBoat(consumer, Items.DARK_OAK_BOAT, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedBoat(consumer, Items.JUNGLE_BOAT, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedBoat(consumer, Items.OAK_BOAT, Blocks.OAK_PLANKS);
        RecipeProvider.shapedBoat(consumer, Items.SPRUCE_BOAT, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.ACACIA_BUTTON, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.ACACIA_DOOR, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.ACACIA_FENCE, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.ACACIA_FENCE_GATE, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.ACACIA_PRESSURE_PLATE, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.ACACIA_STAIRS, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.ACACIA_TRAPDOOR, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.ACACIA_SIGN, Blocks.ACACIA_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.BIRCH_BUTTON, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.BIRCH_DOOR, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.BIRCH_FENCE, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.BIRCH_FENCE_GATE, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.BIRCH_PRESSURE_PLATE, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.BIRCH_STAIRS, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.BIRCH_TRAPDOOR, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.BIRCH_SIGN, Blocks.BIRCH_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.CRIMSON_BUTTON, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.CRIMSON_DOOR, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.CRIMSON_FENCE, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.CRIMSON_FENCE_GATE, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.CRIMSON_PRESSURE_PLATE, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.CRIMSON_TRAPDOOR, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.DARK_OAK_BUTTON, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.DARK_OAK_DOOR, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.DARK_OAK_FENCE_GATE, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.DARK_OAK_TRAPDOOR, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.JUNGLE_BUTTON, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.JUNGLE_DOOR, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.JUNGLE_FENCE, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.JUNGLE_FENCE_GATE, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.JUNGLE_TRAPDOOR, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.JUNGLE_SIGN, Blocks.JUNGLE_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.OAK_BUTTON, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.OAK_DOOR, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.OAK_FENCE, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.OAK_FENCE_GATE, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.OAK_PRESSURE_PLATE, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.OAK_SLAB, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.OAK_STAIRS, Blocks.OAK_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.OAK_TRAPDOOR, Blocks.OAK_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.OAK_SIGN, Blocks.OAK_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.SPRUCE_BUTTON, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.SPRUCE_DOOR, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.SPRUCE_FENCE, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.SPRUCE_FENCE_GATE, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.SPRUCE_TRAPDOOR, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.SPRUCE_SIGN, Blocks.SPRUCE_PLANKS);
        RecipeProvider.shapelessWoodenButton(consumer, Blocks.WARPED_BUTTON, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenDoor(consumer, Blocks.WARPED_DOOR, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenFence(consumer, Blocks.WARPED_FENCE, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenFenceGate(consumer, Blocks.WARPED_FENCE_GATE, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenPressurePlate(consumer, Blocks.WARPED_PRESSURE_PLATE, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenSlab(consumer, Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenStairs(consumer, Blocks.WARPED_STAIRS, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedWoodenTrapdoor(consumer, Blocks.WARPED_TRAPDOOR, Blocks.WARPED_PLANKS);
        RecipeProvider.shapedSign(consumer, Blocks.WARPED_SIGN, Blocks.WARPED_PLANKS);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.BLACK_WOOL, Items.BLACK_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.BLACK_CARPET, Blocks.BLACK_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.BLACK_CARPET, Items.BLACK_DYE);
        RecipeProvider.shapedBed(consumer, Items.BLACK_BED, Blocks.BLACK_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.BLACK_BED, Items.BLACK_DYE);
        RecipeProvider.shapedBanner(consumer, Items.BLACK_BANNER, Blocks.BLACK_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.BLUE_WOOL, Items.BLUE_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.BLUE_CARPET, Blocks.BLUE_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.BLUE_CARPET, Items.BLUE_DYE);
        RecipeProvider.shapedBed(consumer, Items.BLUE_BED, Blocks.BLUE_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.BLUE_BED, Items.BLUE_DYE);
        RecipeProvider.shapedBanner(consumer, Items.BLUE_BANNER, Blocks.BLUE_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.BROWN_WOOL, Items.BROWN_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.BROWN_CARPET, Blocks.BROWN_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.BROWN_CARPET, Items.BROWN_DYE);
        RecipeProvider.shapedBed(consumer, Items.BROWN_BED, Blocks.BROWN_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.BROWN_BED, Items.BROWN_DYE);
        RecipeProvider.shapedBanner(consumer, Items.BROWN_BANNER, Blocks.BROWN_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.CYAN_WOOL, Items.CYAN_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.CYAN_CARPET, Blocks.CYAN_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.CYAN_CARPET, Items.CYAN_DYE);
        RecipeProvider.shapedBed(consumer, Items.CYAN_BED, Blocks.CYAN_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.CYAN_BED, Items.CYAN_DYE);
        RecipeProvider.shapedBanner(consumer, Items.CYAN_BANNER, Blocks.CYAN_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.GRAY_WOOL, Items.GRAY_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.GRAY_CARPET, Blocks.GRAY_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.GRAY_CARPET, Items.GRAY_DYE);
        RecipeProvider.shapedBed(consumer, Items.GRAY_BED, Blocks.GRAY_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.GRAY_BED, Items.GRAY_DYE);
        RecipeProvider.shapedBanner(consumer, Items.GRAY_BANNER, Blocks.GRAY_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.GREEN_WOOL, Items.GREEN_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.GREEN_CARPET, Blocks.GREEN_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.GREEN_CARPET, Items.GREEN_DYE);
        RecipeProvider.shapedBed(consumer, Items.GREEN_BED, Blocks.GREEN_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.GREEN_BED, Items.GREEN_DYE);
        RecipeProvider.shapedBanner(consumer, Items.GREEN_BANNER, Blocks.GREEN_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_BLUE_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.LIGHT_BLUE_CARPET, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedBed(consumer, Items.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.LIGHT_BLUE_BED, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedBanner(consumer, Items.LIGHT_BLUE_BANNER, Blocks.LIGHT_BLUE_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.LIGHT_GRAY_CARPET, Blocks.LIGHT_GRAY_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.LIGHT_GRAY_CARPET, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedBed(consumer, Items.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.LIGHT_GRAY_BED, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedBanner(consumer, Items.LIGHT_GRAY_BANNER, Blocks.LIGHT_GRAY_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.LIME_WOOL, Items.LIME_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.LIME_CARPET, Blocks.LIME_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.LIME_CARPET, Items.LIME_DYE);
        RecipeProvider.shapedBed(consumer, Items.LIME_BED, Blocks.LIME_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.LIME_BED, Items.LIME_DYE);
        RecipeProvider.shapedBanner(consumer, Items.LIME_BANNER, Blocks.LIME_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.MAGENTA_WOOL, Items.MAGENTA_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.MAGENTA_CARPET, Blocks.MAGENTA_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.MAGENTA_CARPET, Items.MAGENTA_DYE);
        RecipeProvider.shapedBed(consumer, Items.MAGENTA_BED, Blocks.MAGENTA_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.MAGENTA_BED, Items.MAGENTA_DYE);
        RecipeProvider.shapedBanner(consumer, Items.MAGENTA_BANNER, Blocks.MAGENTA_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.ORANGE_WOOL, Items.ORANGE_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.ORANGE_CARPET, Blocks.ORANGE_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.ORANGE_CARPET, Items.ORANGE_DYE);
        RecipeProvider.shapedBed(consumer, Items.ORANGE_BED, Blocks.ORANGE_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.ORANGE_BED, Items.ORANGE_DYE);
        RecipeProvider.shapedBanner(consumer, Items.ORANGE_BANNER, Blocks.ORANGE_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.PINK_WOOL, Items.PINK_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.PINK_CARPET, Blocks.PINK_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.PINK_CARPET, Items.PINK_DYE);
        RecipeProvider.shapedBed(consumer, Items.PINK_BED, Blocks.PINK_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.PINK_BED, Items.PINK_DYE);
        RecipeProvider.shapedBanner(consumer, Items.PINK_BANNER, Blocks.PINK_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.PURPLE_WOOL, Items.PURPLE_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.PURPLE_CARPET, Blocks.PURPLE_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.PURPLE_CARPET, Items.PURPLE_DYE);
        RecipeProvider.shapedBed(consumer, Items.PURPLE_BED, Blocks.PURPLE_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.PURPLE_BED, Items.PURPLE_DYE);
        RecipeProvider.shapedBanner(consumer, Items.PURPLE_BANNER, Blocks.PURPLE_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.RED_WOOL, Items.RED_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.RED_CARPET, Blocks.RED_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.RED_CARPET, Items.RED_DYE);
        RecipeProvider.shapedBed(consumer, Items.RED_BED, Blocks.RED_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.RED_BED, Items.RED_DYE);
        RecipeProvider.shapedBanner(consumer, Items.RED_BANNER, Blocks.RED_WOOL);
        RecipeProvider.shapedCarpet(consumer, Blocks.WHITE_CARPET, Blocks.WHITE_WOOL);
        RecipeProvider.shapedBed(consumer, Items.WHITE_BED, Blocks.WHITE_WOOL);
        RecipeProvider.shapedBanner(consumer, Items.WHITE_BANNER, Blocks.WHITE_WOOL);
        RecipeProvider.shapelessColoredWool(consumer, Blocks.YELLOW_WOOL, Items.YELLOW_DYE);
        RecipeProvider.shapedCarpet(consumer, Blocks.YELLOW_CARPET, Blocks.YELLOW_WOOL);
        RecipeProvider.shapelessColoredCarpet(consumer, Blocks.YELLOW_CARPET, Items.YELLOW_DYE);
        RecipeProvider.shapedBed(consumer, Items.YELLOW_BED, Blocks.YELLOW_WOOL);
        RecipeProvider.shapedColoredBed(consumer, Items.YELLOW_BED, Items.YELLOW_DYE);
        RecipeProvider.shapedBanner(consumer, Items.YELLOW_BANNER, Blocks.YELLOW_WOOL);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.BLACK_STAINED_GLASS, Items.BLACK_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLACK_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.BLACK_STAINED_GLASS_PANE, Items.BLACK_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.BLUE_STAINED_GLASS, Items.BLUE_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.BLUE_STAINED_GLASS_PANE, Items.BLUE_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.BROWN_STAINED_GLASS, Items.BROWN_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.BROWN_STAINED_GLASS_PANE, Items.BROWN_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.CYAN_STAINED_GLASS, Items.CYAN_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.CYAN_STAINED_GLASS_PANE, Items.CYAN_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.GRAY_STAINED_GLASS, Items.GRAY_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.GRAY_STAINED_GLASS_PANE, Items.GRAY_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.GREEN_STAINED_GLASS, Items.GREEN_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.GREEN_STAINED_GLASS_PANE, Items.GREEN_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.LIGHT_BLUE_STAINED_GLASS, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.LIGHT_GRAY_STAINED_GLASS, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.LIME_STAINED_GLASS, Items.LIME_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.LIME_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.LIME_STAINED_GLASS_PANE, Items.LIME_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.MAGENTA_STAINED_GLASS, Items.MAGENTA_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.MAGENTA_STAINED_GLASS_PANE, Items.MAGENTA_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.ORANGE_STAINED_GLASS, Items.ORANGE_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.ORANGE_STAINED_GLASS_PANE, Items.ORANGE_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.PINK_STAINED_GLASS, Items.PINK_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.PINK_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.PINK_STAINED_GLASS_PANE, Items.PINK_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.PURPLE_STAINED_GLASS, Items.PURPLE_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.PURPLE_STAINED_GLASS_PANE, Items.PURPLE_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.RED_STAINED_GLASS, Items.RED_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.RED_STAINED_GLASS_PANE, Items.RED_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.WHITE_STAINED_GLASS, Items.WHITE_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.WHITE_STAINED_GLASS_PANE, Items.WHITE_DYE);
        RecipeProvider.shapedColoredGlass(consumer, Blocks.YELLOW_STAINED_GLASS, Items.YELLOW_DYE);
        RecipeProvider.shapedGlassPane(consumer, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS);
        RecipeProvider.shapedColoredPane(consumer, Blocks.YELLOW_STAINED_GLASS_PANE, Items.YELLOW_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.BLACK_TERRACOTTA, Items.BLACK_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.BLUE_TERRACOTTA, Items.BLUE_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.BROWN_TERRACOTTA, Items.BROWN_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.CYAN_TERRACOTTA, Items.CYAN_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.GRAY_TERRACOTTA, Items.GRAY_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.GREEN_TERRACOTTA, Items.GREEN_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.LIME_TERRACOTTA, Items.LIME_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.MAGENTA_TERRACOTTA, Items.MAGENTA_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.ORANGE_TERRACOTTA, Items.ORANGE_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.PINK_TERRACOTTA, Items.PINK_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.PURPLE_TERRACOTTA, Items.PURPLE_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.RED_TERRACOTTA, Items.RED_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.WHITE_TERRACOTTA, Items.WHITE_DYE);
        RecipeProvider.shapedColoredTerracotta(consumer, Blocks.YELLOW_TERRACOTTA, Items.YELLOW_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.BLACK_CONCRETE_POWDER, Items.BLACK_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.BLUE_CONCRETE_POWDER, Items.BLUE_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.BROWN_CONCRETE_POWDER, Items.BROWN_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.CYAN_CONCRETE_POWDER, Items.CYAN_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.GRAY_CONCRETE_POWDER, Items.GRAY_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.GREEN_CONCRETE_POWDER, Items.GREEN_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.LIME_CONCRETE_POWDER, Items.LIME_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.ORANGE_CONCRETE_POWDER, Items.ORANGE_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.PINK_CONCRETE_POWDER, Items.PINK_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.PURPLE_CONCRETE_POWDER, Items.PURPLE_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.RED_CONCRETE_POWDER, Items.RED_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.WHITE_CONCRETE_POWDER, Items.WHITE_DYE);
        RecipeProvider.shapedColorConcretePowder(consumer, Blocks.YELLOW_CONCRETE_POWDER, Items.YELLOW_DYE);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ACTIVATOR_RAIL, 6).key(Character.valueOf('#'), Blocks.REDSTONE_TORCH).key(Character.valueOf('S'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XSX").patternLine("X#X").patternLine("XSX").addCriterion("has_rail", RecipeProvider.hasItem(Blocks.RAIL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.ANDESITE, 2).addIngredient(Blocks.DIORITE).addIngredient(Blocks.COBBLESTONE).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ANVIL).key(Character.valueOf('I'), Blocks.IRON_BLOCK).key(Character.valueOf('i'), Items.IRON_INGOT).patternLine("III").patternLine(" i ").patternLine("iii").addCriterion("has_iron_block", RecipeProvider.hasItem(Blocks.IRON_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.ARMOR_STAND).key(Character.valueOf('/'), Items.STICK).key(Character.valueOf('_'), Blocks.SMOOTH_STONE_SLAB).patternLine("///").patternLine(" / ").patternLine("/_/").addCriterion("has_stone_slab", RecipeProvider.hasItem(Blocks.SMOOTH_STONE_SLAB)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.ARROW, 4).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.FLINT).key(Character.valueOf('Y'), Items.FEATHER).patternLine("X").patternLine("#").patternLine("Y").addCriterion("has_feather", RecipeProvider.hasItem(Items.FEATHER)).addCriterion("has_flint", RecipeProvider.hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BARREL, 1).key(Character.valueOf('P'), ItemTags.PLANKS).key(Character.valueOf('S'), ItemTags.WOODEN_SLABS).patternLine("PSP").patternLine("P P").patternLine("PSP").addCriterion("has_planks", RecipeProvider.hasItem(ItemTags.PLANKS)).addCriterion("has_wood_slab", RecipeProvider.hasItem(ItemTags.WOODEN_SLABS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BEACON).key(Character.valueOf('S'), Items.NETHER_STAR).key(Character.valueOf('G'), Blocks.GLASS).key(Character.valueOf('O'), Blocks.OBSIDIAN).patternLine("GGG").patternLine("GSG").patternLine("OOO").addCriterion("has_nether_star", RecipeProvider.hasItem(Items.NETHER_STAR)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BEEHIVE).key(Character.valueOf('P'), ItemTags.PLANKS).key(Character.valueOf('H'), Items.HONEYCOMB).patternLine("PPP").patternLine("HHH").patternLine("PPP").addCriterion("has_honeycomb", RecipeProvider.hasItem(Items.HONEYCOMB)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BEETROOT_SOUP).addIngredient(Items.BOWL).addIngredient(Items.BEETROOT, 6).addCriterion("has_beetroot", RecipeProvider.hasItem(Items.BEETROOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(Items.INK_SAC).setGroup("black_dye").addCriterion("has_ink_sac", RecipeProvider.hasItem(Items.INK_SAC)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(Blocks.WITHER_ROSE).setGroup("black_dye").addCriterion("has_black_flower", RecipeProvider.hasItem(Blocks.WITHER_ROSE)).build(consumer, "black_dye_from_wither_rose");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLAZE_POWDER, 2).addIngredient(Items.BLAZE_ROD).addCriterion("has_blaze_rod", RecipeProvider.hasItem(Items.BLAZE_ROD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(Items.LAPIS_LAZULI).setGroup("blue_dye").addCriterion("has_lapis_lazuli", RecipeProvider.hasItem(Items.LAPIS_LAZULI)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(Blocks.CORNFLOWER).setGroup("blue_dye").addCriterion("has_blue_flower", RecipeProvider.hasItem(Blocks.CORNFLOWER)).build(consumer, "blue_dye_from_cornflower");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLUE_ICE).key(Character.valueOf('#'), Blocks.PACKED_ICE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_packed_ice", RecipeProvider.hasItem(Blocks.PACKED_ICE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BONE_BLOCK).key(Character.valueOf('X'), Items.BONE_MEAL).patternLine("XXX").patternLine("XXX").patternLine("XXX").addCriterion("has_bonemeal", RecipeProvider.hasItem(Items.BONE_MEAL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 3).addIngredient(Items.BONE).setGroup("bonemeal").addCriterion("has_bone", RecipeProvider.hasItem(Items.BONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL, 9).addIngredient(Blocks.BONE_BLOCK).setGroup("bonemeal").addCriterion("has_bone_block", RecipeProvider.hasItem(Blocks.BONE_BLOCK)).build(consumer, "bone_meal_from_bone_block");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BOOK).addIngredient(Items.PAPER, 3).addIngredient(Items.LEATHER).addCriterion("has_paper", RecipeProvider.hasItem(Items.PAPER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BOOKSHELF).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('X'), Items.BOOK).patternLine("###").patternLine("XXX").patternLine("###").addCriterion("has_book", RecipeProvider.hasItem(Items.BOOK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.BOW).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.STRING).patternLine(" #X").patternLine("# X").patternLine(" #X").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.BOWL, 4).key(Character.valueOf('#'), ItemTags.PLANKS).patternLine("# #").patternLine(" # ").addCriterion("has_brown_mushroom", RecipeProvider.hasItem(Blocks.BROWN_MUSHROOM)).addCriterion("has_red_mushroom", RecipeProvider.hasItem(Blocks.RED_MUSHROOM)).addCriterion("has_mushroom_stew", RecipeProvider.hasItem(Items.MUSHROOM_STEW)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.BREAD).key(Character.valueOf('#'), Items.WHEAT).patternLine("###").addCriterion("has_wheat", RecipeProvider.hasItem(Items.WHEAT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BREWING_STAND).key(Character.valueOf('B'), Items.BLAZE_ROD).key(Character.valueOf('#'), ItemTags.STONE_CRAFTING_MATERIALS).patternLine(" B ").patternLine("###").addCriterion("has_blaze_rod", RecipeProvider.hasItem(Items.BLAZE_ROD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICKS).key(Character.valueOf('#'), Items.BRICK).patternLine("##").patternLine("##").addCriterion("has_brick", RecipeProvider.hasItem(Items.BRICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.BRICKS).patternLine("###").addCriterion("has_brick_block", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_brick_block", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BROWN_DYE).addIngredient(Items.COCOA_BEANS).setGroup("brown_dye").addCriterion("has_cocoa_beans", RecipeProvider.hasItem(Items.COCOA_BEANS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.BUCKET).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("# #").patternLine(" # ").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CAKE).key(Character.valueOf('A'), Items.MILK_BUCKET).key(Character.valueOf('B'), Items.SUGAR).key(Character.valueOf('C'), Items.WHEAT).key(Character.valueOf('E'), Items.EGG).patternLine("AAA").patternLine("BEB").patternLine("CCC").addCriterion("has_egg", RecipeProvider.hasItem(Items.EGG)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CAMPFIRE).key(Character.valueOf('L'), ItemTags.LOGS).key(Character.valueOf('S'), Items.STICK).key(Character.valueOf('C'), ItemTags.COALS).patternLine(" S ").patternLine("SCS").patternLine("LLL").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).addCriterion("has_coal", RecipeProvider.hasItem(ItemTags.COALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.CARROT_ON_A_STICK).key(Character.valueOf('#'), Items.FISHING_ROD).key(Character.valueOf('X'), Items.CARROT).patternLine("# ").patternLine(" X").addCriterion("has_carrot", RecipeProvider.hasItem(Items.CARROT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.WARPED_FUNGUS_ON_A_STICK).key(Character.valueOf('#'), Items.FISHING_ROD).key(Character.valueOf('X'), Items.WARPED_FUNGUS).patternLine("# ").patternLine(" X").addCriterion("has_warped_fungus", RecipeProvider.hasItem(Items.WARPED_FUNGUS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CAULDRON).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("# #").patternLine("# #").patternLine("###").addCriterion("has_water_bucket", RecipeProvider.hasItem(Items.WATER_BUCKET)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COMPOSTER).key(Character.valueOf('#'), ItemTags.WOODEN_SLABS).patternLine("# #").patternLine("# #").patternLine("###").addCriterion("has_wood_slab", RecipeProvider.hasItem(ItemTags.WOODEN_SLABS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHEST).key(Character.valueOf('#'), ItemTags.PLANKS).patternLine("###").patternLine("# #").patternLine("###").addCriterion("has_lots_of_items", new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.atLeast(10), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, new ItemPredicate[0])).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.CHEST_MINECART).key(Character.valueOf('A'), Blocks.CHEST).key(Character.valueOf('B'), Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", RecipeProvider.hasItem(Items.MINECART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_NETHER_BRICKS).key(Character.valueOf('#'), Blocks.NETHER_BRICK_SLAB).patternLine("#").patternLine("#").addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_QUARTZ_BLOCK).key(Character.valueOf('#'), Blocks.QUARTZ_SLAB).patternLine("#").patternLine("#").addCriterion("has_chiseled_quartz_block", RecipeProvider.hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", RecipeProvider.hasItem(Blocks.QUARTZ_PILLAR)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_STONE_BRICKS).key(Character.valueOf('#'), Blocks.STONE_BRICK_SLAB).patternLine("#").patternLine("#").addCriterion("has_stone_bricks", RecipeProvider.hasItem(ItemTags.STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CLAY).key(Character.valueOf('#'), Items.CLAY_BALL).patternLine("##").patternLine("##").addCriterion("has_clay_ball", RecipeProvider.hasItem(Items.CLAY_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.CLOCK).key(Character.valueOf('#'), Items.GOLD_INGOT).key(Character.valueOf('X'), Items.REDSTONE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL, 9).addIngredient(Blocks.COAL_BLOCK).addCriterion("has_coal_block", RecipeProvider.hasItem(Blocks.COAL_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COAL_BLOCK).key(Character.valueOf('#'), Items.COAL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_coal", RecipeProvider.hasItem(Items.COAL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COARSE_DIRT, 4).key(Character.valueOf('D'), Blocks.DIRT).key(Character.valueOf('G'), Blocks.GRAVEL).patternLine("DG").patternLine("GD").addCriterion("has_gravel", RecipeProvider.hasItem(Blocks.GRAVEL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.COBBLESTONE).patternLine("###").addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_WALL, 6).key(Character.valueOf('#'), Blocks.COBBLESTONE).patternLine("###").patternLine("###").addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COMPARATOR).key(Character.valueOf('#'), Blocks.REDSTONE_TORCH).key(Character.valueOf('X'), Items.QUARTZ).key(Character.valueOf('I'), Blocks.STONE).patternLine(" # ").patternLine("#X#").patternLine("III").addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.COMPASS).key(Character.valueOf('#'), Items.IRON_INGOT).key(Character.valueOf('X'), Items.REDSTONE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.COOKIE, 8).key(Character.valueOf('#'), Items.WHEAT).key(Character.valueOf('X'), Items.COCOA_BEANS).patternLine("#X#").addCriterion("has_cocoa", RecipeProvider.hasItem(Items.COCOA_BEANS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CRAFTING_TABLE).key(Character.valueOf('#'), ItemTags.PLANKS).patternLine("##").patternLine("##").addCriterion("has_planks", RecipeProvider.hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.CROSSBOW).key(Character.valueOf('~'), Items.STRING).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('&'), Items.IRON_INGOT).key(Character.valueOf('$'), Blocks.TRIPWIRE_HOOK).patternLine("#&#").patternLine("~$~").patternLine(" # ").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).addCriterion("has_tripwire_hook", RecipeProvider.hasItem(Blocks.TRIPWIRE_HOOK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LOOM).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('@'), Items.STRING).patternLine("@@").patternLine("##").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_RED_SANDSTONE).key(Character.valueOf('#'), Blocks.RED_SANDSTONE_SLAB).patternLine("#").patternLine("#").addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", RecipeProvider.hasItem(Blocks.CHISELED_RED_SANDSTONE)).addCriterion("has_cut_red_sandstone", RecipeProvider.hasItem(Blocks.CUT_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_SANDSTONE).key(Character.valueOf('#'), Blocks.SANDSTONE_SLAB).patternLine("#").patternLine("#").addCriterion("has_stone_slab", RecipeProvider.hasItem(Blocks.SANDSTONE_SLAB)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.GREEN_DYE).addCriterion("has_green_dye", RecipeProvider.hasItem(Items.GREEN_DYE)).addCriterion("has_blue_dye", RecipeProvider.hasItem(Items.BLUE_DYE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE).key(Character.valueOf('S'), Items.PRISMARINE_SHARD).key(Character.valueOf('I'), Items.BLACK_DYE).patternLine("SSS").patternLine("SIS").patternLine("SSS").addCriterion("has_prismarine_shard", RecipeProvider.hasItem(Items.PRISMARINE_SHARD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_STAIRS, 4).key(Character.valueOf('#'), Blocks.PRISMARINE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.PRISMARINE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_prismarine_bricks", RecipeProvider.hasItem(Blocks.PRISMARINE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_STAIRS, 4).key(Character.valueOf('#'), Blocks.DARK_PRISMARINE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_dark_prismarine", RecipeProvider.hasItem(Blocks.DARK_PRISMARINE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DAYLIGHT_DETECTOR).key(Character.valueOf('Q'), Items.QUARTZ).key(Character.valueOf('G'), Blocks.GLASS).key(Character.valueOf('W'), Ingredient.fromTag(ItemTags.WOODEN_SLABS)).patternLine("GGG").patternLine("QQQ").patternLine("WWW").addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DETECTOR_RAIL, 6).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Blocks.STONE_PRESSURE_PLATE).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X X").patternLine("X#X").patternLine("XRX").addCriterion("has_rail", RecipeProvider.hasItem(Blocks.RAIL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.DIAMOND, 9).addIngredient(Blocks.DIAMOND_BLOCK).addCriterion("has_diamond_block", RecipeProvider.hasItem(Blocks.DIAMOND_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_AXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.DIAMOND).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIAMOND_BLOCK).key(Character.valueOf('#'), Items.DIAMOND).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_BOOTS).key(Character.valueOf('X'), Items.DIAMOND).patternLine("X X").patternLine("X X").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_CHESTPLATE).key(Character.valueOf('X'), Items.DIAMOND).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HELMET).key(Character.valueOf('X'), Items.DIAMOND).patternLine("XXX").patternLine("X X").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HOE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.DIAMOND).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_LEGGINGS).key(Character.valueOf('X'), Items.DIAMOND).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_PICKAXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.DIAMOND).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_SHOVEL).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.DIAMOND).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_SWORD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.DIAMOND).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE, 2).key(Character.valueOf('Q'), Items.QUARTZ).key(Character.valueOf('C'), Blocks.COBBLESTONE).patternLine("CQ").patternLine("QC").addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DISPENSER).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Blocks.COBBLESTONE).key(Character.valueOf('X'), Items.BOW).patternLine("###").patternLine("#X#").patternLine("#R#").addCriterion("has_bow", RecipeProvider.hasItem(Items.BOW)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DROPPER).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Blocks.COBBLESTONE).patternLine("###").patternLine("# #").patternLine("#R#").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.EMERALD, 9).addIngredient(Blocks.EMERALD_BLOCK).addCriterion("has_emerald_block", RecipeProvider.hasItem(Blocks.EMERALD_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.EMERALD_BLOCK).key(Character.valueOf('#'), Items.EMERALD).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_emerald", RecipeProvider.hasItem(Items.EMERALD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ENCHANTING_TABLE).key(Character.valueOf('B'), Items.BOOK).key(Character.valueOf('#'), Blocks.OBSIDIAN).key(Character.valueOf('D'), Items.DIAMOND).patternLine(" B ").patternLine("D#D").patternLine("###").addCriterion("has_obsidian", RecipeProvider.hasItem(Blocks.OBSIDIAN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ENDER_CHEST).key(Character.valueOf('#'), Blocks.OBSIDIAN).key(Character.valueOf('E'), Items.ENDER_EYE).patternLine("###").patternLine("#E#").patternLine("###").addCriterion("has_ender_eye", RecipeProvider.hasItem(Items.ENDER_EYE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ENDER_EYE).addIngredient(Items.ENDER_PEARL).addIngredient(Items.BLAZE_POWDER).addCriterion("has_blaze_powder", RecipeProvider.hasItem(Items.BLAZE_POWDER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICKS, 4).key(Character.valueOf('#'), Blocks.END_STONE).patternLine("##").patternLine("##").addCriterion("has_end_stone", RecipeProvider.hasItem(Blocks.END_STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.END_CRYSTAL).key(Character.valueOf('T'), Items.GHAST_TEAR).key(Character.valueOf('E'), Items.ENDER_EYE).key(Character.valueOf('G'), Blocks.GLASS).patternLine("GGG").patternLine("GEG").patternLine("GTG").addCriterion("has_ender_eye", RecipeProvider.hasItem(Items.ENDER_EYE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_ROD, 4).key(Character.valueOf('#'), Items.POPPED_CHORUS_FRUIT).key(Character.valueOf('/'), Items.BLAZE_ROD).patternLine("/").patternLine("#").addCriterion("has_chorus_fruit_popped", RecipeProvider.hasItem(Items.POPPED_CHORUS_FRUIT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FERMENTED_SPIDER_EYE).addIngredient(Items.SPIDER_EYE).addIngredient(Blocks.BROWN_MUSHROOM).addIngredient(Items.SUGAR).addCriterion("has_spider_eye", RecipeProvider.hasItem(Items.SPIDER_EYE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FIRE_CHARGE, 3).addIngredient(Items.GUNPOWDER).addIngredient(Items.BLAZE_POWDER).addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).addCriterion("has_blaze_powder", RecipeProvider.hasItem(Items.BLAZE_POWDER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.FISHING_ROD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.STRING).patternLine("  #").patternLine(" #X").patternLine("# X").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FLINT_AND_STEEL).addIngredient(Items.IRON_INGOT).addIngredient(Items.FLINT).addCriterion("has_flint", RecipeProvider.hasItem(Items.FLINT)).addCriterion("has_obsidian", RecipeProvider.hasItem(Blocks.OBSIDIAN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.FLOWER_POT).key(Character.valueOf('#'), Items.BRICK).patternLine("# #").patternLine(" # ").addCriterion("has_brick", RecipeProvider.hasItem(Items.BRICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.FURNACE).key(Character.valueOf('#'), ItemTags.STONE_CRAFTING_MATERIALS).patternLine("###").patternLine("# #").patternLine("###").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_CRAFTING_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.FURNACE_MINECART).key(Character.valueOf('A'), Blocks.FURNACE).key(Character.valueOf('B'), Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", RecipeProvider.hasItem(Items.MINECART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GLASS_BOTTLE, 3).key(Character.valueOf('#'), Blocks.GLASS).patternLine("# #").patternLine(" # ").addCriterion("has_glass", RecipeProvider.hasItem(Blocks.GLASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GLASS_PANE, 16).key(Character.valueOf('#'), Blocks.GLASS).patternLine("###").patternLine("###").addCriterion("has_glass", RecipeProvider.hasItem(Blocks.GLASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GLOWSTONE).key(Character.valueOf('#'), Items.GLOWSTONE_DUST).patternLine("##").patternLine("##").addCriterion("has_glowstone_dust", RecipeProvider.hasItem(Items.GLOWSTONE_DUST)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_APPLE).key(Character.valueOf('#'), Items.GOLD_INGOT).key(Character.valueOf('X'), Items.APPLE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_AXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_BOOTS).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("X X").patternLine("X X").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CARROT).key(Character.valueOf('#'), Items.GOLD_NUGGET).key(Character.valueOf('X'), Items.CARROT).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_gold_nugget", RecipeProvider.hasItem(Items.GOLD_NUGGET)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_CHESTPLATE).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HELMET).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("XXX").patternLine("X X").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HOE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_LEGGINGS).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_PICKAXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POWERED_RAIL, 6).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("X X").patternLine("X#X").patternLine("XRX").addCriterion("has_rail", RecipeProvider.hasItem(Blocks.RAIL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SHOVEL).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_SWORD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.GOLD_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GOLD_BLOCK).key(Character.valueOf('#'), Items.GOLD_INGOT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_INGOT, 9).addIngredient(Blocks.GOLD_BLOCK).setGroup("gold_ingot").addCriterion("has_gold_block", RecipeProvider.hasItem(Blocks.GOLD_BLOCK)).build(consumer, "gold_ingot_from_gold_block");
        ShapedRecipeBuilder.shapedRecipe(Items.GOLD_INGOT).key(Character.valueOf('#'), Items.GOLD_NUGGET).patternLine("###").patternLine("###").patternLine("###").setGroup("gold_ingot").addCriterion("has_gold_nugget", RecipeProvider.hasItem(Items.GOLD_NUGGET)).build(consumer, "gold_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GOLD_NUGGET, 9).addIngredient(Items.GOLD_INGOT).addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.GRANITE).addIngredient(Blocks.DIORITE).addIngredient(Items.QUARTZ).addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE, 2).addIngredient(Items.BLACK_DYE).addIngredient(Items.WHITE_DYE).addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).addCriterion("has_black_dye", RecipeProvider.hasItem(Items.BLACK_DYE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HAY_BLOCK).key(Character.valueOf('#'), Items.WHEAT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_wheat", RecipeProvider.hasItem(Items.WHEAT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("##").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.HONEY_BOTTLE, 4).addIngredient(Items.HONEY_BLOCK).addIngredient(Items.GLASS_BOTTLE, 4).addCriterion("has_honey_block", RecipeProvider.hasItem(Blocks.HONEY_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HONEY_BLOCK, 1).key(Character.valueOf('S'), Items.HONEY_BOTTLE).patternLine("SS").patternLine("SS").addCriterion("has_honey_bottle", RecipeProvider.hasItem(Items.HONEY_BOTTLE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HONEYCOMB_BLOCK).key(Character.valueOf('H'), Items.HONEYCOMB).patternLine("HH").patternLine("HH").addCriterion("has_honeycomb", RecipeProvider.hasItem(Items.HONEYCOMB)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.HOPPER).key(Character.valueOf('C'), Blocks.CHEST).key(Character.valueOf('I'), Items.IRON_INGOT).patternLine("I I").patternLine("ICI").patternLine(" I ").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.HOPPER_MINECART).key(Character.valueOf('A'), Blocks.HOPPER).key(Character.valueOf('B'), Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", RecipeProvider.hasItem(Items.MINECART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_AXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BARS, 16).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("###").patternLine("###").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_BLOCK).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_BOOTS).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X X").patternLine("X X").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_CHESTPLATE).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_DOOR, 3).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("##").patternLine("##").patternLine("##").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_HELMET).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XXX").patternLine("X X").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_HOE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_INGOT, 9).addIngredient(Blocks.IRON_BLOCK).setGroup("iron_ingot").addCriterion("has_iron_block", RecipeProvider.hasItem(Blocks.IRON_BLOCK)).build(consumer, "iron_ingot_from_iron_block");
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_INGOT).key(Character.valueOf('#'), Items.IRON_NUGGET).patternLine("###").patternLine("###").patternLine("###").setGroup("iron_ingot").addCriterion("has_iron_nugget", RecipeProvider.hasItem(Items.IRON_NUGGET)).build(consumer, "iron_ingot_from_nuggets");
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_LEGGINGS).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.IRON_NUGGET, 9).addIngredient(Items.IRON_INGOT).addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_PICKAXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_SHOVEL).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_SWORD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.IRON_TRAPDOOR).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("##").patternLine("##").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.ITEM_FRAME).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.LEATHER).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JUKEBOX).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('X'), Items.DIAMOND).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_diamond", RecipeProvider.hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LADDER, 3).key(Character.valueOf('#'), Items.STICK).patternLine("# #").patternLine("###").patternLine("# #").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LAPIS_BLOCK).key(Character.valueOf('#'), Items.LAPIS_LAZULI).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_lapis", RecipeProvider.hasItem(Items.LAPIS_LAZULI)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LAPIS_LAZULI, 9).addIngredient(Blocks.LAPIS_BLOCK).addCriterion("has_lapis_block", RecipeProvider.hasItem(Blocks.LAPIS_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2).key(Character.valueOf('~'), Items.STRING).key(Character.valueOf('O'), Items.SLIME_BALL).patternLine("~~ ").patternLine("~O ").patternLine("  ~").addCriterion("has_slime_ball", RecipeProvider.hasItem(Items.SLIME_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER).key(Character.valueOf('#'), Items.RABBIT_HIDE).patternLine("##").patternLine("##").addCriterion("has_rabbit_hide", RecipeProvider.hasItem(Items.RABBIT_HIDE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_BOOTS).key(Character.valueOf('X'), Items.LEATHER).patternLine("X X").patternLine("X X").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_CHESTPLATE).key(Character.valueOf('X'), Items.LEATHER).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_HELMET).key(Character.valueOf('X'), Items.LEATHER).patternLine("XXX").patternLine("X X").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_LEGGINGS).key(Character.valueOf('X'), Items.LEATHER).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.LEATHER_HORSE_ARMOR).key(Character.valueOf('X'), Items.LEATHER).patternLine("X X").patternLine("XXX").patternLine("X X").addCriterion("has_leather", RecipeProvider.hasItem(Items.LEATHER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LECTERN).key(Character.valueOf('S'), ItemTags.WOODEN_SLABS).key(Character.valueOf('B'), Blocks.BOOKSHELF).patternLine("SSS").patternLine(" B ").patternLine(" S ").addCriterion("has_book", RecipeProvider.hasItem(Items.BOOK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LEVER).key(Character.valueOf('#'), Blocks.COBBLESTONE).key(Character.valueOf('X'), Items.STICK).patternLine("X").patternLine("#").addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE).addIngredient(Blocks.BLUE_ORCHID).setGroup("light_blue_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.BLUE_ORCHID)).build(consumer, "light_blue_dye_from_blue_orchid");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_BLUE_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.WHITE_DYE).setGroup("light_blue_dye").addCriterion("has_blue_dye", RecipeProvider.hasItem(Items.BLUE_DYE)).addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).build(consumer, "light_blue_dye_from_blue_white_dye");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.AZURE_BLUET).setGroup("light_gray_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.AZURE_BLUET)).build(consumer, "light_gray_dye_from_azure_bluet");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 2).addIngredient(Items.GRAY_DYE).addIngredient(Items.WHITE_DYE).setGroup("light_gray_dye").addCriterion("has_gray_dye", RecipeProvider.hasItem(Items.GRAY_DYE)).addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).build(consumer, "light_gray_dye_from_gray_white_dye");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE, 3).addIngredient(Items.BLACK_DYE).addIngredient(Items.WHITE_DYE, 2).setGroup("light_gray_dye").addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).addCriterion("has_black_dye", RecipeProvider.hasItem(Items.BLACK_DYE)).build(consumer, "light_gray_dye_from_black_white_dye");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.OXEYE_DAISY).setGroup("light_gray_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.OXEYE_DAISY)).build(consumer, "light_gray_dye_from_oxeye_daisy");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIGHT_GRAY_DYE).addIngredient(Blocks.WHITE_TULIP).setGroup("light_gray_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.WHITE_TULIP)).build(consumer, "light_gray_dye_from_white_tulip");
        ShapedRecipeBuilder.shapedRecipe(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE).key(Character.valueOf('#'), Items.GOLD_INGOT).patternLine("##").addCriterion("has_gold_ingot", RecipeProvider.hasItem(Items.GOLD_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.LIME_DYE, 2).addIngredient(Items.GREEN_DYE).addIngredient(Items.WHITE_DYE).addCriterion("has_green_dye", RecipeProvider.hasItem(Items.GREEN_DYE)).addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.JACK_O_LANTERN).key(Character.valueOf('A'), Blocks.CARVED_PUMPKIN).key(Character.valueOf('B'), Blocks.TORCH).patternLine("A").patternLine("B").addCriterion("has_carved_pumpkin", RecipeProvider.hasItem(Blocks.CARVED_PUMPKIN)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE).addIngredient(Blocks.ALLIUM).setGroup("magenta_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.ALLIUM)).build(consumer, "magenta_dye_from_allium");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 4).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE, 2).addIngredient(Items.WHITE_DYE).setGroup("magenta_dye").addCriterion("has_blue_dye", RecipeProvider.hasItem(Items.BLUE_DYE)).addCriterion("has_rose_red", RecipeProvider.hasItem(Items.RED_DYE)).addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).build(consumer, "magenta_dye_from_blue_red_white_dye");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 3).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE).addIngredient(Items.PINK_DYE).setGroup("magenta_dye").addCriterion("has_pink_dye", RecipeProvider.hasItem(Items.PINK_DYE)).addCriterion("has_blue_dye", RecipeProvider.hasItem(Items.BLUE_DYE)).addCriterion("has_red_dye", RecipeProvider.hasItem(Items.RED_DYE)).build(consumer, "magenta_dye_from_blue_red_pink");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2).addIngredient(Blocks.LILAC).setGroup("magenta_dye").addCriterion("has_double_plant", RecipeProvider.hasItem(Blocks.LILAC)).build(consumer, "magenta_dye_from_lilac");
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGENTA_DYE, 2).addIngredient(Items.PURPLE_DYE).addIngredient(Items.PINK_DYE).setGroup("magenta_dye").addCriterion("has_pink_dye", RecipeProvider.hasItem(Items.PINK_DYE)).addCriterion("has_purple_dye", RecipeProvider.hasItem(Items.PURPLE_DYE)).build(consumer, "magenta_dye_from_purple_and_pink");
        ShapedRecipeBuilder.shapedRecipe(Blocks.MAGMA_BLOCK).key(Character.valueOf('#'), Items.MAGMA_CREAM).patternLine("##").patternLine("##").addCriterion("has_magma_cream", RecipeProvider.hasItem(Items.MAGMA_CREAM)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM).addIngredient(Items.BLAZE_POWDER).addIngredient(Items.SLIME_BALL).addCriterion("has_blaze_powder", RecipeProvider.hasItem(Items.BLAZE_POWDER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.MAP).key(Character.valueOf('#'), Items.PAPER).key(Character.valueOf('X'), Items.COMPASS).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_compass", RecipeProvider.hasItem(Items.COMPASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MELON).key(Character.valueOf('M'), Items.MELON_SLICE).patternLine("MMM").patternLine("MMM").patternLine("MMM").addCriterion("has_melon", RecipeProvider.hasItem(Items.MELON_SLICE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MELON_SEEDS).addIngredient(Items.MELON_SLICE).addCriterion("has_melon", RecipeProvider.hasItem(Items.MELON_SLICE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.MINECART).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine("# #").patternLine("###").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_COBBLESTONE).addIngredient(Blocks.COBBLESTONE).addIngredient(Blocks.VINE).addCriterion("has_vine", RecipeProvider.hasItem(Blocks.VINE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_WALL, 6).key(Character.valueOf('#'), Blocks.MOSSY_COBBLESTONE).patternLine("###").patternLine("###").addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.MOSSY_STONE_BRICKS).addIngredient(Blocks.STONE_BRICKS).addIngredient(Blocks.VINE).addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MUSHROOM_STEW).addIngredient(Blocks.BROWN_MUSHROOM).addIngredient(Blocks.RED_MUSHROOM).addIngredient(Items.BOWL).addCriterion("has_mushroom_stew", RecipeProvider.hasItem(Items.MUSHROOM_STEW)).addCriterion("has_bowl", RecipeProvider.hasItem(Items.BOWL)).addCriterion("has_brown_mushroom", RecipeProvider.hasItem(Blocks.BROWN_MUSHROOM)).addCriterion("has_red_mushroom", RecipeProvider.hasItem(Blocks.RED_MUSHROOM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICKS).key(Character.valueOf('N'), Items.NETHER_BRICK).patternLine("NN").patternLine("NN").addCriterion("has_netherbrick", RecipeProvider.hasItem(Items.NETHER_BRICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_FENCE, 6).key(Character.valueOf('#'), Blocks.NETHER_BRICKS).key(Character.valueOf('-'), Items.NETHER_BRICK).patternLine("#-#").patternLine("#-#").addCriterion("has_nether_brick", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.NETHER_BRICKS).patternLine("###").addCriterion("has_nether_brick", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.NETHER_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_nether_brick", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_WART_BLOCK).key(Character.valueOf('#'), Items.NETHER_WART).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_nether_wart", RecipeProvider.hasItem(Items.NETHER_WART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NOTE_BLOCK).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('X'), Items.REDSTONE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.OBSERVER).key(Character.valueOf('Q'), Items.QUARTZ).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Blocks.COBBLESTONE).patternLine("###").patternLine("RRQ").patternLine("###").addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE).addIngredient(Blocks.ORANGE_TULIP).setGroup("orange_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.ORANGE_TULIP)).build(consumer, "orange_dye_from_orange_tulip");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE, 2).addIngredient(Items.RED_DYE).addIngredient(Items.YELLOW_DYE).setGroup("orange_dye").addCriterion("has_red_dye", RecipeProvider.hasItem(Items.RED_DYE)).addCriterion("has_yellow_dye", RecipeProvider.hasItem(Items.YELLOW_DYE)).build(consumer, "orange_dye_from_red_yellow");
        ShapedRecipeBuilder.shapedRecipe(Items.PAINTING).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Ingredient.fromTag(ItemTags.WOOL)).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_wool", RecipeProvider.hasItem(ItemTags.WOOL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.PAPER, 3).key(Character.valueOf('#'), Blocks.SUGAR_CANE).patternLine("###").addCriterion("has_reeds", RecipeProvider.hasItem(Blocks.SUGAR_CANE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_PILLAR, 2).key(Character.valueOf('#'), Blocks.QUARTZ_BLOCK).patternLine("#").patternLine("#").addCriterion("has_chiseled_quartz_block", RecipeProvider.hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", RecipeProvider.hasItem(Blocks.QUARTZ_PILLAR)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.PACKED_ICE).addIngredient(Blocks.ICE, 9).addCriterion("has_ice", RecipeProvider.hasItem(Blocks.ICE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2).addIngredient(Blocks.PEONY).setGroup("pink_dye").addCriterion("has_double_plant", RecipeProvider.hasItem(Blocks.PEONY)).build(consumer, "pink_dye_from_peony");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE).addIngredient(Blocks.PINK_TULIP).setGroup("pink_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.PINK_TULIP)).build(consumer, "pink_dye_from_pink_tulip");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PINK_DYE, 2).addIngredient(Items.RED_DYE).addIngredient(Items.WHITE_DYE).setGroup("pink_dye").addCriterion("has_white_dye", RecipeProvider.hasItem(Items.WHITE_DYE)).addCriterion("has_red_dye", RecipeProvider.hasItem(Items.RED_DYE)).build(consumer, "pink_dye_from_red_white_dye");
        ShapedRecipeBuilder.shapedRecipe(Blocks.PISTON).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('#'), Blocks.COBBLESTONE).key(Character.valueOf('T'), ItemTags.PLANKS).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("TTT").patternLine("#X#").patternLine("#R#").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BASALT, 4).key(Character.valueOf('S'), Blocks.BASALT).patternLine("SS").patternLine("SS").addCriterion("has_basalt", RecipeProvider.hasItem(Blocks.BASALT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE, 4).key(Character.valueOf('S'), Blocks.GRANITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE, 4).key(Character.valueOf('S'), Blocks.DIORITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE, 4).key(Character.valueOf('S'), Blocks.ANDESITE).patternLine("SS").patternLine("SS").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE).key(Character.valueOf('S'), Items.PRISMARINE_SHARD).patternLine("SS").patternLine("SS").addCriterion("has_prismarine_shard", RecipeProvider.hasItem(Items.PRISMARINE_SHARD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICKS).key(Character.valueOf('S'), Items.PRISMARINE_SHARD).patternLine("SSS").patternLine("SSS").patternLine("SSS").addCriterion("has_prismarine_shard", RecipeProvider.hasItem(Items.PRISMARINE_SHARD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_SLAB, 6).key(Character.valueOf('#'), Blocks.PRISMARINE).patternLine("###").addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.PRISMARINE_BRICKS).patternLine("###").addCriterion("has_prismarine_bricks", RecipeProvider.hasItem(Blocks.PRISMARINE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DARK_PRISMARINE_SLAB, 6).key(Character.valueOf('#'), Blocks.DARK_PRISMARINE).patternLine("###").addCriterion("has_dark_prismarine", RecipeProvider.hasItem(Blocks.DARK_PRISMARINE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_PIE).addIngredient(Blocks.PUMPKIN).addIngredient(Items.SUGAR).addIngredient(Items.EGG).addCriterion("has_carved_pumpkin", RecipeProvider.hasItem(Blocks.CARVED_PUMPKIN)).addCriterion("has_pumpkin", RecipeProvider.hasItem(Blocks.PUMPKIN)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_SEEDS, 4).addIngredient(Blocks.PUMPKIN).addCriterion("has_pumpkin", RecipeProvider.hasItem(Blocks.PUMPKIN)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE, 2).addIngredient(Items.BLUE_DYE).addIngredient(Items.RED_DYE).addCriterion("has_blue_dye", RecipeProvider.hasItem(Items.BLUE_DYE)).addCriterion("has_red_dye", RecipeProvider.hasItem(Items.RED_DYE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SHULKER_BOX).key(Character.valueOf('#'), Blocks.CHEST).key(Character.valueOf('-'), Items.SHULKER_SHELL).patternLine("-").patternLine("#").patternLine("-").addCriterion("has_shulker_shell", RecipeProvider.hasItem(Items.SHULKER_SHELL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_BLOCK, 4).key(Character.valueOf('F'), Items.POPPED_CHORUS_FRUIT).patternLine("FF").patternLine("FF").addCriterion("has_chorus_fruit_popped", RecipeProvider.hasItem(Items.POPPED_CHORUS_FRUIT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_PILLAR).key(Character.valueOf('#'), Blocks.PURPUR_SLAB).patternLine("#").patternLine("#").addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_SLAB, 6).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).patternLine("###").addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PURPUR_STAIRS, 4).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BLOCK).key(Character.valueOf('#'), Items.QUARTZ).patternLine("##").patternLine("##").addCriterion("has_quartz", RecipeProvider.hasItem(Items.QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BRICKS, 4).key(Character.valueOf('#'), Blocks.QUARTZ_BLOCK).patternLine("##").patternLine("##").addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_SLAB, 6).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).patternLine("###").addCriterion("has_chiseled_quartz_block", RecipeProvider.hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", RecipeProvider.hasItem(Blocks.QUARTZ_PILLAR)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_STAIRS, 4).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_chiseled_quartz_block", RecipeProvider.hasItem(Blocks.CHISELED_QUARTZ_BLOCK)).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).addCriterion("has_quartz_pillar", RecipeProvider.hasItem(Blocks.QUARTZ_PILLAR)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW).addIngredient(Items.BAKED_POTATO).addIngredient(Items.COOKED_RABBIT).addIngredient(Items.BOWL).addIngredient(Items.CARROT).addIngredient(Blocks.BROWN_MUSHROOM).setGroup("rabbit_stew").addCriterion("has_cooked_rabbit", RecipeProvider.hasItem(Items.COOKED_RABBIT)).build(consumer, "rabbit_stew_from_brown_mushroom");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW).addIngredient(Items.BAKED_POTATO).addIngredient(Items.COOKED_RABBIT).addIngredient(Items.BOWL).addIngredient(Items.CARROT).addIngredient(Blocks.RED_MUSHROOM).setGroup("rabbit_stew").addCriterion("has_cooked_rabbit", RecipeProvider.hasItem(Items.COOKED_RABBIT)).build(consumer, "rabbit_stew_from_red_mushroom");
        ShapedRecipeBuilder.shapedRecipe(Blocks.RAIL, 16).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.IRON_INGOT).patternLine("X X").patternLine("X#X").patternLine("X X").addCriterion("has_minecart", RecipeProvider.hasItem(Items.MINECART)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.REDSTONE, 9).addIngredient(Blocks.REDSTONE_BLOCK).addCriterion("has_redstone_block", RecipeProvider.hasItem(Blocks.REDSTONE_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_BLOCK).key(Character.valueOf('#'), Items.REDSTONE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_LAMP).key(Character.valueOf('R'), Items.REDSTONE).key(Character.valueOf('G'), Blocks.GLOWSTONE).patternLine(" R ").patternLine("RGR").patternLine(" R ").addCriterion("has_glowstone", RecipeProvider.hasItem(Blocks.GLOWSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REDSTONE_TORCH).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Items.REDSTONE).patternLine("X").patternLine("#").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Items.BEETROOT).setGroup("red_dye").addCriterion("has_beetroot", RecipeProvider.hasItem(Items.BEETROOT)).build(consumer, "red_dye_from_beetroot");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Blocks.POPPY).setGroup("red_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.POPPY)).build(consumer, "red_dye_from_poppy");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE, 2).addIngredient(Blocks.ROSE_BUSH).setGroup("red_dye").addCriterion("has_double_plant", RecipeProvider.hasItem(Blocks.ROSE_BUSH)).build(consumer, "red_dye_from_rose_bush");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(Blocks.RED_TULIP).setGroup("red_dye").addCriterion("has_red_flower", RecipeProvider.hasItem(Blocks.RED_TULIP)).build(consumer, "red_dye_from_tulip");
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICKS).key(Character.valueOf('W'), Items.NETHER_WART).key(Character.valueOf('N'), Items.NETHER_BRICK).patternLine("NW").patternLine("WN").addCriterion("has_nether_wart", RecipeProvider.hasItem(Items.NETHER_WART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE).key(Character.valueOf('#'), Blocks.RED_SAND).patternLine("##").patternLine("##").addCriterion("has_sand", RecipeProvider.hasItem(Blocks.RED_SAND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE)).patternLine("###").addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", RecipeProvider.hasItem(Blocks.CHISELED_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_RED_SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.CUT_RED_SANDSTONE).patternLine("###").addCriterion("has_cut_red_sandstone", RecipeProvider.hasItem(Blocks.CUT_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_STAIRS, 4).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).addCriterion("has_chiseled_red_sandstone", RecipeProvider.hasItem(Blocks.CHISELED_RED_SANDSTONE)).addCriterion("has_cut_red_sandstone", RecipeProvider.hasItem(Blocks.CUT_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.REPEATER).key(Character.valueOf('#'), Blocks.REDSTONE_TORCH).key(Character.valueOf('X'), Items.REDSTONE).key(Character.valueOf('I'), Blocks.STONE).patternLine("#X#").patternLine("III").addCriterion("has_redstone_torch", RecipeProvider.hasItem(Blocks.REDSTONE_TORCH)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE).key(Character.valueOf('#'), Blocks.SAND).patternLine("##").patternLine("##").addCriterion("has_sand", RecipeProvider.hasItem(Blocks.SAND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE)).patternLine("###").addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).addCriterion("has_chiseled_sandstone", RecipeProvider.hasItem(Blocks.CHISELED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.CUT_SANDSTONE).patternLine("###").addCriterion("has_cut_sandstone", RecipeProvider.hasItem(Blocks.CUT_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_STAIRS, 4).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE)).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).addCriterion("has_chiseled_sandstone", RecipeProvider.hasItem(Blocks.CHISELED_SANDSTONE)).addCriterion("has_cut_sandstone", RecipeProvider.hasItem(Blocks.CUT_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SEA_LANTERN).key(Character.valueOf('S'), Items.PRISMARINE_SHARD).key(Character.valueOf('C'), Items.PRISMARINE_CRYSTALS).patternLine("SCS").patternLine("CCC").patternLine("SCS").addCriterion("has_prismarine_crystals", RecipeProvider.hasItem(Items.PRISMARINE_CRYSTALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.SHEARS).key(Character.valueOf('#'), Items.IRON_INGOT).patternLine(" #").patternLine("# ").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.SHIELD).key(Character.valueOf('W'), ItemTags.PLANKS).key(Character.valueOf('o'), Items.IRON_INGOT).patternLine("WoW").patternLine("WWW").patternLine(" W ").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SLIME_BLOCK).key(Character.valueOf('#'), Items.SLIME_BALL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_slime_ball", RecipeProvider.hasItem(Items.SLIME_BALL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.SLIME_BALL, 9).addIngredient(Blocks.SLIME_BLOCK).addCriterion("has_slime", RecipeProvider.hasItem(Blocks.SLIME_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_RED_SANDSTONE, 4).key(Character.valueOf('#'), Blocks.RED_SANDSTONE).patternLine("##").patternLine("##").addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CUT_SANDSTONE, 4).key(Character.valueOf('#'), Blocks.SANDSTONE).patternLine("##").patternLine("##").addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW_BLOCK).key(Character.valueOf('#'), Items.SNOWBALL).patternLine("##").patternLine("##").addCriterion("has_snowball", RecipeProvider.hasItem(Items.SNOWBALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SNOW, 6).key(Character.valueOf('#'), Blocks.SNOW_BLOCK).patternLine("###").addCriterion("has_snowball", RecipeProvider.hasItem(Items.SNOWBALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SOUL_CAMPFIRE).key(Character.valueOf('L'), ItemTags.LOGS).key(Character.valueOf('S'), Items.STICK).key(Character.valueOf('#'), ItemTags.SOUL_FIRE_BASE_BLOCKS).patternLine(" S ").patternLine("S#S").patternLine("LLL").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).addCriterion("has_soul_sand", RecipeProvider.hasItem(ItemTags.SOUL_FIRE_BASE_BLOCKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.GLISTERING_MELON_SLICE).key(Character.valueOf('#'), Items.GOLD_NUGGET).key(Character.valueOf('X'), Items.MELON_SLICE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_melon", RecipeProvider.hasItem(Items.MELON_SLICE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.SPECTRAL_ARROW, 2).key(Character.valueOf('#'), Items.GLOWSTONE_DUST).key(Character.valueOf('X'), Items.ARROW).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_glowstone_dust", RecipeProvider.hasItem(Items.GLOWSTONE_DUST)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STICK, 4).key(Character.valueOf('#'), ItemTags.PLANKS).patternLine("#").patternLine("#").setGroup("sticks").addCriterion("has_planks", RecipeProvider.hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STICK, 1).key(Character.valueOf('#'), Blocks.BAMBOO).patternLine("#").patternLine("#").setGroup("sticks").addCriterion("has_bamboo", RecipeProvider.hasItem(Blocks.BAMBOO)).build(consumer, "stick_from_bamboo_item");
        ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON).key(Character.valueOf('P'), Blocks.PISTON).key(Character.valueOf('S'), Items.SLIME_BALL).patternLine("S").patternLine("P").addCriterion("has_slime_ball", RecipeProvider.hasItem(Items.SLIME_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICKS, 4).key(Character.valueOf('#'), Blocks.STONE).patternLine("##").patternLine("##").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STONE_AXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_TOOL_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.STONE_BRICKS).patternLine("###").addCriterion("has_stone_bricks", RecipeProvider.hasItem(ItemTags.STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_stone_bricks", RecipeProvider.hasItem(ItemTags.STONE_BRICKS)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.STONE_BUTTON).addIngredient(Blocks.STONE).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STONE_HOE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_TOOL_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STONE_PICKAXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_TOOL_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_PRESSURE_PLATE).key(Character.valueOf('#'), Blocks.STONE).patternLine("##").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STONE_SHOVEL).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_TOOL_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_SLAB, 6).key(Character.valueOf('#'), Blocks.STONE).patternLine("###").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_STONE_SLAB, 6).key(Character.valueOf('#'), Blocks.SMOOTH_STONE).patternLine("###").addCriterion("has_smooth_stone", RecipeProvider.hasItem(Blocks.SMOOTH_STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.COBBLESTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.STONE_SWORD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_cobblestone", RecipeProvider.hasItem(ItemTags.STONE_TOOL_MATERIALS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.WHITE_WOOL).key(Character.valueOf('#'), Items.STRING).patternLine("##").patternLine("##").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).build(consumer, "white_wool_from_string");
        ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR).addIngredient(Blocks.SUGAR_CANE).setGroup("sugar").addCriterion("has_reeds", RecipeProvider.hasItem(Blocks.SUGAR_CANE)).build(consumer, "sugar_from_sugar_cane");
        ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR, 3).addIngredient(Items.HONEY_BOTTLE).setGroup("sugar").addCriterion("has_honey_bottle", RecipeProvider.hasItem(Items.HONEY_BOTTLE)).build(consumer, "sugar_from_honey_bottle");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TARGET).key(Character.valueOf('H'), Items.HAY_BLOCK).key(Character.valueOf('R'), Items.REDSTONE).patternLine(" R ").patternLine("RHR").patternLine(" R ").addCriterion("has_redstone", RecipeProvider.hasItem(Items.REDSTONE)).addCriterion("has_hay_block", RecipeProvider.hasItem(Blocks.HAY_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TNT).key(Character.valueOf('#'), Ingredient.fromItems(Blocks.SAND, Blocks.RED_SAND)).key(Character.valueOf('X'), Items.GUNPOWDER).patternLine("X#X").patternLine("#X#").patternLine("X#X").addCriterion("has_gunpowder", RecipeProvider.hasItem(Items.GUNPOWDER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.TNT_MINECART).key(Character.valueOf('A'), Blocks.TNT).key(Character.valueOf('B'), Items.MINECART).patternLine("A").patternLine("B").addCriterion("has_minecart", RecipeProvider.hasItem(Items.MINECART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 4).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).patternLine("X").patternLine("#").addCriterion("has_stone_pickaxe", RecipeProvider.hasItem(Items.STONE_PICKAXE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SOUL_TORCH, 4).key(Character.valueOf('X'), Ingredient.fromItems(Items.COAL, Items.CHARCOAL)).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('S'), ItemTags.SOUL_FIRE_BASE_BLOCKS).patternLine("X").patternLine("#").patternLine("S").addCriterion("has_soul_sand", RecipeProvider.hasItem(ItemTags.SOUL_FIRE_BASE_BLOCKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LANTERN).key(Character.valueOf('#'), Items.TORCH).key(Character.valueOf('X'), Items.IRON_NUGGET).patternLine("XXX").patternLine("X#X").patternLine("XXX").addCriterion("has_iron_nugget", RecipeProvider.hasItem(Items.IRON_NUGGET)).addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SOUL_LANTERN).key(Character.valueOf('#'), Items.SOUL_TORCH).key(Character.valueOf('X'), Items.IRON_NUGGET).patternLine("XXX").patternLine("X#X").patternLine("XXX").addCriterion("has_soul_torch", RecipeProvider.hasItem(Items.SOUL_TORCH)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.TRAPPED_CHEST).addIngredient(Blocks.CHEST).addIngredient(Blocks.TRIPWIRE_HOOK).addCriterion("has_tripwire_hook", RecipeProvider.hasItem(Blocks.TRIPWIRE_HOOK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.TRIPWIRE_HOOK, 2).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('S'), Items.STICK).key(Character.valueOf('I'), Items.IRON_INGOT).patternLine("I").patternLine("S").patternLine("#").addCriterion("has_string", RecipeProvider.hasItem(Items.STRING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.TURTLE_HELMET).key(Character.valueOf('X'), Items.SCUTE).patternLine("XXX").patternLine("X X").addCriterion("has_scute", RecipeProvider.hasItem(Items.SCUTE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT, 9).addIngredient(Blocks.HAY_BLOCK).addCriterion("has_hay_block", RecipeProvider.hasItem(Blocks.HAY_BLOCK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(Items.BONE_MEAL).setGroup("white_dye").addCriterion("has_bone_meal", RecipeProvider.hasItem(Items.BONE_MEAL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(Blocks.LILY_OF_THE_VALLEY).setGroup("white_dye").addCriterion("has_white_flower", RecipeProvider.hasItem(Blocks.LILY_OF_THE_VALLEY)).build(consumer, "white_dye_from_lily_of_the_valley");
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_AXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_HOE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_PICKAXE).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SHOVEL).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Items.WOODEN_SWORD).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_stick", RecipeProvider.hasItem(Items.STICK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.WRITABLE_BOOK).addIngredient(Items.BOOK).addIngredient(Items.INK_SAC).addIngredient(Items.FEATHER).addCriterion("has_book", RecipeProvider.hasItem(Items.BOOK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(Blocks.DANDELION).setGroup("yellow_dye").addCriterion("has_yellow_flower", RecipeProvider.hasItem(Blocks.DANDELION)).build(consumer, "yellow_dye_from_dandelion");
        ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE, 2).addIngredient(Blocks.SUNFLOWER).setGroup("yellow_dye").addCriterion("has_double_plant", RecipeProvider.hasItem(Blocks.SUNFLOWER)).build(consumer, "yellow_dye_from_sunflower");
        ShapelessRecipeBuilder.shapelessRecipe(Items.DRIED_KELP, 9).addIngredient(Blocks.DRIED_KELP_BLOCK).addCriterion("has_dried_kelp_block", RecipeProvider.hasItem(Blocks.DRIED_KELP_BLOCK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DRIED_KELP_BLOCK).addIngredient(Items.DRIED_KELP, 9).addCriterion("has_dried_kelp", RecipeProvider.hasItem(Items.DRIED_KELP)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CONDUIT).key(Character.valueOf('#'), Items.NAUTILUS_SHELL).key(Character.valueOf('X'), Items.HEART_OF_THE_SEA).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_nautilus_core", RecipeProvider.hasItem(Items.HEART_OF_THE_SEA)).addCriterion("has_nautilus_shell", RecipeProvider.hasItem(Items.NAUTILUS_SHELL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.POLISHED_GRANITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_granite", RecipeProvider.hasItem(Blocks.POLISHED_GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.SMOOTH_RED_SANDSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_red_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.MOSSY_STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.POLISHED_DIORITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.MOSSY_COBBLESTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.END_STONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_end_stone_bricks", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.STONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_SANDSTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.SMOOTH_SANDSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_QUARTZ_STAIRS, 4).key(Character.valueOf('#'), Blocks.SMOOTH_QUARTZ).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_smooth_quartz", RecipeProvider.hasItem(Blocks.SMOOTH_QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.GRANITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.ANDESITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.RED_NETHER_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_red_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.POLISHED_ANDESITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_andesite", RecipeProvider.hasItem(Blocks.POLISHED_ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_STAIRS, 4).key(Character.valueOf('#'), Blocks.DIORITE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_GRANITE_SLAB, 6).key(Character.valueOf('#'), Blocks.POLISHED_GRANITE).patternLine("###").addCriterion("has_polished_granite", RecipeProvider.hasItem(Blocks.POLISHED_GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_RED_SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.SMOOTH_RED_SANDSTONE).patternLine("###").addCriterion("has_smooth_red_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.MOSSY_STONE_BRICKS).patternLine("###").addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_DIORITE_SLAB, 6).key(Character.valueOf('#'), Blocks.POLISHED_DIORITE).patternLine("###").addCriterion("has_polished_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_COBBLESTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.MOSSY_COBBLESTONE).patternLine("###").addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.END_STONE_BRICKS).patternLine("###").addCriterion("has_end_stone_bricks", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_SANDSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.SMOOTH_SANDSTONE).patternLine("###").addCriterion("has_smooth_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOOTH_QUARTZ_SLAB, 6).key(Character.valueOf('#'), Blocks.SMOOTH_QUARTZ).patternLine("###").addCriterion("has_smooth_quartz", RecipeProvider.hasItem(Blocks.SMOOTH_QUARTZ)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_SLAB, 6).key(Character.valueOf('#'), Blocks.GRANITE).patternLine("###").addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_SLAB, 6).key(Character.valueOf('#'), Blocks.ANDESITE).patternLine("###").addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.RED_NETHER_BRICKS).patternLine("###").addCriterion("has_red_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_ANDESITE_SLAB, 6).key(Character.valueOf('#'), Blocks.POLISHED_ANDESITE).patternLine("###").addCriterion("has_polished_andesite", RecipeProvider.hasItem(Blocks.POLISHED_ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_SLAB, 6).key(Character.valueOf('#'), Blocks.DIORITE).patternLine("###").addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.BRICKS).patternLine("###").patternLine("###").addCriterion("has_bricks", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_WALL, 6).key(Character.valueOf('#'), Blocks.PRISMARINE).patternLine("###").patternLine("###").addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_SANDSTONE_WALL, 6).key(Character.valueOf('#'), Blocks.RED_SANDSTONE).patternLine("###").patternLine("###").addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.MOSSY_STONE_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.MOSSY_STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRANITE_WALL, 6).key(Character.valueOf('#'), Blocks.GRANITE).patternLine("###").patternLine("###").addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.NETHER_BRICKS).patternLine("###").patternLine("###").addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.ANDESITE_WALL, 6).key(Character.valueOf('#'), Blocks.ANDESITE).patternLine("###").patternLine("###").addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RED_NETHER_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.RED_NETHER_BRICKS).patternLine("###").patternLine("###").addCriterion("has_red_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SANDSTONE_WALL, 6).key(Character.valueOf('#'), Blocks.SANDSTONE).patternLine("###").patternLine("###").addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.END_STONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_end_stone_bricks", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.DIORITE_WALL, 6).key(Character.valueOf('#'), Blocks.DIORITE).patternLine("###").patternLine("###").addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.CREEPER_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.CREEPER_HEAD).addCriterion("has_creeper_head", RecipeProvider.hasItem(Items.CREEPER_HEAD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.SKULL_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.WITHER_SKELETON_SKULL).addCriterion("has_wither_skeleton_skull", RecipeProvider.hasItem(Items.WITHER_SKELETON_SKULL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.FLOWER_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Blocks.OXEYE_DAISY).addCriterion("has_oxeye_daisy", RecipeProvider.hasItem(Blocks.OXEYE_DAISY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.MOJANG_BANNER_PATTERN).addIngredient(Items.PAPER).addIngredient(Items.ENCHANTED_GOLDEN_APPLE).addCriterion("has_enchanted_golden_apple", RecipeProvider.hasItem(Items.ENCHANTED_GOLDEN_APPLE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SCAFFOLDING, 6).key(Character.valueOf('~'), Items.STRING).key(Character.valueOf('I'), Blocks.BAMBOO).patternLine("I~I").patternLine("I I").patternLine("I I").addCriterion("has_bamboo", RecipeProvider.hasItem(Blocks.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.GRINDSTONE).key(Character.valueOf('I'), Items.STICK).key(Character.valueOf('-'), Blocks.STONE_SLAB).key(Character.valueOf('#'), ItemTags.PLANKS).patternLine("I-I").patternLine("# #").addCriterion("has_stone_slab", RecipeProvider.hasItem(Blocks.STONE_SLAB)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLAST_FURNACE).key(Character.valueOf('#'), Blocks.SMOOTH_STONE).key(Character.valueOf('X'), Blocks.FURNACE).key(Character.valueOf('I'), Items.IRON_INGOT).patternLine("III").patternLine("IXI").patternLine("###").addCriterion("has_smooth_stone", RecipeProvider.hasItem(Blocks.SMOOTH_STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMOKER).key(Character.valueOf('#'), ItemTags.LOGS).key(Character.valueOf('X'), Blocks.FURNACE).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_furnace", RecipeProvider.hasItem(Blocks.FURNACE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CARTOGRAPHY_TABLE).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('@'), Items.PAPER).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_paper", RecipeProvider.hasItem(Items.PAPER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('@'), Items.IRON_INGOT).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.FLETCHING_TABLE).key(Character.valueOf('#'), ItemTags.PLANKS).key(Character.valueOf('@'), Items.FLINT).patternLine("@@").patternLine("##").patternLine("##").addCriterion("has_flint", RecipeProvider.hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONECUTTER).key(Character.valueOf('I'), Items.IRON_INGOT).key(Character.valueOf('#'), Blocks.STONE).patternLine(" I ").patternLine("###").addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.LODESTONE).key(Character.valueOf('S'), Items.CHISELED_STONE_BRICKS).key(Character.valueOf('#'), Items.NETHERITE_INGOT).patternLine("SSS").patternLine("S#S").patternLine("SSS").addCriterion("has_netherite_ingot", RecipeProvider.hasItem(Items.NETHERITE_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHERITE_BLOCK).key(Character.valueOf('#'), Items.NETHERITE_INGOT).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_netherite_ingot", RecipeProvider.hasItem(Items.NETHERITE_INGOT)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.NETHERITE_INGOT, 9).addIngredient(Blocks.NETHERITE_BLOCK).setGroup("netherite_ingot").addCriterion("has_netherite_block", RecipeProvider.hasItem(Blocks.NETHERITE_BLOCK)).build(consumer, "netherite_ingot_from_netherite_block");
        ShapelessRecipeBuilder.shapelessRecipe(Items.NETHERITE_INGOT).addIngredient(Items.NETHERITE_SCRAP, 4).addIngredient(Items.GOLD_INGOT, 4).setGroup("netherite_ingot").addCriterion("has_netherite_scrap", RecipeProvider.hasItem(Items.NETHERITE_SCRAP)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.RESPAWN_ANCHOR).key(Character.valueOf('O'), Blocks.CRYING_OBSIDIAN).key(Character.valueOf('G'), Blocks.GLOWSTONE).patternLine("OOO").patternLine("GGG").patternLine("OOO").addCriterion("has_obsidian", RecipeProvider.hasItem(Blocks.CRYING_OBSIDIAN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACKSTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.BLACKSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_STAIRS, 4).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, 4).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE_BRICKS).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACKSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.BLACKSTONE).patternLine("###").addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_SLAB, 6).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE).patternLine("###").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, 6).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE_BRICKS).patternLine("###").addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE, 4).key(Character.valueOf('S'), Blocks.BLACKSTONE).patternLine("SS").patternLine("SS").addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_BRICKS, 4).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE).patternLine("##").patternLine("##").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHISELED_POLISHED_BLACKSTONE).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE_SLAB).patternLine("#").patternLine("#").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.BLACKSTONE_WALL, 6).key(Character.valueOf('#'), Blocks.BLACKSTONE).patternLine("###").patternLine("###").addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_WALL, 6).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE).patternLine("###").patternLine("###").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, 6).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE_BRICKS).patternLine("###").patternLine("###").addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.POLISHED_BLACKSTONE_BUTTON).addIngredient(Blocks.POLISHED_BLACKSTONE).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE).key(Character.valueOf('#'), Blocks.POLISHED_BLACKSTONE).patternLine("##").addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.CHAIN).key(Character.valueOf('I'), Items.IRON_INGOT).key(Character.valueOf('N'), Items.IRON_NUGGET).patternLine("N").patternLine("I").patternLine("N").addCriterion("has_iron_nugget", RecipeProvider.hasItem(Items.IRON_NUGGET)).addCriterion("has_iron_ingot", RecipeProvider.hasItem(Items.IRON_INGOT)).build(consumer);
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_ARMORDYE).build(consumer, "armor_dye");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_BANNERDUPLICATE).build(consumer, "banner_duplicate");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_BOOKCLONING).build(consumer, "book_cloning");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_ROCKET).build(consumer, "firework_rocket");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR).build(consumer, "firework_star");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_FIREWORK_STAR_FADE).build(consumer, "firework_star_fade");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_MAPCLONING).build(consumer, "map_cloning");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_MAPEXTENDING).build(consumer, "map_extending");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_REPAIRITEM).build(consumer, "repair_item");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SHIELD).build(consumer, "shield_decoration");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SHULKERBOXCOLORING).build(consumer, "shulker_box_coloring");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_TIPPEDARROW).build(consumer, "tipped_arrow");
        CustomRecipeBuilder.customRecipe(IRecipeSerializer.CRAFTING_SPECIAL_SUSPICIOUSSTEW).build(consumer, "suspicious_stew");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35f, 200).addCriterion("has_potato", RecipeProvider.hasItem(Items.POTATO)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CLAY_BALL), Items.BRICK, 0.3f, 200).addCriterion("has_clay_ball", RecipeProvider.hasItem(Items.CLAY_BALL)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.LOGS_THAT_BURN), Items.CHARCOAL, 0.15f, 200).addCriterion("has_log", RecipeProvider.hasItem(ItemTags.LOGS_THAT_BURN)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT, 0.1f, 200).addCriterion("has_chorus_fruit", RecipeProvider.hasItem(Items.CHORUS_FRUIT)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.COAL_ORE.asItem()), Items.COAL, 0.1f, 200).addCriterion("has_coal_ore", RecipeProvider.hasItem(Blocks.COAL_ORE)).build(consumer, "coal_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35f, 200).addCriterion("has_beef", RecipeProvider.hasItem(Items.BEEF)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35f, 200).addCriterion("has_chicken", RecipeProvider.hasItem(Items.CHICKEN)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35f, 200).addCriterion("has_cod", RecipeProvider.hasItem(Items.COD)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1f, 200).addCriterion("has_kelp", RecipeProvider.hasItem(Blocks.KELP)).build(consumer, "dried_kelp_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35f, 200).addCriterion("has_salmon", RecipeProvider.hasItem(Items.SALMON)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35f, 200).addCriterion("has_mutton", RecipeProvider.hasItem(Items.MUTTON)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35f, 200).addCriterion("has_porkchop", RecipeProvider.hasItem(Items.PORKCHOP)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35f, 200).addCriterion("has_rabbit", RecipeProvider.hasItem(Items.RABBIT)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.DIAMOND_ORE.asItem()), Items.DIAMOND, 1.0f, 200).addCriterion("has_diamond_ore", RecipeProvider.hasItem(Blocks.DIAMOND_ORE)).build(consumer, "diamond_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LAPIS_ORE.asItem()), Items.LAPIS_LAZULI, 0.2f, 200).addCriterion("has_lapis_ore", RecipeProvider.hasItem(Blocks.LAPIS_ORE)).build(consumer, "lapis_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.EMERALD_ORE.asItem()), Items.EMERALD, 1.0f, 200).addCriterion("has_emerald_ore", RecipeProvider.hasItem(Blocks.EMERALD_ORE)).build(consumer, "emerald_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.SAND), Blocks.GLASS.asItem(), 0.1f, 200).addCriterion("has_sand", RecipeProvider.hasItem(ItemTags.SAND)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.GOLD_ORES), Items.GOLD_INGOT, 1.0f, 200).addCriterion("has_gold_ore", RecipeProvider.hasItem(ItemTags.GOLD_ORES)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.SEA_PICKLE.asItem()), Items.LIME_DYE, 0.1f, 200).addCriterion("has_sea_pickle", RecipeProvider.hasItem(Blocks.SEA_PICKLE)).build(consumer, "lime_dye_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CACTUS.asItem()), Items.GREEN_DYE, 1.0f, 200).addCriterion("has_cactus", RecipeProvider.hasItem(Blocks.CACTUS)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1f, 200).addCriterion("has_golden_pickaxe", RecipeProvider.hasItem(Items.GOLDEN_PICKAXE)).addCriterion("has_golden_shovel", RecipeProvider.hasItem(Items.GOLDEN_SHOVEL)).addCriterion("has_golden_axe", RecipeProvider.hasItem(Items.GOLDEN_AXE)).addCriterion("has_golden_hoe", RecipeProvider.hasItem(Items.GOLDEN_HOE)).addCriterion("has_golden_sword", RecipeProvider.hasItem(Items.GOLDEN_SWORD)).addCriterion("has_golden_helmet", RecipeProvider.hasItem(Items.GOLDEN_HELMET)).addCriterion("has_golden_chestplate", RecipeProvider.hasItem(Items.GOLDEN_CHESTPLATE)).addCriterion("has_golden_leggings", RecipeProvider.hasItem(Items.GOLDEN_LEGGINGS)).addCriterion("has_golden_boots", RecipeProvider.hasItem(Items.GOLDEN_BOOTS)).addCriterion("has_golden_horse_armor", RecipeProvider.hasItem(Items.GOLDEN_HORSE_ARMOR)).build(consumer, "gold_nugget_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1f, 200).addCriterion("has_iron_pickaxe", RecipeProvider.hasItem(Items.IRON_PICKAXE)).addCriterion("has_iron_shovel", RecipeProvider.hasItem(Items.IRON_SHOVEL)).addCriterion("has_iron_axe", RecipeProvider.hasItem(Items.IRON_AXE)).addCriterion("has_iron_hoe", RecipeProvider.hasItem(Items.IRON_HOE)).addCriterion("has_iron_sword", RecipeProvider.hasItem(Items.IRON_SWORD)).addCriterion("has_iron_helmet", RecipeProvider.hasItem(Items.IRON_HELMET)).addCriterion("has_iron_chestplate", RecipeProvider.hasItem(Items.IRON_CHESTPLATE)).addCriterion("has_iron_leggings", RecipeProvider.hasItem(Items.IRON_LEGGINGS)).addCriterion("has_iron_boots", RecipeProvider.hasItem(Items.IRON_BOOTS)).addCriterion("has_iron_horse_armor", RecipeProvider.hasItem(Items.IRON_HORSE_ARMOR)).addCriterion("has_chainmail_helmet", RecipeProvider.hasItem(Items.CHAINMAIL_HELMET)).addCriterion("has_chainmail_chestplate", RecipeProvider.hasItem(Items.CHAINMAIL_CHESTPLATE)).addCriterion("has_chainmail_leggings", RecipeProvider.hasItem(Items.CHAINMAIL_LEGGINGS)).addCriterion("has_chainmail_boots", RecipeProvider.hasItem(Items.CHAINMAIL_BOOTS)).build(consumer, "iron_nugget_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.IRON_ORE.asItem()), Items.IRON_INGOT, 0.7f, 200).addCriterion("has_iron_ore", RecipeProvider.hasItem(Blocks.IRON_ORE.asItem())).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CLAY), Blocks.TERRACOTTA.asItem(), 0.35f, 200).addCriterion("has_clay_block", RecipeProvider.hasItem(Blocks.CLAY)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1f, 200).addCriterion("has_netherrack", RecipeProvider.hasItem(Blocks.NETHERRACK)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2f, 200).addCriterion("has_nether_quartz_ore", RecipeProvider.hasItem(Blocks.NETHER_QUARTZ_ORE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7f, 200).addCriterion("has_redstone_ore", RecipeProvider.hasItem(Blocks.REDSTONE_ORE)).build(consumer, "redstone_from_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.WET_SPONGE), Blocks.SPONGE.asItem(), 0.15f, 200).addCriterion("has_wet_sponge", RecipeProvider.hasItem(Blocks.WET_SPONGE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.STONE.asItem(), 0.1f, 200).addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.SMOOTH_STONE.asItem(), 0.1f, 200).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.asItem(), 0.1f, 200).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1f, 200).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.asItem(), 0.1f, 200).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1f, 200).addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_black_terracotta", RecipeProvider.hasItem(Blocks.BLACK_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_blue_terracotta", RecipeProvider.hasItem(Blocks.BLUE_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_brown_terracotta", RecipeProvider.hasItem(Blocks.BROWN_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_cyan_terracotta", RecipeProvider.hasItem(Blocks.CYAN_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_gray_terracotta", RecipeProvider.hasItem(Blocks.GRAY_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_green_terracotta", RecipeProvider.hasItem(Blocks.GREEN_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_light_blue_terracotta", RecipeProvider.hasItem(Blocks.LIGHT_BLUE_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_light_gray_terracotta", RecipeProvider.hasItem(Blocks.LIGHT_GRAY_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_lime_terracotta", RecipeProvider.hasItem(Blocks.LIME_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_magenta_terracotta", RecipeProvider.hasItem(Blocks.MAGENTA_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_orange_terracotta", RecipeProvider.hasItem(Blocks.ORANGE_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_pink_terracotta", RecipeProvider.hasItem(Blocks.PINK_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_purple_terracotta", RecipeProvider.hasItem(Blocks.PURPLE_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_red_terracotta", RecipeProvider.hasItem(Blocks.RED_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_white_terracotta", RecipeProvider.hasItem(Blocks.WHITE_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1f, 200).addCriterion("has_yellow_terracotta", RecipeProvider.hasItem(Blocks.YELLOW_TERRACOTTA)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 2.0f, 200).addCriterion("has_ancient_debris", RecipeProvider.hasItem(Blocks.ANCIENT_DEBRIS)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.asItem(), 0.1f, 200).addCriterion("has_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS.asItem(), 0.1f, 200).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer);
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.IRON_ORE.asItem()), Items.IRON_INGOT, 0.7f, 100).addCriterion("has_iron_ore", RecipeProvider.hasItem(Blocks.IRON_ORE.asItem())).build(consumer, "iron_ingot_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.GOLD_ORES), Items.GOLD_INGOT, 1.0f, 100).addCriterion("has_gold_ore", RecipeProvider.hasItem(ItemTags.GOLD_ORES)).build(consumer, "gold_ingot_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.DIAMOND_ORE.asItem()), Items.DIAMOND, 1.0f, 100).addCriterion("has_diamond_ore", RecipeProvider.hasItem(Blocks.DIAMOND_ORE)).build(consumer, "diamond_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.LAPIS_ORE.asItem()), Items.LAPIS_LAZULI, 0.2f, 100).addCriterion("has_lapis_ore", RecipeProvider.hasItem(Blocks.LAPIS_ORE)).build(consumer, "lapis_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.REDSTONE_ORE), Items.REDSTONE, 0.7f, 100).addCriterion("has_redstone_ore", RecipeProvider.hasItem(Blocks.REDSTONE_ORE)).build(consumer, "redstone_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.COAL_ORE.asItem()), Items.COAL, 0.1f, 100).addCriterion("has_coal_ore", RecipeProvider.hasItem(Blocks.COAL_ORE)).build(consumer, "coal_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.EMERALD_ORE.asItem()), Items.EMERALD, 1.0f, 100).addCriterion("has_emerald_ore", RecipeProvider.hasItem(Blocks.EMERALD_ORE)).build(consumer, "emerald_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2f, 100).addCriterion("has_nether_quartz_ore", RecipeProvider.hasItem(Blocks.NETHER_QUARTZ_ORE)).build(consumer, "quartz_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR), Items.GOLD_NUGGET, 0.1f, 100).addCriterion("has_golden_pickaxe", RecipeProvider.hasItem(Items.GOLDEN_PICKAXE)).addCriterion("has_golden_shovel", RecipeProvider.hasItem(Items.GOLDEN_SHOVEL)).addCriterion("has_golden_axe", RecipeProvider.hasItem(Items.GOLDEN_AXE)).addCriterion("has_golden_hoe", RecipeProvider.hasItem(Items.GOLDEN_HOE)).addCriterion("has_golden_sword", RecipeProvider.hasItem(Items.GOLDEN_SWORD)).addCriterion("has_golden_helmet", RecipeProvider.hasItem(Items.GOLDEN_HELMET)).addCriterion("has_golden_chestplate", RecipeProvider.hasItem(Items.GOLDEN_CHESTPLATE)).addCriterion("has_golden_leggings", RecipeProvider.hasItem(Items.GOLDEN_LEGGINGS)).addCriterion("has_golden_boots", RecipeProvider.hasItem(Items.GOLDEN_BOOTS)).addCriterion("has_golden_horse_armor", RecipeProvider.hasItem(Items.GOLDEN_HORSE_ARMOR)).build(consumer, "gold_nugget_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SWORD, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.IRON_HORSE_ARMOR, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), Items.IRON_NUGGET, 0.1f, 100).addCriterion("has_iron_pickaxe", RecipeProvider.hasItem(Items.IRON_PICKAXE)).addCriterion("has_iron_shovel", RecipeProvider.hasItem(Items.IRON_SHOVEL)).addCriterion("has_iron_axe", RecipeProvider.hasItem(Items.IRON_AXE)).addCriterion("has_iron_hoe", RecipeProvider.hasItem(Items.IRON_HOE)).addCriterion("has_iron_sword", RecipeProvider.hasItem(Items.IRON_SWORD)).addCriterion("has_iron_helmet", RecipeProvider.hasItem(Items.IRON_HELMET)).addCriterion("has_iron_chestplate", RecipeProvider.hasItem(Items.IRON_CHESTPLATE)).addCriterion("has_iron_leggings", RecipeProvider.hasItem(Items.IRON_LEGGINGS)).addCriterion("has_iron_boots", RecipeProvider.hasItem(Items.IRON_BOOTS)).addCriterion("has_iron_horse_armor", RecipeProvider.hasItem(Items.IRON_HORSE_ARMOR)).addCriterion("has_chainmail_helmet", RecipeProvider.hasItem(Items.CHAINMAIL_HELMET)).addCriterion("has_chainmail_chestplate", RecipeProvider.hasItem(Items.CHAINMAIL_CHESTPLATE)).addCriterion("has_chainmail_leggings", RecipeProvider.hasItem(Items.CHAINMAIL_LEGGINGS)).addCriterion("has_chainmail_boots", RecipeProvider.hasItem(Items.CHAINMAIL_BOOTS)).build(consumer, "iron_nugget_from_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(Blocks.ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 2.0f, 100).addCriterion("has_ancient_debris", RecipeProvider.hasItem(Blocks.ANCIENT_DEBRIS)).build(consumer, "netherite_scrap_from_blasting");
        RecipeProvider.cookingRecipesForMethod(consumer, "smoking", IRecipeSerializer.SMOKING, 100);
        RecipeProvider.cookingRecipesForMethod(consumer, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING, 600);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_SLAB, 2).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_slab_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_STAIRS).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_stairs_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICKS).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_bricks_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_SLAB, 2).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_brick_slab_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_STAIRS).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_brick_stairs_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.CHISELED_STONE_BRICKS).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "chiseled_stone_bricks_stone_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE), Blocks.STONE_BRICK_WALL).addCriterion("has_stone", RecipeProvider.hasItem(Blocks.STONE)).build(consumer, "stone_brick_walls_from_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CUT_SANDSTONE).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "cut_sandstone_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_SLAB, 2).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "sandstone_slab_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CUT_SANDSTONE_SLAB, 2).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "cut_sandstone_slab_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.CUT_SANDSTONE), Blocks.CUT_SANDSTONE_SLAB, 2).addCriterion("has_cut_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "cut_sandstone_slab_from_cut_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_STAIRS).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "sandstone_stairs_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.SANDSTONE_WALL).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "sandstone_wall_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SANDSTONE), Blocks.CHISELED_SANDSTONE).addCriterion("has_sandstone", RecipeProvider.hasItem(Blocks.SANDSTONE)).build(consumer, "chiseled_sandstone_from_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "cut_red_sandstone_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_SLAB, 2).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "red_sandstone_slab_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE_SLAB, 2).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "cut_red_sandstone_slab_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.CUT_RED_SANDSTONE), Blocks.CUT_RED_SANDSTONE_SLAB, 2).addCriterion("has_cut_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "cut_red_sandstone_slab_from_cut_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_STAIRS).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "red_sandstone_stairs_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.RED_SANDSTONE_WALL).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "red_sandstone_wall_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_SANDSTONE), Blocks.CHISELED_RED_SANDSTONE).addCriterion("has_red_sandstone", RecipeProvider.hasItem(Blocks.RED_SANDSTONE)).build(consumer, "chiseled_red_sandstone_from_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_SLAB, 2).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer, "quartz_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_STAIRS).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer, "quartz_stairs_from_quartz_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_PILLAR).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer, "quartz_pillar_from_quartz_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.CHISELED_QUARTZ_BLOCK).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer, "chiseled_quartz_block_from_quartz_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_BRICKS).addCriterion("has_quartz_block", RecipeProvider.hasItem(Blocks.QUARTZ_BLOCK)).build(consumer, "quartz_bricks_from_quartz_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_STAIRS).addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer, "cobblestone_stairs_from_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_SLAB, 2).addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer, "cobblestone_slab_from_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.COBBLESTONE), Blocks.COBBLESTONE_WALL).addCriterion("has_cobblestone", RecipeProvider.hasItem(Blocks.COBBLESTONE)).build(consumer, "cobblestone_wall_from_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_SLAB, 2).addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer, "stone_brick_slab_from_stone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_STAIRS).addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer, "stone_brick_stairs_from_stone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_WALL).addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer, "stone_brick_wall_from_stone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.STONE_BRICKS), Blocks.CHISELED_STONE_BRICKS).addCriterion("has_stone_bricks", RecipeProvider.hasItem(Blocks.STONE_BRICKS)).build(consumer, "chiseled_stone_bricks_from_stone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_SLAB, 2).addCriterion("has_bricks", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer, "brick_slab_from_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_STAIRS).addCriterion("has_bricks", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer, "brick_stairs_from_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BRICKS), Blocks.BRICK_WALL).addCriterion("has_bricks", RecipeProvider.hasItem(Blocks.BRICKS)).build(consumer, "brick_wall_from_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_SLAB, 2).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer, "nether_brick_slab_from_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_STAIRS).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer, "nether_brick_stairs_from_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.NETHER_BRICK_WALL).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer, "nether_brick_wall_from_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.NETHER_BRICKS), Blocks.CHISELED_NETHER_BRICKS).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.NETHER_BRICKS)).build(consumer, "chiseled_nether_bricks_from_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_SLAB, 2).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer, "red_nether_brick_slab_from_red_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_STAIRS).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer, "red_nether_brick_stairs_from_red_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICK_WALL).addCriterion("has_nether_bricks", RecipeProvider.hasItem(Blocks.RED_NETHER_BRICKS)).build(consumer, "red_nether_brick_wall_from_red_nether_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_SLAB, 2).addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer, "purpur_slab_from_purpur_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_STAIRS).addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer, "purpur_stairs_from_purpur_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PURPUR_BLOCK), Blocks.PURPUR_PILLAR).addCriterion("has_purpur_block", RecipeProvider.hasItem(Blocks.PURPUR_BLOCK)).build(consumer, "purpur_pillar_from_purpur_block_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_SLAB, 2).addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer, "prismarine_slab_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_STAIRS).addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer, "prismarine_stairs_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE), Blocks.PRISMARINE_WALL).addCriterion("has_prismarine", RecipeProvider.hasItem(Blocks.PRISMARINE)).build(consumer, "prismarine_wall_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_SLAB, 2).addCriterion("has_prismarine_brick", RecipeProvider.hasItem(Blocks.PRISMARINE_BRICKS)).build(consumer, "prismarine_brick_slab_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_STAIRS).addCriterion("has_prismarine_brick", RecipeProvider.hasItem(Blocks.PRISMARINE_BRICKS)).build(consumer, "prismarine_brick_stairs_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DARK_PRISMARINE), Blocks.DARK_PRISMARINE_SLAB, 2).addCriterion("has_dark_prismarine", RecipeProvider.hasItem(Blocks.DARK_PRISMARINE)).build(consumer, "dark_prismarine_slab_from_dark_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DARK_PRISMARINE), Blocks.DARK_PRISMARINE_STAIRS).addCriterion("has_dark_prismarine", RecipeProvider.hasItem(Blocks.DARK_PRISMARINE)).build(consumer, "dark_prismarine_stairs_from_dark_prismarine_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_SLAB, 2).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "andesite_slab_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_STAIRS).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "andesite_stairs_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.ANDESITE_WALL).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "andesite_wall_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "polished_andesite_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE_SLAB, 2).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "polished_andesite_slab_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.ANDESITE), Blocks.POLISHED_ANDESITE_STAIRS).addCriterion("has_andesite", RecipeProvider.hasItem(Blocks.ANDESITE)).build(consumer, "polished_andesite_stairs_from_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_ANDESITE), Blocks.POLISHED_ANDESITE_SLAB, 2).addCriterion("has_polished_andesite", RecipeProvider.hasItem(Blocks.POLISHED_ANDESITE)).build(consumer, "polished_andesite_slab_from_polished_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_ANDESITE), Blocks.POLISHED_ANDESITE_STAIRS).addCriterion("has_polished_andesite", RecipeProvider.hasItem(Blocks.POLISHED_ANDESITE)).build(consumer, "polished_andesite_stairs_from_polished_andesite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BASALT), Blocks.POLISHED_BASALT).addCriterion("has_basalt", RecipeProvider.hasItem(Blocks.BASALT)).build(consumer, "polished_basalt_from_basalt_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_SLAB, 2).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "granite_slab_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_STAIRS).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "granite_stairs_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.GRANITE_WALL).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "granite_wall_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "polished_granite_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE_SLAB, 2).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "polished_granite_slab_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.GRANITE), Blocks.POLISHED_GRANITE_STAIRS).addCriterion("has_granite", RecipeProvider.hasItem(Blocks.GRANITE)).build(consumer, "polished_granite_stairs_from_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_GRANITE), Blocks.POLISHED_GRANITE_SLAB, 2).addCriterion("has_polished_granite", RecipeProvider.hasItem(Blocks.POLISHED_GRANITE)).build(consumer, "polished_granite_slab_from_polished_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_GRANITE), Blocks.POLISHED_GRANITE_STAIRS).addCriterion("has_polished_granite", RecipeProvider.hasItem(Blocks.POLISHED_GRANITE)).build(consumer, "polished_granite_stairs_from_polished_granite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_SLAB, 2).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer, "diorite_slab_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_STAIRS).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer, "diorite_stairs_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.DIORITE_WALL).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer, "diorite_wall_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.DIORITE)).build(consumer, "polished_diorite_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE_SLAB, 2).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer, "polished_diorite_slab_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.DIORITE), Blocks.POLISHED_DIORITE_STAIRS).addCriterion("has_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer, "polished_diorite_stairs_from_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_DIORITE), Blocks.POLISHED_DIORITE_SLAB, 2).addCriterion("has_polished_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer, "polished_diorite_slab_from_polished_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_DIORITE), Blocks.POLISHED_DIORITE_STAIRS).addCriterion("has_polished_diorite", RecipeProvider.hasItem(Blocks.POLISHED_DIORITE)).build(consumer, "polished_diorite_stairs_from_polished_diorite_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_SLAB, 2).addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_STAIRS).addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_WALL).addCriterion("has_mossy_stone_bricks", RecipeProvider.hasItem(Blocks.MOSSY_STONE_BRICKS)).build(consumer, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_SLAB, 2).addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer, "mossy_cobblestone_slab_from_mossy_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_STAIRS).addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer, "mossy_cobblestone_stairs_from_mossy_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE), Blocks.MOSSY_COBBLESTONE_WALL).addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.MOSSY_COBBLESTONE)).build(consumer, "mossy_cobblestone_wall_from_mossy_cobblestone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_SANDSTONE), Blocks.SMOOTH_SANDSTONE_SLAB, 2).addCriterion("has_smooth_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_SANDSTONE)).build(consumer, "smooth_sandstone_slab_from_smooth_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_SANDSTONE), Blocks.SMOOTH_SANDSTONE_STAIRS).addCriterion("has_mossy_cobblestone", RecipeProvider.hasItem(Blocks.SMOOTH_SANDSTONE)).build(consumer, "smooth_sandstone_stairs_from_smooth_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE_SLAB, 2).addCriterion("has_smooth_red_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(consumer, "smooth_red_sandstone_slab_from_smooth_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE_STAIRS).addCriterion("has_smooth_red_sandstone", RecipeProvider.hasItem(Blocks.SMOOTH_RED_SANDSTONE)).build(consumer, "smooth_red_sandstone_stairs_from_smooth_red_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_QUARTZ), Blocks.SMOOTH_QUARTZ_SLAB, 2).addCriterion("has_smooth_quartz", RecipeProvider.hasItem(Blocks.SMOOTH_QUARTZ)).build(consumer, "smooth_quartz_slab_from_smooth_quartz_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_QUARTZ), Blocks.SMOOTH_QUARTZ_STAIRS).addCriterion("has_smooth_quartz", RecipeProvider.hasItem(Blocks.SMOOTH_QUARTZ)).build(consumer, "smooth_quartz_stairs_from_smooth_quartz_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_SLAB, 2).addCriterion("has_end_stone_brick", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_STAIRS).addCriterion("has_end_stone_brick", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_WALL).addCriterion("has_end_stone_brick", RecipeProvider.hasItem(Blocks.END_STONE_BRICKS)).build(consumer, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICKS).addCriterion("has_end_stone", RecipeProvider.hasItem(Blocks.END_STONE)).build(consumer, "end_stone_bricks_from_end_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_SLAB, 2).addCriterion("has_end_stone", RecipeProvider.hasItem(Blocks.END_STONE)).build(consumer, "end_stone_brick_slab_from_end_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_STAIRS).addCriterion("has_end_stone", RecipeProvider.hasItem(Blocks.END_STONE)).build(consumer, "end_stone_brick_stairs_from_end_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.END_STONE), Blocks.END_STONE_BRICK_WALL).addCriterion("has_end_stone", RecipeProvider.hasItem(Blocks.END_STONE)).build(consumer, "end_stone_brick_wall_from_end_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.SMOOTH_STONE), Blocks.SMOOTH_STONE_SLAB, 2).addCriterion("has_smooth_stone", RecipeProvider.hasItem(Blocks.SMOOTH_STONE)).build(consumer, "smooth_stone_slab_from_smooth_stone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.BLACKSTONE_SLAB, 2).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "blackstone_slab_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.BLACKSTONE_STAIRS).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "blackstone_stairs_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.BLACKSTONE_WALL).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "blackstone_wall_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_WALL).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_wall_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_SLAB, 2).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_slab_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_STAIRS).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_stairs_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.CHISELED_POLISHED_BLACKSTONE).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "chiseled_polished_blackstone_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICKS).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_bricks_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, 2).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_brick_slab_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_brick_stairs_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_WALL).addCriterion("has_blackstone", RecipeProvider.hasItem(Blocks.BLACKSTONE)).build(consumer, "polished_blackstone_brick_wall_from_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_SLAB, 2).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_slab_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_STAIRS).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_stairs_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICKS).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_bricks_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_WALL).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_wall_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, 2).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_brick_slab_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_brick_stairs_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.POLISHED_BLACKSTONE_BRICK_WALL).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "polished_blackstone_brick_wall_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE), Blocks.CHISELED_POLISHED_BLACKSTONE).addCriterion("has_polished_blackstone", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE)).build(consumer, "chiseled_polished_blackstone_from_polished_blackstone_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, 2).addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer, "polished_blackstone_brick_slab_from_polished_blackstone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS).addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer, "polished_blackstone_brick_stairs_from_polished_blackstone_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.POLISHED_BLACKSTONE_BRICK_WALL).addCriterion("has_polished_blackstone_bricks", RecipeProvider.hasItem(Blocks.POLISHED_BLACKSTONE_BRICKS)).build(consumer, "polished_blackstone_brick_wall_from_polished_blackstone_bricks_stonecutting");
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
        RecipeProvider.smithingReinforce(consumer, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
    }

    private static void smithingReinforce(Consumer<IFinishedRecipe> consumer, Item item, Item item2) {
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(item), Ingredient.fromItems(Items.NETHERITE_INGOT), item2).addCriterion("has_netherite_ingot", RecipeProvider.hasItem(Items.NETHERITE_INGOT)).build(consumer, Registry.ITEM.getKey(item2.asItem()).getPath() + "_smithing");
    }

    private static void shapelessPlanksNew(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, ITag<Item> iTag) {
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider, 4).addIngredient(iTag).setGroup("planks").addCriterion("has_log", RecipeProvider.hasItem(iTag)).build(consumer);
    }

    private static void shapelessPlanks(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, ITag<Item> iTag) {
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider, 4).addIngredient(iTag).setGroup("planks").addCriterion("has_logs", RecipeProvider.hasItem(iTag)).build(consumer);
    }

    private static void shapelessStrippedToPlanks(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 3).key(Character.valueOf('#'), iItemProvider2).patternLine("##").patternLine("##").setGroup("bark").addCriterion("has_log", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedBoat(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider).key(Character.valueOf('#'), iItemProvider2).patternLine("# #").patternLine("###").setGroup("boat").addCriterion("in_water", RecipeProvider.enteredBlock(Blocks.WATER)).build(consumer);
    }

    private static void shapelessWoodenButton(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider).addIngredient(iItemProvider2).setGroup("wooden_button").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenDoor(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 3).key(Character.valueOf('#'), iItemProvider2).patternLine("##").patternLine("##").patternLine("##").setGroup("wooden_door").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenFence(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 3).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('W'), iItemProvider2).patternLine("W#W").patternLine("W#W").setGroup("wooden_fence").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenFenceGate(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider).key(Character.valueOf('#'), Items.STICK).key(Character.valueOf('W'), iItemProvider2).patternLine("#W#").patternLine("#W#").setGroup("wooden_fence_gate").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenPressurePlate(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider).key(Character.valueOf('#'), iItemProvider2).patternLine("##").setGroup("wooden_pressure_plate").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenSlab(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 6).key(Character.valueOf('#'), iItemProvider2).patternLine("###").setGroup("wooden_slab").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenStairs(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 4).key(Character.valueOf('#'), iItemProvider2).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("wooden_stairs").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedWoodenTrapdoor(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 2).key(Character.valueOf('#'), iItemProvider2).patternLine("###").patternLine("###").setGroup("wooden_trapdoor").addCriterion("has_planks", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedSign(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 3).setGroup("sign").key(Character.valueOf('#'), iItemProvider2).key(Character.valueOf('X'), Items.STICK).patternLine("###").patternLine("###").patternLine(" X ").addCriterion("has_" + string, RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapelessColoredWool(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider).addIngredient(iItemProvider2).addIngredient(Blocks.WHITE_WOOL).setGroup("wool").addCriterion("has_white_wool", RecipeProvider.hasItem(Blocks.WHITE_WOOL)).build(consumer);
    }

    private static void shapedCarpet(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 3).key(Character.valueOf('#'), iItemProvider2).patternLine("##").setGroup("carpet").addCriterion("has_" + string, RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapelessColoredCarpet(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider.asItem()).getPath();
        String string2 = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 8).key(Character.valueOf('#'), Blocks.WHITE_CARPET).key(Character.valueOf('$'), iItemProvider2).patternLine("###").patternLine("#$#").patternLine("###").setGroup("carpet").addCriterion("has_white_carpet", RecipeProvider.hasItem(Blocks.WHITE_CARPET)).addCriterion("has_" + string2, RecipeProvider.hasItem(iItemProvider2)).build(consumer, string + "_from_white_carpet");
    }

    private static void shapedBed(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider).key(Character.valueOf('#'), iItemProvider2).key(Character.valueOf('X'), ItemTags.PLANKS).patternLine("###").patternLine("XXX").setGroup("bed").addCriterion("has_" + string, RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedColoredBed(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider.asItem()).getPath();
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider).addIngredient(Items.WHITE_BED).addIngredient(iItemProvider2).setGroup("dyed_bed").addCriterion("has_bed", RecipeProvider.hasItem(Items.WHITE_BED)).build(consumer, string + "_from_white_bed");
    }

    private static void shapedBanner(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider).key(Character.valueOf('#'), iItemProvider2).key(Character.valueOf('|'), Items.STICK).patternLine("###").patternLine("###").patternLine(" | ").setGroup("banner").addCriterion("has_" + string, RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedColoredGlass(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 8).key(Character.valueOf('#'), Blocks.GLASS).key(Character.valueOf('X'), iItemProvider2).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_glass").addCriterion("has_glass", RecipeProvider.hasItem(Blocks.GLASS)).build(consumer);
    }

    private static void shapedGlassPane(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 16).key(Character.valueOf('#'), iItemProvider2).patternLine("###").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass", RecipeProvider.hasItem(iItemProvider2)).build(consumer);
    }

    private static void shapedColoredPane(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        String string = Registry.ITEM.getKey(iItemProvider.asItem()).getPath();
        String string2 = Registry.ITEM.getKey(iItemProvider2.asItem()).getPath();
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 8).key(Character.valueOf('#'), Blocks.GLASS_PANE).key(Character.valueOf('$'), iItemProvider2).patternLine("###").patternLine("#$#").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass_pane", RecipeProvider.hasItem(Blocks.GLASS_PANE)).addCriterion("has_" + string2, RecipeProvider.hasItem(iItemProvider2)).build(consumer, string + "_from_glass_pane");
    }

    private static void shapedColoredTerracotta(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapedRecipeBuilder.shapedRecipe(iItemProvider, 8).key(Character.valueOf('#'), Blocks.TERRACOTTA).key(Character.valueOf('X'), iItemProvider2).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_terracotta").addCriterion("has_terracotta", RecipeProvider.hasItem(Blocks.TERRACOTTA)).build(consumer);
    }

    private static void shapedColorConcretePowder(Consumer<IFinishedRecipe> consumer, IItemProvider iItemProvider, IItemProvider iItemProvider2) {
        ShapelessRecipeBuilder.shapelessRecipe(iItemProvider, 8).addIngredient(iItemProvider2).addIngredient(Blocks.SAND, 4).addIngredient(Blocks.GRAVEL, 4).setGroup("concrete_powder").addCriterion("has_sand", RecipeProvider.hasItem(Blocks.SAND)).addCriterion("has_gravel", RecipeProvider.hasItem(Blocks.GRAVEL)).build(consumer);
    }

    private static void cookingRecipesForMethod(Consumer<IFinishedRecipe> consumer, String string, CookingRecipeSerializer<?> cookingRecipeSerializer, int n) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35f, n, cookingRecipeSerializer).addCriterion("has_beef", RecipeProvider.hasItem(Items.BEEF)).build(consumer, "cooked_beef_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35f, n, cookingRecipeSerializer).addCriterion("has_chicken", RecipeProvider.hasItem(Items.CHICKEN)).build(consumer, "cooked_chicken_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35f, n, cookingRecipeSerializer).addCriterion("has_cod", RecipeProvider.hasItem(Items.COD)).build(consumer, "cooked_cod_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1f, n, cookingRecipeSerializer).addCriterion("has_kelp", RecipeProvider.hasItem(Blocks.KELP)).build(consumer, "dried_kelp_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35f, n, cookingRecipeSerializer).addCriterion("has_salmon", RecipeProvider.hasItem(Items.SALMON)).build(consumer, "cooked_salmon_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35f, n, cookingRecipeSerializer).addCriterion("has_mutton", RecipeProvider.hasItem(Items.MUTTON)).build(consumer, "cooked_mutton_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35f, n, cookingRecipeSerializer).addCriterion("has_porkchop", RecipeProvider.hasItem(Items.PORKCHOP)).build(consumer, "cooked_porkchop_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35f, n, cookingRecipeSerializer).addCriterion("has_potato", RecipeProvider.hasItem(Items.POTATO)).build(consumer, "baked_potato_from_" + string);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35f, n, cookingRecipeSerializer).addCriterion("has_rabbit", RecipeProvider.hasItem(Items.RABBIT)).build(consumer, "cooked_rabbit_from_" + string);
    }

    private static EnterBlockTrigger.Instance enteredBlock(Block block) {
        return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, block, StatePropertiesPredicate.EMPTY);
    }

    private static InventoryChangeTrigger.Instance hasItem(IItemProvider iItemProvider) {
        return RecipeProvider.hasItem(ItemPredicate.Builder.create().item(iItemProvider).build());
    }

    private static InventoryChangeTrigger.Instance hasItem(ITag<Item> iTag) {
        return RecipeProvider.hasItem(ItemPredicate.Builder.create().tag(iTag).build());
    }

    private static InventoryChangeTrigger.Instance hasItem(ItemPredicate ... itemPredicateArray) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, itemPredicateArray);
    }

    @Override
    public String getName() {
        return "Recipes";
    }

    private static void lambda$act$0(Set set, DirectoryCache directoryCache, Path path, IFinishedRecipe iFinishedRecipe) {
        if (!set.add(iFinishedRecipe.getID())) {
            throw new IllegalStateException("Duplicate recipe " + iFinishedRecipe.getID());
        }
        RecipeProvider.saveRecipe(directoryCache, iFinishedRecipe.getRecipeJson(), path.resolve("data/" + iFinishedRecipe.getID().getNamespace() + "/recipes/" + iFinishedRecipe.getID().getPath() + ".json"));
        JsonObject jsonObject = iFinishedRecipe.getAdvancementJson();
        if (jsonObject != null) {
            RecipeProvider.saveRecipeAdvancement(directoryCache, jsonObject, path.resolve("data/" + iFinishedRecipe.getID().getNamespace() + "/advancements/" + iFinishedRecipe.getAdvancementID().getPath() + ".json"));
        }
    }
}

