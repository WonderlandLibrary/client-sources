package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class GuiNewChat extends Gui
{
    private final Minecraft mc;
    private final List sentMessages;
    private final List chatLines;
    private final List field_96134_d;
    private int field_73768_d;
    private boolean field_73769_e;
    
    public GuiNewChat(final Minecraft par1Minecraft) {
        this.sentMessages = new ArrayList();
        this.chatLines = new ArrayList();
        this.field_96134_d = new ArrayList();
        this.field_73768_d = 0;
        this.field_73769_e = false;
        this.mc = par1Minecraft;
    }
    
    public void drawChat(final int par1) {
        if (this.mc.gameSettings.chatVisibility != 2) {
            final int var2 = this.func_96127_i();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.field_96134_d.size();
            final float var6 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.getChatOpen()) {
                    var3 = true;
                }
                final float var7 = this.func_96131_h();
                final int var8 = MathHelper.ceiling_float_int(this.func_96126_f() / var7);
                GL11.glPushMatrix();
                GL11.glTranslatef(2.0f, 20.0f, 0.0f);
                GL11.glScalef(var7, var7, 1.0f);
                for (int var9 = 0; var9 + this.field_73768_d < this.field_96134_d.size() && var9 < var2; ++var9) {
                    final ChatLine var10 = this.field_96134_d.get(var9 + this.field_73768_d);
                    if (var10 != null) {
                        final int var11 = par1 - var10.getUpdatedCounter();
                        if (var11 < 200 || var3) {
                            double var12 = var11 / 200.0;
                            var12 = 1.0 - var12;
                            var12 *= 10.0;
                            if (var12 < 0.0) {
                                var12 = 0.0;
                            }
                            if (var12 > 1.0) {
                                var12 = 1.0;
                            }
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
                                Gui.drawRect(var14, var15 - 9, var14 + var8 + 4, var15, var13 / 2 << 24);
                                GL11.glEnable(3042);
                                String var16 = var10.getChatLineString();
                                if (!this.mc.gameSettings.chatColours) {
                                    var16 = StringUtils.stripControlCodes(var16);
                                }
                                this.mc.fontRenderer.drawStringWithShadow(var16, var14, var15 - 8, 16777215 + (var13 << 24));
                            }
                        }
                    }
                }
                if (var3) {
                    final int var9 = this.mc.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef(-3.0f, 0.0f, 0.0f);
                    final int var17 = var5 * var9 + var5;
                    final int var11 = var4 * var9 + var4;
                    final int var18 = this.field_73768_d * var11 / var5;
                    final int var19 = var11 * var11 / var17;
                    if (var17 != var11) {
                        final int var13 = (var18 > 0) ? 170 : 96;
                        final int var20 = this.field_73769_e ? 13382451 : 3355562;
                        Gui.drawRect(0, -var18, 2, -var18 - var19, var20 + (var13 << 24));
                        Gui.drawRect(2, -var18, 1, -var18 - var19, 13421772 + (var13 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }
    
    public void clearChatMessages() {
        this.field_96134_d.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
    
    public void printChatMessage(final String par1Str) {
        this.printChatMessageWithOptionalDeletion(par1Str, 0);
    }
    
    public void printChatMessageWithOptionalDeletion(final String par1Str, final int par2) {
        this.func_96129_a(par1Str, par2, this.mc.ingameGUI.getUpdateCounter(), false);
        this.mc.getLogAgent().logInfo("[CHAT] " + par1Str);
    }
    
    private void func_96129_a(final String par1Str, final int par2, final int par3, final boolean par4) {
        final boolean var5 = this.getChatOpen();
        boolean var6 = true;
        if (par2 != 0) {
            this.deleteChatLine(par2);
        }
        for (String var8 : this.mc.fontRenderer.listFormattedStringToWidth(par1Str, MathHelper.floor_float(this.func_96126_f() / this.func_96131_h()))) {
            if (var5 && this.field_73768_d > 0) {
                this.field_73769_e = true;
                this.scroll(1);
            }
            if (!var6) {
                var8 = " " + var8;
            }
            var6 = false;
            this.field_96134_d.add(0, new ChatLine(par3, var8, par2));
        }
        while (this.field_96134_d.size() > 100) {
            this.field_96134_d.remove(this.field_96134_d.size() - 1);
        }
        if (!par4) {
            this.chatLines.add(0, new ChatLine(par3, par1Str.trim(), par2));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }
    
    public void func_96132_b() {
        this.field_96134_d.clear();
        this.resetScroll();
        for (int var1 = this.chatLines.size() - 1; var1 >= 0; --var1) {
            final ChatLine var2 = this.chatLines.get(var1);
            this.func_96129_a(var2.getChatLineString(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
        }
    }
    
    public List getSentMessages() {
        return this.sentMessages;
    }
    
    public void addToSentMessages(final String par1Str) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(par1Str)) {
            this.sentMessages.add(par1Str);
        }
    }
    
    public void resetScroll() {
        this.field_73768_d = 0;
        this.field_73769_e = false;
    }
    
    public void scroll(final int par1) {
        this.field_73768_d += par1;
        final int var2 = this.field_96134_d.size();
        if (this.field_73768_d > var2 - this.func_96127_i()) {
            this.field_73768_d = var2 - this.func_96127_i();
        }
        if (this.field_73768_d <= 0) {
            this.field_73768_d = 0;
            this.field_73769_e = false;
        }
    }
    
    public ChatClickData func_73766_a(final int par1, final int par2) {
        if (!this.getChatOpen()) {
            return null;
        }
        final ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        final int var4 = var3.getScaleFactor();
        final float var5 = this.func_96131_h();
        int var6 = par1 / var4 - 3;
        int var7 = par2 / var4 - 25;
        var6 = MathHelper.floor_float(var6 / var5);
        var7 = MathHelper.floor_float(var7 / var5);
        if (var6 < 0 || var7 < 0) {
            return null;
        }
        final int var8 = Math.min(this.func_96127_i(), this.field_96134_d.size());
        if (var6 <= MathHelper.floor_float(this.func_96126_f() / this.func_96131_h()) && var7 < this.mc.fontRenderer.FONT_HEIGHT * var8 + var8) {
            final int var9 = var7 / (this.mc.fontRenderer.FONT_HEIGHT + 1) + this.field_73768_d;
            return new ChatClickData(this.mc.fontRenderer, this.field_96134_d.get(var9), var6, var7 - (var9 - this.field_73768_d) * this.mc.fontRenderer.FONT_HEIGHT + var9);
        }
        return null;
    }
    
    public void addTranslatedMessage(final String par1Str, final Object... par2ArrayOfObj) {
        this.printChatMessage(StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj));
    }
    
    public boolean getChatOpen() {
        return Minecraft.currentScreen instanceof GuiChat;
    }
    
    public void deleteChatLine(final int par1) {
        Iterator var2 = this.field_96134_d.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.getChatLineID() == par1) {
                var2.remove();
                return;
            }
        }
        var2 = this.chatLines.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.getChatLineID() == par1) {
                var2.remove();
            }
        }
    }
    
    public int func_96126_f() {
        return func_96128_a(this.mc.gameSettings.chatWidth);
    }
    
    public int func_96133_g() {
        return func_96130_b(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }
    
    public float func_96131_h() {
        return this.mc.gameSettings.chatScale;
    }
    
    public static int func_96128_a(final float par0) {
        final short var1 = 320;
        final byte var2 = 40;
        return MathHelper.floor_float(par0 * (var1 - var2) + var2);
    }
    
    public static int func_96130_b(final float par0) {
        final short var1 = 180;
        final byte var2 = 20;
        return MathHelper.floor_float(par0 * (var1 - var2) + var2);
    }
    
    public int func_96127_i() {
        return this.func_96133_g() / 9;
    }
}
