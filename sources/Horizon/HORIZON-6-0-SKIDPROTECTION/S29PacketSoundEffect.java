package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class S29PacketSoundEffect implements Packet
{
    private String HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private float Âµá€;
    private int Ó;
    private static final String à = "CL_00001309";
    
    public S29PacketSoundEffect() {
        this.Ý = Integer.MAX_VALUE;
    }
    
    public S29PacketSoundEffect(final String p_i45200_1_, final double p_i45200_2_, final double p_i45200_4_, final double p_i45200_6_, final float p_i45200_8_, float p_i45200_9_) {
        this.Ý = Integer.MAX_VALUE;
        Validate.notNull((Object)p_i45200_1_, "name", new Object[0]);
        this.HorizonCode_Horizon_È = p_i45200_1_;
        this.Â = (int)(p_i45200_2_ * 8.0);
        this.Ý = (int)(p_i45200_4_ * 8.0);
        this.Ø­áŒŠá = (int)(p_i45200_6_ * 8.0);
        this.Âµá€ = p_i45200_8_;
        this.Ó = (int)(p_i45200_9_ * 63.0f);
        p_i45200_9_ = MathHelper.HorizonCode_Horizon_È(p_i45200_9_, 0.0f, 255.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(256);
        this.Â = data.readInt();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readInt();
        this.Âµá€ = data.readFloat();
        this.Ó = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeInt(this.Â);
        data.writeInt(this.Ý);
        data.writeInt(this.Ø­áŒŠá);
        data.writeFloat(this.Âµá€);
        data.writeByte(this.Ó);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Â() {
        return this.Â / 8.0f;
    }
    
    public double Ý() {
        return this.Ý / 8.0f;
    }
    
    public double Ø­áŒŠá() {
        return this.Ø­áŒŠá / 8.0f;
    }
    
    public float Âµá€() {
        return this.Âµá€;
    }
    
    public float Ó() {
        return this.Ó / 63.0f;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
