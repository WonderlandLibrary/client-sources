package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S1BPacketEntityAttach implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001327";
    
    public S1BPacketEntityAttach() {
    }
    
    public S1BPacketEntityAttach(final int p_i45218_1_, final Entity p_i45218_2_, final Entity p_i45218_3_) {
        this.HorizonCode_Horizon_È = p_i45218_1_;
        this.Â = p_i45218_2_.ˆá();
        this.Ý = ((p_i45218_3_ != null) ? p_i45218_3_.ˆá() : -1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Â = data.readInt();
        this.Ý = data.readInt();
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.Â);
        data.writeInt(this.Ý);
        data.writeByte(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
