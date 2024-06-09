package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

public class GuiTextField extends Gui_1808253012
{
    public final int Â;
    public final FontRenderer Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    public final int Ó;
    public final int à;
    public String Ø;
    public int áŒŠÆ;
    public int áˆºÑ¢Õ;
    public boolean ÂµÈ;
    public boolean á;
    public boolean ˆÏ­;
    public boolean £á;
    public int Å;
    public int £à;
    public int µà;
    public int ˆà;
    public int ¥Æ;
    public boolean Ø­à;
    public GuiPageButtonList.Ó µÕ;
    public Predicate Æ;
    public static final String Ñ¢á = "CL_00000670";
    
    public GuiTextField(final int p_i45542_1_, final FontRenderer p_i45542_2_, final int p_i45542_3_, final int p_i45542_4_, final int p_i45542_5_, final int p_i45542_6_) {
        this.Ø = "";
        this.áŒŠÆ = 32;
        this.ÂµÈ = true;
        this.á = true;
        this.£á = true;
        this.ˆà = 14737632;
        this.¥Æ = 7368816;
        this.Ø­à = true;
        this.Æ = Predicates.alwaysTrue();
        this.Â = p_i45542_1_;
        this.Ý = p_i45542_2_;
        this.Ø­áŒŠá = p_i45542_3_;
        this.Âµá€ = p_i45542_4_;
        this.Ó = p_i45542_5_;
        this.à = p_i45542_6_;
    }
    
    public void HorizonCode_Horizon_È(final GuiPageButtonList.Ó p_175207_1_) {
        this.µÕ = p_175207_1_;
    }
    
    public void Â() {
        ++this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final String p_146180_1_) {
        if (this.Æ.apply((Object)p_146180_1_)) {
            if (p_146180_1_.length() > this.áŒŠÆ) {
                this.Ø = p_146180_1_.substring(0, this.áŒŠÆ);
            }
            else {
                this.Ø = p_146180_1_;
            }
            this.à();
        }
    }
    
    public String Ý() {
        return this.Ø;
    }
    
    public String Ø­áŒŠá() {
        final int var1 = (this.£à < this.µà) ? this.£à : this.µà;
        final int var2 = (this.£à < this.µà) ? this.µà : this.£à;
        return this.Ø.substring(var1, var2);
    }
    
    public void HorizonCode_Horizon_È(final Predicate p_175205_1_) {
        this.Æ = p_175205_1_;
    }
    
