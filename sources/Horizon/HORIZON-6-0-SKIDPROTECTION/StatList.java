package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.HashSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class StatList
{
    protected static Map HorizonCode_Horizon_È;
    public static List Â;
    public static List Ý;
    public static List Ø­áŒŠá;
    public static List Âµá€;
    public static StatBase Ó;
    public static StatBase à;
    public static StatBase Ø;
    public static StatBase áŒŠÆ;
    public static StatBase áˆºÑ¢Õ;
    public static StatBase ÂµÈ;
    public static StatBase á;
    public static StatBase ˆÏ­;
    public static StatBase £á;
    public static StatBase Å;
    public static StatBase £à;
    public static StatBase µà;
    public static StatBase ˆà;
    public static StatBase ¥Æ;
    public static StatBase Ø­à;
    public static StatBase µÕ;
    public static StatBase Æ;
    public static StatBase Šáƒ;
    public static StatBase Ï­Ðƒà;
    public static StatBase áŒŠà;
    public static StatBase ŠÄ;
    public static StatBase Ñ¢á;
    public static StatBase ŒÏ;
    public static StatBase Çªà¢;
    public static StatBase Ê;
    public static StatBase ÇŽÉ;
    public static StatBase ˆá;
    public static StatBase ÇŽÕ;
    public static final StatBase[] É;
    public static final StatBase[] áƒ;
    public static final StatBase[] á€;
    public static final StatBase[] Õ;
    private static final String à¢ = "CL_00001480";
    
    static {
        StatList.HorizonCode_Horizon_È = Maps.newHashMap();
        StatList.Â = Lists.newArrayList();
        StatList.Ý = Lists.newArrayList();
        StatList.Ø­áŒŠá = Lists.newArrayList();
        StatList.Âµá€ = Lists.newArrayList();
        StatList.Ó = new StatBasic("stat.leaveGame", new ChatComponentTranslation("stat.leaveGame", new Object[0])).áŒŠÆ().Ø();
        StatList.à = new StatBasic("stat.playOneMinute", new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.áˆºÑ¢Õ).áŒŠÆ().Ø();
        StatList.Ø = new StatBasic("stat.timeSinceDeath", new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.áˆºÑ¢Õ).áŒŠÆ().Ø();
        StatList.áŒŠÆ = new StatBasic("stat.walkOneCm", new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.áˆºÑ¢Õ = new StatBasic("stat.crouchOneCm", new ChatComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.ÂµÈ = new StatBasic("stat.sprintOneCm", new ChatComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.á = new StatBasic("stat.swimOneCm", new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.ˆÏ­ = new StatBasic("stat.fallOneCm", new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.£á = new StatBasic("stat.climbOneCm", new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.Å = new StatBasic("stat.flyOneCm", new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.£à = new StatBasic("stat.diveOneCm", new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.µà = new StatBasic("stat.minecartOneCm", new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.ˆà = new StatBasic("stat.boatOneCm", new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.¥Æ = new StatBasic("stat.pigOneCm", new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.Ø­à = new StatBasic("stat.horseOneCm", new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.ÂµÈ).áŒŠÆ().Ø();
        StatList.µÕ = new StatBasic("stat.jump", new ChatComponentTranslation("stat.jump", new Object[0])).áŒŠÆ().Ø();
        StatList.Æ = new StatBasic("stat.drop", new ChatComponentTranslation("stat.drop", new Object[0])).áŒŠÆ().Ø();
        StatList.Šáƒ = new StatBasic("stat.damageDealt", new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.á).Ø();
        StatList.Ï­Ðƒà = new StatBasic("stat.damageTaken", new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.á).Ø();
        StatList.áŒŠà = new StatBasic("stat.deaths", new ChatComponentTranslation("stat.deaths", new Object[0])).Ø();
        StatList.ŠÄ = new StatBasic("stat.mobKills", new ChatComponentTranslation("stat.mobKills", new Object[0])).Ø();
        StatList.Ñ¢á = new StatBasic("stat.animalsBred", new ChatComponentTranslation("stat.animalsBred", new Object[0])).Ø();
        StatList.ŒÏ = new StatBasic("stat.playerKills", new ChatComponentTranslation("stat.playerKills", new Object[0])).Ø();
        StatList.Çªà¢ = new StatBasic("stat.fishCaught", new ChatComponentTranslation("stat.fishCaught", new Object[0])).Ø();
        StatList.Ê = new StatBasic("stat.junkFished", new ChatComponentTranslation("stat.junkFished", new Object[0])).Ø();
        StatList.ÇŽÉ = new StatBasic("stat.treasureFished", new ChatComponentTranslation("stat.treasureFished", new Object[0])).Ø();
        StatList.ˆá = new StatBasic("stat.talkedToVillager", new ChatComponentTranslation("stat.talkedToVillager", new Object[0])).Ø();
        StatList.ÇŽÕ = new StatBasic("stat.tradedWithVillager", new ChatComponentTranslation("stat.tradedWithVillager", new Object[0])).Ø();
        É = new StatBase[4096];
        áƒ = new StatBase[32000];
        á€ = new StatBase[32000];
        Õ = new StatBase[32000];
    }
    
    public static void HorizonCode_Horizon_È() {
        Ý();
        Ø­áŒŠá();
        Âµá€();
        Â();
        AchievementList.HorizonCode_Horizon_È();
        EntityList.HorizonCode_Horizon_È();
    }
    
    private static void Â() {
        final HashSet var0 = Sets.newHashSet();
        for (final IRecipe var3 : CraftingManager.HorizonCode_Horizon_È().Â()) {
            if (var3.Â() != null) {
                var0.add(var3.Â().HorizonCode_Horizon_È());
            }
        }
        for (final ItemStack var4 : FurnaceRecipes.HorizonCode_Horizon_È().Â().values()) {
            var0.add(var4.HorizonCode_Horizon_È());
        }
        for (final Item_1028566121 var5 : var0) {
            if (var5 != null) {
                final int var6 = Item_1028566121.HorizonCode_Horizon_È(var5);
                final String var7 = HorizonCode_Horizon_È(var5);
                if (var7 == null) {
                    continue;
                }
                StatList.áƒ[var6] = new StatCrafting("stat.craftItem.", var7, new ChatComponentTranslation("stat.craftItem", new Object[] { new ItemStack(var5).Çªà¢() }), var5).Ø();
            }
        }
        HorizonCode_Horizon_È(StatList.áƒ);
    }
    
    private static void Ý() {
        for (final Block var2 : Block.HorizonCode_Horizon_È) {
            final Item_1028566121 var3 = Item_1028566121.HorizonCode_Horizon_È(var2);
            if (var3 != null) {
                final int var4 = Block.HorizonCode_Horizon_È(var2);
                final String var5 = HorizonCode_Horizon_È(var3);
                if (var5 == null || !var2.Ê()) {
                    continue;
                }
                StatList.É[var4] = new StatCrafting("stat.mineBlock.", var5, new ChatComponentTranslation("stat.mineBlock", new Object[] { new ItemStack(var2).Çªà¢() }), var3).Ø();
                StatList.Âµá€.add(StatList.É[var4]);
            }
        }
        HorizonCode_Horizon_È(StatList.É);
    }
    
    private static void Ø­áŒŠá() {
        for (final Item_1028566121 var2 : Item_1028566121.HorizonCode_Horizon_È) {
            if (var2 != null) {
                final int var3 = Item_1028566121.HorizonCode_Horizon_È(var2);
                final String var4 = HorizonCode_Horizon_È(var2);
                if (var4 == null) {
                    continue;
                }
                StatList.á€[var3] = new StatCrafting("stat.useItem.", var4, new ChatComponentTranslation("stat.useItem", new Object[] { new ItemStack(var2).Çªà¢() }), var2).Ø();
                if (var2 instanceof ItemBlock) {
                    continue;
                }
                StatList.Ø­áŒŠá.add(StatList.á€[var3]);
            }
        }
        HorizonCode_Horizon_È(StatList.á€);
    }
    
    private static void Âµá€() {
        for (final Item_1028566121 var2 : Item_1028566121.HorizonCode_Horizon_È) {
            if (var2 != null) {
                final int var3 = Item_1028566121.HorizonCode_Horizon_È(var2);
                final String var4 = HorizonCode_Horizon_È(var2);
                if (var4 == null || !var2.Ø­áŒŠá()) {
                    continue;
                }
                StatList.Õ[var3] = new StatCrafting("stat.breakItem.", var4, new ChatComponentTranslation("stat.breakItem", new Object[] { new ItemStack(var2).Çªà¢() }), var2).Ø();
            }
        }
        HorizonCode_Horizon_È(StatList.Õ);
    }
    
    private static String HorizonCode_Horizon_È(final Item_1028566121 p_180204_0_) {
        final ResourceLocation_1975012498 var1 = (ResourceLocation_1975012498)Item_1028566121.HorizonCode_Horizon_È.Â(p_180204_0_);
        return (var1 != null) ? var1.toString().replace(':', '.') : null;
    }
    
    private static void HorizonCode_Horizon_È(final StatBase[] p_75924_0_) {
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ÂµÈ, Blocks.áˆºÑ¢Õ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ˆÏ­, Blocks.á);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.áŒŠÕ, Blocks.Ø­Æ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ˆÐƒØ­à, Blocks.£Ó);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ÇªØ­, Blocks.Ñ¢à);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ˆØ, Blocks.áŒŠá);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.¥Ê, Blocks.ÐƒÇŽà);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.áˆº, Blocks.£áŒŠá);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ÇŽØ, Blocks.áŒŠÉ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.£ÂµÄ, Blocks.Ø­Âµ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ŒÓ, Blocks.ÇŽÊ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.ÇªØ, Blocks.µØ);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.Ø­áŒŠá, Blocks.Âµá€);
        HorizonCode_Horizon_È(p_75924_0_, Blocks.£Â, Blocks.Âµá€);
    }
    
    private static void HorizonCode_Horizon_È(final StatBase[] p_151180_0_, final Block p_151180_1_, final Block p_151180_2_) {
        final int var3 = Block.HorizonCode_Horizon_È(p_151180_1_);
        final int var4 = Block.HorizonCode_Horizon_È(p_151180_2_);
        if (p_151180_0_[var3] != null && p_151180_0_[var4] == null) {
            p_151180_0_[var4] = p_151180_0_[var3];
        }
        else {
            StatList.Â.remove(p_151180_0_[var3]);
            StatList.Âµá€.remove(p_151180_0_[var3]);
            StatList.Ý.remove(p_151180_0_[var3]);
            p_151180_0_[var3] = p_151180_0_[var4];
        }
    }
    
    public static StatBase HorizonCode_Horizon_È(final EntityList.HorizonCode_Horizon_È p_151182_0_) {
        final String var1 = EntityList.Â(p_151182_0_.HorizonCode_Horizon_È);
        return (var1 == null) ? null : new StatBase("stat.killEntity." + var1, new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + var1 + ".name", new Object[0]) })).Ø();
    }
    
    public static StatBase Â(final EntityList.HorizonCode_Horizon_È p_151176_0_) {
        final String var1 = EntityList.Â(p_151176_0_.HorizonCode_Horizon_È);
        return (var1 == null) ? null : new StatBase("stat.entityKilledBy." + var1, new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + var1 + ".name", new Object[0]) })).Ø();
    }
    
    public static StatBase HorizonCode_Horizon_È(final String p_151177_0_) {
        return StatList.HorizonCode_Horizon_È.get(p_151177_0_);
    }
}
