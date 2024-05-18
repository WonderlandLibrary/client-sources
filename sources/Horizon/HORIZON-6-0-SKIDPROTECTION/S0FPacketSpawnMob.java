package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;

public class S0FPacketSpawnMob implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private int Ø;
    private byte áŒŠÆ;
    private byte áˆºÑ¢Õ;
    private byte ÂµÈ;
    private DataWatcher á;
    private List ˆÏ­;
    private static final String £á = "CL_00001279";
    
    public S0FPacketSpawnMob() {
    }
    
    public S0FPacketSpawnMob(final EntityLivingBase p_i45192_1_) {
        this.HorizonCode_Horizon_È = p_i45192_1_.ˆá();
        this.Â = (byte)EntityList.HorizonCode_Horizon_È(p_i45192_1_);
        this.Ý = MathHelper.Ý(p_i45192_1_.ŒÏ * 32.0);
        this.Ø­áŒŠá = MathHelper.Ý(p_i45192_1_.Çªà¢ * 32.0);
        this.Âµá€ = MathHelper.Ý(p_i45192_1_.Ê * 32.0);
        this.áŒŠÆ = (byte)(p_i45192_1_.É * 256.0f / 360.0f);
        this.áˆºÑ¢Õ = (byte)(p_i45192_1_.áƒ * 256.0f / 360.0f);
        this.ÂµÈ = (byte)(p_i45192_1_.ÂµÕ * 256.0f / 360.0f);
        final double var2 = 3.9;
        double var3 = p_i45192_1_.ÇŽÉ;
        double var4 = p_i45192_1_.ˆá;
        double var5 = p_i45192_1_.ÇŽÕ;
        if (var3 < -var2) {
            var3 = -var2;
        }
        if (var4 < -var2) {
            var4 = -var2;
        }
        if (var5 < -var2) {
            var5 = -var2;
        }
        if (var3 > var2) {
            var3 = var2;
        }
        if (var4 > var2) {
            var4 = var2;
        }
        if (var5 > var2) {
            var5 = var2;
        }
        this.Ó = (int)(var3 * 8000.0);
        this.à = (int)(var4 * 8000.0);
        this.Ø = (int)(var5 * 8000.0);
        this.á = p_i45192_1_.É();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = (data.readByte() & 0xFF);
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
        this.Âµá€ = data.readInt();
        this.áŒŠÆ = data.readByte();
        this.áˆºÑ¢Õ = data.readByte();
        this.ÂµÈ = data.readByte();
        this.Ó = data.readShort();
        this.à = data.readShort();
        this.Ø = data.readShort();
        this.ˆÏ­ = DataWatcher.Â(data);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â & 0xFF);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
        data.writeInt(this.Âµá€);
        data.writeByte(this.áŒŠÆ);
        data.writeByte(this.áˆºÑ¢Õ);
        data.writeByte(this.ÂµÈ);
        data.writeShort(this.Ó);
        data.writeShort(this.à);
        data.writeShort(this.Ø);
        this.á.HorizonCode_Horizon_È(data);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180721_1_) {
        p_180721_1_.HorizonCode_Horizon_È(this);
    }
    
    public List HorizonCode_Horizon_È() {
        if (this.ˆÏ­ == null) {
            this.ˆÏ­ = this.á.Ý();
        }
        return this.ˆÏ­;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public int Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public int Ó() {
        return this.Âµá€;
    }
    
    public int à() {
        return this.Ó;
    }
    
    public int Ø() {
        return this.à;
    }
    
    public int áŒŠÆ() {
        return this.Ø;
    }
    
    public byte áˆºÑ¢Õ() {
        return this.áŒŠÆ;
    }
    
    public byte ÂµÈ() {
        return this.áˆºÑ¢Õ;
    }
    
    public byte á() {
        return this.ÂµÈ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
