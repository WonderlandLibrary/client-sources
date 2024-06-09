package HORIZON-6-0-SKIDPROTECTION;

public class EventPacketSend extends Event
{
    private Packet HorizonCode_Horizon_È;
    
    public Event HorizonCode_Horizon_È(final Packet packet) {
        this.HorizonCode_Horizon_È = packet;
        return super.Â();
    }
    
    public Packet Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void Â(final Packet packet) {
        this.HorizonCode_Horizon_È = packet;
    }
}
