package HORIZON-6-0-SKIDPROTECTION;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper
{
    private static final Logger HorizonCode_Horizon_È;
    private static ShaderLinkHelper Â;
    private static final String Ý = "CL_00001045";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public static void HorizonCode_Horizon_È() {
        ShaderLinkHelper.Â = new ShaderLinkHelper();
    }
    
    public static ShaderLinkHelper Â() {
        return ShaderLinkHelper.Â;
    }
    
    public void HorizonCode_Horizon_È(final ShaderManager p_148077_1_) {
        p_148077_1_.Ó().Â(p_148077_1_);
        p_148077_1_.Âµá€().Â(p_148077_1_);
        OpenGlHelper.Âµá€(p_148077_1_.à());
    }
    
    public int Ý() throws JsonException {
        final int var1 = OpenGlHelper.Ø­áŒŠá();
        if (var1 <= 0) {
            throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
        }
        return var1;
    }
    
    public void Â(final ShaderManager manager) {
        manager.Ó().HorizonCode_Horizon_È(manager);
        manager.Âµá€().HorizonCode_Horizon_È(manager);
        OpenGlHelper.Ó(manager.à());
        final int var2 = OpenGlHelper.HorizonCode_Horizon_È(manager.à(), OpenGlHelper.á);
        if (var2 == 0) {
            ShaderLinkHelper.HorizonCode_Horizon_È.warn("Error encountered when linking program containing VS " + manager.Âµá€().HorizonCode_Horizon_È() + " and FS " + manager.Ó().HorizonCode_Horizon_È() + ". Log output:");
            ShaderLinkHelper.HorizonCode_Horizon_È.warn(OpenGlHelper.Âµá€(manager.à(), 32768));
        }
    }
}
