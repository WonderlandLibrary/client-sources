package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;

public abstract class GuiSlot
{
    protected final Minecraft Ý;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    protected int Ó;
    protected int à;
    protected int Ø;
    protected int áŒŠÆ;
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    protected final int áˆºÑ¢Õ;
    private int Â;
    private int Šáƒ;
    protected int ÂµÈ;
    protected int á;
    protected boolean ˆÏ­;
    protected float £á;
    protected float Å;
    protected float £à;
    protected int µà;
    protected long ˆà;
    protected boolean ¥Æ;
    protected boolean Ø­à;
    protected boolean µÕ;
    protected int Æ;
    private boolean Ï­Ðƒà;
    private static final String áŒŠà = "CL_00000679";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiSlot(final Minecraft mcIn, final int width, final int height, final int p_i1052_4_, final int p_i1052_5_, final int p_i1052_6_) {
        this.ˆÏ­ = true;
        this.£á = -2.0f;
        this.µà = -1;
        this.¥Æ = true;
        this.Ø­à = true;
        this.Ï­Ðƒà = true;
        this.Ý = mcIn;
        this.Ø­áŒŠá = width;
        this.Âµá€ = height;
        this.Ó = p_i1052_4_;
        this.à = p_i1052_5_;
        this.áˆºÑ¢Õ = p_i1052_6_;
        this.áŒŠÆ = 0;
        this.Ø = width;
    }
    
    public void Â(final int p_148122_1_, final int p_148122_2_, final int p_148122_3_, final int p_148122_4_) {
        this.Ø­áŒŠá = p_148122_1_;
        this.Âµá€ = p_148122_2_;
        this.Ó = p_148122_3_;
        this.à = p_148122_4_;
        this.áŒŠÆ = 0;
        this.Ø = p_148122_1_;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_148130_1_) {
        this.Ø­à = p_148130_1_;
    }
    
    protected void HorizonCode_Horizon_È(final boolean p_148133_1_, final int p_148133_2_) {
        this.µÕ = p_148133_1_;
        this.Æ = p_148133_2_;
        if (!p_148133_1_) {
            this.Æ = 0;
        }
    }
    
    protected abstract int HorizonCode_Horizon_È();
    
    protected abstract void HorizonCode_Horizon_È(final int p0, final boolean p1, final int p2, final int p3);
    
    protected abstract boolean HorizonCode_Horizon_È(final int p0);
    
    protected int Ó() {
        return this.HorizonCode_Horizon_È() * this.áˆºÑ¢Õ + this.Æ;
    }
    
    protected abstract void Â();
    
    protected void HorizonCode_Horizon_È(final int p_178040_1_, final int p_178040_2_, final int p_178040_3_) {
    }
    
