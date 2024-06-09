package exhibition.management.spotify.othershit;

import com.google.common.base.Preconditions;
import exhibition.management.spotify.HttpResponseCallback;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SpotifyHttpClient {
   public static void get(String url, final Map headers, int timeout, boolean ignoreInvalidCertificates, EventLoopGroup eventLoop, final HttpResponseCallback callback) {
      Preconditions.checkNotNull(url, "url");
      Preconditions.checkNotNull(eventLoop, "eventLoop");
      Preconditions.checkNotNull(callback, "callBack");
      final URI uri = URI.create(url);
      Preconditions.checkNotNull(uri.getScheme(), "scheme");
      Preconditions.checkNotNull(uri.getHost(), "host");
      boolean ssl = uri.getScheme().equals("https");
      int port = uri.getPort();
      if (port == -1) {
         if (uri.getScheme().equals("http")) {
            port = 80;
         } else {
            if (!uri.getScheme().equals("https")) {
               throw new IllegalArgumentException("Unknown scheme " + uri.getScheme());
            }

            port = 443;
         }
      }

      InetAddress inetHost = null;

      try {
         inetHost = InetAddress.getByName(uri.getHost());
      } catch (UnknownHostException var11) {
         callback.call((String)null, -1, var11);
         return;
      }

      ChannelFutureListener future = new ChannelFutureListener() {
         public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
               String path = uri.getRawPath() + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
               HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
               request.headers().set("Host", uri.getHost());
               request.headers().set("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
               Iterator var4 = headers.entrySet().iterator();

               while(var4.hasNext()) {
                  Entry entry = (Entry)var4.next();
                  request.headers().set((String)entry.getKey(), entry.getValue());
               }

               future.channel().writeAndFlush(request);
            } else {
               callback.call((String)null, -1, future.cause());
            }

         }
      };
      ((Bootstrap)((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).channel(NioSocketChannel.class)).group(eventLoop)).handler(new SpotifyHttpInitializer(callback, ssl, uri.getHost(), port, timeout, ignoreInvalidCertificates))).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)).remoteAddress(inetHost, port).connect().addListener(future);
   }
}
