package net;

import cc.novoline.events.events.PacketDirection;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import net.Ea;
import net.FJ;
import net.J8;
import net.UW;
import net.Ua;
import net.VN;
import net.WL;
import net.ZV;
import net.a0E;
import net.a2V;
import net.a6d;
import net.aEF;
import net.aEs;
import net.aEu;
import net.aJ1;
import net.aaz;
import net.adZ;
import net.ae9;
import net.ap9;
import net.aqA;
import net.av2;
import net.ava;
import net.awl;
import net.axu;
import net.azi;
import net.bgM;
import net.dQ;
import net.gZ;
import net.kC;
import net.lS;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@J8
public class as0 {
   bgM o;
   protected final Logger r;
   protected final gZ j;
   protected final UW f;
   protected final Minecraft w;
   protected a0E i;
   protected final a2V p;
   protected final String v;
   protected String n;
   protected final String m;
   protected final int g;
   protected boolean a;
   protected String l;
   @VN("enabled")
   protected final aEu k;
   @VN("hidden")
   protected final aEu d;
   @VN("display-name")
   protected final aEs t;
   protected final aEF e;
   public float s;
   public float h;
   public Color b;
   public Color c;
   private final Cache u;
   private static int[] q;

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull String var3, int var4, @NotNull a2V var5, @Nullable String var6, @Nullable String var7) {
      this.o = bgM.d();
      this.r = LogManager.getLogger();
      this.a = false;
      this.k = axu.g();
      this.d = axu.g();
      d();
      this.t = axu.a("");
      this.e = axu.a((ProtocolPathEntry)aqA.b(0));
      this.s = 0.0F;
      this.h = 0.0F;
      this.b = new Color(255, 0, 0);
      this.c = new Color(0, 255, 255);
      this.u = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.MINUTES).build();
      this.f = var1;
      this.j = var1.d();
      this.w = this.j.f();
      this.v = var2;
      this.p = var5;
      this.g = var4;
      this.m = var6;
      this.n = var3;
      ae9.a(new adZ("MODULE_BIND", "Bind", a6d.BINDABLE, this, this.e));
      this.i = a0E.a(this.j.i().resolve(var7 + ".novo"));
      Ea var9 = this.i.d().b().f();
      var9.a((Predicate)(as0::lambda$new$0), (ZV)(new kC(var1)));
      this.a(var9);

      try {
         this.i.e();
      } catch (awl var11) {
         var11.printStackTrace();
      }

   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull String var3, @NotNull a2V var4, @Nullable String var5, @Nullable String var6) {
      this(var1, var2, var3, 0, var4, var5, var6);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull String var3, int var4, @NotNull a2V var5, @Nullable String var6) {
      this(var1, var2, var3, var4, var5, var6, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull a2V var3, @Nullable String var4, @Nullable String var5) {
      this(var1, var2, var2, 0, var3, var4, var5);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull String var3, @NotNull a2V var4, @Nullable String var5) {
      this(var1, var2, var3, 0, var4, var5, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull String var3, @NotNull int var4, @NotNull a2V var5) {
      this(var1, var2, var3, var4, var5, (String)null, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull a2V var3, @Nullable String var4) {
      this(var1, var2, var2, 0, var3, var4, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull String var2, @NotNull a2V var3) {
      this(var1, var2, var2, 0, var3, (String)null, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull a2V var2, @NotNull String var3) {
      this(var1, var3, var3, 0, var2, (String)null, (String)null);
   }

   protected as0(@NotNull UW var1, @NotNull a2V var2, @NotNull String var3, @NotNull String var4) {
      this(var1, var3, var4, 0, var2, (String)null, (String)null);
   }

   protected void a(@NotNull Ea var1) {
   }

   @NotNull
   public as0 b(Class var1) {
      try {
         return (as0)this.u.get(var1, this::lambda$getModule$1);
      } catch (ExecutionException var3) {
         throw new RuntimeException(var3);
      }
   }

   protected void a(@Nullable Ua var1, boolean var2) {
      int[] var3 = d();
      if(var1 != null) {
         Ua var4 = var1.a(FJ.c);
      }

      IChatComponent var5 = FJ.i;
      this.w.thePlayer.addChatComponentMessage(var5);
   }

   protected void a(@Nullable Ua var1) {
      this.a(var1, false);
   }

   protected void a(@NotNull String var1, boolean var2) {
      this.a(aJ1.b(var1), var2);
   }

   protected void a(@NotNull String var1) {
      this.a(aJ1.b(var1));
   }

   protected void r() {
      this.w.thePlayer.addChatComponentMessage(aJ1.a());
   }

   public float w() {
      switch(dQ.a[this.p.ordinal()]) {
      case 1:
         return 0.9F;
      case 2:
         return 0.55F;
      case 3:
         return 0.45F;
      case 4:
         return 0.1F;
      default:
         return 0.0F;
      }
   }

   protected void a(@NotNull ap9 var1) {
      int[] var2 = d();
      if(var1.a() == PacketDirection.INCOMING) {
         if(var1.d() instanceof S45PacketTitle) {
            S45PacketTitle var3 = (S45PacketTitle)var1.d();
            if(var3.getType() == S45PacketTitle$Type.TITLE) {
               String var4 = var3.getMessage().getUnformattedText();
               if(var4.equals("VICTORY!")) {
                  this.e();
                  this.j.t().a(this.v + " was disabled, because game has ended", 1000, azi.WARNING);
               }
            }
         }

         if(var1.d() instanceof S02PacketChat) {
            S02PacketChat var5 = (S02PacketChat)var1.d();
            String var7 = var5.getChatComponent().getUnformattedText();
            if(var7.equalsIgnoreCase("You died! Want to play again? Click here! ")) {
               this.j.t().a(this.v + " was disabled, because game has ended", 1000, azi.WARNING);
               this.e();
            }
         }

         if((lS.a(WL.SW) || lS.a(WL.BW)) && var1.d() instanceof S39PacketPlayerAbilities) {
            S39PacketPlayerAbilities var6 = (S39PacketPlayerAbilities)var1.d();
            if(var6.e()) {
               this.j.t().a(this.v + " was disabled, because game has ended", 1000, azi.WARNING);
               this.e();
            }
         }
      }

   }

   protected void c(@NotNull Class var1) {
      d();
      as0 var3 = this.b(var1);
      if(var3.y()) {
         this.j.t().a(var3.j() + " was disabled to prevent flags/errors", 1000, azi.WARNING);
         var3.e();
      }

   }

   protected void a(@NotNull Class... var1) {
      int[] var2 = d();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         Class var6 = var1[var5];
         as0 var7 = this.b(var6);
         if(var7.y()) {
            var7.e();
            this.j.t().a(this.f(), var7.v + " was disabled to prevent flags/errors", 1000, azi.WARNING);
         }

         ++var5;
      }

   }

   protected boolean a(@NotNull Class var1) {
      return this.b(var1).y();
   }

   public void b(@NotNull Packet var1) {
      int[] var2 = d();
      if(this.w.thePlayer != null) {
         this.w.thePlayer.sendQueue.b(var1);
      }

   }

   public void a(@NotNull Packet var1) {
      int[] var2 = d();
      if(this.w.thePlayer != null) {
         this.w.thePlayer.sendQueue.addToSendQueue(var1);
      }

   }

   public void a(Object... var1) {
      if(this.o.a()) {
         String var2 = Arrays.toString(var1);
         this.w.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var2));
      }

   }

   public void c(boolean var1) {
      d();
      this.a = var1;
      if(this.a && this.y()) {
         this.e();
         this.j.t().a(this.f() + " is currently detected!", "Can\'t turn that on right now, however, it will be usable again soon!", 3000, azi.WARNING);
      }

   }

   public boolean h() {
      return this.a;
   }

   public boolean e() {
      int[] var1 = d();
      if(((Boolean)this.k.a()).booleanValue()) {
         this.b(false);
         return false;
      } else {
         this.b(true);
         return true;
      }
   }

   public void u() {
      int[] var1 = d();
      if(this.y()) {
         this.e();
      }

   }

   public void c(String var1) {
      int[] var2 = d();
      if(var1 == null || !var1.isEmpty()) {
         String var3 = ((String)((ava)this.b(ava.class)).w().a()).toLowerCase();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -902286926:
            if(!var3.equals("simple")) {
               break;
            }

            var4 = 0;
         case 3075986:
            if(!var3.equals("dash")) {
               break;
            }

            var4 = 1;
         case 137407656:
            if(var3.equals("bracket")) {
               var4 = 2;
            }
         }

         switch(var4) {
         case 0:
            this.l = "§7 " + var1;
         case 1:
            this.l = "§7 - " + var1;
         case 2:
            this.l = "§7 [" + var1 + "]";
         default:
            this.l = "";
         }
      }

      this.l = "";
   }

   public final boolean y() {
      return ((Boolean)this.k.a()).booleanValue();
   }

   public void b(boolean var1) {
      int[] var2 = d();
      if(((Boolean)this.k.a()).booleanValue() != var1) {
         this.k.a(Boolean.valueOf(var1));
         if(var1) {
            this.q();
         }

         this.a();
      }

   }

   public final void q() {
      d();
      aaz.b((Object)this);
      this.n();
      if(this.a) {
         this.e();
         this.j.t().a(this.f() + " is currently detected!", "Can\'t turn that on right now, however, it will be usable again soon!", 3000, azi.WARNING);
      }

      if(!this.j.d().a().contains(this) && this != this.j.d().b(av2.class)) {
         this.j.d().a().add(this);
      }

      this.h = ((ava)this.j.d().b(ava.class)).a(this) - 1.0F;
      this.s = 0.0F;
   }

   public final void a() {
      aaz.a((Object)this);
      this.c();
   }

   public void n() {
   }

   public void c() {
   }

   @NotNull
   public String f() {
      d();
      String var2 = (String)this.t.a();
      return var2 != null && !var2.isEmpty()?var2:this.n;
   }

   public final boolean l() {
      return ((Boolean)this.d.a()).booleanValue();
   }

   public void a(boolean var1) {
      this.d.a(Boolean.valueOf(var1));
   }

   @NotNull
   public String x() {
      int[] var1 = d();
      return this.l == null?"":this.l;
   }

   @NotNull
   public String k() {
      return this.f() + this.x();
   }

   @NotNull
   public String j() {
      return this.v;
   }

   public aEF o() {
      return this.e;
   }

   @Nullable
   public String t() {
      return this.m;
   }

   @NotNull
   public a2V m() {
      return this.p;
   }

   public a0E b() {
      return this.i;
   }

   @NotNull
   public Logger v() {
      return this.r;
   }

   @NotNull
   public String g() {
      return this.n;
   }

   public void b(@NotNull String var1) {
      this.n = var1;
   }

   public void a(ProtocolPathEntry var1) {
      this.e.a(var1);
   }

   public int p() {
      return this.g;
   }

   public void d(String var1) {
      this.t.e(var1);
   }

   @NotNull
   public gZ s() {
      return this.j;
   }

   public boolean i() {
      int[] var1 = d();
      return Integer.parseInt(net.skidunion.J.aK) < 3;
   }

   private as0 lambda$getModule$1(Class var1) throws Exception {
      return this.f.b(var1);
   }

   private static boolean lambda$new$0(TypeToken var0) {
      d();
      Class var2 = var0.getRawType();

      boolean var3;
      while(true) {
         if(!(var3 = var2.isAnnotationPresent(J8.class))) {
            var2 = var2.getSuperclass();
         }

         if(var3 || var2 == null || var2.getSuperclass() == null) {
            break;
         }
      }

      return var3;
   }

   public static void b(int[] var0) {
      q = var0;
   }

   public static int[] d() {
      return q;
   }

   private static awl a(awl var0) {
      return var0;
   }

   static {
      b(new int[2]);
   }
}
