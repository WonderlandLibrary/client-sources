package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import de.tired.api.extension.Extension;
import de.tired.api.guis.altmanager.AltManager;
import de.tired.api.guis.creditsandupdates.GUI;
import de.tired.api.particle.ParticleRenderer;
import de.tired.api.performanceMode.PerformanceGui;
import de.tired.api.performanceMode.UsingType;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.misc.FileUtil;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.render.Translate;
import de.tired.interfaces.FHook;
import de.tired.shaderloader.ShaderRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GLContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private int blurRadius = 0;
    private float animation = 0;

    private float updateCounter;
    private Translate translate;
    private ParticleRenderer particleRenderer;
    private boolean extended = true;
    private int scrollAmount = 4;
    /**
     * The splash message.
     */

    private ArrayList<String> changeLog = new ArrayList<>();
    private static ArrayList<String> autismsus = new ArrayList<>();
    private String splashText;
    private GuiButton buttonResetDemo;

    private int lastY;

    /**
     * Timer used to rotate the panorama, increases every tick.
     */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;
    public static ArrayList<String> sorted = new ArrayList<>();
    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;

    private static STATE currentTab;

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;

    private GuiButton singlePlayer;
    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    /**
     * An array of all the paths to the panorama pictures.
     */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int yAnimation = 0;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private final PerformanceGui performanceGui = new PerformanceGui();

    /**
     * Minecraft Realms button.
     */
    private GuiButton realmsButton;
    private boolean L;
    private GuiScreen M;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;

    public GuiMainMenu() {
        changeLog.add("Test");
        changeLog.add("autism");
        autismsus.addAll(changeLog);
        this.translate = new Translate(0, 0);
        this.openGLWarning2 = field_96138_a;
        this.L = false;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = (String) list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var12) {
            ;
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException var11) {
                    ;
                }
            }
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    private boolean a() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.enumFloat) && this.M != null;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.panoramaTimer;

        if (this.a()) {
            this.M.updateScreen();
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {

        if (PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {

        }

        if (PerformanceGui.isState) {
            FileUtil.FILE_UTIL.savePerformanceMode(PerformanceGui.usingType);
        }
        blurRadius = 0;
        this.translate = new Translate(0, 0);
        particleRenderer = new ParticleRenderer();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }
        int defaultHeight = this.height / 4 + 48;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, defaultHeight + 94, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, defaultHeight + 94, 98, 20, I18n.format("menu.quit", new Object[0])));


        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            //    this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - 24;
            // //  this.field_92020_v = this.field_92022_t + k;
            //   this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.setConnectedToRealms(false);

        if (this.a()) {
            this.M.a(this.width, this.height);
            this.M.initGui();
        }
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        int defaultHeight = this.height / 4 + 48;
        int yAxis = -20;
        int xAxis = 70;
        int xAxisFick = 40;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, defaultHeight + 24, I18n.format("menu.singleplayer", new Object[0])));
        for (STATE state : STATE.values()) {
            if (state == STATE.SINGLEPLAYER) {

                // final boolean isOver = isOver(30 + xAxisFick, 6, FHook.fontRenderer.getStringWidth(state.auxySchwul) + 4, 15, mouseX, mouseY);
                //  FHook.fontRenderer.drawCenteredString(state.auxySchwul, 30 + xAxis, 13, isOver ? Integer.MIN_VALUE : -1);
                xAxis += 130;
                xAxisFick += 130;
                yAxis += 150;


                int wheel = Mouse.getDWheel();


                if (wheel < 0) {
                    if (yAxis + height > 12) scrollAmount -= 16;
                } else if (wheel > 0) {
                    scrollAmount += 34;
                    if (scrollAmount > 0)
                        scrollAmount = 0;
                }

                singlePlayer = new GuiButton(1, 2, 2, "test");
                // this.buttonList.add(singlePlayer = new GuiButton(1, this.width / 2 - 100, yAxis + scrollAmount, I18n.format("menu.singleplayer", new Object[0])));
            }
        }

        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, defaultHeight + 45, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(1222, 10, 10, 200, 20, "UpdatesAndCredits"));
        if (Reflector.GuiModList_Constructor.exists()) {
            this.buttonList.add(this.realmsButton = new GuiButton(14, this.width / 2 - 100, defaultHeight + 54, 98, 20, I18n.format("Account Login", new Object[0]).replace("Minecraft", "").trim()));
            this.buttonList.add(this.modButton = new GuiButton(6, 20, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods", new Object[0])));
        } else {
            this.buttonList.add(this.realmsButton = new GuiButton(14, this.width / 2 - 100, defaultHeight + 66, I18n.format("Account Login", new Object[0])));
        }
        this.buttonList.add(new GuiButton(20, 10, 40, "Change Performance Mode"));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 20) {
            mc.displayGuiScreen(new PerformanceGui());
        }

        if (button.id == 1222) {
            this.mc.displayGuiScreen(new GUI());
        }

        if (button.id == 14) {
            mc.displayGuiScreen(new AltManager());
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
            this.mc.displayGuiScreen((GuiScreen) Reflector.newInstance(Reflector.GuiModList_Constructor, new Object[]{this}));
        }

        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null) {
                GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void f() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(this.openGLWarningLink)});
                } catch (Throwable throwable) {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ShaderRenderer.renderBG();

        final double widthA = 190;

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlineRect((float) (width / 2f - widthA), height / 4f, (float) (width / 2f + widthA), height / 4f + 225, Integer.MIN_VALUE);
        ShaderRenderer.startBlur();

        //Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlineRect((float) (width / 2f - widthA), height / 4f, (float) (width / 2f + widthA), height / 4f + 225, Integer.MIN_VALUE);
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect((float) (width / 2f - widthA), height / 4f, (float) (width / 2f + widthA), height / 4f + 225, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();

        FontManager.SFPROBig.drawString("Welcome to", height / 4f, (float) (width / 2f + widthA), -1);

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton) this.buttonList.get(0)).yPosition - 12, -1);
        }
        GlStateManager.pushMatrix();

        GlStateManager.popMatrix();

        FHook.fontRenderer3.drawString("Copyright Mojang AB and Tired-Client.de".replace("Â©", ""), width / 2 - 70, 340, -1);


        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.a()) {
            this.M.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (this.modUpdateNotification != null) {
            this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    private void renderRect(int x, int y) {

        width = 220;
        height = 220;
      drawRect((float) x, y, (float) x + (float) width, y + (float) height, Integer.MIN_VALUE);
    }

    private void renderCard(String text, int x, int y, double width, double height, String test) {

        width = 220;
        height = 220;
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect((float) x, y, (float) x + (float) width, y + (float) height, Integer.MIN_VALUE);


        // Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect((float) x, y, (float) x + (float) width, y + (float) height, Integer.MIN_VALUE);


        FHook.fontRenderer.drawString(text, calculateMiddle(text, FHook.fontRenderer, x, (int) (width)), y + 8, -1);

        mc.getTextureManager().bindTexture(new ResourceLocation("client/mainmenu/" + test));
        int size = (int) width - 10;
        GUI.drawModalRectWithCustomSizedTexture(x + 5, y + 20, size, size + 13, size, size - 13, size, size);

    }

    private void doRender(int mouseX, int mouseY) {
        Scissoring.SCISSORING.startScissor();
        Scissoring.SCISSORING.scissorOtherWay(0, 44, 600, 540);
     //   Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderGradient(0, 44, 600, 110, Integer.MIN_VALUE, Integer.MIN_VALUE);
        int yAxis2 = -20;
        for (STATE state : STATE.values()) {
            yAxis2 += 250;


            int wheel = Mouse.getDWheel();


            if (wheel < 0) {
                if (yAxis2 + height > 12) scrollAmount -= 66;
            } else if (wheel > 0) {
                scrollAmount += 77;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }

            if (scrollAmount < -1100) {
                scrollAmount ++;
            }


            if (state == STATE.SINGLEPLAYER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                renderCard("Singleplayer", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 90 + animation, 90 + animation, "singleplayer.png");
                if (isOver) {
                   // renderRect("", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation));
                }
            }

            if (state == STATE.ALTMANAGER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                renderCard("AltManager", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 90 + animation, 90 + animation, "altmanager.png");
                if (isOver) {
               //     renderRect("", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation));
                }
            }

            if (state == STATE.CREDITS) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                renderCard("Credits", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 90 + animation, 90 + animation, "credits.png");
                if (isOver) {
                 //   renderRect("", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation));
                }
            }

            if (state == STATE.PERFORMANCE_MODE) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                renderCard("Performancemode", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 90 + animation, 90 + animation, "performance.png");
                if (isOver) {
                  //  renderRect("", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation));
                }
            }

            if (state == STATE.MULTIPLAYER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                if (isOver) {
                   // renderRect((int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation));
                }
                renderCard("Multiplayer", (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 90 + animation, 90 + animation, "multiplayer.png");
            }
        }
        Scissoring.SCISSORING.disableScissor();
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {



        int yAxis2 = -20;
        for (STATE state : STATE.values()) {
            yAxis2 += 250;


      
            if (state == STATE.SINGLEPLAYER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                if (isOver) {
                    mc.displayGuiScreen(new GuiSelectWorld(this));
                }
            }

            if (state == STATE.ALTMANAGER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                if (isOver) {
                    mc.displayGuiScreen(new AltManager());
                }
            }

            if (state == STATE.CREDITS) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation), 220, 220, mouseX, mouseY);
                if (isOver) {
                    mc.displayGuiScreen(new GUI());
                }
            }

            if (state == STATE.PERFORMANCE_MODE) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation),220, 220, mouseX, mouseY);
                if (isOver) {
                    mc.displayGuiScreen(new PerformanceGui());
                }
            }

            if (state == STATE.MULTIPLAYER) {
                final boolean isOver = isOver( (int) (-100 + width / 2 + animation), (int) (30 + yAxis2 + scrollAmount + animation),220, 220, mouseX, mouseY);
                if (isOver) {
                    mc.displayGuiScreen(new GuiMultiplayer(this));
                }

            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        if (this.a()) {
            this.M.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        if (this.M != null) {
            this.M.onGuiClosed();
        }
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }


    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    enum STATE {
        SINGLEPLAYER("SinglePlayer"), MULTIPLAYER("MultiPlayer"), ALTMANAGER("AltManager"), CREDITS("Credits"), PERFORMANCE_MODE("PerformanceMode");

        final String auxySchwul;

        STATE(String caseFix) {
            this.auxySchwul = caseFix;
        }

        static
        public final STATE[] values = values();

        public STATE prev() {
            return values[(ordinal() - 1 + values.length) % values.length];
        }

        public STATE next() {
            return values[(ordinal() + 1) % values.length];
        }

    }


}
