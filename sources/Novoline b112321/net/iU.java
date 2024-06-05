package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.bgR;
import org.jetbrains.annotations.Nullable;

public class iU {
   protected final Map d = new ConcurrentHashMap();
   protected final Set c = Collections.newSetFromMap(new ConcurrentHashMap());
   private static boolean b;

   public void b(bgR var1) {
      boolean var2 = c();
      Objects.requireNonNull(var1, "connection is null!");
      this.c.add(var1);
      if(this.c(var1)) {
         UUID var3 = var1.c().a();
         if(this.d.put(var3, var1) != null) {
            Via.d().getLogger().warning("Duplicate UUID on frontend connection! (" + var3 + ")");
         }
      }

      if(var1.p() != null) {
         var1.p().closeFuture().addListener(this::lambda$onLoginSuccess$0);
      }

   }

   public void a(bgR var1) {
      boolean var2 = c();
      Objects.requireNonNull(var1, "connection is null!");
      this.c.remove(var1);
      if(this.c(var1)) {
         UUID var3 = var1.c().a();
         this.d.remove(var3);
      }

      if(PacketRemapper.b() == null) {
         b(false);
      }

   }

   public boolean c(bgR var1) {
      boolean var2 = c();
      return !var1.b();
   }

   public Map a() {
      return Collections.unmodifiableMap(this.d);
   }

   @Nullable
   public bgR a(UUID var1) {
      return (bgR)this.d.get(var1);
   }

   @Nullable
   public UUID d(bgR var1) {
      boolean var2 = d();
      if(var1.c() == null) {
         return null;
      } else {
         UUID var3 = var1.c().a();
         bgR var4 = (bgR)this.d.get(var3);
         return var4 != null && var4.equals(var1)?var3:null;
      }
   }

   public Set b() {
      return Collections.unmodifiableSet(this.c);
   }

   public boolean b(UUID var1) {
      return this.d.containsKey(var1);
   }

   private void lambda$onLoginSuccess$0(bgR var1, ChannelFuture var2) throws Exception {
      this.a(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean c() {
      return b;
   }

   public static boolean d() {
      boolean var0 = c();
      return true;
   }

   static {
      b(true);
   }
}
