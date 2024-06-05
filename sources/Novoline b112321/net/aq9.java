package net;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;
import net.NO;
import net.Va;
import net.WS;
import net.af_;
import net.api;
import net.hZ;
import net.jo;
import net.lc;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import sun.misc.URLClassPath;
import viamcp.loader.VRProviderLoader;
import viamcp.utils.JLoggerToLog4j;

public class aq9 {
   public static int d = 47;
   public static final Logger a = new JLoggerToLog4j(LogManager.getLogger("ViaFabric"));
   public static final ExecutorService f;
   public static final EventLoop c;
   public static CompletableFuture b = new CompletableFuture();
   private static int e;

   public static String b() {
      return "1.0";
   }

   public void e() throws IllegalAccessException, NoSuchFieldException, MalformedURLException {
      d();
      this.a();
      Via.a(lc.j().a((hZ)(new Va())).a((WS)(new VRProviderLoader())).a((ViaPlatform)(new jo())).a());
      MappingDataLoader.enableMappingsCache();
      new api();
      new NO();
      Via.b().m();
      b.complete((Object)null);
   }

   public void a() throws NoSuchFieldException, IllegalAccessException, MalformedURLException {
      ClassLoader var2 = ClassLoader.getSystemClassLoader();
      int var10000 = d();
      Field var3 = af_.c(var2.getClass(), "ucp");
      var3.setAccessible(true);
      URLClassPath var4 = (URLClassPath)var3.get(var2);
      File[] var5 = (new File(Minecraft.getMinecraft().mcDataDir, "mods")).listFiles();
      int var1 = var10000;
      if(var5 != null) {
         int var7 = var5.length;
         int var8 = 0;
         if(var8 < var7) {
            File var9 = var5[var8];
            if(var9.isFile() && var9.getName().startsWith("Via") && var9.getName().toLowerCase().endsWith(".jar")) {
               var4.addURL(var9.toURI().toURL());
            }

            ++var8;
         }
      }

      if(PacketRemapper.b() == null) {
         ++var1;
         b(var1);
      }

   }

   static {
      b(97);
      ThreadFactory var7 = (new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("ViaFabric-%d").build();
      f = Executors.newFixedThreadPool(8, var7);
      c = (new LocalEventLoopGroup(1, var7)).next();
      EventLoop var10000 = c;
      CompletableFuture var10001 = b;
      b.getClass();
      var10000.submit(var10001::join);
   }

   public static void b(int var0) {
      e = var0;
   }

   public static int c() {
      return e;
   }

   public static int d() {
      int var0 = c();
      return 5;
   }

   private static ReflectiveOperationException a(ReflectiveOperationException var0) {
      return var0;
   }
}
