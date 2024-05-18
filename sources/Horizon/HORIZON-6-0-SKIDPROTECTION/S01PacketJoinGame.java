package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S01PacketJoinGame implements Packet
{
    private int HorizonCode_Horizon_È;
    private boolean Â;
    private WorldSettings.HorizonCode_Horizon_È Ý;
    private int Ø­áŒŠá;
    private EnumDifficulty Âµá€;
    private int Ó;
    private WorldType à;
    private boolean Ø;
    private static final String áŒŠÆ = "CL_00001310";
    
    public S01PacketJoinGame() {
    }
    
    public S01PacketJoinGame(final int p_i45976_1_, final WorldSettings.HorizonCode_Horizon_È p_i45976_2_, final boolean p_i45976_3_, final int p_i45976_4_, final EnumDifficulty p_i45976_5_, final int p_i45976_6_, final WorldType p_i45976_7_, final boolean p_i45976_8_) {
        this.HorizonCode_Horizon_È = p_i45976_1_;
        this.Ø­áŒŠá = p_i45976_4_;
        this.Âµá€ = p_i45976_5_;
        this.Ý = p_i45976_2_;
        this.Ó = p_i45976_6_;
        this.Â = p_i45976_3_;
        this.à = p_i45976_7_;
        this.Ø = p_i45976_8_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readInt();
        final short var2 = data.readUnsignedByte();
        this.Â = ((var2 & 0x8) == 0x8);
        final int var3 = var2 & 0xFFFFFFF7;
        this.Ý = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3);
        this.Ø­áŒŠá = data.readByte();
        this.Âµá€ = EnumDifficulty.HorizonCode_Horizon_È(data.readUnsignedByte());
        this.Ó = data.readUnsignedByte();
        this.à = WorldType.HorizonCode_Horizon_È(data.Ý(16));
        if (this.à == null) {
            this.à = WorldType.Ý;
        }
        this.Ø = data.readBoolean();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È);
        int var2 = this.Ý.HorizonCode_Horizon_È();
        if (this.Â) {
            var2 |= 0x8;
        }
        data.writeByte(var2);
        data.writeByte(this.Ø­áŒŠá);
        data.writeByte(this.Âµá€.HorizonCode_Horizon_È());
        data.writeByte(this.Ó);
        data.HorizonCode_Horizon_È(this.à.HorizonCode_Horizon_È());
        data.writeBoolean(this.Ø);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.Â;
    }
    
    public WorldSettings.HorizonCode_Horizon_È Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public EnumDifficulty Âµá€() {
        return this.Âµá€;
    }
    
    public int Ó() {
        return this.Ó;
    }
    
    public WorldType à() {
        return this.à;
    }
    
    public boolean Ø() {
        return this.Ø;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
