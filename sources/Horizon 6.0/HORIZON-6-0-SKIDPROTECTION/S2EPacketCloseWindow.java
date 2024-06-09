package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2EPacketCloseWindow implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001292";
    
    public S2EPacketCloseWindow() {
    }
    
    public S2EPacketCloseWindow(final int p_i45183_1_) {
        this.HorizonCode_Horizon_È = p_i45183_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180731_1_) {
        p_180731_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
