package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityDaylightDetector extends TileEntity implements IUpdatePlayerListBox
{
    private static final String Âµá€ = "CL_00000350";
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È != null && !this.HorizonCode_Horizon_È.ŠÄ && this.HorizonCode_Horizon_È.Šáƒ() % 20L == 0L) {
            this.Ø­áŒŠá = this.ˆÏ­();
            if (this.Ø­áŒŠá instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector)this.Ø­áŒŠá).áŒŠÆ(this.HorizonCode_Horizon_È, this.Â);
            }
        }
    }
}