    protected abstract void HorizonCode_Horizon_È(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    protected void HorizonCode_Horizon_È(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
    }
    
    protected void HorizonCode_Horizon_È(final int p_148132_1_, final int p_148132_2_) {
    }
    
    protected void Â(final int p_148142_1_, final int p_148142_2_) {
    }
    
    public int Ý(final int p_148124_1_, final int p_148124_2_) {
        final int var3 = this.áŒŠÆ + this.Ø­áŒŠá / 2 - this.o_() / 2;
        final int var4 = this.áŒŠÆ + this.Ø­áŒŠá / 2 + this.o_() / 2;
        final int var5 = p_148124_2_ - this.Ó - this.Æ + (int)this.£à - 4;
        final int var6 = var5 / this.áˆºÑ¢Õ;
        return (p_148124_1_ < this.à() && p_148124_1_ >= var3 && p_148124_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.HorizonCode_Horizon_È()) ? var6 : -1;
    }
    
    public void Ø­áŒŠá(final int p_148134_1_, final int p_148134_2_) {
        this.Â = p_148134_1_;
        this.Šáƒ = p_148134_2_;
    }
    
    protected void á() {
        int var1 = this.ˆÏ­();
        if (var1 < 0) {
            var1 /= 2;
        }
        if (!this.ˆÏ­ && var1 < 0) {
            var1 = 0;
        }
        this.£à = MathHelper.HorizonCode_Horizon_È(this.£à, 0.0f, var1);
    }
    
    public int ˆÏ­() {
        return Math.max(0, this.Ó() - (this.à - this.Ó - 4));
    }
    
    public int £á() {
        return (int)this.£à;
    }
    
    public boolean Âµá€(final int p_148141_1_) {
        return p_148141_1_ >= this.Ó && p_148141_1_ <= this.à && this.ÂµÈ >= this.áŒŠÆ && this.ÂµÈ <= this.Ø;
    }
    
    public void Ó(final int p_148145_1_) {
        this.£à += p_148145_1_;
        this.á();
        this.£á = -2.0f;
    }
    
    public void HorizonCode_Horizon_È(final GuiButton p_148147_1_) {
        if (p_148147_1_.µà) {
            if (p_148147_1_.£à == this.Â) {
                this.£à -= this.áˆºÑ¢Õ * 2 / 3;
                this.£á = -2.0f;
                this.á();
            }
            else if (p_148147_1_.£à == this.Šáƒ) {
                this.£à += this.áˆºÑ¢Õ * 2 / 3;
                this.£á = -2.0f;
                this.á();
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
        if (this.¥Æ) {
            this.ÂµÈ = p_148128_1_;
            this.á = p_148128_2_;
            final int var4 = this.à();
            final int var5 = var4 + 6;
            this.á();
            GlStateManager.Ó();
            GlStateManager.£á();
            final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var7 = var6.Ý();
            final float var8 = 32.0f;
            var7.Â();
            var7.Ý(2105376);
            var7.HorizonCode_Horizon_È(this.áŒŠÆ, this.à, 0.0, this.áŒŠÆ / var8, (this.à + (int)this.£à) / var8);
            var7.HorizonCode_Horizon_È(this.Ø, this.à, 0.0, this.Ø / var8, (this.à + (int)this.£à) / var8);
            var7.HorizonCode_Horizon_È(this.Ø, this.Ó, 0.0, this.Ø / var8, (this.Ó + (int)this.£à) / var8);
            var7.HorizonCode_Horizon_È(this.áŒŠÆ, this.Ó, 0.0, this.áŒŠÆ / var8, (this.Ó + (int)this.£à) / var8);
            var6.Â();
            final int var9 = this.áŒŠÆ + this.Ø­áŒŠá / 2 - this.o_() / 2 + 2;
            final int var10 = this.Ó + 4 - (int)this.£à;
            if (this.µÕ) {
                this.HorizonCode_Horizon_È(var9, var10, var6);
            }
            if (!(Minecraft.áŒŠà().¥Æ instanceof GuiScreenResourcePacks)) {
                if (this.Ý.áŒŠÆ == null) {
                    final ScaledResolution scaledRes = new ScaledResolution(this.Ý, this.Ý.Ó, this.Ý.à);
                    this.Ý.¥à().HorizonCode_Horizon_È(GuiSlot.HorizonCode_Horizon_È);
                    Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
                    Gui_1808253012.HorizonCode_Horizon_È(0, 0, this.Ø­áŒŠá, this.Âµá€, 1627389951, 1610612736);
                    Gui_1808253012.HorizonCode_Horizon_È(0, 0, this.Ø­áŒŠá, this.Âµá€, 1325400064);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(0, 0, this.Ø­áŒŠá, this.Âµá€, -6969946);
                }
            }
            this.HorizonCode_Horizon_È(var9, var10, p_148128_1_, p_148128_2_);
            GlStateManager.áŒŠÆ();
            final byte var11 = 4;
            this.Ý(0, this.Ó, 255, 255);
            this.Ý(this.à, this.Âµá€, 255, 255);
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 0, 1);
            GlStateManager.Ý();
            GlStateManager.áˆºÑ¢Õ(7425);
            GlStateManager.Æ();
            final int var12 = this.ˆÏ­();
            if (var12 > 0) {
                int var13 = (this.à - this.Ó) * (this.à - this.Ó) / this.Ó();
                var13 = MathHelper.HorizonCode_Horizon_È(var13, 32, this.à - this.Ó - 8);
                int var14 = (int)this.£à * (this.à - this.Ó - var13) / var12 + this.Ó;
                if (var14 < this.Ó) {
                    var14 = this.Ó;
                }
                var7.Â();
                var7.HorizonCode_Horizon_È(8421504, 255);
                var7.HorizonCode_Horizon_È(var4, var14 + var13, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, var14 + var13, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, var14, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var4, var14, 0.0, 0.0, 0.0);
                var6.Â();
                var7.Â();
                var7.HorizonCode_Horizon_È(12632256, 255);
                var7.HorizonCode_Horizon_È(var4, var14 + var13 - 1, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var5 - 1, var14 + var13 - 1, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var5 - 1, var14, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var4, var14, 0.0, 0.0, 0.0);
                var6.Â();
            }
            this.Â(p_148128_1_, p_148128_2_);
            GlStateManager.µÕ();
            GlStateManager.áˆºÑ¢Õ(7424);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.ÂµÈ();
        }
    }
    
    public void Ø() {
        if (this.Âµá€(this.á)) {
            if (Mouse.isButtonDown(0) && this.Å()) {
                if (this.£á == -1.0f) {
                    boolean var1 = true;
                    if (this.á >= this.Ó && this.á <= this.à) {
                        final int var2 = this.Ø­áŒŠá / 2 - this.o_() / 2;
                        final int var3 = this.Ø­áŒŠá / 2 + this.o_() / 2;
                        final int var4 = this.á - this.Ó - this.Æ + (int)this.£à - 4;
                        final int var5 = var4 / this.áˆºÑ¢Õ;
                        if (this.ÂµÈ >= var2 && this.ÂµÈ <= var3 && var5 >= 0 && var4 >= 0 && var5 < this.HorizonCode_Horizon_È()) {
                            final boolean var6 = var5 == this.µà && Minecraft.áƒ() - this.ˆà < 250L;
                            this.HorizonCode_Horizon_È(var5, var6, this.ÂµÈ, this.á);
                            this.µà = var5;
                            this.ˆà = Minecraft.áƒ();
                        }
                        else if (this.ÂµÈ >= var2 && this.ÂµÈ <= var3 && var4 < 0) {
                            this.HorizonCode_Horizon_È(this.ÂµÈ - var2, this.á - this.Ó + (int)this.£à - 4);
                            var1 = false;
                        }
                        final int var7 = this.à();
                        final int var8 = var7 + 6;
                        if (this.ÂµÈ >= var7 && this.ÂµÈ <= var8) {
                            this.Å = -1.0f;
                            int var9 = this.ˆÏ­();
                            if (var9 < 1) {
                                var9 = 1;
                            }
                            int var10 = (this.à - this.Ó) * (this.à - this.Ó) / this.Ó();
                            var10 = MathHelper.HorizonCode_Horizon_È(var10, 32, this.à - this.Ó - 8);
                            this.Å /= (this.à - this.Ó - var10) / var9;
                        }
                        else {
                            this.Å = 1.0f;
                        }
                        if (var1) {
                            this.£á = this.á;
                        }
                        else {
                            this.£á = -2.0f;
                        }
                    }
                    else {
                        this.£á = -2.0f;
                    }
                }
                else if (this.£á >= 0.0f) {
                    this.£à -= (this.á - this.£á) * this.Å;
                    this.£á = this.á;
                }
            }
            else {
                this.£á = -1.0f;
            }
            int var11 = Mouse.getEventDWheel();
            if (var11 != 0) {
                if (var11 > 0) {
                    var11 = -1;
                }
                else if (var11 < 0) {
                    var11 = 1;
                }
                this.£à += var11 * this.áˆºÑ¢Õ / 2;
            }
        }
    }
    
    public void Â(final boolean p_148143_1_) {
        this.Ï­Ðƒà = p_148143_1_;
    }
    
    public boolean Å() {
        return this.Ï­Ðƒà;
    }
    
    public int o_() {
        return 220;
    }
    
    protected void HorizonCode_Horizon_È(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_, final int p_148120_4_) {
        final int var5 = this.HorizonCode_Horizon_È();
        final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var7 = var6.Ý();
        for (int var8 = 0; var8 < var5; ++var8) {
            final int var9 = p_148120_2_ + var8 * this.áˆºÑ¢Õ + this.Æ;
            final int var10 = this.áˆºÑ¢Õ - 4;
            if (var9 > this.à || var9 + var10 < this.Ó) {
                this.HorizonCode_Horizon_È(var8, p_148120_1_, var9);
            }
            if (this.Ø­à && this.HorizonCode_Horizon_È(var8)) {
                final int var11 = this.áŒŠÆ + (this.Ø­áŒŠá / 2 - this.o_() / 2);
                final int var12 = this.áŒŠÆ + this.Ø­áŒŠá / 2 + this.o_() / 2;
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.Æ();
                var7.Â();
                var7.Ý(8421504);
                var7.HorizonCode_Horizon_È(var11, var9 + var10 + 2, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var12, var9 + var10 + 2, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var12, var9 - 2, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var11, var9 - 2, 0.0, 0.0, 0.0);
                var7.Ý(0);
                var7.HorizonCode_Horizon_È(var11 + 1, var9 + var10 + 1, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var12 - 1, var9 + var10 + 1, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var12 - 1, var9 - 1, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var11 + 1, var9 - 1, 0.0, 0.0, 0.0);
                var6.Â();
                GlStateManager.µÕ();
            }
            this.HorizonCode_Horizon_È(var8, p_148120_1_, var9, var10, p_148120_3_, p_148120_4_);
        }
    }
    
    protected int à() {
        return this.Ø­áŒŠá / 2 + 124;
    }
    
    protected void Ý(final int p_148136_1_, final int p_148136_2_, final int p_148136_3_, final int p_148136_4_) {
        final Tessellator var5 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var6 = var5.Ý();
        this.Ý.¥à().HorizonCode_Horizon_È(Gui_1808253012.Šáƒ);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        final float var7 = 32.0f;
        var6.Â();
        var6.HorizonCode_Horizon_È(4210752, p_148136_4_);
        var6.HorizonCode_Horizon_È(this.áŒŠÆ, p_148136_2_, 0.0, 0.0, p_148136_2_ / var7);
        var6.HorizonCode_Horizon_È(this.áŒŠÆ + this.Ø­áŒŠá, p_148136_2_, 0.0, this.Ø­áŒŠá / var7, p_148136_2_ / var7);
        var6.HorizonCode_Horizon_È(4210752, p_148136_3_);
        var6.HorizonCode_Horizon_È(this.áŒŠÆ + this.Ø­áŒŠá, p_148136_1_, 0.0, this.Ø­áŒŠá / var7, p_148136_1_ / var7);
        var6.HorizonCode_Horizon_È(this.áŒŠÆ, p_148136_1_, 0.0, 0.0, p_148136_1_ / var7);
        var5.Â();
    }
    
    public void à(final int p_148140_1_) {
        this.áŒŠÆ = p_148140_1_;
        this.Ø = p_148140_1_ + this.Ø­áŒŠá;
    }
    
    public int £à() {
        return this.áˆºÑ¢Õ;
    }
}
