package net.minecraft.src;

import net.minecraft.client.*;
import java.nio.charset.*;
import java.io.*;
import java.util.*;
import org.lwjgl.opengl.*;

public class GuiWinGame extends GuiScreen
{
    private int updateCounter;
    private List lines;
    private int field_73989_c;
    private float field_73987_d;
    
    public GuiWinGame() {
        this.updateCounter = 0;
        this.field_73989_c = 0;
        this.field_73987_d = 0.5f;
    }
    
    @Override
    public void updateScreen() {
        ++this.updateCounter;
        final float var1 = (this.field_73989_c + this.height + this.height + 24) / this.field_73987_d;
        if (this.updateCounter > var1) {
            this.respawnPlayer();
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 1) {
            this.respawnPlayer();
        }
    }
    
    private void respawnPlayer() {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void initGui() {
        if (this.lines == null) {
            this.lines = new ArrayList();
            try {
                String var1 = "";
                final String var2 = new StringBuilder().append(EnumChatFormatting.WHITE).append(EnumChatFormatting.OBFUSCATED).append(EnumChatFormatting.GREEN).append(EnumChatFormatting.AQUA).toString();
                final short var3 = 274;
                BufferedReader var4 = new BufferedReader(new InputStreamReader(GuiWinGame.class.getResourceAsStream("/title/win.txt"), Charset.forName("UTF-8")));
                final Random var5 = new Random(8124371L);
                while ((var1 = var4.readLine()) != null) {
                    String var7;
                    String var8;
                    for (var1 = var1.replaceAll("PLAYERNAME", this.mc.session.username); var1.contains(var2); var1 = String.valueOf(var7) + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8) {
                        final int var6 = var1.indexOf(var2);
                        var7 = var1.substring(0, var6);
                        var8 = var1.substring(var6 + var2.length());
                    }
                    this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.lines.add("");
                }
                for (int var6 = 0; var6 < 8; ++var6) {
                    this.lines.add("");
                }
                var4 = new BufferedReader(new InputStreamReader(GuiWinGame.class.getResourceAsStream("/title/credits.txt"), Charset.forName("UTF-8")));
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", this.mc.session.username);
                    var1 = var1.replaceAll("\t", "    ");
                    this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.lines.add("");
                }
                this.field_73989_c = this.lines.size() * 12;
            }
            catch (Exception var9) {
                var9.printStackTrace();
            }
        }
    }
    
    private void func_73986_b(final int par1, final int par2, final float par3) {
        final Tessellator var4 = Tessellator.instance;
        this.mc.renderEngine.bindTexture("%blur%/gui/background.png");
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        final int var5 = this.width;
        final float var6 = 0.0f - (this.updateCounter + par3) * 0.5f * this.field_73987_d;
        final float var7 = this.height - (this.updateCounter + par3) * 0.5f * this.field_73987_d;
        final float var8 = 0.015625f;
        float var9 = (this.updateCounter + par3 - 0.0f) * 0.02f;
        final float var10 = (this.field_73989_c + this.height + this.height + 24) / this.field_73987_d;
        final float var11 = (var10 - 20.0f - (this.updateCounter + par3)) * 0.005f;
        if (var11 < var9) {
            var9 = var11;
        }
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        var9 *= var9;
        var9 = var9 * 96.0f / 255.0f;
        var4.setColorOpaque_F(var9, var9, var9);
        var4.addVertexWithUV(0.0, this.height, this.zLevel, 0.0, var6 * var8);
        var4.addVertexWithUV(var5, this.height, this.zLevel, var5 * var8, var6 * var8);
        var4.addVertexWithUV(var5, 0.0, this.zLevel, var5 * var8, var7 * var8);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, var7 * var8);
        var4.draw();
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.func_73986_b(par1, par2, par3);
        final Tessellator var4 = Tessellator.instance;
        final short var5 = 274;
        final int var6 = this.width / 2 - var5 / 2;
        final int var7 = this.height + 50;
        final float var8 = -(this.updateCounter + par3) * this.field_73987_d;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, var8, 0.0f);
        this.mc.renderEngine.bindTexture("/title/mclogo.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(var6, var7, 0, 0, 155, 44);
        this.drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
        var4.setColorOpaque_I(16777215);
        int var9 = var7 + 200;
        for (int var10 = 0; var10 < this.lines.size(); ++var10) {
            if (var10 == this.lines.size() - 1) {
                final float var11 = var9 + var8 - (this.height / 2 - 6);
                if (var11 < 0.0f) {
                    GL11.glTranslatef(0.0f, -var11, 0.0f);
                }
            }
            if (var9 + var8 + 12.0f + 8.0f > 0.0f && var9 + var8 < this.height) {
                final String var12 = this.lines.get(var10);
                if (var12.startsWith("[C]")) {
                    this.fontRenderer.drawStringWithShadow(var12.substring(3), var6 + (var5 - this.fontRenderer.getStringWidth(var12.substring(3))) / 2, var9, 16777215);
                }
                else {
                    this.fontRenderer.fontRandom.setSeed(var10 * 4238972211L + this.updateCounter / 4);
                    this.fontRenderer.drawStringWithShadow(var12, var6, var9, 16777215);
                }
            }
            var9 += 12;
        }
        GL11.glPopMatrix();
        this.mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
        GL11.glEnable(3042);
        GL11.glBlendFunc(0, 769);
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var10 = this.width;
        final int var13 = this.height;
        var4.addVertexWithUV(0.0, var13, this.zLevel, 0.0, 1.0);
        var4.addVertexWithUV(var10, var13, this.zLevel, 1.0, 1.0);
        var4.addVertexWithUV(var10, 0.0, this.zLevel, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, 0.0);
        var4.draw();
        GL11.glDisable(3042);
        super.drawScreen(par1, par2, par3);
    }
}
