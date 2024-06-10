/*   1:    */ package net.minecraft.stats;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.util.JsonSerializableSet;
/*   9:    */ 
/*  10:    */ public class AchievementList
/*  11:    */ {
/*  12:    */   public static int minDisplayColumn;
/*  13:    */   public static int minDisplayRow;
/*  14:    */   public static int maxDisplayColumn;
/*  15:    */   public static int maxDisplayRow;
/*  16: 25 */   public static List achievementList = new ArrayList();
/*  17: 28 */   public static Achievement openInventory = new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.book, null).initIndependentStat().registerStat();
/*  18: 31 */   public static Achievement mineWood = new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.log, openInventory).registerStat();
/*  19: 34 */   public static Achievement buildWorkBench = new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.crafting_table, mineWood).registerStat();
/*  20: 37 */   public static Achievement buildPickaxe = new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.wooden_pickaxe, buildWorkBench).registerStat();
/*  21: 40 */   public static Achievement buildFurnace = new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.furnace, buildPickaxe).registerStat();
/*  22: 43 */   public static Achievement acquireIron = new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.iron_ingot, buildFurnace).registerStat();
/*  23: 46 */   public static Achievement buildHoe = new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.wooden_hoe, buildWorkBench).registerStat();
/*  24: 49 */   public static Achievement makeBread = new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.bread, buildHoe).registerStat();
/*  25: 52 */   public static Achievement bakeCake = new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.cake, buildHoe).registerStat();
/*  26: 55 */   public static Achievement buildBetterPickaxe = new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.stone_pickaxe, buildPickaxe).registerStat();
/*  27: 58 */   public static Achievement cookFish = new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.cooked_fished, buildFurnace).registerStat();
/*  28: 61 */   public static Achievement onARail = new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.rail, acquireIron).setSpecial().registerStat();
/*  29: 64 */   public static Achievement buildSword = new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.wooden_sword, buildWorkBench).registerStat();
/*  30: 67 */   public static Achievement killEnemy = new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.bone, buildSword).registerStat();
/*  31: 70 */   public static Achievement killCow = new Achievement("achievement.killCow", "killCow", 7, -3, Items.leather, buildSword).registerStat();
/*  32: 73 */   public static Achievement flyPig = new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.saddle, killCow).setSpecial().registerStat();
/*  33: 76 */   public static Achievement snipeSkeleton = new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.bow, killEnemy).setSpecial().registerStat();
/*  34: 79 */   public static Achievement diamonds = new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.diamond_ore, acquireIron).registerStat();
/*  35: 80 */   public static Achievement field_150966_x = new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.diamond, diamonds).registerStat();
/*  36: 83 */   public static Achievement portal = new Achievement("achievement.portal", "portal", -1, 7, Blocks.obsidian, diamonds).registerStat();
/*  37: 86 */   public static Achievement ghast = new Achievement("achievement.ghast", "ghast", -4, 8, Items.ghast_tear, portal).setSpecial().registerStat();
/*  38: 89 */   public static Achievement blazeRod = new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.blaze_rod, portal).registerStat();
/*  39: 92 */   public static Achievement potion = new Achievement("achievement.potion", "potion", 2, 8, Items.potionitem, blazeRod).registerStat();
/*  40: 95 */   public static Achievement theEnd = new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.ender_eye, blazeRod).setSpecial().registerStat();
/*  41: 98 */   public static Achievement theEnd2 = new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.dragon_egg, theEnd).setSpecial().registerStat();
/*  42:101 */   public static Achievement enchantments = new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.enchanting_table, diamonds).registerStat();
/*  43:102 */   public static Achievement overkill = new Achievement("achievement.overkill", "overkill", -4, 1, Items.diamond_sword, enchantments).setSpecial().registerStat();
/*  44:105 */   public static Achievement bookcase = new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.bookshelf, enchantments).registerStat();
/*  45:106 */   public static Achievement field_150962_H = new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.wheat, killCow).registerStat();
/*  46:107 */   public static Achievement field_150963_I = new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.skull, 1, 1), theEnd2).registerStat();
/*  47:108 */   public static Achievement field_150964_J = new Achievement("achievement.killWither", "killWither", 7, 10, Items.nether_star, field_150963_I).registerStat();
/*  48:109 */   public static Achievement field_150965_K = new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.beacon, field_150964_J).setSpecial().registerStat();
/*  49:110 */   public static Achievement field_150961_L = new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.diamond_boots, theEnd).func_150953_b(JsonSerializableSet.class).setSpecial().registerStat();
/*  50:    */   private static final String __OBFID = "CL_00001467";
/*  51:    */   
/*  52:    */   public static void init() {}
/*  53:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.AchievementList
 * JD-Core Version:    0.7.0.1
 */