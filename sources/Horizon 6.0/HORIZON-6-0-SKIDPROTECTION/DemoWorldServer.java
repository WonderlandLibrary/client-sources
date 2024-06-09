package HORIZON-6-0-SKIDPROTECTION;

public class DemoWorldServer extends WorldServer
{
    public static String É;
    private static final long á€;
    public static final WorldSettings áƒ;
    private static final String Õ = "CL_00001428";
    
    static {
        DemoWorldServer.É = "aHR0cDovL2hvcml6b25jby5kZS9ob3Jpem9uL2JldGEvY2xpZW50L2xvZ2luLnBocD91c2VybmFtZT0=";
        á€ = "North Carolina".hashCode();
        áƒ = new WorldSettings(DemoWorldServer.á€, WorldSettings.HorizonCode_Horizon_È.Â, true, false, WorldType.Ý).HorizonCode_Horizon_È();
    }
    
    public DemoWorldServer(final MinecraftServer server, final ISaveHandler saveHandlerIn, final WorldInfo info, final int dimensionId, final Profiler profilerIn) {
        super(server, saveHandlerIn, info, dimensionId, profilerIn);
        this.Ø­à.HorizonCode_Horizon_È(DemoWorldServer.áƒ);
    }
    
    public static void áˆºÏ() {
    }
}
