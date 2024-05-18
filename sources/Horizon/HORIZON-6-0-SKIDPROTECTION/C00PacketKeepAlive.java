package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C00PacketKeepAlive implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001359";
    
    public C00PacketKeepAlive() {
    }
    
    public C00PacketKeepAlive(final int p_i45252_1_) {
        this.HorizonCode_Horizon_È = p_i45252_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
