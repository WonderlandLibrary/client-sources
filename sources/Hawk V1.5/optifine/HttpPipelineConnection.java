package optifine;

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

public class HttpPipelineConnection {
   private long keepaliveTimeoutMs;
   public static final int TIMEOUT_READ_MS = 5000;
   private int port;
   private String host;
   private HttpPipelineSender httpPipelineSender;
   private List<HttpPipelineRequest> listRequestsSend;
   public static final int TIMEOUT_CONNECT_MS = 5000;
   private Socket socket;
   private int countRequests;
   private HttpPipelineReceiver httpPipelineReceiver;
   private Proxy proxy;
   private boolean terminated;
   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
   private List<HttpPipelineRequest> listRequests;
   private static final String LF = "\n";
   private OutputStream outputStream;
   private InputStream inputStream;
   private int keepaliveMaxCount;
   private boolean responseReceived;
   private long timeLastActivityMs;

   public String getHost() {
      return this.host;
   }

   private void addRequest(HttpPipelineRequest var1, List<HttpPipelineRequest> var2) {
      var2.add(var1);
      this.notifyAll();
   }

   private synchronized void terminate(Exception var1) {
      if (!this.terminated) {
         this.terminated = true;
         this.terminateRequests(var1);
         if (this.httpPipelineSender != null) {
            this.httpPipelineSender.interrupt();
         }

         if (this.httpPipelineReceiver != null) {
            this.httpPipelineReceiver.interrupt();
         }

         try {
            if (this.socket != null) {
               this.socket.close();
            }
         } catch (IOException var3) {
         }

         this.socket = null;
         this.inputStream = null;
         this.outputStream = null;
      }

   }

   private void checkResponseHeader(HttpResponse var1) {
      String var2 = var1.getHeader("Connection");
      if (var2 != null && !var2.toLowerCase().equals("keep-alive")) {
         this.terminate(new EOFException("Connection not keep-alive"));
      }

      String var3 = var1.getHeader("Keep-Alive");
      if (var3 != null) {
         String[] var4 = Config.tokenize(var3, ",;");

         for(int var5 = 0; var5 < var4.length; ++var5) {
            String var6 = var4[var5];
            String[] var7 = this.split(var6, '=');
            if (var7.length >= 2) {
               int var8;
               if (var7[0].equals("timeout")) {
                  var8 = Config.parseInt(var7[1], -1);
                  if (var8 > 0) {
                     this.keepaliveTimeoutMs = (long)(var8 * 1000);
                  }
               }

               if (var7[0].equals("max")) {
                  var8 = Config.parseInt(var7[1], -1);
                  if (var8 > 0) {
                     this.keepaliveMaxCount = var8;
                  }
               }
            }
         }
      }

   }

   public synchronized boolean isClosed() {
      return this.terminated ? true : this.countRequests >= this.keepaliveMaxCount;
   }

   public synchronized HttpPipelineRequest getNextRequestSend() throws IOException, InterruptedException {
      if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
         this.outputStream.flush();
      }

