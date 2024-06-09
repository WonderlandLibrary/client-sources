package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -2236974, Â = "Fall without damage.", HorizonCode_Horizon_È = "NoFall")
public class NoFall extends Mod
{
    private float Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private TimeHelper à;
    int Ý;
    
    public NoFall() {
        this.à = new TimeHelper();
        this.Ý = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ó = false;
        this.Ý = 0;
        this.à.Ø­áŒŠá();
        if (this.Â.á.ŠÂµà) {
            if (this.Â.ÇŽÉ()) {
                Utiils.HorizonCode_Horizon_È(1);
            }
            else {
                Utiils.HorizonCode_Horizon_È();
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (this.à.Â(1000L) && !this.Ó && this.Â.á.µà > 0) {
                this.Ó = true;
            }
            if (!this.Å()) {
                this.Ý = 0;
                final Block highBlock = Utiils.HorizonCode_Horizon_È((int)Math.round(this.Â.á.ŒÏ), (int)Math.round(this.Â.á.à¢.Â - 0.5), (int)Math.round(this.Â.á.Ê));
                if (!(highBlock instanceof BlockAir)) {
                    this.Âµá€ = true;
                }
                else {
                    this.Âµá€ = false;
                    this.Ø­áŒŠá = 0.6f;
                }
            }
            else if (this.Âµá€ && this.Å() && this.Ø­áŒŠá >= 0.11) {
                this.Ø­áŒŠá -= 0.005;
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend packet) {
        if (this.à.Â(1000L)) {
            final C03PacketPlayer p = (C03PacketPlayer)packet.Ý();
            p.Â = this.Â.á.Çªà¢ + this.Ø­áŒŠá - this.Ý * 1.0E-13;
            p.Ó = false;
            ++this.Ý;
        }
    }
    
    private boolean Å() {
        final Block block = Utiils.HorizonCode_Horizon_È((int)this.Â.á.ŒÏ, (int)(this.Â.á.à¢.Â - 0.01), (int)this.Â.á.Ê);
        return !(block instanceof BlockAir) && block.£à();
    }
}
