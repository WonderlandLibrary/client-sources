package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C09PacketHeldItemChange implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001368";
    
    public C09PacketHeldItemChange() {
    }
    
    public C09PacketHeldItemChange(final int p_i45262_1_) {
        this.HorizonCode_Horizon_È = p_i45262_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readShort();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeShort(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
