package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S48PacketResourcePackSend implements Packet
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private static final String Ý = "CL_00002293";
    
    public S48PacketResourcePackSend() {
    }
    
    public S48PacketResourcePackSend(final String url, final String hash) {
        this.HorizonCode_Horizon_È = url;
        this.Â = hash;
        if (hash.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(32767);
        this.Â = data.Ý(40);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
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
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
