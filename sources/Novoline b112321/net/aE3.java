package net;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.aE8;
import net.aEH;
import net.aaW;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class aE3 extends aEH {
   protected List a;

   public aE3(@NotNull List var1) {
      super(var1);
   }

   public aE3(@NotNull Collection var1) {
      this((List)(new ObjectArrayList(var1)));
   }

   @SafeVarargs
   public aE3(@NotNull Object... var1) {
      this((List)(new ObjectArrayList(var1)));
   }

   public aE3(@Nullable Object var1) {
      this((List)(new ObjectArrayList(Collections.singletonList(var1))));
   }

   public aE3(@Nullable Object var1, @Nullable Object var2) {
      aE8.a();
      this((List)(new ObjectArrayList(Arrays.asList(new Object[]{var1, var2}))));
      if(PacketRemapper.b() == null) {
         aE8.b(new int[1]);
      }

   }

   public aE3(@Nullable Object var1, @Nullable Object var2, @Nullable Object var3) {
      aE8.a();
      this((List)(new ObjectArrayList(Arrays.asList(new Object[]{var1, var2, var3}))));
   }

   public aE3() {
      this((List)(new ObjectArrayList()));
   }

   @NotNull
   public static aE3 a(@NotNull List var0) {
      return new aE3(var0);
   }

   @NotNull
   public static aE3 a(@NotNull Collection var0) {
      return new aE3(new ObjectArrayList(var0));
   }

   @SafeVarargs
   @NotNull
   public static aE3 b(@NotNull Object... var0) {
      return new aE3(var0);
   }

   @NotNull
   public static aE3 c(@Nullable Object var0) {
      return new aE3(var0);
   }

   @NotNull
   public static aE3 a(@Nullable Object var0, @Nullable Object var1) {
      return new aE3(var0, var1);
   }

   @NotNull
   public static aE3 a(@Nullable Object var0, @Nullable Object var1, @Nullable Object var2) {
      return new aE3(var0, var1, var2);
   }

   @NotNull
   public static aE3 d() {
      return new aE3();
   }

   public void b(@Nullable List var1) {
      int[] var2 = aE8.a();
      if(this.c(var1)) {
         super.a(var1);
      }

      throw new aaW("Unable to set " + var1 + " as it contains unacceptable value(s): " + Collections2.filter(var1, this::lambda$set$0), this);
   }

   private boolean c(@NotNull List var1) {
      int[] var2 = aE8.a();
      if(this.a != null && !var1.isEmpty() && !this.a.isEmpty()) {
         Iterator var3 = var1.iterator();
         if(var3.hasNext()) {
            Object var4 = var3.next();
            if(!this.a.contains(var4)) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public aE3 b(@NotNull Collection var1) {
      this.a = new ObjectArrayList(var1);
      return this;
   }

   @SafeVarargs
   public final aE3 a(@NotNull Object... var1) {
      this.a = new ObjectArrayList(var1);
      return this;
   }

   public Object a(int var1) {
      int[] var2 = aE8.a();
      return this.c != null?((List)this.c).get(var1):null;
   }

   public boolean a(Object var1) {
      int[] var2 = aE8.a();
      return this.c != null && ((List)this.c).contains(var1);
   }

   public int c() {
      int[] var1 = aE8.a();
      return this.c != null?((List)this.c).size():0;
   }

   public boolean e() {
      int[] var1 = aE8.a();
      return this.c == null || ((List)this.c).isEmpty();
   }

   public boolean d(@Nullable Object var1) {
      int[] var2 = aE8.a();
      if(this.a != null && !this.a.contains(var1)) {
         return false;
      } else {
         if(this.c == null) {
            this.b((List)(new ObjectArrayList()));
         }

         return ((List)this.c).add(var1);
      }
   }

   public boolean b(@Nullable Object var1) {
      int[] var2 = aE8.a();
      return this.c != null && ((List)this.c).remove(var1);
   }

   public List f() {
      return this.a;
   }

   private boolean lambda$set$0(Object var1) {
      int[] var2 = aE8.a();
      return !this.a.contains(var1);
   }

   private static aaW a(aaW var0) {
      return var0;
   }
}
