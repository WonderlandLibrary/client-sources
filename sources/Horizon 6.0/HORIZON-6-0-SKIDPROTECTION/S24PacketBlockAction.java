package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S24PacketBlockAction implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private Block Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001286";
    
    public S24PacketBlockAction() {
    }
    
    public S24PacketBlockAction(final BlockPos p_i45989_1_, final Block p_i45989_2_, final int p_i45989_3_, final int p_i45989_4_) {
        this.HorizonCode_Horizon_È = p_i45989_1_;
        this.Â = p_i45989_3_;
        this.Ý = p_i45989_4_;
        this.Ø­áŒŠá = p_i45989_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
        this.Â = data.readUnsignedByte();
        this.Ý = data.readUnsignedByte();
        this.Ø­áŒŠá = Block.HorizonCode_Horizon_È(data.Ø­áŒŠá() & 0xFFF);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
        data.writeByte(this.Ý);
        data.Â(Block.HorizonCode_Horizon_È(this.Ø­áŒŠá) & 0xFFF);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180726_1_) {
        p_180726_1_.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public Block Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
