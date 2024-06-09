package HORIZON-6-0-SKIDPROTECTION;

import java.security.PrivateKey;
import java.io.IOException;
import java.security.Key;
import java.security.PublicKey;
import javax.crypto.SecretKey;

public class C01PacketEncryptionResponse implements Packet
{
    private byte[] HorizonCode_Horizon_È;
    private byte[] Â;
    private static final String Ý = "CL_00001380";
    
    public C01PacketEncryptionResponse() {
        this.HorizonCode_Horizon_È = new byte[0];
        this.Â = new byte[0];
    }
    
    public C01PacketEncryptionResponse(final SecretKey p_i45271_1_, final PublicKey p_i45271_2_, final byte[] p_i45271_3_) {
        this.HorizonCode_Horizon_È = new byte[0];
        this.Â = new byte[0];
        this.HorizonCode_Horizon_È = CryptManager.HorizonCode_Horizon_È(p_i45271_2_, p_i45271_1_.getEncoded());
        this.Â = CryptManager.HorizonCode_Horizon_È(p_i45271_2_, p_i45271_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.HorizonCode_Horizon_È();
        this.Â = data.HorizonCode_Horizon_È();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerLoginServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public SecretKey HorizonCode_Horizon_È(final PrivateKey key) {
        return CryptManager.HorizonCode_Horizon_È(key, this.HorizonCode_Horizon_È);
    }
    
    public byte[] Â(final PrivateKey p_149299_1_) {
        return (p_149299_1_ == null) ? this.Â : CryptManager.Â(p_149299_1_, this.Â);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerLoginServer)handler);
    }
}
