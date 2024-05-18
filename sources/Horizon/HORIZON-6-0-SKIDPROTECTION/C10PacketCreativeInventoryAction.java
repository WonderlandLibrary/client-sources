package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C10PacketCreativeInventoryAction implements Packet
{
    private int HorizonCode_Horizon_È;
    private ItemStack Â;
    private static final String Ý = "CL_00001369";
    
    public C10PacketCreativeInventoryAction() {
    }
    
    public C10PacketCreativeInventoryAction(final int p_i45263_1_, final ItemStack p_i45263_2_) {
        this.HorizonCode_Horizon_È = p_i45263_1_;
        this.Â = ((p_i45263_2_ != null) ? p_i45263_2_.áˆºÑ¢Õ() : null);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180767_1_) {
        p_180767_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readShort();
        this.Â = data.Ø();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeShort(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public ItemStack Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
