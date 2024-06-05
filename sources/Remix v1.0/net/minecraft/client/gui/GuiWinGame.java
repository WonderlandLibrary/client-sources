package net.minecraft.client.gui;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;

public class GuiWinGame extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146576_f;
    private static final ResourceLocation field_146577_g;
    private int field_146581_h;
    private List field_146582_i;
    private int field_146579_r;
    private float field_146578_s;
    
    public GuiWinGame() {
        this.field_146578_s = 0.5f;
    }
    
    @Override
    public void updateScreen() {
        ++this.field_146581_h;
        final float var1 = (this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s;
        if (this.field_146581_h > var1) {
            this.sendRespawnPacket();
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            this.sendRespawnPacket();
        }
    }
    
    private void sendRespawnPacket() {
        GuiWinGame.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        GuiWinGame.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void initGui() {
        if (this.field_146582_i == null) {
            this.field_146582_i = Lists.newArrayList();
            try {
                String var1 = "";
                final String var2 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
                final short var3 = 274;
                BufferedReader var4 = new BufferedReader(new InputStreamReader(GuiWinGame.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
                final Random var5 = new Random(8124371L);
                while ((var1 = var4.readLine()) != null) {
                    String var7;
                    String var8;
                    for (var1 = var1.replaceAll("PLAYERNAME", GuiWinGame.mc.getSession().getUsername()); var1.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8) {
                        final int var6 = var1.indexOf(var2);
                        var7 = var1.substring(0, var6);
                        var8 = var1.substring(var6 + var2.length());
                    }
                    this.field_146582_i.addAll(GuiWinGame.mc.fontRendererObj.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                for (int var6 = 0; var6 < 8; ++var6) {
                    this.field_146582_i.add("");
                }
                var4 = new BufferedReader(new InputStreamReader(GuiWinGame.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8));
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", GuiWinGame.mc.getSession().getUsername());
                    var1 = var1.replaceAll("\t", "    ");
                    this.field_146582_i.addAll(GuiWinGame.mc.fontRendererObj.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                this.field_146579_r = this.field_146582_i.size() * 12;
            }
            catch (Exception var9) {
                GuiWinGame.logger.error("Couldn't load credits", (Throwable)var9);
            }
        }
    }
    
    private void drawWinGameScreen(final int p_146575_1_, final int p_146575_2_, final float p_146575_3_) {
        final Tessellator var4 = Tessellator.getInstance();
        final WorldRenderer var5 = var4.getWorldRenderer();
        GuiWinGame.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        var5.startDrawingQuads();
        var5.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        final int var6 = GuiWinGame.width;
        final float var7 = 0.0f - (this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        final float var8 = GuiWinGame.height - (this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        final float var9 = 0.015625f;
        float var10 = (this.field_146581_h + p_146575_3_ - 0.0f) * 0.02f;
        final float var11 = (this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s;
        final float var12 = (var11 - 20.0f - (this.field_146581_h + p_146575_3_)) * 0.005f;
        if (var12 < var10) {
            var10 = var12;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        var10 *= var10;
        var10 = var10 * 96.0f / 255.0f;
        var5.func_178986_b(var10, var10, var10);
        var5.addVertexWithUV(0.0, GuiWinGame.height, GuiWinGame.zLevel, 0.0, var7 * var9);
        var5.addVertexWithUV(var6, GuiWinGame.height, GuiWinGame.zLevel, var6 * var9, var7 * var9);
        var5.addVertexWithUV(var6, 0.0, GuiWinGame.zLevel, var6 * var9, var8 * var9);
        var5.addVertexWithUV(0.0, 0.0, GuiWinGame.zLevel, 0.0, var8 * var9);
        var4.draw();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawWinGameScreen(mouseX, mouseY, partialTicks);
        final Tessellator var4 = Tessellator.getInstance();
        final WorldRenderer var5 = var4.getWorldRenderer();
        final short var6 = 274;
        final int var7 = GuiWinGame.width / 2 - var6 / 2;
        final int var8 = GuiWinGame.height + 50;
        final float var9 = -(this.field_146581_h + partialTicks) * this.field_146578_s;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, var9, 0.0f);
        GuiWinGame.mc.getTextureManager().bindTexture(GuiWinGame.field_146576_f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(var7, var8, 0, 0, 155, 44);
        this.drawTexturedModalRect(var7 + 155, var8, 0, 45, 155, 44);
        var5.func_178991_c(16777215);
        int var10 = var8 + 200;
        for (int var11 = 0; var11 < this.field_146582_i.size(); ++var11) {
            if (var11 == this.field_146582_i.size() - 1) {
                final float var12 = var10 + var9 - (GuiWinGame.height / 2 - 6);
                if (var12 < 0.0f) {
                    GlStateManager.translate(0.0f, -var12, 0.0f);
                }
            }
            if (var10 + var9 + 12.0f + 8.0f > 0.0f && var10 + var9 < GuiWinGame.height) {
                final String var13 = this.field_146582_i.get(var11);
                if (var13.startsWith("[C]")) {
                    this.fontRendererObj.func_175063_a(var13.substring(3), (float)(var7 + (var6 - this.fontRendererObj.getStringWidth(var13.substring(3))) / 2), (float)var10, 16777215);
                }
                else {
                    this.fontRendererObj.fontRandom.setSeed(var11 * 4238972211L + this.field_146581_h / 4);
                    this.fontRendererObj.func_175063_a(var13, (float)var7, (float)var10, 16777215);
                }
            }
            var10 += 12;
        }
        GlStateManager.popMatrix();
        GuiWinGame.mc.getTextureManager().bindTexture(GuiWinGame.field_146577_g);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(0, 769);
        var5.startDrawingQuads();
        var5.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        int var11 = GuiWinGame.width;
        final int var14 = GuiWinGame.height;
        var5.addVertexWithUV(0.0, var14, GuiWinGame.zLevel, 0.0, 1.0);
        var5.addVertexWithUV(var11, var14, GuiWinGame.zLevel, 1.0, 1.0);
        var5.addVertexWithUV(var11, 0.0, GuiWinGame.zLevel, 1.0, 0.0);
        var5.addVertexWithUV(0.0, 0.0, GuiWinGame.zLevel, 0.0, 0.0);
        var4.draw();
        GlStateManager.disableBlend();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        logger = LogManager.getLogger();
        field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
        field_146577_g = new ResourceLocation("textures/misc/vignette.png");
    }
}
