package net.optifine.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class HttpPipelineSender extends Thread {
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final String CRLF = "\r\n";
   private static Charset ASCII;

   public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
      super("HttpPipelineSender");
      this.httpPipelineConnection = httpPipelineConnection;
   }

   public void run() {
      HttpPipelineRequest httppipelinerequest = null;

      try {
         this.connect();

         while(!Thread.interrupted()) {
            httppipelinerequest = this.httpPipelineConnection.getNextRequestSend();
            HttpRequest httprequest = httppipelinerequest.getHttpRequest();
            OutputStream outputstream = this.httpPipelineConnection.getOutputStream();
            this.writeRequest(httprequest, outputstream);
            this.httpPipelineConnection.onRequestSent(httppipelinerequest);
         }
      } catch (InterruptedException var4) {
         return;
      } catch (Exception var5) {
         this.httpPipelineConnection.onExceptionSend(httppipelinerequest, var5);
      }

   }

   private void connect() throws IOException {
      String s = this.httpPipelineConnection.getHost();
      int i = this.httpPipelineConnection.getPort();
      Proxy proxy = this.httpPipelineConnection.getProxy();
      Socket socket = new Socket(proxy);
      socket.connect(new InetSocketAddress(s, i), 5000);
      this.httpPipelineConnection.setSocket(socket);
   }

   private void writeRequest(HttpRequest req, OutputStream out) throws IOException {
      this.write(out, req.getMethod() + " " + req.getFile() + " " + req.getHttp() + "\r\n");
      Map<String, String> map = req.getHeaders();
      Iterator var4 = map.keySet().iterator();

      while(var4.hasNext()) {
         String s = (String)var4.next();
         String s1 = (String)req.getHeaders().get(s);
         this.write(out, s + ": " + s1 + "\r\n");
      }

      this.write(out, "\r\n");
   }

   private void write(OutputStream out, String str) throws IOException {
      byte[] abyte = str.getBytes(ASCII);
      out.write(abyte);
   }

   static {
      ASCII = StandardCharsets.US_ASCII;
   }
}
