package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S10PacketSpawnPainting implements Packet
{
    private int HorizonCode_Horizon_È;
    private BlockPos Â;
    private EnumFacing Ý;
    private String Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001280";
    
    public S10PacketSpawnPainting() {
    }
    
    public S10PacketSpawnPainting(final EntityPainting p_i45170_1_) {
        this.HorizonCode_Horizon_È = p_i45170_1_.ˆá();
        this.Â = p_i45170_1_.ˆÏ­();
        this.Ý = p_i45170_1_.Â;
        this.Ø­áŒŠá = p_i45170_1_.Ý.ŒÏ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Ø­áŒŠá = data.Ý(EntityPainting.HorizonCode_Horizon_È.Ñ¢á);
        this.Â = data.Â();
        this.Ý = EnumFacing.Â(data.readUnsignedByte());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        data.HorizonCode_Horizon_È(this.Â);
        data.writeByte(this.Ý.Ý());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180722_1_) {
        p_180722_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public BlockPos Â() {
        return this.Â;
    }
    
    public EnumFacing Ý() {
        return this.Ý;
    }
    
    public String Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
