package net;

import com.viaversion.viaversion.api.connection.ConnectionManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.BO;
import net.axx;
import org.jetbrains.annotations.Nullable;

public final class aqm {
   private final int a;
   private final ConnectionManager c;
   private final Map b;

   private aqm(int var1, ConnectionManager var2) {
      this.b = new ConcurrentHashMap();
      this.a = var1;
      this.c = var2;
   }

   @Nullable
   public BO a(Class var1) {
      return (BO)this.b.get(var1);
   }

   public boolean b(Class var1) {
      return this.b.containsKey(var1);
   }

   public void a(BO var1) {
      this.b.put(var1.getClass(), var1);
   }

   public int a() {
      return this.a;
   }

   public ConnectionManager b() {
      return this.c;
   }

   public String toString() {
      return "StoredEntity{entityId=" + this.a + ", type=" + this.c + ", storedObjects=" + this.b + '}';
   }

   aqm(int var1, ConnectionManager var2, axx var3) {
      this(var1, var2);
   }
}
