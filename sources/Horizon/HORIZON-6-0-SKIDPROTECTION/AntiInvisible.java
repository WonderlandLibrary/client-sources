package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = 0, Â = "Make the Invisible Players visible.", HorizonCode_Horizon_È = "AntiInvisible")
public class AntiInvisible extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventTick ev) {
        for (final Object o : this.Â.áŒŠÆ.Â) {
            if (o instanceof EntityPlayer) {
                final EntityPlayer p = (EntityPlayer)o;
                if (!p.áŒŠÏ()) {
                    continue;
                }
                p.Ó(false);
            }
        }
    }
}
