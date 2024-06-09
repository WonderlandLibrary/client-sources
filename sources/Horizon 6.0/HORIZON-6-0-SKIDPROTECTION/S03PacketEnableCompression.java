package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S03PacketEnableCompression implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00002279";
    
    public S03PacketEnableCompression() {
    }
    
    public S03PacketEnableCompression(final int p_i45929_1_) {
        this.HorizonCode_Horizon_È = p_i45929_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginClient p_179732_1_) {
        p_179732_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginClient)handler);
    }
}
