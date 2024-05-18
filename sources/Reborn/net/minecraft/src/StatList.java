package net.minecraft.src;

import java.util.*;

public class StatList
{
    protected static Map oneShotStats;
    public static List allStats;
    public static List generalStats;
    public static List itemStats;
    public static List objectMineStats;
    public static StatBase startGameStat;
    public static StatBase createWorldStat;
    public static StatBase loadWorldStat;
    public static StatBase joinMultiplayerStat;
    public static StatBase leaveGameStat;
    public static StatBase minutesPlayedStat;
    public static StatBase distanceWalkedStat;
    public static StatBase distanceSwumStat;
    public static StatBase distanceFallenStat;
    public static StatBase distanceClimbedStat;
    public static StatBase distanceFlownStat;
    public static StatBase distanceDoveStat;
    public static StatBase distanceByMinecartStat;
    public static StatBase distanceByBoatStat;
    public static StatBase distanceByPigStat;
    public static StatBase jumpStat;
    public static StatBase dropStat;
    public static StatBase damageDealtStat;
    public static StatBase damageTakenStat;
    public static StatBase deathsStat;
    public static StatBase mobKillsStat;
    public static StatBase playerKillsStat;
    public static StatBase fishCaughtStat;
    public static StatBase[] mineBlockStatArray;
    public static StatBase[] objectCraftStats;
    public static StatBase[] objectUseStats;
    public static StatBase[] objectBreakStats;
    private static boolean blockStatsInitialized;
    private static boolean itemStatsInitialized;
    
