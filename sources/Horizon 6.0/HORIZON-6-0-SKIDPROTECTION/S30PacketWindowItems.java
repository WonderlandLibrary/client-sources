package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;

public class S30PacketWindowItems implements Packet
{
    private int HorizonCode_Horizon_È;
    private ItemStack[] Â;
    private static final String Ý = "CL_00001294";
    
    public S30PacketWindowItems() {
    }
    
    public S30PacketWindowItems(final int p_i45186_1_, final List p_i45186_2_) {
        this.HorizonCode_Horizon_È = p_i45186_1_;
        this.Â = new ItemStack[p_i45186_2_.size()];
        for (int var3 = 0; var3 < this.Â.length; ++var3) {
            final ItemStack var4 = p_i45186_2_.get(var3);
            this.Â[var3] = ((var4 == null) ? null : var4.áˆºÑ¢Õ());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
        final short var2 = data.readShort();
        this.Â = new ItemStack[var2];
        for (int var3 = 0; var3 < var2; ++var3) {
            this.Â[var3] = data.Ø();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â.length);
        for (final ItemStack var5 : this.Â) {
            data.HorizonCode_Horizon_È(var5);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180732_1_) {
        p_180732_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public ItemStack[] Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
