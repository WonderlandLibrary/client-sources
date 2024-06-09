package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class S0CPacketSpawnPlayer implements Packet
{
    private int HorizonCode_Horizon_È;
    private UUID Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private byte Ó;
    private byte à;
    private int Ø;
    private DataWatcher áŒŠÆ;
    private List áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001281";
    
    public S0CPacketSpawnPlayer() {
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer p_i45171_1_) {
        this.HorizonCode_Horizon_È = p_i45171_1_.ˆá();
        this.Â = p_i45171_1_.áˆºà().getId();
        this.Ý = MathHelper.Ý(p_i45171_1_.ŒÏ * 32.0);
        this.Ø­áŒŠá = MathHelper.Ý(p_i45171_1_.Çªà¢ * 32.0);
        this.Âµá€ = MathHelper.Ý(p_i45171_1_.Ê * 32.0);
        this.Ó = (byte)(p_i45171_1_.É * 256.0f / 360.0f);
        this.à = (byte)(p_i45171_1_.áƒ * 256.0f / 360.0f);
        final ItemStack var2 = p_i45171_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        this.Ø = ((var2 == null) ? 0 : Item_1028566121.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È()));
        this.áŒŠÆ = p_i45171_1_.É();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.Ó();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
        this.Âµá€ = data.readInt();
        this.Ó = data.readByte();
        this.à = data.readByte();
        this.Ø = data.readShort();
        this.áˆºÑ¢Õ = DataWatcher.Â(data);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
        data.writeInt(this.Âµá€);
        data.writeByte(this.Ó);
        data.writeByte(this.à);
        data.writeShort(this.Ø);
        this.áŒŠÆ.HorizonCode_Horizon_È(data);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public List HorizonCode_Horizon_È() {
        if (this.áˆºÑ¢Õ == null) {
            this.áˆºÑ¢Õ = this.áŒŠÆ.Ý();
        }
        return this.áˆºÑ¢Õ;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public UUID Ý() {
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
    
    public byte à() {
        return this.Ó;
    }
    
    public byte Ø() {
        return this.à;
    }
    
    public int áŒŠÆ() {
        return this.Ø;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
