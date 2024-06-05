package net;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigOriginFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import net.Sq;
import net.a25;
import net.a6t;
import net.aPI;
import net.aSh;
import net.af_;
import net.agP;
import net.ah8;
import net.akH;
import net.awr;
import net.iE;
import org.jetbrains.annotations.NotNull;

public class awp extends awr {
   public static final Pattern n = Pattern.compile("\r?");
   private static final ConfigRenderOptions o = ConfigRenderOptions.defaults().setOriginComments(false).setJson(false);
   private static final ConfigOrigin k = ConfigOriginFactory.newSimple("configurate-hocon");
   private final ConfigRenderOptions l;
   private final ConfigParseOptions m;
   private static final Constructor j;
   private static final Constructor p;

   public static ConfigRenderOptions a() {
      return o;
   }

   public static ConfigParseOptions c() {
      return ConfigParseOptions.defaults();
   }

   @NotNull
   public static a6t b() {
      return new a6t();
   }

   private awp(a6t var1) {
      super(var1, new aPI[]{ah8.HASH, ah8.DOUBLE_SLASH});
      this.l = var1.f();
      this.m = var1.d();
   }

   public void a(iE var1, BufferedReader var2) throws IOException {
      Config var4 = ConfigFactory.parseReader(var2, this.m);
      int var10000 = a6t.e();
      var4 = var4.resolve();
      int var3 = var10000;
      Iterator var5 = var4.root().entrySet().iterator();
      if(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         a((ConfigValue)var6.getValue(), var1.a(new Object[]{var6.getKey()}));
      }

      if(PacketRemapper.b() == null) {
         ++var3;
         a6t.b(var3);
      }

   }

   private static void a(ConfigValue var0, iE var1) {
      int var2 = a6t.e();
      if(!var0.origin().comments().isEmpty()) {
         var1.a(n.matcher(Joiner.on('\n').join(var0.origin().comments())).replaceAll(""));
      }

      switch(a25.a[var0.valueType().ordinal()]) {
      case 1:
         if(((ConfigObject)var0).isEmpty()) {
            var1.a((Object)ImmutableMap.of());
         }

         Iterator var3 = ((ConfigObject)var0).entrySet().iterator();
         if(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            a((ConfigValue)var4.getValue(), var1.a(new Object[]{var4.getKey()}));
         }
      case 2:
         if(((ConfigList)var0).isEmpty()) {
            var1.a((Object)ImmutableList.of());
         }

         ConfigList var5 = (ConfigList)var0;
         int var6 = 0;
         if(var6 < var5.size()) {
            a((ConfigValue)var5.get(var6), var1.a(new Object[]{Integer.valueOf(var6)}));
            ++var6;
         }
      case 3:
         return;
      default:
         var1.a(var0.unwrapped());
      }
   }

   protected void a(akH var1, Writer var2) throws IOException {
      int var3 = a6t.e();
      if(!var1.f()) {
         if(var1.o() == null) {
            var2.write(c);
         } else {
            throw new IOException("HOCON cannot write nodes not in map format!");
         }
      } else {
         ConfigValue var4 = a(var1);
         String var5 = var4.render(this.l);
         var2.write(var5);
      }
   }

   private static ConfigValue a(akH var0) {
      int var1 = a6t.c();
      if(var0.f()) {
         ConcurrentMap var3 = var0.i().d().a();
         Iterator var4 = var0.s().entrySet().iterator();
         if(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            var3.put(String.valueOf(var5.getKey()), a((akH)var5.getValue()));
         }

         ConfigValue var2 = a((Map)var3);
      }

      if(var0.p()) {
         ArrayList var8 = new ArrayList();
         Iterator var10 = var0.b().iterator();
         if(var10.hasNext()) {
            akH var11 = (akH)var10.next();
            var8.add(a(var11));
         }

         ConfigValue var6 = a((List)var8);
      }

      ConfigValue var7 = ConfigValueFactory.fromAnyRef(var0.o(), "configurate-hocon");
      if(var0 instanceof iE) {
         iE var9 = (iE)var0;
         var7 = (ConfigValue)var9.d().map(awp::lambda$fromValue$0).orElse(var7);
      }

      return var7;
   }

   static ConfigValue a(Map var0) {
      try {
         return (ConfigValue)j.newInstance(new Object[]{k, var0});
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var2) {
         throw new RuntimeException(var2);
      }
   }

   static ConfigValue a(List var0) {
      try {
         return (ConfigValue)p.newInstance(new Object[]{k, var0});
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var2) {
         throw new RuntimeException(var2);
      }
   }

   @NotNull
   public iE a(@NotNull agP var1) {
      a6t.e();
      var1 = var1.a((Set)aSh.a(Map.class, List.class, Double.class, Long.class, Integer.class, Boolean.class, new Class[]{String.class, Number.class}));
      return Sq.a(var1);
   }

   private static ConfigValue lambda$fromValue$0(ConfigValue var0, String var1) {
      return var0.withOrigin(var0.origin().withComments(d.splitToList(var1)));
   }

   awp(a6t var1, a25 var2) {
      this(var1);
   }

   static {
      String var10000 = "com.typesafe.config.impl.SimpleConfigObject";

      Class var7;
      Class var8;
      try {
         var7 = af_.a(var10000).asSubclass(ConfigValue.class);
         var8 = af_.a("com.typesafe.config.impl.SimpleConfigList").asSubclass(ConfigValue.class);
      } catch (ClassNotFoundException var11) {
         throw new ExceptionInInitializerError(var11);
      }

      try {
         j = var7.getDeclaredConstructor(new Class[]{ConfigOrigin.class, Map.class});
         j.setAccessible(true);
         p = var8.getDeclaredConstructor(new Class[]{ConfigOrigin.class, List.class});
         p.setAccessible(true);
      } catch (NoSuchMethodException var10) {
         throw new ExceptionInInitializerError(var10);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
