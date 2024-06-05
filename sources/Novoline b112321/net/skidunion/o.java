package net.skidunion;

import java.net.URI;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.A;
import net.skidunion.H;
import net.skidunion.I;
import net.skidunion.Q;
import net.skidunion.T;
import net.skidunion.V;
import net.skidunion.X;
import net.skidunion.Y;
import net.skidunion.a1;
import net.skidunion.a4;
import net.skidunion.a7;
import net.skidunion.a9;
import net.skidunion.aC;
import net.skidunion.aD;
import net.skidunion.aH;
import net.skidunion.aK;
import net.skidunion.aM;
import net.skidunion.aV;
import net.skidunion.aW;
import net.skidunion.aZ;
import net.skidunion.ac;
import net.skidunion.af;
import net.skidunion.ai;
import net.skidunion.am;
import net.skidunion.an;
import net.skidunion.ao;
import net.skidunion.ar;
import net.skidunion.as;
import net.skidunion.av;
import net.skidunion.c;
import net.skidunion.d;
import net.skidunion.g;
import net.skidunion.q;
import net.skidunion.y;
import net.skidunion.z;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u00107\u001a\u000208J\u0016\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010<\u001a\u00020+J\b\u0010=\u001a\u00020>H\u0016J\u0016\u0010?\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010@\u001a\u00020+J\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020+0:2\u0006\u0010@\u001a\u00020+J\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020C0:2\u0006\u0010@\u001a\u00020+J\u0012\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020C0E0:J\u001a\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020C0E0:2\u0006\u0010F\u001a\u00020>J\u0006\u0010G\u001a\u00020>J\u0006\u0010H\u001a\u00020>J\u0016\u0010I\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010J\u001a\u00020+J\u001e\u0010K\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010L\u001a\u00020+2\u0006\u0010M\u001a\u00020NJ \u0010O\u001a\u0002082\u0006\u0010P\u001a\u00020N2\u0006\u0010Q\u001a\u00020+2\u0006\u0010R\u001a\u00020>H\u0016J\u0010\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u0002082\u0006\u0010<\u001a\u00020+H\u0016J\u0010\u0010W\u001a\u0002082\u0006\u0010X\u001a\u00020YH\u0016J\u0010\u0010Z\u001a\u0002082\u0006\u0010[\u001a\u00020+H\u0016J\u0016\u0010\\\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010]\u001a\u00020^J\u001e\u0010_\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010`\u001a\u00020+2\u0006\u0010]\u001a\u00020^J\u0015\u0010a\u001a\u0002082\u0006\u0010b\u001a\u00020cH\u0000¢\u0006\u0002\bdJ\u0016\u0010e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010L\u001a\u00020+J\u000e\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020iJ\u001e\u0010j\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:2\u0006\u0010@\u001a\u00020+2\u0006\u0010h\u001a\u00020+R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001b\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\'0&¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u00020+X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010-\"\u0004\b2\u0010/R\u0011\u00103\u001a\u000204¢\u0006\b\n\u0000\u001a\u0004\b5\u00106¨\u0006k"},
   d2 = {"Lnet/skidunion/o;", "Lorg/java_websocket/client/WebSocketClient;", "serverURI", "Ljava/net/URI;", "(Ljava/net/URI;)V", "j", "Ljava/util/concurrent/CountDownLatch;", "n", "()Ljava/util/concurrent/CountDownLatch;", "a", "(Ljava/util/concurrent/CountDownLatch;)V", "f", "Lnet/skidunion/c;", "h", "()Lnet/skidunion/c;", "a", "(Lnet/skidunion/c;)V", "i", "Lnet/skidunion/Y;", "c", "()Lnet/skidunion/Y;", "a", "Lnet/skidunion/d;", "l", "()Lnet/skidunion/d;", "a", "(Lnet/skidunion/d;)V", "g", "Lnet/skidunion/Q;", "d", "Lorg/apache/logging/log4j/Logger;", "b", "Lnet/skidunion/I;", "g", "()Lnet/skidunion/I;", "a", "(Lnet/skidunion/I;)V", "h", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Lnet/skidunion/Z;", "d", "()Ljava/util/concurrent/CopyOnWriteArrayList;", "e", "", "f", "()Ljava/lang/String;", "e", "(Ljava/lang/String;)V", "k", "e", "f", "l", "Lnet/skidunion/a4;", "a", "()Lnet/skidunion/a4;", "i", "", "d", "Lnet/skidunion/ac;", "", "message", "connectBlocking", "", "b", "name", "c", "h", "Lnet/skidunion/b;", "getConfigs", "", "self", "j", "m", "a", "username", "a", "target", "duration", "", "onClose", "code", "reason", "remote", "onError", "ex", "Ljava/lang/Exception;", "onMessage", "onOpen", "handshakedata", "Lorg/java_websocket/handshake/ServerHandshake;", "send", "text", "a", "contents", "", "a", "to", "sendPacket", "packet", "Lnet/skidunion/an;", "a", "g", "a", "Lnet/skidunion/g;", "data", "Lnet/skidunion/T;", "a", "client"}
)
public final class o extends WebSocketClient {
   private final Logger d;
   private final Q g;
   @NotNull
   public String e;
   @NotNull
   public String k;
   @NotNull
   private I b;
   @NotNull
   public c f;
   @NotNull
   public d a;
   @NotNull
   private final a4 l;
   @NotNull
   private final Y i;
   @NotNull
   private CountDownLatch j;
   @NotNull
   private final CopyOnWriteArrayList h;
   private static boolean c;

   @NotNull
   public final String f() {
      boolean var1 = o();
      String var10000 = this.e;
      if(this.e == null) {
         Intrinsics.throwUninitializedPropertyAccessException("secret");
      }

      return var10000;
   }

   public final void e(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.e = var1;
   }

   @NotNull
   public final String e() {
      boolean var1 = k();
      String var10000 = this.k;
      if(this.k == null) {
         Intrinsics.throwUninitializedPropertyAccessException("token");
      }

      return var10000;
   }

   public final void f(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.k = var1;
   }

   @NotNull
   public final I g() {
      return this.b;
   }

   public final void a(@NotNull I var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.b = var1;
   }

   @NotNull
   public final c h() {
      c var10000 = this.f;
      if(this.f == null) {
         Intrinsics.throwUninitializedPropertyAccessException("encryptionMethod");
      }

      return var10000;
   }

   public final void a(@NotNull c var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.f = var1;
   }

   @NotNull
   public final d l() {
      d var10000 = this.a;
      if(this.a == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hashFunction");
      }

      return var10000;
   }

   public final void a(@NotNull d var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.a = var1;
   }

   @NotNull
   public final a4 a() {
      return this.l;
   }

   @NotNull
   public final Y c() {
      return this.i;
   }

   @NotNull
   public final CountDownLatch n() {
      return this.j;
   }

   public final void a(@NotNull CountDownLatch var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.j = var1;
   }

   @NotNull
   public final CopyOnWriteArrayList d() {
      return this.h;
   }

   public boolean connectBlocking() {
      k();
      this.i.a((H)(new V(this)));
      this.d.info("Connecting...");
      String var10002 = this.e;
      if(this.e == null) {
         Intrinsics.throwUninitializedPropertyAccessException("secret");
      }

      this.addHeader("X-IRC-Secret", var10002);
      return super.connectBlocking();
   }

   public void onOpen(@NotNull ServerHandshake var1) {
      Intrinsics.checkParameterIsNotNull(var1, "handshakedata");
      this.d.info("Logging in...");
      this.b = I.LOGIN;
   }

   public void onClose(int var1, @NotNull String var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var2, "reason");
      o();
      this.d.info("Connection closed! (" + var1 + ": " + var2 + ')');
      this.i.a((q)(new y(var1, var2)));
      this.l.b().clear();
      this.l.c().clear();
      this.b = I.DISCONNECTED;
      switch(var1) {
      case 4004:
         throw (Throwable)X.a;
      default:
         this.d.info("Reconnecting in 5 seconds...");
         Thread.sleep(5000L);
         (new aC(this)).start();
      }
   }

   public void onMessage(@NotNull String var1) {
      k();
      Intrinsics.checkParameterIsNotNull(var1, "message");
      String var4;
      if(!this.m() && !this.j()) {
         var4 = var1;
      } else {
         c var10000 = this.f;
         if(this.f == null) {
            Intrinsics.throwUninitializedPropertyAccessException("encryptionMethod");
         }

         var4 = var10000.b(var1);
      }

      String var3 = var4;
      this.d.debug("<- " + var3);
      a1.a.a(this, var3);
   }

   public void onError(@NotNull Exception var1) {
      Intrinsics.checkParameterIsNotNull(var1, "ex");
      this.d.error("There was an error in WebSocket connection", (Throwable)var1);
      this.i.a((q)(new z((Throwable)var1)));
   }

   public void send(@NotNull String var1) {
      k();
      Intrinsics.checkParameterIsNotNull(var1, "text");
      this.d.debug("-> " + var1);
      if(this.j()) {
         c var10001 = this.f;
         if(this.f == null) {
            Intrinsics.throwUninitializedPropertyAccessException("encryptionMethod");
         }

         super.send(var10001.a(var1));
      }

      super.send(var1);
   }

   public final boolean m() {
      return this.b == I.LOGIN_SENT;
   }

   public final boolean j() {
      return this.b == I.AUTHENTICATED;
   }

   public final void i() {
      this.j.await();
   }

   public final void a(@NotNull an var1) {
      Intrinsics.checkParameterIsNotNull(var1, "packet");
      this.i.a((q)(new A(var1)));
      this.send(var1.toString());
   }

   @NotNull
   public final ac h(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return new ac(this, (an)(new aM(var1)), (ao)av.a);
   }

   @NotNull
   public final ac c(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return new ac(this, (an)(new af(var1)), (ao)as.a);
   }

   @NotNull
   public final ac c(boolean var1) {
      return new ac(this, (an)(new aZ(var1)), (ao)ar.h);
   }

   @NotNull
   public final ac b() {
      return this.c(false);
   }

   @NotNull
   public final ac b(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return new ac(this, (an)(new aH(var1)), (ao)am.h);
   }

   @NotNull
   public final ac a(@NotNull String var1, @NotNull String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "data");
      return new ac(this, (an)(new ai(var1, var2)), (ao)am.h);
   }

   @NotNull
   public final ac a(@NotNull Object var1) {
      Intrinsics.checkParameterIsNotNull(var1, "contents");
      return new ac(this, (an)(new aD(var1.toString())), (ao)am.h);
   }

   @NotNull
   public final ac a(@NotNull String var1, @NotNull Object var2) {
      Intrinsics.checkParameterIsNotNull(var1, "to");
      Intrinsics.checkParameterIsNotNull(var2, "contents");
      return new ac(this, (an)(new a9(var1, var2.toString())), (ao)am.h);
   }

   @NotNull
   public final g a(@NotNull T var1) {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      return new g(this, (an)(new a7(var1)));
   }

   @NotNull
   public final ac a(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "username");
      return new ac(this, (an)(new aW(var1)), (ao)am.h);
   }

   @NotNull
   public final ac d(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      return new ac(this, (an)(new aK(var1)), (ao)am.h);
   }

   @NotNull
   public final ac a(@NotNull String var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "target");
      return new ac(this, (an)(new aV(var1, var2, false)), (ao)am.h);
   }

   @NotNull
   public final ac g(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "target");
      return new ac(this, (an)(new aV(var1, 0, true)), (ao)am.h);
   }

   public o(@NotNull URI var1) {
      o();
      Intrinsics.checkParameterIsNotNull(var1, "serverURI");
      super(var1);
      Logger var10001 = LogManager.getLogger("IRCClient");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "LogManager.getLogger(\"IRCClient\")");
      this.d = var10001;
      this.g = new Q();
      this.b = I.CONNECTING;
      this.l = new a4(this);
      this.i = new Y();
      this.j = new CountDownLatch(1);
      this.h = new CopyOnWriteArrayList();
      if(acE.b() == null) {
         b(false);
      }

   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean o() {
      return c;
   }

   public static boolean k() {
      boolean var0 = o();
      return true;
   }

   private static X a(X var0) {
      return var0;
   }

   static {
      b(true);
   }
}
