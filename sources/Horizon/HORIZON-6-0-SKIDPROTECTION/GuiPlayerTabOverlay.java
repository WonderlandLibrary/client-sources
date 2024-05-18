package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.ComparisonChain;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import com.google.common.collect.Ordering;

public class GuiPlayerTabOverlay extends Gui_1808253012
{
    private static final Ordering HorizonCode_Horizon_È;
    private final Minecraft Â;
    private final GuiIngame Ý;
    private IChatComponent Ø­áŒŠá;
    private IChatComponent Âµá€;
    private long Ó;
    private boolean à;
    private static final String Ø = "CL_00001943";
    
    static {
        HorizonCode_Horizon_È = Ordering.from((Comparator)new HorizonCode_Horizon_È(null));
    }
    
    public GuiPlayerTabOverlay(final Minecraft mcIn, final GuiIngame p_i45529_2_) {
        this.Â = mcIn;
        this.Ý = p_i45529_2_;
    }
    
    public String HorizonCode_Horizon_È(final NetworkPlayerInfo p_175243_1_) {
        return (p_175243_1_.áˆºÑ¢Õ() != null) ? p_175243_1_.áˆºÑ¢Õ().áŒŠÆ() : ScorePlayerTeam.HorizonCode_Horizon_È(p_175243_1_.Ø(), p_175243_1_.HorizonCode_Horizon_È().getName());
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175246_1_) {
        if (p_175246_1_ && !this.à) {
            this.Ó = Minecraft.áƒ();
        }
        this.à = p_175246_1_;
    }
    
