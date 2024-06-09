package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class GuiLabel extends Gui_1808253012
{
    protected int HorizonCode_Horizon_È;
    protected int Â;
    public int Ý;
    public int Ø­áŒŠá;
    private List à;
    public int Âµá€;
    private boolean Ø;
    public boolean Ó;
    private boolean áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private int ˆÏ­;
    private FontRenderer £á;
    private int Å;
    private static final String £à = "CL_00000671";
    
    public GuiLabel(final FontRenderer p_i45540_1_, final int p_i45540_2_, final int p_i45540_3_, final int p_i45540_4_, final int p_i45540_5_, final int p_i45540_6_, final int p_i45540_7_) {
        this.HorizonCode_Horizon_È = 200;
        this.Â = 20;
        this.Ó = true;
        this.£á = p_i45540_1_;
        this.Âµá€ = p_i45540_2_;
        this.Ý = p_i45540_3_;
        this.Ø­áŒŠá = p_i45540_4_;
        this.HorizonCode_Horizon_È = p_i45540_5_;
        this.Â = p_i45540_6_;
        this.à = Lists.newArrayList();
        this.Ø = false;
        this.áŒŠÆ = false;
        this.áˆºÑ¢Õ = p_i45540_7_;
        this.ÂµÈ = -1;
        this.á = -1;
        this.ˆÏ­ = -1;
        this.Å = 0;
    }
    
    public void HorizonCode_Horizon_È(final String p_175202_1_) {
        this.à.add(I18n.HorizonCode_Horizon_È(p_175202_1_, new Object[0]));
    }
    
    public GuiLabel HorizonCode_Horizon_È() {
        this.Ø = true;
        return this;
    }
    
    public void HorizonCode_Horizon_È(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.Ó) {
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            this.Â(mc, mouseX, mouseY);
            final int var4 = this.Ø­áŒŠá + this.Â / 2 + this.Å / 2;
            final int var5 = var4 - this.à.size() * 10 / 2;
            for (int var6 = 0; var6 < this.à.size(); ++var6) {
                if (this.Ø) {
                    this.HorizonCode_Horizon_È(this.£á, this.à.get(var6), this.Ý + this.HorizonCode_Horizon_È / 2, var5 + var6 * 10, this.áˆºÑ¢Õ);
                }
                else {
                    Gui_1808253012.Â(this.£á, this.à.get(var6), this.Ý, var5 + var6 * 10, this.áˆºÑ¢Õ);
                }
            }
        }
    }
    
    protected void Â(final Minecraft mcIn, final int p_146160_2_, final int p_146160_3_) {
        if (this.áŒŠÆ) {
            final int var4 = this.HorizonCode_Horizon_È + this.Å * 2;
            final int var5 = this.Â + this.Å * 2;
            final int var6 = this.Ý - this.Å;
            final int var7 = this.Ø­áŒŠá - this.Å;
            Gui_1808253012.HorizonCode_Horizon_È(var6, var7, var6 + var4, var7 + var5, this.ÂµÈ);
            this.HorizonCode_Horizon_È(var6, var6 + var4, var7, this.á);
            this.HorizonCode_Horizon_È(var6, var6 + var4, var7 + var5, this.ˆÏ­);
            this.Â(var6, var7, var7 + var5, this.á);
            this.Â(var6 + var4, var7, var7 + var5, this.ˆÏ­);
        }
    }
}
