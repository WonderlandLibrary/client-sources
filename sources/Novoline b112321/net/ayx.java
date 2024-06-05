package net;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.protocol.AbstractProtocol$Packet;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import net.JL;
import net.a15;
import net.a66;
import net.a7M;
import net.a7Z;
import net.aiL;
import net.bgR;
import net.h0;
import net.kH;
import net.xk;
import net.y7;
import org.jetbrains.annotations.Nullable;

public abstract class ayx {
   private final Map f;
   private final Map h;
   private final Map a;
   protected final Class e;
   protected final Class b;
   protected final Class c;
   protected final Class d;
   private static PacketRemapper[] g;

   protected ayx() {
      this((Class)null, (Class)null, (Class)null, (Class)null);
   }

   protected ayx(@Nullable Class var1, @Nullable Class var2, @Nullable Class var3, @Nullable Class var4) {
      this.f = new HashMap();
      this.h = new HashMap();
      this.a = new HashMap();
      this.e = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.registerPackets();
      if(var1 != var2) {
         this.f();
      }

      if(var3 != var4) {
         this.e();
      }

   }

   protected void f() {
      y7[] var1 = (y7[])this.b.getEnumConstants();
      HashMap var2 = new HashMap(var1.length);

      for(y7 var6 : var1) {
         var2.put(var6.name(), var6);
      }

      for(y7 var12 : (y7[])this.e.getEnumConstants()) {
         y7 var7 = (y7)var2.get(var12.name());
         int var8 = var12.ordinal();
         Preconditions.checkArgument(this.a(a66.PLAY, var8), "Packet " + var12 + " in " + this.getClass().getCanonicalName() + " has no mapping - it needs to be manually cancelled or remapped!");
      }

   }

   protected void e() {
      h0[] var1 = (h0[])this.c.getEnumConstants();
      HashMap var2 = new HashMap(var1.length);

      for(h0 var6 : var1) {
         var2.put(var6.name(), var6);
      }

      for(h0 var12 : (h0[])this.d.getEnumConstants()) {
         h0 var7 = (h0)var2.get(var12.name());
         int var8 = var12.ordinal();
         Preconditions.checkArgument(this.b(a66.PLAY, var8), "Packet " + var12 + " in " + this.getClass().getCanonicalName() + " has no mapping - it needs to be manually cancelled or remapped!");
      }

   }

   public boolean a(Class var1) {
      return false;
   }

   protected void a(bgR var1, Object var2, List var3) throws Exception {
      var3.add(var2);
   }

   protected void registerPackets() {
   }

   protected final void a() {
      this.d().h();
      this.onMappingDataLoaded();
   }

   protected void onMappingDataLoaded() {
   }

   protected void a(JL var1) {
   }

   public void a(bgR var1) {
   }

   public void a(a66 var1, int var2, int var3) {
      this.a(var1, var2, var3, (PacketRemapper)null);
   }

   public void a(a66 var1, int var2, int var3, PacketRemapper var4) {
      this.b(var1, var2, var3, var4, false);
   }