    public void HorizonCode_Horizon_È(final int p_175249_1_, final Scoreboard p_175249_2_, final ScoreObjective p_175249_3_) {
        final NetHandlerPlayClient var4 = this.Â.á.HorizonCode_Horizon_È;
        List var5 = GuiPlayerTabOverlay.HorizonCode_Horizon_È.sortedCopy((Iterable)var4.Ý());
        int var6 = 0;
        int var7 = 0;
        for (final NetworkPlayerInfo var9 : var5) {
            int var10 = this.Â.µà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(var9));
            var6 = Math.max(var6, var10);
            if (p_175249_3_ != null && p_175249_3_.Âµá€() != IScoreObjectiveCriteria.HorizonCode_Horizon_È.Â) {
                var10 = this.Â.µà.HorizonCode_Horizon_È(" " + p_175249_2_.Â(var9.HorizonCode_Horizon_È().getName(), p_175249_3_).Â());
                var7 = Math.max(var7, var10);
            }
        }
        var5 = var5.subList(0, Math.min(var5.size(), 80));
        int var10;
        int var12;
        int var11;
        for (var11 = (var12 = var5.size()), var10 = 1; var12 > 20; var12 = (var11 + var10 - 1) / var10) {
            ++var10;
        }
        final boolean var13 = this.Â.Ê() || this.Â.µÕ().Â().Ø­áŒŠá();
        int var14;
        if (p_175249_3_ != null) {
            if (p_175249_3_.Âµá€() == IScoreObjectiveCriteria.HorizonCode_Horizon_È.Â) {
                var14 = 90;
            }
            else {
                var14 = var7;
            }
        }
        else {
            var14 = 0;
        }
        final int var15 = Math.min(var10 * ((var13 ? 9 : 0) + var6 + var14 + 13), p_175249_1_ - 50) / var10;
        final int var16 = p_175249_1_ / 2 - (var15 * var10 + (var10 - 1) * 5) / 2;
        int var17 = 10;
        int var18 = var15 * var10 + (var10 - 1) * 5;
        List var19 = null;
        List var20 = null;
        if (this.Âµá€ != null) {
            var19 = this.Â.µà.Ý(this.Âµá€.áŒŠÆ(), p_175249_1_ - 50);
            for (final String var22 : var19) {
                var18 = Math.max(var18, this.Â.µà.HorizonCode_Horizon_È(var22));
            }
        }
        if (this.Ø­áŒŠá != null) {
            var20 = this.Â.µà.Ý(this.Ø­áŒŠá.áŒŠÆ(), p_175249_1_ - 50);
            for (final String var22 : var20) {
                var18 = Math.max(var18, this.Â.µà.HorizonCode_Horizon_È(var22));
            }
        }
        if (var19 != null) {
            Gui_1808253012.HorizonCode_Horizon_È(p_175249_1_ / 2 - var18 / 2 - 1, var17 - 1, p_175249_1_ / 2 + var18 / 2 + 1, var17 + var19.size() * this.Â.µà.HorizonCode_Horizon_È, Integer.MIN_VALUE);
            for (final String var22 : var19) {
                final int var23 = this.Â.µà.HorizonCode_Horizon_È(var22);
                this.Â.µà.HorizonCode_Horizon_È(var22, p_175249_1_ / 2 - var23 / 2, (float)var17, -1);
                var17 += this.Â.µà.HorizonCode_Horizon_È;
            }
            ++var17;
        }
        Gui_1808253012.HorizonCode_Horizon_È(p_175249_1_ / 2 - var18 / 2 - 1, var17 - 1, p_175249_1_ / 2 + var18 / 2 + 1, var17 + var12 * 9, Integer.MIN_VALUE);
        for (int var24 = 0; var24 < var11; ++var24) {
            final int var25 = var24 / var12;
            final int var23 = var24 % var12;
            int var26 = var16 + var25 * var15 + var25 * 5;
            final int var27 = var17 + var23 * 9;
            Gui_1808253012.HorizonCode_Horizon_È(var26, var27, var26 + var15, var27 + 8, 553648127);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            if (var24 < var5.size()) {
                final NetworkPlayerInfo var28 = var5.get(var24);
                String var29 = this.HorizonCode_Horizon_È(var28);
                if (var13) {
                    this.Â.¥à().HorizonCode_Horizon_È(var28.Ó());
                    Gui_1808253012.HorizonCode_Horizon_È(var26, var27, 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                    final EntityPlayer var30 = this.Â.áŒŠÆ.HorizonCode_Horizon_È(var28.HorizonCode_Horizon_È().getId());
                    if (var30 != null && var30.HorizonCode_Horizon_È(EnumPlayerModelParts.à)) {
                        Gui_1808253012.HorizonCode_Horizon_È(var26, var27, 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                    }
                    var26 += 9;
                }
                if (var28.Â() == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                    var29 = EnumChatFormatting.µÕ + var29;
                    this.Â.µà.HorizonCode_Horizon_È(var29, var26, (float)var27, -1862270977);
                }
                else {
                    this.Â.µà.HorizonCode_Horizon_È(var29, var26, (float)var27, -1);
                }
                if (p_175249_3_ != null && var28.Â() != WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                    final int var31 = var26 + var6 + 1;
                    final int var32 = var31 + var14;
                    if (var32 - var31 > 5) {
                        this.HorizonCode_Horizon_È(p_175249_3_, var27, var28.HorizonCode_Horizon_È().getName(), var31, var32, var28);
                    }
                }
                this.HorizonCode_Horizon_È(var15, var26 - (var13 ? 9 : 0), var27, var28);
            }
        }
        if (var20 != null) {
            var17 += var12 * 9 + 1;
            Gui_1808253012.HorizonCode_Horizon_È(p_175249_1_ / 2 - var18 / 2 - 1, var17 - 1, p_175249_1_ / 2 + var18 / 2 + 1, var17 + var20.size() * this.Â.µà.HorizonCode_Horizon_È, Integer.MIN_VALUE);
            for (final String var22 : var20) {
                final int var23 = this.Â.µà.HorizonCode_Horizon_È(var22);
                this.Â.µà.HorizonCode_Horizon_È(var22, p_175249_1_ / 2 - var23 / 2, (float)var17, -1);
                var17 += this.Â.µà.HorizonCode_Horizon_È;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_, final NetworkPlayerInfo p_175245_4_) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Â.¥à().HorizonCode_Horizon_È(GuiPlayerTabOverlay.áŒŠà);
        final byte var5 = 0;
        final boolean var6 = false;
        byte var7;
        if (p_175245_4_.Ý() < 0) {
            var7 = 5;
        }
        else if (p_175245_4_.Ý() < 150) {
            var7 = 0;
        }
        else if (p_175245_4_.Ý() < 300) {
            var7 = 1;
        }
        else if (p_175245_4_.Ý() < 600) {
            var7 = 2;
        }
        else if (p_175245_4_.Ý() < 1000) {
            var7 = 3;
        }
        else {
            var7 = 4;
        }
        GuiPlayerTabOverlay.ŠÄ += 100.0f;
        this.Â(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + var5 * 10, 176 + var7 * 8, 10, 8);
        GuiPlayerTabOverlay.ŠÄ -= 100.0f;
    }
    
    private void HorizonCode_Horizon_È(final ScoreObjective p_175247_1_, final int p_175247_2_, final String p_175247_3_, final int p_175247_4_, final int p_175247_5_, final NetworkPlayerInfo p_175247_6_) {
        final int var7 = p_175247_1_.HorizonCode_Horizon_È().Â(p_175247_3_, p_175247_1_).Â();
        if (p_175247_1_.Âµá€() == IScoreObjectiveCriteria.HorizonCode_Horizon_È.Â) {
            this.Â.¥à().HorizonCode_Horizon_È(GuiPlayerTabOverlay.áŒŠà);
            if (this.Ó == p_175247_6_.Å()) {
                if (var7 < p_175247_6_.ÂµÈ()) {
                    p_175247_6_.HorizonCode_Horizon_È(Minecraft.áƒ());
                    p_175247_6_.Â((long)(this.Ý.Âµá€() + 20));
                }
                else if (var7 > p_175247_6_.ÂµÈ()) {
                    p_175247_6_.HorizonCode_Horizon_È(Minecraft.áƒ());
                    p_175247_6_.Â((long)(this.Ý.Âµá€() + 10));
                }
            }
            if (Minecraft.áƒ() - p_175247_6_.ˆÏ­() > 1000L || this.Ó != p_175247_6_.Å()) {
                p_175247_6_.Â(var7);
                p_175247_6_.Ý(var7);
                p_175247_6_.HorizonCode_Horizon_È(Minecraft.áƒ());
            }
            p_175247_6_.Ý(this.Ó);
            p_175247_6_.Â(var7);
            final int var8 = MathHelper.Ó(Math.max(var7, p_175247_6_.á()) / 2.0f);
            final int var9 = Math.max(MathHelper.Ó(var7 / 2), Math.max(MathHelper.Ó(p_175247_6_.á() / 2), 10));
            final boolean var10 = p_175247_6_.£á() > this.Ý.Âµá€() && (p_175247_6_.£á() - this.Ý.Âµá€()) / 3L % 2L == 1L;
            if (var8 > 0) {
                final float var11 = Math.min((p_175247_5_ - p_175247_4_ - 4) / var9, 9.0f);
                if (var11 > 3.0f) {
                    for (int var12 = var8; var12 < var9; ++var12) {
                        this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);
                    }
                    for (int var12 = 0; var12 < var8; ++var12) {
                        this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);
                        if (var10) {
                            if (var12 * 2 + 1 < p_175247_6_.á()) {
                                this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, 70, 0, 9, 9);
                            }
                            if (var12 * 2 + 1 == p_175247_6_.á()) {
                                this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (var12 * 2 + 1 < var7) {
                            this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, (var12 >= 10) ? 160 : 52, 0, 9, 9);
                        }
                        if (var12 * 2 + 1 == var7) {
                            this.HorizonCode_Horizon_È(p_175247_4_ + var12 * var11, p_175247_2_, (var12 >= 10) ? 169 : 61, 0, 9, 9);
                        }
                    }
                }
                else {
                    final float var13 = MathHelper.HorizonCode_Horizon_È(var7 / 20.0f, 0.0f, 1.0f);
                    final int var14 = (int)((1.0f - var13) * 255.0f) << 16 | (int)(var13 * 255.0f) << 8;
                    String var15 = new StringBuilder().append(var7 / 2.0f).toString();
                    if (p_175247_5_ - this.Â.µà.HorizonCode_Horizon_È(String.valueOf(var15) + "hp") >= p_175247_4_) {
                        var15 = String.valueOf(var15) + "hp";
                    }
                    this.Â.µà.HorizonCode_Horizon_È(var15, (p_175247_5_ + p_175247_4_) / 2 - this.Â.µà.HorizonCode_Horizon_È(var15) / 2, (float)p_175247_2_, var14);
                }
            }
        }
        else {
            final String var16 = new StringBuilder().append(EnumChatFormatting.Å).append(var7).toString();
            this.Â.µà.HorizonCode_Horizon_È(var16, p_175247_5_ - this.Â.µà.HorizonCode_Horizon_È(var16), (float)p_175247_2_, 16777215);
        }
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent p_175248_1_) {
        this.Ø­áŒŠá = p_175248_1_;
    }
    
