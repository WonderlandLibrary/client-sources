package HORIZON-6-0-SKIDPROTECTION;

public class GuiFlatField extends GuiTextField
{
    private int HorizonCode_Horizon_È;
    
    public GuiFlatField(final int p_i45542_1_, final FontRenderer p_i45542_2_, final int p_i45542_3_, final int p_i45542_4_, final int p_i45542_5_, final int p_i45542_6_) {
        super(p_i45542_1_, p_i45542_2_, p_i45542_3_, p_i45542_4_, p_i45542_5_, p_i45542_6_);
        this.HorizonCode_Horizon_È = 101;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.£á()) {
            if (this.áˆºÑ¢Õ()) {
                if (!this.ˆÏ­) {
                    this.HorizonCode_Horizon_È += 11;
                    if (this.HorizonCode_Horizon_È >= 101) {
                        this.HorizonCode_Horizon_È = 101;
                    }
                    Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá - 1, this.Âµá€ + this.à - 1, this.Ø­áŒŠá + this.Ó + 0, this.Âµá€ + this.à + 0, -2130706433);
                    if (this.HorizonCode_Horizon_È < 101) {
                        Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá - 1 + this.HorizonCode_Horizon_È, this.Âµá€ + this.à - 1, this.Ø­áŒŠá + this.Ó + 0 - this.HorizonCode_Horizon_È, this.Âµá€ + this.à + 0, -10245909);
                    }
                }
                else {
                    this.HorizonCode_Horizon_È -= 11;
                    if (this.HorizonCode_Horizon_È <= 0) {
                        this.HorizonCode_Horizon_È = 0;
                    }
                    Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá - 1, this.Âµá€ + this.à - 1, this.Ø­áŒŠá + this.Ó + 0, this.Âµá€ + this.à + 0, -2130706433);
                    if (this.HorizonCode_Horizon_È < 101) {
                        Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá - 1 + this.HorizonCode_Horizon_È, this.Âµá€ + this.à - 1, this.Ø­áŒŠá + this.Ó + 0 - this.HorizonCode_Horizon_È, this.Âµá€ + this.à + 0, -10245909);
                    }
                }
            }
            final int var1 = this.£á ? this.ˆà : this.¥Æ;
            final int var2 = this.£à - this.Å;
            int var3 = this.µà - this.Å;
            final String var4 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.Å), this.ˆÏ­());
            final boolean var5 = var2 >= 0 && var2 <= var4.length();
            final boolean var6 = this.ˆÏ­ && this.áˆºÑ¢Õ / 6 % 2 == 0 && var5;
            final int var7 = this.ÂµÈ ? (this.Ø­áŒŠá + 4) : this.Ø­áŒŠá;
            final int var8 = this.ÂµÈ ? (this.Âµá€ + (this.à - 8) / 2) : this.Âµá€;
            int var9 = var7;
            if (var3 > var4.length()) {
                var3 = var4.length();
            }
            if (var4.length() > 0) {
                final String var10 = var5 ? var4.substring(0, var2) : var4;
                var9 = this.Ý.HorizonCode_Horizon_È(var10, var7, (float)var8, var1);
            }
            final boolean var11 = this.£à < this.Ø.length() || this.Ø.length() >= this.Ø();
            int var12 = var9;
            if (!var5) {
                var12 = ((var2 > 0) ? (var7 + this.Ó) : var7);
            }
            else if (var11) {
                var12 = var9 - 1;
                --var9;
            }
            if (var4.length() > 0 && var5 && var2 < var4.length()) {
                var9 = this.Ý.HorizonCode_Horizon_È(var4.substring(var2), var9, (float)var8, var1);
            }
            if (var6) {
                if (var11) {
                    Gui_1808253012.HorizonCode_Horizon_È(var12, var8 - 1, var12 + 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È, -1);
                }
                else {
                    this.Ý.HorizonCode_Horizon_È("▏", var12, (float)var8, var1);
                }
            }
            if (var3 != var2) {
                final int var13 = var7 + this.Ý.HorizonCode_Horizon_È(var4.substring(0, var3));
                this.Ý(var12, var8 - 1, var13 - 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È);
            }
        }
    }
}
