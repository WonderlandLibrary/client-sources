package HORIZON-6-0-SKIDPROTECTION;

public final class WorldSettings
{
    private final long HorizonCode_Horizon_È;
    private final HorizonCode_Horizon_È Â;
    private final boolean Ý;
    private final boolean Ø­áŒŠá;
    private final WorldType Âµá€;
    private boolean Ó;
    private boolean à;
    private String Ø;
    private static final String áŒŠÆ = "CL_00000147";
    
    public WorldSettings(final long seedIn, final HorizonCode_Horizon_È gameType, final boolean enableMapFeatures, final boolean hardcoreMode, final WorldType worldTypeIn) {
        this.Ø = "";
        this.HorizonCode_Horizon_È = seedIn;
        this.Â = gameType;
        this.Ý = enableMapFeatures;
        this.Ø­áŒŠá = hardcoreMode;
        this.Âµá€ = worldTypeIn;
    }
    
    public WorldSettings(final WorldInfo info) {
        this(info.Â(), info.µà(), info.ˆà(), info.¥Æ(), info.Ø­à());
    }
    
    public WorldSettings HorizonCode_Horizon_È() {
        this.à = true;
        return this;
    }
    
    public WorldSettings Â() {
        this.Ó = true;
        return this;
    }
    
    public WorldSettings HorizonCode_Horizon_È(final String name) {
        this.Ø = name;
        return this;
    }
    
    public boolean Ý() {
        return this.à;
    }
    
    public long Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public HorizonCode_Horizon_È Âµá€() {
        return this.Â;
    }
    
    public boolean Ó() {
        return this.Ø­áŒŠá;
    }
    
    public boolean à() {
        return this.Ý;
    }
    
    public WorldType Ø() {
        return this.Âµá€;
    }
    
    public boolean áŒŠÆ() {
        return this.Ó;
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int id) {
        return HorizonCode_Horizon_È.HorizonCode_Horizon_È(id);
    }
    
    public String áˆºÑ¢Õ() {
        return this.Ø;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("NOT_SET", 0, "NOT_SET", 0, -1, ""), 
        Â("SURVIVAL", 1, "SURVIVAL", 1, 0, "survival"), 
        Ý("CREATIVE", 2, "CREATIVE", 2, 1, "creative"), 
        Ø­áŒŠá("ADVENTURE", 3, "ADVENTURE", 3, 2, "adventure"), 
        Âµá€("SPECTATOR", 4, "SPECTATOR", 4, 3, "spectator");
        
        int Ó;
        String à;
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00000148";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1956_1_, final int p_i1956_2_, final int typeId, final String nameIn) {
            this.Ó = typeId;
            this.à = nameIn;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public String Â() {
            return this.à;
        }
        
        public void HorizonCode_Horizon_È(final PlayerCapabilities capabilities) {
            if (this == HorizonCode_Horizon_È.Ý) {
                capabilities.Ý = true;
                capabilities.Ø­áŒŠá = true;
                capabilities.HorizonCode_Horizon_È = true;
            }
            else if (this == HorizonCode_Horizon_È.Âµá€) {
                capabilities.Ý = true;
                capabilities.Ø­áŒŠá = false;
                capabilities.HorizonCode_Horizon_È = true;
                capabilities.Â = true;
            }
            else {
                capabilities.Ý = false;
                capabilities.Ø­áŒŠá = false;
                capabilities.HorizonCode_Horizon_È = false;
                capabilities.Â = false;
            }
            capabilities.Âµá€ = !this.Ý();
        }
        
        public boolean Ý() {
            return this == HorizonCode_Horizon_È.Ø­áŒŠá || this == HorizonCode_Horizon_È.Âµá€;
        }
        
        public boolean Ø­áŒŠá() {
            return this == HorizonCode_Horizon_È.Ý;
        }
        
        public boolean Âµá€() {
            return this == HorizonCode_Horizon_È.Â || this == HorizonCode_Horizon_È.Ø­áŒŠá;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int idIn) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.Ó == idIn) {
                    return var4;
                }
            }
            return HorizonCode_Horizon_È.Â;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_77142_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.à.equals(p_77142_0_)) {
                    return var4;
                }
            }
            return HorizonCode_Horizon_È.Â;
        }
    }
}
