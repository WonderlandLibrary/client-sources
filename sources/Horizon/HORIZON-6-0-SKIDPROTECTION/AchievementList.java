package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class AchievementList
{
    public static int HorizonCode_Horizon_È;
    public static int Â;
    public static int Ý;
    public static int Ø­áŒŠá;
    public static List Âµá€;
    public static Achievement Ó;
    public static Achievement à;
    public static Achievement Ø;
    public static Achievement áŒŠÆ;
    public static Achievement áˆºÑ¢Õ;
    public static Achievement ÂµÈ;
    public static Achievement á;
    public static Achievement ˆÏ­;
    public static Achievement £á;
    public static Achievement Å;
    public static Achievement £à;
    public static Achievement µà;
    public static Achievement ˆà;
    public static Achievement ¥Æ;
    public static Achievement Ø­à;
    public static Achievement µÕ;
    public static Achievement Æ;
    public static Achievement Šáƒ;
    public static Achievement Ï­Ðƒà;
    public static Achievement áŒŠà;
    public static Achievement ŠÄ;
    public static Achievement Ñ¢á;
    public static Achievement ŒÏ;
    public static Achievement Çªà¢;
    public static Achievement Ê;
    public static Achievement ÇŽÉ;
    public static Achievement ˆá;
    public static Achievement ÇŽÕ;
    public static Achievement É;
    public static Achievement áƒ;
    public static Achievement á€;
    public static Achievement Õ;
    public static Achievement à¢;
    public static Achievement ŠÂµà;
    private static final String ¥à = "CL_00001467";
    
    static {
        AchievementList.Âµá€ = Lists.newArrayList();
        AchievementList.Ó = new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.Ñ¢Ç, null).HorizonCode_Horizon_È().Ý();
        AchievementList.à = new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.¥Æ, AchievementList.Ó).Ý();
        AchievementList.Ø = new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.ˆÉ, AchievementList.à).Ý();
        AchievementList.áŒŠÆ = new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.Å, AchievementList.Ø).Ý();
        AchievementList.áˆºÑ¢Õ = new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.£Ó, AchievementList.áŒŠÆ).Ý();
        AchievementList.ÂµÈ = new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.áˆºÑ¢Õ, AchievementList.áˆºÑ¢Õ).Ý();
        AchievementList.á = new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.áƒ, AchievementList.Ø).Ý();
        AchievementList.ˆÏ­ = new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.Ç, AchievementList.á).Ý();
        AchievementList.£á = new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.µÐƒáƒ, AchievementList.á).Ý();
        AchievementList.Å = new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.¥Æ, AchievementList.áŒŠÆ).Ý();
        AchievementList.£à = new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.Ø­Æ, AchievementList.áˆºÑ¢Õ).Ý();
        AchievementList.µà = new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.áŒŠáŠ, AchievementList.ÂµÈ).Â().Ý();
        AchievementList.ˆà = new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.ˆÏ­, AchievementList.Ø).Ý();
        AchievementList.¥Æ = new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.ŠÕ, AchievementList.ˆà).Ý();
        AchievementList.Ø­à = new Achievement("achievement.killCow", "killCow", 7, -3, Items.£áŒŠá, AchievementList.ˆà).Ý();
        AchievementList.µÕ = new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.Û, AchievementList.Ø­à).Â().Ý();
        AchievementList.Æ = new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.Ó, AchievementList.¥Æ).Â().Ý();
        AchievementList.Šáƒ = new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.£Ï, AchievementList.ÂµÈ).Ý();
        AchievementList.Ï­Ðƒà = new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.áŒŠÆ, AchievementList.Šáƒ).Ý();
        AchievementList.áŒŠà = new Achievement("achievement.portal", "portal", -1, 7, Blocks.ÇŽá€, AchievementList.Šáƒ).Ý();
        AchievementList.ŠÄ = new Achievement("achievement.ghast", "ghast", -4, 8, Items.¥Å, AchievementList.áŒŠà).Â().Ý();
        AchievementList.Ñ¢á = new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.Çªà, AchievementList.áŒŠà).Ý();
        AchievementList.ŒÏ = new Achievement("achievement.potion", "potion", 2, 8, Items.µÂ, AchievementList.Ñ¢á).Ý();
        AchievementList.Çªà¢ = new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.¥áŠ, AchievementList.Ñ¢á).Â().Ý();
        AchievementList.Ê = new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.áˆºáˆºáŠ, AchievementList.Çªà¢).Â().Ý();
        AchievementList.ÇŽÉ = new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.¥Âµá€, AchievementList.Šáƒ).Ý();
        AchievementList.ˆá = new Achievement("achievement.overkill", "overkill", -4, 1, Items.µÕ, AchievementList.ÇŽÉ).Â().Ý();
        AchievementList.ÇŽÕ = new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.Ï­à, AchievementList.ÇŽÉ).Ý();
        AchievementList.É = new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.Âµà, AchievementList.Ø­à).Ý();
        AchievementList.áƒ = new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.ˆ, 1, 1), AchievementList.Ê).Ý();
        AchievementList.á€ = new Achievement("achievement.killWither", "killWither", 7, 10, Items.áˆºá, AchievementList.áƒ).Ý();
        AchievementList.Õ = new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.áˆºá, AchievementList.á€).Â().Ý();
        AchievementList.à¢ = new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.ˆáƒ, AchievementList.Çªà¢).HorizonCode_Horizon_È(JsonSerializableSet.class).Â().Ý();
        AchievementList.ŠÂµà = new Achievement("achievement.overpowered", "overpowered", 6, 4, Items.£Õ, AchievementList.Å).Â().Ý();
    }
    
    public static void HorizonCode_Horizon_È() {
    }
}
