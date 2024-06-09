package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S36PacketSignEditorOpen implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private static final String Â = "CL_00001316";
    
    public S36PacketSignEditorOpen() {
    }
    
    public S36PacketSignEditorOpen(final BlockPos p_i45971_1_) {
        this.HorizonCode_Horizon_È = p_i45971_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
