package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -1618884, Â = "Nu the ker.", HorizonCode_Horizon_È = "IDNuker")
public class IDNuker extends Mod
{
    Block Ý;
    public TimeHelper Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private double Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private BlockPos ÂµÈ;
    
    public IDNuker() {
        this.Ø­áŒŠá = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (this.Â.áŒŠÆ == null) {
            return;
        }
        this.Âµá€ = -5;
        while (this.Âµá€ < 6) {
            this.Ó = -5;
            while (this.Ó < 6) {
                this.à = 5;
                while (this.à > -5) {
                    final double x = this.Â.á.ŒÏ + this.Âµá€;
                    final double y = this.Â.á.Çªà¢ + this.à;
                    final double z = this.Â.á.Ê + this.Ó;
                    this.Ø = x;
                    this.áˆºÑ¢Õ = y;
                    this.áŒŠÆ = z;
                    final int id = Block.HorizonCode_Horizon_È(this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý());
                    if (id == Horizon.áŒŠÆ) {
                        this.HorizonCode_Horizon_È(new BlockPos(x, y, z));
                        break;
                    }
                    --this.à;
                }
                ++this.Ó;
            }
            ++this.Âµá€;
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos) {
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, pos, EnumFacing.Â));
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý, pos, EnumFacing.Â));
    }
}
