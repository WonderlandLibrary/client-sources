package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MINIGAMES, Ý = 0, Â = "Fucks Blocks at the HiveMC.net Server.", HorizonCode_Horizon_È = "BreakingBlocks")
public class BreakingBlocks extends Mod
{
    Block Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private double à;
    private double Ø;
    private double áŒŠÆ;
    
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
                    this.à = x;
                    this.áŒŠÆ = y;
                    this.Ø = z;
                    final int id = Block.HorizonCode_Horizon_È(this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý());
                    if (id == 1 || id == 2 || id == 3 || id == 4 || id == 14 || id == 15 || id == 16 || id == 22 || id == 8 || id == 9 || id == 10 || id == 11 || id == 13 || id == 24) {
                        this.HorizonCode_Horizon_È(new BlockPos(x, y, z));
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
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, pos, EnumFacing.Â));
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý, pos, EnumFacing.Â));
    }
}
