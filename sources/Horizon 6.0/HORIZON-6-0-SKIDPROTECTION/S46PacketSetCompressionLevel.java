package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S46PacketSetCompressionLevel implements Packet
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00002300";
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179759_1_) {
        p_179759_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
