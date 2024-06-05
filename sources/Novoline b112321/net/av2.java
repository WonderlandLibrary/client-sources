package net;

import cc.novoline.events.events.PacketDirection;
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
import net.BT;
import net.C7;
import net.CK;
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.WT;
import net.a2V;
import net.a6d;
import net.aAV;
import net.aEE;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aHK;
import net.aHW;
import net.aHn;
import net.aHo;
import net.aaz;
import net.adZ;
import net.ae9;
import net.agu;
import net.akP;
import net.ap9;
import net.as0;
import net.as2;
import net.as5;
import net.as_;
import net.ask;
import net.asx;
import net.asz;
import net.au7;
import net.av3;
import net.av6;
import net.av8;
import net.avB;
import net.avN;
import net.avS;
import net.avU;
import net.ava;
import net.avm;
import net.avr;
import net.avu;
import net.axu;
import net.azi;
import net.cz;
import net.d3;
import net.dL;
import net.gZ;
import net.lS;
import net.rE;
import net.uO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
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
import net.minecraft.network.play.server.S38PacketPlayerListItem$AddPlayerData;
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

public final class av2 extends as0 {
   private WL X = WL.NONE;
   private boolean Y;
   private boolean z;
   private rE ab = rE.ALL;
   private d3 ad = new d3();
   private d3 Z = new d3();
   private int y;
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
   @VN("blur")
   private final aEu af = axu.g();
   @VN("close_previous")
   private final aEu ae = axu.g();
   @VN("mode")
   private final aEs C = axu.a("Compact").a(new String[]{"Compact", "Windows", "Dropdown"});
   @VN("razer_fanboy")
   private final aEu E = axu.g();
   @VN("dsv")
   private final aEu B = axu.g();
   @VN("dpi_scale")
   private final aEE D = (aEE)((aEE)axu.a(Double.valueOf(1.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(3.0D));
   @VN("bar-mode")
   private final aEs x = axu.a("Rainbow").a(new String[]{"Rainbow", "Dynamic"});
   private static int[] J;

   public av2(UW var1) {
      super(var1, "ClickGUI", "Click Gui", 54, a2V.VISUALS, "");
      ae9.a(new adZ("CG_STYLE", "Style", a6d.COMBOBOX, this, this.C));
      ae9.a(new adZ("CG_BAR_MODE", "Bar Color", a6d.COMBOBOX, this, this.x, this::lambda$new$0));
      ae9.a(new adZ("CG_RFM", "Razer Fanboy Mode", a6d.CHECKBOX, this, this.E, this::lambda$new$1));
      ae9.a(new adZ("CG_DSV", "Display Slider Values", a6d.CHECKBOX, this, this.B, this::lambda$new$2));
      ae9.a(new adZ("CG_BLUR", "Blur", a6d.CHECKBOX, this, this.af, this::lambda$new$3));
      ae9.a(new adZ("CG_CP", "Close Previous", a6d.CHECKBOX, this, this.ae, this::lambda$new$4));
      ae9.a(new adZ("CG_RL", "Restore layout", a6d.BUTTON, this, this::lambda$new$5));
      ae9.a(new adZ("CG_SL", "Save Layout", a6d.BUTTON, this, this::lambda$new$6));
      ae9.a(new adZ("CG_SL", "Load Layout", a6d.BUTTON, this, this::lambda$new$7));
      ae9.a(new adZ("CG_DPI_SCALE", "DPI Scale", a6d.SLIDER, this, this.D, 0.05D, this::lambda$new$8));
   }

   public aEE f() {
      return this.D;
   }

   public void a(double var1) {
      this.W = var1;
   }

   public double s() {
      return this.W;
   }

   public void n() {
      int var1 = ava.e();
      if(this.C.a("Dropdown")) {
         this.w.displayGuiScreen(this.j.B());
      }

      if(this.C.a("Windows")) {
         this.w.displayGuiScreen(this.j.D());
      }

      this.w.displayGuiScreen(this.j.h());
      this.e();
   }

   public void c() {
      aaz.b((Object)this);
   }

   public int x() {
      return ((ava)this.b((Class)ava.class)).q();
   }

   @agu
   public void a(aAV var1) {
      this.w.thePlayer.sendChatMessage(".bind " + var1.b().j() + " " + var1.c());
   }

   @agu
   public void b(ap9 var1) {
      int var2 = ava.h();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(var1.d() instanceof C00Handshake) {
            this.z = !this.w.isSingleplayer() && this.w.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
         }

         if(this.z && var1.d() instanceof C03PacketPlayer) {
            if(lS.c()) {
               this.j.t().a("Novoline", "Hypixel bypasses enabled!", azi.INFO);
               this.a((Packet)(new C01PacketChatMessage("/language english")));
            }

            this.j.t().a("Novoline", "Hypixel bypasses disabled! Contact staff", 6000, azi.ERROR);
            this.z = false;
         }

         if(var1.d() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement var3 = (C08PacketPlayerBlockPlacement)var1.d();
            this.ac = var3.getPosition();
            this.R = var3.getStack();
         }
      }

   }

   @agu
   public void a(dL var1) {
      int var2 = ava.e();
      if(var1.a() instanceof EntityPlayer && this.j.k().b(var1.a().getName(), au7.TARGET)) {
         this.j.t().a("target detected: " + var1.a().getName(), 3000, azi.WARNING);
      }

      if(var1.a() instanceof EntityMob || var1.a() instanceof EntityAnimal) {
         BlockPos var3 = var1.a().getPosition();
         double var4 = (double)var3.getX();
         double var6 = (double)var3.getY();
         double var8 = (double)var3.getZ();
         if(this.R != null && Item.b(this.R.getItem()) == 383 && !this.N.contains(Integer.valueOf(var1.a().getEntityId())) && var4 == (double)this.ac.getX() && var6 == (double)(this.ac.getY() + 1) && var8 == (double)this.ac.getZ()) {
            this.N.add(Integer.valueOf(var1.a().getEntityId()));
            this.ac = BlockPos.ORIGIN;
            this.R = null;
         }
      }

   }

   @agu
   public void c(uO var1) {
      ava.e();
      this.N.clear();
      if(!lS.c() || this.w.isSingleplayer()) {
         this.a(System.currentTimeMillis());
         this.d(0);
         this.c(0);
         this.b(0);
         this.a(0);
         this.A = false;
      }

      if(lS.c() && !this.w.isSingleplayer()) {
         this.j.y().a(new C7(this, 2500));
      }

   }

   @agu
   public void c(WB var1) {
      int var2 = ava.h();
      if(this.w.theWorld == null) {
         Class[] var3 = new Class[]{asx.class, avU.class, av6.class, as_.class};
         int var4 = var3.length;
         int var5 = 0;
         if(var5 < var4) {
            Class var6 = var3[var5];
            if(this.b((Class)var6).y()) {
               this.b((Class)var6).e();
            }

            ++var5;
         }

         if(this.w.timer.i != 1.0F) {
            this.w.timer.i = 1.0F;
         }

         if(!this.w.getNetHandler().getPlayerInfoMap().isEmpty()) {
            this.w.getNetHandler().getPlayerInfoMap().clear();
         }
      }

   }

   @agu
   public void b(cz var1) {
      int var2 = ava.e();
      if(lS.c() && !this.w.isSingleplayer()) {
         String var3 = var1.a();
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
            ask var5 = (ask)this.b((Class)ask.class);
            if(var5.a().a("Packet")) {
               break;
            }

            this.j.t().a(var5.f(), "mode was set to PACKET due to unstable " + ((String)var5.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var5.a().e("Packet");
         case 1:
            av3 var10 = (av3)this.b((Class)av3.class);
            if((double)((Float)var10.a().a()).floatValue() <= 1.5D) {
               break;
            }

            this.j.t().a(var10.f(), "1.5 Height is maximum on Hypixel", 3500, azi.WARNING);
            var10.a().b(Float.valueOf(1.5F));
         case 2:
            avB var11 = (avB)this.b((Class)avB.class);
            if(((String)var11.e().a()).equals("Hypixel")) {
               break;
            }

            this.j.t().a(var11.f(), "mode was set to HYPIXEL", 3500, azi.WARNING);
            var11.e().e("Hypixel");
         case 3:
            avu var12 = (avu)this.b((Class)avu.class);
            if(!((String)var12.w().a()).equals("Vanilla")) {
               break;
            }

            this.j.t().a(var12.f(), "mode was set to SPOOF due to unstable VANILLA bypass", 3500, azi.WARNING);
            var12.w().e("Spoof");
         case 4:
            avr var13 = (avr)this.b((Class)avr.class);
            if(!((String)var13.a().a()).equals("Dolphin")) {
               break;
            }

            this.j.t().a(var13.f(), "mode was set to SOLID due to unstable Dolphin bypass", 3500, azi.WARNING);
            var13.a().e("Solid");
         case 5:
            asx var14 = (asx)this.b((Class)asx.class);
            if(!((String)var14.w().a()).equals("PRE")) {
               break;
            }

            this.j.t().a(var14.f(), "attack event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var14.w().e("POST");
         case 6:
            asx var15 = (asx)this.b((Class)asx.class);
            if(!((String)var15.g().a()).equals("PRE")) {
               break;
            }

            this.j.t().a(var15.f(), "autoblock event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var15.g().e("POST");
         case 7:
            asx var16 = (asx)this.b((Class)asx.class);
            if(!((String)var16.i().a()).equals("Vanilla")) {
               break;
            }

            this.j.t().a(var16.f(), "autoblock mode was set to PACKET due to unstable VANILLA bypass", 3500, azi.WARNING);
            var16.i().e("Packet");
         case 8:
            avu var17 = (avu)this.b((Class)avu.class);
            if(!var17.C().a("PRE")) {
               break;
            }

            this.j.t().a(var17.f(), "place event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var17.C().e("POST");
         case 9:
            avu var18 = (avu)this.b((Class)avu.class);
            if(!((Boolean)var18.H().a()).booleanValue()) {
               break;
            }

            this.j.t().a(var18.f(), "Ray Trace was turned off due to unstable bypass", 3500, azi.WARNING);
            var18.H().a(Boolean.valueOf(false));
         case 10:
            avS var19 = (avS)this.b((Class)avS.class);
            if(var19.a().a("Vanilla")) {
               this.j.t().a(var19.f(), "mode was set to NCP due to unstable VANILLA bypass", 3500, azi.WARNING);
               var19.a().e("NCP");
            }
         }

         if(this.a((Class)as5.class) && ((as5)this.b((Class)as5.class)).b().a("Watchdog") && ((as5)this.b((Class)as5.class)).a().a("Normal")) {
            var3 = var1.a();
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
               aEl var20 = (aEl)var1.f();
               if(((Float)var20.a()).floatValue() > 1.0F) {
                  this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
                  var20.b(Float.valueOf(1.0F));
               }
            }
         }
      }

      if(lS.b()) {
         String var7 = var1.a();
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
            asx var21 = (asx)this.b((Class)asx.class);
            if(!((String)var21.w().a()).equals("POST")) {
               break;
            }

            this.j.t().a(var21.f(), "attack event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var21.w().e("PRE");
         case 1:
            asx var22 = (asx)this.b((Class)asx.class);
            if(!((String)var22.g().a()).equals("POST")) {
               break;
            }

            this.j.t().a(var22.f(), "autoblock event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var22.g().e("PRE");
         case 2:
            asx var23 = (asx)this.b((Class)asx.class);
            if(!((String)var23.i().a()).equals("Packet")) {
               break;
            }

            this.j.t().a(var23.f(), "autoblock mode was set to VANILLA due to unstable PACKET bypass", 3500, azi.WARNING);
            var23.i().e("Vanilla");
         case 3:
            avu var24 = (avu)this.b((Class)avu.class);
            if(!((String)var24.C().a()).equals("POST")) {
               break;
            }

            this.j.t().a(var24.f(), "place event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var24.C().e("PRE");
         case 4:
            avS var25 = (avS)this.b((Class)avS.class);
            if(!((String)var25.d().a()).equals("Reduced")) {
               break;
            }

            this.j.t().a(var25.f(), "jump height was set to VANILLA due to unstable REDUCED bypass", 3500, azi.WARNING);
            var25.d().e("Vanilla");
         case 5:
            as2 var26 = (as2)this.b((Class)as2.class);
            if(!var26.a().a("Edit")) {
               break;
            }

            this.j.t().a(var26.f(), "mode was set to PACKET due to unstable EDIT bypass", 3500, azi.WARNING);
            var26.a().e("Packet");
         case 6:
            avB var27 = (avB)this.b((Class)avB.class);
            if(((String)var27.e().a()).equals("BlocksMC")) {
               break;
            }

            this.j.t().a(var27.f(), "mode was set to BlocksMC", 3500, azi.WARNING);
            var27.e().e("BlocksMC");
         case 7:
            asz var28 = (asz)this.b((Class)asz.class);
            if(var28.a().a("Packet")) {
               this.j.t().a(var28.f(), "mode was set to EDIT due to unstable PACKET bypass", 3500, azi.WARNING);
               var28.a().e("Edit");
            }
         case 8:
            ask var29 = (ask)this.b((Class)ask.class);
            if(var29.a().a("Verus")) {
               break;
            }

            this.j.t().a(var29.f(), "mode was set to VERUS due to unstable " + ((String)var29.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var29.a().e("Verus");
         case 9:
            asx var30 = (asx)this.b((Class)asx.class);
            if(((Double)var30.V().a()).doubleValue() <= 10.0D) {
               break;
            }

            this.j.t().a(var30.f(), "10 is maximum aps on BlocksMC", 3500, azi.WARNING);
            var30.V().b(Double.valueOf(10.0D));
         case 10:
            asx var31 = (asx)this.b((Class)asx.class);
            if(((Double)var31.R().a()).doubleValue() <= 10.0D) {
               break;
            }

            this.j.t().a(var31.f(), "10 is maximum aps on BlocksMC", 3500, azi.WARNING);
            var31.R().b(Double.valueOf(10.0D));
         case 11:
            avm var32 = (avm)this.b((Class)avm.class);
            if(((Boolean)var32.a().a()).booleanValue()) {
               break;
            }

            this.j.t().a(var32.f(), "VANILLA was turned on due to unstable PACKET bypass", 3500, azi.WARNING);
            var32.a().a(Boolean.valueOf(true));
         case 12:
            av8 var33 = (av8)this.b((Class)av8.class);
            if(var33.a().a("Motion")) {
               break;
            }

            this.j.t().a(var33.f(), "Flight mode was set to MOTION due to unstable " + ((String)var33.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var33.a().e("Motion");
         case 13:
            av8 var34 = (av8)this.b((Class)av8.class);
            if(((Double)var34.e().a()).doubleValue() > 4.0D) {
               this.j.t().a(var34.f(), "4.0 is maximum motion speed on BlocksMC", 3500, azi.WARNING);
               var34.e().b(Double.valueOf(4.0D));
            }
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
            aEl var35 = (aEl)var1.f();
            if(((Float)var35.a()).floatValue() > 1.0F) {
               this.j.t().a("Timer does not bypass on BlocksMC", 3500, azi.WARNING);
               var35.b(Float.valueOf(1.0F));
            }
         }
      }

   }

   @agu
   public void b(uO var1) {
      int var2 = ava.h();
      if(lS.c() && !this.w.isSingleplayer()) {
         ask var3 = (ask)this.b((Class)ask.class);
         avu var4 = (avu)this.b((Class)avu.class);
         av3 var5 = (av3)this.b((Class)av3.class);
         avB var6 = (avB)this.b((Class)avB.class);
         avr var7 = (avr)this.b((Class)avr.class);
         asx var8 = (asx)this.b((Class)asx.class);
         avS var9 = (avS)this.b((Class)avS.class);
         if(!var3.a().a("Packet")) {
            this.j.t().a(var3.f(), "mode was set to PACKET due to unstable " + ((String)var3.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var3.a().e("Packet");
         }

         if(((String)var4.w().a()).equals("Vanilla")) {
            this.j.t().a(var4.f(), "mode was set to SPOOF due to unstable VANILLA bypass", 3500, azi.WARNING);
            var4.w().e("Spoof");
         }

         if((double)((Float)var5.a().a()).floatValue() > 1.5D) {
            this.j.t().a(var5.f(), "1.5 Height is maximum on Hypixel", 3500, azi.WARNING);
            var5.a().b(Float.valueOf(1.5F));
         }

         if(!((String)var6.e().a()).equals("Hypixel")) {
            this.j.t().a(var6.f(), "mode was set to HYPIXEL", 3500, azi.WARNING);
            var6.e().e("Hypixel");
         }

         if(((String)var7.a().a()).equals("Dolphin")) {
            this.j.t().a(var7.f(), "mode was set to SOLID due to unstable Dolphin bypass", 3500, azi.WARNING);
            var7.a().e("Solid");
         }

         if(((String)var8.w().a()).equals("PRE")) {
            this.j.t().a(var8.f(), "attack event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var8.w().e("POST");
         }

         if(((String)var8.g().a()).equals("PRE")) {
            this.j.t().a(var8.f(), "autoblock event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var8.g().e("POST");
         }

         if(((String)var8.i().a()).equals("Vanilla")) {
            this.j.t().a(var8.f(), "autoblock mode was set to PACKET due to unstable VANILLA bypass", 3500, azi.WARNING);
            var8.i().e("Packet");
         }

         if(var4.C().a("PRE")) {
            this.j.t().a(var4.f(), "place event was set to POST due to unstable PRE bypass", 3500, azi.WARNING);
            var4.C().e("POST");
         }

         if(((Boolean)var4.H().a()).booleanValue()) {
            this.j.t().a(var4.f(), "Ray Trace was turned off due to unstable bypass", 3500, azi.WARNING);
            var4.H().a(Boolean.valueOf(false));
         }

         if(var9.a().a("Vanilla")) {
            this.j.t().a(var9.f(), "mode was set to NCP due to unstable VANILLA bypass", 3500, azi.WARNING);
            var9.a().e("NCP");
         }

         if(this.a((Class)as5.class) && ((as5)this.b((Class)as5.class)).b().a("Watchdog") && !((as5)this.b((Class)as5.class)).a().a("Advanced")) {
            if(((Float)var4.z().a()).floatValue() > 1.0F) {
               this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
               var4.z().b(Float.valueOf(1.0F));
            }

            if(((Float)var4.x().a()).floatValue() > 1.0F) {
               this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
               var4.x().b(Float.valueOf(1.0F));
            }

            if(((Float)var4.n().a()).floatValue() > 1.0F) {
               this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
               var4.n().b(Float.valueOf(1.0F));
            }

            if(((Float)var4.c().a()).floatValue() > 1.0F) {
               this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
               var4.c().b(Float.valueOf(1.0F));
            }

            if(((Float)var9.b().a()).floatValue() > 1.0F) {
               this.j.t().a("You need to turn on Advanced Watchdog mode in Disabler to use Timer", 3500, azi.WARNING);
               var9.b().b(Float.valueOf(1.0F));
            }
         }
      }

      if(lS.b()) {
         avu var12 = (avu)this.b((Class)avu.class);
         asx var13 = (asx)this.b((Class)asx.class);
         avS var14 = (avS)this.b((Class)avS.class);
         as2 var15 = (as2)this.b((Class)as2.class);
         avB var16 = (avB)this.b((Class)avB.class);
         asz var17 = (asz)this.b((Class)asz.class);
         ask var18 = (ask)this.b((Class)ask.class);
         avm var10 = (avm)this.b((Class)avm.class);
         av8 var11 = (av8)this.b((Class)av8.class);
         if(((String)var13.w().a()).equals("POST")) {
            this.j.t().a(var13.f(), "attack event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var13.w().e("PRE");
         }

         if(((String)var13.g().a()).equals("POST")) {
            this.j.t().a(var13.f(), "autoblock event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var13.g().e("PRE");
         }

         if(((String)var13.i().a()).equals("Packet")) {
            this.j.t().a(var13.f(), "autoblock mode was set to VANILLA due to unstable PACKET bypass", 3500, azi.WARNING);
            var13.i().e("Vanilla");
         }

         if(((String)var12.C().a()).equals("POST")) {
            this.j.t().a(var12.f(), "place event was set to PRE due to unstable POST bypass", 3500, azi.WARNING);
            var12.C().e("PRE");
         }

         if(((String)var14.d().a()).equals("Reduced")) {
            this.j.t().a(var14.f(), "jump height was set to VANILLA due to unstable REDUCED bypass", 3500, azi.WARNING);
            var14.d().e("Vanilla");
         }

         if(var15.a().a("Edit")) {
            this.j.t().a(var15.f(), "mode was set to PACKET due to unstable EDIT bypass", 3500, azi.WARNING);
            var15.a().e("Packet");
         }

         if(!((String)var16.e().a()).equals("Verus")) {
            this.j.t().a(var16.f(), "mode was set to Verus", 3500, azi.WARNING);
            var16.e().e("Verus");
         }

         if(var17.a().a("Packet")) {
            this.j.t().a(var17.f(), "mode was set to EDIT due to unstable PACKET bypass", 3500, azi.WARNING);
            var17.a().e("Edit");
         }

         if(!var18.a().a("Verus")) {
            this.j.t().a(var18.f(), "mode was set to VERUS due to unstable " + ((String)var18.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var18.a().e("Verus");
         }

         if(!((Boolean)var10.a().a()).booleanValue()) {
            this.j.t().a(var10.f(), "VANILLA was turned on due to unstable PACKET bypass", 3500, azi.WARNING);
            var10.a().a(Boolean.valueOf(true));
         }

         if(!var11.a().a("Motion")) {
            this.j.t().a(var11.f(), "Flight mode was set to MOTION due to unstable " + ((String)var11.a().a()).toUpperCase() + " bypass", 3500, azi.WARNING);
            var11.a().e("Motion");
         }

         if(((Double)var11.e().a()).doubleValue() > 4.0D) {
            this.j.t().a(var11.f(), "4.0 is maximum motion speed on BlocksMC", 3500, azi.WARNING);
            var11.e().b(Double.valueOf(4.0D));
         }

         if(((Float)var12.z().a()).floatValue() > 1.0F) {
            this.j.t().a(var12.f(), "Timer does not bypass on BlocksMC", 3500, azi.WARNING);
            var12.z().b(Float.valueOf(1.0F));
         }

         if(((Float)var12.x().a()).floatValue() > 1.0F) {
            this.j.t().a(var12.f(), "Timer does not bypass on BlocksMC", 3500, azi.WARNING);
            var12.x().b(Float.valueOf(1.0F));
         }

         if(((Float)var12.n().a()).floatValue() > 1.0F) {
            this.j.t().a(var12.f(), "Timer does not bypass on BlocksMC", 3500, azi.WARNING);
            var12.n().b(Float.valueOf(1.0F));
         }

         if(((Float)var12.c().a()).floatValue() > 1.0F) {
            this.j.t().a(var12.f(), "Timer does not bypass on BlocksMC", 3500, azi.WARNING);
            var12.c().b(Float.valueOf(1.0F));
         }

         if(((Float)var14.b().a()).floatValue() > 1.0F) {
            this.j.t().a(var14.f(), "Timer does not bypass on BlocksMC", 3500, azi.WARNING);
            var14.b().b(Float.valueOf(1.0F));
         }

         if(((Double)var13.R().a()).doubleValue() > 10.0D) {
            this.j.t().a(var13.f(), "10 is maximum aps on BlocksMC", 3500, azi.WARNING);
            var13.R().b(Double.valueOf(10.0D));
         }

         if(((Double)var13.V().a()).doubleValue() > 10.0D) {
            this.j.t().a(var13.f(), "10 is maximum aps on BlocksMC", 3500, azi.WARNING);
            var13.V().b(Double.valueOf(10.0D));
         }
      }

   }

   @agu
   public void a(cz var1) {
      int var2 = ava.e();
      if(var1.a().equals("RANGE") || var1.a().equals("BLOCK_RANGE") || var1.a().equals("WALL_RANGE")) {
         aEE var3 = ((asx)this.b((Class)asx.class)).v();
         aEE var4 = ((asx)this.b((Class)asx.class)).A();
         if(((Double)var4.a()).doubleValue() > ((Double)var3.a()).doubleValue()) {
            var4.b((Number)var3.a());
         }
      }

      String var9 = var1.a();
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
         asx var5 = (asx)this.b((Class)asx.class);
         if(((Double)var5.R().a()).doubleValue() <= ((Double)var5.V().a()).doubleValue()) {
            break;
         }

         var5.V().b((Number)var5.R().a());
      case 1:
         asx var11 = (asx)this.b((Class)asx.class);
         if(((Double)var11.V().a()).doubleValue() >= ((Double)var11.R().a()).doubleValue()) {
            break;
         }

         var11.R().b((Number)var11.V().a());
      case 2:
      case 3:
         aEE var12 = ((asx)this.b((Class)asx.class)).v();
         aEE var6 = ((asx)this.b((Class)asx.class)).q();
         if(((Double)var6.a()).doubleValue() < ((Double)var12.a()).doubleValue()) {
            var12.b((Number)var6.a());
         }

         if(((Double)var12.a()).doubleValue() <= ((Double)var6.a()).doubleValue()) {
            break;
         }

         var6.b((Number)var12.a());
      case 4:
         asx var7 = (asx)this.b((Class)asx.class);
         ScaledResolution var8 = new ScaledResolution(Minecraft.getMinecraft());
         if(((Integer)var7.o().a()).intValue() <= var8.getScaledWidth() - 50) {
            break;
         }

         var7.o().b((Number)Integer.valueOf(var8.getScaledWidth() - 50));
      case 5:
         asx var13 = (asx)this.b((Class)asx.class);
         ScaledResolution var14 = new ScaledResolution(Minecraft.getMinecraft());
         if(((Integer)var13.l().a()).intValue() <= var14.getScaledHeight() - 50) {
            break;
         }

         var13.l().b((Number)Integer.valueOf(var14.getScaledHeight() - 50));
      case 6:
         if(!var1.d().a((Object)"Teams") || ((asx)this.b((Class)asx.class)).n() == null) {
            break;
         }

         ((asx)this.b((Class)asx.class)).b((Entity)null);
      case 7:
         if(this.C.a("Dropdown")) {
            this.w.displayGuiScreen(this.j.B());
         }

         if(this.C.a("Windows")) {
            this.w.displayGuiScreen(this.j.D());
         }

         this.w.displayGuiScreen(this.j.h());
      }

   }

   @agu
   public void a(WB var1) {
      int var2 = ava.e();
      if(this.w.theWorld != null && this.w.thePlayer != null && !this.w.isSingleplayer() && (lS.c() || lS.a() || lS.g() || lS.b())) {
         if(!this.a((Class)as5.class)) {
            ((as5)this.b((Class)as5.class)).e();
         }

         if(lS.c()) {
            ((as5)this.b((Class)as5.class)).b().e("Watchdog");
         }

         if(lS.b()) {
            ((as5)this.b((Class)as5.class)).b().e("Verus");
         }

         if(lS.a() || lS.g()) {
            ((as5)this.b((Class)as5.class)).b().e("Simple");
         }
      }

   }

   @agu
   public void c(ap9 var1) {
      int var2 = ava.h();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(var1.d() instanceof S2EPacketCloseWindow) {
            S2EPacketCloseWindow var3 = (S2EPacketCloseWindow)var1.d();
            if(this.w.currentScreen instanceof aHo || this.w.currentScreen instanceof GuiChat || this.w.currentScreen instanceof GuiInventory || this.w.currentScreen instanceof aHn || this.w.currentScreen instanceof aHW || this.w.currentScreen instanceof aHK) {
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof S00PacketDisconnect && !this.j.y().a().isEmpty()) {
            this.j.y().a().clear();
         }

         if(var1.d() instanceof S02PacketChat) {
            S02PacketChat var7 = (S02PacketChat)var1.d();
            if(var7.getChatComponent().getFormattedText().equals("§r§aSelected language: §r§eEnglish§r")) {
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof S2DPacketOpenWindow) {
            S2DPacketOpenWindow var8 = (S2DPacketOpenWindow)var1.d();
            if(var8.getWindowTitle().getFormattedText().equals("Select Language§r")) {
               var1.setCancelled(true);
            }
         }

         if(lS.c() && var1.d() instanceof S3DPacketDisplayScoreboard) {
            S3DPacketDisplayScoreboard var9 = (S3DPacketDisplayScoreboard)var1.d();
            String var4 = var9.func_149370_d();
            if(var4.equalsIgnoreCase("Mw")) {
               this.X = WL.MW;
            }

            if(var4.equalsIgnoreCase("§e§lHYPIXEL")) {
               this.X = WL.UHC;
            }

            if(var4.equalsIgnoreCase("SForeboard")) {
               this.X = WL.SW;
            }

            if(var4.equalsIgnoreCase("BForeboard")) {
               this.X = WL.BW;
            }

            if(var4.equalsIgnoreCase("PreScoreboard")) {
               this.X = WL.PRE;
            }

            if(var4.equalsIgnoreCase("Duels")) {
               this.X = WL.DUELS;
            }

            if(var4.equalsIgnoreCase("Pit")) {
               this.X = WL.PIT;
            }

            if(var4.equalsIgnoreCase("Blitz SG")) {
               this.X = WL.SG;
            }

            if(var4.equalsIgnoreCase("MurderMystery")) {
               this.X = WL.MM;
            }

            if(!var4.contains("health") && !var4.contains("✫")) {
               this.X = WL.NONE;
            }
         }

         if(this.A && var1.d() instanceof S02PacketChat) {
            S02PacketChat var10 = (S02PacketChat)var1.d();
            String var15 = var10.getChatComponent().getFormattedText();
            if(var15.startsWith("§aYour new API key is §r§b")) {
               String var5 = var15.split("§aYour new API key is §r§b")[1].split("§r")[0];
               this.e(var5);
               this.P = true;
            }
         }

         if(var1.d() instanceof S45PacketTitle) {
            S45PacketTitle var11 = (S45PacketTitle)var1.d();
            if(var11.getMessage().getUnformattedText().equals("§6§lVICTORY!")) {
               ++this.S;
            }
         }

         if(var1.d() instanceof S2FPacketSetSlot) {
            S2FPacketSetSlot var12 = (S2FPacketSetSlot)var1.d();
            if(var12.func_149174_e().getDisplayName().contains("§") && var12.func_149174_e().getDisplayName().contains("Spectator")) {
               this.K = true;
            }
         }

         if(!lS.a(WL.PRE) && !lS.a(WL.LOBBY)) {
            if(this.K || !(var1.d() instanceof S38PacketPlayerListItem)) {
               return;
            }

            S38PacketPlayerListItem var13 = (S38PacketPlayerListItem)var1.d();
            Iterator var16 = var13.func_179767_a().iterator();
            if(var16.hasNext()) {
               S38PacketPlayerListItem$AddPlayerData var17 = (S38PacketPlayerListItem$AddPlayerData)var16.next();
               EntityPlayer var6 = this.w.theWorld.getPlayerEntityByUUID(var17.getProfile().getId());
               if(var13.func_179768_b().equals(S38PacketPlayerListItem$Action.REMOVE_PLAYER) && !var6.equals(this.w.thePlayer) && var17.getProfile().getName() == null && var6.getHealth() < var6.getMaxHealth() && this.ag.contains(var6)) {
                  ++this.U;
               }
            }
         }

         if(!this.K) {
            return;
         }

         this.K = false;
      }

      if(var1.d() instanceof C01PacketChatMessage) {
         C01PacketChatMessage var14 = (C01PacketChatMessage)var1.d();
         if(var14.getMessage().startsWith("/language")) {
            var1.setCancelled(true);
         }
      }

   }

   @agu
   public void a(BT var1) {
      int var2 = ava.h();
      if(this.a((Class)asx.class) && ((asx)this.b((Class)asx.class)).P()) {
         asx var3 = (asx)this.b((Class)asx.class);
         if(var3.n() != null && var3.n() instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var3.n();
            if(!this.ag.contains(var4)) {
               this.ag.add(var4);
            }
         }
      }

   }

   @agu
   public void a(aG1 var1) {
      int var2 = ava.e();
      if(lS.c() && !this.w.isSingleplayer() && this.Z.a(5000.0D)) {
         CompletableFuture var3 = CompletableFuture.supplyAsync(this::lambda$onMotion$9, ForkJoinPool.commonPool());
         var3.whenCompleteAsync(this::lambda$onMotion$10);
         this.Z.b();
      }

   }

   @agu
   public void b(WB var1) {
      int var2 = ava.h();
      if(lS.c() && !lS.a(WL.PRE) && !lS.a(WL.LOBBY) && !lS.a(WL.NONE)) {
         ++this.y;
      }

      if(this.y > 0) {
         this.y = 0;
      }

   }

   @agu
   public void b(BT var1) {
      int var2 = ava.e();
      if(lS.a(WL.PRE)) {
         this.Y = true;
      }

      if(this.Y && lS.a(WL.BW)) {
         ((avN)this.b((Class)avN.class)).a((BlockPos)null);
         gZ.g().y().a(new CK(this, 500));
         this.Y = false;
      }

      int var3 = 0;
      if(var3 < 9) {
         if(this.w.thePlayer.bJ.getStackInSlot(var3) != null && this.w.thePlayer.bJ.getStackInSlot(var3).stackSize == 0) {
            this.w.thePlayer.bJ.removeStackFromSlot(var3);
         }

         ++var3;
      }

   }

   @agu
   public void d(ap9 var1) {
      int var2 = ava.h();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(!lS.c()) {
            return;
         }

         if(var1.d() instanceof S02PacketChat) {
            S02PacketChat var3 = (S02PacketChat)var1.d();
            String var4 = var3.getChatComponent().getFormattedText();
            if(var4.startsWith("§aOpened a chat conversation with")) {
               this.ab = rE.PM;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6ALL§r§a channel§r")) {
               this.ab = rE.ALL;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6PARTY§r§a channel§r")) {
               this.ab = rE.PARTY;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
               var1.setCancelled(true);
            }

            if(var4.equals("§aYou are now in the §r§6GUILD§r§a channel§r")) {
               this.ab = rE.GUILD;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
               var1.setCancelled(true);
            }

            if(!this.ab.equals(rE.GUILD) && var4.startsWith("§r§2Guild") && var4.contains(this.w.thePlayer.getDisplayName().getFormattedText())) {
               this.ab = rE.GUILD;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
            }

            if(!this.ab.equals(rE.PARTY) && var4.startsWith("§r§9Party") && var4.contains(this.w.thePlayer.getDisplayName().getFormattedText())) {
               this.ab = rE.PARTY;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
            }

            if(!this.ab.equals(rE.PM) && var4.startsWith("§dTo")) {
               this.ab = rE.PM;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
            }

            if(this.ab.equals(rE.PARTY) && var4.contains(this.w.thePlayer.getName()) && var4.endsWith("§r§ehas disbanded the party!§r")) {
               this.a((Packet)(new C01PacketChatMessage("/chat all")));
            }

            if(!this.ab.equals(rE.ALL) && var4.equals("§cThe conversation you were in expired and you have been moved back to the ALL channel.§r")) {
               this.ab = rE.ALL;
               this.j.t().a("Hypixel", "You are now in the " + this.d() + " channel", 3000, azi.INFO);
            }
         }

         if(var1.d() instanceof S45PacketTitle) {
            S45PacketTitle var5 = (S45PacketTitle)var1.d();
            if(var5.getType().equals(S45PacketTitle$Type.TITLE)) {
               String var10 = var5.getMessage().getUnformattedText();
               if(var10.equals("§6§lVICTORY!") || var10.equals("GAME OVER!")) {
                  if(this.a((Class)asx.class) && ((asx)this.b((Class)asx.class)).c().a((Object)"Game End")) {
                     gZ.g().t().a(((asx)this.b((Class)asx.class)).j() + " was disabled, because game has ended", 1500, azi.WARNING);
                     ((asx)this.b((Class)asx.class)).e();
                  }

                  if(this.a((Class)av6.class) && ((av6)this.b((Class)av6.class)).k().a((Object)"Game End")) {
                     gZ.g().t().a(((av6)this.b((Class)av6.class)).j() + " was disabled, because game has ended", 1500, azi.WARNING);
                     ((av6)this.b((Class)av6.class)).e();
                  }

                  if(this.a((Class)avU.class) && ((avU)this.b((Class)avU.class)).g().a((Object)"Game End")) {
                     gZ.g().t().a(((avU)this.b((Class)avU.class)).j() + " was disabled, because game has ended", 1500, azi.WARNING);
                     ((avU)this.b((Class)avU.class)).e();
                  }
               }
            }
         }

         if(!(var1.d() instanceof S2FPacketSetSlot)) {
            return;
         }

         S2FPacketSetSlot var6 = (S2FPacketSetSlot)var1.d();
         if(var6.func_149174_e().getDisplayName().contains("§") && var6.func_149174_e().getDisplayName().contains("Spectator")) {
            if(this.a((Class)asx.class) && ((asx)this.b((Class)asx.class)).c().a((Object)"Death")) {
               gZ.g().t().a(((asx)this.b((Class)asx.class)).j() + " was disabled, because of player death", 1500, azi.WARNING);
               ((asx)this.b((Class)asx.class)).e();
            }

            if(this.a((Class)av6.class) && ((av6)this.b((Class)av6.class)).k().a((Object)"Death")) {
               gZ.g().t().a(((av6)this.b((Class)av6.class)).j() + " was disabled, because of player death", 1500, azi.WARNING);
               ((av6)this.b((Class)av6.class)).e();
            }

            if(this.a((Class)avU.class) && ((avU)this.b((Class)avU.class)).g().a((Object)"Death")) {
               gZ.g().t().a(((avU)this.b((Class)avU.class)).j() + " was disabled, because of player death", 1500, azi.WARNING);
               ((avU)this.b((Class)avU.class)).e();
            }
         }

         if(lS.a(WL.LOBBY) && var6.func_149174_e().getItem() instanceof ItemEditableBook) {
            var1.setCancelled(true);
         }
      }

      if(lS.c() && !lS.a(WL.LOBBY) || lS.b()) {
         if(var1.d() instanceof C0DPacketCloseWindow) {
            C0DPacketCloseWindow var7 = (C0DPacketCloseWindow)var1.d();
            if(var7.a() == 0) {
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof C16PacketClientStatus) {
            C16PacketClientStatus var8 = (C16PacketClientStatus)var1.d();
            if(var8.getStatus().equals(C16PacketClientStatus$EnumState.OPEN_INVENTORY_ACHIEVEMENT)) {
               var1.setCancelled(true);
            }
         }
      }

      if(lS.c() && !lS.a(WL.LOBBY) && var1.d() instanceof C0EPacketClickWindow && this.w.thePlayer.ay() > 0.075D) {
         C0EPacketClickWindow var9 = (C0EPacketClickWindow)var1.d();
         int var11 = ThreadLocalRandom.current().nextInt(1, 10);
         this.a((Packet)(new C0EPacketClickWindow(var11, -999, 0, -4, (ItemStack)null, var9.getActionNumber())));
         if(var9.getWindowId() == 0) {
            this.a((Packet)(new C0DPacketCloseWindow(var11)));
         }
      }

   }

   @agu
   public void a(uO var1) {
      int var2 = ava.h();
      if(((asx)this.b((Class)asx.class)).y() && ((asx)this.b((Class)asx.class)).c().a((Object)"World Change")) {
         this.j.t().a(((asx)this.b((Class)asx.class)).j() + " was disabled, because world changed", 1500, azi.WARNING);
         ((asx)this.b((Class)asx.class)).e();
      }

      if(((av6)this.b((Class)av6.class)).y() && ((av6)this.b((Class)av6.class)).k().a((Object)"World Change")) {
         this.j.t().a(((av6)this.b((Class)av6.class)).j() + " was disabled, because world changed", 1500, azi.WARNING);
         ((av6)this.b((Class)av6.class)).e();
      }

      if(((avU)this.b((Class)avU.class)).y() && ((avU)this.b((Class)avU.class)).g().a((Object)"World Change")) {
         this.j.t().a(((avU)this.b((Class)avU.class)).j() + " was disabled, because world changed", 1500, azi.WARNING);
         ((avU)this.b((Class)avU.class)).e();
      }

      if(lS.c() && !this.w.getNetHandler().getPlayerInfoMap().isEmpty()) {
         this.w.getNetHandler().getPlayerInfoMap().clear();
      }

      if(!((avU)this.b((Class)avU.class)).d().isEmpty()) {
         ((avU)this.b((Class)avU.class)).d().clear();
      }

      if(this.w.isSingleplayer() && !this.X.equals(WL.NONE)) {
         this.X = WL.NONE;
      }

   }

   public aEu v() {
      return this.af;
   }

   public aEu y() {
      return this.ae;
   }

   public WL A() {
      return this.X;
   }

   public void a(WL var1) {
      this.X = var1;
   }

   public rE m() {
      return this.ab;
   }

   public String d() {
      int var1 = ava.e();
      return this.ab.equals(rE.PM)?"Conversation":StringUtils.a(this.ab.name().toLowerCase());
   }

   public int z() {
      return this.y;
   }

   public aEu c() {
      return this.E;
   }

   public aEu h() {
      return this.B;
   }

   public String w() {
      return (String)this.x.a();
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

   public void d(boolean var1) {
      this.A = var1;
   }

   public boolean t() {
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
      int var3 = ava.e();
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
      return Boolean.valueOf(this.C.a("Compact"));
   }

   private void lambda$new$7(String var1) {
      int var2 = ava.e();
      if(this.j.d != null) {
         this.j.h().b(this.j.h());
      }

   }

   private void lambda$new$6(String var1) {
      int var2 = ava.e();
      if(this.j.d != null) {
         this.j.h().a(this.j.h());
      }

   }

   private void lambda$new$5(String var1) {
      int var2 = ava.h();
      if(this.j.d != null) {
         this.j.h().a(370.0D);
         this.j.h().d(320.0D);
         this.j.h().e(320.0D);
         Iterator var3 = gZ.g().h().c().iterator();
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
      return Boolean.valueOf(this.C.a("Dropdown"));
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(this.C.a("Dropdown"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.C.a("Compact"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.C.a("Compact"));
   }

   private Boolean lambda$new$0() {
      int var1 = ava.h();
      return Boolean.valueOf(this.C.a("Compact") && !((Boolean)this.E.a()).booleanValue());
   }

   static boolean i(av2 var0) {
      return var0.A;
   }

   static Minecraft b(av2 var0) {
      return var0.w;
   }

   static boolean a(av2 var0, boolean var1) {
      return var0.A = var1;
   }

   static Minecraft a(av2 var0) {
      return var0.w;
   }

   static Minecraft g(av2 var0) {
      return var0.w;
   }

   static Minecraft f(av2 var0) {
      return var0.w;
   }

   static Minecraft h(av2 var0) {
      return var0.w;
   }

   static Minecraft e(av2 var0) {
      return var0.w;
   }

   static Minecraft d(av2 var0) {
      return var0.w;
   }

   static gZ c(av2 var0) {
      return var0.j;
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
