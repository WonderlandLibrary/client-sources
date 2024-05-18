package HORIZON-6-0-SKIDPROTECTION;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer
{
    private final MinecraftServer HorizonCode_Horizon_È;
    private final NetworkManager Â;
    private static final String Ý = "CL_00001456";
    
    public NetHandlerHandshakeTCP(final MinecraftServer serverIn, final NetworkManager netManager) {
        this.HorizonCode_Horizon_È = serverIn;
        this.Â = netManager;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C00Handshake packetIn) {
        switch (NetHandlerHandshakeTCP.HorizonCode_Horizon_È.HorizonCode_Horizon_È[packetIn.HorizonCode_Horizon_È().ordinal()]) {
            case 1: {
                this.Â.HorizonCode_Horizon_È(EnumConnectionState.Ø­áŒŠá);
                if (packetIn.Â() > 47) {
                    final ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.8");
                    this.Â.HorizonCode_Horizon_È(new S00PacketDisconnect(var2));
                    this.Â.HorizonCode_Horizon_È(var2);
                    break;
                }
                if (packetIn.Â() < 47) {
                    final ChatComponentText var2 = new ChatComponentText("Outdated client! Please use 1.8");
                    this.Â.HorizonCode_Horizon_È(new S00PacketDisconnect(var2));
                    this.Â.HorizonCode_Horizon_È(var2);
                    break;
                }
                this.Â.HorizonCode_Horizon_È(new NetHandlerLoginServer(this.HorizonCode_Horizon_È, this.Â));
                break;
            }
            case 2: {
                this.Â.HorizonCode_Horizon_È(EnumConnectionState.Ý);
                this.Â.HorizonCode_Horizon_È(new NetHandlerStatusServer(this.HorizonCode_Horizon_È, this.Â));
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + packetIn.HorizonCode_Horizon_È());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001457";
        
        static {
            HorizonCode_Horizon_È = new int[EnumConnectionState.values().length];
            try {
                NetHandlerHandshakeTCP.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumConnectionState.Ø­áŒŠá.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NetHandlerHandshakeTCP.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumConnectionState.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
