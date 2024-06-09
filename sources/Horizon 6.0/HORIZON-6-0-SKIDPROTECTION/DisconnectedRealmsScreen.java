package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class DisconnectedRealmsScreen extends RealmsScreen
{
    private String £á;
    private IChatComponent Å;
    private List £à;
    private final RealmsScreen µà;
    private static final String ˆà = "CL_00002145";
    
    public DisconnectedRealmsScreen(final RealmsScreen p_i45742_1_, final String p_i45742_2_, final IChatComponent p_i45742_3_) {
        this.µà = p_i45742_1_;
        this.£á = RealmsScreen.Ý(p_i45742_2_);
        this.Å = p_i45742_3_;
    }
    
    @Override
    public void init() {
        this.à();
        this.HorizonCode_Horizon_È(RealmsScreen.HorizonCode_Horizon_È(0, this.Ø­áŒŠá() / 2 - 100, this.Âµá€() / 4 + 120 + 12, RealmsScreen.Ý("gui.back")));
        this.£à = this.HorizonCode_Horizon_È(this.Å.áŒŠÆ(), this.Ø­áŒŠá() - 50);
    }
    
    @Override
    public void keyPressed(final char p_keyPressed_1_, final int p_keyPressed_2_) {
        if (p_keyPressed_2_ == 1) {
            Realms.HorizonCode_Horizon_È(this.µà);
        }
    }
    
    @Override
    public void buttonClicked(final RealmsButton p_buttonClicked_1_) {
        if (p_buttonClicked_1_.Â() == 0) {
            Realms.HorizonCode_Horizon_È(this.µà);
        }
    }
    
    @Override
    public void render(final int p_render_1_, final int p_render_2_, final float p_render_3_) {
        this.Â();
        this.HorizonCode_Horizon_È(this.£á, this.Ø­áŒŠá() / 2, this.Âµá€() / 2 - 50, 11184810);
        int var4 = this.Âµá€() / 2 - 30;
        if (this.£à != null) {
            for (final String var6 : this.£à) {
                this.HorizonCode_Horizon_È(var6, this.Ø­áŒŠá() / 2, var4, 16777215);
                var4 += this.Ó();
            }
        }
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
}
