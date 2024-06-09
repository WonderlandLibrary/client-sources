package HORIZON-6-0-SKIDPROTECTION;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private final MinecraftServer HorizonCode_Horizon_È;
    private final NetworkManager Â;
    private static final String Ý = "CL_00001464";
    
    public NetHandlerStatusServer(final MinecraftServer serverIn, final NetworkManager netManager) {
        this.HorizonCode_Horizon_È = serverIn;
        this.Â = netManager;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C00PacketServerQuery packetIn) {
        this.Â.HorizonCode_Horizon_È(new S00PacketServerInfo(this.HorizonCode_Horizon_È.áŒŠáŠ()));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C01PacketPing packetIn) {
        this.Â.HorizonCode_Horizon_È(new S01PacketPong(packetIn.HorizonCode_Horizon_È()));
    }
}
