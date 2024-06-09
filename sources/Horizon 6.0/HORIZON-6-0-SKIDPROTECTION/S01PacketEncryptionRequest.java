package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.security.PublicKey;

public class S01PacketEncryptionRequest implements Packet
{
    private String HorizonCode_Horizon_È;
    private PublicKey Â;
    private byte[] Ý;
    private static final String Ø­áŒŠá = "CL_00001376";
    
    public S01PacketEncryptionRequest() {
    }
    
    public S01PacketEncryptionRequest(final String serverId, final PublicKey key, final byte[] p_i45268_3_) {
        this.HorizonCode_Horizon_È = serverId;
        this.Â = key;
        this.Ý = p_i45268_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(20);
        this.Â = CryptManager.HorizonCode_Horizon_È(data.HorizonCode_Horizon_È());
        this.Ý = data.HorizonCode_Horizon_È();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â.getEncoded());
        data.HorizonCode_Horizon_È(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public PublicKey Â() {
        return this.Â;
    }
    
    public byte[] Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginClient)handler);
    }
}
