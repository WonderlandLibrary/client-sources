package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C19PacketResourcePackStatus implements Packet
{
    private String HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È Â;
    private static final String Ý = "CL_00002282";
    
    public C19PacketResourcePackStatus() {
    }
    
    public C19PacketResourcePackStatus(String p_i45935_1_, final HorizonCode_Horizon_È p_i45935_2_) {
        if (p_i45935_1_.length() > 40) {
            p_i45935_1_ = p_i45935_1_.substring(0, 40);
        }
        this.HorizonCode_Horizon_È = p_i45935_1_;
        this.Â = p_i45935_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(40);
        this.Â = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_179718_1_) {
        p_179718_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("SUCCESSFULLY_LOADED", 0, "SUCCESSFULLY_LOADED", 0), 
        Â("DECLINED", 1, "DECLINED", 1), 
        Ý("FAILED_DOWNLOAD", 2, "FAILED_DOWNLOAD", 2), 
        Ø­áŒŠá("ACCEPTED", 3, "ACCEPTED", 3);
        
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00002281";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45934_1_, final int p_i45934_2_) {
        }
    }
}
