package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Set;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -1, Â = "3spooky5me", HorizonCode_Horizon_È = "Spooky Skin")
public class SpookySkin extends Mod
{
    private Set Ø­áŒŠá;
    Random Ý;
    
    public SpookySkin() {
        this.Ý = new Random();
    }
    
    @Override
    public void á() {
        EnumPlayerModelParts[] values;
        for (int length = (values = EnumPlayerModelParts.values()).length, i = 0; i < length; ++i) {
            final EnumPlayerModelParts part = values[i];
            this.Â.ŠÄ.HorizonCode_Horizon_È(part, true);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        EnumPlayerModelParts[] values;
        for (int length = (values = EnumPlayerModelParts.values()).length, i = 0; i < length; ++i) {
            final EnumPlayerModelParts part = values[i];
            this.Â.ŠÄ.HorizonCode_Horizon_È(part, this.Ý.nextBoolean());
        }
    }
}
