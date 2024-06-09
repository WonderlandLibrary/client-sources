package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory
{
    HorizonCode_Horizon_È("MASTER", 0, "MASTER", 0, "master", 0), 
    Â("MUSIC", 1, "MUSIC", 1, "music", 1), 
    Ý("RECORDS", 2, "RECORDS", 2, "record", 2), 
    Ø­áŒŠá("WEATHER", 3, "WEATHER", 3, "weather", 3), 
    Âµá€("BLOCKS", 4, "BLOCKS", 4, "block", 4), 
    Ó("MOBS", 5, "MOBS", 5, "hostile", 5), 
    à("ANIMALS", 6, "ANIMALS", 6, "neutral", 6), 
    Ø("PLAYERS", 7, "PLAYERS", 7, "player", 7), 
    áŒŠÆ("AMBIENT", 8, "AMBIENT", 8, "ambient", 8);
    
    private static final Map áˆºÑ¢Õ;
    private static final Map ÂµÈ;
    private final String á;
    private final int ˆÏ­;
    private static final SoundCategory[] £á;
    private static final String Å = "CL_00001686";
    
    static {
        £à = new SoundCategory[] { SoundCategory.HorizonCode_Horizon_È, SoundCategory.Â, SoundCategory.Ý, SoundCategory.Ø­áŒŠá, SoundCategory.Âµá€, SoundCategory.Ó, SoundCategory.à, SoundCategory.Ø, SoundCategory.áŒŠÆ };
        áˆºÑ¢Õ = Maps.newHashMap();
        ÂµÈ = Maps.newHashMap();
        £á = new SoundCategory[] { SoundCategory.HorizonCode_Horizon_È, SoundCategory.Â, SoundCategory.Ý, SoundCategory.Ø­áŒŠá, SoundCategory.Âµá€, SoundCategory.Ó, SoundCategory.à, SoundCategory.Ø, SoundCategory.áŒŠÆ };
        for (final SoundCategory var4 : values()) {
            if (SoundCategory.áˆºÑ¢Õ.containsKey(var4.HorizonCode_Horizon_È()) || SoundCategory.ÂµÈ.containsKey(var4.Â())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var4);
            }
            SoundCategory.áˆºÑ¢Õ.put(var4.HorizonCode_Horizon_È(), var4);
            SoundCategory.ÂµÈ.put(var4.Â(), var4);
        }
    }
    
    private SoundCategory(final String s, final int n, final String p_i45126_1_, final int p_i45126_2_, final String p_i45126_3_, final int p_i45126_4_) {
        this.á = p_i45126_3_;
        this.ˆÏ­ = p_i45126_4_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.á;
    }
    
    public int Â() {
        return this.ˆÏ­;
    }
    
    public static SoundCategory HorizonCode_Horizon_È(final String p_147154_0_) {
        return SoundCategory.áˆºÑ¢Õ.get(p_147154_0_);
    }
}
