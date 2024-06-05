package net.minecraft.src;

import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.*;

public class GuiAchievements extends GuiScreen
{
    private static final int guiMapTop;
    private static final int guiMapLeft;
    private static final int guiMapBottom;
    private static final int guiMapRight;
    protected int achievementsPaneWidth;
    protected int achievementsPaneHeight;
    protected int mouseX;
    protected int mouseY;
    protected double field_74117_m;
    protected double field_74115_n;
    protected double guiMapX;
    protected double guiMapY;
    protected double field_74124_q;
    protected double field_74123_r;
    private int isMouseButtonDown;
    private StatFileWriter statFileWriter;
    
    static {
        guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
        guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
        guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
        guiMapRight = AchievementList.maxDisplayRow * 24 - 77;
    }
    
    public GuiAchievements(final StatFileWriter par1StatFileWriter) {
        this.achievementsPaneWidth = 256;
        this.achievementsPaneHeight = 202;
        this.mouseX = 0;
        this.mouseY = 0;
        this.isMouseButtonDown = 0;
        this.statFileWriter = par1StatFileWriter;
        final short var2 = 141;
        final short var3 = 141;
        final double field_74117_m = AchievementList.openInventory.displayColumn * 24 - var2 / 2 - 12;
        this.field_74124_q = field_74117_m;
        this.guiMapX = field_74117_m;
        this.field_74117_m = field_74117_m;
        final double field_74115_n = AchievementList.openInventory.displayRow * 24 - var3 / 2;
        this.field_74123_r = field_74115_n;
        this.guiMapY = field_74115_n;
        this.field_74115_n = field_74115_n;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
        super.actionPerformed(par1GuiButton);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        if (Mouse.isButtonDown(0)) {
            final int var4 = (this.width - this.achievementsPaneWidth) / 2;
            final int var5 = (this.height - this.achievementsPaneHeight) / 2;
            final int var6 = var4 + 8;
            final int var7 = var5 + 17;
            if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && par1 >= var6 && par1 < var6 + 224 && par2 >= var7 && par2 < var7 + 155) {
                if (this.isMouseButtonDown == 0) {
                    this.isMouseButtonDown = 1;
                }
                else {
                    this.guiMapX -= par1 - this.mouseX;
                    this.guiMapY -= par2 - this.mouseY;
                    final double guiMapX = this.guiMapX;
                    this.field_74117_m = guiMapX;
                    this.field_74124_q = guiMapX;
                    final double guiMapY = this.guiMapY;
                    this.field_74115_n = guiMapY;
                    this.field_74123_r = guiMapY;
                }
                this.mouseX = par1;
                this.mouseY = par2;
            }
            if (this.field_74124_q < GuiAchievements.guiMapTop) {
                this.field_74124_q = GuiAchievements.guiMapTop;
            }
            if (this.field_74123_r < GuiAchievements.guiMapLeft) {
                this.field_74123_r = GuiAchievements.guiMapLeft;
            }
            if (this.field_74124_q >= GuiAchievements.guiMapBottom) {
                this.field_74124_q = GuiAchievements.guiMapBottom - 1;
            }
            if (this.field_74123_r >= GuiAchievements.guiMapRight) {
                this.field_74123_r = GuiAchievements.guiMapRight - 1;
            }
        }
        else {
            this.isMouseButtonDown = 0;
        }
        this.drawDefaultBackground();
        this.genAchievementBackground(par1, par2, par3);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        this.drawTitle();
        GL11.glEnable(2896);
        GL11.glEnable(2929);
    }
    
    @Override
    public void updateScreen() {
        this.field_74117_m = this.guiMapX;
        this.field_74115_n = this.guiMapY;
        final double var1 = this.field_74124_q - this.guiMapX;
        final double var2 = this.field_74123_r - this.guiMapY;
        if (var1 * var1 + var2 * var2 < 4.0) {
            this.guiMapX += var1;
            this.guiMapY += var2;
        }
        else {
            this.guiMapX += var1 * 0.85;
            this.guiMapY += var2 * 0.85;
        }
    }
    
    protected void drawTitle() {
        final int var1 = (this.width - this.achievementsPaneWidth) / 2;
        final int var2 = (this.height - this.achievementsPaneHeight) / 2;
        this.fontRenderer.drawString("Achievements", var1 + 15, var2 + 5, 4210752);
    }
    
    protected void genAchievementBackground(final int par1, final int par2, final float par3) {
        int var4 = MathHelper.floor_double(this.field_74117_m + (this.guiMapX - this.field_74117_m) * par3);
        int var5 = MathHelper.floor_double(this.field_74115_n + (this.guiMapY - this.field_74115_n) * par3);
        if (var4 < GuiAchievements.guiMapTop) {
            var4 = GuiAchievements.guiMapTop;
        }
        if (var5 < GuiAchievements.guiMapLeft) {
            var5 = GuiAchievements.guiMapLeft;
        }
        if (var4 >= GuiAchievements.guiMapBottom) {
            var4 = GuiAchievements.guiMapBottom - 1;
        }
        if (var5 >= GuiAchievements.guiMapRight) {
            var5 = GuiAchievements.guiMapRight - 1;
        }
        final int var6 = (this.width - this.achievementsPaneWidth) / 2;
        final int var7 = (this.height - this.achievementsPaneHeight) / 2;
        final int var8 = var6 + 16;
        final int var9 = var7 + 17;
        this.zLevel = 0.0f;
        GL11.glDepthFunc(518);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, -200.0f);
        GL11.glEnable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        this.mc.renderEngine.bindTexture("/terrain.png");
        final int var10 = var4 + 288 >> 4;
        final int var11 = var5 + 288 >> 4;
        final int var12 = (var4 + 288) % 16;
        final int var13 = (var5 + 288) % 16;
        final Random var14 = new Random();
        for (int var15 = 0; var15 * 16 - var13 < 155; ++var15) {
            final float var16 = 0.6f - (var11 + var15) / 25.0f * 0.3f;
            GL11.glColor4f(var16, var16, var16, 1.0f);
            for (int var17 = 0; var17 * 16 - var12 < 224; ++var17) {
                var14.setSeed(1234 + var10 + var17);
                var14.nextInt();
                final int var18 = var14.nextInt(1 + var11 + var15) + (var11 + var15) / 2;
                Icon var19 = Block.sand.getIcon(0, 0);
                if (var18 <= 37 && var11 + var15 != 35) {
                    if (var18 == 22) {
                        if (var14.nextInt(2) == 0) {
                            var19 = Block.oreDiamond.getIcon(0, 0);
                        }
                        else {
                            var19 = Block.oreRedstone.getIcon(0, 0);
                        }
                    }
                    else if (var18 == 10) {
                        var19 = Block.oreIron.getIcon(0, 0);
                    }
                    else if (var18 == 8) {
                        var19 = Block.oreCoal.getIcon(0, 0);
                    }
                    else if (var18 > 4) {
                        var19 = Block.stone.getIcon(0, 0);
                    }
                    else if (var18 > 0) {
                        var19 = Block.dirt.getIcon(0, 0);
                    }
                }
                else {
                    var19 = Block.bedrock.getIcon(0, 0);
                }
                this.drawTexturedModelRectFromIcon(var8 + var17 * 16 - var12, var9 + var15 * 16 - var13, var19, 16, 16);
            }
        }
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glDisable(3553);
        for (int var15 = 0; var15 < AchievementList.achievementList.size(); ++var15) {
            final Achievement var20 = AchievementList.achievementList.get(var15);
            if (var20.parentAchievement != null) {
                final int var17 = var20.displayColumn * 24 - var4 + 11 + var8;
                final int var18 = var20.displayRow * 24 - var5 + 11 + var9;
                final int var21 = var20.parentAchievement.displayColumn * 24 - var4 + 11 + var8;
                final int var22 = var20.parentAchievement.displayRow * 24 - var5 + 11 + var9;
                final boolean var23 = this.statFileWriter.hasAchievementUnlocked(var20);
                final boolean var24 = this.statFileWriter.canUnlockAchievement(var20);
                final int var25 = (Math.sin(Minecraft.getSystemTime() % 600L / 600.0 * 3.141592653589793 * 2.0) > 0.6) ? 255 : 130;
                int var26 = -16777216;
                if (var23) {
                    var26 = -9408400;
                }
                else if (var24) {
                    var26 = 65280 + (var25 << 24);
                }
                this.drawHorizontalLine(var17, var21, var18, var26);
                this.drawVerticalLine(var21, var18, var22, var26);
            }
        }
        Achievement var20 = null;
        final RenderItem var27 = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        for (int var17 = 0; var17 < AchievementList.achievementList.size(); ++var17) {
            final Achievement var28 = AchievementList.achievementList.get(var17);
            final int var21 = var28.displayColumn * 24 - var4;
            final int var22 = var28.displayRow * 24 - var5;
            if (var21 >= -24 && var22 >= -24 && var21 <= 224 && var22 <= 155) {
                if (this.statFileWriter.hasAchievementUnlocked(var28)) {
                    final float var29 = 1.0f;
                    GL11.glColor4f(var29, var29, var29, 1.0f);
                }
                else if (this.statFileWriter.canUnlockAchievement(var28)) {
                    final float var29 = (Math.sin(Minecraft.getSystemTime() % 600L / 600.0 * 3.141592653589793 * 2.0) < 0.6) ? 0.6f : 0.8f;
                    GL11.glColor4f(var29, var29, var29, 1.0f);
                }
                else {
                    final float var29 = 0.3f;
                    GL11.glColor4f(var29, var29, var29, 1.0f);
                }
                this.mc.renderEngine.bindTexture("/achievement/bg.png");
                final int var26 = var8 + var21;
                final int var30 = var9 + var22;
                if (var28.getSpecial()) {
                    this.drawTexturedModalRect(var26 - 2, var30 - 2, 26, 202, 26, 26);
                }
                else {
                    this.drawTexturedModalRect(var26 - 2, var30 - 2, 0, 202, 26, 26);
                }
                if (!this.statFileWriter.canUnlockAchievement(var28)) {
                    final float var31 = 0.1f;
                    GL11.glColor4f(var31, var31, var31, 1.0f);
                    RenderItem.renderWithColor = false;
                }
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                RenderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var28.theItemStack, var26 + 3, var30 + 3);
                GL11.glDisable(2896);
                if (!this.statFileWriter.canUnlockAchievement(var28)) {
                    RenderItem.renderWithColor = true;
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (par1 >= var8 && par2 >= var9 && par1 < var8 + 224 && par2 < var9 + 155 && par1 >= var26 && par1 <= var26 + 22 && par2 >= var30 && par2 <= var30 + 22) {
                    var20 = var28;
                }
            }
        }
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/achievement/bg.png");
        this.drawTexturedModalRect(var6, var7, 0, 0, this.achievementsPaneWidth, this.achievementsPaneHeight);
        GL11.glPopMatrix();
        this.zLevel = 0.0f;
        GL11.glDepthFunc(515);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        super.drawScreen(par1, par2, par3);
        if (var20 != null) {
            final String var32 = StatCollector.translateToLocal(var20.getName());
            final String var33 = var20.getDescription();
            final int var21 = par1 + 12;
            final int var22 = par2 - 4;
            if (this.statFileWriter.canUnlockAchievement(var20)) {
                final int var26 = Math.max(this.fontRenderer.getStringWidth(var32), 120);
                int var30 = this.fontRenderer.splitStringWidth(var33, var26);
                if (this.statFileWriter.hasAchievementUnlocked(var20)) {
                    var30 += 12;
                }
                this.drawGradientRect(var21 - 3, var22 - 3, var21 + var26 + 3, var22 + var30 + 3 + 12, -1073741824, -1073741824);
                this.fontRenderer.drawSplitString(var33, var21, var22 + 12, var26, -6250336);
                if (this.statFileWriter.hasAchievementUnlocked(var20)) {
                    this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("achievement.taken"), var21, var22 + var30 + 4, -7302913);
                }
            }
            else {
                final int var26 = Math.max(this.fontRenderer.getStringWidth(var32), 120);
                final String var34 = StatCollector.translateToLocalFormatted("achievement.requires", StatCollector.translateToLocal(var20.parentAchievement.getName()));
                final int var25 = this.fontRenderer.splitStringWidth(var34, var26);
                this.drawGradientRect(var21 - 3, var22 - 3, var21 + var26 + 3, var22 + var25 + 12 + 3, -1073741824, -1073741824);
                this.fontRenderer.drawSplitString(var34, var21, var22 + 12, var26, -9416624);
            }
            this.fontRenderer.drawStringWithShadow(var32, var21, var22, this.statFileWriter.canUnlockAchievement(var20) ? (var20.getSpecial() ? -128 : -1) : (var20.getSpecial() ? -8355776 : -8355712));
        }
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        RenderHelper.disableStandardItemLighting();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
