package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S31PacketWindowProperty implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001295";
    
    public S31PacketWindowProperty() {
    }
    
    public S31PacketWindowProperty(final int p_i45187_1_, final int p_i45187_2_, final int p_i45187_3_) {
        this.HorizonCode_Horizon_È = p_i45187_1_;
        this.Â = p_i45187_2_;
        this.Ý = p_i45187_3_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180733_1_) {
        p_180733_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
        this.Â = data.readShort();
        this.Ý = data.readShort();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.writeShort(this.Ý);
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
