package cc.novoline;

import cc.novoline.Initializer$Singleton;
import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.dropdown.DropdownGUI;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.ModuleManager$ModuleCreator;
import cc.novoline.modules.PlayerManager;
import cc.novoline.modules.binds.KeybindFactory;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.combat.AimAssist;
import cc.novoline.modules.combat.AntiBot;
import cc.novoline.modules.combat.AutoClicker;
import cc.novoline.modules.combat.AutoPot;
import cc.novoline.modules.combat.Criticals;
import cc.novoline.modules.combat.HitBox;
import cc.novoline.modules.combat.InfiniteAura;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.combat.Reach;
import cc.novoline.modules.combat.Velocity;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.exceptions.OutdatedConfigException;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.exploits.ClickTeleport;
import cc.novoline.modules.exploits.Disabler;
import cc.novoline.modules.exploits.NoFall;
import cc.novoline.modules.exploits.Phase;
import cc.novoline.modules.misc.AntiAtlas;
import cc.novoline.modules.misc.AutoHypixel;
import cc.novoline.modules.misc.AutoRegister;
import cc.novoline.modules.misc.AutoTool;
import cc.novoline.modules.misc.Debug;
import cc.novoline.modules.misc.FastPlace;
import cc.novoline.modules.misc.GuiMove;
import cc.novoline.modules.misc.Killsults;
import cc.novoline.modules.misc.LightningTracker;
import cc.novoline.modules.misc.MiddleClick;
import cc.novoline.modules.misc.NoEffects;
import cc.novoline.modules.misc.Notifier;
import cc.novoline.modules.misc.Respawn;
import cc.novoline.modules.misc.Spammer;
import cc.novoline.modules.misc.StaffAnalyzer;
import cc.novoline.modules.misc.Streamer;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.move.FastSneak;
import cc.novoline.modules.move.Flight;
import cc.novoline.modules.move.LongJump;
import cc.novoline.modules.move.NoSlow;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.move.Sprint;
import cc.novoline.modules.move.Step;
import cc.novoline.modules.move.Strafe;
import cc.novoline.modules.move.TargetStrafe;
import cc.novoline.modules.move.WaterWalk;
import cc.novoline.modules.player.AntiCactus;
import cc.novoline.modules.player.AntiObbyTrap;
import cc.novoline.modules.player.AntiVoid;
import cc.novoline.modules.player.AutoArmor;
import cc.novoline.modules.player.AutoHead;
import cc.novoline.modules.player.BedBreaker;
import cc.novoline.modules.player.ChestStealer;
import cc.novoline.modules.player.FastEat;
import cc.novoline.modules.player.Freecam;
import cc.novoline.modules.player.GameSpeed;
import cc.novoline.modules.player.InvManager;
import cc.novoline.modules.player.NoRotate;
import cc.novoline.modules.player.SpeedMine;
import cc.novoline.modules.visual.Animations;
import cc.novoline.modules.visual.Arrows;
import cc.novoline.modules.visual.Atmosphere;
import cc.novoline.modules.visual.BlockOutline;
import cc.novoline.modules.visual.Brightness;
import cc.novoline.modules.visual.Camera;
import cc.novoline.modules.visual.Chams;
import cc.novoline.modules.visual.ChestESP;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.Crosshair;
import cc.novoline.modules.visual.CustomHit;
import cc.novoline.modules.visual.DMGParticle;
import cc.novoline.modules.visual.ESP;
import cc.novoline.modules.visual.GlintColorize;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.ItemESP;
import cc.novoline.modules.visual.ItemPhysic;
import cc.novoline.modules.visual.JumpCircle;
import cc.novoline.modules.visual.KeyStrokes;
import cc.novoline.modules.visual.MoreBends;
import cc.novoline.modules.visual.Nametags;
import cc.novoline.modules.visual.NoTitle;
import cc.novoline.modules.visual.PlayerESP;
import cc.novoline.modules.visual.Radar;
import cc.novoline.modules.visual.TabGUI;
import cc.novoline.modules.visual.Tracers;
import cc.novoline.modules.visual.Trail;
import cc.novoline.modules.visual.Waypoints;
import cc.novoline.modules.visual.Wings;
import cc.novoline.modules.visual.XRay;
import cc.novoline.utils.DebugUtil;
import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationManager;
import cc.novoline.utils.tasks.TaskManager;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.Pa;
import net.aHK;
import net.aHW;
import net.aHv;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.EnumChatFormatting;
import net.skidunion.J;
import net.skidunion.aJ;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import viaversion.viafabric.ViaFabric;

