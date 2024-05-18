package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class IntCache
{
    private static int HorizonCode_Horizon_È;
    private static List Â;
    private static List Ý;
    private static List Ø­áŒŠá;
    private static List Âµá€;
    private static final String Ó = "CL_00000557";
    
    static {
        IntCache.HorizonCode_Horizon_È = 256;
        IntCache.Â = Lists.newArrayList();
        IntCache.Ý = Lists.newArrayList();
        IntCache.Ø­áŒŠá = Lists.newArrayList();
        IntCache.Âµá€ = Lists.newArrayList();
    }
    
    public static synchronized int[] HorizonCode_Horizon_È(final int p_76445_0_) {
        if (p_76445_0_ <= 256) {
            if (IntCache.Â.isEmpty()) {
                final int[] var1 = new int[256];
                IntCache.Ý.add(var1);
                return var1;
            }
            final int[] var1 = IntCache.Â.remove(IntCache.Â.size() - 1);
            IntCache.Ý.add(var1);
            return var1;
        }
        else {
            if (p_76445_0_ > IntCache.HorizonCode_Horizon_È) {
                IntCache.HorizonCode_Horizon_È = p_76445_0_;
                IntCache.Ø­áŒŠá.clear();
                IntCache.Âµá€.clear();
                final int[] var1 = new int[IntCache.HorizonCode_Horizon_È];
                IntCache.Âµá€.add(var1);
                return var1;
            }
            if (IntCache.Ø­áŒŠá.isEmpty()) {
                final int[] var1 = new int[IntCache.HorizonCode_Horizon_È];
                IntCache.Âµá€.add(var1);
                return var1;
            }
            final int[] var1 = IntCache.Ø­áŒŠá.remove(IntCache.Ø­áŒŠá.size() - 1);
            IntCache.Âµá€.add(var1);
            return var1;
        }
    }
    
    public static synchronized void HorizonCode_Horizon_È() {
        if (!IntCache.Ø­áŒŠá.isEmpty()) {
            IntCache.Ø­áŒŠá.remove(IntCache.Ø­áŒŠá.size() - 1);
        }
        if (!IntCache.Â.isEmpty()) {
            IntCache.Â.remove(IntCache.Â.size() - 1);
        }
        IntCache.Ø­áŒŠá.addAll(IntCache.Âµá€);
        IntCache.Â.addAll(IntCache.Ý);
        IntCache.Âµá€.clear();
        IntCache.Ý.clear();
    }
    
    public static synchronized String Â() {
        return "cache: " + IntCache.Ø­áŒŠá.size() + ", tcache: " + IntCache.Â.size() + ", allocated: " + IntCache.Âµá€.size() + ", tallocated: " + IntCache.Ý.size();
    }
}
