package net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.acE;
import org.jetbrains.annotations.Nullable;
import viaversion.viabackwards.api.entities.storage.EntityTracker$StoredEntity;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.metadata.Metadata;

public class yb {
   private final UserConnection b;
   private final EntityTracker$StoredEntity d;
   private final int e;
   private final Metadata a;
   private final MetaStorage g;
   private List f;
   private static String c;

   public yb(UserConnection var1, EntityTracker$StoredEntity var2, int var3, Metadata var4, MetaStorage var5) {
      this.b = var1;
      this.d = var2;
      g();
      this.e = var3;
      this.a = var4;
      this.g = var5;
      if(acE.b() == null) {
         b("XW6m3b");
      }

   }

   public boolean b() {
      return this.a != null;
   }

   public Metadata a(int var1) {
      g();
      Iterator var3 = this.g.getMetaDataList().iterator();
      if(var3.hasNext()) {
         Metadata var4 = (Metadata)var3.next();
         if(var1 == var4.getId()) {
            return var4;
         }
      }

      return null;
   }

   public void d() {
      this.f = null;
   }

   public void a(Metadata var1) {
      String var2 = g();
      (this.f != null?this.f:(this.f = new ArrayList())).add(var1);
   }

   public UserConnection f() {
      return this.b;
   }

   public EntityTracker$StoredEntity h() {
      return this.d;
   }

   public int a() {
      return this.e;
   }

   public Metadata i() {
      return this.a;
   }

   public MetaStorage c() {
      return this.g;
   }

   @Nullable
   public List e() {
      return this.f;
   }

   public static void b(String var0) {
      c = var0;
   }

   public static String g() {
      return c;
   }

   static {
      if(g() != null) {
         b("HSh");
      }

   }
}
