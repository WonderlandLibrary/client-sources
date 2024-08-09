/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class VillagerTrades {
    public static final Map<VillagerProfession, Int2ObjectMap<ITrade[]>> VILLAGER_DEFAULT_TRADES = Util.make(Maps.newHashMap(), VillagerTrades::lambda$static$0);
    public static final Int2ObjectMap<ITrade[]> field_221240_b = VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new ItemsForEmeraldsTrade(Items.SEA_PICKLE, 2, 1, 5, 1), new ItemsForEmeraldsTrade(Items.SLIME_BALL, 4, 1, 5, 1), new ItemsForEmeraldsTrade(Items.GLOWSTONE, 2, 1, 5, 1), new ItemsForEmeraldsTrade(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new ItemsForEmeraldsTrade(Items.FERN, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.SUGAR_CANE, 1, 1, 8, 1), new ItemsForEmeraldsTrade(Items.PUMPKIN, 1, 1, 4, 1), new ItemsForEmeraldsTrade(Items.KELP, 3, 1, 12, 1), new ItemsForEmeraldsTrade(Items.CACTUS, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.DANDELION, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.POPPY, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.BLUE_ORCHID, 1, 1, 8, 1), new ItemsForEmeraldsTrade(Items.ALLIUM, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.AZURE_BLUET, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.RED_TULIP, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.ORANGE_TULIP, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.WHITE_TULIP, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.PINK_TULIP, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.OXEYE_DAISY, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.CORNFLOWER, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new ItemsForEmeraldsTrade(Items.WHEAT_SEEDS, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.MELON_SEEDS, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.ACACIA_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.BIRCH_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new ItemsForEmeraldsTrade(Items.RED_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.WHITE_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.PINK_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.BLACK_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.GREEN_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.MAGENTA_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.YELLOW_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.PURPLE_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.LIME_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.ORANGE_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.BROWN_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.CYAN_DYE, 1, 3, 12, 1), new ItemsForEmeraldsTrade(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeraldsTrade(Items.VINE, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.RED_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeraldsTrade(Items.LILY_PAD, 1, 2, 5, 1), new ItemsForEmeraldsTrade(Items.SAND, 1, 8, 8, 1), new ItemsForEmeraldsTrade(Items.RED_SAND, 1, 4, 6, 1)}, 2, new ITrade[]{new ItemsForEmeraldsTrade(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeraldsTrade(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeraldsTrade(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeraldsTrade(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeraldsTrade(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeraldsTrade(Items.PODZOL, 3, 3, 6, 1)}));

    private static Int2ObjectMap<ITrade[]> gatAsIntMap(ImmutableMap<Integer, ITrade[]> immutableMap) {
        return new Int2ObjectOpenHashMap<ITrade[]>(immutableMap);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(VillagerProfession.FARMER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.WHEAT, 20, 16, 2), new EmeraldForItemsTrade(Items.POTATO, 26, 16, 2), new EmeraldForItemsTrade(Items.CARROT, 22, 16, 2), new EmeraldForItemsTrade(Items.BEETROOT, 15, 16, 2), new ItemsForEmeraldsTrade(Items.BREAD, 1, 6, 16, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Blocks.PUMPKIN, 6, 12, 10), new ItemsForEmeraldsTrade(Items.PUMPKIN_PIE, 1, 4, 5), new ItemsForEmeraldsTrade(Items.APPLE, 1, 4, 16, 5)}, 3, new ITrade[]{new ItemsForEmeraldsTrade(Items.COOKIE, 3, 18, 10), new EmeraldForItemsTrade(Blocks.MELON, 4, 12, 20)}, 4, new ITrade[]{new ItemsForEmeraldsTrade(Blocks.CAKE, 1, 1, 12, 15), new SuspiciousStewForEmeraldTrade(Effects.NIGHT_VISION, 100, 15), new SuspiciousStewForEmeraldTrade(Effects.JUMP_BOOST, 160, 15), new SuspiciousStewForEmeraldTrade(Effects.WEAKNESS, 140, 15), new SuspiciousStewForEmeraldTrade(Effects.BLINDNESS, 120, 15), new SuspiciousStewForEmeraldTrade(Effects.POISON, 280, 15), new SuspiciousStewForEmeraldTrade(Effects.SATURATION, 7, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Items.GOLDEN_CARROT, 3, 3, 30), new ItemsForEmeraldsTrade(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
        hashMap.put(VillagerProfession.FISHERMAN, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.STRING, 20, 16, 2), new EmeraldForItemsTrade(Items.COAL, 10, 16, 2), new ItemsForEmeraldsAndItemsTrade(Items.COD, 6, Items.COOKED_COD, 6, 16, 1), new ItemsForEmeraldsTrade(Items.COD_BUCKET, 3, 1, 16, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.COD, 15, 16, 10), new ItemsForEmeraldsAndItemsTrade(Items.SALMON, 6, Items.COOKED_SALMON, 6, 16, 5), new ItemsForEmeraldsTrade(Items.CAMPFIRE, 2, 1, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.SALMON, 13, 16, 20), new EnchantedItemForEmeraldsTrade(Items.FISHING_ROD, 3, 3, 10, 0.2f)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.TROPICAL_FISH, 6, 12, 30)}, 5, new ITrade[]{new EmeraldForItemsTrade(Items.PUFFERFISH, 4, 12, 30), new EmeraldForVillageTypeItemTrade(1, 12, 30, ImmutableMap.builder().put(VillagerType.PLAINS, Items.OAK_BOAT).put(VillagerType.TAIGA, Items.SPRUCE_BOAT).put(VillagerType.SNOW, Items.SPRUCE_BOAT).put(VillagerType.DESERT, Items.JUNGLE_BOAT).put(VillagerType.JUNGLE, Items.JUNGLE_BOAT).put(VillagerType.SAVANNA, Items.ACACIA_BOAT).put(VillagerType.SWAMP, Items.DARK_OAK_BOAT).build())})));
        hashMap.put(VillagerProfession.SHEPHERD, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Blocks.WHITE_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.BROWN_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.BLACK_WOOL, 18, 16, 2), new EmeraldForItemsTrade(Blocks.GRAY_WOOL, 18, 16, 2), new ItemsForEmeraldsTrade(Items.SHEARS, 2, 1, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.WHITE_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.GRAY_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.BLACK_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.LIGHT_BLUE_DYE, 12, 16, 10), new EmeraldForItemsTrade(Items.LIME_DYE, 12, 16, 10), new ItemsForEmeraldsTrade(Blocks.WHITE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.YELLOW_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIME_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.PINK_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.CYAN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BROWN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.GREEN_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.RED_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLACK_WOOL, 1, 1, 16, 5), new ItemsForEmeraldsTrade(Blocks.WHITE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIME_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.PINK_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.CYAN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BROWN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.GREEN_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.RED_CARPET, 1, 4, 16, 5), new ItemsForEmeraldsTrade(Blocks.BLACK_CARPET, 1, 4, 16, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.YELLOW_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.LIGHT_GRAY_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.ORANGE_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.RED_DYE, 12, 16, 20), new EmeraldForItemsTrade(Items.PINK_DYE, 12, 16, 20), new ItemsForEmeraldsTrade(Blocks.WHITE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.YELLOW_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.RED_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BLACK_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BLUE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.BROWN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.CYAN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.GRAY_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.GREEN_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.LIME_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.MAGENTA_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.ORANGE_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.PINK_BED, 3, 1, 12, 10), new ItemsForEmeraldsTrade(Blocks.PURPLE_BED, 3, 1, 12, 10)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.BROWN_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.PURPLE_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.BLUE_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.GREEN_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.MAGENTA_DYE, 12, 16, 30), new EmeraldForItemsTrade(Items.CYAN_DYE, 12, 16, 30), new ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 12, 15), new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 12, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Items.PAINTING, 2, 3, 30)})));
        hashMap.put(VillagerProfession.FLETCHER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.STICK, 32, 16, 2), new ItemsForEmeraldsTrade(Items.ARROW, 1, 16, 1), new ItemsForEmeraldsAndItemsTrade(Blocks.GRAVEL, 10, Items.FLINT, 10, 12, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new ItemsForEmeraldsTrade(Items.BOW, 2, 1, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.STRING, 14, 16, 20), new ItemsForEmeraldsTrade(Items.CROSSBOW, 3, 1, 10)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.FEATHER, 24, 16, 30), new EnchantedItemForEmeraldsTrade(Items.BOW, 2, 3, 15)}, 5, new ITrade[]{new EmeraldForItemsTrade(Items.TRIPWIRE_HOOK, 8, 12, 30), new EnchantedItemForEmeraldsTrade(Items.CROSSBOW, 3, 3, 15), new ItemWithPotionForEmeraldsAndItemsTrade(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30)})));
        hashMap.put(VillagerProfession.LIBRARIAN, VillagerTrades.gatAsIntMap(ImmutableMap.builder().put(1, new ITrade[]{new EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new EnchantedBookForEmeraldsTrade(1), new ItemsForEmeraldsTrade(Blocks.BOOKSHELF, 9, 1, 12, 1)}).put(2, new ITrade[]{new EmeraldForItemsTrade(Items.BOOK, 4, 12, 10), new EnchantedBookForEmeraldsTrade(5), new ItemsForEmeraldsTrade(Items.LANTERN, 1, 1, 5)}).put(3, new ITrade[]{new EmeraldForItemsTrade(Items.INK_SAC, 5, 12, 20), new EnchantedBookForEmeraldsTrade(10), new ItemsForEmeraldsTrade(Items.GLASS, 1, 4, 10)}).put(4, new ITrade[]{new EmeraldForItemsTrade(Items.WRITABLE_BOOK, 2, 12, 30), new EnchantedBookForEmeraldsTrade(15), new ItemsForEmeraldsTrade(Items.CLOCK, 5, 1, 15), new ItemsForEmeraldsTrade(Items.COMPASS, 4, 1, 15)}).put(5, new ITrade[]{new ItemsForEmeraldsTrade(Items.NAME_TAG, 20, 1, 30)}).build()));
        hashMap.put(VillagerProfession.CARTOGRAPHER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new ItemsForEmeraldsTrade(Items.MAP, 7, 1, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.GLASS_PANE, 11, 16, 10), new EmeraldForMapTrade(13, Structure.field_236376_l_, MapDecoration.Type.MONUMENT, 12, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.COMPASS, 1, 12, 20), new EmeraldForMapTrade(14, Structure.field_236368_d_, MapDecoration.Type.MANSION, 12, 10)}, 4, new ITrade[]{new ItemsForEmeraldsTrade(Items.ITEM_FRAME, 7, 1, 15), new ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 15), new ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Items.GLOBE_BANNER_PATTERN, 8, 1, 30)})));
        hashMap.put(VillagerProfession.CLERIC, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.ROTTEN_FLESH, 32, 16, 2), new ItemsForEmeraldsTrade(Items.REDSTONE, 1, 2, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.GOLD_INGOT, 3, 12, 10), new ItemsForEmeraldsTrade(Items.LAPIS_LAZULI, 1, 1, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_FOOT, 2, 12, 20), new ItemsForEmeraldsTrade(Blocks.GLOWSTONE, 4, 1, 12, 10)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new EmeraldForItemsTrade(Items.GLASS_BOTTLE, 9, 12, 30), new ItemsForEmeraldsTrade(Items.ENDER_PEARL, 5, 1, 15)}, 5, new ITrade[]{new EmeraldForItemsTrade(Items.NETHER_WART, 22, 12, 30), new ItemsForEmeraldsTrade(Items.EXPERIENCE_BOTTLE, 3, 1, 30)})));
        hashMap.put(VillagerProfession.ARMORER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_LEGGINGS), 7, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_BOOTS), 4, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_HELMET), 5, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_CHESTPLATE), 9, 1, 12, 1, 0.2f)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_BOOTS), 1, 1, 12, 5, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_LEGGINGS), 3, 1, 12, 5, 0.2f)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.LAVA_BUCKET, 1, 12, 20), new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 20), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_HELMET), 1, 1, 12, 10, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 4, 1, 12, 10, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.SHIELD), 5, 1, 12, 10, 0.2f)}, 4, new ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_LEGGINGS, 14, 3, 15, 0.2f), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_BOOTS, 8, 3, 15, 0.2f)}, 5, new ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_HELMET, 8, 3, 30, 0.2f), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_CHESTPLATE, 16, 3, 30, 0.2f)})));
        hashMap.put(VillagerProfession.WEAPONSMITH, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2f), new EnchantedItemForEmeraldsTrade(Items.IRON_SWORD, 2, 3, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 24, 12, 20)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2f)}, 5, new ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_SWORD, 8, 3, 30, 0.2f)})));
        hashMap.put(VillagerProfession.TOOLSMITH, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2f)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 30, 12, 20), new EnchantedItemForEmeraldsTrade(Items.IRON_AXE, 1, 3, 10, 0.2f), new EnchantedItemForEmeraldsTrade(Items.IRON_SHOVEL, 2, 3, 10, 0.2f), new EnchantedItemForEmeraldsTrade(Items.IRON_PICKAXE, 3, 3, 10, 0.2f), new ItemsForEmeraldsTrade(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2f)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2f), new EnchantedItemForEmeraldsTrade(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2f)}, 5, new ITrade[]{new EnchantedItemForEmeraldsTrade(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2f)})));
        hashMap.put(VillagerProfession.BUTCHER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.CHICKEN, 14, 16, 2), new EmeraldForItemsTrade(Items.PORKCHOP, 7, 16, 2), new EmeraldForItemsTrade(Items.RABBIT, 4, 16, 2), new ItemsForEmeraldsTrade(Items.RABBIT_STEW, 1, 1, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new ItemsForEmeraldsTrade(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new ItemsForEmeraldsTrade(Items.COOKED_CHICKEN, 1, 8, 16, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.MUTTON, 7, 16, 20), new EmeraldForItemsTrade(Items.BEEF, 10, 16, 20)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.DRIED_KELP_BLOCK, 10, 12, 30)}, 5, new ITrade[]{new EmeraldForItemsTrade(Items.SWEET_BERRIES, 10, 12, 30)})));
        hashMap.put(VillagerProfession.LEATHERWORKER, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.LEATHER, 6, 16, 2), new DyedArmorForEmeraldsTrade(Items.LEATHER_LEGGINGS, 3), new DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 2, new ITrade[]{new EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 5), new DyedArmorForEmeraldsTrade(Items.LEATHER_BOOTS, 4, 12, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Items.RABBIT_HIDE, 9, 12, 20), new DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new DyedArmorForEmeraldsTrade(Items.LEATHER_HORSE_ARMOR, 6, 12, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2f), new DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 30)})));
        hashMap.put(VillagerProfession.MASON, VillagerTrades.gatAsIntMap(ImmutableMap.of(1, new ITrade[]{new EmeraldForItemsTrade(Items.CLAY_BALL, 10, 16, 2), new ItemsForEmeraldsTrade(Items.BRICK, 1, 10, 16, 1)}, 2, new ITrade[]{new EmeraldForItemsTrade(Blocks.STONE, 20, 16, 10), new ItemsForEmeraldsTrade(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5)}, 3, new ITrade[]{new EmeraldForItemsTrade(Blocks.GRANITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.ANDESITE, 16, 16, 20), new EmeraldForItemsTrade(Blocks.DIORITE, 16, 16, 20), new ItemsForEmeraldsTrade(Blocks.POLISHED_ANDESITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_DIORITE, 1, 4, 16, 10), new ItemsForEmeraldsTrade(Blocks.POLISHED_GRANITE, 1, 4, 16, 10)}, 4, new ITrade[]{new EmeraldForItemsTrade(Items.QUARTZ, 12, 12, 30), new ItemsForEmeraldsTrade(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.RED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeraldsTrade(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15)}, 5, new ITrade[]{new ItemsForEmeraldsTrade(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new ItemsForEmeraldsTrade(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30)})));
    }

    public static interface ITrade {
        @Nullable
        public MerchantOffer getOffer(Entity var1, Random var2);
    }

    static class EmeraldForItemsTrade
    implements ITrade {
        private final Item tradeItem;
        private final int count;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider iItemProvider, int n, int n2, int n3) {
            this.tradeItem = iItemProvider.asItem();
            this.count = n;
            this.maxUses = n2;
            this.xpValue = n3;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            ItemStack itemStack = new ItemStack(this.tradeItem, this.count);
            return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsTrade
    implements ITrade {
        private final ItemStack sellingItem;
        private final int emeraldCount;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block block, int n, int n2, int n3, int n4) {
            this(new ItemStack(block), n, n2, n3, n4);
        }

        public ItemsForEmeraldsTrade(Item item, int n, int n2, int n3) {
            this(new ItemStack(item), n, n2, 12, n3);
        }

        public ItemsForEmeraldsTrade(Item item, int n, int n2, int n3, int n4) {
            this(new ItemStack(item), n, n2, n3, n4);
        }

        public ItemsForEmeraldsTrade(ItemStack itemStack, int n, int n2, int n3, int n4) {
            this(itemStack, n, n2, n3, n4, 0.05f);
        }

        public ItemsForEmeraldsTrade(ItemStack itemStack, int n, int n2, int n3, int n4, float f) {
            this.sellingItem = itemStack;
            this.emeraldCount = n;
            this.sellingItemCount = n2;
            this.maxUses = n3;
            this.xpValue = n4;
            this.priceMultiplier = f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class SuspiciousStewForEmeraldTrade
    implements ITrade {
        final Effect effect;
        final int duration;
        final int xpValue;
        private final float priceMultiplier;

        public SuspiciousStewForEmeraldTrade(Effect effect, int n, int n2) {
            this.effect = effect;
            this.duration = n;
            this.xpValue = n2;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.addEffect(itemStack, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemStack, 12, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsAndItemsTrade
    implements ITrade {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItemsTrade(IItemProvider iItemProvider, int n, Item item, int n2, int n3, int n4) {
            this(iItemProvider, n, 1, item, n2, n3, n4);
        }

        public ItemsForEmeraldsAndItemsTrade(IItemProvider iItemProvider, int n, int n2, Item item, int n3, int n4, int n5) {
            this.buyingItem = new ItemStack(iItemProvider);
            this.buyingItemCount = n;
            this.emeraldCount = n2;
            this.sellingItem = new ItemStack(item);
            this.sellingItemCount = n3;
            this.maxUses = n4;
            this.xpValue = n5;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class EnchantedItemForEmeraldsTrade
    implements ITrade {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EnchantedItemForEmeraldsTrade(Item item, int n, int n2, int n3) {
            this(item, n, n2, n3, 0.05f);
        }

        public EnchantedItemForEmeraldsTrade(Item item, int n, int n2, int n3, float f) {
            this.sellingStack = new ItemStack(item);
            this.emeraldCount = n;
            this.maxUses = n2;
            this.xpValue = n3;
            this.priceMultiplier = f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            int n = 5 + random2.nextInt(15);
            ItemStack itemStack = EnchantmentHelper.addRandomEnchantment(random2, new ItemStack(this.sellingStack.getItem()), n, false);
            int n2 = Math.min(this.emeraldCount + n, 64);
            ItemStack itemStack2 = new ItemStack(Items.EMERALD, n2);
            return new MerchantOffer(itemStack2, itemStack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class EmeraldForVillageTypeItemTrade
    implements ITrade {
        private final Map<VillagerType, Item> villagerTypeItems;
        private final int count;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForVillageTypeItemTrade(int n, int n2, int n3, Map<VillagerType, Item> map) {
            Registry.VILLAGER_TYPE.stream().filter(arg_0 -> EmeraldForVillageTypeItemTrade.lambda$new$0(map, arg_0)).findAny().ifPresent(EmeraldForVillageTypeItemTrade::lambda$new$1);
            this.villagerTypeItems = map;
            this.count = n;
            this.maxUses = n2;
            this.xpValue = n3;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            if (entity2 instanceof IVillagerDataHolder) {
                ItemStack itemStack = new ItemStack(this.villagerTypeItems.get(((IVillagerDataHolder)((Object)entity2)).getVillagerData().getType()), this.count);
                return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, 0.05f);
            }
            return null;
        }

        private static void lambda$new$1(VillagerType villagerType) {
            throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getKey(villagerType));
        }

        private static boolean lambda$new$0(Map map, VillagerType villagerType) {
            return !map.containsKey(villagerType);
        }
    }

    static class ItemWithPotionForEmeraldsAndItemsTrade
    implements ITrade {
        private final ItemStack potionStack;
        private final int potionCount;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final Item buyingItem;
        private final int buyingItemCount;
        private final float priceMultiplier;

        public ItemWithPotionForEmeraldsAndItemsTrade(Item item, int n, Item item2, int n2, int n3, int n4, int n5) {
            this.potionStack = new ItemStack(item2);
            this.emeraldCount = n3;
            this.maxUses = n4;
            this.xpValue = n5;
            this.buyingItem = item;
            this.buyingItemCount = n;
            this.potionCount = n2;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.emeraldCount);
            List list = Registry.POTION.stream().filter(ItemWithPotionForEmeraldsAndItemsTrade::lambda$getOffer$0).collect(Collectors.toList());
            Potion potion = (Potion)list.get(random2.nextInt(list.size()));
            ItemStack itemStack2 = PotionUtils.addPotionToItemStack(new ItemStack(this.potionStack.getItem(), this.potionCount), potion);
            return new MerchantOffer(itemStack, new ItemStack(this.buyingItem, this.buyingItemCount), itemStack2, this.maxUses, this.xpValue, this.priceMultiplier);
        }

        private static boolean lambda$getOffer$0(Potion potion) {
            return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
        }
    }

    static class EnchantedBookForEmeraldsTrade
    implements ITrade {
        private final int xpValue;

        public EnchantedBookForEmeraldsTrade(int n) {
            this.xpValue = n;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            List list = Registry.ENCHANTMENT.stream().filter(Enchantment::canVillagerTrade).collect(Collectors.toList());
            Enchantment enchantment = (Enchantment)list.get(random2.nextInt(list.size()));
            int n = MathHelper.nextInt(random2, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemStack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, n));
            int n2 = 2 + random2.nextInt(5 + n * 10) + 3 * n;
            if (enchantment.isTreasureEnchantment()) {
                n2 *= 2;
            }
            if (n2 > 64) {
                n2 = 64;
            }
            return new MerchantOffer(new ItemStack(Items.EMERALD, n2), new ItemStack(Items.BOOK), itemStack, 12, this.xpValue, 0.2f);
        }
    }

    static class EmeraldForMapTrade
    implements ITrade {
        private final int count;
        private final Structure<?> structureName;
        private final MapDecoration.Type mapDecorationType;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForMapTrade(int n, Structure<?> structure, MapDecoration.Type type, int n2, int n3) {
            this.count = n;
            this.structureName = structure;
            this.mapDecorationType = type;
            this.maxUses = n2;
            this.xpValue = n3;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            if (!(entity2.world instanceof ServerWorld)) {
                return null;
            }
            ServerWorld serverWorld = (ServerWorld)entity2.world;
            BlockPos blockPos = serverWorld.func_241117_a_(this.structureName, entity2.getPosition(), 100, false);
            if (blockPos != null) {
                ItemStack itemStack = FilledMapItem.setupNewMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte)2, true, true);
                FilledMapItem.func_226642_a_(serverWorld, itemStack);
                MapData.addTargetDecoration(itemStack, blockPos, "+", this.mapDecorationType);
                itemStack.setDisplayName(new TranslationTextComponent("filled_map." + this.structureName.getStructureName().toLowerCase(Locale.ROOT)));
                return new MerchantOffer(new ItemStack(Items.EMERALD, this.count), new ItemStack(Items.COMPASS), itemStack, this.maxUses, this.xpValue, 0.2f);
            }
            return null;
        }
    }

    static class DyedArmorForEmeraldsTrade
    implements ITrade {
        private final Item tradeItem;
        private final int price;
        private final int maxUses;
        private final int xpValue;

        public DyedArmorForEmeraldsTrade(Item item, int n) {
            this(item, n, 12, 1);
        }

        public DyedArmorForEmeraldsTrade(Item item, int n, int n2, int n3) {
            this.tradeItem = item;
            this.price = n;
            this.maxUses = n2;
            this.xpValue = n3;
        }

        @Override
        public MerchantOffer getOffer(Entity entity2, Random random2) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.price);
            ItemStack itemStack2 = new ItemStack(this.tradeItem);
            if (this.tradeItem instanceof DyeableArmorItem) {
                ArrayList<DyeItem> arrayList = Lists.newArrayList();
                arrayList.add(DyedArmorForEmeraldsTrade.getRandomDyeItem(random2));
                if (random2.nextFloat() > 0.7f) {
                    arrayList.add(DyedArmorForEmeraldsTrade.getRandomDyeItem(random2));
                }
                if (random2.nextFloat() > 0.8f) {
                    arrayList.add(DyedArmorForEmeraldsTrade.getRandomDyeItem(random2));
                }
                itemStack2 = IDyeableArmorItem.dyeItem(itemStack2, arrayList);
            }
            return new MerchantOffer(itemStack, itemStack2, this.maxUses, this.xpValue, 0.2f);
        }

        private static DyeItem getRandomDyeItem(Random random2) {
            return DyeItem.getItem(DyeColor.byId(random2.nextInt(16)));
        }
    }
}

