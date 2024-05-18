package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiYesNo extends GuiScreen
{
    protected GuiYesNoCallback HorizonCode_Horizon_È;
    protected String Â;
    private String Ó;
    private final List à;
    protected String Ý;
    protected String Ø­áŒŠá;
    protected int Âµá€;
    private int Ø;
    private static final String áŒŠÆ = "CL_00000684";
    
    public GuiYesNo(final GuiYesNoCallback p_i1082_1_, final String p_i1082_2_, final String p_i1082_3_, final int p_i1082_4_) {
        this.à = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1082_1_;
        this.Â = p_i1082_2_;
        this.Ó = p_i1082_3_;
        this.Âµá€ = p_i1082_4_;
        this.Ý = I18n.HorizonCode_Horizon_È("gui.yes", new Object[0]);
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È("gui.no", new Object[0]);
    }
    
    public GuiYesNo(final GuiYesNoCallback p_i1083_1_, final String p_i1083_2_, final String p_i1083_3_, final String p_i1083_4_, final String p_i1083_5_, final int p_i1083_6_) {
        this.à = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1083_1_;
        this.Â = p_i1083_2_;
        this.Ó = p_i1083_3_;
        this.Ý = p_i1083_4_;
        this.Ø­áŒŠá = p_i1083_5_;
        this.Âµá€ = p_i1083_6_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiOptionButton(0, GuiYesNo.Çªà¢ / 2 - 155, GuiYesNo.Ê / 6 + 96, this.Ý));
        this.ÇŽÉ.add(new GuiOptionButton(1, GuiYesNo.Çªà¢ / 2 - 155 + 160, GuiYesNo.Ê / 6 + 96, this.Ø­áŒŠá));
        this.à.clear();
        this.à.addAll(this.É.Ý(this.Ó, GuiYesNo.Çªà¢ - 50));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à == 0, this.Âµá€);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, GuiScreen.Çªà¢, (float)GuiScreen.Ê, -8418163);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiYesNo.Çªà¢ / 2, 70, 16777215);
        int var4 = 90;
        for (final String var6 : this.à) {
            this.HorizonCode_Horizon_È(this.É, var6, GuiYesNo.Çªà¢ / 2, var4, 16777215);
            var4 += this.É.HorizonCode_Horizon_È;
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    public void HorizonCode_Horizon_È(final int p_146350_1_) {
        this.Ø = p_146350_1_;
        for (final GuiButton var3 : this.ÇŽÉ) {
            var3.µà = false;
        }
    }
    
    @Override
    public void Ý() {
        super.Ý();
        final int ø = this.Ø - 1;
        this.Ø = ø;
        if (ø == 0) {
            for (final GuiButton var2 : this.ÇŽÉ) {
                var2.µà = true;
            }
        }
    }
}
