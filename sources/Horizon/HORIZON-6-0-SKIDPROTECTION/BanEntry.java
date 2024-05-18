package HORIZON-6-0-SKIDPROTECTION;

import java.text.ParseException;
import com.google.gson.JsonObject;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class BanEntry extends UserListEntry
{
    public static final SimpleDateFormat HorizonCode_Horizon_È;
    protected final Date Â;
    protected final String Ý;
    protected final Date Ø­áŒŠá;
    protected final String Âµá€;
    private static final String Ó = "CL_00001395";
    
    static {
        HorizonCode_Horizon_È = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }
    
    public BanEntry(final Object p_i46334_1_, final Date p_i46334_2_, final String p_i46334_3_, final Date p_i46334_4_, final String p_i46334_5_) {
        super(p_i46334_1_);
        this.Â = ((p_i46334_2_ == null) ? new Date() : p_i46334_2_);
        this.Ý = ((p_i46334_3_ == null) ? "(Unknown)" : p_i46334_3_);
        this.Ø­áŒŠá = p_i46334_4_;
        this.Âµá€ = ((p_i46334_5_ == null) ? "Banned by an operator." : p_i46334_5_);
    }
    
    protected BanEntry(final Object p_i1174_1_, final JsonObject p_i1174_2_) {
        super(p_i1174_1_, p_i1174_2_);
        Date var3;
        try {
            var3 = (p_i1174_2_.has("created") ? BanEntry.HorizonCode_Horizon_È.parse(p_i1174_2_.get("created").getAsString()) : new Date());
        }
        catch (ParseException var5) {
            var3 = new Date();
        }
        this.Â = var3;
        this.Ý = (p_i1174_2_.has("source") ? p_i1174_2_.get("source").getAsString() : "(Unknown)");
        Date var4;
        try {
            var4 = (p_i1174_2_.has("expires") ? BanEntry.HorizonCode_Horizon_È.parse(p_i1174_2_.get("expires").getAsString()) : null);
        }
        catch (ParseException var6) {
            var4 = null;
        }
        this.Ø­áŒŠá = var4;
        this.Âµá€ = (p_i1174_2_.has("reason") ? p_i1174_2_.get("reason").getAsString() : "Banned by an operator.");
    }
    
    public Date HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public String Â() {
        return this.Âµá€;
    }
    
    @Override
    boolean Ý() {
        return this.Ø­áŒŠá != null && this.Ø­áŒŠá.before(new Date());
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final JsonObject data) {
        data.addProperty("created", BanEntry.HorizonCode_Horizon_È.format(this.Â));
        data.addProperty("source", this.Ý);
        data.addProperty("expires", (this.Ø­áŒŠá == null) ? "forever" : BanEntry.HorizonCode_Horizon_È.format(this.Ø­áŒŠá));
        data.addProperty("reason", this.Âµá€);
    }
}
