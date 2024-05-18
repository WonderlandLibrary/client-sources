package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2APacketParticles implements Packet
{
    private EnumParticleTypes HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private float Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private float à;
    private float Ø;
    private int áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private int[] ÂµÈ;
    private static final String á = "CL_00001308";
    
    public S2APacketParticles() {
    }
    
    public S2APacketParticles(final EnumParticleTypes p_i45977_1_, final boolean p_i45977_2_, final float p_i45977_3_, final float p_i45977_4_, final float p_i45977_5_, final float p_i45977_6_, final float p_i45977_7_, final float p_i45977_8_, final float p_i45977_9_, final int p_i45977_10_, final int... p_i45977_11_) {
        this.HorizonCode_Horizon_È = p_i45977_1_;
        this.áˆºÑ¢Õ = p_i45977_2_;
        this.Â = p_i45977_3_;
        this.Ý = p_i45977_4_;
        this.Ø­áŒŠá = p_i45977_5_;
        this.Âµá€ = p_i45977_6_;
        this.Ó = p_i45977_7_;
        this.à = p_i45977_8_;
        this.Ø = p_i45977_9_;
        this.áŒŠÆ = p_i45977_10_;
        this.ÂµÈ = p_i45977_11_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = EnumParticleTypes.HorizonCode_Horizon_È(data.readInt());
        if (this.HorizonCode_Horizon_È == null) {
            this.HorizonCode_Horizon_È = EnumParticleTypes.á€;
        }
        this.áˆºÑ¢Õ = data.readBoolean();
        this.Â = data.readFloat();
        this.Ý = data.readFloat();
        this.Ø­áŒŠá = data.readFloat();
        this.Âµá€ = data.readFloat();
        this.Ó = data.readFloat();
        this.à = data.readFloat();
        this.Ø = data.readFloat();
        this.áŒŠÆ = data.readInt();
        final int var2 = this.HorizonCode_Horizon_È.Ø­áŒŠá();
        this.ÂµÈ = new int[var2];
        for (int var3 = 0; var3 < var2; ++var3) {
            this.ÂµÈ[var3] = data.Ø­áŒŠá();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È.Ý());
        data.writeBoolean(this.áˆºÑ¢Õ);
        data.writeFloat(this.Â);
        data.writeFloat(this.Ý);
        data.writeFloat(this.Ø­áŒŠá);
        data.writeFloat(this.Âµá€);
        data.writeFloat(this.Ó);
        data.writeFloat(this.à);
        data.writeFloat(this.Ø);
        data.writeInt(this.áŒŠÆ);
        for (int var2 = this.HorizonCode_Horizon_È.Ø­áŒŠá(), var3 = 0; var3 < var2; ++var3) {
            data.Â(this.ÂµÈ[var3]);
        }
    }
    
    public EnumParticleTypes HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public double Ý() {
        return this.Â;
    }
    
    public double Ø­áŒŠá() {
        return this.Ý;
    }
    
    public double Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public float Ó() {
        return this.Âµá€;
    }
    
    public float à() {
        return this.Ó;
    }
    
    public float Ø() {
        return this.à;
    }
    
    public float áŒŠÆ() {
        return this.Ø;
    }
    
    public int áˆºÑ¢Õ() {
        return this.áŒŠÆ;
    }
    
    public int[] ÂµÈ() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180740_1_) {
        p_180740_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
