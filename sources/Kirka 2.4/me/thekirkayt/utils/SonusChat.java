/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.List;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.utils.NahrFont;
import me.thekirkayt.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class SonusChat
extends GuiNewChat {
    private final Minecraft mc;
    private int y1;
    private float width;

    public SonusChat(Minecraft mc) {
        super(mc);
        this.mc = mc;
        this.x = 2;
        this.y = 20;
    }

    @Override
    public void drawChat(int p_146230_1_) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int var2 = this.getLineCount();
            boolean var3 = false;
            int var4 = 0;
            int var5 = this.field_146253_i.size();
            float var6 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (var5 > 0) {
                int var8;
                int var10;
                int var13;
                int var14;
                if (this.getChatOpen()) {
                    var3 = true;
                }
                float var7 = this.getChatScale();
                MathHelper.ceiling_float_int((float)this.getChatWidth() / var7);
                GL11.glPushMatrix();
                GL11.glScalef((float)var7, (float)var7, (float)1.0f);
                for (int var82 = 0; var82 + this.scrollPos < this.field_146253_i.size() && var82 < var2; ++var82) {
                    int var9;
                    ChatLine color = (ChatLine)this.field_146253_i.get(var82 + this.scrollPos);
                    if (color == null || (var9 = p_146230_1_ - color.getUpdatedCounter()) >= 200 && !var3) continue;
                    double bcolor = (double)var9 / 200.0;
                    bcolor = 1.0 - bcolor;
                    if ((bcolor *= 10.0) < 0.0) {
                        bcolor = 0.0;
                    }
                    if (bcolor > 1.0) {
                        bcolor = 1.0;
                    }
                    bcolor *= bcolor;
                    int var102 = (int)(255.0 * bcolor);
                    if (var3) {
                        var102 = 255;
                    }
                    int var11 = (int)((float)var102 * var6);
                    ++var4;
                }
                GL11.glTranslated((double)this.x, (double)this.y, (double)0.0);
                this.y1 = -var2 * 9;
                GL11.glTranslated((double)0.0, (double)-8.0, (double)0.0);
                int var12 = this.drag ? Integer.MIN_VALUE : 1610612736;
                int n = var13 = this.drag ? 1610612736 : 1073741824;
                if (this.getChatOpen() && this.width > 0.0f) {
                    var14 = -var2 * 9;
                    float var15 = (float)(var12 >> 24 & 255) / 255.0f;
                    float var16 = (float)(var12 >> 16 & 255) / 255.0f;
                    float var17 = (float)(var12 >> 8 & 255) / 255.0f;
                    float var18 = (float)(var12 & 255) / 255.0f;
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)3553);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glEnable((int)2848);
                    GL11.glPushMatrix();
                    GL11.glColor4f((float)var16, (float)var17, (float)var18, (float)var15);
                    GL11.glLineWidth((float)2.0f);
                    GL11.glBegin((int)1);
                    GL11.glVertex2d((double)(this.width / 2.0f - 15.0f), (double)(var14 - 11));
                    GL11.glVertex2d((double)(this.width / 2.0f - 15.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)(this.width / 2.0f - 15.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)0.0, (double)(var14 + 4));
                    GL11.glVertex2d((double)0.0, (double)(var14 + 4));
                    GL11.glVertex2d((double)0.0, (double)9.0);
                    GL11.glVertex2d((double)0.0, (double)9.0);
                    GL11.glVertex2d((double)(this.width + 4.0f), (double)9.0);
                    GL11.glVertex2d((double)(this.width + 4.0f), (double)9.0);
                    GL11.glVertex2d((double)(this.width + 4.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)(this.width + 4.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)(this.width / 2.0f + 15.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)(this.width / 2.0f + 15.0f), (double)(var14 + 4));
                    GL11.glVertex2d((double)(this.width / 2.0f + 15.0f), (double)(var14 - 11));
                    GL11.glVertex2d((double)(this.width / 2.0f + 15.0f), (double)(var14 - 11));
                    GL11.glVertex2d((double)(this.width / 2.0f - 15.0f), (double)(var14 - 11));
                    GL11.glEnd();
                    GL11.glPopMatrix();
                    GL11.glEnable((int)3553);
                    GL11.glDisable((int)3042);
                    GL11.glDisable((int)2848);
                    RenderHelper.drawRect(this.width / 2.0f - 15.0f, var14 - 11, this.width / 2.0f + 15.0f, (float)var14 + 4.0f, var12);
                    RenderHelper.getNahrFont().drawString("Chat", this.width / 2.0f - 11.0f, var14 - 13, NahrFont.FontType.SHADOW_THICK, -1 + ((int)(255.0f * var6) << 24), -16777216 + ((int)(255.0f * var6) << 24));
                    RenderHelper.drawRect(0.0f, var14 + 4, this.width + 4.0f, 9.0f, var12);
                    this.width = 0.0f;
                } else if (var4 > 0 && this.width > 0.0f) {
                    var14 = -var4 * 9;
                    RenderHelper.drawBorderedRect(0.0f, var14 + 4, this.width + 4.0f, 9.0f, 2.0f, var13 + ((int)(255.0f * var6) << 24), var12 + ((int)(255.0f * var6) << 24));
                    this.width = 0.0f;
                }
                GL11.glLineWidth((float)1.0f);
                GL11.glTranslated((double)0.0, (double)5.0, (double)0.0);
                for (var8 = 0; var8 + this.scrollPos < this.field_146253_i.size() && var8 < var2; ++var8) {
                    int var9;
                    ChatLine var19 = (ChatLine)this.field_146253_i.get(var8 + this.scrollPos);
                    if (var19 == null || (var9 = p_146230_1_ - var19.getUpdatedCounter()) >= 200 && !var3) continue;
                    double var20 = (double)var9 / 200.0;
                    var20 = 1.0 - var20;
                    if ((var20 *= 10.0) < 0.0) {
                        var20 = 0.0;
                    }
                    if (var20 > 1.0) {
                        var20 = 1.0;
                    }
                    var20 *= var20;
                    var10 = (int)(255.0 * var20);
                    if (var3) {
                        var10 = 255;
                    }
                    ++var4;
                    if ((var10 *= (int)var6) <= 0) continue;
                    boolean var21 = true;
                    int var22 = -var8 * 9;
                    String var23 = FriendManager.replaceText(var19.getChatComponent().getFormattedText().replace("\u00e4", "ae").replace("\u00f6", "oe").replace("\u00fc", "ue").replace("hax", "IchLiebeDich"));
                    RenderHelper.getNahrFont().drawString(var23, 2.0f, var22 - 12, NahrFont.FontType.SHADOW_THICK, -1 + (var10 << 24), -16777216 + (var10 << 24));
                    float width = RenderHelper.getNahrFont().getStringWidth(StringUtils.stripControlCodes(var23));
                    if (this.width < width) {
                        this.width = width;
                    }
                    GL11.glDisable((int)3008);
                }
                GL11.glTranslated((double)(-this.x), (double)(-this.y), (double)0.0);
                if (var3) {
                    var8 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GL11.glTranslatef((float)-3.0f, (float)0.0f, (float)0.0f);
                    int var142 = var5 * var8 + var5;
                    int var9 = var4 * var8 + var4;
                    int var24 = this.scrollPos * var9 / var5;
                    int var25 = var9 * var9 / var142;
                    if (var142 != var9) {
                        var10 = var24 > 0 ? 170 : 96;
                        int var26 = this.isScrolled ? 13382451 : 3355562;
                        SonusChat.drawRect(0, -var24, 2, -var24 - var25, var26 + (var10 << 24));
                        SonusChat.drawRect(2, -var24, 1, -var24 - var25, 13421772 + (var10 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }

    private boolean isMouseOverTitle(int par1, int par2) {
        RenderUtils.getScaledRes();
        int height = ScaledResolution.getScaledHeight();
        return (float)par1 >= (float)this.x + this.width / 2.0f - 15.0f && par2 >= this.y + this.y1 - 20 + height - 48 && (float)par1 <= (float)this.x + this.width / 2.0f + 15.0f && par2 <= this.y + this.y1 - 6 + height - 48;
    }

    public void mouseClicked(int par1, int par2, int par3) {
        if (this.isMouseOverTitle(par1, par2) && par3 == 0) {
            this.drag = true;
            this.dragX = this.x - par1;
            this.dragY = this.y - par2;
        }
    }

    public void mouseReleased(int par1, int par2, int par3) {
        if (par3 == 0) {
            this.drag = false;
        }
    }

    public void prepareScissorBox(float x, float y, float x2, float y2) {
        int factor = RenderUtils.getScaledRes().getScaleFactor();
        RenderUtils.getScaledRes();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)ScaledResolution.getScaledHeight() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }
}