    public void Â(final IChatComponent p_175244_1_) {
        this.Âµá€ = p_175244_1_;
    }
    
    static class HorizonCode_Horizon_È implements Comparator
    {
        private static final String HorizonCode_Horizon_È = "CL_00001941";
        
        private HorizonCode_Horizon_È() {
        }
        
        public int HorizonCode_Horizon_È(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
            final ScorePlayerTeam var3 = p_178952_1_.Ø();
            final ScorePlayerTeam var4 = p_178952_2_.Ø();
            return ComparisonChain.start().compareTrueFirst(p_178952_1_.Â() != WorldSettings.HorizonCode_Horizon_È.Âµá€, p_178952_2_.Â() != WorldSettings.HorizonCode_Horizon_È.Âµá€).compare((Comparable)((var3 != null) ? var3.HorizonCode_Horizon_È() : ""), (Comparable)((var4 != null) ? var4.HorizonCode_Horizon_È() : "")).compare((Comparable)p_178952_1_.HorizonCode_Horizon_È().getName(), (Comparable)p_178952_2_.HorizonCode_Horizon_È().getName()).result();
        }
        
        @Override
        public int compare(final Object p_compare_1_, final Object p_compare_2_) {
            return this.HorizonCode_Horizon_È((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
        }
        
        HorizonCode_Horizon_È(final Object p_i45528_1_) {
            this();
        }
    }
}
