package net;

import java.net.Proxy;
import net.optifine.HttpPipeline;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpRequest;
import net.optifine.HttpResponse;

public class a6p {
   public static HttpRequest b(String var0, Proxy var1) {
      return HttpPipeline.makeRequest(var0, var1);
   }

   public static void a(HttpPipelineRequest var0) {
      HttpPipeline.addRequest(var0);
   }

   public static byte[] a(String var0, Proxy var1) {
      return HttpPipeline.get(var0, var1);
   }

   public static HttpResponse a(HttpRequest var0) {
      return HttpPipeline.executeRequest(var0);
   }
}
