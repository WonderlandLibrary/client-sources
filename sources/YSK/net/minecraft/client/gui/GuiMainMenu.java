package net.minecraft.client.gui;

import java.util.concurrent.atomic.*;
import net.minecraft.client.renderer.texture.*;
import java.net.*;
import java.lang.reflect.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.realms.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import org.apache.commons.io.*;
import java.io.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.storage.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private int field_92023_s;
    private final Object threadLock;
    private String openGLWarning2;
    private static final Logger logger;
    private static final AtomicInteger field_175373_f;
    private float updateCounter;
    public static final String field_96138_a;
    private static final String[] I;
    private GuiButton buttonResetDemo;
    private static final ResourceLocation minecraftTitleTextures;
    private int field_92021_u;
    private ResourceLocation backgroundTexture;
    private String openGLWarning1;
    private static final Random RANDOM;
    private static final ResourceLocation[] titlePanoramaPaths;
    private int panoramaTimer;
    private String splashText;
    private int field_92024_r;
    private int field_92022_t;
    private DynamicTexture viewportTexture;
    private int field_92020_v;
    private String openGLWarningLink;
    private GuiButton realmsButton;
    private int field_92019_w;
    private static final ResourceLocation splashTexts;
    private boolean field_175375_v;
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b && n == (0x2E ^ 0x22)) {
            final ISaveFormat saveLoader = this.mc.getSaveLoader();
            saveLoader.flushCache();
            saveLoader.deleteWorldDirectory(GuiMainMenu.I[0x5B ^ 0x44]);
            this.mc.displayGuiScreen(this);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else if (n == (0x31 ^ 0x3C)) {
            if (b) {
                try {
                    final Class<?> forName = Class.forName(GuiMainMenu.I[0x73 ^ 0x53]);
                    final Object invoke = forName.getMethod(GuiMainMenu.I[0x2 ^ 0x23], (Class<?>[])new Class["".length()]).invoke(null, new Object["".length()]);
                    final Class<?> clazz = forName;
                    final String s = GuiMainMenu.I[0x10 ^ 0x32];
                    final Class[] array = new Class[" ".length()];
                    array["".length()] = URI.class;
                    final Method method = clazz.getMethod(s, (Class<?>[])array);
                    final Object o = invoke;
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = new URI(this.openGLWarningLink);
                    method.invoke(o, array2);
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    GuiMainMenu.logger.error(GuiMainMenu.I[0xA5 ^ 0x86], t);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void drawPanorama(final int n, final int n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.matrixMode(3795 + 1537 - 2534 + 3091);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(2139 + 2044 - 2207 + 3912);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.tryBlendFuncSeparate(673 + 275 - 281 + 103, 659 + 189 - 518 + 441, " ".length(), "".length());
        final int n4 = 0x1F ^ 0x17;
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < n4 * n4) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((i % n4 / n4 - 0.5f) / 64.0f, (i / n4 / n4 - 0.5f) / 64.0f, 0.0f);
            GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + n3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-(this.panoramaTimer + n3) * 0.1f, 0.0f, 1.0f, 0.0f);
            int j = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j < (0x1D ^ 0x1B)) {
                GlStateManager.pushMatrix();
                if (j == " ".length()) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (j == "  ".length()) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (j == "   ".length()) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (j == (0x75 ^ 0x71)) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (j == (0x42 ^ 0x47)) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(GuiMainMenu.titlePanoramaPaths[j]);
                worldRenderer.begin(0x4E ^ 0x49, DefaultVertexFormats.POSITION_TEX_COLOR);
                final int n5 = (13 + 87 - 31 + 186) / (i + " ".length());
                worldRenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(221 + 132 - 283 + 185, 152 + 215 - 240 + 128, 8 + 50 + 25 + 172, n5).endVertex();
                worldRenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(115 + 203 - 206 + 143, 62 + 190 - 77 + 80, 142 + 65 - 171 + 219, n5).endVertex();
                worldRenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(229 + 3 - 225 + 248, 88 + 159 - 100 + 108, 97 + 70 - 131 + 219, n5).endVertex();
                worldRenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(211 + 48 - 112 + 108, 191 + 137 - 298 + 225, 178 + 131 - 290 + 236, n5).endVertex();
                instance.draw();
                GlStateManager.popMatrix();
                ++j;
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
            ++i;
        }
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
        GlStateManager.matrixMode(2092 + 4861 - 2983 + 1919);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(1498 + 4186 - 1167 + 1371);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }
    
    @Override
    public void updateScreen() {
        this.panoramaTimer += " ".length();
    }
    
    private void switchToRealms() {
        new RealmsBridge().switchToRealms(this);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        GlStateManager.disableAlpha();
        this.renderSkybox(n, n2, n3);
        GlStateManager.enableAlpha();
        Tessellator.getInstance().getWorldRenderer();
        final int n4 = this.width / "  ".length() - (78 + 75 - 83 + 204) / "  ".length();
        final int n5 = 0x9 ^ 0x17;
        this.drawGradientRect("".length(), "".length(), this.width, this.height, -(1371999912 + 932335471 - 382579740 + 208950790), 6609249 + 11350133 - 13989054 + 12806887);
        this.drawGradientRect("".length(), "".length(), this.width, this.height, "".length(), -"".length());
        this.mc.getTextureManager().bindTexture(GuiMainMenu.minecraftTitleTextures);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.updateCounter < 1.0E-4) {
            this.drawTexturedModalRect(n4 + "".length(), n5 + "".length(), "".length(), "".length(), 0xFD ^ 0x9E, 0x0 ^ 0x2C);
            this.drawTexturedModalRect(n4 + (0x26 ^ 0x45), n5 + "".length(), 77 + 97 - 145 + 100, "".length(), 0x19 ^ 0x2, 0x15 ^ 0x39);
            this.drawTexturedModalRect(n4 + (0xC6 ^ 0xA5) + (0x77 ^ 0x6D), n5 + "".length(), 0xF3 ^ 0x8D, "".length(), "   ".length(), 0x66 ^ 0x4A);
            this.drawTexturedModalRect(n4 + (0xD2 ^ 0xB1) + (0x48 ^ 0x52) + "   ".length(), n5 + "".length(), 0xD8 ^ 0xBB, "".length(), 0xBD ^ 0xA7, 0x1E ^ 0x32);
            this.drawTexturedModalRect(n4 + (6 + 72 + 9 + 68), n5 + "".length(), "".length(), 0x7D ^ 0x50, 19 + 24 + 88 + 24, 0x5C ^ 0x70);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            this.drawTexturedModalRect(n4 + "".length(), n5 + "".length(), "".length(), "".length(), 5 + 101 - 105 + 154, 0x74 ^ 0x58);
            this.drawTexturedModalRect(n4 + (23 + 130 - 39 + 41), n5 + "".length(), "".length(), 0x98 ^ 0xB5, 104 + 154 - 162 + 59, 0x87 ^ 0xAB);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / "  ".length() + (0x3A ^ 0x60), 70.0f, 0.0f);
        GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
        final float n6 = (1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f)) * 100.0f / (this.fontRendererObj.getStringWidth(this.splashText) + (0x9E ^ 0xBE));
        GlStateManager.scale(n6, n6, n6);
        this.drawCenteredString(this.fontRendererObj, this.splashText, "".length(), -(0x9F ^ 0x97), -(15 + 127 - 106 + 220));
        GlStateManager.popMatrix();
        String string = GuiMainMenu.I[0x22 ^ 0x6];
        if (this.mc.isDemo()) {
            string = String.valueOf(string) + GuiMainMenu.I[0x68 ^ 0x4D];
        }
        this.drawString(this.fontRendererObj, string, "  ".length(), this.height - (0x1C ^ 0x16), -" ".length());
        final String s = GuiMainMenu.I[0xE4 ^ 0xC2];
        this.drawString(this.fontRendererObj, s, this.width - this.fontRendererObj.getStringWidth(s) - "  ".length(), this.height - (0x4A ^ 0x40), -" ".length());
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            Gui.drawRect(this.field_92022_t - "  ".length(), this.field_92021_u - "  ".length(), this.field_92020_v + "  ".length(), this.field_92019_w - " ".length(), 925642850 + 401394499 - 738105086 + 839228249);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -" ".length());
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / "  ".length(), this.buttonList.get("".length()).yPosition - (0x91 ^ 0x9D), -" ".length());
        }
        super.drawScreen(n, n2, n3);
    }
    
    private void addDemoButtons(final int n, final int n2) {
        this.buttonList.add(new GuiButton(0xA2 ^ 0xA9, this.width / "  ".length() - (0x5E ^ 0x3A), n, I18n.format(GuiMainMenu.I[0x9A ^ 0x83], new Object["".length()])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(0x91 ^ 0x9D, this.width / "  ".length() - (0xE9 ^ 0x8D), n + n2 * " ".length(), I18n.format(GuiMainMenu.I[0xAA ^ 0xB0], new Object["".length()])));
        if (this.mc.getSaveLoader().getWorldInfo(GuiMainMenu.I[0x5C ^ 0x47]) == null) {
            this.buttonResetDemo.enabled = ("".length() != 0);
        }
    }
    
    private void renderSkybox(final int n, final int n2, final float n3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport("".length(), "".length(), 43 + 162 - 167 + 218, 120 + 101 - 118 + 153);
        this.drawPanorama(n, n2, n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.mc.getFramebuffer().bindFramebuffer(" ".length() != 0);
        GlStateManager.viewport("".length(), "".length(), this.mc.displayWidth, this.mc.displayHeight);
        float n4;
        if (this.width > this.height) {
            n4 = 120.0f / this.width;
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n4 = 120.0f / this.height;
        }
        final float n5 = n4;
        final float n6 = this.height * n5 / 256.0f;
        final float n7 = this.width * n5 / 256.0f;
        final int width = this.width;
        final int height = this.height;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x3A ^ 0x3D, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, height, this.zLevel).tex(0.5f - n6, 0.5f + n7).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, height, this.zLevel).tex(0.5f - n6, 0.5f - n7).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, 0.0, this.zLevel).tex(0.5f + n6, 0.5f - n7).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.5f + n6, 0.5f + n7).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        instance.draw();
    }
    
    private void rotateAndBlurSkybox(final float n) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3229 + 2306 - 5002 + 3020, 480 + 7076 - 4240 + 6925, 418 + 2154 + 3215 + 3942);
        GL11.glTexParameteri(2236 + 3276 - 4261 + 2302, 1903 + 5853 - 5764 + 8248, 2137 + 3461 + 3493 + 638);
        GL11.glCopyTexSubImage2D(2412 + 1931 - 1382 + 592, "".length(), "".length(), "".length(), "".length(), "".length(), 75 + 197 - 106 + 90, 86 + 180 - 246 + 236);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(497 + 290 - 25 + 8, 455 + 67 - 149 + 398, " ".length(), "".length());
        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x32 ^ 0x35, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        final int length = "   ".length();
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < length) {
            final float n2 = 1.0f / (i + " ".length());
            final int width = this.width;
            final int height = this.height;
            final float n3 = (i - length / "  ".length()) / 256.0f;
            worldRenderer.pos(width, height, this.zLevel).tex(0.0f + n3, 1.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(width, 0.0, this.zLevel).tex(1.0f + n3, 1.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(0.0, 0.0, this.zLevel).tex(1.0f + n3, 0.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(0.0, height, this.zLevel).tex(0.0f + n3, 0.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            ++i;
        }
        instance.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
    }
    
    private void addSingleplayerMultiplayerButtons(final int n, final int n2) {
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0x31 ^ 0x55), n, I18n.format(GuiMainMenu.I[0xB6 ^ 0xA0], new Object["".length()])));
        this.buttonList.add(new GuiButton("  ".length(), this.width / "  ".length() - (0xDD ^ 0xB9), n + n2 * " ".length(), I18n.format(GuiMainMenu.I[0xBE ^ 0xA9], new Object["".length()])));
        this.buttonList.add(this.realmsButton = new GuiButton(0x18 ^ 0x16, this.width / "  ".length() - (0x3C ^ 0x58), n + n2 * "  ".length(), I18n.format(GuiMainMenu.I[0x67 ^ 0x7F], new Object["".length()])));
    }
    
    private static void I() {
        (I = new String[0xBF ^ 0x98])["".length()] = I("\u001d?3\u00066F);\u001e$\u001a2.\u0001k\u001d\"?", "iZKrE");
        GuiMainMenu.I[" ".length()] = I("\u0013\r4\"3\u0015\r?y!\u0012\u0001c\"/\u0013\u0004)y+\u000e\u0006)54\u0006\u000e8x6\t\u000f", "ghLVF");
        GuiMainMenu.I["  ".length()] = I("\"-\u001e06$-\u0015k$#!I0*\"$\u0003k!7+\r#19=\b l&)\b+17%\u0007\u001bsx8\b#", "VHfDC");
        GuiMainMenu.I["   ".length()] = I("?\u000e\u0014\u001f\u00139\u000e\u001fD\u0001>\u0002C\u001f\u000f?\u0007\tD\u0004*\b\u0007\f\u0014$\u001e\u0002\u000fI;\n\u0002\u0004\u0014*\u0006\r4We\u001b\u0002\f", "Kklkf");
        GuiMainMenu.I[0x78 ^ 0x7C] = I("\u0000\u0010\u0012\u000e\u0006\u0006\u0010\u0019U\u0014\u0001\u001cE\u000e\u001a\u0000\u0019\u000fU\u0011\u0015\u0016\u0001\u001d\u0001\u001b\u0000\u0004\u001e\\\u0004\u0014\u0004\u0015\u0001\u0015\u0018\u000b%AZ\u0005\u0004\u001d", "tujzs");
        GuiMainMenu.I[0x2A ^ 0x2F] = I("\u000145,;\u00074>w)\u00008b,'\u0001=(w,\u00142&?<\u001a$#<a\u00050#7<\u0014<,\u0007}[!#?", "uQMXN");
        GuiMainMenu.I[0x89 ^ 0x8F] = I(".#)-\u0018(#\"v\n//~-\u0004.*4v\u000f;%:>\u001f53?=B*'?6\u001f;+0\u0006Yt6?>", "ZFQYm");
        GuiMainMenu.I[0xC5 ^ 0xC2] = I("\u0002\u0014< \u0014\u0004\u00147{\u0006\u0003\u0018k \b\u0002\u001d!{\u0003\u0017\u0012/3\u0013\u0019\u0004*0N\u0006\u0010*;\u0013\u0017\u001c%\u000bTX\u0001*3", "vqDTa");
        GuiMainMenu.I[0x8 ^ 0x0] = I("$/\".0\u0011c$#*\u0017(g", "tCGOC");
        GuiMainMenu.I[0x82 ^ 0x8B] = I("&\u000105", "NdBPa");
        GuiMainMenu.I[0x2C ^ 0x26] = I("v-<$k;$!3k?%599;*'?$8e", "VKSVK");
        GuiMainMenu.I[0x54 ^ 0x5F] = I(")\u001d\u0014\u0005<*\u0013\t\u0019", "DtgvU");
        GuiMainMenu.I[0x61 ^ 0x6D] = I("", "JVaRw");
        GuiMainMenu.I[0xA1 ^ 0xAC] = I(">\u001d\u0019\u0005\u000fd\u001b\u0001\r\r&E", "Jtmij");
        GuiMainMenu.I[0xA4 ^ 0xAA] = I("5?='\u001fo9%/\u001d-d", "AVIKz");
        GuiMainMenu.I[0x74 ^ 0x7B] = I("\u001c\u0010-&\u001eNKv>\b\u0018\u0014w;\u0002\u001e\u000571C\u0017\u000b4y\u000e\u0001\u0017-9\u0000\u0011\u0016v&\u0002\u0006\u00108:B\u0015\u0016-?\u000e\u0018\u0001*y^FQ`bUK\u0016<0P\u0013\u000543", "tdYVm");
        GuiMainMenu.I[0xBD ^ 0xAD] = I("4\n$\n\u0015$\u00042\u000f\u0016", "VkGar");
        GuiMainMenu.I[0x38 ^ 0x29] = I("\u001e\u0014\u001c\u001c\u001fs)C\u0003\u0007 P", "Sqnnf");
        GuiMainMenu.I[0xAF ^ 0xBD] = I("\"\b:1\u0015J\u0007/6L\u0013\f+3M", "jiJAl");
        GuiMainMenu.I[0x9 ^ 0x1A] = I("8:\u000b\u0015#8:+\u0015#\u0018\u001aEZ\u001f\u0007\u001a\u000b\u00115V", "wudzL");
        GuiMainMenu.I[0xB1 ^ 0xA5] = I("(\u001f(>m*\n2\",+\t", "EzFKC");
        GuiMainMenu.I[0xAD ^ 0xB8] = I("\u0015\u0012>\u0000{\t\u00029\u0001", "xwPuU");
        GuiMainMenu.I[0x4 ^ 0x12] = I("\u001d+\u001c&d\u0003'\u001c4&\u0015>\u001e23\u0015<", "pNrSJ");
        GuiMainMenu.I[0x1A ^ 0xD] = I(">$:<|>48=;#-507!", "SATIR");
        GuiMainMenu.I[0x4A ^ 0x52] = I("\u0018\u0003\u001f;G\u001a\b\u001d'\u0007\u0010", "ufqNi");
        GuiMainMenu.I[0xDD ^ 0xC4] = I("=!+\u001c_ ($\u0010\u00155)*", "PDEiq");
        GuiMainMenu.I[0x1C ^ 0x6] = I("!\u0003 $V>\u0003=4\f(\u0003#>", "LfNQx");
        GuiMainMenu.I[0x7C ^ 0x67] = I("\u001c\u000f<)\u001a\u000f\u0005#*!", "XjQFE");
        GuiMainMenu.I[0x5D ^ 0x41] = I("6\f\u0019\u000e2%\u0006\u0006\r\t", "ritam");
        GuiMainMenu.I[0x89 ^ 0x94] = I("\r'\u0018\u00077\u001e-\u0007\u0004\f", "IBuhh");
        GuiMainMenu.I[0x83 ^ 0x9D] = I("'\u000f\u0018%%4\u0005\u0007&\u001e", "cjuJz");
        GuiMainMenu.I[0x9C ^ 0x83] = I("\u0013\u0006'\u001c%\u0000\f8\u001f\u001e", "WcJsz");
        GuiMainMenu.I[0x5F ^ 0x7F] = I("\b\u0004&'{\u0003\u0012$h\u0011\u0007\u0016;2:\u0012", "bePFU");
        GuiMainMenu.I[0x9B ^ 0xBA] = I("\u0006\u0011\u0019\"/\u0012\u001f\u0019\t:", "atmfJ");
        GuiMainMenu.I[0x53 ^ 0x71] = I("!(\u0000\u0016\u0015&", "CZoaf");
        GuiMainMenu.I[0x7F ^ 0x5C] = I("'\f\u0003\u00050\nD\u0002I;\u0014\u0006\u0018I8\r\r\u001d", "dcviT");
        GuiMainMenu.I[0x9B ^ 0xBF] = I("+\u001b\rl9CfvbI", "rHFLy");
        GuiMainMenu.I[0x38 ^ 0x1D] = I("x4$\u001f&", "XpArI");
        GuiMainMenu.I[0x6B ^ 0x4D] = I("%\u0002\u001a\u000e\u0003\u000f\n\u0002\u0003Q+\u0002\u0000\u0016\u001f\u0001M+5_F)\u0005W\u001f\t\u0019J\u0013\u0018\u0015\u0019\u0018\u001e\u0013\u0013\u0019\u000fV", "fmjwq");
    }
    
    public GuiMainMenu() {
        this.field_175375_v = (" ".length() != 0);
        this.threadLock = new Object();
        this.openGLWarning2 = GuiMainMenu.field_96138_a;
        this.splashText = GuiMainMenu.I[0x96 ^ 0x9D];
        BufferedReader bufferedReader = null;
        Label_0291: {
            try {
                final ArrayList arrayList = Lists.newArrayList();
                bufferedReader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(GuiMainMenu.splashTexts).getInputStream(), Charsets.UTF_8));
                "".length();
                if (1 < 0) {
                    throw null;
                }
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final String trim = line.trim();
                    if (!trim.isEmpty()) {
                        arrayList.add(trim);
                    }
                }
                if (!arrayList.isEmpty()) {
                    do {
                        this.splashText = (String)arrayList.get(GuiMainMenu.RANDOM.nextInt(arrayList.size()));
                    } while (this.splashText.hashCode() == 70686672 + 97828309 - 111043897 + 68309699);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
            }
            catch (IOException ex) {
                if (bufferedReader == null) {
                    break Label_0291;
                }
                try {
                    bufferedReader.close();
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                    break Label_0291;
                }
                catch (IOException ex2) {
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    break Label_0291;
                }
            }
            finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                        "".length();
                        if (3 < 1) {
                            throw null;
                        }
                    }
                    catch (IOException ex3) {}
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                catch (IOException ex4) {}
            }
        }
        this.updateCounter = GuiMainMenu.RANDOM.nextFloat();
        this.openGLWarning1 = GuiMainMenu.I[0x6 ^ 0xA];
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format(GuiMainMenu.I[0x72 ^ 0x7F], new Object["".length()]);
            this.openGLWarning2 = I18n.format(GuiMainMenu.I[0x25 ^ 0x2B], new Object["".length()]);
            this.openGLWarningLink = GuiMainMenu.I[0x6D ^ 0x62];
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        field_175373_f = new AtomicInteger("".length());
        logger = LogManager.getLogger();
        RANDOM = new Random();
        splashTexts = new ResourceLocation(GuiMainMenu.I["".length()]);
        minecraftTitleTextures = new ResourceLocation(GuiMainMenu.I[" ".length()]);
        final ResourceLocation[] titlePanoramaPaths2 = new ResourceLocation[0x8B ^ 0x8D];
        titlePanoramaPaths2["".length()] = new ResourceLocation(GuiMainMenu.I["  ".length()]);
        titlePanoramaPaths2[" ".length()] = new ResourceLocation(GuiMainMenu.I["   ".length()]);
        titlePanoramaPaths2["  ".length()] = new ResourceLocation(GuiMainMenu.I[0x84 ^ 0x80]);
        titlePanoramaPaths2["   ".length()] = new ResourceLocation(GuiMainMenu.I[0xA1 ^ 0xA4]);
        titlePanoramaPaths2[0x6B ^ 0x6F] = new ResourceLocation(GuiMainMenu.I[0xBB ^ 0xBD]);
        titlePanoramaPaths2[0x65 ^ 0x60] = new ResourceLocation(GuiMainMenu.I[0xB1 ^ 0xB6]);
        titlePanoramaPaths = titlePanoramaPaths2;
        field_96138_a = GuiMainMenu.I[0x58 ^ 0x50] + EnumChatFormatting.UNDERLINE + GuiMainMenu.I[0xA8 ^ 0xA1] + EnumChatFormatting.RESET + GuiMainMenu.I[0x8 ^ 0x2];
    }
    
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(69 + 151 - 57 + 93, 31 + 68 + 157 + 0);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation(GuiMainMenu.I[0x5F ^ 0x4F], this.viewportTexture);
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if (instance.get("  ".length()) + " ".length() == (0x93 ^ 0x9F) && instance.get(0x10 ^ 0x15) == (0x34 ^ 0x2C)) {
            this.splashText = GuiMainMenu.I[0x47 ^ 0x56];
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (instance.get("  ".length()) + " ".length() == " ".length() && instance.get(0x1F ^ 0x1A) == " ".length()) {
            this.splashText = GuiMainMenu.I[0x5B ^ 0x49];
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (instance.get("  ".length()) + " ".length() == (0x78 ^ 0x72) && instance.get(0x66 ^ 0x63) == (0x68 ^ 0x77)) {
            this.splashText = GuiMainMenu.I[0x7E ^ 0x6D];
        }
        final int n = this.height / (0x92 ^ 0x96) + (0x16 ^ 0x26);
        if (this.mc.isDemo()) {
            this.addDemoButtons(n, 0x5F ^ 0x47);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            this.addSingleplayerMultiplayerButtons(n, 0x96 ^ 0x8E);
        }
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (0xC4 ^ 0xA0), n + (0x8E ^ 0xC6) + (0x2E ^ 0x22), 0x55 ^ 0x37, 0xB0 ^ 0xA4, I18n.format(GuiMainMenu.I[0x59 ^ 0x4D], new Object["".length()])));
        this.buttonList.add(new GuiButton(0xBE ^ 0xBA, this.width / "  ".length() + "  ".length(), n + (0x53 ^ 0x1B) + (0xBC ^ 0xB0), 0xF9 ^ 0x9B, 0x4F ^ 0x5B, I18n.format(GuiMainMenu.I[0x60 ^ 0x75], new Object["".length()])));
        this.buttonList.add(new GuiButtonLanguage(0x5E ^ 0x5B, this.width / "  ".length() - (0x0 ^ 0x7C), n + (0x42 ^ 0xA) + (0x13 ^ 0x1F)));
        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            final int max = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - max) / "  ".length();
            this.field_92021_u = this.buttonList.get("".length()).yPosition - (0xA0 ^ 0xB8);
            this.field_92020_v = this.field_92022_t + max;
            this.field_92019_w = this.field_92021_u + (0x60 ^ 0x78);
            // monitorexit(this.threadLock)
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        this.mc.func_181537_a("".length() != 0);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && n >= this.field_92022_t && n <= this.field_92020_v && n2 >= this.field_92021_u && n2 <= this.field_92019_w) {
                final GuiConfirmOpenLink guiConfirmOpenLink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 0xA7 ^ 0xAA, " ".length() != 0);
                guiConfirmOpenLink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiConfirmOpenLink);
            }
            // monitorexit(this.threadLock)
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (guiButton.id == (0x0 ^ 0x5)) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (guiButton.id == "  ".length()) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (guiButton.id == (0x37 ^ 0x39) && this.realmsButton.visible) {
            this.switchToRealms();
        }
        if (guiButton.id == (0x9 ^ 0xD)) {
            this.mc.shutdown();
        }
        if (guiButton.id == (0x57 ^ 0x5C)) {
            this.mc.launchIntegratedServer(GuiMainMenu.I[0x2E ^ 0x32], GuiMainMenu.I[0x74 ^ 0x69], DemoWorldServer.demoWorldSettings);
        }
        if (guiButton.id == (0x21 ^ 0x2D)) {
            final WorldInfo worldInfo = this.mc.getSaveLoader().getWorldInfo(GuiMainMenu.I[0x6 ^ 0x18]);
            if (worldInfo != null) {
                this.mc.displayGuiScreen(GuiSelectWorld.func_152129_a(this, worldInfo.getWorldName(), 0x9 ^ 0x5));
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return "".length() != 0;
    }
}
