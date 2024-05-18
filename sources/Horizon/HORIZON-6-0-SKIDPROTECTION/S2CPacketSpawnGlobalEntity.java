package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2CPacketSpawnGlobalEntity implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001278";
    
    public S2CPacketSpawnGlobalEntity() {
    }
    
    public S2CPacketSpawnGlobalEntity(final Entity p_i45191_1_) {
        this.HorizonCode_Horizon_È = p_i45191_1_.ˆá();
        this.Â = MathHelper.Ý(p_i45191_1_.ŒÏ * 32.0);
        this.Ý = MathHelper.Ý(p_i45191_1_.Çªà¢ * 32.0);
        this.Ø­áŒŠá = MathHelper.Ý(p_i45191_1_.Ê * 32.0);
        if (p_i45191_1_ instanceof EntityLightningBolt) {
            this.Âµá€ = 1;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Âµá€ = data.readByte();
        this.Â = data.readInt();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Âµá€);
        data.writeInt(this.Â);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180720_1_) {
        p_180720_1_.HorizonCode_Horizon_È(this);
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
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
