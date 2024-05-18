package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui_1808253012
{
    private static final Logger áŒŠÆ;
    private final Minecraft áˆºÑ¢Õ;
    private final List ÂµÈ;
    private final List á;
    protected final List HorizonCode_Horizon_È;
    protected int Â;
    protected boolean Ý;
    private static final String ˆÏ­ = "CL_00000669";
    protected int Ø­áŒŠá;
    protected int Âµá€;
    protected int Ó;
    protected int à;
    protected boolean Ø;
    
    static {
        áŒŠÆ = LogManager.getLogger();
    }
    
    public GuiNewChat(final Minecraft mcIn) {
        this.ÂµÈ = Lists.newArrayList();
        this.á = Lists.newArrayList();
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.áˆºÑ¢Õ = mcIn;
    }
    
    public void HorizonCode_Horizon_È(final int p_146230_1_) {
        if (this.áˆºÑ¢Õ.ŠÄ.Ñ¢à != EntityPlayer.HorizonCode_Horizon_È.Ý) {
            final int var2 = this.áˆºÑ¢Õ();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.HorizonCode_Horizon_È.size();
            final float var6 = this.áˆºÑ¢Õ.ŠÄ.Šà * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.Ó()) {
                    var3 = true;
                }
                final float var7 = this.áŒŠÆ();
                final int var8 = MathHelper.Ó(this.à() / var7);
                GlStateManager.Çªà¢();
                GlStateManager.Â(2.0f, 20.0f, 0.0f);
                GlStateManager.HorizonCode_Horizon_È(var7, var7, 1.0f);
                for (int var9 = 0; var9 + this.Â < this.HorizonCode_Horizon_È.size() && var9 < var2; ++var9) {
                    final ChatLine var10 = this.HorizonCode_Horizon_È.get(var9 + this.Â);
                    if (var10 != null) {
                        final int var11 = p_146230_1_ - var10.Â();
                        if (var11 < 200 || var3) {
                            double var12 = var11 / 200.0;
                            var12 = 1.0 - var12;
                            var12 *= 10.0;
                            var12 = MathHelper.HorizonCode_Horizon_È(var12, 0.0, 1.0);
                            var12 *= var12;
                            int var13 = (int)(255.0 * var12);
                            if (var3) {
                                var13 = 255;
                            }
                            var13 *= (int)var6;
                            ++var4;
                            if (var13 > 3) {
                                final byte var14 = 0;
                                final int var15 = -var9 * 9;
                                Gui_1808253012.HorizonCode_Horizon_È(var14, var15 - 9, var14 + var8 + 4, var15, var13 / 2 << 24);
                                final String var16 = var10.HorizonCode_Horizon_È().áŒŠÆ();
                                GlStateManager.á();
                                this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È(var16, var14, (float)(var15 - 8), 16777215 + (var13 << 24));
                                GlStateManager.Ý();
                                GlStateManager.ÂµÈ();
                            }
                        }
                    }
                }
                if (var3) {
                    final int var9 = this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È;
                    GlStateManager.Â(-3.0f, 0.0f, 0.0f);
                    final int var17 = var5 * var9 + var5;
                    final int var11 = var4 * var9 + var4;
                    final int var18 = this.Â * var11 / var5;
                    final int var19 = var11 * var11 / var17;
                    if (var17 != var11) {
                        final int var13 = (var18 > 0) ? 170 : 96;
                        final int var20 = this.Ý ? 13382451 : 3355562;
                        Gui_1808253012.HorizonCode_Horizon_È(0, -var18, 2, -var18 - var19, var20 + (var13 << 24));
                        Gui_1808253012.HorizonCode_Horizon_È(2, -var18, 1, -var18 - var19, 13421772 + (var13 << 24));
                    }
                }
                GlStateManager.Ê();
            }
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.clear();
        this.á.clear();
        this.ÂµÈ.clear();
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent p_146227_1_) {
        this.HorizonCode_Horizon_È(p_146227_1_, 0);
    }
    
    public int Â() {
        return this.Ø() / 9;
    }
    
    public void HorizonCode_Horizon_È(IChatComponent p_146234_1_, final int p_146234_2_) {
        if (p_146234_1_.Ø().contains("Der Server ist voll. Nur Premium-Spieler / Teammitglieder / YouTuber können ihn betreten http://buy.gommehd.net/")) {
            p_146234_1_ = new ChatComponentText("§7GommeHD - §aServer Full");
        }
        this.HorizonCode_Horizon_È(p_146234_1_, p_146234_2_, this.áˆºÑ¢Õ.Šáƒ.Âµá€(), false);
        GuiNewChat.áŒŠÆ.info("[CHAT] " + p_146234_1_.Ø());
    }
    
    private void HorizonCode_Horizon_È(final IChatComponent p_146237_1_, final int p_146237_2_, final int p_146237_3_, final boolean p_146237_4_) {
        if (p_146237_2_ != 0) {
            this.Ý(p_146237_2_);
        }
        final int var5 = MathHelper.Ø­áŒŠá(this.à() / this.áŒŠÆ());
        final List var6 = GuiUtilRenderComponents.HorizonCode_Horizon_È(p_146237_1_, var5, this.áˆºÑ¢Õ.µà, false, false);
        final boolean var7 = this.Ó();
        for (final IChatComponent var9 : var6) {
            if (var7 && this.Â > 0) {
                this.Ý = true;
                this.Â(1);
            }
            this.HorizonCode_Horizon_È.add(0, new ChatLine(p_146237_3_, var9, p_146237_2_));
        }
        while (this.HorizonCode_Horizon_È.size() > 100) {
            this.HorizonCode_Horizon_È.remove(this.HorizonCode_Horizon_È.size() - 1);
        }
        if (!p_146237_4_) {
            this.á.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
            while (this.á.size() > 100) {
                this.á.remove(this.á.size() - 1);
            }
        }
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È.clear();
        this.Âµá€();
        for (int var1 = this.á.size() - 1; var1 >= 0; --var1) {
            final ChatLine var2 = this.á.get(var1);
            this.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È(), var2.Ý(), var2.Â(), true);
        }
    }
    
    public List Ø­áŒŠá() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final String p_146239_1_) {
        if (this.ÂµÈ.isEmpty() || !this.ÂµÈ.get(this.ÂµÈ.size() - 1).equals(p_146239_1_)) {
            this.ÂµÈ.add(p_146239_1_);
        }
    }
    
    public void Âµá€() {
        this.Â = 0;
        this.Ý = false;
    }
    
    public void Â(final int p_146229_1_) {
        this.Â += p_146229_1_;
        final int var2 = this.HorizonCode_Horizon_È.size();
        if (this.Â > var2 - this.áˆºÑ¢Õ()) {
            this.Â = var2 - this.áˆºÑ¢Õ();
        }
        if (this.Â <= 0) {
            this.Â = 0;
            this.Ý = false;
        }
    }
    
    public IChatComponent HorizonCode_Horizon_È(final int p_146236_1_, final int p_146236_2_) {
        if (!this.Ó()) {
            return null;
        }
        final ScaledResolution var3 = new ScaledResolution(this.áˆºÑ¢Õ, this.áˆºÑ¢Õ.Ó, this.áˆºÑ¢Õ.à);
        final int var4 = var3.Âµá€();
        final float var5 = this.áŒŠÆ();
        int var6 = p_146236_1_ / var4 - 3;
        int var7 = p_146236_2_ / var4 - 27;
        var6 = MathHelper.Ø­áŒŠá(var6 / var5);
        var7 = MathHelper.Ø­áŒŠá(var7 / var5);
        if (var6 < 0 || var7 < 0) {
            return null;
        }
        final int var8 = Math.min(this.áˆºÑ¢Õ(), this.HorizonCode_Horizon_È.size());
        if (var6 <= MathHelper.Ø­áŒŠá(this.à() / this.áŒŠÆ()) && var7 < this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È * var8 + var8) {
            final int var9 = var7 / this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È + this.Â;
            if (var9 >= 0 && var9 < this.HorizonCode_Horizon_È.size()) {
                final ChatLine var10 = this.HorizonCode_Horizon_È.get(var9);
                int var11 = 0;
                for (final IChatComponent var13 : var10.HorizonCode_Horizon_È()) {
                    if (var13 instanceof ChatComponentText) {
                        var11 += this.áˆºÑ¢Õ.µà.HorizonCode_Horizon_È(GuiUtilRenderComponents.HorizonCode_Horizon_È(((ChatComponentText)var13).HorizonCode_Horizon_È(), false));
                        if (var11 > var6) {
                            return var13;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    public boolean Ó() {
        return this.áˆºÑ¢Õ.¥Æ instanceof GuiChat;
    }
    
    public void Ý(final int p_146242_1_) {
        Iterator var2 = this.HorizonCode_Horizon_È.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.Ý() == p_146242_1_) {
                var2.remove();
            }
        }
        var2 = this.á.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.Ý() == p_146242_1_) {
                var2.remove();
                break;
            }
        }
    }
    
    public int à() {
        return HorizonCode_Horizon_È(this.áˆºÑ¢Õ.ŠÄ.áŒŠÔ);
    }
    
    public int Ø() {
        return Â(this.Ó() ? this.áˆºÑ¢Õ.ŠÄ.£Ø­à : this.áˆºÑ¢Õ.ŠÄ.ŠÕ);
    }
    
    public float áŒŠÆ() {
        return this.áˆºÑ¢Õ.ŠÄ.Ø­Æ;
    }
    
    public static int HorizonCode_Horizon_È(final float p_146233_0_) {
        final short var1 = 320;
        final byte var2 = 40;
        return MathHelper.Ø­áŒŠá(p_146233_0_ * (var1 - var2) + var2);
    }
    
    public static int Â(final float p_146243_0_) {
        final short var1 = 180;
        final byte var2 = 20;
        return MathHelper.Ø­áŒŠá(p_146243_0_ * (var1 - var2) + var2);
    }
    
    public int áˆºÑ¢Õ() {
        return this.Ø() / 9;
    }
}
