package net;

import net.optifine.HttpListener;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpRequest;

public class Iy {
   public static void a(HttpPipelineRequest var0, boolean var1) {
      var0.setClosed(var1);
   }

   public static HttpRequest b(HttpPipelineRequest var0) {
      return var0.getHttpRequest();
   }

   public static HttpListener a(HttpPipelineRequest var0) {
      return var0.getHttpListener();
   }
}
