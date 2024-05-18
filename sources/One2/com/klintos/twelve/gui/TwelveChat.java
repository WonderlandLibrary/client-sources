// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import com.klintos.twelve.utils.GuiUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.NahrFont;
import net.minecraft.client.gui.GuiNewChat;

public final class TwelveChat extends GuiNewChat
{
    private final NahrFont font;
    private final Minecraft mc;
    private int y1;
    private int mmmm;
    
    public TwelveChat(final Minecraft mc) {
        super(mc);
        this.font = new NahrFont(new Font("Calibri", 0, 22), true, false);
        this.mc = mc;
        this.x = 2;
        this.y = 20;
    }
    
    public void drawChat(final int p_146230_1_) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int var2 = this.getLineCount();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.field_146253_i.size();
            final float var6 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.getChatOpen()) {
                    var3 = true;
                }
                final float var7 = this.getChatScale();
                final int var8 = MathHelper.ceiling_float_int(this.getChatWidth() / var7);
                for (int var9 = 0; var9 + this.scrollPos < this.field_146253_i.size() && var9 < var2 - 3; ++var9) {
                    final ChatLine var10 = (ChatLine) this.field_146253_i.get(var9 + this.scrollPos);
                    if (var10 != null) {
                        final int var11 = p_146230_1_ - var10.getUpdatedCounter();
                        if (var11 < 200 || var3) {
                            double var12 = var11 / 200.0;
                            var12 = 1.0 - var12;
                            var12 *= 10.0;
                            var12 = MathHelper.clamp_double(var12, 0.0, 1.0);
                            var12 *= var12;
                            int var13 = (int)(255.0 * var12);
                            if (var3) {
                                var13 = 255;
                            }
                            var13 *= (int)var6;
                            ++var4;
                        }
                    }
                }
                GlStateManager.pushMatrix();
                GlStateManager.scale(var7, var7, 1.0f);
                final int width = this.getChatWidth();
                GlStateManager.translate((float)this.x, (float)this.y, 0.0f);
                this.y1 = -var4 * 11;
                GlStateManager.translate(0.0f, -8.0f, 0.0f);
                final int color = this.drag ? Integer.MIN_VALUE : 1610612736;
                final int bcolor = this.drag ? -1442840576 : -1879048192;
                if (this.getChatOpen()) {
                    final int mmm = -var4 * 11;
                    GuiUtils.drawBorderRectNoCorners(0, (this.mmmm = mmm) + 4, width - 18, 9, bcolor, color);
                }
                else if (var4 > 0) {
                    final int mmm = -var4 * 11;
                    GuiUtils.drawBorderRectNoCorners(0, mmm + 4, width - 18, 9, bcolor, color);
                }
                GL11.glLineWidth(1.0f);
                GlStateManager.translate(0.0f, 5.0f, 0.0f);
                for (int var14 = 0; var14 + this.scrollPos < this.field_146253_i.size() && var14 < var2 - 3; ++var14) {
                    final ChatLine var15 = (ChatLine) this.field_146253_i.get(var14 + this.scrollPos);
                    if (var15 != null) {
                        final int var16 = p_146230_1_ - var15.getUpdatedCounter();
                        if (var16 < 200 || var3) {
                            double var17 = var16 / 200.0;
                            var17 = 1.0 - var17;
                            var17 *= 10.0;
                            if (var17 < 0.0) {
                                var17 = 0.0;
                            }
                            if (var17 > 1.0) {
                                var17 = 1.0;
                            }
                            var17 *= var17;
                            int var18 = (int)(255.0 * var17);
                            if (var3) {
                                var18 = 255;
                            }
                            var18 *= (int)var6;
                            ++var4;
                            if (var18 > 0) {
                                final byte var19 = 2;
                                final int var20 = -var14 * 11;
                                final String var21 = var15.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                this.font.drawString(var21, 3.5f, var20 - 7.5f, NahrFont.FontType.SHADOW_THIN, -1 + (var18 << 24), var18 << 24);
                                GlStateManager.disableBlend();
                                GlStateManager.disableAlpha();
                            }
                        }
                    }
                }
                GlStateManager.translate((float)(-this.x), (float)(-this.y), 0.0f);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.func_179144_i(0);
    }
    
    private boolean isMouseOverTitle(final int par1, final int par2) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final int height = sr.getScaledHeight();
        return par1 >= this.x && par2 >= this.y + this.y1 - 20 + height - 32 && par1 <= this.x + this.getChatWidth() + 3 && par2 <= this.y + this.y1 - 16 + height - 32 - this.mmmm;
    }
    
    public void mouseClicked(final int par1, final int par2, final int par3) {
        if (this.isMouseOverTitle(par1, par2)) {
            this.drag = true;
            this.dragX = this.x - par1;
            this.dragY = this.y - par2;
        }
    }
    
    public void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        if (par3 == 0) {
            this.drag = false;
        }
    }
    
    public void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final int factor = sr.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((sr.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
}
