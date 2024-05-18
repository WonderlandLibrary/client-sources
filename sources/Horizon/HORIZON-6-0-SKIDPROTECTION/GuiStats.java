package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.Collections;
import org.lwjgl.input.Mouse;
import java.util.Comparator;
import java.util.List;
import java.io.IOException;

public class GuiStats extends GuiScreen implements IProgressMeter
{
    protected GuiScreen Â;
    protected String Ý;
    private Ý Ø­áŒŠá;
    private Ø­áŒŠá Âµá€;
    private Â Ó;
    private Âµá€ à;
    private StatFileWriter Ø;
    private GuiSlot áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000723";
    
    public GuiStats(final GuiScreen p_i1071_1_, final StatFileWriter p_i1071_2_) {
        this.Ý = "Select world";
        this.áˆºÑ¢Õ = true;
        this.Â = p_i1071_1_;
        this.Ø = p_i1071_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ý = I18n.HorizonCode_Horizon_È("gui.stats", new Object[0]);
        this.áˆºÑ¢Õ = true;
        GuiStats.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C16PacketClientStatus(C16PacketClientStatus.HorizonCode_Horizon_È.Â));
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        if (this.áŒŠÆ != null) {
            this.áŒŠÆ.Ø();
        }
    }
    
    public void Ó() {
        (this.Ø­áŒŠá = new Ý(GuiStats.Ñ¢á)).Ø­áŒŠá(1, 1);
        (this.Âµá€ = new Ø­áŒŠá(GuiStats.Ñ¢á)).Ø­áŒŠá(1, 1);
        (this.Ó = new Â(GuiStats.Ñ¢á)).Ø­áŒŠá(1, 1);
        (this.à = new Âµá€(GuiStats.Ñ¢á)).Ø­áŒŠá(1, 1);
    }
    
    public void à() {
        this.ÇŽÉ.add(new GuiButton(0, GuiStats.Çªà¢ / 2 + 4, GuiStats.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiStats.Çªà¢ / 2 - 160, GuiStats.Ê - 52, 80, 20, I18n.HorizonCode_Horizon_È("stat.generalButton", new Object[0])));
        final GuiButton var1;
        this.ÇŽÉ.add(var1 = new GuiButton(2, GuiStats.Çªà¢ / 2 - 80, GuiStats.Ê - 52, 80, 20, I18n.HorizonCode_Horizon_È("stat.blocksButton", new Object[0])));
        final GuiButton var2;
        this.ÇŽÉ.add(var2 = new GuiButton(3, GuiStats.Çªà¢ / 2, GuiStats.Ê - 52, 80, 20, I18n.HorizonCode_Horizon_È("stat.itemsButton", new Object[0])));
        final GuiButton var3;
        this.ÇŽÉ.add(var3 = new GuiButton(4, GuiStats.Çªà¢ / 2 + 80, GuiStats.Ê - 52, 80, 20, I18n.HorizonCode_Horizon_È("stat.mobsButton", new Object[0])));
        if (this.Ó.HorizonCode_Horizon_È() == 0) {
            var1.µà = false;
        }
        if (this.Âµá€.HorizonCode_Horizon_È() == 0) {
            var2.µà = false;
        }
        if (this.à.HorizonCode_Horizon_È() == 0) {
            var3.µà = false;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 0) {
                GuiStats.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            else if (button.£à == 1) {
                this.áŒŠÆ = this.Ø­áŒŠá;
            }
            else if (button.£à == 3) {
                this.áŒŠÆ = this.Âµá€;
            }
            else if (button.£à == 2) {
                this.áŒŠÆ = this.Ó;
            }
            else if (button.£à == 4) {
                this.áŒŠÆ = this.à;
            }
            else {
                this.áŒŠÆ.HorizonCode_Horizon_È(button);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.áˆºÑ¢Õ) {
            this.£á();
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("multiplayer.downloadingStats", new Object[0]), GuiStats.Çªà¢ / 2, GuiStats.Ê / 2, 16777215);
            this.HorizonCode_Horizon_È(this.É, GuiStats.HorizonCode_Horizon_È[(int)(Minecraft.áƒ() / 150L % GuiStats.HorizonCode_Horizon_È.length)], GuiStats.Çªà¢ / 2, GuiStats.Ê / 2 + this.É.HorizonCode_Horizon_È * 2, 16777215);
        }
        else {
            this.áŒŠÆ.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
            this.HorizonCode_Horizon_È(this.É, this.Ý, GuiStats.Çªà¢ / 2, 20, 16777215);
            super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        }
    }
    
    @Override
    public void Â() {
        if (this.áˆºÑ¢Õ) {
            this.Ó();
            this.à();
            this.áŒŠÆ = this.Ø­áŒŠá;
            this.áˆºÑ¢Õ = false;
        }
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return !this.áˆºÑ¢Õ;
    }
    
    private void HorizonCode_Horizon_È(final int p_146521_1_, final int p_146521_2_, final Item_1028566121 p_146521_3_) {
        this.HorizonCode_Horizon_È(p_146521_1_ + 1, p_146521_2_ + 1);
        GlStateManager.ŠÄ();
        RenderHelper.Ý();
        this.ŒÏ.HorizonCode_Horizon_È(new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.Ñ¢á();
    }
    
    private void HorizonCode_Horizon_È(final int p_146531_1_, final int p_146531_2_) {
        this.Ý(p_146531_1_, p_146531_2_, 0, 0);
    }
    
    private void Ý(final int p_146527_1_, final int p_146527_2_, final int p_146527_3_, final int p_146527_4_) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiStats.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiStats.Ï­Ðƒà);
        final float var5 = 0.0078125f;
        final float var6 = 0.0078125f;
        final boolean var7 = true;
        final boolean var8 = true;
        final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var10 = var9.Ý();
        var10.Â();
        var10.HorizonCode_Horizon_È(p_146527_1_ + 0, p_146527_2_ + 18, GuiStats.ŠÄ, (p_146527_3_ + 0) * 0.0078125f, (p_146527_4_ + 18) * 0.0078125f);
        var10.HorizonCode_Horizon_È(p_146527_1_ + 18, p_146527_2_ + 18, GuiStats.ŠÄ, (p_146527_3_ + 18) * 0.0078125f, (p_146527_4_ + 18) * 0.0078125f);
        var10.HorizonCode_Horizon_È(p_146527_1_ + 18, p_146527_2_ + 0, GuiStats.ŠÄ, (p_146527_3_ + 18) * 0.0078125f, (p_146527_4_ + 0) * 0.0078125f);
        var10.HorizonCode_Horizon_È(p_146527_1_ + 0, p_146527_2_ + 0, GuiStats.ŠÄ, (p_146527_3_ + 0) * 0.0078125f, (p_146527_4_ + 0) * 0.0078125f);
        var9.Â();
    }
    
    abstract class HorizonCode_Horizon_È extends GuiSlot
    {
        protected int HorizonCode_Horizon_È;
        protected List Â;
        protected Comparator Šáƒ;
        protected int Ï­Ðƒà;
        protected int áŒŠà;
        private static final String Ñ¢á = "CL_00000730";
        
        protected HorizonCode_Horizon_È(final Minecraft mcIn) {
            super(mcIn, GuiStats.Çªà¢, GuiStats.Ê, 32, GuiStats.Ê - 64, 20);
            this.HorizonCode_Horizon_È = -1;
            this.Ï­Ðƒà = -1;
            this.HorizonCode_Horizon_È(false);
            this.HorizonCode_Horizon_È(true, 20);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return false;
        }
        
        @Override
        protected void Â() {
            GuiStats.this.£á();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            if (!Mouse.isButtonDown(0)) {
                this.HorizonCode_Horizon_È = -1;
            }
            if (this.HorizonCode_Horizon_È == 0) {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.HorizonCode_Horizon_È == 1) {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.HorizonCode_Horizon_È == 2) {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.Ï­Ðƒà != -1) {
                short var4 = 79;
                byte var5 = 18;
                if (this.Ï­Ðƒà == 1) {
                    var4 = 129;
                }
                else if (this.Ï­Ðƒà == 2) {
                    var4 = 179;
                }
                if (this.áŒŠà == 1) {
                    var5 = 36;
                }
                GuiStats.this.Ý(p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
            }
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_148132_1_, final int p_148132_2_) {
            this.HorizonCode_Horizon_È = -1;
            if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
                this.HorizonCode_Horizon_È = 0;
            }
            else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
                this.HorizonCode_Horizon_È = 1;
            }
            else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
                this.HorizonCode_Horizon_È = 2;
            }
            if (this.HorizonCode_Horizon_È >= 0) {
                this.Ø­áŒŠá(this.HorizonCode_Horizon_È);
                this.Ý.£ÂµÄ().HorizonCode_Horizon_È(PositionedSoundRecord.HorizonCode_Horizon_È(new ResourceLocation_1975012498("gui.button.press"), 1.0f));
            }
        }
        
        @Override
        protected final int HorizonCode_Horizon_È() {
            return this.Â.size();
        }
        
        protected final StatCrafting Â(final int p_148211_1_) {
            return this.Â.get(p_148211_1_);
        }
        
        protected abstract String Ý(final int p0);
        
        protected void HorizonCode_Horizon_È(final StatBase p_148209_1_, final int p_148209_2_, final int p_148209_3_, final boolean p_148209_4_) {
            if (p_148209_1_ != null) {
                final String var5 = p_148209_1_.HorizonCode_Horizon_È(GuiStats.this.Ø.HorizonCode_Horizon_È(p_148209_1_));
                Gui_1808253012.Â(GuiStats.this.É, var5, p_148209_2_ - GuiStats.this.É.HorizonCode_Horizon_È(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
            }
            else {
                final String var5 = "-";
                Gui_1808253012.Â(GuiStats.this.É, var5, p_148209_2_ - GuiStats.this.É.HorizonCode_Horizon_È(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
            }
        }
        
        @Override
        protected void Â(final int p_148142_1_, final int p_148142_2_) {
            if (p_148142_2_ >= this.Ó && p_148142_2_ <= this.à) {
                final int var3 = this.Ý(p_148142_1_, p_148142_2_);
                final int var4 = this.Ø­áŒŠá / 2 - 92 - 16;
                if (var3 >= 0) {
                    if (p_148142_1_ < var4 + 40 || p_148142_1_ > var4 + 40 + 20) {
                        return;
                    }
                    final StatCrafting var5 = this.Â(var3);
                    this.HorizonCode_Horizon_È(var5, p_148142_1_, p_148142_2_);
                }
                else {
                    String var6 = "";
                    if (p_148142_1_ >= var4 + 115 - 18 && p_148142_1_ <= var4 + 115) {
                        var6 = this.Ý(0);
                    }
                    else if (p_148142_1_ >= var4 + 165 - 18 && p_148142_1_ <= var4 + 165) {
                        var6 = this.Ý(1);
                    }
                    else {
                        if (p_148142_1_ < var4 + 215 - 18 || p_148142_1_ > var4 + 215) {
                            return;
                        }
                        var6 = this.Ý(2);
                    }
                    var6 = new StringBuilder().append(I18n.HorizonCode_Horizon_È(var6, new Object[0])).toString().trim();
                    if (var6.length() > 0) {
                        final int var7 = p_148142_1_ + 12;
                        final int var8 = p_148142_2_ - 12;
                        final int var9 = GuiStats.this.É.HorizonCode_Horizon_È(var6);
                        Gui_1808253012.HorizonCode_Horizon_È(var7 - 3, var8 - 3, var7 + var9 + 3, var8 + 8 + 3, -1073741824, -1073741824);
                        GuiStats.this.É.HorizonCode_Horizon_È(var6, var7, (float)var8, -1);
                    }
                }
            }
        }
        
        protected void HorizonCode_Horizon_È(final StatCrafting p_148213_1_, final int p_148213_2_, final int p_148213_3_) {
            if (p_148213_1_ != null) {
                final Item_1028566121 var4 = p_148213_1_.HorizonCode_Horizon_È();
                final ItemStack var5 = new ItemStack(var4);
                final String var6 = var5.ÂµÈ();
                final String var7 = new StringBuilder().append(I18n.HorizonCode_Horizon_È(String.valueOf(var6) + ".name", new Object[0])).toString().trim();
                if (var7.length() > 0) {
                    final int var8 = p_148213_2_ + 12;
                    final int var9 = p_148213_3_ - 12;
                    final int var10 = GuiStats.this.É.HorizonCode_Horizon_È(var7);
                    Gui_1808253012.HorizonCode_Horizon_È(var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
                    GuiStats.this.É.HorizonCode_Horizon_È(var7, var8, (float)var9, -1);
                }
            }
        }
        
        protected void Ø­áŒŠá(final int p_148212_1_) {
            if (p_148212_1_ != this.Ï­Ðƒà) {
                this.Ï­Ðƒà = p_148212_1_;
                this.áŒŠà = -1;
            }
            else if (this.áŒŠà == -1) {
                this.áŒŠà = 1;
            }
            else {
                this.Ï­Ðƒà = -1;
                this.áŒŠà = 0;
            }
            Collections.sort((List<Object>)this.Â, this.Šáƒ);
        }
    }
    
    class Â extends HorizonCode_Horizon_È
    {
        private static final String ŒÏ = "CL_00000724";
        
        public Â(final Minecraft mcIn) {
            super(mcIn);
            this.Â = Lists.newArrayList();
            for (final StatCrafting var4 : StatList.Âµá€) {
                boolean var5 = false;
                final int var6 = Item_1028566121.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È());
                if (GuiStats.this.Ø.HorizonCode_Horizon_È(var4) > 0) {
                    var5 = true;
                }
                else if (StatList.á€[var6] != null && GuiStats.this.Ø.HorizonCode_Horizon_È(StatList.á€[var6]) > 0) {
                    var5 = true;
                }
                else if (StatList.áƒ[var6] != null && GuiStats.this.Ø.HorizonCode_Horizon_È(StatList.áƒ[var6]) > 0) {
                    var5 = true;
                }
                if (var5) {
                    this.Â.add(var4);
                }
            }
            this.Šáƒ = new Comparator() {
                private static final String Â = "CL_00000725";
                
                public int HorizonCode_Horizon_È(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
                    final int var3 = Item_1028566121.HorizonCode_Horizon_È(p_compare_1_.HorizonCode_Horizon_È());
                    final int var4 = Item_1028566121.HorizonCode_Horizon_È(p_compare_2_.HorizonCode_Horizon_È());
                    StatBase var5 = null;
                    StatBase var6 = null;
                    if (GuiStats.Â.this.Ï­Ðƒà == 2) {
                        var5 = StatList.É[var3];
                        var6 = StatList.É[var4];
                    }
                    else if (GuiStats.Â.this.Ï­Ðƒà == 0) {
                        var5 = StatList.áƒ[var3];
                        var6 = StatList.áƒ[var4];
                    }
                    else if (GuiStats.Â.this.Ï­Ðƒà == 1) {
                        var5 = StatList.á€[var3];
                        var6 = StatList.á€[var4];
                    }
                    if (var5 != null || var6 != null) {
                        if (var5 == null) {
                            return 1;
                        }
                        if (var6 == null) {
                            return -1;
                        }
                        final int var7 = GuiStats.this.Ø.HorizonCode_Horizon_È(var5);
                        final int var8 = GuiStats.this.Ø.HorizonCode_Horizon_È(var6);
                        if (var7 != var8) {
                            return (var7 - var8) * GuiStats.Â.this.áŒŠà;
                        }
                    }
                    return var3 - var4;
                }
                
                @Override
                public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                    return this.HorizonCode_Horizon_È((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
                }
            };
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            super.HorizonCode_Horizon_È(p_148129_1_, p_148129_2_, p_148129_3_);
            if (this.HorizonCode_Horizon_È == 0) {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
            }
            if (this.HorizonCode_Horizon_È == 1) {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
            }
            if (this.HorizonCode_Horizon_È == 2) {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
            }
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final StatCrafting var7 = this.Â(p_180791_1_);
            final Item_1028566121 var8 = var7.HorizonCode_Horizon_È();
            GuiStats.this.HorizonCode_Horizon_È(p_180791_2_ + 40, p_180791_3_, var8);
            final int var9 = Item_1028566121.HorizonCode_Horizon_È(var8);
            this.HorizonCode_Horizon_È(StatList.áƒ[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
            this.HorizonCode_Horizon_È(StatList.á€[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
            this.HorizonCode_Horizon_È(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
        }
        
        @Override
        protected String Ý(final int p_148210_1_) {
            return (p_148210_1_ == 0) ? "stat.crafted" : ((p_148210_1_ == 1) ? "stat.used" : "stat.mined");
        }
    }
    
    class Ý extends GuiSlot
    {
        private static final String Â = "CL_00000726";
        
        public Ý(final Minecraft mcIn) {
            super(mcIn, GuiStats.Çªà¢, GuiStats.Ê, 32, GuiStats.Ê - 64, 10);
            this.HorizonCode_Horizon_È(false);
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return StatList.Ý.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return false;
        }
        
        @Override
        protected int Ó() {
            return this.HorizonCode_Horizon_È() * 10;
        }
        
        @Override
        protected void Â() {
            GuiStats.this.£á();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final StatBase var7 = StatList.Ý.get(p_180791_1_);
            Gui_1808253012.Â(GuiStats.this.É, var7.Âµá€().Ø(), p_180791_2_ + 2, p_180791_3_ + 1, (p_180791_1_ % 2 == 0) ? 16777215 : 9474192);
            final String var8 = var7.HorizonCode_Horizon_È(GuiStats.this.Ø.HorizonCode_Horizon_È(var7));
            Gui_1808253012.Â(GuiStats.this.É, var8, p_180791_2_ + 2 + 213 - GuiStats.this.É.HorizonCode_Horizon_È(var8), p_180791_3_ + 1, (p_180791_1_ % 2 == 0) ? 16777215 : 9474192);
        }
    }
    
    class Ø­áŒŠá extends HorizonCode_Horizon_È
    {
        private static final String ŒÏ = "CL_00000727";
        
        public Ø­áŒŠá(final Minecraft mcIn) {
            super(mcIn);
            this.Â = Lists.newArrayList();
            for (final StatCrafting var4 : StatList.Ø­áŒŠá) {
                boolean var5 = false;
                final int var6 = Item_1028566121.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È());
                if (GuiStats.this.Ø.HorizonCode_Horizon_È(var4) > 0) {
                    var5 = true;
                }
                else if (StatList.Õ[var6] != null && GuiStats.this.Ø.HorizonCode_Horizon_È(StatList.Õ[var6]) > 0) {
                    var5 = true;
                }
                else if (StatList.áƒ[var6] != null && GuiStats.this.Ø.HorizonCode_Horizon_È(StatList.áƒ[var6]) > 0) {
                    var5 = true;
                }
                if (var5) {
                    this.Â.add(var4);
                }
            }
            this.Šáƒ = new Comparator() {
                private static final String Â = "CL_00000728";
                
                public int HorizonCode_Horizon_È(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
                    final int var3 = Item_1028566121.HorizonCode_Horizon_È(p_compare_1_.HorizonCode_Horizon_È());
                    final int var4 = Item_1028566121.HorizonCode_Horizon_È(p_compare_2_.HorizonCode_Horizon_È());
                    StatBase var5 = null;
                    StatBase var6 = null;
                    if (Ø­áŒŠá.this.Ï­Ðƒà == 0) {
                        var5 = StatList.Õ[var3];
                        var6 = StatList.Õ[var4];
                    }
                    else if (Ø­áŒŠá.this.Ï­Ðƒà == 1) {
                        var5 = StatList.áƒ[var3];
                        var6 = StatList.áƒ[var4];
                    }
                    else if (Ø­áŒŠá.this.Ï­Ðƒà == 2) {
                        var5 = StatList.á€[var3];
                        var6 = StatList.á€[var4];
                    }
                    if (var5 != null || var6 != null) {
                        if (var5 == null) {
                            return 1;
                        }
                        if (var6 == null) {
                            return -1;
                        }
                        final int var7 = GuiStats.this.Ø.HorizonCode_Horizon_È(var5);
                        final int var8 = GuiStats.this.Ø.HorizonCode_Horizon_È(var6);
                        if (var7 != var8) {
                            return (var7 - var8) * Ø­áŒŠá.this.áŒŠà;
                        }
                    }
                    return var3 - var4;
                }
                
                @Override
                public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                    return this.HorizonCode_Horizon_È((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
                }
            };
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            super.HorizonCode_Horizon_È(p_148129_1_, p_148129_2_, p_148129_3_);
            if (this.HorizonCode_Horizon_È == 0) {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
            }
            if (this.HorizonCode_Horizon_È == 1) {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
            }
            if (this.HorizonCode_Horizon_È == 2) {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
            }
            else {
                GuiStats.this.Ý(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
            }
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final StatCrafting var7 = this.Â(p_180791_1_);
            final Item_1028566121 var8 = var7.HorizonCode_Horizon_È();
            GuiStats.this.HorizonCode_Horizon_È(p_180791_2_ + 40, p_180791_3_, var8);
            final int var9 = Item_1028566121.HorizonCode_Horizon_È(var8);
            this.HorizonCode_Horizon_È(StatList.Õ[var9], p_180791_2_ + 115, p_180791_3_, p_180791_1_ % 2 == 0);
            this.HorizonCode_Horizon_È(StatList.áƒ[var9], p_180791_2_ + 165, p_180791_3_, p_180791_1_ % 2 == 0);
            this.HorizonCode_Horizon_È(var7, p_180791_2_ + 215, p_180791_3_, p_180791_1_ % 2 == 0);
        }
        
        @Override
        protected String Ý(final int p_148210_1_) {
            return (p_148210_1_ == 1) ? "stat.crafted" : ((p_148210_1_ == 2) ? "stat.used" : "stat.depleted");
        }
    }
    
    class Âµá€ extends GuiSlot
    {
        private final List Â;
        private static final String Šáƒ = "CL_00000729";
        
        public Âµá€(final Minecraft mcIn) {
            super(mcIn, GuiStats.Çªà¢, GuiStats.Ê, 32, GuiStats.Ê - 64, GuiStats.this.É.HorizonCode_Horizon_È * 4);
            this.Â = Lists.newArrayList();
            this.HorizonCode_Horizon_È(false);
            for (final EntityList.HorizonCode_Horizon_È var4 : EntityList.HorizonCode_Horizon_È.values()) {
                if (GuiStats.this.Ø.HorizonCode_Horizon_È(var4.Ø­áŒŠá) > 0 || GuiStats.this.Ø.HorizonCode_Horizon_È(var4.Âµá€) > 0) {
                    this.Â.add(var4);
                }
            }
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return this.Â.size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return false;
        }
        
        @Override
        protected int Ó() {
            return this.HorizonCode_Horizon_È() * GuiStats.this.É.HorizonCode_Horizon_È * 4;
        }
        
        @Override
        protected void Â() {
            GuiStats.this.£á();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final EntityList.HorizonCode_Horizon_È var7 = this.Â.get(p_180791_1_);
            final String var8 = I18n.HorizonCode_Horizon_È("entity." + EntityList.Â(var7.HorizonCode_Horizon_È) + ".name", new Object[0]);
            final int var9 = GuiStats.this.Ø.HorizonCode_Horizon_È(var7.Ø­áŒŠá);
            final int var10 = GuiStats.this.Ø.HorizonCode_Horizon_È(var7.Âµá€);
            String var11 = I18n.HorizonCode_Horizon_È("stat.entityKills", var9, var8);
            String var12 = I18n.HorizonCode_Horizon_È("stat.entityKilledBy", var8, var10);
            if (var9 == 0) {
                var11 = I18n.HorizonCode_Horizon_È("stat.entityKills.none", var8);
            }
            if (var10 == 0) {
                var12 = I18n.HorizonCode_Horizon_È("stat.entityKilledBy.none", var8);
            }
            Gui_1808253012.Â(GuiStats.this.É, var8, p_180791_2_ + 2 - 10, p_180791_3_ + 1, 16777215);
            Gui_1808253012.Â(GuiStats.this.É, var11, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.this.É.HorizonCode_Horizon_È, (var9 == 0) ? 6316128 : 9474192);
            Gui_1808253012.Â(GuiStats.this.É, var12, p_180791_2_ + 2, p_180791_3_ + 1 + GuiStats.this.É.HorizonCode_Horizon_È * 2, (var10 == 0) ? 6316128 : 9474192);
        }
    }
}
