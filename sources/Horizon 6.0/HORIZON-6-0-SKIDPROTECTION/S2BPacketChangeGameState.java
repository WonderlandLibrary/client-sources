package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2BPacketChangeGameState implements Packet
{
    public static final String[] HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    private static final String Ø­áŒŠá = "CL_00001301";
    
    static {
        HorizonCode_Horizon_È = new String[] { "tile.bed.notValid" };
    }
    
    public S2BPacketChangeGameState() {
    }
    
    public S2BPacketChangeGameState(final int stateIn, final float p_i45194_2_) {
        this.Â = stateIn;
        this.Ý = p_i45194_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Â = data.readUnsignedByte();
        this.Ý = data.readFloat();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.Â);
        data.writeFloat(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public float Â() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
