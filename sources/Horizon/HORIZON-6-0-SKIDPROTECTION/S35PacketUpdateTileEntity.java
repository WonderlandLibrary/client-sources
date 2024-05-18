package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S35PacketUpdateTileEntity implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private int Â;
    private NBTTagCompound Ý;
    private static final String Ø­áŒŠá = "CL_00001285";
    
    public S35PacketUpdateTileEntity() {
    }
    
    public S35PacketUpdateTileEntity(final BlockPos p_i45990_1_, final int p_i45990_2_, final NBTTagCompound p_i45990_3_) {
        this.HorizonCode_Horizon_È = p_i45990_1_;
        this.Â = p_i45990_2_;
        this.Ý = p_i45990_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
        this.Â = data.readUnsignedByte();
        this.Ý = data.à();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte((byte)this.Â);
        data.HorizonCode_Horizon_È(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180725_1_) {
        p_180725_1_.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public NBTTagCompound Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
