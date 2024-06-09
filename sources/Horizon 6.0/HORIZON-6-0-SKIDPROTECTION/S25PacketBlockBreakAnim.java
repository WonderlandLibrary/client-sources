package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S25PacketBlockBreakAnim implements Packet
{
    private int HorizonCode_Horizon_È;
    private BlockPos Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001284";
    
    public S25PacketBlockBreakAnim() {
    }
    
    public S25PacketBlockBreakAnim(final int breakerId, final BlockPos pos, final int progress) {
        this.HorizonCode_Horizon_È = breakerId;
        this.Â = pos;
        this.Ý = progress;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.Â();
        this.Ý = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.writeByte(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public BlockPos Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
