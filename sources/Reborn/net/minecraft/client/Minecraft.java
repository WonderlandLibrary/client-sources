package net.minecraft.client;

import org.lwjgl.*;
import me.enrythebest.reborn.cracked.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.input.*;
import me.enrythebest.reborn.cracked.util.*;
import java.text.*;
import java.util.concurrent.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public abstract class Minecraft implements Runnable, IPlayerUsage
{
    public static byte[] memoryReserve;
    private final ILogAgent field_94139_O;
    private ServerData currentServerData;
    private static Minecraft theMinecraft;
    public PlayerControllerMP playerController;
    private boolean fullscreen;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    private Timer timer;
    private PlayerUsageSnooper usageSnooper;
    public static WorldClient theWorld;
    public RenderGlobal renderGlobal;
    public static EntityClientPlayerMP thePlayer;
    public EntityLiving renderViewEntity;
    public EntityLiving pointedEntityLiving;
    public EffectRenderer effectRenderer;
    public Session session;
    public String minecraftUri;
    public Canvas mcCanvas;
    public boolean hideQuitButton;
    public volatile boolean isGamePaused;
    public RenderEngine renderEngine;
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;
    public static GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private ThreadDownloadResources downloadResourcesThread;
    private int leftClickCounter;
    private int tempDisplayWidth;
    private int tempDisplayHeight;
    private IntegratedServer theIntegratedServer;
    public GuiAchievement guiAchievement;
    public GuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public MovingObjectPosition objectMouseOver;
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager;
    public MouseHelper mouseHelper;
    public TexturePackList texturePackList;
    public File mcDataDir;
    private ISaveFormat saveLoader;
    private static int debugFPS;
    private int rightClickDelayTimer;
    private boolean refreshTexturePacksScheduled;
    public StatFileWriter statFileWriter;
    private String serverName;
    private int serverPort;
    boolean isTakingScreenshot;
    public boolean inGameHasFocus;
    long systemTime;
    private int joinPlayerCounter;
    private boolean isDemo;
    private INetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler;
    private long field_83002_am;
    private static File minecraftDir;
    public volatile boolean running;
    public String debug;
    long debugUpdateTime;
    int fpsCounter;
    long prevFrameTime;
    private String debugProfilerName;
    
    static {
        Minecraft.memoryReserve = new byte[10485760];
        Minecraft.currentScreen = null;
        Minecraft.minecraftDir = null;
    }
    
    public Minecraft(final Canvas par1Canvas, final MinecraftApplet par2MinecraftApplet, final int par3, final int par4, final boolean par5) {
        this.field_94139_O = new LogAgent("Minecraft-Client", " [CLIENT]", new File(getMinecraftDir(), "output-client.log").getAbsolutePath());
        this.fullscreen = false;
        this.hasCrashed = false;
        this.timer = new Timer(20.0f);
        this.usageSnooper = new PlayerUsageSnooper("client", this);
        this.session = null;
        this.hideQuitButton = false;
        this.isGamePaused = false;
        this.leftClickCounter = 0;
        this.skipRenderWorld = false;
        this.objectMouseOver = null;
        this.sndManager = new SoundManager();
        this.rightClickDelayTimer = 0;
        this.isTakingScreenshot = false;
        this.inGameHasFocus = false;
        this.systemTime = getSystemTime();
        this.joinPlayerCounter = 0;
        this.mcProfiler = new Profiler();
        this.field_83002_am = -1L;
        this.running = true;
        this.debug = "";
        this.debugUpdateTime = getSystemTime();
        this.fpsCounter = 0;
        this.prevFrameTime = -1L;
        this.debugProfilerName = "root";
        StatList.nopInit();
        this.tempDisplayHeight = par4;
        this.fullscreen = par5;
        this.mcApplet = par2MinecraftApplet;
        Packet3Chat.maxChatLength = 32767;
        this.startTimerHackThread();
        this.mcCanvas = par1Canvas;
        this.displayWidth = par3;
        this.displayHeight = par4;
        this.fullscreen = par5;
        Minecraft.theMinecraft = this;
        TextureManager.init();
        this.guiAchievement = new GuiAchievement(this);
    }
    
    private void startTimerHackThread() {
        final ThreadClientSleep var1 = new ThreadClientSleep(this, "Timer hack thread");
        var1.setDaemon(true);
        var1.start();
    }
    
    public void crashed(final CrashReport par1CrashReport) {
        this.hasCrashed = true;
        this.crashReporter = par1CrashReport;
    }
    
    public void displayCrashReport(final CrashReport par1CrashReport) {
        this.hasCrashed = true;
        this.displayCrashReportInternal(par1CrashReport);
    }
    
    public abstract void displayCrashReportInternal(final CrashReport p0);
    
    public void setServer(final String par1Str, final int par2) {
        this.serverName = par1Str;
        this.serverPort = par2;
    }
    
    public void startGame() throws LWJGLException {
        if (this.mcCanvas != null) {
            final Graphics var1 = this.mcCanvas.getGraphics();
            if (var1 != null) {
                var1.setColor(Color.BLACK);
                var1.fillRect(0, 0, this.displayWidth, this.displayHeight);
                var1.dispose();
            }
            Display.setParent(this.mcCanvas);
        }
        else if (this.fullscreen) {
            Display.setFullscreen(true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
        Display.setTitle("Minecraft Minecraft 1.5.2");
        this.getLogAgent().logInfo("LWJGL Version: " + Sys.getVersion());
        try {
            Display.create(new PixelFormat().withDepthBits(24));
        }
        catch (LWJGLException var2) {
            var2.printStackTrace();
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            Display.create();
        }
        OpenGlHelper.initializeTextures();
        this.mcDataDir = getMinecraftDir();
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.texturePackList = new TexturePackList(this.mcDataDir, this);
        this.renderEngine = new RenderEngine(this.texturePackList, this.gameSettings);
        this.loadScreen();
        this.fontRenderer = new FontRenderer(this.gameSettings, "/font/default.png", this.renderEngine, false);
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, "/font/alternate.png", this.renderEngine, false);
        if (this.gameSettings.language != null) {
            StringTranslate.getInstance().setLanguage(this.gameSettings.language, false);
            this.fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
            this.fontRenderer.setBidiFlag(StringTranslate.isBidirectional(this.gameSettings.language));
        }
        ColorizerGrass.setGrassBiomeColorizer(this.renderEngine.getTextureContents("/misc/grasscolor.png"));
        ColorizerFoliage.setFoliageBiomeColorizer(this.renderEngine.getTextureContents("/misc/foliagecolor.png"));
        this.entityRenderer = new EntityRenderer(this);
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        this.statFileWriter = new StatFileWriter(this.session, this.mcDataDir);
        AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
        this.loadScreen();
        Mouse.create();
        this.mouseHelper = new MouseHelper(this.mcCanvas, this.gameSettings);
        this.checkGLError("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        this.checkGLError("Startup");
        this.sndManager.loadSoundSettings(this.gameSettings);
        this.renderGlobal = new RenderGlobal(this, this.renderEngine);
        this.renderEngine.refreshTextureMaps();
        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(Minecraft.theWorld, this.renderEngine);
        try {
            (this.downloadResourcesThread = new ThreadDownloadResources(this.mcDataDir, this)).start();
        }
        catch (Exception ex2) {}
        this.checkGLError("Post startup");
        this.ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
        }
        else {
            this.displayGuiScreen(new GuiMainMenu());
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        Morbid.getInstance().onStart();
    }
    
    private void loadScreen() throws LWJGLException {
        final ScaledResolution var1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glDisable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2912);
        final Tessellator var2 = Tessellator.instance;
        this.renderEngine.bindTexture("/title/mojang.png");
        var2.startDrawingQuads();
        var2.setColorOpaque_I(16777215);
        var2.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        var2.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var2.setColorOpaque_I(16777215);
        final short var3 = 256;
        final short var4 = 256;
        this.scaledTessellator((ScaledResolution.getScaledWidth() - var3) / 2, (ScaledResolution.getScaledHeight() - var4) / 2, 0, 0, var3, var4);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        Display.swapBuffers();
    }
    
    public void scaledTessellator(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0, (par3 + 0) * var7, (par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0, (par3 + par5) * var7, (par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0, (par3 + par5) * var7, (par4 + 0) * var8);
        var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0, (par3 + 0) * var7, (par4 + 0) * var8);
        var9.draw();
    }
    
    public static File getMinecraftDir() {
        if (Minecraft.minecraftDir == null) {
            Minecraft.minecraftDir = getAppDir("minecraft");
        }
        return Minecraft.minecraftDir;
    }
    
    public static File getAppDir(final String par0Str) {
        final String var1 = System.getProperty("user.home", ".");
        File var2 = null;
        switch (EnumOSHelper.field_90049_a[getOs().ordinal()]) {
            case 1:
            case 2: {
                var2 = new File(var1, String.valueOf('.') + par0Str + '/');
                break;
            }
            case 3: {
                final String var3 = System.getenv("APPDATA");
                if (var3 != null) {
                    var2 = new File(var3, "." + par0Str + '/');
                    break;
                }
                var2 = new File(var1, String.valueOf('.') + par0Str + '/');
                break;
            }
            case 4: {
                var2 = new File(var1, "Library/Application Support/" + par0Str);
                break;
            }
            default: {
                var2 = new File(var1, String.valueOf(par0Str) + '/');
                break;
            }
        }
        if (!var2.exists() && !var2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + var2);
        }
        return var2;
    }
    
    public static EnumOS getOs() {
        final String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? EnumOS.WINDOWS : (var0.contains("mac") ? EnumOS.MACOS : (var0.contains("solaris") ? EnumOS.SOLARIS : (var0.contains("sunos") ? EnumOS.SOLARIS : (var0.contains("linux") ? EnumOS.LINUX : (var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }
    
    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }
    
    public void displayGuiScreen(GuiScreen par1GuiScreen) {
        if (Minecraft.currentScreen != null) {
            Minecraft.currentScreen.onGuiClosed();
        }
        this.statFileWriter.syncStats();
        if (par1GuiScreen == null && Minecraft.theWorld == null) {
            par1GuiScreen = new GuiMainMenu();
        }
        else if (par1GuiScreen == null && Minecraft.thePlayer.getHealth() <= 0) {
            par1GuiScreen = new GuiGameOver();
        }
        if (par1GuiScreen instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages();
        }
        if ((Minecraft.currentScreen = par1GuiScreen) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution var2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
            final int var3 = ScaledResolution.getScaledWidth();
            final int var4 = ScaledResolution.getScaledHeight();
            par1GuiScreen.setWorldAndResolution(this, var3, var4);
            this.skipRenderWorld = false;
        }
        else {
            this.setIngameFocus();
        }
    }
    
    private void checkGLError(final String par1Str) {
        final int var2 = GL11.glGetError();
        if (var2 != 0) {
            final String var3 = GLU.gluErrorString(var2);
            this.getLogAgent().logSevere("########## GL ERROR ##########");
            this.getLogAgent().logSevere("@ " + par1Str);
            this.getLogAgent().logSevere(String.valueOf(var2) + ": " + var3);
        }
    }
    
    public void shutdownMinecraftApplet() {
        try {
            this.statFileWriter.syncStats();
            try {
                if (this.downloadResourcesThread != null) {
                    this.downloadResourcesThread.closeMinecraft();
                }
            }
            catch (Exception ex) {}
            this.getLogAgent().logInfo("Stopping!");
            try {
                this.loadWorld(null);
            }
            catch (Throwable t) {}
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable t2) {}
            this.sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally {
            Display.destroy();
            if (!this.hasCrashed) {
                System.exit(0);
            }
        }
        Display.destroy();
        if (!this.hasCrashed) {
            System.exit(0);
        }
        MorbidHelper.gc();
    }
    
    @Override
    public void run() {
        this.running = true;
        Label_0109: {
            try {
                this.startGame();
                break Label_0109;
            }
            catch (Exception var11) {
                var11.printStackTrace();
                this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(new CrashReport("Failed to start game", var11)));
                return;
            }
            try {
                while (!this.hasCrashed || this.crashReporter == null) {
                    if (this.refreshTexturePacksScheduled) {
                        this.refreshTexturePacksScheduled = false;
                        this.renderEngine.refreshTextures();
                    }
                    try {
                        this.runGameLoop();
                    }
                    catch (OutOfMemoryError var15) {
                        this.freeMemory();
                        this.displayGuiScreen(new GuiMemoryErrorScreen());
                        MorbidHelper.gc();
                    }
                    if (!this.running) {
                        break Label_0109;
                    }
                }
                this.displayCrashReport(this.crashReporter);
                return;
            }
            catch (MinecraftError minecraftError) {}
            catch (ReportedException var12) {
                this.addGraphicsAndWorldToCrashReport(var12.getCrashReport());
                this.freeMemory();
                var12.printStackTrace();
                this.displayCrashReport(var12.getCrashReport());
            }
            catch (Throwable var14) {
                final CrashReport var13 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
                this.freeMemory();
                var14.printStackTrace();
                this.displayCrashReport(var13);
            }
            finally {
                this.shutdownMinecraftApplet();
            }
        }
        this.shutdownMinecraftApplet();
    }
    
    private void runGameLoop() {
        if (this.mcApplet != null && !this.mcApplet.isActive()) {
            this.running = false;
        }
        else {
            AxisAlignedBB.getAABBPool().cleanPool();
            if (Minecraft.theWorld != null) {
                Minecraft.theWorld.getWorldVec3Pool().clear();
            }
            this.mcProfiler.startSection("root");
            if (this.mcCanvas == null && Display.isCloseRequested()) {
                this.shutdown();
            }
            if (this.isGamePaused && Minecraft.theWorld != null) {
                final float var1 = this.timer.renderPartialTicks;
                this.timer.updateTimer();
                this.timer.renderPartialTicks = var1;
            }
            else {
                this.timer.updateTimer();
            }
            final long var2 = System.nanoTime();
            this.mcProfiler.startSection("tick");
            for (int var3 = 0; var3 < this.timer.elapsedTicks; ++var3) {
                this.runTick();
            }
            this.mcProfiler.endStartSection("preRenderErrors");
            final long var4 = System.nanoTime() - var2;
            this.checkGLError("Pre render");
            RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
            this.mcProfiler.endStartSection("sound");
            this.sndManager.setListener(Minecraft.thePlayer, this.timer.renderPartialTicks);
            if (!this.isGamePaused) {
                this.sndManager.func_92071_g();
            }
            this.mcProfiler.endSection();
            this.mcProfiler.startSection("render");
            this.mcProfiler.startSection("display");
            GL11.glEnable(3553);
            if (!Keyboard.isKeyDown(65)) {
                Display.update();
            }
            if (Minecraft.thePlayer != null && Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
                this.gameSettings.thirdPersonView = 0;
            }
            this.mcProfiler.endSection();
            if (!this.skipRenderWorld) {
                this.mcProfiler.endStartSection("gameRenderer");
                this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
                this.mcProfiler.endSection();
            }
            GL11.glFlush();
            this.mcProfiler.endSection();
            if (!Display.isActive() && this.fullscreen) {
                this.toggleFullscreen();
            }
            if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
                if (!this.mcProfiler.profilingEnabled) {
                    this.mcProfiler.clearProfiling();
                }
                this.mcProfiler.profilingEnabled = true;
                this.displayDebugInfo(var4);
            }
            else {
                this.mcProfiler.profilingEnabled = false;
                this.prevFrameTime = System.nanoTime();
            }
            this.guiAchievement.updateAchievementWindow();
            this.mcProfiler.startSection("root");
            Thread.yield();
            if (Keyboard.isKeyDown(65)) {
                Display.update();
            }
            this.screenshotListener();
            if (this.mcCanvas != null && !this.fullscreen && (this.mcCanvas.getWidth() != this.displayWidth || this.mcCanvas.getHeight() != this.displayHeight)) {
                this.displayWidth = this.mcCanvas.getWidth();
                this.displayHeight = this.mcCanvas.getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
            this.checkGLError("Post render");
            ++this.fpsCounter;
            final boolean var5 = this.isGamePaused;
            this.isGamePaused = (this.isSingleplayer() && Minecraft.currentScreen != null && Minecraft.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
            if (this.isIntegratedServerRunning() && Minecraft.thePlayer != null && Minecraft.thePlayer.sendQueue != null && this.isGamePaused != var5) {
                ((MemoryConnection)Minecraft.thePlayer.sendQueue.getNetManager()).setGamePaused(this.isGamePaused);
            }
            while (getSystemTime() >= this.debugUpdateTime + 1000L) {
                Minecraft.debugFPS = this.fpsCounter;
                this.debug = String.valueOf(Minecraft.debugFPS) + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
                WorldRenderer.chunksUpdated = 0;
                this.debugUpdateTime += 1000L;
                this.fpsCounter = 0;
                this.usageSnooper.addMemoryStatsToSnooper();
                if (!this.usageSnooper.isSnooperRunning()) {
                    this.usageSnooper.startSnooper();
                }
            }
            this.mcProfiler.endSection();
            if (this.func_90020_K() > 0) {
                Display.sync(EntityRenderer.performanceToFps(this.func_90020_K()));
            }
        }
    }
    
    private int func_90020_K() {
        return (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof GuiMainMenu) ? 2 : this.gameSettings.limitFramerate;
    }
    
    public void freeMemory() {
        try {
            Minecraft.memoryReserve = new byte[0];
            this.renderGlobal.deleteAllDisplayLists();
        }
        catch (Throwable t) {}
        try {
            MorbidHelper.gc();
            AxisAlignedBB.getAABBPool().clearPool();
            Minecraft.theWorld.getWorldVec3Pool().clearAndFreeCache();
        }
        catch (Throwable t2) {}
        try {
            MorbidHelper.gc();
            this.loadWorld(null);
        }
        catch (Throwable t3) {}
        MorbidHelper.gc();
    }
    
    private void screenshotListener() {
        if (Keyboard.isKeyDown(60)) {
            if (!this.isTakingScreenshot) {
                this.isTakingScreenshot = true;
                this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(Minecraft.minecraftDir, this.displayWidth, this.displayHeight));
            }
        }
        else {
            this.isTakingScreenshot = false;
        }
    }
    
    private void updateDebugProfilerName(int par1) {
        final List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (var2 != null && !var2.isEmpty()) {
            final ProfilerResult var3 = var2.remove(0);
            if (par1 == 0) {
                if (var3.field_76331_c.length() > 0) {
                    final int var4 = this.debugProfilerName.lastIndexOf(".");
                    if (var4 >= 0) {
                        this.debugProfilerName = this.debugProfilerName.substring(0, var4);
                    }
                }
            }
            else if (--par1 < var2.size() && !var2.get(par1).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
                }
                this.debugProfilerName = String.valueOf(this.debugProfilerName) + var2.get(par1).field_76331_c;
            }
        }
    }
    
    private void displayDebugInfo(final long par1) {
        if (this.mcProfiler.profilingEnabled) {
            final List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
            final ProfilerResult var4 = var3.remove(0);
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glEnable(2903);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GL11.glDisable(3553);
            final Tessellator var5 = Tessellator.instance;
            final short var6 = 160;
            final int var7 = this.displayWidth - var6 - 10;
            final int var8 = this.displayHeight - var6 * 2;
            GL11.glEnable(3042);
            var5.startDrawingQuads();
            var5.setColorRGBA_I(0, 200);
            var5.addVertex(var7 - var6 * 1.1f, var8 - var6 * 0.6f - 16.0f, 0.0);
            var5.addVertex(var7 - var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex(var7 + var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex(var7 + var6 * 1.1f, var8 - var6 * 0.6f - 16.0f, 0.0);
            var5.draw();
            GL11.glDisable(3042);
            double var9 = 0.0;
            for (int var10 = 0; var10 < var3.size(); ++var10) {
                final ProfilerResult var11 = var3.get(var10);
                final int var12 = MathHelper.floor_double(var11.field_76332_a / 4.0) + 1;
                var5.startDrawing(6);
                var5.setColorOpaque_I(var11.func_76329_a());
                var5.addVertex(var7, var8, 0.0);
                for (int var13 = var12; var13 >= 0; --var13) {
                    final float var14 = (float)((var9 + var11.field_76332_a * var13 / var12) * 3.141592653589793 * 2.0 / 100.0);
                    final float var15 = MathHelper.sin(var14) * var6;
                    final float var16 = MathHelper.cos(var14) * var6 * 0.5f;
                    var5.addVertex(var7 + var15, var8 - var16, 0.0);
                }
                var5.draw();
                var5.startDrawing(5);
                var5.setColorOpaque_I((var11.func_76329_a() & 0xFEFEFE) >> 1);
                for (int var13 = var12; var13 >= 0; --var13) {
                    final float var14 = (float)((var9 + var11.field_76332_a * var13 / var12) * 3.141592653589793 * 2.0 / 100.0);
                    final float var15 = MathHelper.sin(var14) * var6;
                    final float var16 = MathHelper.cos(var14) * var6 * 0.5f;
                    var5.addVertex(var7 + var15, var8 - var16, 0.0);
                    var5.addVertex(var7 + var15, var8 - var16 + 10.0f, 0.0);
                }
                var5.draw();
                var9 += var11.field_76332_a;
            }
            final DecimalFormat var17 = new DecimalFormat("##0.00");
            GL11.glEnable(3553);
            String var18 = "";
            if (!var4.field_76331_c.equals("unspecified")) {
                var18 = String.valueOf(var18) + "[0] ";
            }
            if (var4.field_76331_c.length() == 0) {
                var18 = String.valueOf(var18) + "ROOT ";
            }
            else {
                var18 = String.valueOf(var18) + var4.field_76331_c + " ";
            }
            final int var12 = 16777215;
            this.fontRenderer.drawStringWithShadow(var18, var7 - var6, var8 - var6 / 2 - 16, var12);
            this.fontRenderer.drawStringWithShadow(var18 = String.valueOf(var17.format(var4.field_76330_b)) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var18), var8 - var6 / 2 - 16, var12);
            for (int var13 = 0; var13 < var3.size(); ++var13) {
                final ProfilerResult var19 = var3.get(var13);
                String var20 = "";
                if (var19.field_76331_c.equals("unspecified")) {
                    var20 = String.valueOf(var20) + "[?] ";
                }
                else {
                    var20 = String.valueOf(var20) + "[" + (var13 + 1) + "] ";
                }
                var20 = String.valueOf(var20) + var19.field_76331_c;
                this.fontRenderer.drawStringWithShadow(var20, var7 - var6, var8 + var6 / 2 + var13 * 8 + 20, var19.func_76329_a());
                this.fontRenderer.drawStringWithShadow(var20 = String.valueOf(var17.format(var19.field_76332_a)) + "%", var7 + var6 - 50 - this.fontRenderer.getStringWidth(var20), var8 + var6 / 2 + var13 * 8 + 20, var19.func_76329_a());
                this.fontRenderer.drawStringWithShadow(var20 = String.valueOf(var17.format(var19.field_76330_b)) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var20), var8 + var6 / 2 + var13 * 8 + 20, var19.func_76329_a());
            }
        }
    }
    
    public void shutdown() {
        this.running = false;
    }
    
    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }
    
    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            KeyBinding.unPressAllKeys();
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }
    
    public void displayInGameMenu() {
        if (Minecraft.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                this.sndManager.pauseAllSounds();
            }
        }
    }
    
    private void sendClickBlockToController(final int par1, final boolean par2) {
        if (!par2) {
            this.leftClickCounter = 0;
        }
        if (par1 != 0 || this.leftClickCounter <= 0) {
            if (par2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && par1 == 0) {
                final int var3 = this.objectMouseOver.blockX;
                final int var4 = this.objectMouseOver.blockY;
                final int var5 = this.objectMouseOver.blockZ;
                this.playerController.onPlayerDamageBlock(var3, var4, var5, this.objectMouseOver.sideHit);
                if (Minecraft.thePlayer.canCurrentToolHarvestBlock(var3, var4, var5)) {
                    this.effectRenderer.addBlockHitEffects(var3, var4, var5, this.objectMouseOver.sideHit);
                    Minecraft.thePlayer.swingItem();
                }
            }
            else {
                this.playerController.resetBlockRemoving();
            }
        }
    }
    
    private void clickMouse(final int par1) {
        if (par1 != 0 || this.leftClickCounter <= 0) {
            if (par1 == 0) {
                Minecraft.thePlayer.swingItem();
            }
            if (par1 == 1) {
                this.rightClickDelayTimer = 4;
            }
            boolean var2 = true;
            final ItemStack var3 = Minecraft.thePlayer.inventory.getCurrentItem();
            if (this.objectMouseOver == null) {
                if (par1 == 0 && this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
            else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
                if (par1 == 0) {
                    this.playerController.attackEntity(Minecraft.thePlayer, this.objectMouseOver.entityHit);
                }
                if (par1 == 1 && this.playerController.func_78768_b(Minecraft.thePlayer, this.objectMouseOver.entityHit)) {
                    var2 = false;
                }
            }
            else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                final int var4 = this.objectMouseOver.blockX;
                final int var5 = this.objectMouseOver.blockY;
                final int var6 = this.objectMouseOver.blockZ;
                final int var7 = this.objectMouseOver.sideHit;
                if (par1 == 0) {
                    this.playerController.clickBlock(var4, var5, var6, this.objectMouseOver.sideHit);
                }
                else {
                    final int var8 = (var3 != null) ? var3.stackSize : 0;
                    if (this.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, var3, var4, var5, var6, var7, this.objectMouseOver.hitVec)) {
                        var2 = false;
                        Minecraft.thePlayer.swingItem();
                    }
                    if (var3 == null) {
                        return;
                    }
                    if (var3.stackSize == 0) {
                        Minecraft.thePlayer.inventory.mainInventory[Minecraft.thePlayer.inventory.currentItem] = null;
                    }
                    else if (var3.stackSize != var8 || this.playerController.isInCreativeMode()) {
                        this.entityRenderer.itemRenderer.resetEquippedProgress();
                    }
                }
            }
            if (var2 && par1 == 1) {
                final ItemStack var9 = Minecraft.thePlayer.inventory.getCurrentItem();
                if (var9 != null && this.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, var9)) {
                    this.entityRenderer.itemRenderer.resetEquippedProgress2();
                }
            }
        }
    }
    
    public void toggleFullscreen() {
        try {
            this.fullscreen = !this.fullscreen;
            if (this.fullscreen) {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            else {
                if (this.mcCanvas != null) {
                    this.displayWidth = this.mcCanvas.getWidth();
                    this.displayHeight = this.mcCanvas.getHeight();
                }
                else {
                    this.displayWidth = this.tempDisplayWidth;
                    this.displayHeight = this.tempDisplayHeight;
                }
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            if (Minecraft.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            }
            Display.setFullscreen(this.fullscreen);
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            Display.update();
        }
        catch (Exception var2) {
            var2.printStackTrace();
        }
    }
    
    private void resize(final int par1, final int par2) {
        this.displayWidth = ((par1 <= 0) ? 1 : par1);
        this.displayHeight = ((par2 <= 0) ? 1 : par2);
        if (Minecraft.currentScreen != null) {
            final ScaledResolution var3 = new ScaledResolution(this.gameSettings, par1, par2);
            final int var4 = ScaledResolution.getScaledWidth();
            final int var5 = ScaledResolution.getScaledHeight();
            Minecraft.currentScreen.setWorldAndResolution(this, var4, var5);
        }
    }
    
    public void runTick() {
        if (this.rightClickDelayTimer > 0) {
            --this.rightClickDelayTimer;
        }
        this.mcProfiler.startSection("stats");
        this.statFileWriter.func_77449_e();
        this.mcProfiler.endStartSection("gui");
        if (!this.isGamePaused) {
            this.ingameGUI.updateTick();
        }
        this.mcProfiler.endStartSection("pick");
        this.entityRenderer.getMouseOver(1.0f);
        this.mcProfiler.endStartSection("gameMode");
        if (!this.isGamePaused && Minecraft.theWorld != null) {
            this.playerController.updateController();
        }
        this.renderEngine.bindTexture("/terrain.png");
        this.mcProfiler.endStartSection("textures");
        if (!this.isGamePaused) {
            this.renderEngine.updateDynamicTextures();
        }
        if (Minecraft.currentScreen == null && Minecraft.thePlayer != null) {
            if (Minecraft.thePlayer.getHealth() <= 0) {
                this.displayGuiScreen(null);
            }
            else if (Minecraft.thePlayer.isPlayerSleeping() && Minecraft.theWorld != null) {
                this.displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof GuiSleepMP && !Minecraft.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (Minecraft.currentScreen != null) {
            this.leftClickCounter = 10000;
        }
        if (Minecraft.currentScreen != null) {
            try {
                Minecraft.currentScreen.handleInput();
            }
            catch (Throwable var2) {
                final CrashReport var1 = CrashReport.makeCrashReport(var2, "Updating screen events");
                final CrashReportCategory var3 = var1.makeCategory("Affected screen");
                var3.addCrashSectionCallable("Screen name", new CallableUpdatingScreenName(this));
                throw new ReportedException(var1);
            }
            if (Minecraft.currentScreen != null) {
                try {
                    Minecraft.currentScreen.guiParticles.update();
                }
                catch (Throwable var4) {
                    final CrashReport var1 = CrashReport.makeCrashReport(var4, "Ticking screen particles");
                    final CrashReportCategory var3 = var1.makeCategory("Affected screen");
                    var3.addCrashSectionCallable("Screen name", new CallableParticleScreenName(this));
                    throw new ReportedException(var1);
                }
                try {
                    Minecraft.currentScreen.updateScreen();
                }
                catch (Throwable var5) {
                    final CrashReport var1 = CrashReport.makeCrashReport(var5, "Ticking screen");
                    final CrashReportCategory var3 = var1.makeCategory("Affected screen");
                    var3.addCrashSectionCallable("Screen name", new CallableTickingScreenName(this));
                    throw new ReportedException(var1);
                }
            }
        }
        if (Minecraft.currentScreen == null || Minecraft.currentScreen.allowUserInput) {
            this.mcProfiler.endStartSection("mouse");
            while (Mouse.next()) {
                KeyBinding.setKeyBindState(Mouse.getEventButton() - 100, Mouse.getEventButtonState());
                if (Mouse.getEventButtonState()) {
                    KeyBinding.onTick(Mouse.getEventButton() - 100);
                }
                final long var6 = getSystemTime() - this.systemTime;
                if (var6 <= 200L) {
                    int var7 = Mouse.getEventDWheel();
                    if (var7 != 0) {
                        Minecraft.thePlayer.inventory.changeCurrentItem(var7);
                        if (this.gameSettings.noclip) {
                            if (var7 > 0) {
                                var7 = 1;
                            }
                            if (var7 < 0) {
                                var7 = -1;
                            }
                            final GameSettings gameSettings = this.gameSettings;
                            gameSettings.noclipRate += var7 * 0.25f;
                        }
                    }
                    if (Minecraft.currentScreen == null) {
                        if (this.inGameHasFocus || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        this.setIngameFocus();
                    }
                    else {
                        if (Minecraft.currentScreen == null) {
                            continue;
                        }
                        Minecraft.currentScreen.handleMouseInput();
                    }
                }
            }
            if (this.leftClickCounter > 0) {
                --this.leftClickCounter;
            }
            this.mcProfiler.endStartSection("keyboard");
            while (Keyboard.next()) {
                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }
                if (this.field_83002_am > 0L) {
                    if (getSystemTime() - this.field_83002_am >= 6000L) {
                        throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                    }
                    if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                        this.field_83002_am = -1L;
                    }
                }
                else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                    this.field_83002_am = getSystemTime();
                }
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == 87) {
                        this.toggleFullscreen();
                    }
                    else {
                        Morbid.getHookManager().onKeyPressed(Keyboard.getEventKey());
                        if (Minecraft.currentScreen != null) {
                            Minecraft.currentScreen.handleKeyboardInput();
                        }
                        else {
                            if (Keyboard.getEventKey() == 1) {
                                this.displayInGameMenu();
                            }
                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                                this.forceReload();
                            }
                            if (Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61)) {
                                this.renderEngine.refreshTextures();
                                this.renderGlobal.loadRenderers();
                            }
                            if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61)) {
                                final boolean var8 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                                this.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, var8 ? -1 : 1);
                            }
                            if (Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61)) {
                                this.renderGlobal.loadRenderers();
                            }
                            if (Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61)) {
                                this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                                this.gameSettings.saveOptions();
                            }
                            if (Keyboard.getEventKey() == 48 && Keyboard.isKeyDown(61)) {
                                RenderManager.field_85095_o = !RenderManager.field_85095_o;
                            }
                            if (Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61)) {
                                this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                                this.gameSettings.saveOptions();
                            }
                            if (Keyboard.getEventKey() == 59) {
                                this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                            }
                            if (Keyboard.getEventKey() == 61) {
                                this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                                this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                            }
                            if (Keyboard.getEventKey() == 63) {
                                final GameSettings gameSettings2 = this.gameSettings;
                                ++gameSettings2.thirdPersonView;
                                if (this.gameSettings.thirdPersonView > 2) {
                                    this.gameSettings.thirdPersonView = 0;
                                }
                            }
                            if (Keyboard.getEventKey() == 66) {
                                this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                            }
                        }
                        for (int var9 = 0; var9 < 9; ++var9) {
                            if (Keyboard.getEventKey() == 2 + var9) {
                                Minecraft.thePlayer.inventory.currentItem = var9;
                            }
                        }
                        if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) {
                            continue;
                        }
                        if (Keyboard.getEventKey() == 11) {
                            this.updateDebugProfilerName(0);
                        }
                        for (int var9 = 0; var9 < 9; ++var9) {
                            if (Keyboard.getEventKey() == 2 + var9) {
                                this.updateDebugProfilerName(var9 + 1);
                            }
                        }
                    }
                }
            }
            final boolean var8 = this.gameSettings.chatVisibility != 2;
            while (this.gameSettings.keyBindInventory.isPressed()) {
                this.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
            }
            while (this.gameSettings.keyBindDrop.isPressed()) {
                Minecraft.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
            }
            while (this.gameSettings.keyBindChat.isPressed() && var8) {
                this.displayGuiScreen(new GuiChat());
            }
            if (Minecraft.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var8) {
                this.displayGuiScreen(new GuiChat("/"));
            }
            if (Minecraft.thePlayer.isUsingItem()) {
                if (!this.gameSettings.keyBindUseItem.pressed) {
                    this.playerController.onStoppedUsingItem(Minecraft.thePlayer);
                }
                while (this.gameSettings.keyBindAttack.isPressed()) {}
                while (this.gameSettings.keyBindUseItem.isPressed()) {}
                while (this.gameSettings.keyBindPickBlock.isPressed()) {}
            }
            else {
                while (this.gameSettings.keyBindAttack.isPressed()) {
                    this.clickMouse(0);
                }
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                    this.clickMouse(1);
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                    this.clickMiddleMouseButton();
                }
            }
            if (this.gameSettings.keyBindUseItem.pressed && this.rightClickDelayTimer == 0 && !Minecraft.thePlayer.isUsingItem()) {
                this.clickMouse(1);
            }
            this.sendClickBlockToController(0, Minecraft.currentScreen == null && this.gameSettings.keyBindAttack.pressed && this.inGameHasFocus);
        }
        if (Minecraft.theWorld != null) {
            if (Minecraft.thePlayer != null) {
                ++this.joinPlayerCounter;
                if (this.joinPlayerCounter == 30) {
                    this.joinPlayerCounter = 0;
                    Minecraft.theWorld.joinEntityInSurroundings(Minecraft.thePlayer);
                }
            }
            this.mcProfiler.endStartSection("gameRenderer");
            if (!this.isGamePaused) {
                this.entityRenderer.updateRenderer();
            }
            this.mcProfiler.endStartSection("levelRenderer");
            if (!this.isGamePaused) {
                this.renderGlobal.updateClouds();
            }
            this.mcProfiler.endStartSection("level");
            if (!this.isGamePaused) {
                if (Minecraft.theWorld.lastLightningBolt > 0) {
                    final WorldClient theWorld = Minecraft.theWorld;
                    --theWorld.lastLightningBolt;
                }
                Minecraft.theWorld.updateEntities();
            }
            if (!this.isGamePaused) {
                Minecraft.theWorld.setAllowedSpawnTypes(Minecraft.theWorld.difficultySetting > 0, true);
                try {
                    Minecraft.theWorld.tick();
                }
                catch (Throwable var10) {
                    final CrashReport var1 = CrashReport.makeCrashReport(var10, "Exception in world tick");
                    if (Minecraft.theWorld == null) {
                        final CrashReportCategory var3 = var1.makeCategory("Affected level");
                        var3.addCrashSection("Problem", "Level is null!");
                    }
                    else {
                        Minecraft.theWorld.addWorldInfoToCrashReport(var1);
                    }
                    throw new ReportedException(var1);
                }
            }
            this.mcProfiler.endStartSection("animateTick");
            if (!this.isGamePaused && Minecraft.theWorld != null) {
                Minecraft.theWorld.doVoidFogParticles(MathHelper.floor_double(Minecraft.thePlayer.posX), MathHelper.floor_double(Minecraft.thePlayer.posY), MathHelper.floor_double(Minecraft.thePlayer.posZ));
            }
            this.mcProfiler.endStartSection("particles");
            if (!this.isGamePaused) {
                this.effectRenderer.updateEffects();
            }
        }
        else if (this.myNetworkManager != null) {
            this.mcProfiler.endStartSection("pendingConnection");
            this.myNetworkManager.processReadPackets();
        }
        this.mcProfiler.endSection();
        this.systemTime = getSystemTime();
    }
    
    private void forceReload() {
        this.getLogAgent().logInfo("FORCING RELOAD!");
        if (this.sndManager != null) {
            this.sndManager.stopAllSounds();
        }
        (this.sndManager = new SoundManager()).loadSoundSettings(this.gameSettings);
        this.downloadResourcesThread.reloadResources();
    }
    
    public void launchIntegratedServer(final String par1Str, final String par2Str, WorldSettings par3WorldSettings) {
        this.loadWorld(null);
        MorbidHelper.gc();
        final ISaveHandler var4 = this.saveLoader.getSaveLoader(par1Str, false);
        WorldInfo var5 = var4.loadWorldInfo();
        if (var5 == null && par3WorldSettings != null) {
            this.statFileWriter.readStat(StatList.createWorldStat, 1);
            var5 = new WorldInfo(par3WorldSettings, par1Str);
            var4.saveWorldInfo(var5);
        }
        if (par3WorldSettings == null) {
            par3WorldSettings = new WorldSettings(var5);
        }
        this.statFileWriter.readStat(StatList.startGameStat, 1);
        (this.theIntegratedServer = new IntegratedServer(this, par1Str, par2Str, par3WorldSettings)).startServerThread();
        this.integratedServerIsRunning = true;
        this.loadingScreen.displayProgressMessage(StatCollector.translateToLocal("menu.loadingLevel"));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            final String var6 = this.theIntegratedServer.getUserMessage();
            if (var6 != null) {
                this.loadingScreen.resetProgresAndWorkingMessage(StatCollector.translateToLocal(var6));
            }
            else {
                this.loadingScreen.resetProgresAndWorkingMessage("");
            }
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException ex) {}
        }
        this.displayGuiScreen(null);
        try {
            final NetClientHandler var7 = new NetClientHandler(this, this.theIntegratedServer);
            this.myNetworkManager = var7.getNetManager();
        }
        catch (IOException var8) {
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(new CrashReport("Connecting to integrated server", var8)));
        }
    }
    
    public void loadWorld(final WorldClient par1WorldClient) {
        this.loadWorld(par1WorldClient, "");
    }
    
    public void loadWorld(final WorldClient par1WorldClient, final String par2Str) {
        this.statFileWriter.syncStats();
        if (par1WorldClient == null) {
            final NetClientHandler var3 = this.getNetHandler();
            if (var3 != null) {
                var3.cleanup();
            }
            if (this.myNetworkManager != null) {
                this.myNetworkManager.closeConnections();
            }
            if (this.theIntegratedServer != null) {
                this.theIntegratedServer.initiateShutdown();
            }
            this.theIntegratedServer = null;
        }
        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(par2Str);
            this.loadingScreen.resetProgresAndWorkingMessage("");
        }
        if (par1WorldClient == null && Minecraft.theWorld != null) {
            if (this.texturePackList.getIsDownloading()) {
                this.texturePackList.onDownloadFinished();
            }
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        this.sndManager.playStreaming(null, 0.0f, 0.0f, 0.0f);
        this.sndManager.stopAllSounds();
        if ((Minecraft.theWorld = par1WorldClient) != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(par1WorldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(par1WorldClient);
            }
            if (Minecraft.thePlayer == null) {
                Minecraft.thePlayer = this.playerController.func_78754_a(par1WorldClient);
                this.playerController.flipPlayer(Minecraft.thePlayer);
            }
            Minecraft.thePlayer.preparePlayerToSpawn();
            par1WorldClient.spawnEntityInWorld(Minecraft.thePlayer);
            Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(Minecraft.thePlayer);
            this.renderViewEntity = Minecraft.thePlayer;
        }
        else {
            this.saveLoader.flushCache();
            Minecraft.thePlayer = null;
        }
        MorbidHelper.gc();
        this.systemTime = 0L;
    }
    
    public void installResource(String par1Str, final File par2File) {
        final int var3 = par1Str.indexOf("/");
        final String var4 = par1Str.substring(0, var3);
        par1Str = par1Str.substring(var3 + 1);
        if (var4.equalsIgnoreCase("sound3")) {
            this.sndManager.addSound(par1Str, par2File);
        }
        else if (var4.equalsIgnoreCase("streaming")) {
            this.sndManager.addStreaming(par1Str, par2File);
        }
        else if (!var4.equalsIgnoreCase("music") && !var4.equalsIgnoreCase("newmusic")) {
            if (var4.equalsIgnoreCase("lang")) {
                StringTranslate.getInstance().func_94519_a(par1Str, par2File);
            }
        }
        else {
            this.sndManager.addMusic(par1Str, par2File);
        }
    }
    
    public String debugInfoRenders() {
        return this.renderGlobal.getDebugInfoRenders();
    }
    
    public String getEntityDebug() {
        return this.renderGlobal.getDebugInfoEntities();
    }
    
    public String getWorldProviderName() {
        return Minecraft.theWorld.getProviderName();
    }
    
    public String debugInfoEntities() {
        return "P: " + this.effectRenderer.getStatistics() + ". T: " + Minecraft.theWorld.getDebugLoadedEntities();
    }
    
    public void setDimensionAndSpawnPlayer(final int par1) {
        Minecraft.theWorld.setSpawnLocation();
        Minecraft.theWorld.removeAllEntities();
        int var2 = 0;
        if (Minecraft.thePlayer != null) {
            var2 = Minecraft.thePlayer.entityId;
            Minecraft.theWorld.removeEntity(Minecraft.thePlayer);
        }
        this.renderViewEntity = null;
        Minecraft.thePlayer = this.playerController.func_78754_a(Minecraft.theWorld);
        Minecraft.thePlayer.dimension = par1;
        this.renderViewEntity = Minecraft.thePlayer;
        Minecraft.thePlayer.preparePlayerToSpawn();
        Minecraft.theWorld.spawnEntityInWorld(Minecraft.thePlayer);
        this.playerController.flipPlayer(Minecraft.thePlayer);
        Minecraft.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        Minecraft.thePlayer.entityId = var2;
        this.playerController.setPlayerCapabilities(Minecraft.thePlayer);
        if (Minecraft.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }
    
    void setDemo(final boolean par1) {
        this.isDemo = par1;
    }
    
    public final boolean isDemo() {
        return this.isDemo;
    }
    
    public NetClientHandler getNetHandler() {
        return (Minecraft.thePlayer != null) ? Minecraft.thePlayer.sendQueue : null;
    }
    
    public static void main(final String[] par0ArrayOfStr) {
        final HashMap var1 = new HashMap();
        boolean var2 = false;
        boolean var3 = true;
        final boolean var4 = false;
        String var6;
        final String var5 = var6 = "Player" + getSystemTime() % 1000L;
        if (par0ArrayOfStr.length > 0) {
            var6 = par0ArrayOfStr[0];
        }
        String var7 = "-";
        if (par0ArrayOfStr.length > 1) {
            var7 = par0ArrayOfStr[1];
        }
        final ArrayList var8 = new ArrayList();
        for (int var9 = 2; var9 < par0ArrayOfStr.length; ++var9) {
            final String var10 = par0ArrayOfStr[var9];
            final String var11 = (var9 == par0ArrayOfStr.length - 1) ? null : par0ArrayOfStr[var9 + 1];
            boolean var12 = false;
            if (!var10.equals("-demo") && !var10.equals("--demo")) {
                if (var10.equals("--applet")) {
                    var3 = false;
                }
                else if (var10.equals("--password") && var11 != null) {
                    final String[] var13 = HttpUtil.loginToMinecraft(null, var6, var11);
                    if (var13 != null) {
                        var6 = var13[0];
                        var7 = var13[1];
                        var8.add("Logged in insecurely as " + var6);
                    }
                    else {
                        var8.add("Could not log in as " + var6 + " with given password");
                    }
                    var12 = true;
                }
            }
            else {
                var2 = true;
            }
            if (var12) {
                ++var9;
            }
        }
        if (var6.contains("@") && var7.length() <= 1) {
            var6 = var5;
        }
        var1.put("demo", new StringBuilder().append(var2).toString());
        var1.put("stand-alone", new StringBuilder().append(var3).toString());
        var1.put("username", var6);
        var1.put("fullscreen", new StringBuilder().append(var4).toString());
        var1.put("sessionid", var7);
        final Frame var14 = new Frame();
        var14.setTitle("Reborn v0.6 | Cracked by Wolve189");
        var14.setBackground(Color.BLACK);
        final JPanel var15 = new JPanel();
        var14.setLayout(new BorderLayout());
        var15.setPreferredSize(new Dimension(854, 480));
        var14.add(var15, "Center");
        var14.pack();
        var14.setLocationRelativeTo(null);
        var14.setVisible(true);
        var14.addWindowListener(new GameWindowListener());
        final MinecraftFakeLauncher var16 = new MinecraftFakeLauncher(var1);
        final MinecraftApplet var17 = new MinecraftApplet();
        var17.setStub(var16);
        var16.setLayout(new BorderLayout());
        var16.add(var17, "Center");
        var16.validate();
        var14.removeAll();
        var14.setLayout(new BorderLayout());
        var14.add(var16, "Center");
        var14.validate();
        var17.init();
        for (final String var19 : var8) {
            getMinecraft().getLogAgent().logInfo(var19);
        }
        var17.start();
        Runtime.getRuntime().addShutdownHook(new ThreadShutdown());
    }
    
    public static boolean isGuiEnabled() {
        return Minecraft.theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }
    
    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }
    
    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }
    
    public boolean handleClientCommand(final String par1Str) {
        return !par1Str.startsWith("/") && false;
    }
    
    private void clickMiddleMouseButton() {
        if (this.objectMouseOver != null) {
            final boolean var1 = Minecraft.thePlayer.capabilities.isCreativeMode;
            int var2 = 0;
            boolean var3 = false;
            int var8;
            if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                final int var4 = this.objectMouseOver.blockX;
                final int var5 = this.objectMouseOver.blockY;
                final int var6 = this.objectMouseOver.blockZ;
                final Block var7 = Block.blocksList[Minecraft.theWorld.getBlockId(var4, var5, var6)];
                if (var7 == null) {
                    return;
                }
                var8 = var7.idPicked(Minecraft.theWorld, var4, var5, var6);
                if (var8 == 0) {
                    return;
                }
                var3 = Item.itemsList[var8].getHasSubtypes();
                final int var9 = (var8 < 256 && !Block.blocksList[var7.blockID].isFlowerPot()) ? var8 : var7.blockID;
                var2 = Block.blocksList[var9].getDamageValue(Minecraft.theWorld, var4, var5, var6);
            }
            else {
                if (this.objectMouseOver.typeOfHit != EnumMovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var1) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    var8 = Item.painting.itemID;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    final EntityItemFrame var10 = (EntityItemFrame)this.objectMouseOver.entityHit;
                    if (var10.getDisplayedItem() == null) {
                        var8 = Item.itemFrame.itemID;
                    }
                    else {
                        var8 = var10.getDisplayedItem().itemID;
                        var2 = var10.getDisplayedItem().getItemDamage();
                        var3 = true;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    final EntityMinecart var11 = (EntityMinecart)this.objectMouseOver.entityHit;
                    if (var11.getMinecartType() == 2) {
                        var8 = Item.minecartPowered.itemID;
                    }
                    else if (var11.getMinecartType() == 1) {
                        var8 = Item.minecartCrate.itemID;
                    }
                    else if (var11.getMinecartType() == 3) {
                        var8 = Item.minecartTnt.itemID;
                    }
                    else if (var11.getMinecartType() == 5) {
                        var8 = Item.minecartHopper.itemID;
                    }
                    else {
                        var8 = Item.minecartEmpty.itemID;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    var8 = Item.boat.itemID;
                }
                else {
                    var8 = Item.monsterPlacer.itemID;
                    var2 = EntityList.getEntityID(this.objectMouseOver.entityHit);
                    var3 = true;
                    if (var2 <= 0 || !EntityList.entityEggs.containsKey(var2)) {
                        return;
                    }
                }
            }
            Minecraft.thePlayer.inventory.setCurrentItem(var8, var2, var3, var1);
            if (var1) {
                final int var4 = Minecraft.thePlayer.inventoryContainer.inventorySlots.size() - 9 + Minecraft.thePlayer.inventory.currentItem;
                this.playerController.sendSlotPacket(Minecraft.thePlayer.inventory.getStackInSlot(Minecraft.thePlayer.inventory.currentItem), var4);
            }
        }
    }
    
    public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport par1CrashReport) {
        par1CrashReport.func_85056_g().addCrashSectionCallable("LWJGL", new CallableLWJGLVersion(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("OpenGL", new CallableGLInfo(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableModded(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableType2(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Texture Pack", new CallableTexturePack(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableClientProfiler(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableClientMemoryStats(this));
        if (Minecraft.theWorld != null) {
            Minecraft.theWorld.addWorldInfoToCrashReport(par1CrashReport);
        }
        return par1CrashReport;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.theMinecraft;
    }
    
    public void scheduleTexturePackRefresh() {
        this.refreshTexturePacksScheduled = true;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("fps", Minecraft.debugFPS);
        par1PlayerUsageSnooper.addData("texpack_name", this.texturePackList.getSelectedTexturePack().getTexturePackFileName());
        par1PlayerUsageSnooper.addData("vsync_enabled", this.gameSettings.enableVsync);
        par1PlayerUsageSnooper.addData("display_frequency", Display.getDisplayMode().getFrequency());
        par1PlayerUsageSnooper.addData("display_type", this.fullscreen ? "fullscreen" : "windowed");
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            par1PlayerUsageSnooper.addData("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("opengl_version", GL11.glGetString(7938));
        par1PlayerUsageSnooper.addData("opengl_vendor", GL11.glGetString(7936));
        par1PlayerUsageSnooper.addData("client_brand", ClientBrandRetriever.getClientModName());
        par1PlayerUsageSnooper.addData("applet", this.hideQuitButton);
        final ContextCapabilities var2 = GLContext.getCapabilities();
        par1PlayerUsageSnooper.addData("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_matrix_palette]", var2.GL_ARB_matrix_palette);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
        par1PlayerUsageSnooper.addData("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        par1PlayerUsageSnooper.addData("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        par1PlayerUsageSnooper.addData("gl_max_texture_size", getGLMaximumTextureSize());
    }
    
    public static int getGLMaximumTextureSize() {
        for (int var0 = 16384; var0 > 0; var0 >>= 1) {
            GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
            final int var2 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
            if (var2 != 0) {
                return var0;
            }
        }
        return -1;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }
    
    public void setServerData(final ServerData par1ServerData) {
        this.currentServerData = par1ServerData;
    }
    
    public ServerData getServerData() {
        return this.currentServerData;
    }
    
    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }
    
    public boolean isSingleplayer() {
        return this.integratedServerIsRunning && this.theIntegratedServer != null;
    }
    
    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }
    
    public static void stopIntegratedServer() {
        if (Minecraft.theMinecraft != null) {
            final IntegratedServer var0 = Minecraft.theMinecraft.getIntegratedServer();
            if (var0 != null) {
                var0.stopServer();
            }
        }
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public boolean isFullScreen() {
        return this.fullscreen;
    }
    
    @Override
    public ILogAgent getLogAgent() {
        return this.field_94139_O;
    }
}
