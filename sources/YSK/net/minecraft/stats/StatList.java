package net.minecraft.stats;

import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import net.minecraft.util.*;

public class StatList
{
    public static StatBase minutesPlayedStat;
    private static final String[] I;
    public static StatBase field_181728_L;
    public static List<StatBase> generalStats;
    public static StatBase distanceFallenStat;
    public static StatBase field_181741_Y;
    public static StatBase damageDealtStat;
    public static StatBase field_181733_Q;
    public static StatBase field_181729_M;
    public static StatBase mobKillsStat;
    public static StatBase animalsBredStat;
    public static StatBase distanceClimbedStat;
    public static StatBase fishCaughtStat;
    public static StatBase junkFishedStat;
    public static StatBase playerKillsStat;
    public static StatBase distanceCrouchedStat;
    public static StatBase treasureFishedStat;
    public static final StatBase[] objectCraftStats;
    public static StatBase damageTakenStat;
    public static StatBase field_181734_R;
    public static StatBase field_181738_V;
    public static StatBase distanceWalkedStat;
    public static StatBase field_181739_W;
    public static StatBase leaveGameStat;
    public static StatBase field_181736_T;
    public static StatBase jumpStat;
    public static StatBase field_181725_I;
    public static StatBase distanceSwumStat;
    public static final StatBase[] objectUseStats;
    public static StatBase distanceFlownStat;
    public static StatBase field_181723_aa;
    public static StatBase deathsStat;
    public static StatBase distanceDoveStat;
    public static StatBase dropStat;
    public static StatBase distanceByBoatStat;
    public static StatBase distanceByHorseStat;
    public static StatBase timesTradedWithVillagerStat;
    protected static Map<String, StatBase> oneShotStats;
    public static StatBase distanceByMinecartStat;
    public static StatBase field_181737_U;
    public static List<StatCrafting> objectMineStats;
    public static StatBase field_181726_J;
    public static StatBase field_181742_Z;
    public static List<StatBase> allStats;
    public static StatBase field_181727_K;
    public static StatBase field_181740_X;
    public static StatBase field_181735_S;
    public static StatBase field_181730_N;
    public static StatBase distanceByPigStat;
    public static final StatBase[] mineBlockStatArray;
    public static StatBase field_181732_P;
    public static StatBase field_181724_H;
    public static StatBase distanceSprintedStat;
    public static StatBase timeSinceDeathStat;
    public static final StatBase[] objectBreakStats;
    public static StatBase field_181731_O;
    public static StatBase timesTalkedToVillagerStat;
    public static List<StatCrafting> itemStats;
    
