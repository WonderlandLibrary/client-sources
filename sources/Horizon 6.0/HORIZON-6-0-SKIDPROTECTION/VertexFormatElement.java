package HORIZON-6-0-SKIDPROTECTION;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement
{
    private static final Logger HorizonCode_Horizon_È;
    private final HorizonCode_Horizon_È Â;
    private final Â Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private static final String à = "CL_00002399";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public VertexFormatElement(final int p_i46096_1_, final HorizonCode_Horizon_È p_i46096_2_, final Â p_i46096_3_, final int p_i46096_4_) {
        if (!this.HorizonCode_Horizon_È(p_i46096_1_, p_i46096_3_)) {
            VertexFormatElement.HorizonCode_Horizon_È.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
            this.Ý = VertexFormatElement.Â.Ø­áŒŠá;
        }
        else {
            this.Ý = p_i46096_3_;
        }
        this.Â = p_i46096_2_;
        this.Ø­áŒŠá = p_i46096_1_;
        this.Âµá€ = p_i46096_4_;
        this.Ó = 0;
    }
    
    public void HorizonCode_Horizon_È(final int p_177371_1_) {
        this.Ó = p_177371_1_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    private final boolean HorizonCode_Horizon_È(final int p_177372_1_, final Â p_177372_2_) {
        return p_177372_1_ == 0 || p_177372_2_ == VertexFormatElement.Â.Ø­áŒŠá;
    }
    
    public final HorizonCode_Horizon_È Â() {
        return this.Â;
    }
    
    public final Â Ý() {
        return this.Ý;
    }
    
    public final int Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public final int Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.Âµá€) + "," + this.Ý.HorizonCode_Horizon_È() + "," + this.Â.Â();
    }
    
    public final int Ó() {
        return this.Â.HorizonCode_Horizon_È() * this.Âµá€;
    }
    
    public final boolean à() {
        return this.Ý == VertexFormatElement.Â.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final VertexFormatElement var2 = (VertexFormatElement)p_equals_1_;
            return this.Âµá€ == var2.Âµá€ && this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Ó == var2.Ó && this.Â == var2.Â && this.Ý == var2.Ý;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int var1 = this.Â.hashCode();
        var1 = 31 * var1 + this.Ý.hashCode();
        var1 = 31 * var1 + this.Ø­áŒŠá;
        var1 = 31 * var1 + this.Âµá€;
        var1 = 31 * var1 + this.Ó;
        return var1;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("FLOAT", 0, "FLOAT", 0, 4, "Float", 5126), 
        Â("UBYTE", 1, "UBYTE", 1, 1, "Unsigned Byte", 5121), 
        Ý("BYTE", 2, "BYTE", 2, 1, "Byte", 5120), 
        Ø­áŒŠá("USHORT", 3, "USHORT", 3, 2, "Unsigned Short", 5123), 
        Âµá€("SHORT", 4, "SHORT", 4, 2, "Short", 5122), 
        Ó("UINT", 5, "UINT", 5, 4, "Unsigned Int", 5125), 
        à("INT", 6, "INT", 6, 4, "Int", 5124);
        
        private final int Ø;
        private final String áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00002398";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46095_1_, final int p_i46095_2_, final int p_i46095_3_, final String p_i46095_4_, final int p_i46095_5_) {
            this.Ø = p_i46095_3_;
            this.áŒŠÆ = p_i46095_4_;
            this.áˆºÑ¢Õ = p_i46095_5_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Ø;
        }
        
        public String Â() {
            return this.áŒŠÆ;
        }
        
        public int Ý() {
            return this.áˆºÑ¢Õ;
        }
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("POSITION", 0, "POSITION", 0, "Position"), 
        Â("NORMAL", 1, "NORMAL", 1, "Normal"), 
        Ý("COLOR", 2, "COLOR", 2, "Vertex Color"), 
        Ø­áŒŠá("UV", 3, "UV", 3, "UV"), 
        Âµá€("MATRIX", 4, "MATRIX", 4, "Bone Matrix"), 
        Ó("BLEND_WEIGHT", 5, "BLEND_WEIGHT", 5, "Blend Weight"), 
        à("PADDING", 6, "PADDING", 6, "Padding");
        
        private final String Ø;
        private static final Â[] áŒŠÆ;
        private static final String áˆºÑ¢Õ = "CL_00002397";
        
        static {
            ÂµÈ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à };
            áŒŠÆ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó, Â.à };
        }
        
        private Â(final String s, final int n, final String p_i46094_1_, final int p_i46094_2_, final String p_i46094_3_) {
            this.Ø = p_i46094_3_;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.Ø;
        }
    }
}
