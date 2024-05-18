package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S05PacketSpawnPosition implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private static final String Â = "CL_00001336";
    
    public S05PacketSpawnPosition() {
    }
    
    public S05PacketSpawnPosition(final BlockPos p_i45956_1_) {
        this.HorizonCode_Horizon_È = p_i45956_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180752_1_) {
        p_180752_1_.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
