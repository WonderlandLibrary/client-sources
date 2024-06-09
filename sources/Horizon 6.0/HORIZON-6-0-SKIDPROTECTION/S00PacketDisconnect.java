package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S00PacketDisconnect implements Packet
{
    private IChatComponent HorizonCode_Horizon_È;
    private static final String Â = "CL_00001377";
    
    public S00PacketDisconnect() {
    }
    
    public S00PacketDisconnect(final IChatComponent reasonIn) {
        this.HorizonCode_Horizon_È = reasonIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginClient p_180772_1_) {
        p_180772_1_.HorizonCode_Horizon_È(this);
    }
    
    public IChatComponent HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginClient)handler);
    }
}
