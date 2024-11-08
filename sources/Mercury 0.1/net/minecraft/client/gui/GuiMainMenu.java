/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;
import superblaubeere27.particle.ParticleSystem;
import us.amerikan.gui.AltLogin;
import us.amerikan.gui.CreditsGui;
import us.amerikan.gui.FontUtils;

public class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random field_175374_h = new Random();
    private float updateCounter;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private boolean field_175375_v;
    private final Object field_104025_t;
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation field_110351_G;
    private GuiButton field_175372_K;
    private static final String __OBFID = "CL_00001154";
    private ParticleSystem particleSystem;
    private FontUtils font;
    private FontUtils fontsmall;

    public GuiMainMenu() {
        block18 : {
            this.field_175375_v = true;
            this.field_104025_t = new Object();
            this.font = new FontUtils("Comfortaa", 0, 52);
            this.fontsmall = new FontUtils("Comfortaa", 2, 18);
            this.field_146972_A = field_96138_a;
            this.splashText = "missingno";
            BufferedReader var1 = null;
            try {
                try {
                    String var3;
                    ArrayList<String> var2 = Lists.newArrayList();
                    var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
                    while ((var3 = var1.readLine()) != null) {
                        if ((var3 = var3.trim()).isEmpty()) continue;
                        var2.add(var3);
                    }
                    if (!var2.isEmpty()) {
                        do {
                            this.splashText = (String)var2.get(field_175374_h.nextInt(var2.size()));
                        } while (this.splashText.hashCode() == 125780783);
                    }
                }
                catch (IOException var2) {
                    if (var1 != null) {
                        try {
                            var1.close();
                        }
                        catch (IOException iOException) {}
                    }
                    break block18;
                }
            }
            catch (Throwable throwable) {
                if (var1 != null) {
                    try {
                        var1.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                throw throwable;
            }
            if (var1 != null) {
                try {
                    var1.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        this.updateCounter = field_175374_h.nextFloat();
        this.field_92025_p = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.field_92025_p = I18n.format("title.oldgl1", new Object[0]);
            this.field_146972_A = I18n.format("title.oldgl2", new Object[0]);
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        } else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        } else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        boolean var2 = true;
        int var3 = this.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(var3, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(var3, 24);
        }
        this.buttonList.add(new GuiButton(0, 10, this.height / 2 + 28, 130, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, 10, this.height / 2 + 50, 130, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButton(187, 10, this.height / 2 + 72, 130, 20, I18n.format("Credits", new Object[0])));
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int var5 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - var5) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get((int)0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + var5;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        this.buttonList.add(new GuiButton(1, 10, this.height / 2 - 50, 130, 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, 10, this.height / 2 - 28, 130, 20, I18n.format("menu.multiplayer", new Object[0])));
        this.field_175372_K = new GuiButton(14, 10, this.height / 2, 130, 20, I18n.format("Alt-Login", new Object[0]));
        this.buttonList.add(this.field_175372_K);
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0]));
        this.buttonList.add(this.buttonResetDemo);
        ISaveFormat var3 = this.mc.getSaveLoader();
        WorldInfo var4 = var3.getWorldInfo("Demo_World");
        if (var4 == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        WorldInfo var3;
        ISaveFormat var2;
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 14) {
            this.mc.displayGuiScreen(new AltLogin(this));
        }
        if (button.id == 187) {
            this.mc.displayGuiScreen(new CreditsGui(this));
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (button.id == 12 && (var3 = (var2 = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
            this.mc.displayGuiScreen(var4);
        }
    }

    private void switchToRealms() {
        RealmsBridge var1 = new RealmsBridge();
        var1.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean result, int id2) {
        if (result && id2 == 12) {
            ISaveFormat var6 = this.mc.getSaveLoader();
            var6.flushCache();
            var6.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id2 == 13) {
            if (result) {
                try {
                    Class<?> var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    var3.getMethod("browse", URI.class).invoke(var4, new URI(this.field_104024_v));
                }
                catch (Throwable var5) {
                    logger.error("Couldn't open link", var5);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int var6 = 8;
        for (int var7 = 0; var7 < var6 * var6; ++var7) {
            GlStateManager.pushMatrix();
            float var8 = ((float)(var7 % var6) / (float)var6 - 0.5f) / 64.0f;
            float var9 = ((float)(var7 / var6) / (float)var6 - 0.5f) / 64.0f;
            float var10 = 0.0f;
            GlStateManager.translate(var8, var9, var10);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int var11 = 0; var11 < 6; ++var11) {
                GlStateManager.pushMatrix();
                if (var11 == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var11 == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var11]);
                var5.startDrawingQuads();
                var5.func_178974_a(16777215, 255 / (var7 + 1));
                float var12 = 0.0f;
                var5.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var12, 0.0f + var12);
                var5.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var12, 0.0f + var12);
                var5.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var12, 1.0f - var12);
                var5.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var12, 1.0f - var12);
                var4.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        var5.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_) {
        this.mc.getTextureManager().bindTexture(this.field_110351_G);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        var3.startDrawingQuads();
        GlStateManager.disableAlpha();
        int var4 = 3;
        for (int var5 = 0; var5 < var4; ++var5) {
            var3.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f / (float)(var5 + 1));
            int var6 = this.width;
            int var7 = this.height;
            float var8 = (float)(var5 - var4 / 2) / 256.0f;
            var3.addVertexWithUV(var6, var7, zLevel, 0.0f + var8, 1.0);
            var3.addVertexWithUV(var6, 0.0, zLevel, 1.0f + var8, 1.0);
            var3.addVertexWithUV(0.0, 0.0, zLevel, 1.0f + var8, 0.0);
            var3.addVertexWithUV(0.0, var7, zLevel, 0.0f + var8, 0.0);
        }
        var2.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        var5.startDrawingQuads();
        float var6 = this.width > this.height ? 120.0f / (float)this.width : 120.0f / (float)this.height;
        float var7 = (float)this.height * var6 / 256.0f;
        float var8 = (float)this.width * var6 / 256.0f;
        var5.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        int var9 = this.width;
        int var10 = this.height;
        var5.addVertexWithUV(0.0, var10, zLevel, 0.5f - var7, 0.5f + var8);
        var5.addVertexWithUV(var9, var10, zLevel, 0.5f - var7, 0.5f - var8);
        var5.addVertexWithUV(var9, 0.0, zLevel, 0.5f + var7, 0.5f - var8);
        var5.addVertexWithUV(0.0, 0.0, zLevel, 0.5f + var7, 0.5f + var8);
        var4.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(1);
        GuiMainMenu.drawRect(0, 0, 150, this.height, -1156246251);
        this.font.drawString("Mercury", 30.0f, this.height / 2 - 100, Color.white.getRGB());
        this.fontsmall.drawString("by Hizzy, Garpex & SnowCode", 2.0f, this.height - 16, Color.white.getRGB());
        if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
            GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            GuiMainMenu.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
            GuiMainMenu.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            if (this.field_92025_p.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                var5.disableSecurityWarning();
                this.mc.displayGuiScreen(var5);
            }
        }
    }
}

