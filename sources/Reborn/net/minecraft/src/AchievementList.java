package net.minecraft.src;

import java.util.*;

public class AchievementList
{
    public static int minDisplayColumn;
    public static int minDisplayRow;
    public static int maxDisplayColumn;
    public static int maxDisplayRow;
    public static List achievementList;
    public static Achievement openInventory;
    public static Achievement mineWood;
    public static Achievement buildWorkBench;
    public static Achievement buildPickaxe;
    public static Achievement buildFurnace;
    public static Achievement acquireIron;
    public static Achievement buildHoe;
    public static Achievement makeBread;
    public static Achievement bakeCake;
    public static Achievement buildBetterPickaxe;
    public static Achievement cookFish;
    public static Achievement onARail;
    public static Achievement buildSword;
    public static Achievement killEnemy;
    public static Achievement killCow;
    public static Achievement flyPig;
    public static Achievement snipeSkeleton;
    public static Achievement diamonds;
    public static Achievement portal;
    public static Achievement ghast;
    public static Achievement blazeRod;
    public static Achievement potion;
    public static Achievement theEnd;
    public static Achievement theEnd2;
    public static Achievement enchantments;
    public static Achievement overkill;
    public static Achievement bookcase;
    
    static {
        AchievementList.achievementList = new ArrayList();
        AchievementList.openInventory = new Achievement(0, "openInventory", 0, 0, Item.book, null).setIndependent().registerAchievement();
        AchievementList.mineWood = new Achievement(1, "mineWood", 2, 1, Block.wood, AchievementList.openInventory).registerAchievement();
        AchievementList.buildWorkBench = new Achievement(2, "buildWorkBench", 4, -1, Block.workbench, AchievementList.mineWood).registerAchievement();
        AchievementList.buildPickaxe = new Achievement(3, "buildPickaxe", 4, 2, Item.pickaxeWood, AchievementList.buildWorkBench).registerAchievement();
        AchievementList.buildFurnace = new Achievement(4, "buildFurnace", 3, 4, Block.furnaceIdle, AchievementList.buildPickaxe).registerAchievement();
        AchievementList.acquireIron = new Achievement(5, "acquireIron", 1, 4, Item.ingotIron, AchievementList.buildFurnace).registerAchievement();
        AchievementList.buildHoe = new Achievement(6, "buildHoe", 2, -3, Item.hoeWood, AchievementList.buildWorkBench).registerAchievement();
        AchievementList.makeBread = new Achievement(7, "makeBread", -1, -3, Item.bread, AchievementList.buildHoe).registerAchievement();
        AchievementList.bakeCake = new Achievement(8, "bakeCake", 0, -5, Item.cake, AchievementList.buildHoe).registerAchievement();
        AchievementList.buildBetterPickaxe = new Achievement(9, "buildBetterPickaxe", 6, 2, Item.pickaxeStone, AchievementList.buildPickaxe).registerAchievement();
        AchievementList.cookFish = new Achievement(10, "cookFish", 2, 6, Item.fishCooked, AchievementList.buildFurnace).registerAchievement();
        AchievementList.onARail = new Achievement(11, "onARail", 2, 3, Block.rail, AchievementList.acquireIron).setSpecial().registerAchievement();
        AchievementList.buildSword = new Achievement(12, "buildSword", 6, -1, Item.swordWood, AchievementList.buildWorkBench).registerAchievement();
        AchievementList.killEnemy = new Achievement(13, "killEnemy", 8, -1, Item.bone, AchievementList.buildSword).registerAchievement();
        AchievementList.killCow = new Achievement(14, "killCow", 7, -3, Item.leather, AchievementList.buildSword).registerAchievement();
        AchievementList.flyPig = new Achievement(15, "flyPig", 8, -4, Item.saddle, AchievementList.killCow).setSpecial().registerAchievement();
        AchievementList.snipeSkeleton = new Achievement(16, "snipeSkeleton", 7, 0, Item.bow, AchievementList.killEnemy).setSpecial().registerAchievement();
        AchievementList.diamonds = new Achievement(17, "diamonds", -1, 5, Item.diamond, AchievementList.acquireIron).registerAchievement();
        AchievementList.portal = new Achievement(18, "portal", -1, 7, Block.obsidian, AchievementList.diamonds).registerAchievement();
        AchievementList.ghast = new Achievement(19, "ghast", -4, 8, Item.ghastTear, AchievementList.portal).setSpecial().registerAchievement();
        AchievementList.blazeRod = new Achievement(20, "blazeRod", 0, 9, Item.blazeRod, AchievementList.portal).registerAchievement();
        AchievementList.potion = new Achievement(21, "potion", 2, 8, Item.potion, AchievementList.blazeRod).registerAchievement();
        AchievementList.theEnd = new Achievement(22, "theEnd", 3, 10, Item.eyeOfEnder, AchievementList.blazeRod).setSpecial().registerAchievement();
        AchievementList.theEnd2 = new Achievement(23, "theEnd2", 4, 13, Block.dragonEgg, AchievementList.theEnd).setSpecial().registerAchievement();
        AchievementList.enchantments = new Achievement(24, "enchantments", -4, 4, Block.enchantmentTable, AchievementList.diamonds).registerAchievement();
        AchievementList.overkill = new Achievement(25, "overkill", -4, 1, Item.swordDiamond, AchievementList.enchantments).setSpecial().registerAchievement();
        AchievementList.bookcase = new Achievement(26, "bookcase", -3, 6, Block.bookShelf, AchievementList.enchantments).registerAchievement();
        System.out.println(String.valueOf(AchievementList.achievementList.size()) + " achievements");
    }
    
    public static void init() {
    }
}
