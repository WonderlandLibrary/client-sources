package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S1DPacketEntityEffect implements Packet
{
    private int HorizonCode_Horizon_È;
    private byte Â;
    private byte Ý;
    private int Ø­áŒŠá;
    private byte Âµá€;
    private static final String Ó = "CL_00001343";
    
    public S1DPacketEntityEffect() {
    }
    
    public S1DPacketEntityEffect(final int p_i45237_1_, final PotionEffect p_i45237_2_) {
        this.HorizonCode_Horizon_È = p_i45237_1_;
        this.Â = (byte)(p_i45237_2_.HorizonCode_Horizon_È() & 0xFF);
        this.Ý = (byte)(p_i45237_2_.Ý() & 0xFF);
        if (p_i45237_2_.Â() > 32767) {
            this.Ø­áŒŠá = 32767;
        }
        else {
            this.Ø­áŒŠá = p_i45237_2_.Â();
        }
        this.Âµá€ = (byte)(p_i45237_2_.Âµá€() ? 1 : 0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readByte();
        this.Ý = data.readByte();
        this.Ø­áŒŠá = data.Ø­áŒŠá();
        this.Âµá€ = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
        data.writeByte(this.Ý);
        data.Â(this.Ø­áŒŠá);
        data.writeByte(this.Âµá€);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá == 32767;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public byte Ý() {
        return this.Â;
    }
    
    public byte Ø­áŒŠá() {
        return this.Ý;
    }
    
    public int Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public boolean Ó() {
        return this.Âµá€ != 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
