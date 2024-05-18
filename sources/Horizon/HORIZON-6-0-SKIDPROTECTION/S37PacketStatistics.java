package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import com.google.common.collect.Maps;
import java.util.Map;

public class S37PacketStatistics implements Packet
{
    private Map HorizonCode_Horizon_È;
    private static final String Â = "CL_00001283";
    
    public S37PacketStatistics() {
    }
    
    public S37PacketStatistics(final Map p_i45173_1_) {
        this.HorizonCode_Horizon_È = p_i45173_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        final int var2 = data.Ø­áŒŠá();
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        for (int var3 = 0; var3 < var2; ++var3) {
            final StatBase var4 = StatList.HorizonCode_Horizon_È(data.Ý(32767));
            final int var5 = data.Ø­áŒŠá();
            if (var4 != null) {
                this.HorizonCode_Horizon_È.put(var4, var5);
            }
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È.size());
        for (final Map.Entry var3 : this.HorizonCode_Horizon_È.entrySet()) {
            data.HorizonCode_Horizon_È(var3.getKey().à);
            data.Â(var3.getValue());
        }
    }
    
    public Map HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
