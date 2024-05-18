package HORIZON-6-0-SKIDPROTECTION;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final MinecraftServer HorizonCode_Horizon_È;
    private final NetworkManager Â;
    private static final String Ý = "CL_00001445";
    
    public NetHandlerHandshakeMemory(final MinecraftServer p_i45287_1_, final NetworkManager p_i45287_2_) {
        this.HorizonCode_Horizon_È = p_i45287_1_;
        this.Â = p_i45287_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C00Handshake packetIn) {
        this.Â.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        this.Â.HorizonCode_Horizon_È(new NetHandlerLoginServer(this.HorizonCode_Horizon_È, this.Â));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
    }
}
