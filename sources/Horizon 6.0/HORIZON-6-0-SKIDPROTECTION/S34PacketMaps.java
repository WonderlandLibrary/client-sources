package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Collection;

public class S34PacketMaps implements Packet
{
    private int HorizonCode_Horizon_È;
    private byte Â;
    private Vec4b[] Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private byte[] Ø;
    private static final String áŒŠÆ = "CL_00001311";
    
    public S34PacketMaps() {
    }
    
    public S34PacketMaps(final int p_i45975_1_, final byte p_i45975_2_, final Collection p_i45975_3_, final byte[] p_i45975_4_, final int p_i45975_5_, final int p_i45975_6_, final int p_i45975_7_, final int p_i45975_8_) {
        this.HorizonCode_Horizon_È = p_i45975_1_;
        this.Â = p_i45975_2_;
        this.Ý = p_i45975_3_.toArray(new Vec4b[p_i45975_3_.size()]);
        this.Ø­áŒŠá = p_i45975_5_;
        this.Âµá€ = p_i45975_6_;
        this.Ó = p_i45975_7_;
        this.à = p_i45975_8_;
        this.Ø = new byte[p_i45975_7_ * p_i45975_8_];
        for (int var9 = 0; var9 < p_i45975_7_; ++var9) {
            for (int var10 = 0; var10 < p_i45975_8_; ++var10) {
                this.Ø[var9 + var10 * p_i45975_7_] = p_i45975_4_[p_i45975_5_ + var9 + (p_i45975_6_ + var10) * 128];
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readByte();
        this.Ý = new Vec4b[data.Ø­áŒŠá()];
        for (int var2 = 0; var2 < this.Ý.length; ++var2) {
            final short var3 = data.readByte();
            this.Ý[var2] = new Vec4b((byte)(var3 >> 4 & 0xF), data.readByte(), data.readByte(), (byte)(var3 & 0xF));
        }
        this.Ó = data.readUnsignedByte();
        if (this.Ó > 0) {
            this.à = data.readUnsignedByte();
            this.Ø­áŒŠá = data.readUnsignedByte();
            this.Âµá€ = data.readUnsignedByte();
            this.Ø = data.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
        data.Â(this.Ý.length);
        for (final Vec4b var5 : this.Ý) {
            data.writeByte((var5.HorizonCode_Horizon_È() & 0xF) << 4 | (var5.Ø­áŒŠá() & 0xF));
            data.writeByte(var5.Â());
            data.writeByte(var5.Ý());
        }
        data.writeByte(this.Ó);
        if (this.Ó > 0) {
            data.writeByte(this.à);
            data.writeByte(this.Ø­áŒŠá);
            data.writeByte(this.Âµá€);
            data.HorizonCode_Horizon_È(this.Ø);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180741_1_) {
        p_180741_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final MapData p_179734_1_) {
        p_179734_1_.Âµá€ = this.Â;
        p_179734_1_.Ø.clear();
        for (int var2 = 0; var2 < this.Ý.length; ++var2) {
            final Vec4b var3 = this.Ý[var2];
            p_179734_1_.Ø.put("icon-" + var2, var3);
        }
        for (int var2 = 0; var2 < this.Ó; ++var2) {
            for (int var4 = 0; var4 < this.à; ++var4) {
                p_179734_1_.Ó[this.Ø­áŒŠá + var2 + (this.Âµá€ + var4) * 128] = this.Ø[var2 + var4 * this.Ó];
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
