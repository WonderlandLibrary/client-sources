package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class VertexFormat
{
    private static final Logger HorizonCode_Horizon_È;
    private final List Â;
    private final List Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private List Ó;
    private int à;
    private static final String Ø = "CL_00002401";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public VertexFormat(final VertexFormat p_i46097_1_) {
        this();
        for (int var2 = 0; var2 < p_i46097_1_.Ø(); ++var2) {
            this.HorizonCode_Horizon_È(p_i46097_1_.Ý(var2));
        }
        this.Ø­áŒŠá = p_i46097_1_.Ó();
    }
    
    public VertexFormat() {
        this.Â = Lists.newArrayList();
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = 0;
        this.Âµá€ = -1;
        this.Ó = Lists.newArrayList();
        this.à = -1;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â.clear();
        this.Ý.clear();
        this.Âµá€ = -1;
        this.Ó.clear();
        this.à = -1;
        this.Ø­áŒŠá = 0;
    }
    
    public void HorizonCode_Horizon_È(final VertexFormatElement p_177349_1_) {
        if (p_177349_1_.à() && this.áŒŠÆ()) {
            VertexFormat.HorizonCode_Horizon_È.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
        }
        else {
            this.Â.add(p_177349_1_);
            this.Ý.add(this.Ø­áŒŠá);
            p_177349_1_.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            this.Ø­áŒŠá += p_177349_1_.Ó();
            switch (VertexFormat.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_177349_1_.Ý().ordinal()]) {
                case 1: {
                    this.à = p_177349_1_.HorizonCode_Horizon_È();
                    break;
                }
                case 2: {
                    this.Âµá€ = p_177349_1_.HorizonCode_Horizon_È();
                    break;
                }
                case 3: {
                    this.Ó.add(p_177349_1_.Âµá€(), p_177349_1_.HorizonCode_Horizon_È());
                    break;
                }
            }
        }
    }
    
    public boolean Â() {
        return this.à >= 0;
    }
    
    public int Ý() {
        return this.à;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Âµá€ >= 0;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    public boolean HorizonCode_Horizon_È(final int p_177347_1_) {
        return this.Ó.size() - 1 >= p_177347_1_;
    }
    
    public int Â(final int p_177344_1_) {
        return this.Ó.get(p_177344_1_);
    }
    
    @Override
    public String toString() {
        String var1 = "format: " + this.Â.size() + " elements: ";
        for (int var2 = 0; var2 < this.Â.size(); ++var2) {
            var1 = String.valueOf(var1) + this.Â.get(var2).toString();
            if (var2 != this.Â.size() - 1) {
                var1 = String.valueOf(var1) + " ";
            }
        }
        return var1;
    }
    
    private boolean áŒŠÆ() {
        for (final VertexFormatElement var2 : this.Â) {
            if (var2.à()) {
                return true;
            }
        }
        return false;
    }
    
    public int Ó() {
        return this.Ø­áŒŠá;
    }
    
    public List à() {
        return this.Â;
    }
    
    public int Ø() {
        return this.Â.size();
    }
    
    public VertexFormatElement Ý(final int p_177348_1_) {
        return this.Â.get(p_177348_1_);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final VertexFormat var2 = (VertexFormat)p_equals_1_;
            return this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Â.equals(var2.Â) && this.Ý.equals(var2.Ý);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int var1 = this.Â.hashCode();
        var1 = 31 * var1 + this.Ý.hashCode();
        var1 = 31 * var1 + this.Ø­áŒŠá;
        return var1;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002400";
        
        static {
            HorizonCode_Horizon_È = new int[VertexFormatElement.Â.values().length];
            try {
                VertexFormat.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                VertexFormat.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                VertexFormat.HorizonCode_Horizon_È.HorizonCode_Horizon_È[VertexFormatElement.Â.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
