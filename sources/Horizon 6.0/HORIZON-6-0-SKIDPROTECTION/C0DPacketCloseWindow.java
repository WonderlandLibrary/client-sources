package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C0DPacketCloseWindow implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001354";
    
    public C0DPacketCloseWindow() {
    }
    
    public C0DPacketCloseWindow(final int p_i45247_1_) {
        this.HorizonCode_Horizon_È = p_i45247_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180759_1_) {
        p_180759_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
