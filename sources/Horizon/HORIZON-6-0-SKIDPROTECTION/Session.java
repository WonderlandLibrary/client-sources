package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import com.mojang.util.UUIDTypeAdapter;
import com.mojang.authlib.GameProfile;

public class Session
{
    public String HorizonCode_Horizon_È;
    public String Â;
    public String Ý;
    private final HorizonCode_Horizon_È Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000659";
    
    public Session(final String p_i1098_1_, final String p_i1098_2_, final String p_i1098_3_, final String p_i1098_4_) {
        this.HorizonCode_Horizon_È = p_i1098_1_;
        this.Â = p_i1098_2_;
        this.Ý = p_i1098_3_;
        this.Ø­áŒŠá = Session.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i1098_4_);
    }
    
    public String HorizonCode_Horizon_È() {
        return "token:" + this.Ý + ":" + this.Â;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public String Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Ø­áŒŠá() {
        return this.Ý;
    }
    
    public GameProfile Âµá€() {
        try {
            final UUID var1 = UUIDTypeAdapter.fromString(this.Â());
            return new GameProfile(var1, this.Ý());
        }
        catch (IllegalArgumentException var2) {
            return new GameProfile((UUID)null, this.Ý());
        }
    }
    
    public HorizonCode_Horizon_È Ó() {
        return this.Ø­áŒŠá;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("LEGACY", 0, "LEGACY", 0, "legacy"), 
        Â("MOJANG", 1, "MOJANG", 1, "mojang");
        
        private static final Map Ý;
        private final String Ø­áŒŠá;
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00001851";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = Maps.newHashMap();
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ý.put(var4.Ø­áŒŠá, var4);
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1096_1_, final int p_i1096_2_, final String p_i1096_3_) {
            this.Ø­áŒŠá = p_i1096_3_;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_152421_0_) {
            return HorizonCode_Horizon_È.Ý.get(p_152421_0_.toLowerCase());
        }
    }
}
