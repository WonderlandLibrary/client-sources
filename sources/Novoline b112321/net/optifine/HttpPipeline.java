package net.optifine;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.HttpListener;
import net.optifine.HttpPipelineConnection;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpRequest;
import net.optifine.HttpResponse;
import net.optifine.MatchBlock;

public class HttpPipeline {
   private static Map mapConnections = new HashMap();
   public static final String i = "User-Agent";
   public static final String c = "Host";
   public static final String f = "Accept";
   public static final String d = "Location";
   public static final String j = "Keep-Alive";
   public static final String h = "Connection";
   public static final String b = "keep-alive";
   public static final String e = "Transfer-Encoding";
   public static final String g = "chunked";

   public static void addRequest(String var0, HttpListener var1) throws IOException {
      addRequest(var0, var1, Proxy.NO_PROXY);
   }

   public static void addRequest(String var0, HttpListener var1, Proxy var2) throws IOException {
      HttpRequest var3 = makeRequest(var0, var2);
      HttpPipelineRequest var4 = new HttpPipelineRequest(var3, var1);
      addRequest(var4);
   }

   public static HttpRequest makeRequest(String var0, Proxy var1) throws IOException {
      MatchBlock.b();
      URL var3 = new URL(var0);
      if(!var3.getProtocol().equals("http")) {
         throw new IOException("Only protocol http is supported: " + var3);
      } else {
         String var4 = var3.getFile();
         String var5 = var3.getHost();
         int var6 = var3.getPort();
         if(var6 <= 0) {
            var6 = 80;
         }

         String var7 = "GET";
         String var8 = "HTTP/1.1";
         LinkedHashMap var9 = new LinkedHashMap();
         var9.put("User-Agent", "Java/" + System.getProperty("java.version"));
         var9.put("Host", var5);
         var9.put("Accept", "text/html, image/gif, image/png");
         var9.put("Connection", "keep-alive");
         byte[] var10 = new byte[0];
         HttpRequest var11 = new HttpRequest(var5, var6, var1, var7, var4, var8, var9, var10);
         return var11;
      }
   }

   public static void addRequest(HttpPipelineRequest var0) {
      HttpRequest var2 = var0.getHttpRequest();
      MatchBlock.b();
      HttpPipelineConnection var3 = getConnection(var2.getHost(), var2.getPort(), var2.getProxy());
      if(!var3.addRequest(var0)) {
         removeConnection(var2.getHost(), var2.getPort(), var2.getProxy(), var3);
         var3 = getConnection(var2.getHost(), var2.getPort(), var2.getProxy());
      }

   }

   private static synchronized HttpPipelineConnection getConnection(String var0, int var1, Proxy var2) {
      MatchBlock.b();
      String var4 = makeConnectionKey(var0, var1, var2);
      HttpPipelineConnection var5 = (HttpPipelineConnection)mapConnections.get(var4);
      if(var5 == null) {
         var5 = new HttpPipelineConnection(var0, var1, var2);
         mapConnections.put(var4, var5);
      }

      return var5;
   }

   private static synchronized void removeConnection(String var0, int var1, Proxy var2, HttpPipelineConnection var3) {
      MatchBlock.b();
      String var5 = makeConnectionKey(var0, var1, var2);
      HttpPipelineConnection var6 = (HttpPipelineConnection)mapConnections.get(var5);
      if(var6 == var3) {
         mapConnections.remove(var5);
      }

   }

   private static String makeConnectionKey(String var0, int var1, Proxy var2) {
      String var3 = var0 + ":" + var1 + "-" + var2;
      return var3;
   }

   public static byte[] get(String var0) throws IOException {
      return get(var0, Proxy.NO_PROXY);
   }

   public static byte[] get(String var0, Proxy var1) throws IOException {
      MatchBlock.b();
      HttpRequest var3 = makeRequest(var0, var1);
      HttpResponse var4 = executeRequest(var3);
      if(var4.getStatus() / 100 != 2) {
         throw new IOException("HTTP response: " + var4.getStatus());
      } else {
         return var4.getBody();
      }
   }

   public static HttpResponse executeRequest(HttpRequest param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static boolean hasActiveRequests() {
      MatchBlock.b();
      Iterator var1 = mapConnections.values().iterator();
      if(var1.hasNext()) {
         Object var2 = var1.next();
         if(((HttpPipelineConnection)var2).hasActiveRequests()) {
            return true;
         }
      }

      return false;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
