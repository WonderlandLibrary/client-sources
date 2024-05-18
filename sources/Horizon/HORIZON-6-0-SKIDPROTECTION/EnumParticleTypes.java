package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;

public enum EnumParticleTypes
{
    HorizonCode_Horizon_È("EXPLOSION_NORMAL", 0, "EXPLOSION_NORMAL", 0, "explode", 0, true), 
    Â("EXPLOSION_LARGE", 1, "EXPLOSION_LARGE", 1, "largeexplode", 1, true), 
    Ý("EXPLOSION_HUGE", 2, "EXPLOSION_HUGE", 2, "hugeexplosion", 2, true), 
    Ø­áŒŠá("FIREWORKS_SPARK", 3, "FIREWORKS_SPARK", 3, "fireworksSpark", 3, false), 
    Âµá€("WATER_BUBBLE", 4, "WATER_BUBBLE", 4, "bubble", 4, false), 
    Ó("WATER_SPLASH", 5, "WATER_SPLASH", 5, "splash", 5, false), 
    à("WATER_WAKE", 6, "WATER_WAKE", 6, "wake", 6, false), 
    Ø("SUSPENDED", 7, "SUSPENDED", 7, "suspended", 7, false), 
    áŒŠÆ("SUSPENDED_DEPTH", 8, "SUSPENDED_DEPTH", 8, "depthsuspend", 8, false), 
    áˆºÑ¢Õ("CRIT", 9, "CRIT", 9, "crit", 9, false), 
    ÂµÈ("CRIT_MAGIC", 10, "CRIT_MAGIC", 10, "magicCrit", 10, false), 
    á("SMOKE_NORMAL", 11, "SMOKE_NORMAL", 11, "smoke", 11, false), 
    ˆÏ­("SMOKE_LARGE", 12, "SMOKE_LARGE", 12, "largesmoke", 12, false), 
    £á("SPELL", 13, "SPELL", 13, "spell", 13, false), 
    Å("SPELL_INSTANT", 14, "SPELL_INSTANT", 14, "instantSpell", 14, false), 
    £à("SPELL_MOB", 15, "SPELL_MOB", 15, "mobSpell", 15, false), 
    µà("SPELL_MOB_AMBIENT", 16, "SPELL_MOB_AMBIENT", 16, "mobSpellAmbient", 16, false), 
    ˆà("SPELL_WITCH", 17, "SPELL_WITCH", 17, "witchMagic", 17, false), 
    ¥Æ("DRIP_WATER", 18, "DRIP_WATER", 18, "dripWater", 18, false), 
    Ø­à("DRIP_LAVA", 19, "DRIP_LAVA", 19, "dripLava", 19, false), 
    µÕ("VILLAGER_ANGRY", 20, "VILLAGER_ANGRY", 20, "angryVillager", 20, false), 
    Æ("VILLAGER_HAPPY", 21, "VILLAGER_HAPPY", 21, "happyVillager", 21, false), 
    Šáƒ("TOWN_AURA", 22, "TOWN_AURA", 22, "townaura", 22, false), 
    Ï­Ðƒà("NOTE", 23, "NOTE", 23, "note", 23, false), 
    áŒŠà("PORTAL", 24, "PORTAL", 24, "portal", 24, false), 
    ŠÄ("ENCHANTMENT_TABLE", 25, "ENCHANTMENT_TABLE", 25, "enchantmenttable", 25, false), 
    Ñ¢á("FLAME", 26, "FLAME", 26, "flame", 26, false), 
    ŒÏ("LAVA", 27, "LAVA", 27, "lava", 27, false), 
    Çªà¢("FOOTSTEP", 28, "FOOTSTEP", 28, "footstep", 28, false), 
    Ê("CLOUD", 29, "CLOUD", 29, "cloud", 29, false), 
    ÇŽÉ("REDSTONE", 30, "REDSTONE", 30, "reddust", 30, false), 
    ˆá("SNOWBALL", 31, "SNOWBALL", 31, "snowballpoof", 31, false), 
    ÇŽÕ("SNOW_SHOVEL", 32, "SNOW_SHOVEL", 32, "snowshovel", 32, false), 
    É("SLIME", 33, "SLIME", 33, "slime", 33, false), 
    áƒ("HEART", 34, "HEART", 34, "heart", 34, false), 
    á€("BARRIER", 35, "BARRIER", 35, "barrier", 35, false), 
    Õ("ITEM_CRACK", 36, "ITEM_CRACK", 36, "iconcrack_", 36, false, 2), 
    à¢("BLOCK_CRACK", 37, "BLOCK_CRACK", 37, "blockcrack_", 37, false, 1), 
    ŠÂµà("BLOCK_DUST", 38, "BLOCK_DUST", 38, "blockdust_", 38, false, 1), 
    ¥à("WATER_DROP", 39, "WATER_DROP", 39, "droplet", 39, false), 
    Âµà("ITEM_TAKE", 40, "ITEM_TAKE", 40, "take", 40, false), 
    Ç("MOB_APPEARANCE", 41, "MOB_APPEARANCE", 41, "mobappearance", 41, true);
    
    private final String È;
    private final int áŠ;
    private final boolean ˆáŠ;
    private final int áŒŠ;
    private static final Map £ÂµÄ;
    private static final String[] Ø­Âµ;
    private static final EnumParticleTypes[] Ä;
    private static final String Ñ¢Â = "CL_00002317";
    
