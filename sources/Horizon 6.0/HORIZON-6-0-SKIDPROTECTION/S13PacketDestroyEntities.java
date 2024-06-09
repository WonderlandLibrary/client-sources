package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S13PacketDestroyEntities implements Packet
{
    private int[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001320";
    
    public S13PacketDestroyEntities() {
    }
    
    public S13PacketDestroyEntities(final int... p_i45211_1_) {
        this.HorizonCode_Horizon_È = p_i45211_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = new int[data.Ø­áŒŠá()];
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            this.HorizonCode_Horizon_È[var2] = data.Ø­áŒŠá();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È.length);
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            data.Â(this.HorizonCode_Horizon_È[var2]);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
