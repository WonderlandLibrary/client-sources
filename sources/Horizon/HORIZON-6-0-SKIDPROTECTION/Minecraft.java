package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Executors;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.apache.commons.lang3.Validate;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;
import java.nio.ByteOrder;
import com.google.common.util.concurrent.ListenableFuture;
import java.net.SocketAddress;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.util.concurrent.Callable;
import java.text.DecimalFormat;
import java.util.concurrent.FutureTask;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.GL11;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.PixelFormat;
import com.google.common.collect.Iterables;
import com.mojang.authlib.properties.Property;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.util.UUID;
import com.google.common.collect.Queues;
import net.minecraft.client.main.GameConfiguration;
import com.google.common.collect.Lists;
import org.lwjgl.opengl.DisplayMode;
import org.apache.logging.log4j.LogManager;
import java.util.Queue;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.net.Proxy;
import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class Minecraft implements IThreadListener, IPlayerUsage
{
    private static final Logger ˆáŠ;
    private static final ResourceLocation_1975012498 áŒŠ;
    public static final boolean HorizonCode_Horizon_È;
    public static byte[] Â;
    private static final List £ÂµÄ;
    public final File Ý;
    private final PropertyMap Ø­Âµ;
    private ServerData Ä;
    public TextureManager Ø­áŒŠá;
    private static Minecraft Ñ¢Â;
    public PlayerControllerMP Âµá€;
    private boolean Ï­à;
    private boolean áˆºáˆºÈ;
    private boolean ÇŽá€;
    private CrashReport Ï;
    public int Ó;
    public int à;
    public Timer_356573597 Ø;
    private PlayerUsageSnooper Ô;
    public WorldClient áŒŠÆ;
    public RenderGlobal áˆºÑ¢Õ;
    public RenderManager ÂµÈ;
    private RenderItem ÇªÓ;
    private ItemRenderer áˆºÏ;
    public EntityPlayerSPOverwrite á;
    public Entity ˆÏ­;
    public Entity £á;
    public EffectRenderer Å;
    public Session £à;
    private boolean ˆáƒ;
    public FontRenderer µà;
    public FontRenderer ˆà;
    public GuiScreen ¥Æ;
    public LoadingScreenRenderer Ø­à;
    public EntityRenderer µÕ;
    private int Œ;
    private int £Ï;
    private int Ø­á;
    private IntegratedServer ˆÉ;
    public GuiAchievement Æ;
    public GuiIngame Šáƒ;
    public boolean Ï­Ðƒà;
    public MovingObjectPosition áŒŠà;
    public GameSettings ŠÄ;
    public MouseHelper Ñ¢á;
    public final File ŒÏ;
    private final File Ï­Ï­Ï;
    private final String £Â;
    private final Proxy £Ó;
    private ISaveFormat ˆÐƒØ­à;
    public static int Çªà¢;
    public static int Ê;
    private String £Õ;
    private int Ï­Ô;
    public boolean ÇŽÉ;
    long ˆá;
    private int Œà;
    private final boolean Ðƒá;
    private final boolean ˆÏ;
    private NetworkManager áˆºÇŽØ;
    private boolean ÇªÂµÕ;
    public final Profiler ÇŽÕ;
    private long áŒŠÏ;
    private IReloadableResourceManager áŒŠáŠ;
    private final IMetadataSerializer ˆÓ;
    private final List ¥Ä;
    private final DefaultResourcePack ÇªÔ;
    private ResourcePackRepository Û;
    private LanguageManager ŠÓ;
    private IStream ÇŽá;
    private Framebuffer Ñ¢à;
    private TextureMap ÇªØ­;
    private SoundHandler £áŒŠá;
    private MusicTicker áˆº;
    private ResourceLocation_1975012498 Šà;
    private final MinecraftSessionService áŒŠá€;
    private SkinManager ¥Ï;
    private final Queue ˆà¢;
    private long Ñ¢Ç;
    private final Thread £É;
    public ModelManager É;
    private BlockRendererDispatcher Ðƒáƒ;
    public boolean áƒ;
    public String á€;
    public boolean Õ;
    public boolean à¢;
    public boolean ŠÂµà;
    public boolean ¥à;
    long Âµà;
    int Ç;
    long È;
    private String Ðƒà;
    private static final String ¥É = "CL_00000631";
    
    static {
        ˆáŠ = LogManager.getLogger();
        áŒŠ = new ResourceLocation_1975012498("textures/gui/title/mojang.png");
        HorizonCode_Horizon_È = (Util_1252169911.HorizonCode_Horizon_È() == Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá);
        Minecraft.Â = new byte[10485760];
        £ÂµÄ = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
    }
    
    public Minecraft(final GameConfiguration gameConfiguration) {
        this.áˆºáˆºÈ = true;
        this.Ø = new Timer_356573597(20.0f);
        this.Ô = new PlayerUsageSnooper("client", this, MinecraftServer.Œà());
        this.ˆá = áƒ();
        this.ÇŽÕ = new Profiler();
        this.áŒŠÏ = -1L;
        this.ˆÓ = new IMetadataSerializer();
        this.¥Ä = Lists.newArrayList();
        this.ˆà¢ = Queues.newArrayDeque();
        this.Ñ¢Ç = 0L;
        this.£É = Thread.currentThread();
        this.áƒ = true;
        this.á€ = "";
        this.Õ = false;
        this.à¢ = false;
        this.ŠÂµà = false;
        this.¥à = true;
        this.Âµà = áƒ();
        this.È = -1L;
        this.Ðƒà = "root";
        Minecraft.Ñ¢Â = this;
        this.ŒÏ = gameConfiguration.Ý.HorizonCode_Horizon_È;
        this.Ï­Ï­Ï = gameConfiguration.Ý.Ý;
        this.Ý = gameConfiguration.Ý.Â;
        this.£Â = gameConfiguration.Ø­áŒŠá.Â;
        this.Ø­Âµ = gameConfiguration.HorizonCode_Horizon_È.Â;
        this.ÇªÔ = new DefaultResourcePack(new ResourceIndex(gameConfiguration.Ý.Ý, gameConfiguration.Ý.Ø­áŒŠá).HorizonCode_Horizon_È());
        this.£Ó = ((gameConfiguration.HorizonCode_Horizon_È.Ý == null) ? Proxy.NO_PROXY : gameConfiguration.HorizonCode_Horizon_È.Ý);
        this.áŒŠá€ = new YggdrasilAuthenticationService(gameConfiguration.HorizonCode_Horizon_È.Ý, UUID.randomUUID().toString()).createMinecraftSessionService();
        this.£à = gameConfiguration.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        Minecraft.ˆáŠ.info("Setting user: " + this.£à.Ý());
        Minecraft.ˆáŠ.info("(Session ID is " + this.£à.HorizonCode_Horizon_È() + ")");
        this.ˆÏ = gameConfiguration.Ø­áŒŠá.HorizonCode_Horizon_È;
        this.Ó = ((gameConfiguration.Â.HorizonCode_Horizon_È > 0) ? gameConfiguration.Â.HorizonCode_Horizon_È : 1);
        this.à = ((gameConfiguration.Â.Â > 0) ? gameConfiguration.Â.Â : 1);
        this.£Ï = gameConfiguration.Â.HorizonCode_Horizon_È;
        this.Ø­á = gameConfiguration.Â.Â;
        this.Ï­à = gameConfiguration.Â.Ý;
        this.Ðƒá = £Õ();
        this.ˆÉ = new IntegratedServer(this);
        if (gameConfiguration.Âµá€.HorizonCode_Horizon_È != null) {
            this.£Õ = gameConfiguration.Âµá€.HorizonCode_Horizon_È;
            this.Ï­Ô = gameConfiguration.Âµá€.Â;
        }
        ImageIO.setUseCache(false);
        Bootstrap_2101973421.Ý();
    }
    
    public void HorizonCode_Horizon_È() {
        this.áƒ = true;
        try {
            this.Ø­á();
        }
        catch (Throwable causeIn) {
            final CrashReport horizonCode_Horizon_È = CrashReport.HorizonCode_Horizon_È(causeIn, "Initializing game");
            horizonCode_Horizon_È.HorizonCode_Horizon_È("Initialization");
            this.Â(this.Ý(horizonCode_Horizon_È));
            return;
        }
        try {
            do {
                if (this.ÇŽá€) {
                    if (this.Ï != null) {
                        this.Â(this.Ï);
                        return;
                    }
                }
                try {
                    this.Ðƒá();
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    this.ˆÏ­();
                    this.HorizonCode_Horizon_È(new GuiMemoryErrorScreen());
                    System.gc();
                }
            } while (this.áƒ);
        }
        catch (MinecraftError minecraftError) {}
        catch (ReportedException ex) {
            this.Ý(ex.HorizonCode_Horizon_È());
            this.ˆÏ­();
            Minecraft.ˆáŠ.fatal("Reported exception thrown!", (Throwable)ex);
            this.Â(ex.HorizonCode_Horizon_È());
        }
        catch (Throwable causeThrowable) {
            final CrashReport ý = this.Ý(new CrashReport("Unexpected error", causeThrowable));
            this.ˆÏ­();
            Minecraft.ˆáŠ.fatal("Unreported exception thrown!", causeThrowable);
            this.Â(ý);
        }
        finally {
            this.Ø();
        }
        this.Ø();
    }
    
    private void Ø­á() throws LWJGLException {
        DemoWorldServer.áˆºÏ();
        this.ŠÄ = new GameSettings(this, this.ŒÏ);
        this.¥Ä.add(this.ÇªÔ);
        this.Ï­Ô();
        JFrame.setDefaultLookAndFeelDecorated(true);
        if (this.ŠÄ.Ø­Ñ¢á€ > 0 && this.ŠÄ.Š > 0) {
            this.Ó = this.ŠÄ.Š;
            this.à = this.ŠÄ.Ø­Ñ¢á€;
        }
        EntityRenderer.áˆºÑ¢Õ();
        Minecraft.ˆáŠ.info("LWJGL Version: " + Sys.getVersion());
        this.ˆÐƒØ­à();
        this.£Ó();
        this.£Â();
        OpenGlHelper.HorizonCode_Horizon_È();
        (this.Ñ¢à = new Framebuffer(this.Ó, this.à, true)).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.0f);
        this.ˆÉ();
        this.Û = new ResourcePackRepository(this.Ý, new File(this.ŒÏ, "server-resource-packs"), this.ÇªÔ, this.ˆÓ, this.ŠÄ);
        this.áŒŠáŠ = new SimpleReloadableResourceManager(this.ˆÓ);
        this.ŠÓ = new LanguageManager(this.ˆÓ, this.ŠÄ.Ï­Ó);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.ŠÓ);
        this.Ó();
        this.Ø­áŒŠá = new TextureManager(this.áŒŠáŠ);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá);
        this.Ï­Ï­Ï();
        this.¥Ï = new SkinManager(this.Ø­áŒŠá, new File(this.Ï­Ï­Ï, "skins"), this.áŒŠá€);
        this.ˆÐƒØ­à = new AnvilSaveConverter(new File(this.ŒÏ, "saves"));
        this.£áŒŠá = new SoundHandler(this.áŒŠáŠ, this.ŠÄ);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.£áŒŠá);
        this.áˆº = new MusicTicker(this);
        this.µà = new FontRenderer(this.ŠÄ, new ResourceLocation_1975012498("textures/font/ascii.png"), this.Ø­áŒŠá, false);
        if (this.ŠÄ.Ï­Ó != null) {
            this.µà.HorizonCode_Horizon_È(this.Âµá€());
            this.µà.Â(this.ŠÓ.Â());
        }
        this.ˆà = new FontRenderer(this.ŠÄ, new ResourceLocation_1975012498("textures/font/ascii_sga.png"), this.Ø­áŒŠá, false);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.µà);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.ˆà);
        this.áŒŠáŠ.HorizonCode_Horizon_È(new GrassColorReloadListener());
        this.áŒŠáŠ.HorizonCode_Horizon_È(new FoliageColorReloadListener());
        AchievementList.Ó.HorizonCode_Horizon_È(new IStatStringFormat() {
            private static final String Â = "CL_00000632";
            
            @Override
            public String HorizonCode_Horizon_È(final String p_74535_1_) {
                try {
                    return String.format(p_74535_1_, GameSettings.HorizonCode_Horizon_È(Minecraft.this.ŠÄ.Ï­Ï.áŒŠÆ()));
                }
                catch (Exception var3) {
                    return "Error: " + var3.getLocalizedMessage();
                }
            }
        });
        this.Ñ¢á = new MouseHelper();
        this.HorizonCode_Horizon_È("Pre startup");
        GlStateManager.µÕ();
        GlStateManager.áˆºÑ¢Õ(7425);
        GlStateManager.HorizonCode_Horizon_È(1.0);
        GlStateManager.áˆºÑ¢Õ();
        GlStateManager.Ý(515);
        GlStateManager.Ø­áŒŠá();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.Âµá€(1029);
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        GlStateManager.á(5888);
        this.HorizonCode_Horizon_È("Startup");
        (this.ÇªØ­ = new TextureMap("textures")).HorizonCode_Horizon_È(this.ŠÄ.áŒŠÕ);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(TextureMap.à, this.ÇªØ­);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(TextureMap.à);
        this.ÇªØ­.HorizonCode_Horizon_È(false, this.ŠÄ.áŒŠÕ > 0);
        this.É = new ModelManager(this.ÇªØ­);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.É);
        this.ÇªÓ = new RenderItem(this.Ø­áŒŠá, this.É);
        this.ÂµÈ = new RenderManager(this.Ø­áŒŠá, this.ÇªÓ);
        this.áˆºÏ = new ItemRenderer(this);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.ÇªÓ);
        this.µÕ = new EntityRenderer(this, this.áŒŠáŠ);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.µÕ);
        this.Ðƒáƒ = new BlockRendererDispatcher(this.É.Ý(), this.ŠÄ);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.Ðƒáƒ);
        this.áˆºÑ¢Õ = new RenderGlobal(this);
        this.áŒŠáŠ.HorizonCode_Horizon_È(this.áˆºÑ¢Õ);
        this.Æ = new GuiAchievement(this);
        GlStateManager.Â(0, 0, this.Ó, this.à);
        this.Å = new EffectRenderer(this.áŒŠÆ, this.Ø­áŒŠá);
        this.HorizonCode_Horizon_È("Post startup");
        this.Šáƒ = new GuiIngame(this);
        new Horizon().Ý();
        if (this.£Õ != null) {
            this.Ø();
        }
        else {
            this.HorizonCode_Horizon_È(new GuiMainMenu());
        }
        this.Ø­áŒŠá.Ý(this.Šà);
        this.Šà = null;
        this.Ø­à = new LoadingScreenRenderer(this);
        if (this.ŠÄ.¥Ï && !this.Ï­à) {
            this.ˆà();
        }
        try {
            Display.setVSyncEnabled(this.ŠÄ.ˆà¢);
        }
        catch (OpenGLException ex) {
            this.ŠÄ.ˆà¢ = false;
            this.ŠÄ.Â();
        }
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
    }
    
    private void ˆÉ() {
        this.ˆÓ.HorizonCode_Horizon_È(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.ˆÓ.HorizonCode_Horizon_È(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.ˆÓ.HorizonCode_Horizon_È(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.ˆÓ.HorizonCode_Horizon_È(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.ˆÓ.HorizonCode_Horizon_È(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }
    
    private void Ï­Ï­Ï() {
        try {
            this.ÇŽá = new TwitchStream(this, (Property)Iterables.getFirst((Iterable)this.Ø­Âµ.get((Object)"twitch_access_token"), (Object)null));
        }
        catch (Throwable p_i1006_1_) {
            this.ÇŽá = new NullStream(p_i1006_1_);
            Minecraft.ˆáŠ.error("Couldn't initialize twitch stream");
        }
    }
    
    private void £Â() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("Loading...");
        try {
            Display.create(new PixelFormat().withDepthBits(24));
        }
        catch (LWJGLException ex) {
            Minecraft.ˆáŠ.error("Couldn't set pixel format", (Throwable)ex);
            if (this.Ï­à) {
                this.Œà();
            }
            Display.create();
        }
    }
    
    public ScaledResolution Â() {
        return new ScaledResolution(this, this.Ó, this.à);
    }
    
    private void £Ó() throws LWJGLException {
        if (this.Ï­à) {
            Display.setFullscreen(true);
            final DisplayMode displayMode = Display.getDisplayMode();
            this.Ó = Math.max(1, displayMode.getWidth());
            this.à = Math.max(1, displayMode.getHeight());
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.Ó, this.à));
        }
    }
    
    private void ˆÐƒØ­à() {
        if (Util_1252169911.HorizonCode_Horizon_È() != Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá) {
            InputStream ý = null;
            InputStream ý2 = null;
            try {
                ý = this.ÇªÔ.Ý(new ResourceLocation_1975012498("icons/icon_16x16.png"));
                ý2 = this.ÇªÔ.Ý(new ResourceLocation_1975012498("icons/icon_32x32.png"));
                if (ý != null && ý2 != null) {
                    Display.setIcon(new ByteBuffer[] { this.HorizonCode_Horizon_È(ý), this.HorizonCode_Horizon_È(ý2) });
                }
            }
            catch (IOException ex) {
                Minecraft.ˆáŠ.error("Couldn't set icon", (Throwable)ex);
                return;
            }
            finally {
                IOUtils.closeQuietly(ý);
                IOUtils.closeQuietly(ý2);
            }
            IOUtils.closeQuietly(ý);
            IOUtils.closeQuietly(ý2);
        }
    }
    
    private static boolean £Õ() {
        String[] array;
        for (int length = (array = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" }).length, i = 0; i < length; ++i) {
            final String property = System.getProperty(array[i]);
            if (property != null && property.contains("64")) {
                return true;
            }
        }
        return false;
    }
    
    public Framebuffer Ý() {
        return this.Ñ¢à;
    }
    
    public String Ø­áŒŠá() {
        return this.£Â;
    }
    
    private void Ï­Ô() {
        final Thread thread = new Thread("Timer hack thread") {
            private static final String Â = "CL_00000639";
            
            @Override
            public void run() {
                while (Minecraft.this.áƒ) {
                    try {
                        Thread.sleep(2147483647L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    public void HorizonCode_Horizon_È(final CrashReport ï) {
        this.ÇŽá€ = true;
        this.Ï = ï;
    }
    
    public void Â(final CrashReport crashReport) {
        final File toFile = new File(new File(áŒŠà().ŒÏ, "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        Bootstrap_2101973421.HorizonCode_Horizon_È(crashReport.Ø­áŒŠá());
        if (crashReport.Âµá€() != null) {
            Bootstrap_2101973421.HorizonCode_Horizon_È("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.Âµá€());
            System.exit(-1);
        }
        else if (crashReport.HorizonCode_Horizon_È(toFile)) {
            Bootstrap_2101973421.HorizonCode_Horizon_È("#@!@# Game crashed! Crash report saved to: #@!@# " + toFile.getAbsolutePath());
            System.exit(-1);
        }
        else {
            Bootstrap_2101973421.HorizonCode_Horizon_È("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
    
    public boolean Âµá€() {
        return this.ŠÓ.HorizonCode_Horizon_È() || this.ŠÄ.ŠáŒŠà¢;
    }
    
    public void Ó() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.¥Ä);
        final Iterator<ResourcePackRepository.HorizonCode_Horizon_È> iterator = this.Û.Ý().iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().Ý());
        }
        if (this.Û.Âµá€() != null) {
            arrayList.add(this.Û.Âµá€());
        }
        try {
            this.áŒŠáŠ.HorizonCode_Horizon_È(arrayList);
        }
        catch (RuntimeException ex) {
            Minecraft.ˆáŠ.info("Caught error stitching, removing all assigned resourcepacks", (Throwable)ex);
            arrayList.clear();
            arrayList.addAll(this.¥Ä);
            this.Û.HorizonCode_Horizon_È(Collections.emptyList());
            this.áŒŠáŠ.HorizonCode_Horizon_È(arrayList);
            this.ŠÄ.ÇŽá.clear();
            this.ŠÄ.Â();
        }
        this.ŠÓ.HorizonCode_Horizon_È(arrayList);
        if (this.áˆºÑ¢Õ != null) {
            this.áˆºÑ¢Õ.Ø­áŒŠá();
        }
    }
    
    private ByteBuffer HorizonCode_Horizon_È(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        final int[] rgb = read.getRGB(0, 0, read.getWidth(), read.getHeight(), null, 0, read.getWidth());
        final ByteBuffer allocate = ByteBuffer.allocate(4 * rgb.length);
        final int[] array = rgb;
        for (int length = rgb.length, i = 0; i < length; ++i) {
            final int n = array[i];
            allocate.putInt(n << 8 | (n >> 24 & 0xFF));
        }
        allocate.flip();
        return allocate;
    }
    
    private void Œà() throws LWJGLException {
        final HashSet hashSet = Sets.newHashSet();
        Collections.addAll(hashSet, Display.getAvailableDisplayModes());
        DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
        if (!hashSet.contains(desktopDisplayMode) && Util_1252169911.HorizonCode_Horizon_È() == Util_1252169911.HorizonCode_Horizon_È.Ø­áŒŠá) {
            for (final DisplayMode displayMode : Minecraft.£ÂµÄ) {
                boolean b = true;
                for (final DisplayMode displayMode2 : hashSet) {
                    if (displayMode2.getBitsPerPixel() == 32 && displayMode2.getWidth() == displayMode.getWidth() && displayMode2.getHeight() == displayMode.getHeight()) {
                        b = false;
                        break;
                    }
                }
                if (!b) {
                    for (final DisplayMode displayMode3 : hashSet) {
                        if (displayMode3.getBitsPerPixel() == 32 && displayMode3.getWidth() == displayMode.getWidth() / 2 && displayMode3.getHeight() == displayMode.getHeight() / 2) {
                            desktopDisplayMode = displayMode3;
                            break;
                        }
                    }
                }
            }
        }
        Display.setDisplayMode(desktopDisplayMode);
        this.Ó = desktopDisplayMode.getWidth();
        this.à = desktopDisplayMode.getHeight();
    }
    
    private void HorizonCode_Horizon_È(final TextureManager textureManager) {
        final ScaledResolution scaledResolution = new ScaledResolution(this, this.Ó, this.à);
        final int âµá€ = scaledResolution.Âµá€();
        final Framebuffer framebuffer = new Framebuffer(scaledResolution.HorizonCode_Horizon_È() * âµá€, scaledResolution.Â() * âµá€, true);
        framebuffer.HorizonCode_Horizon_È(false);
        GlStateManager.á(5889);
        GlStateManager.ŒÏ();
        GlStateManager.HorizonCode_Horizon_È(0.0, scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â(), 0.0, 1000.0, 3000.0);
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        GlStateManager.Â(0.0f, 0.0f, -2000.0f);
        GlStateManager.Ó();
        GlStateManager.£á();
        GlStateManager.áŒŠÆ();
        GlStateManager.µÕ();
        InputStream horizonCode_Horizon_È = null;
        Label_0212: {
            try {
                horizonCode_Horizon_È = this.ÇªÔ.HorizonCode_Horizon_È(Minecraft.áŒŠ);
                textureManager.HorizonCode_Horizon_È(this.Šà = textureManager.HorizonCode_Horizon_È("logo", new DynamicTexture(ImageIO.read(horizonCode_Horizon_È))));
            }
            catch (IOException ex) {
                Minecraft.ˆáŠ.error("Unable to load logo: " + Minecraft.áŒŠ, (Throwable)ex);
                break Label_0212;
            }
            finally {
                IOUtils.closeQuietly(horizonCode_Horizon_È);
            }
            IOUtils.closeQuietly(horizonCode_Horizon_È);
        }
        final Tessellator horizonCode_Horizon_È2 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer ý = horizonCode_Horizon_È2.Ý();
        ý.Â();
        ý.Ý(16777215);
        ý.HorizonCode_Horizon_È(0.0, this.à, 0.0, 0.0, 0.0);
        ý.HorizonCode_Horizon_È(this.Ó, this.à, 0.0, 0.0, 0.0);
        ý.HorizonCode_Horizon_È(this.Ó, 0.0, 0.0, 0.0, 0.0);
        ý.HorizonCode_Horizon_È(0.0, 0.0, 0.0, 0.0, 0.0);
        horizonCode_Horizon_È2.Â();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        ý.Ý(16777215);
        final int n = 256;
        final int n2 = 256;
        this.HorizonCode_Horizon_È((scaledResolution.HorizonCode_Horizon_È() - n) / 2, (scaledResolution.Â() - n2) / 2, 0, 0, n, n2);
        GlStateManager.Ó();
        GlStateManager.£á();
        framebuffer.Âµá€();
        framebuffer.Ý(scaledResolution.HorizonCode_Horizon_È() * âµá€, scaledResolution.Â() * âµá€);
        GlStateManager.Ø­áŒŠá();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        this.áŒŠÆ();
    }
    
    public void HorizonCode_Horizon_È(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final WorldRenderer ý = Tessellator.HorizonCode_Horizon_È().Ý();
        ý.Â();
        ý.HorizonCode_Horizon_È(n + 0, n2 + n6, 0.0, (n3 + 0) * n7, (n4 + n6) * n8);
        ý.HorizonCode_Horizon_È(n + n5, n2 + n6, 0.0, (n3 + n5) * n7, (n4 + n6) * n8);
        ý.HorizonCode_Horizon_È(n + n5, n2 + 0, 0.0, (n3 + n5) * n7, (n4 + 0) * n8);
        ý.HorizonCode_Horizon_È(n + 0, n2 + 0, 0.0, (n3 + 0) * n7, (n4 + 0) * n8);
        Tessellator.HorizonCode_Horizon_È().Â();
    }
    
    public ISaveFormat à() {
        return this.ˆÐƒØ­à;
    }
    
    public void HorizonCode_Horizon_È(GuiScreen ¥æ) {
        if (this.¥Æ != null) {
            this.¥Æ.q_();
        }
        if (¥æ == null && this.áŒŠÆ == null) {
            ¥æ = new GuiMainMenu();
        }
        else if (¥æ == null && this.á.Ï­Ä() <= 0.0f) {
            ¥æ = new GuiGameOver();
        }
        if (¥æ instanceof GuiMainMenu) {
            this.ŠÄ.µÐƒÓ = false;
            this.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È();
        }
        if ((this.¥Æ = ¥æ) != null) {
            this.£à();
            final ScaledResolution scaledResolution = new ScaledResolution(this, this.Ó, this.à);
            ¥æ.HorizonCode_Horizon_È(this, scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â());
            this.Ï­Ðƒà = false;
        }
        else {
            this.£áŒŠá.Âµá€();
            this.Å();
        }
    }
    
    private void HorizonCode_Horizon_È(final String s) {
        if (this.áˆºáˆºÈ) {
            final int glGetError = GL11.glGetError();
            if (glGetError != 0) {
                final String gluErrorString = GLU.gluErrorString(glGetError);
                Minecraft.ˆáŠ.error("########## GL ERROR ##########");
                Minecraft.ˆáŠ.error("@ " + s);
                Minecraft.ˆáŠ.error(String.valueOf(glGetError) + ": " + gluErrorString);
            }
        }
    }
    
    public void Ø() {
        Horizon.à¢.áˆºÏ.Ý();
        Horizon.à¢.Ñ¢Â.HorizonCode_Horizon_È();
        if (Horizon.ÇŽÕ) {
            Horizon.à¢.£ÂµÄ.Â();
        }
        try {
            this.ÇŽá.Ó();
            Minecraft.ˆáŠ.info("Stopping!");
            try {
                this.HorizonCode_Horizon_È((WorldClient)null);
            }
            catch (Throwable t) {}
            this.£áŒŠá.Ø­áŒŠá();
        }
        finally {
            Display.destroy();
            if (!this.ÇŽá€) {
                System.exit(0);
            }
        }
        Display.destroy();
        if (!this.ÇŽá€) {
            System.exit(0);
        }
        System.gc();
    }
    
    private void Ðƒá() throws IOException {
        this.ÇŽÕ.HorizonCode_Horizon_È("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.£á();
        }
        if (this.ˆáƒ && this.áŒŠÆ != null) {
            final float ý = this.Ø.Ý;
            this.Ø.HorizonCode_Horizon_È();
            this.Ø.Ý = ý;
        }
        else {
            this.Ø.HorizonCode_Horizon_È();
        }
        this.ÇŽÕ.HorizonCode_Horizon_È("scheduledExecutables");
        final Queue ˆà¢ = this.ˆà¢;
        synchronized (this.ˆà¢) {
            while (!this.ˆà¢.isEmpty()) {
                this.ˆà¢.poll().run();
            }
        }
        // monitorexit(this.\u02c6\u00e0¢)
        this.ÇŽÕ.Â();
        final long nanoTime = System.nanoTime();
        this.ÇŽÕ.HorizonCode_Horizon_È("tick");
        for (int i = 0; i < this.Ø.Â; ++i) {
            this.¥Æ();
        }
        this.ÇŽÕ.Ý("preRenderErrors");
        final long n = System.nanoTime() - nanoTime;
        this.HorizonCode_Horizon_È("Pre render");
        this.ÇŽÕ.Ý("sound");
        this.£áŒŠá.HorizonCode_Horizon_È(this.á, this.Ø.Ý);
        this.ÇŽÕ.Â();
        this.ÇŽÕ.HorizonCode_Horizon_È("render");
        GlStateManager.Çªà¢();
        GlStateManager.ÂµÈ(16640);
        this.Ñ¢à.HorizonCode_Horizon_È(true);
        this.ÇŽÕ.HorizonCode_Horizon_È("display");
        GlStateManager.µÕ();
        if (this.á != null && this.á.£Ï()) {
            this.ŠÄ.µÏ = 0;
        }
        this.ÇŽÕ.Â();
        if (!this.Ï­Ðƒà) {
            this.ÇŽÕ.Ý("gameRenderer");
            this.µÕ.Ý(this.Ø.Ý);
            this.ÇŽÕ.Â();
        }
        this.ÇŽÕ.Â();
        if (this.ŠÄ.µÐƒÓ && this.ŠÄ.¥áŒŠà && !this.ŠÄ.µ) {
            if (!this.ÇŽÕ.HorizonCode_Horizon_È) {
                this.ÇŽÕ.HorizonCode_Horizon_È();
            }
            this.ÇŽÕ.HorizonCode_Horizon_È = true;
            this.HorizonCode_Horizon_È(n);
        }
        else {
            this.ÇŽÕ.HorizonCode_Horizon_È = false;
            this.È = System.nanoTime();
        }
        this.Æ.HorizonCode_Horizon_È();
        this.Ñ¢à.Âµá€();
        GlStateManager.Ê();
        GlStateManager.Çªà¢();
        this.Ñ¢à.Ý(this.Ó, this.à);
        GlStateManager.Ê();
        GlStateManager.Çªà¢();
        this.µÕ.Ø­áŒŠá(this.Ø.Ý);
        GlStateManager.Ê();
        this.ÇŽÕ.HorizonCode_Horizon_È("root");
        this.áŒŠÆ();
        Thread.yield();
        this.ÇŽÕ.HorizonCode_Horizon_È("stream");
        this.ÇŽÕ.HorizonCode_Horizon_È("update");
        this.ÇŽá.à();
        this.ÇŽÕ.Ý("submit");
        this.ÇŽá.Ø();
        this.ÇŽÕ.Â();
        this.ÇŽÕ.Â();
        this.HorizonCode_Horizon_È("Post render");
        ++this.Ç;
        this.ˆáƒ = (this.ÇŽÉ() && this.¥Æ != null && this.¥Æ.Ø­áŒŠá() && !this.ˆÉ.ÇŽá());
        while (áƒ() >= this.Âµà + 1000L) {
            Minecraft.Çªà¢ = this.Ç;
            this.á€ = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", Minecraft.Çªà¢, RenderChunk.HorizonCode_Horizon_È, (RenderChunk.HorizonCode_Horizon_È != 1) ? "s" : "", (this.ŠÄ.à == GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€()) ? "inf" : this.ŠÄ.à, this.ŠÄ.ˆà¢ ? " vsync" : "", this.ŠÄ.Û ? "" : " fast", this.ŠÄ.ÇªÔ ? " clouds" : "", OpenGlHelper.Ó() ? " vbo" : "");
            RenderChunk.HorizonCode_Horizon_È = 0;
            this.Âµà += 1000L;
            this.Ç = 0;
            this.Ô.Â();
            if (!this.Ô.Ø­áŒŠá()) {
                this.Ô.HorizonCode_Horizon_È();
            }
        }
        if (this.á()) {
            this.ÇŽÕ.HorizonCode_Horizon_È("fpslimit_wait");
            Display.sync(this.ÂµÈ());
            this.ÇŽÕ.Â();
        }
        this.ÇŽÕ.Â();
    }
    
    public void áŒŠÆ() {
        this.ÇŽÕ.HorizonCode_Horizon_È("display_update");
        Display.update();
        this.ÇŽÕ.Â();
        this.áˆºÑ¢Õ();
    }
    
    protected void áˆºÑ¢Õ() {
        if (!this.Ï­à && Display.wasResized()) {
            final int ó = this.Ó;
            final int à = this.à;
            this.Ó = Display.getWidth();
            this.à = Display.getHeight();
            if (this.Ó != ó || this.à != à) {
                if (this.Ó <= 0) {
                    this.Ó = 1;
                }
                if (this.à <= 0) {
                    this.à = 1;
                }
                this.HorizonCode_Horizon_È(this.Ó, this.à);
            }
        }
    }
    
    public int ÂµÈ() {
        return (this.áŒŠÆ == null && this.¥Æ != null) ? 30 : this.ŠÄ.à;
    }
    
    public boolean á() {
        return this.ÂµÈ() < GameSettings.HorizonCode_Horizon_È.áŒŠÆ.Âµá€();
    }
    
    public void ˆÏ­() {
        try {
            Minecraft.Â = new byte[0];
            this.áˆºÑ¢Õ.áŒŠÆ();
        }
        catch (Throwable t) {}
        try {
            System.gc();
            this.HorizonCode_Horizon_È((WorldClient)null);
        }
        catch (Throwable t2) {}
        System.gc();
    }
    
    private void Â(int n) {
        final List â = this.ÇŽÕ.Â(this.Ðƒà);
        if (â != null && !â.isEmpty()) {
            final Profiler.HorizonCode_Horizon_È horizonCode_Horizon_È = â.remove(0);
            if (n == 0) {
                if (horizonCode_Horizon_È.Ý.length() > 0) {
                    final int lastIndex = this.Ðƒà.lastIndexOf(".");
                    if (lastIndex >= 0) {
                        this.Ðƒà = this.Ðƒà.substring(0, lastIndex);
                    }
                }
            }
            else if (--n < â.size() && !â.get(n).Ý.equals("unspecified")) {
                if (this.Ðƒà.length() > 0) {
                    this.Ðƒà = String.valueOf(this.Ðƒà) + ".";
                }
                this.Ðƒà = String.valueOf(this.Ðƒà) + â.get(n).Ý;
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final long n) {
        if (this.ÇŽÕ.HorizonCode_Horizon_È) {
            final List â = this.ÇŽÕ.Â(this.Ðƒà);
            final Profiler.HorizonCode_Horizon_È horizonCode_Horizon_È = â.remove(0);
            GlStateManager.ÂµÈ(256);
            GlStateManager.á(5889);
            GlStateManager.à();
            GlStateManager.ŒÏ();
            GlStateManager.HorizonCode_Horizon_È(0.0, this.Ó, this.à, 0.0, 1000.0, 3000.0);
            GlStateManager.á(5888);
            GlStateManager.ŒÏ();
            GlStateManager.Â(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.Æ();
            final Tessellator horizonCode_Horizon_È2 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer ý = horizonCode_Horizon_È2.Ý();
            final int n2 = 160;
            final int n3 = this.Ó - n2 - 10;
            final int n4 = this.à - n2 * 2;
            GlStateManager.á();
            ý.Â();
            ý.HorizonCode_Horizon_È(0, 200);
            ý.Â(n3 - n2 * 1.1f, n4 - n2 * 0.6f - 16.0f, 0.0);
            ý.Â(n3 - n2 * 1.1f, n4 + n2 * 2, 0.0);
            ý.Â(n3 + n2 * 1.1f, n4 + n2 * 2, 0.0);
            ý.Â(n3 + n2 * 1.1f, n4 - n2 * 0.6f - 16.0f, 0.0);
            horizonCode_Horizon_È2.Â();
            GlStateManager.ÂµÈ();
            double n5 = 0.0;
            for (int i = 0; i < â.size(); ++i) {
                final Profiler.HorizonCode_Horizon_È horizonCode_Horizon_È3 = â.get(i);
                final int n6 = MathHelper.Ý(horizonCode_Horizon_È3.HorizonCode_Horizon_È / 4.0) + 1;
                ý.HorizonCode_Horizon_È(6);
                ý.Ý(horizonCode_Horizon_È3.HorizonCode_Horizon_È());
                ý.Â(n3, n4, 0.0);
                for (int j = n6; j >= 0; --j) {
                    final float n7 = (float)((n5 + horizonCode_Horizon_È3.HorizonCode_Horizon_È * j / n6) * 3.141592653589793 * 2.0 / 100.0);
                    ý.Â(n3 + MathHelper.HorizonCode_Horizon_È(n7) * n2, n4 - MathHelper.Â(n7) * n2 * 0.5f, 0.0);
                }
                horizonCode_Horizon_È2.Â();
                ý.HorizonCode_Horizon_È(5);
                ý.Ý((horizonCode_Horizon_È3.HorizonCode_Horizon_È() & 0xFEFEFE) >> 1);
                for (int k = n6; k >= 0; --k) {
                    final float n8 = (float)((n5 + horizonCode_Horizon_È3.HorizonCode_Horizon_È * k / n6) * 3.141592653589793 * 2.0 / 100.0);
                    final float n9 = MathHelper.HorizonCode_Horizon_È(n8) * n2;
                    final float n10 = MathHelper.Â(n8) * n2 * 0.5f;
                    ý.Â(n3 + n9, n4 - n10, 0.0);
                    ý.Â(n3 + n9, n4 - n10 + 10.0f, 0.0);
                }
                horizonCode_Horizon_È2.Â();
                n5 += horizonCode_Horizon_È3.HorizonCode_Horizon_È;
            }
            final DecimalFormat decimalFormat = new DecimalFormat("##0.00");
            GlStateManager.µÕ();
            String string = "";
            if (!horizonCode_Horizon_È.Ý.equals("unspecified")) {
                string = String.valueOf(string) + "[0] ";
            }
            String p_175063_1_;
            if (horizonCode_Horizon_È.Ý.length() == 0) {
                p_175063_1_ = String.valueOf(string) + "ROOT ";
            }
            else {
                p_175063_1_ = String.valueOf(string) + horizonCode_Horizon_È.Ý + " ";
            }
            final int n11 = 16777215;
            this.µà.HorizonCode_Horizon_È(p_175063_1_, n3 - n2, (float)(n4 - n2 / 2 - 16), n11);
            final FontRenderer µà = this.µà;
            final String string2 = String.valueOf(decimalFormat.format(horizonCode_Horizon_È.Â)) + "%";
            µà.HorizonCode_Horizon_È(string2, n3 + n2 - this.µà.HorizonCode_Horizon_È(string2), (float)(n4 - n2 / 2 - 16), n11);
            for (int l = 0; l < â.size(); ++l) {
                final Profiler.HorizonCode_Horizon_È horizonCode_Horizon_È4 = â.get(l);
                final String s = "";
                String s2;
                if (horizonCode_Horizon_È4.Ý.equals("unspecified")) {
                    s2 = String.valueOf(s) + "[?] ";
                }
                else {
                    s2 = String.valueOf(s) + "[" + (l + 1) + "] ";
                }
                this.µà.HorizonCode_Horizon_È(String.valueOf(s2) + horizonCode_Horizon_È4.Ý, n3 - n2, (float)(n4 + n2 / 2 + l * 8 + 20), horizonCode_Horizon_È4.HorizonCode_Horizon_È());
                final FontRenderer µà2 = this.µà;
                final String string3 = String.valueOf(decimalFormat.format(horizonCode_Horizon_È4.HorizonCode_Horizon_È)) + "%";
                µà2.HorizonCode_Horizon_È(string3, n3 + n2 - 50 - this.µà.HorizonCode_Horizon_È(string3), (float)(n4 + n2 / 2 + l * 8 + 20), horizonCode_Horizon_È4.HorizonCode_Horizon_È());
                final FontRenderer µà3 = this.µà;
                final String string4 = String.valueOf(decimalFormat.format(horizonCode_Horizon_È4.Â)) + "%";
                µà3.HorizonCode_Horizon_È(string4, n3 + n2 - this.µà.HorizonCode_Horizon_È(string4), (float)(n4 + n2 / 2 + l * 8 + 20), horizonCode_Horizon_È4.HorizonCode_Horizon_È());
            }
        }
    }
    
    public void £á() {
        this.áƒ = false;
    }
    
    public void Å() {
        if (Display.isActive() && !this.ÇŽÉ) {
            this.ÇŽÉ = true;
            this.Ñ¢á.HorizonCode_Horizon_È();
            this.HorizonCode_Horizon_È((GuiScreen)null);
            this.Œ = 10000;
        }
    }
    
    public void £à() {
        if (this.ÇŽÉ) {
            KeyBinding.HorizonCode_Horizon_È();
            this.ÇŽÉ = false;
            this.Ñ¢á.Â();
        }
    }
    
    public void µà() {
        if (this.¥Æ == null) {
            this.HorizonCode_Horizon_È(new GuiIngameMenu());
            if (this.ÇŽÉ() && !this.ˆÉ.ÇŽá()) {
                this.£áŒŠá.Â();
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final boolean b) {
        if (!b) {
            this.Œ = 0;
        }
        if (this.Œ <= 0 && !this.á.Ñ¢Ó()) {
            if (b && this.áŒŠà != null && this.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
                final BlockPos horizonCode_Horizon_È = this.áŒŠà.HorizonCode_Horizon_È();
                if (this.áŒŠÆ.Â(horizonCode_Horizon_È).Ý().Ó() != Material.HorizonCode_Horizon_È && this.Âµá€.Ý(horizonCode_Horizon_È, this.áŒŠà.Â)) {
                    this.Å.HorizonCode_Horizon_È(horizonCode_Horizon_È, this.áŒŠà.Â);
                    this.á.b_();
                }
            }
            else {
                this.Âµá€.Ý();
            }
        }
    }
    
    private void ˆÏ() {
        if (this.Œ <= 0) {
            this.á.b_();
            if (this.áŒŠà == null) {
                Minecraft.ˆáŠ.error("Null returned as 'hitResult', this shouldn't happen!");
                if (this.Âµá€.à()) {
                    this.Œ = 10;
                }
            }
            else {
                switch (Minecraft.HorizonCode_Horizon_È.HorizonCode_Horizon_È[this.áŒŠà.HorizonCode_Horizon_È.ordinal()]) {
                    case 1: {
                        this.Âµá€.HorizonCode_Horizon_È(this.á, this.áŒŠà.Ø­áŒŠá);
                        return;
                    }
                    case 2: {
                        final BlockPos horizonCode_Horizon_È = this.áŒŠà.HorizonCode_Horizon_È();
                        if (this.áŒŠÆ.Â(horizonCode_Horizon_È).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                            this.Âµá€.Â(horizonCode_Horizon_È, this.áŒŠà.Â);
                            return;
                        }
                        break;
                    }
                }
                if (this.Âµá€.à()) {
                    this.Œ = 10;
                }
            }
        }
    }
    
    private void áˆºÇŽØ() {
        Minecraft.Ê = 4;
        boolean b = true;
        final ItemStack ø­áŒŠá = this.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (this.áŒŠà == null) {
            Minecraft.ˆáŠ.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        else {
            switch (Minecraft.HorizonCode_Horizon_È.HorizonCode_Horizon_È[this.áŒŠà.HorizonCode_Horizon_È.ordinal()]) {
                case 1: {
                    if (this.Âµá€.HorizonCode_Horizon_È(this.á, this.áŒŠà.Ø­áŒŠá, this.áŒŠà)) {
                        b = false;
                        break;
                    }
                    if (this.Âµá€.Â(this.á, this.áŒŠà.Ø­áŒŠá)) {
                        b = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    final BlockPos horizonCode_Horizon_È = this.áŒŠà.HorizonCode_Horizon_È();
                    if (this.áŒŠÆ.Â(horizonCode_Horizon_È).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                        break;
                    }
                    final int n = (ø­áŒŠá != null) ? ø­áŒŠá.Â : 0;
                    if (this.Âµá€.HorizonCode_Horizon_È(this.á, this.áŒŠÆ, ø­áŒŠá, horizonCode_Horizon_È, this.áŒŠà.Â, this.áŒŠà.Ý)) {
                        b = false;
                        this.á.b_();
                    }
                    if (ø­áŒŠá == null) {
                        return;
                    }
                    if (ø­áŒŠá.Â == 0) {
                        this.á.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.á.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
                        break;
                    }
                    if (ø­áŒŠá.Â != n || this.Âµá€.Ø()) {
                        this.µÕ.Ý.Â();
                        break;
                    }
                    break;
                }
            }
        }
        if (b) {
            final ItemStack ø­áŒŠá2 = this.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
            if (ø­áŒŠá2 != null && this.Âµá€.HorizonCode_Horizon_È(this.á, this.áŒŠÆ, ø­áŒŠá2)) {
                this.µÕ.Ý.Ý();
            }
        }
    }
    
    public void ˆà() {
        try {
            this.Ï­à = !this.Ï­à;
            this.ŠÄ.¥Ï = this.Ï­à;
            if (this.Ï­à) {
                this.Œà();
                this.Ó = Display.getDisplayMode().getWidth();
                this.à = Display.getDisplayMode().getHeight();
                if (this.Ó <= 0) {
                    this.Ó = 1;
                }
                if (this.à <= 0) {
                    this.à = 1;
                }
            }
            else {
                Display.setDisplayMode(new DisplayMode(this.£Ï, this.Ø­á));
                this.Ó = this.£Ï;
                this.à = this.Ø­á;
                if (this.Ó <= 0) {
                    this.Ó = 1;
                }
                if (this.à <= 0) {
                    this.à = 1;
                }
            }
            if (this.¥Æ != null) {
                this.HorizonCode_Horizon_È(this.Ó, this.à);
            }
            else {
                this.ÇªÂµÕ();
            }
            Display.setFullscreen(this.Ï­à);
            Display.setVSyncEnabled(this.ŠÄ.ˆà¢);
            this.áŒŠÆ();
        }
        catch (Exception ex) {
            Minecraft.ˆáŠ.error("Couldn't toggle fullscreen", (Throwable)ex);
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_i46324_2_, final int p_i46324_3_) {
        this.Ó = Math.max(1, p_i46324_2_);
        this.à = Math.max(1, p_i46324_3_);
        if (this.¥Æ != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(this, p_i46324_2_, p_i46324_3_);
            this.¥Æ.Â(this, scaledResolution.HorizonCode_Horizon_È(), scaledResolution.Â());
        }
        this.Ø­à = new LoadingScreenRenderer(this);
        this.ÇªÂµÕ();
    }
    
    private void ÇªÂµÕ() {
        this.Ñ¢à.HorizonCode_Horizon_È(this.Ó, this.à);
        if (this.µÕ != null) {
            this.µÕ.HorizonCode_Horizon_È(this.Ó, this.à);
        }
    }
    
    public void ¥Æ() throws IOException {
        if (Minecraft.Ê > 0) {
            --Minecraft.Ê;
        }
        this.ÇŽÕ.HorizonCode_Horizon_È("gui");
        if (!this.ˆáƒ) {
            this.Šáƒ.Ý();
        }
        this.ÇŽÕ.Â();
        this.µÕ.HorizonCode_Horizon_È(1.0f);
        this.ÇŽÕ.HorizonCode_Horizon_È("gameMode");
        if (!this.ˆáƒ && this.áŒŠÆ != null) {
            this.Âµá€.Âµá€();
        }
        new EventTick().Â();
        this.ÇŽÕ.Ý("textures");
        if (!this.ˆáƒ) {
            this.Ø­áŒŠá.Â();
        }
        if (this.¥Æ == null && this.á != null) {
            if (this.á.Ï­Ä() <= 0.0f) {
                this.HorizonCode_Horizon_È((GuiScreen)null);
            }
            else if (this.á.Ï­Ó() && this.áŒŠÆ != null) {
                this.HorizonCode_Horizon_È(new GuiSleepMP());
            }
        }
        else if (this.¥Æ != null && this.¥Æ instanceof GuiSleepMP && !this.á.Ï­Ó()) {
            this.HorizonCode_Horizon_È((GuiScreen)null);
        }
        if (this.¥Æ != null) {
            this.Œ = 10000;
        }
        if (this.¥Æ != null) {
            try {
                this.¥Æ.á();
            }
            catch (Throwable causeIn) {
                final CrashReport horizonCode_Horizon_È = CrashReport.HorizonCode_Horizon_È(causeIn, "Updating screen events");
                horizonCode_Horizon_È.HorizonCode_Horizon_È("Affected screen").HorizonCode_Horizon_È("Screen name", new Callable() {
                    private static final String Â = "CL_00000640";
                    
                    public String HorizonCode_Horizon_È() {
                        return Minecraft.this.¥Æ.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(horizonCode_Horizon_È);
            }
            if (this.¥Æ != null) {
                try {
                    this.¥Æ.Ý();
                }
                catch (Throwable causeIn2) {
                    final CrashReport horizonCode_Horizon_È2 = CrashReport.HorizonCode_Horizon_È(causeIn2, "Ticking screen");
                    horizonCode_Horizon_È2.HorizonCode_Horizon_È("Affected screen").HorizonCode_Horizon_È("Screen name", new Callable() {
                        private static final String Â = "CL_00000642";
                        
                        public String HorizonCode_Horizon_È() {
                            return Minecraft.this.¥Æ.getClass().getCanonicalName();
                        }
                    });
                    throw new ReportedException(horizonCode_Horizon_È2);
                }
            }
        }
        if (this.¥Æ == null || this.¥Æ.ÇŽÕ) {
            this.ÇŽÕ.Ý("mouse");
            while (Mouse.next()) {
                new MouseClicked(Mouse.getEventButton()).Â();
                final int eventButton = Mouse.getEventButton();
                KeyBinding.HorizonCode_Horizon_È(eventButton - 100, Mouse.getEventButtonState());
                if (Mouse.getEventButtonState()) {
                    if (this.á.Ø­áŒŠá() && eventButton == 2) {
                        this.Šáƒ.à().Â();
                    }
                    else {
                        KeyBinding.HorizonCode_Horizon_È(eventButton - 100);
                    }
                }
                if (áƒ() - this.ˆá <= 200L) {
                    final int eventDWheel = Mouse.getEventDWheel();
                    if (eventDWheel != 0) {
                        if (this.á.Ø­áŒŠá()) {
                            final int n = (eventDWheel < 0) ? -1 : 1;
                            if (this.Šáƒ.à().HorizonCode_Horizon_È()) {
                                this.Šáƒ.à().Â(-n);
                            }
                            else {
                                this.á.áˆºáˆºáŠ.HorizonCode_Horizon_È(MathHelper.HorizonCode_Horizon_È(this.á.áˆºáˆºáŠ.HorizonCode_Horizon_È() + n * 0.005f, 0.0f, 0.2f));
                            }
                        }
                        else {
                            this.á.Ø­Ñ¢Ï­Ø­áˆº.Â(eventDWheel);
                        }
                    }
                    if (this.¥Æ == null) {
                        if (this.ÇŽÉ || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        this.Å();
                    }
                    else {
                        if (this.¥Æ == null) {
                            continue;
                        }
                        this.¥Æ.n_();
                    }
                }
            }
            if (this.Œ > 0) {
                --this.Œ;
            }
            this.ÇŽÕ.Ý("keyboard");
            while (Keyboard.next()) {
                final int n2 = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 'Ā') : Keyboard.getEventKey();
                KeyBinding.HorizonCode_Horizon_È(n2, Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    KeyBinding.HorizonCode_Horizon_È(n2);
                }
                if (this.áŒŠÏ > 0L) {
                    if (áƒ() - this.áŒŠÏ >= 6000L) {
                        throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                    }
                    if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                        this.áŒŠÏ = -1L;
                    }
                }
                else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                    this.áŒŠÏ = áƒ();
                }
                this.Ñ¢Â();
                if (Keyboard.getEventKeyState()) {
                    if (n2 == 62 && this.µÕ != null) {
                        this.µÕ.Â();
                    }
                    if (this.¥Æ != null) {
                        this.¥Æ.ˆÏ­();
                    }
                    else {
                        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                        for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
                            if (n2 == mod.Âµá€()) {
                                mod.ˆÏ­();
                            }
                        }
                        if (n2 == 1) {
                            this.µà();
                        }
                        if (n2 == 32 && Keyboard.isKeyDown(61) && this.Šáƒ != null) {
                            this.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È();
                        }
                        if (n2 == 31 && Keyboard.isKeyDown(61)) {
                            this.Ó();
                        }
                        if (n2 != 17 || Keyboard.isKeyDown(61)) {}
                        if (n2 != 18 || Keyboard.isKeyDown(61)) {}
                        if (n2 != 47 || Keyboard.isKeyDown(61)) {}
                        if (n2 != 38 || Keyboard.isKeyDown(61)) {}
                        if (n2 != 22 || Keyboard.isKeyDown(61)) {}
                        if (n2 == 20 && Keyboard.isKeyDown(61)) {
                            this.Ó();
                        }
                        if (n2 == 33 && Keyboard.isKeyDown(61)) {
                            this.ŠÄ.HorizonCode_Horizon_È(GameSettings.HorizonCode_Horizon_È.Ó, (Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54)) ? -1 : 1);
                        }
                        if (n2 == 30 && Keyboard.isKeyDown(61)) {
                            this.áˆºÑ¢Õ.Ø­áŒŠá();
                        }
                        if (n2 == 35 && Keyboard.isKeyDown(61)) {
                            this.ŠÄ.¥É = !this.ŠÄ.¥É;
                            this.ŠÄ.Â();
                        }
                        if (n2 == 48 && Keyboard.isKeyDown(61)) {
                            this.ÂµÈ.Â(!this.ÂµÈ.Â());
                        }
                        if (n2 == 25 && Keyboard.isKeyDown(61)) {
                            this.ŠÄ.£ÇªÓ = !this.ŠÄ.£ÇªÓ;
                            this.ŠÄ.Â();
                        }
                        if (n2 == 59) {
                            this.ŠÄ.µ = !this.ŠÄ.µ;
                        }
                        if (n2 == 61) {
                            this.ŠÄ.µÐƒÓ = !this.ŠÄ.µÐƒÓ;
                            this.ŠÄ.¥áŒŠà = GuiScreen.£à();
                        }
                        if (this.ŠÄ.ÂµáˆºÂ.Ó()) {
                            if (Keyboard.isKeyDown(62)) {
                                final GameSettings šä = this.ŠÄ;
                                šä.µÏ += 2;
                            }
                            else {
                                final GameSettings šä2 = this.ŠÄ;
                                ++šä2.µÏ;
                            }
                            if (this.ŠÄ.µÏ > 2) {
                                this.ŠÄ.µÏ = 0;
                            }
                            if (this.ŠÄ.µÏ == 0) {
                                this.µÕ.HorizonCode_Horizon_È(this.ÇŽá€());
                            }
                            else if (this.ŠÄ.µÏ == 1) {
                                this.µÕ.HorizonCode_Horizon_È((Entity)null);
                            }
                        }
                        if (this.ŠÄ.¥Âµá€.Ó()) {
                            this.ŠÄ.áŒŠÈ = !this.ŠÄ.áŒŠÈ;
                        }
                    }
                    if (!this.ŠÄ.µÐƒÓ || !this.ŠÄ.¥áŒŠà) {
                        continue;
                    }
                    if (n2 == 11) {
                        this.Â(0);
                    }
                    for (int i = 0; i < 9; ++i) {
                        if (n2 == 2 + i) {
                            this.Â(i + 1);
                        }
                    }
                }
            }
            for (int j = 0; j < 9; ++j) {
                if (this.ŠÄ.áŒŠÉ[j].Ó()) {
                    if (this.á.Ø­áŒŠá()) {
                        this.Šáƒ.à().HorizonCode_Horizon_È(j);
                    }
                    else {
                        this.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = j;
                    }
                }
            }
            final boolean b = this.ŠÄ.Ñ¢à != EntityPlayer.HorizonCode_Horizon_È.Ý;
            while (this.ŠÄ.Ï­Ï.Ó()) {
                if (this.Âµá€.áˆºÑ¢Õ()) {
                    this.á.Šáƒ();
                }
                else {
                    this.µÕ().HorizonCode_Horizon_È(new C16PacketClientStatus(C16PacketClientStatus.HorizonCode_Horizon_È.Ý));
                    this.HorizonCode_Horizon_È(new GuiInventory(this.á));
                }
            }
            while (this.ŠÄ.ˆÐƒØ.Ó()) {
                if (!this.á.Ø­áŒŠá()) {
                    this.á.HorizonCode_Horizon_È(GuiScreen.Å());
                }
            }
            while (this.ŠÄ.Œá.Ó() && b) {
                this.HorizonCode_Horizon_È(new GuiChat());
            }
            if (this.¥Æ == null && this.ŠÄ.Ñ¢ÇŽÏ.Ó() && b) {
                this.HorizonCode_Horizon_È(new GuiChat("/"));
            }
            if (this.á.Ñ¢Ó()) {
                if (!this.ŠÄ.ŠØ.Ø­áŒŠá()) {
                    this.Âµá€.Ý(this.á);
                }
                while (this.ŠÄ.Çªà.Ó()) {}
                while (this.ŠÄ.ŠØ.Ó()) {}
                while (this.ŠÄ.¥Å.Ó()) {}
            }
            else {
                while (this.ŠÄ.Çªà.Ó()) {
                    this.ˆÏ();
                }
                while (this.ŠÄ.ŠØ.Ó()) {
                    this.áˆºÇŽØ();
                }
                while (this.ŠÄ.¥Å.Ó()) {
                    this.áŒŠÏ();
                }
            }
            if (this.ŠÄ.ŠØ.Ø­áŒŠá() && Minecraft.Ê == 0 && !this.á.Ñ¢Ó()) {
                this.áˆºÇŽØ();
            }
            this.HorizonCode_Horizon_È(this.¥Æ == null && this.ŠÄ.Çªà.Ø­áŒŠá() && this.ÇŽÉ);
        }
        if (this.áŒŠÆ != null) {
            if (this.á != null) {
                ++this.Œà;
                if (this.Œà == 30) {
                    this.Œà = 0;
                    this.áŒŠÆ.Ø(this.á);
                }
            }
            this.ÇŽÕ.Ý("gameRenderer");
            if (!this.ˆáƒ) {
                this.µÕ.Ø­áŒŠá();
            }
            this.ÇŽÕ.Ý("levelRenderer");
            if (!this.ˆáƒ) {
                this.áˆºÑ¢Õ.Ø();
            }
            this.ÇŽÕ.Ý("level");
            if (!this.ˆáƒ) {
                if (this.áŒŠÆ.Âµà() > 0) {
                    this.áŒŠÆ.Ø­áŒŠá(this.áŒŠÆ.Âµà() - 1);
                }
                this.áŒŠÆ.£á();
            }
        }
        if (!this.ˆáƒ) {
            this.áˆº.HorizonCode_Horizon_È();
            this.£áŒŠá.HorizonCode_Horizon_È();
        }
        if (this.áŒŠÆ != null) {
            if (!this.ˆáƒ) {
                this.áŒŠÆ.HorizonCode_Horizon_È(this.áŒŠÆ.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È, true);
                try {
                    this.áŒŠÆ.r_();
                }
                catch (Throwable causeIn3) {
                    final CrashReport horizonCode_Horizon_È3 = CrashReport.HorizonCode_Horizon_È(causeIn3, "Exception in world tick");
                    if (this.áŒŠÆ == null) {
                        horizonCode_Horizon_È3.HorizonCode_Horizon_È("Affected level").HorizonCode_Horizon_È("Problem", "Level is null!");
                    }
                    else {
                        this.áŒŠÆ.HorizonCode_Horizon_È(horizonCode_Horizon_È3);
                    }
                    throw new ReportedException(horizonCode_Horizon_È3);
                }
            }
            this.ÇŽÕ.Ý("animateTick");
            if (!this.ˆáƒ && this.áŒŠÆ != null) {
                this.áŒŠÆ.HorizonCode_Horizon_È(MathHelper.Ý(this.á.ŒÏ), MathHelper.Ý(this.á.Çªà¢), MathHelper.Ý(this.á.Ê));
            }
            this.ÇŽÕ.Ý("particles");
            if (!this.ˆáƒ) {
                this.Å.HorizonCode_Horizon_È();
            }
        }
        else if (this.áˆºÇŽØ != null) {
            this.ÇŽÕ.Ý("pendingConnection");
            this.áˆºÇŽØ.HorizonCode_Horizon_È();
        }
        this.ÇŽÕ.Â();
        this.ˆá = áƒ();
    }
    
    public void HorizonCode_Horizon_È(final String value, final String s, WorldSettings worldSettings) {
        this.HorizonCode_Horizon_È((WorldClient)null);
        System.gc();
        final ISaveHandler horizonCode_Horizon_È = this.ˆÐƒØ­à.HorizonCode_Horizon_È(value, false);
        WorldInfo ý = horizonCode_Horizon_È.Ý();
        if (ý == null && worldSettings != null) {
            ý = new WorldInfo(worldSettings, value);
            horizonCode_Horizon_È.HorizonCode_Horizon_È(ý);
        }
        if (worldSettings == null) {
            worldSettings = new WorldSettings(ý);
        }
        try {
            (this.ˆÉ = new IntegratedServer(this, value, s, worldSettings)).Ñ¢á();
            this.ÇªÂµÕ = true;
        }
        catch (Throwable causeIn) {
            final CrashReport horizonCode_Horizon_È2 = CrashReport.HorizonCode_Horizon_È(causeIn, "Starting integrated server");
            final CrashReportCategory horizonCode_Horizon_È3 = horizonCode_Horizon_È2.HorizonCode_Horizon_È("Starting integrated server");
            horizonCode_Horizon_È3.HorizonCode_Horizon_È("Level ID", value);
            horizonCode_Horizon_È3.HorizonCode_Horizon_È("Level Name", s);
            throw new ReportedException(horizonCode_Horizon_È2);
        }
        this.Ø­à.Â(I18n.HorizonCode_Horizon_È("menu.loadingLevel", new Object[0]));
        while (!this.ˆÉ.Ø­á()) {
            final String áœšæ = this.ˆÉ.áŒŠÆ();
            if (áœšæ != null) {
                this.Ø­à.Ý(I18n.HorizonCode_Horizon_È(áœšæ, new Object[0]));
            }
            else {
                this.Ø­à.Ý("");
            }
        }
        this.HorizonCode_Horizon_È((GuiScreen)null);
        final SocketAddress horizonCode_Horizon_È4 = this.ˆÉ.£Ï().HorizonCode_Horizon_È();
        final NetworkManager horizonCode_Horizon_È5 = NetworkManager.HorizonCode_Horizon_È(horizonCode_Horizon_È4);
        horizonCode_Horizon_È5.HorizonCode_Horizon_È(new NetHandlerLoginClient(horizonCode_Horizon_È5, this, null));
        horizonCode_Horizon_È5.HorizonCode_Horizon_È(new C00Handshake(47, horizonCode_Horizon_È4.toString(), 0, EnumConnectionState.Ø­áŒŠá));
        horizonCode_Horizon_È5.HorizonCode_Horizon_È(new C00PacketLoginStart(this.Õ().Âµá€()));
        this.áˆºÇŽØ = horizonCode_Horizon_È5;
    }
    
    public void HorizonCode_Horizon_È(final WorldClient worldClient) {
        this.HorizonCode_Horizon_È(worldClient, "");
    }
    
    public void HorizonCode_Horizon_È(final WorldClient worldClient, final String p_73721_1_) {
        if (worldClient == null) {
            final NetHandlerPlayClient µõ = this.µÕ();
            if (µõ != null) {
                µõ.HorizonCode_Horizon_È();
            }
            if (this.ˆÉ != null && this.ˆÉ.á€()) {
                this.ˆÉ.Ø­à();
                this.ˆÉ.ŠÓ();
            }
            this.ˆÉ = null;
            this.Æ.Â();
            this.µÕ.áŒŠÆ().HorizonCode_Horizon_È();
        }
        this.ˆÏ­ = null;
        this.áˆºÇŽØ = null;
        if (this.Ø­à != null) {
            this.Ø­à.HorizonCode_Horizon_È(p_73721_1_);
            this.Ø­à.Ý("");
        }
        if (worldClient == null && this.áŒŠÆ != null) {
            if (this.Û.Âµá€() != null) {
                this.Û.Ó();
                this.ŠÄ();
            }
            this.HorizonCode_Horizon_È((ServerData)null);
            this.ÇªÂµÕ = false;
        }
        this.£áŒŠá.Ý();
        if ((this.áŒŠÆ = worldClient) != null) {
            if (this.áˆºÑ¢Õ != null) {
                this.áˆºÑ¢Õ.HorizonCode_Horizon_È(worldClient);
            }
            if (this.Å != null) {
                this.Å.HorizonCode_Horizon_È(worldClient);
            }
            if (this.á == null) {
                this.á = this.Âµá€.HorizonCode_Horizon_È(worldClient, new StatFileWriter());
                this.Âµá€.Â(this.á);
            }
            this.á.áƒ();
            worldClient.HorizonCode_Horizon_È(this.á);
            this.á.Â = new MovementInputFromOptions(this.ŠÄ);
            this.Âµá€.HorizonCode_Horizon_È(this.á);
            this.ˆÏ­ = this.á;
        }
        else {
            this.ˆÐƒØ­à.Ø­áŒŠá();
            this.á = null;
        }
        System.gc();
        this.ˆá = 0L;
        if (this.á != null) {
            this.á.Â(new ChatComponentText(""));
        }
    }
    
    public void HorizonCode_Horizon_È(final int çªÔ) {
        this.áŒŠÆ.áˆºÑ¢Õ();
        this.áŒŠÆ.à();
        int ˆá = 0;
        String ï­Ðƒà = null;
        if (this.á != null) {
            ˆá = this.á.ˆá();
            this.áŒŠÆ.Â(this.á);
            ï­Ðƒà = this.á.Ï­Ðƒà();
        }
        this.ˆÏ­ = null;
        final EntityPlayerSPOverwrite á = this.á;
        this.á = this.Âµá€.HorizonCode_Horizon_È(this.áŒŠÆ, (this.á == null) ? new StatFileWriter() : this.á.c_());
        this.á.É().HorizonCode_Horizon_È(á.É().Ý());
        this.á.ÇªÔ = çªÔ;
        this.ˆÏ­ = this.á;
        this.á.áƒ();
        this.á.Ý(ï­Ðƒà);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.á);
        this.Âµá€.Â(this.á);
        this.á.Â = new MovementInputFromOptions(this.ŠÄ);
        this.á.Ø­áŒŠá(ˆá);
        this.Âµá€.HorizonCode_Horizon_È(this.á);
        this.á.áˆºÑ¢Õ(á.¥Ðƒá());
        if (this.¥Æ instanceof GuiGameOver) {
            this.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    public final boolean Ø­à() {
        return this.ˆÏ;
    }
    
    public NetHandlerPlayClient µÕ() {
        return (this.á != null) ? this.á.HorizonCode_Horizon_È : null;
    }
    
    public static boolean Æ() {
        return Minecraft.Ñ¢Â == null || !Minecraft.Ñ¢Â.ŠÄ.µ;
    }
    
    public static boolean Šáƒ() {
        return Minecraft.Ñ¢Â != null && Minecraft.Ñ¢Â.ŠÄ.Û;
    }
    
    public static boolean Ï­Ðƒà() {
        return Minecraft.Ñ¢Â != null && Minecraft.Ñ¢Â.ŠÄ.ŠÓ != 0;
    }
    
    private void áŒŠÏ() {
        if (this.áŒŠà != null) {
            final boolean ø­áŒŠá = this.á.áˆºáˆºáŠ.Ø­áŒŠá;
            int n = 0;
            boolean â = false;
            TileEntity horizonCode_Horizon_È = null;
            Item_1028566121 item_1028566121;
            if (this.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
                final BlockPos horizonCode_Horizon_È2 = this.áŒŠà.HorizonCode_Horizon_È();
                final Block ý = this.áŒŠÆ.Â(horizonCode_Horizon_È2).Ý();
                if (ý.Ó() == Material.HorizonCode_Horizon_È) {
                    return;
                }
                item_1028566121 = ý.Âµá€(this.áŒŠÆ, horizonCode_Horizon_È2);
                if (item_1028566121 == null) {
                    return;
                }
                if (ø­áŒŠá && GuiScreen.Å()) {
                    horizonCode_Horizon_È = this.áŒŠÆ.HorizonCode_Horizon_È(horizonCode_Horizon_È2);
                }
                n = ((item_1028566121 instanceof ItemBlock && !ý.áƒ()) ? Block.HorizonCode_Horizon_È(item_1028566121) : ý).Ó(this.áŒŠÆ, horizonCode_Horizon_È2);
                â = item_1028566121.Â();
            }
            else {
                if (this.áŒŠà.HorizonCode_Horizon_È != MovingObjectPosition.HorizonCode_Horizon_È.Ý || this.áŒŠà.Ø­áŒŠá == null || !ø­áŒŠá) {
                    return;
                }
                if (this.áŒŠà.Ø­áŒŠá instanceof EntityPainting) {
                    item_1028566121 = Items.ˆÐƒØ­à;
                }
                else if (this.áŒŠà.Ø­áŒŠá instanceof EntityLeashKnot) {
                    item_1028566121 = Items.áˆºÕ;
                }
                else if (this.áŒŠà.Ø­áŒŠá instanceof EntityItemFrame) {
                    final ItemStack µà = ((EntityItemFrame)this.áŒŠà.Ø­áŒŠá).µà();
                    if (µà == null) {
                        item_1028566121 = Items.µÏ;
                    }
                    else {
                        item_1028566121 = µà.HorizonCode_Horizon_È();
                        n = µà.Ø();
                        â = true;
                    }
                }
                else if (this.áŒŠà.Ø­áŒŠá instanceof EntityMinecart) {
                    switch (Minecraft.HorizonCode_Horizon_È.Â[((EntityMinecart)this.áŒŠà.Ø­áŒŠá).à().ordinal()]) {
                        case 1: {
                            item_1028566121 = Items.Ðƒà;
                            break;
                        }
                        case 2: {
                            item_1028566121 = Items.Ðƒáƒ;
                            break;
                        }
                        case 3: {
                            item_1028566121 = Items.ÇŽ;
                            break;
                        }
                        case 4: {
                            item_1028566121 = Items.ÇŽÅ;
                            break;
                        }
                        case 5: {
                            item_1028566121 = Items.ÐƒáˆºÄ;
                            break;
                        }
                        default: {
                            item_1028566121 = Items.ÇªÔ;
                            break;
                        }
                    }
                }
                else if (this.áŒŠà.Ø­áŒŠá instanceof EntityBoat) {
                    item_1028566121 = Items.ÇªØ­;
                }
                else if (this.áŒŠà.Ø­áŒŠá instanceof EntityArmorStand) {
                    item_1028566121 = Items.¥Ðƒá;
                }
                else {
                    item_1028566121 = Items.áˆºáˆºáŠ;
                    n = EntityList.HorizonCode_Horizon_È(this.áŒŠà.Ø­áŒŠá);
                    â = true;
                    if (!EntityList.HorizonCode_Horizon_È.containsKey(n)) {
                        return;
                    }
                }
            }
            final InventoryPlayer ø­Ñ¢Ï­Ø­áˆº = this.á.Ø­Ñ¢Ï­Ø­áˆº;
            if (horizonCode_Horizon_È == null) {
                ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(item_1028566121, n, â, ø­áŒŠá);
            }
            else {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                horizonCode_Horizon_È.Â(nbtTagCompound);
                final ItemStack stack = new ItemStack(item_1028566121, 1, n);
                stack.HorizonCode_Horizon_È("BlockEntityTag", nbtTagCompound);
                final NBTTagCompound value = new NBTTagCompound();
                final NBTTagList value2 = new NBTTagList();
                value2.HorizonCode_Horizon_È(new NBTTagString("(+NBT)"));
                value.HorizonCode_Horizon_È("Lore", value2);
                stack.HorizonCode_Horizon_È("display", value);
                ø­Ñ¢Ï­Ø­áˆº.Ý(ø­Ñ¢Ï­Ø­áˆº.Ý, stack);
            }
            if (ø­áŒŠá) {
                this.Âµá€.HorizonCode_Horizon_È(ø­Ñ¢Ï­Ø­áˆº.á(ø­Ñ¢Ï­Ø­áˆº.Ý), this.á.ŒÂ.Ý.size() - 9 + ø­Ñ¢Ï­Ø­áˆº.Ý);
            }
        }
    }
    
    public CrashReport Ý(final CrashReport report) {
        report.Ó().HorizonCode_Horizon_È("Launched Version", new Callable() {
            private static final String Â = "CL_00000643";
            
            public String HorizonCode_Horizon_È() {
                return Minecraft.this.£Â;
            }
        });
        report.Ó().HorizonCode_Horizon_È("LWJGL", new Callable() {
            private static final String Â = "CL_00000644";
            
            public String HorizonCode_Horizon_È() {
                return Sys.getVersion();
            }
        });
        report.Ó().HorizonCode_Horizon_È("OpenGL", new Callable() {
            private static final String Â = "CL_00000645";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(GL11.glGetString(7937)) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
            }
        });
        report.Ó().HorizonCode_Horizon_È("GL Caps", new Callable() {
            private static final String Â = "CL_00000646";
            
            public String HorizonCode_Horizon_È() {
                return OpenGlHelper.Ý();
            }
        });
        report.Ó().HorizonCode_Horizon_È("Using VBOs", new Callable() {
            private static final String Â = "CL_00000647";
            
            public String HorizonCode_Horizon_È() {
                return Minecraft.this.ŠÄ.Ñ¢Ç ? "Yes" : "No";
            }
        });
        report.Ó().HorizonCode_Horizon_È("Is Modded", new Callable() {
            private static final String Â = "CL_00000633";
            
            public String HorizonCode_Horizon_È() {
                final String var1 = ClientBrandRetriever.HorizonCode_Horizon_È();
                return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + var1 + "'");
            }
        });
        report.Ó().HorizonCode_Horizon_È("Type", new Callable() {
            private static final String Â = "CL_00000634";
            
            public String HorizonCode_Horizon_È() {
                return "Client (map_client.txt)";
            }
        });
        report.Ó().HorizonCode_Horizon_È("Resource Packs", new Callable() {
            private static final String Â = "CL_00000635";
            
            public String HorizonCode_Horizon_È() {
                return Minecraft.this.ŠÄ.ÇŽá.toString();
            }
        });
        report.Ó().HorizonCode_Horizon_È("Current Language", new Callable() {
            private static final String Â = "CL_00000636";
            
            public String HorizonCode_Horizon_È() {
                return Minecraft.this.ŠÓ.Ý().toString();
            }
        });
        report.Ó().HorizonCode_Horizon_È("Profiler Position", new Callable() {
            private static final String Â = "CL_00000637";
            
            public String HorizonCode_Horizon_È() {
                return Minecraft.this.ÇŽÕ.HorizonCode_Horizon_È ? Minecraft.this.ÇŽÕ.Ý() : "N/A (disabled)";
            }
        });
        if (this.áŒŠÆ != null) {
            this.áŒŠÆ.HorizonCode_Horizon_È(report);
        }
        return report;
    }
    
    public static Minecraft áŒŠà() {
        return Minecraft.Ñ¢Â;
    }
    
    public ListenableFuture ŠÄ() {
        return this.HorizonCode_Horizon_È(new Runnable() {
            private static final String Â = "CL_00001853";
            
            @Override
            public void run() {
                Minecraft.this.Ó();
            }
        });
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.HorizonCode_Horizon_È("fps", Minecraft.Çªà¢);
        playerUsageSnooper.HorizonCode_Horizon_È("vsync_enabled", this.ŠÄ.ˆà¢);
        playerUsageSnooper.HorizonCode_Horizon_È("display_frequency", Display.getDisplayMode().getFrequency());
        playerUsageSnooper.HorizonCode_Horizon_È("display_type", this.Ï­à ? "fullscreen" : "windowed");
        playerUsageSnooper.HorizonCode_Horizon_È("run_time", (MinecraftServer.Œà() - playerUsageSnooper.à()) / 60L * 1000L);
        playerUsageSnooper.HorizonCode_Horizon_È("endianness", (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big");
        playerUsageSnooper.HorizonCode_Horizon_È("resource_packs", this.Û.Ý().size());
        int n = 0;
        final Iterator<ResourcePackRepository.HorizonCode_Horizon_È> iterator = this.Û.Ý().iterator();
        while (iterator.hasNext()) {
            playerUsageSnooper.HorizonCode_Horizon_È("resource_pack[" + n++ + "]", iterator.next().Ø­áŒŠá());
        }
        if (this.ˆÉ != null && this.ˆÉ.£Ó() != null) {
            playerUsageSnooper.HorizonCode_Horizon_È("snooper_partner", this.ˆÉ.£Ó().Ó());
        }
    }
    
    @Override
    public void Â(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.Â("opengl_version", GL11.glGetString(7938));
        playerUsageSnooper.Â("opengl_vendor", GL11.glGetString(7936));
        playerUsageSnooper.Â("client_brand", ClientBrandRetriever.HorizonCode_Horizon_È());
        playerUsageSnooper.Â("launched_version", this.£Â);
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        playerUsageSnooper.Â("gl_caps[ARB_arrays_of_arrays]", capabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.Â("gl_caps[ARB_base_instance]", capabilities.GL_ARB_base_instance);
        playerUsageSnooper.Â("gl_caps[ARB_blend_func_extended]", capabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.Â("gl_caps[ARB_clear_buffer_object]", capabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_color_buffer_float]", capabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.Â("gl_caps[ARB_compatibility]", capabilities.GL_ARB_compatibility);
        playerUsageSnooper.Â("gl_caps[ARB_compressed_texture_pixel_storage]", capabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.Â("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.Â("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.Â("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.Â("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.Â("gl_caps[ARB_compute_shader]", capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.Â("gl_caps[ARB_copy_buffer]", capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.Â("gl_caps[ARB_copy_image]", capabilities.GL_ARB_copy_image);
        playerUsageSnooper.Â("gl_caps[ARB_depth_buffer_float]", capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.Â("gl_caps[ARB_depth_clamp]", capabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.Â("gl_caps[ARB_depth_texture]", capabilities.GL_ARB_depth_texture);
        playerUsageSnooper.Â("gl_caps[ARB_draw_buffers]", capabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.Â("gl_caps[ARB_draw_buffers_blend]", capabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.Â("gl_caps[ARB_draw_elements_base_vertex]", capabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.Â("gl_caps[ARB_draw_indirect]", capabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.Â("gl_caps[ARB_draw_instanced]", capabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.Â("gl_caps[ARB_explicit_attrib_location]", capabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.Â("gl_caps[ARB_explicit_uniform_location]", capabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.Â("gl_caps[ARB_fragment_layer_viewport]", capabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.Â("gl_caps[ARB_fragment_program]", capabilities.GL_ARB_fragment_program);
        playerUsageSnooper.Â("gl_caps[ARB_fragment_shader]", capabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.Â("gl_caps[ARB_fragment_program_shadow]", capabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.Â("gl_caps[ARB_framebuffer_object]", capabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_framebuffer_sRGB]", capabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.Â("gl_caps[ARB_geometry_shader4]", capabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.Â("gl_caps[ARB_gpu_shader5]", capabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.Â("gl_caps[ARB_half_float_pixel]", capabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.Â("gl_caps[ARB_half_float_vertex]", capabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.Â("gl_caps[ARB_instanced_arrays]", capabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.Â("gl_caps[ARB_map_buffer_alignment]", capabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.Â("gl_caps[ARB_map_buffer_range]", capabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.Â("gl_caps[ARB_multisample]", capabilities.GL_ARB_multisample);
        playerUsageSnooper.Â("gl_caps[ARB_multitexture]", capabilities.GL_ARB_multitexture);
        playerUsageSnooper.Â("gl_caps[ARB_occlusion_query2]", capabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.Â("gl_caps[ARB_pixel_buffer_object]", capabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_seamless_cube_map]", capabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.Â("gl_caps[ARB_shader_objects]", capabilities.GL_ARB_shader_objects);
        playerUsageSnooper.Â("gl_caps[ARB_shader_stencil_export]", capabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.Â("gl_caps[ARB_shader_texture_lod]", capabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.Â("gl_caps[ARB_shadow]", capabilities.GL_ARB_shadow);
        playerUsageSnooper.Â("gl_caps[ARB_shadow_ambient]", capabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.Â("gl_caps[ARB_stencil_texturing]", capabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.Â("gl_caps[ARB_sync]", capabilities.GL_ARB_sync);
        playerUsageSnooper.Â("gl_caps[ARB_tessellation_shader]", capabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.Â("gl_caps[ARB_texture_border_clamp]", capabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.Â("gl_caps[ARB_texture_buffer_object]", capabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_texture_cube_map]", capabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.Â("gl_caps[ARB_texture_cube_map_array]", capabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.Â("gl_caps[ARB_texture_non_power_of_two]", capabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.Â("gl_caps[ARB_uniform_buffer_object]", capabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_vertex_blend]", capabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.Â("gl_caps[ARB_vertex_buffer_object]", capabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.Â("gl_caps[ARB_vertex_program]", capabilities.GL_ARB_vertex_program);
        playerUsageSnooper.Â("gl_caps[ARB_vertex_shader]", capabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.Â("gl_caps[EXT_bindable_uniform]", capabilities.GL_EXT_bindable_uniform);
        playerUsageSnooper.Â("gl_caps[EXT_blend_equation_separate]", capabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.Â("gl_caps[EXT_blend_func_separate]", capabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.Â("gl_caps[EXT_blend_minmax]", capabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.Â("gl_caps[EXT_blend_subtract]", capabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.Â("gl_caps[EXT_draw_instanced]", capabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.Â("gl_caps[EXT_framebuffer_multisample]", capabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.Â("gl_caps[EXT_framebuffer_object]", capabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.Â("gl_caps[EXT_framebuffer_sRGB]", capabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.Â("gl_caps[EXT_geometry_shader4]", capabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.Â("gl_caps[EXT_gpu_program_parameters]", capabilities.GL_EXT_gpu_program_parameters);
        playerUsageSnooper.Â("gl_caps[EXT_gpu_shader4]", capabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.Â("gl_caps[EXT_multi_draw_arrays]", capabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.Â("gl_caps[EXT_packed_depth_stencil]", capabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.Â("gl_caps[EXT_paletted_texture]", capabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.Â("gl_caps[EXT_rescale_normal]", capabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.Â("gl_caps[EXT_separate_shader_objects]", capabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.Â("gl_caps[EXT_shader_image_load_store]", capabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.Â("gl_caps[EXT_shadow_funcs]", capabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.Â("gl_caps[EXT_shared_texture_palette]", capabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.Â("gl_caps[EXT_stencil_clear_tag]", capabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.Â("gl_caps[EXT_stencil_two_side]", capabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.Â("gl_caps[EXT_stencil_wrap]", capabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.Â("gl_caps[EXT_texture_3d]", capabilities.GL_EXT_texture_3d);
        playerUsageSnooper.Â("gl_caps[EXT_texture_array]", capabilities.GL_EXT_texture_array);
        playerUsageSnooper.Â("gl_caps[EXT_texture_buffer_object]", capabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.Â("gl_caps[EXT_texture_integer]", capabilities.GL_EXT_texture_integer);
        playerUsageSnooper.Â("gl_caps[EXT_texture_lod_bias]", capabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.Â("gl_caps[EXT_texture_sRGB]", capabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.Â("gl_caps[EXT_vertex_shader]", capabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.Â("gl_caps[EXT_vertex_weighting]", capabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.Â("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
        GL11.glGetError();
        playerUsageSnooper.Â("gl_max_texture_size", Ñ¢á());
    }
    
    public static int Ñ¢á() {
        for (int i = 16384; i > 0; i >>= 1) {
            GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (ByteBuffer)null);
            if (GL11.glGetTexLevelParameteri(32868, 0, 4096) != 0) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public boolean ŒÏ() {
        return this.ŠÄ.áŒŠá€;
    }
    
    public void HorizonCode_Horizon_È(final ServerData ä) {
        this.Ä = ä;
    }
    
    public ServerData Çªà¢() {
        return this.Ä;
    }
    
    public boolean Ê() {
        return this.ÇªÂµÕ;
    }
    
    public boolean ÇŽÉ() {
        return this.ÇªÂµÕ && this.ˆÉ != null;
    }
    
    public IntegratedServer ˆá() {
        return this.ˆÉ;
    }
    
    public static void ÇŽÕ() {
        if (Minecraft.Ñ¢Â != null) {
            final IntegratedServer ˆá = Minecraft.Ñ¢Â.ˆá();
            if (ˆá != null) {
                ˆá.ˆà();
            }
        }
    }
    
    public PlayerUsageSnooper É() {
        return this.Ô;
    }
    
    public static long áƒ() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public boolean á€() {
        return this.Ï­à;
    }
    
    public Session Õ() {
        return this.£à;
    }
    
    public PropertyMap à¢() {
        return this.Ø­Âµ;
    }
    
    public Proxy ŠÂµà() {
        return this.£Ó;
    }
    
    public TextureManager ¥à() {
        return this.Ø­áŒŠá;
    }
    
    public IResourceManager Âµà() {
        return this.áŒŠáŠ;
    }
    
    public ResourcePackRepository Ç() {
        return this.Û;
    }
    
    public LanguageManager È() {
        return this.ŠÓ;
    }
    
    public TextureMap áŠ() {
        return this.ÇªØ­;
    }
    
    public boolean ˆáŠ() {
        return this.Ðƒá;
    }
    
    public boolean áŒŠ() {
        return this.ˆáƒ;
    }
    
    public SoundHandler £ÂµÄ() {
        return this.£áŒŠá;
    }
    
    public MusicTicker.HorizonCode_Horizon_È Ø­Âµ() {
        return (this.¥Æ instanceof GuiWinGame) ? MusicTicker.HorizonCode_Horizon_È.Ø­áŒŠá : ((this.á != null) ? ((this.á.Ï­Ðƒà.£à instanceof WorldProviderHell) ? MusicTicker.HorizonCode_Horizon_È.Âµá€ : ((this.á.Ï­Ðƒà.£à instanceof WorldProviderEnd) ? ((BossStatus.Ý != null && BossStatus.Â > 0) ? MusicTicker.HorizonCode_Horizon_È.Ó : MusicTicker.HorizonCode_Horizon_È.à) : ((this.á.áˆºáˆºáŠ.Ø­áŒŠá && this.á.áˆºáˆºáŠ.Ý) ? MusicTicker.HorizonCode_Horizon_È.Ý : MusicTicker.HorizonCode_Horizon_È.Â))) : MusicTicker.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    public IStream Ä() {
        return this.ÇŽá;
    }
    
    public void Ñ¢Â() {
        final int n = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (n != 0 && !Keyboard.isRepeatEvent() && (!(this.¥Æ instanceof GuiControls) || ((GuiControls)this.¥Æ).Ý <= áƒ() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (n == this.ŠÄ.¥áŠ.áŒŠÆ()) {
                    if (this.Ä().ÂµÈ()) {
                        if (this.Ä().á()) {
                            this.Ä().Å();
                        }
                        else {
                            this.Ä().£á();
                        }
                    }
                }
                else if (n == this.ŠÄ.µÊ.áŒŠÆ()) {
                    if (this.Ä().ÂµÈ()) {
                        this.Ä().ˆÏ­();
                    }
                }
                else if (n == this.ŠÄ.áˆºáˆºáŠ.áŒŠÆ()) {
                    this.ÇŽá.HorizonCode_Horizon_È(true);
                }
                else if (n == this.ŠÄ.ÇŽÈ.áŒŠÆ()) {
                    this.ˆà();
                }
                else if (n == this.ŠÄ.ÇªÂ.áŒŠÆ()) {
                    this.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(ScreenShotHelper.HorizonCode_Horizon_È(this.ŒÏ, this.Ó, this.à, this.Ñ¢à));
                }
            }
            else if (n == this.ŠÄ.áˆºáˆºáŠ.áŒŠÆ()) {
                this.ÇŽá.HorizonCode_Horizon_È(false);
            }
        }
    }
    
    public MinecraftSessionService Ï­à() {
        return this.áŒŠá€;
    }
    
    public SkinManager áˆºáˆºÈ() {
        return this.¥Ï;
    }
    
    public Entity ÇŽá€() {
        return this.ˆÏ­;
    }
    
    public void HorizonCode_Horizon_È(final Entity ˆï­) {
        this.ˆÏ­ = ˆï­;
        this.µÕ.HorizonCode_Horizon_È(ˆï­);
    }
    
    public ListenableFuture HorizonCode_Horizon_È(final Callable callable) {
        Validate.notNull((Object)callable);
        if (!this.Ï()) {
            final ListenableFutureTask create = ListenableFutureTask.create(callable);
            final Queue ˆà¢ = this.ˆà¢;
            synchronized (this.ˆà¢) {
                this.ˆà¢.add(create);
                final ListenableFutureTask listenableFutureTask = create;
                // monitorexit(this.\u02c6\u00e0¢)
                return (ListenableFuture)listenableFutureTask;
            }
        }
        try {
            return Futures.immediateFuture(callable.call());
        }
        catch (Exception ex) {
            return (ListenableFuture)Futures.immediateFailedCheckedFuture(ex);
        }
    }
    
    @Override
    public ListenableFuture HorizonCode_Horizon_È(final Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.HorizonCode_Horizon_È(Executors.callable(runnable));
    }
    
    @Override
    public boolean Ï() {
        return Thread.currentThread() == this.£É;
    }
    
    public BlockRendererDispatcher Ô() {
        return this.Ðƒáƒ;
    }
    
    public RenderManager ÇªÓ() {
        return this.ÂµÈ;
    }
    
    public RenderItem áˆºÏ() {
        return this.ÇªÓ;
    }
    
    public ItemRenderer ˆáƒ() {
        return this.áˆºÏ;
    }
    
    public static int Œ() {
        return Minecraft.Çªà¢;
    }
    
    public static Map £Ï() {
        final HashMap hashMap = Maps.newHashMap();
        hashMap.put("X-Minecraft-Username", áŒŠà().Õ().Ý());
        hashMap.put("X-Minecraft-UUID", áŒŠà().Õ().Â());
        hashMap.put("X-Minecraft-Version", "1.8");
        return hashMap;
    }
    
    public void HorizonCode_Horizon_È(final double n) {
        OpenGlHelper.ÂµÈ(OpenGlHelper.µà);
        GL11.glDisable(3553);
        OpenGlHelper.ÂµÈ(OpenGlHelper.£à);
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00001959";
        
        static {
            Â = new int[EntityMinecart.HorizonCode_Horizon_È.values().length];
            try {
                Minecraft.HorizonCode_Horizon_È.Â[EntityMinecart.HorizonCode_Horizon_È.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Minecraft.HorizonCode_Horizon_È.Â[EntityMinecart.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Minecraft.HorizonCode_Horizon_È.Â[EntityMinecart.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Minecraft.HorizonCode_Horizon_È.Â[EntityMinecart.HorizonCode_Horizon_È.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                Minecraft.HorizonCode_Horizon_È.Â[EntityMinecart.HorizonCode_Horizon_È.à.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            HorizonCode_Horizon_È = new int[MovingObjectPosition.HorizonCode_Horizon_È.values().length];
            try {
                Minecraft.HorizonCode_Horizon_È.HorizonCode_Horizon_È[MovingObjectPosition.HorizonCode_Horizon_È.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                Minecraft.HorizonCode_Horizon_È.HorizonCode_Horizon_È[MovingObjectPosition.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                Minecraft.HorizonCode_Horizon_È.HorizonCode_Horizon_È[MovingObjectPosition.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
        }
    }
}