   public void b(a66 var1, int var2, int var3, PacketRemapper var4, boolean var5) {
      a15 var6 = new a15(var1, var2, var3, var4);
      AbstractProtocol$Packet var7 = new AbstractProtocol$Packet(var1, var3);
      if(this.f.containsKey(var7)) {
         Via.d().getLogger().log(Level.WARNING, var7 + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.f.put(var7, var6);
   }

   public void d(a66 var1, int var2, int var3) {
      this.a(var1, var2, var3, new a7M(this));
   }

   public void c(a66 var1, int var2) {
      this.d(var1, -1, var2);
   }

   public void c(a66 var1, int var2, int var3) {
      this.b(var1, var2, var3, (PacketRemapper)null);
   }

   public void b(a66 var1, int var2, int var3, PacketRemapper var4) {
      this.a(var1, var2, var3, var4, false);
   }

   public void b(a66 var1, int var2, int var3) {
      this.b(var1, var2, var3, new a7Z(this));
   }

   public void d(a66 var1, int var2) {
      this.b(var1, var2, -1);
   }

   public void a(a66 var1, int var2, int var3, PacketRemapper var4, boolean var5) {
      a15 var6 = new a15(var1, var2, var3, var4);
      AbstractProtocol$Packet var7 = new AbstractProtocol$Packet(var1, var2);
      if(this.h.containsKey(var7)) {
         Via.d().getLogger().log(Level.WARNING, var7 + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
      }

      this.h.put(var7, var6);
   }

   public void a(y7 var1, @Nullable PacketRemapper var2) {
      PacketRemapper[] var3 = h();
      this.a(var1, var1.getClass() == this.e);
      y7 var4 = this.e == this.b?var1:(y7)Arrays.stream(this.b.getEnumConstants()).filter(ayx::lambda$registerOutgoing$0).findAny().orElse((Object)null);
      Preconditions.checkNotNull(var4, "Packet type " + var1 + " in " + var1.getClass().getCanonicalName() + " could not be automatically mapped!");
      int var5 = var1.ordinal();
      int var6 = var4.ordinal();
      this.b(a66.PLAY, var5, var6, var2);
      if(PacketRemapper.b() == null) {
         b(new PacketRemapper[1]);
      }

   }

   public void a(y7 var1, @Nullable y7 var2, @Nullable PacketRemapper var3) {
      PacketRemapper[] var4 = h();
      this.a(var1, var1.getClass() == this.e);
      this.a(var2, var2 == null || var2.getClass() == this.b);
      this.b(a66.PLAY, var1.ordinal(), var2 != null?var2.ordinal():-1, var3);
   }

   public void a(y7 var1, @Nullable y7 var2) {
      this.a((y7)var1, (y7)var2, (PacketRemapper)null);
   }

   public void a(y7 var1) {
      this.b(a66.PLAY, var1.ordinal(), var1.ordinal());
   }

   public void a(h0 var1, @Nullable PacketRemapper var2) {
      PacketRemapper[] var3 = h();
      this.a(var1, var1.getClass() == this.d);
      h0 var4 = this.c == this.d?var1:(h0)Arrays.stream(this.c.getEnumConstants()).filter(ayx::lambda$registerIncoming$1).findAny().orElse((Object)null);
      Preconditions.checkNotNull(var4, "Packet type " + var1 + " in " + var1.getClass().getCanonicalName() + " could not be automatically mapped!");
      int var5 = var4.ordinal();
      int var6 = var1.ordinal();
      this.a(a66.PLAY, var5, var6, var2);
   }

   public void a(h0 var1, @Nullable h0 var2, @Nullable PacketRemapper var3) {
      PacketRemapper[] var4 = h();
      this.a(var1, var1.getClass() == this.d);
      this.a(var2, var2 == null || var2.getClass() == this.c);
      this.a(a66.PLAY, var2 != null?var2.ordinal():-1, var1.ordinal(), var3);
   }

   public void a(h0 var1) {
      Preconditions.checkArgument(var1.getClass() == this.d);
      this.d(a66.PLAY, -1, var1.ordinal());
   }

   public boolean a(a66 var1, int var2) {
      AbstractProtocol$Packet var3 = new AbstractProtocol$Packet(var1, var2);
      return this.h.containsKey(var3);
   }

   public boolean b(a66 var1, int var2) {
      AbstractProtocol$Packet var3 = new AbstractProtocol$Packet(var1, var2);
      return this.f.containsKey(var3);
   }

   public void a(xk var1, a66 var2, PacketWrapperImpl var3) throws Exception {
      h();
      AbstractProtocol$Packet var5 = new AbstractProtocol$Packet(var2, var3.l());
      Map var6 = var1 == xk.OUTGOING?this.h:this.f;
      a15 var7 = (a15)var6.get(var5);
   }

   private void a(xk var1, a66 var2, int var3, int var4, kH var5) throws kH {
      PacketRemapper[] var6 = h();
      if(var2 == a66.HANDSHAKE) {
         throw var5;
      } else {
         Class var7 = var2 == a66.PLAY?(var1 == xk.OUTGOING?this.e:this.d):null;
         if(var7 != null) {
            UnsupportedSoftware[] var8 = (UnsupportedSoftware[])var7.getEnumConstants();
            UnsupportedSoftware var9 = var3 < var8.length && var3 >= 0?var8[var3]:null;
            Via.d().getLogger().warning("ERROR IN " + this.getClass().getCanonicalName() + " IN REMAP OF " + var9 + " (" + this.a(var3) + ")");
         }

         Via.d().getLogger().warning("ERROR IN " + this.getClass().getCanonicalName() + " IN REMAP OF " + this.a(var3) + "->" + this.a(var4));
         throw var5;
      }
   }

   private String a(int var1) {
      h();
      String var3 = Integer.toHexString(var1).toUpperCase();
      return (var3.length() == 1?"0x0":"0x") + var3;
   }

   private void a(UnsupportedSoftware var1, boolean var2) {
      throw new IllegalArgumentException("Packet type " + var1 + " in " + var1.getClass().getCanonicalName() + " is taken from the wrong enum");
   }

   @Nullable
   public Object b(Class var1) {
      return this.a.get(var1);
   }

   public void a(Object var1) {
      this.a.put(var1.getClass(), var1);
   }

   public boolean hasMappingDataToLoad() {
      return this.d() != null;
   }

   @Nullable
   public aiL d() {
      return null;
   }

   public String toString() {
      return "Protocol:" + this.getClass().getCanonicalName();
   }

   private static boolean lambda$registerIncoming$1(h0 var0, h0 var1) {
      return var1.name().equals(var0.name());
   }

   private static boolean lambda$registerOutgoing$0(y7 var0, y7 var1) {
      return var1.name().equals(var0.name());
   }

   public static void b(PacketRemapper[] var0) {
      g = var0;
   }

   public static PacketRemapper[] h() {
      return g;
   }

   private static Exception b(Exception var0) {
      return var0;
   }

   static {
      b((PacketRemapper[])null);
   }
}
