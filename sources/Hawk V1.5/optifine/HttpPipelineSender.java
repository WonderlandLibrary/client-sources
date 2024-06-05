package optifine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpPipelineSender extends Thread {
   private static final String CRLF = "\r\n";
   private HttpPipelineConnection httpPipelineConnection = null;
   private static Charset ASCII = Charset.forName("ASCII");

   private void writeRequest(HttpRequest var1, OutputStream var2) throws IOException {
      this.write(var2, String.valueOf((new StringBuilder(String.valueOf(var1.getMethod()))).append(" ").append(var1.getFile()).append(" ").append(var1.getHttp()).append("\r\n")));
      Map var3 = var1.getHeaders();
      Set var4 = var3.keySet();
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         String var7 = (String)var1.getHeaders().get(var6);
         this.write(var2, String.valueOf((new StringBuilder(String.valueOf(var6))).append(": ").append(var7).append("\r\n")));
      }

      this.write(var2, "\r\n");
   }

   private void write(OutputStream var1, String var2) throws IOException {
      byte[] var3 = var2.getBytes(ASCII);
      var1.write(var3);
   }

   public HttpPipelineSender(HttpPipelineConnection var1) {
      super("HttpPipelineSender");
      this.httpPipelineConnection = var1;
   }

   private void connect() throws IOException {
      String var1 = this.httpPipelineConnection.getHost();
      int var2 = this.httpPipelineConnection.getPort();
      Proxy var3 = this.httpPipelineConnection.getProxy();
      Socket var4 = new Socket(var3);
      var4.connect(new InetSocketAddress(var1, var2), 5000);
      this.httpPipelineConnection.setSocket(var4);
   }

   public void run() {
      HttpPipelineRequest var1 = null;

      try {
         this.connect();

         while(!Thread.interrupted()) {
            var1 = this.httpPipelineConnection.getNextRequestSend();
            HttpRequest var2 = var1.getHttpRequest();
            OutputStream var3 = this.httpPipelineConnection.getOutputStream();
            this.writeRequest(var2, var3);
            this.httpPipelineConnection.onRequestSent(var1);
         }
      } catch (InterruptedException var4) {
         return;
      } catch (Exception var5) {
         this.httpPipelineConnection.onExceptionSend(var1, var5);
      }

   }
}
