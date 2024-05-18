package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -1, Â = "Climb Ladders faster.", HorizonCode_Horizon_È = "Ladders")
public class Ladders extends Mod
{
    public static boolean Ý;
    
    static {
        Ladders.Ý = false;
    }
    
    public Ladders() {
        this.HorizonCode_Horizon_È = "nonskip";
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final boolean movementInput = this.Â.ŠÄ.ÇªÉ.HorizonCode_Horizon_È || this.Â.ŠÄ.ÇŽà.HorizonCode_Horizon_È || this.Â.ŠÄ.ŠÏ­áˆºá.HorizonCode_Horizon_È || this.Â.ŠÄ.ŠÏ­áˆºá.HorizonCode_Horizon_È;
            final int posX = MathHelper.Ý(this.Â.á.ŒÏ);
            final int minY = MathHelper.Ý(this.Â.á.à¢.Â);
            final int maxY = MathHelper.Ý(this.Â.á.à¢.Â + 1.0);
            final int posZ = MathHelper.Ý(this.Â.á.Ê);
            if (movementInput && this.Â.á.¥à && !this.Â.á.£ÂµÄ()) {
                if (!this.Â.á.i_()) {
                    Block block = Utiils.HorizonCode_Horizon_È(posX, minY, posZ);
                    if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                        block = Utiils.HorizonCode_Horizon_È(posX, maxY, posZ);
                        if (block instanceof BlockLadder || block instanceof BlockVine) {
                            this.Â.á.ˆá = 0.5;
                        }
                    }
                }
                else if (this.Â.á.i_()) {
                    final EntityPlayerSPOverwrite á = this.Â.á;
                    á.ˆá *= 2.44;
                    if (this.HorizonCode_Horizon_È.equals("skip") && this.Â.á.ŠÂµà && this.Å() > 0 && !this.Â.á.Çªà¢()) {
                        this.Â.á.Â(this.Â.á.ŒÏ, ((this.Å() >= 10) ? 9.79 : (this.Å() + 0.99)) + this.Â.á.Çªà¢, this.Â.á.Ê, this.Â.á.É, this.Â.á.áƒ);
                    }
                }
            }
        }
    }
    
    public int Å() {
        int ladders = 0;
        for (int dist = 1; dist < 256; ++dist) {
            final BlockPos bpos = new BlockPos(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + dist, this.Â.á.Ê);
            final Block block = this.Â.áŒŠÆ.Â(bpos).Ý();
            if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                break;
            }
            ++ladders;
        }
        return ladders;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Utiils.HorizonCode_Horizon_È();
    }
}
