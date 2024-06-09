package HORIZON-6-0-SKIDPROTECTION;

import java.util.regex.Pattern;

public class StringUtils
{
    private static final Pattern HorizonCode_Horizon_È;
    private static final String Â = "CL_00001501";
    
    static {
        HorizonCode_Horizon_È = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
    
    public static String HorizonCode_Horizon_È(final int p_76337_0_) {
        int var1 = p_76337_0_ / 20;
        final int var2 = var1 / 60;
        var1 %= 60;
        return (var1 < 10) ? (String.valueOf(var2) + ":0" + var1) : (String.valueOf(var2) + ":" + var1);
    }
    
    public static String HorizonCode_Horizon_È(final String p_76338_0_) {
        return StringUtils.HorizonCode_Horizon_È.matcher(p_76338_0_).replaceAll("");
    }
    
    public static boolean Â(final String p_151246_0_) {
        return org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)p_151246_0_);
    }
}
