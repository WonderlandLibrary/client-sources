package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S49PacketUpdateEntityNBT implements Packet
{
    private int HorizonCode_Horizon_È;
    private NBTTagCompound Â;
    private static final String Ý = "CL_00002301";
    
    public S49PacketUpdateEntityNBT() {
    }
    
    public S49PacketUpdateEntityNBT(final int p_i45979_1_, final NBTTagCompound p_i45979_2_) {
        this.HorizonCode_Horizon_È = p_i45979_1_;
        this.Â = p_i45979_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.à();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179762_1_) {
        p_179762_1_.HorizonCode_Horizon_È(this);
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        return worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
