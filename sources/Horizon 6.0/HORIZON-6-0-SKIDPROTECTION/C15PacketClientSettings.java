package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C15PacketClientSettings implements Packet
{
    private String HorizonCode_Horizon_È;
    private int Â;
    private EntityPlayer.HorizonCode_Horizon_È Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001350";
    
    public C15PacketClientSettings() {
    }
    
    public C15PacketClientSettings(final String p_i45946_1_, final int p_i45946_2_, final EntityPlayer.HorizonCode_Horizon_È p_i45946_3_, final boolean p_i45946_4_, final int p_i45946_5_) {
        this.HorizonCode_Horizon_È = p_i45946_1_;
        this.Â = p_i45946_2_;
        this.Ý = p_i45946_3_;
        this.Ø­áŒŠá = p_i45946_4_;
        this.Âµá€ = p_i45946_5_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(7);
        this.Â = data.readByte();
        this.Ý = EntityPlayer.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.readByte());
        this.Ø­áŒŠá = data.readBoolean();
        this.Âµá€ = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
        data.writeByte(this.Ý.HorizonCode_Horizon_È());
        data.writeBoolean(this.Ø­áŒŠá);
        data.writeByte(this.Âµá€);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public EntityPlayer.HorizonCode_Horizon_È Â() {
        return this.Ý;
    }
    
    public boolean Ý() {
        return this.Ø­áŒŠá;
    }
    
    public int Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
