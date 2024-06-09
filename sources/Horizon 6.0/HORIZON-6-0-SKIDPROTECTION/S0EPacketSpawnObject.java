package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S0EPacketSpawnObject implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private static final String á = "CL_00001276";
    
    public S0EPacketSpawnObject() {
    }
    
    public S0EPacketSpawnObject(final Entity p_i45165_1_, final int p_i45165_2_) {
        this(p_i45165_1_, p_i45165_2_, 0);
    }
    
    public S0EPacketSpawnObject(final Entity p_i45166_1_, final int p_i45166_2_, final int p_i45166_3_) {
        this.HorizonCode_Horizon_È = p_i45166_1_.ˆá();
        this.Â = MathHelper.Ý(p_i45166_1_.ŒÏ * 32.0);
        this.Ý = MathHelper.Ý(p_i45166_1_.Çªà¢ * 32.0);
        this.Ø­áŒŠá = MathHelper.Ý(p_i45166_1_.Ê * 32.0);
        this.Ø = MathHelper.Ø­áŒŠá(p_i45166_1_.áƒ * 256.0f / 360.0f);
        this.áŒŠÆ = MathHelper.Ø­áŒŠá(p_i45166_1_.É * 256.0f / 360.0f);
        this.áˆºÑ¢Õ = p_i45166_2_;
        this.ÂµÈ = p_i45166_3_;
        if (p_i45166_3_ > 0) {
            double var4 = p_i45166_1_.ÇŽÉ;
            double var5 = p_i45166_1_.ˆá;
            double var6 = p_i45166_1_.ÇŽÕ;
            final double var7 = 3.9;
            if (var4 < -var7) {
                var4 = -var7;
            }
            if (var5 < -var7) {
                var5 = -var7;
            }
            if (var6 < -var7) {
                var6 = -var7;
            }
            if (var4 > var7) {
                var4 = var7;
            }
            if (var5 > var7) {
                var5 = var7;
            }
            if (var6 > var7) {
                var6 = var7;
            }
            this.Âµá€ = (int)(var4 * 8000.0);
            this.Ó = (int)(var5 * 8000.0);
            this.à = (int)(var6 * 8000.0);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.áˆºÑ¢Õ = data.readByte();
        this.Â = data.readInt();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
        this.Ø = data.readByte();
        this.áŒŠÆ = data.readByte();
        this.ÂµÈ = data.readInt();
        if (this.ÂµÈ > 0) {
            this.Âµá€ = data.readShort();
            this.Ó = data.readShort();
            this.à = data.readShort();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.áˆºÑ¢Õ);
        data.writeInt(this.Â);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
        data.writeByte(this.Ø);
        data.writeByte(this.áŒŠÆ);
        data.writeInt(this.ÂµÈ);
        if (this.ÂµÈ > 0) {
            data.writeShort(this.Âµá€);
            data.writeShort(this.Ó);
            data.writeShort(this.à);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    public int Ó() {
        return this.Ó;
    }
    
    public int à() {
        return this.à;
    }
    
    public int Ø() {
        return this.Ø;
    }
    
    public int áŒŠÆ() {
        return this.áŒŠÆ;
    }
    
    public int áˆºÑ¢Õ() {
        return this.áˆºÑ¢Õ;
    }
    
    public int ÂµÈ() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final int p_148996_1_) {
        this.Â = p_148996_1_;
    }
    
    public void Â(final int p_148995_1_) {
        this.Ý = p_148995_1_;
    }
    
    public void Ý(final int p_149005_1_) {
        this.Ø­áŒŠá = p_149005_1_;
    }
    
    public void Ø­áŒŠá(final int p_149003_1_) {
        this.Âµá€ = p_149003_1_;
    }
    
    public void Âµá€(final int p_149000_1_) {
        this.Ó = p_149000_1_;
    }
    
    public void Ó(final int p_149007_1_) {
        this.à = p_149007_1_;
    }
    
    public void à(final int p_149002_1_) {
        this.ÂµÈ = p_149002_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
