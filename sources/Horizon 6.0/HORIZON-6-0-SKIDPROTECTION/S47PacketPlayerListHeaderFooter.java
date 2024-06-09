package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S47PacketPlayerListHeaderFooter implements Packet
{
    private IChatComponent HorizonCode_Horizon_È;
    private IChatComponent Â;
    private static final String Ý = "CL_00002285";
    
    public S47PacketPlayerListHeaderFooter() {
    }
    
    public S47PacketPlayerListHeaderFooter(final IChatComponent p_i45950_1_) {
        this.HorizonCode_Horizon_È = p_i45950_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý();
        this.Â = data.Ý();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179699_1_) {
        p_179699_1_.HorizonCode_Horizon_È(this);
    }
    
    public IChatComponent HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public IChatComponent Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
