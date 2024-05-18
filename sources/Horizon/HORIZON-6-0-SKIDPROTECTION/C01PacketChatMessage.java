package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C01PacketChatMessage implements Packet
{
    private String HorizonCode_Horizon_È;
    private static final String Â = "CL_00001347";
    
    public C01PacketChatMessage() {
    }
    
    public C01PacketChatMessage(String messageIn) {
        if (messageIn.length() > 100) {
            messageIn = messageIn.substring(0, 100);
        }
        this.HorizonCode_Horizon_È = messageIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(100);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180757_1_) {
        p_180757_1_.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
