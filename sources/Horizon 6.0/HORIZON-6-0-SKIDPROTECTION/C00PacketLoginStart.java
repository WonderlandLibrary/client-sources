package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class C00PacketLoginStart implements Packet
{
    private GameProfile HorizonCode_Horizon_È;
    private static final String Â = "CL_00001379";
    
    public C00PacketLoginStart() {
    }
    
    public C00PacketLoginStart(final GameProfile profileIn) {
        this.HorizonCode_Horizon_È = profileIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = new GameProfile((UUID)null, data.Ý(16));
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.getName());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginServer p_180773_1_) {
        p_180773_1_.HorizonCode_Horizon_È(this);
    }
    
    public GameProfile HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginServer)handler);
    }
}
