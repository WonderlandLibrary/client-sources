package HORIZON-6-0-SKIDPROTECTION;

public final class EventPacketSent extends Event implements Cancellable
{
    private boolean HorizonCode_Horizon_È;
    private Packet Â;
    
    public EventPacketSent(final Packet packet) {
        this.Â = packet;
    }
    
    public Packet Ý() {
        return this.Â;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean cancel) {
        this.HorizonCode_Horizon_È = cancel;
    }
    
    public void HorizonCode_Horizon_È(final Packet packet) {
        this.Â = packet;
    }
}
