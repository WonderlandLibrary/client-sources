package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S03PacketTimeUpdate implements Packet
{
    private long HorizonCode_Horizon_È;
    private long Â;
    private static final String Ý = "CL_00001337";
    
    public S03PacketTimeUpdate() {
    }
    
    public S03PacketTimeUpdate(final long p_i45230_1_, final long p_i45230_3_, final boolean p_i45230_5_) {
        this.HorizonCode_Horizon_È = p_i45230_1_;
        this.Â = p_i45230_3_;
        if (!p_i45230_5_) {
            this.Â = -this.Â;
            if (this.Â == 0L) {
                this.Â = -1L;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readLong();
        this.Â = data.readLong();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeLong(this.HorizonCode_Horizon_È);
        data.writeLong(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public long HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public long Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
