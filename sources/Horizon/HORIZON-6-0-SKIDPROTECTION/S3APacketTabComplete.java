package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S3APacketTabComplete implements Packet
{
    private String[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001288";
    
    public S3APacketTabComplete() {
    }
    
    public S3APacketTabComplete(final String[] p_i45178_1_) {
        this.HorizonCode_Horizon_È = p_i45178_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = new String[data.Ø­áŒŠá()];
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            this.HorizonCode_Horizon_È[var2] = data.Ý(32767);
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È.length);
        for (final String var5 : this.HorizonCode_Horizon_È) {
            data.HorizonCode_Horizon_È(var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
