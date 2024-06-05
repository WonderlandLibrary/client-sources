package net;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.X9;
import net.ZV;
import net.aE1;
import net.aE4;
import net.aEN;
import net.aEU;
import net.agP;
import net.akH;
import net.am5;

public class S1 implements akH {
   private final agP e;
   volatile boolean b;
   volatile Object f;
   private S1 c;
   private volatile aEN d;

   public static S1 g() {
      return a(agP.e());
   }

   public static S1 a(agP var0) {
      return new S1((Object)null, (S1)null, var0);
   }

   protected S1(Object var1, S1 var2, agP var3) {
      am5.b();
      Objects.requireNonNull(var3, "options");
      this.f = var1;
      this.e = var3;
      this.c = var2;
      this.d = new aE4(this);
      if(var2 == null) {
         this.b = true;
      }

   }

   protected S1(S1 var1, S1 var2) {
      this.e = var2.e;
      this.b = true;
      this.f = var2.f;
      this.c = var1;
      this.d = var2.d.a(this);
   }

   private Object d(Object var1) {
      PacketRemapper[] var2 = am5.b();
      if(var1 != null && this.i().c()) {
         this.b(var1);
      }

      return var1;
   }

   private Object a(TypeToken var1, Object var2) throws X9 {
      PacketRemapper[] var3 = am5.b();
      if(var2 != null && this.i().c()) {
         this.a((TypeToken)var1, (Object)var2);
      }

      return var2;
   }

   public Object c(Object var1) {
      am5.b();
      Object var3 = this.d.a();
      return var3 == null?this.d(var1):var3;
   }

   public Object a(Supplier var1) {
      am5.b();
      Object var3 = this.d.a();
      return var3 == null?this.d(var1.get()):var3;
   }

   public Object a(Function var1, Object var2) {
      am5.b();
      Object var4 = var1.apply(this.o());
      return var4 == null?this.d(var2):var4;
   }

   public Object a(Function var1, Supplier var2) {
      am5.b();
      Object var4 = var1.apply(this.o());
      return var4 == null?this.d(var2.get()):var4;
   }

   public List a(Function var1) {
      am5.b();
      Builder var3 = ImmutableList.builder();
      aEN var4 = this.d;
      if(var4 instanceof aEU) {
         Iterator var5 = var4.c().iterator();
         if(var5.hasNext()) {
            S1 var6 = (S1)var5.next();
            Object var7 = var1.apply(var6.o());
            var3.add(var7);
         }
      }

      Object var8 = var1.apply(var4.a());
      var3.add(var8);
      return var3.build();
   }

   public List a(Function var1, List var2) {
      am5.b();
      List var4 = this.a(var1);
      return var4.isEmpty()?(List)this.d((Object)var2):var4;
   }

   public List b(Function var1, Supplier var2) {
      am5.b();
      List var4 = this.a(var1);
      return var4.isEmpty()?(List)this.d(var2.get()):var4;
   }

   public List a(TypeToken var1, List var2) throws X9 {
      am5.b();
      List var4 = (List)this.b((TypeToken)var1, (Object)var2);
      return var4.isEmpty()?(List)this.d((Object)var2):var4;
   }

   public List b(TypeToken var1, Supplier var2) throws X9 {
      am5.b();
      List var4 = (List)this.a(var1, var2);
      return var4.isEmpty()?(List)this.d(var2.get()):var4;
   }

   public Object b(TypeToken var1, Object var2) throws X9 {
      am5.b();
      Object var4 = this.o();
      if(var4 == null) {
         return this.a(var1, var2);
      } else {
         ZV var5 = this.i().f().a((TypeToken)var1);
         return var5 == null?(var1.getRawType().isInstance(var4)?var1.getRawType().cast(var4):this.a(var1, var2)):var5.a(var1, this);
      }
   }

   public Object a(TypeToken var1, Supplier var2) throws X9 {
      am5.b();
      Object var4 = this.o();
      if(var4 == null) {
         return this.a(var1, var2.get());
      } else {
         ZV var5 = this.i().f().a((TypeToken)var1);
         return var5 == null?(var1.getRawType().isInstance(var4)?var1.getRawType().cast(var4):this.a(var1, var2.get())):var5.a(var1, this);
      }
   }

   public S1 b(Object param1) {
      // $FF: Couldn't be decompiled
   }

   private void a(Object param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public akH a(akH param1) {
      // $FF: Couldn't be decompiled
   }

   public S1 a(Object... var1) {
      am5.b();
      S1 var3 = this;
      int var5 = var1.length;
      int var6 = 0;
      if(var6 < var5) {
         Object var7 = var1[var6];
         var3 = this.b(var7, false);
         ++var6;
      }

      return var3;
   }

   public boolean e() {
      PacketRemapper[] var1 = am5.b();
      return !this.b;
   }

   public am5 a() {
      return this.d.d();
   }

   public List b() {
      am5.b();
      aEN var2 = this.d;
      return (List)(var2 instanceof aEU?ImmutableList.copyOf((Collection)((aEU)var2).b.get()):Collections.emptyList());
   }

   public Map s() {
      am5.b();
      aEN var2 = this.d;
      return (Map)(var2 instanceof aE1?ImmutableMap.copyOf(((aE1)var2).b):Collections.emptyMap());
   }

   protected S1 b(Object var1, boolean var2) {
      am5.b();
      S1 var4 = this.d.b(var1);
      if(var4 == null) {
         this.b();
         S1 var5 = this.d.b(var1, var4 = this.a(var1));
         if(var5 != null) {
            var4 = var5;
         }

         this.d(var4);
         var4 = this.a(var1);
      }

      return var4;
   }

   public boolean b(Object var1) {
      return c(this.d.a(var1, (S1)null)) != null;
   }

   private static S1 c(S1 var0) {
      PacketRemapper[] var1 = am5.b();
      if(var0 != null) {
         var0.b = false;
         var0.f();
      }

      return var0;
   }

   public S1 a() {
      return this.b(Integer.valueOf(-1), false);
   }

   public Object n() {
      return this.f;
   }

   public Object[] g() {
      am5.b();
      LinkedList var2 = new LinkedList();
      Object var3 = this;
      if(this.m() == null) {
         return new Object[]{this.n()};
      } else {
         while(true) {
            var2.addFirst(((akH)var3).n());
            if(((akH)(var3 = ((akH)var3).m())).m() == null) {
               break;
            }
         }

         return var2.toArray();
      }
   }

   public S1 c() {
      return this.c;
   }

   public agP i() {
      return this.e;
   }

   public S1 e() {
      return this.a((S1)null);
   }

   protected S1 a(S1 var1) {
      return new S1(var1, this);
   }

   S1 d() {
      am5.b();
      S1 var2 = this.c;
      if(var2.e()) {
         var2 = var2.d().b(var2);
      }

      return this.c = var2;
   }

   protected void b() {
      PacketRemapper[] var1 = am5.b();
      if(!this.b) {
         this.d().d(this);
      }

   }

   protected S1 a(Object var1) {
      return new S1(var1, this, this.e);
   }

   protected S1 b(S1 var1) {
      return this.a(var1, true);
   }

   private void d(S1 var1) {
      this.a(var1, false);
   }

   private S1 a(S1 param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   protected void f() {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = am5.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof S1)) {
         return false;
      } else {
         S1 var3 = (S1)var1;
         return Objects.equals(this.f, var3.f) && Objects.equals(this.d, var3.d);
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.f) ^ Objects.hashCode(this.d);
   }

   public String toString() {
      return "SimpleConfigurationNode{key=" + this.f + ", value=" + this.d + '}';
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
