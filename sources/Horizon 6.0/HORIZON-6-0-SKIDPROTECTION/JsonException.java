package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.StringUtils;
import java.io.FileNotFoundException;
import com.google.common.collect.Lists;
import java.util.List;
import java.io.IOException;

public class JsonException extends IOException
{
    private final List HorizonCode_Horizon_È;
    private final String Â;
    private static final String Ý = "CL_00001414";
    
    public JsonException(final String p_i45279_1_) {
        (this.HorizonCode_Horizon_È = Lists.newArrayList()).add(new HorizonCode_Horizon_È(null));
        this.Â = p_i45279_1_;
    }
    
    public JsonException(final String p_i45280_1_, final Throwable p_i45280_2_) {
        super(p_i45280_2_);
        (this.HorizonCode_Horizon_È = Lists.newArrayList()).add(new HorizonCode_Horizon_È(null));
        this.Â = p_i45280_1_;
    }
    
    public void HorizonCode_Horizon_È(final String p_151380_1_) {
        this.HorizonCode_Horizon_È.get(0).HorizonCode_Horizon_È(p_151380_1_);
    }
    
    public void Â(final String p_151381_1_) {
        JsonException.HorizonCode_Horizon_È.Â(this.HorizonCode_Horizon_È.get(0), p_151381_1_);
        this.HorizonCode_Horizon_È.add(0, new HorizonCode_Horizon_È(null));
    }
    
    @Override
    public String getMessage() {
        return "Invalid " + this.HorizonCode_Horizon_È.get(this.HorizonCode_Horizon_È.size() - 1).toString() + ": " + this.Â;
    }
    
    public static JsonException HorizonCode_Horizon_È(final Exception p_151379_0_) {
        if (p_151379_0_ instanceof JsonException) {
            return (JsonException)p_151379_0_;
        }
        String var1 = p_151379_0_.getMessage();
        if (p_151379_0_ instanceof FileNotFoundException) {
            var1 = "File not found";
        }
        return new JsonException(var1, p_151379_0_);
    }
    
    public static class HorizonCode_Horizon_È
    {
        private String HorizonCode_Horizon_È;
        private final List Â;
        private static final String Ý = "CL_00001416";
        
        private HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = null;
            this.Â = Lists.newArrayList();
        }
        
        private void HorizonCode_Horizon_È(final String p_151373_1_) {
            this.Â.add(0, p_151373_1_);
        }
        
        public String HorizonCode_Horizon_È() {
            return StringUtils.join((Iterable)this.Â, "->");
        }
        
        @Override
        public String toString() {
            return (this.HorizonCode_Horizon_È != null) ? (this.Â.isEmpty() ? this.HorizonCode_Horizon_È : (String.valueOf(this.HorizonCode_Horizon_È) + " " + this.HorizonCode_Horizon_È())) : (this.Â.isEmpty() ? "(Unknown file)" : ("(Unknown file) " + this.HorizonCode_Horizon_È()));
        }
        
        HorizonCode_Horizon_È(final Object p_i45278_1_) {
            this();
        }
        
        static /* synthetic */ void Â(final HorizonCode_Horizon_È horizonCode_Horizon_È, final String horizonCode_Horizon_È2) {
            horizonCode_Horizon_È.HorizonCode_Horizon_È = horizonCode_Horizon_È2;
        }
    }
}