    static {
        StatList.oneShotStats = new HashMap();
        StatList.allStats = new ArrayList();
        StatList.generalStats = new ArrayList();
        StatList.itemStats = new ArrayList();
        StatList.objectMineStats = new ArrayList();
        StatList.startGameStat = new StatBasic(1000, "stat.startGame").initIndependentStat().registerStat();
        StatList.createWorldStat = new StatBasic(1001, "stat.createWorld").initIndependentStat().registerStat();
        StatList.loadWorldStat = new StatBasic(1002, "stat.loadWorld").initIndependentStat().registerStat();
        StatList.joinMultiplayerStat = new StatBasic(1003, "stat.joinMultiplayer").initIndependentStat().registerStat();
        StatList.leaveGameStat = new StatBasic(1004, "stat.leaveGame").initIndependentStat().registerStat();
        StatList.minutesPlayedStat = new StatBasic(1100, "stat.playOneMinute", StatBase.timeStatType).initIndependentStat().registerStat();
        StatList.distanceWalkedStat = new StatBasic(2000, "stat.walkOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceSwumStat = new StatBasic(2001, "stat.swimOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFallenStat = new StatBasic(2002, "stat.fallOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceClimbedStat = new StatBasic(2003, "stat.climbOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceFlownStat = new StatBasic(2004, "stat.flyOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceDoveStat = new StatBasic(2005, "stat.diveOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByMinecartStat = new StatBasic(2006, "stat.minecartOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByBoatStat = new StatBasic(2007, "stat.boatOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.distanceByPigStat = new StatBasic(2008, "stat.pigOneCm", StatBase.distanceStatType).initIndependentStat().registerStat();
        StatList.jumpStat = new StatBasic(2010, "stat.jump").initIndependentStat().registerStat();
        StatList.dropStat = new StatBasic(2011, "stat.drop").initIndependentStat().registerStat();
        StatList.damageDealtStat = new StatBasic(2020, "stat.damageDealt").registerStat();
        StatList.damageTakenStat = new StatBasic(2021, "stat.damageTaken").registerStat();
        StatList.deathsStat = new StatBasic(2022, "stat.deaths").registerStat();
        StatList.mobKillsStat = new StatBasic(2023, "stat.mobKills").registerStat();
        StatList.playerKillsStat = new StatBasic(2024, "stat.playerKills").registerStat();
        StatList.fishCaughtStat = new StatBasic(2025, "stat.fishCaught").registerStat();
        StatList.mineBlockStatArray = initMinableStats("stat.mineBlock", 16777216);
        AchievementList.init();
        StatList.blockStatsInitialized = false;
        StatList.itemStatsInitialized = false;
    }
    
    public static void nopInit() {
    }
    
    public static void initBreakableStats() {
        StatList.objectUseStats = initUsableStats(StatList.objectUseStats, "stat.useItem", 16908288, 0, 256);
        StatList.objectBreakStats = initBreakStats(StatList.objectBreakStats, "stat.breakItem", 16973824, 0, 256);
        StatList.blockStatsInitialized = true;
        initCraftableStats();
    }
    
    public static void initStats() {
        StatList.objectUseStats = initUsableStats(StatList.objectUseStats, "stat.useItem", 16908288, 256, 32000);
        StatList.objectBreakStats = initBreakStats(StatList.objectBreakStats, "stat.breakItem", 16973824, 256, 32000);
        StatList.itemStatsInitialized = true;
        initCraftableStats();
    }
    
    public static void initCraftableStats() {
        if (StatList.blockStatsInitialized && StatList.itemStatsInitialized) {
            final HashSet var0 = new HashSet();
            for (final IRecipe var3 : CraftingManager.getInstance().getRecipeList()) {
                if (var3.getRecipeOutput() != null) {
                    var0.add(var3.getRecipeOutput().itemID);
                }
            }
            for (final ItemStack var4 : FurnaceRecipes.smelting().getSmeltingList().values()) {
                var0.add(var4.itemID);
            }
            StatList.objectCraftStats = new StatBase[32000];
            for (final Integer var5 : var0) {
                if (Item.itemsList[var5] != null) {
                    final String var6 = StatCollector.translateToLocalFormatted("stat.craftItem", Item.itemsList[var5].getStatName());
                    StatList.objectCraftStats[var5] = new StatCrafting(16842752 + var5, var6, var5).registerStat();
                }
            }
            replaceAllSimilarBlocks(StatList.objectCraftStats);
        }
    }
    
    private static StatBase[] initMinableStats(final String par0Str, final int par1) {
        final StatBase[] var2 = new StatBase[256];
        for (int var3 = 0; var3 < 256; ++var3) {
            if (Block.blocksList[var3] != null && Block.blocksList[var3].getEnableStats()) {
                final String var4 = StatCollector.translateToLocalFormatted(par0Str, Block.blocksList[var3].getLocalizedName());
                var2[var3] = new StatCrafting(par1 + var3, var4, var3).registerStat();
                StatList.objectMineStats.add(var2[var3]);
            }
        }
        replaceAllSimilarBlocks(var2);
        return var2;
    }
    
    private static StatBase[] initUsableStats(StatBase[] par0ArrayOfStatBase, final String par1Str, final int par2, final int par3, final int par4) {
        if (par0ArrayOfStatBase == null) {
            par0ArrayOfStatBase = new StatBase[32000];
        }
        for (int var5 = par3; var5 < par4; ++var5) {
            if (Item.itemsList[var5] != null) {
                final String var6 = StatCollector.translateToLocalFormatted(par1Str, Item.itemsList[var5].getStatName());
                par0ArrayOfStatBase[var5] = new StatCrafting(par2 + var5, var6, var5).registerStat();
                if (var5 >= 256) {
                    StatList.itemStats.add(par0ArrayOfStatBase[var5]);
                }
            }
        }
        replaceAllSimilarBlocks(par0ArrayOfStatBase);
        return par0ArrayOfStatBase;
    }
    
    private static StatBase[] initBreakStats(StatBase[] par0ArrayOfStatBase, final String par1Str, final int par2, final int par3, final int par4) {
        if (par0ArrayOfStatBase == null) {
            par0ArrayOfStatBase = new StatBase[32000];
        }
        for (int var5 = par3; var5 < par4; ++var5) {
            if (Item.itemsList[var5] != null && Item.itemsList[var5].isDamageable()) {
                final String var6 = StatCollector.translateToLocalFormatted(par1Str, Item.itemsList[var5].getStatName());
                par0ArrayOfStatBase[var5] = new StatCrafting(par2 + var5, var6, var5).registerStat();
            }
        }
        replaceAllSimilarBlocks(par0ArrayOfStatBase);
        return par0ArrayOfStatBase;
    }
    
    private static void replaceAllSimilarBlocks(final StatBase[] par0ArrayOfStatBase) {
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.waterStill.blockID, Block.waterMoving.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.lavaStill.blockID, Block.lavaStill.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.furnaceBurning.blockID, Block.furnaceIdle.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.stoneDoubleSlab.blockID, Block.stoneSingleSlab.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.woodDoubleSlab.blockID, Block.woodSingleSlab.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.grass.blockID, Block.dirt.blockID);
        replaceSimilarBlocks(par0ArrayOfStatBase, Block.tilledField.blockID, Block.dirt.blockID);
    }
    
    private static void replaceSimilarBlocks(final StatBase[] par0ArrayOfStatBase, final int par1, final int par2) {
        if (par0ArrayOfStatBase[par1] != null && par0ArrayOfStatBase[par2] == null) {
            par0ArrayOfStatBase[par2] = par0ArrayOfStatBase[par1];
        }
        else {
            StatList.allStats.remove(par0ArrayOfStatBase[par1]);
            StatList.objectMineStats.remove(par0ArrayOfStatBase[par1]);
            StatList.generalStats.remove(par0ArrayOfStatBase[par1]);
            par0ArrayOfStatBase[par1] = par0ArrayOfStatBase[par2];
        }
    }
    
    public static StatBase getOneShotStat(final int par0) {
        return StatList.oneShotStats.get(par0);
    }
}
