package net.minecraft.client.gui;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.audio.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class GuiWinGame extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation VIGNETTE_TEXTURE;
    private static final String[] I;
    private int field_146581_h;
    private static final ResourceLocation MINECRAFT_LOGO;
    private List<String> field_146582_i;
    private float field_146578_s;
    private int field_146579_r;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawWinGameScreen(n, n2, n3);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final int n4 = 66 + 104 + 26 + 78;
        final int n5 = this.width / "  ".length() - n4 / "  ".length();
        final int n6 = this.height + (0x29 ^ 0x1B);
        final float n7 = -(this.field_146581_h + n3) * this.field_146578_s;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, n7, 0.0f);
        this.mc.getTextureManager().bindTexture(GuiWinGame.MINECRAFT_LOGO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(n5, n6, "".length(), "".length(), 78 + 1 - 33 + 109, 0x45 ^ 0x69);
        this.drawTexturedModalRect(n5 + (27 + 40 + 78 + 10), n6, "".length(), 0x1D ^ 0x30, 128 + 64 - 70 + 33, 0xA4 ^ 0x88);
        int n8 = n6 + (118 + 116 - 207 + 173);
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < this.field_146582_i.size()) {
            if (i == this.field_146582_i.size() - " ".length()) {
                final float n9 = n8 + n7 - (this.height / "  ".length() - (0x85 ^ 0x83));
                if (n9 < 0.0f) {
                    GlStateManager.translate(0.0f, -n9, 0.0f);
                }
            }
            if (n8 + n7 + 12.0f + 8.0f > 0.0f && n8 + n7 < this.height) {
                final String s = this.field_146582_i.get(i);
                if (s.startsWith(GuiWinGame.I[0x71 ^ 0x7F])) {
                    this.fontRendererObj.drawStringWithShadow(s.substring("   ".length()), n5 + (n4 - this.fontRendererObj.getStringWidth(s.substring("   ".length()))) / "  ".length(), n8, 2119427 + 13285591 - 7917706 + 9289903);
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else {
                    this.fontRendererObj.fontRandom.setSeed(i * 4238972211L + this.field_146581_h / (0x6E ^ 0x6A));
                    this.fontRendererObj.drawStringWithShadow(s, n5, n8, 11570092 + 2372398 - 12380804 + 15215529);
                }
            }
            n8 += 12;
            ++i;
        }
        GlStateManager.popMatrix();
        this.mc.getTextureManager().bindTexture(GuiWinGame.VIGNETTE_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc("".length(), 291 + 232 + 101 + 145);
        final int width = this.width;
        final int height = this.height;
        worldRenderer.begin(0x47 ^ 0x40, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, height, this.zLevel).tex(0.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, height, this.zLevel).tex(1.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, 0.0, this.zLevel).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        instance.draw();
        GlStateManager.disableBlend();
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x6B ^ 0x64])["".length()] = I("\u0016.(6&\u0010.#m4\u0017\"\u007f6:\u0016'5m>\u000b%5!!\u0003-$l#\f,", "bKPBS");
        GuiWinGame.I[" ".length()] = I(".\u0013*7\f(\u0013!l\u00143\u00051l\u000f3\u0011<&\r.\u0013|3\u0017=", "ZvRCy");
        GuiWinGame.I["  ".length()] = I("", "iCdvj");
        GuiWinGame.I["   ".length()] = I(",++\u0004\u0012w+=\u0014O,6'", "XNSpa");
        GuiWinGame.I[0x1E ^ 0x1A] = I(":\u0007\u0003\u001e\u00118\u0005\u0003\n\u0011", "jKBGT");
        GuiWinGame.I[0x38 ^ 0x3D] = I("\u0013\u0010!\f\b\u0013\u0010!", "KHyTP");
        GuiWinGame.I[0x19 ^ 0x1F] = I("", "YkZRk");
        GuiWinGame.I[0x6D ^ 0x6A] = I("", "onatd");
        GuiWinGame.I[0x9A ^ 0x92] = I("\u0015\u000f?';N\t56,\b\u001e4}<\u0019\u001e", "ajGSH");
        GuiWinGame.I[0x3A ^ 0x33] = I("#5\u0019\u00017!7\u0019\u00157", "syXXr");
        GuiWinGame.I[0xB7 ^ 0xBD] = I("n", "gXJbf");
        GuiWinGame.I[0x22 ^ 0x29] = I("gYgg", "GyGGM");
        GuiWinGame.I[0x53 ^ 0x5F] = I("", "IIXRE");
        GuiWinGame.I[0x59 ^ 0x54] = I("\u0004!\u001e\u00164)i\u001fZ<(/\u000fZ35+\u000f\u0013$4", "GNkzP");
        GuiWinGame.I[0x7A ^ 0x74] = I("\u0001..", "ZmswC");
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == " ".length()) {
            this.sendRespawnPacket();
        }
    }
    
    public GuiWinGame() {
        this.field_146578_s = 0.5f;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return " ".length() != 0;
    }
    
    private void sendRespawnPacket() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public void updateScreen() {
        final MusicTicker func_181535_r = this.mc.func_181535_r();
        final SoundHandler soundHandler = this.mc.getSoundHandler();
        if (this.field_146581_h == 0) {
            func_181535_r.func_181557_a();
            func_181535_r.func_181558_a(MusicTicker.MusicType.CREDITS);
            soundHandler.resumeSounds();
        }
        soundHandler.update();
        this.field_146581_h += " ".length();
        if (this.field_146581_h > (this.field_146579_r + this.height + this.height + (0x92 ^ 0x8A)) / this.field_146578_s) {
            this.sendRespawnPacket();
        }
    }
    
    @Override
    public void initGui() {
        if (this.field_146582_i == null) {
            this.field_146582_i = (List<String>)Lists.newArrayList();
            try {
                final String s = GuiWinGame.I["  ".length()];
                final String string = new StringBuilder().append(EnumChatFormatting.WHITE).append(EnumChatFormatting.OBFUSCATED).append(EnumChatFormatting.GREEN).append(EnumChatFormatting.AQUA).toString();
                final int n = 245 + 210 - 190 + 9;
                final InputStream inputStream = this.mc.getResourceManager().getResource(new ResourceLocation(GuiWinGame.I["   ".length()])).getInputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
                final Random random = new Random(8124371L);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String s2 = line.replaceAll(GuiWinGame.I[0x10 ^ 0x14], this.mc.getSession().getUsername());
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    while (s2.contains(string)) {
                        final int index = s2.indexOf(string);
                        s2 = String.valueOf(s2.substring("".length(), index)) + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + GuiWinGame.I[0x87 ^ 0x82].substring("".length(), random.nextInt(0x7A ^ 0x7E) + "   ".length()) + s2.substring(index + string.length());
                    }
                    this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s2, n));
                    this.field_146582_i.add(GuiWinGame.I[0x5A ^ 0x5C]);
                }
                inputStream.close();
                int i = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
                while (i < (0x27 ^ 0x2F)) {
                    this.field_146582_i.add(GuiWinGame.I[0xA9 ^ 0xAE]);
                    ++i;
                }
                final InputStream inputStream2 = this.mc.getResourceManager().getResource(new ResourceLocation(GuiWinGame.I[0x44 ^ 0x4C])).getInputStream();
                final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2, Charsets.UTF_8));
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                String line2;
                while ((line2 = bufferedReader2.readLine()) != null) {
                    this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(line2.replaceAll(GuiWinGame.I[0x6D ^ 0x64], this.mc.getSession().getUsername()).replaceAll(GuiWinGame.I[0x9 ^ 0x3], GuiWinGame.I[0x9D ^ 0x96]), n));
                    this.field_146582_i.add(GuiWinGame.I[0x6D ^ 0x61]);
                }
                inputStream2.close();
                this.field_146579_r = this.field_146582_i.size() * (0x46 ^ 0x4A);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (Exception ex) {
                GuiWinGame.logger.error(GuiWinGame.I[0x64 ^ 0x69], (Throwable)ex);
            }
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        MINECRAFT_LOGO = new ResourceLocation(GuiWinGame.I["".length()]);
        VIGNETTE_TEXTURE = new ResourceLocation(GuiWinGame.I[" ".length()]);
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void drawWinGameScreen(final int n, final int n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        worldRenderer.begin(0x3 ^ 0x4, DefaultVertexFormats.POSITION_TEX_COLOR);
        final int width = this.width;
        final float n4 = 0.0f - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n5 = this.height - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n6 = 0.015625f;
        float n7 = (this.field_146581_h + n3 - 0.0f) * 0.02f;
        final float n8 = ((this.field_146579_r + this.height + this.height + (0x77 ^ 0x6F)) / this.field_146578_s - 20.0f - (this.field_146581_h + n3)) * 0.005f;
        if (n8 < n7) {
            n7 = n8;
        }
        if (n7 > 1.0f) {
            n7 = 1.0f;
        }
        final float n9 = n7 * n7 * 96.0f / 255.0f;
        worldRenderer.pos(0.0, this.height, this.zLevel).tex(0.0, n4 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(width, this.height, this.zLevel).tex(width * n6, n4 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(width, 0.0, this.zLevel).tex(width * n6, n5 * n6).color(n9, n9, n9, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.0, n5 * n6).color(n9, n9, n9, 1.0f).endVertex();
        instance.draw();
    }
}
