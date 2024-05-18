package net.minecraft.client.gui.achievement;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiAchievement extends Gui
{
    private String achievementDescription;
    private static final ResourceLocation achievementBg;
    private long notificationTime;
    private Minecraft mc;
    private RenderItem renderItem;
    private boolean permanentNotification;
    private int height;
    private Achievement theAchievement;
    private int width;
    private String achievementTitle;
    private static final String[] I;
    
    public void displayUnformattedAchievement(final Achievement theAchievement) {
        this.achievementTitle = theAchievement.getStatName().getUnformattedText();
        this.achievementDescription = theAchievement.getDescription();
        this.notificationTime = Minecraft.getSystemTime() + 2500L;
        this.theAchievement = theAchievement;
        this.permanentNotification = (" ".length() != 0);
    }
    
    static {
        I();
        achievementBg = new ResourceLocation(GuiAchievement.I["".length()]);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiAchievement(final Minecraft mc) {
        this.mc = mc;
        this.renderItem = mc.getRenderItem();
    }
    
    public void updateAchievementWindow() {
        if (this.theAchievement != null && this.notificationTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
            double n = (Minecraft.getSystemTime() - this.notificationTime) / 3000.0;
            if (!this.permanentNotification) {
                if (n < 0.0 || n > 1.0) {
                    this.notificationTime = 0L;
                    return;
                }
            }
            else if (n > 0.5) {
                n = 0.5;
            }
            this.updateAchievementWindowScale();
            GlStateManager.disableDepth();
            GlStateManager.depthMask("".length() != 0);
            double n2 = n * 2.0;
            if (n2 > 1.0) {
                n2 = 2.0 - n2;
            }
            double n3 = 1.0 - n2 * 4.0;
            if (n3 < 0.0) {
                n3 = 0.0;
            }
            final double n4 = n3 * n3;
            final double n5 = n4 * n4;
            final int n6 = this.width - (158 + 97 - 187 + 92);
            final int n7 = "".length() - (int)(n5 * 36.0);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableTexture2D();
            this.mc.getTextureManager().bindTexture(GuiAchievement.achievementBg);
            GlStateManager.disableLighting();
            this.drawTexturedModalRect(n6, n7, 0x3C ^ 0x5C, 54 + 72 + 15 + 61, 119 + 146 - 114 + 9, 0xA ^ 0x2A);
            if (this.permanentNotification) {
                this.mc.fontRendererObj.drawSplitString(this.achievementDescription, n6 + (0xD9 ^ 0xC7), n7 + (0x2C ^ 0x2B), 0xE0 ^ 0x98, -" ".length());
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                this.mc.fontRendererObj.drawString(this.achievementTitle, n6 + (0x1E ^ 0x0), n7 + (0x18 ^ 0x1F), -(209 + 155 - 353 + 245));
                this.mc.fontRendererObj.drawString(this.achievementDescription, n6 + (0x33 ^ 0x2D), n7 + (0x13 ^ 0x1), -" ".length());
            }
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, n6 + (0x4B ^ 0x43), n7 + (0x2A ^ 0x22));
            GlStateManager.disableLighting();
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableDepth();
        }
    }
    
    public void clearAchievements() {
        this.theAchievement = null;
        this.notificationTime = 0L;
    }
    
    private void updateAchievementWindowScale() {
        GlStateManager.viewport("".length(), "".length(), this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(1025 + 3206 - 1708 + 3366);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(2704 + 4648 - 2847 + 1383);
        GlStateManager.loadIdentity();
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.width = scaledResolution.getScaledWidth();
        this.height = scaledResolution.getScaledHeight();
        GlStateManager.clear(48 + 79 + 60 + 69);
        GlStateManager.matrixMode(5151 + 2847 - 4529 + 2420);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, this.width, this.height, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(2106 + 3208 - 1158 + 1732);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    public void displayAchievement(final Achievement theAchievement) {
        this.achievementTitle = I18n.format(GuiAchievement.I[" ".length()], new Object["".length()]);
        this.achievementDescription = theAchievement.getStatName().getUnformattedText();
        this.notificationTime = Minecraft.getSystemTime();
        this.theAchievement = theAchievement;
        this.permanentNotification = ("".length() != 0);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0017/,&?\u0011/'}-\u0016#{3)\u000b#1$/\u000e/:&e\u0002)<;/\u0015/97$\u0017\u001563)\b-&=?\r.z\"$\u0004", "cJTRJ");
        GuiAchievement.I[" ".length()] = I("\u0014-\r?\u0002\u0003+\b3\t\u0001`\u00023\u0013", "uNeVg");
    }
}
