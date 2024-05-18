package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class S20PacketEntityProperties implements Packet
{
    private int HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00001341";
    
    public S20PacketEntityProperties() {
        this.Â = Lists.newArrayList();
    }
    
    public S20PacketEntityProperties(final int p_i45236_1_, final Collection p_i45236_2_) {
        this.Â = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i45236_1_;
        for (final IAttributeInstance var4 : p_i45236_2_) {
            this.Â.add(new HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È().HorizonCode_Horizon_È(), var4.Â(), var4.Ý()));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        for (int var2 = data.readInt(), var3 = 0; var3 < var2; ++var3) {
            final String var4 = data.Ý(64);
            final double var5 = data.readDouble();
            final ArrayList var6 = Lists.newArrayList();
            for (int var7 = data.Ø­áŒŠá(), var8 = 0; var8 < var7; ++var8) {
                final UUID var9 = data.Ó();
                var6.add(new AttributeModifier(var9, "Unknown synced attribute modifier", data.readDouble(), data.readByte()));
            }
            this.Â.add(new HorizonCode_Horizon_È(var4, var5, var6));
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeInt(this.Â.size());
        for (final HorizonCode_Horizon_È var3 : this.Â) {
            data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È());
            data.writeDouble(var3.Â());
            data.Â(var3.Ý().size());
            for (final AttributeModifier var5 : var3.Ý()) {
                data.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È());
                data.writeDouble(var5.Ø­áŒŠá());
                data.writeByte(var5.Ý());
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180754_1_) {
        p_180754_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public List Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public class HorizonCode_Horizon_È
    {
        private final String Â;
        private final double Ý;
        private final Collection Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001342";
        
        public HorizonCode_Horizon_È(final String p_i45235_2_, final double p_i45235_3_, final Collection p_i45235_5_) {
            this.Â = p_i45235_2_;
            this.Ý = p_i45235_3_;
            this.Ø­áŒŠá = p_i45235_5_;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public double Â() {
            return this.Ý;
        }
        
        public Collection Ý() {
            return this.Ø­áŒŠá;
        }
    }
}
