package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S04PacketEntityEquipment implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private ItemStack Ý;
    private static final String Ø­áŒŠá = "CL_00001330";
    
    public S04PacketEntityEquipment() {
    }
    
    public S04PacketEntityEquipment(final int p_i45221_1_, final int p_i45221_2_, final ItemStack p_i45221_3_) {
        this.HorizonCode_Horizon_È = p_i45221_1_;
        this.Â = p_i45221_2_;
        this.Ý = ((p_i45221_3_ == null) ? null : p_i45221_3_.áˆºÑ¢Õ());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readShort();
        this.Ý = data.Ø();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.HorizonCode_Horizon_È(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public ItemStack HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
