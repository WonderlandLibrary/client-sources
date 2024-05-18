package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

@ModInfo(Ø­áŒŠá = Category.SERVER, Ý = -13330213, Â = "Lagg.", HorizonCode_Horizon_È = "Blink")
public class Blink extends Mod
{
    protected final Queue<Packet> Ý;
    private int Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    
    public Blink() {
        this.Ý = new ConcurrentLinkedQueue<Packet>();
        this.Ø­áŒŠá = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final EntityOtherPlayerMP entity = new EntityOtherPlayerMP(this.Â.áŒŠÆ, new GameProfile(this.Â.á.£áŒŠá(), this.Â.á.l_().v_()));
        this.Â.áŒŠÆ.HorizonCode_Horizon_È(-1337, entity);
        entity.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.à¢.Â, this.Â.á.Ê, this.Â.á.É, this.Â.á.áƒ);
        entity.ˆÏ­();
    }
    
    @Override
    public void á() {
        while (!this.Ý.isEmpty()) {
            this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý.poll());
        }
        this.Ý.clear();
        this.Â.áŒŠÆ.Â(-1337);
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend event) {
        event.HorizonCode_Horizon_È(true);
        this.Ý.add(event.Ý());
    }
}
