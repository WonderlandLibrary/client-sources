package net.optifine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import net.optifine.HttpPipelineConnection;
import net.optifine.HttpPipelineRequest;
import net.optifine.HttpRequest;
import net.optifine.MatchBlock;

public class HttpPipelineSender extends Thread {
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final String a = "\r\n";
   private static Charset ASCII = Charset.forName("ASCII");

   public HttpPipelineSender(HttpPipelineConnection var1) {
      super("HttpPipelineSender");
      this.httpPipelineConnection = var1;
   }

   public void run() {
      MatchBlock.b();
      HttpPipelineRequest var2 = null;

      try {
         this.connect();
         if(!Thread.interrupted()) {
            var2 = this.httpPipelineConnection.getNextRequestSend();
            HttpRequest var3 = var2.getHttpRequest();
            OutputStream var4 = this.httpPipelineConnection.getOutputStream();
            this.writeRequest(var3, var4);
            this.httpPipelineConnection.onRequestSent(var2);
         }
      } catch (InterruptedException var5) {
         return;
      } catch (Exception var6) {
         this.httpPipelineConnection.onExceptionSend(var2, var6);
      }

   }

   private void connect() throws IOException {
      String var1 = this.httpPipelineConnection.getHost();
      int var2 = this.httpPipelineConnection.getPort();
      Proxy var3 = this.httpPipelineConnection.getProxy();
      Socket var4 = new Socket(var3);
      var4.connect(new InetSocketAddress(var1, var2), 5000);
      this.httpPipelineConnection.setSocket(var4);
   }

   private void writeRequest(HttpRequest var1, OutputStream var2) throws IOException {
      this.write(var2, var1.getMethod() + " " + var1.getFile() + " " + var1.getHttp() + "\r\n");
      Map var4 = var1.getHeaders();
      MatchBlock.b();
      Iterator var5 = var4.keySet().iterator();
      if(var5.hasNext()) {
         String var6 = (String)var5.next();
         String var7 = (String)var1.getHeaders().get(var6);
         this.write(var2, var6 + ": " + var7 + "\r\n");
      }

      this.write(var2, "\r\n");
   }

   private void write(OutputStream var1, String var2) throws IOException {
      byte[] var3 = var2.getBytes(ASCII);
      var1.write(var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
