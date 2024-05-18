package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S40PacketDisconnect implements Packet
{
    private IChatComponent HorizonCode_Horizon_È;
    private static final String Â = "CL_00001298";
    
    public S40PacketDisconnect() {
    }
    
    public S40PacketDisconnect(final IChatComponent reasonIn) {
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
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public IChatComponent HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