public class Initializer {
   private static final Logger LOGGER = LogManager.getLogger();
   private boolean b = false;
   public double[] e = new double[]{0.42D, 0.753D, 1.001D, 1.084D, 1.006D};
   private List a = new CopyOnWriteArrayList();
   private List r = new CopyOnWriteArrayList();
   private List n = new CopyOnWriteArrayList();
   private Timer o = new Timer();
   private Timer i = new Timer();
   private Timer p = new Timer();
   private Timer q = new Timer();
   private int d;
   private int f;
   private int t;
   private float l;
   private float h;
   private int m;
   private Timer c = new Timer();
   private ArrayList g = new ArrayList();
   private boolean s;
   private final Queue j = new ConcurrentLinkedDeque();

   public static Initializer getInstance() {
      return Initializer$Singleton.access$000(Initializer$Singleton.INSTANCE);
   }

   public String Xor(Object var1, String var2) {
      StringBuilder var4 = new StringBuilder();
      Novoline.C();
      char[] var5 = var2.toCharArray();
      int var6 = 0;
      char[] var7 = var1.toString().toCharArray();
      int var8 = var7.length;
      int var9 = 0;
      if(var9 < var8) {
         char var10 = var7[var9];
         var4.append((char)(var10 ^ var5[var6 % var5.length]));
         ++var6;
         ++var9;
      }

      return var4.toString();
   }

