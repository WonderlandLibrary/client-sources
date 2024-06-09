package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S07PacketRespawn implements Packet
{
    private int HorizonCode_Horizon_È;
    private EnumDifficulty Â;
    private WorldSettings.HorizonCode_Horizon_È Ý;
    private WorldType Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001322";
    
    public S07PacketRespawn() {
    }
    
    public S07PacketRespawn(final int p_i45213_1_, final EnumDifficulty p_i45213_2_, final WorldType p_i45213_3_, final WorldSettings.HorizonCode_Horizon_È p_i45213_4_) {
        this.HorizonCode_Horizon_È = p_i45213_1_;
        this.Â = p_i45213_2_;
        this.Ý = p_i45213_4_;
        this.Ø­áŒŠá = p_i45213_3_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readInt();
        this.Â = EnumDifficulty.HorizonCode_Horizon_È(data.readUnsignedByte());
        this.Ý = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.readUnsignedByte());
        this.Ø­áŒŠá = WorldType.HorizonCode_Horizon_È(data.Ý(16));
        if (this.Ø­áŒŠá == null) {
            this.Ø­áŒŠá = WorldType.Ý;
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â.HorizonCode_Horizon_È());
        data.writeByte(this.Ý.HorizonCode_Horizon_È());
        data.HorizonCode_Horizon_È(this.Ø­áŒŠá.HorizonCode_Horizon_È());
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public EnumDifficulty Â() {
        return this.Â;
    }
    
    public WorldSettings.HorizonCode_Horizon_È Ý() {
        return this.Ý;
    }
    
    public WorldType Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
