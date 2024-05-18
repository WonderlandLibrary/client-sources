package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;

public class S1CPacketEntityMetadata implements Packet
{
    private int HorizonCode_Horizon_È;
    private List Â;
    private static final String Ý = "CL_00001326";
    
    public S1CPacketEntityMetadata() {
    }
    
    public S1CPacketEntityMetadata(final int p_i45217_1_, final DataWatcher p_i45217_2_, final boolean p_i45217_3_) {
        this.HorizonCode_Horizon_È = p_i45217_1_;
        if (p_i45217_3_) {
            this.Â = p_i45217_2_.Ý();
        }
        else {
            this.Â = p_i45217_2_.Â();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = DataWatcher.Â(data);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        DataWatcher.HorizonCode_Horizon_È(this.Â, data);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180748_1_) {
        p_180748_1_.HorizonCode_Horizon_È(this);
    }
    
    public List HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
