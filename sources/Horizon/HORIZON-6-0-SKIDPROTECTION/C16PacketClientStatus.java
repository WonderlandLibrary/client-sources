package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C16PacketClientStatus implements Packet
{
    private HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private static final String Â = "CL_00001348";
    
    public C16PacketClientStatus() {
    }
    
    public C16PacketClientStatus(final HorizonCode_Horizon_È statusIn) {
        this.HorizonCode_Horizon_È = statusIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180758_1_) {
        p_180758_1_.HorizonCode_Horizon_È(this);
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("PERFORM_RESPAWN", 0, "PERFORM_RESPAWN", 0), 
        Â("REQUEST_STATS", 1, "REQUEST_STATS", 1), 
        Ý("OPEN_INVENTORY_ACHIEVEMENT", 2, "OPEN_INVENTORY_ACHIEVEMENT", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001349";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45947_1_, final int p_i45947_2_) {
        }
    }
}
