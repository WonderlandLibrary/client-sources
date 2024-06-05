package net.minecraft.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import hawk.Client;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements IThreadListener, IPlayerUsage {
   public static byte[] memoryReserve;
   private final boolean isDemo;
   public boolean field_175614_C = false;
   public GameSettings gameSettings;
   public final Profiler mcProfiler = new Profiler();
   public int displayHeight;
   public GuiScreen currentScreen;
   long debugUpdateTime = getSystemTime();
   public GuiAchievement guiAchievement;
   private LanguageManager mcLanguageManager;
   private final DefaultResourcePack mcDefaultResourcePack;
   private MusicTicker mcMusicTicker;
   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
   public boolean inGameHasFocus;
   private final File fileAssets;
   public String debug = "";
   private long debugCrashKeyPressTime = -1L;
   public MouseHelper mouseHelper;
   public FontRenderer fontRendererObj;
   public MovingObjectPosition objectMouseOver;
   private boolean fullscreen;
   private final List defaultResourcePacks = Lists.newArrayList();
   private final Thread mcThread = Thread.currentThread();
   private Framebuffer framebufferMc;
   public WorldClient theWorld;
   private final Queue scheduledTasks = Queues.newArrayDeque();
   private ServerData currentServerData;
   long systemTime = getSystemTime();
   private static final String __OBFID = "CL_00000631";
   private boolean field_175619_R = true;
   private RenderManager renderManager;
   private boolean hasCrashed;
   private int leftClickCounter;
   int fpsCounter;
   private final Proxy proxy;
   public boolean field_175611_D = false;
   public RenderGlobal renderGlobal;
   private ResourceLocation mojangLogo;
   private ModelManager modelManager;
   private boolean isGamePaused;
   private ItemRenderer itemRenderer;
   private Entity field_175622_Z;
   private ResourcePackRepository mcResourcePackRepository;
   public Timer timer = new Timer(20.0F);
   public final File mcDataDir;
   private final PropertyMap twitchDetails;
   public EffectRenderer effectRenderer;
   private static final Logger logger = LogManager.getLogger();
   private final String launchedVersion;
   private BlockRendererDispatcher field_175618_aM;
   private SoundHandler mcSoundHandler;
   private int tempDisplayWidth;
   public int displayWidth;
   public boolean skipRenderWorld;
   public static final boolean isRunningOnMac;
   private static Minecraft theMinecraft;
   private ISaveFormat saveLoader;
   private NetworkManager myNetworkManager;
   private RenderItem renderItem;
   public Entity pointedEntity;
   public GuiIngame ingameGUI;
   public boolean field_175612_E = true;
   public EntityRenderer entityRenderer;
   private SkinManager skinManager;
   public boolean field_175613_B = false;
   private CrashReport crashReporter;
   public LoadingScreenRenderer loadingScreen;
   public PlayerControllerMP playerController;
   public boolean integratedServerIsRunning;
   public static int debugFPS;
   private final File fileResourcepacks;
   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
   private static final List macDisplayModes;
   private TextureMap textureMapBlocks;
   public int rightClickDelayTimer;
   public EntityPlayerSP thePlayer;
   private String debugProfilerName = "root";
   public String serverName;
   private IStream stream;
   private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
   public int serverPort;
   private final MinecraftSessionService sessionService;
   private TextureManager renderEngine;
   long prevFrameTime = -1L;
   private IReloadableResourceManager mcResourceManager;
   private IntegratedServer theIntegratedServer;
   private int joinPlayerCounter;
   private final boolean jvm64bit;
   boolean running = true;
   public FontRenderer standardGalacticFontRenderer;
   private int tempDisplayHeight;
   public Session session;
   private long field_175615_aJ = 0L;

   public static boolean isGuiEnabled() {
      return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
   }

   public void loadWorld(WorldClient var1) {
      this.loadWorld(var1, "");
   }

   public void func_175607_a(Entity var1) {
      this.field_175622_Z = var1;
      this.entityRenderer.func_175066_a(var1);
   }

   public IStream getTwitchStream() {
      return this.stream;
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      var1.addClientStat("fps", debugFPS);
      var1.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
      var1.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
      var1.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
      var1.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - var1.getMinecraftStartTimeMillis()) / 60L * 1000L);
      String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      var1.addClientStat("endianness", var2);
      var1.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
      int var3 = 0;
      Iterator var4 = this.mcResourcePackRepository.getRepositoryEntries().iterator();

      while(var4.hasNext()) {
         ResourcePackRepository.Entry var5 = (ResourcePackRepository.Entry)var4.next();
         var1.addClientStat(String.valueOf((new StringBuilder("resource_pack[")).append(var3++).append("]")), var5.getResourcePackName());
      }

      if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
         var1.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
      }

   }

   public void refreshResources() {
      ArrayList var1 = Lists.newArrayList(this.defaultResourcePacks);
      Iterator var2 = this.mcResourcePackRepository.getRepositoryEntries().iterator();

      while(var2.hasNext()) {
         ResourcePackRepository.Entry var3 = (ResourcePackRepository.Entry)var2.next();
         var1.add(var3.getResourcePack());
      }

      if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
         var1.add(this.mcResourcePackRepository.getResourcePackInstance());
      }

      try {
         this.mcResourceManager.reloadResources(var1);
      } catch (RuntimeException var4) {
         logger.info("Caught error stitching, removing all assigned resourcepacks", var4);
         var1.clear();
         var1.addAll(this.defaultResourcePacks);
         this.mcResourcePackRepository.func_148527_a(Collections.emptyList());
         this.mcResourceManager.reloadResources(var1);
         this.gameSettings.resourcePacks.clear();
         this.gameSettings.saveOptions();
      }

      this.mcLanguageManager.parseLanguageMetadata(var1);
      if (this.renderGlobal != null) {
         this.renderGlobal.loadRenderers();
      }

   }

   public TextureMap getTextureMapBlocks() {
      return this.textureMapBlocks;
   }

   static String access$0(Minecraft var0) {
      return var0.launchedVersion;
   }

   private void func_175608_ak() {
      this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public MusicTicker.MusicType getAmbientMusicType() {
      return this.currentScreen instanceof GuiWinGame ? MusicTicker.MusicType.CREDITS : (this.thePlayer != null ? (this.thePlayer.worldObj.provider instanceof WorldProviderHell ? MusicTicker.MusicType.NETHER : (this.thePlayer.worldObj.provider instanceof WorldProviderEnd ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : (this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
   }

   public void shutdown() {
      this.running = false;
   }

   public void setDimensionAndSpawnPlayer(int var1) {
      this.theWorld.setInitialSpawnLocation();
      this.theWorld.removeAllEntities();
      int var2 = 0;
      String var3 = null;
      if (this.thePlayer != null) {
         var2 = this.thePlayer.getEntityId();
         this.theWorld.removeEntity(this.thePlayer);
         var3 = this.thePlayer.getClientBrand();
      }

      this.field_175622_Z = null;
      EntityPlayerSP var4 = this.thePlayer;
      this.thePlayer = this.playerController.func_178892_a(this.theWorld, this.thePlayer == null ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
      this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(var4.getDataWatcher().getAllWatched());
      this.thePlayer.dimension = var1;
      this.field_175622_Z = this.thePlayer;
      this.thePlayer.preparePlayerToSpawn();
      this.thePlayer.func_175158_f(var3);
      this.theWorld.spawnEntityInWorld(this.thePlayer);
      this.playerController.flipPlayer(this.thePlayer);
      this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
      this.thePlayer.setEntityId(var2);
      this.playerController.setPlayerCapabilities(this.thePlayer);
      this.thePlayer.func_175150_k(var4.func_175140_cp());
      if (this.currentScreen instanceof GuiGameOver) {
         this.displayGuiScreen((GuiScreen)null);
      }

   }

   private void displayDebugInfo(long var1) {
      if (this.mcProfiler.profilingEnabled) {
         List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
         Profiler.Result var4 = (Profiler.Result)var3.remove(0);
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0D, (double)this.displayWidth, (double)this.displayHeight, 0.0D, 1000.0D, 3000.0D);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.func_179090_x();
         Tessellator var5 = Tessellator.getInstance();
         WorldRenderer var6 = var5.getWorldRenderer();
         short var7 = 160;
         int var8 = this.displayWidth - var7 - 10;
         int var9 = this.displayHeight - var7 * 2;
         GlStateManager.enableBlend();
         var6.startDrawingQuads();
         var6.func_178974_a(0, 200);
         var6.addVertex((double)((float)var8 - (float)var7 * 1.1F), (double)((float)var9 - (float)var7 * 0.6F - 16.0F), 0.0D);
         var6.addVertex((double)((float)var8 - (float)var7 * 1.1F), (double)(var9 + var7 * 2), 0.0D);
         var6.addVertex((double)((float)var8 + (float)var7 * 1.1F), (double)(var9 + var7 * 2), 0.0D);
         var6.addVertex((double)((float)var8 + (float)var7 * 1.1F), (double)((float)var9 - (float)var7 * 0.6F - 16.0F), 0.0D);
         var5.draw();
         GlStateManager.disableBlend();
         double var10 = 0.0D;

         int var12;
         int var15;
         for(int var13 = 0; var13 < var3.size(); ++var13) {
            Profiler.Result var14 = (Profiler.Result)var3.get(var13);
            var12 = MathHelper.floor_double(var14.field_76332_a / 4.0D) + 1;
            var6.startDrawing(6);
            var6.func_178991_c(var14.func_76329_a());
            var6.addVertex((double)var8, (double)var9, 0.0D);

            float var16;
            float var17;
            float var18;
            for(var15 = var12; var15 >= 0; --var15) {
               var16 = (float)((var10 + var14.field_76332_a * (double)var15 / (double)var12) * 3.141592653589793D * 2.0D / 100.0D);
               var17 = MathHelper.sin(var16) * (float)var7;
               var18 = MathHelper.cos(var16) * (float)var7 * 0.5F;
               var6.addVertex((double)((float)var8 + var17), (double)((float)var9 - var18), 0.0D);
            }

            var5.draw();
            var6.startDrawing(5);
            var6.func_178991_c((var14.func_76329_a() & 16711422) >> 1);

            for(var15 = var12; var15 >= 0; --var15) {
               var16 = (float)((var10 + var14.field_76332_a * (double)var15 / (double)var12) * 3.141592653589793D * 2.0D / 100.0D);
               var17 = MathHelper.sin(var16) * (float)var7;
               var18 = MathHelper.cos(var16) * (float)var7 * 0.5F;
               var6.addVertex((double)((float)var8 + var17), (double)((float)var9 - var18), 0.0D);
               var6.addVertex((double)((float)var8 + var17), (double)((float)var9 - var18 + 10.0F), 0.0D);
            }

            var5.draw();
            var10 += var14.field_76332_a;
         }

         DecimalFormat var19 = new DecimalFormat("##0.00");
         GlStateManager.func_179098_w();
         String var20 = "";
         if (!var4.field_76331_c.equals("unspecified")) {
            var20 = String.valueOf((new StringBuilder(String.valueOf(var20))).append("[0] "));
         }

         if (var4.field_76331_c.length() == 0) {
            var20 = String.valueOf((new StringBuilder(String.valueOf(var20))).append("ROOT "));
         } else {
            var20 = String.valueOf((new StringBuilder(String.valueOf(var20))).append(var4.field_76331_c).append(" "));
         }

         var12 = 16777215;
         this.fontRendererObj.drawStringWithShadow(var20, (double)((float)(var8 - var7)), (double)((float)(var9 - var7 / 2 - 16)), var12);
         this.fontRendererObj.drawStringWithShadow(var20 = String.valueOf((new StringBuilder(String.valueOf(var19.format(var4.field_76330_b)))).append("%")), (double)((float)(var8 + var7 - this.fontRendererObj.getStringWidth(var20))), (double)((float)(var9 - var7 / 2 - 16)), var12);

         for(var15 = 0; var15 < var3.size(); ++var15) {
            Profiler.Result var21 = (Profiler.Result)var3.get(var15);
            String var22 = "";
            if (var21.field_76331_c.equals("unspecified")) {
               var22 = String.valueOf((new StringBuilder(String.valueOf(var22))).append("[?] "));
            } else {
               var22 = String.valueOf((new StringBuilder(String.valueOf(var22))).append("[").append(var15 + 1).append("] "));
            }

            var22 = String.valueOf((new StringBuilder(String.valueOf(var22))).append(var21.field_76331_c));
            this.fontRendererObj.drawStringWithShadow(var22, (double)((float)(var8 - var7)), (double)((float)(var9 + var7 / 2 + var15 * 8 + 20)), var21.func_76329_a());
            this.fontRendererObj.drawStringWithShadow(var22 = String.valueOf((new StringBuilder(String.valueOf(var19.format(var21.field_76332_a)))).append("%")), (double)((float)(var8 + var7 - 50 - this.fontRendererObj.getStringWidth(var22))), (double)((float)(var9 + var7 / 2 + var15 * 8 + 20)), var21.func_76329_a());
            this.fontRendererObj.drawStringWithShadow(var22 = String.valueOf((new StringBuilder(String.valueOf(var19.format(var21.field_76330_b)))).append("%")), (double)((float)(var8 + var7 - this.fontRendererObj.getStringWidth(var22))), (double)((float)(var9 + var7 / 2 + var15 * 8 + 20)), var21.func_76329_a());
         }
      }

   }

   public void launchIntegratedServer(String var1, String var2, WorldSettings var3) {
      this.loadWorld((WorldClient)null);
      System.gc();
      ISaveHandler var4 = this.saveLoader.getSaveLoader(var1, false);
      WorldInfo var5 = var4.loadWorldInfo();
      if (var5 == null && var3 != null) {
         var5 = new WorldInfo(var3, var1);
         var4.saveWorldInfo(var5);
      }

      if (var3 == null) {
         var3 = new WorldSettings(var5);
      }

      try {
         this.theIntegratedServer = new IntegratedServer(this, var1, var2, var3);
         this.theIntegratedServer.startServerThread();
         this.integratedServerIsRunning = true;
      } catch (Throwable var10) {
         CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
         CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
         var8.addCrashSection("Level ID", var1);
         var8.addCrashSection("Level Name", var2);
         throw new ReportedException(var7);
      }

      this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel"));

      while(!this.theIntegratedServer.serverIsInRunLoop()) {
         String var6 = this.theIntegratedServer.getUserMessage();
         if (var6 != null) {
            this.loadingScreen.displayLoadingString(I18n.format(var6));
         } else {
            this.loadingScreen.displayLoadingString("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var9) {
         }
      }

      this.displayGuiScreen((GuiScreen)null);
      SocketAddress var11 = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
      NetworkManager var12 = NetworkManager.provideLocalClient(var11);
      var12.setNetHandler(new NetHandlerLoginClient(var12, this, (GuiScreen)null));
      var12.sendPacket(new C00Handshake(47, var11.toString(), 0, EnumConnectionState.LOGIN));
      var12.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
      this.myNetworkManager = var12;
   }

   public boolean isIntegratedServerRunning() {
      return this.integratedServerIsRunning;
   }

   public boolean isGamePaused() {
      return this.isGamePaused;
   }

   public void scaledTessellator(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
      var9.startDrawingQuads();
      var9.addVertexWithUV((double)var1, (double)(var2 + var6), 0.0D, (double)((float)var3 * var7), (double)((float)(var4 + var6) * var8));
      var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
      var9.addVertexWithUV((double)(var1 + var5), (double)var2, 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)var4 * var8));
      var9.addVertexWithUV((double)var1, (double)var2, 0.0D, (double)((float)var3 * var7), (double)((float)var4 * var8));
      Tessellator.getInstance().draw();
   }

   public Session getSession() {
      return this.session;
   }

   private void updateDisplayMode() throws LWJGLException {
      HashSet var1 = Sets.newHashSet();
      Collections.addAll(var1, Display.getAvailableDisplayModes());
      DisplayMode var2 = Display.getDesktopDisplayMode();
      if (!var1.contains(var2) && Util.getOSType() == Util.EnumOS.OSX) {
         Iterator var3 = macDisplayModes.iterator();

         label60:
         while(true) {
            while(true) {
               DisplayMode var4;
               boolean var5;
               Iterator var6;
               DisplayMode var7;
               do {
                  if (!var3.hasNext()) {
                     break label60;
                  }

                  var4 = (DisplayMode)var3.next();
                  var5 = true;
                  var6 = var1.iterator();

                  while(var6.hasNext()) {
                     var7 = (DisplayMode)var6.next();
                     if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() && var7.getHeight() == var4.getHeight()) {
                        var5 = false;
                        break;
                     }
                  }
               } while(var5);

               var6 = var1.iterator();

               while(var6.hasNext()) {
                  var7 = (DisplayMode)var6.next();
                  if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() / 2 && var7.getHeight() == var4.getHeight() / 2) {
                     var2 = var7;
                     break;
                  }
               }
            }
         }
      }

      Display.setDisplayMode(var2);
      this.displayWidth = var2.getWidth();
      this.displayHeight = var2.getHeight();
   }

   public ListenableFuture addScheduledTask(Callable var1) {
      Validate.notNull(var1);
      if (!this.isCallingFromMinecraftThread()) {
         ListenableFutureTask var2 = ListenableFutureTask.create(var1);
         Queue var3 = this.scheduledTasks;
         synchronized(this.scheduledTasks) {
            this.scheduledTasks.add(var2);
            return var2;
         }
      } else {
         try {
            return Futures.immediateFuture(var1.call());
         } catch (Exception var6) {
            return Futures.immediateFailedCheckedFuture(var6);
         }
      }
   }

   public ListenableFuture func_175603_A() {
      return this.addScheduledTask(new Runnable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00001853";

         {
            this.this$0 = var1;
         }

         public void run() {
            this.this$0.refreshResources();
         }
      });
   }

   public NetHandlerPlayClient getNetHandler() {
      return this.thePlayer != null ? this.thePlayer.sendQueue : null;
   }

   private void func_175594_ao() {
      Util.EnumOS var1 = Util.getOSType();
      if (var1 != Util.EnumOS.OSX) {
         InputStream var2 = null;
         InputStream var3 = null;

         try {
            var2 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
            var3 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
            if (var2 != null && var3 != null) {
               Display.setIcon(new ByteBuffer[]{this.readImageToBuffer(var2), this.readImageToBuffer(var3)});
            }
         } catch (IOException var8) {
            logger.error("Couldn't set icon", var8);
         } finally {
            IOUtils.closeQuietly(var2);
            IOUtils.closeQuietly(var3);
         }
      }

   }

   public RenderItem getRenderItem() {
      return this.renderItem;
   }

   private void func_175609_am() throws LWJGLException {
      Display.setResizable(true);
      Display.setTitle("Loading Minecraft...");

      try {
         Display.create((new PixelFormat()).withDepthBits(24));
      } catch (LWJGLException var4) {
         logger.error("Couldn't set pixel format", var4);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
         }

         if (this.fullscreen) {
            this.updateDisplayMode();
         }

         Display.create();
      }

   }

   public boolean isUnicode() {
      return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
   }

   public ResourcePackRepository getResourcePackRepository() {
      return this.mcResourcePackRepository;
   }

   private void runGameLoop() throws IOException {
      this.mcProfiler.startSection("root");
      if (Display.isCreated() && Display.isCloseRequested()) {
         this.shutdown();
      }

      if (this.isGamePaused && this.theWorld != null) {
         float var1 = this.timer.renderPartialTicks;
         this.timer.updateTimer();
         this.timer.renderPartialTicks = var1;
      } else {
         this.timer.updateTimer();
      }

      this.mcProfiler.startSection("scheduledExecutables");
      Queue var7 = this.scheduledTasks;
      synchronized(this.scheduledTasks) {
         while(!this.scheduledTasks.isEmpty()) {
            ((FutureTask)this.scheduledTasks.poll()).run();
         }
      }

      this.mcProfiler.endSection();
      long var2 = System.nanoTime();
      this.mcProfiler.startSection("tick");

      for(int var4 = 0; var4 < this.timer.elapsedTicks; ++var4) {
         this.runTick();
      }

      this.mcProfiler.endStartSection("preRenderErrors");
      long var8 = System.nanoTime() - var2;
      this.checkGLError("Pre render");
      this.mcProfiler.endStartSection("sound");
      this.mcSoundHandler.setListener(this.thePlayer, this.timer.renderPartialTicks);
      this.mcProfiler.endSection();
      this.mcProfiler.startSection("render");
      GlStateManager.pushMatrix();
      GlStateManager.clear(16640);
      this.framebufferMc.bindFramebuffer(true);
      this.mcProfiler.startSection("display");
      GlStateManager.func_179098_w();
      if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
         this.gameSettings.thirdPersonView = 0;
      }

      this.mcProfiler.endSection();
      if (!this.skipRenderWorld) {
         this.mcProfiler.endStartSection("gameRenderer");
         this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
      if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
         if (!this.mcProfiler.profilingEnabled) {
            this.mcProfiler.clearProfiling();
         }

         this.mcProfiler.profilingEnabled = true;
         this.displayDebugInfo(var8);
      } else {
         this.mcProfiler.profilingEnabled = false;
         this.prevFrameTime = System.nanoTime();
      }

      this.guiAchievement.updateAchievementWindow();
      this.framebufferMc.unbindFramebuffer();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.entityRenderer.func_152430_c(this.timer.renderPartialTicks);
      GlStateManager.popMatrix();
      this.mcProfiler.startSection("root");
      this.func_175601_h();
      Thread.yield();
      this.mcProfiler.startSection("stream");
      this.mcProfiler.startSection("update");
      this.stream.func_152935_j();
      this.mcProfiler.endStartSection("submit");
      this.stream.func_152922_k();
      this.mcProfiler.endSection();
      this.mcProfiler.endSection();
      this.checkGLError("Post render");
      ++this.fpsCounter;
      this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();

      while(getSystemTime() >= this.debugUpdateTime + 1000L) {
         debugFPS = this.fpsCounter;
         this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", debugFPS, RenderChunk.field_178592_a, RenderChunk.field_178592_a != 1 ? "s" : "", (float)this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : this.gameSettings.limitFramerate, this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : "");
         RenderChunk.field_178592_a = 0;
         this.debugUpdateTime += 1000L;
         this.fpsCounter = 0;
         this.usageSnooper.addMemoryStatsToSnooper();
         if (!this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.startSnooper();
         }
      }

      if (this.isFramerateLimitBelowMax()) {
         this.mcProfiler.startSection("fpslimit_wait");
         Display.sync(this.getLimitFramerate());
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
   }

   public ServerData getCurrentServerData() {
      return this.currentServerData;
   }

   public static void stopIntegratedServer() {
      if (theMinecraft != null) {
         IntegratedServer var0 = theMinecraft.getIntegratedServer();
         if (var0 != null) {
            var0.stopServer();
         }
      }

   }

   private void middleClickMouse() {
      if (this.objectMouseOver != null) {
         boolean var1 = this.thePlayer.capabilities.isCreativeMode;
         int var2 = 0;
         boolean var3 = false;
         TileEntity var4 = null;
         Object var5;
         if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var12 = this.objectMouseOver.func_178782_a();
            Block var14 = this.theWorld.getBlockState(var12).getBlock();
            if (var14.getMaterial() == Material.air) {
               return;
            }

            var5 = var14.getItem(this.theWorld, var12);
            if (var5 == null) {
               return;
            }

            if (var1 && GuiScreen.isCtrlKeyDown()) {
               var4 = this.theWorld.getTileEntity(var12);
            }

            Block var8 = var5 instanceof ItemBlock && !var14.isFlowerPot() ? Block.getBlockFromItem((Item)var5) : var14;
            var2 = var8.getDamageValue(this.theWorld, var12);
            var3 = ((Item)var5).getHasSubtypes();
         } else {
            if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var1) {
               return;
            }

            if (this.objectMouseOver.entityHit instanceof EntityPainting) {
               var5 = Items.painting;
            } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
               var5 = Items.lead;
            } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
               EntityItemFrame var6 = (EntityItemFrame)this.objectMouseOver.entityHit;
               ItemStack var7 = var6.getDisplayedItem();
               if (var7 == null) {
                  var5 = Items.item_frame;
               } else {
                  var5 = var7.getItem();
                  var2 = var7.getMetadata();
                  var3 = true;
               }
            } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
               EntityMinecart var11 = (EntityMinecart)this.objectMouseOver.entityHit;
               switch(var11.func_180456_s()) {
               case FURNACE:
                  var5 = Items.furnace_minecart;
                  break;
               case CHEST:
                  var5 = Items.chest_minecart;
                  break;
               case TNT:
                  var5 = Items.tnt_minecart;
                  break;
               case HOPPER:
                  var5 = Items.hopper_minecart;
                  break;
               case COMMAND_BLOCK:
                  var5 = Items.command_block_minecart;
                  break;
               default:
                  var5 = Items.minecart;
               }
            } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
               var5 = Items.boat;
            } else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
               var5 = Items.armor_stand;
            } else {
               var5 = Items.spawn_egg;
               var2 = EntityList.getEntityID(this.objectMouseOver.entityHit);
               var3 = true;
               if (!EntityList.entityEggs.containsKey(var2)) {
                  return;
               }
            }
         }

         InventoryPlayer var13 = this.thePlayer.inventory;
         if (var4 == null) {
            var13.setCurrentItem((Item)var5, var2, var3, var1);
         } else {
            NBTTagCompound var15 = new NBTTagCompound();
            var4.writeToNBT(var15);
            ItemStack var17 = new ItemStack((Item)var5, 1, var2);
            var17.setTagInfo("BlockEntityTag", var15);
            NBTTagCompound var9 = new NBTTagCompound();
            NBTTagList var10 = new NBTTagList();
            var10.appendTag(new NBTTagString("(+NBT)"));
            var9.setTag("Lore", var10);
            var17.setTagInfo("display", var9);
            var13.setInventorySlotContents(var13.currentItem, var17);
         }

         if (var1) {
            int var16 = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + var13.currentItem;
            this.playerController.sendSlotPacket(var13.getStackInSlot(var13.currentItem), var16);
         }
      }

   }

   public boolean isFramerateLimitBelowMax() {
      return (float)this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
   }

   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.mcThread;
   }

   public void run() {
      this.running = true;

      CrashReport var1;
      try {
         this.startGame();
      } catch (Throwable var11) {
         var1 = CrashReport.makeCrashReport(var11, "Initializing game");
         var1.makeCategory("Initialization");
         this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(var1));
         return;
      }

      while(true) {
         try {
            if (!this.running) {
               break;
            }

            if (!this.hasCrashed || this.crashReporter == null) {
               try {
                  this.runGameLoop();
               } catch (OutOfMemoryError var10) {
                  this.freeMemory();
                  this.displayGuiScreen(new GuiMemoryErrorScreen());
                  System.gc();
               }
               continue;
            }

            this.displayCrashReport(this.crashReporter);
         } catch (MinecraftError var12) {
            break;
         } catch (ReportedException var13) {
            this.addGraphicsAndWorldToCrashReport(var13.getCrashReport());
            this.freeMemory();
            logger.fatal("Reported exception thrown!", var13);
            this.displayCrashReport(var13.getCrashReport());
            break;
         } catch (Throwable var14) {
            var1 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
            this.freeMemory();
            logger.fatal("Unreported exception thrown!", var14);
            this.displayCrashReport(var1);
            break;
         } finally {
            this.shutdownMinecraftApplet();
         }

         return;
      }

   }

   public static boolean isFancyGraphicsEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
   }

   private static boolean isJvm64bit() {
      String[] var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      String[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1[var3];
         String var5 = System.getProperty(var4);
         if (var5 != null && var5.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public static long getSystemTime() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public Entity func_175606_aa() {
      return this.field_175622_Z;
   }

   public void runTick() throws IOException {
      if (this.rightClickDelayTimer > 0) {
         --this.rightClickDelayTimer;
      }

      this.mcProfiler.startSection("gui");
      if (!this.isGamePaused) {
         this.ingameGUI.updateTick();
      }

      this.mcProfiler.endSection();
      this.entityRenderer.getMouseOver(1.0F);
      this.mcProfiler.startSection("gameMode");
      if (!this.isGamePaused && this.theWorld != null) {
         this.playerController.updateController();
      }

      this.mcProfiler.endStartSection("textures");
      if (!this.isGamePaused) {
         this.renderEngine.tick();
      }

      if (this.currentScreen == null && this.thePlayer != null) {
         if (this.thePlayer.getHealth() <= 0.0F) {
            this.displayGuiScreen((GuiScreen)null);
         } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
            this.displayGuiScreen(new GuiSleepMP());
         }
      } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
         this.displayGuiScreen((GuiScreen)null);
      }

      if (this.currentScreen != null) {
         this.leftClickCounter = 10000;
      }

      CrashReport var1;
      CrashReportCategory var2;
      if (this.currentScreen != null) {
         try {
            this.currentScreen.handleInput();
         } catch (Throwable var9) {
            var1 = CrashReport.makeCrashReport(var9, "Updating screen events");
            var2 = var1.makeCategory("Affected screen");
            var2.addCrashSectionCallable("Screen name", new Callable(this) {
               private static final String __OBFID = "CL_00000640";
               final Minecraft this$0;

               {
                  this.this$0 = var1;
               }

               public Object call1() {
                  return this.call();
               }

               public Object call() throws Exception {
                  return this.call();
               }

               public String call() {
                  return this.this$0.currentScreen.getClass().getCanonicalName();
               }
            });
            throw new ReportedException(var1);
         }

         if (this.currentScreen != null) {
            try {
               this.currentScreen.updateScreen();
            } catch (Throwable var8) {
               var1 = CrashReport.makeCrashReport(var8, "Ticking screen");
               var2 = var1.makeCategory("Affected screen");
               var2.addCrashSectionCallable("Screen name", new Callable(this) {
                  private static final String __OBFID = "CL_00000642";
                  final Minecraft this$0;

                  {
                     this.this$0 = var1;
                  }

                  public Object call() throws Exception {
                     return this.call();
                  }

                  public String call() {
                     return this.this$0.currentScreen.getClass().getCanonicalName();
                  }

                  public Object call1() {
                     return this.call();
                  }
               });
               throw new ReportedException(var1);
            }
         }
      }

      if (this.currentScreen == null || this.currentScreen.allowUserInput) {
         this.mcProfiler.endStartSection("mouse");

         int var3;
         while(Mouse.next()) {
            var3 = Mouse.getEventButton();
            KeyBinding.setKeyBindState(var3 - 100, Mouse.getEventButtonState());
            if (Mouse.getEventButtonState()) {
               if (this.thePlayer.func_175149_v() && var3 == 2) {
                  this.ingameGUI.func_175187_g().func_175261_b();
               } else {
                  KeyBinding.onTick(var3 - 100);
               }
            }

            long var4 = getSystemTime() - this.systemTime;
            if (var4 <= 200L) {
               int var6 = Mouse.getEventDWheel();
               if (var6 != 0) {
                  if (this.thePlayer.func_175149_v()) {
                     var6 = var6 < 0 ? -1 : 1;
                     if (this.ingameGUI.func_175187_g().func_175262_a()) {
                        this.ingameGUI.func_175187_g().func_175259_b(-var6);
                     } else {
                        float var7 = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + (float)var6 * 0.005F, 0.0F, 0.2F);
                        this.thePlayer.capabilities.setFlySpeed(var7);
                     }
                  } else {
                     this.thePlayer.inventory.changeCurrentItem(var6);
                  }
               }

               if (this.currentScreen == null) {
                  if (!this.inGameHasFocus && Mouse.getEventButtonState()) {
                     this.setIngameFocus();
                  }
               } else if (this.currentScreen != null) {
                  this.currentScreen.handleMouseInput();
               }
            }
         }

         if (this.leftClickCounter > 0) {
            --this.leftClickCounter;
         }

         this.mcProfiler.endStartSection("keyboard");

         label532:
         while(true) {
            do {
               do {
                  boolean var11;
                  do {
                     if (!Keyboard.next()) {
                        for(var3 = 0; var3 < 9; ++var3) {
                           if (this.gameSettings.keyBindsHotbar[var3].isPressed()) {
                              if (this.thePlayer.func_175149_v()) {
                                 this.ingameGUI.func_175187_g().func_175260_a(var3);
                              } else {
                                 this.thePlayer.inventory.currentItem = var3;
                              }
                           }
                        }

                        var11 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;

                        while(this.gameSettings.keyBindInventory.isPressed()) {
                           if (this.playerController.isRidingHorse()) {
                              this.thePlayer.func_175163_u();
                           } else {
                              this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                              this.displayGuiScreen(new GuiInventory(this.thePlayer));
                           }
                        }

                        while(this.gameSettings.keyBindDrop.isPressed()) {
                           if (!this.thePlayer.func_175149_v()) {
                              this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
                           }
                        }

                        while(this.gameSettings.keyBindChat.isPressed() && var11) {
                           this.displayGuiScreen(new GuiChat());
                        }

                        if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var11) {
                           this.displayGuiScreen(new GuiChat("/"));
                        }

                        if (this.thePlayer.isUsingItem()) {
                           if (!this.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                              this.playerController.onStoppedUsingItem(this.thePlayer);
                           }

                           while(this.gameSettings.keyBindAttack.isPressed()) {
                           }

                           while(this.gameSettings.keyBindUseItem.isPressed()) {
                           }

                           while(this.gameSettings.keyBindPickBlock.isPressed()) {
                           }
                        } else {
                           while(this.gameSettings.keyBindAttack.isPressed()) {
                              this.clickMouse();
                           }

                           while(this.gameSettings.keyBindUseItem.isPressed()) {
                              this.rightClickMouse();
                           }

                           while(this.gameSettings.keyBindPickBlock.isPressed()) {
                              this.middleClickMouse();
                           }
                        }

                        if (this.gameSettings.keyBindUseItem.getIsKeyPressed() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
                           this.rightClickMouse();
                        }

                        this.sendClickBlockToController(this.currentScreen == null && this.gameSettings.keyBindAttack.getIsKeyPressed() && this.inGameHasFocus);
                        break label532;
                     }

                     var3 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
                     KeyBinding.setKeyBindState(var3, Keyboard.getEventKeyState());
                     if (Keyboard.getEventKeyState()) {
                        KeyBinding.onTick(var3);
                     }

                     if (this.debugCrashKeyPressTime > 0L) {
                        if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                           throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                        }

                        if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                           this.debugCrashKeyPressTime = -1L;
                        }
                     } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                        this.debugCrashKeyPressTime = getSystemTime();
                     }

                     this.dispatchKeypresses();
                  } while(!Keyboard.getEventKeyState());

                  if (var3 == 62 && this.entityRenderer != null) {
                     this.entityRenderer.func_175071_c();
                  }

                  if (this.currentScreen != null) {
                     this.currentScreen.handleKeyboardInput();
                  } else {
                     Client.keyPress(var3);
                     if (var3 == 1) {
                        this.displayInGameMenu();
                     }

                     if (var3 == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
                        this.ingameGUI.getChatGUI().clearChatMessages();
                     }

                     if (var3 == 31 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                     }

                     if (var3 == 17 && Keyboard.isKeyDown(61)) {
                     }

                     if (var3 == 18 && Keyboard.isKeyDown(61)) {
                     }

                     if (var3 == 47 && Keyboard.isKeyDown(61)) {
                     }

                     if (var3 == 38 && Keyboard.isKeyDown(61)) {
                     }

                     if (var3 == 22 && Keyboard.isKeyDown(61)) {
                     }

                     if (var3 == 20 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                     }

                     if (var3 == 33 && Keyboard.isKeyDown(61)) {
                        var11 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                        this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var11 ? -1 : 1);
                     }

                     if (var3 == 30 && Keyboard.isKeyDown(61)) {
                        this.renderGlobal.loadRenderers();
                     }

                     if (var3 == 35 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                        this.gameSettings.saveOptions();
                     }

                     if (var3 == 48 && Keyboard.isKeyDown(61)) {
                        this.renderManager.func_178629_b(!this.renderManager.func_178634_b());
                     }

                     if (var3 == 25 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                        this.gameSettings.saveOptions();
                     }

                     if (var3 == 59) {
                        this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                     }

                     if (var3 == 61) {
                        this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                        this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                     }

                     if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                        ++this.gameSettings.thirdPersonView;
                        if (this.gameSettings.thirdPersonView > 2) {
                           this.gameSettings.thirdPersonView = 0;
                        }

                        if (this.gameSettings.thirdPersonView == 0) {
                           this.entityRenderer.func_175066_a(this.func_175606_aa());
                        } else if (this.gameSettings.thirdPersonView == 1) {
                           this.entityRenderer.func_175066_a((Entity)null);
                        }
                     }

                     if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                        this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                     }
                  }
               } while(!this.gameSettings.showDebugInfo);
            } while(!this.gameSettings.showDebugProfilerChart);

            if (var3 == 11) {
               this.updateDebugProfilerName(0);
            }

            for(int var12 = 0; var12 < 9; ++var12) {
               if (var3 == 2 + var12) {
                  this.updateDebugProfilerName(var12 + 1);
               }
            }
         }
      }

      if (this.theWorld != null) {
         if (this.thePlayer != null) {
            ++this.joinPlayerCounter;
            if (this.joinPlayerCounter == 30) {
               this.joinPlayerCounter = 0;
               this.theWorld.joinEntityInSurroundings(this.thePlayer);
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
            if (this.theWorld.func_175658_ac() > 0) {
               this.theWorld.setLastLightningBolt(this.theWorld.func_175658_ac() - 1);
            }

            this.theWorld.updateEntities();
         }
      }

      if (!this.isGamePaused) {
         this.mcMusicTicker.update();
         this.mcSoundHandler.update();
      }

      if (this.theWorld != null) {
         if (!this.isGamePaused) {
            this.theWorld.setAllowedSpawnTypes(this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);

            try {
               this.theWorld.tick();
            } catch (Throwable var10) {
               var1 = CrashReport.makeCrashReport(var10, "Exception in world tick");
               if (this.theWorld == null) {
                  var2 = var1.makeCategory("Affected level");
                  var2.addCrashSection("Problem", "Level is null!");
               } else {
                  this.theWorld.addWorldInfoToCrashReport(var1);
               }

               throw new ReportedException(var1);
            }
         }

         this.mcProfiler.endStartSection("animateTick");
         if (!this.isGamePaused && this.theWorld != null) {
            this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
         }

         this.mcProfiler.endStartSection("particles");
         if (!this.isGamePaused) {
            this.effectRenderer.updateEffects();
         }
      } else if (this.myNetworkManager != null) {
         this.mcProfiler.endStartSection("pendingConnection");
         this.myNetworkManager.processReceivedPackets();
      }

      this.mcProfiler.endSection();
      this.systemTime = getSystemTime();
   }

   public void dispatchKeypresses() {
      int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
      if (var1 != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)) {
         if (Keyboard.getEventKeyState()) {
            if (var1 == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
               if (this.getTwitchStream().func_152934_n()) {
                  this.getTwitchStream().func_152914_u();
               } else if (this.getTwitchStream().func_152924_m()) {
                  this.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                     private static final String __OBFID = "CL_00001852";
                     final Minecraft this$0;

                     {
                        this.this$0 = var1;
                     }

                     public void confirmClicked(boolean var1, int var2) {
                        if (var1) {
                           this.this$0.getTwitchStream().func_152930_t();
                        }

                        this.this$0.displayGuiScreen((GuiScreen)null);
                     }
                  }, I18n.format("stream.confirm_start"), "", 0));
               } else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                  if (this.theWorld != null) {
                     this.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
                  }
               } else {
                  GuiStreamUnavailable.func_152321_a(this.currentScreen);
               }
            } else if (var1 == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
               if (this.getTwitchStream().func_152934_n()) {
                  if (this.getTwitchStream().isPaused()) {
                     this.getTwitchStream().func_152933_r();
                  } else {
                     this.getTwitchStream().func_152916_q();
                  }
               }
            } else if (var1 == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
               if (this.getTwitchStream().func_152934_n()) {
                  this.getTwitchStream().func_152931_p();
               }
            } else if (var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
               this.stream.func_152910_a(true);
            } else if (var1 == this.gameSettings.keyBindFullscreen.getKeyCode()) {
               this.toggleFullscreen();
            } else if (var1 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
               this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
            }
         } else if (var1 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
            this.stream.func_152910_a(false);
         }
      }

   }

   public IntegratedServer getIntegratedServer() {
      return this.theIntegratedServer;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public PropertyMap func_180509_L() {
      return this.twitchDetails;
   }

   public void displayGuiScreen(GuiScreen var1) {
      if (this.currentScreen != null) {
         this.currentScreen.onGuiClosed();
      }

      if (var1 == null && this.theWorld == null) {
         var1 = new GuiMainMenu();
      } else if (var1 == null && this.thePlayer.getHealth() <= 0.0F) {
         var1 = new GuiGameOver();
      }

      if (var1 instanceof GuiMainMenu) {
         this.gameSettings.showDebugInfo = false;
         this.ingameGUI.getChatGUI().clearChatMessages();
      }

      this.currentScreen = (GuiScreen)var1;
      if (var1 != null) {
         this.setIngameNotInFocus();
         ScaledResolution var2 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
         int var3 = var2.getScaledWidth();
         int var4 = var2.getScaledHeight();
         ((GuiScreen)var1).setWorldAndResolution(this, var3, var4);
         this.skipRenderWorld = false;
      } else {
         this.mcSoundHandler.resumeSounds();
         this.setIngameFocus();
      }

   }

   public TextureManager getTextureManager() {
      return this.renderEngine;
   }

   public boolean isSingleplayer() {
      return this.integratedServerIsRunning && this.theIntegratedServer != null;
   }

   public void shutdownMinecraftApplet() {
      try {
         Client.shutdown();
         this.stream.shutdownStream();
         logger.info("Stopping!");

         try {
            this.loadWorld((WorldClient)null);
         } catch (Throwable var5) {
         }

         this.mcSoundHandler.unloadSounds();
      } finally {
         Display.destroy();
         if (!this.hasCrashed) {
            System.exit(0);
         }

      }

      System.gc();
   }

   private void updateDebugProfilerName(int var1) {
      List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
      if (var2 != null && !var2.isEmpty()) {
         Profiler.Result var3 = (Profiler.Result)var2.remove(0);
         if (var1 == 0) {
            if (var3.field_76331_c.length() > 0) {
               int var4 = this.debugProfilerName.lastIndexOf(".");
               if (var4 >= 0) {
                  this.debugProfilerName = this.debugProfilerName.substring(0, var4);
               }
            }
         } else {
            --var1;
            if (var1 < var2.size() && !((Profiler.Result)var2.get(var1)).field_76331_c.equals("unspecified")) {
               if (this.debugProfilerName.length() > 0) {
                  this.debugProfilerName = String.valueOf((new StringBuilder(String.valueOf(this.debugProfilerName))).append("."));
               }

               this.debugProfilerName = String.valueOf((new StringBuilder(String.valueOf(this.debugProfilerName))).append(((Profiler.Result)var2.get(var1)).field_76331_c));
            }
         }
      }

   }

   public void addServerTypeToSnooper(PlayerUsageSnooper var1) {
      var1.addStatToSnooper("opengl_version", GL11.glGetString(7938));
      var1.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
      var1.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
      var1.addStatToSnooper("launched_version", this.launchedVersion);
      ContextCapabilities var2 = GLContext.getCapabilities();
      var1.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", var2.GL_ARB_arrays_of_arrays);
      var1.addStatToSnooper("gl_caps[ARB_base_instance]", var2.GL_ARB_base_instance);
      var1.addStatToSnooper("gl_caps[ARB_blend_func_extended]", var2.GL_ARB_blend_func_extended);
      var1.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", var2.GL_ARB_clear_buffer_object);
      var1.addStatToSnooper("gl_caps[ARB_color_buffer_float]", var2.GL_ARB_color_buffer_float);
      var1.addStatToSnooper("gl_caps[ARB_compatibility]", var2.GL_ARB_compatibility);
      var1.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", var2.GL_ARB_compressed_texture_pixel_storage);
      var1.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
      var1.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
      var1.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
      var1.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
      var1.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
      var1.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
      var1.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
      var1.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
      var1.addStatToSnooper("gl_caps[ARB_depth_clamp]", var2.GL_ARB_depth_clamp);
      var1.addStatToSnooper("gl_caps[ARB_depth_texture]", var2.GL_ARB_depth_texture);
      var1.addStatToSnooper("gl_caps[ARB_draw_buffers]", var2.GL_ARB_draw_buffers);
      var1.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", var2.GL_ARB_draw_buffers_blend);
      var1.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", var2.GL_ARB_draw_elements_base_vertex);
      var1.addStatToSnooper("gl_caps[ARB_draw_indirect]", var2.GL_ARB_draw_indirect);
      var1.addStatToSnooper("gl_caps[ARB_draw_instanced]", var2.GL_ARB_draw_instanced);
      var1.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", var2.GL_ARB_explicit_attrib_location);
      var1.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", var2.GL_ARB_explicit_uniform_location);
      var1.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", var2.GL_ARB_fragment_layer_viewport);
      var1.addStatToSnooper("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
      var1.addStatToSnooper("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
      var1.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", var2.GL_ARB_fragment_program_shadow);
      var1.addStatToSnooper("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
      var1.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", var2.GL_ARB_framebuffer_sRGB);
      var1.addStatToSnooper("gl_caps[ARB_geometry_shader4]", var2.GL_ARB_geometry_shader4);
      var1.addStatToSnooper("gl_caps[ARB_gpu_shader5]", var2.GL_ARB_gpu_shader5);
      var1.addStatToSnooper("gl_caps[ARB_half_float_pixel]", var2.GL_ARB_half_float_pixel);
      var1.addStatToSnooper("gl_caps[ARB_half_float_vertex]", var2.GL_ARB_half_float_vertex);
      var1.addStatToSnooper("gl_caps[ARB_instanced_arrays]", var2.GL_ARB_instanced_arrays);
      var1.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", var2.GL_ARB_map_buffer_alignment);
      var1.addStatToSnooper("gl_caps[ARB_map_buffer_range]", var2.GL_ARB_map_buffer_range);
      var1.addStatToSnooper("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
      var1.addStatToSnooper("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
      var1.addStatToSnooper("gl_caps[ARB_occlusion_query2]", var2.GL_ARB_occlusion_query2);
      var1.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
      var1.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", var2.GL_ARB_seamless_cube_map);
      var1.addStatToSnooper("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
      var1.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", var2.GL_ARB_shader_stencil_export);
      var1.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", var2.GL_ARB_shader_texture_lod);
      var1.addStatToSnooper("gl_caps[ARB_shadow]", var2.GL_ARB_shadow);
      var1.addStatToSnooper("gl_caps[ARB_shadow_ambient]", var2.GL_ARB_shadow_ambient);
      var1.addStatToSnooper("gl_caps[ARB_stencil_texturing]", var2.GL_ARB_stencil_texturing);
      var1.addStatToSnooper("gl_caps[ARB_sync]", var2.GL_ARB_sync);
      var1.addStatToSnooper("gl_caps[ARB_tessellation_shader]", var2.GL_ARB_tessellation_shader);
      var1.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", var2.GL_ARB_texture_border_clamp);
      var1.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", var2.GL_ARB_texture_buffer_object);
      var1.addStatToSnooper("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
      var1.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", var2.GL_ARB_texture_cube_map_array);
      var1.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
      var1.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
      var1.addStatToSnooper("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
      var1.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
      var1.addStatToSnooper("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
      var1.addStatToSnooper("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
      var1.addStatToSnooper("gl_caps[EXT_bindable_uniform]", var2.GL_EXT_bindable_uniform);
      var1.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", var2.GL_EXT_blend_equation_separate);
      var1.addStatToSnooper("gl_caps[EXT_blend_func_separate]", var2.GL_EXT_blend_func_separate);
      var1.addStatToSnooper("gl_caps[EXT_blend_minmax]", var2.GL_EXT_blend_minmax);
      var1.addStatToSnooper("gl_caps[EXT_blend_subtract]", var2.GL_EXT_blend_subtract);
      var1.addStatToSnooper("gl_caps[EXT_draw_instanced]", var2.GL_EXT_draw_instanced);
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", var2.GL_EXT_framebuffer_multisample);
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_object]", var2.GL_EXT_framebuffer_object);
      var1.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", var2.GL_EXT_framebuffer_sRGB);
      var1.addStatToSnooper("gl_caps[EXT_geometry_shader4]", var2.GL_EXT_geometry_shader4);
      var1.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", var2.GL_EXT_gpu_program_parameters);
      var1.addStatToSnooper("gl_caps[EXT_gpu_shader4]", var2.GL_EXT_gpu_shader4);
      var1.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", var2.GL_EXT_multi_draw_arrays);
      var1.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", var2.GL_EXT_packed_depth_stencil);
      var1.addStatToSnooper("gl_caps[EXT_paletted_texture]", var2.GL_EXT_paletted_texture);
      var1.addStatToSnooper("gl_caps[EXT_rescale_normal]", var2.GL_EXT_rescale_normal);
      var1.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", var2.GL_EXT_separate_shader_objects);
      var1.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", var2.GL_EXT_shader_image_load_store);
      var1.addStatToSnooper("gl_caps[EXT_shadow_funcs]", var2.GL_EXT_shadow_funcs);
      var1.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", var2.GL_EXT_shared_texture_palette);
      var1.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", var2.GL_EXT_stencil_clear_tag);
      var1.addStatToSnooper("gl_caps[EXT_stencil_two_side]", var2.GL_EXT_stencil_two_side);
      var1.addStatToSnooper("gl_caps[EXT_stencil_wrap]", var2.GL_EXT_stencil_wrap);
      var1.addStatToSnooper("gl_caps[EXT_texture_3d]", var2.GL_EXT_texture_3d);
      var1.addStatToSnooper("gl_caps[EXT_texture_array]", var2.GL_EXT_texture_array);
      var1.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", var2.GL_EXT_texture_buffer_object);
      var1.addStatToSnooper("gl_caps[EXT_texture_integer]", var2.GL_EXT_texture_integer);
      var1.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", var2.GL_EXT_texture_lod_bias);
      var1.addStatToSnooper("gl_caps[EXT_texture_sRGB]", var2.GL_EXT_texture_sRGB);
      var1.addStatToSnooper("gl_caps[EXT_vertex_shader]", var2.GL_EXT_vertex_shader);
      var1.addStatToSnooper("gl_caps[EXT_vertex_weighting]", var2.GL_EXT_vertex_weighting);
      var1.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
      GL11.glGetError();
      var1.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
      GL11.glGetError();
      var1.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
   }

   public SkinManager getSkinManager() {
      return this.skinManager;
   }

   public LanguageManager getLanguageManager() {
      return this.mcLanguageManager;
   }

   private void startTimerHackThread() {
      Thread var1 = new Thread(this, "Timer hack thread") {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000639";

         public void run() {
            while(this.this$0.running) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
               }
            }

         }

         {
            this.this$0 = var1;
         }
      };
      var1.setDaemon(true);
      var1.start();
   }

   public Minecraft(GameConfiguration var1) {
      theMinecraft = this;
      this.mcDataDir = var1.field_178744_c.field_178760_a;
      this.fileAssets = var1.field_178744_c.field_178759_c;
      this.fileResourcepacks = var1.field_178744_c.field_178758_b;
      this.launchedVersion = var1.field_178741_d.field_178755_b;
      this.twitchDetails = var1.field_178745_a.field_178750_b;
      this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(var1.field_178744_c.field_178759_c, var1.field_178744_c.field_178757_d)).func_152782_a());
      this.proxy = var1.field_178745_a.field_178751_c == null ? Proxy.NO_PROXY : var1.field_178745_a.field_178751_c;
      this.sessionService = (new YggdrasilAuthenticationService(var1.field_178745_a.field_178751_c, UUID.randomUUID().toString())).createMinecraftSessionService();
      this.session = var1.field_178745_a.field_178752_a;
      logger.info(String.valueOf((new StringBuilder("Setting user: ")).append(this.session.getUsername())));
      logger.info(String.valueOf((new StringBuilder("(Session ID is ")).append(this.session.getSessionID()).append(")")));
      this.isDemo = var1.field_178741_d.field_178756_a;
      this.displayWidth = var1.field_178743_b.field_178764_a > 0 ? var1.field_178743_b.field_178764_a : 1;
      this.displayHeight = var1.field_178743_b.field_178762_b > 0 ? var1.field_178743_b.field_178762_b : 1;
      this.tempDisplayWidth = var1.field_178743_b.field_178764_a;
      this.tempDisplayHeight = var1.field_178743_b.field_178762_b;
      this.fullscreen = var1.field_178743_b.field_178763_c;
      this.jvm64bit = isJvm64bit();
      this.theIntegratedServer = new IntegratedServer(this);
      if (var1.field_178742_e.field_178754_a != null) {
         this.serverName = var1.field_178742_e.field_178754_a;
         this.serverPort = var1.field_178742_e.field_178753_b;
      }

      ImageIO.setUseCache(false);
      Bootstrap.register();
   }

   public static int func_175610_ah() {
      return debugFPS;
   }

   static LanguageManager access$1(Minecraft var0) {
      return var0.mcLanguageManager;
   }

   public void displayInGameMenu() {
      if (this.currentScreen == null) {
         this.displayGuiScreen(new GuiIngameMenu());
         if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
            this.mcSoundHandler.pauseSounds();
         }
      }

   }

   public void displayCrashReport(CrashReport var1) {
      File var2 = new File(getMinecraft().mcDataDir, "crash-reports");
      File var3 = new File(var2, String.valueOf((new StringBuilder("crash-")).append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-client.txt")));
      Bootstrap.func_179870_a(var1.getCompleteReport());
      if (var1.getFile() != null) {
         Bootstrap.func_179870_a(String.valueOf((new StringBuilder("#@!@# Game crashed! Crash report saved to: #@!@# ")).append(var1.getFile())));
         System.exit(-1);
      } else if (var1.saveToFile(var3)) {
         Bootstrap.func_179870_a(String.valueOf((new StringBuilder("#@!@# Game crashed! Crash report saved to: #@!@# ")).append(var3.getAbsolutePath())));
         System.exit(-1);
      } else {
         Bootstrap.func_179870_a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }

   }

   private void func_180510_a(TextureManager var1) {
      ScaledResolution var2 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
      int var3 = var2.getScaleFactor();
      Framebuffer var4 = new Framebuffer(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3, true);
      var4.bindFramebuffer(false);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, (double)var2.getScaledWidth(), (double)var2.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      GlStateManager.disableDepth();
      GlStateManager.func_179098_w();
      InputStream var5 = null;

      try {
         var5 = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
         this.mojangLogo = var1.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(var5)));
         var1.bindTexture(this.mojangLogo);
      } catch (IOException var12) {
         logger.error(String.valueOf((new StringBuilder("Unable to load logo: ")).append(locationMojangPng)), var12);
      } finally {
         IOUtils.closeQuietly(var5);
      }

      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawingQuads();
      var7.func_178991_c(16777215);
      var7.addVertexWithUV(0.0D, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
      var7.addVertexWithUV((double)this.displayWidth, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
      var7.addVertexWithUV((double)this.displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
      var7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      var6.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var7.func_178991_c(16777215);
      short var8 = 256;
      short var9 = 256;
      this.scaledTessellator((var2.getScaledWidth() - var8) / 2, (var2.getScaledHeight() - var9) / 2, 0, 0, var8, var9);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      var4.unbindFramebuffer();
      var4.framebufferRender(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.func_175601_h();
   }

   private void checkGLError(String var1) {
      if (this.field_175619_R) {
         int var2 = GL11.glGetError();
         if (var2 != 0) {
            String var3 = GLU.gluErrorString(var2);
            logger.error("########## GL ERROR ##########");
            logger.error(String.valueOf((new StringBuilder("@ ")).append(var1)));
            logger.error(String.valueOf((new StringBuilder(String.valueOf(var2))).append(": ").append(var3)));
         }
      }

   }

   public BlockRendererDispatcher getBlockRendererDispatcher() {
      return this.field_175618_aM;
   }

   private void rightClickMouse() {
      this.rightClickDelayTimer = 4;
      boolean var1 = true;
      ItemStack var2 = this.thePlayer.inventory.getCurrentItem();
      if (this.objectMouseOver == null) {
         logger.warn("Null returned as 'hitResult', this shouldn't happen!");
      } else {
         switch(this.objectMouseOver.typeOfHit) {
         case ENTITY:
            if (this.playerController.func_178894_a(this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
               var1 = false;
            } else if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
               var1 = false;
            }
            break;
         case BLOCK:
            BlockPos var3 = this.objectMouseOver.func_178782_a();
            if (this.theWorld.getBlockState(var3).getBlock().getMaterial() != Material.air) {
               int var4 = var2 != null ? var2.stackSize : 0;
               if (this.playerController.func_178890_a(this.thePlayer, this.theWorld, var2, var3, this.objectMouseOver.field_178784_b, this.objectMouseOver.hitVec)) {
                  var1 = false;
                  this.thePlayer.swingItem();
               }

               if (var2 == null) {
                  return;
               }

               if (var2.stackSize == 0) {
                  this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
               } else if (var2.stackSize != var4 || this.playerController.isInCreativeMode()) {
                  this.entityRenderer.itemRenderer.resetEquippedProgress();
               }
            }
         }
      }

      if (var1) {
         ItemStack var5 = this.thePlayer.inventory.getCurrentItem();
         if (var5 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var5)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress2();
         }
      }

   }

   public static Map func_175596_ai() {
      HashMap var0 = Maps.newHashMap();
      var0.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
      var0.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
      var0.put("X-Minecraft-Version", "1.8");
      return var0;
   }

   public void loadWorld(WorldClient var1, String var2) {
      if (var1 == null) {
         NetHandlerPlayClient var3 = this.getNetHandler();
         if (var3 != null) {
            var3.cleanup();
         }

         if (this.theIntegratedServer != null && this.theIntegratedServer.func_175578_N()) {
            this.theIntegratedServer.initiateShutdown();
            this.theIntegratedServer.func_175592_a();
         }

         this.theIntegratedServer = null;
         this.guiAchievement.clearAchievements();
         this.entityRenderer.getMapItemRenderer().func_148249_a();
      }

      this.field_175622_Z = null;
      this.myNetworkManager = null;
      if (this.loadingScreen != null) {
         this.loadingScreen.resetProgressAndMessage(var2);
         this.loadingScreen.displayLoadingString("");
      }

      if (var1 == null && this.theWorld != null) {
         if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            this.mcResourcePackRepository.func_148529_f();
            this.func_175603_A();
         }

         this.setServerData((ServerData)null);
         this.integratedServerIsRunning = false;
      }

      this.mcSoundHandler.stopSounds();
      this.theWorld = var1;
      if (var1 != null) {
         if (this.renderGlobal != null) {
            this.renderGlobal.setWorldAndLoadRenderers(var1);
         }

         if (this.effectRenderer != null) {
            this.effectRenderer.clearEffects(var1);
         }

         if (this.thePlayer == null) {
            this.thePlayer = this.playerController.func_178892_a(var1, new StatFileWriter());
            this.playerController.flipPlayer(this.thePlayer);
         }

         this.thePlayer.preparePlayerToSpawn();
         var1.spawnEntityInWorld(this.thePlayer);
         this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
         this.playerController.setPlayerCapabilities(this.thePlayer);
         this.field_175622_Z = this.thePlayer;
      } else {
         this.saveLoader.flushCache();
         this.thePlayer = null;
      }

      System.gc();
      this.systemTime = 0L;
   }

   public void func_175601_h() {
      this.mcProfiler.startSection("display_update");
      Display.update();
      this.mcProfiler.endSection();
      this.func_175604_i();
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public ListenableFuture addScheduledTask(Runnable var1) {
      Validate.notNull(var1);
      return this.addScheduledTask(Executors.callable(var1));
   }

   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport var1) {
      var1.getCategory().addCrashSectionCallable("Launched Version", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000643";

         public Object call() throws Exception {
            return this.call();
         }

         {
            this.this$0 = var1;
         }

         public Object call1() {
            return this.call();
         }

         public String call() {
            return Minecraft.access$0(this.this$0);
         }
      });
      var1.getCategory().addCrashSectionCallable("LWJGL", new Callable(this) {
         private static final String __OBFID = "CL_00000644";
         final Minecraft this$0;

         {
            this.this$0 = var1;
         }

         public String call() {
            return Sys.getVersion();
         }

         public Object call() throws Exception {
            return this.call();
         }

         public Object call1() {
            return this.call();
         }
      });
      var1.getCategory().addCrashSectionCallable("OpenGL", new Callable(this) {
         private static final String __OBFID = "CL_00000645";
         final Minecraft this$0;

         {
            this.this$0 = var1;
         }

         public Object call1() {
            return this.call();
         }

         public String call() {
            return String.valueOf((new StringBuilder(String.valueOf(GL11.glGetString(7937)))).append(" GL version ").append(GL11.glGetString(7938)).append(", ").append(GL11.glGetString(7936)));
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var1.getCategory().addCrashSectionCallable("GL Caps", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000646";

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return OpenGlHelper.func_153172_c();
         }

         {
            this.this$0 = var1;
         }

         public Object call1() {
            return this.call();
         }
      });
      var1.getCategory().addCrashSectionCallable("Using VBOs", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000647";

         public Object call1() {
            return this.call();
         }

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return this.this$0.gameSettings.field_178881_t ? "Yes" : "No";
         }

         {
            this.this$0 = var1;
         }
      });
      var1.getCategory().addCrashSectionCallable("Is Modded", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000633";

         public Object call1() {
            return this.call();
         }

         public String call() {
            String var1 = ClientBrandRetriever.getClientModName();
            return !var1.equals("vanilla") ? String.valueOf((new StringBuilder("Definitely; Client brand changed to '")).append(var1).append("'")) : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
         }

         public Object call() throws Exception {
            return this.call();
         }

         {
            this.this$0 = var1;
         }
      });
      var1.getCategory().addCrashSectionCallable("Type", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000634";

         public String call() {
            return "Client (map_client.txt)";
         }

         public Object call1() {
            return this.call();
         }

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var1.getCategory().addCrashSectionCallable("Resource Packs", new Callable(this) {
         final Minecraft this$0;
         private static final String __OBFID = "CL_00000635";

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return this.this$0.gameSettings.resourcePacks.toString();
         }

         public Object call1() {
            return this.call();
         }

         {
            this.this$0 = var1;
         }
      });
      var1.getCategory().addCrashSectionCallable("Current Language", new Callable(this) {
         private static final String __OBFID = "CL_00000636";
         final Minecraft this$0;

         public String call() {
            return Minecraft.access$1(this.this$0).getCurrentLanguage().toString();
         }

         {
            this.this$0 = var1;
         }

         public Object call1() {
            return this.call();
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var1.getCategory().addCrashSectionCallable("Profiler Position", new Callable(this) {
         private static final String __OBFID = "CL_00000637";
         final Minecraft this$0;

         public Object call() {
            return this.call1();
         }

         public String call1() {
            return this.this$0.mcProfiler.profilingEnabled ? this.this$0.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
         }

         {
            this.this$0 = var1;
         }
      });
      if (this.theWorld != null) {
         this.theWorld.addWorldInfoToCrashReport(var1);
      }

      return var1;
   }

   public boolean isJava64bit() {
      return this.jvm64bit;
   }

   public String func_175600_c() {
      return this.launchedVersion;
   }

   public int getLimitFramerate() {
      return this.theWorld == null && this.currentScreen != null ? 30 : this.gameSettings.limitFramerate;
   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public static int getGLMaximumTextureSize() {
      for(int var0 = 16384; var0 > 0; var0 >>= 1) {
         GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
         int var1 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
         if (var1 != 0) {
            return var0;
         }
      }

      return -1;
   }

   private void resize(int var1, int var2) {
      this.displayWidth = Math.max(1, var1);
      this.displayHeight = Math.max(1, var2);
      if (this.currentScreen != null) {
         ScaledResolution var3 = new ScaledResolution(this, var1, var2);
         this.currentScreen.func_175273_b(this, var3.getScaledWidth(), var3.getScaledHeight());
      }

      this.loadingScreen = new LoadingScreenRenderer(this);
      this.updateFramebufferSize();
   }

   private void sendClickBlockToController(boolean var1) {
      if (!var1) {
         this.leftClickCounter = 0;
      }

      if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem()) {
         if (var1 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var2 = this.objectMouseOver.func_178782_a();
            if (this.theWorld.getBlockState(var2).getBlock().getMaterial() != Material.air && this.playerController.func_180512_c(var2, this.objectMouseOver.field_178784_b)) {
               this.effectRenderer.func_180532_a(var2, this.objectMouseOver.field_178784_b);
               this.thePlayer.swingItem();
            }
         } else {
            this.playerController.resetBlockRemoving();
         }
      }

   }

   public void setIngameFocus() {
      if (Display.isActive() && !this.inGameHasFocus) {
         this.inGameHasFocus = true;
         this.mouseHelper.grabMouseCursor();
         this.displayGuiScreen((GuiScreen)null);
         this.leftClickCounter = 10000;
      }

   }

   public SoundHandler getSoundHandler() {
      return this.mcSoundHandler;
   }

   public void crashed(CrashReport var1) {
      this.hasCrashed = true;
      this.crashReporter = var1;
   }

   public ISaveFormat getSaveLoader() {
      return this.saveLoader;
   }

   private void func_175595_al() {
      try {
         this.stream = new TwitchStream(this, (Property)Iterables.getFirst(this.twitchDetails.get("twitch_access_token"), (Object)null));
      } catch (Throwable var2) {
         this.stream = new NullStream(var2);
         logger.error("Couldn't initialize twitch stream");
      }

   }

   static {
      isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
      memoryReserve = new byte[10485760];
      macDisplayModes = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   }

   public RenderManager getRenderManager() {
      return this.renderManager;
   }

   public void freeMemory() {
      try {
         memoryReserve = new byte[0];
         this.renderGlobal.deleteAllDisplayLists();
      } catch (Throwable var3) {
      }

      try {
         System.gc();
         this.loadWorld((WorldClient)null);
      } catch (Throwable var2) {
      }

      System.gc();
   }

   public static Minecraft getMinecraft() {
      return theMinecraft;
   }

   private void startGame() throws LWJGLException {
      this.gameSettings = new GameSettings(this, this.mcDataDir);
      this.defaultResourcePacks.add(this.mcDefaultResourcePack);
      this.startTimerHackThread();
      if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
         this.displayWidth = this.gameSettings.overrideWidth;
         this.displayHeight = this.gameSettings.overrideHeight;
      }

      logger.info(String.valueOf((new StringBuilder("LWJGL Version: ")).append(Sys.getVersion())));
      this.func_175594_ao();
      this.func_175605_an();
      this.func_175609_am();
      OpenGlHelper.initializeTextures();
      this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
      this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.func_175608_ak();
      this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
      this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
      this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
      this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
      this.refreshResources();
      this.renderEngine = new TextureManager(this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.renderEngine);
      this.func_180510_a(this.renderEngine);
      this.func_175595_al();
      this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
      this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
      this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
      this.mcMusicTicker = new MusicTicker(this);
      this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
      if (this.gameSettings.language != null) {
         this.fontRendererObj.setUnicodeFlag(this.isUnicode());
         this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
      }

      this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
      this.mcResourceManager.registerReloadListener(this.fontRendererObj);
      this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
      this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
      this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
      AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat(this) {
         private static final String __OBFID = "CL_00000632";
         final Minecraft this$0;

         {
            this.this$0 = var1;
         }

         public String formatString(String var1) {
            try {
               return String.format(var1, GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindInventory.getKeyCode()));
            } catch (Exception var3) {
               return String.valueOf((new StringBuilder("Error: ")).append(var3.getLocalizedMessage()));
            }
         }
      });
      this.mouseHelper = new MouseHelper();
      this.checkGLError("Pre startup");
      GlStateManager.func_179098_w();
      GlStateManager.shadeModel(7425);
      GlStateManager.clearDepth(1.0D);
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.cullFace(1029);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      this.checkGLError("Startup");
      this.textureMapBlocks = new TextureMap("textures");
      this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
      this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
      this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      this.textureMapBlocks.func_174937_a(false, this.gameSettings.mipmapLevels > 0);
      this.modelManager = new ModelManager(this.textureMapBlocks);
      this.mcResourceManager.registerReloadListener(this.modelManager);
      this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
      this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
      this.itemRenderer = new ItemRenderer(this);
      this.mcResourceManager.registerReloadListener(this.renderItem);
      this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.entityRenderer);
      this.field_175618_aM = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.field_175618_aM);
      this.renderGlobal = new RenderGlobal(this);
      this.mcResourceManager.registerReloadListener(this.renderGlobal);
      this.guiAchievement = new GuiAchievement(this);
      GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
      this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
      this.checkGLError("Post startup");
      this.ingameGUI = new GuiIngame(this);
      Client.startup();
      if (this.serverName != null) {
         this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
      } else {
         this.displayGuiScreen(new GuiMainMenu());
      }

      this.renderEngine.deleteTexture(this.mojangLogo);
      this.mojangLogo = null;
      this.loadingScreen = new LoadingScreenRenderer(this);
      if (this.gameSettings.fullScreen && !this.fullscreen) {
         this.toggleFullscreen();
      }

      try {
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
      } catch (OpenGLException var2) {
         this.gameSettings.enableVsync = false;
         this.gameSettings.saveOptions();
      }

      this.renderGlobal.func_174966_b();
   }

   private void func_175605_an() throws LWJGLException {
      if (this.fullscreen) {
         Display.setFullscreen(true);
         DisplayMode var1 = Display.getDisplayMode();
         this.displayWidth = Math.max(1, var1.getWidth());
         this.displayHeight = Math.max(1, var1.getHeight());
      } else {
         Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
      }

   }

   public Framebuffer getFramebuffer() {
      return this.framebufferMc;
   }

   public void toggleFullscreen() {
      try {
         this.fullscreen = !this.fullscreen;
         this.gameSettings.fullScreen = this.fullscreen;
         if (this.fullscreen) {
            this.updateDisplayMode();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }
         } else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }
         }

         if (this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
         } else {
            this.updateFramebufferSize();
         }

         Display.setFullscreen(this.fullscreen);
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
         this.func_175601_h();
      } catch (Exception var2) {
         logger.error("Couldn't toggle fullscreen", var2);
      }

   }

   private ByteBuffer readImageToBuffer(InputStream var1) throws IOException {
      BufferedImage var2 = ImageIO.read(var1);
      int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
      int[] var5 = var3;
      int var6 = var3.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int var8 = var5[var7];
         var4.putInt(var8 << 8 | var8 >> 24 & 255);
      }

      var4.flip();
      return var4;
   }

   public boolean isFullScreen() {
      return this.fullscreen;
   }

   protected void func_175604_i() {
      if (!this.fullscreen && Display.wasResized()) {
         int var1 = this.displayWidth;
         int var2 = this.displayHeight;
         this.displayWidth = Display.getWidth();
         this.displayHeight = Display.getHeight();
         if (this.displayWidth != var1 || this.displayHeight != var2) {
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }

            this.resize(this.displayWidth, this.displayHeight);
         }
      }

   }

   private void updateFramebufferSize() {
      this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
      if (this.entityRenderer != null) {
         this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
      }

   }

   public void setServerData(ServerData var1) {
      this.currentServerData = var1;
   }

   public final boolean isDemo() {
      return this.isDemo;
   }

   private void clickMouse() {
      if (this.leftClickCounter <= 0) {
         this.thePlayer.swingItem();
         if (this.objectMouseOver == null) {
            logger.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.playerController.isNotCreative()) {
               this.leftClickCounter = 10;
            }
         } else {
            switch(this.objectMouseOver.typeOfHit) {
            case ENTITY:
               this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
               break;
            case BLOCK:
               BlockPos var1 = this.objectMouseOver.func_178782_a();
               if (this.theWorld.getBlockState(var1).getBlock().getMaterial() != Material.air) {
                  this.playerController.func_180511_b(var1, this.objectMouseOver.field_178784_b);
                  break;
               }
            case MISS:
            default:
               if (this.playerController.isNotCreative()) {
                  this.leftClickCounter = 10;
               }
            }
         }
      }

   }

   public boolean isSnooperEnabled() {
      return this.gameSettings.snooperEnabled;
   }

   public IResourceManager getResourceManager() {
      return this.mcResourceManager;
   }

   public void setIngameNotInFocus() {
      if (this.inGameHasFocus) {
         KeyBinding.unPressAllKeys();
         this.inGameHasFocus = false;
         this.mouseHelper.ungrabMouseCursor();
      }

   }

   public static boolean isAmbientOcclusionEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0;
   }

   static final class SwitchEnumMinecartType {
      private static final String __OBFID = "CL_00001959";
      static final int[] field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];
      static final int[] field_152390_a;

      static {
         try {
            field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
         } catch (NoSuchFieldError var7) {
         }

         try {
            field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
         }

         field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];

         try {
            field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
