// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import net.minecraft.util.EnumChatFormatting;
import intent.AquaDev.aqua.fontrenderer.ClientFont;
import org.apache.logging.log4j.LogManager;
import net.minecraft.util.Session;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import intent.AquaDev.aqua.alt.design.Login;
import com.thealtening.auth.service.AlteningServiceType;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.api.retriever.BasicDataRetriever;
import com.google.common.base.Strings;
import com.thealtening.api.TheAltening;
import intent.AquaDev.aqua.altloader.Api;
import intent.AquaDev.aqua.altloader.RedeemResponse;
import intent.AquaDev.aqua.altloader.Callback;
import intent.AquaDev.aqua.msauth.MicrosoftAuthentication;
import io.netty.util.internal.ThreadLocalRandom;
import org.lwjgl.input.Mouse;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.optifine.CustomPanoramaProperties;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.optifine.CustomPanorama;
import org.lwjgl.util.glu.Project;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import java.net.URI;
import net.minecraft.world.demo.DemoWorldServer;
import net.optifine.reflect.Reflector;
import intent.AquaDev.aqua.alt.design.AltManager;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.realms.RealmsBridge;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import intent.AquaDev.aqua.Aqua;
import java.util.Date;
import java.util.Calendar;
import net.minecraft.client.settings.GameSettings;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GLContext;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.commons.io.Charsets;
import net.minecraft.client.Minecraft;
import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.DynamicTexture;
import de.Hero.settings.GuiColorChooser2;
import com.thealtening.auth.TheAlteningAuthentication;
import intent.AquaDev.aqua.fontrenderer.GlyphPageFontRenderer;
import intent.AquaDev.aqua.altloader.AltLoader;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import java.util.Random;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger field_175373_f;
    private static final Logger logger;
    private static final Random RANDOM;
    public Animate anim;
    private static final AltLoader altLoader;
    public static GlyphPageFontRenderer font3;
    public boolean altManager;
    private boolean doneWaiting;
    private long animationStart;
    private static TheAlteningAuthentication alteningAuthentication;
    private final float updateCounter;
    public static GuiColorChooser2 colorChooser2;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final boolean field_175375_v = true;
    String apiKey;
    private final Object threadLock;
    boolean onStart;
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts;
    private static final ResourceLocation minecraftTitleTextures;
    private static final ResourceLocation[] titlePanoramaPaths;
    public static final String field_96138_a;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private GuiButton realmsButton;
    private boolean field_183502_L;
    private GuiScreen field_183503_M;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;
    
    public GuiMainMenu() {
        this.anim = new Animate();
        this.doneWaiting = false;
        this.apiKey = "";
        this.threadLock = new Object();
        this.onStart = true;
        this.animationStart = System.currentTimeMillis();
        this.openGLWarning2 = GuiMainMenu.field_96138_a;
        this.field_183502_L = false;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;
        try {
            final List<String> list = (List<String>)Lists.newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(GuiMainMenu.splashTexts).getInputStream(), Charsets.UTF_8));
            String s;
            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();
                if (!s.isEmpty()) {
                    list.add(s);
                }
            }
            if (!list.isEmpty()) {
                do {
                    this.splashText = list.get(GuiMainMenu.RANDOM.nextInt(list.size()));
                } while (this.splashText.hashCode() == 125780783);
            }
        }
        catch (IOException ex) {}
        finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                }
                catch (IOException ex2) {}
            }
        }
        this.updateCounter = GuiMainMenu.RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }
    
    private boolean func_183501_a() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.field_183503_M != null;
    }
    
    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
        if (this.func_183501_a()) {
            this.field_183503_M.updateScreen();
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        final int i = 24;
        final int j = GuiMainMenu.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        }
        else {
            this.buttonList.add(new IMGButton(1, GuiMainMenu.width / 2 - 105, j + 72 - 20, 50, 50, "Singleplayer", new ResourceLocation("Aqua/gui/sp1.png")));
        }
        this.buttonList.add(new IMGButton(2, GuiMainMenu.width / 2 + 10, j + 72 - 20, 50, 50, "Multiplayer", new ResourceLocation("Aqua/gui/mp.png")));
        this.buttonList.add(new IMGButton(0, GuiMainMenu.width / 2 + 65, j + 72 - 20, 50, 50, "Options", new ResourceLocation("Aqua/gui/settings.png")));
        this.buttonList.add(new IMGButton(26, GuiMainMenu.width / 2 - 50, j + 72 - 20, 50, 50, "AltManager", new ResourceLocation("Aqua/gui/am.png")));
        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            final int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (GuiMainMenu.width - k) / 2;
            this.field_92021_u = this.buttonList.get(0).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }
        if (!Aqua.allowed) {
            try {
                Display.releaseContext();
            }
            catch (LWJGLException e) {
                e.printStackTrace();
            }
        }
        this.mc.setConnectedToRealms(false);
        if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L) {
            final RealmsBridge realmsbridge = new RealmsBridge();
            this.field_183502_L = true;
        }
        if (this.func_183501_a()) {
            this.field_183503_M.setGuiSize(GuiMainMenu.width, GuiMainMenu.height);
            this.field_183503_M.initGui();
        }
    }
    
    private void addSingleplayerMultiplayerButtons(final int p_73969_1_, final int p_73969_2_) {
        this.buttonList.add(new GuiButton(1, GuiMainMenu.width / 2 - 50, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiMainMenu.width / 2 - 50, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
    }
    
    private void addDemoButtons(final int p_73972_1_, final int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, GuiMainMenu.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, GuiMainMenu.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        final ISaveFormat isaveformat = this.mc.getSaveLoader();
        final WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.altManager = false;
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 26) {
            this.altManager = true;
            this.mc.displayGuiScreen(new AltManager(this));
        }
        if (button.id == 5) {
            this.altManager = false;
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (button.id == 1) {
            this.altManager = false;
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.altManager = false;
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 14 && this.realmsButton.visible) {
            this.switchToRealms();
        }
        if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
            this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, this));
        }
        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (button.id == 12) {
            final ISaveFormat isaveformat = this.mc.getSaveLoader();
            final WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
            if (worldinfo != null) {
                final GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }
    
    private void switchToRealms() {
        final RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        if (result && id == 12) {
            final ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13) {
            if (result) {
                try {
                    final Class<?> oclass = Class.forName("java.awt.Desktop");
                    final Object object = oclass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                }
                catch (Throwable throwable) {
                    GuiMainMenu.logger.error("Couldn't open link", throwable);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void drawPanorama(final int p_73970_1_, final int p_73970_2_, final float p_73970_3_) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
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
        final int i = 8;
        int j = 64;
        final CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            j = custompanoramaproperties.getBlur1();
        }
        for (int k = 0; k < j; ++k) {
            GlStateManager.pushMatrix();
            final float f = (k % 8 / 8.0f - 0.5f) / 64.0f;
            final float f2 = (k / 8 / 8.0f - 0.5f) / 64.0f;
            final float f3 = 0.0f;
            GlStateManager.translate(f, f2, 0.0f);
            GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int l = 0; l < 6; ++l) {
                GlStateManager.pushMatrix();
                if (l == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (l == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (l == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (l == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (l == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                ResourceLocation[] aresourcelocation = GuiMainMenu.titlePanoramaPaths;
                if (custompanoramaproperties != null) {
                    aresourcelocation = custompanoramaproperties.getPanoramaLocations();
                }
                this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                final int i2 = 255 / (k + 1);
                final float f4 = 0.0f;
                worldrenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, i2).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, i2).endVertex();
                worldrenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, i2).endVertex();
                worldrenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, i2).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        worldrenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }
    
    private void rotateAndBlurSkybox(final float p_73968_1_) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        final int i = 3;
        int j = 3;
        final CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            j = custompanoramaproperties.getBlur2();
        }
        for (int k = 0; k < j; ++k) {
            final float f = 1.0f / (k + 1);
            final int l = GuiMainMenu.width;
            final int i2 = GuiMainMenu.height;
            final float f2 = (k - 1) / 256.0f;
            worldrenderer.pos(l, i2, GuiMainMenu.zLevel).tex(0.0f + f2, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(l, 0.0, GuiMainMenu.zLevel).tex(1.0f + f2, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, 0.0, GuiMainMenu.zLevel).tex(1.0f + f2, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            worldrenderer.pos(0.0, i2, GuiMainMenu.zLevel).tex(0.0f + f2, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }
    
    private void renderSkybox(final int p_73971_1_, final int p_73971_2_, final float p_73971_3_) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        int i = 3;
        final CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            i = custompanoramaproperties.getBlur3();
        }
        for (int j = 0; j < i; ++j) {
            this.rotateAndBlurSkybox(p_73971_3_);
            this.rotateAndBlurSkybox(p_73971_3_);
        }
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        final float f2 = (GuiMainMenu.width > GuiMainMenu.height) ? (120.0f / GuiMainMenu.width) : (120.0f / GuiMainMenu.height);
        final float f3 = GuiMainMenu.height * f2 / 256.0f;
        final float f4 = GuiMainMenu.width * f2 / 256.0f;
        final int k = GuiMainMenu.width;
        final int l = GuiMainMenu.height;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0, l, GuiMainMenu.zLevel).tex(0.5f - f3, 0.5f + f4).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(k, l, GuiMainMenu.zLevel).tex(0.5f - f3, 0.5f - f4).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(k, 0.0, GuiMainMenu.zLevel).tex(0.5f + f3, 0.5f - f4).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldrenderer.pos(0.0, 0.0, GuiMainMenu.zLevel).tex(0.5f + f3, 0.5f + f4).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (Aqua.INSTANCE.ircClient.getNickname().equalsIgnoreCase("ClientQUI9240")) {
            System.exit(0);
        }
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        final int k = 30;
        int i1 = 16777215;
        int j1 = 0;
        int k2 = Integer.MIN_VALUE;
        final CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            i1 = custompanoramaproperties.getOverlay1Bottom();
            j1 = custompanoramaproperties.getOverlay2Top();
            k2 = custompanoramaproperties.getOverlay2Bottom();
        }
        if (Aqua.INSTANCE.ircClient.getNickname().equalsIgnoreCase("DaddyGay")) {
            Aqua.INSTANCE.shaderBackgroundMM.renderShader();
        }
        else if (!GL11.glGetString(7937).contains("AMD") && !GL11.glGetString(7937).contains("RX ")) {
            Aqua.INSTANCE.shaderBackgroundMM.renderShader();
        }
        else {
            this.drawDefaultBackground();
        }
        if (this.onStart) {
            final ScaledResolution scaledResolution2 = new ScaledResolution(this.mc);
        }
        if (this.altManager) {
            final double animationTime = 10.0;
            final double waitTime = 1000.0;
            this.doneWaiting = (System.currentTimeMillis() - this.animationStart < animationTime + waitTime);
            final double animationProgress = this.doneWaiting ? (1.0 - (System.currentTimeMillis() - (this.animationStart + animationTime + waitTime)) / animationTime) : Math.min(1.0, (System.currentTimeMillis() - this.animationStart) / animationTime);
            final ScaledResolution sr = new ScaledResolution(this.mc);
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 140, animationProgress + 4.0, 111.5, 130.0, 3.0, new Color(0, 0, 0, 100));
            Aqua.INSTANCE.comfortaa4.drawString("AltLogin", (float)(sr.getScaledWidth() - 105), (float)(animationProgress + 10.0), -1);
            Aqua.INSTANCE.comfortaa4.drawCenteredString("Name : §4" + this.mc.getSession().getUsername(), (float)(sr.getScaledWidth() - 85), (float)(animationProgress + 25.0), Color.white.getRGB());
            if (this.mouseOver(mouseX, mouseY, sr.getScaledWidth() - 121, 45, 75, 15)) {
                if (Mouse.isButtonDown(0)) {
                    final StringBuilder randomName = new StringBuilder();
                    final String alphabet = "1234567891012121314151638926704982";
                    for (int random = ThreadLocalRandom.current().nextInt(2, 5), l = 0; l < random; ++l) {
                        randomName.append(alphabet.charAt(ThreadLocalRandom.current().nextInt(1, alphabet.length())));
                    }
                    if (Mouse.isButtonDown(1)) {
                        this.login(GuiScreen.getClipboardString(), "a");
                    }
                    else {
                        this.login("Aqua" + (Object)randomName + "User", "a");
                    }
                }
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 45.0, 75.0, 15.0, 3.0, new Color(156, 1, 120, 118));
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 245, animationProgress + 45.0, 100.0, 30.0, 3.0, new Color(0, 0, 0, 60));
                Aqua.INSTANCE.comfortaa4.drawString("Hold Right Mouse Click", (float)(sr.getScaledWidth() - 243), (float)(animationProgress + 48.0), -1);
                Aqua.INSTANCE.comfortaa4.drawString("for Clipboard", (float)(sr.getScaledWidth() - 223), (float)(animationProgress + 63.0), -1);
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 45.0, 75.0, 15.0, 3.0, new Color(0, 0, 0, 60));
            }
            if (this.mouseOver(mouseX, mouseY, sr.getScaledWidth() - 121, 65, 75, 15)) {
                if (Mouse.isButtonDown(0)) {
                    MicrosoftAuthentication.getInstance().loginWithPopUpWindow();
                }
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 65.0, 75.0, 15.0, 3.0, new Color(156, 1, 120, 118));
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 65.0, 75.0, 15.0, 3.0, new Color(0, 0, 0, 60));
            }
            if (this.mouseOver(mouseX, mouseY, sr.getScaledWidth() - 121, 85, 75, 15)) {
                if (Mouse.isButtonDown(0)) {
                    final String token = GuiScreen.getClipboardString();
                    if (token.equals("")) {
                        try {
                            final Class<?> oclass = Class.forName("java.awt.Desktop");
                            final Object object = oclass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                            oclass.getMethod("browse", URI.class).invoke(object, new URI("https://easymc.io/"));
                        }
                        catch (Throwable t) {}
                    }
                    Api.redeem(token, new Callback<Object>() {
                        @Override
                        public void done(final Object o) {
                            if (o instanceof String) {
                                return;
                            }
                            if (GuiMainMenu.altLoader.savedSession == null) {
                                GuiMainMenu.altLoader.savedSession = GuiMainMenu.this.mc.getSession();
                            }
                            final RedeemResponse response = GuiMainMenu.altLoader.easyMCSession = (RedeemResponse)o;
                            GuiMainMenu.altLoader.setEasyMCSession(response);
                        }
                    });
                }
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 85.0, 75.0, 15.0, 3.0, new Color(156, 1, 120, 118));
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 85.0, 75.0, 15.0, 3.0, new Color(0, 0, 0, 60));
            }
            if (this.mouseOver(mouseX, mouseY, sr.getScaledWidth() - 121, 105, 75, 15)) {
                if (Mouse.isButtonDown(0)) {
                    final String clipboard = GuiScreen.getClipboardString();
                    if (!clipboard.contains("@alt")) {
                        if (clipboard.contains("api-")) {
                            this.apiKey = clipboard;
                        }
                        if (this.apiKey != null) {
                            final BasicDataRetriever basicDataRetriever = TheAltening.newBasicRetriever(this.apiKey);
                            final AsynchronousDataRetriever asynchronousDataRetriever = basicDataRetriever.toAsync();
                            try {
                                this.loginAltening(asynchronousDataRetriever.getAccount().getToken(), "test");
                            }
                            catch (Exception ex) {}
                            return;
                        }
                    }
                    this.loginAltening(GuiScreen.getClipboardString(), "test");
                }
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 105.0, 75.0, 15.0, 3.0, new Color(156, 1, 120, 118));
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() - 121, animationProgress + 105.0, 75.0, 15.0, 3.0, new Color(0, 0, 0, 60));
            }
            Aqua.INSTANCE.comfortaa4.drawCenteredString("Cracked", (float)(sr.getScaledWidth() - 85), (float)(animationProgress + 48.0), -1);
            Aqua.INSTANCE.comfortaa4.drawCenteredString("Microsoft", (float)(sr.getScaledWidth() - 85), (float)(animationProgress + 68.0), -1);
            Aqua.INSTANCE.comfortaa4.drawCenteredString("EasyMC", (float)(sr.getScaledWidth() - 85), (float)(animationProgress + 88.0), -1);
            Aqua.INSTANCE.comfortaa4.drawCenteredString("TheAltening", (float)(sr.getScaledWidth() - 85), (float)(animationProgress + 108.0), -1);
        }
        if (this.mouseOver(mouseX, mouseY, 0, 5, 140, 110)) {
            RenderUtil.drawRoundedRect2Alpha(-1.0, 5.0, 140.0, 110.0, 3.0, new Color(0, 0, 0, 100));
            Aqua.INSTANCE.comfortaa4.drawString("Look on The DC!", 1.0f, 30.0f, -1);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(-1.0, 5.0, 140.0, 20.0, 3.0, new Color(0, 0, 0, 100));
        }
        RenderUtil.drawRoundedRect2Alpha(0.0, 23.0, 138.5, 2.0, 0.0, new Color(255, 255, 255, 255));
        Aqua.INSTANCE.comfortaa4.drawString(Aqua.name + " b" + Aqua.build, 45.0f, 10.0f, -1);
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(GuiMainMenu.width / 2 + 90), 70.0f, 0.0f);
        GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
        final float f = 1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        GlStateManager.popMatrix();
        String s = "" + Aqua.name + " : " + Aqua.INSTANCE.ircClient.getRank().name() + "";
        if (this.mc.isDemo()) {
            s += " Demo";
        }
        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            final Object object2 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            final List<String> list = Lists.reverse((List<String>)Reflector.call(object2, Reflector.FMLCommonHandler_getBrandings, true));
            for (int l2 = 0; l2 < list.size(); ++l2) {
                final String s2 = list.get(l2);
                if (!Strings.isNullOrEmpty(s2)) {
                    final FontRenderer fontRendererObj = this.fontRendererObj;
                    final String text = s2;
                    final int x = 2;
                    final int height = GuiMainMenu.height;
                    final int n = 10;
                    final int n2 = l2;
                    final FontRenderer fontRendererObj2 = this.fontRendererObj;
                    this.drawString(fontRendererObj, text, x, height - (n + n2 * (FontRenderer.FONT_HEIGHT + 1)), 16777215);
                }
            }
            if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_renderMainMenu, this, this.fontRendererObj, GuiMainMenu.width, GuiMainMenu.height);
            }
        }
        else {
            Aqua.INSTANCE.comfortaa3.drawString(s, 5.0f, (float)(GuiMainMenu.height - 12), -1);
        }
        final String s3 = "Client by LCA_MODZ";
        Aqua.INSTANCE.comfortaa3.drawString("Client by LCA_MODZ", (float)(GuiMainMenu.width - this.fontRendererObj.getStringWidth("Client by LCA_MODZ") - 13), (float)(GuiMainMenu.height - 13), -1);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            Gui.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (GuiMainMenu.width - this.field_92024_r) / 2, this.buttonList.get(0).yPosition - 12, -1);
        }
        if (!this.onStart || Mouse.isButtonDown(0)) {}
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.func_183501_a()) {
            this.field_183503_M.drawScreen(mouseX, mouseY, partialTicks);
        }
        if (this.modUpdateNotification != null) {
            this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                final GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
        if (this.func_183501_a()) {
            this.field_183503_M.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void login(final String Email, final String password) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        GuiMainMenu.alteningAuthentication.updateService(AlteningServiceType.MOJANG);
        try {
            Minecraft.getMinecraft().session = Login.logIn(Email, password);
        }
        catch (Exception e) {
            final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(Email);
            authentication.setPassword(password);
            try {
                authentication.logIn();
                Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
            }
            catch (Exception e2) {
                Minecraft.getMinecraft().session = new Session(Email, "", "", "mojang");
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        if (this.field_183503_M != null) {
            this.field_183503_M.onGuiClosed();
        }
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    public void loginAltening(final String Email, final String password) {
        Aqua.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        GuiMainMenu.alteningAuthentication.updateService(AlteningServiceType.THEALTENING);
        try {
            Minecraft.getMinecraft().session = Login.logIn(Email, password);
        }
        catch (Exception e) {
            final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(Email);
            authentication.setPassword(password);
            try {
                authentication.logIn();
                Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
            }
            catch (Exception e2) {
                Minecraft.getMinecraft().session = new Session(Email, "", "", "mojang");
            }
        }
    }
    
    static {
        field_175373_f = new AtomicInteger(0);
        logger = LogManager.getLogger();
        RANDOM = new Random();
        altLoader = new AltLoader();
        GuiMainMenu.font3 = ClientFont.font(40, "Comfortaa-Regular", true);
        GuiMainMenu.alteningAuthentication = TheAlteningAuthentication.mojang();
        splashTexts = new ResourceLocation("texts/splashes.txt");
        minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
        titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
        field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }
}
