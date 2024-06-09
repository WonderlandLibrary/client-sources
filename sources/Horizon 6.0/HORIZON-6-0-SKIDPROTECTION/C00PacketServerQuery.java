package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C00PacketServerQuery implements Packet
{
    private static final String HorizonCode_Horizon_È = "CL_00001393";
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerStatusServer p_180775_1_) {
        p_180775_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerStatusServer)handler);
    }
}