   public void a(String var1) {
      int var2 = Novoline.C();
      Initializer var10000 = this;
      String var10001 = var1;
      String var10002 = "youreafaggotXD";

      try {
         String var3 = var10000.Xor(var10001, var10002);
         String var4 = var3.split(":")[0];
         String var5 = var3.split(":")[1];
         String[] var6 = System.getProperty("java.net.preferIPv4Stack", "true").split("-");
         if(var6[0].hashCode() != var1.hashCode() || Integer.parseInt(var6[1]) != "true".hashCode()) {
            throw new Throwable();
         }

         System.setProperty("java.net.preferIPv4Stack", var5);
         aJ var7 = new aJ();
         var4 = var7.a(var4);
         long var8 = Long.parseLong(var4.split(":")[0]);
         if(Novoline.getInstance().A() == null) {
            throw new Throwable();
         }

         if(!Novoline.getInstance().A().i()) {
            Novoline.getInstance().A().a((Object)"Protection is not passed");
            throw new Throwable();
         }

         if(System.currentTimeMillis() - var8 > 8000L) {
            Novoline.getInstance().A().a((Object)"Failed time check");
            throw new Throwable();
         }

         if(!Gui.protectionIsPassed) {
            Novoline.getInstance().A().a((Object)"Failed GUIProtection check");
            throw new Throwable();
         }

         if(!J.Q || J.am || J.aw.hashCode() != "dl$!#Oenu)!ujhnn#ER".hashCode() || !Novoline.getInstance().A().b() || !Novoline.getInstance().A().d().equals("true") || Novoline.getInstance().A().az != 16705.0D || J.N.hashCode() != "you\'re actually debugging those LOL".hashCode()) {
            throw new Throwable();
         }

         J.Q = false;
         J.am = true;
         J.aw = "literally nothing why do you keep looking";
         Novoline.getInstance().A().az = 1488.0D;
         Novoline.getInstance().A().aQ = false;
         Novoline.getInstance().A().aS = false;
         J.ai.clear();
         Novoline.getInstance().A().S = "there\'s nothing, what else would you expect?";
         J.N = "wait fr stop";
         RenderGlobal.ak = true;
         WorldClient.R = true;
         this.e = new double[]{0.41999998688698D, 0.7531999805212D, 1.00133597911215D, 1.06083597911215D, 0.9824359775862711D};

         try {
            Novoline.getInstance().setModuleManager(new ModuleManager(Novoline.getInstance(), 4));
            int var10 = Integer.parseInt(J.aK);
            this.b = var10 == 1 || var10 == 2;
            this.register("sprint", Sprint::<init>);
            this.register("hud", HUD::<init>);
            this.register("click_gui", ClickGUI::<init>);
            this.register("anti_bot", AntiBot::<init>);
            this.register("speed_mine", SpeedMine::<init>);
            this.register("auto_pot", AutoPot::<init>);
            this.register("kill_aura", KillAura::<init>);
            this.register("criticals", Criticals::<init>);
            this.register("reach", Reach::<init>);
            this.register("step", Step::<init>);
            this.register("water-walk", WaterWalk::<init>);
            this.register("no_slow", NoSlow::<init>);
            this.register("speed", Speed::<init>);
            this.register("velocity", Velocity::<init>);
            this.register("glow", ESP::<init>);
            this.register("chams", Chams::<init>);
            this.register("chest_esp", ChestESP::<init>);
            this.register("nametags", Nametags::<init>);
            this.register("no_fall", NoFall::<init>);
            this.register("xray", XRay::<init>);
            this.register("chest_stealer", ChestStealer::<init>);
            this.register("scaffold", Scaffold::<init>);
            this.register("auto_armor", AutoArmor::<init>);
            this.register("inv_manager", InvManager::<init>);
            this.register("auto-hypixel", AutoHypixel::<init>);
            this.register("gui_move", GuiMove::<init>);
            this.register("phase", Phase::<init>);
            this.register("auto_tool", AutoTool::<init>);
            this.register("animations", Animations::<init>);
            this.register("glint-colorize", GlintColorize::<init>);
            this.register("middle-click", MiddleClick::<init>);
            this.register("dmg-particles", DMGParticle::<init>);
            this.register("tracers", Tracers::<init>);
            this.register("respawn", Respawn::<init>);
            this.register("blink", Blink::<init>);
            this.register("anti_cactus", AntiCactus::<init>);
            this.register("no_effects", NoEffects::<init>);
            this.register("crosshair", Crosshair::<init>);
            this.register("brightness", Brightness::<init>);
            this.register("item_physic", ItemPhysic::<init>);
            this.register("item_esp", ItemESP::<init>);
            this.register("auto_clicker", AutoClicker::<init>);
            this.register("aim_assist", AimAssist::<init>);
            this.register("fast_place", FastPlace::<init>);
            this.register("radar", Radar::<init>);
            this.register("players_finder", Notifier::<init>);
            this.register("world_time", Atmosphere::<init>);
            this.register("killsults", Killsults::<init>);
            this.register("spammer", Spammer::<init>);
            this.register("player_esp", PlayerESP::<init>);
            this.register("hit_box", HitBox::<init>);
            this.register("waypoints", Waypoints::<init>);
            this.register("streamer", Streamer::<init>);
            this.register("antiobbytrap", AntiObbyTrap::<init>);
            this.register("freecam", Freecam::<init>);
            this.register("target-strafe", TargetStrafe::<init>);
            this.register("tab-gui", TabGUI::<init>);
            this.register("lightning-trecker", LightningTracker::<init>);
            this.register("camera", Camera::<init>);
            this.register("fast-sneak", FastSneak::<init>);
            this.register("arrows", Arrows::<init>);
            this.register("bed-breaker", BedBreaker::<init>);
            this.register("infinite-aura", InfiniteAura::<init>);
            this.register("flight", Flight::<init>);
            this.register("anti_void", AntiVoid::<init>);
            this.register("long-jump", LongJump::<init>);
            this.register("anti-alts", AntiAtlas::<init>);
            this.register("blocks-overlay", BlockOutline::<init>);
            this.register("game-speed", GameSpeed::<init>);
            this.register("no-rotate", NoRotate::<init>);
            this.register("windowed-screen", WindowedFullscreen::<init>);
            this.register("no-title", NoTitle::<init>);
            this.register("key-strokes", KeyStrokes::<init>);
            this.register("click-teleport", ClickTeleport::<init>);
            this.register("strafe", Strafe::<init>);
            this.register("jump-circle", JumpCircle::<init>);
            this.register("custom-hit-glint", CustomHit::<init>);
            this.register("staff-analyzer", StaffAnalyzer::<init>);
            this.register("train", Trail::<init>);
            this.register("disabler", Disabler::<init>);
            this.register("auto-register", AutoRegister::<init>);
            this.register("fast-eat", FastEat::<init>);
            this.register("auto-head", AutoHead::<init>);
            this.register("wings", Wings::<init>);
            this.register("mobends", MoreBends::<init>);
            if(this.b) {
               this.register("debug", Debug::<init>);
            }

            Novoline.getInstance().playerManager = new PlayerManager(Novoline.getInstance(), "players.novo");
            Novoline.getInstance().taskManager = new TaskManager();
            Novoline.getInstance().notificationManager = new NotificationManager();
            Novoline.getInstance().r = new Pa(Novoline.getInstance());
         } catch (Throwable var20) {
            throw new RuntimeException("An error occurred while loading managers", var20);
         }

         Minecraft.getInstance().niggerService.schedule(Initializer::lambda$onProtection$3, 0L, TimeUnit.SECONDS);
         EventManager.register(Novoline.getInstance().getModuleManager().getModule(ClickGUI.class));
         Killsults var25 = (Killsults)Novoline.getInstance().getModuleManager().getModule(Killsults.class);
         if(Files.notExists(var25.getPath(), new LinkOption[0])) {
            Files.createFile(var25.getPath(), new FileAttribute[0]);
         }

         if(Novoline.getInstance().version.toCharArray()[0] == 64) {
            Novoline.getInstance().version = (new SimpleDateFormat("MMddyy")).format(new Date());
         }

         (new ViaFabric()).onInitialize();
         Novoline.getInstance().getModuleManager().getBindManager().load();
         ConfigManager var11 = Novoline.getInstance().getModuleManager().getConfigManager();
         Path var12 = var11.getConfigPath("default");
         Path var13 = Paths.get(Novoline.getInstance().getPathString() + "scripts", new String[0]);
         ObjectIterator var14 = Novoline.getInstance().getModuleManager().getModuleManager().values().iterator();
         if(var14.hasNext()) {
            ModuleHolder var15 = (ModuleHolder)var14.next();
            AbstractModule var16 = var15.getModule();
            int var17 = ((ModuleKeybind)var16.getKeybind().get()).getKey();
            int var18 = var16.p();
            if(var17 == 0 && var18 != 0) {
               var16.setKeyBind(KeybindFactory.keyboard(var18));
            }
         }

         Novoline.getInstance().getModuleManager().getBindManager().save();
         if(!Files.exists(var13, new LinkOption[0])) {
            Files.createDirectory(var13, new FileAttribute[0]);
         }

         label1992: {
            try {
               Path var28 = var12;
               byte var29 = 0;

               try {
                  if(Files.exists(var28, new LinkOption[var29])) {
                     var11.load(var12, false);
                  }
                  break label1992;
               } catch (Throwable var21) {
                  var26 = var21;
               }
            } catch (OutdatedConfigException var22) {
               LOGGER.error("Default config is outdated. Deleting it...");
               Path var27 = var12;

               try {
                  Files.delete(var27);
               } catch (IOException var19) {
                  LOGGER.error("An I/O error occurred while deleting default config", var19);
               }
               break label1992;
            }

            LOGGER.error("An unexpected error occurred while loading default config", var26);
         }

         Display.setTitle("Novoline " + Novoline.getInstance().getVersion() + "\n");
         Novoline.getInstance().discordGUI = new DiscordGUI(Novoline.getInstance());
         Novoline.getInstance().dropDownGUI = new DropdownGUI();
         Novoline.getInstance().q = new aHW();
         Novoline.getInstance().d = new aHK();
         Novoline.getInstance().altRepositoryGUI = new AltRepositoryGUI(Novoline.getInstance());
         System.setProperty("java.net.preferIPv4Stack", "true");
      } catch (Throwable var23) {
         var23.printStackTrace();
         Minecraft.getInstance().shutdown();
         return;
      }

      Minecraft.getInstance().displayGuiScreen(new aHv());
   }

