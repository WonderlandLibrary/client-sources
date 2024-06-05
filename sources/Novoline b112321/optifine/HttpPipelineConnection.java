package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import optifine.Config;
import optifine.HttpListener;
import optifine.HttpPipeline;
import optifine.HttpPipelineReceiver;
import optifine.HttpPipelineRequest;
import optifine.HttpPipelineSender;
import optifine.HttpRequest;
import optifine.HttpResponse;
import optifine.MatchBlock;

public class HttpPipelineConnection {
   private String host;
   private int port;
   private Proxy proxy;
   private List listRequests;
   private List listRequestsSend;
   private Socket socket;
   private InputStream inputStream;
   private OutputStream outputStream;
   private HttpPipelineSender httpPipelineSender;
   private HttpPipelineReceiver httpPipelineReceiver;
   private int countRequests;
   private boolean responseReceived;
   private long keepaliveTimeoutMs;
   private int keepaliveMaxCount;
   private long timeLastActivityMs;
   private boolean terminated;
   private static final String LF = "\n";
   public static final int TIMEOUT_CONNECT_MS = 5000;
   public static final int TIMEOUT_READ_MS = 5000;
   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");

   public HttpPipelineConnection(String var1, int var2) {
      this(var1, var2, Proxy.NO_PROXY);
   }

   public HttpPipelineConnection(String var1, int var2, Proxy var3) {
      this.host = null;
      this.port = 0;
      this.proxy = Proxy.NO_PROXY;
      this.listRequests = new LinkedList();
      this.listRequestsSend = new LinkedList();
      this.socket = null;
      this.inputStream = null;
      this.outputStream = null;
      this.httpPipelineSender = null;
      this.httpPipelineReceiver = null;
      this.countRequests = 0;
      this.responseReceived = false;
      this.keepaliveTimeoutMs = 5000L;
      this.keepaliveMaxCount = 1000;
      this.timeLastActivityMs = System.currentTimeMillis();
      this.terminated = false;
      this.host = var1;
      this.port = var2;
      this.proxy = var3;
      this.httpPipelineSender = new HttpPipelineSender(this);
      this.httpPipelineSender.start();
      this.httpPipelineReceiver = new HttpPipelineReceiver(this);
      this.httpPipelineReceiver.start();
   }

