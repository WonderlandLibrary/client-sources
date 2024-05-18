package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import org.lwjgl.input.Mouse;
import java.io.IOException;
import java.awt.TrayIcon;

public class GuiAchievements extends GuiScreen implements IProgressMeter
{
    private static final int £á;
    private static final int Å;
    private static final int £à;
    private static final int µà;
    private static final ResourceLocation_1975012498 ˆà;
    protected GuiScreen Â;
    protected int Ý;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    protected int Ó;
    protected float à;
    protected double Ø;
    protected double áŒŠÆ;
    protected double áˆºÑ¢Õ;
    protected double ÂµÈ;
    protected double á;
    protected double ˆÏ­;
    private int ¥Æ;
    private StatFileWriter Ø­à;
    private boolean µÕ;
    private static final String Æ = "CL_00000722";
    
    static {
        £á = AchievementList.HorizonCode_Horizon_È * 24 - 112;
        Å = AchievementList.Â * 24 - 112;
        £à = AchievementList.Ý * 24 - 77;
        µà = AchievementList.Ø­áŒŠá * 24 - 77;
        ˆà = new ResourceLocation_1975012498("textures/gui/achievement/achievement_background.png");
    }
    
    public GuiAchievements(final GuiScreen p_i45026_1_, final StatFileWriter p_i45026_2_) {
        this.Ý = 256;
        this.Ø­áŒŠá = 202;
        this.à = 1.0f;
        this.µÕ = true;
        this.Â = p_i45026_1_;
        this.Ø­à = p_i45026_2_;
        final short var3 = 141;
        final short var4 = 141;
        final double ø = AchievementList.Ó.HorizonCode_Horizon_È * 24 - var3 / 2 - 12;
        this.á = ø;
        this.áˆºÑ¢Õ = ø;
        this.Ø = ø;
        final double áœšæ = AchievementList.Ó.Â * 24 - var4 / 2;
        this.ˆÏ­ = áœšæ;
        this.ÂµÈ = áœšæ;
        this.áŒŠÆ = áœšæ;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        GuiAchievements.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C16PacketClientStatus(C16PacketClientStatus.HorizonCode_Horizon_È.Â));
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiOptionButton(1, GuiAchievements.Çªà¢ / 2 + 24, GuiAchievements.Ê / 2 + 74, 80, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        if (Horizon.ÂµÈ) {
            this.ÇŽÉ.add(new GuiButton(2, GuiAchievements.Çªà¢ / 2 + 110, GuiAchievements.Ê / 2 + 100, 100, 20, I18n.HorizonCode_Horizon_È("Disable GhostMode", new Object[0])));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (!this.µÕ) {
            if (button.£à == 1) {
                GuiAchievements.Ñ¢á.HorizonCode_Horizon_È(this.Â);
            }
            if (button.£à == 2) {
                Horizon.ÂµÈ = false;
                GuiAchievements.Ñ¢á.HorizonCode_Horizon_È(new GuiIngameMenu());
                GhostTray.HorizonCode_Horizon_È.displayMessage("Horizon Client", "Ghost-Mode Deactivated", TrayIcon.MessageType.INFO);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == GuiAchievements.Ñ¢á.ŠÄ.Ï­Ï.áŒŠÆ()) {
            GuiAchievements.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            GuiAchievements.Ñ¢á.Å();
        }
        else {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.µÕ) {
            this.£á();
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("multiplayer.downloadingStats", new Object[0]), GuiAchievements.Çªà¢ / 2, GuiAchievements.Ê / 2, 16777215);
            this.HorizonCode_Horizon_È(this.É, GuiAchievements.HorizonCode_Horizon_È[(int)(Minecraft.áƒ() / 150L % GuiAchievements.HorizonCode_Horizon_È.length)], GuiAchievements.Çªà¢ / 2, GuiAchievements.Ê / 2 + this.É.HorizonCode_Horizon_È * 2, 16777215);
        }
        else {
            if (Mouse.isButtonDown(0)) {
                final int var4 = (GuiAchievements.Çªà¢ - this.Ý) / 2;
                final int var5 = (GuiAchievements.Ê - this.Ø­áŒŠá) / 2;
                final int var6 = var4 + 8;
                final int var7 = var5 + 17;
                if ((this.¥Æ == 0 || this.¥Æ == 1) && mouseX >= var6 && mouseX < var6 + 224 && mouseY >= var7 && mouseY < var7 + 155) {
                    if (this.¥Æ == 0) {
                        this.¥Æ = 1;
                    }
                    else {
                        this.áˆºÑ¢Õ -= (mouseX - this.Âµá€) * this.à;
                        this.ÂµÈ -= (mouseY - this.Ó) * this.à;
                        final double áˆºÑ¢Õ = this.áˆºÑ¢Õ;
                        this.Ø = áˆºÑ¢Õ;
                        this.á = áˆºÑ¢Õ;
                        final double âµÈ = this.ÂµÈ;
                        this.áŒŠÆ = âµÈ;
                        this.ˆÏ­ = âµÈ;
                    }
                    this.Âµá€ = mouseX;
                    this.Ó = mouseY;
                }
            }
            else {
                this.¥Æ = 0;
            }
            final int var4 = Mouse.getDWheel();
            final float var8 = this.à;
            if (var4 < 0) {
                this.à += 0.25f;
            }
            else if (var4 > 0) {
                this.à -= 0.25f;
            }
            this.à = MathHelper.HorizonCode_Horizon_È(this.à, 1.0f, 2.0f);
            if (this.à != var8) {
                final float var9 = var8 - this.à;
                final float var10 = var8 * this.Ý;
                final float var11 = var8 * this.Ø­áŒŠá;
                final float var12 = this.à * this.Ý;
                final float var13 = this.à * this.Ø­áŒŠá;
                this.áˆºÑ¢Õ -= (var12 - var10) * 0.5f;
                this.ÂµÈ -= (var13 - var11) * 0.5f;
                final double áˆºÑ¢Õ2 = this.áˆºÑ¢Õ;
                this.Ø = áˆºÑ¢Õ2;
                this.á = áˆºÑ¢Õ2;
                final double âµÈ2 = this.ÂµÈ;
                this.áŒŠÆ = âµÈ2;
                this.ˆÏ­ = âµÈ2;
            }
            if (this.á < GuiAchievements.£á) {
                this.á = GuiAchievements.£á;
            }
            if (this.ˆÏ­ < GuiAchievements.Å) {
                this.ˆÏ­ = GuiAchievements.Å;
            }
            if (this.á >= GuiAchievements.£à) {
                this.á = GuiAchievements.£à - 1;
            }
            if (this.ˆÏ­ >= GuiAchievements.µà) {
                this.ˆÏ­ = GuiAchievements.µà - 1;
            }
            this.£á();
            this.Â(mouseX, mouseY, partialTicks);
            GlStateManager.Ó();
            GlStateManager.áŒŠÆ();
            this.Ó();
            GlStateManager.Âµá€();
            GlStateManager.áˆºÑ¢Õ();
        }
    }
    
    @Override
    public void Â() {
        if (this.µÕ) {
            this.µÕ = false;
        }
    }
    
    @Override
    public void Ý() {
        if (!this.µÕ) {
            this.Ø = this.áˆºÑ¢Õ;
            this.áŒŠÆ = this.ÂµÈ;
            final double var1 = this.á - this.áˆºÑ¢Õ;
            final double var2 = this.ˆÏ­ - this.ÂµÈ;
            if (var1 * var1 + var2 * var2 < 4.0) {
                this.áˆºÑ¢Õ += var1;
                this.ÂµÈ += var2;
            }
            else {
                this.áˆºÑ¢Õ += var1 * 0.85;
                this.ÂµÈ += var2 * 0.85;
            }
        }
    }
    
    protected void Ó() {
        final int var1 = (GuiAchievements.Çªà¢ - this.Ý) / 2;
        final int var2 = (GuiAchievements.Ê - this.Ø­áŒŠá) / 2;
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("gui.achievements", new Object[0]), var1 + 15, var2 + 5, 4210752);
    }
    