      return this.getNextRequest(this.listRequestsSend, true);
   }

   public synchronized boolean hasActiveRequests() {
      return this.listRequests.size() > 0;
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

   public int getCountRequests() {
      return this.countRequests;
   }

   public synchronized void onResponseReceived(HttpPipelineRequest var1, HttpResponse var2) {
      if (!this.terminated) {
         this.responseReceived = true;
         this.onActivity();
         if (this.listRequests.size() <= 0 || this.listRequests.get(0) != var1) {
            throw new IllegalArgumentException(String.valueOf((new StringBuilder("Response out of order: ")).append(var1)));
         }

         this.listRequests.remove(0);
         var1.setClosed(true);
         String var3 = var2.getHeader("Location");
         if (var2.getStatus() / 100 == 3 && var3 != null && var1.getHttpRequest().getRedirects() < 5) {
            try {
               var3 = this.normalizeUrl(var3, var1.getHttpRequest());
               HttpRequest var7 = HttpPipeline.makeRequest(var3, var1.getHttpRequest().getProxy());
               var7.setRedirects(var1.getHttpRequest().getRedirects() + 1);
               HttpPipelineRequest var5 = new HttpPipelineRequest(var7, var1.getHttpListener());
               HttpPipeline.addRequest(var5);
            } catch (IOException var6) {
               var1.getHttpListener().failed(var1.getHttpRequest(), var6);
            }
         } else {
            HttpListener var4 = var1.getHttpListener();
            var4.finished(var1.getHttpRequest(), var2);
         }

         this.checkResponseHeader(var2);
      }

   }

   private void checkTimeout() {
      if (this.socket != null) {
         long var1 = this.keepaliveTimeoutMs;
         if (this.listRequests.size() > 0) {
            var1 = 5000L;
         }

         long var3 = System.currentTimeMillis();
         if (var3 > this.timeLastActivityMs + var1) {
            this.terminate(new InterruptedException(String.valueOf((new StringBuilder("Timeout ")).append(var1))));
         }
      }

   }

   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
      while(this.outputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.outputStream;
   }

   private String[] split(String var1, char var2) {
      int var3 = var1.indexOf(var2);
      if (var3 < 0) {
         return new String[]{var1};
      } else {
         String var4 = var1.substring(0, var3);
         String var5 = var1.substring(var3 + 1);
         return new String[]{var4, var5};
      }
   }

   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> var1, boolean var2) throws InterruptedException {
      while(var1.size() <= 0) {
         this.checkTimeout();
         this.wait(1000L);
      }

      this.onActivity();
      if (var2) {
         return (HttpPipelineRequest)var1.remove(0);
      } else {
         return (HttpPipelineRequest)var1.get(0);
      }
   }

   public int getPort() {
      return this.port;
   }

   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
      return this.getNextRequest(this.listRequests, false);
   }

   public synchronized void onRequestSent(HttpPipelineRequest var1) {
      if (!this.terminated) {
         this.onActivity();
      }

   }

   public HttpPipelineConnection(String var1, int var2) {
      this(var1, var2, Proxy.NO_PROXY);
   }

   private void onActivity() {
      this.timeLastActivityMs = System.currentTimeMillis();
   }

   public synchronized boolean addRequest(HttpPipelineRequest var1) {
      if (this.isClosed()) {
         return false;
      } else {
         this.addRequest(var1, this.listRequests);
         this.addRequest(var1, this.listRequestsSend);
         ++this.countRequests;
         return true;
      }
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public synchronized void setSocket(Socket var1) throws IOException {
      if (!this.terminated) {
         if (this.socket != null) {
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

   public synchronized void onExceptionSend(HttpPipelineRequest var1, Exception var2) {
      this.terminate(var2);
   }

   private String normalizeUrl(String var1, HttpRequest var2) {
      if (patternFullUrl.matcher(var1).matches()) {
         return var1;
      } else if (var1.startsWith("//")) {
         return String.valueOf((new StringBuilder("http:")).append(var1));
      } else {
         String var3 = var2.getHost();
         if (var2.getPort() != 80) {
            var3 = String.valueOf((new StringBuilder(String.valueOf(var3))).append(":").append(var2.getPort()));
         }

         if (var1.startsWith("/")) {
            return String.valueOf((new StringBuilder("http://")).append(var3).append(var1));
         } else {
            String var4 = var2.getFile();
            int var5 = var4.lastIndexOf("/");
            return var5 >= 0 ? String.valueOf((new StringBuilder("http://")).append(var3).append(var4.substring(0, var5 + 1)).append(var1)) : String.valueOf((new StringBuilder("http://")).append(var3).append("/").append(var1));
         }
      }
   }

   private void terminateRequests(Exception var1) {
      if (this.listRequests.size() > 0) {
         HttpPipelineRequest var2;
         if (!this.responseReceived) {
            var2 = (HttpPipelineRequest)this.listRequests.remove(0);
            var2.getHttpListener().failed(var2.getHttpRequest(), var1);
            var2.setClosed(true);
         }

         while(this.listRequests.size() > 0) {
            var2 = (HttpPipelineRequest)this.listRequests.remove(0);
            HttpPipeline.addRequest(var2);
         }
      }

   }

   public synchronized InputStream getInputStream() throws InterruptedException, IOException {
      while(this.inputStream == null) {
         this.checkTimeout();
         this.wait(1000L);
      }

      return this.inputStream;
   }

   public synchronized void onExceptionReceive(HttpPipelineRequest var1, Exception var2) {
      this.terminate(var2);
   }
}
