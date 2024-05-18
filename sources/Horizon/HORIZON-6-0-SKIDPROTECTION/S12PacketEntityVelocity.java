package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S12PacketEntityVelocity implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001328";
    
    public S12PacketEntityVelocity() {
    }
    
    public S12PacketEntityVelocity(final Entity p_i45219_1_) {
        this(p_i45219_1_.ˆá(), p_i45219_1_.ÇŽÉ, p_i45219_1_.ˆá, p_i45219_1_.ÇŽÕ);
    }
    
    public S12PacketEntityVelocity(final int p_i45220_1_, double p_i45220_2_, double p_i45220_4_, double p_i45220_6_) {
        this.HorizonCode_Horizon_È = p_i45220_1_;
        final double var8 = 3.9;
        if (p_i45220_2_ < -var8) {
            p_i45220_2_ = -var8;
        }
        if (p_i45220_4_ < -var8) {
            p_i45220_4_ = -var8;
        }
        if (p_i45220_6_ < -var8) {
            p_i45220_6_ = -var8;
        }
        if (p_i45220_2_ > var8) {
            p_i45220_2_ = var8;
        }
        if (p_i45220_4_ > var8) {
            p_i45220_4_ = var8;
        }
        if (p_i45220_6_ > var8) {
            p_i45220_6_ = var8;
        }
        this.Â = (int)(p_i45220_2_ * 8000.0);
        this.Ý = (int)(p_i45220_4_ * 8000.0);
        this.Ø­áŒŠá = (int)(p_i45220_6_ * 8000.0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readShort();
        this.Ý = data.readShort();
        this.Ø­áŒŠá = data.readShort();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.writeShort(this.Ý);
        data.writeShort(this.Ø­áŒŠá);
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
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