    protected void Â(final int p_146552_1_, final int p_146552_2_, final float p_146552_3_) {
        int var4 = MathHelper.Ý(this.Ø + (this.áˆºÑ¢Õ - this.Ø) * p_146552_3_);
        int var5 = MathHelper.Ý(this.áŒŠÆ + (this.ÂµÈ - this.áŒŠÆ) * p_146552_3_);
        if (var4 < GuiAchievements.£á) {
            var4 = GuiAchievements.£á;
        }
        if (var5 < GuiAchievements.Å) {
            var5 = GuiAchievements.Å;
        }
        if (var4 >= GuiAchievements.£à) {
            var4 = GuiAchievements.£à - 1;
        }
        if (var5 >= GuiAchievements.µà) {
            var5 = GuiAchievements.µà - 1;
        }
        final int var6 = (GuiAchievements.Çªà¢ - this.Ý) / 2;
        final int var7 = (GuiAchievements.Ê - this.Ø­áŒŠá) / 2;
        final int var8 = var6 + 16;
        final int var9 = var7 + 17;
        GuiAchievements.ŠÄ = 0.0f;
        GlStateManager.Ý(518);
        GlStateManager.Çªà¢();
        GlStateManager.Â(var8, var9, -200.0f);
        GlStateManager.HorizonCode_Horizon_È(1.0f / this.à, 1.0f / this.à, 0.0f);
        GlStateManager.µÕ();
        GlStateManager.Ó();
        GlStateManager.ŠÄ();
        GlStateManager.à();
        final int var10 = var4 + 288 >> 4;
        final int var11 = var5 + 288 >> 4;
        final int var12 = (var4 + 288) % 16;
        final int var13 = (var5 + 288) % 16;
        final boolean var14 = true;
        final boolean var15 = true;
        final boolean var16 = true;
        final boolean var17 = true;
        final boolean var18 = true;
        final Random var19 = new Random();
        final float var20 = 16.0f / this.à;
        final float var21 = 16.0f / this.à;
        for (int var22 = 0; var22 * var20 - var13 < 155.0f; ++var22) {
            final float var23 = 0.6f - (var11 + var22) / 25.0f * 0.3f;
            GlStateManager.Ý(var23, var23, var23, 1.0f);
            for (int var24 = 0; var24 * var21 - var12 < 224.0f; ++var24) {
                var19.setSeed(GuiAchievements.Ñ¢á.Õ().Â().hashCode() + var10 + var24 + (var11 + var22) * 16);
                final int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
                TextureAtlasSprite var26 = this.HorizonCode_Horizon_È(Blocks.£á);
                if (var25 <= 37 && var11 + var22 != 35) {
                    if (var25 == 22) {
                        if (var19.nextInt(2) == 0) {
                            var26 = this.HorizonCode_Horizon_È(Blocks.£Ï);
                        }
                        else {
                            var26 = this.HorizonCode_Horizon_È(Blocks.Ñ¢à);
                        }
                    }
                    else if (var25 == 10) {
                        var26 = this.HorizonCode_Horizon_È(Blocks.µà);
                    }
                    else if (var25 == 8) {
                        var26 = this.HorizonCode_Horizon_È(Blocks.ˆà);
                    }
                    else if (var25 > 4) {
                        var26 = this.HorizonCode_Horizon_È(Blocks.Ý);
                    }
                    else if (var25 > 0) {
                        var26 = this.HorizonCode_Horizon_È(Blocks.Âµá€);
                    }
                }
                else {
                    final Block var27 = Blocks.áŒŠÆ;
                    var26 = this.HorizonCode_Horizon_È(var27);
                }
                GuiAchievements.Ñ¢á.¥à().HorizonCode_Horizon_È(TextureMap.à);
                this.HorizonCode_Horizon_È(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
            }
        }
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ý(515);
        GuiAchievements.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiAchievements.ˆà);
        for (int var22 = 0; var22 < AchievementList.Âµá€.size(); ++var22) {
            final Achievement var28 = AchievementList.Âµá€.get(var22);
            if (var28.Âµá€ != null) {
                final int var24 = var28.HorizonCode_Horizon_È * 24 - var4 + 11;
                final int var25 = var28.Â * 24 - var5 + 11;
                final int var29 = var28.Âµá€.HorizonCode_Horizon_È * 24 - var4 + 11;
                final int var30 = var28.Âµá€.Â * 24 - var5 + 11;
                final boolean var31 = this.Ø­à.HorizonCode_Horizon_È(var28);
                final boolean var32 = this.Ø­à.Â(var28);
                final int var33 = this.Ø­à.Ý(var28);
                if (var33 <= 4) {
                    int var34 = -16777216;
                    if (var31) {
                        var34 = -6250336;
                    }
                    else if (var32) {
                        var34 = -16711936;
                    }
                    this.HorizonCode_Horizon_È(var24, var29, var25, var34);
                    this.Â(var29, var25, var30, var34);
                    if (var24 > var29) {
                        this.Â(var24 - 11 - 7, var25 - 5, 114, 234, 7, 11);
                    }
                    else if (var24 < var29) {
                        this.Â(var24 + 11, var25 - 5, 107, 234, 7, 11);
                    }
                    else if (var25 > var30) {
                        this.Â(var24 - 5, var25 - 11 - 7, 96, 234, 11, 7);
                    }
                    else if (var25 < var30) {
                        this.Â(var24 - 5, var25 + 11, 96, 241, 11, 7);
                    }
                }
            }
        }
        Achievement var35 = null;
        final float var23 = (p_146552_1_ - var8) * this.à;
        final float var36 = (p_146552_2_ - var9) * this.à;
        RenderHelper.Ý();
        GlStateManager.Ó();
        GlStateManager.ŠÄ();
        GlStateManager.à();
        for (int var25 = 0; var25 < AchievementList.Âµá€.size(); ++var25) {
            final Achievement var37 = AchievementList.Âµá€.get(var25);
            final int var30 = var37.HorizonCode_Horizon_È * 24 - var4;
            final int var38 = var37.Â * 24 - var5;
            if (var30 >= -24 && var38 >= -24 && var30 <= 224.0f * this.à && var38 <= 155.0f * this.à) {
                final int var39 = this.Ø­à.Ý(var37);
                if (this.Ø­à.HorizonCode_Horizon_È(var37)) {
                    final float var40 = 0.75f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                }
                else if (this.Ø­à.Â(var37)) {
                    final float var40 = 1.0f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                }
                else if (var39 < 3) {
                    final float var40 = 0.3f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                }
                else if (var39 == 3) {
                    final float var40 = 0.2f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                }
                else {
                    if (var39 != 4) {
                        continue;
                    }
                    final float var40 = 0.1f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                }
                GuiAchievements.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiAchievements.ˆà);
                if (var37.à()) {
                    this.Â(var30 - 2, var38 - 2, 26, 202, 26, 26);
                }
                else {
                    this.Â(var30 - 2, var38 - 2, 0, 202, 26, 26);
                }
                if (!this.Ø­à.Â(var37)) {
                    final float var40 = 0.1f;
                    GlStateManager.Ý(var40, var40, var40, 1.0f);
                    this.ŒÏ.HorizonCode_Horizon_È(false);
                }
                GlStateManager.Âµá€();
                GlStateManager.Å();
                this.ŒÏ.Â(var37.Ó, var30 + 3, var38 + 3);
                GlStateManager.Â(770, 771);
                GlStateManager.Ó();
                if (!this.Ø­à.Â(var37)) {
                    this.ŒÏ.HorizonCode_Horizon_È(true);
                }
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                if (var23 >= var30 && var23 <= var30 + 22 && var36 >= var38 && var36 <= var38 + 22) {
                    var35 = var37;
                }
            }
        }
        GlStateManager.áŒŠÆ();
        GlStateManager.á();
        GlStateManager.Ê();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiAchievements.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiAchievements.ˆà);
        this.Â(var6, var7, 0, 0, this.Ý, this.Ø­áŒŠá);
        GuiAchievements.ŠÄ = 0.0f;
        GlStateManager.Ý(515);
        GlStateManager.áŒŠÆ();
        GlStateManager.µÕ();
        super.HorizonCode_Horizon_È(p_146552_1_, p_146552_2_, p_146552_3_);
        if (var35 != null) {
            String var41 = var35.Âµá€().Ø();
            final String var42 = var35.Ó();
            final int var30 = p_146552_1_ + 12;
            final int var38 = p_146552_2_ - 4;
            final int var39 = this.Ø­à.Ý(var35);
            if (this.Ø­à.Â(var35)) {
                final int var33 = Math.max(this.É.HorizonCode_Horizon_È(var41), 120);
                int var34 = this.É.Â(var42, var33);
                if (this.Ø­à.HorizonCode_Horizon_È(var35)) {
                    var34 += 12;
                }
                Gui_1808253012.HorizonCode_Horizon_È(var30 - 3, var38 - 3, var30 + var33 + 3, var38 + var34 + 3 + 12, -1073741824, -1073741824);
                this.É.HorizonCode_Horizon_È(var42, var30, var38 + 12, var33, -6250336);
                if (this.Ø­à.HorizonCode_Horizon_È(var35)) {
                    this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("achievement.taken", new Object[0]), var30, (float)(var38 + var34 + 4), -7302913);
                }
            }
            else if (var39 == 3) {
                var41 = I18n.HorizonCode_Horizon_È("achievement.unknown", new Object[0]);
                final int var33 = Math.max(this.É.HorizonCode_Horizon_È(var41), 120);
                final String var43 = new ChatComponentTranslation("achievement.requires", new Object[] { var35.Âµá€.Âµá€() }).Ø();
                final int var44 = this.É.Â(var43, var33);
                Gui_1808253012.HorizonCode_Horizon_È(var30 - 3, var38 - 3, var30 + var33 + 3, var38 + var44 + 12 + 3, -1073741824, -1073741824);
                this.É.HorizonCode_Horizon_È(var43, var30, var38 + 12, var33, -9416624);
            }
            else if (var39 < 3) {
                final int var33 = Math.max(this.É.HorizonCode_Horizon_È(var41), 120);
                final String var43 = new ChatComponentTranslation("achievement.requires", new Object[] { var35.Âµá€.Âµá€() }).Ø();
                final int var44 = this.É.Â(var43, var33);
                Gui_1808253012.HorizonCode_Horizon_È(var30 - 3, var38 - 3, var30 + var33 + 3, var38 + var44 + 12 + 3, -1073741824, -1073741824);
                this.É.HorizonCode_Horizon_È(var43, var30, var38 + 12, var33, -9416624);
            }
            else {
                var41 = null;
            }
            if (var41 != null) {
                this.É.HorizonCode_Horizon_È(var41, var30, (float)var38, this.Ø­à.Â(var35) ? (var35.à() ? -128 : -1) : (var35.à() ? -8355776 : -8355712));
            }
        }
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Âµá€();
        RenderHelper.HorizonCode_Horizon_È();
    }
    
    private TextureAtlasSprite HorizonCode_Horizon_È(final Block p_175371_1_) {
        return Minecraft.áŒŠà().Ô().HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_175371_1_.¥à());
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return !this.µÕ;
    }
}
