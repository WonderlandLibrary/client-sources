package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S19PacketEntityStatus implements Packet
{
    private int HorizonCode_Horizon_È;
    private byte Â;
    private static final String Ý = "CL_00001299";
    
    public S19PacketEntityStatus() {
    }
    
    public S19PacketEntityStatus(final Entity p_i46335_1_, final byte p_i46335_2_) {
        this.HorizonCode_Horizon_È = p_i46335_1_.ˆá();
        this.Â = p_i46335_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readInt();
        this.Â = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180736_1_) {
        p_180736_1_.HorizonCode_Horizon_È(this);
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        return worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public byte HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
