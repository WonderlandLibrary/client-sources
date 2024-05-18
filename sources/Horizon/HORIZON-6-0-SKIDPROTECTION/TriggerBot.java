package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;
import java.util.Random;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = 0, Â = "Trigger to the Bot.", HorizonCode_Horizon_È = "TriggerBot")
public class TriggerBot extends Mod
{
    TimeHelper Ý;
    Random Ø­áŒŠá;
    private long Âµá€;
    
    public TriggerBot() {
        this.Ý = new TimeHelper();
        this.Ø­áŒŠá = new Random();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        final boolean wassprint = this.Â.á.ÇªÂµÕ();
        if (this.Â.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Ý && Mouse.isButtonDown(0) && this.Â.¥Æ == null) {
            final Entity entity = this.Â.áŒŠà.Ø­áŒŠá;
            if (entity instanceof EntityLivingBase || (entity instanceof EntityOtherPlayerMP && entity.Œ() && !this.Â.á.Ñ¢Ó() && !this.Â.á.ˆáŠ && !entity.ˆáŠ)) {
                this.Âµá€ = this.HorizonCode_Horizon_È(100, 140);
                if (this.Ý.Â(this.Âµá€)) {
                    this.Â.á.b_();
                    this.Â.á.Â(wassprint);
                    this.Â.Âµá€.HorizonCode_Horizon_È(this.Â.á, entity);
                    this.Ý.Ø­áŒŠá();
                }
            }
        }
    }
    
    public int HorizonCode_Horizon_È(final int min, final int max) {
        return this.Ø­áŒŠá.nextInt(max - min + 1) + max;
    }
}
