package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.UUID;

public class C18PacketSpectate implements Packet
{
    private UUID HorizonCode_Horizon_È;
    private static final String Â = "CL_00002280";
    
    public C18PacketSpectate() {
    }
    
    public C18PacketSpectate(final UUID p_i45932_1_) {
        this.HorizonCode_Horizon_È = p_i45932_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ó();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_179728_1_) {
        p_179728_1_.HorizonCode_Horizon_È(this);
    }
    
    public Entity HorizonCode_Horizon_È(final WorldServer p_179727_1_) {
        return p_179727_1_.Â(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