   private void register(@NotNull String var1, @NotNull ModuleManager$ModuleCreator var2) {
      Novoline.getInstance().getModuleManager().a(var1, var2, true);
   }

   private void a(Object... var1) {
      DebugUtil.print(var1);
   }

   private void a(Packet var1) {
      Minecraft.getInstance().getNetHandler().b(var1);
   }

   private void b(Packet var1) {
      Minecraft.getInstance().getNetHandler().sendPacketNoEvent(var1);
   }

   public void a(MotionUpdateEvent var1) {
      int var2 = Novoline.C();
      var1.setX(var1.getX() + (Minecraft.getInstance().player.ticksExisted % 2 == 0?ThreadLocalRandom.current().nextDouble(0.06D, 0.0625D):-ThreadLocalRandom.current().nextDouble(0.06D, 0.0625D)));
      var1.setZ(var1.getZ() + (Minecraft.getInstance().player.ticksExisted % 2 != 0?ThreadLocalRandom.current().nextDouble(0.06D, 0.0625D):-ThreadLocalRandom.current().nextDouble(0.06D, 0.0625D)));
      if(acE.b() == null) {
         ++var2;
         Novoline.b(var2);
      }

   }

   public void e(PacketEvent var1) {
      int var2 = Novoline.E();
      if(var1.getDirection().equals(PacketDirection.OUTGOING) && var1.getPacket() instanceof C03PacketPlayer$C06PacketPlayerPosLook) {
         C03PacketPlayer$C06PacketPlayerPosLook var3 = (C03PacketPlayer$C06PacketPlayerPosLook)var1.getPacket();
         if(Minecraft.getInstance().player.ticksExisted == 0) {
            ++this.m;
            if(this.m > 1) {
               var1.setCancelled(true);
            }
         }
      }

   }