    public void Â(final String p_146191_1_) {
        String var2 = "";
        final String var3 = ChatAllowedCharacters.HorizonCode_Horizon_È(p_146191_1_);
        final int var4 = (this.£à < this.µà) ? this.£à : this.µà;
        final int var5 = (this.£à < this.µà) ? this.µà : this.£à;
        final int var6 = this.áŒŠÆ - this.Ø.length() - (var4 - var5);
        final boolean var7 = false;
        if (this.Ø.length() > 0) {
            var2 = String.valueOf(var2) + this.Ø.substring(0, var4);
        }
        int var8;
        if (var6 < var3.length()) {
            var2 = String.valueOf(var2) + var3.substring(0, var6);
            var8 = var6;
        }
        else {
            var2 = String.valueOf(var2) + var3;
            var8 = var3.length();
        }
        if (this.Ø.length() > 0 && var5 < this.Ø.length()) {
            var2 = String.valueOf(var2) + this.Ø.substring(var5);
        }
        if (this.Æ.apply((Object)var2)) {
            this.Ø = var2;
            this.Ø­áŒŠá(var4 - this.µà + var8);
            if (this.µÕ != null) {
                this.µÕ.HorizonCode_Horizon_È(this.Â, this.Ø);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_146177_1_) {
        if (this.Ø.length() != 0) {
            if (this.µà != this.£à) {
                this.Â("");
            }
            else {
                this.Â(this.Ý(p_146177_1_) - this.£à);
            }
        }
    }
    
    public void Â(final int p_146175_1_) {
        if (this.Ø.length() != 0) {
            if (this.µà != this.£à) {
                this.Â("");
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
                    this.Ø­áŒŠá(p_146175_1_);
                }
                if (this.µÕ != null) {
                    this.µÕ.HorizonCode_Horizon_È(this.Â, this.Ø);
                }
            }
        }
    }
    
    public int Âµá€() {
        return this.Â;
    }
    
    public int Ý(final int p_146187_1_) {
        return this.HorizonCode_Horizon_È(p_146187_1_, this.áŒŠÆ());
    }
    
    public int HorizonCode_Horizon_È(final int p_146183_1_, final int p_146183_2_) {
        return this.HorizonCode_Horizon_È(p_146183_1_, p_146183_2_, true);
    }
    
    public int HorizonCode_Horizon_È(final int p_146197_1_, final int p_146197_2_, final boolean p_146197_3_) {
        int var4 = p_146197_2_;
        final boolean var5 = p_146197_1_ < 0;
        for (int var6 = Math.abs(p_146197_1_), var7 = 0; var7 < var6; ++var7) {
            if (var5) {
                while (p_146197_3_ && var4 > 0) {
                    if (this.Ø.charAt(var4 - 1) != ' ') {
                        break;
                    }
                    --var4;
                }
                while (var4 > 0) {
                    if (this.Ø.charAt(var4 - 1) == ' ') {
                        break;
                    }
                    --var4;
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
    
    public void Ø­áŒŠá(final int p_146182_1_) {
        this.Âµá€(this.µà + p_146182_1_);
    }
    
    public void Âµá€(final int p_146190_1_) {
        this.£à = p_146190_1_;
        final int var2 = this.Ø.length();
        this.áŒŠÆ(this.£à = MathHelper.HorizonCode_Horizon_È(this.£à, 0, var2));
    }
    
    public void Ó() {
        this.Âµá€(0);
    }
    
    public void à() {
        this.Âµá€(this.Ø.length());
    }
    
    public boolean HorizonCode_Horizon_È(final char p_146201_1_, final int p_146201_2_) {
        if (!this.ˆÏ­) {
            return false;
        }
        if (GuiScreen.à(p_146201_2_)) {
            this.à();
            this.áŒŠÆ(0);
            return true;
        }
        if (GuiScreen.Ó(p_146201_2_)) {
            GuiScreen.Ø­áŒŠá(this.Ø­áŒŠá());
            return true;
        }
        if (GuiScreen.Âµá€(p_146201_2_)) {
            if (this.£á) {
                this.Â(GuiScreen.ÂµÈ());
            }
            return true;
        }
        if (GuiScreen.Ø­áŒŠá(p_146201_2_)) {
            GuiScreen.Ø­áŒŠá(this.Ø­áŒŠá());
            if (this.£á) {
                this.Â("");
            }
            return true;
        }
        switch (p_146201_2_) {
            case 14: {
                if (GuiScreen.Å()) {
                    if (this.£á) {
                        this.HorizonCode_Horizon_È(-1);
                    }
                }
                else if (this.£á) {
                    this.Â(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.£à()) {
                    this.áŒŠÆ(0);
                }
                else {
                    this.Ó();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.£à()) {
                    if (GuiScreen.Å()) {
                        this.áŒŠÆ(this.HorizonCode_Horizon_È(-1, this.á()));
                    }
                    else {
                        this.áŒŠÆ(this.á() - 1);
                    }
                }
                else if (GuiScreen.Å()) {
                    this.Âµá€(this.Ý(-1));
                }
                else {
                    this.Ø­áŒŠá(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.£à()) {
                    if (GuiScreen.Å()) {
                        this.áŒŠÆ(this.HorizonCode_Horizon_È(1, this.á()));
                    }
                    else {
                        this.áŒŠÆ(this.á() + 1);
                    }
                }
                else if (GuiScreen.Å()) {
                    this.Âµá€(this.Ý(1));
                }
                else {
                    this.Ø­áŒŠá(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.£à()) {
                    this.áŒŠÆ(this.Ø.length());
                }
                else {
                    this.à();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.Å()) {
                    if (this.£á) {
                        this.HorizonCode_Horizon_È(1);
                    }
                }
                else if (this.£á) {
                    this.Â(1);
                }
                return true;
            }
            default: {
                if (ChatAllowedCharacters.HorizonCode_Horizon_È(p_146201_1_)) {
                    if (this.£á) {
                        this.Â(Character.toString(p_146201_1_));
                    }
                    return true;
                }
                return false;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_146192_1_, final int p_146192_2_, final int p_146192_3_) {
        final boolean var4 = p_146192_1_ >= this.Ø­áŒŠá && p_146192_1_ < this.Ø­áŒŠá + this.Ó && p_146192_2_ >= this.Âµá€ && p_146192_2_ < this.Âµá€ + this.à;
        if (this.á) {
            this.Â(var4);
        }
        if (this.ˆÏ­ && var4 && p_146192_3_ == 0) {
            int var5 = p_146192_1_ - this.Ø­áŒŠá;
            if (this.ÂµÈ) {
                var5 -= 4;
            }
            final String var6 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.Å), this.ˆÏ­());
            this.Âµá€(this.Ý.HorizonCode_Horizon_È(var6, var5).length() + this.Å);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.£á()) {
            if (this.áˆºÑ¢Õ()) {
                Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá - 1, this.Âµá€ - 1, this.Ø­áŒŠá + this.Ó + 1, this.Âµá€ + this.à + 1, -6250336);
                Gui_1808253012.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€, this.Ø­áŒŠá + this.Ó, this.Âµá€ + this.à, -16777216);
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
                    Gui_1808253012.HorizonCode_Horizon_È(var12, var8 - 1, var12 + 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È, -3092272);
                }
                else {
                    this.Ý.HorizonCode_Horizon_È("_", var12, (float)var8, var1);
                }
            }
            if (var3 != var2) {
                final int var13 = var7 + this.Ý.HorizonCode_Horizon_È(var4.substring(0, var3));
                this.Ý(var12, var8 - 1, var13 - 1, var8 + 1 + this.Ý.HorizonCode_Horizon_È);
            }
        }
    }
    
    public void Ý(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
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
        if (p_146188_3_ > this.Ø­áŒŠá + this.Ó) {
            p_146188_3_ = this.Ø­áŒŠá + this.Ó;
        }
        if (p_146188_1_ > this.Ø­áŒŠá + this.Ó) {
            p_146188_1_ = this.Ø­áŒŠá + this.Ó;
        }
        final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var7 = var6.Ý();
        GlStateManager.Ý(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.Æ();
        GlStateManager.¥Æ();
        GlStateManager.Ó(5387);
        var7.Â();
        var7.Â(p_146188_1_, p_146188_4_, 0.0);
        var7.Â(p_146188_3_, p_146188_4_, 0.0);
        var7.Â(p_146188_3_, p_146188_2_, 0.0);
        var7.Â(p_146188_1_, p_146188_2_, 0.0);
        var6.Â();
        GlStateManager.Ø­à();
        GlStateManager.µÕ();
    }
    
    public void Ó(final int p_146203_1_) {
        this.áŒŠÆ = p_146203_1_;
        if (this.Ø.length() > p_146203_1_) {
            this.Ø = this.Ø.substring(0, p_146203_1_);
        }
    }
    
    public int Ø() {
        return this.áŒŠÆ;
    }
    
    public int áŒŠÆ() {
        return this.£à;
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_146185_1_) {
        this.ÂµÈ = p_146185_1_;
    }
    
    public void à(final int p_146193_1_) {
        this.ˆà = p_146193_1_;
    }
    
    public void Ø(final int p_146204_1_) {
        this.¥Æ = p_146204_1_;
    }
    
    public void Â(final boolean p_146195_1_) {
        if (p_146195_1_ && !this.ˆÏ­) {
            this.áˆºÑ¢Õ = 0;
        }
        this.ˆÏ­ = p_146195_1_;
    }
    
    public boolean ÂµÈ() {
        return this.ˆÏ­;
    }
    
    public void Ý(final boolean p_146184_1_) {
        this.£á = p_146184_1_;
    }
    
    public int á() {
        return this.µà;
    }
    
    public int ˆÏ­() {
        return this.áˆºÑ¢Õ() ? (this.Ó - 8) : this.Ó;
    }
    
    public void áŒŠÆ(int p_146199_1_) {
        final int var2 = this.Ø.length();
        if (p_146199_1_ > var2) {
            p_146199_1_ = var2;
        }
        if (p_146199_1_ < 0) {
            p_146199_1_ = 0;
        }
        this.µà = p_146199_1_;
        if (this.Ý != null) {
            if (this.Å > var2) {
                this.Å = var2;
            }
            final int var3 = this.ˆÏ­();
            final String var4 = this.Ý.HorizonCode_Horizon_È(this.Ø.substring(this.Å), var3);
            final int var5 = var4.length() + this.Å;
            if (p_146199_1_ == this.Å) {
                this.Å -= this.Ý.HorizonCode_Horizon_È(this.Ø, var3, true).length();
            }
            if (p_146199_1_ > var5) {
                this.Å += p_146199_1_ - var5;
            }
            else if (p_146199_1_ <= this.Å) {
                this.Å -= this.Å - p_146199_1_;
            }
            this.Å = MathHelper.HorizonCode_Horizon_È(this.Å, 0, var2);
        }
    }
    
    public void Ø­áŒŠá(final boolean p_146205_1_) {
        this.á = p_146205_1_;
    }
    
    public boolean £á() {
        return this.Ø­à;
    }
    
    public void Âµá€(final boolean p_146189_1_) {
        this.Ø­à = p_146189_1_;
    }
}
