package net;

import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.bgR;
import net.cA;
import org.jetbrains.annotations.Nullable;

public abstract class cp extends cA implements ClientEntityIdChangeListener {
   private final Map c;
   private final ConnectionManager f;
   private int e;
   private static String[] d;

   protected cp(bgR var1, ConnectionManager var2) {
      a();
      super(var1);
      this.c = new ConcurrentHashMap();
      this.f = var2;
   }

   public void b(int var1) {
      this.c.remove(Integer.valueOf(var1));
   }

   public void a(int var1, ConnectionManager var2) {
      this.c.put(Integer.valueOf(var1), var2);
   }

   public boolean c(int var1) {
      return this.c.containsKey(Integer.valueOf(var1));
   }

   @Nullable
   public ConnectionManager e(int var1) {
      return (ConnectionManager)this.c.get(Integer.valueOf(var1));
   }

   public void setClientEntityId(int var1) {
      a();
      this.e = var1;
      this.c.put(Integer.valueOf(var1), this.f);
      if(PacketRemapper.b() == null) {
         b(new String[1]);
      }

   }

   public int b() {
      return this.e;
   }

   public void d(int var1) {
      this.e = var1;
   }

   public static void b(String[] var0) {
      d = var0;
   }

   public static String[] a() {
      return d;
   }

   static {
      if(a() != null) {
         b(new String[3]);
      }

   }
}
