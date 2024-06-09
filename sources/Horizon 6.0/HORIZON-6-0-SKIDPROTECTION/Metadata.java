package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.util.Map;
import com.google.gson.Gson;

public class Metadata
{
    private static final Gson HorizonCode_Horizon_È;
    private final String Â;
    private String Ý;
    private Map Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001823";
    
    static {
        HorizonCode_Horizon_È = new Gson();
    }
    
    public Metadata(final String p_i46345_1_, final String p_i46345_2_) {
        this.Â = p_i46345_1_;
        this.Ý = p_i46345_2_;
    }
    
    public Metadata(final String p_i1030_1_) {
        this(p_i1030_1_, null);
    }
    
    public void HorizonCode_Horizon_È(final String p_152807_1_) {
        this.Ý = p_152807_1_;
    }
    
    public String HorizonCode_Horizon_È() {
        return (this.Ý == null) ? this.Â : this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final String p_152808_1_, final String p_152808_2_) {
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá = Maps.newHashMap();
        }
        if (this.Ø­áŒŠá.size() > 50) {
            throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
        }
        if (p_152808_1_ == null) {
            throw new IllegalArgumentException("Metadata payload key cannot be null!");
        }
        if (p_152808_1_.length() > 255) {
            throw new IllegalArgumentException("Metadata payload key is too long!");
        }
        if (p_152808_2_ == null) {
            throw new IllegalArgumentException("Metadata payload value cannot be null!");
        }
        if (p_152808_2_.length() > 255) {
            throw new IllegalArgumentException("Metadata payload value is too long!");
        }
        this.Ø­áŒŠá.put(p_152808_1_, p_152808_2_);
    }
    
    public String Â() {
        return (this.Ø­áŒŠá != null && !this.Ø­áŒŠá.isEmpty()) ? Metadata.HorizonCode_Horizon_È.toJson((Object)this.Ø­áŒŠá) : null;
    }
    
    public String Ý() {
        return this.Â;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("name", (Object)this.Â).add("description", (Object)this.Ý).add("data", (Object)this.Â()).toString();
    }
}
