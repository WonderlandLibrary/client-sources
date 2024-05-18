package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S18PacketEntityTeleport implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private byte Âµá€;
    private byte Ó;
    private boolean à;
    private static final String Ø = "CL_00001340";
    
    public S18PacketEntityTeleport() {
    }
    
    public S18PacketEntityTeleport(final Entity p_i45233_1_) {
        this.HorizonCode_Horizon_È = p_i45233_1_.ˆá();
        this.Â = MathHelper.Ý(p_i45233_1_.ŒÏ * 32.0);
        this.Ý = MathHelper.Ý(p_i45233_1_.Çªà¢ * 32.0);
        this.Ø­áŒŠá = MathHelper.Ý(p_i45233_1_.Ê * 32.0);
        this.Âµá€ = (byte)(p_i45233_1_.É * 256.0f / 360.0f);
        this.Ó = (byte)(p_i45233_1_.áƒ * 256.0f / 360.0f);
        this.à = p_i45233_1_.ŠÂµà;
    }
    
    public S18PacketEntityTeleport(final int p_i45949_1_, final int p_i45949_2_, final int p_i45949_3_, final int p_i45949_4_, final byte p_i45949_5_, final byte p_i45949_6_, final boolean p_i45949_7_) {
        this.HorizonCode_Horizon_È = p_i45949_1_;
        this.Â = p_i45949_2_;
        this.Ý = p_i45949_3_;
        this.Ø­áŒŠá = p_i45949_4_;
        this.Âµá€ = p_i45949_5_;
        this.Ó = p_i45949_6_;
        this.à = p_i45949_7_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readInt();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
        this.Âµá€ = data.readByte();
        this.Ó = data.readByte();
        this.à = data.readBoolean();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeInt(this.Â);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
        data.writeByte(this.Âµá€);
        data.writeByte(this.Ó);
        data.writeBoolean(this.à);
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
    
    public byte Âµá€() {
        return this.Âµá€;
    }
    
    public byte Ó() {
        return this.Ó;
    }
    
    public boolean à() {
        return this.à;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
