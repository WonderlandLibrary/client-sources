package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class S02PacketLoginSuccess implements Packet
{
    private GameProfile HorizonCode_Horizon_È;
    private static final String Â = "CL_00001375";
    
    public S02PacketLoginSuccess() {
    }
    
    public S02PacketLoginSuccess(final GameProfile profileIn) {
        this.HorizonCode_Horizon_È = profileIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        final String var2 = data.Ý(36);
        final String var3 = data.Ý(16);
        final UUID var4 = UUID.fromString(var2);
        this.HorizonCode_Horizon_È = new GameProfile(var4, var3);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        final UUID var2 = this.HorizonCode_Horizon_È.getId();
        data.HorizonCode_Horizon_È((var2 == null) ? "" : var2.toString());
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.getName());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginClient p_180771_1_) {
        p_180771_1_.HorizonCode_Horizon_È(this);
    }
    
    public GameProfile HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginClient)handler);
    }
}
