package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiAchievement extends Gui
{
    private Minecraft theGame;
    private int achievementWindowWidth;
    private int achievementWindowHeight;
    private String achievementGetLocalText;
    private String achievementStatName;
    private Achievement theAchievement;
    private long achievementTime;
    private RenderItem itemRender;
    private boolean haveAchiement;
    
    public GuiAchievement(final Minecraft par1Minecraft) {
        this.theGame = par1Minecraft;
        this.itemRender = new RenderItem();
    }
    
    public void queueTakenAchievement(final Achievement par1Achievement) {
        this.achievementGetLocalText = StatCollector.translateToLocal("achievement.get");
        this.achievementStatName = StatCollector.translateToLocal(par1Achievement.getName());
        this.achievementTime = Minecraft.getSystemTime();
        this.theAchievement = par1Achievement;
        this.haveAchiement = false;
    }
    
    public void queueAchievementInformation(final Achievement par1Achievement) {
        this.achievementGetLocalText = StatCollector.translateToLocal(par1Achievement.getName());
        this.achievementStatName = par1Achievement.getDescription();
        this.achievementTime = Minecraft.getSystemTime() - 2500L;
        this.theAchievement = par1Achievement;
        this.haveAchiement = true;
    }
    
    private void updateAchievementWindowScale() {
        GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.achievementWindowWidth = this.theGame.displayWidth;
        this.achievementWindowHeight = this.theGame.displayHeight;
        final ScaledResolution var1 = new ScaledResolution(this.theGame.gameSettings, this.theGame.displayWidth, this.theGame.displayHeight);
        this.achievementWindowWidth = ScaledResolution.getScaledWidth();
        this.achievementWindowHeight = ScaledResolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, this.achievementWindowWidth, this.achievementWindowHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    public void updateAchievementWindow() {
        if (this.theAchievement != null && this.achievementTime != 0L) {
            final double var1 = (Minecraft.getSystemTime() - this.achievementTime) / 3000.0;
            if (!this.haveAchiement && (var1 < 0.0 || var1 > 1.0)) {
                this.achievementTime = 0L;
            }
            else {
                this.updateAchievementWindowScale();
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                double var2 = var1 * 2.0;
                if (var2 > 1.0) {
                    var2 = 2.0 - var2;
                }
                var2 *= 4.0;
                var2 = 1.0 - var2;
                if (var2 < 0.0) {
                    var2 = 0.0;
                }
                var2 *= var2;
                var2 *= var2;
                final int var3 = this.achievementWindowWidth - 160;
                final int var4 = 0 - (int)(var2 * 36.0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3553);
                this.theGame.renderEngine.bindTexture("/achievement/bg.png");
                GL11.glDisable(2896);
                this.drawTexturedModalRect(var3, var4, 96, 202, 160, 32);
                if (this.haveAchiement) {
                    this.theGame.fontRenderer.drawSplitString(this.achievementStatName, var3 + 30, var4 + 7, 120, -1);
                }
                else {
                    this.theGame.fontRenderer.drawString(this.achievementGetLocalText, var3 + 30, var4 + 7, -256);
                    this.theGame.fontRenderer.drawString(this.achievementStatName, var3 + 30, var4 + 18, -1);
                }
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glDisable(2896);
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glEnable(2896);
                RenderItem.renderItemAndEffectIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.theAchievement.theItemStack, var3 + 8, var4 + 8);
                GL11.glDisable(2896);
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
            }
        }
    }
}
