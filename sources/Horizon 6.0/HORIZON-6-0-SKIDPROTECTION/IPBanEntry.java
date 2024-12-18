package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonObject;
import java.util.Date;

public class IPBanEntry extends BanEntry
{
    private static final String Ó = "CL_00001883";
    
    public IPBanEntry(final String p_i46330_1_) {
        this(p_i46330_1_, null, null, null, null);
    }
    
    public IPBanEntry(final String p_i1159_1_, final Date p_i1159_2_, final String p_i1159_3_, final Date p_i1159_4_, final String p_i1159_5_) {
        super(p_i1159_1_, p_i1159_2_, p_i1159_3_, p_i1159_4_, p_i1159_5_);
    }
    
    public IPBanEntry(final JsonObject p_i46331_1_) {
        super(Â(p_i46331_1_), p_i46331_1_);
    }
    
    private static String Â(final JsonObject p_152647_0_) {
        return p_152647_0_.has("ip") ? p_152647_0_.get("ip").getAsString() : null;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final JsonObject data) {
        if (this.Ø­áŒŠá() != null) {
            data.addProperty("ip", (String)this.Ø­áŒŠá());
            super.HorizonCode_Horizon_È(data);
        }
    }
}
