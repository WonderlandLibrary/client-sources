package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings Âµá€;
    private static final String Ó = "CL_00000937";
    
    public MovementInputFromOptions(final GameSettings p_i1237_1_) {
        this.Âµá€ = p_i1237_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = 0.0f;
        this.Â = 0.0f;
        Label_0071: {
            if (!this.Âµá€.ÇªÉ.Ø­áŒŠá()) {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(Inventory.class).áˆºÑ¢Õ() || !Keyboard.isKeyDown(17) || Minecraft.áŒŠà().¥Æ instanceof GuiChat) {
                    break Label_0071;
                }
            }
            ++this.Â;
        }
        Label_0132: {
            if (!this.Âµá€.ÇŽà.Ø­áŒŠá()) {
                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(Inventory.class).áˆºÑ¢Õ() || !Keyboard.isKeyDown(31) || Minecraft.áŒŠà().¥Æ instanceof GuiChat) {
                    break Label_0132;
                }
            }
            --this.Â;
        }
        Label_0193: {
            if (!this.Âµá€.ŠÏ­áˆºá.Ø­áŒŠá()) {
                final ModuleManager áˆºÏ3 = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(Inventory.class).áˆºÑ¢Õ() || !Keyboard.isKeyDown(30) || Minecraft.áŒŠà().¥Æ instanceof GuiChat) {
                    break Label_0193;
                }
            }
            ++this.HorizonCode_Horizon_È;
        }
        Label_0254: {
            if (!this.Âµá€.ŠáˆºÂ.Ø­áŒŠá()) {
                final ModuleManager áˆºÏ4 = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(Inventory.class).áˆºÑ¢Õ() || !Keyboard.isKeyDown(32) || Minecraft.áŒŠà().¥Æ instanceof GuiChat) {
                    break Label_0254;
                }
            }
            --this.HorizonCode_Horizon_È;
        }
        this.Ý = this.Âµá€.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        this.Ø­áŒŠá = this.Âµá€.ŒÂ.Ø­áŒŠá();
        if (this.Ø­áŒŠá) {
            this.HorizonCode_Horizon_È *= 0.3;
            this.Â *= 0.3;
        }
    }
}
