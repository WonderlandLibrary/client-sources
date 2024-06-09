package HORIZON-6-0-SKIDPROTECTION;

public class Util_1252169911
{
    private static final String HorizonCode_Horizon_È = "CL_00001633";
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        final String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? Util_1252169911.HorizonCode_Horizon_È.Ý : (var0.contains("mac") ? Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá : (var0.contains("solaris") ? Util_1252169911.HorizonCode_Horizon_È.Â : (var0.contains("sunos") ? Util_1252169911.HorizonCode_Horizon_È.Â : (var0.contains("linux") ? Util_1252169911.HorizonCode_Horizon_È.HorizonCode_Horizon_È : (var0.contains("unix") ? Util_1252169911.HorizonCode_Horizon_È.HorizonCode_Horizon_È : Util_1252169911.HorizonCode_Horizon_È.Âµá€)))));
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("LINUX", 0, "LINUX", 0), 
        Â("SOLARIS", 1, "SOLARIS", 1), 
        Ý("WINDOWS", 2, "WINDOWS", 2), 
        Ø­áŒŠá("OSX", 3, "OSX", 3), 
        Âµá€("UNKNOWN", 4, "UNKNOWN", 4);
        
        private static final HorizonCode_Horizon_È[] Ó;
        private static final String à = "CL_00001660";
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i1357_1_, final int p_i1357_2_) {
        }
    }
}