   public void d(PacketEvent var1) {
      int var2 = Novoline.C();
      if(var1.getDirection().equals(PacketDirection.OUTGOING) && var1.getPacket() instanceof C0FPacketConfirmTransaction) {
         C0FPacketConfirmTransaction var3 = (C0FPacketConfirmTransaction)var1.getPacket();
         if(var3.getID() < 0) {
            ++this.d;
            if(this.d > 6) {
               this.r.add(var3);
               var1.setCancelled(true);
               if(this.i.delay(120.0D)) {
                  int var4 = this.f;
                  if(var4 < this.r.size()) {
                     this.b((Packet)this.r.get(this.f++));
                     ++var4;
                  }

                  this.i.reset();
               }
            }
         }
      }

   }

   public Timer b() {
      return this.o;
   }

   public Timer e() {
      return this.i;
   }

   public void c(PacketEvent var1) {
      int var2 = Novoline.E();
      if(var1.getDirection().equals(PacketDirection.OUTGOING) && var1.getPacket() instanceof C0FPacketConfirmTransaction) {
         C0FPacketConfirmTransaction var3 = (C0FPacketConfirmTransaction)var1.getPacket();
         ++this.d;
         if(this.d > 6) {
            this.r.add(var3);
            var1.setCancelled(true);
            if(this.o.delay(900.0D)) {
               C0FPacketConfirmTransaction var4 = (C0FPacketConfirmTransaction)this.r.get(this.f++);
               this.b((Packet)var4);
               this.o.reset();
            }

            if(this.i.delay(9000.0D)) {
               int var6 = this.f;
               if(var6 < this.r.size()) {
                  C0FPacketConfirmTransaction var5 = (C0FPacketConfirmTransaction)this.r.get(this.f++);
                  this.b((Packet)var5);
                  ++var6;
               }

               this.i.reset();
            }
         }
      }

   }

