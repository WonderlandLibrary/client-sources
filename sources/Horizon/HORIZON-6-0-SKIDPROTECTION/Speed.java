package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Walk faster than Haze or Andrew.", HorizonCode_Horizon_È = "Speed")
public class Speed extends Mod
{
    private int Ø­áŒŠá;
    private int Âµá€;
    int Ý;
    private int Ó;
    
    public Speed() {
        this.Ý = 0;
        this.HorizonCode_Horizon_È = "normal";
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventTick event) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (this.HorizonCode_Horizon_È.equalsIgnoreCase("sprintjump")) {
            if (this.Â.á.ÇªÂµÕ() && !this.Â.ŠÄ.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá() && !this.Â.á.¥à && (this.Â.á.Ï­áˆºÓ != 0.0f || this.Â.á.£áƒ != 0.0f) && !this.Â.á.Çªà¢()) {
                if (!this.Â.á.Âµà) {
                    if (this.Â.á.ŠÂµà) {
                        this.Â.á.ˆá = -0.053;
                    }
                    this.Â.á.Â(false);
                    this.Â.Ø.Ø­áŒŠá = 1.15f;
                }
                if (this.Â.á.Âµà) {
                    this.Â.á.ŠÏ();
                    this.Â.á.Â(false);
                    this.Â.á.ˆá = 0.033;
                    this.Â.Ø.Ø­áŒŠá = 1.0f;
                }
            }
        }
        else if (Keyboard.isKeyDown(17) || Keyboard.isKeyDown(31) || Keyboard.isKeyDown(30) || Keyboard.isKeyDown(32)) {
            final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
            final Jesus j = (Jesus)ModuleManager.HorizonCode_Horizon_È(Jesus.class);
            if (this.Â.á.ŠÂµà && !this.Â.á.Çªà¢() && !this.Â.á.i_()) {
                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                final Step step = (Step)ModuleManager.HorizonCode_Horizon_È(Step.class);
                if (step.áˆºÑ¢Õ() && step.Ý) {
                    return;
                }
                ++this.Ó;
                this.Ó %= 2;
                if (this.Ó == 1) {
                    final EntityPlayerSPOverwrite á = this.Â.á;
                    á.ÇŽÉ *= (j.Å() ? 1.8 : Horizon.Õ);
                    final EntityPlayerSPOverwrite á2 = this.Â.á;
                    á2.ÇŽÕ *= (j.Å() ? 1.8 : Horizon.Õ);
                }
                else {
                    final EntityPlayerSPOverwrite á3 = this.Â.á;
                    á3.ÇŽÉ /= 1.5;
                    final EntityPlayerSPOverwrite á4 = this.Â.á;
                    á4.ÇŽÕ /= 1.5;
                }
                final EntityPlayerSPOverwrite á5 = this.Â.á;
                á5.£áƒ *= 0.0f;
                this.Â.á.ˆá = 0.001;
            }
            this.Â.á.áŒŠÏ = false;
        }
    }
    
    public boolean HorizonCode_Horizon_È(final EventMovementSpeed e) {
        final boolean moving = this.Â.á.Â.Â != 0.0f || this.Â.á.Â.HorizonCode_Horizon_È != 0.0f;
        return !this.Â.á.£ÂµÄ() && !BlockHelper_118352462.HorizonCode_Horizon_È() && !BlockHelper_118352462.Ø­áŒŠá() && !this.Â.á.¥à && !BlockHelper_118352462.Â() && !BlockHelper_118352462.Ý() && !this.Â.á.Çªà¢() && this.Â.á.ŠÂµà && moving;
    }
    
    @Override
    public void á() {
    }
}
