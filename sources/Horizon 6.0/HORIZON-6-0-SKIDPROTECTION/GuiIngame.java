package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import com.google.common.collect.Iterables;
import com.google.common.base.Predicate;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import java.awt.Color;
import org.lwjgl.input.Keyboard;
import java.util.Random;

public class GuiIngame extends Gui_1808253012
{
    private static final ResourceLocation_1975012498 Ý;
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private static final ResourceLocation_1975012498 Âµá€;
    private final Random Ó;
    private final Minecraft à;
    private final RenderItem Ø;
    public GuiNewChat HorizonCode_Horizon_È;
    private final GuiStreamIndicator áŒŠÆ;
    private int áˆºÑ¢Õ;
    private String ÂµÈ;
    private int á;
    private boolean ˆÏ­;
    public float Â;
    private int £á;
    private ItemStack Å;
    private final GuiOverlayDebug £à;
    private final GuiSpectator µà;
    private final GuiPlayerTabOverlay ˆà;
    private int ¥Æ;
    private String Ø­à;
    private String µÕ;
    private int Æ;
    private int Ñ¢á;
    private int ŒÏ;
    private int Çªà¢;
    private int Ê;
    private long ÇŽÉ;
    private long ˆá;
    private static final String ÇŽÕ = "CL_00000661";
    
    static {
        Ý = new ResourceLocation_1975012498("textures/misc/vignette.png");
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/gui/widgets.png");
        Âµá€ = new ResourceLocation_1975012498("textures/misc/pumpkinblur.png");
    }
    