    private static void replaceAllSimilarBlocks(final StatBase[] array) {
        mergeStatBases(array, Blocks.water, Blocks.flowing_water);
        mergeStatBases(array, Blocks.lava, Blocks.flowing_lava);
        mergeStatBases(array, Blocks.lit_pumpkin, Blocks.pumpkin);
        mergeStatBases(array, Blocks.lit_furnace, Blocks.furnace);
        mergeStatBases(array, Blocks.lit_redstone_ore, Blocks.redstone_ore);
        mergeStatBases(array, Blocks.powered_repeater, Blocks.unpowered_repeater);
        mergeStatBases(array, Blocks.powered_comparator, Blocks.unpowered_comparator);
        mergeStatBases(array, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
        mergeStatBases(array, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
        mergeStatBases(array, Blocks.double_stone_slab, Blocks.stone_slab);
        mergeStatBases(array, Blocks.double_wooden_slab, Blocks.wooden_slab);
        mergeStatBases(array, Blocks.double_stone_slab2, Blocks.stone_slab2);
        mergeStatBases(array, Blocks.grass, Blocks.dirt);
        mergeStatBases(array, Blocks.farmland, Blocks.dirt);
    }
    
    public static StatBase getOneShotStat(final String s) {
        return StatList.oneShotStats.get(s);
    }
    
    public static StatBase getStatEntityKilledBy(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        StatBase registerStat;
        if (stringFromID == null) {
            registerStat = null;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            final String string = StatList.I[0x2E ^ 0x42] + stringFromID;
            final String s = StatList.I[0x13 ^ 0x7E];
            final Object[] array = new Object[" ".length()];
            array["".length()] = new ChatComponentTranslation(StatList.I[0x14 ^ 0x7A] + stringFromID + StatList.I[0x46 ^ 0x29], new Object["".length()]);
            registerStat = new StatBase(string, new ChatComponentTranslation(s, array)).registerStat();
        }
        return registerStat;
    }
    
    public static StatBase getStatKillEntity(final EntityList.EntityEggInfo entityEggInfo) {
        final String stringFromID = EntityList.getStringFromID(entityEggInfo.spawnedID);
        StatBase registerStat;
        if (stringFromID == null) {
            registerStat = null;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            final String string = StatList.I[0x7 ^ 0x6F] + stringFromID;
            final String s = StatList.I[0x37 ^ 0x5E];
            final Object[] array = new Object[" ".length()];
            array["".length()] = new ChatComponentTranslation(StatList.I[0x3D ^ 0x57] + stringFromID + StatList.I[0xF6 ^ 0x9D], new Object["".length()]);
            registerStat = new StatBase(string, new ChatComponentTranslation(s, array)).registerStat();
        }
        return registerStat;
    }
    
    public static void init() {
        initMiningStats();
        initStats();
        initItemDepleteStats();
        initCraftableStats();
        AchievementList.init();
        EntityList.func_151514_a();
    }
    
    static {
        I();
        StatList.oneShotStats = (Map<String, StatBase>)Maps.newHashMap();
        StatList.allStats = (List<StatBase>)Lists.newArrayList();
        StatList.generalStats = (List<StatBase>)Lists.newArrayList();
        StatList.itemStats = (List<StatCrafting>)Lists.newArrayList();
        StatList.objectMineStats = (List<StatCrafting>)Lists.newArrayList();
        StatList.leaveGameStat = new StatBasic(StatList.I["".length()], new ChatComponentTranslation(StatList.I[" ".length()], new Object["".length()])).initIndependentStat().registerStat();
        StatList.minutesPlayedStat = new StatBasic(StatList.I["  ".length()], new ChatComponentTranslation(StatList.I["   ".length()], new Object["".length()]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.timeSinceDeathStat = new StatBasic(StatList.I[0x8C ^ 0x88], new ChatComponentTranslation(StatList.I[0x57 ^ 0x52], new Object["".length()]), StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.distanceWalkedStat = new StatBasic(StatList.I[0xC5 ^ 0xC3], new ChatComponentTranslation(StatList.I[0x7E ^ 0x79], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceCrouchedStat = new StatBasic(StatList.I[0x9D ^ 0x95], new ChatComponentTranslation(StatList.I[0x78 ^ 0x71], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSprintedStat = new StatBasic(StatList.I[0x5C ^ 0x56], new ChatComponentTranslation(StatList.I[0x82 ^ 0x89], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSwumStat = new StatBasic(StatList.I[0xBC ^ 0xB0], new ChatComponentTranslation(StatList.I[0x82 ^ 0x8F], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFallenStat = new StatBasic(StatList.I[0x43 ^ 0x4D], new ChatComponentTranslation(StatList.I[0x72 ^ 0x7D], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceClimbedStat = new StatBasic(StatList.I[0x39 ^ 0x29], new ChatComponentTranslation(StatList.I[0x79 ^ 0x68], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFlownStat = new StatBasic(StatList.I[0x63 ^ 0x71], new ChatComponentTranslation(StatList.I[0xD5 ^ 0xC6], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceDoveStat = new StatBasic(StatList.I[0x22 ^ 0x36], new ChatComponentTranslation(StatList.I[0xAC ^ 0xB9], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByMinecartStat = new StatBasic(StatList.I[0x14 ^ 0x2], new ChatComponentTranslation(StatList.I[0x79 ^ 0x6E], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByBoatStat = new StatBasic(StatList.I[0x42 ^ 0x5A], new ChatComponentTranslation(StatList.I[0x4D ^ 0x54], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByPigStat = new StatBasic(StatList.I[0x34 ^ 0x2E], new ChatComponentTranslation(StatList.I[0x57 ^ 0x4C], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByHorseStat = new StatBasic(StatList.I[0xAB ^ 0xB7], new ChatComponentTranslation(StatList.I[0x5 ^ 0x18], new Object["".length()]), StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.jumpStat = new StatBasic(StatList.I[0xA6 ^ 0xB8], new ChatComponentTranslation(StatList.I[0xDA ^ 0xC5], new Object["".length()])).initIndependentStat().registerStat();
        StatList.dropStat = new StatBasic(StatList.I[0x3D ^ 0x1D], new ChatComponentTranslation(StatList.I[0x8D ^ 0xAC], new Object["".length()])).initIndependentStat().registerStat();
        StatList.damageDealtStat = new StatBasic(StatList.I[0x32 ^ 0x10], new ChatComponentTranslation(StatList.I[0xA2 ^ 0x81], new Object["".length()]), StatBase.field_111202_k).registerStat();
        StatList.damageTakenStat = new StatBasic(StatList.I[0x77 ^ 0x53], new ChatComponentTranslation(StatList.I[0xAD ^ 0x88], new Object["".length()]), StatBase.field_111202_k).registerStat();
        StatList.deathsStat = new StatBasic(StatList.I[0xBF ^ 0x99], new ChatComponentTranslation(StatList.I[0x28 ^ 0xF], new Object["".length()])).registerStat();
        StatList.mobKillsStat = new StatBasic(StatList.I[0x92 ^ 0xBA], new ChatComponentTranslation(StatList.I[0x76 ^ 0x5F], new Object["".length()])).registerStat();
        StatList.animalsBredStat = new StatBasic(StatList.I[0x6D ^ 0x47], new ChatComponentTranslation(StatList.I[0x66 ^ 0x4D], new Object["".length()])).registerStat();
        StatList.playerKillsStat = new StatBasic(StatList.I[0x4E ^ 0x62], new ChatComponentTranslation(StatList.I[0xE8 ^ 0xC5], new Object["".length()])).registerStat();
        StatList.fishCaughtStat = new StatBasic(StatList.I[0x19 ^ 0x37], new ChatComponentTranslation(StatList.I[0x77 ^ 0x58], new Object["".length()])).registerStat();
        StatList.junkFishedStat = new StatBasic(StatList.I[0x13 ^ 0x23], new ChatComponentTranslation(StatList.I[0x53 ^ 0x62], new Object["".length()])).registerStat();
        StatList.treasureFishedStat = new StatBasic(StatList.I[0xB ^ 0x39], new ChatComponentTranslation(StatList.I[0xA7 ^ 0x94], new Object["".length()])).registerStat();
        StatList.timesTalkedToVillagerStat = new StatBasic(StatList.I[0xA9 ^ 0x9D], new ChatComponentTranslation(StatList.I[0x5D ^ 0x68], new Object["".length()])).registerStat();
        StatList.timesTradedWithVillagerStat = new StatBasic(StatList.I[0x7E ^ 0x48], new ChatComponentTranslation(StatList.I[0x47 ^ 0x70], new Object["".length()])).registerStat();
        StatList.field_181724_H = new StatBasic(StatList.I[0x23 ^ 0x1B], new ChatComponentTranslation(StatList.I[0xA7 ^ 0x9E], new Object["".length()])).registerStat();
        StatList.field_181725_I = new StatBasic(StatList.I[0x20 ^ 0x1A], new ChatComponentTranslation(StatList.I[0x59 ^ 0x62], new Object["".length()])).registerStat();
        StatList.field_181726_J = new StatBasic(StatList.I[0x7E ^ 0x42], new ChatComponentTranslation(StatList.I[0x44 ^ 0x79], new Object["".length()])).registerStat();
        StatList.field_181727_K = new StatBasic(StatList.I[0x84 ^ 0xBA], new ChatComponentTranslation(StatList.I[0x6C ^ 0x53], new Object["".length()])).registerStat();
        StatList.field_181728_L = new StatBasic(StatList.I[0x40 ^ 0x0], new ChatComponentTranslation(StatList.I[0x2D ^ 0x6C], new Object["".length()])).registerStat();
        StatList.field_181729_M = new StatBasic(StatList.I[0x41 ^ 0x3], new ChatComponentTranslation(StatList.I[0xD5 ^ 0x96], new Object["".length()])).registerStat();
        StatList.field_181730_N = new StatBasic(StatList.I[0xD4 ^ 0x90], new ChatComponentTranslation(StatList.I[0x49 ^ 0xC], new Object["".length()])).registerStat();
        StatList.field_181731_O = new StatBasic(StatList.I[0x39 ^ 0x7F], new ChatComponentTranslation(StatList.I[0xC5 ^ 0x82], new Object["".length()])).registerStat();
        StatList.field_181732_P = new StatBasic(StatList.I[0x12 ^ 0x5A], new ChatComponentTranslation(StatList.I[0x22 ^ 0x6B], new Object["".length()])).registerStat();
        StatList.field_181733_Q = new StatBasic(StatList.I[0x8C ^ 0xC6], new ChatComponentTranslation(StatList.I[0x72 ^ 0x39], new Object["".length()])).registerStat();
        StatList.field_181734_R = new StatBasic(StatList.I[0xC2 ^ 0x8E], new ChatComponentTranslation(StatList.I[0x52 ^ 0x1F], new Object["".length()])).registerStat();
        StatList.field_181735_S = new StatBasic(StatList.I[0x8C ^ 0xC2], new ChatComponentTranslation(StatList.I[0x33 ^ 0x7C], new Object["".length()])).registerStat();
        StatList.field_181736_T = new StatBasic(StatList.I[0x61 ^ 0x31], new ChatComponentTranslation(StatList.I[0x54 ^ 0x5], new Object["".length()])).registerStat();
        StatList.field_181737_U = new StatBasic(StatList.I[0x25 ^ 0x77], new ChatComponentTranslation(StatList.I[0x5 ^ 0x56], new Object["".length()])).registerStat();
        StatList.field_181738_V = new StatBasic(StatList.I[0x94 ^ 0xC0], new ChatComponentTranslation(StatList.I[0xA ^ 0x5F], new Object["".length()])).registerStat();
        StatList.field_181739_W = new StatBasic(StatList.I[0xC6 ^ 0x90], new ChatComponentTranslation(StatList.I[0x2D ^ 0x7A], new Object["".length()])).registerStat();
        StatList.field_181740_X = new StatBasic(StatList.I[0x41 ^ 0x19], new ChatComponentTranslation(StatList.I[0x99 ^ 0xC0], new Object["".length()])).registerStat();
        StatList.field_181741_Y = new StatBasic(StatList.I[0xCE ^ 0x94], new ChatComponentTranslation(StatList.I[0x72 ^ 0x29], new Object["".length()])).registerStat();
        StatList.field_181742_Z = new StatBasic(StatList.I[0xCE ^ 0x92], new ChatComponentTranslation(StatList.I[0x55 ^ 0x8], new Object["".length()])).registerStat();
        StatList.field_181723_aa = new StatBasic(StatList.I[0xE ^ 0x50], new ChatComponentTranslation(StatList.I[0x73 ^ 0x2C], new Object["".length()])).registerStat();
        mineBlockStatArray = new StatBase[1585 + 3202 - 3383 + 2692];
        objectCraftStats = new StatBase[6834 + 30098 - 34432 + 29500];
        objectUseStats = new StatBase[819 + 26622 - 21536 + 26095];
        objectBreakStats = new StatBase[18968 + 27230 - 37839 + 23641];
    }
    
    private static void initMiningStats() {
        final Iterator<Object> iterator = Block.blockRegistry.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Block block = iterator.next();
            final Item itemFromBlock = Item.getItemFromBlock(block);
            if (itemFromBlock != null) {
                final int idFromBlock = Block.getIdFromBlock(block);
                final String func_180204_a = func_180204_a(itemFromBlock);
                if (func_180204_a == null || !block.getEnableStats()) {
                    continue;
                }
                final StatBase[] mineBlockStatArray = StatList.mineBlockStatArray;
                final int n = idFromBlock;
                final String s = StatList.I[0xC1 ^ 0xA3];
                final String s2 = func_180204_a;
                final String s3 = StatList.I[0x24 ^ 0x47];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ItemStack(block).getChatComponent();
                mineBlockStatArray[n] = new StatCrafting(s, s2, new ChatComponentTranslation(s3, array), itemFromBlock).registerStat();
                StatList.objectMineStats.add((StatCrafting)StatList.mineBlockStatArray[idFromBlock]);
            }
        }
        replaceAllSimilarBlocks(StatList.mineBlockStatArray);
    }
    
    private static void mergeStatBases(final StatBase[] array, final Block block, final Block block2) {
        final int idFromBlock = Block.getIdFromBlock(block);
        final int idFromBlock2 = Block.getIdFromBlock(block2);
        if (array[idFromBlock] != null && array[idFromBlock2] == null) {
            array[idFromBlock2] = array[idFromBlock];
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            StatList.allStats.remove(array[idFromBlock]);
            StatList.objectMineStats.remove(array[idFromBlock]);
            StatList.generalStats.remove(array[idFromBlock]);
            array[idFromBlock] = array[idFromBlock2];
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void initStats() {
        final Iterator<Item> iterator = Item.itemRegistry.iterator();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                final StatBase[] objectUseStats = StatList.objectUseStats;
                final int n = idFromItem;
                final String s = StatList.I[0x21 ^ 0x45];
                final String s2 = func_180204_a;
                final String s3 = StatList.I[0xFD ^ 0x98];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ItemStack(item).getChatComponent();
                objectUseStats[n] = new StatCrafting(s, s2, new ChatComponentTranslation(s3, array), item).registerStat();
                if (item instanceof ItemBlock) {
                    continue;
                }
                StatList.itemStats.add((StatCrafting)StatList.objectUseStats[idFromItem]);
            }
        }
        replaceAllSimilarBlocks(StatList.objectUseStats);
    }
    
    private static void initCraftableStats() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IRecipe recipe = iterator.next();
            if (recipe.getRecipeOutput() != null) {
                hashSet.add(recipe.getRecipeOutput().getItem());
            }
        }
        final Iterator<ItemStack> iterator2 = FurnaceRecipes.instance().getSmeltingList().values().iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            hashSet.add(iterator2.next().getItem());
        }
        final Iterator<Item> iterator3 = hashSet.iterator();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final Item item = iterator3.next();
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null) {
                    continue;
                }
                final StatBase[] objectCraftStats = StatList.objectCraftStats;
                final int n = idFromItem;
                final String s = StatList.I[0xE1 ^ 0x81];
                final String s2 = func_180204_a;
                final String s3 = StatList.I[0xF1 ^ 0x90];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ItemStack(item).getChatComponent();
                objectCraftStats[n] = new StatCrafting(s, s2, new ChatComponentTranslation(s3, array), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectCraftStats);
    }
    
    private static void I() {
        (I = new String[0x45 ^ 0x35])["".length()] = I("%\u001e\u0010.b:\u000f\u0010,)\u0011\u000b\u001c?", "VjqZL");
        StatList.I[" ".length()] = I(">'5\u0005e!65\u0007.\n29\u0014", "MSTqK");
        StatList.I["  ".length()] = I("=\u001d\n\u001cg>\u0005\n\u0011\u0006 \f&\u0001';\u001d\u000e", "NikhI");
        StatList.I["   ".length()] = I("\u0000\u0003\u0016-f\u0003\u001b\u0016 \u0007\u001d\u0012:0&\u0006\u0003\u0012", "swwYH");
        StatList.I[0xA1 ^ 0xA5] = I("\u0014\u0018'\u0000w\u0013\u0005+\u0011\n\u000e\u0002%\u0011\u001d\u0002\r2\u001c", "glFtY");
        StatList.I[0x7 ^ 0x2] = I("\u001f#\u0014\u001db\u0018>\u0018\f\u001f\u00059\u0016\f\b\t6\u0001\u0001", "lWuiL");
        StatList.I[0x8B ^ 0x8D] = I("\n;3\u0001[\u000e.>\u001e:\u0017*\u0011\u0018", "yORuu");
        StatList.I[0xBA ^ 0xBD] = I("\u000679\u001aX\u0002\"4\u00059\u001b&\u001b\u0003", "uCXnv");
        StatList.I[0x9 ^ 0x1] = I(")%99l9#78!2\u001e6(\u00017", "ZQXMB");
        StatList.I[0x7 ^ 0xE] = I("+\u0011\u0004;\u007f;\u0017\n:20*\u000b*\u00125", "XeeOQ");
        StatList.I[0x10 ^ 0x1A] = I("\u0006\u0000\u0007\u0016{\u0006\u0004\u0014\u000b;\u0001;\b\u0007\u0016\u0018", "utfbU");
        StatList.I[0x84 ^ 0x8F] = I(")?\u00115C);\u0002(\u0003.\u0004\u001e$.7", "ZKpAm");
        StatList.I[0xBC ^ 0xB0] = I("\u0010\u0004\u0015\u0011v\u0010\u0007\u001d\b\u0017\r\u00157\b", "cpteX");
        StatList.I[0xB3 ^ 0xBE] = I("\u00056*?X\u00055\"&9\u0018'\b&", "vBKKv");
        StatList.I[0xB4 ^ 0xBA] = I("0.\u0017\u0017B%;\u001a\u000f#-?5\u000e", "CZvcl");
        StatList.I[0x8C ^ 0x83] = I("\u0004\u00078;W\u0011\u00125#6\u0019\u0016\u001a\"", "wsYOy");
        StatList.I[0x50 ^ 0x40] = I("\u0007\u001e\u0000\u001bj\u0017\u0006\b\u0002&;\u0004\u0004,)", "tjaoD");
        StatList.I[0x16 ^ 0x7] = I("8\u0000\u000e\u0004\\(\u0018\u0006\u001d\u0010\u0004\u001a\n3\u001f", "Ktopr");
        StatList.I[0x17 ^ 0x5] = I("\"%\u001b-G7=\u0003\u0016\u00074\u0012\u0017", "QQzYi");
        StatList.I[0xD5 ^ 0xC6] = I("\u0012\u0016\u0004\u0016{\u0007\u000e\u001c-;\u0004!\b", "abebU");
        StatList.I[0xB0 ^ 0xA4] = I("\u0017-\u00180k\u00000\u000f!\n\n<:)", "dYyDE");
        StatList.I[0x28 ^ 0x3D] = I("\u0004\u0013#8F\u0013\u000e4)'\u0019\u0002\u0001!", "wgBLh");
        StatList.I[0x72 ^ 0x64] = I("$\u000e\u000b0v:\u0013\u0004!;6\b\u001e\u000b629\u0007", "WzjDX");
        StatList.I[0x10 ^ 0x7] = I("\u0015\u001d\u0015=[\u000b\u0000\u001a,\u0016\u0007\u001b\u0000\u0006\u001b\u0003*\u0019", "fitIu");
        StatList.I[0x80 ^ 0x98] = I("%\u0005\u0010=L4\u001e\u0010=-8\u00142$", "VqqIb");
        StatList.I[0x81 ^ 0x98] = I("\u0006\u0001\u0018&\u007f\u0017\u001a\u0018&\u001e\u001b\u0010:?", "uuyRQ");
        StatList.I[0x6B ^ 0x71] = I("\u000462\u0000B\u0007+4;\u0002\u0012\u0001>", "wBStl");
        StatList.I[0x2A ^ 0x31] = I(" %\u0007\u0011M#8\u0001*\r6\u0012\u000b", "SQfec");
        StatList.I[0x1E ^ 0x2] = I("\u0002\u0010\u0004'`\u0019\u000b\u0017 +>\n\u0000\u0010#", "qdeSN");
        StatList.I[0x8C ^ 0x91] = I("\u0011\u0004*'W\n\u001f9 \u001c-\u001e.\u0010\u0014", "bpKSy");
        StatList.I[0x3F ^ 0x21] = I("\u0004$\u0010\rl\u001d%\u001c\t", "wPqyB");
        StatList.I[0x90 ^ 0x8F] = I("\u00101\u0016\u0017[\t0\u001a\u0013", "cEwcu");
        StatList.I[0x75 ^ 0x55] = I("2\u000e(\u0007_%\b&\u0003", "AzIsq");
        StatList.I[0x87 ^ 0xA6] = I("\u0005 \f7h\u0012&\u00023", "vTmCF");
        StatList.I[0x3A ^ 0x18] = I("2\u000e\u0007;L%\u001b\u000b.\u0005$>\u0003.\u000e5", "AzfOb");
        StatList.I[0x24 ^ 0x7] = I("\u0018\u001e\u00131}\u000f\u000b\u001f$4\u000e.\u0017$?\u001f", "kjrES");
        StatList.I[0x5F ^ 0x7B] = I("\u001a\u001d\u00000H\r\b\f%\u0001\f=\u0000/\u0003\u0007", "iiaDf");
        StatList.I[0x8C ^ 0xA9] = I("?\u00062 W(\u0013>5\u001e)&2?\u001c\"", "LrSTy");
        StatList.I[0x71 ^ 0x57] = I(">\u00157\u0004~)\u00047\u00048>", "MaVpP");
        StatList.I[0xE2 ^ 0xC5] = I("8,\r6m/=\r6+8", "KXlBC");
        StatList.I[0x26 ^ 0xE] = I("*\u00069&o4\u001d:\u0019(5\u001e+", "YrXRA");
        StatList.I[0x4E ^ 0x67] = I("\u001f595z\u0001.:\n=\u0000-+", "lAXAT");
        StatList.I[0x56 ^ 0x7C] = I("*<\u0004\u001fl8&\f\u0006#5;'\u0019'=", "YHekB");
        StatList.I[0xA3 ^ 0x88] = I("\n\u001020K\u0018\n:)\u0004\u0015\u0017\u00116\u0000\u001d", "ydSDe");
        StatList.I[0xBF ^ 0x93] = I(";<8:g8$87,:\u00030\"%;", "HHYNI");
        StatList.I[0x51 ^ 0x7C] = I("9\u0000\u0000 t:\u0018\u0000-?8?\b869", "JtaTZ");
        StatList.I[0x50 ^ 0x7E] = I("\u0006\u0013\n\u001el\u0013\u000e\u0018\u0002\u0001\u0014\u0012\f\u00026", "ugkjB");
        StatList.I[0x1E ^ 0x31] = I("\t:\u0014\u0019w\u001c'\u0006\u0005\u001a\u001b;\u0012\u0005-", "zNumY");
        StatList.I[0x63 ^ 0x53] = I("$\u001f;\u0006b=\u001e4\u0019\n>\u00182\u0017(", "WkZrL");
        StatList.I[0x6A ^ 0x5B] = I("\u0017#8\u0013`\u000e\"7\f\b\r$1\u0002*", "dWYgN");
        StatList.I[0x48 ^ 0x7A] = I("\u0003\u000e'=`\u0004\b#(=\u0005\b#\u000f'\u0003\u0012#-", "pzFIN");
        StatList.I[0xB4 ^ 0x87] = I("9\u0018\u0017\u0011Y>\u001e\u0013\u0004\u0004?\u001e\u0013#\u001e9\u0004\u0013\u0001", "Jlvew");
        StatList.I[0xB3 ^ 0x87] = I("\u0016%5 v\u001108?=\u0001\u0005;\u00021\t=53=\u0017", "eQTTX");
        StatList.I[0x8E ^ 0xBB] = I("\u0005\u0013(-A\u0002\u0006%2\n\u00123&\u000f\u0006\u001a\u000b(>\n\u0004", "vgIYo");
        StatList.I[0x49 ^ 0x7F] = I("\u000b0\u001b5g\f6\u001b%,\u001c\u0013\u00135!.-\u0016-(\u001f!\b", "xDzAI");
        StatList.I[0x7F ^ 0x48] = I(">\u0003\u0005\u001b\u007f9\u0005\u0005\u000b4) \r\u001b9\u001b\u001e\b\u00030*\u0012\u0016", "MwdoQ");
        StatList.I[0x60 ^ 0x58] = I(":3\u0019\u0004@*&\u0013\u0015=%.\u001b\u0015\u001d\f&\f\u0015\u0000", "IGxpn");
        StatList.I[0xF ^ 0x36] = I("\u0004\u0005\u000e=\\\u0014\u0010\u0004,!\u001b\u0018\f,\u00012\u0010\u001b,\u001c", "wqoIr");
        StatList.I[0x86 ^ 0xBC] = I("<6\f6w,#\u0018.==-\u0003\u00040#.\b&", "OBmBY");
        StatList.I[0x99 ^ 0xA2] = I("\n%\f3V\u001a0\u0018+\u001c\u000b>\u0003\u0001\u0011\u0015=\b#", "yQmGx");
        StatList.I[0x65 ^ 0x59] = I("\u0011%\u0016-E\u00010\u00025\u000f\u0010>\u0019\f\u0018\u00075", "bQwYk");
        StatList.I[0x1E ^ 0x23] = I("18.:\\!-:\"\u00160#!\u001b\u0001'(", "BLONr");
        StatList.I[0x5F ^ 0x61] = I("*\u0013\u0002-\\8\u0015\u000e6\u0000\u001a\u000b\u00068\u001c<\u0003", "YgcYr");
        StatList.I[0xFF ^ 0xC0] = I("!0/-k36#67\u0011(+8+7 ", "RDNYE");
        StatList.I[0x49 ^ 0x9] = I("*\u0018\u0019\"F;\r\u00168\r+/\u00143\t7\t\u001c", "YlxVh");
        StatList.I[0x52 ^ 0x13] = I("\u00121\u000b\fE\u0003$\u0004\u0016\u000e\u0013\u0006\u0006\u001d\n\u000f \u000e", "aEjxk");
        StatList.I[0x3A ^ 0x78] = I("\u0017\f\u0005\"T\u0006\n\u0001!\u0013\n\u001f\u0017\"\u001b\n\u001c-8\u000e\u0001\n\u00055\u000e\r\u0017\n", "dxdVz");
        StatList.I[0x73 ^ 0x30] = I("\u0002..${\u0013(*'<\u001f=<$4\u001f>\u0006>!\u0014(.3!\u00185!", "qZOPU");
        StatList.I[0x6D ^ 0x29] = I("+\u001b3\u0019a:\n3\u000e 6&<\u0019**\u000e1\u0019&7\u0001", "XoRmO");
        StatList.I[0x25 ^ 0x60] = I("\u00150\b9{\u0004!\b.:\b\r\u000790\u0014%\n9<\t*", "fDiMU");
        StatList.I[0xF ^ 0x49] = I(" \u000e*&\u007f7\b$\"!6\b\u0002<\"#\u001f(&47", "SzKRQ");
        StatList.I[0xCE ^ 0x89] = I("\u001a\u0015 -g\r\u0013.)9\f\u0013\b7:\u0019\u0004\"-,\r", "iaAYI");
        StatList.I[0x16 ^ 0x5E] = I("\u001825\u001fG\u0003)$\u001b\f\u0019\u000f:\u0018\u0019\u000e% \u000e\r", "kFTki");
        StatList.I[0x70 ^ 0x39] = I("251.\\). *\u00173\b>)\u0002$\"$?\u0016", "AAPZr");
        StatList.I[0x46 ^ 0xC] = I("\"\u0005\u00061g5\u0018\u00145,?\u0002\u00027\u0000?\u0002\u0017 *%\u0014\u0003", "QqgEI");
        StatList.I[0x72 ^ 0x39] = I("'-\u0007&x00\u0015\"3:*\u0003 \u001f:*\u001675 <\u0002", "TYfRV");
        StatList.I[0x8F ^ 0xC3] = I("\t9\b\u0001_\u0014\"\u001d\u0010\u0013\u0016\"\n\u001e!\u0016,\u0010\u0010\u0015", "zMiuq");
        StatList.I[0x6 ^ 0x4B] = I("\u0014.&#D\t532\b\u000b5$<:\u000b;>2\u000e", "gZGWj");
        StatList.I[0x69 ^ 0x27] = I("\u00197\u0004\u0004W\u0004,\u0011\u0015\u001b\u0006,\u0006\u001b-\u001f-\u0000\u0014", "jCepy");
        StatList.I[0x5B ^ 0x14] = I("7\u0016\u0019\u001bW*\r\f\n\u001b(\r\u001b\u0004-1\f\u001d\u000b", "Dbxoy");
        StatList.I[0xFE ^ 0xAE] = I("\u0002\u001b\u0014\fK\u0017\u0003\u001a\u000f\u0000\u0003?\u001a\f\u0011\u0014\u000b", "qouxe");
        StatList.I[0x33 ^ 0x62] = I("<-1\u001eh)5?\u001d#=\t?\u001e2*=", "OYPjF");
        StatList.I[0x36 ^ 0x64] = I("\u00072\u0007<{\u00004\u00078%\u0011\"% 0\u000722:<\u0013!\u0003:0\u0010", "tFfHU");
        StatList.I[0x39 ^ 0x6A] = I("\u0003\u001f;\u0000^\u0004\u0019;\u0004\u0000\u0015\u000f\u0019\u001c\u0015\u0003\u001f\u000e\u0006\u0019\u0017\f?\u0006\u0015\u0014", "pkZtp");
        StatList.I[0x21 ^ 0x75] = I("#\u00117\u0000v5\u000b2\u0011*3\r3\u0007,\u001f\u00153\u001a=4", "PeVtX");
        StatList.I[0x90 ^ 0xC5] = I("\u0018./<Y\u000e4*-\u0005\b2+;\u0003$*+&\u0012\u000f", "kZNHw");
        StatList.I[0xE3 ^ 0xB5] = I("5?\u0004&m/?\u0000?\u0006((\r3-2.\u0001", "FKeRC");
        StatList.I[0x0 ^ 0x57] = I("\u0018&5?L\u0002&1&'\u00051<*\f\u001f70", "kRTKb");
        StatList.I[0xF5 ^ 0xAD] = I("\u0006\u0016\n?Y\u0007\u0007\b$\u0005\u00112\u0007*\u000e\u0010\u0006", "ubkKw");
        StatList.I[0x76 ^ 0x2F] = I("\u00126\n\u001eo\u0013'\b\u00053\u0005\u0012\u0007\u000b8\u0004&", "aBkjA");
        StatList.I[0x1D ^ 0x47] = I("*\u0004\u000b3\u007f?\u0005\u0018)0:\u0015#)%<\u0002\u000b$%0\u001f\u0004", "YpjGQ");
        StatList.I[0xFD ^ 0xA6] = I("%3\u000f\u001aK02\u001c\u0000\u00045\"'\u0000\u001135\u000f\r\u0011?(\u0000", "VGnne");
        StatList.I[0x4B ^ 0x17] = I("\u00129\u0007\u0000e\u0002?\u0007\u0012?\b#\u0001 *\u0003!\u0003=%\u0015(\u0014\u0015(\u0015$\t\u001a", "aMftK");
        StatList.I[0x4 ^ 0x59] = I(";\u00190<H?\u0002##\u0004-\u00032 /&\u00194:\u0007+\u00198'\b", "HmQHf");
        StatList.I[0xE ^ 0x50] = I("\u0012&)\u0006g\u0002:-\u0001=.\"-\u001c,\u0005", "aRHrI");
        StatList.I[0xE0 ^ 0xBF] = I("\u000b\u0005\n\u001bv\u001b\u0019\u000e\u001c,7\u0001\u000e\u0001=\u001c", "xqkoX");
        StatList.I[0x77 ^ 0x17] = I("%:-\u0010m5<-\u00027\u001f:)\tm", "VNLdC");
        StatList.I[0x1D ^ 0x7C] = I("\u001222\u0003t\u000242\u0011.(26\u001a", "aFSwZ");
        StatList.I[0x15 ^ 0x77] = I("\n\u0006\u0006\u0001J\u0014\u001b\t\u0010&\u0015\u001d\u0004\u001eJ", "yrgud");
        StatList.I[0xEE ^ 0x8D] = I("96\u000e\u0016X'+\u0001\u00074&-\f\t", "JBobv");
        StatList.I[0xF7 ^ 0x93] = I("\n\u001c\u0003\u0015Y\f\u001b\u0007(\u0003\u001c\u0005L", "yhbaw");
        StatList.I[0x70 ^ 0x15] = I(":\u0005\u0011\u0005W<\u0002\u00158\r,\u001c", "Iqpqy");
        StatList.I[0xD9 ^ 0xBF] = I("\u001a\u0019\u0016!{\u000b\u001f\u00124> \u0019\u00128{", "imwUU");
        StatList.I[0xCA ^ 0xAD] = I("<\u00019$I-\u0007=1\f\u0006\u0001==", "OuXPg");
        StatList.I[0x5C ^ 0x34] = I("$\u0019\u0019=W<\u0004\u0014%<9\u0019\u0011=\u0000y", "WmxIy");
        StatList.I[0x73 ^ 0x1A] = I("\u0003\u0003%\u0010m\u0015\u00190\r7\t<-\b/", "pwDdC");
        StatList.I[0xE5 ^ 0x8F] = I("\u001d#2:\u0018\u0001c", "xMFSl");
        StatList.I[0xE3 ^ 0x88] = I("|\u000f&*\u0017", "RaGGr");
        StatList.I[0xF8 ^ 0x94] = I("\u0002\u001f1\u0007A\u0014\u0005$\u001a\u001b\b 9\u001f\u0003\u0014\u000f\u0012\nA", "qkPso");
        StatList.I[0x6C ^ 0x1] = I("\u000b\u0003*:l\u001d\u0019?'6\u0001<\"\".\u001d\u0013\t7", "xwKNB");
        StatList.I[0xF ^ 0x61] = I("\u00076\u001c*\u0012\u001bv", "bXhCf");
        StatList.I[0xFE ^ 0x91] = I("M\u001c\u0010\u0017\u000b", "crqzn");
    }
    
    private static String func_180204_a(final Item item) {
        final ResourceLocation resourceLocation = Item.itemRegistry.getNameForObject(item);
        String replace;
        if (resourceLocation != null) {
            replace = resourceLocation.toString().replace((char)(0xA8 ^ 0x92), (char)(0x62 ^ 0x4C));
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            replace = null;
        }
        return replace;
    }
    
    private static void initItemDepleteStats() {
        final Iterator<Item> iterator = Item.itemRegistry.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            if (item != null) {
                final int idFromItem = Item.getIdFromItem(item);
                final String func_180204_a = func_180204_a(item);
                if (func_180204_a == null || !item.isDamageable()) {
                    continue;
                }
                final StatBase[] objectBreakStats = StatList.objectBreakStats;
                final int n = idFromItem;
                final String s = StatList.I[0x6E ^ 0x8];
                final String s2 = func_180204_a;
                final String s3 = StatList.I[0x6E ^ 0x9];
                final Object[] array = new Object[" ".length()];
                array["".length()] = new ItemStack(item).getChatComponent();
                objectBreakStats[n] = new StatCrafting(s, s2, new ChatComponentTranslation(s3, array), item).registerStat();
            }
        }
        replaceAllSimilarBlocks(StatList.objectBreakStats);
    }
}
