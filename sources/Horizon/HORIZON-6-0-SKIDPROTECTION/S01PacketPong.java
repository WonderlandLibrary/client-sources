package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S01PacketPong implements Packet
{
    private long HorizonCode_Horizon_È;
    private static final String Â = "CL_00001383";
    
    public S01PacketPong() {
    }
    
    public S01PacketPong(final long time) {
        this.HorizonCode_Horizon_È = time;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readLong();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeLong(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerStatusClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerStatusClient)handler);
    }
}
