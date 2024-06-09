package exhibition.management.spotify.othershit;

import exhibition.management.spotify.HttpResponseCallback;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SpotifyHttpInitializer extends ChannelInitializer {
   private static final TrustManager[] INSECURE_TRUST_MANAGER = new TrustManager[]{new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }
   }};
   private final HttpResponseCallback callback;
   private final boolean ssl;
   private final String host;
   private final int port;
   private int timeout;
   private boolean ignoreInvalidCertificates;

   public SpotifyHttpInitializer(HttpResponseCallback callback, boolean ssl, String host, int port, int timeout, boolean ignoreInvalidCertificates) {
      this.callback = callback;
      this.ssl = ssl;
      this.host = host;
      this.port = port;
      this.timeout = timeout;
      this.ignoreInvalidCertificates = ignoreInvalidCertificates;
   }

   protected void initChannel(Channel ch) throws Exception {
      ch.pipeline().addLast("timeout", new ReadTimeoutHandler((long)this.timeout, TimeUnit.MILLISECONDS));
      if (this.ssl) {
         SSLEngine engine;
         if (this.ignoreInvalidCertificates) {
            engine = this.createInsecureSSLEngine();
         } else {
            try {
               Class sslContextClass = Class.forName("io.netty.handler.ssl.SslContext");
               Object clientContext = sslContextClass.getMethod("newClientContext").invoke((Object)null);
               engine = (SSLEngine)clientContext.getClass().getMethod("newEngine", ByteBufAllocator.class, String.class, Integer.TYPE).invoke(clientContext, ch.alloc(), this.host, this.port);
            } catch (Exception var5) {
               engine = this.createInsecureSSLEngine();
            }
         }

         ch.pipeline().addLast("ssl", new SslHandler(engine));
      }

      ch.pipeline().addLast("http", new HttpClientCodec());
      ch.pipeline().addLast("handler", new SpotifyHttpHandler(this.callback));
   }

   private SSLEngine createInsecureSSLEngine() throws KeyManagementException, NoSuchAlgorithmException {
      SSLContext context = SSLContext.getInstance("SSL");
      context.init((KeyManager[])null, INSECURE_TRUST_MANAGER, new SecureRandom());
      SSLEngine engine = context.createSSLEngine();
      engine.setUseClientMode(true);
      return engine;
   }
}
