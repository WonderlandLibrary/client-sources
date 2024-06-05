package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.BindEvent;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.SettingEvent;
import cc.novoline.events.events.SpawnCheckEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.dropdown.DropdownGUI;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.combat.AutoPot;
import cc.novoline.modules.combat.Criticals;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.exploits.Disabler;
import cc.novoline.modules.exploits.NoFall;
import cc.novoline.modules.move.Flight;
import cc.novoline.modules.move.LongJump;
import cc.novoline.modules.move.NoSlow;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.move.Step;
import cc.novoline.modules.move.WaterWalk;
import cc.novoline.modules.player.BedBreaker;
import cc.novoline.modules.player.ChestStealer;
import cc.novoline.modules.player.InvManager;
import cc.novoline.modules.visual.ClickGUI$1;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.Channels;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.C7;
import net.WT;
import net.aHK;
import net.aHM;
import net.aHW;
import net.aXg;
import net.akP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public final class ClickGUI extends AbstractModule {
   private Servers currentServer = Servers.NONE;
   private boolean Y;
   private boolean notify;
   private Channels channel = Channels.ALL;
   private Timer swapLobbyStopwatch = new Timer();
   private Timer Z = new Timer();
   private int ticks;
   private double W = 1.0D;
   private List N = new CopyOnWriteArrayList();
   private BlockPos ac;
   private ItemStack R;
   private float T;
   private float L;
   private int I;
   private int F;
   private int Q = 0;
   private int M;
   private int H = 0;
   private int aa;
   private int O;
   private int U;
   private int S;
   private String G = "";
   private boolean A;
   private boolean P;
   private List ag = new CopyOnWriteArrayList();
   private boolean K;
   private long V;
   @Property("blur")
   private final BooleanProperty blur = PropertyFactory.booleanFalse();
   @Property("close_previous")
   private final BooleanProperty ae = PropertyFactory.booleanFalse();
   @Property("mode")
   private final StringProperty C = PropertyFactory.createString("Compact").acceptableValues(new String[]{"Compact", "Windows", "Dropdown"});
   @Property("razer_fanboy")
   private final BooleanProperty E = PropertyFactory.booleanFalse();
   @Property("dsv")
   private final BooleanProperty B = PropertyFactory.booleanFalse();
   @Property("dpi_scale")
   private final DoubleProperty D = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(3.0D));
   @Property("bar-mode")
   private final StringProperty x = PropertyFactory.createString("Rainbow").acceptableValues(new String[]{"Rainbow", "Dynamic"});
   private static int[] J;

   public ClickGUI(ModuleManager var1) {
      super(var1, "ClickGUI", "Click Gui", 54, EnumModuleType.VISUALS, "");
      Manager.put(new Setting("CG_STYLE", "Style", SettingType.COMBOBOX, this, this.C));
      Manager.put(new Setting("CG_BAR_MODE", "Bar Color", SettingType.COMBOBOX, this, this.x, this::lambda$new$0));
      Manager.put(new Setting("CG_RFM", "Razer Fanboy Mode", SettingType.CHECKBOX, this, this.E, this::lambda$new$1));
      Manager.put(new Setting("CG_DSV", "Display Slider Values", SettingType.CHECKBOX, this, this.B, this::lambda$new$2));
      Manager.put(new Setting("CG_BLUR", "Blur", SettingType.CHECKBOX, this, this.blur, this::lambda$new$3));
      Manager.put(new Setting("CG_CP", "Close Previous", SettingType.CHECKBOX, this, this.ae, this::lambda$new$4));
      Manager.put(new Setting("CG_RL", "Restore layout", SettingType.BUTTON, this, this::lambda$new$5));
      Manager.put(new Setting("CG_SL", "Save Layout", SettingType.BUTTON, this, this::lambda$new$6));
      Manager.put(new Setting("CG_SL", "Load Layout", SettingType.BUTTON, this, this::lambda$new$7));
      Manager.put(new Setting("CG_DPI_SCALE", "DPI Scale", SettingType.SLIDER, this, this.D, 0.05D, this::lambda$new$8));
   }

   public DoubleProperty f() {
      return this.D;
   }

   public void a(double var1) {
      this.W = var1;
   }

   public double s() {
      return this.W;
   }

   public void onEnable() {
      int var1 = HUD.e();
      if(this.C.equals("Dropdown")) {
         this.mc.displayGuiScreen(this.novoline.getDropDownGUI());
      }

      if(this.C.equals("Windows")) {
         this.mc.displayGuiScreen(this.novoline.D());
      }

      this.mc.displayGuiScreen(this.novoline.h());
      this.toggle();
   }

   public void onDisable() {
      EventManager.register(this);
   }

   public int getGUIColor() {
      return ((HUD)this.getModule(HUD.class)).getHUDColor();
   }

   @EventTarget
   public void onBind(BindEvent var1) {
      this.mc.player.c(".bind " + var1.getModule().getName() + " " + var1.getKeyName());
   }

   @EventTarget
   public void onConnection(PacketEvent var1) {
      int var2 = HUD.h();
      if(var1.getDirection().equals(PacketDirection.OUTGOING)) {
         if(var1.getPacket() instanceof C00Handshake) {
            this.notify = !this.mc.isSingleplayer() && this.mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
         }

         if(this.notify && var1.getPacket() instanceof C03PacketPlayer) {
            if(ServerUtils.isHypixel()) {
               this.novoline.getNotificationManager().pop("Novoline", "Hypixel bypasses enabled!", NotificationType.INFO);
               this.sendPacketNoEvent(new C01PacketChatMessage("/language english"));
            }

            this.novoline.getNotificationManager().pop("Novoline", "Hypixel bypasses disabled! Contact staff", 6000, NotificationType.ERROR);
            this.notify = false;
         }

         if(var1.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement var3 = (C08PacketPlayerBlockPlacement)var1.getPacket();
            this.ac = var3.getPosition();
            this.R = var3.getStack();
         }
      }

   }

   @EventTarget
   public void a(SpawnCheckEvent var1) {
      int var2 = HUD.e();
      if(var1.getEntity() instanceof EntityPlayer && this.novoline.getPlayerManager().hasType(var1.getEntity().getName(), PlayerManager$EnumPlayerType.TARGET)) {
         this.novoline.getNotificationManager().pop("target detected: " + var1.getEntity().getName(), 3000, NotificationType.WARNING);
      }

      if(var1.getEntity() instanceof EntityMob || var1.getEntity() instanceof EntityAnimal) {
         BlockPos var3 = var1.getEntity().getPosition();
         double var4 = (double)var3.getX();
         double var6 = (double)var3.getY();
         double var8 = (double)var3.getZ();
         if(this.R != null && Item.b(this.R.getItem()) == 383 && !this.N.contains(Integer.valueOf(var1.getEntity().getEntityID())) && var4 == (double)this.ac.getX() && var6 == (double)(this.ac.getY() + 1) && var8 == (double)this.ac.getZ()) {
            this.N.add(Integer.valueOf(var1.getEntity().getEntityID()));
            this.ac = BlockPos.ORIGIN;
            this.R = null;
         }
      }

   }

   @EventTarget
   public void c(LoadWorldEvent var1) {
      HUD.e();
      this.N.clear();
      if(!ServerUtils.isHypixel() || this.mc.isSingleplayer()) {
         this.a(System.currentTimeMillis());
         this.d(0);
         this.c(0);
         this.b(0);
         this.a(0);
         this.A = false;
      }

      if(ServerUtils.isHypixel() && !this.mc.isSingleplayer()) {
         this.novoline.getTaskManager().queue(new C7(this, 2500));
      }

   }

   @EventTarget
   public void c(TickUpdateEvent var1) {
      int var2 = HUD.h();
      if(this.mc.world == null) {
         Class[] var3 = new Class[]{KillAura.class, ChestStealer.class, InvManager.class, Blink.class};
         int var4 = var3.length;
         int var5 = 0;
         if(var5 < var4) {
            Class var6 = var3[var5];
            if(this.getModule(var6).isEnabled()) {
               this.getModule(var6).toggle();
            }

            ++var5;
         }

         if(this.mc.timer.timerSpeed != 1.0F) {
            this.mc.timer.timerSpeed = 1.0F;
         }

         if(!this.mc.getNetHandler().getPlayerInfoMap().isEmpty()) {
            this.mc.getNetHandler().getPlayerInfoMap().clear();
         }
      }

   }

   @EventTarget
   public void b(SettingEvent var1) {
      int var2 = HUD.e();
      if(ServerUtils.isHypixel() && !this.mc.isSingleplayer()) {
         String var3 = var1.getSettingName();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1695009846:
            if(!var3.equals("NF_MODE")) {
               break;
            }

            var4 = 0;
         case -79002118:
            if(!var3.equals("STEP_HEIGHT")) {
               break;
            }

            var4 = 1;
         case 939466692:
            if(!var3.equals("LJ_MODE")) {
               break;
            }

            var4 = 2;
         case -1161873220:
            if(!var3.equals("SF_SPRINT_MODE")) {
               break;
            }

            var4 = 3;
         case -1810715742:
            if(!var3.equals("WW_MODE")) {
               break;
            }

            var4 = 4;
         case 558474764:
            if(!var3.equals("KA_ATTACK_EVENT")) {
               break;
            }

            var4 = 5;
         case 1420022181:
            if(!var3.equals("KA_AB_EVENT")) {
               break;
            }

            var4 = 6;
         case -1477981928:
            if(!var3.equals("KA_AB_MODE")) {
               break;
            }

            var4 = 7;
         case 408664374:
            if(!var3.equals("SF_PLACE_EVENT")) {
               break;
            }

            var4 = 8;
         case -983080508:
            if(!var3.equals("SF_RAY_TRACE")) {
               break;
            }

            var4 = 9;
         case 1146741435:
            if(var3.equals("SPEED_MODE")) {
               var4 = 10;
            }
         }

         switch(var4) {
         case 0:
            NoFall var5 = (NoFall)this.getModule(NoFall.class);
            if(var5.a().equals("Packet")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var5.getDisplayName(), "mode was set to PACKET due to unstable " + ((String)var5.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var5.a().set("Packet");
         case 1:
            Step var10 = (Step)this.getModule(Step.class);
            if((double)((Float)var10.a().get()).floatValue() <= 1.5D) {
               break;
            }

            this.novoline.getNotificationManager().pop(var10.getDisplayName(), "1.5 Height is maximum on Hypixel", 3500, NotificationType.WARNING);
            var10.a().set(Float.valueOf(1.5F));
         case 2:
            LongJump var11 = (LongJump)this.getModule(LongJump.class);
            if(((String)var11.e().get()).equals("Hypixel")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var11.getDisplayName(), "mode was set to HYPIXEL", 3500, NotificationType.WARNING);
            var11.e().set("Hypixel");
         case 3:
            Scaffold var12 = (Scaffold)this.getModule(Scaffold.class);
            if(!((String)var12.w().get()).equals("Vanilla")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "mode was set to SPOOF due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
            var12.w().set("Spoof");
         case 4:
            WaterWalk var13 = (WaterWalk)this.getModule(WaterWalk.class);
            if(!((String)var13.a().get()).equals("Dolphin")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "mode was set to SOLID due to unstable Dolphin bypass", 3500, NotificationType.WARNING);
            var13.a().set("Solid");
         case 5:
            KillAura var14 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var14.w().get()).equals("PRE")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var14.getDisplayName(), "attack event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var14.w().set("POST");
         case 6:
            KillAura var15 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var15.g().get()).equals("PRE")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var15.getDisplayName(), "autoblock event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var15.g().set("POST");
         case 7:
            KillAura var16 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var16.i().get()).equals("Vanilla")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var16.getDisplayName(), "autoblock mode was set to PACKET due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
            var16.i().set("Packet");
         case 8:
            Scaffold var17 = (Scaffold)this.getModule(Scaffold.class);
            if(!var17.C().equals("PRE")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var17.getDisplayName(), "place event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var17.C().set("POST");
         case 9:
            Scaffold var18 = (Scaffold)this.getModule(Scaffold.class);
            if(!((Boolean)var18.H().get()).booleanValue()) {
               break;
            }

            this.novoline.getNotificationManager().pop(var18.getDisplayName(), "Ray Trace was turned off due to unstable bypass", 3500, NotificationType.WARNING);
            var18.H().set(Boolean.valueOf(false));
         case 10:
            Speed var19 = (Speed)this.getModule(Speed.class);
            if(var19.a().equals("Vanilla")) {
               this.novoline.getNotificationManager().pop(var19.getDisplayName(), "mode was set to NCP due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
               var19.a().set("NCP");
            }
         }

         if(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Watchdog") && ((Disabler)this.getModule(Disabler.class)).a().equals("Normal")) {
            var3 = var1.getSettingName();
            var4 = -1;
            switch(var3.hashCode()) {
            case -847159088:
               if(!var3.equals("SF_TIMER_BOOST_MOVING")) {
                  break;
               }

               var4 = 0;
            case 1918800423:
               if(!var3.equals("SF_TIMER_BOOST_TOWER")) {
                  break;
               }

               var4 = 1;
            case 521099896:
               if(!var3.equals("SF_TIMER_BOOST_TOWERMOVE")) {
                  break;
               }

               var4 = 2;
            case -842976736:
               if(!var3.equals("SF_TIMER_BOOST_DOWNWARD")) {
                  break;
               }

               var4 = 3;
            case -1489874479:
               if(var3.equals("SPEED_TIMER_BOOST")) {
                  var4 = 4;
               }
            }

            switch(var4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
               FloatProperty var20 = (FloatProperty)var1.getNumberProperty();
               if(((Float)var20.get()).floatValue() > 1.0F) {
                  this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
                  var20.set(Float.valueOf(1.0F));
               }
            }
         }
      }

      if(ServerUtils.b()) {
         String var7 = var1.getSettingName();
         byte var9 = -1;
         switch(var7.hashCode()) {
         case 558474764:
            if(!var7.equals("KA_ATTACK_EVENT")) {
               break;
            }

            var9 = 0;
         case 1420022181:
            if(!var7.equals("KA_AB_EVENT")) {
               break;
            }

            var9 = 1;
         case -1477981928:
            if(!var7.equals("KA_AB_MODE")) {
               break;
            }

            var9 = 2;
         case 408664374:
            if(!var7.equals("SF_PLACE_EVENT")) {
               break;
            }

            var9 = 3;
         case 936962467:
            if(!var7.equals("SPEED_HEIGHT_MODE")) {
               break;
            }

            var9 = 4;
         case -61364301:
            if(!var7.equals("AP_MODE")) {
               break;
            }

            var9 = 5;
         case 939466692:
            if(!var7.equals("LJ_MODE")) {
               break;
            }

            var9 = 6;
         case -1374152082:
            if(!var7.equals("CRITICALS_MODE")) {
               break;
            }

            var9 = 7;
         case -1695009846:
            if(!var7.equals("NF_MODE")) {
               break;
            }

            var9 = 8;
         case 547557024:
            if(!var7.equals("KA_MAX_APS")) {
               break;
            }

            var9 = 9;
         case 767355022:
            if(!var7.equals("KA_MIN_APS")) {
               break;
            }

            var9 = 10;
         case 1171292897:
            if(!var7.equals("NS_VANILLA")) {
               break;
            }

            var9 = 11;
         case -926664686:
            if(!var7.equals("FLIGHT_MODE")) {
               break;
            }

            var9 = 12;
         case -1829034547:
            if(!var7.equals("FLIGHT_MOTION_SPEED")) {
               break;
            }

            var9 = 13;
         case -847159088:
            if(!var7.equals("SF_TIMER_BOOST_MOVING")) {
               break;
            }

            var9 = 14;
         case 1918800423:
            if(!var7.equals("SF_TIMER_BOOST_TOWER")) {
               break;
            }

            var9 = 15;
         case 521099896:
            if(!var7.equals("SF_TIMER_BOOST_TOWERMOVE")) {
               break;
            }

            var9 = 16;
         case -842976736:
            if(!var7.equals("SF_TIMER_BOOST_DOWNWARD")) {
               break;
            }

            var9 = 17;
         case -1489874479:
            if(var7.equals("SPEED_TIMER_BOOST")) {
               var9 = 18;
            }
         }

         switch(var9) {
         case 0:
            KillAura var21 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var21.w().get()).equals("POST")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var21.getDisplayName(), "attack event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var21.w().set("PRE");
         case 1:
            KillAura var22 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var22.g().get()).equals("POST")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var22.getDisplayName(), "autoblock event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var22.g().set("PRE");
         case 2:
            KillAura var23 = (KillAura)this.getModule(KillAura.class);
            if(!((String)var23.i().get()).equals("Packet")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var23.getDisplayName(), "autoblock mode was set to VANILLA due to unstable PACKET bypass", 3500, NotificationType.WARNING);
            var23.i().set("Vanilla");
         case 3:
            Scaffold var24 = (Scaffold)this.getModule(Scaffold.class);
            if(!((String)var24.C().get()).equals("POST")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var24.getDisplayName(), "place event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var24.C().set("PRE");
         case 4:
            Speed var25 = (Speed)this.getModule(Speed.class);
            if(!((String)var25.d().get()).equals("Reduced")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var25.getDisplayName(), "jump height was set to VANILLA due to unstable REDUCED bypass", 3500, NotificationType.WARNING);
            var25.d().set("Vanilla");
         case 5:
            AutoPot var26 = (AutoPot)this.getModule(AutoPot.class);
            if(!var26.a().equals("Edit")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var26.getDisplayName(), "mode was set to PACKET due to unstable EDIT bypass", 3500, NotificationType.WARNING);
            var26.a().set("Packet");
         case 6:
            LongJump var27 = (LongJump)this.getModule(LongJump.class);
            if(((String)var27.e().get()).equals("BlocksMC")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var27.getDisplayName(), "mode was set to BlocksMC", 3500, NotificationType.WARNING);
            var27.e().set("BlocksMC");
         case 7:
            Criticals var28 = (Criticals)this.getModule(Criticals.class);
            if(var28.a().equals("Packet")) {
               this.novoline.getNotificationManager().pop(var28.getDisplayName(), "mode was set to EDIT due to unstable PACKET bypass", 3500, NotificationType.WARNING);
               var28.a().set("Edit");
            }
         case 8:
            NoFall var29 = (NoFall)this.getModule(NoFall.class);
            if(var29.a().equals("Verus")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var29.getDisplayName(), "mode was set to VERUS due to unstable " + ((String)var29.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var29.a().set("Verus");
         case 9:
            KillAura var30 = (KillAura)this.getModule(KillAura.class);
            if(((Double)var30.getAps().get()).doubleValue() <= 10.0D) {
               break;
            }

            this.novoline.getNotificationManager().pop(var30.getDisplayName(), "10 is maximum aps on BlocksMC", 3500, NotificationType.WARNING);
            var30.getAps().set(Double.valueOf(10.0D));
         case 10:
            KillAura var31 = (KillAura)this.getModule(KillAura.class);
            if(((Double)var31.R().get()).doubleValue() <= 10.0D) {
               break;
            }

            this.novoline.getNotificationManager().pop(var31.getDisplayName(), "10 is maximum aps on BlocksMC", 3500, NotificationType.WARNING);
            var31.R().set(Double.valueOf(10.0D));
         case 11:
            NoSlow var32 = (NoSlow)this.getModule(NoSlow.class);
            if(((Boolean)var32.a().get()).booleanValue()) {
               break;
            }

            this.novoline.getNotificationManager().pop(var32.getDisplayName(), "VANILLA was turned on due to unstable PACKET bypass", 3500, NotificationType.WARNING);
            var32.a().set(Boolean.valueOf(true));
         case 12:
            Flight var33 = (Flight)this.getModule(Flight.class);
            if(var33.a().equals("Motion")) {
               break;
            }

            this.novoline.getNotificationManager().pop(var33.getDisplayName(), "Flight mode was set to MOTION due to unstable " + ((String)var33.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var33.a().set("Motion");
         case 13:
            Flight var34 = (Flight)this.getModule(Flight.class);
            if(((Double)var34.e().get()).doubleValue() > 4.0D) {
               this.novoline.getNotificationManager().pop(var34.getDisplayName(), "4.0 is maximum motion speed on BlocksMC", 3500, NotificationType.WARNING);
               var34.e().set(Double.valueOf(4.0D));
            }
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
            FloatProperty var35 = (FloatProperty)var1.getNumberProperty();
            if(((Float)var35.get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
               var35.set(Float.valueOf(1.0F));
            }
         }
      }

   }

   @EventTarget
   public void b(LoadWorldEvent var1) {
      int var2 = HUD.h();
      if(ServerUtils.isHypixel() && !this.mc.isSingleplayer()) {
         NoFall var3 = (NoFall)this.getModule(NoFall.class);
         Scaffold var4 = (Scaffold)this.getModule(Scaffold.class);
         Step var5 = (Step)this.getModule(Step.class);
         LongJump var6 = (LongJump)this.getModule(LongJump.class);
         WaterWalk var7 = (WaterWalk)this.getModule(WaterWalk.class);
         KillAura var8 = (KillAura)this.getModule(KillAura.class);
         Speed var9 = (Speed)this.getModule(Speed.class);
         if(!var3.a().equals("Packet")) {
            this.novoline.getNotificationManager().pop(var3.getDisplayName(), "mode was set to PACKET due to unstable " + ((String)var3.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var3.a().set("Packet");
         }

         if(((String)var4.w().get()).equals("Vanilla")) {
            this.novoline.getNotificationManager().pop(var4.getDisplayName(), "mode was set to SPOOF due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
            var4.w().set("Spoof");
         }

         if((double)((Float)var5.a().get()).floatValue() > 1.5D) {
            this.novoline.getNotificationManager().pop(var5.getDisplayName(), "1.5 Height is maximum on Hypixel", 3500, NotificationType.WARNING);
            var5.a().set(Float.valueOf(1.5F));
         }

         if(!((String)var6.e().get()).equals("Hypixel")) {
            this.novoline.getNotificationManager().pop(var6.getDisplayName(), "mode was set to HYPIXEL", 3500, NotificationType.WARNING);
            var6.e().set("Hypixel");
         }

         if(((String)var7.a().get()).equals("Dolphin")) {
            this.novoline.getNotificationManager().pop(var7.getDisplayName(), "mode was set to SOLID due to unstable Dolphin bypass", 3500, NotificationType.WARNING);
            var7.a().set("Solid");
         }

         if(((String)var8.w().get()).equals("PRE")) {
            this.novoline.getNotificationManager().pop(var8.getDisplayName(), "attack event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var8.w().set("POST");
         }

         if(((String)var8.g().get()).equals("PRE")) {
            this.novoline.getNotificationManager().pop(var8.getDisplayName(), "autoblock event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var8.g().set("POST");
         }

         if(((String)var8.i().get()).equals("Vanilla")) {
            this.novoline.getNotificationManager().pop(var8.getDisplayName(), "autoblock mode was set to PACKET due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
            var8.i().set("Packet");
         }

         if(var4.C().equals("PRE")) {
            this.novoline.getNotificationManager().pop(var4.getDisplayName(), "place event was set to POST due to unstable PRE bypass", 3500, NotificationType.WARNING);
            var4.C().set("POST");
         }

         if(((Boolean)var4.H().get()).booleanValue()) {
            this.novoline.getNotificationManager().pop(var4.getDisplayName(), "Ray Trace was turned off due to unstable bypass", 3500, NotificationType.WARNING);
            var4.H().set(Boolean.valueOf(false));
         }

         if(var9.a().equals("Vanilla")) {
            this.novoline.getNotificationManager().pop(var9.getDisplayName(), "mode was set to NCP due to unstable VANILLA bypass", 3500, NotificationType.WARNING);
            var9.a().set("NCP");
         }

         if(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Watchdog") && !((Disabler)this.getModule(Disabler.class)).a().equals("Advanced")) {
            if(((Float)var4.z().get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
               var4.z().set(Float.valueOf(1.0F));
            }

            if(((Float)var4.x().get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
               var4.x().set(Float.valueOf(1.0F));
            }

            if(((Float)var4.n().get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
               var4.n().set(Float.valueOf(1.0F));
            }

            if(((Float)var4.c().get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
               var4.c().set(Float.valueOf(1.0F));
            }

            if(((Float)var9.b().get()).floatValue() > 1.0F) {
               this.novoline.getNotificationManager().pop("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, NotificationType.WARNING);
               var9.b().set(Float.valueOf(1.0F));
            }
         }
      }

      if(ServerUtils.b()) {
         Scaffold var12 = (Scaffold)this.getModule(Scaffold.class);
         KillAura var13 = (KillAura)this.getModule(KillAura.class);
         Speed var14 = (Speed)this.getModule(Speed.class);
         AutoPot var15 = (AutoPot)this.getModule(AutoPot.class);
         LongJump var16 = (LongJump)this.getModule(LongJump.class);
         Criticals var17 = (Criticals)this.getModule(Criticals.class);
         NoFall var18 = (NoFall)this.getModule(NoFall.class);
         NoSlow var10 = (NoSlow)this.getModule(NoSlow.class);
         Flight var11 = (Flight)this.getModule(Flight.class);
         if(((String)var13.w().get()).equals("POST")) {
            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "attack event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var13.w().set("PRE");
         }

         if(((String)var13.g().get()).equals("POST")) {
            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "autoblock event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var13.g().set("PRE");
         }

         if(((String)var13.i().get()).equals("Packet")) {
            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "autoblock mode was set to VANILLA due to unstable PACKET bypass", 3500, NotificationType.WARNING);
            var13.i().set("Vanilla");
         }

         if(((String)var12.C().get()).equals("POST")) {
            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "place event was set to PRE due to unstable POST bypass", 3500, NotificationType.WARNING);
            var12.C().set("PRE");
         }

         if(((String)var14.d().get()).equals("Reduced")) {
            this.novoline.getNotificationManager().pop(var14.getDisplayName(), "jump height was set to VANILLA due to unstable REDUCED bypass", 3500, NotificationType.WARNING);
            var14.d().set("Vanilla");
         }

         if(var15.a().equals("Edit")) {
            this.novoline.getNotificationManager().pop(var15.getDisplayName(), "mode was set to PACKET due to unstable EDIT bypass", 3500, NotificationType.WARNING);
            var15.a().set("Packet");
         }

         if(!((String)var16.e().get()).equals("Verus")) {
            this.novoline.getNotificationManager().pop(var16.getDisplayName(), "mode was set to Verus", 3500, NotificationType.WARNING);
            var16.e().set("Verus");
         }

         if(var17.a().equals("Packet")) {
            this.novoline.getNotificationManager().pop(var17.getDisplayName(), "mode was set to EDIT due to unstable PACKET bypass", 3500, NotificationType.WARNING);
            var17.a().set("Edit");
         }

         if(!var18.a().equals("Verus")) {
            this.novoline.getNotificationManager().pop(var18.getDisplayName(), "mode was set to VERUS due to unstable " + ((String)var18.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var18.a().set("Verus");
         }

         if(!((Boolean)var10.a().get()).booleanValue()) {
            this.novoline.getNotificationManager().pop(var10.getDisplayName(), "VANILLA was turned on due to unstable PACKET bypass", 3500, NotificationType.WARNING);
            var10.a().set(Boolean.valueOf(true));
         }

         if(!var11.a().equals("Motion")) {
            this.novoline.getNotificationManager().pop(var11.getDisplayName(), "Flight mode was set to MOTION due to unstable " + ((String)var11.a().get()).toUpperCase() + " bypass", 3500, NotificationType.WARNING);
            var11.a().set("Motion");
         }

         if(((Double)var11.e().get()).doubleValue() > 4.0D) {
            this.novoline.getNotificationManager().pop(var11.getDisplayName(), "4.0 is maximum motion speed on BlocksMC", 3500, NotificationType.WARNING);
            var11.e().set(Double.valueOf(4.0D));
         }

         if(((Float)var12.z().get()).floatValue() > 1.0F) {
            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
            var12.z().set(Float.valueOf(1.0F));
         }

         if(((Float)var12.x().get()).floatValue() > 1.0F) {
            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
            var12.x().set(Float.valueOf(1.0F));
         }

         if(((Float)var12.n().get()).floatValue() > 1.0F) {
            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
            var12.n().set(Float.valueOf(1.0F));
         }

         if(((Float)var12.c().get()).floatValue() > 1.0F) {
            this.novoline.getNotificationManager().pop(var12.getDisplayName(), "Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
            var12.c().set(Float.valueOf(1.0F));
         }

         if(((Float)var14.b().get()).floatValue() > 1.0F) {
            this.novoline.getNotificationManager().pop(var14.getDisplayName(), "Timer does not bypass on BlocksMC", 3500, NotificationType.WARNING);
            var14.b().set(Float.valueOf(1.0F));
         }

         if(((Double)var13.R().get()).doubleValue() > 10.0D) {
            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "10 is maximum aps on BlocksMC", 3500, NotificationType.WARNING);
            var13.R().set(Double.valueOf(10.0D));
         }

         if(((Double)var13.getAps().get()).doubleValue() > 10.0D) {
            this.novoline.getNotificationManager().pop(var13.getDisplayName(), "10 is maximum aps on BlocksMC", 3500, NotificationType.WARNING);
            var13.getAps().set(Double.valueOf(10.0D));
         }
      }

   }

   @EventTarget
   public void a(SettingEvent var1) {
      int var2 = HUD.e();
      if(var1.getSettingName().equals("RANGE") || var1.getSettingName().equals("BLOCK_RANGE") || var1.getSettingName().equals("WALL_RANGE")) {
         DoubleProperty var3 = ((KillAura)this.getModule(KillAura.class)).getRange();
         DoubleProperty var4 = ((KillAura)this.getModule(KillAura.class)).getWallRange();
         if(((Double)var4.get()).doubleValue() > ((Double)var3.get()).doubleValue()) {
            var4.set((Number)var3.get());
         }
      }

      String var9 = var1.getSettingName();
      byte var10 = -1;
      switch(var9.hashCode()) {
      case 767355022:
         if(!var9.equals("KA_MIN_APS")) {
            break;
         }

         var10 = 0;
      case 547557024:
         if(!var9.equals("KA_MAX_APS")) {
            break;
         }

         var10 = 1;
      case 174795:
         if(!var9.equals("BLOCK_RANGE")) {
            break;
         }

         var10 = 2;
      case 77742365:
         if(!var9.equals("RANGE")) {
            break;
         }

         var10 = 3;
      case 2574669:
         if(!var9.equals("TH_X")) {
            break;
         }

         var10 = 4;
      case 2574670:
         if(!var9.equals("TH_Y")) {
            break;
         }

         var10 = 5;
      case -314268031:
         if(!var9.equals("KA_FILTER")) {
            break;
         }

         var10 = 6;
      case -2103527946:
         if(var9.equals("CG_STYLE")) {
            var10 = 7;
         }
      }

      switch(var10) {
      case 0:
         KillAura var5 = (KillAura)this.getModule(KillAura.class);
         if(((Double)var5.R().get()).doubleValue() <= ((Double)var5.getAps().get()).doubleValue()) {
            break;
         }

         var5.getAps().set((Number)var5.R().get());
      case 1:
         KillAura var11 = (KillAura)this.getModule(KillAura.class);
         if(((Double)var11.getAps().get()).doubleValue() >= ((Double)var11.R().get()).doubleValue()) {
            break;
         }

         var11.R().set((Number)var11.getAps().get());
      case 2:
      case 3:
         DoubleProperty var12 = ((KillAura)this.getModule(KillAura.class)).getRange();
         DoubleProperty var6 = ((KillAura)this.getModule(KillAura.class)).getBlockRange();
         if(((Double)var6.get()).doubleValue() < ((Double)var12.get()).doubleValue()) {
            var12.set((Number)var6.get());
         }

         if(((Double)var12.get()).doubleValue() <= ((Double)var6.get()).doubleValue()) {
            break;
         }

         var6.set((Number)var12.get());
      case 4:
         KillAura var7 = (KillAura)this.getModule(KillAura.class);
         ScaledResolution var8 = new ScaledResolution(Minecraft.getInstance());
         if(((Integer)var7.getThx().get()).intValue() <= var8.getScaledWidth() - 50) {
            break;
         }

         var7.getThx().set(Integer.valueOf(var8.getScaledWidth() - 50));
      case 5:
         KillAura var13 = (KillAura)this.getModule(KillAura.class);
         ScaledResolution var14 = new ScaledResolution(Minecraft.getInstance());
         if(((Integer)var13.getThy().get()).intValue() <= var14.getScaledHeight() - 50) {
            break;
         }

         var13.getThy().set(Integer.valueOf(var14.getScaledHeight() - 50));
      case 6:
         if(!var1.getListProperty().contains("Teams") || ((KillAura)this.getModule(KillAura.class)).getTarget() == null) {
            break;
         }

         ((KillAura)this.getModule(KillAura.class)).setTarget((Entity)null);
      case 7:
         if(this.C.equals("Dropdown")) {
            this.mc.displayGuiScreen(this.novoline.getDropDownGUI());
         }

         if(this.C.equals("Windows")) {
            this.mc.displayGuiScreen(this.novoline.D());
         }

         this.mc.displayGuiScreen(this.novoline.h());
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      int var2 = HUD.e();
      if(this.mc.world != null && this.mc.player != null && !this.mc.isSingleplayer() && (ServerUtils.isHypixel() || ServerUtils.a() || ServerUtils.g() || ServerUtils.b())) {
         if(!this.isEnabled(Disabler.class)) {
            ((Disabler)this.getModule(Disabler.class)).toggle();
         }

         if(ServerUtils.isHypixel()) {
            ((Disabler)this.getModule(Disabler.class)).b().set("Watchdog");
         }

         if(ServerUtils.b()) {
            ((Disabler)this.getModule(Disabler.class)).b().set("Verus");
         }

         if(ServerUtils.a() || ServerUtils.g()) {
            ((Disabler)this.getModule(Disabler.class)).b().set("Simple");
         }
      }

   }

   @EventTarget
   public void c(PacketEvent var1) {
      int var2 = HUD.h();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(var1.getPacket() instanceof S2EPacketCloseWindow) {
            S2EPacketCloseWindow var3 = (S2EPacketCloseWindow)var1.getPacket();
            if(this.mc.currentScreen instanceof DiscordGUI || this.mc.currentScreen instanceof aHM || this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof DropdownGUI || this.mc.currentScreen instanceof aHW || this.mc.currentScreen instanceof aHK) {
               var1.setCancelled(true);
            }
         }

         if(var1.getPacket() instanceof S00PacketDisconnect && !this.novoline.getTaskManager().getFutureTasks().isEmpty()) {
            this.novoline.getTaskManager().getFutureTasks().clear();
         }

         if(var1.getPacket() instanceof S02PacketChat) {
            S02PacketChat var7 = (S02PacketChat)var1.getPacket();
            if(var7.getChatComponent().getFormattedText().equals("§r§aSelected language: §r§eEnglish§r")) {
               var1.setCancelled(true);
            }
         }

         if(var1.getPacket() instanceof S2DPacketOpenWindow) {
            S2DPacketOpenWindow var8 = (S2DPacketOpenWindow)var1.getPacket();
            if(var8.getWindowTitle().getFormattedText().equals("Select Language§r")) {
               var1.setCancelled(true);
            }
         }

         if(ServerUtils.isHypixel() && var1.getPacket() instanceof S3DPacketDisplayScoreboard) {
            S3DPacketDisplayScoreboard var9 = (S3DPacketDisplayScoreboard)var1.getPacket();
            String var4 = var9.getServerName();
            if(var4.equalsIgnoreCase("Mw")) {
               this.currentServer = Servers.MW;
            }

            if(var4.equalsIgnoreCase("§e§lHYPIXEL")) {
               this.currentServer = Servers.UHC;
            }

            if(var4.equalsIgnoreCase("SForeboard")) {
               this.currentServer = Servers.SW;
            }

            if(var4.equalsIgnoreCase("BForeboard")) {
               this.currentServer = Servers.BW;
            }

            if(var4.equalsIgnoreCase("PreScoreboard")) {
               this.currentServer = Servers.PRE;
            }

            if(var4.equalsIgnoreCase("Duels")) {
               this.currentServer = Servers.DUELS;
            }

            if(var4.equalsIgnoreCase("Pit")) {
               this.currentServer = Servers.PIT;
            }

            if(var4.equalsIgnoreCase("Blitz SG")) {
               this.currentServer = Servers.SG;
            }

            if(var4.equalsIgnoreCase("MurderMystery")) {
               this.currentServer = Servers.MM;
            }

            if(!var4.contains("health") && !var4.contains("✫")) {
               this.currentServer = Servers.NONE;
            }
         }

         if(this.A && var1.getPacket() instanceof S02PacketChat) {
            S02PacketChat var10 = (S02PacketChat)var1.getPacket();
            String var15 = var10.getChatComponent().getFormattedText();
            if(var15.startsWith("§aYour new API key is §r§b")) {
               String var5 = var15.split("§aYour new API key is §r§b")[1].split("§r")[0];
               this.e(var5);
               this.P = true;
            }
         }

         if(var1.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle var11 = (S45PacketTitle)var1.getPacket();
            if(var11.getMessage().getUnformattedText().equals("§6§lVICTORY!")) {
               ++this.S;
            }
         }

         if(var1.getPacket() instanceof S2FPacketSetSlot) {
            S2FPacketSetSlot var12 = (S2FPacketSetSlot)var1.getPacket();
            if(var12.getItem().getDisplayName().contains("§") && var12.getItem().getDisplayName().contains("Spectator")) {
               this.K = true;
            }
         }

         if(!ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY)) {
            if(this.K || !(var1.getPacket() instanceof S38PacketPlayerListItem)) {
               return;
            }

            S38PacketPlayerListItem var13 = (S38PacketPlayerListItem)var1.getPacket();
            Iterator var16 = var13.playersDataList().iterator();
            if(var16.hasNext()) {
               aXg var17 = (aXg)var16.next();
               EntityPlayer var6 = this.mc.world.getPlayerEntityByUUID(var17.a().getId());
               if(var13.getAction().equals(S38PacketPlayerListItem$Action.REMOVE_PLAYER) && !var6.equals(this.mc.player) && var17.a().getName() == null && var6.getHealth() < var6.getMaxHealth() && this.ag.contains(var6)) {
                  ++this.U;
               }
            }
         }

         if(!this.K) {
            return;
         }

         this.K = false;
      }

      if(var1.getPacket() instanceof C01PacketChatMessage) {
         C01PacketChatMessage var14 = (C01PacketChatMessage)var1.getPacket();
         if(var14.getMessage().startsWith("/language")) {
            var1.setCancelled(true);
         }
      }

   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      int var2 = HUD.h();
      if(this.isEnabled(KillAura.class) && ((KillAura)this.getModule(KillAura.class)).shouldAttack()) {
         KillAura var3 = (KillAura)this.getModule(KillAura.class);
         if(var3.getTarget() != null && var3.getTarget() instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var3.getTarget();
            if(!this.ag.contains(var4)) {
               this.ag.add(var4);
            }
         }
      }

   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int var2 = HUD.e();
      if(ServerUtils.isHypixel() && !this.mc.isSingleplayer() && this.Z.delay(5000.0D)) {
         CompletableFuture var3 = CompletableFuture.supplyAsync(this::lambda$onMotion$9, ForkJoinPool.commonPool());
         var3.whenCompleteAsync(this::lambda$onMotion$10);
         this.Z.reset();
      }

   }

   @EventTarget
   public void inGameTime(TickUpdateEvent var1) {
      int var2 = HUD.h();
      if(ServerUtils.isHypixel() && !ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY) && !ServerUtils.serverIs(Servers.NONE)) {
         ++this.ticks;
      }

      if(this.ticks > 0) {
         this.ticks = 0;
      }

   }

   @EventTarget
   public void b(PlayerUpdateEvent var1) {
      int var2 = HUD.e();
      if(ServerUtils.serverIs(Servers.PRE)) {
         this.Y = true;
      }

      if(this.Y && ServerUtils.serverIs(Servers.BW)) {
         ((BedBreaker)this.getModule(BedBreaker.class)).setWhiteListed((BlockPos)null);
         Novoline.getInstance().getTaskManager().queue(new ClickGUI$1(this, 500));
         this.Y = false;
      }

      int var3 = 0;
      if(var3 < 9) {
         if(this.mc.player.inventory.getStackInSlot(var3) != null && this.mc.player.inventory.getStackInSlot(var3).stackSize == 0) {
            this.mc.player.inventory.removeStackFromSlot(var3);
         }

         ++var3;
      }

   }

   @EventTarget
   public void onAutoDisable(PacketEvent var1) {
      int var2 = HUD.h();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(!ServerUtils.isHypixel()) {
            return;
         }

         if(var1.getPacket() instanceof S02PacketChat) {
            S02PacketChat var3 = (S02PacketChat)var1.getPacket();
            String var4 = var3.getChatComponent().getFormattedText();
            if(var4.startsWith("§aOpened a chat conversation with")) {
               this.channel = Channels.PM;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6ALL§r§a channel§r")) {
               this.channel = Channels.ALL;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6PARTY§r§a channel§r")) {
               this.channel = Channels.PARTY;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6GUILD§r§a channel§r")) {
               this.channel = Channels.GUILD;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
               var1.setCancelled(true);
            }

            if(!this.channel.equals(Channels.GUILD) && var4.startsWith("§r§2Guild") && var4.contains(this.mc.player.getDisplayName().getFormattedText())) {
               this.channel = Channels.GUILD;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
            }

            if(!this.channel.equals(Channels.PARTY) && var4.startsWith("§r§9Party") && var4.contains(this.mc.player.getDisplayName().getFormattedText())) {
               this.channel = Channels.PARTY;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
            }

            if(!this.channel.equals(Channels.PM) && var4.startsWith("§dTo")) {
               this.channel = Channels.PM;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
            }

            if(this.channel.equals(Channels.PARTY) && var4.contains(this.mc.player.getName()) && var4.endsWith("§r§ehas disbanded the party!§r")) {
               this.sendPacketNoEvent(new C01PacketChatMessage("/chat all"));
            }

            if(!this.channel.equals(Channels.ALL) && var4.equals("§cThe conversation you were in expired and you have been moved back to the ALL channel.§r")) {
               this.channel = Channels.ALL;
               this.novoline.getNotificationManager().pop("Hypixel", "You are now in the " + this.channelName() + " channel", 3000, NotificationType.INFO);
            }
         }

         if(var1.getPacket() instanceof S45PacketTitle) {
            S45PacketTitle var5 = (S45PacketTitle)var1.getPacket();
            if(var5.getType().equals(S45PacketTitle$Type.TITLE)) {
               String var10 = var5.getMessage().getUnformattedText();
               if(var10.equals("§6§lVICTORY!") || var10.equals("GAME OVER!")) {
                  if(this.isEnabled(KillAura.class) && ((KillAura)this.getModule(KillAura.class)).getAutoDisable().contains("Game End")) {
                     Novoline.getInstance().getNotificationManager().pop(((KillAura)this.getModule(KillAura.class)).getName() + " was disabled, because game has ended", 1500, NotificationType.WARNING);
                     ((KillAura)this.getModule(KillAura.class)).toggle();
                  }

                  if(this.isEnabled(InvManager.class) && ((InvManager)this.getModule(InvManager.class)).getAutoDisable().contains("Game End")) {
                     Novoline.getInstance().getNotificationManager().pop(((InvManager)this.getModule(InvManager.class)).getName() + " was disabled, because game has ended", 1500, NotificationType.WARNING);
                     ((InvManager)this.getModule(InvManager.class)).toggle();
                  }

                  if(this.isEnabled(ChestStealer.class) && ((ChestStealer)this.getModule(ChestStealer.class)).getAutoDisable().contains("Game End")) {
                     Novoline.getInstance().getNotificationManager().pop(((ChestStealer)this.getModule(ChestStealer.class)).getName() + " was disabled, because game has ended", 1500, NotificationType.WARNING);
                     ((ChestStealer)this.getModule(ChestStealer.class)).toggle();
                  }
               }
            }
         }

         if(!(var1.getPacket() instanceof S2FPacketSetSlot)) {
            return;
         }

         S2FPacketSetSlot var6 = (S2FPacketSetSlot)var1.getPacket();
         if(var6.getItem().getDisplayName().contains("§") && var6.getItem().getDisplayName().contains("Spectator")) {
            if(this.isEnabled(KillAura.class) && ((KillAura)this.getModule(KillAura.class)).getAutoDisable().contains("Death")) {
               Novoline.getInstance().getNotificationManager().pop(((KillAura)this.getModule(KillAura.class)).getName() + " was disabled, because of player death", 1500, NotificationType.WARNING);
               ((KillAura)this.getModule(KillAura.class)).toggle();
            }

            if(this.isEnabled(InvManager.class) && ((InvManager)this.getModule(InvManager.class)).getAutoDisable().contains("Death")) {
               Novoline.getInstance().getNotificationManager().pop(((InvManager)this.getModule(InvManager.class)).getName() + " was disabled, because of player death", 1500, NotificationType.WARNING);
               ((InvManager)this.getModule(InvManager.class)).toggle();
            }

            if(this.isEnabled(ChestStealer.class) && ((ChestStealer)this.getModule(ChestStealer.class)).getAutoDisable().contains("Death")) {
               Novoline.getInstance().getNotificationManager().pop(((ChestStealer)this.getModule(ChestStealer.class)).getName() + " was disabled, because of player death", 1500, NotificationType.WARNING);
               ((ChestStealer)this.getModule(ChestStealer.class)).toggle();
            }
         }

         if(ServerUtils.serverIs(Servers.LOBBY) && var6.getItem().getItem() instanceof ItemEditableBook) {
            var1.setCancelled(true);
         }
      }

      if(ServerUtils.isHypixel() && !ServerUtils.serverIs(Servers.LOBBY) || ServerUtils.b()) {
         if(var1.getPacket() instanceof C0DPacketCloseWindow) {
            C0DPacketCloseWindow var7 = (C0DPacketCloseWindow)var1.getPacket();
            if(var7.getWindowId() == 0) {
               var1.setCancelled(true);
            }
         }

         if(var1.getPacket() instanceof C16PacketClientStatus) {
            C16PacketClientStatus var8 = (C16PacketClientStatus)var1.getPacket();
            if(var8.getStatus().equals(C16PacketClientStatus$EnumState.OPEN_INVENTORY_ACHIEVEMENT)) {
               var1.setCancelled(true);
            }
         }
      }

      if(ServerUtils.isHypixel() && !ServerUtils.serverIs(Servers.LOBBY) && var1.getPacket() instanceof C0EPacketClickWindow && this.mc.player.getLastTickDistance() > 0.075D) {
         C0EPacketClickWindow var9 = (C0EPacketClickWindow)var1.getPacket();
         int var11 = ThreadLocalRandom.current().nextInt(1, 10);
         this.sendPacketNoEvent(new C0EPacketClickWindow(var11, -999, 0, -4, (ItemStack)null, var9.getActionNumber()));
         if(var9.getWindowId() == 0) {
            this.sendPacketNoEvent(new C0DPacketCloseWindow(var11));
         }
      }

   }

   @EventTarget
   public void onLoadWorld(LoadWorldEvent var1) {
      int var2 = HUD.h();
      if(((KillAura)this.getModule(KillAura.class)).isEnabled() && ((KillAura)this.getModule(KillAura.class)).getAutoDisable().contains("World Change")) {
         this.novoline.getNotificationManager().pop(((KillAura)this.getModule(KillAura.class)).getName() + " was disabled, because world changed", 1500, NotificationType.WARNING);
         ((KillAura)this.getModule(KillAura.class)).toggle();
      }

      if(((InvManager)this.getModule(InvManager.class)).isEnabled() && ((InvManager)this.getModule(InvManager.class)).getAutoDisable().contains("World Change")) {
         this.novoline.getNotificationManager().pop(((InvManager)this.getModule(InvManager.class)).getName() + " was disabled, because world changed", 1500, NotificationType.WARNING);
         ((InvManager)this.getModule(InvManager.class)).toggle();
      }

      if(((ChestStealer)this.getModule(ChestStealer.class)).isEnabled() && ((ChestStealer)this.getModule(ChestStealer.class)).getAutoDisable().contains("World Change")) {
         this.novoline.getNotificationManager().pop(((ChestStealer)this.getModule(ChestStealer.class)).getName() + " was disabled, because world changed", 1500, NotificationType.WARNING);
         ((ChestStealer)this.getModule(ChestStealer.class)).toggle();
      }

      if(ServerUtils.isHypixel() && !this.mc.getNetHandler().getPlayerInfoMap().isEmpty()) {
         this.mc.getNetHandler().getPlayerInfoMap().clear();
      }

      if(!((ChestStealer)this.getModule(ChestStealer.class)).getChestIds().isEmpty()) {
         ((ChestStealer)this.getModule(ChestStealer.class)).getChestIds().clear();
      }

      if(this.mc.isSingleplayer() && !this.currentServer.equals(Servers.NONE)) {
         this.currentServer = Servers.NONE;
      }

   }

   public BooleanProperty getBlur() {
      return this.blur;
   }

   public BooleanProperty getClosePrevious() {
      return this.ae;
   }

   public Servers getCurrentServer() {
      return this.currentServer;
   }

   public void setCurrentServer(Servers var1) {
      this.currentServer = var1;
   }

   public Channels getChannel() {
      return this.channel;
   }

   public String channelName() {
      int var1 = HUD.e();
      return this.channel.equals(Channels.PM)?"Conversation":StringUtils.capitalize(this.channel.name().toLowerCase());
   }

   public int getTicks() {
      return this.ticks;
   }

   public BooleanProperty c() {
      return this.E;
   }

   public BooleanProperty h() {
      return this.B;
   }

   public String w() {
      return (String)this.x.get();
   }

   public List k() {
      return this.N;
   }

   public void b() {
      this.H = 0;
      this.Q = 0;
      this.M = 0;
      this.I = 0;
      this.F = 0;
      this.aa = 0;
   }

   public int j() {
      return this.I;
   }

   public int l() {
      return this.F;
   }

   public int n() {
      return this.S;
   }

   public int a() {
      return this.U;
   }

   public void c(int var1) {
      this.I = var1;
   }

   public void d(int var1) {
      this.F = var1;
   }

   public void a(int var1) {
      this.S = var1;
   }

   public void b(int var1) {
      this.U = var1;
   }

   public String o() {
      return this.G;
   }

   public void e(String var1) {
      this.G = var1;
   }

   public void setMwPort(boolean var1) {
      this.A = var1;
   }

   public boolean isMwPort() {
      return this.P;
   }

   public int g() {
      return this.H;
   }

   public int r() {
      return this.Q;
   }

   public int q() {
      return this.O;
   }

   public int u() {
      return this.aa;
   }

   public void a(long var1) {
      this.V = var1;
   }

   public long e() {
      return this.V;
   }

   private void lambda$onMotion$10(JSONObject var1, Throwable var2) {
      int var3 = HUD.e();
      if(var1.getBoolean("success")) {
         if(this.P) {
            this.Q = 0;
            this.H = 0;
            this.I = var1.getInt("staff_total");
            this.F = var1.getInt("watchdog_total");
            this.U = 0;
            this.S = 0;
            this.P = false;
         }

         this.Q = Math.max(0, var1.getInt("staff_total") - this.I);
         this.H = Math.max(0, var1.getInt("watchdog_total") - this.F);
         this.aa = var1.getInt("watchdog_lastMinute");
         this.M = var1.getInt("staff_total");
      }

   }

   private JSONObject lambda$onMotion$9() {
      String var1 = "";
      CloseableHttpClient var2 = HttpClients.createDefault();
      HttpGet var3 = new HttpGet("https://api.hypixel.net/punishmentstats?key=" + this.G);
      var3.setHeader("xf-api-key", this.G);
      var3.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
      CloseableHttpResponse var4 = null;
      CloseableHttpClient var10000 = var2;
      HttpGet var10001 = var3;

      try {
         var4 = var10000.execute(var10001);
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      String var5 = null;

      try {
         var5 = IOUtils.toString(var4.getEntity().getContent(), StandardCharsets.UTF_8);
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      return new JSONObject(var5);
   }

   private Boolean lambda$new$8() {
      return Boolean.valueOf(this.C.equals("Compact"));
   }

   private void lambda$new$7(String var1) {
      int var2 = HUD.e();
      if(this.novoline.d != null) {
         this.novoline.h().b(this.novoline.h());
      }

   }

   private void lambda$new$6(String var1) {
      int var2 = HUD.e();
      if(this.novoline.d != null) {
         this.novoline.h().a(this.novoline.h());
      }

   }

   private void lambda$new$5(String var1) {
      int var2 = HUD.h();
      if(this.novoline.d != null) {
         this.novoline.h().a(370.0D);
         this.novoline.h().d(320.0D);
         this.novoline.h().e(320.0D);
         Iterator var3 = Novoline.getInstance().h().c().iterator();
         if(var3.hasNext()) {
            akP var4 = (akP)var3.next();
            Iterator var5 = var4.d().iterator();
            if(var5.hasNext()) {
               WT var6 = (WT)var5.next();
               var6.b(0.0D);
            }
         }
      }

   }

   private Boolean lambda$new$4() {
      return Boolean.valueOf(this.C.equals("Dropdown"));
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(this.C.equals("Dropdown"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.C.equals("Compact"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.C.equals("Compact"));
   }

   private Boolean lambda$new$0() {
      int var1 = HUD.h();
      return Boolean.valueOf(this.C.equals("Compact") && !((Boolean)this.E.get()).booleanValue());
   }

   static boolean i(ClickGUI var0) {
      return var0.A;
   }

   static Minecraft b(ClickGUI var0) {
      return var0.mc;
   }

   static boolean a(ClickGUI var0, boolean var1) {
      return var0.A = var1;
   }

   static Minecraft access$000(ClickGUI var0) {
      return var0.mc;
   }

   static Minecraft access$100(ClickGUI var0) {
      return var0.mc;
   }

   static Minecraft access$200(ClickGUI var0) {
      return var0.mc;
   }

   static Minecraft access$300(ClickGUI var0) {
      return var0.mc;
   }

   static Minecraft access$400(ClickGUI var0) {
      return var0.mc;
   }

   static Minecraft access$500(ClickGUI var0) {
      return var0.mc;
   }

   static Novoline access$600(ClickGUI var0) {
      return var0.novoline;
   }

   public static void a(int[] var0) {
      J = var0;
   }

   public static int[] i() {
      return J;
   }

   static {
      a(new int[2]);
   }
}
