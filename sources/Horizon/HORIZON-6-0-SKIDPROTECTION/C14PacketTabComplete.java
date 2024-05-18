package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;

public class C14PacketTabComplete implements Packet
{
    private String HorizonCode_Horizon_È;
    private BlockPos Â;
    private static final String Ý = "CL_00001346";
    
    public C14PacketTabComplete() {
    }
    
    public C14PacketTabComplete(final String msg) {
        this(msg, null);
    }
    
    public C14PacketTabComplete(final String p_i45948_1_, final BlockPos p_i45948_2_) {
        this.HorizonCode_Horizon_È = p_i45948_1_;
        this.Â = p_i45948_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(32767);
        final boolean var2 = data.readBoolean();
        if (var2) {
            this.Â = data.Â();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(StringUtils.substring(this.HorizonCode_Horizon_È, 0, 32767));
        final boolean var2 = this.Â != null;
        data.writeBoolean(var2);
        if (var2) {
            data.HorizonCode_Horizon_È(this.Â);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180756_1_) {
        p_180756_1_.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public BlockPos Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