    public GuiIngame(final Minecraft mcIn) {
        this.Ó = new Random();
        this.ÂµÈ = "";
        this.Â = 1.0f;
        this.Ø­à = "";
        this.µÕ = "";
        this.Çªà¢ = 0;
        this.Ê = 0;
        this.ÇŽÉ = 0L;
        this.ˆá = 0L;
        this.à = mcIn;
        this.Ø = mcIn.áˆºÏ();
        this.£à = new GuiOverlayDebug(mcIn);
        this.µà = new GuiSpectator(mcIn);
        this.HorizonCode_Horizon_È = new GuiNewChat(mcIn);
        this.áŒŠÆ = new GuiStreamIndicator(mcIn);
        this.ˆà = new GuiPlayerTabOverlay(mcIn, this);
        this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È() {
        this.Æ = 10;
        this.Ñ¢á = 70;
        this.ŒÏ = 20;
    }
    
    public void HorizonCode_Horizon_È(final float p_175180_1_) {
        final ScaledResolution var2 = new ScaledResolution(this.à, this.à.Ó, this.à.à);
        final int var3 = var2.HorizonCode_Horizon_È();
        final int var4 = var2.Â();
        this.à.µÕ.Ø();
        GlStateManager.á();
        if (Minecraft.Šáƒ()) {
            this.HorizonCode_Horizon_È(this.à.á.Â(p_175180_1_), var2);
        }
        else {
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        }
        final ItemStack var5 = this.à.á.Ø­Ñ¢Ï­Ø­áˆº.Ý(3);
        if (this.à.ŠÄ.µÏ == 0 && var5 != null && var5.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ)) {
            this.Âµá€(var2);
        }
        if (!this.à.á.HorizonCode_Horizon_È(Potion.ÂµÈ)) {
            final float var6 = this.à.á.ˆÏ­ + (this.à.á.á - this.à.á.ˆÏ­) * p_175180_1_;
            if (var6 > 0.0f) {
                this.Â(var6, var2);
            }
        }
        if (this.à.Âµá€.HorizonCode_Horizon_È()) {
            this.µà.HorizonCode_Horizon_È(var2, p_175180_1_);
        }
        else {
            this.HorizonCode_Horizon_È(var2, p_175180_1_);
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.à.¥à().HorizonCode_Horizon_È(GuiIngame.áŒŠà);
        GlStateManager.á();
        if (this.Â()) {
            GlStateManager.HorizonCode_Horizon_È(775, 769, 1, 0);
            GlStateManager.Ø­áŒŠá();
            this.Â(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
        }
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        this.à.ÇŽÕ.HorizonCode_Horizon_È("bossHealth");
        this.áŒŠÆ();
        this.à.ÇŽÕ.Â();
        if (this.à.Âµá€.Â()) {
            this.Ø­áŒŠá(var2);
        }
        GlStateManager.ÂµÈ();
        if (this.à.á.Ï­áˆºÓ() > 0) {
            this.à.ÇŽÕ.HorizonCode_Horizon_È("sleep");
            GlStateManager.áŒŠÆ();
            GlStateManager.Ý();
            final int var7 = this.à.á.Ï­áˆºÓ();
            float var8 = var7 / 100.0f;
            if (var8 > 1.0f) {
                var8 = 1.0f - (var7 - 100) / 10.0f;
            }
            final int var9 = (int)(220.0f * var8) << 24 | 0x101020;
            Gui_1808253012.HorizonCode_Horizon_È(0, 0, var3, var4, var9);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.áˆºÑ¢Õ();
            this.à.ÇŽÕ.Â();
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        final int var7 = var3 / 2 - 91;
        if (this.à.á.Ñ¢á()) {
            this.HorizonCode_Horizon_È(var2, var7);
        }
        else if (this.à.Âµá€.Ó()) {
            this.Â(var2, var7);
        }
        if (this.à.ŠÄ.Ñ¢Ó && !this.à.Âµá€.HorizonCode_Horizon_È()) {
            this.HorizonCode_Horizon_È(var2);
        }
        else if (this.à.á.Ø­áŒŠá()) {
            this.µà.HorizonCode_Horizon_È(var2);
        }
        if (this.à.Ø­à()) {
            this.Â(var2);
        }
        if (this.à.ŠÄ.µÐƒÓ) {
            this.£à.HorizonCode_Horizon_È(var2);
        }
        else {
            if (!Horizon.ÂµÈ) {
                HorizonRenderer.HorizonCode_Horizon_È();
                HorizonRenderer.Ý();
                Horizon.à¢.áˆºÏ.Â();
            }
            if (Horizon.ÂµÈ && Keyboard.isKeyDown(208)) {
                Horizon.à¢.áˆºÏ.Â();
            }
        }
        if (this.á > 0) {
            this.à.ÇŽÕ.HorizonCode_Horizon_È("overlayMessage");
            final float var8 = this.á - p_175180_1_;
            int var9 = (int)(var8 * 255.0f / 20.0f);
            if (var9 > 255) {
                var9 = 255;
            }
            if (var9 > 8) {
                GlStateManager.Çªà¢();
                GlStateManager.Â(var3 / 2, var4 - 68, 0.0f);
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                int var10 = 16777215;
                if (this.ˆÏ­) {
                    var10 = (Color.HSBtoRGB(var8 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF);
                }
                this.Ó().HorizonCode_Horizon_È(this.ÂµÈ, -this.Ó().HorizonCode_Horizon_È(this.ÂµÈ) / 2, -4, var10 + (var9 << 24 & 0xFF000000));
                GlStateManager.ÂµÈ();
                GlStateManager.Ê();
            }
            this.à.ÇŽÕ.Â();
        }
        if (this.¥Æ > 0) {
            this.à.ÇŽÕ.HorizonCode_Horizon_È("titleAndSubtitle");
            final float var8 = this.¥Æ - p_175180_1_;
            int var9 = 255;
            if (this.¥Æ > this.ŒÏ + this.Ñ¢á) {
                final float var11 = this.Æ + this.Ñ¢á + this.ŒÏ - var8;
                var9 = (int)(var11 * 255.0f / this.Æ);
            }
            if (this.¥Æ <= this.ŒÏ) {
                var9 = (int)(var8 * 255.0f / this.ŒÏ);
            }
            var9 = MathHelper.HorizonCode_Horizon_È(var9, 0, 255);
            if (var9 > 8) {
                GlStateManager.Çªà¢();
                GlStateManager.Â(var3 / 2, var4 / 2, 0.0f);
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                GlStateManager.Çªà¢();
                GlStateManager.HorizonCode_Horizon_È(4.0f, 4.0f, 4.0f);
                final int var10 = var9 << 24 & 0xFF000000;
                this.Ó().HorizonCode_Horizon_È(this.Ø­à, -this.Ó().HorizonCode_Horizon_È(this.Ø­à) / 2, -10.0f, 0xFFFFFF | var10, true);
                GlStateManager.Ê();
                GlStateManager.Çªà¢();
                GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
                this.Ó().HorizonCode_Horizon_È(this.µÕ, -this.Ó().HorizonCode_Horizon_È(this.µÕ) / 2, 5.0f, 0xFFFFFF | var10, true);
                GlStateManager.Ê();
                GlStateManager.ÂµÈ();
                GlStateManager.Ê();
            }
            this.à.ÇŽÕ.Â();
        }
        final Scoreboard var12 = this.à.áŒŠÆ.à¢();
        ScoreObjective var13 = null;
        final ScorePlayerTeam var14 = var12.Ó(this.à.á.v_());
        if (var14 != null) {
            final int var15 = var14.ÂµÈ().HorizonCode_Horizon_È();
            if (var15 >= 0) {
                var13 = var12.HorizonCode_Horizon_È(3 + var15);
            }
        }
        ScoreObjective var16 = (var13 != null) ? var13 : var12.HorizonCode_Horizon_È(1);
        if (var16 != null) {
            this.HorizonCode_Horizon_È(var16, var2);
        }
        Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È();
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Ý();
        GlStateManager.Çªà¢();
        GlStateManager.Â(0.0f, var4 - 48, 0.0f);
        this.à.ÇŽÕ.HorizonCode_Horizon_È("chat");
        if (Horizon.ÂµÈ) {
            Display.setTitle("Minecraft 1.8");
        }
        else {
            Display.setTitle(String.valueOf("Horizon") + " " + Horizon.Ý);
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.áˆºÑ¢Õ);
        this.à.ÇŽÕ.Â();
        GlStateManager.Ê();
        var16 = var12.HorizonCode_Horizon_È(0);
        if (this.à.ŠÄ.µÂ.Ø­áŒŠá() && (!this.à.Ê() || this.à.á.HorizonCode_Horizon_È.Ý().size() > 1 || var16 != null)) {
            this.ˆà.HorizonCode_Horizon_È(true);
            this.ˆà.HorizonCode_Horizon_È(var3, var12, var16);
        }
        else {
            this.ˆà.HorizonCode_Horizon_È(false);
        }
        if (!this.à.ŠÄ.µÐƒÓ) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È();
            if (!Horizon.ÂµÈ) {
                GL11.glPushMatrix();
                HorizonRenderer.HorizonCode_Horizon_È(Mouse.getX(), Mouse.getY());
                GL11.glPopMatrix();
            }
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ó();
        GlStateManager.Ø­áŒŠá();
    }
    
    protected void HorizonCode_Horizon_È(final ScaledResolution p_180479_1_, final float p_180479_2_) {
        if (this.à.ÇŽá€() instanceof EntityPlayer) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.à.¥à().HorizonCode_Horizon_È(GuiIngame.Ø­áŒŠá);
            final EntityPlayer var3 = (EntityPlayer)this.à.ÇŽá€();
            final int var4 = p_180479_1_.HorizonCode_Horizon_È() / 2;
            final float var5 = GuiIngame.ŠÄ;
            GuiIngame.ŠÄ = -90.0f;
            this.Â(var4 - 91, p_180479_1_.Â() - 22, 0, 0, 182, 22);
            this.Â(var4 - 91 - 1 + var3.Ø­Ñ¢Ï­Ø­áˆº.Ý * 20, p_180479_1_.Â() - 22 - 1, 0, 22, 24, 22);
            GuiIngame.ŠÄ = var5;
            GlStateManager.ŠÄ();
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
            RenderHelper.Ý();
            for (int var6 = 0; var6 < 9; ++var6) {
                final int var7 = p_180479_1_.HorizonCode_Horizon_È() / 2 - 90 + var6 * 20 + 2;
                final int var8 = p_180479_1_.Â() - 16 - 3;
                this.HorizonCode_Horizon_È(var6, var7, var8, p_180479_2_, var3);
            }
            RenderHelper.HorizonCode_Horizon_È();
            GlStateManager.Ñ¢á();
            GlStateManager.ÂµÈ();
        }
    }
    
    public void HorizonCode_Horizon_È(final ScaledResolution p_175186_1_, final int p_175186_2_) {
        this.à.ÇŽÕ.HorizonCode_Horizon_È("jumpBar");
        this.à.¥à().HorizonCode_Horizon_È(Gui_1808253012.áŒŠà);
        final float var3 = this.à.á.ŒÏ();
        final short var4 = 182;
        final int var5 = (int)(var3 * (var4 + 1));
        final int var6 = p_175186_1_.Â() - 32 + 3;
        this.Â(p_175186_2_, var6, 0, 84, var4, 5);
        if (var5 > 0) {
            this.Â(p_175186_2_, var6, 0, 89, var5, 5);
        }
        this.à.ÇŽÕ.Â();
    }
    
    public void Â(final ScaledResolution p_175176_1_, final int p_175176_2_) {
        this.à.ÇŽÕ.HorizonCode_Horizon_È("expBar");
        this.à.¥à().HorizonCode_Horizon_È(Gui_1808253012.áŒŠà);
        final int var3 = this.à.á.ÇªÉ();
        if (var3 > 0) {
            final short var4 = 182;
            final int var5 = (int)(this.à.á.ŒÓ * (var4 + 1));
            final int var6 = p_175176_1_.Â() - 32 + 3;
            this.Â(p_175176_2_, var6, 0, 64, var4, 5);
            if (var5 > 0) {
                this.Â(p_175176_2_, var6, 0, 69, var5, 5);
            }
        }
        this.à.ÇŽÕ.Â();
        if (this.à.á.áŒŠÉ > 0) {
            this.à.ÇŽÕ.HorizonCode_Horizon_È("expLevel");
            final int var7 = 8453920;
            final String var8 = new StringBuilder().append(this.à.á.áŒŠÉ).toString();
            final int var6 = (p_175176_1_.HorizonCode_Horizon_È() - this.Ó().HorizonCode_Horizon_È(var8)) / 2;
            final int var9 = p_175176_1_.Â() - 31 - 4;
            final boolean var10 = false;
            this.Ó().HorizonCode_Horizon_È(var8, var6 + 1, var9, 0);
            this.Ó().HorizonCode_Horizon_È(var8, var6 - 1, var9, 0);
            this.Ó().HorizonCode_Horizon_È(var8, var6, var9 + 1, 0);
            this.Ó().HorizonCode_Horizon_È(var8, var6, var9 - 1, 0);
            this.Ó().HorizonCode_Horizon_È(var8, var6, var9, var7);
            this.à.ÇŽÕ.Â();
        }
    }
    
    public void HorizonCode_Horizon_È(final ScaledResolution p_175182_1_) {
        this.à.ÇŽÕ.HorizonCode_Horizon_È("toolHighlight");
        if (this.£á > 0 && this.Å != null) {
            String var2 = this.Å.µà();
            if (this.Å.¥Æ()) {
                var2 = EnumChatFormatting.µÕ + var2;
            }
            final int var3 = (p_175182_1_.HorizonCode_Horizon_È() - this.Ó().HorizonCode_Horizon_È(var2)) / 2;
            int var4 = p_175182_1_.Â() - 59;
            if (!this.à.Âµá€.Â()) {
                var4 += 14;
            }
            int var5 = (int)(this.£á * 256.0f / 10.0f);
            if (var5 > 255) {
                var5 = 255;
            }
            if (var5 > 0) {
                GlStateManager.Çªà¢();
                GlStateManager.á();
                GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
                this.Ó().HorizonCode_Horizon_È(var2, var3, (float)var4, 16777215 + (var5 << 24));
                GlStateManager.ÂµÈ();
                GlStateManager.Ê();
            }
        }
        this.à.ÇŽÕ.Â();
    }
    
    public void Â(final ScaledResolution p_175185_1_) {
        this.à.ÇŽÕ.HorizonCode_Horizon_È("demo");
        String var2 = "";
        if (this.à.áŒŠÆ.Šáƒ() >= 120500L) {
            var2 = I18n.HorizonCode_Horizon_È("demo.demoExpired", new Object[0]);
        }
        else {
            var2 = I18n.HorizonCode_Horizon_È("demo.remainingTime", StringUtils.HorizonCode_Horizon_È((int)(120500L - this.à.áŒŠÆ.Šáƒ())));
        }
        final int var3 = this.Ó().HorizonCode_Horizon_È(var2);
        this.Ó().HorizonCode_Horizon_È(var2, p_175185_1_.HorizonCode_Horizon_È() - var3 - 10, 5.0f, 16777215);
        this.à.ÇŽÕ.Â();
    }
    
    protected boolean Â() {
        if (this.à.ŠÄ.µÐƒÓ && !this.à.á.¥Ðƒá() && !this.à.ŠÄ.Ðƒáƒ) {
            return false;
        }
        if (!this.à.Âµá€.HorizonCode_Horizon_È()) {
            return true;
        }
        if (this.à.£á != null) {
            return true;
        }
        if (this.à.áŒŠà != null && this.à.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
            final BlockPos var1 = this.à.áŒŠà.HorizonCode_Horizon_È();
            if (this.à.áŒŠÆ.HorizonCode_Horizon_È(var1) instanceof IInventory) {
                return true;
            }
        }
        return false;
    }
    
    public void Ý(final ScaledResolution p_180478_1_) {
        this.áŒŠÆ.HorizonCode_Horizon_È(p_180478_1_.HorizonCode_Horizon_È() - 10, 10);
    }
    
    private void HorizonCode_Horizon_È(final ScoreObjective p_180475_1_, final ScaledResolution p_180475_2_) {
        final Scoreboard var3 = p_180475_1_.HorizonCode_Horizon_È();
        final Collection var4 = var3.HorizonCode_Horizon_È(p_180475_1_);
        final ArrayList var5 = Lists.newArrayList(Iterables.filter((Iterable)var4, (Predicate)new Predicate() {
            private static final String Â = "CL_00001958";
            
            public boolean HorizonCode_Horizon_È(final Score p_178903_1_) {
                return p_178903_1_.Ø­áŒŠá() != null && !p_178903_1_.Ø­áŒŠá().startsWith("#");
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Score)p_apply_1_);
            }
        }));
        ArrayList var6;
        if (var5.size() > 15) {
            var6 = Lists.newArrayList(Iterables.skip((Iterable)var5, var4.size() - 15));
        }
        else {
            var6 = var5;
        }
        int var7 = this.Ó().HorizonCode_Horizon_È(p_180475_1_.Ø­áŒŠá());
        for (final Score var9 : var6) {
            final ScorePlayerTeam var10 = var3.Ó(var9.Ø­áŒŠá());
            final String var11 = String.valueOf(ScorePlayerTeam.HorizonCode_Horizon_È(var10, var9.Ø­áŒŠá())) + ": " + EnumChatFormatting.ˆÏ­ + var9.Â();
            var7 = Math.max(var7, this.Ó().HorizonCode_Horizon_È(var11));
        }
        final int var12 = var6.size() * this.Ó().HorizonCode_Horizon_È;
        final int var13 = p_180475_2_.Â() / 2 + var12 / 3;
        final byte var14 = 3;
        final int var15 = p_180475_2_.HorizonCode_Horizon_È() - var7 - var14;
        int var16 = 0;
        for (final Score var18 : var6) {
            ++var16;
            final ScorePlayerTeam var19 = var3.Ó(var18.Ø­áŒŠá());
            final String var20 = ScorePlayerTeam.HorizonCode_Horizon_È(var19, var18.Ø­áŒŠá());
            final String var21 = new StringBuilder().append(EnumChatFormatting.ˆÏ­).append(var18.Â()).toString();
            final int var22 = var13 - var16 * this.Ó().HorizonCode_Horizon_È;
            final int var23 = p_180475_2_.HorizonCode_Horizon_È() - var14 + 2;
            Gui_1808253012.HorizonCode_Horizon_È(var15 - 2, var22, var23, var22 + this.Ó().HorizonCode_Horizon_È, 1342177280);
            this.Ó().HorizonCode_Horizon_È(var20, var15, var22, 553648127);
            this.Ó().HorizonCode_Horizon_È(var21, var23 - this.Ó().HorizonCode_Horizon_È(var21), var22, 553648127);
            if (var16 == var6.size()) {
                final String var24 = p_180475_1_.Ø­áŒŠá();
                Gui_1808253012.HorizonCode_Horizon_È(var15 - 2, var22 - this.Ó().HorizonCode_Horizon_È - 1, var23, var22 - 1, 1610612736);
                Gui_1808253012.HorizonCode_Horizon_È(var15 - 2, var22 - 1, var23, var22, 1342177280);
                this.Ó().HorizonCode_Horizon_È(var24, var15 + var7 / 2 - this.Ó().HorizonCode_Horizon_È(var24) / 2, var22 - this.Ó().HorizonCode_Horizon_È, 553648127);
            }
        }
    }
    
    private void Ø­áŒŠá(final ScaledResolution p_180477_1_) {
        if (this.à.ÇŽá€() instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)this.à.ÇŽá€();
            final int var3 = MathHelper.Ó(var2.Ï­Ä());
            final boolean var4 = this.ˆá > this.áˆºÑ¢Õ && (this.ˆá - this.áˆºÑ¢Õ) / 3L % 2L == 1L;
            if (var3 < this.Çªà¢ && var2.ˆÉ > 0) {
                this.ÇŽÉ = Minecraft.áƒ();
                this.ˆá = this.áˆºÑ¢Õ + 20;
            }
            else if (var3 > this.Çªà¢ && var2.ˆÉ > 0) {
                this.ÇŽÉ = Minecraft.áƒ();
                this.ˆá = this.áˆºÑ¢Õ + 10;
            }
            if (Minecraft.áƒ() - this.ÇŽÉ > 1000L) {
                this.Çªà¢ = var3;
                this.Ê = var3;
                this.ÇŽÉ = Minecraft.áƒ();
            }
            this.Çªà¢ = var3;
            final int var5 = this.Ê;
            this.Ó.setSeed(this.áˆºÑ¢Õ * 312871);
            final boolean var6 = false;
            final FoodStats var7 = var2.ŠÏ­áˆºá();
            final int var8 = var7.HorizonCode_Horizon_È();
            final int var9 = var7.Â();
            final IAttributeInstance var10 = var2.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È);
            final int var11 = p_180477_1_.HorizonCode_Horizon_È() / 2 - 91;
            final int var12 = p_180477_1_.HorizonCode_Horizon_È() / 2 + 91;
            final int var13 = p_180477_1_.Â() - 39;
            final float var14 = (float)var10.Âµá€();
            final float var15 = var2.Ñ¢È();
            final int var16 = MathHelper.Ó((var14 + var15) / 2.0f / 10.0f);
            final int var17 = Math.max(10 - (var16 - 2), 3);
            final int var18 = var13 - (var16 - 1) * var17 - 10;
            float var19 = var15;
            final int var20 = var2.áŒŠÉ();
            int var21 = -1;
            if (var2.HorizonCode_Horizon_È(Potion.á)) {
                var21 = this.áˆºÑ¢Õ % MathHelper.Ó(var14 + 5.0f);
            }
            this.à.ÇŽÕ.HorizonCode_Horizon_È("armor");
            for (int var22 = 0; var22 < 10; ++var22) {
                if (var20 > 0) {
                    final int var23 = var11 + var22 * 8;
                    if (var22 * 2 + 1 < var20) {
                        this.Â(var23, var18, 34, 9, 9, 9);
                    }
                    if (var22 * 2 + 1 == var20) {
                        this.Â(var23, var18, 25, 9, 9, 9);
                    }
                    if (var22 * 2 + 1 > var20) {
                        this.Â(var23, var18, 16, 9, 9, 9);
                    }
                }
            }
            this.à.ÇŽÕ.Ý("health");
            for (int var22 = MathHelper.Ó((var14 + var15) / 2.0f) - 1; var22 >= 0; --var22) {
                int var23 = 16;
                if (var2.HorizonCode_Horizon_È(Potion.µÕ)) {
                    var23 += 36;
                }
                else if (var2.HorizonCode_Horizon_È(Potion.Æ)) {
                    var23 += 72;
                }
                byte var24 = 0;
                if (var4) {
                    var24 = 1;
                }
                final int var25 = MathHelper.Ó((var22 + 1) / 10.0f) - 1;
                final int var26 = var11 + var22 % 10 * 8;
                int var27 = var13 - var25 * var17;
                if (var3 <= 4) {
                    var27 += this.Ó.nextInt(2);
                }
                if (var22 == var21) {
                    var27 -= 2;
                }
                byte var28 = 0;
                if (var2.Ï­Ðƒà.ŒÏ().¥Æ()) {
                    var28 = 5;
                }
                this.Â(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
                if (var4) {
                    if (var22 * 2 + 1 < var5) {
                        this.Â(var26, var27, var23 + 54, 9 * var28, 9, 9);
                    }
                    if (var22 * 2 + 1 == var5) {
                        this.Â(var26, var27, var23 + 63, 9 * var28, 9, 9);
                    }
                }
                if (var19 > 0.0f) {
                    if (var19 == var15 && var15 % 2.0f == 1.0f) {
                        this.Â(var26, var27, var23 + 153, 9 * var28, 9, 9);
                    }
                    else {
                        this.Â(var26, var27, var23 + 144, 9 * var28, 9, 9);
                    }
                    var19 -= 2.0f;
                }
                else {
                    if (var22 * 2 + 1 < var3) {
                        this.Â(var26, var27, var23 + 36, 9 * var28, 9, 9);
                    }
                    if (var22 * 2 + 1 == var3) {
                        this.Â(var26, var27, var23 + 45, 9 * var28, 9, 9);
                    }
                }
            }
            final Entity var29 = var2.Æ;
            if (var29 == null) {
                this.à.ÇŽÕ.Ý("food");
                for (int var23 = 0; var23 < 10; ++var23) {
                    int var30 = var13;
                    int var25 = 16;
                    byte var31 = 0;
                    if (var2.HorizonCode_Horizon_È(Potion.¥Æ)) {
                        var25 += 36;
                        var31 = 13;
                    }
                    if (var2.ŠÏ­áˆºá().Ø­áŒŠá() <= 0.0f && this.áˆºÑ¢Õ % (var8 * 3 + 1) == 0) {
                        var30 = var13 + (this.Ó.nextInt(3) - 1);
                    }
                    if (var6) {
                        var31 = 1;
                    }
                    final int var27 = var12 - var23 * 8 - 9;
                    this.Â(var27, var30, 16 + var31 * 9, 27, 9, 9);
                    if (var6) {
                        if (var23 * 2 + 1 < var9) {
                            this.Â(var27, var30, var25 + 54, 27, 9, 9);
                        }
                        if (var23 * 2 + 1 == var9) {
                            this.Â(var27, var30, var25 + 63, 27, 9, 9);
                        }
                    }
                    if (var23 * 2 + 1 < var8) {
                        this.Â(var27, var30, var25 + 36, 27, 9, 9);
                    }
                    if (var23 * 2 + 1 == var8) {
                        this.Â(var27, var30, var25 + 45, 27, 9, 9);
                    }
                }
            }
            else if (var29 instanceof EntityLivingBase) {
                this.à.ÇŽÕ.Ý("mountHealth");
                final EntityLivingBase var32 = (EntityLivingBase)var29;
                final int var30 = (int)Math.ceil(var32.Ï­Ä());
                final float var33 = var32.ÇŽÊ();
                int var26 = (int)(var33 + 0.5f) / 2;
                if (var26 > 30) {
                    var26 = 30;
                }
                int var27 = var13;
                int var34 = 0;
                while (var26 > 0) {
                    final int var35 = Math.min(var26, 10);
                    var26 -= var35;
                    for (int var36 = 0; var36 < var35; ++var36) {
                        final byte var37 = 52;
                        byte var38 = 0;
                        if (var6) {
                            var38 = 1;
                        }
                        final int var39 = var12 - var36 * 8 - 9;
                        this.Â(var39, var27, var37 + var38 * 9, 9, 9, 9);
                        if (var36 * 2 + 1 + var34 < var30) {
                            this.Â(var39, var27, var37 + 36, 9, 9, 9);
                        }
                        if (var36 * 2 + 1 + var34 == var30) {
                            this.Â(var39, var27, var37 + 45, 9, 9, 9);
                        }
                    }
                    var27 -= 10;
                    var34 += 20;
                }
            }
            this.à.ÇŽÕ.Ý("air");
            if (var2.HorizonCode_Horizon_È(Material.Ø)) {
                final int var23 = this.à.á.ˆÓ();
                for (int var30 = MathHelper.Ó((var23 - 2) * 10.0 / 300.0), var25 = MathHelper.Ó(var23 * 10.0 / 300.0) - var30, var26 = 0; var26 < var30 + var25; ++var26) {
                    if (var26 < var30) {
                        this.Â(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
                    }
                    else {
                        this.Â(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
                    }
                }
            }
            this.à.ÇŽÕ.Â();
        }
    }
    
    private void áŒŠÆ() {
        if (BossStatus.Ý != null && BossStatus.Â > 0) {
            --BossStatus.Â;
            final FontRenderer var1 = this.à.µà;
            final ScaledResolution var2 = new ScaledResolution(this.à, this.à.Ó, this.à.à);
            final int var3 = var2.HorizonCode_Horizon_È();
            final short var4 = 182;
            final int var5 = var3 / 2 - var4 / 2;
            final int var6 = (int)(BossStatus.HorizonCode_Horizon_È * (var4 + 1));
            final byte var7 = 12;
            this.Â(var5, var7, 0, 74, var4, 5);
            this.Â(var5, var7, 0, 74, var4, 5);
            if (var6 > 0) {
                this.Â(var5, var7, 0, 79, var6, 5);
            }
            final String var8 = BossStatus.Ý;
            this.Ó().HorizonCode_Horizon_È(var8, var3 / 2 - this.Ó().HorizonCode_Horizon_È(var8) / 2, (float)(var7 - 10), 16777215);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.à.¥à().HorizonCode_Horizon_È(GuiIngame.áŒŠà);
        }
    }
    
    private void Âµá€(final ScaledResolution p_180476_1_) {
        GlStateManager.áŒŠÆ();
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ý();
        this.à.¥à().HorizonCode_Horizon_È(GuiIngame.Âµá€);
        final Tessellator var2 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var3 = var2.Ý();
        var3.Â();
        var3.HorizonCode_Horizon_È(0.0, p_180476_1_.Â(), -90.0, 0.0, 1.0);
        var3.HorizonCode_Horizon_È(p_180476_1_.HorizonCode_Horizon_È(), p_180476_1_.Â(), -90.0, 1.0, 1.0);
        var3.HorizonCode_Horizon_È(p_180476_1_.HorizonCode_Horizon_È(), 0.0, -90.0, 1.0, 0.0);
        var3.HorizonCode_Horizon_È(0.0, 0.0, -90.0, 0.0, 0.0);
        var2.Â();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void HorizonCode_Horizon_È(float p_180480_1_, final ScaledResolution p_180480_2_) {
        p_180480_1_ = 1.0f - p_180480_1_;
        p_180480_1_ = MathHelper.HorizonCode_Horizon_È(p_180480_1_, 0.0f, 1.0f);
        final WorldBorder var3 = this.à.áŒŠÆ.áŠ();
        float var4 = (float)var3.HorizonCode_Horizon_È(this.à.á);
        final double var5 = Math.min(var3.Å() * var3.£à() * 1000.0, Math.abs(var3.áˆºÑ¢Õ() - var3.Ø()));
        final double var6 = Math.max(var3.µà(), var5);
        if (var4 < var6) {
            var4 = 1.0f - (float)(var4 / var6);
        }
        else {
            var4 = 0.0f;
        }
        this.Â += (float)((p_180480_1_ - this.Â) * 0.01);
        GlStateManager.áŒŠÆ();
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.HorizonCode_Horizon_È(0, 769, 1, 0);
        if (var4 > 0.0f) {
            GlStateManager.Ý(0.0f, var4, var4, 1.0f);
        }
        else {
            GlStateManager.Ý(this.Â, this.Â, this.Â, 1.0f);
        }
        this.à.¥à().HorizonCode_Horizon_È(GuiIngame.Ý);
        final Tessellator var7 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var8 = var7.Ý();
        var8.Â();
        var8.HorizonCode_Horizon_È(0.0, p_180480_2_.Â(), -90.0, 0.0, 1.0);
        var8.HorizonCode_Horizon_È(p_180480_2_.HorizonCode_Horizon_È(), p_180480_2_.Â(), -90.0, 1.0, 1.0);
        var8.HorizonCode_Horizon_È(p_180480_2_.HorizonCode_Horizon_È(), 0.0, -90.0, 1.0, 0.0);
        var8.HorizonCode_Horizon_È(0.0, 0.0, -90.0, 0.0, 0.0);
        var7.Â();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
    }
    
    private void Â(float p_180474_1_, final ScaledResolution p_180474_2_) {
        if (p_180474_1_ < 1.0f) {
            p_180474_1_ *= p_180474_1_;
            p_180474_1_ *= p_180474_1_;
            p_180474_1_ = p_180474_1_ * 0.8f + 0.2f;
        }
        GlStateManager.Ý();
        GlStateManager.áŒŠÆ();
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, p_180474_1_);
        this.à.¥à().HorizonCode_Horizon_È(TextureMap.à);
        final TextureAtlasSprite var3 = this.à.Ô().HorizonCode_Horizon_È().HorizonCode_Horizon_È(Blocks.µÐƒáƒ.¥à());
        final float var4 = var3.Âµá€();
        final float var5 = var3.à();
        final float var6 = var3.Ó();
        final float var7 = var3.Ø();
        final Tessellator var8 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var9 = var8.Ý();
        var9.Â();
        var9.HorizonCode_Horizon_È(0.0, p_180474_2_.Â(), -90.0, var4, var7);
        var9.HorizonCode_Horizon_È(p_180474_2_.HorizonCode_Horizon_È(), p_180474_2_.Â(), -90.0, var6, var7);
        var9.HorizonCode_Horizon_È(p_180474_2_.HorizonCode_Horizon_È(), 0.0, -90.0, var6, var5);
        var9.HorizonCode_Horizon_È(0.0, 0.0, -90.0, var4, var5);
        var8.Â();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void HorizonCode_Horizon_È(final int p_175184_1_, final int p_175184_2_, final int p_175184_3_, final float p_175184_4_, final EntityPlayer p_175184_5_) {
        final ItemStack var6 = p_175184_5_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[p_175184_1_];
        if (var6 != null) {
            final float var7 = var6.Ý - p_175184_4_;
            if (var7 > 0.0f) {
                GlStateManager.Çªà¢();
                final float var8 = 1.0f + var7 / 5.0f;
                GlStateManager.Â(p_175184_2_ + 8, p_175184_3_ + 12, 0.0f);
                GlStateManager.HorizonCode_Horizon_È(1.0f / var8, (var8 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.Â(-(p_175184_2_ + 8), -(p_175184_3_ + 12), 0.0f);
            }
            this.Ø.Â(var6, p_175184_2_, p_175184_3_);
            if (var7 > 0.0f) {
                GlStateManager.Ê();
            }
            this.Ø.HorizonCode_Horizon_È(this.à.µà, var6, p_175184_2_, p_175184_3_);
        }
    }
    
    public void Ý() {
        if (this.á > 0) {
            --this.á;
        }
        if (this.¥Æ > 0) {
            --this.¥Æ;
            if (this.¥Æ <= 0) {
                this.Ø­à = "";
                this.µÕ = "";
            }
        }
        ++this.áˆºÑ¢Õ;
        this.áŒŠÆ.HorizonCode_Horizon_È();
        if (this.à.á != null) {
            final ItemStack var1 = this.à.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
            if (var1 == null) {
                this.£á = 0;
            }
            else if (this.Å != null && var1.HorizonCode_Horizon_È() == this.Å.HorizonCode_Horizon_È() && ItemStack.HorizonCode_Horizon_È(var1, this.Å) && (var1.Ø­áŒŠá() || var1.Ø() == this.Å.Ø())) {
                if (this.£á > 0) {
                    --this.£á;
                }
            }
            else {
                this.£á = 40;
            }
            this.Å = var1;
        }
    }
    
    public void HorizonCode_Horizon_È(final String p_73833_1_) {
        this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("record.nowPlaying", p_73833_1_), true);
    }
    
    public void HorizonCode_Horizon_È(final String p_110326_1_, final boolean p_110326_2_) {
        this.ÂµÈ = p_110326_1_;
        this.á = 60;
        this.ˆÏ­ = p_110326_2_;
    }
    
    public void HorizonCode_Horizon_È(final String p_175178_1_, final String p_175178_2_, final int p_175178_3_, final int p_175178_4_, final int p_175178_5_) {
        if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0) {
            this.Ø­à = "";
            this.µÕ = "";
            this.¥Æ = 0;
        }
        else if (p_175178_1_ != null) {
            this.Ø­à = p_175178_1_;
            this.¥Æ = this.Æ + this.Ñ¢á + this.ŒÏ;
        }
        else if (p_175178_2_ != null) {
            this.µÕ = p_175178_2_;
        }
        else {
            if (p_175178_3_ >= 0) {
                this.Æ = p_175178_3_;
            }
            if (p_175178_4_ >= 0) {
                this.Ñ¢á = p_175178_4_;
            }
            if (p_175178_5_ >= 0) {
                this.ŒÏ = p_175178_5_;
            }
            if (this.¥Æ > 0) {
                this.¥Æ = this.Æ + this.Ñ¢á + this.ŒÏ;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent p_175188_1_, final boolean p_175188_2_) {
        this.HorizonCode_Horizon_È(p_175188_1_.Ø(), p_175188_2_);
    }
    
    public GuiNewChat Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Âµá€() {
        return this.áˆºÑ¢Õ;
    }
    
    public FontRenderer Ó() {
        return this.à.µà;
    }
    
    public GuiSpectator à() {
        return this.µà;
    }
    
    public GuiPlayerTabOverlay Ø() {
        return this.ˆà;
    }
}
