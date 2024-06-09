package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;

public class GuiPageButtonList extends GuiListExtended
{
    private final List HorizonCode_Horizon_È;
    private final IntHashMap Â;
    private final List Šáƒ;
    private final Âµá€[][] Ï­Ðƒà;
    private int áŒŠà;
    private Ó ŠÄ;
    private Gui_1808253012 Ñ¢á;
    private static final String ŒÏ = "CL_00001950";
    
    public GuiPageButtonList(final Minecraft mcIn, final int p_i45536_2_, final int p_i45536_3_, final int p_i45536_4_, final int p_i45536_5_, final int p_i45536_6_, final Ó p_i45536_7_, final Âµá€[]... p_i45536_8_) {
        super(mcIn, p_i45536_2_, p_i45536_3_, p_i45536_4_, p_i45536_5_, p_i45536_6_);
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.Â = new IntHashMap();
        this.Šáƒ = Lists.newArrayList();
        this.ŠÄ = p_i45536_7_;
        this.Ï­Ðƒà = p_i45536_8_;
        this.ˆÏ­ = false;
        this.µà();
        this.ˆà();
    }
    
    private void µà() {
        for (final Âµá€[] var4 : this.Ï­Ðƒà) {
            for (int var5 = 0; var5 < var4.length; var5 += 2) {
                final Âµá€ var6 = var4[var5];
                final Âµá€ var7 = (var5 < var4.length - 1) ? var4[var5 + 1] : null;
                final Gui_1808253012 var8 = this.HorizonCode_Horizon_È(var6, 0, var7 == null);
                final Gui_1808253012 var9 = this.HorizonCode_Horizon_È(var7, 160, var6 == null);
                final Ý var10 = new Ý(var8, var9);
                this.HorizonCode_Horizon_È.add(var10);
                if (var6 != null && var8 != null) {
                    this.Â.HorizonCode_Horizon_È(var6.Â(), var8);
                    if (var8 instanceof GuiTextField) {
                        this.Šáƒ.add(var8);
                    }
                }
                if (var7 != null && var9 != null) {
                    this.Â.HorizonCode_Horizon_È(var7.Â(), var9);
                    if (var9 instanceof GuiTextField) {
                        this.Šáƒ.add(var9);
                    }
                }
            }
        }
    }
    
    private void ˆà() {
        this.HorizonCode_Horizon_È.clear();
        for (int var1 = 0; var1 < this.Ï­Ðƒà[this.áŒŠà].length; var1 += 2) {
            final Âµá€ var2 = this.Ï­Ðƒà[this.áŒŠà][var1];
            final Âµá€ var3 = (var1 < this.Ï­Ðƒà[this.áŒŠà].length - 1) ? this.Ï­Ðƒà[this.áŒŠà][var1 + 1] : null;
            final Gui_1808253012 var4 = (Gui_1808253012)this.Â.HorizonCode_Horizon_È(var2.Â());
            final Gui_1808253012 var5 = (var3 != null) ? ((Gui_1808253012)this.Â.HorizonCode_Horizon_È(var3.Â())) : null;
            final Ý var6 = new Ý(var4, var5);
            this.HorizonCode_Horizon_È.add(var6);
        }
    }
    
    public int Ø­áŒŠá() {
        return this.áŒŠà;
    }
    
    public int Âµá€() {
        return this.Ï­Ðƒà.length;
    }
    
    public Gui_1808253012 áŒŠÆ() {
        return this.Ñ¢á;
    }
    
    public void áˆºÑ¢Õ() {
        if (this.áŒŠà > 0) {
            final int var1 = this.áŒŠà--;
            this.ˆà();
            this.Âµá€(var1, this.áŒŠà);
            this.£à = 0.0f;
        }
    }
    
    public void ÂµÈ() {
        if (this.áŒŠà < this.Ï­Ðƒà.length - 1) {
            final int var1 = this.áŒŠà++;
            this.ˆà();
            this.Âµá€(var1, this.áŒŠà);
            this.£à = 0.0f;
        }
    }
    
    public Gui_1808253012 Ý(final int p_178061_1_) {
        return (Gui_1808253012)this.Â.HorizonCode_Horizon_È(p_178061_1_);
    }
    