   public synchronized boolean addRequest(HttpPipelineRequest var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.isClosed()) {
         return false;
      } else {
         this.addRequest(var1, this.listRequests);
         this.addRequest(var1, this.listRequestsSend);
         ++this.countRequests;
         return true;
      }
   }

   private void addRequest(HttpPipelineRequest var1, List var2) {
      var2.add(var1);
      this.notifyAll();
   }

   public synchronized void setSocket(Socket var1) throws IOException {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!this.terminated) {
         if(this.socket != null) {
            throw new IllegalArgumentException("Already connected");
         }

         this.socket = var1;
         this.socket.setTcpNoDelay(true);
         this.inputStream = this.socket.getInputStream();
         this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
         this.onActivity();
         this.notifyAll();
      }

   }

   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.outputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.outputStream;
   }

   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.inputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.inputStream;
   }

   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.listRequestsSend.size() <= 0 && this.outputStream != null) {
         this.outputStream.flush();
      }

      return this.getNextRequest(this.listRequestsSend, true);
   }

   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
      return this.getNextRequest(this.listRequests, false);
   }

   private HttpPipelineRequest getNextRequest(List var1, boolean var2) throws InterruptedException {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1.size() <= 0) {
         this.checkTimeout();
         this.wait(1000L);
      }

      this.onActivity();
      return (HttpPipelineRequest)var1.remove(0);
   }

   private void checkTimeout() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.socket != null) {
         long var2 = this.keepaliveTimeoutMs;
         if(this.listRequests.size() > 0) {
            var2 = 5000L;
         }

         long var4 = System.currentTimeMillis();
         if(var4 > this.timeLastActivityMs + var2) {
            this.terminate(new InterruptedException("Timeout " + var2));
         }
      }

   }

   private void onActivity() {
      this.timeLastActivityMs = System.currentTimeMillis();
   }

   public synchronized void onRequestSent(HttpPipelineRequest var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!this.terminated) {
         this.onActivity();
      }

   }

   public synchronized void onResponseReceived(HttpPipelineRequest var1, HttpResponse var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(!this.terminated) {
         this.responseReceived = true;
         this.onActivity();
         if(this.listRequests.size() > 0 && this.listRequests.get(0) == var1) {
            label52: {
               this.listRequests.remove(0);
               var1.setClosed(true);
               String var4 = var2.getHeader("Location");
               if(var2.getStatus() / 100 == 3 && var1.getHttpRequest().getRedirects() < 5) {
                  try {
                     var4 = this.normalizeUrl(var4, var1.getHttpRequest());
                     HttpRequest var9 = HttpPipeline.makeRequest(var4, var1.getHttpRequest().getProxy());
                     var9.setRedirects(var1.getHttpRequest().getRedirects() + 1);
                     HttpPipelineRequest var6 = new HttpPipelineRequest(var9, var1.getHttpListener());
                     HttpPipeline.addRequest(var6);
                     break label52;
                  } catch (IOException var7) {
                     var1.getHttpListener().failed(var1.getHttpRequest(), var7);
                  }
               }

               HttpListener var5 = var1.getHttpListener();
               var5.finished(var1.getHttpRequest(), var2);
            }

            this.checkResponseHeader(var2);
         }

         throw new IllegalArgumentException("Response out of order: " + var1);
      }
   }

   private String normalizeUrl(String var1, HttpRequest var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(patternFullUrl.matcher(var1).matches()) {
         return var1;
      } else if(var1.startsWith("//")) {
         return "http:" + var1;
      } else {
         String var4 = var2.getHost();
         if(var2.getPort() != 80) {
            var4 = var4 + ":" + var2.getPort();
         }

         if(var1.startsWith("/")) {
            return "http://" + var4 + var1;
         } else {
            String var5 = var2.getFile();
            int var6 = var5.lastIndexOf("/");
            return "http://" + var4 + var5.substring(0, var6 + 1) + var1;
         }
      }
   }

   private void checkResponseHeader(HttpResponse var1) {
      MatchBlock.b();
      String var3 = var1.getHeader("Connection");
      if(var3 != null && !var3.toLowerCase().equals("keep-alive")) {
         this.terminate(new EOFException("Connection not keep-alive"));
      }

      String var4 = var1.getHeader("Keep-Alive");
      if(var4 != null) {
         String[] var5 = Config.tokenize(var4, ",;");
         int var6 = 0;
         if(var6 < var5.length) {
            String var7 = var5[var6];
            String[] var8 = this.split(var7, '=');
            if(var8.length >= 2) {
               if(var8[0].equals("timeout")) {
                  int var9 = Config.parseInt(var8[1], -1);
                  if(var9 > 0) {
                     this.keepaliveTimeoutMs = (long)(var9 * 1000);
                  }
               }

               if(var8[0].equals("max")) {
                  int var11 = Config.parseInt(var8[1], -1);
                  this.keepaliveMaxCount = var11;
               }
            }

            ++var6;
         }
      }

   }

   private String[] split(String var1, char var2) {
      MatchBlock.b();
      int var4 = var1.indexOf(var2);
      if(var4 < 0) {
         return new String[]{var1};
      } else {
         String var5 = var1.substring(0, var4);
         String var6 = var1.substring(var4 + 1);
         return new String[]{var5, var6};
      }
   }

   public synchronized void onExceptionSend(HttpPipelineRequest var1, Exception var2) {
      this.terminate(var2);
   }

   public synchronized void onExceptionReceive(HttpPipelineRequest var1, Exception var2) {
      this.terminate(var2);
   }

   private synchronized void terminate(Exception var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!this.terminated) {
         this.terminated = true;
         this.terminateRequests(var1);
         if(this.httpPipelineSender != null) {
            this.httpPipelineSender.interrupt();
         }

         if(this.httpPipelineReceiver != null) {
            this.httpPipelineReceiver.interrupt();
         }

         try {
            if(this.socket != null) {
               this.socket.close();
            }
         } catch (IOException var4) {
            ;
         }

         this.socket = null;
         this.inputStream = null;
         this.outputStream = null;
      }

   }

   private void terminateRequests(Exception var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.listRequests.size() > 0) {
         if(!this.responseReceived) {
            HttpPipelineRequest var3 = (HttpPipelineRequest)this.listRequests.remove(0);
            var3.getHttpListener().failed(var3.getHttpRequest(), var1);
            var3.setClosed(true);
         }

         if(this.listRequests.size() > 0) {
            HttpPipelineRequest var4 = (HttpPipelineRequest)this.listRequests.remove(0);
            HttpPipeline.addRequest(var4);
         }
      }

   }

   public synchronized boolean isClosed() {
      PacketRemapper[] var1 = MatchBlock.b();
      return this.terminated?true:this.countRequests >= this.keepaliveMaxCount;
   }

   public int getCountRequests() {
      return this.countRequests;
   }

   public synchronized boolean hasActiveRequests() {
      PacketRemapper[] var1 = MatchBlock.b();
      return this.listRequests.size() > 0;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   private static Exception c(Exception var0) {
      return var0;
   }
}
