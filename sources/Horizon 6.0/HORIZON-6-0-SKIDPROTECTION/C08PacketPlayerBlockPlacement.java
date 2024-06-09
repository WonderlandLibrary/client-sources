package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C08PacketPlayerBlockPlacement implements Packet
{
    private static final BlockPos HorizonCode_Horizon_È;
    private BlockPos Â;
    private int Ý;
    private ItemStack Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private float à;
    private static final String Ø = "CL_00001371";
    
    static {
        HorizonCode_Horizon_È = new BlockPos(-1, -1, -1);
    }
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    public C08PacketPlayerBlockPlacement(final ItemStack p_i45930_1_) {
        this(C08PacketPlayerBlockPlacement.HorizonCode_Horizon_È, 255, p_i45930_1_, 0.0f, 0.0f, 0.0f);
    }
    
    public C08PacketPlayerBlockPlacement(final BlockPos p_i45931_1_, final int p_i45931_2_, final ItemStack p_i45931_3_, final float p_i45931_4_, final float p_i45931_5_, final float p_i45931_6_) {
        this.Â = p_i45931_1_;
        this.Ý = p_i45931_2_;
        this.Ø­áŒŠá = ((p_i45931_3_ != null) ? p_i45931_3_.áˆºÑ¢Õ() : null);
        this.Âµá€ = p_i45931_4_;
        this.Ó = p_i45931_5_;
        this.à = p_i45931_6_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Â = data.Â();
        this.Ý = data.readUnsignedByte();
        this.Ø­áŒŠá = data.Ø();
        this.Âµá€ = data.readUnsignedByte() / 16.0f;
        this.Ó = data.readUnsignedByte() / 16.0f;
        this.à = data.readUnsignedByte() / 16.0f;
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.Â);
        data.writeByte(this.Ý);
        data.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        data.writeByte((int)(this.Âµá€ * 16.0f));
        data.writeByte((int)(this.Ó * 16.0f));
        data.writeByte((int)(this.à * 16.0f));
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180769_1_) {
        p_180769_1_.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public int Â() {
        return this.Ý;
    }
    
    public ItemStack Ý() {
        return this.Ø­áŒŠá;
    }
    
    public float Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public float Âµá€() {
        return this.Ó;
    }
    
    public float Ó() {
        return this.à;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
