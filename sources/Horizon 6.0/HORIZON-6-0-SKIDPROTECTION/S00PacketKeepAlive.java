package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S00PacketKeepAlive implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001303";
    
    public S00PacketKeepAlive() {
    }
    
    public S00PacketKeepAlive(final int p_i45195_1_) {
        this.HorizonCode_Horizon_È = p_i45195_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
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
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
