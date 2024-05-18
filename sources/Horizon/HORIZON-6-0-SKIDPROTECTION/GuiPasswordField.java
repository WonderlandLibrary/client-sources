package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public final class GuiPasswordField extends Gui_1808253012
{
    private final int HorizonCode_Horizon_È;
    private final int Â;
    private final FontRenderer Ý;
    private boolean Ø­áŒŠá;
    private boolean Âµá€;
    private int Ó;
    private boolean à;
    private String Ø;
    private int áŒŠÆ;
    private final int áˆºÑ¢Õ;
    private final int ÂµÈ;
    private boolean á;
    private int ˆÏ­;
    private int £á;
    private int Å;
    private int £à;
    private int µà;
    private boolean ˆà;
    private int ¥Æ;
    
    public GuiPasswordField(final FontRenderer p_i1032_1_, final int p_i1032_2_, final int p_i1032_3_, final int p_i1032_4_, final int p_i1032_5_) {
        this.Ø­áŒŠá = true;
        this.à = true;
        this.Ø = "";
        this.áŒŠÆ = 32;
        this.á = true;
        this.ˆÏ­ = 7368816;
        this.£á = 14737632;
        this.ˆà = true;
        this.¥Æ = 101;
        this.Ý = p_i1032_1_;
        this.HorizonCode_Horizon_È = p_i1032_2_;
        this.Â = p_i1032_3_;
        this.áˆºÑ¢Õ = p_i1032_4_;
        this.ÂµÈ = p_i1032_5_;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Â()) {
            if (this.Ý()) {
                if (!this.Âµá€) {
                    this.¥Æ += 11;
                    if (this.¥Æ >= 101) {
                        this.¥Æ = 101;
                    }
                    Gui_1808253012.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È - 1, this.Â + this.ÂµÈ - 1, this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ + 0, this.Â + this.ÂµÈ + 0, -2130706433);
                    if (this.¥Æ < 101) {
                        Gui_1808253012.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È - 1 + this.¥Æ, this.Â + this.ÂµÈ - 1, this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ + 0 - this.¥Æ, this.Â + this.ÂµÈ + 0, -10245909);
                    }
                }
                else {
                    this.¥Æ -= 11;
                    if (this.¥Æ <= 0) {
                        this.¥Æ = 0;
                    }
                    Gui_1808253012.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È - 1, this.Â + this.ÂµÈ - 1, this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ + 0, this.Â + this.ÂµÈ + 0, -2130706433);
                    if (this.¥Æ < 101) {
                        Gui_1808253012.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È - 1 + this.¥Æ, this.Â + this.ÂµÈ - 1, this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ + 0 - this.¥Æ, this.Â + this.ÂµÈ + 0, -10245909);
                    }
                }
            }
            final int var1 = this.ˆà ? this.£á : this.ˆÏ­;
            final int var2 = this.£à - this.µà;
            int var3 = this.Å - this.µà;
            final String var4 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.µà), this.à());
            final boolean var5 = var2 >= 0 && var2 <= var4.length();
            final boolean var6 = this.Âµá€ && this.Ó / 6 % 2 == 0 && var5;
            final int var7 = this.à ? (this.HorizonCode_Horizon_È + 4) : this.HorizonCode_Horizon_È;
            final int var8 = this.à ? (this.Â + (this.ÂµÈ - 8) / 2) : this.Â;
            int var9 = var7;
            if (var3 > var4.length()) {
                var3 = var4.length();
            }
            if (var4.length() > 0) {
                final String var10 = var5 ? var4.substring(0, var2) : var4;
                var9 = this.Ý.HorizonCode_Horizon_È(var10.replaceAll(".", "*"), var7, var8, var1);
            }
            final boolean var11 = this.£à < this.Ø.length() || this.Ø.length() >= this.áˆºÑ¢Õ();
            int var12 = var9;
            if (!var5) {
                var12 = ((var2 > 0) ? (var7 + this.áˆºÑ¢Õ) : var7);
            }
            else if (var11) {
                var12 = var9 - 1;
                --var9;
            }
            if (var4.length() > 0 && var5 && var2 < var4.length()) {
                this.Ý.HorizonCode_Horizon_È(var4.substring(var2).replaceAll(".", "*"), var9, var8, var1);
            }
            if (var6) {
                if (var11) {
                    Gui_1808253012.HorizonCode_Horizon_È(var12, var8 - 1, var12 + 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È, -3092272);
                }
                else {
                    this.Ý.HorizonCode_Horizon_È("▏", var12, var8, var1);
                }
            }
            if (var3 != var2) {
                final int var13 = var7 + this.Ý.HorizonCode_Horizon_È(var4.substring(0, var3).replaceAll(".", "*"));
                this.Ý(var12, var8 - 1, var13 - 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_146175_1_) {
        if (this.Ø.length() != 0) {
            if (this.Å != this.£à) {
                this.HorizonCode_Horizon_È("");
            }
            else {
                final boolean var2 = p_146175_1_ < 0;
                final int var3 = var2 ? (this.£à + p_146175_1_) : this.£à;
                final int var4 = var2 ? this.£à : (this.£à + p_146175_1_);
                String var5 = "";
                if (var3 >= 0) {
                    var5 = this.Ø.substring(0, var3);
                }
                if (var4 < this.Ø.length()) {
                    var5 = String.valueOf(var5) + this.Ø.substring(var4);
                }
                this.Ø = var5;
                if (var2) {
                    this.Ý(p_146175_1_);
                }
            }
        }
    }
    
    public boolean Â() {
        return this.á;
    }
    
    public void Â(final int p_146177_1_) {
        if (this.Ø.length() != 0) {
            if (this.Å != this.£à) {
                this.HorizonCode_Horizon_È("");
            }
            else {
                this.HorizonCode_Horizon_È(this.Ø­áŒŠá(p_146177_1_) - this.£à);
            }
        }
    }
    
    public boolean Ý() {
        return this.à;
    }
    
    public void Ý(final int p_146182_1_) {
        this.Âµá€(this.Å + p_146182_1_);
    }
    
    public int HorizonCode_Horizon_È(final int p_146183_1_, final int p_146183_2_) {
        return this.HorizonCode_Horizon_È(p_146183_1_, this.Ó(), true);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_146184_1_) {
        this.ˆà = p_146184_1_;
    }
    
    public void Â(final boolean p_146185_1_) {
        this.à = p_146185_1_;
    }
    
    public int Ø­áŒŠá() {
        return this.Å;
    }
    
    public int Ø­áŒŠá(final int p_146187_1_) {
        return this.HorizonCode_Horizon_È(p_146187_1_, this.Ó());
    }
    
    private void Ý(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
        if (p_146188_1_ < p_146188_3_) {
            final int var5 = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = var5;
        }
        if (p_146188_2_ < p_146188_4_) {
            final int var5 = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = var5;
        }
        if (p_146188_3_ > this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ) {
            p_146188_3_ = this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ;
        }
        if (p_146188_1_ > this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ) {
            p_146188_1_ = this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ;
        }
        final WorldRenderer var6 = Tessellator.HorizonCode_Horizon_È.Ý();
        GL11.glColor4f(0.0f, 0.0f, 255.0f, 255.0f);
        GL11.glDisable(3553);
        GL11.glEnable(3058);
        GL11.glLogicOp(5387);
        var6.Â();
        var6.Â(p_146188_1_, p_146188_4_, 0.0);
        var6.Â(p_146188_3_, p_146188_4_, 0.0);
        var6.Â(p_146188_3_, p_146188_2_, 0.0);
        var6.Â(p_146188_1_, p_146188_2_, 0.0);
        var6.Ø­áŒŠá();
        GL11.glDisable(3058);
        GL11.glEnable(3553);
    }
    
    public void Ý(final boolean p_146189_1_) {
        this.á = p_146189_1_;
    }
    
    public void Âµá€(final int p_146190_1_) {
        this.£à = p_146190_1_;
        final int var2 = this.Ø.length();
        if (this.£à < 0) {
            this.£à = 0;
        }
        if (this.£à > var2) {
            this.£à = var2;
        }
        this.à(this.£à);
    }
    
    public void HorizonCode_Horizon_È(final String p_146191_1_) {
        String var2 = "";
        final String var3 = ChatAllowedCharacters.HorizonCode_Horizon_È(p_146191_1_);
        final int var4 = (this.£à < this.Å) ? this.£à : this.Å;
        final int var5 = (this.£à < this.Å) ? this.Å : this.£à;
        final int var6 = this.áŒŠÆ - this.Ø.length() - (var4 - this.Å);
        if (this.Ø.length() > 0) {
            var2 = String.valueOf(var2) + this.Ø.substring(0, var4);
        }
        int var7;
        if (var6 < var3.length()) {
            var2 = String.valueOf(var2) + var3.substring(0, var6);
            var7 = var6;
        }
        else {
            var2 = String.valueOf(var2) + var3;
            var7 = var3.length();
        }
        if (this.Ø.length() > 0 && var5 < this.Ø.length()) {
            var2 = String.valueOf(var2) + this.Ø.substring(var5);
        }
        this.Ø = var2;
        this.Ý(var4 - this.Å + var7);
    }
    
    public void Ó(final int p_146193_1_) {
        this.£á = p_146193_1_;
    }
    
    public void Âµá€() {
        this.Âµá€(0);
    }
    
    public int HorizonCode_Horizon_È(final int p_146197_1_, final int p_146197_2_, final boolean p_146197_3_) {
        int var4 = p_146197_2_;
        final boolean var5 = p_146197_1_ < 0;
        for (int var6 = Math.abs(p_146197_1_), var7 = 0; var7 < var6; ++var7) {
            if (var5) {
                do {
                    --var4;
                } while (!p_146197_3_ || var4 <= 0 || this.Ø.charAt(var4 - 1) == ' ');
                while (--var4 > 0) {
                    if (this.Ø.charAt(var4 - 1) == ' ') {
                        break;
                    }
                }
            }
            else {
                final int var8 = this.Ø.length();
                var4 = this.Ø.indexOf(32, var4);
                if (var4 == -1) {
                    var4 = var8;
                }
                else {
                    while (p_146197_3_ && var4 < var8 && this.Ø.charAt(var4) == ' ') {
                        ++var4;
                    }
                }
            }
        }
        return var4;
    }
    
    public int Ó() {
        return this.£à;
    }
    
    public void à(int p_146199_1_) {
        final int var2 = this.Ø.length();
        if (p_146199_1_ > var2) {
            p_146199_1_ = var2;
        }
        if (p_146199_1_ < 0) {
            p_146199_1_ = 0;
        }
        this.Å = p_146199_1_;
        if (this.Ý != null) {
            if (this.µà > var2) {
                this.µà = var2;
            }
            final int var3 = this.à();
            final String var4 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.µà), var3);
            final int var5 = var4.length() + this.µà;
            if (p_146199_1_ == this.µà) {
                this.µà -= this.Ý.HorizonCode_Horizon_È(this.Ø, var3, true).length();
            }
            if (p_146199_1_ > var5) {
                this.µà += p_146199_1_ - var5;
            }
            else if (p_146199_1_ <= this.µà) {
                this.µà -= this.µà - p_146199_1_;
            }
            if (this.µà < 0) {
                this.µà = 0;
            }
            if (this.µà > var2) {
                this.µà = var2;
            }
        }
    }
    
    public int à() {
        return this.Ý() ? (this.áˆºÑ¢Õ - 8) : this.áˆºÑ¢Õ;
    }
    
    public void Ø() {
        this.Âµá€(this.Ø.length());
    }
    
    public void Ø(final int p_146203_1_) {
        this.áŒŠÆ = p_146203_1_;
        if (this.Ø.length() > p_146203_1_) {
            this.Ø = this.Ø.substring(0, p_146203_1_);
        }
    }
    
    public void áŒŠÆ(final int p_146204_1_) {
        this.ˆÏ­ = p_146204_1_;
    }
    
    public void Ø­áŒŠá(final boolean p_146205_1_) {
        this.Ø­áŒŠá = p_146205_1_;
    }
    
    public String áŒŠÆ() {
        final int var1 = (this.£à < this.Å) ? this.£à : this.Å;
        final int var2 = (this.£à < this.Å) ? this.Å : this.£à;
        return this.Ø.substring(var1, var2);
    }
    
    public int áˆºÑ¢Õ() {
        return this.áŒŠÆ;
    }
    
    public String ÂµÈ() {
        return this.Ø;
    }
    
    public boolean á() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final int p_146192_1_, final int p_146192_2_, final int p_146192_3_) {
        final boolean var4 = p_146192_1_ >= this.HorizonCode_Horizon_È && p_146192_1_ < this.HorizonCode_Horizon_È + this.áˆºÑ¢Õ && p_146192_2_ >= this.Â && p_146192_2_ < this.Â + this.ÂµÈ;
        if (this.Ø­áŒŠá) {
            this.Âµá€(var4);
        }
        if (this.Âµá€ && p_146192_3_ == 0) {
            int var5 = p_146192_1_ - this.HorizonCode_Horizon_È;
            if (this.à) {
                var5 -= 4;
            }
            final String var6 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.µà), this.à());
            this.Âµá€(this.Ý.HorizonCode_Horizon_È(var6, var5).length() + this.µà);
        }
    }
    
    public void Âµá€(final boolean p_146195_1_) {
        if (p_146195_1_ && !this.Âµá€) {
            this.Ó = 0;
        }
        this.Âµá€ = p_146195_1_;
    }
    
    public void Â(final String p_146180_1_) {
        if (p_146180_1_.length() > this.áŒŠÆ) {
            this.Ø = p_146180_1_.substring(0, this.áŒŠÆ);
        }
        else {
            this.Ø = p_146180_1_;
        }
        this.Ø();
    }
    
    public boolean HorizonCode_Horizon_È(final char p_146201_1_, final int p_146201_2_) {
        if (!this.Âµá€) {
            return false;
        }
        switch (p_146201_1_) {
            case '\u0001': {
                this.Ø();
                this.à(0);
                return true;
            }
            case '\u0003': {
                GuiScreen.Ø­áŒŠá(this.áŒŠÆ());
                return true;
            }
            case '\u0016': {
                if (this.ˆà) {
                    this.HorizonCode_Horizon_È(GuiScreen.ÂµÈ());
                }
                return true;
            }
            case '\u0018': {
                GuiScreen.Ø­áŒŠá(this.áŒŠÆ());
                if (this.ˆà) {
                    this.HorizonCode_Horizon_È("");
                }
                return true;
            }
            default: {
                switch (p_146201_2_) {
                    case 14: {
                        if (GuiScreen.Å()) {
                            if (this.ˆà) {
                                this.Â(-1);
                            }
                        }
                        else if (this.ˆà) {
                            this.HorizonCode_Horizon_È(-1);
                        }
                        return true;
                    }
                    case 199: {
                        if (GuiScreen.£à()) {
                            this.à(0);
                        }
                        else {
                            this.Âµá€();
                        }
                        return true;
                    }
                    case 203: {
                        if (GuiScreen.£à()) {
                            if (GuiScreen.Å()) {
                                this.à(this.HorizonCode_Horizon_È(-1, this.Ø­áŒŠá()));
                            }
                            else {
                                this.à(this.Ø­áŒŠá() - 1);
                            }
                        }
                        else if (GuiScreen.Å()) {
                            this.Âµá€(this.Ø­áŒŠá(-1));
                        }
                        else {
                            this.Ý(-1);
                        }
                        return true;
                    }
                    case 205: {
                        if (GuiScreen.£à()) {
                            if (GuiScreen.Å()) {
                                this.à(this.HorizonCode_Horizon_È(1, this.Ø­áŒŠá()));
                            }
                            else {
                                this.à(this.Ø­áŒŠá() + 1);
                            }
                        }
                        else if (GuiScreen.Å()) {
                            this.Âµá€(this.Ø­áŒŠá(1));
                        }
                        else {
                            this.Ý(1);
                        }
                        return true;
                    }
                    case 207: {
                        if (GuiScreen.£à()) {
                            this.à(this.Ø.length());
                        }
                        else {
                            this.Ø();
                        }
                        return true;
                    }
                    case 211: {
                        if (GuiScreen.Å()) {
                            if (this.ˆà) {
                                this.Â(1);
                            }
                        }
                        else if (this.ˆà) {
                            this.HorizonCode_Horizon_È(1);
                        }
                        return true;
                    }
                    default: {
                        if (ChatAllowedCharacters.HorizonCode_Horizon_È(p_146201_1_)) {
                            if (this.ˆà) {
                                this.HorizonCode_Horizon_È(Character.toString(p_146201_1_));
                            }
                            return true;
                        }
                        return false;
                    }
                }
                break;
            }
        }
    }
    
    public void ˆÏ­() {
        ++this.Ó;
    }
}
