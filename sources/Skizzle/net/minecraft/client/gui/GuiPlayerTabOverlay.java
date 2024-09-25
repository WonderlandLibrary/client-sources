/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Ordering
 */
package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

public class GuiPlayerTabOverlay
extends Gui {
    private static final Ordering field_175252_a = Ordering.from((Comparator)new PlayerComparator(null));
    private final Minecraft field_175250_f;
    private final GuiIngame field_175251_g;
    private IChatComponent footer;
    private IChatComponent header;
    private long field_175253_j;
    private boolean field_175254_k;
    private static final String __OBFID = "CL_00001943";

    public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame p_i45529_2_) {
        this.field_175250_f = mcIn;
        this.field_175251_g = p_i45529_2_;
    }

    public static String getPlayerName(NetworkPlayerInfo p_175243_1_) {
        return p_175243_1_.func_178854_k() != null ? p_175243_1_.func_178854_k().getFormattedText() : ScorePlayerTeam.formatPlayerName(p_175243_1_.func_178850_i(), p_175243_1_.func_178845_a().getName());
    }

    public void func_175246_a(boolean p_175246_1_) {
        if (p_175246_1_ && !this.field_175254_k) {
            this.field_175253_j = Minecraft.getSystemTime();
        }
        this.field_175254_k = p_175246_1_;
    }

    /*
     * Exception decompiling
     */
    public void func_175249_a(int p_175249_1_, Scoreboard p_175249_2_, ScoreObjective p_175249_3_) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl532 : GOTO - null : trying to set 5 previously set to 4
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:203)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1542)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:400)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }

    protected void func_175245_a(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo p_175245_4_) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_175250_f.getTextureManager().bindTexture(icons);
        int var5 = 0;
        int var7 = p_175245_4_.getResponseTime() < 0 ? 5 : (p_175245_4_.getResponseTime() < 150 ? 0 : (p_175245_4_.getResponseTime() < 300 ? 1 : (p_175245_4_.getResponseTime() < 600 ? 2 : (p_175245_4_.getResponseTime() < 1000 ? 3 : 4))));
        this.zLevel += 100.0f;
        this.drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + var5 * 10, 176 + var7 * 8, 10, 8);
        this.zLevel -= 100.0f;
    }

    private void func_175247_a(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
        int var7 = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();
        if (p_175247_1_.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            boolean var10;
            this.field_175250_f.getTextureManager().bindTexture(icons);
            if (this.field_175253_j == p_175247_6_.func_178855_p()) {
                if (var7 < p_175247_6_.func_178835_l()) {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b(this.field_175251_g.getUpdateCounter() + 20);
                } else if (var7 > p_175247_6_.func_178835_l()) {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b(this.field_175251_g.getUpdateCounter() + 10);
                }
            }
            if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.field_175253_j != p_175247_6_.func_178855_p()) {
                p_175247_6_.func_178836_b(var7);
                p_175247_6_.func_178857_c(var7);
                p_175247_6_.func_178846_a(Minecraft.getSystemTime());
            }
            p_175247_6_.func_178843_c(this.field_175253_j);
            p_175247_6_.func_178836_b(var7);
            int var8 = MathHelper.ceiling_float_int((float)Math.max(var7, p_175247_6_.func_178860_m()) / 2.0f);
            int var9 = Math.max(MathHelper.ceiling_float_int(var7 / 2), Math.max(MathHelper.ceiling_float_int(p_175247_6_.func_178860_m() / 2), 10));
            boolean bl = var10 = p_175247_6_.func_178858_o() > (long)this.field_175251_g.getUpdateCounter() && (p_175247_6_.func_178858_o() - (long)this.field_175251_g.getUpdateCounter()) / 3L % 2L == 1L;
            if (var8 > 0) {
                float var11 = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)var9, 9.0f);
                if (var11 > 3.0f) {
                    int var12;
                    for (var12 = var8; var12 < var9; ++var12) {
                        this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);
                    }
                    for (var12 = 0; var12 < var8; ++var12) {
                        this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, var10 ? 25 : 16, 0, 9, 9);
                        if (var10) {
                            if (var12 * 2 + 1 < p_175247_6_.func_178860_m()) {
                                this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, 70, 0, 9, 9);
                            }
                            if (var12 * 2 + 1 == p_175247_6_.func_178860_m()) {
                                this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (var12 * 2 + 1 < var7) {
                            this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, var12 >= 10 ? 160 : 52, 0, 9, 9);
                        }
                        if (var12 * 2 + 1 != var7) continue;
                        this.func_175174_a((float)p_175247_4_ + (float)var12 * var11, p_175247_2_, var12 >= 10 ? 169 : 61, 0, 9, 9);
                    }
                } else {
                    float var16 = MathHelper.clamp_float((float)var7 / 20.0f, 0.0f, 1.0f);
                    int var13 = (int)((1.0f - var16) * 255.0f) << 16 | (int)(var16 * 255.0f) << 8;
                    String var14 = "" + (float)var7 / 2.0f;
                    if (p_175247_5_ - this.field_175250_f.fontRendererObj.getStringWidth(String.valueOf(var14) + "hp") >= p_175247_4_) {
                        var14 = String.valueOf(var14) + "hp";
                    }
                    this.field_175250_f.fontRendererObj.drawStringWithShadow(var14, (p_175247_5_ + p_175247_4_) / 2 - this.field_175250_f.fontRendererObj.getStringWidth(var14) / 2, p_175247_2_, var13);
                }
            }
        } else {
            String var15 = "" + (Object)((Object)EnumChatFormatting.YELLOW) + var7;
            this.field_175250_f.fontRendererObj.drawStringWithShadow(var15, p_175247_5_ - this.field_175250_f.fontRendererObj.getStringWidth(var15), p_175247_2_, 0xFFFFFF);
        }
    }

    public void setFooter(IChatComponent p_175248_1_) {
        this.footer = p_175248_1_;
    }

    public void setHeader(IChatComponent p_175244_1_) {
        this.header = p_175244_1_;
    }

    static class PlayerComparator
    implements Comparator {
        private static final String __OBFID = "CL_00001941";

        private PlayerComparator() {
        }

        public int func_178952_a(NetworkPlayerInfo p_178952_1_, NetworkPlayerInfo p_178952_2_) {
            ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
            ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
            return ComparisonChain.start().compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((Object)(var3 != null ? var3.getRegisteredName() : "")), (Comparable)((Object)(var4 != null ? var4.getRegisteredName() : ""))).compare((Comparable)((Object)p_178952_1_.func_178845_a().getName()), (Comparable)((Object)p_178952_2_.func_178845_a().getName())).result();
        }

        public int compare(Object p_compare_1_, Object p_compare_2_) {
            return this.func_178952_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
        }

        PlayerComparator(Object p_i45528_1_) {
            this();
        }
    }
}

