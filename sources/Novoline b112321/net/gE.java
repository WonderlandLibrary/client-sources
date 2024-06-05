package net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import net.optifine.HttpPipelineConnection;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpResponse;

public class gE {
   public static HttpPipelineRequest e(HttpPipelineConnection var0) {
      return var0.getNextRequestSend();
   }

   public static OutputStream d(HttpPipelineConnection var0) {
      return var0.getOutputStream();
   }

   public static void b(HttpPipelineConnection var0, HttpPipelineRequest var1) {
      var0.onRequestSent(var1);
   }

   public static void b(HttpPipelineConnection var0, HttpPipelineRequest var1, Exception var2) {
      var0.onExceptionSend(var1, var2);
   }

   public static String g(HttpPipelineConnection var0) {
      return var0.getHost();
   }

   public static int f(HttpPipelineConnection var0) {
      return var0.getPort();
   }

   public static Proxy c(HttpPipelineConnection var0) {
      return var0.getProxy();
   }

   public static void a(HttpPipelineConnection var0, Socket var1) {
      var0.setSocket(var1);
   }

   public static boolean a(HttpPipelineConnection var0, HttpPipelineRequest var1) {
      return var0.addRequest(var1);
   }

   public static HttpPipelineRequest b(HttpPipelineConnection var0) {
      return var0.getNextRequestReceive();
   }

   public static InputStream a(HttpPipelineConnection var0) {
      return var0.getInputStream();
   }

   public static void a(HttpPipelineConnection var0, HttpPipelineRequest var1, HttpResponse var2) {
      var0.onResponseReceived(var1, var2);
   }

   public static void a(HttpPipelineConnection var0, HttpPipelineRequest var1, Exception var2) {
      var0.onExceptionReceive(var1, var2);
   }
}
