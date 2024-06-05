package net;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import net.aRY;
import net.aTi;
import net.acE;
import net.rX;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.boss.BossBar;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.storage.EntityTracker;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;
import viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;

public class cq extends EntityTracker {
   private final Map t;
   private final Map i;
   private final Map s;
   private final Map j;
   private final Set p;
   private final Set h;
   private final Set o;
   private boolean q;
   private boolean r;
   private Position g;
   private boolean n;
   private GameMode m;
   private String l;
   private static String k;

   public cq(UserConnection var1) {
      c();
      super(var1, t4.PLAYER);
      this.t = new ConcurrentHashMap();
      this.i = new ConcurrentHashMap();
      this.s = new ConcurrentHashMap();
      this.j = new ConcurrentHashMap();
      this.p = Sets.newConcurrentHashSet();
      this.h = Sets.newConcurrentHashSet();
      this.o = Collections.newSetFromMap(CacheBuilder.newBuilder().maximumSize(10L).expireAfterAccess(250L, TimeUnit.MILLISECONDS).build().asMap());
      this.q = false;
      this.r = false;
      this.g = null;
      this.n = false;
   }

   public UUID a(int var1) {
      c();
      UUID var3 = (UUID)this.t.get(Integer.valueOf(var1));
      if(var3 == null) {
         var3 = UUID.randomUUID();
         this.t.put(Integer.valueOf(var1), var3);
      }

      return var3;
   }

   public void a(Item var1) {
      this.a(this.getClientEntityId(), var1);
   }

   public void a(int var1, Item var2) {
      PacketWrapper var3 = new PacketWrapper(60, (ByteBuf)null, this.d());
      var3.write(Type.VAR_INT, Integer.valueOf(var1));
      var3.write(Type.VAR_INT, Integer.valueOf(1));
      var3.write(Type.ITEM, var2);
      PacketWrapper var10000 = var3;
      Class var10001 = aRY.class;

      try {
         var10000.send(var10001);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void removeEntity(int var1) {
      c();
      super.removeEntity(var1);
      this.s.remove(Integer.valueOf(var1));
      this.t.remove(Integer.valueOf(var1));
      this.p.remove(Integer.valueOf(var1));
      this.h.remove(Integer.valueOf(var1));
      this.i.remove(Integer.valueOf(var1));
      BossBar var3 = (BossBar)this.j.remove(Integer.valueOf(var1));
      if(var3 != null) {
         var3.hide();
         ((BossBarProvider)Via.getManager().f().b(BossBarProvider.class)).handleRemove(this.d(), var3.getId());
      }

      if(acE.b() == null) {
         b("RP4tUb");
      }

   }

   public boolean a(int var1, int var2, int var3) {
      return this.o.contains(new Position(var1, (short)var2, var3));
   }

   public void b(Position var1) {
      this.o.add(var1);
   }

   public void a(int var1, List var2) {
      c();
      EntityType var4 = this.getEntity(var1);
   }

   public Metadata a(List var1, int var2) {
      c();
      Iterator var4 = var1.iterator();
      if(var4.hasNext()) {
         Metadata var5 = (Metadata)var4.next();
         if(var2 == var5.getId()) {
            return var5;
         }
      }

      return null;
   }

   public void a(boolean var1, boolean var2) {
      c();
      PacketWrapper var4 = new PacketWrapper(65, (ByteBuf)null, this.d());
      var4.write(Type.STRING, "viaversion");
      if(!this.n) {
         var4.write(Type.BYTE, Byte.valueOf((byte)0));
         var4.write(Type.STRING, "viaversion");
         var4.write(Type.STRING, "§f");
         var4.write(Type.STRING, "");
         var4.write(Type.BYTE, Byte.valueOf((byte)0));
         var4.write(Type.STRING, "");
         var4.write(Type.STRING, "never");
         var4.write(Type.BYTE, Byte.valueOf((byte)15));
      }

      var4.write(Type.BYTE, Byte.valueOf((byte)3));
      var4.write(Type.STRING_ARRAY, new String[]{this.d().getProtocolInfo().getUsername()});
      var4.write(Type.BYTE, Byte.valueOf((byte)1));
      this.n = var1;
      PacketWrapper var10000 = var4;
      Class var10001 = aRY.class;
      boolean var10002 = true;
      boolean var10003 = var2;

      try {
         var10000.send(var10001, var10002, var10003);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public void b(int var1, List var2) {
      c();
      List var4 = (List)this.i.get(Integer.valueOf(var1));
      if(var4 != null) {
         var4.addAll(var2);
      }

      this.i.put(Integer.valueOf(var1), var2);
   }

   public void c(int var1) {
      c();
      List var3 = (List)this.i.get(Integer.valueOf(var1));
      PacketWrapper var4 = new PacketWrapper(57, (ByteBuf)null, this.d());
      var4.write(Type.VAR_INT, Integer.valueOf(var1));
      var4.write(rX.a, var3);
      ((aTi)((aRY)this.d().getProtocolInfo().getPipeline().getProtocol(aRY.class)).get(aTi.class)).handleMetadata(var1, var3, this.d());
      this.a(var1, var3);
      if(!var3.isEmpty()) {
         PacketWrapper var10000 = var4;
         Class var10001 = aRY.class;

         try {
            var10000.send(var10001);
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      this.i.remove(Integer.valueOf(var1));
   }

   public int e() {
      try {
         return ((EntityIdProvider)Via.getManager().f().b(EntityIdProvider.class)).getEntityId(this.d());
      } catch (Exception var2) {
         return this.getClientEntityId();
      }
   }

   public Map g() {
      return this.t;
   }

   public Map a() {
      return this.i;
   }

   public Map b() {
      return this.s;
   }

   public Map o() {
      return this.j;
   }

   public Set m() {
      return this.p;
   }

   public Set i() {
      return this.h;
   }

   public Set l() {
      return this.o;
   }

   public boolean j() {
      return this.q;
   }

   public void c(boolean var1) {
      this.q = var1;
   }

   public boolean f() {
      return this.r;
   }

   public void a(boolean var1) {
      this.r = var1;
   }

   public Position d() {
      return this.g;
   }

   public void a(Position var1) {
      this.g = var1;
   }

   public boolean k() {
      return this.n;
   }

   public GameMode h() {
      return this.m;
   }

   public void a(GameMode var1) {
      this.m = var1;
   }

   public String n() {
      return this.l;
   }

   public void a(String var1) {
      this.l = var1;
   }

   public static void b(String var0) {
      k = var0;
   }

   public static String c() {
      return k;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b("iH67I");
   }
}
