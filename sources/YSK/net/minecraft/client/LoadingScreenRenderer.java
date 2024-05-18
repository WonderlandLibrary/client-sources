package net.minecraft.client;

import net.minecraft.client.shader.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private Framebuffer framebuffer;
    private boolean field_73724_e;
    private Minecraft mc;
    private String message;
    private static final String[] I;
    private ScaledResolution scaledResolution;
    private String currentlyDisplayedText;
    private long systemTime;
    
    static {
        I();
    }
    
    @Override
    public void setLoadingProgress(final int n) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final long systemTime = Minecraft.getSystemTime();
            if (systemTime - this.systemTime >= 100L) {
                this.systemTime = systemTime;
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                final int scaleFactor = scaledResolution.getScaleFactor();
                final int scaledWidth = scaledResolution.getScaledWidth();
                final int scaledHeight = scaledResolution.getScaledHeight();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferClear();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    GlStateManager.clear(141 + 75 + 1 + 39);
                }
                this.framebuffer.bindFramebuffer("".length() != 0);
                GlStateManager.matrixMode(3296 + 1694 - 4052 + 4951);
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
                GlStateManager.matrixMode(3217 + 5587 - 8031 + 5115);
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0f, 0.0f, -200.0f);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.clear(5307 + 3704 - 5421 + 13050);
                }
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                final float n2 = 32.0f;
                worldRenderer.begin(0x11 ^ 0x16, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(0.0, scaledHeight, 0.0).tex(0.0, scaledHeight / n2).color(0xD6 ^ 0x96, 0xD6 ^ 0x96, 0xD1 ^ 0x91, 62 + 224 - 113 + 82).endVertex();
                worldRenderer.pos(scaledWidth, scaledHeight, 0.0).tex(scaledWidth / n2, scaledHeight / n2).color(0x86 ^ 0xC6, 0x38 ^ 0x78, 0xC4 ^ 0x84, 254 + 81 - 125 + 45).endVertex();
                worldRenderer.pos(scaledWidth, 0.0, 0.0).tex(scaledWidth / n2, 0.0).color(0x1D ^ 0x5D, 0x4A ^ 0xA, 0x58 ^ 0x18, 120 + 39 + 17 + 79).endVertex();
                worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(0x55 ^ 0x15, 0xF6 ^ 0xB6, 0x13 ^ 0x53, 97 + 131 - 159 + 186).endVertex();
                instance.draw();
                if (n >= 0) {
                    final int n3 = 0xA ^ 0x6E;
                    final int length = "  ".length();
                    final int n4 = scaledWidth / "  ".length() - n3 / "  ".length();
                    final int n5 = scaledHeight / "  ".length() + (0xD4 ^ 0xC4);
                    GlStateManager.disableTexture2D();
                    worldRenderer.begin(0x58 ^ 0x5F, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(n4, n5, 0.0).color(34 + 2 - 2 + 94, 88 + 77 - 66 + 29, 116 + 66 - 125 + 71, 191 + 134 - 304 + 234).endVertex();
                    worldRenderer.pos(n4, n5 + length, 0.0).color(65 + 44 - 8 + 27, 67 + 9 - 1 + 53, 125 + 96 - 170 + 77, 106 + 136 - 216 + 229).endVertex();
                    worldRenderer.pos(n4 + n3, n5 + length, 0.0).color(86 + 96 - 91 + 37, 44 + 124 - 106 + 66, 23 + 118 - 134 + 121, 97 + 163 - 258 + 253).endVertex();
                    worldRenderer.pos(n4 + n3, n5, 0.0).color(22 + 46 - 48 + 108, 21 + 13 + 74 + 20, 59 + 67 - 117 + 119, 242 + 33 - 214 + 194).endVertex();
                    worldRenderer.pos(n4, n5, 0.0).color(72 + 65 - 134 + 125, 135 + 2 + 117 + 1, 2 + 20 + 97 + 9, 30 + 205 - 107 + 127).endVertex();
                    worldRenderer.pos(n4, n5 + length, 0.0).color(41 + 76 - 54 + 65, 186 + 246 - 229 + 52, 59 + 13 - 3 + 59, 30 + 98 - 97 + 224).endVertex();
                    worldRenderer.pos(n4 + n, n5 + length, 0.0).color(121 + 105 - 181 + 83, 174 + 112 - 107 + 76, 90 + 54 - 66 + 50, 90 + 53 - 141 + 253).endVertex();
                    worldRenderer.pos(n4 + n, n5, 0.0).color(81 + 24 - 89 + 112, 227 + 208 - 306 + 126, 5 + 41 + 37 + 45, 241 + 186 - 349 + 177).endVertex();
                    instance.draw();
                    GlStateManager.enableTexture2D();
                }
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(764 + 565 - 564 + 5, 166 + 733 - 217 + 89, " ".length(), "".length());
                this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, (scaledWidth - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / "  ".length(), scaledHeight / "  ".length() - (0x2A ^ 0x2E) - (0x96 ^ 0x86), 15375727 + 3726521 - 3920285 + 1595252);
                this.mc.fontRendererObj.drawStringWithShadow(this.message, (scaledWidth - this.mc.fontRendererObj.getStringWidth(this.message)) / "  ".length(), scaledHeight / "  ".length() - (0x6D ^ 0x69) + (0xCB ^ 0xC3), 11273622 + 8469052 - 4895378 + 1929919);
                this.framebuffer.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferRender(scaledWidth * scaleFactor, scaledHeight * scaleFactor);
                }
                this.mc.updateDisplay();
                try {
                    Thread.yield();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.field_73724_e = (" ".length() != 0);
        this.displayString(s);
    }
    
    @Override
    public void resetProgressAndMessage(final String s) {
        this.field_73724_e = ("".length() != 0);
        this.displayString(s);
    }
    
    @Override
    public void setDoneWorking() {
    }
    
    private void displayString(final String currentlyDisplayedText) {
        this.currentlyDisplayedText = currentlyDisplayedText;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            GlStateManager.clear(48 + 133 - 145 + 220);
            GlStateManager.matrixMode(2733 + 3932 - 4553 + 3777);
            GlStateManager.loadIdentity();
            if (OpenGlHelper.isFramebufferEnabled()) {
                final int scaleFactor = this.scaledResolution.getScaleFactor();
                GlStateManager.ortho(0.0, this.scaledResolution.getScaledWidth() * scaleFactor, this.scaledResolution.getScaledHeight() * scaleFactor, 0.0, 100.0, 300.0);
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
            }
            GlStateManager.matrixMode(4991 + 4726 - 7244 + 3415);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -200.0f);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("", "tvfmX");
        LoadingScreenRenderer.I[" ".length()] = I("", "bemEW");
    }
    
    public LoadingScreenRenderer(final Minecraft mc) {
        this.message = LoadingScreenRenderer.I["".length()];
        this.currentlyDisplayedText = LoadingScreenRenderer.I[" ".length()];
        this.systemTime = Minecraft.getSystemTime();
        this.mc = mc;
        this.scaledResolution = new ScaledResolution(mc);
        (this.framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, "".length() != 0)).setFramebufferFilter(4543 + 9048 - 9852 + 5989);
    }
    
    @Override
    public void displayLoadingString(final String message) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            this.systemTime = 0L;
            this.message = message;
            this.setLoadingProgress(-" ".length());
            this.systemTime = 0L;
        }
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