   public void a(PacketEvent var1) {
      int var2 = Novoline.C();
      if(var1.getDirection().equals(PacketDirection.OUTGOING) && var1.getPacket() instanceof C0FPacketConfirmTransaction) {
         C0FPacketConfirmTransaction var3 = (C0FPacketConfirmTransaction)var1.getPacket();
         this.r.add(var3);
         var1.setCancelled(true);
         if(this.i.delay(12000.0D)) {
            this.r.forEach(this::b);
            this.r.clear();
            this.i.reset();
         }
      }

   }

   public void b(PacketEvent var1) {
      Minecraft var3 = Minecraft.getInstance();
      double var4 = var3.player.posX;
      Novoline.C();
      double var6 = var3.player.posY;
      double var8 = var3.player.posZ;
      double var10 = Math.toRadians((double)var3.player.rotationYaw);
      double var12 = -Math.sin(var10);
      double var14 = Math.cos(var10);
      Packet var16 = var1.getPacket();
      if(this.c()) {
         if(var1.getDirection() == PacketDirection.OUTGOING) {
            if(var1.getPacket() instanceof C0FPacketConfirmTransaction || var1.getPacket() instanceof C00PacketKeepAlive) {
               short var17 = -1;
               if(var1.getPacket() instanceof C0FPacketConfirmTransaction) {
                  var17 = ((C0FPacketConfirmTransaction)var1.getPacket()).getID();
               }

               if(var17 != -1 && this.a(var17)) {
                  return;
               }

               var1.setCancelled(true);
               this.j.add(var1.getPacket());
            }

            if(!(var1.getPacket() instanceof C03PacketPlayer)) {
               return;
            }

            C03PacketPlayer var18 = (C03PacketPlayer)var1.getPacket();
            if(var3.player.ticksExisted % 25 == 0) {
               this.s = true;
               var18.setMoving(false);
               var18.setY(-0.03125D);
               var18.setOnGround(false);
            }
         }

         if(var1.getPacket() instanceof S08PacketPlayerPosLook && this.s) {
            S08PacketPlayerPosLook var19 = (S08PacketPlayerPosLook)var1.getPacket();
            this.s = false;
            var1.setCancelled(true);
            this.b((Packet)(new C03PacketPlayer$C06PacketPlayerPosLook(var19.getX(), var19.getY(), var19.getZ(), var19.getYaw(), var19.getPitch(), true)));
         }

      }
   }

   public void a(PlayerUpdateEvent var1) {
      int var2 = Novoline.E();
      if(!this.c()) {
         this.s = false;
         this.c.reset();
         this.j.clear();
      } else {
         if(this.c.delay(ThreadLocalRandom.current().nextDouble(250.0D, 260.0D))) {
            this.c.reset();
            if(!this.j.isEmpty()) {
               Minecraft.getInstance().player.connection.getNetworkManager().sendPacketNoEvent((Packet)this.j.poll());
            }
         }

      }
   }

   private boolean c() {
      Novoline.E();
      Minecraft var2 = Minecraft.getInstance();
      return var2.player != null && var2.player.ticksExisted > 5;
   }

   private boolean a(short var1) {
      int var2 = Novoline.C();
      return var1 > 0 && var1 < 100;
   }

   public void a(LoadWorldEvent var1) {
      int var2 = Novoline.E();
      if(!this.r.isEmpty()) {
         int var3 = this.f;
         if(var3 < this.r.size()) {
            this.b((Packet)this.r.get(var3));
            ++var3;
         }

         this.r.clear();
      }

      if(!this.a.isEmpty()) {
         int var5 = this.t;
         if(var5 < this.a.size()) {
            this.b((Packet)this.a.get(var5));
            ++var5;
         }

         this.a.clear();
      }

      this.n.clear();
      this.d = 0;
      this.f = 0;
      this.t = 0;
      this.m = 0;
   }

