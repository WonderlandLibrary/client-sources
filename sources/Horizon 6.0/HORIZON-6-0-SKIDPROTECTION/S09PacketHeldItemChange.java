package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S09PacketHeldItemChange implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001324";
    
    public S09PacketHeldItemChange() {
    }
    
    public S09PacketHeldItemChange(final int p_i45215_1_) {
        this.HorizonCode_Horizon_È = p_i45215_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180746_1_) {
        p_180746_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
