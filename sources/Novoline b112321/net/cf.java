package net;

import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.Dk;
import net.aMz;
import net.aRi;
import net.bgR;
import net.cA;
import net.t4;

public class cf extends cA implements ClientEntityIdChangeListener {
   private final Map j = new ConcurrentHashMap();
   private final Map m = new ConcurrentHashMap();
   private final Map g;
   private final Map e;
   private final Map d;
   private final Map l;
   private final Map i;
   private int f;
   private int k;
   private int h;
   private int c;
   private static String n;

   public cf(bgR var1) {
      super(var1);
      b();
      this.g = new ConcurrentHashMap();
      this.e = new ConcurrentHashMap();
      this.d = new HashMap();
      this.l = new HashMap();
      this.i = new HashMap();
      this.f = 0;
      this.k = -1;
      this.h = -1;
      this.c = 0;
      if(PacketRemapper.b() == null) {
         b("SLmDAb");
      }

   }

   public void i(int var1) {
      b();
      this.j.remove(Integer.valueOf(var1));
      if(this.e.containsKey(Integer.valueOf(var1))) {
         ((EntityReplacement)this.e.remove(Integer.valueOf(var1))).despawn();
      }

      if(this.d.containsKey(Integer.valueOf(var1))) {
         this.l.remove(this.d.remove(Integer.valueOf(var1)));
      }

   }

   public void a(Integer var1, UUID var2) {
      this.l.put(var2, var1);
      this.d.put(var1, var2);
   }

   public UUID e(int var1) {
      return (UUID)this.d.get(Integer.valueOf(var1));
   }

   public int b(UUID var1) {
      return ((Integer)this.l.getOrDefault(var1, Integer.valueOf(-1))).intValue();
   }

   public aMz[] a(UUID var1) {
      return (aMz[])this.i.get(var1);
   }

   public void a(UUID var1, aMz[] var2) {
      this.i.put(var1, var2);
   }

   public Map g() {
      return this.j;
   }

   public void a(int var1, List var2) {
      String var3 = b();
      if(this.m.containsKey(Integer.valueOf(var1))) {
         ((List)this.m.get(Integer.valueOf(var1))).addAll(var2);
      }

      if(!var2.isEmpty()) {
         this.m.put(Integer.valueOf(var1), var2);
      }

   }

   public void a(EntityReplacement var1) {
      this.e.put(Integer.valueOf(var1.getEntityId()), var1);
   }

   public EntityReplacement a(int var1) {
      return (EntityReplacement)this.e.get(Integer.valueOf(var1));
   }

   public List h(int var1) {
      return (List)this.m.get(Integer.valueOf(var1));
   }

   public void g(int var1) {
      String var2 = b();
      if(this.m.containsKey(Integer.valueOf(var1))) {
         if(this.e.containsKey(Integer.valueOf(var1))) {
            ((EntityReplacement)this.e.get(Integer.valueOf(var1))).updateMetadata((List)this.m.remove(Integer.valueOf(var1)));
         }

         t4 var3 = (t4)this.g().get(Integer.valueOf(var1));
         PacketWrapperImpl var4 = new PacketWrapperImpl(28, (ByteBuf)null, this.d());
         var4.a(Type.VAR_INT, Integer.valueOf(var1));
         var4.a(Types1_8.METADATA_LIST, this.m.get(Integer.valueOf(var1)));
         Dk.a(var3, (List)this.m.get(Integer.valueOf(var1)));
         if(!((List)this.m.get(Integer.valueOf(var1))).isEmpty()) {
            PacketUtil.b(var4, aRi.class);
         }

         this.m.remove(Integer.valueOf(var1));
      }
   }

   public int j(int var1) {
      b();
      Iterator var3 = this.g.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if(((Integer)var4.getValue()).intValue() == var1) {
            return ((Integer)var4.getValue()).intValue();
         }
      }

