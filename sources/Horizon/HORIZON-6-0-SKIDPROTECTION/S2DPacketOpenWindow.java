package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2DPacketOpenWindow implements Packet
{
    private int HorizonCode_Horizon_È;
    private String Â;
    private IChatComponent Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001293";
    
    public S2DPacketOpenWindow() {
    }
    
    public S2DPacketOpenWindow(final int p_i45981_1_, final String p_i45981_2_, final IChatComponent p_i45981_3_) {
        this(p_i45981_1_, p_i45981_2_, p_i45981_3_, 0);
    }
    
    public S2DPacketOpenWindow(final int p_i45982_1_, final String p_i45982_2_, final IChatComponent p_i45982_3_, final int p_i45982_4_) {
        this.HorizonCode_Horizon_È = p_i45982_1_;
        this.Â = p_i45982_2_;
        this.Ý = p_i45982_3_;
        this.Ø­áŒŠá = p_i45982_4_;
    }
    
    public S2DPacketOpenWindow(final int p_i45983_1_, final String p_i45983_2_, final IChatComponent p_i45983_3_, final int p_i45983_4_, final int p_i45983_5_) {
        this(p_i45983_1_, p_i45983_2_, p_i45983_3_, p_i45983_4_);
        this.Âµá€ = p_i45983_5_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
        this.Â = data.Ý(32);
        this.Ý = data.Ý();
        this.Ø­áŒŠá = data.readUnsignedByte();
        if (this.Â.equals("EntityHorse")) {
            this.Âµá€ = data.readInt();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.HorizonCode_Horizon_È(this.Ý);
        data.writeByte(this.Ø­áŒŠá);
        if (this.Â.equals("EntityHorse")) {
            data.writeInt(this.Âµá€);
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public IChatComponent Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    public boolean Ó() {
        return this.Ø­áŒŠá > 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
