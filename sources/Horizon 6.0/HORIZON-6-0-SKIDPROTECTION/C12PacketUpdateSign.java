package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C12PacketUpdateSign implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private IChatComponent[] Â;
    private static final String Ý = "CL_00001370";
    
    public C12PacketUpdateSign() {
    }
    
    public C12PacketUpdateSign(final BlockPos p_i45933_1_, final IChatComponent[] p_i45933_2_) {
        this.HorizonCode_Horizon_È = p_i45933_1_;
        this.Â = new IChatComponent[] { p_i45933_2_[0], p_i45933_2_[1], p_i45933_2_[2], p_i45933_2_[3] };
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
        this.Â = new IChatComponent[4];
        for (int var2 = 0; var2 < 4; ++var2) {
            this.Â[var2] = data.Ý();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        for (int var2 = 0; var2 < 4; ++var2) {
            data.HorizonCode_Horizon_È(this.Â[var2]);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public IChatComponent[] Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
