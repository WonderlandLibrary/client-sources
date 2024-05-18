package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import com.google.common.collect.Lists;
import java.util.List;

public class S27PacketExplosion implements Packet
{
    private double HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private float Ø­áŒŠá;
    private List Âµá€;
    private float Ó;
    private float à;
    private float Ø;
    private static final String áŒŠÆ = "CL_00001300";
    
    public S27PacketExplosion() {
    }
    
    public S27PacketExplosion(final double p_i45193_1_, final double p_i45193_3_, final double p_i45193_5_, final float p_i45193_7_, final List p_i45193_8_, final Vec3 p_i45193_9_) {
        this.HorizonCode_Horizon_È = p_i45193_1_;
        this.Â = p_i45193_3_;
        this.Ý = p_i45193_5_;
        this.Ø­áŒŠá = p_i45193_7_;
        this.Âµá€ = Lists.newArrayList((Iterable)p_i45193_8_);
        if (p_i45193_9_ != null) {
            this.Ó = (float)p_i45193_9_.HorizonCode_Horizon_È;
            this.à = (float)p_i45193_9_.Â;
            this.Ø = (float)p_i45193_9_.Ý;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readFloat();
        this.Â = data.readFloat();
        this.Ý = data.readFloat();
        this.Ø­áŒŠá = data.readFloat();
        final int var2 = data.readInt();
        this.Âµá€ = Lists.newArrayListWithCapacity(var2);
        final int var3 = (int)this.HorizonCode_Horizon_È;
        final int var4 = (int)this.Â;
        final int var5 = (int)this.Ý;
        for (int var6 = 0; var6 < var2; ++var6) {
            final int var7 = data.readByte() + var3;
            final int var8 = data.readByte() + var4;
            final int var9 = data.readByte() + var5;
            this.Âµá€.add(new BlockPos(var7, var8, var9));
        }
        this.Ó = data.readFloat();
        this.à = data.readFloat();
        this.Ø = data.readFloat();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeFloat((float)this.HorizonCode_Horizon_È);
        data.writeFloat((float)this.Â);
        data.writeFloat((float)this.Ý);
        data.writeFloat(this.Ø­áŒŠá);
        data.writeInt(this.Âµá€.size());
        final int var2 = (int)this.HorizonCode_Horizon_È;
        final int var3 = (int)this.Â;
        final int var4 = (int)this.Ý;
        for (final BlockPos var6 : this.Âµá€) {
            final int var7 = var6.HorizonCode_Horizon_È() - var2;
            final int var8 = var6.Â() - var3;
            final int var9 = var6.Ý() - var4;
            data.writeByte(var7);
            data.writeByte(var8);
            data.writeByte(var9);
        }
        data.writeFloat(this.Ó);
        data.writeFloat(this.à);
        data.writeFloat(this.Ø);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    public float Â() {
        return this.à;
    }
    
    public float Ý() {
        return this.Ø;
    }
    
    public double Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Âµá€() {
        return this.Â;
    }
    
    public double Ó() {
        return this.Ý;
    }
    
    public float à() {
        return this.Ø­áŒŠá;
    }
    
    public List Ø() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