    private void Âµá€(final int p_178060_1_, final int p_178060_2_) {
        for (final Âµá€ var6 : this.Ï­Ðƒà[p_178060_1_]) {
            if (var6 != null) {
                this.HorizonCode_Horizon_È((Gui_1808253012)this.Â.HorizonCode_Horizon_È(var6.Â()), false);
            }
        }
        for (final Âµá€ var6 : this.Ï­Ðƒà[p_178060_2_]) {
            if (var6 != null) {
                this.HorizonCode_Horizon_È((Gui_1808253012)this.Â.HorizonCode_Horizon_È(var6.Â()), true);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final Gui_1808253012 p_178066_1_, final boolean p_178066_2_) {
        if (p_178066_1_ instanceof GuiButton) {
            ((GuiButton)p_178066_1_).ˆà = p_178066_2_;
        }
        else if (p_178066_1_ instanceof GuiTextField) {
            ((GuiTextField)p_178066_1_).Âµá€(p_178066_2_);
        }
        else if (p_178066_1_ instanceof GuiLabel) {
            ((GuiLabel)p_178066_1_).Ó = p_178066_2_;
        }
    }
    
    private Gui_1808253012 HorizonCode_Horizon_È(final Âµá€ p_178058_1_, final int p_178058_2_, final boolean p_178058_3_) {
        return (p_178058_1_ instanceof à) ? this.HorizonCode_Horizon_È(this.Ø­áŒŠá / 2 - 155 + p_178058_2_, 0, (à)p_178058_1_) : ((p_178058_1_ instanceof Â) ? this.HorizonCode_Horizon_È(this.Ø­áŒŠá / 2 - 155 + p_178058_2_, 0, (Â)p_178058_1_) : ((p_178058_1_ instanceof HorizonCode_Horizon_È) ? this.HorizonCode_Horizon_È(this.Ø­áŒŠá / 2 - 155 + p_178058_2_, 0, (HorizonCode_Horizon_È)p_178058_1_) : ((p_178058_1_ instanceof Ø­áŒŠá) ? this.HorizonCode_Horizon_È(this.Ø­áŒŠá / 2 - 155 + p_178058_2_, 0, (Ø­áŒŠá)p_178058_1_, p_178058_3_) : null)));
    }
    
    @Override
    public boolean Â(final int p_148179_1_, final int p_148179_2_, final int p_148179_3_) {
        final boolean var4 = super.Â(p_148179_1_, p_148179_2_, p_148179_3_);
        final int var5 = this.Ý(p_148179_1_, p_148179_2_);
        if (var5 >= 0) {
            final Ý var6 = this.Ø­áŒŠá(var5);
            if (this.Ñ¢á != var6.Ø­áŒŠá && this.Ñ¢á != null && this.Ñ¢á instanceof GuiTextField) {
                ((GuiTextField)this.Ñ¢á).Â(false);
            }
            this.Ñ¢á = var6.Ø­áŒŠá;
        }
        return var4;
    }
    
    private GuiSlider HorizonCode_Horizon_È(final int p_178067_1_, final int p_178067_2_, final à p_178067_3_) {
        final GuiSlider var4 = new GuiSlider(this.ŠÄ, p_178067_3_.Â(), p_178067_1_, p_178067_2_, p_178067_3_.Ý(), p_178067_3_.Âµá€(), p_178067_3_.Ó(), p_178067_3_.à(), p_178067_3_.HorizonCode_Horizon_È());
        var4.ˆà = p_178067_3_.Ø­áŒŠá();
        return var4;
    }
    
    private GuiListButton HorizonCode_Horizon_È(final int p_178065_1_, final int p_178065_2_, final Â p_178065_3_) {
        final GuiListButton var4 = new GuiListButton(this.ŠÄ, p_178065_3_.Â(), p_178065_1_, p_178065_2_, p_178065_3_.Ý(), p_178065_3_.HorizonCode_Horizon_È());
        var4.ˆà = p_178065_3_.Ø­áŒŠá();
        return var4;
    }
    
    private GuiTextField HorizonCode_Horizon_È(final int p_178068_1_, final int p_178068_2_, final HorizonCode_Horizon_È p_178068_3_) {
        final GuiTextField var4 = new GuiTextField(p_178068_3_.Â(), this.Ý.µà, p_178068_1_, p_178068_2_, 150, 20);
        var4.HorizonCode_Horizon_È(p_178068_3_.Ý());
        var4.HorizonCode_Horizon_È(this.ŠÄ);
        var4.Âµá€(p_178068_3_.Ø­áŒŠá());
        var4.HorizonCode_Horizon_È(p_178068_3_.HorizonCode_Horizon_È());
        return var4;
    }
    
    private GuiLabel HorizonCode_Horizon_È(final int p_178063_1_, final int p_178063_2_, final Ø­áŒŠá p_178063_3_, final boolean p_178063_4_) {
        GuiLabel var5;
        if (p_178063_4_) {
            var5 = new GuiLabel(this.Ý.µà, p_178063_3_.Â(), p_178063_1_, p_178063_2_, this.Ø­áŒŠá - p_178063_1_ * 2, 20, -1);
        }
        else {
            var5 = new GuiLabel(this.Ý.µà, p_178063_3_.Â(), p_178063_1_, p_178063_2_, 150, 20, -1);
        }
        var5.Ó = p_178063_3_.Ø­áŒŠá();
        var5.HorizonCode_Horizon_È(p_178063_3_.Ý());
        var5.HorizonCode_Horizon_È();
        return var5;
    }
    
    public void HorizonCode_Horizon_È(final char p_178062_1_, final int p_178062_2_) {
        if (this.Ñ¢á instanceof GuiTextField) {
            GuiTextField var3 = (GuiTextField)this.Ñ¢á;
            if (!GuiScreen.Âµá€(p_178062_2_)) {
                if (p_178062_2_ == 15) {
                    var3.Â(false);
                    int var4 = this.Šáƒ.indexOf(this.Ñ¢á);
                    if (GuiScreen.£à()) {
                        if (var4 == 0) {
                            var4 = this.Šáƒ.size() - 1;
                        }
                        else {
                            --var4;
                        }
                    }
                    else if (var4 == this.Šáƒ.size() - 1) {
                        var4 = 0;
                    }
                    else {
                        ++var4;
                    }
                    this.Ñ¢á = this.Šáƒ.get(var4);
                    var3 = (GuiTextField)this.Ñ¢á;
                    var3.Â(true);
                    final int var5 = var3.Âµá€ + this.áˆºÑ¢Õ;
                    final int var6 = var3.Âµá€;
                    if (var5 > this.à) {
                        this.£à += var5 - this.à;
                    }
                    else if (var6 < this.Ó) {
                        this.£à = var6;
                    }
                }
                else {
                    var3.HorizonCode_Horizon_È(p_178062_1_, p_178062_2_);
                }
            }
            else {
                final String var7 = GuiScreen.ÂµÈ();
                final String[] var8 = var7.split(";");
                int var9;
                final int var6 = var9 = this.Šáƒ.indexOf(this.Ñ¢á);
                final String[] var10 = var8;
                for (int var11 = var8.length, var12 = 0; var12 < var11; ++var12) {
                    final String var13 = var10[var12];
                    this.Šáƒ.get(var9).HorizonCode_Horizon_È(var13);
                    if (var9 == this.Šáƒ.size() - 1) {
                        var9 = 0;
                    }
                    else {
                        ++var9;
                    }
                    if (var9 == var6) {
                        break;
                    }
                }
            }
        }
    }
    
    public Ý Ø­áŒŠá(final int p_178070_1_) {
        return this.HorizonCode_Horizon_È.get(p_178070_1_);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    @Override
    public int o_() {
        return 400;
    }
    
    @Override
    protected int à() {
        return super.à() + 32;
    }
    
    @Override
    public GuiListExtended.HorizonCode_Horizon_È Â(final int p_148180_1_) {
        return this.Ø­áŒŠá(p_148180_1_);
    }
    
    public static class HorizonCode_Horizon_È extends Âµá€
    {
        private final Predicate HorizonCode_Horizon_È;
        private static final String Â = "CL_00001948";
        
        public HorizonCode_Horizon_È(final int p_i45534_1_, final String p_i45534_2_, final boolean p_i45534_3_, final Predicate p_i45534_4_) {
            super(p_i45534_1_, p_i45534_2_, p_i45534_3_);
            this.HorizonCode_Horizon_È = (Predicate)Objects.firstNonNull((Object)p_i45534_4_, (Object)Predicates.alwaysTrue());
        }
        
        public Predicate HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
    }
    
    public static class Â extends Âµá€
    {
        private final boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00001949";
        
        public Â(final int p_i45535_1_, final String p_i45535_2_, final boolean p_i45535_3_, final boolean p_i45535_4_) {
            super(p_i45535_1_, p_i45535_2_, p_i45535_3_);
            this.HorizonCode_Horizon_È = p_i45535_4_;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
    }
    
    public static class Ý implements GuiListExtended.HorizonCode_Horizon_È
    {
        private final Minecraft HorizonCode_Horizon_È;
        private final Gui_1808253012 Â;
        private final Gui_1808253012 Ý;
        private Gui_1808253012 Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001947";
        
        public Ý(final Gui_1808253012 p_i45533_1_, final Gui_1808253012 p_i45533_2_) {
            this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
            this.Â = p_i45533_1_;
            this.Ý = p_i45533_2_;
        }
        
        public Gui_1808253012 HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public Gui_1808253012 Â() {
            return this.Ý;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
            this.HorizonCode_Horizon_È(this.Â, y, mouseX, mouseY, false);
            this.HorizonCode_Horizon_È(this.Ý, y, mouseX, mouseY, false);
        }
        
        private void HorizonCode_Horizon_È(final Gui_1808253012 p_178017_1_, final int p_178017_2_, final int p_178017_3_, final int p_178017_4_, final boolean p_178017_5_) {
            if (p_178017_1_ != null) {
                if (p_178017_1_ instanceof GuiButton) {
                    this.HorizonCode_Horizon_È((GuiButton)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
                }
                else if (p_178017_1_ instanceof GuiTextField) {
                    this.HorizonCode_Horizon_È((GuiTextField)p_178017_1_, p_178017_2_, p_178017_5_);
                }
                else if (p_178017_1_ instanceof GuiLabel) {
                    this.HorizonCode_Horizon_È((GuiLabel)p_178017_1_, p_178017_2_, p_178017_3_, p_178017_4_, p_178017_5_);
                }
            }
        }
        
        private void HorizonCode_Horizon_È(final GuiButton p_178024_1_, final int p_178024_2_, final int p_178024_3_, final int p_178024_4_, final boolean p_178024_5_) {
            p_178024_1_.£á = p_178024_2_;
            if (!p_178024_5_) {
                p_178024_1_.Ý(this.HorizonCode_Horizon_È, p_178024_3_, p_178024_4_);
            }
        }
        
        private void HorizonCode_Horizon_È(final GuiTextField p_178027_1_, final int p_178027_2_, final boolean p_178027_3_) {
            p_178027_1_.Âµá€ = p_178027_2_;
            if (!p_178027_3_) {
                p_178027_1_.HorizonCode_Horizon_È();
            }
        }
        
        private void HorizonCode_Horizon_È(final GuiLabel p_178025_1_, final int p_178025_2_, final int p_178025_3_, final int p_178025_4_, final boolean p_178025_5_) {
            p_178025_1_.Ø­áŒŠá = p_178025_2_;
            if (!p_178025_5_) {
                p_178025_1_.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_178025_3_, p_178025_4_);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
            this.HorizonCode_Horizon_È(this.Â, p_178011_3_, 0, 0, true);
            this.HorizonCode_Horizon_È(this.Ý, p_178011_3_, 0, 0, true);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
            final boolean var7 = this.HorizonCode_Horizon_È(this.Â, p_148278_2_, p_148278_3_, p_148278_4_);
            final boolean var8 = this.HorizonCode_Horizon_È(this.Ý, p_148278_2_, p_148278_3_, p_148278_4_);
            return var7 || var8;
        }
        
        private boolean HorizonCode_Horizon_È(final Gui_1808253012 p_178026_1_, final int p_178026_2_, final int p_178026_3_, final int p_178026_4_) {
            if (p_178026_1_ == null) {
                return false;
            }
            if (p_178026_1_ instanceof GuiButton) {
                return this.HorizonCode_Horizon_È((GuiButton)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
            }
            if (p_178026_1_ instanceof GuiTextField) {
                this.HorizonCode_Horizon_È((GuiTextField)p_178026_1_, p_178026_2_, p_178026_3_, p_178026_4_);
            }
            return false;
        }
        
        private boolean HorizonCode_Horizon_È(final GuiButton p_178023_1_, final int p_178023_2_, final int p_178023_3_, final int p_178023_4_) {
            final boolean var5 = p_178023_1_.Â(this.HorizonCode_Horizon_È, p_178023_2_, p_178023_3_);
            if (var5) {
                this.Ø­áŒŠá = p_178023_1_;
            }
            return var5;
        }
        
        private void HorizonCode_Horizon_È(final GuiTextField p_178018_1_, final int p_178018_2_, final int p_178018_3_, final int p_178018_4_) {
            p_178018_1_.HorizonCode_Horizon_È(p_178018_2_, p_178018_3_, p_178018_4_);
            if (p_178018_1_.ÂµÈ()) {
                this.Ø­áŒŠá = p_178018_1_;
            }
        }
        
        @Override
        public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
            this.Â(this.Â, x, y, mouseEvent);
            this.Â(this.Ý, x, y, mouseEvent);
        }
        
        private void Â(final Gui_1808253012 p_178016_1_, final int p_178016_2_, final int p_178016_3_, final int p_178016_4_) {
            if (p_178016_1_ != null && p_178016_1_ instanceof GuiButton) {
                this.Â((GuiButton)p_178016_1_, p_178016_2_, p_178016_3_, p_178016_4_);
            }
        }
        
        private void Â(final GuiButton p_178019_1_, final int p_178019_2_, final int p_178019_3_, final int p_178019_4_) {
            p_178019_1_.HorizonCode_Horizon_È(p_178019_2_, p_178019_3_);
        }
    }
    
    public static class Ø­áŒŠá extends Âµá€
    {
        private static final String HorizonCode_Horizon_È = "CL_00001946";
        
        public Ø­áŒŠá(final int p_i45532_1_, final String p_i45532_2_, final boolean p_i45532_3_) {
            super(p_i45532_1_, p_i45532_2_, p_i45532_3_);
        }
    }
    
    public static class Âµá€
    {
        private final int HorizonCode_Horizon_È;
        private final String Â;
        private final boolean Ý;
        private static final String Ø­áŒŠá = "CL_00001945";
        
        public Âµá€(final int p_i45531_1_, final String p_i45531_2_, final boolean p_i45531_3_) {
            this.HorizonCode_Horizon_È = p_i45531_1_;
            this.Â = p_i45531_2_;
            this.Ý = p_i45531_3_;
        }
        
        public int Â() {
            return this.HorizonCode_Horizon_È;
        }
        
        public String Ý() {
            return this.Â;
        }
        
        public boolean Ø­áŒŠá() {
            return this.Ý;
        }
    }
    
    public static class à extends Âµá€
    {
        private final GuiSlider.HorizonCode_Horizon_È HorizonCode_Horizon_È;
        private final float Â;
        private final float Ý;
        private final float Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001944";
        
        public à(final int p_i45530_1_, final String p_i45530_2_, final boolean p_i45530_3_, final GuiSlider.HorizonCode_Horizon_È p_i45530_4_, final float p_i45530_5_, final float p_i45530_6_, final float p_i45530_7_) {
            super(p_i45530_1_, p_i45530_2_, p_i45530_3_);
            this.HorizonCode_Horizon_È = p_i45530_4_;
            this.Â = p_i45530_5_;
            this.Ý = p_i45530_6_;
            this.Ø­áŒŠá = p_i45530_7_;
        }
        
        public GuiSlider.HorizonCode_Horizon_È HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public float Âµá€() {
            return this.Â;
        }
        
        public float Ó() {
            return this.Ý;
        }
        
        public float à() {
            return this.Ø­áŒŠá;
        }
    }
    
    public interface Ó
    {
        void HorizonCode_Horizon_È(final int p0, final boolean p1);
        
        void HorizonCode_Horizon_È(final int p0, final float p1);
        
        void HorizonCode_Horizon_È(final int p0, final String p1);
    }
}
