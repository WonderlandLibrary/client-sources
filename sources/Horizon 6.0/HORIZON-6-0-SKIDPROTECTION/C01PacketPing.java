package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C01PacketPing implements Packet
{
    private long HorizonCode_Horizon_È;
    private static final String Â = "CL_00001392";
    
    public C01PacketPing() {
    }
    
    public C01PacketPing(final long p_i45276_1_) {
        this.HorizonCode_Horizon_È = p_i45276_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readLong();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeLong(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerStatusServer p_180774_1_) {
        p_180774_1_.HorizonCode_Horizon_È(this);
    }
    
    public long HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerStatusServer)handler);
    }
}
