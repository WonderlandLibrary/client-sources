package HORIZON-6-0-SKIDPROTECTION;

import java.io.File;

public class ResourceUtils
{
    private static ReflectorClass HorizonCode_Horizon_È;
    private static ReflectorField Â;
    private static boolean Ý;
    
    static {
        ResourceUtils.HorizonCode_Horizon_È = new ReflectorClass(AbstractResourcePack.class);
        ResourceUtils.Â = new ReflectorField(ResourceUtils.HorizonCode_Horizon_È, "resourcePackFile");
        ResourceUtils.Ý = true;
    }
    
    public static File HorizonCode_Horizon_È(final AbstractResourcePack arp) {
        if (ResourceUtils.Ý) {
            try {
                return arp.HorizonCode_Horizon_È;
            }
            catch (IllegalAccessError var2) {
                ResourceUtils.Ý = false;
                if (!ResourceUtils.Â.Ý()) {
                    throw var2;
                }
            }
        }
        return (File)Reflector.HorizonCode_Horizon_È(arp, ResourceUtils.Â);
    }
}
