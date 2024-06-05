package net;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.T5;
import net.X9;
import net.agP;
import net.am5;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface akH {
   int a = 0;
   String j = "No serializer available for type ";

   @Nullable
   Object n();

   @NotNull
   Object[] g();

   @Nullable
   akH m();

   @NotNull
   akH a(@NotNull Object... var1);

   boolean e();

   @NotNull
   agP i();

   @NotNull
   am5 a();

   default boolean p() {
      return this.a() == am5.LIST;
   }

   default boolean f() {
      return this.a() == am5.MAP;
   }

   @NotNull
   List b();

   @NotNull
   Map s();

   @Nullable
   default Object o() {
      return this.c((Object)null);
   }

   @NotNull
   akH a(@Nullable Object var1);

   Object c(@Nullable Object var1);

   Object a(@NotNull Supplier var1);

   @Nullable
   default Object b(@NotNull Function var1) {
      return this.a((Function)var1, (Object)null);
   }

   Object a(@NotNull Function var1, @Nullable Object var2);

   Object a(@NotNull Function var1, @NotNull Supplier var2);

   @NotNull
   List a(@NotNull Function var1);

   List a(@NotNull Function var1, @Nullable List var2);

   List b(@NotNull Function var1, @NotNull Supplier var2);

   @NotNull
   default List a(@NotNull TypeToken var1) throws X9 {
      return this.a((TypeToken)var1, (List)ImmutableList.of());
   }

   List a(@NotNull TypeToken var1, @Nullable List var2) throws X9;

   List b(@NotNull TypeToken var1, @NotNull Supplier var2) throws X9;

   @Nullable
   default String d() {
      return this.a((String)null);
   }

   default String a(@Nullable String var1) {
      return (String)this.a((Function)(T5::e), (Object)var1);
   }

   default float j() {
      return this.a(0.0F);
   }

   default float a(float var1) {
      return ((Float)this.a((Function)(T5::b), (Object)Float.valueOf(var1))).floatValue();
   }

   default double l() {
      return this.a(0.0D);
   }

   default double a(double var1) {
      return ((Double)this.a((Function)(T5::h), (Object)Double.valueOf(var1))).doubleValue();
   }

   default int c() {
      return this.a(0);
   }

   default int a(int var1) {
      return ((Integer)this.a((Function)(T5::d), (Object)Integer.valueOf(var1))).intValue();
   }

   default long h() {
      return this.a(0L);
   }

   default long a(long var1) {
      return ((Long)this.a((Function)(T5::c), (Object)Long.valueOf(var1))).longValue();
   }

   default boolean q() {
      return this.a(false);
   }

   default boolean a(boolean var1) {
      return ((Boolean)this.a((Function)(T5::a), (Object)Boolean.valueOf(var1))).booleanValue();
   }

   @Nullable
   default Object b(@NotNull TypeToken var1) throws X9 {
      return this.b((TypeToken)var1, (Object)null);
   }

   Object b(@NotNull TypeToken var1, Object var2) throws X9;

   Object a(@NotNull TypeToken var1, @NotNull Supplier var2) throws X9;

   @NotNull
   default akH a(@NotNull TypeToken var1, @Nullable Object var2) throws X9 {
      PacketRemapper[] var3 = am5.b();
      this.a((Object)null);
      return this;
   }

   @NotNull
   akH a(@NotNull akH var1);

   boolean b(@NotNull Object var1);

   @NotNull
   akH k();

   @NotNull
   akH r();

   private static default X9 a(X9 var0) {
      return var0;
   }
}