      return -1;
   }

   public int f(int var1) {
      return ((Integer)this.g.getOrDefault(Integer.valueOf(var1), Integer.valueOf(-1))).intValue();
   }

   public void a(int var1, int var2) {
      String var3 = b();
      if(var1 == this.h && this.h != this.k) {
         try {
            PacketWrapperImpl var4 = new PacketWrapperImpl(11, (ByteBuf)null, this.d());
            var4.a(Type.VAR_INT, Integer.valueOf(this.k));
            var4.a(Type.VAR_INT, Integer.valueOf(0));
            var4.a(Type.VAR_INT, Integer.valueOf(0));
            PacketWrapperImpl var5 = new PacketWrapperImpl(11, (ByteBuf)null, this.d());
            var5.a(Type.VAR_INT, Integer.valueOf(this.k));
            var5.a(Type.VAR_INT, Integer.valueOf(1));
            var5.a(Type.VAR_INT, Integer.valueOf(0));
            PacketUtil.a(var4, aRi.class, true, true);
            this.d(this.k);
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      if(var1 == -1) {
         int var7 = this.j(var2);
         this.g.remove(Integer.valueOf(var7));
      }

      if(var2 == -1) {
         this.g.remove(Integer.valueOf(var1));
      }

      this.g.put(Integer.valueOf(var1), Integer.valueOf(var2));
   }

   public int d() {
      return this.h;
   }

   public boolean d(int var1) {
      String var2 = b();
      if(var1 != this.k && this.f(var1) != -1) {
         PacketWrapperImpl var6 = new PacketWrapperImpl(11, (ByteBuf)null, this.d());
         var6.a(Type.VAR_INT, Integer.valueOf(this.k));
         var6.a(Type.VAR_INT, Integer.valueOf(0));
         var6.a(Type.VAR_INT, Integer.valueOf(0));
         PacketWrapperImpl var4 = new PacketWrapperImpl(11, (ByteBuf)null, this.d());
         var4.a(Type.VAR_INT, Integer.valueOf(this.k));
         var4.a(Type.VAR_INT, Integer.valueOf(1));
         var4.a(Type.VAR_INT, Integer.valueOf(0));
         PacketUtil.a(var6, aRi.class, true, true);
         this.d(this.k);
         return false;
      } else {
         if(this.h != var1 && this.h != this.k) {
            PacketWrapperImpl var3 = new PacketWrapperImpl(27, (ByteBuf)null, this.d());
            var3.a(Type.I, Integer.valueOf(this.k));
            var3.a(Type.I, Integer.valueOf(-1));
            var3.a(Type.c, Boolean.valueOf(false));
            PacketUtil.b(var3, aRi.class);
         }

         this.h = var1;
         if(var1 != this.k) {
            PacketWrapperImpl var5 = new PacketWrapperImpl(27, (ByteBuf)null, this.d());
            var5.a(Type.I, Integer.valueOf(this.k));
            var5.a(Type.I, Integer.valueOf(this.h));
            var5.a(Type.c, Boolean.valueOf(false));
            PacketUtil.b(var5, aRi.class);
         }

         return true;
      }
   }

   public int a() {
      return this.f;
   }

   public void k(int var1) {
      this.f = var1;
   }

   public int c() {
      return this.k;
   }

   public void b(int var1) {
      String var2 = b();
      if(this.k != -1) {
         throw new IllegalStateException("playerId was already set!");
      } else {
         this.k = this.h = var1;
      }
   }

   public void e() {
      this.j.clear();
      this.e.clear();
      this.g.clear();
      this.m.clear();
   }

   public int f() {
      return this.c;
   }

   public void c(int var1) {
      this.c = var1;
   }

   public void setClientEntityId(int var1) {
      String var2 = b();
      if(this.h == this.k) {
         this.h = var1;
      }

      this.j.remove(Integer.valueOf(this.k));
      this.k = var1;
      this.j.put(Integer.valueOf(this.k), t4.ENTITY_HUMAN);
   }

   public static void b(String var0) {
      n = var0;
   }

   public static String b() {
      return n;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      if(b() == null) {
         b("vOaim");
      }

   }
}