    static {
        Ï­à = new EnumParticleTypes[] { EnumParticleTypes.HorizonCode_Horizon_È, EnumParticleTypes.Â, EnumParticleTypes.Ý, EnumParticleTypes.Ø­áŒŠá, EnumParticleTypes.Âµá€, EnumParticleTypes.Ó, EnumParticleTypes.à, EnumParticleTypes.Ø, EnumParticleTypes.áŒŠÆ, EnumParticleTypes.áˆºÑ¢Õ, EnumParticleTypes.ÂµÈ, EnumParticleTypes.á, EnumParticleTypes.ˆÏ­, EnumParticleTypes.£á, EnumParticleTypes.Å, EnumParticleTypes.£à, EnumParticleTypes.µà, EnumParticleTypes.ˆà, EnumParticleTypes.¥Æ, EnumParticleTypes.Ø­à, EnumParticleTypes.µÕ, EnumParticleTypes.Æ, EnumParticleTypes.Šáƒ, EnumParticleTypes.Ï­Ðƒà, EnumParticleTypes.áŒŠà, EnumParticleTypes.ŠÄ, EnumParticleTypes.Ñ¢á, EnumParticleTypes.ŒÏ, EnumParticleTypes.Çªà¢, EnumParticleTypes.Ê, EnumParticleTypes.ÇŽÉ, EnumParticleTypes.ˆá, EnumParticleTypes.ÇŽÕ, EnumParticleTypes.É, EnumParticleTypes.áƒ, EnumParticleTypes.á€, EnumParticleTypes.Õ, EnumParticleTypes.à¢, EnumParticleTypes.ŠÂµà, EnumParticleTypes.¥à, EnumParticleTypes.Âµà, EnumParticleTypes.Ç };
        £ÂµÄ = Maps.newHashMap();
        Ä = new EnumParticleTypes[] { EnumParticleTypes.HorizonCode_Horizon_È, EnumParticleTypes.Â, EnumParticleTypes.Ý, EnumParticleTypes.Ø­áŒŠá, EnumParticleTypes.Âµá€, EnumParticleTypes.Ó, EnumParticleTypes.à, EnumParticleTypes.Ø, EnumParticleTypes.áŒŠÆ, EnumParticleTypes.áˆºÑ¢Õ, EnumParticleTypes.ÂµÈ, EnumParticleTypes.á, EnumParticleTypes.ˆÏ­, EnumParticleTypes.£á, EnumParticleTypes.Å, EnumParticleTypes.£à, EnumParticleTypes.µà, EnumParticleTypes.ˆà, EnumParticleTypes.¥Æ, EnumParticleTypes.Ø­à, EnumParticleTypes.µÕ, EnumParticleTypes.Æ, EnumParticleTypes.Šáƒ, EnumParticleTypes.Ï­Ðƒà, EnumParticleTypes.áŒŠà, EnumParticleTypes.ŠÄ, EnumParticleTypes.Ñ¢á, EnumParticleTypes.ŒÏ, EnumParticleTypes.Çªà¢, EnumParticleTypes.Ê, EnumParticleTypes.ÇŽÉ, EnumParticleTypes.ˆá, EnumParticleTypes.ÇŽÕ, EnumParticleTypes.É, EnumParticleTypes.áƒ, EnumParticleTypes.á€, EnumParticleTypes.Õ, EnumParticleTypes.à¢, EnumParticleTypes.ŠÂµà, EnumParticleTypes.¥à, EnumParticleTypes.Âµà, EnumParticleTypes.Ç };
        final ArrayList var0 = Lists.newArrayList();
        for (final EnumParticleTypes var5 : values()) {
            EnumParticleTypes.£ÂµÄ.put(var5.Ý(), var5);
            if (!var5.Â().endsWith("_")) {
                var0.add(var5.Â());
            }
        }
        Ø­Âµ = var0.toArray(new String[var0.size()]);
    }
    
    private EnumParticleTypes(final String s, final int n, final String p_i46011_1_, final int p_i46011_2_, final String p_i46011_3_, final int p_i46011_4_, final boolean p_i46011_5_, final int p_i46011_6_) {
        this.È = p_i46011_3_;
        this.áŠ = p_i46011_4_;
        this.ˆáŠ = p_i46011_5_;
        this.áŒŠ = p_i46011_6_;
    }
    
    private EnumParticleTypes(final String s, final int n, final String p_i46012_1_, final int p_i46012_2_, final String p_i46012_3_, final int p_i46012_4_, final boolean p_i46012_5_) {
        this(s, n, p_i46012_1_, p_i46012_2_, p_i46012_3_, p_i46012_4_, p_i46012_5_, 0);
    }
    
    public static String[] HorizonCode_Horizon_È() {
        return EnumParticleTypes.Ø­Âµ;
    }
    
    public String Â() {
        return this.È;
    }
    
    public int Ý() {
        return this.áŠ;
    }
    
    public int Ø­áŒŠá() {
        return this.áŒŠ;
    }
    
    public boolean Âµá€() {
        return this.ˆáŠ;
    }
    
    public boolean Ó() {
        return this.áŒŠ > 0;
    }
    
    public static EnumParticleTypes HorizonCode_Horizon_È(final int p_179342_0_) {
        return EnumParticleTypes.£ÂµÄ.get(p_179342_0_);
    }
}
