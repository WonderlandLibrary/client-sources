/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import dev.intave.viamcp.ViaMCP;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.SoundEvents;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import optifine.CustomPanorama;
import optifine.CustomPanoramaProperties;
import optifine.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.ui.login.GuiAltLogin;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.MusicHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiMainMenu
extends GuiScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private final float updateCounter;
    private String splashText;
    private GuiButton buttonResetDemo;
    private float panoramaTimer;
    private DynamicTexture viewportTexture;
    private final Object threadLock;
    public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    private int openGLWarning2Width;
    private int openGLWarning1Width;
    private int openGLWarningX1;
    private int openGLWarningY1;
    private int openGLWarningX2;
    private int openGLWarningY2;
    private final String openGLWarning1;
    private final String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
    private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    private ResourceLocation backgroundTexture;
    private GuiButton realmsButton;
    private boolean hasCheckedForRealmsNotification;
    private GuiScreen realmsNotification;
    private int field_193978_M;
    private int field_193979_N;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;
    boolean clicked;
    static AnimationUtils st = new AnimationUtils(255.0f, 255.0f, 0.012f);
    public static AnimationUtils quit = new AnimationUtils(0.0f, 0.0f, 0.007f);
    public static AnimationUtils quit2 = new AnimationUtils(0.0f, 0.0f, 0.0275f);
    static AnimationUtils inter = new AnimationUtils(1.0f, 1.0f, 0.015f);
    static AnimationUtils alphaInter = new AnimationUtils(0.0f, 0.0f, 0.045f);
    static AnimationUtils xNotifyAnim = new AnimationUtils(0.0f, 0.0f, 0.08f);
    boolean initClickGuiGuiimages;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GuiMainMenu() {
        IResource iresource;
        block5: {
            this.threadLock = new Object();
            this.clicked = true;
            this.openGLWarning2 = MORE_INFO_TEXT;
            this.splashText = "missingno";
            iresource = null;
            try {
                String s;
                ArrayList<String> list = Lists.newArrayList();
                iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
                while ((s = bufferedreader.readLine()) != null) {
                    if ((s = s.trim()).isEmpty()) continue;
                    list.add(s);
                }
                if (list.isEmpty()) break block5;
                do {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
                } while (this.splashText.hashCode() == 125780783);
            } catch (IOException iOException) {
                IOUtils.closeQuietly(iresource);
            } catch (Throwable throwable) {
                IOUtils.closeQuietly(iresource);
                throw throwable;
            }
        }
        IOUtils.closeQuietly((Closeable)iresource);
        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";
    }

    private boolean areRealmsNotificationsEnabled() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.realmsNotification != null;
    }

    @Override
    public void updateScreen() {
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotification.updateScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        if (!Panic.stop) {
            ClientRP.getInstance().getDiscordRP().update("\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e \u0438\u0433\u0440\u044b", "\u0411\u0435\u0437\u0434\u0435\u0439\u0441\u0442\u0432\u0443\u0435\u0442");
        }
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
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
        int var3 = height / 2 - 78;
        if (this.mc.isDemo()) {
            this.addDemoButtons(var3, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(var3, 24);
        }
        if (!Panic.stop) {
            return;
        }
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 20, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, width / 2 + 2, var3 + 72 + 20, 98, 20, I18n.format("menu.quit", new Object[0])));
        if (Panic.stop) {
            this.buttonList.add(new GuiButtonLanguage(5, width / 2 - 124, var3 + 72 + 12));
        }
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        if (!Panic.stop) {
            return;
        }
        this.buttonList.add(new GuiButton(1, width / 2 - 100, p_73969_1_ + 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, p_73969_1_ + p_73969_2_ + 20, I18n.format("menu.multiplayer", new Object[0])));
        if (Reflector.GuiModList_Constructor.exists()) {
            this.realmsButton = this.addButton(new GuiButton(14, width / 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("menu.online", new Object[0]).replace("Minecraft", "").trim()));
            this.modButton = new GuiButton(6, width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods", new Object[0]));
            this.buttonList.add(this.modButton);
        } else {
            this.realmsButton = this.addButton(new GuiButton(14, width / 2 - 100, p_73969_1_ + p_73969_2_ * 2 + 20, I18n.format(Panic.stop ? "Minecraft Realms" : "\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b", new Object[0])));
        }
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = this.addButton(new GuiButton(12, width / 2 - 100, p_73972_1_ + p_73972_2_, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        ISaveFormat isaveformat;
        WorldInfo worldinfo;
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 14 && !Panic.stop) {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
        if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
            this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, this));
        }
        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
        }
        if (button.id == 12 && (worldinfo = (isaveformat = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion", new Object[0]), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 12));
        }
        if (button.id == 69) {
            // empty if block
        }
    }

    private void switchToRealms() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 12) {
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable1) {
                    LOGGER.error("Couldn't open link", throwable1);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
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
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int i = 8;
        int j = 64;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            j = custompanoramaproperties.getBlur1();
        }
        for (int k = 0; k < j; ++k) {
            GlStateManager.pushMatrix();
            float f = ((float)(k % 8) / 8.0f - 0.5f) / 64.0f;
            float f1 = ((float)(k / 8) / 8.0f - 0.5f) / 64.0f;
            float f2 = 0.0f;
            GlStateManager.translate(f, f1, 0.0f);
            GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-this.panoramaTimer * 0.1f, 0.0f, 1.0f, 0.0f);
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
                ResourceLocation[] aresourcelocation = TITLE_PANORAMA_PATHS;
                if (custompanoramaproperties != null) {
                    aresourcelocation = custompanoramaproperties.getPanoramaLocations();
                }
                this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int i1 = 255 / (k + 1);
                float f3 = 0.0f;
                bufferbuilder.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, i1).endVertex();
                bufferbuilder.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, i1).endVertex();
                bufferbuilder.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, i1).endVertex();
                bufferbuilder.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, i1).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        bufferbuilder.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox() {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;
        int j = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            j = custompanoramaproperties.getBlur2();
        }
        for (int k = 0; k < j; ++k) {
            float f = 1.0f / (float)(k + 1);
            int l = width;
            int i1 = height;
            float f1 = (float)(k - 1) / 256.0f;
            bufferbuilder.pos(l, i1, this.zLevel).tex(0.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(l, 0.0, this.zLevel).tex(1.0f + f1, 1.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(0.0, 0.0, this.zLevel).tex(1.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
            bufferbuilder.pos(0.0, i1, this.zLevel).tex(0.0f + f1, 0.0).color(1.0f, 1.0f, 1.0f, f).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(mouseX, mouseY, partialTicks);
        this.rotateAndBlurSkybox();
        int i = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
        if (custompanoramaproperties != null) {
            i = custompanoramaproperties.getBlur3();
        }
        for (int j = 0; j < i; ++j) {
            this.rotateAndBlurSkybox();
            this.rotateAndBlurSkybox();
        }
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f2 = 120.0f / (float)(width > height ? width : height);
        float f = (float)height * f2 / 256.0f;
        float f1 = (float)width * f2 / 256.0f;
        int k = width;
        int l = height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0, l, this.zLevel).tex(0.5f - f, 0.5f + f1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(k, l, this.zLevel).tex(0.5f - f, 0.5f - f1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(k, 0.0, this.zLevel).tex(0.5f + f, 0.5f - f1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(0.0, 0.0, this.zLevel).tex(0.5f + f, 0.5f + f1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    List<String> buttons() {
        ArrayList<String> buttons = new ArrayList<String>();
        buttons.add("Quit");
        buttons.add("Single");
        buttons.add("Multy");
        buttons.add("Altmgr");
        buttons.add("Settings");
        return buttons;
    }

    void clickI(int i) {
        if (i == 0) {
            if (Panic.stop) {
                this.mc.shutdown();
            } else {
                quit.setAnim(0.0f);
                quit2.setAnim(0.0f);
                GuiMainMenu.quit2.to = 1.0f;
                MusicHelper.playSound("main_quit.wav");
            }
        }
        if (i == 1) {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }
        if (i == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (i == 3) {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if (i == 4) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (i != 0 || Panic.stop) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float f;
        if (!this.initClickGuiGuiimages && ClickGui.instance != null) {
            Arrays.asList(ClickGui.instance.getSetting((String)"Image", (Settings.Category)Settings.Category.String_Massive).modes).stream().forEach(another -> this.mc.getTextureManager().bindTexture(new ResourceLocation("vegaline/modules/clickgui/images/" + another.toLowerCase() + ".png")));
            this.initClickGuiGuiimages = true;
        }
        if (!Panic.stop && (double)quit.getAnim() > 0.97) {
            ClientRP.getInstance().getDiscordRP().shutdown();
            this.mc.shutdown();
        }
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (st.getAnim() > 0.0f) {
            GuiMainMenu.st.to = -1.0f;
        }
        if (Panic.stop) {
            this.panoramaTimer += partialTicks;
            GlStateManager.disableAlpha();
            this.renderSkybox(mouseX, mouseY, partialTicks);
            GlStateManager.enableAlpha();
            int i = 274;
            int j = width / 2 - 137;
            int k = 30;
            int l = -2130706433;
            int i1 = 0xFFFFFF;
            int j1 = 0;
            int k1 = Integer.MIN_VALUE;
            CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
            if (custompanoramaproperties != null) {
                l = custompanoramaproperties.getOverlay1Top();
                i1 = custompanoramaproperties.getOverlay1Bottom();
                j1 = custompanoramaproperties.getOverlay2Top();
                k1 = custompanoramaproperties.getOverlay2Bottom();
            }
            if (l != 0 || i1 != 0) {
                this.drawGradientRect(0, 0, width, height, l, i1);
            }
            if (j1 != 0 || k1 != 0) {
                this.drawGradientRect(0, 0, width, height, j1, k1);
            }
            this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if ((double)this.updateCounter < 1.0E-4) {
                this.drawTexturedModalRect(j, 30, 0, 0, 99, 44);
                this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
                this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
                this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            } else {
                this.drawTexturedModalRect(j, 30, 0, 0, 155, 44);
                this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
            }
            this.mc.getTextureManager().bindTexture(field_194400_H);
            GuiMainMenu.drawModalRectWithCustomSizedTexture(j + 88, 67, 0.0f, 0.0f, 98, 14, 128.0f, 16.0f);
            if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                this.splashText = Reflector.callString(Reflector.ForgeHooksClient_renderMainMenu, this, this.fontRendererObj, width, height, this.splashText);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(width / 2 + 90, 70.0f, 0.0f);
            GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
            float f2 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * ((float)Math.PI * 2)) * 0.1f);
            f2 = f2 * 100.0f / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
            GlStateManager.scale(f2, f2, f2);
            this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
            GlStateManager.popMatrix();
            Object s = ClientBrandRetriever.getFalseClientPrefix().trim();
            s = this.mc.isDemo() ? (String)s + " Demo" : (String)s + (String)("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
            if (Reflector.FMLCommonHandler_getBrandings.exists()) {
                Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
                List list = Lists.reverse((List)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, true));
                for (int l1 = 0; l1 < list.size(); ++l1) {
                    String s1 = (String)list.get(l1);
                    if (Strings.isNullOrEmpty(s1)) continue;
                    this.drawString(this.fontRendererObj, s1, 2, height - (10 + l1 * (this.fontRendererObj.FONT_HEIGHT + 1)), 0xFFFFFF);
                }
            } else {
                this.drawString(this.fontRendererObj, (String)s, 2, height - 40, -1);
                this.drawString(this.fontRendererObj, "MCP 9.40", 2, height - 30, -1);
                this.drawString(this.fontRendererObj, "Powered by Forge 14.21.1.2443", 2, height - 20, -1);
                this.drawString(this.fontRendererObj, "4 mods loaded, 4 mods active", 2, height - 10, -1);
            }
            this.drawString(this.fontRendererObj, "Copyright Mojang AB. Do not distribute!", width - this.mc.fontRendererObj.getStringWidth("Copyright Mojang AB. Do not distribute!") - 2, height - 10, -1);
            if (mouseX > this.field_193979_N && mouseX < this.field_193979_N + this.field_193978_M && mouseY > height - 10 && mouseY < height && Mouse.isInsideWindow()) {
                GuiMainMenu.drawRect(this.field_193979_N, (double)(height - 1), (double)(this.field_193979_N + this.field_193978_M), (double)height, -1);
            }
            if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
                GuiMainMenu.drawRect(this.openGLWarningX1 - 2, (double)(this.openGLWarningY1 - 2), (double)(this.openGLWarningX2 + 2), (double)(this.openGLWarningY2 - 1), 0x55200000);
                this.drawString(this.fontRendererObj, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
                this.drawString(this.fontRendererObj, this.openGLWarning2, (width - this.openGLWarning2Width) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
            }
            super.drawScreen(mouseX, mouseY, partialTicks);
            if (this.areRealmsNotificationsEnabled()) {
                this.realmsNotification.drawScreen(mouseX, mouseY, partialTicks);
            }
            if (this.modUpdateNotification != null) {
                this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
            }
        } else {
            int step;
            if (!Panic.stop) {
                RenderUtils.drawScreenShaderBackground(sr, mouseX, mouseY);
            }
            float midUPX = (float)sr.getScaledWidth() / 2.0f;
            float midUPY = (float)sr.getScaledHeight() / 2.0f - 64.0f;
            float texW = 256.0f;
            float texH = 64.0f;
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE_MINUS_CONSTANT_ALPHA, GlStateManager.DestFactor.ZERO);
            RenderUtils.drawImageWithAlpha(new ResourceLocation("vegaline/ui/mainmenu/logo.png"), midUPX - texW / 2.0f, midUPY - texH / 2.0f, texW, texH, -1, 255);
            RenderUtils.drawImageWithAlpha(new ResourceLocation("vegaline/ui/mainmenu/icons/urlicons/nightsquadlink.png"), sr.getScaledWidth() - 16, sr.getScaledHeight() - 16, 16.0f, 16.0f, -1, 255);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.DestFactor.ZERO);
            int textC = ColorUtils.swapAlpha(-1, 65.0f);
            Fonts.mntsb_12.drawStringWithShadow("fps: " + Minecraft.getDebugFPS(), 3.0, sr.getScaledHeight() - 24, textC);
            Fonts.mntsb_12.drawStringWithShadow("build: " + Client.version.replace("#00", "0"), 3.0, sr.getScaledHeight() - 16, textC);
            Fonts.mntsb_12.drawStringWithShadow("author: @vega33", 3.0, sr.getScaledHeight() - 8, textC);
            int eki = sr.getScaledWidth() <= 600 ? 7 : 0;
            int size = sr.getScaledWidth() > 600 ? 64 : 32;
            int round = sr.getScaledWidth() > 600 ? 30 : 15;
            float smooth = sr.getScaledWidth() > 600 ? 3.0f : 1.5f;
            int xStep = step = sr.getScaledWidth() > 600 ? 76 : 36;
            int alphaIcon = 200;
            int width = 0;
            int i = 0;
            for (String button : this.buttons()) {
                width += step;
            }
            int y = sr.getScaledHeight() / 2;
            int x = sr.getScaledWidth() / 2 - width / 2;
            try {
                for (String button : this.buttons()) {
                    i = (mouseX - x) / step;
                }
            } catch (Exception exception) {
                // empty catch block
            }
            x -= step;
            int stepDelta = 0;
            xStep = 0;
            int tesed = 2;
            int clown = ColorUtils.swapAlpha(-1, 35.0f);
            int clown2 = ColorUtils.getColor(255, 255, 255, 40);
            int xNotify = -1000;
            int yNotify = -1000;
            String buttonOnMouse = null;
            boolean click = false;
            for (String button : this.buttons()) {
                int ext;
                if (RenderUtils.isHovered(mouseX, mouseY, x + step, y, x + width + 4 - x, y + step - y) && i >= 0 && i <= 4 && this.buttons().get(i) != null) {
                    buttonOnMouse = this.buttons().get(i);
                    if (i >= 0 && i <= 4) {
                        xNotify = x + size / 2 + step * i + step + 2;
                        yNotify = sr.getScaledHeight() / 2 - step / 3 + 20 - (int)((float)eki * 1.5f);
                    }
                }
                ++stepDelta;
                for (int st = 0; st < stepDelta; ++st) {
                    xStep += step / stepDelta;
                }
                String res = button.toLowerCase();
                ResourceLocation icon = new ResourceLocation("vegaline/ui/mainmenu/icons/buttonicons/" + res + ".png");
                int n = ext = buttonOnMouse != null && buttonOnMouse == button ? -2 : 0;
                if (ext != 0) {
                    click = true;
                }
                GL11.glTranslated(0.0, ext, 0.0);
                RenderUtils.fixShadows();
                GlStateManager.disableDepth();
                RenderUtils.drawRoundedFullGradientShadow(x + xStep + tesed, y + tesed, x + xStep + size + tesed, y + size + tesed, round, (float)round / 8.0f, clown, clown, clown2, clown2, true);
                RenderUtils.drawRoundedFullGradientInsideShadow(x + xStep + tesed, y + tesed, x + xStep + size + tesed, y + size + tesed, (float)round * 2.0f, clown, clown, clown2, clown2, true);
                GlStateManager.enableDepth();
                RenderUtils.resetBlender();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderUtils.drawImageWithAlpha(icon, x + xStep + tesed, y + tesed, size, size, -1, alphaIcon * (ext != 0 ? -((int)((double)ext / 1.5)) : 1));
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GL11.glTranslated(0.0, -ext, 0.0);
            }
            RenderUtils.resetBlender();
            ArrayList<Vec2f> vecs = new ArrayList<Vec2f>();
            if (xNotify != -1000 && yNotify != -1000) {
                if (xNotifyAnim.getAnim() == 0.0f) {
                    xNotifyAnim.setAnim(xNotify);
                }
                GuiMainMenu.xNotifyAnim.to = xNotify;
            } else {
                GuiMainMenu.xNotifyAnim.to = mouseX;
            }
            vecs.add(new Vec2f(xNotifyAnim.getAnim(), yNotify));
            vecs.add(new Vec2f(xNotifyAnim.getAnim() - 5.0f, (float)yNotify - 7.5f));
            vecs.add(new Vec2f(xNotifyAnim.getAnim() + 5.0f, (float)yNotify - 7.5f));
            int notifyCol = ColorUtils.swapAlpha(-1, 65.0f);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderUtils.drawSome(vecs, notifyCol);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            vecs.clear();
            if (yNotify > 0) {
                RenderUtils.fullRoundFG(xNotifyAnim.getAnim() - (float)(Fonts.noise_24.getStringWidth(buttonOnMouse) / 2) - 4.0f, yNotify - 26, GuiMainMenu.xNotifyAnim.anim + (float)(Fonts.noise_24.getStringWidth(buttonOnMouse) / 2) + 4.0f, (float)yNotify - 7.5f, 10.0f, notifyCol, notifyCol, notifyCol, notifyCol, true);
                Fonts.noise_24.drawString(buttonOnMouse, GuiMainMenu.xNotifyAnim.anim - (float)(Fonts.noise_24.getStringWidth(buttonOnMouse) / 2), yNotify - 22, -1);
            }
            if (!Panic.stop) {
                ViaMCP.INSTANCE.getViaPanel().drawPanel(1.0f - GuiMainMenu.inter.anim, mouseX, mouseY);
            }
            if (GuiMainMenu.quit.to != 1.0f) {
                super.drawScreen(mouseX, mouseY, partialTicks);
            }
            if (Mouse.isButtonDown(0)) {
                if (!this.clicked) {
                    return;
                }
                if (click) {
                    this.clickI(i);
                }
                this.clicked = false;
            } else {
                this.clicked = true;
            }
        }
        if (quit2.getAnim() > 0.0f) {
            float quitKachel = (GuiMainMenu.quit2.anim > 0.5f ? 1.0f - GuiMainMenu.quit2.anim : GuiMainMenu.quit2.anim) * 2.0f;
            int solveColor = ColorUtils.swapAlpha(-1, 255.0f * quitKachel);
            float scale = (float)sr.getScaledWidth() / 1.75f * GuiMainMenu.quit2.anim;
            float x = (float)sr.getScaledWidth() / 2.0f;
            float y = (float)sr.getScaledHeight() / 2.0f;
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - scale, y - scale, x + scale, y + scale, scale, scale, solveColor, solveColor, solveColor, solveColor, true, true, true);
            if ((double)GuiMainMenu.quit2.anim > 0.97) {
                GuiMainMenu.quit.to = 1.0f;
            }
        }
        if (quit.getAnim() > 0.0f) {
            ClientRP.getInstance().getDiscordRP().update("\u0412\u044b\u0445\u043e\u0434\u0438\u0442 \u0438\u0437 \u0438\u0433\u0440\u044b", "\u041f\u043e\u043a\u0438\u0434\u0430\u0435\u0442 \u043c\u0438\u0440 \u043a\u0443\u0431\u043e\u0432");
            RenderUtils.drawAlphedRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.getColor((int)(26.0f * (1.0f - GuiMainMenu.quit.anim)), (int)(26.0f * (1.0f - GuiMainMenu.quit.anim)), (int)(34.0f * (1.0f - GuiMainMenu.quit.anim)), MathUtils.clamp(255.0f * (GuiMainMenu.quit.anim * 2.0f), 0.0f, 255.0f)));
            float quitKachel = (GuiMainMenu.quit.anim > 0.5f ? 1.0f - GuiMainMenu.quit.anim : GuiMainMenu.quit.anim) * 2.0f;
            quitKachel *= quitKachel;
            RenderUtils.resetBlender();
            RenderUtils.fixShadows();
            if (GuiMainMenu.quit.anim * 255.0f >= 1.0f) {
                float gap = ((double)GuiMainMenu.quit.anim > 0.5 ? 1.0f - GuiMainMenu.quit.anim : GuiMainMenu.quit.anim) * 2.5f;
                gap = MathUtils.clamp(gap, 0.0f, 1.0f);
                int c = ColorUtils.getOverallColorFrom(0, -1, gap);
                if ((float)ColorUtils.getAlphaFromColor(c = ColorUtils.swapAlpha(c, (float)ColorUtils.getAlphaFromColor(c) * gap)) * quitKachel >= 33.0f) {
                    Fonts.neverlose500_18.drawString("\u0414\u043e \u0441\u043a\u043e\u0440\u043e\u0439 \u0432\u0441\u0442\u0440\u0435\u0447\u0438", (float)sr.getScaledWidth() / 2.0f - (float)Fonts.neverlose500_18.getStringWidth("\u0414\u043e \u0441\u043a\u043e\u0440\u043e\u0439 \u0432\u0441\u0442\u0440\u0435\u0447\u0438") / 2.0f, (float)sr.getScaledHeight() / 2.0f + 120.0f - 120.0f * GuiMainMenu.quit.anim, c);
                }
            }
        }
        if (GuiMainMenu.alphaInter.to == 0.0f && Minecraft.getDebugFPS() >= 80) {
            GuiMainMenu.alphaInter.to = 1.0f;
            MusicHelper.playSound("main_init.wav", 0.5f);
        }
        if (GuiMainMenu.inter.to == 1.0f && (double)alphaInter.getAnim() > 0.995) {
            GuiMainMenu.inter.to = 0.0f;
        }
        if ((1.0f - GuiMainMenu.alphaInter.anim) * inter.getAnim() * 255.0f >= 1.0f) {
            int bgColor = ColorUtils.swapAlpha(0, MathUtils.clamp(255.0f * (1.0f - GuiMainMenu.alphaInter.anim) * GuiMainMenu.inter.anim, 1.0f, 255.0f));
            RenderUtils.drawAlphedRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), bgColor);
        }
        float aPC = GuiMainMenu.alphaInter.anim * GuiMainMenu.inter.anim;
        if (MathUtils.clamp(f, 0.0f, 1.0f) * 255.0f >= 1.0f) {
            int bgColor = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0, 0, 0), ColorUtils.getColor(27, 27, 33), GuiMainMenu.alphaInter.anim), MathUtils.clamp(255.0f * GuiMainMenu.inter.anim, 1.0f, 255.0f));
            RenderUtils.drawAlphedRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), bgColor);
            int textColor = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(0, 0, 0), -1, GuiMainMenu.alphaInter.anim), 255.0f * GuiMainMenu.inter.anim);
            if (ColorUtils.getAlphaFromColor(textColor) >= 33) {
                float textX = (float)sr.getScaledWidth() / 2.0f - (float)Fonts.neverlose500_18.getStringWidth("\u0412\u0435\u0433\u0443\u043b\u044f \u043b\u044e\u0431\u0438\u043c\u044b\u0439 :)") / 2.0f;
                float textY = (float)sr.getScaledHeight() / 2.0f - 30.0f * GuiMainMenu.inter.anim + 30.0f;
                Fonts.neverlose500_18.drawStringWithShadow("\u0412\u0435\u0433\u0443\u043b\u044f \u043b\u044e\u0431\u0438\u043c\u044b\u0439 :)", textX, textY, textColor);
            }
        }
        RenderUtils.fixShadows();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object object = this.threadLock;
        synchronized (object) {
            if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink((GuiYesNoCallback)this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
        if (this.areRealmsNotificationsEnabled()) {
            this.realmsNotification.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (!Panic.stop) {
            ViaMCP.INSTANCE.getViaPanel().mouseClick(mouseX, mouseY, mouseButton);
            ScaledResolution sr = new ScaledResolution(this.mc);
            if (mouseButton == 0 && mouseX >= sr.getScaledWidth() - 32 && mouseY >= sr.getScaledHeight() - 32) {
                Desktop.getDesktop().browse(URI.create("https://discord.gg/AWeGKFh7Dq"));
                MusicHelper.playSound("browseurl.wav");
            }
        }
        if (mouseX > this.field_193979_N && mouseX < this.field_193979_N + this.field_193978_M && mouseY > height - 10 && mouseY < height) {
            this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.realmsNotification != null) {
            this.realmsNotification.onGuiClosed();
        }
    }
}