   public void a(GuiDisconnected var1) {
      if(this.f()) {
         CompletableFuture var2 = CompletableFuture.supplyAsync(Initializer::lambda$banType$4, ForkJoinPool.commonPool());
         var2.whenCompleteAsync(Initializer::lambda$banType$5);
      }

   }

   public boolean f() {
      Novoline.C();
      int var2 = Integer.parseInt(J.aK);
      return var2 == 1 || var2 == 2 || var2 == 4 || var2 == 5;
   }

   public boolean a() {
      return this.b;
   }

   private static void lambda$banType$5(GuiDisconnected var0, String var1, Throwable var2) {
      JSONObject var4 = new JSONObject(var1);
      Novoline.C();
      String var5 = var4.get("punishmentCategory").toString();
      var0.a(var5.equals("hacks")?EnumChatFormatting.RED + "WATCHDOG CHEAT DETECTION":(var5.equals("other")?EnumChatFormatting.GREEN + "Blacklisted Modifications":"Unable to get ban type."));
   }

   private static String lambda$banType$4(GuiDisconnected var0) {
      String var1 = (String)var0.c().get(5);
      String var2 = var1.split("§r§f")[1].replace("#", "");
      String var3 = Minecraft.getInstance().session.getProfile().getId().toString().replace("-", "");
      String var4 = "https://hypixel.net/api/players/" + var3 + "/ban/" + var2;
      CloseableHttpClient var5 = HttpClients.createDefault();
      HttpGet var6 = new HttpGet(var4);
      var6.setHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36");
      var6.setHeader("xf-api-key", "lQnlsEl1D9txtJqEVWVgnr1hNjmvTZa1");
      String var7 = "";

      try {
         CloseableHttpResponse var8 = var5.execute(var6);
         var7 = IOUtils.toString(var8.getEntity().getContent(), StandardCharsets.UTF_8);
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      return var7;
   }

   private static void lambda$onProtection$3() {
      Minecraft.getInstance().niggerService.scheduleAtFixedRate(Initializer::lambda$null$2, 0L, 1L, TimeUnit.SECONDS);
   }

   private static void lambda$null$2() {
      CompletableFuture var0 = CompletableFuture.supplyAsync(Initializer::lambda$null$0, ForkJoinPool.commonPool());
      var0.whenCompleteAsync(Initializer::lambda$null$1);
   }

   private static void lambda$null$1(List var0, Throwable var1) {
      Novoline.C();
      ObjectIterator var3 = Novoline.getInstance().getModuleManager().getModuleManager().values().iterator();
      if(var3.hasNext()) {
         ModuleHolder var4 = (ModuleHolder)var3.next();
         var4.getModule().c(false);
      }

      Iterator var7 = var0.iterator();
      if(var7.hasNext()) {
         String var8 = (String)var7.next();
         ObjectIterator var5 = Novoline.getInstance().getModuleManager().getModuleManager().values().iterator();
         if(var5.hasNext()) {
            ModuleHolder var6 = (ModuleHolder)var5.next();
            if(var6.getModule().getName().equalsIgnoreCase(var8)) {
               var6.getModule().c(true);
            }
         }
      }

   }

   private static List lambda$null$0() {
      CloseableHttpClient var0 = HttpClients.createDefault();
      HttpGet var1 = new HttpGet("http://novoline.software/detected_modules.html");
      CloseableHttpResponse var2 = null;
      CloseableHttpClient var10000 = var0;
      HttpGet var10001 = var1;

      try {
         var2 = var10000.execute(var10001);
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      String var3 = null;

      try {
         var3 = IOUtils.toString(var2.getEntity().getContent(), StandardCharsets.UTF_8);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return new CopyOnWriteArrayList(Arrays.asList(var3.split("\n")));
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
