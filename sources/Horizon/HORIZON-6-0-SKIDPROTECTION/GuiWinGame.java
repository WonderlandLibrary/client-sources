package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Random;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.io.Charsets;
import com.google.common.collect.Lists;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private static final ResourceLocation_1975012498 Ý;
    private int Ø­áŒŠá;
    private List Âµá€;
    private int Ó;
    private float à;
    private static final String Ø = "CL_00000719";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ResourceLocation_1975012498("textures/gui/title/minecraft.png");
        Ý = new ResourceLocation_1975012498("textures/misc/vignette.png");
    }
    
    public GuiWinGame() {
        this.à = 0.5f;
    }
    
    @Override
    public void Ý() {
        ++this.Ø­áŒŠá;
        final float var1 = (this.Ó + GuiWinGame.Ê + GuiWinGame.Ê + 24) / this.à;
        if (this.Ø­áŒŠá > var1) {
            this.Ó();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.Ó();
        }
    }
    
    private void Ó() {
        GuiWinGame.Ñ¢á.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C16PacketClientStatus(C16PacketClientStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        GuiWinGame.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Âµá€ == null) {
            this.Âµá€ = Lists.newArrayList();
            try {
                String var1 = "";
                final String var2 = new StringBuilder().append(EnumChatFormatting.£à).append(EnumChatFormatting.µà).append(EnumChatFormatting.ÂµÈ).append(EnumChatFormatting.á).toString();
                final short var3 = 274;
                BufferedReader var4 = new BufferedReader(new InputStreamReader(GuiWinGame.Ñ¢á.Âµà().HorizonCode_Horizon_È(new ResourceLocation_1975012498("texts/end.txt")).Â(), Charsets.UTF_8));
                final Random var5 = new Random(8124371L);
                while ((var1 = var4.readLine()) != null) {
                    String var7;
                    String var8;
                    for (var1 = var1.replaceAll("PLAYERNAME", GuiWinGame.Ñ¢á.Õ().Ý()); var1.contains(var2); var1 = String.valueOf(var7) + EnumChatFormatting.£à + EnumChatFormatting.µà + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8) {
                        final int var6 = var1.indexOf(var2);
                        var7 = var1.substring(0, var6);
                        var8 = var1.substring(var6 + var2.length());
                    }
                    this.Âµá€.addAll(GuiWinGame.Ñ¢á.µà.Ý(var1, var3));
                    this.Âµá€.add("");
                }
                for (int var6 = 0; var6 < 8; ++var6) {
                    this.Âµá€.add("");
                }
                var4 = new BufferedReader(new InputStreamReader(GuiWinGame.Ñ¢á.Âµà().HorizonCode_Horizon_È(new ResourceLocation_1975012498("texts/credits.txt")).Â(), Charsets.UTF_8));
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", GuiWinGame.Ñ¢á.Õ().Ý());
                    var1 = var1.replaceAll("\t", "    ");
                    this.Âµá€.addAll(GuiWinGame.Ñ¢á.µà.Ý(var1, var3));
                    this.Âµá€.add("");
                }
                this.Ó = this.Âµá€.size() * 12;
            }
            catch (Exception var9) {
                GuiWinGame.HorizonCode_Horizon_È.error("Couldn't load credits", (Throwable)var9);
            }
        }
    }
    
    private void Â(final int p_146575_1_, final int p_146575_2_, final float p_146575_3_) {
        final Tessellator var4 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var5 = var4.Ý();
        GuiWinGame.Ñ¢á.¥à().HorizonCode_Horizon_È(Gui_1808253012.Šáƒ);
        var5.Â();
        var5.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f, 1.0f);
        final int var6 = GuiWinGame.Çªà¢;
        final float var7 = 0.0f - (this.Ø­áŒŠá + p_146575_3_) * 0.5f * this.à;
        final float var8 = GuiWinGame.Ê - (this.Ø­áŒŠá + p_146575_3_) * 0.5f * this.à;
        final float var9 = 0.015625f;
        float var10 = (this.Ø­áŒŠá + p_146575_3_ - 0.0f) * 0.02f;
        final float var11 = (this.Ó + GuiWinGame.Ê + GuiWinGame.Ê + 24) / this.à;
        final float var12 = (var11 - 20.0f - (this.Ø­áŒŠá + p_146575_3_)) * 0.005f;
        if (var12 < var10) {
            var10 = var12;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        var10 *= var10;
        var10 = var10 * 96.0f / 255.0f;
        var5.Â(var10, var10, var10);
        var5.HorizonCode_Horizon_È(0.0, GuiWinGame.Ê, GuiWinGame.ŠÄ, 0.0, var7 * var9);
        var5.HorizonCode_Horizon_È(var6, GuiWinGame.Ê, GuiWinGame.ŠÄ, var6 * var9, var7 * var9);
        var5.HorizonCode_Horizon_È(var6, 0.0, GuiWinGame.ŠÄ, var6 * var9, var8 * var9);
        var5.HorizonCode_Horizon_È(0.0, 0.0, GuiWinGame.ŠÄ, 0.0, var8 * var9);
        var4.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.Â(mouseX, mouseY, partialTicks);
        final Tessellator var4 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var5 = var4.Ý();
        final short var6 = 274;
        final int var7 = GuiWinGame.Çªà¢ / 2 - var6 / 2;
        final int var8 = GuiWinGame.Ê + 50;
        final float var9 = -(this.Ø­áŒŠá + partialTicks) * this.à;
        GlStateManager.Çªà¢();
        GlStateManager.Â(0.0f, var9, 0.0f);
        GuiWinGame.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiWinGame.Â);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Â(var7, var8, 0, 0, 155, 44);
        this.Â(var7 + 155, var8, 0, 45, 155, 44);
        var5.Ý(16777215);
        int var10 = var8 + 200;
        for (int var11 = 0; var11 < this.Âµá€.size(); ++var11) {
            if (var11 == this.Âµá€.size() - 1) {
                final float var12 = var10 + var9 - (GuiWinGame.Ê / 2 - 6);
                if (var12 < 0.0f) {
                    GlStateManager.Â(0.0f, -var12, 0.0f);
                }
            }
            if (var10 + var9 + 12.0f + 8.0f > 0.0f && var10 + var9 < GuiWinGame.Ê) {
                final String var13 = this.Âµá€.get(var11);
                if (var13.startsWith("[C]")) {
                    this.É.HorizonCode_Horizon_È(var13.substring(3), var7 + (var6 - this.É.HorizonCode_Horizon_È(var13.substring(3))) / 2, (float)var10, 16777215);
                }
                else {
                    this.É.Â.setSeed(var11 * 4238972211L + this.Ø­áŒŠá / 4);
                    this.É.HorizonCode_Horizon_È(var13, var7, (float)var10, 16777215);
                }
            }
            var10 += 12;
        }
        GlStateManager.Ê();
        GuiWinGame.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiWinGame.Ý);
        GlStateManager.á();
        GlStateManager.Â(0, 769);
        var5.Â();
        var5.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f, 1.0f);
        int var11 = GuiWinGame.Çªà¢;
        final int var14 = GuiWinGame.Ê;
        var5.HorizonCode_Horizon_È(0.0, var14, GuiWinGame.ŠÄ, 0.0, 1.0);
        var5.HorizonCode_Horizon_È(var11, var14, GuiWinGame.ŠÄ, 1.0, 1.0);
        var5.HorizonCode_Horizon_È(var11, 0.0, GuiWinGame.ŠÄ, 1.0, 0.0);
        var5.HorizonCode_Horizon_È(0.0, 0.0, GuiWinGame.ŠÄ, 0.0, 0.0);
        var4.Â();
        GlStateManager.ÂµÈ();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
