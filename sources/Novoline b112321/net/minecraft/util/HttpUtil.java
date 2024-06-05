package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HttpUtil {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);

   public static String buildPostString(Map var0) {
      StringBuilder var1 = new StringBuilder();

      for(Entry var3 : var0.entrySet()) {
         if(var1.length() > 0) {
            var1.append('&');
         }

         try {
            var1.append(URLEncoder.encode((String)var3.getKey(), "UTF-8"));
         } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
         }

         if(var3.getValue() != null) {
            var1.append('=');
            StringBuilder var10000 = var1;
            Entry var10001 = var3;

            try {
               var10000.append(URLEncoder.encode(var10001.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException var5) {
               var5.printStackTrace();
            }
         }
      }

      return var1.toString();
   }

   public static String postMap(URL var0, Map var1, boolean var2) {
      return post(var0, buildPostString(var1), var2);
   }

   private static String post(URL param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public static ListenableFuture downloadResourcePack(File var0, String var1, Map var2, int var3, IProgressUpdate var4, Proxy var5) {
      ListenableFuture var6 = field_180193_a.submit(HttpUtil::lambda$downloadResourcePack$0);
      return var6;
   }

   public static int getSuitableLanPort() throws IOException {
      ServerSocket var0 = null;

      int var1;
      try {
         var0 = new ServerSocket(0);
         var1 = var0.getLocalPort();
      } finally {
         ServerSocket var10000 = var0;

         try {
            var10000.close();
         } catch (IOException var8) {
            ;
         }

      }

      return var1;
   }

   public static String get(URL var0) throws IOException {
      HttpURLConnection var1 = (HttpURLConnection)var0.openConnection();
      var1.setRequestMethod("GET");
      BufferedReader var2 = new BufferedReader(new InputStreamReader(var1.getInputStream()));
      StringBuilder var3 = new StringBuilder();

      String var4;
      while((var4 = var2.readLine()) != null) {
         var3.append(var4);
         var3.append('\r');
      }

      var2.close();
      return var3.toString();
   }

   private static void lambda$downloadResourcePack$0(IProgressUpdate param0, String param1, Proxy param2, Map param3, File param4, int param5) {
      // $FF: Couldn't be decompiled
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
