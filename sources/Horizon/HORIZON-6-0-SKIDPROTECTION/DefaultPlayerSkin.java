package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;

public class DefaultPlayerSkin
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private static final String Ý = "CL_00002396";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/steve.png");
        Â = new ResourceLocation_1975012498("textures/entity/alex.png");
    }
    
    public static ResourceLocation_1975012498 HorizonCode_Horizon_È() {
        return DefaultPlayerSkin.HorizonCode_Horizon_È;
    }
    
    public static ResourceLocation_1975012498 HorizonCode_Horizon_È(final UUID p_177334_0_) {
        return Ý(p_177334_0_) ? DefaultPlayerSkin.Â : DefaultPlayerSkin.HorizonCode_Horizon_È;
    }
    
    public static String Â(final UUID p_177332_0_) {
        return Ý(p_177332_0_) ? "slim" : "default";
    }
    
    private static boolean Ý(final UUID p_177333_0_) {
        return (p_177333_0_.hashCode() & 0x1) == 0x1;
    }
}
