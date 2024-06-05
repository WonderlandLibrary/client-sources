package com.viaversion.viaversion.protocol;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_16;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.Protocol1_12_1To1_12;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import net.aR0;
import net.aR1;
import net.aR2;
import net.aR3;
import net.aR4;
import net.aR6;
import net.aRF;
import net.aRG;
import net.aRI;
import net.aRL;
import net.aRN;
import net.aRP;
import net.aRR;
import net.aRU;
import net.aRY;
import net.aRb;
import net.aRe;
import net.aRh;
import net.aRm;
import net.aRp;
import net.aRq;
import net.aRr;
import net.aRs;
import net.aRu;
import net.aRy;
import net.ayx;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ProtocolManagerImpl {
   public static final ayx k = new aRm();
   public static int a = -1;
   public static int c = 50;
   private static final Int2ObjectMap b = new Int2ObjectOpenHashMap(32);
   private static final Map f = new HashMap();
   private static final Map i = new ConcurrentHashMap();
   private static final Set g = new HashSet();
   private static final List j = Lists.newCopyOnWriteArrayList();
   private static final List l = new ArrayList();
   private static final Object e = new Object();
   private static Map d = new HashMap();
   private static ThreadPoolExecutor m;
   private static boolean h;

   public static void c() {
   }

   public static void a(ayx var0, ProtocolVersion var1, ProtocolVersion var2) {
      a(var0, Collections.singletonList(Integer.valueOf(var1.d())), var2.d());
   }

   public static void a(ayx var0, List var1, int var2) {
      if(!i.isEmpty()) {
         i.clear();
      }

      f.put(var0.getClass(), var0);
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         int var4 = ((Integer)var3.next()).intValue();
         Int2ObjectMap var5 = (Int2ObjectMap)b.computeIfAbsent(var4, ProtocolManagerImpl::lambda$registerProtocol$0);
         var5.put(var2, var0);
      }

      if(Via.d().h()) {
         var0.a(Via.b().f());
         refreshVersions();
      } else {
         l.add(var0);
      }

      if(var0.hasMappingDataToLoad()) {
         if(m != null) {
            a(var0.getClass(), var0::a);
         } else {
            var0.a();
         }
      }

   }

   public static void a(ayx var0, Range var1) {
      j.add(new Pair(var1, var0));
      if(Via.d().h()) {
         var0.a(Via.b().f());
         refreshVersions();
      } else {
         l.add(var0);
      }

   }

   public static void refreshVersions() {
      g.clear();
      g.add(Integer.valueOf(a));

      for(ProtocolVersion var1 : ProtocolVersion.i()) {
         List var2 = a(var1.d(), a);
      }

   }

   public static SortedSet e() {
      return Collections.unmodifiableSortedSet(new TreeSet(g));
   }

   public static boolean b() {
      ayx.h();
      ObjectIterator var1 = b.values().iterator();
      if(var1.hasNext()) {
         Int2ObjectMap var2 = (Int2ObjectMap)var1.next();
         if(var2.containsKey(a)) {
            return true;
         }
      }

      return false;
   }

   public static void onServerLoaded() {
      ayx.h();
      Iterator var1 = l.iterator();
      if(var1.hasNext()) {
         ayx var2 = (ayx)var1.next();
         var2.a(Via.b().f());
      }

      l.clear();
   }

   @Nullable
   private static List a(List var0, int var1, int var2) {
      if(var1 == var2) {
         return null;
      } else if(var0.size() > c) {
         return null;
      } else {
         Int2ObjectMap var3 = (Int2ObjectMap)b.get(var1);
         return null;
      }
   }

   @Nullable
   public static List a(int var0, int var1) {
      Pair var2 = new Pair(Integer.valueOf(var0), Integer.valueOf(var1));
      List var3 = (List)i.get(var2);
      return var3;
   }

   @Nullable
   public static ayx b(Class var0) {
      return (ayx)f.get(var0);
   }

   public static ayx a(int var0) {
      ayx.h();
      Iterator var2 = Lists.reverse(j).iterator();
      if(var2.hasNext()) {
         Pair var3 = (Pair)var2.next();
         if(((Range)var3.getKey()).contains(Integer.valueOf(var0))) {
            return (ayx)var3.getValue();
         }
      }

      throw new IllegalStateException("No Base Protocol for " + var0);
   }

   public static boolean a(ayx var0) {
      ayx.h();
      Iterator var2 = j.iterator();
      if(var2.hasNext()) {
         Pair var3 = (Pair)var2.next();
         if(var3.getValue() == var0) {
            return true;
         }
      }

      return false;
   }

   public static void c(Class var0) throws Exception {
      PacketRemapper[] var1 = ayx.h();
      if(!h) {
         CompletableFuture var2 = a(var0);
         if(var2 != null) {
            var2.get();
         }
      }
   }

   public static boolean a() {
      // $FF: Couldn't be decompiled
   }

   private static void shutdownLoaderExecutor() {
      Via.d().getLogger().info("Finished mapping loading, shutting down loader executor!");
      h = true;
      m.shutdown();
      ayx.h();
      m = null;
      d.clear();
      d = null;
      if(MappingDataLoader.isCacheJsonMappings()) {
         MappingDataLoader.getMappingsCache().clear();
      }

   }

   public static void a(Class param0, Runnable param1) {
      // $FF: Couldn't be decompiled
   }

   public static void a(Class param0, Class param1, Runnable param2) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public static CompletableFuture a(Class param0) {
      // $FF: Couldn't be decompiled
   }

   private static Void lambda$addMappingLoaderFuture$3(Class var0, Throwable var1) {
      Via.d().getLogger().severe("Error during mapping loading of " + var0.getCanonicalName());
      var1.printStackTrace();
      return null;
   }

   private static void lambda$addMappingLoaderFuture$1(Runnable var0, Void var1, Throwable var2) {
      var0.run();
   }

   private static Void lambda$addMappingLoaderFuture$1(Class var0, Throwable var1) {
      Via.d().getLogger().severe("Error during mapping loading of " + var0.getCanonicalName());
      var1.printStackTrace();
      return null;
   }

   private static Int2ObjectMap lambda$registerProtocol$0(int var0) {
      return new Int2ObjectOpenHashMap(2);
   }

   static {
      ThreadFactory var7 = (new ThreadFactoryBuilder()).setNameFormat("Via-Mappingloader-%d").build();
      m = new ThreadPoolExecutor(5, 16, 45L, TimeUnit.SECONDS, new SynchronousQueue(), var7);
      m.allowCoreThreadTimeOut(true);
      a(k, Range.lessThan(Integer.valueOf(Integer.MIN_VALUE)));
      a((ayx)(new aRu()), (Range)Range.lessThan(Integer.valueOf(ProtocolVersion.v1_16.d())));
      a((ayx)(new BaseProtocol1_16()), (Range)Range.atLeast(Integer.valueOf(ProtocolVersion.v1_16.d())));
      a((ayx)(new aRY()), (ProtocolVersion)ProtocolVersion.v1_9, (ProtocolVersion)ProtocolVersion.v1_8);
      a(new aRy(), Arrays.asList(new Integer[]{Integer.valueOf(ProtocolVersion.v1_9_1.d()), Integer.valueOf(ProtocolVersion.v1_9_2.d())}), ProtocolVersion.v1_9.d());
      a((ayx)(new aRR()), (ProtocolVersion)ProtocolVersion.v1_9_3, (ProtocolVersion)ProtocolVersion.v1_9_2);
      a((ayx)(new aR2()), (ProtocolVersion)ProtocolVersion.v1_9, (ProtocolVersion)ProtocolVersion.v1_9_2);
      a(new aR0(), Arrays.asList(new Integer[]{Integer.valueOf(ProtocolVersion.v1_9_1.d()), Integer.valueOf(ProtocolVersion.v1_9_2.d())}), ProtocolVersion.v1_9_3.d());
      a((ayx)(new aRF()), (ProtocolVersion)ProtocolVersion.v1_10, (ProtocolVersion)ProtocolVersion.v1_9_3);
      a((ayx)(new aRs()), (ProtocolVersion)ProtocolVersion.v1_11, (ProtocolVersion)ProtocolVersion.v1_10);
      a((ayx)(new aR3()), (ProtocolVersion)ProtocolVersion.v1_11_1, (ProtocolVersion)ProtocolVersion.v1_11);
      a((ayx)(new aRp()), (ProtocolVersion)ProtocolVersion.v1_12, (ProtocolVersion)ProtocolVersion.v1_11_1);
      a((ayx)(new Protocol1_12_1To1_12()), (ProtocolVersion)ProtocolVersion.v1_12_1, (ProtocolVersion)ProtocolVersion.v1_12);
      a((ayx)(new aRr()), (ProtocolVersion)ProtocolVersion.v1_12_2, (ProtocolVersion)ProtocolVersion.v1_12_1);
      a((ayx)(new aRP()), (ProtocolVersion)ProtocolVersion.v1_13, (ProtocolVersion)ProtocolVersion.v1_12_2);
      a((ayx)(new Protocol1_13_1To1_13()), (ProtocolVersion)ProtocolVersion.v1_13_1, (ProtocolVersion)ProtocolVersion.v1_13);
      a((ayx)(new aRL()), (ProtocolVersion)ProtocolVersion.v1_13_2, (ProtocolVersion)ProtocolVersion.v1_13_1);
      a((ayx)(new aR4()), (ProtocolVersion)ProtocolVersion.v1_14, (ProtocolVersion)ProtocolVersion.v1_13_2);
      a((ayx)(new aRb()), (ProtocolVersion)ProtocolVersion.v1_14_1, (ProtocolVersion)ProtocolVersion.v1_14);
      a((ayx)(new aRN()), (ProtocolVersion)ProtocolVersion.v1_14_2, (ProtocolVersion)ProtocolVersion.v1_14_1);
      a((ayx)(new aRU()), (ProtocolVersion)ProtocolVersion.v1_14_3, (ProtocolVersion)ProtocolVersion.v1_14_2);
      a((ayx)(new aR1()), (ProtocolVersion)ProtocolVersion.v1_14_4, (ProtocolVersion)ProtocolVersion.v1_14_3);
      a((ayx)(new aR6()), (ProtocolVersion)ProtocolVersion.v1_15, (ProtocolVersion)ProtocolVersion.v1_14_4);
      a((ayx)(new aRq()), (ProtocolVersion)ProtocolVersion.v1_15_1, (ProtocolVersion)ProtocolVersion.v1_15);
      a((ayx)(new aRI()), (ProtocolVersion)ProtocolVersion.v1_15_2, (ProtocolVersion)ProtocolVersion.v1_15_1);
      a((ayx)(new Protocol1_16To1_15_2()), (ProtocolVersion)ProtocolVersion.v1_16, (ProtocolVersion)ProtocolVersion.v1_15_2);
      a((ayx)(new aRe()), (ProtocolVersion)ProtocolVersion.v1_16_1, (ProtocolVersion)ProtocolVersion.v1_16);
      a((ayx)(new Protocol1_16_2To1_16_1()), (ProtocolVersion)ProtocolVersion.v1_16_2, (ProtocolVersion)ProtocolVersion.v1_16_1);
      a((ayx)(new aRG()), (ProtocolVersion)ProtocolVersion.v1_16_3, (ProtocolVersion)ProtocolVersion.v1_16_2);
      a((ayx)(new aRh()), (ProtocolVersion)ProtocolVersion.u, (ProtocolVersion)ProtocolVersion.v1_16_3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
