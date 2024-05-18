package net.minecraft.stats;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class AchievementList
{
    public static Achievement snipeSkeleton;
    public static Achievement buildWorkBench;
    public static Achievement blazeRod;
    public static int maxDisplayRow;
    public static Achievement flyPig;
    public static Achievement killCow;
    public static Achievement breedCow;
    public static Achievement portal;
    public static int minDisplayRow;
    public static Achievement cookFish;
    public static Achievement buildPickaxe;
    public static Achievement fullBeacon;
    public static Achievement exploreAllBiomes;
    public static Achievement theEnd;
    public static Achievement bookcase;
    private static final String[] I;
    public static Achievement overpowered;
    public static Achievement buildFurnace;
    public static Achievement bakeCake;
    public static Achievement spawnWither;
    public static Achievement killWither;
    public static Achievement openInventory;
    public static Achievement ghast;
    public static Achievement mineWood;
    public static Achievement acquireIron;
    public static Achievement theEnd2;
    public static Achievement buildBetterPickaxe;
    public static Achievement potion;
    public static Achievement onARail;
    public static Achievement buildSword;
    public static Achievement diamonds;
    public static Achievement enchantments;
    public static Achievement overkill;
    public static int minDisplayColumn;
    public static int maxDisplayColumn;
    public static Achievement makeBread;
    public static Achievement buildHoe;
    public static Achievement diamondsToYou;
    public static Achievement killEnemy;
    public static List<Achievement> achievementList;
    
    public static void init() {
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        AchievementList.achievementList = (List<Achievement>)Lists.newArrayList();
        AchievementList.openInventory = new Achievement(AchievementList.I["".length()], AchievementList.I[" ".length()], "".length(), "".length(), Items.book, null).initIndependentStat().registerStat();
        AchievementList.mineWood = new Achievement(AchievementList.I["  ".length()], AchievementList.I["   ".length()], "  ".length(), " ".length(), Blocks.log, AchievementList.openInventory).registerStat();
        AchievementList.buildWorkBench = new Achievement(AchievementList.I[0x5C ^ 0x58], AchievementList.I[0x53 ^ 0x56], 0x1D ^ 0x19, -" ".length(), Blocks.crafting_table, AchievementList.mineWood).registerStat();
        AchievementList.buildPickaxe = new Achievement(AchievementList.I[0x11 ^ 0x17], AchievementList.I[0x6D ^ 0x6A], 0x4 ^ 0x0, "  ".length(), Items.wooden_pickaxe, AchievementList.buildWorkBench).registerStat();
        AchievementList.buildFurnace = new Achievement(AchievementList.I[0x38 ^ 0x30], AchievementList.I[0x3F ^ 0x36], "   ".length(), 0xA8 ^ 0xAC, Blocks.furnace, AchievementList.buildPickaxe).registerStat();
        AchievementList.acquireIron = new Achievement(AchievementList.I[0xBA ^ 0xB0], AchievementList.I[0x66 ^ 0x6D], " ".length(), 0x5 ^ 0x1, Items.iron_ingot, AchievementList.buildFurnace).registerStat();
        AchievementList.buildHoe = new Achievement(AchievementList.I[0xAC ^ 0xA0], AchievementList.I[0x87 ^ 0x8A], "  ".length(), -"   ".length(), Items.wooden_hoe, AchievementList.buildWorkBench).registerStat();
        AchievementList.makeBread = new Achievement(AchievementList.I[0x93 ^ 0x9D], AchievementList.I[0x13 ^ 0x1C], -" ".length(), -"   ".length(), Items.bread, AchievementList.buildHoe).registerStat();
        AchievementList.bakeCake = new Achievement(AchievementList.I[0x0 ^ 0x10], AchievementList.I[0x86 ^ 0x97], "".length(), -(0x1B ^ 0x1E), Items.cake, AchievementList.buildHoe).registerStat();
        AchievementList.buildBetterPickaxe = new Achievement(AchievementList.I[0x66 ^ 0x74], AchievementList.I[0x50 ^ 0x43], 0x34 ^ 0x32, "  ".length(), Items.stone_pickaxe, AchievementList.buildPickaxe).registerStat();
        AchievementList.cookFish = new Achievement(AchievementList.I[0x97 ^ 0x83], AchievementList.I[0x83 ^ 0x96], "  ".length(), 0xAA ^ 0xAC, Items.cooked_fish, AchievementList.buildFurnace).registerStat();
        AchievementList.onARail = new Achievement(AchievementList.I[0x78 ^ 0x6E], AchievementList.I[0x35 ^ 0x22], "  ".length(), "   ".length(), Blocks.rail, AchievementList.acquireIron).setSpecial().registerStat();
        AchievementList.buildSword = new Achievement(AchievementList.I[0x86 ^ 0x9E], AchievementList.I[0x4C ^ 0x55], 0x90 ^ 0x96, -" ".length(), Items.wooden_sword, AchievementList.buildWorkBench).registerStat();
        AchievementList.killEnemy = new Achievement(AchievementList.I[0x54 ^ 0x4E], AchievementList.I[0xBA ^ 0xA1], 0xB1 ^ 0xB9, -" ".length(), Items.bone, AchievementList.buildSword).registerStat();
        AchievementList.killCow = new Achievement(AchievementList.I[0x54 ^ 0x48], AchievementList.I[0xAD ^ 0xB0], 0xA8 ^ 0xAF, -"   ".length(), Items.leather, AchievementList.buildSword).registerStat();
        AchievementList.flyPig = new Achievement(AchievementList.I[0xA0 ^ 0xBE], AchievementList.I[0xD8 ^ 0xC7], 0x21 ^ 0x28, -"   ".length(), Items.saddle, AchievementList.killCow).setSpecial().registerStat();
        AchievementList.snipeSkeleton = new Achievement(AchievementList.I[0x10 ^ 0x30], AchievementList.I[0x48 ^ 0x69], 0x4 ^ 0x3, "".length(), Items.bow, AchievementList.killEnemy).setSpecial().registerStat();
        AchievementList.diamonds = new Achievement(AchievementList.I[0x78 ^ 0x5A], AchievementList.I[0x61 ^ 0x42], -" ".length(), 0x9 ^ 0xC, Blocks.diamond_ore, AchievementList.acquireIron).registerStat();
        AchievementList.diamondsToYou = new Achievement(AchievementList.I[0xAB ^ 0x8F], AchievementList.I[0x99 ^ 0xBC], -" ".length(), "  ".length(), Items.diamond, AchievementList.diamonds).registerStat();
        AchievementList.portal = new Achievement(AchievementList.I[0x22 ^ 0x4], AchievementList.I[0xD ^ 0x2A], -" ".length(), 0x70 ^ 0x77, Blocks.obsidian, AchievementList.diamonds).registerStat();
        AchievementList.ghast = new Achievement(AchievementList.I[0x64 ^ 0x4C], AchievementList.I[0x2B ^ 0x2], -(0x68 ^ 0x6C), 0x74 ^ 0x7C, Items.ghast_tear, AchievementList.portal).setSpecial().registerStat();
        AchievementList.blazeRod = new Achievement(AchievementList.I[0x2C ^ 0x6], AchievementList.I[0xBD ^ 0x96], "".length(), 0xCD ^ 0xC4, Items.blaze_rod, AchievementList.portal).registerStat();
        AchievementList.potion = new Achievement(AchievementList.I[0x9A ^ 0xB6], AchievementList.I[0x3F ^ 0x12], "  ".length(), 0x7F ^ 0x77, Items.potionitem, AchievementList.blazeRod).registerStat();
        AchievementList.theEnd = new Achievement(AchievementList.I[0x0 ^ 0x2E], AchievementList.I[0x72 ^ 0x5D], "   ".length(), 0xBE ^ 0xB4, Items.ender_eye, AchievementList.blazeRod).setSpecial().registerStat();
        AchievementList.theEnd2 = new Achievement(AchievementList.I[0x31 ^ 0x1], AchievementList.I[0x57 ^ 0x66], 0x3F ^ 0x3B, 0x38 ^ 0x35, Blocks.dragon_egg, AchievementList.theEnd).setSpecial().registerStat();
        AchievementList.enchantments = new Achievement(AchievementList.I[0xBB ^ 0x89], AchievementList.I[0x9F ^ 0xAC], -(0x25 ^ 0x21), 0x2B ^ 0x2F, Blocks.enchanting_table, AchievementList.diamonds).registerStat();
        AchievementList.overkill = new Achievement(AchievementList.I[0xE ^ 0x3A], AchievementList.I[0xA1 ^ 0x94], -(0x29 ^ 0x2D), " ".length(), Items.diamond_sword, AchievementList.enchantments).setSpecial().registerStat();
        AchievementList.bookcase = new Achievement(AchievementList.I[0x0 ^ 0x36], AchievementList.I[0xAE ^ 0x99], -"   ".length(), 0x6C ^ 0x6A, Blocks.bookshelf, AchievementList.enchantments).registerStat();
        AchievementList.breedCow = new Achievement(AchievementList.I[0x25 ^ 0x1D], AchievementList.I[0x33 ^ 0xA], 0x46 ^ 0x41, -(0x16 ^ 0x13), Items.wheat, AchievementList.killCow).registerStat();
        AchievementList.spawnWither = new Achievement(AchievementList.I[0x1 ^ 0x3B], AchievementList.I[0x2 ^ 0x39], 0x73 ^ 0x74, 0x7B ^ 0x77, new ItemStack(Items.skull, " ".length(), " ".length()), AchievementList.theEnd2).registerStat();
        AchievementList.killWither = new Achievement(AchievementList.I[0x90 ^ 0xAC], AchievementList.I[0x4C ^ 0x71], 0x78 ^ 0x7F, 0x69 ^ 0x63, Items.nether_star, AchievementList.spawnWither).registerStat();
        AchievementList.fullBeacon = new Achievement(AchievementList.I[0x0 ^ 0x3E], AchievementList.I[0x8C ^ 0xB3], 0x5E ^ 0x59, 0xB3 ^ 0xBB, Blocks.beacon, AchievementList.killWither).setSpecial().registerStat();
        AchievementList.exploreAllBiomes = new Achievement(AchievementList.I[0x4E ^ 0xE], AchievementList.I[0x5A ^ 0x1B], 0x6C ^ 0x68, 0x64 ^ 0x6C, Items.diamond_boots, AchievementList.theEnd).func_150953_b((Class<? extends IJsonSerializable>)JsonSerializableSet.class).setSpecial().registerStat();
        AchievementList.overpowered = new Achievement(AchievementList.I[0xE6 ^ 0xA4], AchievementList.I[0xE7 ^ 0xA4], 0xB6 ^ 0xB0, 0x1A ^ 0x1E, new ItemStack(Items.golden_apple, " ".length(), " ".length()), AchievementList.buildBetterPickaxe).setSpecial().registerStat();
    }
    
    private static void I() {
        (I = new String[0xD7 ^ 0x93])["".length()] = I("\u0005\u0000\u0007\u0002\f\u0012\u0006\u0002\u000e\u0007\u0010M\u0000\u001b\f\n*\u0001\u001d\f\n\u0017\u0000\u0019\u0010", "dcoki");
        AchievementList.I[" ".length()] = I("\u001f\u0013\u001c,\u001e\u001e\u0015\u001c,#\u001f\u0011\u0000", "pcyBW");
        AchievementList.I["  ".length()] = I("%\u0011;?\"2\u0017>3)0\\>?)!%<9#", "DrSVG");
        AchievementList.I["   ".length()] = I("\t\u0002;/\u0016\u000b\u00041", "dkUJA");
        AchievementList.I[0x5D ^ 0x59] = I("\f1=9\u001c\u001b785\u0017\u0019|7%\u0010\u00016\u0002?\u000b\u0006\u00100>\u001a\u0005", "mRUPy");
        AchievementList.I[0x2D ^ 0x28] = I("\u0016\u00071\"\u0011#\u001d*%7\u0011\u001c;&", "trXNu");
        AchievementList.I[0x3F ^ 0x39] = I("(0\u0002.&?6\u0007\"-=}\b2*%7:. \"2\u0012\"", "ISjGC");
        AchievementList.I[0xB6 ^ 0xB1] = I("\t\u00138\u00033;\u000f2\u00046\u0013\u0003", "kfQoW");
        AchievementList.I[0x65 ^ 0x6D] = I("*\n''7=\f\"+<?G-;;'\r\t; %\b,+", "KiONR");
        AchievementList.I[0xCB ^ 0xC2] = I("5\u0012-\t\u0001\u0011\u00126\u000b\u00044\u0002", "WgDee");
        AchievementList.I[0x8D ^ 0x87] = I("\u0014\u0012\u001d!<\u0003\u0014\u0018-7\u0001_\u0014+(\u0000\u0018\u0007-\u0010\u0007\u001e\u001b", "uquHY");
        AchievementList.I[0x94 ^ 0x9F] = I("$\u0013\u0018\u001a\b7\u0015 \u001d\u000e+", "Epioa");
        AchievementList.I[0x68 ^ 0x64] = I("\t,2\u0004\u000e\u001e*7\b\u0005\u001ca8\u0018\u0002\u0004+\u0012\u0002\u000e", "hOZmk");
        AchievementList.I[0x6A ^ 0x67] = I("\u0003\u001f\n#\u0005)\u0005\u0006", "ajcOa");
        AchievementList.I[0x79 ^ 0x77] = I("7\u0002)3. \u0004,?%\"O,; 3#3?*2", "VaAZK");
        AchievementList.I[0x8A ^ 0x85] = I("\u001b\u000e\u001a$\t\u0004\n\u0010%", "voqAK");
        AchievementList.I[0x7B ^ 0x6B] = I("\u00191806\u000e7=<=\f|288\u001d\u0011126", "xRPYS");
        AchievementList.I[0xAC ^ 0xBD] = I("\u0017\u000f#\u0000\u000f\u0014\u0005-", "unHeL");
        AchievementList.I[0x90 ^ 0x82] = I("\f\u0019\u001e\u001f\u0011\u001b\u001f\u001b\u0013\u001a\u0019T\u0014\u0003\u001d\u0001\u001e4\u0013\u0000\u0019\u001f\u0004&\u001d\u000e\u0011\u0017\u000e\u0011", "mzvvt");
        AchievementList.I[0xA5 ^ 0xB6] = I("\t\f\u0007\u0004\u0015)\u001c\u001a\u001c\u0014\u0019)\u0007\u000b\u001a\n\u0001\u000b", "kynhq");
        AchievementList.I[0x9E ^ 0x8A] = I("\u0013\u000e;\u0005\u0017\u0004\b>\t\u001c\u0006C0\u0003\u001d\u0019+:\u001f\u001a", "rmSlr");
        AchievementList.I[0x2 ^ 0x17] = I(",\u001c6\u0006.&\u00001", "OsYmh");
        AchievementList.I[0xB0 ^ 0xA6] = I("6\f/\u001b<!\n*\u00177#A(\u001c\u0018\u0005\u000e.\u001e", "WoGrY");
        AchievementList.I[0x34 ^ 0x23] = I(";-69(=/", "TCwkI");
        AchievementList.I[0x4A ^ 0x52] = I("\u0017\u0006\u0004\u0007\u0017\u0000\u0000\u0001\u000b\u001c\u0002K\u000e\u001b\u001b\u001a\u0001?\u0019\u001d\u0004\u0001", "velnr");
        AchievementList.I[0x6E ^ 0x77] = I(",3\u0006\n0\u001d1\u0000\u00140", "NFofT");
        AchievementList.I[0x2 ^ 0x18] = I("\u0014\u00000\u0001(\u0003\u00065\r#\u0001M3\u0001!\u0019&6\r \f", "ucXhM");
        AchievementList.I[0xB5 ^ 0xAE] = I("\"\" 6\u000e'.!#", "IKLZK");
        AchievementList.I[0xC ^ 0x10] = I("$\b\u00058\u00153\u000e\u00004\u001e1E\u00068\u001c)(\u0002&", "EkmQp");
        AchievementList.I[0xA7 ^ 0xBA] = I("\b#!\u00055\f=", "cJMiv");
        AchievementList.I[0x70 ^ 0x6E] = I("\n\u00120/\f\u001d\u00145#\u0007\u001f_>*\u0010;\u0018?", "kqXFi");
        AchievementList.I[0x9A ^ 0x85] = I("3<+\n!2", "UPRZH");
        AchievementList.I[0x79 ^ 0x59] = I("/\u00042 \u00108\u00027,\u001b:I)'\u001c>\u0002\t\"\u0010\"\u0002.&\u001b", "NgZIu");
        AchievementList.I[0x29 ^ 0x8] = I("\u0007**82'/&$2\u0000+-", "tDCHW");
        AchievementList.I[0x81 ^ 0xA3] = I("\u0004\">>-\u0013$;2&\u0011o2>)\b.83;", "eAVWH");
        AchievementList.I[0x12 ^ 0x31] = I("\u0017:*\u00075\u001d78", "sSKjZ");
        AchievementList.I[0x5 ^ 0x21] = I("\u000b/8\f\r\u001c)=\u0000\u0006\u001eb4\f\t\u0007#>\u0001\u001b>#\t\n\u001d", "jLPeh");
        AchievementList.I[0x3B ^ 0x1E] = I("\u0010\u001b\u000f\u001c<\u001a\u0016\u001d%<-\u001d\u001b", "trnqS");
        AchievementList.I[0xB5 ^ 0x93] = I("\u00111\t\u0007\u001c\u00067\f\u000b\u0017\u0004|\u0011\u0001\u000b\u00043\r", "pRany");
        AchievementList.I[0x44 ^ 0x63] = I("\u001b$\u0011\u0001\u0002\u0007", "kKcuc");
        AchievementList.I[0x3C ^ 0x14] = I(",\b:.\u0001;\u000e?\"\n9E5/\u0005>\u001f", "MkRGd");
        AchievementList.I[0x4B ^ 0x62] = I("\t:4\u0000\u0006", "nRUsr");
        AchievementList.I[0xB4 ^ 0x9E] = I("\u0016:.\b\u000e\u0001<+\u0004\u0005\u0003w$\r\n\r<\u0014\u000e\u000f", "wYFak");
        AchievementList.I[0x39 ^ 0x12] = I("\u0010\u001d&)? \u001e#", "rqGSZ");
        AchievementList.I[0x21 ^ 0xD] = I("\u0011\b1+!\u0006\u000e4'*\u0004E)-0\u0019\u00047", "pkYBD");
        AchievementList.I[0x56 ^ 0x7B] = I("!\u001f''5?", "QpSNZ");
        AchievementList.I[0x14 ^ 0x3A] = I("-\u0002\u000130:\u0004\u0004?;8O\u001d20\t\u000f\r", "LaiZU");
        AchievementList.I[0x4 ^ 0x2B] = I("\u0007\u0018),\n\u0017", "spLid");
        AchievementList.I[0xA4 ^ 0x94] = I("'6\u0005\u0003&00\u0000\u000f-2{\u0019\u0002&\u0003;\tX", "FUmjC");
        AchievementList.I[0x13 ^ 0x22] = I("\u001d)\b\u0015\u001d\rs", "iAmPs");
        AchievementList.I[0x62 ^ 0x50] = I("\u00042\t\u001a6\u00134\f\u0016=\u0011\u007f\u0004\u001d0\r0\u000f\u0007>\u0000?\u0015\u0000", "eQasS");
        AchievementList.I[0xB6 ^ 0x85] = I("(\r\n\t$#\u0017\u0004\u0004+9\u0010", "MciaE");
        AchievementList.I[0x2E ^ 0x1A] = I("\u000b)0\u0003\f\u001c/5\u000f\u0007\u001ed7\u001c\f\u0018!1\u0006\u0005", "jJXji");
        AchievementList.I[0x85 ^ 0xB0] = I("\u0000\u001b\b+\u0011\u0006\u0001\u0001", "ommYz");
        AchievementList.I[0x9F ^ 0xA9] = I("\u000e$\u001b\u0011<\u0019\"\u001e\u001d7\u001bi\u0011\u00176\u0004$\u0012\u000b<", "oGsxY");
        AchievementList.I[0x10 ^ 0x27] = I("\t5\u000e\t\u001a\n)\u0004", "kZaby");
        AchievementList.I[0x81 ^ 0xB9] = I(".\u000e >\u001d9\b%2\u0016;C*%\u001d*\t\u000b8\u000f", "OmHWx");
        AchievementList.I[0xA7 ^ 0x9E] = I("0873\u001d\u0011%%", "RJRVy");
        AchievementList.I[0x8 ^ 0x32] = I("*\f\u0012\u000b?=\n\u0017\u00074?A\t\u0012;<\u0001-\u000b.#\n\b", "KozbZ");
        AchievementList.I[0xB2 ^ 0x89] = I("\u0005\u00055\u0011+!\u001c \u000e \u0004", "vuTfE");
        AchievementList.I[0x93 ^ 0xAF] = I("\u0003\u0006\u000e$\n\u0014\u0000\u000b(\u0001\u0016K\r$\u0003\u000e2\u000f9\u0007\u0007\u0017", "befMo");
        AchievementList.I[0x46 ^ 0x7B] = I("\u001e\f\u0005\u001a5\u001c\u0011\u0001\u0013\u0010", "ueivb");
        AchievementList.I[0x4C ^ 0x72] = I(" \b-&\u001d7\u000e(*\u00165E#:\u0014-) .\u001b.\u0005", "AkEOx");
        AchievementList.I[0x5B ^ 0x64] = I("4? \u0002*7+/\u0001\u0006", "RJLnh");
        AchievementList.I[0x69 ^ 0x29] = I("2\u0013,</%\u0015)0$'^!-:?\u001f60\u000b?\u001c\u0006<%>\u00157", "SpDUJ");
        AchievementList.I[0x3F ^ 0x7E] = I("\u0007\u0016\u001a8&\u0010\u000b+8% \u0007\u00059,\u0011", "bnjTI");
        AchievementList.I[0x1E ^ 0x5C] = I(" (\f +7.\t, 5e\u000b?+3;\u000b>+3.\u0000", "AKdIN");
        AchievementList.I[0xE4 ^ 0xA7] = I("'!\u001c75' \u001c7 ,", "HWyEE");
    }
}
