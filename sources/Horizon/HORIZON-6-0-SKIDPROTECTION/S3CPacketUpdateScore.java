package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S3CPacketUpdateScore implements Packet
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private int Ý;
    private HorizonCode_Horizon_È Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001335";
    
    public S3CPacketUpdateScore() {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
    }
    
    public S3CPacketUpdateScore(final Score scoreIn) {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.HorizonCode_Horizon_È = scoreIn.Ø­áŒŠá();
        this.Â = scoreIn.Ý().Â();
        this.Ý = scoreIn.Â();
        this.Ø­áŒŠá = S3CPacketUpdateScore.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
    
    public S3CPacketUpdateScore(final String nameIn) {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.HorizonCode_Horizon_È = nameIn;
        this.Â = "";
        this.Ý = 0;
        this.Ø­áŒŠá = S3CPacketUpdateScore.HorizonCode_Horizon_È.Â;
    }
    
    public S3CPacketUpdateScore(final String nameIn, final ScoreObjective objectiveIn) {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.HorizonCode_Horizon_È = nameIn;
        this.Â = objectiveIn.Â();
        this.Ý = 0;
        this.Ø­áŒŠá = S3CPacketUpdateScore.HorizonCode_Horizon_È.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(40);
        this.Ø­áŒŠá = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        this.Â = data.Ý(16);
        if (this.Ø­áŒŠá != S3CPacketUpdateScore.HorizonCode_Horizon_È.Â) {
            this.Ý = data.Ø­áŒŠá();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        data.HorizonCode_Horizon_È(this.Â);
        if (this.Ø­áŒŠá != S3CPacketUpdateScore.HorizonCode_Horizon_È.Â) {
            data.Â(this.Ý);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public HorizonCode_Horizon_È Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("CHANGE", 0, "CHANGE", 0), 
        Â("REMOVE", 1, "REMOVE", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private static final String Ø­áŒŠá = "CL_00002288";
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45957_1_, final int p_i45957_2_) {
        }
    }
}
