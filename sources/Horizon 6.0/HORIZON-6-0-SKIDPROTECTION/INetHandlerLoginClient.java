package HORIZON-6-0-SKIDPROTECTION;

public interface INetHandlerLoginClient extends INetHandler
{
    void HorizonCode_Horizon_È(final S01PacketEncryptionRequest p0);
    
    void HorizonCode_Horizon_È(final S02PacketLoginSuccess p0);
    
    void HorizonCode_Horizon_È(final S00PacketDisconnect p0);
    
    void HorizonCode_Horizon_È(final S03PacketEnableCompression p0);
}
