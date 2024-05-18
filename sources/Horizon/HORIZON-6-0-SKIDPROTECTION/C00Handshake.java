package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C00Handshake implements Packet
{
    private int HorizonCode_Horizon_È;
    private String Â;
    private int Ý;
    private EnumConnectionState Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001372";
    
    public C00Handshake() {
    }
    
    public C00Handshake(final int p_i45266_1_, final String p_i45266_2_, final int p_i45266_3_, final EnumConnectionState p_i45266_4_) {
        this.HorizonCode_Horizon_È = p_i45266_1_;
        this.Â = p_i45266_2_;
        this.Ý = p_i45266_3_;
        this.Ø­áŒŠá = p_i45266_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.Ý(255);
        this.Ý = data.readUnsignedShort();
        this.Ø­áŒŠá = EnumConnectionState.HorizonCode_Horizon_È(data.Ø­áŒŠá());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.writeShort(this.Ý);
        data.Â(this.Ø­áŒŠá.HorizonCode_Horizon_È());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerHandshakeServer p_180770_1_) {
        p_180770_1_.HorizonCode_Horizon_È(this);
    }
    
    public EnumConnectionState HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerHandshakeServer)handler);
    }
}
