package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -7453523, Â = "Automaticly fucks Beds.", HorizonCode_Horizon_È = "BedFucker")
public class BedFucker extends Mod
{
    public TimeHelper Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    
    public BedFucker() {
        this.Ý = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (this.Â.áŒŠÆ == null) {
            return;
        }
        this.Ø­áŒŠá = -5;
        while (this.Ø­áŒŠá < 6) {
            this.Âµá€ = -5;
            while (this.Âµá€ < 6) {
                this.Ó = 5;
                while (this.Ó > -5) {
                    final double x = this.Â.á.ŒÏ + this.Ø­áŒŠá;
                    final double y = this.Â.á.Çªà¢ + this.Ó;
                    final double z = this.Â.á.Ê + this.Âµá€;
                    final int id = Block.HorizonCode_Horizon_È(this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý());
                    if (id == 26 && this.Ý.Â(5L) && this.Â.á.Â(new BlockPos(x, y, z)) <= 8.4) {
                        this.HorizonCode_Horizon_È(new BlockPos(x, y, z));
                        this.Ý.Ø­áŒŠá();
                        break;
                    }
                    --this.Ó;
                }
                ++this.Âµá€;
            }
            ++this.Ø­áŒŠá;
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos) {
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, pos, EnumFacing.Â));
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý, pos, EnumFacing.Â));
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
    }
}
